/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package origine_mundi.opus024;

import com.mina.util.Doubles;
import com.mina.util.Integers;
import la.clamor.Aestima;
import la.clamor.Cinctum;
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
public class Parte024AC extends ParteTaleae {
    Integer talea_break;

    public Parte024AC(Mensa mensa, Integers ids, int talea_primo, int length, Integer talea_break) {
        super(mensa, ids, talea_primo, length);
        this.talea_break = talea_break;
    }

    @Override
    protected void creo(int t) {
        if(t % 2 != 0){
            return;
        }
        Doubles[] notes = new Doubles[]{new Doubles(69, 70), new Doubles(66, 67)}; 
        Cinctum[] pan = new Cinctum[]{
            new Cinctum(true, new Punctum(1), new Punctum(0.2)),
            new Cinctum(true, new Punctum(0.2), new Punctum(1))
        };
        Vowel[] vowels = new Vowel[]{new Vowel(1.15, 1.15, 1.15, 1.15), new Vowel(1.25, 1.25, 1.25, 1.25)};
        boolean _break = talea_break != null && t == talea_break - 1;
        for(int i = 0;i < notes.length;i++){
            cambio(i);
            sono(t, 0.0,
                    CadentesFormae.capioLegibilis(new Referibile(new OscillatioPulse(false),
                            new Envelope<>(new Punctum(Temperamentum.instance.capioHZ(notes[i].get(0))),
                                    new Positio(diu(t, 0.0, t + 1, 0.0), new Punctum(Temperamentum.instance.capioHZ(notes[i].get(0)))),
                                    new Positio(diu(t, 0.0, t + 1, 0.25), new Punctum(Temperamentum.instance.capioHZ(notes[i].get(1))))
                            ), 
                            diu(t, 0.0, t + 1, (_break?2.0:3.5))),
                            new Formant(new Envelope<>(new Aestima(100)),
                                    new Envelope<>(Vowel.U.multiplico(vowels[i]),
                                    new Positio(diu(t, 0., t + 1, 0.0), Vowel.U.multiplico(vowels[i])),
                                    new Positio(diu(t, 0., t + 1, 0.25), Vowel.A.multiplico(vowels[i])))),
                            new IIRFilter(1000, 1800, true),
                            new VCA(new ModEnv(new Envelope<>(new Punctum(0),
                                    new Positio(diu(t, 0., t, 0.5), new Punctum(1)),
                                    new Positio(diu(t, 0., t + 1, _break?1.75:3.25), new Punctum(1)),
                                    new Positio(diu(t, 0., t + 1, _break?2.0:3.55), new Punctum(0))
                            ),
                                    new Envelope<>(new Punctum(1000. / diu(t, 0.5, t, 0.75))),
                                    new Envelope<>(new Punctum(0.1))
                            ))));
            ponoPan(t, 0, pan[i]);
            ponoPan(t, 3, pan[i]);
            ponoPan(t + 1, 0, pan[(i + 1) % 2]);
            ponoPan(t + 1, 3.9, pan[(i + 1) % 2]);
        }

    }

}
