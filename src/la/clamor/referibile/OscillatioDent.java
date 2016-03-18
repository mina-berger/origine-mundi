package la.clamor.referibile;

import java.io.File;
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
public class OscillatioDent extends OscillatioDelta {

    @Override
    protected Aestimatio capioUnda(Aestimatio delta_t) {
        double pos = delta_t.doubleValue() - FastMath.floor(delta_t.doubleValue());
        if (pos < 0) {
            pos += 1;
        }
        return new Aestimatio((1. - pos) * 2. - 1.);
    }

    public static void main(String[] args) {
        //Res.publica.ponoChannel(4);
        File out_file = new File(OmUtil.getDirectory("opus"), "osc_dent.wav");
        ScriptorWav sw = new ScriptorWav(out_file);
        sw.scribo(new Referibile(new OscillatioDent(),
            new Envelope<>(new Punctum(240), new Positio(3000, new Punctum(960))),
            new Envelope<>(new Punctum(1))), false);
        //Functiones.ludoLimam(out_file);

    }
}
