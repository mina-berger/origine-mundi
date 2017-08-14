/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor.deinde;

import java.io.File;
import la.clamor.Aestima;
import la.clamor.Consilium;
import la.clamor.Envelope;
import la.clamor.Functiones;
import la.clamor.Legibilis;
import la.clamor.Positio;
import la.clamor.Punctum;
import la.clamor.Temperamentum;
import la.clamor.forma.AutoLimitter;
import la.clamor.forma.CadentesFormae;
import la.clamor.forma.IIRFilter;
import la.clamor.forma.VCA;
import la.clamor.io.IOUtil;
import la.clamor.io.LectorWav;
import la.clamor.io.ScriptorWav;
import la.clamor.referibile.ModEnv;
import la.clamor.referibile.OscillatioPulse;
import la.clamor.referibile.Referibile;
import la.clamor.voix.Formant;
import la.clamor.voix.Vowel;

/**
 *
 * @author hiyamamina
 */
public class DeindeTest2 {

    public static void main(String[] args) {
        Vowel vowel = new Vowel(1, 1, 1, 1);
        Consilium c = new Consilium();
        Legibilis legi = CadentesFormae.capioLegibilis(new Referibile(new OscillatioPulse(false),
                new Envelope<>(new Punctum(Temperamentum.instance.capioHZ(48))),
                1000d),
                new Formant(new Envelope<>(new Aestima(100)),
                        new Envelope<>(Vowel.U.multiplico(vowel),
                                new Positio(2000, Vowel.U.multiplico(vowel)),
                                new Positio(2010, Vowel.A.multiplico(vowel)))),
                new IIRFilter(1000, 1800, true),
                new AutoLimitter(),
                new VCA(new ModEnv(new Envelope<>(new Punctum(0),
                        new Positio(20, new Punctum(1)),
                        new Positio(500, new Punctum(1)),
                        new Positio(800, new Punctum(0))
                ),
                        new Envelope<>(new Punctum(3)),
                        new Envelope<>(new Punctum(0.1))
                )));
        c.addo(0, legi);
        
        File in_file = new File(IOUtil.getDirectory("edit"), "k.wav");
        LectorWav lw = new LectorWav(in_file);
        c.addo(0, lw);
        
        File out_file = new File(IOUtil.getDirectory("sample"), "ka.wav");

        ScriptorWav sw = new ScriptorWav(out_file);
        sw.scribo(c, false);
        Functiones.ludoLimam(out_file);
        

    }

}
