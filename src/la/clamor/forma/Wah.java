/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor.forma;

import la.clamor.referibile.OscillatioPulse;
import java.io.File;
import la.clamor.Envelope;
import la.clamor.Positio;
import la.clamor.Punctum;
import la.clamor.Res;
import la.clamor.io.IOUtil;
import la.clamor.io.ScriptorWav;
import la.clamor.referibile.ModEnv;
import la.clamor.referibile.OscillatioQuad;
import la.clamor.referibile.Referibile;
import origine_mundi.OmUtil;

/**
 *
 * @author mina
 */
public class Wah implements Forma {
    private final ModEnv filters;
    private final IIRFilter[] iirs;
    private int index;
    private final int band;
    public Wah(Punctum primum){
        this(new ModEnv(
                        new Envelope<>(primum),
                        new Envelope<>(new Punctum(0)),
                        new Envelope<>(new Punctum(0))));
    }
    public Wah(ModEnv filters){
        this.filters = filters;
        this.iirs = new IIRFilter[Res.publica.channel()];
        band = 100;
        Punctum primo_filter = filters.capio(0);
        for(int i = 0;i < Res.publica.channel();i++){
            iirs[i] = IIRFilter.resonator(primo_filter.capioAestimatio(i).doubleValue(), band);
        }
        index = 0;
    }
    public void ponoPositio(double tempus, Punctum value){
        filters.ponoPrimum(new Positio(tempus, value));
        
    }

    @Override
    public Punctum formo(Punctum lectum) {
        Punctum reditum = new Punctum();
        for(int i = 0;i < Res.publica.channel();i++){
            double freq = filters.capio(index).capioAestimatio(i).doubleValue();
            iirs[i].rescriboResonator(freq, band);
            reditum.ponoAestimatio(i, iirs[i].formo(new Punctum(lectum)).capioAestimatio(i));
        }
        index++;
        return reditum;
    }

    @Override
    public int resto() {
        return 0;
    }

    public static void _main(String[] args){
        Res.publica.ponoChannel(4);
        File out_file = new File(IOUtil.getDirectory("opus"), "iir_osc.wav");
        ScriptorWav sw = new ScriptorWav(out_file);
        sw.scribo(CadentesFormae.capioLegibilis(new Referibile(new OscillatioPulse(false), new Envelope<>(new Punctum(500)), 5000),
            //new Wah(new Envelope<>(new Punctum(500))),
            new VCA(new Envelope<>(new Punctum(), 
                new Positio(50, new Punctum(1, 0, 0, 0)), 
                new Positio(1000, new Punctum(0, 1, 0, 0)), 
                new Positio(2000, new Punctum(0, 0, 0, 1)), 
                new Positio(3000, new Punctum(0, 0, 1, 0)), 
                new Positio(4000, new Punctum(1, 0, 0, 0)), 
                new Positio(5000, new Punctum(0))))
            ), false);
            
        
    }
    public static void main(String[] args){
        double value = 10000;
        Envelope<Punctum> env = new Envelope<>(new Punctum(value));
        for(int i = 0;i < 40;i++){
            value *= 0.9;
            env.ponoPositiones(new Positio<>(i * 100, new Punctum(value)));
        }
        File out_file = new File(IOUtil.getDirectory("opus"), "iir_osc1.wav");
        ScriptorWav sw = new ScriptorWav(out_file);
        sw.scribo(CadentesFormae.capioLegibilis(new Referibile(new OscillatioQuad(), new Envelope<>(new Punctum(500)), 5000),
            new Wah(new ModEnv(
                    new Envelope<>(new Punctum(1000)),
                    new Envelope<>(new Punctum(2)),
                    new Envelope<>(new Punctum(0.8))
            )), 
            new VCA(new Envelope<>(true, new Punctum(), 
                new Positio(50, new Punctum(1))/*, 
                new Positio(3000, new Punctum(1)), 
                new Positio(4000, new Punctum(0))*/))), false);
    }
    
}
