/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor.forma;

import java.io.File;
import la.clamor.Aestima;
import la.clamor.Legibilis;
import la.clamor.Punctum;
import la.clamor.Res;
import la.clamor.io.IOUtil;
import la.clamor.io.LectorLimam;
import la.clamor.io.ScriptorLimam;

/**
 *
 * @author hiyamamina
 */
public class AutoLimitter implements FormaCapta{
    Aestima ratio;
    public AutoLimitter(){
        ratio = new Aestima(1);
    }

    @Override
    public Punctum formo(Punctum lectum) {
        return lectum.multiplico(ratio);
    }

    @Override
    public int resto() {
        return 0;
    }

    @Override
    public void ponoPunctum(int index, double tempus, Punctum punctum) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void close() {
        
    }

    @Override
    public Legibilis capio(Legibilis fons) {
        File file = IOUtil.createTempFile("auto_limitter");
        ScriptorLimam sl = new ScriptorLimam(file);
        Aestima max = new Aestima(0);
        while(fons.paratusSum()){
            Punctum lectum = fons.lego();
            for(int i = 0;i < Res.publica.channel();i++){
                max = lectum.capioAestima(i).abs().max(max);
            }
            sl.scribo(lectum);
        }
        sl.close();
        if(!max.equals(new Aestima(0))){
            System.out.println("DEBUG:AutoLimitter:" + max.toString());
            ratio = new Aestima(1).partior(max);
        }
        return new LectorLimam(file);
        /*
        if (aestima.equals(new Aestima(0))) {
            return;
        }
        int value = (int) FastMath.round(aestima.abs().doubleValue() * 100);
        if (counter.containsKey(value)) {
            counter.put(value, counter.get(value) + 1);
        } else {
            counter.put(value, 1l);
        }
        
        */
    }
    
}
