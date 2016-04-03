/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package origine_mundi.opus023;

import com.mina.util.Doubles;
import com.mina.util.Integers;
import la.clamor.Cinctum;
import la.clamor.Punctum;
import la.clamor.Vel;
import la.clamor.opus.Mensa;
import la.clamor.opus.ParteTaleae;

/**
 *
 * @author hiyamamina
 */
public class Parte023IG extends ParteTaleae {

    boolean end;

    public Parte023IG(Mensa mensa, Integers ids, int talea_primo, int length, boolean end) {
        super(mensa, ids, talea_primo, length);
        this.end = end;
    }

    @Override
    protected void creo(int t) {
        double bass;
        for (int j = 0; j < (!end && t % 8 == 7 ? 2 : 4); j++) {
            cambio(0);
            bass = 48;
            ludo(t, j + 0.0, 0.2, bass, new Vel(1));
            ludo(t, j + 0.25, 0.1, bass, new Vel(1));
            ludo(t, j + 0.5, 0.2, bass, new Vel(1));
            ludo(t, j + 0.75, 0.1, bass, new Vel(1));
            cambio(1);
            bass = 48;
            ludo(t, j + 0.0, 0.2, bass, new Vel(1));
            ludo(t, j + 0.25, 0.1, bass, new Vel(1));
            ludo(t, j + 0.5, 0.2, bass, new Vel(1));
            ludo(t, j + 0.75, 0.1, bass, new Vel(1));
        }
        if (t % 4 == 0) {
            cambio(0);
            ponoPan(t + 0, 0, new Cinctum(true, new Punctum(1), new Punctum(0.5)));
            ponoPan(t + 2, 0, new Cinctum(true, new Punctum(0.5), new Punctum(1)));
            ponoPan(t + 4, 0, new Cinctum(true, new Punctum(1), new Punctum(0.5)));
            ponoLevel(t + 0, 0, new Punctum(1));
            ponoLevel(t + 2, 0, new Punctum(0.2));
            ponoLevel(t + 4, 0, new Punctum(1));
            cambio(1);
            ponoPan(t + 0, 0, new Cinctum(true, new Punctum(0.5), new Punctum(1)));
            ponoPan(t + 2, 0, new Cinctum(true, new Punctum(1), new Punctum(0.5)));
            ponoPan(t + 4, 0, new Cinctum(true, new Punctum(0.5), new Punctum(1)));
            ponoLevel(t + 0, 0, new Punctum(0.2));
            ponoLevel(t + 2, 0, new Punctum(1));
            ponoLevel(t + 4, 0, new Punctum(0.2));
            ponoFormae("wah0", t + 0, 0, 0, new Punctum(2000));
            ponoFormae("wah0", t + 2, 0, 0, new Punctum(300));
            ponoFormae("wah0", t + 4, 0, 0, new Punctum(2000));
            ponoFormae("wah1", t + 0, 0, 0, new Punctum(300));
            ponoFormae("wah1", t + 2, 0, 0, new Punctum(2000));
            ponoFormae("wah1", t + 4, 0, 0, new Punctum(300));
        }

    }
}
