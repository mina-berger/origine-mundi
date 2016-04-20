/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package origine_mundi.opus027;

import com.mina.util.Integers;
import java.util.ArrayList;
import la.clamor.Cinctum;
import la.clamor.Punctum;
import la.clamor.forma.AutoLimitter;
import la.clamor.forma.CadentesFormae;
import la.clamor.forma.Equalizer;
import la.clamor.forma.IIRFilter;
import la.clamor.opus.Humanizer;
import la.clamor.opus.Mensa;
import la.clamor.opus.Taleae;
import origine_mundi.archive.ArchiveLudior;

/**
 *
 * @author hiyamamina
 */
public class Opus027 extends Mensa {

    public Opus027() {
        super(true, false, false);
    }

    @Override
    protected void ponoComitis(ArrayList<Taleae.Comes> comites) {
    }

    double tempo = 80;

    @Override
    protected void ponoRapidi(ArrayList<Taleae.Rapidus> rapidi) {
        rapidi.add(new Taleae.Rapidus(0, 0, tempo, false));
    }

    @Override
    protected void creo() {
        //new Parte027AR(this, new Integers(5, 6), 0, 16).creo();
        new Parte027BR(this, new Integers(5, 6), 0, 9).creo();
    }

    @Override
    protected void dominor() {
    }

    @Override
    public void initio() {
    }

    @Override
    protected void anteFacio() {
        ponoInstrument(4, new Punctum(1), new Cinctum(), new ArchiveLudior("mu500r", "piano", 200), new CadentesFormae(
                new Equalizer(0., 1., 1., 0., -0.5, -0.5, -1., -1., 3., 3.),
                new AutoLimitter()
        //new Compressor(new Punctum(0.5), new Punctum(0.5)),
        //new Delay(new Punctum(Delay.temps(tempo, 0.5)), new Punctum(2), new Punctum(0.3, -0.3)),
        //new PressureMeter("piano0")
        ));
        ponoInstrument(5, new Punctum(1), new Cinctum(true, new Punctum(0.5), new Punctum(1)), new ArchiveLudior("mu500r", "nylgt", 80), new CadentesFormae(
                new IIRFilter(80, false),
                new Equalizer(-1., -1., 0., 0., 0., 0., -0.5, -0.5, -0.5, -0.5),
                new AutoLimitter()
        //new Compressor(new Punctum(0.7), new Punctum(0.1), new Punctum(1)),
        //new Limitter(new Punctum(0.8)),
        //new Compressor(new Punctum(0.5), new Punctum(0.3), new Punctum(1)),
        //new Limitter(new Punctum(0.7))
        ));
        ponoInstrument(6, new Punctum(1), new Cinctum(true, new Punctum(1), new Punctum(1)), new ArchiveLudior("mu500r", "bass006433", 10), new CadentesFormae(
                new Equalizer(-1., -0.5, 1., 0., 0, 0, 0., -0.5, -1., -1.),
                //new PressureMeter("bass0"),
                //new Limitter(new Punctum(1.3)),
                new AutoLimitter()//,
        //new Compressor(new Punctum(0.6), new Punctum(0.1)),
        //new Compressor(new Punctum(0.5), new Punctum(0.3)),
        //new PressureMeter(null)
        ));
        ponoHumanizer(new Humanizer()
                .pono(0, 0, 0.8).pono(0.25, -0.01, 0.8).pono(0.5, -0.02, 1.).pono(0.75, -0.03, 0.8)
                .ponoRandomRepenso(-0.01, 0.01).ponoRandomVelocitas(0, 0), 0, 1, 2, 3, 4, 5, 6);
    }

}
