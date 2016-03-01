/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor.deinde.bk;

import java.util.ArrayList;
import la.clamor.Constantia;
import la.clamor.Functiones;
import la.clamor.Legibilis;
import la.clamor.Punctum;
import la.clamor.SineOscillatio;

/**
 *
 * @author mina
 */
public class OscillatioSpectrum implements Legibilis, Constantia {
    //Spectrum spectrum;
    long longitudo;
    long count;
    ArrayList<SineLegibilis> legibiles;

    public OscillatioSpectrum(Spectrum spectrum, double temps){
        //this.spectrum = spectrum;
        longitudo = Functiones.adPositio(temps);
        count = 0;
        legibiles = new ArrayList<>();
        for(int i = 0;i < spectrum.longitudo;i++){
            Punctum amp = spectrum.getAmp(i);
            if(Functiones.approximateZero(amp)){
                continue;
            }
            legibiles.add(new SineLegibilis(spectrum.getFrequency(i, REGULA_EXAMPLI_D), amp));
        }
    }

    @Override
    public Punctum lego() {
        count++;
        Punctum punctum = new Punctum();
        for(SineLegibilis legibilis:legibiles){
            punctum = punctum.addo(legibilis.lego());
        }
        return punctum;
    }

    @Override
    public boolean paratusSum() {
        return count < longitudo;
    }

    @Override
    public void close() {
        for(SineLegibilis legibilis:legibiles){
            legibilis.close();
        }
    }
    static class SineLegibilis implements Legibilis {
        SineOscillatio so;
        Punctum pitch;
        Punctum amp;
        
        public SineLegibilis(double pitch, Punctum amp){
            so = new SineOscillatio();
            this.pitch = new Punctum(pitch);
            this.amp = amp;
        }

        @Override
        public Punctum lego() {
            return so.lego(pitch, amp);
        }

        @Override
        public boolean paratusSum() {
            return true;
        }

        @Override
        public void close() {
        }
        
    }
    
}
