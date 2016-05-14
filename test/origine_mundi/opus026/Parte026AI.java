/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package origine_mundi.opus026;

import com.mina.util.Doubles;
import com.mina.util.Integers;
import la.clamor.Punctum;
import la.clamor.Vel;
import la.clamor.opus.Mensa;
import la.clamor.opus.ParteTaleae;

/**
 *
 * @author hiyamamina
 */
public class Parte026AI extends ParteTaleae {
    int last;
    public Parte026AI(Mensa mensa, Integers ids, int talea_primo, int length, int last) {
        super(mensa, ids, talea_primo, length);
        this.last = last;
    }

    @Override
    protected void creo(int t) {
        if(t % 4 == 3){
            cambio(2);
            ludo(t, 0, 4, 61, new Vel(1));
        }else{
            cambio(0);
            ludo(t, 0.5, 0.5, 60, new Vel(1));
            cambio(1);
            ludo(t, 0, 0.5, 60, new Vel(1));
        }
        if(t == last){
            cambio(3);
            ludo(t, 3, 4, 62, new Vel(1));  
            this.ponoLevel(t, 0, Punctum.ZERO);
            this.ponoLevel(t, 3.49, Punctum.ZERO);
            this.ponoLevel(t, 3.5, new Punctum(1));
            cambio(4);
            ludo(t, 0, 4, 60, new Vel(1));  
        }
    }

}
