/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package origine_mundi.opus026;

import com.mina.util.Doubles;
import com.mina.util.Integers;
import la.clamor.Vel;
import la.clamor.opus.Mensa;
import la.clamor.opus.ParteTaleae;

/**
 *
 * @author hiyamamina
 */
public class Parte026AD extends ParteTaleae {

    int last;

    public Parte026AD(Mensa mensa, Integers ids, int talea_primo, int length, int last) {
        super(mensa, ids, talea_primo, length);
        this.last = last;
    }

    @Override
    protected void creo(int t) {
        if (t == last) {
            cambio(0);
            ludo(t,
                    (int i) -> new Doubles(0).get(i),
                    (int i) -> 0.25,
                    (int i) -> 36d,
                    (int i) -> new Vel(1), 1);
            
        } else if (t % 4 == 3) {
            cambio(0);
            ludo(t,
                    (int i) -> new Doubles(0, 3.5).get(i),
                    (int i) -> 0.25,
                    (int i) -> 36d,
                    (int i) -> i == 0 ? new Vel(1) : new Vel(0.8), 2);
            cambio(1);
            ludo(t,
                    (int i) -> new Doubles(1, 3).get(i),
                    (int i) -> 0.5,
                    (int i) -> 40d,
                    (int i) -> new Vel[]{new Vel(1), new Vel(1)}[i], 2);
            cambio(2);
            ludo(t,
                    (int i) -> new Doubles(0, 0.25, 0.5).get(i % 3) + 3,
                    (int i) -> new Doubles(0.25, 0.25, 0.5).get(i % 3),
                    (int i) -> new Doubles(42, 42, 46).get(i % 3),
                    (int i) -> new Vel[]{new Vel(0.5), new Vel(0.5), new Vel(0.8)}[i % 3], 3);

        } else {
            cambio(0);
            ludo(t,
                    (int i) -> new Doubles(0, 0.75, 1.5, 2.5, 2.75, 3.5).get(i),
                    (int i) -> 0.25,
                    (int i) -> 36d,
                    (int i) -> i == 0 ? new Vel(1) : i == 3 ? new Vel(0.6) : new Vel(0.8), 6);
            cambio(1);
            ludo(t,
                    (int i) -> new Doubles(1, 3).get(i),
                    (int i) -> 0.5,
                    (int i) -> 40d,
                    (int i) -> new Vel[]{new Vel(1), new Vel(1)}[i], 2);
            cambio(2);
            ludo(t,
                    (int i) -> new Doubles(0, 0.25, 0.5).get(i % 3) + i / 3,
                    (int i) -> new Doubles(0.25, 0.25, 0.5).get(i % 3),
                    (int i) -> new Doubles(42, 42, 46).get(i % 3),
                    (int i) -> new Vel[]{new Vel(0.5), new Vel(0.5), new Vel(0.8)}[i % 3], 12);
        }
    }

}
