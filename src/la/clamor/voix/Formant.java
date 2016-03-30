/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor.voix;

import java.io.File;
import la.clamor.Aestima;
import la.clamor.Consilium;
import la.clamor.Envelope;
import la.clamor.Functiones;
import la.clamor.Positio;
import la.clamor.Punctum;
import la.clamor.Temperamentum;
import la.clamor.forma.VCA;
import la.clamor.forma.CadentesFormae;
import la.clamor.forma.Forma;
import la.clamor.forma.IIRFilter;
import la.clamor.io.IOUtil;
import la.clamor.io.ScriptorWav;
import la.clamor.referibile.OscillatioPulse;
import la.clamor.referibile.Referibile;

/**
 *
 * @author mina
 */
public class Formant implements Forma {

    private final IIRFilter[] filters;
    private final Envelope<Aestima> band_env;
    private final Envelope<Vowel> freqs;
    private Punctum deenphasis;
    private long index;

    public Formant(Envelope<Aestima> band_env, Envelope<Vowel> freqs) {
        double[] primo_freqs = freqs.capioValue(0).getFrequentia();
        double band = band_env.capioValue(0).doubleValue();
        filters = new IIRFilter[primo_freqs.length];
        for (int i = 0; i < primo_freqs.length; i++) {
            filters[i] = IIRFilter.resonator(primo_freqs[i], band);
        }
        this.band_env = band_env;
        this.freqs = freqs;
        deenphasis = new Punctum();
        index = 0;
    }

    @Override
    public Punctum formo(Punctum lectum) {
        double[] current_freqs = freqs.capioValue(index).getFrequentia();
        double band = band_env.capioValue(index).doubleValue();
        Punctum reditum = new Punctum();
        for (int i = 0; i < filters.length; i++) {
            filters[i].rescriboResonator(current_freqs[i], band);
            reditum = reditum.addo(filters[i].formo(lectum));
        }
        reditum = reditum.addo(deenphasis.multiplico(0.98));
        deenphasis = reditum;
        //System.out.println(reditum);
        index++;
        return reditum;
    }

    @Override
    public int resto() {
        return 0;
    }

    public static void main(String[] args) {
        File out_file = new File(IOUtil.getDirectory("sample"), "formant3_2.wav");
        ScriptorWav sw = new ScriptorWav(out_file);
        Consilium c = new Consilium();
        double temps = 0;
        double duration = 3000;
        double[][] freqs = new double[][]{
            new double[]{
                400
            }
        };
        //Vowel factor = new Vowel(2.3, 2.2, 2.1, 2);
        Vowel factor = new Vowel(1.2, 1.2, 1.2, 1.2);
        for (int i = 0; i < freqs.length; i++) {
            for (int j = 0; j < freqs[i].length; j++) {
                double unit = duration / 40;
                c.addo(temps, CadentesFormae.capioLegibilis(new Referibile(new OscillatioPulse(false),
                    new Envelope<>(new Punctum(freqs[i][j])), duration),
                    new Formant(new Envelope<>(new Aestima(100)/*,
                            new Positio<>(8 * unit, new Aestima(50)),
                            new Positio<>(10 * unit, new Aestima(100)),
                            new Positio<>(18 * unit, new Aestima(50)),
                            new Positio<>(20 * unit, new Aestima(100)),
                            new Positio<>(28 * unit, new Aestima(50)),
                            new Positio<>(30 * unit, new Aestima(100)),
                            new Positio<>(38 * unit, new Aestima(50))*/
                        ),
                        new Envelope<>(Vowel.A.multiplico(factor),
                            new Positio<>(8 * unit, Vowel.A.multiplico(factor)),
                            new Positio<>(10 * unit, Vowel.I.multiplico(factor)),
                            new Positio<>(18 * unit, Vowel.I.multiplico(factor)),
                            new Positio<>(20 * unit, Vowel.O.multiplico(factor)),
                            new Positio<>(28 * unit, Vowel.O.multiplico(factor)),
                            new Positio<>(30 * unit, Vowel.A.multiplico(factor)))),
                    new IIRFilter(3000, true)
                    /*new VCA(new ModEnv(new Envelope<>(new Punctum(0),
                        new Positio(unit, new Punctum(1)),
                        new Positio(38.5 * unit, new Punctum(1)),
                        new Positio(39.5 * unit, new Punctum(0))),
                        new Envelope<>(new Punctum(unit * 10. / 4.)),
                        new Envelope<>(new Punctum(0.5))*/
                    ));
            }
            temps += duration;
            duration *= 0.8;
            //freq *= 1.2;
        }
        sw.scribo(c, false);
        Functiones.ludoLimam(out_file);

    }

    public static void _main(String[] args) {
        File out_file = new File(IOUtil.getDirectory("opus"), "formant3_1.wav");
        ScriptorWav sw = new ScriptorWav(out_file);
        Consilium c = new Consilium();
        double temps = 0;
        double duration = 3000;
        double[][] freqs = new double[][]{
            new double[]{
                Temperamentum.instance.capioHZ(47.98),
                Temperamentum.instance.capioHZ(60),
                Temperamentum.instance.capioHZ(64),
                Temperamentum.instance.capioHZ(67),
                Temperamentum.instance.capioHZ(72.02)
            },
            new double[]{
                Temperamentum.instance.capioHZ(52.98),
                Temperamentum.instance.capioHZ(60),
                Temperamentum.instance.capioHZ(65),
                Temperamentum.instance.capioHZ(69),
                Temperamentum.instance.capioHZ(72.02)
            },
            new double[]{
                Temperamentum.instance.capioHZ(52.98),
                Temperamentum.instance.capioHZ(60),
                Temperamentum.instance.capioHZ(65),
                Temperamentum.instance.capioHZ(68),
                Temperamentum.instance.capioHZ(72.02)
            },
            new double[]{
                Temperamentum.instance.capioHZ(47.98),
                Temperamentum.instance.capioHZ(60),
                Temperamentum.instance.capioHZ(64),
                Temperamentum.instance.capioHZ(67),
                Temperamentum.instance.capioHZ(72.02)
            }
        };
        for (int i = 0; i < freqs.length; i++) {
            for (int j = 0; j < freqs[i].length; j++) {
                double unit = duration / 40;
                c.addo(temps, CadentesFormae.capioLegibilis(new Referibile(new OscillatioPulse(false),
                    new Envelope<>(new Punctum(freqs[i][j])),
                    duration),
                    new Formant(new Envelope<>(new Aestima(100)),
                        new Envelope<>(Vowel.A,
                            new Positio<>(8 * unit, Vowel.A),
                            new Positio<>(10 * unit, Vowel.I),
                            new Positio<>(18 * unit, Vowel.I),
                            new Positio<>(20 * unit, Vowel.O),
                            new Positio<>(28 * unit, Vowel.O),
                            new Positio<>(30 * unit, Vowel.A))),
                    new IIRFilter(1800, true),
                    new VCA(new Envelope<>(new Punctum(0),
                        new Positio(unit, new Punctum(1)),
                        new Positio(38.5 * unit, new Punctum(1)),
                        new Positio(39.5 * unit, new Punctum(0))
                    )
                    )));
            }
            temps += duration;
            //duration *= 0.8;
            //freq *= 1.2;
        }
        sw.scribo(c, false);
        Functiones.ludoLimam(out_file);

    }
}
