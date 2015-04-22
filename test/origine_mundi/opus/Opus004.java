/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package origine_mundi.opus;

import javax.sound.midi.ShortMessage;
import origine_mundi.OmPlayer;
import origine_mundi.machine.D_110;
import origine_mundi.machine.SysexBuilder;

/**
 *
 * @author Mina
 */
public class Opus004 extends OmPlayer {
    @Override
     public void setSequence(){
        D_110 d_110 = D_110.instance();
        d_110.setChannels(0, 1, 2, 3, 4, 5, 6, 7, 8);
        SysexBuilder timbre0 = d_110.builderTimbreTemporary(0);
        timbre0.setValue("temporary.output level", 100);
        timbre0.setValue("temporary.panpod", 0);
        SysexBuilder timbre1 = d_110.builderTimbreTemporary(1);
        timbre1.setValue("temporary.output level", 100);
        timbre1.setValue("temporary.panpod", 14);
        timbre1.getExplanations().print();
        d_110.send(timbre1.getSysex());
        d_110.callMemoryTone(10, 0);
        d_110.callMemoryTone(10, 1);
        callDevice(0, d_110);
        tempo(105, 0);
        callDevice(0);
        callTrack(1);
        
        int measure = 48;
        int exp = 0;
        int cutoff = 50;
        int resonance = 15;
        brev(ShortMessage.CONTROL_CHANGE, 1, 0x0b, 100, 0);
        brev(ShortMessage.CONTROL_CHANGE, 0, 0x0b, 100, 0);
        note(1, 60, 80, 1, 10);
        note(0, 64, 80, 2, 9);

    }
    //public static
}
