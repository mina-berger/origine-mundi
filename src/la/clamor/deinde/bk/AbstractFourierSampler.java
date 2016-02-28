/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor.deinde.bk;

import java.util.ArrayList;
import static la.clamor.Constantia.REGULA_EXAMPLI_D;
import la.clamor.Functiones;
import la.clamor.Legibilis;
import la.clamor.OrbisPuncti;
import la.clamor.Puncta;
import la.clamor.Punctum;

/**
 *
 * @author mina
 */
public abstract class AbstractFourierSampler {
    private final Marker marker;
    private final int length;
    private final int interval;
    //private final boolean single;
    //private int index;
    //ArrayList<Puncta> results;
    
    public AbstractFourierSampler(Legibilis fons, int length, int interval){
        this.marker = new Marker(fons);
        marker.mark();
        this.length = length;
        this.interval = Math.max(length, interval);
        //single = interval <= 0;
        //index = 0;
        //results = new ArrayList<>();
    }
    protected int getLength(){
        return length;
    }
    
    public ArrayList<Spectrum> lego(){
        ArrayList<Spectrum> spectra = lego(marker, length, interval);
        marker.back();
        Spectrum spectrum;
        spectrum = spectra.get(0);
        spectrum.print(System.out, REGULA_EXAMPLI_D);
        System.out.println(spectrum.getLoudFrequency(REGULA_EXAMPLI_D));
                    
        /*double base_freq = spectrum.getLoudFrequency(REGULA_EXAMPLI_D);
        
        int sampling = 32;
        spectra = lego(new Resampler(marker, 439 * 2, 1), sampling, 0);
        spectrum = spectra.get(0);
        spectrum.print(System.out, sampling);*/
        //System.out.println(spectrum.getLoudFrequency(32));
        
        //;
        return spectra;
        
    }
    private ArrayList<Spectrum> lego(Legibilis legibilis, int longitudo, int interval){
        boolean single = interval <= 0;
        ArrayList<Spectrum> spectra = new ArrayList();
        OrbisPuncti oa = new OrbisPuncti(longitudo);
        double[] w = Functiones.hanningWindow(longitudo);
        int index = 0;
        while(true){
            oa.pono(legibilis.paratusSum()?legibilis.lego():new Punctum());
            index++;
            if(index == longitudo){
                spectra.add(legoPuncta(oa, w));
                if(single || !legibilis.paratusSum()){
                    break;
                }
            }
            if(!single && index >= interval){
                index = 0;
            }
        }
        return spectra;
    }
    private Spectrum legoPuncta(OrbisPuncti oa, double[] w){
        int longitudo = oa.longitudo();
        //Spectrum spectrum = new Spectrum(longitudo);
        Puncta sample = new Puncta(longitudo); 
        //Puncta sample_imag = new Puncta(longitudo); 
        for(int n = 0;n < longitudo;n++){
            //sample.ponoPunctum(n, oa.capio(longitudo - 1 - n));
            sample.ponoPunctum(n, oa.capio(length - 1 - n).multiplico(w[n]));
        }
        return transform(sample);
    }
    public abstract Spectrum transform(Puncta sample);
}
