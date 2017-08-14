/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package origine_mundi.opus026;

import com.mina.util.Integers;
import java.util.ArrayList;
import la.clamor.Cinctum;
import la.clamor.Punctum;
import la.clamor.forma.AutoLimitter;
import la.clamor.forma.CadentesFormae;
import la.clamor.forma.Chorus;
import la.clamor.forma.Compressor;
import la.clamor.forma.Delay;
import la.clamor.forma.PressureMeter;
import la.clamor.opus.Humanizer;
import la.clamor.opus.Mensa;
import la.clamor.opus.Taleae;
import origine_mundi.archive.ArchiveLudior;

/**
 *
 * @author hiyamamina
 */
public class Opus026 extends Mensa {

    public Opus026() {
        super(true, true, false);
    }

    @Override
    protected void ponoComitis(ArrayList<Taleae.Comes> comites) {
    }
    double tempo = 100;

    @Override
    protected void ponoRapidi(ArrayList<Taleae.Rapidus> rapidi) {
        rapidi.add(new Taleae.Rapidus(0, 0, tempo, false));
    }

    @Override
    protected void creo() {
        new Parte026AD(this, new Integers(0, 1, 2), 0, 16, 15).creo();
        new Parte026AI(this, new Integers(5, 6, 7, 8, 9), 0, 16, 15).creo();
        /*
        new Parte026AD(this, new Integers(0, 1, 2), 0, 24).creo();
        new Parte026AI(this, new Integers(5, 6, 7), 0, 24).creo();
        ponoMasterLevel(20, 0, new Punctum(1));
        ponoMasterLevel(24, 0, new Punctum(0));
         */

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
        ponoInstrument(0, new Punctum(0.8), new Cinctum(true, new Punctum(1), new Punctum(1)), new ArchiveLudior("record", "voixdrum", 10), new CadentesFormae(
                new AutoLimitter(),
                new Compressor(new Punctum(0.5), new Punctum(0.1))//,
        ));
        ponoInstrument(1, new Punctum(0.5), new Cinctum(true, new Punctum(1), new Punctum(0.5)), new ArchiveLudior("record", "voixdrum", 10), new CadentesFormae(
                new AutoLimitter(),
                new Compressor(new Punctum(0.5), new Punctum(0.1))//,
        ));
        ponoInstrument(2, new Punctum(0.3), new Cinctum(true, new Punctum(0.5), new Punctum(1)), new ArchiveLudior("record", "voixdrum", 10), new CadentesFormae(
                new AutoLimitter(),
                new Compressor(new Punctum(0.5), new Punctum(0.1))//,
        ));
        ponoInstrument(5, new Punctum(0.8), new Cinctum(true, new Punctum(1), new Punctum(0.2)), new ArchiveLudior("record", "voixdrum", 10), new CadentesFormae(
                new AutoLimitter(),
                new Compressor(new Punctum(0.5), new Punctum(0.3)),
                new Delay(new Punctum(Delay.temps(tempo, 0.75)), new Punctum(3), new Punctum(0.5))//,
        ));
        ponoInstrument(6, new Punctum(0.8), new Cinctum(true, new Punctum(0.2), new Punctum(1)), new ArchiveLudior("record", "voixdrum", 10), new CadentesFormae(
                new AutoLimitter(),
                new Compressor(new Punctum(0.5), new Punctum(0.3)),
                new Delay(new Punctum(Delay.temps(tempo, 0.75)), new Punctum(3), new Punctum(0.5))//,
        ));
        ponoInstrument(7, new Punctum(1), new Cinctum(true, new Punctum(1), new Punctum(1)), new ArchiveLudior("record", "voixdrum", 10), new CadentesFormae( //new Delay(new Punctum(Delay.temps(tempo, 0.75)), new Punctum(3), new Punctum(0.5))//,
                ));
        ponoInstrument(8, new Punctum(0.8), new Cinctum(true, new Punctum(1), new Punctum(1)), new ArchiveLudior("record", "voixdrum", 10), new CadentesFormae(
                new AutoLimitter(),
                new Compressor(new Punctum(0.5), new Punctum(0.3)),
                new Delay(new Punctum(Delay.temps(tempo, 3.5)), new Punctum(5), new Punctum(-0.8, 0.8)),
                new Delay(new Punctum(Delay.temps(tempo, 2.)), new Punctum(10), new Punctum(0.7, -0.7)),
                new Delay(new Punctum(Delay.temps(tempo, 0.75)), new Punctum(10), new Punctum(0.5, 0.5))
        ));
        ponoInstrument(9, new Punctum(0.8), new Cinctum(true, new Punctum(1), new Punctum(1)), new ArchiveLudior("record", "voixdrum", 10), new CadentesFormae(
                new AutoLimitter(),
                new Compressor(new Punctum(0.5), new Punctum(0.3)),
                new Delay(new Punctum(Delay.temps(tempo, 7.125)), new Punctum(3), new Punctum(-0.95, 0.95)),
                new Delay(new Punctum(Delay.temps(tempo, 4.25)), new Punctum(3), new Punctum(-0.8, 0.8)),
                new Delay(new Punctum(Delay.temps(tempo, 2.25)), new Punctum(5), new Punctum(0.7, -0.7)),
                new Delay(new Punctum(Delay.temps(tempo, 0.70)), new Punctum(20), new Punctum(0.5, 0.5))
        ));
        ponoHumanizer(new Humanizer()
                .pono(0, 0, 1).pono(0.25, 0.015, 0.5).pono(0.5, 0.02, 0.8).pono(0.75, 0.025, 0.5)
                .ponoRandomVelocitas(0, 0.).ponoRandomRepenso(-0.03, 0.03), 0, 1, 2);
    }

}
