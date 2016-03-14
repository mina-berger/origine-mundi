/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor.forma;

import java.io.IOException;
import la.clamor.Constantia;
import la.clamor.OrbisPuncti;
import la.clamor.Punctum;
import org.apache.commons.math3.util.FastMath;
import static org.apache.commons.math3.util.FastMath.PI;

/**
 *
 * @author mina
 */
public class IIRFilter implements Forma {

    private final OrbisPuncti oa_a;
    private final OrbisPuncti oa_b;
    IIRCoefficients coef;

    public IIRFilter(double freq, boolean is_lpf) {
        oa_a = new OrbisPuncti(3);
        oa_b = new OrbisPuncti(3);
        coef = getCoefficientsLpfHpf(freq, 1.0 / FastMath.sqrt(2.0), is_lpf);
    }
    public void rescribo(double freq, boolean is_lpf) {
        coef = getCoefficientsLpfHpf(freq, 1.0 / FastMath.sqrt(2.0), is_lpf);
    }

    @Override
    public int resto() {
        return 0;
    }

    @Override
    public Punctum formo(Punctum lectum) {
        Punctum reditum = new Punctum();
        //for (int i = 1; i < coef.b.length; i++) {
        //    reditum = reditum.addo(oa_b.capio(i - 1).multiplico(coef.b[i]));
        //}
        for (int i = 0; i < coef.b.length; i++) {
            reditum = reditum.addo((i == 0 ? lectum : oa_b.capio(i - 1)).multiplico(coef.b[i]));
        }
        for (int i = 1; i < coef.a.length; i++) {
            reditum = reditum.addo(oa_a.capio(i - 1).multiplico(coef.a[i] * -1.0));
        }
        oa_b.pono(lectum);
        oa_a.pono(reditum);
        return reditum;
    }

    public static IIRCoefficients getCoefficientsBpfBef(double freq1, double freq2, double q, boolean is_bpf) {
        IIRCoefficients coef = new IIRCoefficients();
        double fc1 = FastMath.tan(PI * freq1 / Constantia.REGULA_EXAMPLI_D) / (2.0 * PI);
        double fc2 = FastMath.tan(PI * freq2 / Constantia.REGULA_EXAMPLI_D) / (2.0 * PI);
        coef.a = new double[3];
        coef.b = new double[3];
        double denom = 1.0 + 2.0 * PI * (fc2 - fc1) / q + 4.0 * PI * PI * fc1 * fc2;
        coef.a[0] = 1.0;
        coef.a[1] = (8.0 * PI * PI * fc1 * fc2 - 2.0) / denom;
        coef.a[2] = (1.0 - 2.0 * PI * (fc2 - fc1) / q + 4.0 * PI * PI * fc1 * fc2) / denom;
        if (is_bpf) {
            coef.b[0] =  2.0 * PI * (fc2 - fc1) / denom;
            coef.b[1] =  0.0;
            coef.b[2] = -2.0 * PI * (fc2 - fc1) / denom;
        } else {
            coef.b[0] = (4.0 * PI * PI * fc1 * fc2 + 1.0) / denom;
            coef.b[1] = (8.0 * PI * PI * fc1 * fc2 - 2.0) / denom;
            coef.b[2] = (4.0 * PI * PI * fc1 * fc2 + 1.0) / denom;
        }

        //System.out.println(coef.a[0]);
        //System.out.println(coef.a[1]);
        //System.out.println(coef.a[2]);
        //System.out.println(coef.b[0]);
        //System.out.println(coef.b[1]);
        //System.out.println(coef.b[2]);
        return coef;
    }

    public static IIRCoefficients getCoefficientsLpfHpf(double freq, double q, boolean is_lpf) {
        IIRCoefficients coef = new IIRCoefficients();
        double fc = FastMath.tan(PI * freq / Constantia.REGULA_EXAMPLI_D) / (2.0 * PI);
        coef.a = new double[3];
        coef.b = new double[3];
        double denom = 1.0 + 2.0 * PI * fc / q + 4.0 * PI * PI * fc * fc;
        coef.a[0] = 1.0;
        coef.a[1] = (8.0 * PI * PI * fc * fc - 2.0) / denom;
        coef.a[2] = (1.0 - 2.0 * PI * fc / q + 4.0 * PI * PI * fc * fc) / denom;
        if (is_lpf) {
            coef.b[0] = 4.0 * PI * PI * fc * fc / denom;
            coef.b[1] = 8.0 * PI * PI * fc * fc / denom;
            coef.b[2] = 4.0 * PI * PI * fc * fc / denom;
        } else {
            //coef.b[0] = 4.0 * PI * PI * fc * fc / denom;
            //coef.b[1] = -8.0 * PI * PI * fc * fc / denom;
            //coef.b[2] = 4.0 * PI * PI * fc * fc / denom;
            coef.b[0] = 1.0 / denom;
            coef.b[1] = -2.0 / denom;
            coef.b[2] = 1.0 / denom;
        }

        //System.out.println(coef.a[0]);
        //System.out.println(coef.a[1]);
        //System.out.println(coef.a[2]);
        //System.out.println(coef.b[0]);
        //System.out.println(coef.b[1]);
        //System.out.println(coef.b[2]);
        return coef;
    }

    public static class IIRCoefficients {

        double[] a;
        double[] b;
    }

    public static void main(String[] args) throws IOException {
        int length = 1000;
        for (int n = 0; n < length; n++) {
            System.out.println(10000.0 * Math.exp(-5.0 * n / length));
        }
        /*Oscillator osc = new Oscillator(getPreset("filter"));
        double a = Temperamentum.instance.capioHZ(9 + 12);
        Consilium cns = new Consilium();
        cns.addo(0, osc.capioOscillationes(new Punctum(a), 5000, Velocitas.una(1)));
         */
    }
    /*public static void main(String[] args) throws IOException {
        File in_file = new File(OmUtil.getDirectory("opus"), "filter2.wav");
        LectorWav lw = new LectorWav(in_file);
        File out_file = new File(OmUtil.getDirectory("opus"), "iir_l23.wav");
        ScriptorWav sw = new ScriptorWav(out_file);
        //sw.scribo(cns, false);
        sw.scribo(new FormaLegibilis(lw, new IIRFilter(5000, false)), false);

        Functiones.ludoLimam(in_file);
        Functiones.ludoLimam(out_file);
    }*/

}
