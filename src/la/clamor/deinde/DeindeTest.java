/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor.deinde;

import java.io.File;
import la.clamor.Functiones;
import la.clamor.Legibilis;
import la.clamor.Puncta;
import la.clamor.Res;
import la.clamor.io.IOUtil;
import la.clamor.io.LectorLimam;
import la.clamor.io.LectorWav;
import la.clamor.io.ScriptorLimam;
import la.clamor.io.ScriptorWav;

/**
 *
 * @author mina
 */
public class DeindeTest {

    public static void main(String[] args) {
        //rate(2.0);
        Functiones.ludoLimam(new File("/Users/hiyamamina/Music/disques/central-park-west_john-coltrane.mp3"));
    }

    public static void rate(double rate) {
        boolean shrink;
        if (rate > 1.0) {
            shrink = true;
        } else if (rate >= 0.5 && rate < 1.0) {
            shrink = false;
        } else {
            throw new IllegalArgumentException("rate must be in range (rate > 1.0) or (rate >= 0.5 && rate < 1.0)");
        }

        File in_file = new File(IOUtil.getDirectory("opus"), "sine_500.wav");
        File lima = IOUtil.createTempFile("strech");
        File out_file = new File(IOUtil.getDirectory("opus"), "sine_500_" + rate + ".wav");
        LectorWav lw = new LectorWav(in_file);
        Puncta pcm0 = new Puncta();
        while (lw.paratusSum()) {
            pcm0.addoPunctum(lw.lego());
        }
        lw.close();

        //double rate = 2.0;
        double fs = Res.publica.sampleRateDouble();
        //double bit = Constantia.BYTE_PER_EXAMPLUM;
        int length = (int) (pcm0.longitudo() / rate + 1);
        Puncta pcm1 = new Puncta(length);

        int template_size = (int) (fs * 0.01);
        int pmin = (int) (fs * 0.005);
        int pmax = (int) (fs * 0.02);
        Puncta x = new Puncta(template_size);
        Puncta y = new Puncta(template_size);
        double[] r = new double[pmax + 1];
        int offset0 = 0;
        int offset1 = 0;

        while (offset0 + pmax * 2 < pcm0.longitudo()) {
            for (int n = 0; n < template_size; n++) {
                x.ponoPunctum(n, pcm0.capioPunctum(offset0 + n));
            }
            double rmax = 0.0;
            int p = pmin;
            for (int m = pmin; m <= pmax; m++) {
                for (int n = 0; n < template_size; n++) {
                    y.ponoPunctum(n, pcm0.capioPunctum(offset0 + m + n));
                }
                r[m] = 0.0;
                for (int n = 0; n < template_size; n++) {
                    r[m] += x.capioPunctum(n).average().doubleValue() * y.capioPunctum(n).average().doubleValue();
                }
                if (r[m] > rmax) {
                    rmax = r[m];
                    p = m;
                }
            }
            int q;
            if (shrink) {
                for (int n = 0; n < p; n++) {
                    pcm1.ponoPunctum(offset1 + n,
                            pcm0.capioPunctum(offset0 + n).multiplico((double) (p - n) / (double) p).addo(
                            pcm0.capioPunctum(offset0 + p + n).multiplico((double) n / (double) p)));
                }
                q = (int) ((double) p / (rate - 1.0) + 0.5);
                for (int n = p; n < q; n++) {
                    if (offset0 + p + n >= pcm0.longitudo()) {
                        break;
                    }
                    pcm1.ponoPunctum(offset1 + n, pcm0.capioPunctum(offset0 + p + n));
                }
                offset0 += p + q;
                offset1 += q;
            } else {
                for (int n = 0; n < p; n++) {
                    pcm1.ponoPunctum(offset1 + n, pcm0.capioPunctum(offset0 + n));
                }
                for (int n = 0; n < p; n++) {
                    pcm1.ponoPunctum(offset1 + p + n,
                            pcm0.capioPunctum(offset0 + p + n).multiplico((double)(p - n) / (double)p).addo(
                            pcm0.capioPunctum(offset0 + n).multiplico((double)n / (double)p)));
                }

                q = (int) ((double) p * rate / (1.0 - rate) + 0.5);
                for (int n = p; n < q; n++) {
                    if (offset0 + n >= pcm0.longitudo()) {
                        break;
                    }
                    pcm1.ponoPunctum(offset1 + p + n, pcm0.capioPunctum(offset0 + n));
                }
                offset0 += q;
                offset1 += p + q;
            }
            System.out.println("p=" + p + ";q=" + q);
            System.out.println("o0=" + offset0 + ";o1=" + offset1);
        }
        System.out.println("p0=" + pcm0.longitudo() + ";p1=" + pcm1.longitudo() + ";pmax=" + pmax);
        ScriptorLimam sl = new ScriptorLimam(lima);
        Legibilis legi = pcm1.capioLegibilem();
        while(legi.paratusSum()){
            sl.scribo(legi.lego());
        }
        legi.close();
        sl.close();

        LectorLimam ll = new LectorLimam(lima);
        ScriptorWav sw = new ScriptorWav(out_file);
        sw.scribo(ll, false);
    }

}
