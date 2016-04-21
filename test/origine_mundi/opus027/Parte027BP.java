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
import la.clamor.opus.Notes;
import la.clamor.opus.ParteTaleae;

/**
 *
 * @author hiyamamina
 */
public class Parte027BP extends ParteTaleae {

    public Parte027BP(Mensa mensa, Integers ids, int talea_primo, int length) {
        super(mensa, ids, talea_primo, length);
    }

    @Override
    protected void creo(int t) {
        double[] intervals = new double[]{0, 12};
        switch (t % 9) {
            case 0:
                ludoClaves(t,
                        (int i) -> new Doubles(1, 1.33, 1.66, 2).get(i),
                        (int i) -> new Doubles(0.3, 0.3, 0.3, 2).get(i),
                        (int i) -> new Notes(new Doubles(66, 70, 68, 65).get(i), intervals),
                        (int i) -> new Vel(1), 4);
                break;
            case 1:
                ludoClaves(t,
                        (int i) -> new Doubles(0.33, 0.66, 1, 1.33, 1.66, 2).get(i),
                        (int i) -> new Doubles(0.3, 0.3, 0.3, 0.3, 0.3, 2).get(i),
                        (int i) -> new Notes(new Doubles(65, 66, 68, 66, 65, 70).get(i), intervals),
                        (int i) -> new Vel(1), 6);
                break;
            case 2:
                ludoClaves(t,
                        (int i) -> new Doubles(1, 1.33, 1.66, 2).get(i),
                        (int i) -> new Doubles(0.3, 0.3, 0.3, 2).get(i),
                        (int i) -> new Notes(new Doubles(64, 68, 66, 63).get(i), intervals),
                        (int i) -> new Vel(1), 4);
                break;
            case 3:
                ludoClaves(t,
                        (int i) -> new Doubles(0.33, 0.66, 1, 1.33, 1.66, 2).get(i),
                        (int i) -> new Doubles(0.3, 0.3, 0.3, 0.3, 0.3, 2).get(i),
                        (int i) -> new Notes(new Doubles(63, 64, 66, 64, 63, 68).get(i), intervals),
                        (int i) -> new Vel(1), 6);
                break;
            case 4:
                ludoClaves(t,
                        (int i) -> new Doubles(0, 0.66, 1.33, 2.75, 3.5).get(i),
                        (int i) -> new Doubles(0.6, 0.6, 1.3, 0.6, 0.45).get(i),
                        (int i) -> new Notes(new Doubles(68, 67, 65, 67, 65).get(i), intervals),
                        (int i) -> new Vel(1), 5);
                break;
            case 5:
                ludoClaves(t,
                        (int i) -> new Doubles(0, 0.66, 1.33, 2.75, 3.5).get(i),
                        (int i) -> new Doubles(0.6, 0.6, 1.3, 0.6, 0.45).get(i),
                        (int i) -> new Notes(new Doubles(67, 65, 63, 65, 68).get(i), intervals),
                        (int i) -> new Vel(1), 5);
                break;
            case 6:
                ludoClaves(t,
                        (int i) -> new Doubles(0, 0.66, 1.33, 2.75, 3.5).get(i),
                        (int i) -> new Doubles(0.6, 0.6, 1.3, 0.6, 0.45).get(i),
                        (int i) -> new Notes(new Doubles(71, 70, 68, 70, 68).get(i), intervals),
                        (int i) -> new Vel(1), 5);
                break;
            case 7:
                ludoClaves(t,
                        (int i) -> new Doubles(0.33, 0.66, 1, 1.33, 1.66, 2, 2.33, 2.66, 3, 3.33, 3.66).get(i),
                        (int i) -> new Doubles(0.3, 0.3, 0.3, 0.3, 0.3, 0.3, 0.3, 0.3, 0.3, 0.3, 0.3).get(i),
                        (int i) -> new Notes(new Doubles(62, 65, 63, 67, 65, 68, 67, 70, 68, 72, 70).get(i), intervals),
                        (int i) -> new Vel(1), 11);
                break;
            case 8:
                ludoClaves(t,
                        (int i) -> new Doubles(0, 0.33, 0.66, 1, 1.33, 1.66, 2, 2.5).get(i),
                        (int i) -> new Doubles(0.3, 0.3, 0.3, 0.3, 0.3, 0.3, 0.2, 1.2).get(i),
                        (int i) -> new Notes(new Doubles(74, 72, 75, 74, 77, 75, 79, 80).get(i), intervals),
                        (int i) -> new Vel(1), 8);
                break;
        }
    }

}
