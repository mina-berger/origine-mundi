/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package origine_mundi.opus24;

import com.mina.util.Doubles;
import java.util.ArrayList;
import la.clamor.Cinctum;
import la.clamor.Punctum;
import la.clamor.Vel;
import la.clamor.forma.CadentesFormae;
import la.clamor.forma.Chorus;
import la.clamor.forma.Compressor;
import la.clamor.forma.Equalizer;
import la.clamor.forma.FormaNominata;
import la.clamor.forma.Wah;
import la.clamor.opus.Mensa;
import la.clamor.opus.Taleae;
import origine_mundi.archive.ArchiveLudior;

/**
 *
 * @author hiyamamina
 */
public class Opus024 extends Mensa {

    public Opus024() {
        super(true, false);
    }

    @Override
    protected void ponoComitis(ArrayList<Taleae.Comes> comites) {
    }

    static double tempo = 84;

    @Override
    protected void ponoRapidi(ArrayList<Taleae.Rapidus> rapidi) {
        rapidi.add(new Taleae.Rapidus(0, 0, tempo, false));
    }

    @Override
    protected void creo() {
        Wah wah = new Wah(new Punctum(1200));
        ponoInstrument(0, new Punctum(1.5), new Cinctum(), new ArchiveLudior("mu500r", "mtgt", 50), new CadentesFormae(
                new Compressor(new Punctum(0.7), new Punctum(0.05)),
                new FormaNominata("wah", wah),
                new Compressor(new Punctum(0.1), new Punctum(0.05)),
                //new IIRFilter(2000, true),
                new Equalizer(-1., 0., 0., 1., -0, 0, -1., -1., -1., -1.),
        //new Distortion(new Punctum(0.5), new Punctum(1.3))
        //new Compressor(new Punctum(0.5), new Punctum(0.0))//,
                new Chorus(new Punctum(0.3), new Punctum(tempo / 60. * 2.), new Punctum(0.5, -0.5), new Punctum(0.5, -0.5))
        ));

        for (int i = 0; i < 4; i++) {
            Doubles notes = i % 2 == 0 ? new Doubles(50, 57, 61, 66, 69) : new Doubles(45, 57, 60, 64, 71);
            for(int j = 0;j < 4;j++){
                ponoFormae("wah", i, j + 0.5, 0, new Punctum(1200));
                ponoFormae("wah", i, j + 0.6, 0, new Punctum(2500));
                ponoFormae("wah", i, j + 1, 0, new Punctum(1200));
                ludo(0, i, j + 0.5, 0.2, notes, new Vel(1));
                ludo(0, i, j + 0.75, 0.1, notes, new Vel(0.7));
                //wah.ponoPositio(capioTempus(i, j + 1.), new Punctum(1500));
            }
        }
    }

    @Override
    public void initio() {
    }

    @Override
    protected void anteFacio() {
    }

}
