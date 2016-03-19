/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor.forma;

import java.io.File;
import la.clamor.Consilium;
import la.clamor.Envelope;
import la.clamor.Functiones;
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
    public Formant(double band, double... freqs){
        filters = new IIRFilter[freqs.length];
        for(int i = 0;i < freqs.length;i++){
            filters[i] = IIRFilter.resonator(freqs[i], band);
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
        File out_file = new File(OmUtil.getDirectory("opus"), "formant2_i.wav");
        ScriptorWav sw = new ScriptorWav(out_file);
        Consilium c = new Consilium();
        double temps = 0;
        double duration = 3000;
        double freq = 100;
        c.addo(temps, new FormaLegibilis(new Referibile(new OscillatioPulse(false),
            new Envelope<>(new Punctum(freq)),
            duration),
            new Formant(100, 
                //800, 1200, 2500, 3500
                300, 2300, 2900, 3500
                // 300, 1200, 2500, 3500
                //500, 1900, 2500, 3500
                //500, 800, 2500, 3500
            )
        ));
        temps += duration;
        c.addo(temps, new FormaLegibilis(new Referibile(new OscillatioPulse(false),
            new Envelope<>(new Punctum(freq)),
            duration),
            new Formant(50, 
                //800, 1200, 2500, 3500
                300, 2300, 2900, 3500
                //300, 1200, 2500, 3500
                //500, 1900, 2500, 3500
                //500, 800, 2500, 3500
            )
        ));
        sw.scribo(c, false);
        Functiones.ludoLimam(out_file);
        
    }
    public static void _main(String[] args){
        File out_file = new File(OmUtil.getDirectory("opus"), "formant.wav");
        ScriptorWav sw = new ScriptorWav(out_file);
        Consilium c = new Consilium();
        double temps = 0;
        double duration = 400;
        double freq = 100;
        for(int i = 0;i < 16;i++){
            
            c.addo(temps, new FormaLegibilis(new Referibile(new OscillatioPulse(false),
                new Envelope<>(new Punctum(freq)),
                duration),
                new Formant(100, 
                    800, 1200, 2500, 3500
                    //300, 2300, 2900, 3500
                    //300, 1200, 2500, 3500
                    //500, 1900, 2500, 3500
                    //500, 800, 2500, 3500
                )
            ));
            temps += duration;
            c.addo(temps, new FormaLegibilis(new Referibile(new OscillatioPulse(false),
                new Envelope<>(new Punctum(freq)),
                duration),
                new Formant(100, 
                    //800, 1200, 2500, 3500
                    300, 2300, 2900, 3500
                    //300, 1200, 2500, 3500
                    //500, 1900, 2500, 3500
                    //500, 800, 2500, 3500
                )
            ));
            temps += duration;
            c.addo(temps, new FormaLegibilis(new Referibile(new OscillatioPulse(false),
                new Envelope<>(new Punctum(freq)),
                duration),
                new Formant(100, 
                    //800, 1200, 2500, 3500
                    //300, 2300, 2900, 3500
                    //300, 1200, 2500, 3500
                    //500, 1900, 2500, 3500
                    500, 800, 2500, 3500
                )
            ));
            temps += duration;
            c.addo(temps, new FormaLegibilis(new Referibile(new OscillatioPulse(false),
                new Envelope<>(new Punctum(freq)),
                duration),
                new Formant(100, 
                    800, 1200, 2500, 3500
                    //300, 2300, 2900, 3500
                    //300, 1200, 2500, 3500
                    //500, 1900, 2500, 3500
                    //500, 800, 2500, 3500
                )
            ));
            temps += duration;
            duration *= 0.8;
            freq *= 1.2;
            
        }
        sw.scribo(c, false);
        Functiones.ludoLimam(out_file);
        
    }


                
}
