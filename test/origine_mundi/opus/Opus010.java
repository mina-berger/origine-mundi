/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package origine_mundi.opus;

import java.io.File;
import la.clamor.Functiones;
import la.clamor.FunctionesLimae;
import la.clamor.LectorLimam;
import la.clamor.Punctum;
import la.clamor.ScriptorWav;
import la.clamor.effector.Chorus;
import origine_mundi.OmUtil;

/**
 *
 * @author user
 */
public class Opus010 {
     public static void main(String[] args){
        File out_file = new File(OmUtil.getDirectory("opus"), "opus010.wav");
        File wav_file = new File(OmUtil.getDirectory("opus"), "sample03.wav");
        
        File lima = new File(OmUtil.getDirectory("sample"), "sample03.lima");
        FunctionesLimae.facioLimam(wav_file, lima, new Punctum.Aestimatio(1), true);
            
        ScriptorWav sw = new ScriptorWav(out_file);
        sw.scribo(new Chorus(new LectorLimam(lima), new Punctum(0.3), new Punctum(3.5, -3.5), new Punctum(-1, 1), new Punctum(0.5, -0.5)), false);
        
        //sw.scribo(new LectorLimam(lima), false);
        Functiones.ludoLimam(out_file);
    }    
     public static void _main(String[] args){
        File wav_file = new File(OmUtil.getDirectory("sample"), "01d.wav");
        File lima_m = new File(OmUtil.getDirectory("sample"), "01dm.lima");
        File lima_w = new File(OmUtil.getDirectory("sample"), "01d.lima");
        File out_file = new File(OmUtil.getDirectory("opus"), "opus009_t.wav");
        
        FunctionesLimae.facioLimam(wav_file, lima_m, new Punctum.Aestimatio(1), true);
        ScriptorWav sw = new ScriptorWav(out_file);
        sw.scribo(new LectorLimam(lima_m), false);
 
        Functiones.ludoLimam(out_file);
        /*File lima_m = new File(OmUtil.getDirectory("sample"), "01dm.lima");
        File lima_w = new File(OmUtil.getDirectory("sample"), "01d.lima");
        LectorLimam llm = new LectorLimam(lima_m);
        LectorLimam llw = new LectorLimam(lima_w);
        int i = 0;
        boolean eofm = false;
        boolean eofw = false;
        while(true){
            Punctum m;
            if(llm.paratusSum()){
                m = llm.lego();
            }else{
                if(!eofm){
                    eofm = true;
                    System.out.println(i + ":m:eof");
                }
                m = new Punctum();
            }
            Punctum w;
            if(llw.paratusSum()){
                w = llw.lego();
            }else{
                if(!eofw){
                    eofw = true;
                    System.out.println(i + ":w:eof");
                }
                w = new Punctum();
            }
            if(!m.equals(w)){
                //System.out.println(i + ":" + m + ":" + w);
            }
            i++;
            if(eofm && eofw){
                break;
            }
            
        }*/
            
    }    
}
