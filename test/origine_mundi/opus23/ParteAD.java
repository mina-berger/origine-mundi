/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package origine_mundi.opus23;

import com.mina.util.Integers;
import la.clamor.Vel;
import la.clamor.opus.Mensa;
import la.clamor.opus.ParteTaleae;

/**
 *
 * @author hiyamamina
 */
public class ParteAD extends ParteTaleae {
    public ParteAD(Mensa mensa, Integers ids, int talea_primus, int length) {
        super(mensa, ids, talea_primus, length);
    }

    @Override
    protected void creo(int taela) {
        cambio(0);
        if(taela % 4 == 3){
            ludo(taela, 
                (int index) -> new double[]{0.0, 1.5, 2.5}[index], 
                (int index) -> 0.5, 
                (int index) -> 36., 
                (int index) -> new Vel(new double[]{1, 0.5, 1}[index]), 3);
            //ludo(i, 0.0, 0.5, 36, new Vel(1));
            //ludo(i, 1.5, 0.5, 36, new Vel(0.5));
            //ludo(i, 2.5, 0.5, 36, new Vel(1));
        }else{
            ludo(taela, 
                (int index) -> new double[]{0.0, 1.5, 2.0, 3.5}[index], 
                (int index) -> 0.5, 
                (int index) -> 36., 
                (int index) -> new Vel(new double[]{1, 0.8, 1, 0.5}[index]), 4);
            //ludo(i, 0.0, 0.5, 36, new Vel(1));
            //ludo(i, 1.5, 0.5, 36, new Vel(0.5));
            //ludo(i, 2.0, 0.5, 36, new Vel(1));
            //ludo(i, 3.5, 0.5, 36, new Vel(0.5));
        }
        cambio(1);
        ludo(taela, 
            (int index) -> index * 0.5, 
            (int index) -> 0.5, 
            (int index) -> (index == 7?46d:42d), 
            (int index) -> new Vel(1), 8);
        cambio(2);
        ludo(taela, 
            (int index) -> index * 2 + 1.0, 
            (int index) -> 1., 
            (int index) -> 40d, 
            (int index) -> new Vel(1), 2);
        //ludo(i, 1.0, 1, 40, new Vel(1));
        //ludo(i, 3.0, 1, 40, new Vel(1));
    }
}

