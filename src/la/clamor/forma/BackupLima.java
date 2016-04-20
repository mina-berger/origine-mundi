/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor.forma;

import java.io.File;
import la.clamor.Punctum;
import la.clamor.io.ScriptorLimam;
import la.clamor.io.ScriptorWav;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author hiyamamina
 */
public class BackupLima implements Forma {
    public static Log log = LogFactory.getLog(BackupLima.class);
    ScriptorLimam sl;
    File lima;
    public BackupLima(File lima){
        this.lima = lima;
        sl = new ScriptorLimam(lima);
    }

    @Override
    public Punctum formo(Punctum lectum) {
        sl.scribo(lectum);
        return lectum;
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
        sl.close();
        log.info("backedup:" + lima.getAbsolutePath());
    }
    
}
