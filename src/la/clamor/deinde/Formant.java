/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor.deinde;

import java.io.File;
import la.clamor.Envelope;
import la.clamor.Functiones;
import la.clamor.Positio;
import la.clamor.Punctum;
import la.clamor.forma.IIRFilter;
import la.clamor.io.ScriptorWav;
import la.clamor.referibile.OscillatioPulse;
import la.clamor.referibile.Referibile;
import la.clamor.referibile.Referibilis;
import origine_mundi.OmUtil;

/**
 *
 * @author mina
 */
public class Formant implements Referibilis{
    
    private final IIRFilter[] filters;
    private final Referibilis referibilis;
    private Punctum deenphasis;
    public Formant(Referibilis referibilis, double... freqs){
        this.referibilis = referibilis;
        filters = new IIRFilter[freqs.length];
        for(int i = 0;i < freqs.length;i++){
            filters[i] = IIRFilter.resonator(freqs[i], 100);
        }
        deenphasis = new Punctum();
    }

    @Override
    public Punctum lego(Punctum frequentia, Punctum quantitas) {
        Punctum lectum = referibilis.lego(frequentia, new Punctum(1));
        //System.out.println(lectum);
        Punctum reditum = new Punctum();
        for(IIRFilter filter:filters){
            reditum = reditum.addo(filter.formo(lectum));
        }
        reditum = reditum.addo(deenphasis.multiplico(0.98));
        deenphasis = reditum;
        //System.out.println(reditum);
        return reditum.multiplico(quantitas);
        
    }
    public static void main(String[] args){
        File out_file = new File(OmUtil.getDirectory("opus"), "formant2.wav");
        ScriptorWav sw = new ScriptorWav(out_file);
        sw.scribo(new Referibile(
            new Formant(new OscillatioPulse(false), 800, 1200, 2500, 3500),
            new Envelope<>(new Punctum(100), new Positio(3000, new Punctum(100))),
            new Envelope<>(new Punctum(1))), false);

        Functiones.ludoLimam(out_file);
        
    }
    
}
