/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package origine_mundi;

import com.mina.util.Integers;
import java.util.List;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.SysexMessage;
import static origine_mundi.OmUtil.SYSEX_STATUS_AB;
import static origine_mundi.OmUtil.SYSEX_STATUS_AD;
import origine_mundi.SysexDataModel.DataUnitIndex;
import static origine_mundi.SysexDataModel.as7bitValues;
import static origine_mundi.SysexDataModel.as7bitValues;
import static origine_mundi.SysexDataModel.as7bitValues;
import static origine_mundi.SysexDataModel.as7bitValues;
import static origine_mundi.SysexDataModel.as7bitValues;
import static origine_mundi.SysexDataModel.as7bitValues;
import static origine_mundi.SysexDataModel.as7bitValues;
import static origine_mundi.SysexDataModel.as7bitValues;
import static origine_mundi.SysexDataModel.as7bitValues;
import static origine_mundi.SysexDataModel.as7bitValues;
import static origine_mundi.SysexDataModel.as7bitValues;
import static origine_mundi.SysexDataModel.as7bitValues;
import static origine_mundi.SysexDataModel.as7bitValues;
import static origine_mundi.SysexDataModel.as7bitValues;
import static origine_mundi.SysexDataModel.as7bitValues;
import static origine_mundi.SysexDataModel.as7bitValues;

/**
 *
 * @author Mina
 */
public class SysexBuilder {
    Integers _header;
    Integers contents;
    Integers footer;
    SysexDataModel data_model;
    boolean checksum;
    /*protected SysexBuilder(SysexBuilder sb){
        header = new ArrayUtilArrays.Utils.clone(sb.header);
        contents = new ArrayList(sb.contents);
        footer = ArrayUtils.clone(sb.footer);
    }*/
    public SysexBuilder(Integers header, SysexDataModel data_model, Integers data, boolean checksum){
        this._header = new Integers(SYSEX_STATUS_AB).append(header);
        if(data_model == null){
            throw new OmException("SysexDataModel cannot be null"); 
        }
        if(data == null){
            throw new OmException("data cannot be null"); 
        }
        if(data_model.length() != data.size()){
            throw new OmException("illegal data length " + data_model.getName() + "(" + data.size() + " expected " + data_model.length() + ")"); 
        }
        
        
        this.data_model = data_model;
        contents = new Integers(data);
        data_model.check(contents);
        footer = new Integers();
        footer.add(SYSEX_STATUS_AD);
        this.checksum = checksum;
    }
    public Explanations getExplanations(){
        return data_model.getExplanations(contents);
    }
    public String getValueText(String fullname){
        DataUnitIndex dui = data_model.getDataUnitIndex(fullname);
        return dui.getDataUnit().getText(contents, dui.getIndex());
    }
    public void setCharacters(String fullname, String text){
        DataUnitIndex dui = data_model.getDataUnitIndex(fullname);
        Integers values = new Integers();
        for(int i = 0;i < dui.length();i++){
            values.add(text.length() <= i?' ':(int)text.charAt(i));
        }
        setValue(dui, values);
    }
    public void update(UpdateMap update_map){
        for(String key:update_map.keySet()){
            Integers values = update_map.get(key);
            setValue(key, values);
        }
    }
    public void setValue(String fullname, int value){
        setValue(data_model.getDataUnitIndex(fullname), new Integers(value));
    }
    public void setValue(String fullname, Integers values){
        setValue(data_model.getDataUnitIndex(fullname), values);
    }
    private void setValue(DataUnitIndex dui, Integers values){
        if(values.size() != dui.length()){
            throw new OmException("illegal value length(expected " + dui.length() + " but " + values.size() + ")");
        }
        for(int i = 0;i < dui.length();i++){
            contents.set(dui.getIndex() + i, values.get(i));
        }
        dui.check(contents);
    }
    public List<Integer> getValues(String fullname){
        DataUnitIndex dui = data_model.getDataUnitIndex(fullname);
        int index = dui.getIndex();
        int length = dui.length();
        return contents.subList(index, index + length);
    }
    public int getInt(String fullname){
        return as7bitValues(getValues(fullname));
    }
    /*public void setDatum(int index, int value, HashMap<Integer, String> map){
        if(map.containsKey(value)){
            setDatum(index, value);
        }else{
            throw new OmException("illegal value : " + Integer.toHexString(value));
        }
    }
    public void setDatum(int index, int value, int min, int max){
        if(value >= min && value <= max){
            setDatum(index, value);
        }else{
            throw new OmException("illegal value : " + Integer.toHexString(value));
        }
    }

    public void setDatum(int index, int value){
        contents.set(index + 3, value);
    }
    public int getDatum(int index){
        return contents.get(index + 3);
    }
    public void setData(int index, List<Integer> data){
        for(int i = 0;i < data.size();i++){
            setDatum(index + i, data.get(i));
        }
    }
    public ArrayList<Integer> getData(int index, int length){
        ArrayList<Integer> list = new ArrayList<>();
        for(int i = 0;i < length;i++){
            list.add(getDatum(index + i));
        }
        return list;
    }*/
    public SysexMessage getSysex(){
        try {
            Integers list = new Integers(_header).append(contents);
            if(checksum){
                list.add(checksum(contents));
            }
            list.addAll(footer);
            
            return OmUtil.sysex(list);
        } catch (InvalidMidiDataException ex) {
            throw new OmException("failed to retrieve SysexMessage", ex);
        }
    }
    private int checksum(Integers data){
        int sum = 0;
        for(int datum:data){
            sum += datum;
        }
        return 0x80 - (sum % 0x80);
    }
    /*public void setAsciiString(int index, int length, String str){
        ArrayList<Integer> data = new ArrayList<>();
        for(int i = 0;i < length;i++){
            data.add(str.length() > i?(int)str.charAt(i):0x20);
        }
        setData(index, data);
    }
    public String getAsciiString(int index, int length){
        StringBuilder sb = new StringBuilder();
        for(Integer value:getData(index, length)){
            sb.append((char)value.intValue());
        }
        return sb.toString();
    }
    public String getText(int index, HashMap<Integer, String> map){
        int value = getDatum(0x0b);
        for(Integer key:map.keySet()){
            if(key.equals(value)){
                return "(" + Integer.toHexString(key) + ") " + map.get(key);
            }
        }
        return "(" + Integer.toHexString(value) + ") N/A";
    }
    public String getNoteText(int index, int shift){
        int value = getDatum(index);
        return "("+ Integer.toHexString(value) + ") " + new OmUtil.Note(value + shift).toString();
    }
    public String getSignedText(int index, int zero){
        int value = getDatum(index);
        return "("+ Integer.toHexString(value) + ") " + 
                (value == zero?" 00":
                 value  < zero?"-" + OmUtil.fill(Integer.toString((value - zero) * -1), 2)
                              :"+" + OmUtil.fill(Integer.toString( value - zero      ), 2));        
    }
    
    protected static HashMap<Integer, String> toMap(Object... key_value){
        if(key_value.length % 2 != 0){
            throw new OmException("key_value length should be even.");
        }
        HashMap<Integer, String> map = new HashMap<>();
        for(int i = 0;i < key_value.length / 2;i++){
            if(!(key_value[i * 2] instanceof Integer)){
                throw new OmException("key_value[" + (i * 2) + "] should be integer.");
            }
            if(!(key_value[i * 2 + 1] instanceof String)){
                throw new OmException("key_value[" + (i * 2 + 1) + "] should be String.");
            }
            map.put((Integer)key_value[i * 2], (String)key_value[i * 2 + 1]);
        }
        return map;
    }*/
}
