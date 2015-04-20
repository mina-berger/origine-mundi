/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package origine_mundi.opus;

import javax.sound.midi.ShortMessage;
import origine_mundi.OmPlayer;
import origine_mundi.machine.D_110;

/**
 *
 * @author Mina
 */
public class Opus003 extends OmPlayer {
    @Override
     public void setSequence(){
        
        callDevice(0, D_110.instance());
        
        tempo(84, 0);
        callDevice(0);
        callTrack(1);
        
        int measure = 16;
        int exp = 0;
        for(int i = 0;i < measure;i++){
            double offset = (i + 1) * 4;
            for(int j = 0;j < 16;j++){
                double pos = offset + 0.25 * j;
                note(0, 60, 100, pos, 0.2);
                brev(ShortMessage.CONTROL_CHANGE, 0, 0x0b, exp++ % 0x80, pos);
            }
        }
    }
    //public static
}
