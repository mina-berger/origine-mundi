/*
 * CLAMOR project
 * by MINA BERGER
 */
package la.clamor.forma;

import java.io.File;
import java.text.DecimalFormat;
import la.clamor.Aestima;
import la.clamor.Envelope;
import la.clamor.Positio;
import la.clamor.Punctum;
import la.clamor.Res;
import la.clamor.io.IOUtil;
import la.clamor.io.ScriptorWav;
import la.clamor.referibile.OscillatioSine;
import la.clamor.referibile.Referibile;
import org.apache.commons.math3.util.FastMath;

/**
 *
 * @author minae.hiyamae
 */
public class Compressor implements Forma {

    Punctum threshold;
    Punctum ratio;
    Punctum gain;

    public Compressor(Punctum threshold, Punctum ratio) {
        this(threshold, ratio, new Punctum(1.0).partior(threshold.addo((new Punctum(1.0).subtraho(threshold)).multiplico(ratio))));

    }
    public Compressor(Punctum threshold, Punctum ratio, Punctum gain) {
        this.threshold = threshold;
        this.ratio = ratio;
        this.gain = gain;
        //System.out.println("gain=" + gain);

    }

    @Override
    public int resto() {
        return 0;
    }

    @Override
    public Punctum formo(Punctum lectum) {
        Punctum reditum = new Punctum();
        for (int i = 0; i < Res.publica.channel(); i++) {
            Aestima _threshold = threshold.capioAestima(i);
            Aestima _lectum = lectum.capioAestima(i);
            Aestima _ratio = ratio.capioAestima(i);
            /*if (_lectum.compareTo(_threshold) > 0) {
                reditum.ponoAestimatio(i, _threshold.addo(_lectum.subtraho(_threshold).multiplico(_ratio)));
            } else if (_lectum.compareTo(_threshold.nego()) < 0) {
                reditum.ponoAestimatio(i, _threshold.nego().addo(_lectum.addo(_threshold).multiplico(_ratio)));
            } else {
                reditum.ponoAestimatio(i, _lectum);
            }*/
            reditum.ponoAestimatio(i, compress(_lectum, _threshold, _ratio));
        }
        return reditum.multiplico(gain);

    }

    private static Aestima compress(Aestima lectum, Aestima threshold, Aestima ratio) {
        Aestima reditum;
        if (lectum.compareTo(threshold) > 0) {
            double position = 1. - FastMath.exp(-5. * lectum.subtraho(threshold).doubleValue());
            reditum = mergo(position, threshold, new Aestima(1).subtraho(threshold).multiplico(ratio).addo(threshold));
        } else if (lectum.compareTo(threshold.nego()) < 0) {
            double position = 1. - FastMath.exp(-5. * lectum.nego().subtraho(threshold).doubleValue());
            reditum = mergo(position, threshold.nego(), new Aestima(1).subtraho(threshold).multiplico(ratio).addo(threshold).nego());
        } else {
            reditum = lectum;
        }
        //System.out.println(df.format(lectum.doubleValue()) + "->" + df.format(reditum.doubleValue()));
        return reditum;
    }
    private static DecimalFormat df = new DecimalFormat("###,##0.000000");

    private static Aestima mergo(double position, Aestima solum, Aestima tectum) {
        //System.out.println(solum.doubleValue() + ":" + tectum.doubleValue());
        return solum.multiplico(new Aestima(1. - position)).addo(tectum.multiplico(new Aestima(position)));
    }

    public static void main(String[] args) {
        File out_file = new File(IOUtil.getDirectory("opus"), "compressor.wav");

        ScriptorWav sl = new ScriptorWav(out_file);
        sl.scribo(CadentesFormae.capioLegibilis(new Referibile(new OscillatioSine(),
                new Envelope<>(new Punctum(400)), 5000),
                new VCA(new Envelope<>(new Punctum(0), new Positio(5000, new Punctum(1)))),
                new Compressor(new Punctum(0.2), new Punctum(0.1))
        ), false);
        //Functiones.ludoLimam(out_file);
    }

    @Override
    public void ponoPunctum(int index, double tempus, Punctum punctum) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void close() {
    }

}
