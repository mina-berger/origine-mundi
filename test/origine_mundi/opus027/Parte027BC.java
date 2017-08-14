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
public class Parte027BC extends Parte027_C {

    public Parte027BC(Mensa mensa, Integers ids, int talea_primo, int length) {
        super(mensa, ids, talea_primo, length);
    }

    @Override
    protected void creo(int t) {
        switch (t % 9) {
            case 0:
                sono(0, t, vowels[0], new Doubles(66, 65));
                sono(1, t, vowels[1], new Doubles(71, 71));
                break;
            case 1:
                sono(0, t, vowels[0], new Doubles(65, 63));
                sono(1, t, vowels[1], new Doubles(70, 70));
                break;
            case 2:
                sono(0, t, vowels[0], new Doubles(64, 63));
                sono(1, t, vowels[1], new Doubles(69, 69));
                break;
            case 3:
                sono(0, t, vowels[0], new Doubles(63, 61));
                sono(1, t, vowels[1], new Doubles(68, 68));
                break;
            case 4:
                sono(0, t, vowels[0], new Doubles(63, 62));
                sono(1, t, vowels[1], new Doubles(68, 68));
                break;
            case 5:
                sono(0, t, vowels[0], new Doubles(62, 65));
                sono(1, t, vowels[1], new Doubles(67, 68));
                break;
            case 6:
                sono(0, t, vowels[0], new Doubles(64, 64));
                sono(1, t, vowels[1], new Doubles(71, 70));
                break;
            case 7:
                sono(0, t, 0.5, 0.5, Vowel.U.multiplico(vowels[0]), 63);
                sono(0, t, 1.25, 0.5, Vowel.U.multiplico(vowels[0]), 63);
                sono(0, t, 1.75, 2, Vowel.A.multiplico(vowels[0]), 62);
                sono(1, t, 0.5, 0.5, Vowel.U.multiplico(vowels[1]), 68);
                sono(1, t, 1.25, 0.5, Vowel.U.multiplico(vowels[1]), 68);
                sono(1, t, 1.75, 2, Vowel.A.multiplico(vowels[1]), 68);
                break;
            case 8:
                sono(0, t, 0.5, 0.5, Vowel.U.multiplico(vowels[0]), 63);
                sono(0, t, 1.25, 0.5, Vowel.U.multiplico(vowels[0]), 63);
                sono(0, t, 1.75, 0.5, Vowel.A.multiplico(vowels[0]), 62);
                sono(1, t, 0.5, 0.5, Vowel.U.multiplico(vowels[1]), 68);
                sono(1, t, 1.25, 0.5, Vowel.U.multiplico(vowels[1]), 68);
                sono(1, t, 1.75, 0.5, Vowel.A.multiplico(vowels[1]), 68);
                break;
        }
    }

}
