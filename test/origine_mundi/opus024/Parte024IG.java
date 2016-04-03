/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package origine_mundi.opus024;

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
public class Parte024IG extends ParteTaleae {
    Integer talea_break;
    public Parte024IG(Mensa mensa, Integers ids, int talea_primo, int length, Integer talea_break) {
        super(mensa, ids, talea_primo, length);
        this.talea_break = talea_break;
    }

    @Override
    protected void creo(int t) {
        Doubles notes = null;
        double[] bass = new double[]{50, 50, 50, 50, 50, 50, 50, 50};
        boolean _break = talea_break != null && t == talea_break;
        
        switch(t % 2){
            case 0:
                notes = new Doubles(57, 60, 66, 69);
                break;
            case 1:
                notes = new Doubles(58, 62, 67, 70);
                break;
        }
        for (int j = 0; j < (_break?2:4); j++) {
            ponoFormae("wah0", t, j + 0.5, 0, new Punctum(1200));
            ponoFormae("wah0", t, j + 0.6, 0, new Punctum(2500));
            ponoFormae("wah0", t, j + 1, 0, new Punctum(1200));
            cambio(0);
            ludo(t, j + 0.5, 0.2, notes, new Vel(1));
            ludo(t, j + 0.75, 0.1, notes, new Vel(0.7));
            cambio(1);
            ludo(t, j + 0.0, 0.2, bass[j * 2], new Vel(1));
            ludo(t, j + 0.25, 0.1, bass[j * 2], new Vel(1));
            ludo(t, j + 0.5, 0.2, bass[j * 2 + 1], new Vel(1));
            ludo(t, j + 0.75, 0.1, bass[j * 2 + 1], new Vel(1));
        }
        if(t % 4 == 0){
            cambio(0);
            ponoPan(t + 0, 0, new Cinctum(true, new Punctum(1), new Punctum(0.5)));
            ponoPan(t + 2, 0, new Cinctum(true, new Punctum(0.5), new Punctum(1)));
            ponoPan(t + 4, 0, new Cinctum(true, new Punctum(1), new Punctum(0.5)));
            cambio(1);
            ponoPan(t + 0, 0, new Cinctum(true, new Punctum(0.5), new Punctum(1)));
            ponoPan(t + 2, 0, new Cinctum(true, new Punctum(1), new Punctum(0.5)));
            ponoPan(t + 4, 0, new Cinctum(true, new Punctum(0.5), new Punctum(1)));
            ponoLevel(t + 0, 0, new Punctum(0.2));
            ponoLevel(t + 2, 0, new Punctum(1));
            ponoLevel(t + 4, 0, new Punctum(0.2));
            ponoFormae("wah1", t + 0, 0, 0, new Punctum(300));
            ponoFormae("wah1", t + 2, 0, 0, new Punctum(2000));
            ponoFormae("wah1", t + 4, 0, 0, new Punctum(300));
        }

    }
}
