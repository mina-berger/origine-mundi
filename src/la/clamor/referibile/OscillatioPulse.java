package la.clamor.referibile;

import java.io.File;
import la.clamor.Aestimatio;
import la.clamor.Constantia;
import la.clamor.Envelope;
import la.clamor.Functiones;
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
public class OscillatioPulse implements Referibilis, Constantia {

    Punctum x;
    Punctum last;
    Aestimatio ratio;
    Aestimatio threshold;
    boolean con_negatif;

    /**
     * setting for this oscillatio
     * @param con_negatif
     */
    public OscillatioPulse(boolean con_negatif) {
        this.con_negatif = con_negatif;
        ratio = new Aestimatio(2d * FastMath.PI / REGULA_EXAMPLI_D);
        threshold = new Aestimatio(2d * FastMath.PI);
        x = new Punctum();
        last = new Punctum();
        //System.out.println("th:" + threshold);
    }

    @Override
    public Punctum lego(Punctum frequentia) {
        //f//requentia = (frequentia == null) ? new Punctum() : frequentia;
        //quantitas = (quantitas == null) ? new Punctum() : quantitas;
        Punctum punctum = new Punctum();
        for (int i = 0; i < Res.publica.channel(); i++) {
            Aestimatio omega_t = frequentia.capioAestimatio(i).multiplico(ratio);
            Aestimatio current_x = x.capioAestimatio(i).addo(omega_t);
            //System.out.println(omega_t + ":" + current_x + ":" + current_x.resto(threshold));
            if (current_x.compareTo(threshold) >= 0) {
                punctum.ponoAestimatio(i, new Aestimatio(1));
            } else if (con_negatif && last.capioAestimatio(i).equals(new Aestimatio(1))) {
                punctum.ponoAestimatio(i, new Aestimatio(-1));
                //punctum.ponoAestimatio(i, new Aestimatio(0));
            }
            x.ponoAestimatio(i, current_x.resto(threshold));
        }
        last = punctum;
        return punctum;
    }

    /*public static void main(String[] arg) {
        PulseOscillatio o = new PulseOscillatio();
        for (int i = 0; i < 200; i++) {
            System.out.println(i + ":" + o.lego(new Punctum(4800), new Punctum(1)));
        }
    }*/
    public static void main(String[] args) {

        File out_file = new File(OmUtil.getDirectory("opus"), "pulse_train1.wav");
        ScriptorWav sw = new ScriptorWav(out_file);
        sw.scribo(new Referibile(new OscillatioPulse(false),
            new Envelope<>(new Punctum(100)),
            3000), false);

        Functiones.ludoLimam(out_file);
    }
}
