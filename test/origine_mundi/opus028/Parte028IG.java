/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package origine_mundi.opus028;

import com.mina.util.Doubles;
import com.mina.util.Integers;
import la.clamor.Vel;
import la.clamor.opus.Mensa;
import la.clamor.opus.ParteTaleae;

/**
 *
 * @author hiyamamina
 */
public class Parte028IG extends ParteTaleae {


    public Parte028IG(Mensa mensa, Integers ids, int talea_primo, int length) {
        super(mensa, ids, talea_primo, length);
    }

    @Override
    protected void creo(int t) {
        double bass = t % 2 == 0?48:43;
        Doubles chord = t % 2 == 0?new Doubles(55, 59, 64, 67):new Doubles(53, 58, 62, 69);
        for (int j = 0; j < 4; j++) {
            cambio(0);
            if(j % 2 == 1){
                ludo(t, j + 0.0, 0.2, chord, new Vel(1));
            }else{
                ludo(t, j + 0.0, 0.2, bass, new Vel(1));
            }
            ludo(t, j + 0.25, 0.1, bass, new Vel(1));
            ludo(t, j + 0.5, 0.2, bass, new Vel(1));
            ludo(t, j + 0.75, 0.1, bass, new Vel(1));
        }

    }
}
