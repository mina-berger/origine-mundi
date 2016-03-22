/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor.forma;

import java.io.File;
import la.clamor.Functiones;
import la.clamor.Punctum;
import la.clamor.io.LectorWav;
import la.clamor.io.ScriptorWav;
import origine_mundi.OmUtil;

/**
 *
 * @author mina
 */
public class Equalizer implements Forma{
    private static final double Q = 2.0;
    private IIRFilter[] filters;
    public Equalizer(
        double g_31_25, double g_62_5, double g_125, double g_250, double g_500,
        double g_1k, double g_2k, double g_4k, double g_8k, double g_16k){
        filters = new IIRFilter[10];
        filters[0] = IIRFilter.peaking(   31.25, Q, g_31_25);
        filters[1] = IIRFilter.peaking(   62.5,  Q, g_62_5);
        filters[2] = IIRFilter.peaking(  125,    Q, g_125);
        filters[3] = IIRFilter.peaking(  250,    Q, g_250);
        filters[4] = IIRFilter.peaking(  500,    Q, g_500);
        filters[5] = IIRFilter.peaking( 1000,    Q, g_1k);
        filters[6] = IIRFilter.peaking( 2000,    Q, g_2k);
        filters[7] = IIRFilter.peaking( 4000,    Q, g_4k);
        filters[8] = IIRFilter.peaking( 8000,    Q, g_8k);
        filters[9] = IIRFilter.peaking(16000,    Q, g_16k);
    }

    @Override
    public Punctum formo(Punctum lectum) {
        Punctum reditum = new Punctum(lectum);
        for(IIRFilter filter:filters){
            reditum = filter.formo(reditum);
        }
        return reditum;
    }

    @Override
    public int resto() {
        return 0;
    }
    public static void main(String[] args){
        File in_file = new File("/Users/mina/drive/doc/sound_material/book2/chapter07/ex7_2/sample04.wav");
        //File in_file = new File(OmUtil.getDirectory("opus"), "filter2.wav");
        LectorWav lw = new LectorWav(in_file);
        File out_file = new File(OmUtil.getDirectory("opus"), "equalizer.wav");
        //File out_file = new File(OmUtil.getDirectory("opus"), "iir_456.wav");
        ScriptorWav sw = new ScriptorWav(out_file);
        //sw.scribo(cns, false);
        sw.scribo(new FormaLegibilis(lw, 
            //new Equalizer(0., 0., 0., 0., 0., 0., 0., 0., 0., 0.)
            new Equalizer(0., 2., 2., 0., -0.5, -0.5, -1., -1., 8., 8.)
        ), false);

        //Functiones.ludoLimam(in_file);
        //Functiones.ludoLimam(out_file);
        
        
    }
    
}
