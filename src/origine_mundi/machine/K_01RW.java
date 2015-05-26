/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package origine_mundi.machine;

import static origine_mundi.OmUtil.MICRO_LITE_6;

/**
 *
 * @author Mina
 */
public class K_01RW extends Korg {
    private static final String MIDI_PORT = MICRO_LITE_6;

    private static K_01RW instance = null;
    public static K_01RW instance(){
        if(instance == null){
            instance = new K_01RW();
        }
        return instance;
    }
    public K_01RW() {
        super(MIDI_PORT, MIDI_PORT);
    }
    
}
