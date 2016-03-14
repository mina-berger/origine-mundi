/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor.deinde;

import java.io.File;
import la.clamor.Envelope;
import la.clamor.Functiones;
import la.clamor.Legibilis;
import la.clamor.Positio;
import la.clamor.Punctum;
import la.clamor.Referibile;
import la.clamor.Referibilis;
import la.clamor.forma.IIRFilter;
import la.clamor.io.ScriptorWav;
import origine_mundi.OmUtil;

/**
 *
 * @author mina
 */
public class IIROscillatio implements Legibilis {
    private Referibile referibile;
    private Envelope freqs;
    private IIRFilter filter;
    public IIROscillatio(Referibilis referibilis, Envelope freqs){
        this.referibile = new Referibile(referibilis, 
            new Envelope(new Punctum(500), new Positio(3000, new Punctum(500))), 
            new Envelope(new Punctum(), 
                new Positio(50, new Punctum(1)), 
                //new Positio(2000, new Punctum(0.5)), 
                new Positio(3000, new Punctum(0))));
        this.freqs = freqs;
        filter = new IIRFilter(freqs.capioPunctum(0).capioAestimatio(0).doubleValue(), true);
    }

    @Override
    public Punctum lego() {
        long index = referibile.capioIndecem();
        Punctum lectum = referibile.lego();
        double freq = freqs.capioPunctum(index).capioAestimatio(0).doubleValue();
        //System.out.println(freq);
        filter.rescribo(freq, true);
        lectum = filter.formo(lectum);
        return lectum;
    }

    @Override
    public boolean paratusSum() {
        return referibile.paratusSum();
    }

    @Override
    public void close() {
        referibile.close();
    }
    public static void main(String[] args){
        File out_file = new File(OmUtil.getDirectory("opus"), "iir_osc.wav");
        ScriptorWav sw = new ScriptorWav(out_file);
        sw.scribo(new IIROscillatio(
            new PulseOscillatio(), 
            new Envelope(new Punctum(12000), 
                new Positio(1000., new Punctum(1200)), 
                new Positio(3000., new Punctum(50)))), false);
        
        Functiones.ludoLimam(out_file);
        Functiones.ludoLimam(new File("/Users/mina/Downloads/chapter07/ex7_1/ex7_1.wav"));
    }
    
}
