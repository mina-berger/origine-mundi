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

/**
 *
 * @author mina
 */
public class FourierSampler1 {
    Legibilis fons;
    int length;
    int interval;
    int index;
    boolean single;
    ArrayList<Puncta> results;
    
    public FourierSampler1(Legibilis fons, int length, int interval){
        this.fons = fons;
        this.length = length;
        this.interval = Math.max(length, interval);
        single = interval <= 0;
        index = 0;
        results = new ArrayList<>();
    }
    public ArrayList<Spectrum> lego(){
        ArrayList<Spectrum> spectra = new ArrayList();
        OrbisAestimationis oa = new OrbisAestimationis(length);
        while(fons.paratusSum()){
            oa.pono(fons.lego());
            index++;
            if(index >= length){
                spectra.add(legoPuncta(oa));
                index = 0;
                if(single){
                    break;
                }
            }
        }
        return spectra;
        
    }
    private static Spectrum legoPuncta(OrbisAestimationis oa){
        int longitudo = oa.longitudo();
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
        return spectrum;
    }
    
}
