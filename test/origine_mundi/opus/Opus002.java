/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package origine_mundi.opus;

import javax.sound.midi.ShortMessage;
import origine_mundi.OmBrevCreator;
import origine_mundi.player.OmPlayer;
import origine_mundi.machine.TG77;

/**
 *
 * @author Mina
 */
public class Opus002 extends OmPlayer {

    @Override
    public void setSequence() {
        callDevice(0, TG77.instance());
        callTrack(1);
        /*for(int i = 0;i < 16;i++){
            //program(i, 0, 0, i, 0);
            brev(ShortMessage.CONTROL_CHANGE, i, 10, i % 2 == 0?0:127, 0);
            note(i, 60, 85, 4 + i * 3, 4);
        }*/
        /*brev(ShortMessage.CONTROL_CHANGE, 0, 7, 124, 0);
        brev(ShortMessage.CONTROL_CHANGE, 1, 7, 0, 0);
        brev(ShortMessage.CONTROL_CHANGE, 2, 7, 124, 0);
        brev(ShortMessage.CONTROL_CHANGE, 3, 7, 0, 0);
        brev(ShortMessage.CONTROL_CHANGE, 1, 10, 0, 0);
        brev(ShortMessage.CONTROL_CHANGE, 3, 10, 127, 0);*/
        int measure = 64;
        program(0, 0, 0, 118, 0);
        program(0, 0, 0, 63, 0);
        tempo(104, 0);
        OmBrevCreator drum = new OmBrevCreator(0, 0, 4);
        drum.note(0, 38, 120, 0,    0.5);
        drum.note(0, 79, 120, 1,    0.5);
        drum.note(0, 38,  60, 1.75, 0.1);
        drum.note(0, 38, 120, 2,    0.5);
        drum.note(0, 79, 120, 3,    0.5);
        for(int i = 0;i < 4;i++){
            drum.note(0, 57, 120, i ,       0.5);
            drum.note(0, 57,  80, i + 0.25, 0.5);
            drum.note(0, 57,  80, i + 0.5, 0.5);
            drum.note(0, 57,  95, i + 0.75, 0.5);
        }
        brev(drum.getBrev(1, measure + 1));
        callTrack(2);
        program(1, 0, 0, 26, 0);
        int notes[] = new int[]{64, 71, 67, 66, 64, 74, 71, 69, 71, 66, 67};
        int k = 0;
        for(int i = 1;i <= measure;i++){
            note(1, notes[k], 95, i * 4 + 0,    0.5);
            k = (k+ 1) % notes.length;
            note(1, notes[k], 95, i * 4 + 0.5,  0.25);
            k = (k+ 1) % notes.length;
            note(1, notes[k], 95, i * 4 + 0.75, 0.5);
            k = (k+ 1) % notes.length;
            note(1, notes[k], 95, i * 4 + 1.25, 0.5);
            k = (k+ 1) % notes.length;
            note(1, notes[k], 95, i * 4 + 1.75, 0.5);
            k = (k+ 1) % notes.length;
            note(1, notes[k], 95, i * 4 + 2.25, 0.5);
            k = (k+ 1) % notes.length;
            note(1, notes[k], 95, i * 4 + 2.75, 0.25);
            k = (k+ 1) % notes.length;
            note(1, notes[k], 95, i * 4 + 3,    0.5);
            k = (k+ 1) % notes.length;
            note(1, notes[k], 95, i * 4 + 3.5,  0.5);
            k = (k+ 1) % notes.length;
        }
        callTrack(3);
        program(2, 0, 0, 26, 0);
        for(int i = 0;i < measure;i++){
            int note = i % 8 < 4?28:24;
            note(2, note, 95, (i + 1) * 4 + 0,  0.25);
            note(2, note, 95, (i + 1) * 4 + 0.5,  0.2);
            note(2, note, 95, (i + 1) * 4 + 0.75, 0.2);
            note(2, note + 12, 95, (i + 1) * 4 + 2.25,  0.2);
            note(2, note + 12, 95, (i + 1) * 4 + 2.75,  0.2);
            note(2, i % 4 == 3?26:note, 95, (i + 1) * 4 + 3.5,  0.25);
            
        }
        callTrack(4);
        program(3, 0, 0, 41, 0);
        k = 1;
        for(int i = 1;i <= measure;i++){
            note(3, notes[k], 95, i * 4 + 0,    0.5);
            k = (k+ notes.length -1) % notes.length;
            note(3, notes[k], 95, i * 4 + 0.5,  0.25);
            k = (k+ notes.length -1) % notes.length;
            note(3, notes[k], 95, i * 4 + 0.75, 0.5);
            k = (k+ notes.length -1) % notes.length;
            note(3, notes[k], 95, i * 4 + 1.25, 0.5);
            k = (k+ notes.length -1) % notes.length;
            note(3, notes[k], 95, i * 4 + 1.75, 0.5);
            k = (k+ notes.length -1) % notes.length;
            note(3, notes[k], 95, i * 4 + 2.25, 0.5);
            k = (k+ notes.length -1) % notes.length;
            note(3, notes[k], 95, i * 4 + 2.75, 0.25);
            k = (k+ notes.length -1) % notes.length;
            note(3, notes[k], 95, i * 4 + 3,    0.5);
            k = (k+ notes.length -1) % notes.length;
            note(3, notes[k], 95, i * 4 + 3.5,  0.5);
            k = (k+ notes.length -1) % notes.length;
        }
    }
    
}
