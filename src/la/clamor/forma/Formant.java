/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor.forma;

import java.io.File;
import la.clamor.Envelope;
import la.clamor.Functiones;
import la.clamor.Positio;
import la.clamor.Punctum;
import la.clamor.io.ScriptorWav;
import la.clamor.referibile.OscillatioPulse;
import la.clamor.referibile.Referibile;
import origine_mundi.OmUtil;

/**
 *
 * @author mina
 */
public class Formant implements Forma{
    
    private final IIRFilter[] filters;
    private Punctum deenphasis;
    public Formant(double... freqs){
        filters = new IIRFilter[freqs.length];
        for(int i = 0;i < freqs.length;i++){
            filters[i] = IIRFilter.resonator(freqs[i], 100);
        }
        deenphasis = new Punctum();
    }
    @Override
    public Punctum formo(Punctum lectum) {
        Punctum reditum = new Punctum();
        for(IIRFilter filter:filters){
            reditum = reditum.addo(filter.formo(lectum));
        }
        reditum = reditum.addo(deenphasis.multiplico(0.98));
        deenphasis = reditum;
        //System.out.println(reditum);
        return reditum;
    }

    @Override
    public int resto() {
        return 0;
    }
    public static void main(String[] args){
        File out_file = new File(OmUtil.getDirectory("opus"), "formant.wav");
        ScriptorWav sw = new ScriptorWav(out_file);
        sw.scribo(new FormaLegibilis(new Referibile(new OscillatioPulse(false),
            new Envelope<>(new Punctum(100), 
                new Positio(1000, new Punctum(800))
            ),
            2000),
            new Formant(
                //800, 1200, 2500, 3500
                //300, 2300, 2900, 3500
                //300, 1200, 2500, 3500
                500, 1900, 2500, 3500
                //500, 800, 2500, 3500
            )
        
        ), false);
        Functiones.ludoLimam(out_file);
        
    }


    
}
