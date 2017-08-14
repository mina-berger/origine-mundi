/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package origine_mundi.opus027;

import com.mina.util.Doubles;
import com.mina.util.Integers;
import la.clamor.opus.Mensa;
import la.clamor.voix.Vowel;

/**
 *
 * @author hiyamamina
 */
public class Parte027IC extends Parte027_C {
    boolean end;
    public Parte027IC(Mensa mensa, Integers ids, int talea_primo, int length, boolean end) {
        super(mensa, ids, talea_primo, length);
        this.end = end;
    }

    @Override
    protected void creo(int t) {
        sono(0, t, vowels[0], new Doubles(67, 67), (end && t % 4 == 3));
        sono(1, t, vowels[1], new Doubles(72, 74), (end && t % 4 == 3));
    }
}
