/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package origine_mundi.opus;

import java.util.ArrayList;
import la.clamor.Oscillator;
import la.clamor.Velocitas;
import la.clamor.Punctum;
import la.clamor.opus.Mensa;
import la.clamor.opus.Taleae.Comes;
import la.clamor.opus.Taleae.Rapidus;

/**
 *
 * @author minae.hiyamae
 */
public class Opus020 extends Mensa {
    int count = 10;
    public Opus020() {
        super(false, true);
    }

    @Override
    protected void ponoComitis(ArrayList<Comes> comites) {
        comites.add(new Comes(0, 4));
    }

    @Override
    protected void ponoRapidi(ArrayList<Rapidus> rapidi) {
        rapidi.add(new Rapidus(0, 0, 90, false));
        rapidi.add(new Rapidus(count, 0, 600, true));
    }

    @Override
    public void initio() {
    }
    @Override
    protected void creo() {
        ponoInstrument(0, new Punctum(1, 1.0), new Oscillator("epiano"));
                
        for(int i = 0;i < count;i++){
            ludo(0, i, 0.0, 0.5, i + 65,  new Velocitas(1));
            ludo(0, i, 0.5, 0.5, i + 69,  new Velocitas(1));
            ludo(0, i, 1.0, 0.5, i + 72, new Velocitas(1));
            ludo(0, i, 1.5, 0.5, i + 76, new Velocitas(1));
            ludo(0, i, 2.0, 0.5, i + 79, new Velocitas(1));
            ludo(0, i, 2.5, 0.5, i + 76, new Velocitas(1));
            ludo(0, i, 3.0, 0.5, i + 72, new Velocitas(1));
            ludo(0, i, 3.5, 0.5, i + 69,  new Velocitas(1));
        }
    }

    @Override
    protected void anteFacio() {
    }

    
}
