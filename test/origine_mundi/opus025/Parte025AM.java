/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package origine_mundi.opus025;

import com.mina.util.Integers;
import la.clamor.Vel;
import la.clamor.opus.Mensa;
import la.clamor.opus.ParteTaleae;

/**
 *
 * @author hiyamamina
 */
public class Parte025AM extends ParteTaleae {

    public Parte025AM(Mensa mensa, Integers ids, int talea_primo, int length) {
        super(mensa, ids, talea_primo, length);
    }

    @Override
    protected void creo(int t) {
        switch(t % 16){
            case 0:
            case 4:
                ludo(t, 0, 1.8, 62, new Vel(0.8));
                ludo(t, 2., 0.8, 66, new Vel(0.8));
                ludo(t, 3., 0.8, 67, new Vel(0.8));
                break;
            case 1:
            case 5:
                ludo(t, 0, 1.8, 69, new Vel(0.8));
                ludo(t, 2., 0.8, 66, new Vel(0.8));
                ludo(t, 3., 0.8, 62, new Vel(0.8));
                break;
            case 2:
                ludo(t, 0, 1.45, 67, new Vel(0.8));
                ludo(t, 1.5, 0.45, 62, new Vel(0.8));
                ludo(t, 2., 0.45, 67, new Vel(0.8));
                ludo(t, 2.5, 0.45, 69, new Vel(0.8));
                ludo(t, 3.5, 1.5, 70, new Vel(0.8));
                break;
            case 3:
                ludo(t, 1.5, 0.2, 69, new Vel(0.8));
                ludo(t, 2., 0.8, 70, new Vel(0.8));
                ludo(t, 3., 0.8, 72, new Vel(0.8));
                break;
            case 6:
                ludo(t, 0, 6., 67, new Vel(0.8));
                break;
            case 7:
                ludo(t, 3, 0.8, 74, new Vel(0.8));
                break;
            case 8:
                ludo(t, 0, 1.8, 72, new Vel(0.8));
                ludo(t, 2., 1.4, 75, new Vel(0.8));
                ludo(t, 3.5, 3.5, 69, new Vel(0.8));
                break;
            case 9:
                ludo(t, 3, 0.8, 72, new Vel(0.8));
                break;
            case 10:
                ludo(t, 0, 1.8, 70, new Vel(0.8));
                ludo(t, 2., 1.4, 74, new Vel(0.8));
                ludo(t, 3.5, 3.5, 67, new Vel(0.8));
                break;
            case 12:
                ludo(t, 0, 1.8, 62, new Vel(0.8));
                ludo(t, 2., 0.4, 66, new Vel(0.8));
                ludo(t, 2.5, 0.4, 67, new Vel(0.8));
                ludo(t, 3.5, 2.2, 69, new Vel(0.8));
                break;
            case 13:
                ludo(t, 2., 0.8, 66, new Vel(0.8));
                ludo(t, 3., 0.8, 62, new Vel(0.8));
                break;
            case 14:
                ludo(t, 0., 4.2, 67, new Vel(0.8));
                break;
            
        }

    }
}
