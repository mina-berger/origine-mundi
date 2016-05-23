/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package origine_mundi.machine;

import static origine_mundi.OmUtil.MICRO_LITE_3;

/**
 *
 * @author Mina
 */
public class M3R extends Korg {
    private static final String MIDI_PORT = MICRO_LITE_3;

    private static M3R instance = null;
    public static M3R instance(){
        if(instance == null){
            instance = new M3R(MIDI_PORT);
        }
        return instance;
    }
    public M3R(String midi_port) {
        super(midi_port, midi_port);
    }
    
}
