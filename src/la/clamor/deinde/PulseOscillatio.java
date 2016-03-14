package la.clamor.deinde;

import java.io.File;
import la.clamor.Aestimatio;
import la.clamor.Constantia;
import la.clamor.Envelope;
import la.clamor.Functiones;
import la.clamor.Positio;
import la.clamor.Punctum;
import la.clamor.Referibile;
import la.clamor.Referibilis;
import la.clamor.io.ScriptorWav;
import org.apache.commons.math3.util.FastMath;
import origine_mundi.OmUtil;

/**
 * Oscillation with duration specified, but frequency nor volume unspecified
 *
 * @author minae.hiyamae
 */
public class PulseOscillatio implements Referibilis, Constantia {

    Punctum x;
    Aestimatio ratio;
    Aestimatio threshold;

    /**
     * setting for this oscillatio
     */
    public PulseOscillatio() {
        ratio = new Aestimatio(2d * FastMath.PI / REGULA_EXAMPLI_D);
        threshold = new Aestimatio(2d * FastMath.PI);
        x = new Punctum();
        //System.out.println("th:" + threshold);
    }

    @Override
    public Punctum lego(Punctum frequentia, Punctum quantitas) {
        frequentia = (frequentia == null) ? new Punctum() : frequentia;
        quantitas = (quantitas == null) ? new Punctum() : quantitas;
        Punctum punctum = new Punctum();
        for (int i = 0; i < CHANNEL; i++) {
            Aestimatio omega_t = frequentia.capioAestimatio(i).multiplico(ratio);
            Aestimatio current_x = x.capioAestimatio(i).addo(omega_t);
            //System.out.println(omega_t + ":" + current_x + ":" + current_x.resto(threshold));
            if (current_x.compareTo(threshold) >= 0) {
                punctum.ponoAestimatio(i, new Aestimatio(1));
            }
            x.ponoAestimatio(i, current_x.resto(threshold));
        }
        return punctum.multiplico(quantitas);
    }

    /*public static void main(String[] arg) {
        PulseOscillatio o = new PulseOscillatio();
        for (int i = 0; i < 200; i++) {
            System.out.println(i + ":" + o.lego(new Punctum(4800), new Punctum(1)));
        }
    }*/

    public static void main(String[] args) {

        File out_file = new File(OmUtil.getDirectory("opus"), "pulse_train.wav");
        ScriptorWav sw = new ScriptorWav(out_file);
        sw.scribo(new Referibile(new PulseOscillatio(), new Envelope(new Punctum(20), new Positio(3000, new Punctum(1000))), new Envelope(true)), false);
        
        Functiones.ludoLimam(out_file);
    }
}
