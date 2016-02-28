/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package origine_mundi.opus;

import la.clamor.Velocitas;
import la.clamor.Punctum;
import la.clamor.opus.Mensa;
import la.clamor.opus.Taleae.Comes;
import la.clamor.opus.Taleae.Rapidus;

/**
 *
 * @author minae.hiyamae
 */
public class ZZZSample1 extends Mensa {
    int count = 10;
    public ZZZSample1() {
        super("sample1", false, true);
    }

    @Override
    protected Comes[] capioComitis() {
        return new Comes[]{new Comes(0, 4)};
    }

    @Override
    protected Rapidus[] capioRapidi() {
        return new Rapidus[]{new Rapidus(0, 0, 90, false), new Rapidus(count, 0, 600, true)};
    }

    @Override
    public void initio() {
    }
    @Override
    protected void creo() {
        ponoOscillator(0, new Punctum(1, 1.0), "epiano");
                
        for(int i = 0;i < count;i++){
            ludo(0, i, 0.0, 0.5, i + 5,  Velocitas.una(1));
            ludo(0, i, 0.5, 0.5, i + 9,  Velocitas.una(1));
            ludo(0, i, 1.0, 0.5, i + 12, Velocitas.una(1));
            ludo(0, i, 1.5, 0.5, i + 16, Velocitas.una(1));
            ludo(0, i, 2.0, 0.5, i + 19, Velocitas.una(1));
            ludo(0, i, 2.5, 0.5, i + 16, Velocitas.una(1));
            ludo(0, i, 3.0, 0.5, i + 12, Velocitas.una(1));
            ludo(0, i, 3.5, 0.5, i + 9,  Velocitas.una(1));
        }
    }

    @Override
    protected void anteFacio() {
    }

    
}
