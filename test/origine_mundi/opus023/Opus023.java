/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package origine_mundi.opus023;

import com.mina.util.Integers;
import java.util.ArrayList;
import la.clamor.Cinctum;
import la.clamor.Envelope;
import la.clamor.Positio;
import la.clamor.Punctum;
import la.clamor.forma.AutoLimitter;
import la.clamor.forma.CadentesFormae;
import la.clamor.forma.Chorus;
import la.clamor.forma.Compressor;
import la.clamor.forma.Delay;
import la.clamor.forma.Equalizer;
import la.clamor.forma.FormaNominata;
import la.clamor.forma.IIRFilter;
import la.clamor.forma.PressureMeter;
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
 * @author mina
 */
public class Opus023 extends Mensa {

    public Opus023() {
        super(true, true, false);
    }

    @Override
    protected void anteFacio() {
        //super.ponoTaleamAb(25, 0);
        //super.ponoTaleamAd(35, 0);
        Punctum volume0 = new Punctum(3);//1
        Punctum volume1 = new Punctum(0.15);
        Punctum volume31 = new Punctum(0.3);
        Punctum volume2 = new Punctum(1.7);
        ponoInstrument(0, volume0, new Cinctum(), new ArchiveLudior("mu500r", "drum_basic", 10), new CadentesFormae(
                //new PressureMeter("point1"),
                new Equalizer(0., 0., 0., 0., 0., 0., 0., -0.5, -1., -1.),
                new PressureMeter("point1"),
                //new Limitter(new Punctum(1.6)),
                new AutoLimitter(),
                new Compressor(new Punctum(0.2), new Punctum(0.1))
        //new Compressor(new Punctum(0.5), new Punctum(0.3))//,
        //new PressureMeter("point1")
        ));
        ponoInstrument(1, volume1, new Cinctum(), new ArchiveLudior("mu500r", "drum_basic", 10), new CadentesFormae(
                new IIRFilter(500, 7000, true),
                //new Limitter(new Punctum(0.55)),
                new AutoLimitter(),
                new Compressor(new Punctum(0.2), new Punctum(0.1)),
                new Compressor(new Punctum(0.5), new Punctum(0.3))
        ));
        ponoInstrument(31, volume31, new Cinctum(), new ArchiveLudior("mu500r", "drum_basic", 10), new CadentesFormae(
                new IIRFilter(500, 7000, true),
                //new Limitter(new Punctum(0.55)),
                new AutoLimitter(),
                new Compressor(new Punctum(0.2), new Punctum(0.1)),
                new Compressor(new Punctum(0.5), new Punctum(0.3))
        ));

        ponoInstrument(2, volume2, new Cinctum(), new ArchiveLudior("mu500r", "drum_basic", 10), new CadentesFormae(
                new Equalizer(-1., -1., -1., 0., 0., 0., 0., -0.5, -1., -1.),
                //new Limitter(new Punctum(1.7)),
                new AutoLimitter(),
                new Compressor(new Punctum(0.2), new Punctum(0.1)),
                new Compressor(new Punctum(0.5), new Punctum(0.3))//,
        //new PressureMeter("point1")
        ));
        Punctum volume3 = new Punctum(1.1);
        Punctum volume5 = new Punctum(0.35, -0.35);
        ponoInstrument(3, volume3, new Cinctum(true, new Punctum(1), new Punctum(1)), new ArchiveLudior("mu500r", "bass006433", 10), new CadentesFormae(
                new Equalizer(-1., -0.5, 1., 0., 0, 0, 0., -0.5, -1., -1.),
                //new PressureMeter("bass0"),
                //new Limitter(new Punctum(1.3)),
                new AutoLimitter(),
                new Compressor(new Punctum(0.6), new Punctum(0.1)),
                new Compressor(new Punctum(0.5), new Punctum(0.3)),
                new PressureMeter(null)
        ));
        ponoInstrument(4, new Punctum(2.75), new Cinctum(), new ArchiveLudior("mu500r", "piano", 200), new CadentesFormae(
                new Equalizer(0., 1., 1., 0., -0.5, -0.5, -1., -1., 3., 3.),
                //new Limitter(new Punctum(1.45)),
                new AutoLimitter(),
                new Compressor(new Punctum(0.5), new Punctum(0.5)),
                new Delay(new Punctum(Delay.temps(tempo, 0.5)), new Punctum(2), new Punctum(0.3, -0.3)),
                new PressureMeter("piano0")
        ));
        ponoInstrument(5, volume5, new Cinctum(true, new Punctum(1), new Punctum(1)),
                new Referens("test", new OscillatioQuad(),
                        new ModEnv(
                                new Envelope<>(new Punctum(1)),
                                new Envelope<>(new Punctum(3)),
                                new Envelope<>(new Punctum(0.008, -0.008))
                        ),
                        new ModEnv(new Envelope<>(new Punctum(1500), new Positio<>(1000, new Punctum(400)))),
                        new ModEnv(new Envelope<>(new Punctum(0),
                                new Positio<>(10, new Punctum(1)),
                                new Positio<>(200, new Punctum(1)))), 300),
                new CadentesFormae(
                        new IIRFilter(3000, true),
                        new Equalizer(-1., 0., 1., 0., -0.5, -0.5, -1., -1., -1., -1.),
                        new PressureMeter("synth"),
                        //new Limitter(new Punctum(1.95)),
                        //new Compressor(new Punctum(0.7), new Punctum(0.5)),
                        new PressureMeter(null)
                //new Delay(new Punctum(Delay.temps(90, 0.5)), new Punctum(2), new Punctum(0.5, -0.5)),
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

        /*ponoInstrument(10, new Punctum(0.5), new Cinctum(), new ArchiveLudior("mu500r", "piano", 50), new CadentesFormae(
                new Chorus(new Punctum(0.1), new Punctum(10), new Punctum(1, 0), new Punctum(0, 1)),
                new Delay(new Punctum(Delay.temps(120, 0.75)), new Punctum(3), new Punctum(0.5, -0.5)),
                new IIRFilter(500, 7000, true)));*/
        Punctum volume20 = new Punctum(0.28);
        Punctum volume21 = new Punctum(0.28);
        ponoInstrument(20, volume20, new Cinctum(true, new Punctum(0.5), new Punctum(1)), new ArchiveLudior("mu500r", "mtgt", 50), new CadentesFormae(
                new FormaNominata("wah0", new Wah(new Punctum(1200))),
                new FormaNominata("vca0", new VCA(new Envelope(new Punctum(1)))),
                new Chorus(new Punctum(0.3), new Punctum(tempo / 60. * 2.), new Punctum(0.5), new Punctum(0.5)),
                //new Limitter(new Punctum(0.15)),
                new AutoLimitter(),
                new Compressor(new Punctum(0.2), new Punctum(0.1)),
                new Compressor(new Punctum(0.2), new Punctum(0.3)),
                new Compressor(new Punctum(0.2), new Punctum(0.5)),
                new PressureMeter(null)
        ));
        ponoInstrument(21, volume21, new Cinctum(true, new Punctum(1), new Punctum(0.5)), new ArchiveLudior("mu500r", "mtgt", 50), new CadentesFormae(
                new FormaNominata("wah1", new Wah(new Punctum(1200))),
                new FormaNominata("vca1", new VCA(new Envelope(new Punctum(1)))),
                new Chorus(new Punctum(0.3), new Punctum(tempo / 60. * 2.), new Punctum(0.5), new Punctum(0.5)),
                //new Limitter(new Punctum(0.15)),
                new AutoLimitter(),
                new Compressor(new Punctum(0.2), new Punctum(0.1)),
                new Compressor(new Punctum(0.2), new Punctum(0.3)),
                new Compressor(new Punctum(0.2), new Punctum(0.5)),
                //new Compressor(new Punctum(0.5), new Punctum(0.1)),
                //new Equalizer(-1., 0., 0., 0., 0, 1, 1., 0., 0., -1.),
                new PressureMeter("gt1")
        ));

        ponoHumanizer(new Humanizer()
                .pono(0, 0, 1).pono(0.25, 0.02, 0.5).pono(0.5, 0, 0.8).pono(0.75, 0.02, 0.5)
                .ponoRandomVelocitas(0, 0), 0, 1, 2, 3, 5);
        ponoHumanizer(new Humanizer()
                .pono(0, 0, 1).pono(0.25, 0.01, 0.85).pono(0.5, 0, 0.9).pono(0.75, 0.01, 0.85)
                .ponoRandomVelocitas(0, 0.5), 4);
        ponoHumanizer(new Humanizer()
                .pono(0, 0, 1).pono(0.25, 0.015, 0.5).pono(0.5, -0.05, 0.8).pono(0.75, 0.015, 0.5)
                .ponoRandomVelocitas(0, 0.5), 20);
        ponoHumanizer(new Humanizer()
                .pono(0, 0, 1).pono(0.25, 0.02, 0.5).pono(0.5, 0, 0.8).pono(0.75, 0.02, 0.5)
                .ponoRandomVelocitas(0, 0.5), 21);
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
        //new Parte023AD(this, new Integers(0, -1, -1), 0, 8).creo();
        //new Parte023AB(this, new Integers(3, -1), 0, 8, false).creo();
        //new Parte023AM(this, new Integers(4), 0, 8, false).creo();
        new Parte023ID(this, new Integers(0, 1, 2), 1, 8, false).creo();
        new Parte023IB(this, new Integers(3, 5), 1, 8, false).creo();
        new Parte023IG(this, new Integers(20, 21), 1, 8, false).creo();
        new Parte023AD(this, new Integers(0, 1, 2), 9, 16).creo();
        new Parte023AB(this, new Integers(3, 5), 9, 16, false).creo();
        new Parte023AM(this, new Integers(4), 9, 16, false).creo();
        
        new Parte023BD(this, new Integers(0, 31, 2), 25, 10).creo();
        new Parte023BB(this, new Integers(3, 5), 25, 10).creo();
        new Parte023BM(this, new Integers(4), 25, 10).creo();
        
        new Parte023AD(this, new Integers(0, 1, 2), 35, 8).creo();
        new Parte023AB(this, new Integers(3, 5), 35, 8, true).creo();
        new Parte023AM(this, new Integers(4), 35, 8, true).creo();
        new Parte023ID(this, new Integers(0, 1, 2), 43, 16, true).creo();
        new Parte023IB(this, new Integers(3, 5), 43, 16, true).creo();
        new Parte023IG(this, new Integers(20, 21), 43, 16, true).creo();
        ponoMasterLevel(51, 0, new Punctum(1));
        ponoMasterLevel(59, 0, new Punctum(0));

    }

    @Override
    public void initio() {
    }

    @Override
    protected void dominor() {
        ponoMasterFormas(
                new PressureMeter("master in"),
                //new Limitter(new Punctum(10.3)),
                new AutoLimitter(),
                new Chorus(new Punctum(0.3), new Punctum(tempo / 60. * 2.), new Punctum(0.9), new Punctum(0.1)),
                new Compressor(new Punctum(0.4), new Punctum(0.25)),
                //new Compressor(new Punctum(0.7), new Punctum(0.3)),
                new PressureMeter("master")//,
        );
    }

}
