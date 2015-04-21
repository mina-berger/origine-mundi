/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package origine_mundi.opus;

import javax.sound.midi.ShortMessage;
import origine_mundi.Integers;
import origine_mundi.OmPlayer;
import origine_mundi.machine.D_110;

/**
 *
 * @author Mina
 */
public class Opus003 extends OmPlayer {
    @Override
     public void setSequence(){
        D_110 d_110 = D_110.instance();
        d_110.setChannels(0, 1, 2, 3, 4, 5, 6, 7, 8);
        d_110.callMemoryTone(1, 1);
        d_110.callMemoryTone(10, 2);
        d_110.callMemoryTone(36, 3);
        callDevice(0, d_110);
        tempo(135, 0);
        callDevice(0);
        callTrack(1);
        
        int measure = 48;
        int exp = 0;
        int cutoff = 50;
        int resonance = 15;
        int[] notes1 = new int[]{60, 55, 60, 62, 67, 62, 60, 62, 61, 55, 61, 63, 67, 63, 61, 63};
        int[] notes2 = new int[]{48, 52, 55, 53, 52, 59, 57, 55, 54, 52, 54, 57, 59, 55, 53, 47};
        int[] notes3 = new int[]{55, 60, 64, 57, 61, 65};
        brev(ShortMessage.CONTROL_CHANGE, 1, 0x0b, 100, 0);
        brev(ShortMessage.CONTROL_CHANGE, 2, 0x0b, 100, 0);
        for(int i = 0;i < measure;i++){
            double offset = (i + 1) * 4;
            cutoff = (int)(Math.random() * 50) + 50;
            resonance = (int)(Math.random() * 15) +15;
            //sysex(d_110.sysexToneTemporary(1, "partial0.TVF cutoff freq", cutoff), offset);
            //sysex(d_110.sysexToneTemporary(1, "partial1.TVF cutoff freq", cutoff), offset);
            //sysex(d_110.sysexToneTemporary(1, "partial2.TVF cutoff freq", cutoff), offset);
            //sysex(d_110.sysexToneTemporary(1, "partial3.TVF cutoff freq", cutoff), offset);
            exp = (int)(Math.random() * 64) + 64;
            brev(ShortMessage.CONTROL_CHANGE, 3, 0x0b, exp, offset);
            note(3, notes3[(i % 2) * 3 + 0] + 12, 100, offset, 3.8);
            note(3, notes3[(i % 2) * 3 + 1] + 12, 100, offset, 3.8);
            note(3, notes3[(i % 2) * 3 + 2] + 12, 100, offset, 3.8);
            for(int j = 0;j < notes1.length;j++){
                double pos = offset + 0.25 * j;
                exp = (int)(Math.random() * 64) + 64;
                brev(ShortMessage.CONTROL_CHANGE, 1, 0x0b, exp, pos);
                exp = (int)(Math.random() * 64) + 64;
                brev(ShortMessage.CONTROL_CHANGE, 2, 0x0b, exp, pos);
                /*sysex(d_110.sysexToneTemporary(1, "partial0.TVF resonance", resonance), pos);
                sysex(d_110.sysexToneTemporary(1, "partial1.TVF resonance", resonance), pos);
                sysex(d_110.sysexToneTemporary(1, "partial2.TVF resonance", resonance), pos);
                sysex(d_110.sysexToneTemporary(1, "partial3.TVF resonance", resonance), pos);
                */
                //cutoff = (int)(Math.random() * 50) + 50;
                //resonance = (int)(Math.random() * 15) + 15;
                /*sysex(d_110.sysexToneTemporary(2, "partial0.TVF cutoff freq", cutoff), pos);
                sysex(d_110.sysexToneTemporary(2, "partial1.TVF cutoff freq", cutoff), pos);
                sysex(d_110.sysexToneTemporary(2, "partial2.TVF cutoff freq", cutoff), pos);
                sysex(d_110.sysexToneTemporary(2, "partial3.TVF cutoff freq", cutoff), pos);
                sysex(d_110.sysexToneTemporary(2, "partial0.TVF resonance", resonance), pos);
                sysex(d_110.sysexToneTemporary(2, "partial1.TVF resonance", resonance), pos);
                sysex(d_110.sysexToneTemporary(2, "partial2.TVF resonance", resonance), pos);
                sysex(d_110.sysexToneTemporary(2, "partial3.TVF resonance", resonance), pos);
                */
                double length = Math.random() * 0.15 + 0.15;
                note(1, notes1[j], 80, pos, length);
                note(2, notes2[j], 100, pos, length);
                
            }
        }
    }
    //public static
}
