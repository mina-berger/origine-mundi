/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor.deinde;

import java.util.ArrayList;
import la.clamor.Legibilis;
import la.clamor.Puncta;
import la.clamor.Punctum;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;

/**
 *
 * @author mina
 */
public class FourierSampler2 {
    Legibilis fons;
    int length;
    int interval;
    int index;
    boolean single;
    ArrayList<Puncta> results;
    FastFourierTransformer ftt;
    
    public FourierSampler2(Legibilis fons, int length, int interval){
        this.fons = fons;
        this.length = length;
        this.interval = Math.max(length, interval);
        single = interval <= 0;
        index = 0;
        results = new ArrayList<>();
        ftt = new FastFourierTransformer(DftNormalization.STANDARD);
    }
    public ArrayList<Complex[]> lego(){
        ArrayList<Complex[]> spectra = new ArrayList();
        Puncta values = new Puncta(length);
        while(fons.paratusSum()){
            values.ponoPunctum(index, fons.lego());
            index++;
            if(index >= length){
                spectra.add(legoPuncta(values));
                index = 0;
                if(single){
                    break;
                }
            }
        }
        return spectra;
        
    }
    private Complex[] legoPuncta(Puncta values){
        return ftt.transform(values.capioDoubleArray(0), TransformType.FORWARD);
        /*int longitudo = oa.longitudo();
        Spectrum spectrum = new Spectrum(longitudo);
        Puncta sample_real = new Puncta(longitudo); 
        Puncta sample_imag = new Puncta(longitudo); 
        for(int n = 0;n < longitudo;n++){
            sample_real.ponoPunctum(n, oa.capio(longitudo - 1 - n));
        }
        for(int k = 0;k < longitudo;k++){
            for(int n = 0;n < longitudo;n++){
                double w_real =  Math.cos(2.0 * Math.PI * (double)k * (double)n / (double)longitudo);
                double w_imag = -Math.sin(2.0 * Math.PI * (double)k * (double)n / (double)longitudo);
                //System.out.println("debug:" + k + ":" + n + ":" + Functiones.toString(w_real) + ":" + Functiones.toString(w_imag));
                Punctum punctum_real = sample_real.capioPunctum(n);
                Punctum punctum_imag = sample_imag.capioPunctum(n);
                //Punctum punctum = oa.capio(n);
                //System.out.println("debug:" + k + ":" + n + ":" + punctum);
                spectrum.add(k, true,  punctum_real.multiplico(w_real).subtraho(punctum_imag.multiplico(w_imag)));
                spectrum.add(k, false, punctum_imag.multiplico(w_real).addo    (punctum_real.multiplico(w_imag)));
            }
        }
        return spectrum;*/
    }
    
}
