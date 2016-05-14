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
import la.clamor.opus.ParteTaleae;

/**
 *
 * @author hiyamamina
 */
public class Parte027PD extends ParteTaleae {
    public Parte027PD(Mensa mensa, Integers ids, int talea_primo, int length) {
        super(mensa, ids, talea_primo, length);
    }

    @Override
    protected void creo(int t) {
        cambio(2);
        ludo(t, 
            (int index) -> new Doubles(3.25, 3.5).get(index), 
            (int index) -> 0.5, 
            (int index) -> 37d, 
            (int index) -> new Vel(1), 2);
    }
    
}
