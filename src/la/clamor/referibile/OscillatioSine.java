package la.clamor.referibile;

import java.io.File;
import la.clamor.Aestima;
import la.clamor.Constantia;
import static la.clamor.Constantia.REGULA_EXAMPLI_D;
import la.clamor.Envelope;
import la.clamor.Functiones;
import la.clamor.Positio;
import la.clamor.Punctum;
import la.clamor.Res;
import la.clamor.io.ScriptorWav;
import org.apache.commons.math3.util.FastMath;
import origine_mundi.OmUtil;

/**
 * Oscillation with duration specified, but frequency nor volume unspecified
 *
 * @author minae.hiyamae
 */
public class OscillatioSine implements Referibilis, Constantia {

    double t;
    Punctum y_1;
    Punctum y_2;
    int count_buffer;

    /**
     * setting for this oscillatio
     */
    public OscillatioSine() {

        t = 1d / REGULA_EXAMPLI_D;
        y_1 = new Punctum();
        y_2 = new Punctum();
        count_buffer = 2;

    }

    @Override
    public Punctum lego(Punctum frequentia) {
        //frequentia = (frequentia == null) ? new Punctum() : frequentia;
        //quantitas = (quantitas == null) ? new Punctum() : quantitas;
        Punctum punctum = new Punctum();
        if (count_buffer == 2) {
            count_buffer--;
            return punctum;
        }
        if (count_buffer == 1) {
            count_buffer--;
            for (int i = 0; i < Res.publica.channel(); i++) {
                Aestima omega_t = frequentia.capioAestimatio(i).multiplico(new Aestima(2 * FastMath.PI * t));
                punctum.ponoAestimatio(i, new Aestima(FastMath.sin(omega_t.doubleValue())));
            }
        } else {
            for (int i = 0; i < Res.publica.channel(); i++) {
                Aestima omega_t = frequentia.capioAestimatio(i).multiplico(new Aestima(2 * FastMath.PI * t));
                Aestima b1_m = new Aestima(2).multiplico(new Aestima(FastMath.cos(omega_t.doubleValue())));
                punctum.ponoAestimatio(i, b1_m.multiplico(y_1.capioAestimatio(i)).subtraho(y_2.capioAestimatio(i)));
            }
            y_2 = y_1;
        }
        y_1 = punctum;
        return punctum;
    }

    public static void main(String[] args) {
        //Res.publica.ponoChannel(4);
        File out_file = new File(OmUtil.getDirectory("opus"), "osc_sine.wav");
        ScriptorWav sw = new ScriptorWav(out_file);
        sw.scribo(new Referibile(new OscillatioSine(),
            new ModEnv(
                new Envelope<>(true, new Punctum(420), new Positio<>(1000, new Punctum(440))),
                new Envelope<>(new Punctum(1), new Positio<>(9000, new Punctum(15))),
                new Envelope<>(new Punctum(0), new Positio<>(1000, new Punctum(0.08)))),
            10000), false);
        Functiones.ludoLimam(out_file);

    }

    @Override
    public Referibilis duplicate() {
        return new OscillatioSine();
    }
}
