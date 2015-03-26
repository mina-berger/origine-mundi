/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package origine_mundi;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.ArrayUtils;
import origine_mundi.SysexDataModel.DataBlock;
import origine_mundi.SysexDataModel.DataUnit;
import origine_mundi.machine.Explanations;
import origine_mundi.machine.Explanations.Explanation;

/**
 *
 * @author Mina
 */
public class SysexDataModel extends TreeMap<Integer, DataUnit>{
    private int length;
    private String name;
    private HashMap<String, Integer> indeces;
    public SysexDataModel(String name, DataUnit... data_units){
        this.name = name;
        int index = 0;
        indeces = new HashMap<>();
        for(DataUnit data_unit:data_units){
            put(index, data_unit);
            String data_unit_name = data_unit.getName();
            if(indeces.containsKey(data_unit_name)){
                throw new OmException("duplicate data unit name:" + data_unit_name);
            }
            indeces.put(data_unit_name, index);
            index += data_unit.length();
        }
        length = index;
    }
    public String getName(){
        return name;
    }
    public int length(){
        return length;
    }
    public void check(List<Integer> values){
        for(Integer index:keySet()){
            DataUnit data_unit = get(index);
            data_unit.check(values, index);
        }
    }
    public Explanations getExplanations(List<Integer> values){
        Explanations expls = new Explanations(name);
        for(Integer index:keySet()){
            DataUnit data_unit = get(index);
            data_unit.check(values, index);
            Explanations data_unit_expls = data_unit.getExplanations("", values, index);
            if(data_unit_expls != null){
                expls.addAll(data_unit_expls);
            }
        }
        return expls;
    }
    public DataUnitIndex getDataUnitIndex(String fullname){
        String[] names = fullname.split("\\.");
        if(!indeces.containsKey(names[0])){
            throw new OmException("cannot find data unit:" + fullname);
        }
        int index = indeces.get(names[0]);
        DataUnit data_unit = get(index);
        if(names.length == 1){
            return new DataUnitIndex(data_unit, index);
        }
        if(!(data_unit instanceof DataBlock)){
            System.out.println(data_unit.getClass().getName());
            throw new OmException("full_name is too deep:" + fullname);
        }
        DataBlock data_block = (DataBlock)data_unit;
        String[] sub_names = ArrayUtils.remove(names, 0);
        DataUnitIndex dui = data_block.getDataUnitIndex(sub_names);
        dui.addIndex(index);
        return dui;
        
    }
    public static class KV {
        int key;
        String value;
        public KV(int key, String value){
            this.key = key;
            this.value = value;
        }
    }
    public static abstract class DataUnit implements Cloneable {
        protected String name;
        protected DataUnit(String name){
            if(name.contains(".")){
                throw new IllegalArgumentException("cannot contain period(.) for name");
            }
            this.name = name;
        }
        public String getName(){
            return name;
        }
        public Explanations getExplanations(String prefix, List<Integer> values, int index){
            String text = getText(values, index);
            if(text == null){
                return null;
            }
            return new Explanations(getName(), new Explanation(prefix + name, text));
        }
        public DataUnit copy(String name){
            try {
                DataUnit du = (DataUnit)clone();
                du.name = name;
                return du;
            } catch (CloneNotSupportedException ex) {
                throw new OmException("failed to copy", ex);
            }
        }        
        public abstract int length();
        public abstract String getText(List<Integer> values, int index);
        public abstract void check(List<Integer> values, int index);
    }
    public static abstract class OneByte extends DataUnit {
        
        private int min;
        private int max;
        protected OneByte(String name){
            this(name, 0x00, 0x7F);
        }
        protected OneByte(String name, int min, int max){
            super(name);
            this.min = min;
            this.max = max;
        }
        @Override
        public int length(){
            return 1;
        }
        public void defaultCheck(List<Integer> values, int index) {
            defaultCheck(values, index, min, max);
        }
        public void defaultCheck(List<Integer> values, int index, int min, int max) {
            int value = values.get(index);
            if(value < min || value > max){
                throw new OmException("illegal data for " + getClass().getSimpleName() + " " + name + "(" + value + ")");
            }
        }
    }
   public static abstract class MultiBytes extends DataUnit {
        private int length;
        private int min;
        private int max;
        public MultiBytes(String name, int length){
            this(name, length, 0x00, 0x7f);
        }
        
        public MultiBytes(String name, int length, int min, int max){
            super(name);
            this.length = length;
            this.min = min;
            this.max = max;
        }
       @Override
        public int length() {
            return length;
        }
        public String getDataExpression(List<Integer> values, int index) {
            String str = "";
            for(int i = 0;i < length();i++){
                if(!str.isEmpty()){
                    str += ", ";
                }
                str += OmUtil.hex(values.get(index + i));
            }
            return str;
        }
        public void defaultCheck(List<Integer> values, int index) {
            for(int i = 0;i < length();i++){
                int value = values.get(index + i);
                if(value < min || value > max){
                    throw new OmException("illegal data for " + getClass().getSimpleName() + "[" + i + "] " + name);
                }
            }
        }
    }
    public static class ByteValue extends OneByte {
        public ByteValue(String name){
            super(name, 0x00, 0x7F);
        }
        public ByteValue(String name, int min, int max){
            super(name, min, max);
        }
        @Override
        public String getText(List<Integer> values, int index) {
            return OmUtil.hex(values.get(index));
        }

        @Override
        public void check(List<Integer> values, int index) {
            defaultCheck(values, index);
        }
    }
    public static class FixedValue extends ByteValue {
        public FixedValue(String name, int value){
            super(name, value, value);
        }
    }
    public static class OnOffValue extends OneByte {
        private int off_max;
        public OnOffValue(String name, int min, int off_max, int max){
            super(name, min, max);
            this.off_max = off_max;
        }
        @Override
        public String getText(List<Integer> values, int index) {
            int value = values.get(index);
            String str_expl = OmUtil.hex(values.get(index));
            if(value <= off_max){
                str_expl += " : off";
            }else{
                str_expl += " : on";
            }
            return str_expl;
        }

        @Override
        public void check(List<Integer> values, int index) {
            defaultCheck(values, index);
        }
    }
    public static class CodeValue extends OneByte {
        KV[] kvs;
        public CodeValue(String name, KV... kvs){
            super(name);
            this.kvs = kvs;
        }
        public CodeValue(String name, int min, int max, KV... kvs){
            super(name, min, max);
            this.kvs = kvs;
        }
        @Override
        public String getText(List<Integer> values, int index) {
            int value = values.get(index);
            String str_expl = OmUtil.hex(values.get(index));
            for(KV kv:kvs){
                if(kv.key == value)
                str_expl += " : " + kv.value;
            }
            return str_expl;
        }

        @Override
        public void check(List<Integer> values, int index) {
            defaultCheck(values, index);
        }
    }
   
    public static class OffsetBinary extends OneByte {
        int offset;
        public OffsetBinary(String name){
            this(name, 0x00, 0x7f, 0x40);
        }
        public OffsetBinary(String name, int min, int max, int offset){
            super(name, min, max);
            this.offset = offset;
        }
        @Override
        public String getText(List<Integer> values, int index) {
            int value = values.get(index);
            String str_expl = OmUtil.hex(values.get(index)) + " : " + 
                    (value == offset?"0":
                     value  < offset?"-" + (Integer.toString((value - offset) * -1))
                                    :"+" + (Integer.toString( value - offset)));                 
            return str_expl;
        }

        @Override
        public void check(List<Integer> values, int index) {
            defaultCheck(values, index);
        }
    }
    public static class MidiChannel extends OneByte {
        public MidiChannel(String name){
            super(name, 0x00, 0x0f);
        }
        @Override
        public String getText(List<Integer> values, int index) {
            return OmUtil.hex(values.get(index)) + " : ch." + values.get(index);
        }

        @Override
        public void check(List<Integer> values, int index) {
            defaultCheck(values, index);
        }
    }
    public static class NoteValue extends OneByte {
        int shift;
        public NoteValue(String name, int shift){
            this(name, 0x00, 0x0f, shift);
        }
        public NoteValue(String name, int min, int max, int shift){
            super(name, min, max);
            this.shift = shift;
        }
        @Override
        public String getText(List<Integer> values, int index) {
            int value = values.get(index);
            return OmUtil.hex(value) + " : " + new OmUtil.Note(value + shift).toString();
        }

        @Override
        public void check(List<Integer> values, int index) {
            defaultCheck(values, index);
        }
    }
    public static class BitArray extends OneByte {
        boolean reverse;
        int digit;
        public BitArray(String name, int digit, boolean reverse){
            super(name, 0, ((int)Math.pow(2, digit + 1)) - 1);
            this.digit = digit;
            this.reverse = reverse;
        }
        @Override
        public String getText(List<Integer> values, int index) {
            int value = values.get(index);
            String str = "[";
            if(reverse){
                for(int i = 0;i < digit;i++){
                    int mask = (int)Math.pow(2, i);
                    str += ((value & mask) == mask?"*":"-");
                }
            }else{
                for(int i = digit - 1;i >= 0;i--){
                    int mask = (int)Math.pow(2, i);
                    str += ((value & mask) == mask?"*":"-");
                }
            }
            str += "]";
            return OmUtil.hex(values.get(index)) + " : " + str;
        }

        @Override
        public void check(List<Integer> values, int index) {
            defaultCheck(values, index);
        }
        
    }
    public static class D110BiasPoint extends OneByte {
        public D110BiasPoint(String name){
            super(name, 0x00, 0x7f);
        }
        @Override
        public String getText(List<Integer> values, int index) {
            int value = values.get(index);
            return OmUtil.hex(value) + " : " + (value / 0x40 == 0?"<":">") + new OmUtil.Note((value % 0x40) + 33).toString();
        }

        @Override
        public void check(List<Integer> values, int index) {
            defaultCheck(values, index);
        }
    }
    
    public static class ByteValues extends MultiBytes {
        public ByteValues(String name, int length){
            super(name, length);
        }
        public ByteValues(String name, int length, int min, int max){
            super(name, length, min, max);
        }
        @Override
        public String getText(List<Integer> values, int index) {
            return getDataExpression(values, index);
        }
        @Override
        public void check(List<Integer> values, int index) {
            defaultCheck(values, index);
        }
    }
    public static class OffsetBinaries extends MultiBytes {
        int offset;
        public OffsetBinaries(String name, int length){
            this(name, length, 0x00, 0x7f, 0x40);
        }
        public OffsetBinaries(String name, int length, int min, int max, int offset){
            super(name, length, min, max);
            this.offset = offset;
        }
        @Override
        public String getText(List<Integer> values, int index) {
             String str = "";
            for(int i = 0;i < length();i++){
                if(!str.isEmpty()){
                    str += ", ";
                }
                int value = values.get(index + i);
                str += (value == offset?"0":
                        value  < offset?"-" + (Integer.toString((value - offset) * -1))
                                       :"+" + (Integer.toString( value - offset)));                 
            }
            return getDataExpression(values, index) + " : " + str;
        }

        @Override
        public void check(List<Integer> values, int index) {
            defaultCheck(values, index);
        }
    }
    public static class DataLength extends MultiBytes {
        public DataLength(String name, int length){
            super(name, length);
        }
        @Override
        public String getText(List<Integer> values, int index) {
            return getDataExpression(values, index) + " : " + as7bitValues(values, index, length());
        }
        @Override
        public void check(List<Integer> values, int index) {
            defaultCheck(values, index);
        }
    }
    public static class Blank extends MultiBytes {
        public Blank(int length){
            super("_blank", length);
        }
        public Blank(String name, int length){
            super(name, length);
        }
        @Override
        public String getText(List<Integer> values, int index) {
            return null;
        }
        @Override
        public void check(List<Integer> values, int index) {
            defaultCheck(values, index);
        }
    }
    public static class Reserve extends MultiBytes {
        int[] data;
        public Reserve(int[] data){
            this("_reserve", data);
        }
        public Reserve(String name, int[] data){
            super(name, data.length);
            this.data = data;
        }
        @Override
        public String getText(List<Integer> values, int index) {
            return getDataExpression(values, index);
        }
        @Override
        public void check(List<Integer> values, int index) {
            for(int i = 0;i < length();i++){
                if(values.get(index + i) != data[i]){
                    throw new OmException("reserve value is illegal(" + values.get(index + i) + "expected" + data[i] +")");
                }
            }
        }
    }
    public static class Characters extends MultiBytes {
        public Characters(String name, int length){
            super(name, length);
        }

        @Override
        public String getText(List<Integer> values, int index) {
            StringBuilder sb = new StringBuilder();
            for(int i = 0;i < length();i++){
                sb.append((char)values.get(index + i).intValue());
            }
            return "'" + sb.toString() + "'";
        }
        @Override
        public void check(List<Integer> values, int index) {
            if(values.size() < index + length()){
                throw new OmException("values is too short : required minimum " + (index + length()));
            }
            for(int i = 0;i < length();i++){
                int value = values.get(index + i);
                if(value < 0x20 || value > 0x7f){
                    throw new OmException("illegal data for Characters[" + i + "] " + name);
                }
            }
        }
    }
    public static class DataBlock extends DataUnit {
        private DataUnit[] data_units;
        private int length;
        private HashMap<String, Integer> indeces_to_array;
        private HashMap<String, Integer> indeces_to_data;
        public DataBlock(String name, DataUnit... data_units){
            super(name);
            this.data_units = data_units;
            length = 0;
            indeces_to_array = new HashMap<>();
            indeces_to_data = new HashMap<>();
            for(int i = 0;i < data_units.length;i++){
                DataUnit data_unit = data_units[i];
                String data_unit_name = data_unit.getName();
                if(indeces_to_array.containsKey(data_unit_name)){
                    throw new OmException("data unit duplicated:" + name + "." + data_unit_name);
                }
                indeces_to_array.put(data_unit_name, i);
                indeces_to_data .put(data_unit_name, length);
                length += data_unit.length();
            }
        }
        @Override
        public int length() {
            return length;
        }
        @Override
        public Explanations getExplanations(String prefix, List<Integer> values, int index) {
            Explanations expls = new Explanations(getName());
            for(DataUnit data_unit:data_units){
                Explanations data_unit_expls = data_unit.getExplanations(prefix + name + ".", values, index);
                if(data_unit_expls != null){
                    expls.addAll(data_unit_expls);
                }
                index += data_unit.length();
            }
            return expls;
        }

        @Override
        public void check(List<Integer> values, int index) {
            for(DataUnit data_unit:data_units){
                data_unit.check(values, index);
                index += data_unit.length();
            }
        }
        public DataUnitIndex getDataUnitIndex(String[] names){
            if(!indeces_to_array.containsKey(names[0])){
                throw new OmException("cannot find data unit:" + getName() + "." + names[0]);
            }
            DataUnit data_unit = data_units[indeces_to_array.get(names[0])];
            int index_to_data = indeces_to_data.get(names[0]);
            if(names.length == 1){
                return new DataUnitIndex(data_unit, index_to_data);
            }
            if(!(data_unit instanceof DataBlock)){
                throw new OmException("full_name is too deep:" + getName() + "." + names[0]);
            }
            DataBlock data_block = (DataBlock)data_unit;
            String[] sub_names = ArrayUtils.remove(names, 0);
            DataUnitIndex dui = data_block.getDataUnitIndex(sub_names);
            dui.addIndex(index_to_data);
            return dui;
        }
        @Override
        public String getText(List<Integer> values, int index) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        @Override
        public DataBlock copy(String name){
            try {
                DataBlock copy = (DataBlock)clone();//
                copy.name = name;
                return copy;
            } catch (CloneNotSupportedException ex) {
                throw new OmException("failed to copy", ex);
            }
        }
        
    }
    public static class DataUnitIndex {
        private DataUnit data_unit;
        private int index;
        DataUnitIndex(DataUnit data_unit, int index){
            this.data_unit = data_unit;
            this.index = index;
        }
        void addIndex(int index){
            this.index += index;
        }
        public DataUnit getDataUnit(){
            return data_unit;
        }
        public int getIndex(){
            return index;
        }
        public int length(){
            return data_unit.length();
        }
    }
    public static void main(String[] args){
        SysexDataModel model = new SysexDataModel("test", 
                new Characters("name", 3),
                new ByteValue("age"),
                new DataBlock("block", new Characters("color", 5), new ByteValue("number"),
                     new DataBlock("block2", new Characters("color2", 2), new ByteValue("number2"))),
                new Characters("hello", 2)
        );
        
        model.getExplanations(Arrays.asList(new Integer[]{0x20, 0x32, 0x33, 3, 0x35, 0x36, 0x37, 0x20, 0x20, 0x5, 0x40, 0x53, 0x2, 0x36, 0x37})).print();
        DataUnitIndex dui = model.getDataUnitIndex("block.block2.number2");
        System.out.println(dui.getDataUnit().getName() + ":" + dui.getIndex() + ":" + dui.getDataUnit().length());
    }

    public static int as7bitValues(List<Integer> values){
        return as7bitValues(values, 0, values.size());
    }
    public static int as7bitValues(List<Integer> values, int index, int length){
        int value = 0;
        for(int i = 0;i < length;i++){
            value += values.get(index + i) * Math.pow(0x80, length - i - 1);
        }
        return value;
    }
}
