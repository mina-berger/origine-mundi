/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor.deinde;

import la.clamor.referibile.OscillatioPulse;
import java.io.File;
import la.clamor.Envelope;
import la.clamor.Legibilis;
import la.clamor.Positio;
import la.clamor.Punctum;
import la.clamor.referibile.Referibile;
import la.clamor.referibile.Referibilis;
import la.clamor.Res;
import la.clamor.forma.IIRFilter;
import la.clamor.io.ScriptorWav;
import la.clamor.referibile.OscillatioFrag;
import origine_mundi.OmUtil;

/**
 *
 * @author mina
 */
public class IIROscillatio implements Legibilis {
    private final Referibile referibile;
    private final Envelope<Punctum> quantitates;
    private final Envelope<Punctum> filters;
    private final IIRFilter[] iirs;
    public IIROscillatio(Referibilis referibilis, Envelope<Punctum> frequentiae, Envelope<Punctum> quantitates, Envelope<Punctum> filters){
        this.referibile = new Referibile(referibilis, frequentiae, capioFalsum(quantitates));
        this.quantitates = quantitates;
        this.filters = filters;
        this.iirs = new IIRFilter[Res.publica.channel()];
        Punctum primo_filter = filters.capioValue(0);
        for(int i = 0;i < Res.publica.channel();i++){
            iirs[i] = new IIRFilter(primo_filter.capioAestimatio(i).doubleValue(), true);
        }
    }
    private static Envelope<Punctum> capioFalsum(Envelope<Punctum> env){
        Envelope falsum = new Envelope<>(new Punctum(1));
        falsum.put(env.lastKey(), new Punctum(1));
        return falsum;
    }

    @Override
    public Punctum lego() {
        long index = referibile.capioIndecem();
        Punctum lectum = referibile.lego();
        Punctum reditum = new Punctum();
        
        for(int i = 0;i < Res.publica.channel();i++){
            double freq = filters.capioValue(index).capioAestimatio(i).doubleValue();
            iirs[i].rescribo(freq, true);
            reditum.ponoAestimatio(i, iirs[i].formo(new Punctum(lectum)).capioAestimatio(i));
        }
        //reditum = lectum;
        return reditum.multiplico(quantitates.capioValue(index));
    }

    @Override
    public boolean paratusSum() {
        return referibile.paratusSum();
    }

    @Override
    public void close() {
        referibile.close();
    }
    public static void _main(String[] args){
        Res.publica.ponoChannel(4);
        File out_file = new File(OmUtil.getDirectory("opus"), "iir_osc.wav");
        ScriptorWav sw = new ScriptorWav(out_file);
        sw.scribo(new IIROscillatio(
            new OscillatioPulse(false), 
            new Envelope<>(new Punctum(500)), 
            new Envelope<>(new Punctum(), 
                new Positio(50, new Punctum(1, 0, 0, 0)), 
                new Positio(1000, new Punctum(0, 1, 0, 0)), 
                new Positio(2000, new Punctum(0, 0, 0, 1)), 
                new Positio(3000, new Punctum(0, 0, 1, 0)), 
                new Positio(4000, new Punctum(1, 0, 0, 0)), 
                new Positio(5000, new Punctum(0))),
            new Envelope<>(new Punctum(500))
                //new Positio(2000., new Punctum(100))
            ), false);
    }
    public static void main(String[] args){
        File out_file = new File(OmUtil.getDirectory("opus"), "iir_osc1.wav");
        ScriptorWav sw = new ScriptorWav(out_file);
        sw.scribo(new IIROscillatio(
            new OscillatioFrag(), 
            //new OscillatioPulse(false), 
            new Envelope<>(new Punctum(500),
                //new Positio(50, new Punctum(5000)), 
                //new Positio(500, new Punctum(300)), 
                //new Positio(1000, new Punctum(5000)), 
                //new Positio(1500, new Punctum(300)), 
                //new Positio(2000, new Punctum(5000)), 
                new Positio(2500, new Punctum(50))
            ), 
            new Envelope<>(new Punctum(), 
                new Positio(50, new Punctum(1)), 
                new Positio(3000, new Punctum(1)), 
                new Positio(4000, new Punctum(0))),
            new Envelope<>(new Punctum(500),
                new Positio(2000., new Punctum(100)))
            ), false);
        /*
        /*
        sw.scribo(new IIROscillatio(
            new PulseOscillatio(), 
            new Envelope<>(new Punctum(500), 
                new Positio(3000, new Punctum(500, 750, 1000, 1500))), 
            new Envelope<>(new Punctum(), 
                new Positio(50, new Punctum(1, 0, 0, 0)), 
                new Positio(1000, new Punctum(0, 1, 0, 0)), 
                new Positio(2000, new Punctum(0, 0, 0, 1)), 
                new Positio(3000, new Punctum(0, 0, 1, 0)), 
                new Positio(4000, new Punctum(0))),
            new Envelope<>(new Punctum(500), 
                new Positio(500., new Punctum(1200)), 
                new Positio(1000., new Punctum(500)), 
                new Positio(1500., new Punctum(1200)), 
                new Positio(2000., new Punctum(500)), 
                new Positio(2500., new Punctum(1200)), 
                new Positio(3000., new Punctum(500)), 
                new Positio(3500., new Punctum(1200))
                //new Positio(2000., new Punctum(100))
            )), false);
        
        
        */
        
        //Functiones.ludoLimam(out_file);
        //Functiones.ludoLimam(new File("/Users/mina/Downloads/chapter07/ex7_1/ex7_1.wav"));

        /*Envelope<Punctum> env = new Envelope<>(new Punctum(), 
                new Positio(50, new Punctum(1, 0, 0, 0)), 
                new Positio(1000, new Punctum(0, 1, 0, 0)), 
                new Positio(2000, new Punctum(0, 0, 0, 1)), 
                new Positio(3000, new Punctum(0, 0, 1, 0)), 
                new Positio(4000, new Punctum(0)));
        System.out.println(env.capioValue(48000));*/

    }
    
}
