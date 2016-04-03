/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package origine_mundi.opus;

import com.mina.util.Doubles;
import java.util.ArrayList;
import la.clamor.Aestima;
import la.clamor.Cinctum;
import la.clamor.Envelope;
import la.clamor.Positio;
import la.clamor.Punctum;
import la.clamor.Temperamentum;
import la.clamor.Vel;
import la.clamor.forma.VCA;
import la.clamor.forma.CadentesFormae;
import la.clamor.forma.Chorus;
import la.clamor.forma.Delay;
import la.clamor.forma.Limitter;
import la.clamor.forma.IIRFilter;
import la.clamor.opus.Humanizer;
import la.clamor.opus.Mensa;
import la.clamor.opus.Taleae;
import la.clamor.referibile.ModEnv;
import la.clamor.referibile.OscillatioFrag;
import la.clamor.referibile.OscillatioPulse;
import la.clamor.referibile.Referibile;
import la.clamor.voix.Formant;
import la.clamor.voix.Vowel;
import origine_mundi.archive.ArchiveLudior;

/**
 *
 * @author mina
 */
public class Opus022 extends Mensa {

    public Opus022() {
        super(true, false);
    }

    @Override
    protected void ponoComitis(ArrayList<Taleae.Comes> comites) {
    }

    @Override
    protected void ponoRapidi(ArrayList<Taleae.Rapidus> rapidi) {
        rapidi.add(new Taleae.Rapidus(0, 0, 120, false));
    }

    @Override
    protected void creo() {
        ponoInstrument(0, new Punctum(1), new Cinctum(), new ArchiveLudior("mu500r", "drum_basic", 10), new CadentesFormae(
            new Limitter(new Punctum(0.8), new Punctum(2), new Punctum(1.2)),
            new IIRFilter(3000, true)
        ));
        ponoInstrument(1, new Punctum(0.5, 0.5), new Cinctum(), new ArchiveLudior("mu500r", "drum_basic", 10), new CadentesFormae(
            new IIRFilter(500, 7000, true)));
        ponoNoInstrument(2, new Punctum(0.4, 0.4), new Cinctum(), new CadentesFormae(
            new Chorus(new Punctum(0.1), new Punctum(4), new Punctum(0.8), new Punctum(0.3))));
        ponoNoInstrument(3, new Punctum(1.3, 1.3), new Cinctum(), new CadentesFormae(
            new Chorus(new Punctum(0.1), new Punctum(4), new Punctum(0.8), new Punctum(0.3))));
        ponoInstrument(10, new Punctum(0.5), new Cinctum(), new ArchiveLudior("mu500r", "piano", 500), new CadentesFormae(
            new Chorus(new Punctum(0.1), new Punctum(10), new Punctum(1, 0), new Punctum(0, 1)),
            new Delay(new Punctum(Delay.temps(120, 0.75)), new Punctum(3), new Punctum(0.5, -0.5)),
            new IIRFilter(500, 7000, true)));
        ponoHumanizer(new Humanizer()
            .pono(0, 0, 1).pono(0.25, 0.02, 0.5).pono(0.5, 0, 0.8).pono(0.75, 0.02, 0.5)
            .ponoRandomVelocitas(0, 0.5), 0, 1);

        ludoA(0, 8);
        ludoB(8, 8, true);
        ludoA(16, 8);
        ludoB(24, 8, true);
        ludoA(32, 8);
        ludoB(32, 8, false);
        ludoA(40, 8);
        ludoB(40, 8, false);
        ponoMasterLevel(42, 0, new Punctum(1));
        ponoMasterLevel(48, 0, new Punctum(0));
        //ludoA(16, 8);
    }

    public void ludoB(int talea, int length, boolean hat) {
        double[][] notess = new double[][]{
            new double[]{48, 60, 64, 67, 71},
            new double[]{48, 60, 64, 67, 71},
            new double[]{55, 62, 65, 69, 72},
            new double[]{55, 62, 65, 69, 72}
        };

        
        for (int i = talea; i < talea + length; i++) {
            double[] notes = notess[i % notess.length];
            if(hat){
                for (int j = 0; j < 4; j++) {
                    ludo(1, i, j, 0.25, 42, new Vel(1));
                    ludo(1, i, j + 0.25, 0.25, 42, new Vel(0.5));
                    ludo(1, i, j + 0.5, 0.5, 46, new Vel(1));
                }
            }
            ludo(10, i, 0.5, 1., new Doubles(
                notes[0], notes[1], notes[2],notes[3]), new Vel(0.8));
            double[] a_notes = new double[]{notes[2], notes[3], notes[4]};
            for(int j = 0;j < a_notes.length;j++){
                sono(3, i, 0.5,
                    CadentesFormae.capioLegibilis(new Referibile(new OscillatioPulse(false),
                        new Envelope<>(new Punctum(Temperamentum.instance.capioHZ(a_notes[j]))), diu(i, 0.55, i, 3.75)),
                        new Formant(new Envelope<>(new Aestima(100)),
                            new Envelope<>(Vowel.A,
                                new Positio<>(diu(i, 0.5, i, 1.2), Vowel.A),
                                new Positio<>(diu(i, 0.5, i, 1.25), Vowel.I),
                                new Positio<>(diu(i, 0.5, i, 1.95), Vowel.I),
                                new Positio<>(diu(i, 0.5, i, 2.0), Vowel.O),
                                new Positio<>(diu(i, 0.5, i, 2.95), Vowel.O),
                                new Positio<>(diu(i, 0.5, i, 3.0), Vowel.A))),
                        new IIRFilter(1000, 1800, true),
                        new VCA(new ModEnv(new Envelope<>(new Punctum(0),
                            new Positio(diu(i, 0.5, i, 0.55), new Punctum(1)),
                            new Positio(diu(i, 0.5, i, 3.7), new Punctum(1)),
                            new Positio(diu(i, 0.5, i, 3.75), new Punctum(0))
                            ),
                            new Envelope<>(new Punctum(1000. / diu(i, 0.5, i, 0.75))),
                            new Envelope<>(new Punctum(0.1))
                        ))));
            }
        }

    }

    public void ludoA(int talea, int length) {
        for (int i = talea; i < talea + length; i++) {
            ludo(0, i, 0.0, 0.5, 36, new Vel(1));
            ludo(0, i, 1.0, 0.5, 36, new Vel(1));
            ludo(0, i, 2.0, 0.5, 36, new Vel(1));
            ludo(0, i, 3.0, 0.5, 36, new Vel(1));
            for (int j = 0; j < 4; j++) {
                ludo(1, i, j, 0.25, 42, new Vel(1));
                ludo(1, i, j + 0.25, 0.25, 42, new Vel(0.5));
                ludo(1, i, j + 0.5, 0.5, 46, new Vel(1));
            }
            sono(2, i, 0.55,
                CadentesFormae.capioLegibilis(new Referibile(new OscillatioFrag(false),
                    new Envelope<>(new Punctum(100)), diu(i, 0.55, i, 3.75)),
                    new Formant(new Envelope<>(new Aestima(100)),
                        new Envelope<>(Vowel.A,
                            new Positio<>(diu(i, 0.5, i, 1.2), Vowel.A),
                            new Positio<>(diu(i, 0.5, i, 1.25), Vowel.I),
                            new Positio<>(diu(i, 0.5, i, 1.95), Vowel.I),
                            new Positio<>(diu(i, 0.5, i, 2.0), Vowel.O),
                            new Positio<>(diu(i, 0.5, i, 2.95), Vowel.O),
                            new Positio<>(diu(i, 0.5, i, 3.0), Vowel.A))),
                    new IIRFilter(1000, 1800, true),
                    new VCA(new ModEnv(new Envelope<>(new Punctum(0),
                        new Positio(diu(i, 0.5, i, 0.55), new Punctum(1)),
                        new Positio(diu(i, 0.5, i, 3.7), new Punctum(1)),
                        new Positio(diu(i, 0.5, i, 3.75), new Punctum(0))
                    ),
                        new Envelope<>(new Punctum(1000. / diu(i, 0.5, i, 0.75))),
                        new Envelope<>(new Punctum(0.1))
                    ))));
        }

    }

    public void ludoC(int talea, int length) {
        for (int i = talea; i < talea + length; i++) {
            ludo(0, i, 0.0, 0.5, 36, new Vel(1));
            ludo(0, i, 1.0, 0.5, 36, new Vel(1));
            ludo(0, i, 2.0, 0.5, 36, new Vel(1));
            ludo(0, i, 3.0, 0.5, 36, new Vel(1));
        }

    }
    @Override
    public void initio() {
    }

    @Override
    protected void anteFacio() {
    }

}
