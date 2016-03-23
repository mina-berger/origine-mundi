/*
 * CLAMOR project
 * by MINA BERGER
 */
package la.clamor.forma;

import java.io.File;
import la.clamor.Aestimatio;
import la.clamor.Envelope;
import la.clamor.Functiones;
import la.clamor.Punctum;
import la.clamor.Res;
import la.clamor.io.ScriptorWav;
import la.clamor.referibile.OscillatioSine;
import la.clamor.referibile.Referibile;
import origine_mundi.OmUtil;

/**
 *
 * @author minae.hiyamae
 */
public class Compressor implements Forma {

    Punctum threshold;
    Punctum ratio;
    Punctum gain;

    public Compressor(Punctum threshold, Punctum ratio) {
        this.threshold = threshold;
        this.ratio = ratio;
        gain = new Punctum(1.0).partior(threshold.addo((new Punctum(1.0).subtraho(threshold)).multiplico(ratio)));
        System.out.println("gain=" + gain);

    }

    @Override
    public int resto() {
        return 0;
    }

    @Override
    public Punctum formo(Punctum lectum) {
        Punctum reditum = new Punctum();
        for (int i = 0; i < Res.publica.channel(); i++) {
            Aestimatio _threshold = threshold.capioAestimatio(i);
            Aestimatio _lectum = lectum.capioAestimatio(i);
            Aestimatio _ratio = ratio.capioAestimatio(i);
            if (_lectum.compareTo(_threshold) > 0) {
                reditum.ponoAestimatio(i, _threshold.addo(_lectum.subtraho(_threshold).multiplico(_ratio)));
            } else if (_lectum.compareTo(_threshold.nego()) < 0) {
                reditum.ponoAestimatio(i, _threshold.nego().addo(_lectum.addo(_threshold).multiplico(_ratio)));
            } else {
                reditum.ponoAestimatio(i, _lectum);
            }
        }
        return reditum.multiplico(gain);

    }

    /*private Aestimatio calculoRatio(Aestimatio lectum, Aestimatio threshold, Aestimatio ratio, Aestimatio gain) {
       
    }*/
    public static void main(String[] args) {
        File out_file = new File(OmUtil.getDirectory("opus"), "compressor.wav");

        ScriptorWav sl = new ScriptorWav(out_file);
        sl.scribo(CadentesFormae.capioLegibilis(new Referibile(new OscillatioSine(),
            new Envelope<>(new Punctum(400)), 5000),
            new Compressor(new Punctum(0.2), new Punctum(0.1)),
            new VCA(new Envelope<>(new Punctum(1)))
        ), false);
        Functiones.ludoLimam(out_file);
    }

}
