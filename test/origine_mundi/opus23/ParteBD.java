/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package origine_mundi.opus23;

import com.mina.util.Integers;
import la.clamor.Vel;
import la.clamor.ludum.Mutans;
import la.clamor.opus.Mensa;
import la.clamor.opus.ParteTaleae;

/**
 *
 * @author hiyamamina
 */
public class ParteBD extends ParteTaleae {

    public ParteBD(Mensa mensa, Integers ids, int talea_primo, int length) {
        super(mensa, ids, talea_primo, length);
    }

    @Override
    protected void creo(int talea) {
        int i = talea;
        double rv = 0.8;
        int ride = 59;
        switch (talea % 10) {
            case 0:
            case 1:
            case 2:
            case 4:
            case 5:
                cambio(0);
                ludo(talea, 0.0, 0.5, 36, new Vel(1));
                ludo(talea, 0.75, 0.5, 36, new Vel(1));
                ludo(talea, 1.5, 0.5, 36, new Vel(1));
                ludo(talea, 2.25, 0.5, 36, new Vel(1));
                ludo(talea, 3.5, 0.5, 36, new Vel(0.5));
                cambio(2);
                ludo(talea, 3.0, 1, 40, new Vel(1));
                cambio(1);
                ludo(talea, 0, 0.5, ride, new Vel(rv * 1));
                ludo(talea, 0.5, 0.5, ride, new Vel(rv * 0.5));
                ludo(talea, 0.75, 0.5, ride, new Vel(rv * 1));
                ludo(talea, 1.25, 0.5, ride, new Vel(rv * 0.5));
                ludo(talea, 1.5, 0.5, ride, new Vel(rv * 1));
                ludo(talea, 2.0, 0.5, ride, new Vel(rv * 0.5));
                ludo(talea, 2.25, 0.5, ride, new Vel(rv * 1));
                ludo(talea, 2.75, 0.5, ride, new Vel(rv * 0.5));
                ludo(talea, 3., 0.5, ride, new Vel(rv * 1));
                ludo(talea, 3.5, 0.5, ride, new Vel(rv * 1));
                break;
            case 3:
                cambio(0);
                ludo(talea, 0.0, 0.5, 36, new Vel(1));
                ludo(talea, 0.75, 0.5, 36, new Vel(1));
                ludo(talea, 1.5, 0.5, 36, new Vel(1));
                ludo(talea, 3.0, 0.5, 36, new Vel(0.5));
                cambio(1);
                ludo(talea, 0, 0.5, ride, new Vel(rv * 1));
                ludo(talea, 0.5, 0.5, ride, new Vel(rv * 0.5));
                ludo(talea, 0.75, 0.5, ride, new Vel(rv * 1));
                ludo(talea, 1.25, 0.5, ride, new Vel(rv * 0.5));
                ludo(talea, 1.5, 1.2, ride, new Vel(rv * 1));
                ludo(talea, 2.75, 0.12, ride, new Vel(rv * 0.3));
                ludo(talea, 2.875, 0.12, ride, new Vel(rv * 0.3));
                ludo(talea, 3., 0.9, ride, new Vel(rv * 1));
                break;
            case 6:
            case 7:
                cambio(0);
                ludo(talea, 0.0, 0.5, 36, new Vel(1));
                ludo(talea, 1.5, 0.5, 36, new Vel(0.5));
                ludo(talea, 2.5, 0.5, 36, new Vel(1));
                ludo(talea, 3.25, 0.5, 36, new Vel(0.5));
                cambio(2);
                ludo(talea, 1.0, 1, 40, new Vel(1));
                ludo(talea, 3.0, 1, 40, new Vel(1));
                cambio(1);
                ludo(talea, 0, 0.5, ride, new Vel(rv * 1));
                ludo(talea, 0.5, 0.5, ride, new Vel(rv * 0.5));
                ludo(talea, 0.75, 0.5, ride, new Vel(rv * 1));
                ludo(talea, 1.25, 0.5, ride, new Vel(rv * 0.5));
                ludo(talea, 1.5, 0.5, ride, new Vel(rv * 1));
                ludo(talea, 2.0, 0.5, ride, new Vel(rv * 0.5));
                ludo(talea, 2.25, 0.5, ride, new Vel(rv * 1));
                ludo(talea, 2.75, 0.5, ride, new Vel(rv * 0.5));
                ludo(talea, 3., 0.5, ride, new Vel(rv * 1));
                ludo(talea, 3.5, 0.5, ride, new Vel(rv * 1));
                break;
            case 8:
                cambio(0);
                ludo(talea, (int index) -> index * 0.5, (int index) -> 0.5, (int index) -> 36d, 
                        (int index) -> new Vel(0.5 + 0.5 * (double)index / 8. ), 8);
                cambio(2);
                ludo(talea, 1.0, 1, 40, new Vel(1));
                ludo(talea, 3.0, 1, 40, new Vel(1));
                cambio(1);
                ludo(talea, (int index) -> index * 0.5, (int index) -> 0.5, (int index) -> (double)ride, 
                        (int index) -> new Vel(0.5 + 0.5 * (double)index / 8. ), 8);
                /*ludo(talea, 0, 0.5, ride, new Vel(rv * 1));
                ludo(talea, 0.5, 0.5, ride, new Vel(rv * 0.5));
                ludo(talea, 0.75, 0.5, ride, new Vel(rv * 1));
                ludo(talea, 1.25, 0.5, ride, new Vel(rv * 0.5));
                ludo(talea, 1.5, 0.5, ride, new Vel(rv * 1));
                ludo(talea, 2.0, 0.5, ride, new Vel(rv * 0.5));
                ludo(talea, 2.25, 0.5, ride, new Vel(rv * 1));
                ludo(talea, 2.75, 0.5, ride, new Vel(rv * 0.5));
                ludo(talea, 3., 0.5, ride, new Vel(rv * 1));
                ludo(talea, 3.5, 0.5, ride, new Vel(rv * 1));*/
                break;
            case 9:
                cambio(0);
                ludo(talea, 0.0, 0.5, 36, new Vel(1));
                ludo(talea, 2.5, 0.5, 36, new Vel(1));
                cambio(2);
                ludo(talea, 3.25, 0.25, 40, new Vel(0.7));
                ludo(talea, 3.25, 0.25, 40, new Vel(0.7));
                ludo(talea, 3.5, 0.5, 40, new Vel(1));
                cambio(1);
                ludo(talea, 2.5, 0.8, ride, new Vel(rv * 1));
        }
    }

}
