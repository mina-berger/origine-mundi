/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package origine_mundi.machine;

import java.util.ArrayList;
import java.util.Arrays;
import org.apache.commons.lang3.ArrayUtils;
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
    public SysexBuilder getRQT(Address address, Address length){
        ArrayList<Integer> data = new ArrayList<>(address);
        data.addAll(length);
        return new SysexBuilder(new int[]{ROLAND_ID, device, model, COMMAND_RQT}, 
                new SysexDataModel("RQT", new ByteValues("address", 3), new ByteValues("length", 3)), data, getChecksumRequest());
    }
    public SysexBuilder getDT1(SysexDataModel data_model, int[] data){
        return new SysexBuilder(new int[]{ROLAND_ID, device, model, COMMAND_DT1}, data_model, Arrays.asList(ArrayUtils.toObject(data)), getChecksumDump());
    }
    
}
