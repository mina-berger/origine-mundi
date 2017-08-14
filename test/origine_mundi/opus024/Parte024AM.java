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
public class Parte024AM extends ParteTaleae{

    public Parte024AM(Mensa mensa, Integers ids, int talea_primo, int length) {
        super(mensa, ids, talea_primo, length);
    }

    @Override
    protected void creo(int t) {
        switch(t % 8){
            case 0:
            case 4:
                ludo(t - 1, 3.5, 0.2, 62, new Vel(0.8));
                ludo(t, 0.0, 0.45, 72, new Vel(0.8));
                ludo(t, 0.5, 0.2, 70, new Vel(0.8));
                ludo(t, 0.75, 0.2, 69, new Vel(0.8));
                ludo(t, 1.0, 0.2, 70, new Vel(0.8));
                ludo(t, 1.5, 0.45, 74, new Vel(0.8));
                ludo(t, 2.0, 0.45, 62, new Vel(0.8));
                ludo(t, 2.5, 0.45, 64, new Vel(0.8));
                ludo(t, 3.0, 0.45, 66, new Vel(0.8));
                ludo(t, 3.5, 0.45, 67, new Vel(0.8));
                break;
            case 1:
                ludo(t, 0.0, 0.45, 69, new Vel(0.8));
                ludo(t, 0.5, 0.2, 70, new Vel(0.8));
                ludo(t, 0.75, 0.2, 72, new Vel(0.8));
                ludo(t, 1.0, 0.2, 70, new Vel(0.8));
                ludo(t, 1.5, 0.45, 67, new Vel(0.8));
                ludo(t, 2.0, 0.45, 66, new Vel(0.8));
                ludo(t, 2.5, 0.45, 67, new Vel(0.8));
                ludo(t, 3.0, 0.45, 69, new Vel(0.8));
                ludo(t, 3.5, 0.45, 70, new Vel(0.8));
                break;
            case 2:
                ludo(t, 0.0, 0.45, 72, new Vel(0.8));
                ludo(t, 0.5, 0.2, 74, new Vel(0.8));
                ludo(t, 0.75, 0.2, 75, new Vel(0.8));
                ludo(t, 1.0, 0.2, 74, new Vel(0.8));
                ludo(t, 1.5, 0.45, 71, new Vel(0.8));
                ludo(t, 2.0, 0.45, 79, new Vel(0.8));
                ludo(t, 2.5, 0.45, 77, new Vel(0.8));
                ludo(t, 3.0, 0.45, 75, new Vel(0.8));
                ludo(t, 3.5, 0.45, 74, new Vel(0.8));
                break;
            case 3:
                ludo(t, 0.0, 0.45, 72, new Vel(0.8));
                ludo(t, 0.5, 0.2, 70, new Vel(0.8));
                ludo(t, 0.75, 0.2, 69, new Vel(0.8));
                ludo(t, 1.0, 0.2, 70, new Vel(0.8));
                ludo(t, 1.5, 0.45, 67, new Vel(0.8));
                ludo(t, 2.0, 0.95, 74, new Vel(0.8));
                break;
            case 5:
                ludo(t, 0.0, 0.45, 69, new Vel(0.8));
                ludo(t, 0.5, 0.2, 67, new Vel(0.8));
                ludo(t, 0.75, 0.2, 66, new Vel(0.8));
                ludo(t, 1.0, 0.2, 67, new Vel(0.8));
                ludo(t, 1.5, 0.45, 70, new Vel(0.8));
                ludo(t, 2.0, 0.45, 69, new Vel(0.8));
                ludo(t, 2.5, 0.45, 70, new Vel(0.8));
                ludo(t, 3.0, 0.45, 72, new Vel(0.8));
                ludo(t, 3.5, 0.45, 74, new Vel(0.8));
                break;
            case 6:
                ludo(t, 0.0, 0.45, 75, new Vel(0.8));
                ludo(t, 0.5, 0.2, 74, new Vel(0.8));
                ludo(t, 0.75, 0.2, 72, new Vel(0.8));
                ludo(t, 1.0, 0.2, 74, new Vel(0.8));
                ludo(t, 1.5, 0.45, 75, new Vel(0.8));
                ludo(t, 2.0, 0.45, 66, new Vel(0.8));
                ludo(t, 2.5, 0.45, 67, new Vel(0.8));
                ludo(t, 3.0, 0.45, 69, new Vel(0.8));
                ludo(t, 3.5, 0.45, 72, new Vel(0.8));
                break;
            case 7:
                ludo(t, 0.0, 0.45, 70, new Vel(0.8));
                ludo(t, 0.5, 0.2, 69, new Vel(0.8));
                ludo(t, 0.75, 0.2, 67, new Vel(0.8));
                ludo(t, 1.0, 0.2, 69, new Vel(0.8));
                ludo(t, 1.5, 0.45, 66, new Vel(0.8));
                ludo(t, 2.0, 1.45, 67, new Vel(0.8));
                break;
            
        }
    }
    
}
