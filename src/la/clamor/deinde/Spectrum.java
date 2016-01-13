/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor.deinde;

import java.io.PrintStream;
import la.clamor.Puncta;
import la.clamor.Punctum;

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
            System.out.println("DEBUG:" + (is_real?value:new Punctum()) + ":" + (is_real?new Punctum():value));
            System.out.println("     :" + real.capioPunctum(index) + ":" + imag.capioPunctum(index));
        }
    }
    public void print(PrintStream out){
        for(int i = 0;i < longitudo;i++){
            out.println(i + ":" + real.capioPunctum(i).toString() + ", j" + imag.capioPunctum(i).toString());
        }
        
    }
    
}
