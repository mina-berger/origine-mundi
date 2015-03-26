/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package origine_mundi.machine;

import java.util.ArrayList;
import origine_mundi.OmUtil;
import origine_mundi.SysexDataModel;
import static origine_mundi.machine.Roland.ROLAND_ID;

/**
 *
 * @author Mina
 */
public class Yamaha extends MidiMachine {
    protected static int YAMAHA_ID = 0x43;
    private int device_l;
    private int model;

    public Yamaha(int device_l, int model, String ex_device_name, String in_device_name) {
        super(ex_device_name, in_device_name, 3, false, true);
        this.device_l = device_l;
        this.model = model;
    }
    public SysexBuilder getRequest(int[] data){
        return new SysexBuilder(new int[]{YAMAHA_ID, 0x20 + device_l, model}, 
                new SysexDataModel("request",
                new SysexDataModel.Characters("dump code", 10),
                new SysexDataModel.Blank(16)
        ), OmUtil.toList(data), getChecksumRequest());
    }    
    
}
