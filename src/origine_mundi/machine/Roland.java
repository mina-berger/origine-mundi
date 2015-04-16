/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package origine_mundi.machine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;
import origine_mundi.OmUtil;
import origine_mundi.SysexDataModel;
import origine_mundi.SysexDataModel.ByteValues;
import static origine_mundi.machine.D_110.COMMAND_DT1;
import static origine_mundi.machine.D_110.COMMAND_RQT;

/**
 *
 * @author Mina
 */
public class Roland extends MidiMachine {
    protected static int ROLAND_ID = 0x41;
    public static int COMMAND_RQT = 0x11;
    public static int COMMAND_DT1 = 0x12;
    private final int device;
    private final int model;

    public Roland(int device, int model, String ex_device_name, String in_device_name) {
        super(ex_device_name, in_device_name, 4, true, true);
        this.device = device;
        this.model = model;
    }
    public int getDevice(){return device;}
    public int getModel(){return model;}
    /*public SysexBuilderOld getRQTOld(Address address, Address length){
        return new SysexBuilderOld(ROLAND_ID, device, model, COMMAND_RQT, address, length);
    }*/
    public SysexBuilder getRQT(AddressDeRoland address, AddressDeRoland length){
        ArrayList<Integer> data = new ArrayList<>();
        data.addAll(address.getValues());
        data.addAll(length.getValues());
        return getRQT(data);
    }
    public SysexBuilder getRQT(int... address_length){
        return getRQT(new ArrayList<>(OmUtil.toList(address_length)));
    }
    public SysexBuilder getRQT(ArrayList<Integer> data){
        return new SysexBuilder(new int[]{ROLAND_ID, device, model, COMMAND_RQT}, 
                new SysexDataModel("RQT", new ByteValues("address", 3), new ByteValues("length", 3)), data, getChecksumRequest());
    }
    public SysexBuilder getDT1(SysexDataModel data_model, int[] data){
        return new SysexBuilder(new int[]{ROLAND_ID, device, model, COMMAND_DT1}, data_model, Arrays.asList(ArrayUtils.toObject(data)), getChecksumDump());
    }
    public static class AddressDeRoland {
        List<Integer> data;
        public AddressDeRoland(int value0, int value1, int value2){
            data = new ArrayList<>(OmUtil.toList(new int[]{value0, value1, value2}));
            init();
        }
        public AddressDeRoland(List<Integer> data){
            this.data = data;
            init();
        }
        private void init(){
            if(data.size() != 3){
                throw new IllegalArgumentException("data length must be 3");
            }
            for(int value:data){
                if(value < 0 || value >= 128){
                    throw new IllegalArgumentException("value is out of range[" + data.get(0) + "," + data.get(1) + "," + data.get(2) + "]");
                }
            }
        }
        public List<Integer> getValues(){
            return data;
        }
        public AddressDeRoland multiply(int multiplier){
            int value0 = data.get(0) * multiplier;
            int value1 = data.get(1) * multiplier;
            int value2 = data.get(2) * multiplier;
            value1 += value2 / 0x80;
            value2 %= 0x80;
            value0 += value1 / 0x80;
            value1 %= 0x80;
            return new AddressDeRoland(value0, value1, value2);
        }
        public AddressDeRoland shift(int shift){
            return shift(0, 0, shift);
        }
        public AddressDeRoland shift(AddressDeRoland shift){
            return shift(shift.data.get(0), shift.data.get(1), shift.data.get(2));
        }
        public AddressDeRoland shift(int shift0, int shift1, int shift2){
            int value0 = data.get(0) + shift0;
            int value1 = data.get(1) + shift1;
            int value2 = data.get(2) + shift2;
            value1 += value2 / 0x80;
            value2 %= 0x80;
            value0 += value1 / 0x80;
            value1 %= 0x80;
            return new AddressDeRoland(value0, value1, value2);
        }
    }
    
}
