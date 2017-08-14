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
public class Parte027BD extends ParteTaleae {

    public Parte027BD(Mensa mensa, Integers ids, int talea_primo, int length) {
        super(mensa, ids, talea_primo, length);
    }

    @Override
    protected void creo(int t) {
        cambio(0);
        ludo(t,
                (int index) -> new double[]{1., 3.}[index],
                (int index) -> 0.5,
                (int index) -> 36.,
                (int index) -> new Vel(1), 2);
        cambio(1);
            ludo(t, 
                (int index) -> index + 0.5, 
                (int index) -> 0.2, 
                (int index) -> 42d,//(index == 7?46d:42d), 
                (int index) -> new Vel(1), 4);
        cambio(2);
        ludo(t,
                (int index) -> new Doubles(0.5, 1.25, 2, 2.75, 3.75).get(index),
                (int index) -> 0.5,
                (int index) -> 37d,
                (int index) -> new Vel(1), 5);
        cambio(3);
        ludo(t,
                (int index) -> new Doubles(0, 0.5, 0.75, 1.25, 1.75, 2.25, 2.75, 3, 3.5).get(index),
                (int index) -> new Doubles(0.5, 0.25, 0.5, 0.5, 0.5, 0.5, 0.25, 0.5, 0.5).get(index),
                (int index) -> 59d,//(index == 7?46d:42d), 
                (int index) -> new Vel(1), 9);
    }

}
