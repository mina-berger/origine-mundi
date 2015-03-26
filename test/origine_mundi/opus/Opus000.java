/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package origine_mundi.opus;

import javax.sound.midi.ShortMessage;
import origine_mundi.OmBrevCreator;
import origine_mundi.OmPlayer;
import origine_mundi.machine.D_110;
import origine_mundi.machine.MU500;
import origine_mundi.machine.TG77;
import origine_mundi.machine.U_110;

/**
 *
 * @author Mina
 */
public class Opus000 extends OmPlayer {
    @Override
     public void setSequence(){
        
        callDevice(0, TG77.instance());
        callDevice(1, MU500.instance(0));
        callDevice(2, D_110.instance());
        callDevice(3, U_110.instance());
        //callDevice(1, MU500_2);
        
        tempo(84, 0);
        callDevice(1);
        callTrack(1);
        program(9, 127, 0, 1, 0);
        
        int measure = 32;
        note(9, 36, 120, 2.75, 0.5);
        note(9, 38, 90, 2.98, 0.5);
        note(9, 38, 120, 3, 0.5);
        note(9, 46, 120, 3.75, 0.5);
        
        OmBrevCreator drum = new OmBrevCreator(0, 1, 4);
        drum.note(9, 36, 120, 0,    0.5);
        drum.note(9, 38, 120, 1,    0.5);
        drum.note(9, 36,  60, 1.75, 0.1);
        drum.note(9, 36, 120, 2,    0.5);
        drum.note(9, 38, 120, 3,    0.5);
        for(int i = 0;i < 4;i++){
            drum.note(9, 42, 120, i ,       0.5);
            drum.note(9, 42,  80, i + 0.25, 0.5);
            drum.note(9, 42,  80, i + 0.5, 0.5);
            drum.note(9, 42,  95, i + 0.75, 0.5);
        }
        brev(drum.getBrev(1, measure + 1));
        /*for(int j = 0;j < measure;j++){
            double offset = (j + 1) * 4;
            note(9, 36, 120, offset + 0,    0.5);
            if(j % 2 == 1){
                note(9, 36, 90, offset + 0.5,    0.1);
            }
            note(9, 38, 120, offset + 1,    0.5);
            note(9, 36,  60, offset + 1.75, 0.1);
            note(9, 36, 120, offset + 2,    0.5);
            if(j % 4 == 3){
                note(9, 38, 80, offset + 2.96,  0.1);
                note(9, 38, 90, offset + 2.98,  0.1);
            }
            note(9, 38, 120, offset + 3,    0.5);
            if(j % 4 == 3){
                note(9, 38, 90, offset + 3.25,  0.1);
            }
            if(j % 8 == 7){
                note(9, 38, 90, offset + 3.5,  0.1);
                //note(9, 36, 90, offset + 3.75,  0.1);
            }
            for(int i = 0;i < 4;i++){
                note(9, 42, 120, offset + i + 0, 0.5);
                note(9, (i % 4 == 0 && j % 2 == 0)?46:42,  80, offset + i + 0.25, 0.5);
                note(9, 42,  80, offset + i + 0.5, 0.5);
                note(9, (i % 4 == 3 && j % 2 == 1)?46:42,  95, offset + i + 0.75, 0.5);
            }
        }*/
        //program(1, 0, 0, 89, 0);
        int[][] chord = new int[][]{
            new int[]{64, 67, 71, 74},
            new int[]{63, 67, 70, 74},
            new int[]{65, 68, 70, 73},
            new int[]{64, 68, 71, 73},
            new int[]{66, 67, 71, 74},
            new int[]{65, 67, 69, 73},
            new int[]{64, 65, 69, 72},
            new int[]{63, 65, 67, 71},
        };
        callDevice(0);
        callTrack(2);
        for(int i = 0;i < measure;i++){
            for(int note:chord[i % chord.length]){
                note(0, note, 100, (i + 1) * 4, 4);
            }
        }
        callDevice(2);
        callTrack(3);
        for(int i = 0;i < measure;i++){
            for(int note:chord[i % chord.length]){
                note(0, note, 100, (i + 1) * 4 + 1, 3);
            }
        }
        callDevice(3);
        callTrack(4);
        for(int i = 0;i < measure;i++){
            for(int j = 0;j < 16;j++){
                int vel = (int)(Math.random() * 28) + 100;
                int pan = (int)(Math.random() * 128);
                double beat = (i + 1) * 4 + j * 0.25;
                brev(ShortMessage.CONTROL_CHANGE, 0, 0x1, pan, beat);
                for(int note:chord[i % chord.length]){
                    note(0, note + 12, vel, (i + 1) * 4 + j * 0.25, 0.2);
                }
            }
        }
        
        callDevice(1);
        callTrack(3);
        program(2, 0, 0, 39, 0);
        int[] bass = new int[]{24, 27, 30, 21, 28, 27, 26, 19};
        for(int i = 0;i < measure;i++){
            for(int j = 0;j < 4;j++){
                note(2, bass[i % bass.length] + 0 - 12, 100, (i + 1) * 4 + j,       0.1);
                note(2, bass[i % bass.length] + 0,      100, (i + 1) * 4 + j + 0.5, 0.7);
            }
        }
        /*
        callDevice(1);
        callTrack(4);
        //program(0, 0, 0, 39, 0);
        int[][] lead = new int[][]{
            new int[]{74, 72, 74}, 
            new int[]{73, 71, 73},
            new int[]{74, 78, 77},
            new int[]{76, 81, 79}};
        for(int i = 0;i < measure / 2;i++){
            for(int j = 0;j < 2;j++){
                note(0, lead[i % lead.length][0] + j * 7, 100, (i * 2 + 1) * 4, 3.25);
                note(0, lead[i % lead.length][1] + j * 7, 100, (i * 2 + 1) * 4 + 3.25, 0.75);
                note(0, lead[i % lead.length][2] + j * 7, 100, (i * 2 + 1) * 4 + 4, 3.8);
            }
        }
        /*callDevice(1);
        callTrack(2);
        program(9, 126, 0, 1, 0);
        note(9, 39, 120, 1.5, 0.5);
        note(9, 40, 120, 2.5, 0.5);*/
    }
}
