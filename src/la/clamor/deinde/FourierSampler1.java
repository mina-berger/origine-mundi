/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor.deinde;

import java.util.ArrayList;
import la.clamor.Legibilis;
import la.clamor.OrbisAestimationis;
import la.clamor.Puncta;
import la.clamor.Punctum;
import org.apache.commons.math3.util.FastMath;

/**
 *
 * @author mina
 */
public class FourierSampler1 extends AbstractFourierSampler {
    public FourierSampler1(Legibilis fons, int length, int interval){
        super(fons, length, interval);
    }
    

    @Override
    public Spectrum transform(Puncta sample) {
        int longitudo = getLength();
        Spectrum spectrum = new Spectrum(longitudo);
        for(int k = 0;k < longitudo;k++){
            for(int n = 0;n < longitudo;n++){
                double w_real =  Math.cos(2.0 * Math.PI * (double)k * (double)n / (double)longitudo);
                double w_imag = -Math.sin(2.0 * Math.PI * (double)k * (double)n / (double)longitudo);
                //System.out.println("debug:" + k + ":" + n + ":" + Functiones.toString(w_real) + ":" + Functiones.toString(w_imag));
                Punctum punctum_real = sample.capioPunctum(n);
                Punctum punctum_imag = new Punctum(0);
                //Punctum punctum = oa.capio(n);
                //System.out.println("debug:" + k + ":" + n + ":" + punctum);
                spectrum.add(k, true,  punctum_real.multiplico(w_real).subtraho(punctum_imag.multiplico(w_imag)));
                spectrum.add(k, false, punctum_imag.multiplico(w_real).addo    (punctum_real.multiplico(w_imag)));
            }
        }
        return spectrum;
    }
    
}
