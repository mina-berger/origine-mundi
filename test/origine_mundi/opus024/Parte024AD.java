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
public class Parte024AD extends ParteTaleae {
    Integer talea_break;
    public Parte024AD(Mensa mensa, Integers ids, int talea_primo, int length, Integer talea_break) {
        super(mensa, ids, talea_primo, length);
        this.talea_break = talea_break;
    }

    @Override
    protected void creo(int t) {
        boolean _break = talea_break != null && t == talea_break;
        cambio(0);
        ludo(t, 
            (int index) -> new double[]{1., 3.}[index], 
            (int index) -> 0.5, 
            (int index) -> 36., 
            (int index) -> new Vel(1), _break?1:2);
        cambio(1);
        ludo(t, 
            (int index) -> index * 0.25, 
            (int index) -> 0.2, 
            (int index) -> 42d,//(index == 7?46d:42d), 
            (int index) -> new Vel(1), _break?8:16);
        cambio(2);
        ludo(t, 
            (int index) -> new double[]{1.75, 3.0}[index], 
            (int index) -> new double[]{0.25, 1.}[index], 
            (int index) -> 37d, 
            (int index) -> new Vel(1), _break?1:2);
    }
    
}
