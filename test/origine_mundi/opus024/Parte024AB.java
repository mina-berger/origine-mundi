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
public class Parte024AB extends ParteTaleae {

    public Parte024AB(Mensa mensa, Integers ids, int talea_primo, int length) {
        super(mensa, ids, talea_primo, length);
    }

    @Override
    protected void creo(int t) {
        cambio(0);
        ludo(t,
                (int i) -> new double[]{0.5, 1.0, 1.75, 2.5, 2.75, 3.0, 3.5}[i],
                (int i) -> new double[]{0.2, 0.7, 0.2, 0.22, 0.15, 0.45, 0.45}[i],
                (int i)
                -> (t % 4 == 0 || t % 4 == 1) ? new double[]{38., 38., 38., 50., 50., 38., 38.}[i]
                : (t % 8 == 2) ? new double[]{33., 35., 35., 48., 48., 36., 36.}[i]
                : (t % 8 == 3) ? new double[]{36., 36., 36., 50., 50., 38., 38.}[i]
                : (t % 8 == 6) ? new double[]{36., 36., 36., 46., 46., 33., 33.}[i]
                : new double[]{31., 38., 31., 43., 43., 31., 31.}[i]
                        ,
                (int i) -> new Vel(
                        0.8), 7);
    }

}
