package la.clamor.referibile;

import java.io.File;
import la.clamor.Aestimatio;
import la.clamor.Constantia;
import static la.clamor.Constantia.REGULA_EXAMPLI_D;
import la.clamor.Functiones;
import la.clamor.Legibilis;
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
                Aestimatio omega_t = frequentia.capioAestimatio(i).multiplico(new Aestimatio(2 * FastMath.PI * t));
                punctum.ponoAestimatio(i, new Aestimatio(FastMath.sin(omega_t.doubleValue())));
            }
        } else {
            for (int i = 0; i < Res.publica.channel(); i++) {
                Aestimatio omega_t = frequentia.capioAestimatio(i).multiplico(new Aestimatio(2 * FastMath.PI * t));
                Aestimatio b1_m = new Aestimatio(2).multiplico(new Aestimatio(FastMath.cos(omega_t.doubleValue())));
                punctum.ponoAestimatio(i, b1_m.multiplico(y_1.capioAestimatio(i)).subtraho(y_2.capioAestimatio(i)));
            }
            y_2 = y_1;
        }
        y_1 = punctum;
        return punctum;
    }

    public static void _main(String[] arg) {
        OscillatioSine o = new OscillatioSine();
        for (int i = 0; i < 200; i++) {
            System.out.println(o.lego(new Punctum(0)));
        }
    }

    public static void main(String[] args) {
        Res.publica.ponoChannel(4);
        File out_file = new File(OmUtil.getDirectory("opus"), "sine_oscillatio.wav");
        ScriptorWav sw = new ScriptorWav(out_file);
        sw.scribo(new Legibilis() {
            OscillatioSine o = new OscillatioSine();
            int count = 0;
            double pitch = 110;

            @Override
            public Punctum lego() {
                count++;
                pitch += 0.02;
                return o.lego(new Punctum(pitch));
            }

            @Override
            public boolean paratusSum() {
                return count < 144000;
            }

            @Override
            public void close() {
            }
        }, false);
        Functiones.ludoLimam(out_file);

    }
}
