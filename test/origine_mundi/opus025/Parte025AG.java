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

/**
 *
 * @author hiyamamina
 */
public class Parte025AG extends Parte025_G{

    public Parte025AG(Mensa mensa, Integers ids, int talea_primo, int length) {
        super(mensa, ids, talea_primo, length);
    }

    @Override
    protected void creo(int t) {
        Doubles chord0 = null, chord1 = null;
        Doubles bass = null;
        switch(t % 16){
            case 0:
                pickup(t - 1, new Doubles(45, 46, 48));
            case 1:
            case 4:
            case 5:
            case 12:
            case 13:
                chord0 = chord1 = new Doubles(50, 57, 60, 66);
                bass = new Doubles(50, 45);
                break;
            case 2:
            case 3:
            case 6:
            case 7:
            case 10:
            case 11:
            case 14:
                chord0 = chord1 = new Doubles(55, 58, 62, 67);
                bass = new Doubles(43, 50);
                break;
            case 8:
            case 9:
                chord0 = chord1 = new Doubles(55, 60, 63, 67);
                bass = new Doubles(48, 43);
                break;
            case 15:
                chord0 = new Doubles(55, 58, 62, 67);
                bass = new Doubles(43);
                break;
        }
        creo(t, chord0, chord1, bass);
    }
    
}
