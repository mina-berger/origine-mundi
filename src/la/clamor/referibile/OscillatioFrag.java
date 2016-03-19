package la.clamor.referibile;

import java.io.File;
import java.util.Random;
import la.clamor.Aestimatio;
import la.clamor.Envelope;
import la.clamor.Functiones;
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

    @Override
    protected Aestimatio capioUnda(Aestimatio delta_t) {
        return new Aestimatio(FastMath.sin(new Random().nextGaussian() * 2. * FastMath.PI));
    }

    public static void main(String[] args) {
        //Res.publica.ponoChannel(4);
        File out_file = new File(OmUtil.getDirectory("opus"), "osc_frag.wav");
        ScriptorWav sw = new ScriptorWav(out_file);
        sw.scribo(new Referibile(new OscillatioFrag(),
            new Envelope<>(new Punctum(240)),
            4000), false);
        Functiones.ludoLimam(out_file);

    }
}
