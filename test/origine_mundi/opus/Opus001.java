/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package origine_mundi.opus;

import origine_mundi.OmPlayer;
import origine_mundi.machine.D_110;
import origine_mundi.machine.TG77;
import origine_mundi.machine.U_110;

/**
 *
 * @author Mina
 */
public class Opus001 extends OmPlayer {

    @Override
    public void setSequence() {
        callDevice(0, TG77.instance());
        callDevice(1, D_110.instance());
        callDevice(2, U_110.instance());
        
        tempo(135, 0);
        for(int k = 0;k < 4;k++){
            callDevice(0);
            callTrack(1);
            program(0, 0, 0, 0, 0);
            int s = 24;
            note(0, 50 + s, 85, 3 + k * 48, 3);
            note(0, 46 + s, 85, 6 + k * 48, 1);
            note(0, 48 + s, 85, 7 + k * 48, 1);
            note(0, 53 + s, 85, 8 + k * 48, 1);
            note(0, 50 + s, 85, 9 + k * 48, 3);
            note(0, 46 + s, 85, 12 + k * 48, 1);
            note(0, 48 + s, 85, 13 + k * 48, 1);
            note(0, 53 + s, 85, 14 + k * 48, 1);
            note(0, 55 + s, 85, 15 + k * 48, 3);
            note(0, 57 + s, 85, 18 + k * 48, 2);
            note(0, 48 + s, 85, 20 + k * 48, 1);
            note(0, 47 + s, 85, 21 + k * 48, 3);
            note(0, 48 + s, 85, 24 + k * 48, 1);
            note(0, 52 + s, 85, 25 + k * 48, 1);
            note(0, 55 + s, 85, 26 + k * 48, 1);
            note(0, 59 + s, 85, 27 + k * 48, 3);
            note(0, 55 + s, 85, 30 + k * 48, 1);
            note(0, 57 + s, 85, 31 + k * 48, 1);
            note(0, 62 + s, 85, 32 + k * 48, 1);
            note(0, 59 + s, 85, 33 + k * 48, 3);
            note(0, 55 + s, 85, 36 + k * 48, 1);
            note(0, 57 + s, 85, 37 + k * 48, 1);
            note(0, 62 + s, 85, 38 + k * 48, 1);
            note(0, 64 + s, 85, 39 + k * 48, 2);
            note(0, 60 + s, 85, 41 + k * 48, 1);
            note(0, 59 + s, 85, 42 + k * 48, 1);
            note(0, 60 + s, 85, 43 + k * 48, 1);
            note(0, 61 + s, 85, 44 + k * 48, 1);
            note(0, 62 + s, 85, 45 + k * 48, 2);
            note(0, 58 + s, 85, 47 + k * 48, 1);
            note(0, 57 + s, 85, 48 + k * 48, 1);
            note(0, 58 + s, 85, 49 + k * 48, 1);
            note(0, 60 + s, 85, 50 + k * 48, 1);

            callDevice(1);
            callTrack(2);
            program(1, 0, 0, 6, 0);
            int[][] chord = new int[][]{
                new int[]{34, 53, 58, 62, 65},
                new int[]{29, 53, 58, 60, 65},
                new int[]{34, 53, 58, 62, 65},
                new int[]{29, 53, 58, 60, 65},
                new int[]{33, 55, 60, 64, 67},
                new int[]{26, 55, 60, 64, 67},
                new int[]{31, 54, 59, 64, 66},
                new int[]{26, 55, 60, 64, 67},
                new int[]{31, 54, 59, 64, 66},
                new int[]{26, 55, 60, 64, 67},
                new int[]{31, 54, 59, 64, 66},
                new int[]{26, 55, 60, 64, 67},
                new int[]{26, 53, 57, 60, 64},
                new int[]{31, 53, 57, 59, 63},
                new int[]{24, 51, 55, 58, 62},
                new int[]{29, 51, 55, 57, 61}
            };
            for(int i = 0;i < chord.length;i++){
                for(int j = 1;j < chord[i].length;j++){
                    note(1, chord[i][j], 90, (i + 1) * 3 + 0.05 * j + k * 48, 3);
                    //note(1, chord[i][j] + 12, 90, (i + 1) * 3 + 2.05 * j + k * 48, 1);
                }
            }
            callDevice(2);
            callTrack(3);
            program(0, 0, 0, 49, 0);
            for(int i = 0;i < chord.length;i++){
                note(0, chord[i][0], 90, (i + 1) * 3 + k * 48, 3);
            }
        }
        
    }
    
}
