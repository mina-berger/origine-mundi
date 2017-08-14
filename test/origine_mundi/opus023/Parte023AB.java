/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package origine_mundi.opus023;

import com.mina.util.Integers;
import la.clamor.Vel;
import la.clamor.opus.Mensa;
import la.clamor.opus.ParteTaleae;

/**
 *
 * @author hiyamamina
 */
public class Parte023AB extends ParteTaleae {

    double[][] notess = new double[][]{
        new double[]{41, 65, 69, 72, 40, 67, 70, 72},
        new double[]{38, 65, 69, 72, 36, 65, 69, 75},
        new double[]{34, 65, 69, 74, 33, 67, 72, 74},
        new double[]{31, 65, 70, 74, 36, 64, 70, 73}};
    double[][] notess_end = new double[][]{
        new double[]{41, 65, 69, 72, 39, 65, 69, 72},
        new double[]{38, 65, 70, 75, 37, 65, 70, 77},
        new double[]{36, 65, 69, 72, 35, 65, 67, 74},
        new double[]{31, 65, 70, 74, 36, 64, 70, 73}};
    private boolean end;
    public Parte023AB(Mensa mensa, Integers ids, int talea_primo, int length, boolean end) {
        super(mensa, ids, talea_primo, length);
        this.end = end;
    }

    @Override
    protected void creo(int talea) {
        double[] notes;
        cambio(0);
        if(end && talea % 8 >= 4){
            notes = notess_end[talea % notess.length];
        }else{
            notes = notess[talea % notess.length];
        }
        switch (talea % 8) {
            case 3:
                ludo(talea, 0.0, 0.3, 31, new Vel(0.8));
                ludo(talea, 0.5, 0.48, 33, new Vel(1));
                ludo(talea, 1.0, 0.3, 34, new Vel(0.8));
                ludo(talea, 1.5, 0.98, 36, new Vel(1));
                ludo(talea, 2.5, 0.48, 36, new Vel(1));
                ludo(talea, 3.25, 0.7, 36, new Vel(1));
                break;
            case 7:
                ludo(talea, 0.0, 0.3, 34, new Vel(0.8));
                ludo(talea, 0.5, 0.48, 35, new Vel(1));
                ludo(talea, 1.0, 0.3, 36, new Vel(0.8));
                ludo(talea, 1.5, 0.98, 41, new Vel(1));
                ludo(talea, 2.5, 0.48, 29, new Vel(1));
                ludo(talea, 3.25, 0.7, 29, new Vel(1));
                break;
            default:
                ludo(talea, 0.0, 0.3, notes[0], new Vel(0.8));
                ludo(talea, 0.5, 0.48, notes[0], new Vel(1));
                ludo(talea, 1.0, 0.3, notes[0], new Vel(0.8));
                ludo(talea, 1.5, 0.48, notes[0], new Vel(1));
                ludo(talea, 2.0, 0.3, notes[4], new Vel(0.8));
                ludo(talea, 2.5, 0.48, notes[4], new Vel(1));
                ludo(talea, 3.0, 0.3, notes[4], new Vel(0.8));
                ludo(talea, 3.5, 0.48, notes[4], new Vel(1));
                break;
        }
        cambio(1);

        if (talea % 8 == 7) {
            ludo(talea, 0.0, 0.5, 67, new Vel(1));
            ludo(talea, 0.5, 0.5, 74, new Vel(1));
            ludo(talea, 1.0, 0.5, 76, new Vel(1));
            ludo(talea, 1.5, 0.5, 70, new Vel(1));

            ludo(talea, 2.0, 0.5, 69, new Vel(1));
            ludo(talea, 2.5, 0.5, 72, new Vel(1));
            ludo(talea, 3.0, 0.5, 77, new Vel(1));
            ludo(talea, 3.5, 0.5, 72, new Vel(1));
        } else {
            ludo(talea, 0.0, 0.5, notes[1], new Vel(1));
            ludo(talea, 0.5, 0.5, notes[2], new Vel(1));
            ludo(talea, 1.0, 0.5, notes[3], new Vel(1));
            ludo(talea, 1.5, 0.5, notes[2], new Vel(1));

            ludo(talea, 2.0, 0.5, notes[5], new Vel(1));
            ludo(talea, 2.5, 0.5, notes[6], new Vel(1));
            ludo(talea, 3.0, 0.5, notes[7], new Vel(1));
            ludo(talea, 3.5, 0.5, notes[6], new Vel(1));
        }
    }

}
