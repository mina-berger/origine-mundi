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
public abstract class Parte025_G extends ParteTaleae{

    public Parte025_G(Mensa mensa, Integers ids, int talea_primo, int length) {
        super(mensa, ids, talea_primo, length);
    }

    protected void creo(int t, Doubles chord0, Doubles chord1, Doubles bass) {
        if(chord1 == null){
            ludo(t, 0., 0.2, chord0, new Vel(0.8));
            ludo(t, 0., 0.2, bass.get(0), new Vel(0.8));
            return;
        }
        ludo(t, 
                (int i)->new Doubles(0, 2).get(i),
                (int i)->new Doubles(0.8, 0.8).get(i),
                (int i)->bass.get(i),
                (int i)->new Vel(0.8), 2);
        ludoClaves(t, 
                (int i)->new Doubles(1, 3).get(i),
                (int i)->new Doubles(0.1, 0.1).get(i),
                (int i)->(i % 2 == 0?chord0:chord1),
                (int i)->new Vel(0.8), 2);
    }
    protected void pickup(int t, Doubles bass) {
        ludo(t, 1.5, 0.2, bass.get(0), new Vel(0.8));
        ludo(t, 2., 0.8, bass.get(1), new Vel(0.8));
        ludo(t, 3., 0.8, bass.get(2), new Vel(0.8));
        
    }
    
}
