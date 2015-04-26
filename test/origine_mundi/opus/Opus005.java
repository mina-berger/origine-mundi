/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package origine_mundi.opus;

import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;
import origine_mundi.machine.MU500;
import origine_mundi.simple.OmPlayerSimple;

/**
 *
 * @author Mina
 */
public class Opus005 extends OmPlayerSimple {
    @Override
    public void setSequence(){
        //MU500 mu500 = MU500.instance(0);
        //callDevice(0, mu500);
        tempo(95, 0);
        callDevice(0);
        callTrack(1);
        
        brev(ShortMessage.PROGRAM_CHANGE, 0, 0, 3, 0);
        brev(ShortMessage.CONTROL_CHANGE, 1, 0, 3, 0);
        //note(1, 60, 80, 1, 10);
        int[] notes0 = new int[]{60, 61, 62, 59};
        int[] notes1 = new int[]{64, 65, 65, 65};
        for(int i = 0;i < 2;i++){
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
            /*brev(ShortMessage.CONTROL_CHANGE, 0, 0x01, mod, pos);
            brev(ShortMessage.CONTROL_CHANGE, 0, 0x0a, pan, pos);
            brev(ShortMessage.CONTROL_CHANGE, 0, 0x0a, 127 - pan, pos + 0.5);
            brev(ShortMessage.CONTROL_CHANGE, 1, 0x01, 127 - mod, pos);
            brev(ShortMessage.CONTROL_CHANGE, 1, 0x0a, 127 - pan, pos);
            brev(ShortMessage.CONTROL_CHANGE, 1, 0x0a, pan, pos + 0.65);*/
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
    public static void main(String[] args) throws Exception {
        Sequencer sequencer = null;
        try {
            sequencer = MidiSystem.getSequencer();
            sequencer.open();

            Sequence seq = new Sequence(Sequence.PPQ, 240);
            Track track = seq.createTrack();

            MetaMessage tempo = new MetaMessage();
            tempo.setMessage(0x51, new byte[] {0x07, (byte)0xa1, 0x20}, 3);
            track.add(new MidiEvent(tempo, 0));
        
            ShortMessage noteOn1 = new ShortMessage();
            noteOn1.setMessage(ShortMessage.NOTE_ON, 62, 64);
            track.add(new MidiEvent(noteOn1, 0));

            ShortMessage noteOn2 = new ShortMessage();
            noteOn2.setMessage(ShortMessage.NOTE_OFF, 60, 0);
            track.add(new MidiEvent(noteOn2, 480));
        
            sequencer.setSequence(seq);
            sequencer.start();
            while (sequencer.isRunning()) Thread.sleep(100);
        }
        finally {
            if (sequencer != null && sequencer.isOpen()) sequencer.close();
        }
    }
}
