/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor.record;

import java.io.File;
import java.io.IOException;
import static java.lang.Integer.toHexString;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import la.clamor.Aestima;
import la.clamor.forma.CadentesFormae;
import la.clamor.forma.MonoOut;
import la.clamor.io.FunctionesLimae;
import la.clamor.io.IOUtil;
import la.clamor.io.LectorLimam;
import la.clamor.io.LectorWav;
import la.clamor.io.ScriptorLimam;
import la.clamor.io.ScriptorWav;
import static origine_mundi.archive.ArchiveUtil.toDodeciString;

/**
 *
 * @author hiyamamina
 */
public class RecordEditor {
    public static void main(String[] args) throws IOException{
        //FunctionesLimae(new File(IOUtil.getDirectory("edit"), "sd.wav"));
        trimWav(new File(IOUtil.getDirectory("edit"), "k.wav"), new Aestima(0.02));
        //trimWav(new File(IOUtil.getDirectory("edit"), "tomm.wav"), new Aestima(0.01));
        //trimWav(new File(IOUtil.getDirectory("edit"), "toml.wav"), new Aestima(0.01));
        //archive(new File(IOUtil.getDirectory("edit"), "bd.wav"), "voixdrum", 36, 127);
        //archive(new File(IOUtil.getDirectory("edit"), "sd.wav"), "voixdrum", 40, 127);
        //File file = new File(IOUtil.getDirectory("edit"), "rin2.wav");
        //archive(file, "voixdrum", 62, 127);
        //archive(new File(IOUtil.getDirectory("edit"), "hic.wav"), "voixdrum", 42, 127);
        //archive(new File(IOUtil.getDirectory("edit"), "hio.wav"), "voixdrum", 46, 127);
        //archive(new File(IOUtil.getDirectory("edit"), "tomh.wav"), "voixdrum", 48, 127);
        //archive(new File(IOUtil.getDirectory("edit"), "tomm.wav"), "voixdrum", 47, 127);
        //archive(new File(IOUtil.getDirectory("edit"), "toml.wav"), "voixdrum", 45, 127);*/
        //FunctionesLimae.formoWav(file, new CadentesFormae(new MonoOut()), true);
        //Functiones.ludoLimam(file);
    }
    public static void archive(File wav, String sound, int note, int velocity) throws IOException{
        File dir = new File(IOUtil.getArchivePath() + "record" + "/" + sound);
        dir.mkdirs();
        File file = new File(dir, toDodeciString(note) + "_" + toHexString(velocity) + ".wav");
        Files.copy(wav.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        
    }
    public static void trimWav(File wav, Aestima min){
        LectorWav lw = new LectorWav(wav);
        File lima = new File(wav.getParentFile(), wav.getName() + ".lima");
        ScriptorLimam sl = new ScriptorLimam(lima);
        while(lw.paratusSum()){
            sl.scribo(lw.lego());
        }
        lw.close();
        sl.close();
        FunctionesLimae.trim(lima, min);
        FunctionesLimae.renameBackup(wav, "wav");
        ScriptorWav sw = new ScriptorWav(wav);
        sw.scribo(new LectorLimam(lima), false);
    }
    
}
