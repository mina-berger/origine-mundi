/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package origine_mundi.opus23;

import com.mina.util.Doubles;
import com.mina.util.Integers;
import la.clamor.Vel;
import la.clamor.opus.Mensa;
import la.clamor.opus.ParteTaleae;

/**
 *
 * @author hiyamamina
 */
public class ParteIB extends ParteTaleae {
    boolean end;
    public ParteIB(Mensa mensa, Integers ids, int talea_primo, int length, boolean end) {
        super(mensa, ids, talea_primo, length);
        this.end = end;
    }

    @Override
    protected void creo(int t) {
        switch(end?t % 4:t % 8){
            case 0:
            case 2:
            case 4:
            case 6:
                ludo(t, 0.0, 0.2, 45, new Vel(0.8));
                ludo(t, 0.25, 0.2, 43, new Vel(1));
                ludo(t, 0.75, 0.45, 45, new Vel(0.8));
                ludo(t, 1.25, 0.45, (end?t % 4:t % 8) == 2?47:46, new Vel(0.8));
                ludo(t, 1.75, 1.0, 48, new Vel(0.8));
                ludo(t, 3., 3.5, 36, new Vel(1));
                ludo(t + 1, 2.75, 0.2, 48, new Vel(0.8));
                ludo(t + 1, 3.0, 0.9, 36, new Vel(0.8));
                cambio(1);
                //ludoClaves(t, 
                //    (int i) -> i * 1.0 + 0.5, 
                //    (int i) -> 0.25, 
                //    (int i) -> (i < 2?new Doubles(67, 72, 76):t % 8 == 2?new Doubles(66, 69, 74):new Doubles(65, 70, 74)), 
                //    (int i) -> new Vel(0.7), 8);

                ludo(t, 0.0, 1.7, new Doubles(67, 72, 76), new Vel(0.8));
                if(!end && t % 8 == 6){
                    ludo(t, 1.75, 5.2, new Doubles(65, 70, 74), new Vel(0.8));
                    ludo(t + 1, 3., 0.9, new Doubles(64, 70, 73), new Vel(0.8));
                }else{
                    ludo(t, 1.75, 6.2, (end?t % 4:t % 8) == 2?new Doubles(66, 69, 74):new Doubles(65, 70, 74), new Vel(0.8));
                }
                
                break;

        }
    }
    
}
