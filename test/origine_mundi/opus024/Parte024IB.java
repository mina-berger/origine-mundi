/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package origine_mundi.opus024;

import com.mina.util.Integers;
import la.clamor.Vel;
import la.clamor.opus.Mensa;
import la.clamor.opus.ParteTaleae;

/**
 *
 * @author hiyamamina
 */
public class Parte024IB extends ParteTaleae {
    Integer talea_break;
    public Parte024IB(Mensa mensa, Integers ids, int talea_primo, int length, Integer talea_break) {
        super(mensa, ids, talea_primo, length);
        this.talea_break = talea_break;
    }

    @Override
    protected void creo(int t) {
        boolean _break = talea_break != null && t == talea_break;
        cambio(0);
        ludo(t,
                (int i) -> new double[]{0.5, 1.0, 1.75, 2.5, 2.75, 3.0, 3.5}[i],
                (int i) -> new double[]{0.2, 0.7, 0.2, 0.22, 0.15, 0.45, 0.45}[i],
                (int i) -> new double[]{38., 38., 38., 50., 50., 38., 38.}[i],
                (int i) -> new Vel(0.8), _break?3:7);
    }

}
