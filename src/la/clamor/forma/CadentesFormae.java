/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor.forma;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import la.clamor.Functiones;
import la.clamor.Legibilis;
import la.clamor.Punctum;
import la.clamor.SineOscillatio;
import la.clamor.io.ScriptorWav;
import origine_mundi.OmUtil;

/**
 *
 * @author mina
 */
public class CadentesFormae extends ArrayList<Forma> {
    
    public CadentesFormae(Forma... formae){
        addAll(Arrays.asList(formae));
    }
    
    public Legibilis capioLegibilis(Legibilis fons){
        Legibilis l = fons;
        for(Forma forma:this){
            l = new FormaLegibilis(l, forma);
        }
        return l;
    }
     public static void main(String[] args){
        File out_file = new File(OmUtil.getDirectory("opus"), "cadentes.wav");
         
        ScriptorWav sl = new ScriptorWav(out_file);
        CadentesFormae cf = new CadentesFormae(
            new Distortion(new Punctum(1), new Punctum(1.1), new Punctum(1)),
            new Chorus(new Punctum(1), new Punctum(3), new Punctum(1), new Punctum(1, -1)));
        sl.scribo(cf.capioLegibilis(new Legibilis(){
            SineOscillatio o = new SineOscillatio();
            int count= 0;
            @Override
            public Punctum lego() {
                count++;
                return o.lego(new Punctum(440), new Punctum(1));
            }

            @Override
            public boolean paratusSum() {
                return count < 144000;
            }
        }), false);        
        Functiones.ludoLimam(out_file);
    }    
    
}
