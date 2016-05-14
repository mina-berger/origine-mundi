/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package origine_mundi.machine;

import origine_mundi.SysexBuilder;
import origine_mundi.MidiMachine;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;
import com.mina.util.Integers;
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
        Integers data = new Integers(address).append(length);
        return getRQT(data);
    }
    public SysexBuilder getRQT(int... address_length){
        return getRQT(new Integers(address_length));
    }
    public SysexBuilder getRQT(Integers data){
        return new SysexBuilder(new Integers(ROLAND_ID, device, model, COMMAND_RQT), 
                new SysexDataModel("RQT", new ByteValues("address", 3), new ByteValues("length", 3)), data, getChecksumRequest());
    }
    public SysexBuilder getDT1(SysexDataModel data_model, Integers data){
        return new SysexBuilder(new Integers(ROLAND_ID, device, model, COMMAND_DT1), data_model, data, getChecksumDump());
    }
    public static class AddressDeRoland extends Integers {
        public AddressDeRoland(int value0, int value1, int value2){
            super(value0, value1, value2);
            init();
        }
        public AddressDeRoland(Integers data){
            super(data);
            init();
        }
        public AddressDeRoland(AddressDeRoland base, AddressDeRoland length, int index){
            super(base.shift(length.multiply(index)));
            init();
        }
        private void init(){
            if(size() != 3){
                throw new IllegalArgumentException("data length must be 3");
            }
            for(int value:this){
                if(value < 0 || value >= 128){
                    throw new IllegalArgumentException("value is out of range[" + get(0) + "," + get(1) + "," + get(2) + "]");
                }
            }
        }
        @Override
        public String toString(){
            return getClass().getSimpleName() + "[" + OmUtil.hex(get(0)) + " " + OmUtil.hex(get(1)) + " " + OmUtil.hex(get(2)) + "]";
        }
        public AddressDeRoland multiply(int multiplier){
            int value0 = get(0) * multiplier;
            int value1 = get(1) * multiplier;
            int value2 = get(2) * multiplier;
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
            return shift(shift.get(0), shift.get(1), shift.get(2));
        }
        public AddressDeRoland shift(int shift0, int shift1, int shift2){
            int value0 = get(0) + shift0;
            int value1 = get(1) + shift1;
            int value2 = get(2) + shift2;
            value1 += value2 / 0x80;
            value2 %= 0x80;
            value0 += value1 / 0x80;
            value1 %= 0x80;
            return new AddressDeRoland(value0, value1, value2);
        }
    }
    
}
