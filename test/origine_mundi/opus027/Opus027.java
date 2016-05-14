/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package origine_mundi.opus027;

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
import la.clamor.forma.IIRFilter;
import la.clamor.forma.PressureMeter;
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
public class Opus027 extends Mensa {

    public Opus027() {
        super(true, true, false);
    }

    @Override
    protected void ponoComitis(ArrayList<Taleae.Comes> comites) {
    }

    double tempo = 78;

    @Override
    protected void ponoRapidi(ArrayList<Taleae.Rapidus> rapidi) {
        rapidi.add(new Taleae.Rapidus(0, 0, tempo, false));
    }

    @Override
    protected void creo() {
        /*new Parte027BM(this, new Integers(7), 0, 9, false).creo();
        new Parte027BC(this, new Integers(10, 11), 0, 9).creo();
        new Parte027BR(this, new Integers(5, 6), 0, 9).creo();
        new Parte027BP(this, new Integers(8), 0, 9).creo();
        new Parte027BD(this, new Integers(0, 21, 2), 0, 9).creo();
        */
        new Parte027PD(this, new Integers(0, 1, 2), 0, 1).creo();
        
        new Parte027IR(this, new Integers(5, 6), 1, 4, false).creo();
        new Parte027ID(this, new Integers(0, 1, 2), 1, 4).creo();

        new Parte027AM(this, new Integers(4), 5, 16, false).creo();
        new Parte027AR(this, new Integers(5, 6), 5, 16, false).creo();
        new Parte027AD(this, new Integers(0, 1, 2), 5, 16).creo();

        new Parte027BM(this, new Integers(4), 21, 9, true).creo();
        new Parte027BC(this, new Integers(10, 11), 21, 9).creo();
        new Parte027BR(this, new Integers(5, 6), 21, 9).creo();
        new Parte027BD(this, new Integers(0, 1, 2, 21), 21, 9).creo();

        new Parte027AM(this, new Integers(4), 30, 9, true).creo();
        new Parte027AR(this, new Integers(5, 6), 30, 9, true).creo();
        new Parte027AD(this, new Integers(0, 1, 2), 30, 9).creo();

        new Parte027IR(this, new Integers(5, 6), 39, 1, true).creo();
        new Parte027ID(this, new Integers(0, 1, 2), 39, 1).creo();

        new Parte027BM(this, new Integers(7), 40, 9, false).creo();
        new Parte027BC(this, new Integers(10, 11), 40, 9).creo();
        new Parte027BR(this, new Integers(5, 6), 40, 9).creo();
        new Parte027BP(this, new Integers(8), 40, 9).creo();
        new Parte027BD(this, new Integers(0, 1, 2, 21), 40, 9).creo();

        new Parte027AM(this, new Integers(4), 49, 9, true).creo();
        new Parte027AR(this, new Integers(5, 6), 49, 9, true).creo();
        new Parte027AD(this, new Integers(0, 1, 2), 49, 9).creo();

        new Parte027IR(this, new Integers(5, 6), 58, 4, true).creo();
        new Parte027IC(this, new Integers(10, 11), 58, 4, true).creo();
        new Parte027ID(this, new Integers(0, 1, 2), 58, 4, 3).creo();
        
    }

    @Override
    protected void dominor() {
        ponoMasterFormas(
                new AutoLimitter(),
                new Chorus(new Punctum(0.3), new Punctum(tempo / 60. * 2.), new Punctum(0.9), new Punctum(0.1)),
                new Compressor(new Punctum(0.7), new Punctum(0.3)),
                new PressureMeter("master")//,
        );
    }

    @Override
    public void initio() {
    }

    @Override
    protected void anteFacio() {
        ponoInstrument(0, new Punctum(1.1), new Cinctum(), new ArchiveLudior("mu500r", "drum_basic", 10), new CadentesFormae(
                new IIRFilter(50, 1500, true),
                //new Equalizer(0., 0., 0., 0., 0., 0., 0., -0.5, -1., -1.),
                //new PressureMeter("bd"),
                new AutoLimitter(),
                new Compressor(new Punctum(0.2), new Punctum(0.1)),
                new Compressor(new Punctum(0.5), new Punctum(0.3)),
                new PressureMeter(null)
        ));
        ponoInstrument(1, new Punctum(0.1), new Cinctum(true, new Punctum(0.5), new Punctum(1)), new ArchiveLudior("mu500r", "drum_basic", 10), new CadentesFormae(
                new IIRFilter(500, 4000, true),
                new AutoLimitter(),
                new Compressor(new Punctum(0.2), new Punctum(0.1)),
                new Compressor(new Punctum(0.5), new Punctum(0.3))
        ));
        ponoInstrument(21, new Punctum(0.8), new Cinctum(true, new Punctum(0.5), new Punctum(1)), new ArchiveLudior("mu500r", "drum_basic", 10), new CadentesFormae(
                new IIRFilter(500, 4000, true),
                new AutoLimitter(),
                //new Compressor(new Punctum(0.2), new Punctum(0.1)),
                new Compressor(new Punctum(0.5), new Punctum(0.3))
        ));
        ponoInstrument(2, new Punctum(0.8), new Cinctum(true, new Punctum(1), new Punctum(0.5)), new ArchiveLudior("mu500r", "drum_basic", 10), new CadentesFormae(
                new Equalizer(-1., -1., -1., 0., 0., 0., 0., -0.5, -1., -1.),
                //new Equalizer(0., 0., 0., 0., 0., 0., 0., -0.5, -1., -1.),
                //new PressureMeter("bd"),
                new AutoLimitter(),
                //new Compressor(new Punctum(0.2), new Punctum(0.1)),
                new Compressor(new Punctum(0.5), new Punctum(0.3))
        ));
        ponoInstrument(4, new Punctum(2), new Cinctum(true, new Punctum(1), new Punctum(0.5)), new ArchiveLudior("mu500r", "piano", 200), new CadentesFormae(
                new Equalizer(0., 1., 1., 0., -0.5, -0.5, -1., -1., 3., 3.),
                new AutoLimitter(),
                new Compressor(new Punctum(0.5), new Punctum(0.5)),
                new Delay(new Punctum(Delay.temps(tempo, 0.25)), new Punctum(2), new Punctum(0.3, -0.3))
        //new PressureMeter("piano0")
        ));
        ponoInstrument(8, new Punctum(0.7), new Cinctum(true, new Punctum(1), new Punctum(0.5)), new ArchiveLudior("mu500r", "piano", 200), new CadentesFormae(
                new Equalizer(0., 1., 1., 0., -0.5, -0.5, -1., -1., 3., 3.),
                new AutoLimitter(),
                new Compressor(new Punctum(0.5), new Punctum(0.5)),
                new Delay(new Punctum(Delay.temps(tempo, 0.5)), new Punctum(2), new Punctum(0.3, -0.3))
        //new PressureMeter("piano0")
        ));
        ponoInstrument(5, new Punctum(0.6), new Cinctum(true, new Punctum(0.5), new Punctum(1)), new ArchiveLudior("mu500r", "nylgt", 80), new CadentesFormae(
                new IIRFilter(80, 3000, true),
                new Equalizer(-1., -1., 0., 0., 0., 0., -0.5, -0.5, -0.5, -0.5),
                new AutoLimitter(),
                new Compressor(new Punctum(0.7), new Punctum(0.1), new Punctum(1))
        //new Limitter(new Punctum(0.8)),
        //new Compressor(new Punctum(0.5), new Punctum(0.3), new Punctum(1)),
        //new Limitter(new Punctum(0.7))
        ));
        ponoInstrument(6, new Punctum(0.55), new Cinctum(true, new Punctum(1), new Punctum(1)), new ArchiveLudior("mu500r", "bass006433", 10), new CadentesFormae(
                new Equalizer(-1., -0.5, 1., 0., 0, 0, 0., -0.5, -1., -1.),
                //new PressureMeter("bass0"),
                //new Limitter(new Punctum(1.3)),
                new AutoLimitter(),
                new Compressor(new Punctum(0.6), new Punctum(0.1)),
                new Compressor(new Punctum(0.5), new Punctum(0.3)) //new PressureMeter(null)
        ));

        ponoInstrument(7, new Punctum(1.2), new Cinctum(),
                new Referens("test", new OscillatioQuad(),
                        new ModEnv(
                                new Envelope<>(new Punctum(1)),
                                new Envelope<>(new Punctum(3)),
                                new Envelope<>(new Punctum(0.008, -0.008))
                        ),
                        new ModEnv(new Envelope<>(new Punctum(6000), new Positio<>(1000, new Punctum(1000)))),
                        new ModEnv(new Envelope<>(new Punctum(1))), 500),
                new CadentesFormae(
                        new Equalizer(-1., -1., -0.5, 0., 2., 2., 0., -0.5, -1., -1.),
                        new AutoLimitter(),
                        new Compressor(new Punctum(0.5), new Punctum(0.3))
                //new IIRFilter(3000, true)
                )
        );

        ponoNoInstrument(10, new Punctum(0.2), new Cinctum(true, new Punctum(1), new Punctum(0.2)), new CadentesFormae(
                new AutoLimitter(),
                new Compressor(new Punctum(0.5), new Punctum(0.3)),
                new Chorus(new Punctum(0.1), new Punctum(4), new Punctum(0.8), new Punctum(0.3))));
        ponoNoInstrument(11, new Punctum(0.2), new Cinctum(true, new Punctum(0.2), new Punctum(1)), new CadentesFormae(
                new AutoLimitter(),
                new Compressor(new Punctum(0.5), new Punctum(0.3)),
                new Chorus(new Punctum(0.1), new Punctum(4), new Punctum(0.8), new Punctum(0.3))));

        ponoHumanizer(new Humanizer()
                .pono(0, 0, 0.8).pono(0.25, -0.01, 0.8).pono(0.5, -0.02, 1.).pono(0.75, -0.03, 0.8)
                .ponoRandomRepenso(-0.01, 0.01).ponoRandomVelocitas(0, 0), 0, 1, 21, 2, 4, 5, 6);
        ponoHumanizer(new Humanizer()
                .pono(0, 0, 0.8).pono(0.25, 0.01, 1).pono(0.5, 0.0, 0.8).pono(0.75, 0.01, 1)
                .ponoRandomRepenso(-0.01, 0.01).ponoRandomVelocitas(-0.1, 0), 4, 7, 8);
    }

}
