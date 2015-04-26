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
import origine_mundi.machine.MU500;

/**
 *
 * @author Mina
 */
public class Opus004 extends OmPlayer {
    @Override
    public void setSequence(){
        MU500 mu500 = MU500.instance(0);
        callDevice(0, mu500);
        tempo(95, 0);
        callDevice(0);
        callTrack(1);
        
        brev(ShortMessage.PROGRAM_CHANGE, 0, 0, 3, 0);
        brev(ShortMessage.CONTROL_CHANGE, 1, 0, 3, 0);
        //note(1, 60, 80, 1, 10);
        int[] notes0 = new int[]{60, 61, 62, 59};
        int[] notes1 = new int[]{64, 65, 65, 65};
        for(int i = 0;i < 16;i++){
            measure(i, notes0[i % 4], notes1[i % 4]);
        }

    }
    public void _setSequence(){
        D_110 d_110 = D_110.instance();
        boolean setup = false;
        if(setup){
            d_110.setChannels(0, 1, 2, 3, 4, 5, 6, 7, 8);
            d_110.updateTimbreTemporary(0, new UpdateMap(
                    new UpdateKV("temporary.output level", 30),
                    new UpdateKV("temporary.panpod", 7)
            ));
            d_110.updateTimbreTemporary(1, new UpdateMap(
                    new UpdateKV("temporary.output level", 30),
                    new UpdateKV("temporary.panpod", 7)
            ));
            /*d_110.updateTimbreTemporary(8, new UpdateMap(
                    new UpdateKV("temporary.output level", 100)
            ));*/
            d_110.callMemoryTone(5, 0);
            d_110.callMemoryTone(5, 1);
            d_110.updateToneTemporary(0, new UpdateMap(
                    new UpdateKV("partial0.P-LFO depth", 100),
                    new UpdateKV("partial1.P-LFO depth", 100),
                    new UpdateKV("partial2.P-LFO depth", 100),
                    new UpdateKV("partial3.P-LFO depth", 100),
                    new UpdateKV("partial0.P-LFO mod sens", 100),
                    new UpdateKV("partial1.P-LFO mod sens", 100),
                    new UpdateKV("partial2.P-LFO mod sens", 100),
                    new UpdateKV("partial3.P-LFO mod sens", 100)
            ));
            d_110.updateToneTemporary(1, new UpdateMap(
                    new UpdateKV("partial0.P-LFO depth", 100),
                    new UpdateKV("partial1.P-LFO depth", 100),
                    new UpdateKV("partial2.P-LFO depth", 100),
                    new UpdateKV("partial3.P-LFO depth", 100),
                    new UpdateKV("partial0.P-LFO mod sens", 100),
                    new UpdateKV("partial1.P-LFO mod sens", 100),
                    new UpdateKV("partial2.P-LFO mod sens", 100),
                    new UpdateKV("partial3.P-LFO mod sens", 100)
            ));
        }
        /*d_110.callMemoryTone(5, 0);
        d_110.updateToneTemporary(0, new UpdateMap(
                
                new UpdateKV("partial0.P-LFO depth", 100),
                new UpdateKV("partial1.P-LFO depth", 100),
                new UpdateKV("partial2.P-LFO depth", 100),
                new UpdateKV("partial3.P-LFO depth", 100),
                new UpdateKV("partial0.P-LFO mod sens", 100),
                new UpdateKV("partial1.P-LFO mod sens", 100),
                new UpdateKV("partial2.P-LFO mod sens", 100),
                new UpdateKV("partial3.P-LFO mod sens", 100)
        ));*/        
        //Logger.getLogger(OmUtil.class.getName()).log(Level.SEVERE, "HELLO");
        //assertEquals(1, 2);
        callDevice(0, d_110);
        tempo(95, 0);
        callDevice(0);
        callTrack(1);
        
        //brev(ShortMessage.CONTROL_CHANGE, 1, 0x0b, 100, 0);
        //note(1, 60, 80, 1, 10);
        int[] notes0 = new int[]{60, 61, 62, 59};
        int[] notes1 = new int[]{64, 65, 65, 65};
        for(int i = 0;i < 16;i++){
            measure(i, notes0[i % 4], notes1[i % 4]);
        }

    }
    private void measure(int measure, int note0, int note1){
        int length = 4;
        double head = 4 * measure + 1;
        for(int i = 0;i < length;i++){
            double pos = head + (double)i / 1d;
            int pan = i % 2 == 0?28:100;
            int mod = (int)((double)i / (double)length * 118d) + 10;
            int vol = (int)((double)(length - i - 1) / (double)length * 118) + 10;
            System.out.println(vol);
            brev(ShortMessage.CONTROL_CHANGE, 0, 0x01, mod, pos);
            brev(ShortMessage.CONTROL_CHANGE, 0, 0x0a, pan, pos);
            brev(ShortMessage.CONTROL_CHANGE, 0, 0x0a, 127 - pan, pos + 0.5);
            brev(ShortMessage.CONTROL_CHANGE, 1, 0x01, 127 - mod, pos);
            brev(ShortMessage.CONTROL_CHANGE, 1, 0x0a, 127 - pan, pos);
            brev(ShortMessage.CONTROL_CHANGE, 1, 0x0a, pan, pos + 0.65);
            //brev(ShortMessage.CONTROL_CHANGE, 1, 0x07, vol, pos);
            //brev(ShortMessage.CONTROL_CHANGE, 1, 0x07, vol, pos);
            note(0, note0, mod, pos, 1);
            note(0, note1, mod, pos, 1);
            note(1, note0, mod, pos, 1);
            note(1, note1, mod, pos, 1);
        }
        int ch_r = 9;
        note(ch_r, 36, 100, head, 0.2);
        note(ch_r, 36, 100, head + 2, 0.2);
        note(ch_r, 38, 100, head + 1, 0.2);
        note(ch_r, 38, 100, head + 3, 0.2);
        //note(8, 39, 100, head, 0.2);
        note(ch_r, 39, 100, head + 0.75, 0.2);
        //note(8, 78, 100, head + 2, 0.2);
        note(ch_r, 78, 100, head + 2.75, 0.2);
        note(ch_r, 42, 100, head, 0.2);
        note(ch_r, 42, 100, head + 0.5, 0.2);
        note(ch_r, 42, 100, head + 1.0, 0.2);
        note(ch_r, 42, 100, head + 1.5, 0.2);
        note(ch_r, 42, 100, head + 2.0, 0.2);
        note(ch_r, 42, 100, head + 2.5, 0.2);
        note(ch_r, 42, 100, head + 3.0, 0.2);
        note(ch_r, 42, 100, head + 3.5, 0.2);
    }
    //public static
}
