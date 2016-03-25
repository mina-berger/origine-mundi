/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package origine_mundi.machine;

import origine_mundi.SysexBuilder;
import origine_mundi.MidiMachine;
import com.mina.util.Integers;
import origine_mundi.SysexDataModel;

/**
 *
 * @author Mina
 */
public class Yamaha extends MidiMachine {
    protected static int YAMAHA_ID = 0x43;
    protected int device_l;
    protected int model;

    public Yamaha(int device_l, int model, String ex_device_name, String in_device_name) {
        super(ex_device_name, in_device_name, 3, false, true);
        this.device_l = device_l;
        this.model = model;
    }
    public SysexBuilder getRequest(int[] data, SysexDataModel request_model){
        return getRequest(model, data, request_model);
    }
    public SysexBuilder getRequest(int model, int[] data, SysexDataModel request_model){
        return new SysexBuilder(new Integers(YAMAHA_ID, device_l, model), request_model, new Integers(data), getChecksumRequest());
    }
    
}
