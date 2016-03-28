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
public class ParteBM extends ParteTaleae {

    public ParteBM(Mensa mensa, Integers ids, int talea_primo, int length) {
        super(mensa, ids, talea_primo, length);
    }

    @Override
    protected void creo(int t) {
        switch (t % 10) {
            case 0:
            case 4:
                ludo(t, 0.0, 0.7, 69, new Vel(0.8));
                ludo(t, 0.75, 0.2, 65, new Vel(0.8));
                ludo(t, 1.5, 0.7, 74, new Vel(0.8));
                ludo(t, 2.25, 1.2, 72, new Vel(0.8));
                ludo(t, 3.5, 0.2, 69, new Vel(0.8));
                ludo(t, 3.75, 0.2, 70, new Vel(0.8));
                break;
            case 1:
                ludo(t, 0.0, 0.2, 72, new Vel(0.8));
                ludo(t, 0.25, 0.5, 70, new Vel(0.8));
                ludo(t, 0.75, 0.2, 69, new Vel(0.8));
                ludo(t, 1.0, 0.5, 67, new Vel(0.8));
                ludo(t, 1.5, 0.2, 67, new Vel(0.8));
                ludo(t, 1.75, 0.5, 69, new Vel(0.8));
                ludo(t, 2.25, 1.2, 65, new Vel(0.8));
                ludo(t, 3.5, 0.2, 62, new Vel(0.8));
                ludo(t, 3.75, 0.2, 64, new Vel(0.8));
                break;
            case 2:
                ludo(t, 0.0, 0.2, 65, new Vel(0.8));
                ludo(t, 0.25, 0.5, 64, new Vel(0.8));
                ludo(t, 0.75, 0.5, 65, new Vel(0.8));
                ludo(t, 1.25, 0.2, 67, new Vel(0.8));
                ludo(t, 1.5, 0.5, 69, new Vel(0.8));
                ludo(t, 2., 0.2, 65, new Vel(0.8));
                ludo(t, 2.25, 1.5, 62, new Vel(0.8));
                ludo(t, 3.75, 0.2, 65, new Vel(0.8));
                break;
            case 3:
                ludo(t, 0.0, 0.5, 70, new Vel(0.8));
                ludo(t, 0.5, 0.2, 69, new Vel(0.8));
                ludo(t, 0.75, 0.5, 67, new Vel(0.8));
                ludo(t, 1.25, 0.2, 65, new Vel(0.8));
                ludo(t, 1.5, 2., 67, new Vel(0.8));
                ludo(t, 3.5, 0.2, 65, new Vel(0.8));
                ludo(t, 3.75, 0.2, 67, new Vel(0.8));
                break;
            case 5:
                ludo(t, 0.0, 0.2, 72, new Vel(0.8));
                ludo(t, 0.25, 0.5, 70, new Vel(0.8));
                ludo(t, 0.75, 0.2, 72, new Vel(0.8));
                ludo(t, 1.5, 0.7, 76, new Vel(0.8));
                ludo(t, 2.25, 1.2, 77, new Vel(0.8));
                ludo(t, 3.5, 0.2, 62, new Vel(0.8));
                ludo(t, 3.75, 0.2, 64, new Vel(0.8));
                break;
            case 6:
                ludo(t, 0.0, 0.2, 65, new Vel(0.8));
                ludo(t, 0.25, 0.2, 65, new Vel(0.8));
                ludo(t, 0.5, 0.2, 64, new Vel(0.8));
                ludo(t, 0.75, 0.2, 65, new Vel(0.8));
                ludo(t, 1.5, 0.2, 62, new Vel(0.8));
                ludo(t, 1.75, 0.2, 64, new Vel(0.8));
                ludo(t, 2.0, 0.2, 65, new Vel(0.8));
                ludo(t, 2.25, 0.2, 65, new Vel(0.8));
                ludo(t, 2.5, 0.2, 64, new Vel(0.8));
                ludo(t, 2.75, 0.2, 65, new Vel(0.8));
                ludo(t, 3.5, 0.2, 65, new Vel(0.8));
                ludo(t, 3.75, 0.2, 67, new Vel(0.8));
                break;
            case 7:
                ludo(t, 0.0, 0.2, 69, new Vel(0.8));
                ludo(t, 1.0, 0.2, 65, new Vel(0.8));
                ludo(t, 2.0, 0.2, 62, new Vel(0.8));
                ludo(t, 3.0, 0.2, 74, new Vel(0.8));
                break;
            case 8:
                ludo(t, 0.0, 4, 72, new Vel(0.8));
        }


    }
    @Override
    protected void ludo(int talea, double repenso, double diutius, double clavis, Vel velocitas) {
        super.ludo(talea, repenso, diutius, clavis, velocitas);
        super.ludo(talea, repenso, diutius, clavis + 12, velocitas);
    }
}