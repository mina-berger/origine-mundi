/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package origine_mundi.opus025;

import com.mina.util.Doubles;
import com.mina.util.Integers;
import la.clamor.Vel;
import la.clamor.opus.Mensa;
import la.clamor.opus.ParteTaleae;

/**
 *
 * @author hiyamamina
 */
public abstract class Parte025_B extends ParteTaleae {

    public Parte025_B(Mensa mensa, Integers ids, int talea_primo, int length) {
        super(mensa, ids, talea_primo, length);
    }

    protected void creo(int t, Doubles bass) {
        switch (bass.size()) {
            case 1:
                ludo(t,
                        (int i) -> 0.,
                        (int i) -> 1.8,
                        (int i) -> bass.get(i),
                        (int i) -> new Vel(0.8), 1);
                break;
            case 2:
                ludo(t,
                        (int i) -> new Doubles(0, 2).get(i),
                        (int i) -> 1.8,
                        (int i) -> bass.get(i),
                        (int i) -> new Vel(0.8), 2);
                break;
            case 3:
                ludo(t,
                        (int i) -> new Doubles(0, 2, 3).get(i),
                        (int i) -> new Doubles(1.8, 0.8, 0.8).get(i),
                        (int i) -> bass.get(i),
                        (int i) -> new Vel(0.8), 3);
                break;
            case 4:
                ludo(t,
                        (int i) -> (double)i,
                        (int i) -> 0.8,
                        (int i) -> bass.get(i),
                        (int i) -> new Vel(0.8), 4);
                break;
        }
    }


}
