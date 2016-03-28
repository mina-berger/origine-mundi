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
public class ParteID extends ParteTaleae {
    boolean end;

    public ParteID(Mensa mensa, Integers ids, int talea_primo, int length, boolean end) {
        super(mensa, ids, talea_primo, length);
        this.end = end;
    }

    @Override
    protected void creo(int t) {
        switch(end?t % 4:t % 8){
            case 7:
                cambio(0);
                ludo(t, 0.75, 0.5, 36, new Vel(1));
                ludo(t, 1.25, 0.5, 36, new Vel(1));
                ludo(t, 1.75, 0.5, 36, new Vel(1));
                ludo(t, 3.0, 0.5, 36, new Vel(1));
                cambio(1);
                ludo(t, 
                    (int i) -> i * 0.25, 
                    (int i) -> 0.25, 
                    (int i) -> (i == 3 || i == 5 || i == 7?46d:42d), 
                    (int i) -> new Vel(i == 7?0.1:0.7), 9);
                ludo(t, 2.75, 0.25, 42, new Vel(0.7));
                ludo(t, 3.0, 1., 46, new Vel(0.7));
                break;
            case 0:
                if(!end){
                    cambio(1);
                    ludo(t - 1, 2.75, 0.25, 42, new Vel(0.7));
                    ludo(t - 1, 3.0, 1., 46, new Vel(0.7));
                }
            default:
                cambio(0);
                ludo(t, 1.0, 0.5, 36, new Vel(1));
                ludo(t, 3.0, 0.5, 36, new Vel(1));
                cambio(1);
                ludo(t, 
                    (int i) -> i * 0.25, 
                    (int i) -> 0.25, 
                    (int i) -> (t % 2 == 0 && (i == 3 || i == 5 || i == 7)?46d:42d), 
                    (int i) -> new Vel(0.7), 16);
        }
    }

}
