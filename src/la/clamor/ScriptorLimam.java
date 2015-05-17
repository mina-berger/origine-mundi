/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import static la.clamor.Constantia.CHANNEL;

/**
 *
 * @author user
 */
public class ScriptorLimam {
    private ObjectOutputStream o_out;
    private FileOutputStream f_out;
    public ScriptorLimam(File lima){
        //if(lima.exists()){
        //    lima.delete();
        //}
        try {
            f_out = new FileOutputStream(lima);
            o_out = new ObjectOutputStream(f_out);
        } catch (IOException ex) {
            throw new ExceptioClamoris(ex);
        }
    }
    public void scribo(Punctum punctum){
        for(int k = 0;k < CHANNEL;k++){
            try {
                o_out.writeDouble(punctum.capioAestimatio(k).rawValue());
            } catch (IOException ex) {
                throw new ExceptioClamoris(ex);
            }
        }
    }
    public void close(){
        try {
            o_out.flush();
            o_out.close();
            f_out.close();
        } catch (IOException ex) {
            throw new ExceptioClamoris(ex);
        }
    }
    
}
