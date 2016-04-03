/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package origine_mundi.opus024;

import com.mina.util.Integers;
import java.util.ArrayList;
import la.clamor.Cinctum;
import la.clamor.Envelope;
import la.clamor.Positio;
import la.clamor.Punctum;
import la.clamor.forma.CadentesFormae;
import la.clamor.forma.Chorus;
import la.clamor.forma.Compressor;
import la.clamor.forma.Equalizer;
import la.clamor.forma.FormaNominata;
import la.clamor.forma.IIRFilter;
import la.clamor.forma.VCA;
import la.clamor.forma.Wah;
import la.clamor.opus.Humanizer;
import la.clamor.opus.Mensa;
import la.clamor.opus.Taleae;
import la.clamor.referibile.ModEnv;
import la.clamor.referibile.OscillatioQuad;
import la.clamor.referibile.Referens;
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
        ponoInstrument(0, new Punctum(1), new Cinctum(), new ArchiveLudior("mu500r", "drum_basic", 10), new CadentesFormae(
                new Compressor(new Punctum(0.2), new Punctum(0.1)),
                new Equalizer(1., 0., 0., 0., 0., 0., 0., -0.5, -1., -1.)
        ));
        ponoInstrument(1, new Punctum(0.5, 0.5), new Cinctum(), new ArchiveLudior("mu500r", "drum_basic", 10), new CadentesFormae(
                new Compressor(new Punctum(0.2), new Punctum(0.1)),
                new IIRFilter(500, 4000, true)));

        ponoInstrument(2, new Punctum(0.5, 0.5), new Cinctum(), new ArchiveLudior("mu500r", "drum_basic", 10), new CadentesFormae(
                new Compressor(new Punctum(0.2), new Punctum(0.1)),
                new Equalizer(-1., -1., -1., 0., 0., 0., 0., -0.5, -1., -1.)
        ));
        ponoInstrument(10, new Punctum(2), new Cinctum(true, new Punctum(1), new Punctum(0.5)), new ArchiveLudior("mu500r", "mtgt", 50), new CadentesFormae(
                new Compressor(new Punctum(0.8), new Punctum(0.1)),
                new FormaNominata("wah0", new Wah(new Punctum(1200))),
                new VCA(new Envelope<>(new Punctum(6))),
                //new Compressor(new Punctum(0.1), new Punctum(0.1)),
                //new IIRFilter(2000, true),
                new Equalizer(-1., 0., 0., 1., -0, 0, -1., -1., -1., -1.),
                //new Distortion(new Punctum(0.5), new Punctum(1.3))
                //new Compressor(new Punctum(0.5), new Punctum(0.0))//,
                new Chorus(new Punctum(0.3), new Punctum(tempo / 60. * 2.), new Punctum(0.5), new Punctum(0.5))
        ));
        ponoInstrument(11, new Punctum(2.5), new Cinctum(true, new Punctum(0.5), new Punctum(1)), new ArchiveLudior("mu500r", "mtgt", 50), new CadentesFormae(
                new Compressor(new Punctum(0.7), new Punctum(0.05)),
                new FormaNominata("wah1", new Wah(new Punctum(1200))),
                new Compressor(new Punctum(0.5), new Punctum(0.1)),
                //new IIRFilter(2000, true),
                new Equalizer(-1., 0., 0., 0., 0, 1, 1., 0., 0., -1.),
                //new Distortion(new Punctum(0.5), new Punctum(1.3))
                //new Compressor(new Punctum(0.5), new Punctum(0.0))//,
                new Chorus(new Punctum(0.3), new Punctum(tempo / 60. * 2.), new Punctum(0.5), new Punctum(0.5))
        ));
        ponoInstrument(3, new Punctum(0.2), new Cinctum(true, new Punctum(1), new Punctum(1)), new ArchiveLudior("mu500r", "bass006433", 10), new CadentesFormae(
                new Compressor(new Punctum(0.7), new Punctum(0.1)),
                new Equalizer(2., 1., 1., 0., 0, 0, 0., -0.5, -1., -1.)
        ));
        ponoInstrument(4, new Punctum(0.8), new Cinctum(), new ArchiveLudior("mu500r", "piano", 500), new CadentesFormae(
                new Equalizer(0., 1., 1., 0., -0.5, -0.5, -1., -1., 3., 3.)
        ));
        ponoInstrument(5, new Punctum(0.2), new Cinctum(true, new Punctum(1), new Punctum(1)),
                new Referens("test", new OscillatioQuad(),
                        new ModEnv(
                                new Envelope<>(new Punctum(1)),
                                new Envelope<>(new Punctum(3)),
                                new Envelope<>(new Punctum(0.008, -0.008))
                        ),
                        new ModEnv(new Envelope<>(new Punctum(1500), new Positio<>(1000, new Punctum(400)))),
                        new ModEnv(new Envelope<>(new Punctum(1), new Positio<>(1000, new Punctum(0.6)))), 500),
                new CadentesFormae(
                        new Equalizer(0., 1., 1., 0., -0.5, -0.5, -1., -1., 1., 1.),
                        //new Delay(new Punctum(Delay.temps(90, 0.5)), new Punctum(1), new Punctum(0.5)),
                        new IIRFilter(3000, true)
                )
        );
        ponoNoInstrument(20, new Punctum(0.8), new Cinctum(true, new Punctum(1), new Punctum(0.2)), new CadentesFormae(
            new Chorus(new Punctum(0.1), new Punctum(4), new Punctum(0.8), new Punctum(0.3))));
        ponoNoInstrument(21, new Punctum(0.8), new Cinctum(true, new Punctum(0.2), new Punctum(1)), new CadentesFormae(
            new Chorus(new Punctum(0.1), new Punctum(4), new Punctum(0.8), new Punctum(0.3))));
        ponoHumanizer(new Humanizer()
                .pono(0, 0, 1).pono(0.25, 0.02, 0.5).pono(0.5, 0, 0.8).pono(0.75, 0.02, 0.5)
                .ponoRandomVelocitas(0, 0.5).ponoRandomRepenso(-0.01, 0.01), 0, 1, 2, 3, 4, 5, 10, 11);
        //ponoHumanizer(new Humanizer()
        //        .pono(0, 0, 1).pono(0.25, 0.01, 0.85).pono(0.5, 0, 0.9).pono(0.75, 0.01, 0.85)
        //        .ponoRandomVelocitas(0, 0.5), 4);

        new Parte024AC(this, new Integers(20, 21), 8, 8, 7).creo();
        new Parte024AD(this, new Integers(0, 1, 2), 8, 8, 7).creo();
        new Parte024IB(this, new Integers(3), 0, 16, 15).creo();
        new Parte024IG(this, new Integers(10, 11), 0, 16, 15).creo();
        
        new Parte024AD(this, new Integers(0, 1, 2), 16, 8, null).creo();
        new Parte024AB(this, new Integers(3), 16, 8).creo();
        new Parte024AG(this, new Integers(10, 11), 16, 8).creo();
        new Parte024AM(this, new Integers(4), 16, 8).creo();
        new Parte024AM(this, new Integers(5), 16, 8).creo();
        
        new Parte024AC(this, new Integers(20, 21), 24, 4, null).creo();
        new Parte024AD(this, new Integers(0, 1, 2), 24, 4, null).creo();
        new Parte024IB(this, new Integers(3), 24, 4, null).creo();
        new Parte024IG(this, new Integers(10, 11), 24, 4, null).creo();

        new Parte024AD(this, new Integers(0, 1, 2), 28, 8, null).creo();
        new Parte024AB(this, new Integers(3), 28, 8).creo();
        new Parte024AG(this, new Integers(10, 11), 28, 8).creo();
        new Parte024AM(this, new Integers(4), 28, 8).creo();
        new Parte024AM(this, new Integers(5), 28, 8).creo();

        new Parte024AC(this, new Integers(20, 21), 36, 16, null).creo();
        new Parte024AD(this, new Integers(0, 1, 2), 36, 16, null).creo();
        new Parte024IB(this, new Integers(3), 36, 16, null).creo();
        new Parte024IG(this, new Integers(10, 11), 36, 16, null).creo();

        ponoMasterLevel(0, 0, new Punctum(0));
        ponoMasterLevel(4, 0, new Punctum(1));
        ponoMasterLevel(44, 0, new Punctum(1));
        ponoMasterLevel(52, 0, new Punctum(0));

    }

    @Override
    public void initio() {
    }

    @Override
    protected void anteFacio() {
    }

}
