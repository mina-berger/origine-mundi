/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package origine_mundi.machine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import org.apache.commons.lang3.ArrayUtils;
import origine_mundi.OmException;
import static origine_mundi.OmUtil.MICRO_LITE_2;
import static origine_mundi.machine.Roland.COMMAND_DT1;
import static origine_mundi.machine.Roland.ROLAND_ID;

/**
 *
 * @author Mina
 */
public class U_110 extends Roland {
    public static int DEVICE_ID = 0x10; // SET ON D_110
    public static int MODEL_ID = 0x16; //D_110
    private static String MIDI_PORT = MICRO_LITE_2;
    private static U_110 instance = null;
    public static U_110 instance(){
        if(instance == null){
            instance = new U_110();
        }
        return instance;
    }
    private U_110(){
        super(DEVICE_ID, MODEL_ID, MIDI_PORT, MIDI_PORT);
    }
    
}
