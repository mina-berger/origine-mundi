/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package origine_mundi.opus027;

import com.mina.util.Doubles;
import com.mina.util.Integers;
import la.clamor.Aestima;
import la.clamor.Envelope;
import la.clamor.Positio;
import la.clamor.Punctum;
import la.clamor.Temperamentum;
import la.clamor.forma.CadentesFormae;
import la.clamor.forma.IIRFilter;
import la.clamor.forma.VCA;
import la.clamor.opus.Mensa;
import la.clamor.opus.ParteTaleae;
import la.clamor.referibile.ModEnv;
import la.clamor.referibile.OscillatioPulse;
import la.clamor.referibile.Referibile;
import la.clamor.voix.Formant;
import la.clamor.voix.Vowel;

/**
 *
 * @author hiyamamina
 */
public abstract class Parte027_C extends ParteTaleae {
    protected Vowel[] vowels = new Vowel[]{new Vowel(1.35, 1.35, 1.35, 1.35), new Vowel(1.45, 1.45, 1.45, 1.45)};

    public Parte027_C(Mensa mensa, Integers ids, int talea_primo, int length) {
        super(mensa, ids, talea_primo, length);
    }

    protected void sono(int index, int t, Vowel vowel, Doubles notes) {
        sono(index, t, vowel, notes, false);
    }
    protected void sono(int index, int t, Vowel vowel, Doubles notes, boolean end) {
        cambio(index);
        sono(t, 0.0,
                CadentesFormae.capioLegibilis(new Referibile(new OscillatioPulse(false),
                        new Envelope<>(new Punctum(Temperamentum.instance.capioHZ(notes.get(0))),
                                new Positio(diu(t, 0.0, t, 1.75), new Punctum(Temperamentum.instance.capioHZ(notes.get(0)))),
                                new Positio(diu(t, 0.0, t, 2.0), new Punctum(Temperamentum.instance.capioHZ(notes.get(1))))
                        ),
                        diu(t, 0.0, t, end?6:3.85)),
                        new Formant(new Envelope<>(new Aestima(100)),
                                new Envelope<>(Vowel.U.multiplico(vowel),
                                        new Positio(diu(t, 0., t, 1.75), Vowel.U.multiplico(vowel)),
                                        new Positio(diu(t, 0., t, 2.0), Vowel.A.multiplico(vowel)))),
                        new IIRFilter(1000, 1800, true),
                        new VCA(new ModEnv(
                                new Envelope<>(new Punctum(0),
                                        new Positio(diu(t, 0., t, 0.5), new Punctum(1)),
                                        new Positio(diu(t, 0., t, end?5.75:3.75), new Punctum(1)),
                                        new Positio(diu(t, 0., t, end?6:3.85), new Punctum(0))
                                ),
                                new Envelope<>(new Punctum(1000. / diu(t, 0.5, t, 0.75))),
                                new Envelope<>(new Punctum(0.1))))
                ));

    }

    protected void sono(int index, int t, double repenso, double diu, Vowel vowel, double note) {
        cambio(index);
        sono(t, repenso,
                CadentesFormae.capioLegibilis(new Referibile(new OscillatioPulse(false),
                        new Envelope<>(new Punctum(Temperamentum.instance.capioHZ(note))),
                        diu(t, 0.0, t, 3.85)),
                        new Formant(new Envelope<>(new Aestima(100)),
                                new Envelope<>(vowel)),
                        new IIRFilter(1000, 1800, true),
                        new VCA(new ModEnv(
                                new Envelope<>(new Punctum(0),
                                        new Positio(diu(t, 0., t, 0.1), new Punctum(1)),
                                        new Positio(diu(t, 0., t, diu - 0.1), new Punctum(1)),
                                        new Positio(diu(t, 0., t, diu), new Punctum(0))
                                ),
                                new Envelope<>(new Punctum(1000. / diu(t, 0.5, t, 0.75))),
                                new Envelope<>(new Punctum(0.1))))
                ));

    }

}
