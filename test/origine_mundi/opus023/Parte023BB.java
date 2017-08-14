/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package origine_mundi.opus023;

import com.mina.util.Doubles;
import com.mina.util.Integers;
import la.clamor.Vel;
import la.clamor.opus.Mensa;
import la.clamor.opus.ParteTaleae;

/**
 *
 * @author hiyamamina
 */
public class Parte023BB extends ParteTaleae {

    public Parte023BB(Mensa mensa, Integers ids, int talea_primo, int length) {
        super(mensa, ids, talea_primo, length);
    }

    @Override
    protected void creo(int t) {
        cambio(0);
        switch (t % 10) {
            case 0:
                ludo(t, 0.0, 0.5, 34, new Vel(0.8));
                ludo(t, 0.5, 0.2, 46, new Vel(1));
                ludo(t, 0.75, 0.7, 33, new Vel(0.8));
                ludo(t, 1.5, 0.7, 31, new Vel(0.8));
                ludo(t, 2.25, 1., 36, new Vel(0.8));
                ludo(t, 3.25, 0.2, 48, new Vel(1));
                ludo(t, 3.5, 0.5, 31, new Vel(0.8));
                cambio(1);

                ludo(t, 0.0, 0.7, new Doubles(65, 69, 74), new Vel(0.8));
                ludo(t, 0.75, 0.7, new Doubles(65, 69, 72), new Vel(0.8));
                ludo(t, 1.5, 0.7, new Doubles(65, 70, 74), new Vel(0.8));
                ludo(t, 2.25, 1., new Doubles(65, 70, 74), new Vel(0.8));
                ludo(t, 3.25, 0.7, new Doubles(64, 70, 74), new Vel(1));
                break;
            case 1:
                ludo(t, 0.0, 0.5, 36, new Vel(0.8));
                ludo(t, 0.5, 0.2, 48, new Vel(1));
                ludo(t, 0.75, 0.7, 37, new Vel(0.8));
                ludo(t, 1.5, 0.7, 38, new Vel(0.8));
                ludo(t, 2.25, 1.0, 45, new Vel(0.8));
                ludo(t, 3.25, 0.2, 50, new Vel(1));
                ludo(t, 3.5, 0.5, 42, new Vel(0.8));

                cambio(1);
                ludo(t, 0.0, 0.7, new Doubles(67, 70, 76), new Vel(0.8));
                ludo(t, 0.75, 0.7, new Doubles(67, 70, 76), new Vel(0.8));
                ludo(t, 1.5, 0.7, new Doubles(65, 69, 72), new Vel(0.8));
                ludo(t, 2.25, 1., new Doubles(65, 69, 71), new Vel(0.8));
                ludo(t, 3.25, 0.7, new Doubles(66, 69, 70), new Vel(1));
                break;
            case 2:
                ludo(t, 0.0, 0.5, 31, new Vel(0.8));
                ludo(t, 0.5, 0.2, 43, new Vel(1));
                ludo(t, 0.75, 0.7, 33, new Vel(0.8));
                ludo(t, 1.5, 0.7, 34, new Vel(0.8));
                ludo(t, 2.25, 1.0, 41, new Vel(0.8));
                ludo(t, 3.25, 0.2, 46, new Vel(1));
                ludo(t, 3.5, 0.5, 35, new Vel(0.8));

                cambio(1);
                ludo(t, 0.0, 0.7, new Doubles(65, 70, 74), new Vel(0.8));
                ludo(t, 0.75, 0.7, new Doubles(65, 69, 73), new Vel(0.8));
                ludo(t, 1.5, 0.7, new Doubles(65, 69, 74), new Vel(0.8));
                ludo(t, 2.25, 1., new Doubles(65, 69, 74), new Vel(0.8));
                ludo(t, 3.25, 0.7, new Doubles(66, 68, 74), new Vel(1));

                break;
            case 3:
                ludo(t, 0.0, 0.5, 36, new Vel(0.8));
                ludo(t, 0.5, 0.2, 48, new Vel(1));
                ludo(t, 0.75, 0.5, 36, new Vel(0.8));
                ludo(t, 1.25, 0.2, 48, new Vel(1));
                ludo(t, 1.5, 1.2, 36, new Vel(0.8));
                ludo(t, 3.0, 0.9, 35, new Vel(0.8));

                cambio(1);
                ludo(t, 0.0, 0.7, new Doubles(65, 70, 74), new Vel(0.8));
                ludo(t, 0.75, 0.7, new Doubles(65, 70, 74), new Vel(0.8));
                ludo(t, 1.5, 1.2, new Doubles(63, 70, 74), new Vel(0.8));
                ludo(t, 3., 0.9, new Doubles(63, 69, 73), new Vel(1));

                break;
            case 4:
                ludo(t, 0.0, 0.5, 34, new Vel(0.8));
                ludo(t, 0.5, 0.2, 46, new Vel(1));
                ludo(t, 0.75, 0.7, 33, new Vel(0.8));
                ludo(t, 1.5, 0.7, 31, new Vel(0.8));
                ludo(t, 2.25, 1., 36, new Vel(0.8));
                ludo(t, 3.25, 0.2, 48, new Vel(1));
                ludo(t, 3.5, 0.5, 31, new Vel(0.8));

                cambio(1);
                ludo(t, 0.0, 0.7, new Doubles(65, 69, 74), new Vel(0.8));
                ludo(t, 0.75, 0.7, new Doubles(65, 69, 72), new Vel(0.8));
                ludo(t, 1.5, 0.7, new Doubles(65, 70, 74), new Vel(0.8));
                ludo(t, 2.25, 1., new Doubles(65, 70, 74), new Vel(0.8));
                ludo(t, 3.25, 0.7, new Doubles(64, 70, 74), new Vel(1));
                break;
            case 5:
                ludo(t, 0.0, 0.5, 36, new Vel(0.8));
                ludo(t, 0.5, 0.2, 48, new Vel(1));
                ludo(t, 0.75, 0.7, 37, new Vel(0.8));
                ludo(t, 1.5, 0.7, 38, new Vel(0.8));
                ludo(t, 2.25, 1.0, 32, new Vel(0.8));
                ludo(t, 3.25, 0.2, 44, new Vel(1));
                ludo(t, 3.5, 0.5, 30, new Vel(0.8));

                cambio(1);
                ludo(t, 0.0, 0.7, new Doubles(67, 70, 76), new Vel(0.8));
                ludo(t, 0.75, 0.7, new Doubles(67, 70, 76), new Vel(0.8));
                ludo(t, 1.5, 0.7, new Doubles(67, 72, 76), new Vel(0.8));
                ludo(t, 2.25, 1., new Doubles(66, 72, 77), new Vel(0.8));
                ludo(t, 3.25, 0.7, new Doubles(66, 72, 75), new Vel(1));
                break;
            case 6:
            case 7:
            case 8:
                double[] bass = new double[][]{
                    new double[]{31, 33}, new double[]{34, 35}, new double[]{36, 36}
                }[(t % 10) - 6];
                Doubles[] chords = new Doubles[][]{
                    new Doubles[]{new Doubles(65, 70, 74), new Doubles(67, 72, 76)},
                    new Doubles[]{new Doubles(65, 69, 74), new Doubles(65, 68, 74)},
                    new Doubles[]{new Doubles(65, 70, 74), new Doubles(70, 74, 77)}
                }[(t % 10) - 6];
                ludo(t, 0.0, 0.3, bass[0], new Vel(0.8));
                ludo(t, 0.5, 0.48, bass[0], new Vel(1));
                ludo(t, 1.0, 0.3, bass[0], new Vel(0.8));
                ludo(t, 1.5, 0.48, bass[0], new Vel(1));
                ludo(t, 2.0, 0.3, bass[1], new Vel(0.8));
                ludo(t, 2.5, 0.48, bass[1], new Vel(1));
                ludo(t, 3.0, 0.3, bass[1], new Vel(0.8));
                ludo(t, 3.5, 0.48, bass[1], new Vel(1));
                cambio(1);
                ludo(t, 0.0, 1.9, chords[0], new Vel(0.8));
                ludo(t, 2.0, 1.9, chords[1], new Vel(0.8));
                break;
            case 9:
                ludo(t, 0.0, 0.2, 36, new Vel(0.8));
                ludo(t, 0.25, 0.45, 46, new Vel(0.8));
                ludo(t, 0.75, 0.45, 45, new Vel(0.8));
                ludo(t, 1.25, 0.45, 44, new Vel(0.8));
                ludo(t, 1.75, 0.2, 43, new Vel(0.8));
                ludo(t, 2.5, 1.35, 36, new Vel(0.8));
                cambio(1);
                ludo(t, 0.0, 1.85, new Doubles(70, 76, 81), new Vel(0.8));
                ludo(t, 2.5, 1.35, new Doubles(70, 76, 80), new Vel(0.8));
        }

    }
}
