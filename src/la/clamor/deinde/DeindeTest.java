/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor.deinde;

import la.clamor.Functiones;
import la.clamor.Legibilis;
import la.clamor.OscillatioSimplex;
import la.clamor.Punctum;
import org.apache.commons.math3.complex.Complex;

/**
 *
 * @author mina
 */
public class DeindeTest {
    static Legibilis legibilis = new Legibilis(){
            OscillatioSimplex o = new OscillatioSimplex();
            int count= 0;
            double pitch = 500;
            @Override
            public Punctum lego() {
                count++;
                //pitch += 0.02;
                return o.lego(new Punctum(pitch), new Punctum(1));
            }

            @Override
            public boolean paratusSum() {
                return count < 144000;
            }
        };
    public static void main(String[] args){
        FourierSampler2 fs = new FourierSampler2(legibilis, 64, 0);
        for(Complex[] spectrum:fs.lego()){
            for(int i = 0;i < spectrum.length;i++){
                System.out.println(i + ":" + Functiones.toString(spectrum[i].getReal()) + ":" + Functiones.toString(spectrum[i].getImaginary()));
            }
            //spectrum.print(System.out);
        }
        //File out_file = new File(OmUtil.getDirectory("opus"), "oscillatio_sumplex.wav");
        //ScriptorWav sw = new ScriptorWav(out_file);
        //sw.scribo(new LowFi(legibilis, 0.5), false);
        //Functiones.ludoLimam(out_file);
        
    }
    
}
