package la.clamor.referibile;

import java.io.File;
import java.util.Random;
import la.clamor.Aestimatio;
import la.clamor.Envelope;
import la.clamor.Positio;
import la.clamor.Punctum;
import la.clamor.io.ScriptorWav;
import org.apache.commons.math3.util.FastMath;
import origine_mundi.OmUtil;

/**
 * Oscillation with duration specified, but frequency nor volume unspecified
 *
 * @author minae.hiyamae
 */
public class OscillatioFrag extends OscillatioDelta {

    //static final int SIZE = 100;//22050;
    //static final double F0 = 1.0;
    //double[] theta;
    

    /*public OscillatioFrag() {
        theta = new double[SIZE];
        for (int i = 0; i < SIZE; i++) {
            theta[i] = new Random().nextGaussian() * 2.0 * FastMath.PI;
        }
    }*/
    //int index = 0;

    @Override
    protected Aestimatio capioUnda(Aestimatio delta_t) {
        return new Aestimatio(FastMath.sin(new Random().nextGaussian() * 2. * FastMath.PI));
        //return new Aestimatio(new Random().nextGaussian() * 2. - 1);
        /*double value = 0;
        for (int i = 0; i < SIZE; i++) {
            value += FastMath.sin(2.0 * FastMath.PI * i * F0 * delta_t.doubleValue() + theta[i]);
        }
        index++;
        if(index % 1000 == 0){
            System.out.println(index);
        }
        return new Aestimatio(value / (double)SIZE);*/
    }

    public static void main(String[] args) {
        //Res.publica.ponoChannel(4);
        File out_file = new File(OmUtil.getDirectory("opus"), "osc_frag.wav");
        ScriptorWav sw = new ScriptorWav(out_file);
        sw.scribo(new Referibile(new OscillatioFrag(),
            new Envelope<>(new Punctum(240), new Positio(3000, new Punctum(960))),
            new Envelope<>(new Punctum(1))), false);
        //Functiones.ludoLimam(out_file);

    }
}
