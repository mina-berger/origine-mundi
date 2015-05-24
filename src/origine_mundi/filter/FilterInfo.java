/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package origine_mundi.filter;

import la.clamor.Legibilis;
import la.clamor.Aestimatio;
import origine_mundi.ProcessorInfo;

/**
 *
 * @author user
 */
public abstract class FilterInfo implements ProcessorInfo {
    public static class ThruInfo extends FilterInfo {
        
    }

    public static class FIRInfo extends FilterInfo {
        double cutoff_freq;
        boolean lpf;
        boolean hamming;
        Aestimatio volume;
        public FIRInfo(double cutoff_freq, boolean lpf, boolean hamming, Aestimatio volume){
            this.cutoff_freq = cutoff_freq;
            this.lpf = lpf;
            this.hamming = hamming;
            this.volume = volume;
        }
    }
    public static Legibilis getFilter(Legibilis legibilis, FilterInfo info){
        
        if(info instanceof ThruInfo){
            return legibilis;
        }else if(info instanceof FIRInfo){
            FIRInfo fir_info = (FIRInfo)info;
            return new FIRFilter(legibilis, fir_info.cutoff_freq, fir_info.lpf, fir_info.hamming, fir_info.volume);
        }
        return legibilis;
        
    }
    
    
}
