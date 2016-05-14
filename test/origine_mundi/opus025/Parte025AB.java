/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package origine_mundi.opus025;

import com.mina.util.Doubles;
import com.mina.util.Integers;
import la.clamor.opus.Mensa;

/**
 *
 * @author hiyamamina
 */
public class Parte025AB extends Parte025_B{

    public Parte025AB(Mensa mensa, Integers ids, int talea_primo, int length) {
        super(mensa, ids, talea_primo, length);
    }

    @Override
    protected void creo(int t) {
        Doubles bass = null;
        switch(t % 16){
            case 0:
            case 4:
            case 12:
            case 13:
                bass = new Doubles(38, 33);
                break;
            case 1:
            case 5:
                bass = new Doubles(38, 33, 32);
                break;
            case 2:
            case 6:
            case 10:
            case 11:
                bass = new Doubles(31, 38);
                break;
            case 3:
                bass = new Doubles(31, 33, 34, 36);
                break;
            case 7:
                bass = new Doubles(31, 33, 34, 38);
                break;
            case 8:
            case 9:
                bass = new Doubles(36, 31);
                break;
            case 14:
                bass = new Doubles(31, 36, 38, 30);
                break;
            case 15:
                bass = new Doubles(31);
                break;
        }
        creo(t, bass);
    }
    
}
