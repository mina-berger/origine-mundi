/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor.forma;

import java.io.File;
import la.clamor.Aestima;
import la.clamor.Constantia;
import la.clamor.Legibilis;
import la.clamor.Punctum;
import la.clamor.Res;
import la.clamor.io.IOUtil;
import la.clamor.io.LectorLimam;
import la.clamor.io.ScriptorLimam;
import la.clamor.io.ScriptorWav;
import static la.clamor.io.ScriptorWav.log;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author hiyamamina
 */
public class AutoLimitter implements FormaCapta {

    public static Log log = LogFactory.getLog(AutoLimitter.class);
    Aestima ratio;

    public AutoLimitter() {
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
        long longitudo = 0;
        while (fons.paratusSum()) {
            Punctum lectum = fons.lego();
            for (int i = 0; i < Res.publica.channel(); i++) {
                max = lectum.capioAestima(i).abs().max(max);
            }
            sl.scribo(lectum);
            longitudo++;
            if (longitudo % (Constantia.REGULA_EXAMPLI* 5) == 0) {
                log.info("lecti   : " + (longitudo / Constantia.REGULA_EXAMPLI) + " sec.(locus)");
            }
        }
        sl.close();
        if (!max.equals(new Aestima(0))) {
            log.info("max=" + max.toString());
            ratio = new Aestima(1).partior(max);
        }
        return new LectorLimam(file);
    }

}
