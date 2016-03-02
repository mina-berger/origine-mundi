/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor.forma;

import java.io.File;
import java.io.IOException;
import la.clamor.Aestimatio;
import la.clamor.Functiones;
import la.clamor.io.LectorWav;
import la.clamor.Legibilis;
import la.clamor.OrbisPuncti;
import la.clamor.Punctum;
import la.clamor.io.ScriptorWav;
import org.apache.commons.math3.util.FastMath;
import static org.apache.commons.math3.util.FastMath.PI;
import origine_mundi.OmUtil;

/**
 *
 * @author mina
 */
public class IIRFilter implements Forma {
    private final OrbisPuncti oa;
    IIRCoefficients coef;
    public IIRFilter(double freq, boolean is_lpf){
        oa = new OrbisPuncti(3);
        coef = getCoefficientsLPF(freq, 1.0 / FastMath.sqrt(2.0), is_lpf);
    }
    @Override
    public int resto() {
        return 0;
    }
    @Override
    public Punctum formo(Punctum lectum) {
        Punctum p = new Punctum(lectum);
        for(int i = 0;i < coef.b.length;i++){
            p = p.addo((i == 0?lectum:oa.capio(i - 1)).multiplico(coef.b[i]));
        }
        for(int i = 1;i < coef.a.length;i++){
            p = p.addo(oa.capio(i - 1).multiplico(coef.a[i] * -1.0));
        }
        oa.pono(p);
        return p;
    }
    
    
    public static IIRCoefficients getCoefficientsLPF(double freq, double q, boolean is_lpf){
        IIRCoefficients coef = new IIRCoefficients();
        double fc = FastMath.tan(PI * freq / 48000) / (2.0 * PI);
        coef.a = new double[3];
        coef.b = new double[3];
        double denom = 1.0 + 2.0 * PI * fc / q + 4.0 * PI * PI * fc * fc;
        coef.a[0] = 1.0;
        coef.a[1] = (8.0 * PI * PI * fc * fc - 2.0) / denom;
        coef.a[2] = (1.0 - 2.0 * PI * fc / q + 4.0 * PI *PI * fc * fc) / denom;
        if(is_lpf){
            coef.b[0] = 4.0 * PI * PI * fc * fc / denom;
            coef.b[1] = 8.0 * PI * PI * fc * fc / denom;
            coef.b[2] = 4.0 * PI * PI * fc * fc / denom;
        }else{
            //coef.b[0] = 4.0 * PI * PI * fc * fc / denom;
            //coef.b[1] = -8.0 * PI * PI * fc * fc / denom;
            //coef.b[2] = 4.0 * PI * PI * fc * fc / denom;
            coef.b[0] =  1.0 / denom;
            coef.b[1] = -2.0 / denom;
            coef.b[2] =  1.0 / denom;
        }

        System.out.println(coef.a[0]);
        System.out.println(coef.a[1]);
        System.out.println(coef.a[2]);
        System.out.println(coef.b[0]);
        System.out.println(coef.b[1]);
        System.out.println(coef.b[2]);
        return coef;
    }

    public static class IIRCoefficients{
        double[] a;
        double[] b;
    }
    public static void main(String[] args) throws IOException{
        /*Oscillator osc = new Oscillator(getPreset("filter"));
        double a = Temperamentum.instance.capioHZ(9 + 12);
        Consilium cns = new Consilium();
        cns.addo(0, osc.capioOscillationes(new Punctum(a), 5000, Velocitas.una(1)));
        */
        File in_file = new File(OmUtil.getDirectory("opus"), "filter2.wav");
        LectorWav lw = new LectorWav(in_file);
        File out_file = new File(OmUtil.getDirectory("opus"), "iir_l23.wav");
        ScriptorWav sw = new ScriptorWav(out_file);
        //sw.scribo(cns, false);
        sw.scribo(new FormaLegibilis(lw, new IIRFilter(800, true)), false);
        

        Functiones.ludoLimam(in_file);
        Functiones.ludoLimam(out_file);
    }
    
}
