/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package origine_mundi.opus23;

import com.mina.util.Integers;
import java.util.ArrayList;
import la.clamor.Cinctum;
import la.clamor.Envelope;
import la.clamor.Positio;
import la.clamor.Punctum;
import la.clamor.forma.CadentesFormae;
import la.clamor.forma.Chorus;
import la.clamor.forma.Compressor;
import la.clamor.forma.Delay;
import la.clamor.forma.Equalizer;
import la.clamor.forma.IIRFilter;
import la.clamor.opus.Humanizer;
import la.clamor.opus.Mensa;
import la.clamor.opus.Taleae;
import la.clamor.referibile.ModEnv;
import la.clamor.referibile.OscillatioQuad;
import la.clamor.referibile.Referens;
import origine_mundi.archive.ArchiveLudior;

/**
 *
 * @author mina
 */
public class Opus023 extends Mensa {

    public Opus023() {
        super(false, true);
    }

    @Override
    protected void anteFacio() {
        //super.ponoTaleamAb(25, 0);
        //super.ponoTaleamAd(35, 0);
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
        ponoInstrument(0, new Punctum(1), new Cinctum(), new ArchiveLudior("mu500r", "drum_basic", 10), new CadentesFormae(
                new Compressor(new Punctum(0.2), new Punctum(0.1)),
                new Equalizer(1., 0., 0., 0., 0., 0., 0., -0.5, -1., -1.)
        ));
        ponoInstrument(1, new Punctum(0.5, 0.5), new Cinctum(), new ArchiveLudior("mu500r", "drum_basic", 10), new CadentesFormae(
                new Compressor(new Punctum(0.2), new Punctum(0.1)),
                new IIRFilter(500, 7000, true)));

        ponoInstrument(2, new Punctum(0.5, 0.5), new Cinctum(), new ArchiveLudior("mu500r", "drum_basic", 10), new CadentesFormae(
                new Compressor(new Punctum(0.2), new Punctum(0.1)),
                new Equalizer(-1., -1., -1., 0., 0., 0., 0., -0.5, -1., -1.)
        ));
        ponoInstrument(3, new Punctum(0.85), new Cinctum(true, new Punctum(1), new Punctum(0.5)), new ArchiveLudior("mu500r", "bass006433", 10), new CadentesFormae(
                new Equalizer(2., 1., 1., 0., 0, 0, 0., -0.5, -1., -1.)
        ));
        ponoInstrument(4, new Punctum(1.5), new Cinctum(), new ArchiveLudior("mu500r", "piano", 500), new CadentesFormae(
                new Equalizer(0., 1., 1., 0., -0.5, -0.5, -1., -1., 3., 3.)
        ));
        ponoInstrument(5, new Punctum(0.4), new Cinctum(true, new Punctum(0.5), new Punctum(1)),
                new Referens("test", new OscillatioQuad(),
                        new ModEnv(
                                new Envelope<>(new Punctum(1)),
                                new Envelope<>(new Punctum(3)),
                                new Envelope<>(new Punctum(0.008, -0.008))
                        ),
                        new ModEnv(new Envelope<>(new Punctum(1500), new Positio<>(1000, new Punctum(400)))),
                        new ModEnv(new Envelope<>(new Punctum(1))), 500),
                new CadentesFormae(
                        new Equalizer(0., 1., 1., 0., -0.5, -0.5, -1., -1., 1., 1.),
                        new Delay(new Punctum(Delay.temps(90, 0.5)), new Punctum(3), new Punctum(0.5, -0.5)),
                        new IIRFilter(3000, true)
                )
        );
        ponoInstrument(6, new Punctum(1), new Cinctum(),
                new Referens("test", new OscillatioQuad(),
                        new ModEnv(
                                new Envelope<>(new Punctum(1)),
                                new Envelope<>(new Punctum(3)),
                                new Envelope<>(new Punctum(0.008, -0.008))
                        ),
                        new ModEnv(new Envelope<>(new Punctum(1500), new Positio<>(1000, new Punctum(400)))),
                        new ModEnv(new Envelope<>(new Punctum(1))), 500),
                new CadentesFormae(
                        new Equalizer(-1., -1., -0.5, 0., 2., 2., 0., -0.5, -1., -1.)
                //new IIRFilter(3000, true)
                )
        );

        ponoInstrument(10, new Punctum(0.5), new Cinctum(), new ArchiveLudior("mu500r", "piano", 500), new CadentesFormae(
                new Chorus(new Punctum(0.1), new Punctum(10), new Punctum(1, 0), new Punctum(0, 1)),
                new Delay(new Punctum(Delay.temps(120, 0.75)), new Punctum(3), new Punctum(0.5, -0.5)),
                new IIRFilter(500, 7000, true)));
        ponoHumanizer(new Humanizer()
                .pono(0, 0, 1).pono(0.25, 0.02, 0.5).pono(0.5, 0, 0.8).pono(0.75, 0.02, 0.5)
                .ponoRandomVelocitas(0, 0.5), 0, 1, 2, 3, 5);
        ponoHumanizer(new Humanizer()
                .pono(0, 0, 1).pono(0.25, 0.01, 0.85).pono(0.5, 0, 0.9).pono(0.75, 0.01, 0.85)
                .ponoRandomVelocitas(0, 0.5), 4);
        new ParteID(this, new Integers(0, 1, 2), 1, 8, false).creo();
        new ParteIB(this, new Integers(3, 5), 1, 8, false).creo();
        new ParteAD(this, new Integers(0, 1, 2), 9, 16).creo();
        new ParteAB(this, new Integers(3, 5), 9, 16, false).creo();
        new ParteAM(this, new Integers(4), 9, 16, false).creo();
        new ParteBD(this, new Integers(0, 1, 2), 25, 10).creo();
        new ParteBB(this, new Integers(3, 5), 25, 10).creo();
        new ParteBM(this, new Integers(4), 25, 10).creo();
        new ParteAD(this, new Integers(0, 1, 2), 35, 8).creo();
        new ParteAB(this, new Integers(3, 5), 35, 8, true).creo();
        new ParteAM(this, new Integers(4), 35, 8, true).creo();
        new ParteID(this, new Integers(0, 1, 2), 43, 16, true).creo();
        new ParteIB(this, new Integers(3, 5), 43, 16, true).creo();
        ponoMasterLevel(51, 0, new Punctum(1));
        ponoMasterLevel(59, 0, new Punctum(0));

    }

    @Override
    public void initio() {
    }

}
