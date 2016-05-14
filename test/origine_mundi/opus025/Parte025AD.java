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
import la.clamor.opus.ParteTaleae;

/**
 *
 * @author hiyamamina
 */
public class Parte025AD extends ParteTaleae {

    public Parte025AD(Mensa mensa, Integers ids, int talea_primo, int length) {
        super(mensa, ids, talea_primo, length);
    }

    @Override
    protected void creo(int t) {
        switch (t % 16) {
            //case 3:
            case 7:
            //case 11:
                ludo(t,
                        (int i) -> new Doubles(0, 0.5, 1, 2, 3, 3.5).get(i),
                        (int i) -> new Doubles(1, 0.5, 0.8, 1, 0.45, 0.45).get(i),
                        (int i) -> new Doubles(26, 25, 27, 27, 27, 27).get(i),
                        (int i) -> new Vel[]{
                            new Vel(0.05), new Vel(0.1), new Vel(0.8),
                            new Vel(0.8), new Vel(0.8), new Vel(0.5)}[i], 6);
                break;
            case 15:
                ludo(t,
                        (int i) -> new Doubles(0).get(i),
                        (int i) -> new Doubles(1).get(i),
                        (int i) -> new Doubles(27).get(i),
                        (int i) -> new Vel(0.8), 1);
                break;
            case 0:
                ludo(t - 1,
                        (int i) -> new Doubles(1.5, 2, 3).get(i),
                        (int i) -> new Doubles(0.5, 0.45, 0.45).get(i),
                        (int i) -> new Doubles(25, 27, 27).get(i),
                        (int i) -> new Vel[]{
                            new Vel(0.1), new Vel(0.8), new Vel(0.5)}[i], 3);
                
            default:
                ludo(t,
                        (int i) -> new Doubles(0, 0.5, 1, 2, 2.5, 3).get(i),
                        (int i) -> new Doubles(1, 0.5, 0.8).get(i % 3),
                        (int i) -> new Doubles(26, 25, 27).get(i % 3),
                        (int i) -> new Vel[]{new Vel(0.05), new Vel(0.1), new Vel(0.8)}[i % 3], 6);
                break;
        }
    }

}
