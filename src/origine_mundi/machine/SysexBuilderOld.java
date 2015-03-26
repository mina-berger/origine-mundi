/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package origine_mundi.machine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.SysexMessage;
import org.apache.commons.lang3.ArrayUtils;
import origine_mundi.OmException;
import origine_mundi.OmUtil;

/**
 *
 * @author Mina
 */
public class SysexBuilderOld {
    public static int STATUS_AB  = 0xf0;
    public static int STATUS_AD  = 0xf7;
    int[] header;
    ArrayList<Integer> contents;
    int[] footer;
    protected SysexBuilderOld(SysexBuilderOld sb){
        header = ArrayUtils.clone(sb.header);
        contents = new ArrayList(sb.contents);
        footer = ArrayUtils.clone(sb.footer);
    }
    public SysexBuilderOld(int product, int device, int model, int command, MidiMachine.Address address){
        this(product, device, model, command, address, null);
    }
    public SysexBuilderOld(int product, int device, int model, int command, MidiMachine.Address address, List<Integer> data){
        header = new int[]{STATUS_AB ,product, device, model, command};
        contents = new ArrayList<>();
        contents.addAll(address);
        if(data != null){
            contents.addAll(data);
        }
        footer = new int[]{STATUS_AD};
    }
    public void add(List<Integer> data){
        contents.addAll(data);
    }
    public void setDatum(int index, int value, HashMap<Integer, String> map){
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
    }
    public SysexMessage getSysex() throws InvalidMidiDataException{
        int[] data = ArrayUtils.toPrimitive(contents.toArray(new Integer[0]));
        int checksum = checksum(data);
        ArrayList<Integer> list = new ArrayList<>();
        list.addAll(OmUtil.toList(header));
        list.addAll(OmUtil.toList(data));
        list.add(checksum);
        list.addAll(OmUtil.toList(footer));
        return OmUtil.sysex(list);
    }
    private int checksum(int[] data){
        int sum = 0;
        for(int datum:data){
            sum += datum;
        }
        return 0x80 - (sum % 0x80);
    }
    public void setAsciiString(int index, int length, String str){
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
    }
}
