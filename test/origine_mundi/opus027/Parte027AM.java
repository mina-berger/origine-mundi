/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package origine_mundi.opus027;

import com.mina.util.Doubles;
import com.mina.util.Integers;
import la.clamor.Vel;
import la.clamor.opus.Mensa;
import la.clamor.opus.ParteTaleae;

/**
 *
 * @author hiyamamina
 */
public class Parte027AM extends ParteTaleae {

    boolean end;

    public Parte027AM(Mensa mensa, Integers ids, int talea_primo, int length, boolean end) {
        super(mensa, ids, talea_primo, length);
        this.end = end;
    }

    @Override
    protected void creo(int t) {
        switch (end ? t % 9 + 8 : t % 16) {
            case 0:
            case 8:
                ludo(t,
                        (int i) -> new Doubles(0, 0.5, 0.75, 1.25, 1.75, 2.25, 3., 3.5).get(i),
                        (int i) -> new Doubles(0.2, 0.2, 0.2, 0.2, 0.2, 0.65, 0.2, 0.2).get(i),
                        (int i) -> new Doubles(60, 63, 63, 65, 63, 62, 60, 58).get(i),
                        (int i) -> new Vel(1), 8);
                break;
            case 1:
            case 9:
                ludo(t,
                        (int i) -> new Doubles(0, 1.75).get(i),
                        (int i) -> new Doubles(1.6, 2).get(i),
                        (int i) -> new Doubles(62, 61).get(i),
                        (int i) -> new Vel(1), 2);
                break;
            case 2:
            case 10:
                ludo(t,
                        (int i) -> new Doubles(0, 0.5, 0.75, 1.25, 1.75, 2.25, 2.75, 3).get(i),
                        (int i) -> new Doubles(0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2).get(i),
                        (int i) -> new Doubles(60, 65, 65, 67, 65, 63, 62, 63).get(i),
                        (int i) -> new Vel(1), 8);
                break;
            case 3:
                ludo(t,
                        (int i) -> new Doubles(-0.5, 1.75).get(i),
                        (int i) -> new Doubles(2.1, 2).get(i),
                        (int i) -> new Doubles(65, 64).get(i),
                        (int i) -> new Vel(1), 2);
                break;
            case 11:
                ludo(t,
                        (int i) -> new Doubles(-0.5, 1.25, 1.75).get(i),
                        (int i) -> new Doubles(1.6, 0.2, 2).get(i),
                        (int i) -> new Doubles(65, 67, 64).get(i),
                        (int i) -> new Vel(1), 3);
                break;
            case 4:
            case 12:
                ludo(t,
                        (int i) -> new Doubles(0, 0.5, 0.75, 1.25, 1.75, 2.25, 3., 3.5).get(i),
                        (int i) -> new Doubles(0.2, 0.2, 0.2, 0.2, 0.2, 0.65, 0.3, 0.3).get(i),
                        (int i) -> new Doubles(63, 70, 70, 72, 70, 68, 67, 68).get(i),
                        (int i) -> new Vel(1), 8);
                break;
            case 5:
            case 13:
                ludo(t,
                        (int i) -> new Doubles(0, 0.5, 0.75, 1.25, 1.75, 2.75).get(i),
                        (int i) -> new Doubles(0.2, 0.2, 0.2, 0.2, 0.9, 1.1).get(i),
                        (int i) -> new Doubles(70, 68, 68, 67, 70, 68).get(i),
                        (int i) -> new Vel(1), 6);
                break;
            case 6:
                ludo(t,
                        (int i) -> new Doubles(0, 0.5, 0.75, 1.25, 1.75, 2.75, 3., 3.5, 3.75).get(i),
                        (int i) -> new Doubles(0.2, 0.2, 0.2, 0.2, 0.9, 0.2, 0.4, 0.2, 3.5).get(i),
                        (int i) -> new Doubles(67, 65, 67, 65, 67, 65, 67, 68, 65).get(i),
                        (int i) -> new Vel(1), 9);
                break;
            case 14:
                if (end) {
                    ludo(t,
                            (int i) -> new Doubles(0, 0.5, 0.75, 1.25, 1.75, 2.25, 3., 3.5).get(i),
                            (int i) -> new Doubles(0.2, 0.2, 0.2, 0.2, 0.2, 0.7, 0.2, 0.2).get(i),
                            (int i) -> new Doubles(67, 60, 63, 65, 67, 60, 67, 68).get(i),
                            (int i) -> new Vel(1), 8);
                } else {
                    ludo(t,
                            (int i) -> new Doubles(0, 0.5, 0.75, 1.25, 1.75, 2.75, 3., 3.75).get(i),
                            (int i) -> new Doubles(0.2, 0.2, 0.2, 0.2, 0.9, 0.2, 0.7, 3.5).get(i),
                            (int i) -> new Doubles(67, 60, 63, 65, 67, 60, 67, 63).get(i),
                            (int i) -> new Vel(1), 8);
                }
                break;
            case 15:
                if (end) {
                    ludo(t,
                            (int i) -> new Doubles(0, 0.5, 0.75, 1.25, 1.75, 2.25, 3., 3.5).get(i),
                            (int i) -> new Doubles(0.2, 0.2, 0.2, 0.2, 0.2, 0.7, 0.2, 0.2).get(i),
                            (int i) -> new Doubles(70, 63, 66, 68, 70, 63, 70, 68).get(i),
                            (int i) -> new Vel(1), 8);
                }
                break;
            case 16:
                ludo(t,
                        (int i) -> new Doubles(0, 0.5, 0.75, 1.25, 1.75, 2.75, 3., 3.75).get(i),
                        (int i) -> new Doubles(0.2, 0.2, 0.2, 0.2, 0.9, 0.2, 0.7, 2.5).get(i),
                        (int i) -> new Doubles(67, 60, 63, 65, 67, 60, 67, 63).get(i),
                        (int i) -> new Vel(1), 8);
                break;

        }
    }

}
