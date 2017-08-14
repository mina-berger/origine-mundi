/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package origine_mundi.opus028;

import com.mina.util.Integers;
import java.util.ArrayList;
import la.clamor.Cinctum;
import la.clamor.Punctum;
import la.clamor.forma.AutoLimitter;
import la.clamor.forma.CadentesFormae;
import la.clamor.opus.Mensa;
import la.clamor.opus.Taleae;
import origine_mundi.archive.ArchiveLudior;

/**
 *
 * @author hiyamamina
 */
public class Opus028 extends Mensa{

    public Opus028() {
        super(true, false, false);
    }

    @Override
    protected void ponoComitis(ArrayList<Taleae.Comes> comites) {
    }

    static double tempo = 94;
    @Override
    protected void ponoRapidi(ArrayList<Taleae.Rapidus> rapidi) {
        rapidi.add(new Taleae.Rapidus(0, 0, tempo, false));
    }

    @Override
    protected void creo() {
        new Parte028IG(this, new Integers(0), 0, 4).creo();
    }

    @Override
    protected void dominor() {
    }

    @Override
    public void initio() {
    }

    @Override
    protected void anteFacio() {
        ponoInstrument(0, new Punctum(1), new Cinctum(true, new Punctum(1), new Punctum(1)), new ArchiveLudior("mu500r", "nylgt", 50), new CadentesFormae(
                new AutoLimitter()//,
                //new Compressor(new Punctum(0.2), new Punctum(0.1))
        ));
    }
    
}
