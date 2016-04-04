/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package origine_mundi.opus025;

import com.mina.util.Integers;
import java.util.ArrayList;
import la.clamor.Cinctum;
import la.clamor.Punctum;
import la.clamor.forma.CadentesFormae;
import la.clamor.forma.Chorus;
import la.clamor.forma.Compressor;
import la.clamor.forma.IIRFilter;
import la.clamor.forma.Limitter;
import la.clamor.forma.PressureMeter;
import la.clamor.opus.Humanizer;
import la.clamor.opus.Mensa;
import la.clamor.opus.Taleae;
import origine_mundi.archive.ArchiveLudior;

/**
 *
 * @author hiyamamina
 */
public class Opus025 extends Mensa {

    public Opus025() {
        super(true, false);
    }

    @Override
    protected void ponoComitis(ArrayList<Taleae.Comes> comites) {
    }

    static double tempo = 200;

    @Override
    protected void ponoRapidi(ArrayList<Taleae.Rapidus> rapidi) {
        rapidi.add(new Taleae.Rapidus(0, 0, tempo, false));
    }

    @Override
    protected void creo() {
        new Parte025AG(this, new Integers(4), 1, 32).creo();
        new Parte025AM(this, new Integers(5), 1, 32).creo();
        new Parte025AB(this, new Integers(6), 1, 32).creo();
        new Parte025AD(this, new Integers(0), 1, 32).creo();
    }

    @Override
    public void initio() {
    }

    @Override
    protected void anteFacio() {
        ponoInstrument(0, new Punctum(0.3), new Cinctum(true, new Punctum(1), new Punctum(0.5)), new ArchiveLudior("mu500r", "drum_basic", 10), new CadentesFormae(
                new Compressor(new Punctum(0.2), new Punctum(0.1))//,
                //new Compressor(new Punctum(0.2), new Punctum(0.2)),
                //new PressureMeter("point1")
                //new Equalizer(1., 0., 0., 0., 0., 0., 0., -0.5, -1., -1.)
        ));
        /*ponoInstrument(1, new Punctum(0.5, 0.5), new Cinctum(), new ArchiveLudior("mu500r", "drum_basic", 10), new CadentesFormae(
                new Limitter(new Punctum(0.72)),
                //new Compressor(new Punctum(0.2), new Punctum(0.1)),
                //new PressureMeter("point1"),
                new IIRFilter(500, 4000, true),
                new Limitter(new Punctum(0.31))
        ));

        ponoInstrument(2, new Punctum(0.5, 0.5), new Cinctum(), new ArchiveLudior("mu500r", "drum_basic", 10), new CadentesFormae(
                new Compressor(new Punctum(0.2), new Punctum(0.1)),
                new Equalizer(-1., -1., -1., 0., 0., 0., 0., -0.5, -1., -1.)
        ));*/
        ponoInstrument(4, new Punctum(0.8), new Cinctum(true, new Punctum(0.5), new Punctum(1)), new ArchiveLudior("mu500r", "nylgt", 80), new CadentesFormae(
                new IIRFilter(80, false),
                new Limitter(new Punctum(3)),
                new Compressor(new Punctum(0.7), new Punctum(0.1), new Punctum(1)),
                new Limitter(new Punctum(0.8)),
                new Compressor(new Punctum(0.5), new Punctum(0.3), new Punctum(1)),
                new Limitter(new Punctum(0.7))
         
        ///*,
        //new Limitter(new Punctum(0.70))
        /*new Equalizer(0., 1., 1., 0., -0.5, -0.5, -1., -1., 3., 3.),
                new Limitter(new Punctum(0.81))*/
        ));
        ponoInstrument(5, new Punctum(0.8), new Cinctum(), new ArchiveLudior("mu500r", "accord", 50), new CadentesFormae( //new Equalizer(0., 1., 1., 0., -0.5, -0.5, -1., -1., 3., 3.)
                //new PressureMeter("point0"),
                new Limitter(new Punctum(0.85)),
                new Compressor(new Punctum(0.5), new Punctum(0.5))//,
                //new PressureMeter("point1")
                //new Limitter(new Punctum(0.65))
                //new PressureMeter("point1")
                ));
        ponoInstrument(6, new Punctum(0.2), new Cinctum(true, new Punctum(1), new Punctum(1)), new ArchiveLudior("mu500r", "bass006433", 10), new CadentesFormae(
                new Compressor(new Punctum(0.5), new Punctum(0.1)),
                new Compressor(new Punctum(0.8), new Punctum(0.3))//,
                //new PressureMeter("point1")
                //new Equalizer(2., 1., 1., 0., 0, 0, 0., -0.5, -1., -1.)
        ));
        ponoHumanizer(new Humanizer()
                .pono(0, 0, 1).pono(0.25, 0.015, 0.5).pono(0.5, 0.02, 0.8).pono(0.75, 0.025, 0.5)
                .ponoRandomVelocitas(0, 0.).ponoRandomRepenso(-0.03, 0.03), 4, 5);
        ponoMasterFormas(
                new Limitter(new Punctum(2.3)),
                new Chorus(new Punctum(0.3), new Punctum(tempo / 60. * 2.), new Punctum(0.8), new Punctum(0.2)),
                new Limitter(new Punctum(0.93)),
                new Compressor(new Punctum(0.4), new Punctum(0.3)),
                new Compressor(new Punctum(0.7), new Punctum(0.3)),
                new PressureMeter("point1")//,
        );
    }

}
