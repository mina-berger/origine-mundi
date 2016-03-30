/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor.io;

import java.io.File;
import la.clamor.Functiones;
import static la.clamor.Functiones.isMac;

/**
 *
 * @author hiyamamina
 */
public class IOUtil {
    public static File createTempFile(String prefix){
        
        return new File(getTempPath(), prefix + "_" + Long.toString(System.currentTimeMillis()));
    }
    public static boolean clearTempFiles(){
        for(File file:getTempPath().listFiles()){
            file.delete();
        }
        return true;
    }

    public static String getArchivePath(){
        return isMac()?"/Users/hiyamamina/drive/doc/origine_mundi/archive/":"D:/origine_mundi/archive/";
    }
    public static String getHomePath(){
        return isMac()?"/Users/hiyamamina/drive/":"C://drive/";
    }
    public static File getTempPath(){
        return getDirectory("temp/");
    }
    public static File getDirectory(String subdir) {
        File dir = new File(getHomePath() + "doc/origine_mundi/" + subdir);
        dir.mkdirs();
        return dir;
    }
    
}
