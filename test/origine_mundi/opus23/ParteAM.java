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
public class ParteAM extends ParteTaleae {
    private boolean end;
    public ParteAM(Mensa mensa, Integers ids, int talea_primo, int length, boolean end) {
        super(mensa, ids, talea_primo, length);
        this.end = end;
    }

    @Override
    protected void creo(int talea) {
        if(end && talea % 8 >= 4){
            switch (talea % 8) {
                case 4:
                    ludo(talea, 0.0, 0.2, 69, new Vel(0.8));
                    ludo(talea, 0.25, 0.2, 67, new Vel(0.8));
                    ludo(talea, 0.75, 0.5, 69, new Vel(0.8));
                    ludo(talea, 1.25, 0.5, 70, new Vel(0.8));
                    ludo(talea, 1.75, 0.5, 72, new Vel(0.8));
                    ludo(talea, 2.75, 2.5, 77, new Vel(0.8));
                    ludo(talea, 2.75, 2.5, 65, new Vel(0.8));
                    break;
                case 5:
                    ludo(talea, 1.0, 0.95, 77, new Vel(0.8));
                    ludo(talea, 2.0, 0.45, 77, new Vel(0.8));
                    ludo(talea, 2.75, 2.5, 77, new Vel(0.8));
                    ludo(talea, 1.0, 0.45, 75, new Vel(0.8));
                    ludo(talea, 1.5, 0.45, 74, new Vel(0.8));
                    ludo(talea, 2.0, 0.2, 72, new Vel(0.8));
                    ludo(talea, 2.25, 0.2, 73, new Vel(0.8));
                    ludo(talea, 2.75, 2.5, 65, new Vel(0.8));
                    break;
                case 6:
                    ludo(talea, 1.5, 0.45, 67, new Vel(0.8));
                    ludo(talea, 2.0, 0.2, 69, new Vel(0.8));
                    ludo(talea, 2.25, 0.2, 69, new Vel(0.8));
                    ludo(talea, 2.5, 0.2, 67, new Vel(0.8));
                    ludo(talea, 2.75, 0.2, 69, new Vel(0.8));
                    ludo(talea, 3.5, 0.2, 65, new Vel(0.8));
                    ludo(talea, 3.75, 0.2, 67, new Vel(0.8));
                    break;
                case 7:
                    ludo(talea, 0.0, 0.3, 69, new Vel(0.8));
                    ludo(talea, 0.5, 0.3, 65, new Vel(0.8));
                    ludo(talea, 1.0, 0.3, 67, new Vel(0.8));
                    ludo(talea, 1.5, 2.7, 65, new Vel(0.8));
                    break;
            }
            
            return;
        }
        switch (talea % 8) {
            case 0:
            case 4:
                ludo(talea, 0.0, 0.2, 69, new Vel(0.8));
                ludo(talea, 0.25, 0.2, 67, new Vel(0.8));
                ludo(talea, 0.75, 0.5, 69, new Vel(0.8));
                ludo(talea, 1.25, 0.2, 70, new Vel(0.8));
                ludo(talea, 1.75, 0.5, 72, new Vel(0.8));
                ludo(talea, 2.25, 0.2, 70, new Vel(0.8));
                ludo(talea, 2.75, 0.5, 69, new Vel(0.8));
                ludo(talea, 3.25, 1.2, 67, new Vel(0.8));
                break;
            case 1:
            case 5:
                ludo(talea, 0.0, 0.2, 65, new Vel(0.8));
                ludo(talea, 0.25, 0.2, 64, new Vel(0.8));
                ludo(talea, 0.75, 0.5, 65, new Vel(0.8));
                ludo(talea, 1.25, 0.2, 67, new Vel(0.8));
                ludo(talea, 1.75, 0.5, 69, new Vel(0.8));
                ludo(talea, 2.25, 0.2, 67, new Vel(0.8));
                ludo(talea, 2.75, 0.5, 65, new Vel(0.8));
                ludo(talea, 3.25, 1.2, 60, new Vel(0.8));
                break;
            case 2:
            case 6:
                ludo(talea, 0.0, 0.7, 62, new Vel(0.8));
                ludo(talea, 0.75, 0.7, 65, new Vel(0.8));
                ludo(talea, 1.5, 0.4, 62, new Vel(0.8));
                ludo(talea, 2.0, 0.7, 60, new Vel(0.8));
                ludo(talea, 2.75, 0.7, 65, new Vel(0.8));
                break;
            case 3:
                ludo(talea - 1, 3.5, 0.4, 69, new Vel(0.8));
                ludo(talea, 0.0, 0.3, 70, new Vel(0.8));
                ludo(talea, 0.5, 0.3, 69, new Vel(0.8));
                ludo(talea, 1.0, 0.3, 65, new Vel(0.8));
                ludo(talea, 1.5, 2.7, 67, new Vel(0.8));
                break;
            case 7:
                ludo(talea, 0.0, 0.3, 65, new Vel(0.8));
                ludo(talea, 0.5, 0.3, 65, new Vel(0.8));
                ludo(talea, 1.0, 0.3, 67, new Vel(0.8));
                ludo(talea, 1.5, 2.7, 65, new Vel(0.8));
                break;
        }
    }

}
