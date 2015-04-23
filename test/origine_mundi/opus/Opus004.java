/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package origine_mundi.opus;

import javax.sound.midi.ShortMessage;
import origine_mundi.OmPlayer;
import origine_mundi.machine.D_110;
import origine_mundi.UpdateMap;
import origine_mundi.UpdateMap.UpdateKV;

/**
 *
 * @author Mina
 */
public class Opus004 extends OmPlayer {
    @Override
     public void setSequence(){
        D_110 d_110 = D_110.instance();
        d_110.setChannels(0, 1, 2, 3, 4, 5, 6, 7, 8);
        d_110.updateTimbreTemporary(0, new UpdateMap(
                new UpdateKV("temporary.output level", 100),
                new UpdateKV("temporary.panpod", 0)
        ));
        d_110.updateTimbreTemporary(1, new UpdateMap(
                new UpdateKV("temporary.output level", 100),
                new UpdateKV("temporary.panpod", 14)
        ));
        d_110.callMemoryTone(10, 0);
        d_110.callMemoryTone(10, 1);
        callDevice(0, d_110);
        tempo(105, 0);
        callDevice(0);
        callTrack(1);
        
        brev(ShortMessage.CONTROL_CHANGE, 1, 0x0b, 100, 0);
        brev(ShortMessage.CONTROL_CHANGE, 0, 0x0b, 100, 0);
        note(1, 60, 80, 1, 10);
        note(0, 64, 80, 2, 9);

    }
    //public static
}
