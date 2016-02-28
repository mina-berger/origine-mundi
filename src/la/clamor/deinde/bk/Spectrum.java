/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor.deinde.bk;

import java.io.PrintStream;
import static la.clamor.Constantia.REGULA_EXAMPLI;
import static la.clamor.Constantia.REGULA_EXAMPLI_D;
import la.clamor.Functiones;
import la.clamor.Puncta;
import la.clamor.Punctum;
import org.apache.commons.math3.util.FastMath;

/**
 *
 * @author mina
 */
public class Spectrum {
    Puncta real;
    Puncta imag;
    int longitudo;
    public Spectrum(int longitudo){
        this.longitudo = longitudo;
        real = new Puncta(longitudo);
        imag = new Puncta(longitudo);
    }
    public void add(int index, boolean is_real, Punctum value){
        Puncta puncta = is_real?real:imag;
        puncta.ponoPunctum(index, puncta.capioPunctum(index).addo(value));
        if(index == 1){
            //System.out.println("DEBUG:" + (is_real?value:new Punctum()) + ":" + (is_real?new Punctum():value));
            //System.out.println("     :" + real.capioPunctum(index) + ":" + imag.capioPunctum(index));
        }
    }
    public void print(PrintStream out, double sample_rate){
        for(int i = 1;i < longitudo / 2;i++){
            Punctum amp = getAmp(i);
            //S//ystem.out.println("DEBUG:" + amp.toString());
            if(!Functiones.approximateZero(amp.partior(longitudo / 2.), 0.05)){
                out.println(
                        Functiones.toString((double)i * sample_rate / (double)longitudo) + 
                        " amp:" + getAmp(i).partior(longitudo / 2.).toString() + 
                        " phase=" + getPhase(i).partior(FastMath.PI).toString());
            }
        }
    }
    public double getLoudFrequency(double sample_rate){
        Integer index = null;
        Double max = null;
        for(int i = 1;i < longitudo / 2;i++){
            double amp = getAmp(i).average().abs().doubleValue();
            if(index == null || max < amp){
                index = i;
                max = amp;
            }
        }
        if(index == null){
            return 0;
        }
        if(index == 1 && longitudo / 2 == 2){
            return (double)index * sample_rate / (double)longitudo;
        }
        int index_2;
        double max_2;
        if(index == 1){
            index_2 = 2;
            max_2 = getAmp(index_2).average().abs().doubleValue();
        }else if(index == longitudo / 2 - 1){
            index_2 = index - 1;
            max_2 = getAmp(index_2).average().abs().doubleValue();
        }else{
            double max_sub = getAmp(index - 1).average().abs().doubleValue();
            double max_sup = getAmp(index + 1).average().abs().doubleValue();
            if(max_sub > max_sup){
                index_2 = index - 1;
                max_2 = max_sub;
            }else{
                index_2 = index + 1;
                max_2 = max_sup;
            }
        }
        double d_index = (index_2 - index) * max_2 / (max + max_2) + index;
        return d_index * sample_rate / (double)longitudo;
    }
    public double getFrequency(int index, double sample_rate){
        return (double)index * sample_rate / (double)longitudo;
    }
    public Punctum getAmp(int index){
        Punctum p_real = real.capioPunctum(index);
        Punctum p_imag = imag.capioPunctum(index);
        return p_real.pow(2).addo(p_imag.pow(2)).sqrt();
    }
    public Punctum getPhase(int index){
        Punctum p_real = real.capioPunctum(index);
        Punctum p_imag = imag.capioPunctum(index);
        return Punctum.atan2(p_imag, p_real);
    }
    
}
