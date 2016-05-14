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
public class Parte027AD extends ParteTaleae {
    Integer end; 
    public Parte027AD(Mensa mensa, Integers ids, int talea_primo, int length) {
        this(mensa, ids, talea_primo, length, null);
    }
    public Parte027AD(Mensa mensa, Integers ids, int talea_primo, int length, Integer end) {
        super(mensa, ids, talea_primo, length);
        this.end = end;
    }

    @Override
    protected void creo(int t) {
        if(end != null && t == end){
            cambio(0);
            ludo(t, 
                (int index) -> new double[]{1., 1.75}[index], 
                (int index) -> 0.5, 
                (int index) -> 36., 
                (int index) -> new Vel(1), 2);
            cambio(1);
            ludo(t, 
                (int index) -> index * 0.25, 
                (int index) -> 0.2, 
                (int index) -> 42d,//(index == 7?46d:42d), 
                (int index) -> new Vel(1), 8);
            cambio(2);
            ludo(t, 
                (int index) -> new Doubles(0.5, 1.25, 1.5).get(index), 
                (int index) -> 0.5, 
                (int index) -> 37d, 
                (int index) -> new Vel(1), 3);
        }else{
            cambio(0);
            ludo(t, 
                (int index) -> new double[]{1., 3.}[index], 
                (int index) -> 0.5, 
                (int index) -> 36., 
                (int index) -> new Vel(1), 2);
            cambio(1);
            ludo(t, 
                (int index) -> index * 0.25, 
                (int index) -> 0.2, 
                (int index) -> 42d,//(index == 7?46d:42d), 
                (int index) -> new Vel(1), 16);
            cambio(2);
            ludo(t, 
                (int index) -> new Doubles(0.5, 1.25, 2, 2.75, 3.75).get(index), 
                (int index) -> 0.5, 
                (int index) -> 37d, 
                (int index) -> new Vel(1), 5);
        }
    }
    
}
