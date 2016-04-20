/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import la.clamor.ExceptioClamoris;
import la.clamor.Punctum;
import la.clamor.Res;

/**
 *
 * @author user
 */
public class ScriptorLimam {

    private ObjectOutputStream o_out;
    private FileOutputStream f_out;

    public ScriptorLimam(File lima) {
        try {
            f_out = new FileOutputStream(lima);
            o_out = new ObjectOutputStream(f_out);
        } catch (IOException ex) {
            throw new ExceptioClamoris(ex);
        }
    }

    public void scribo(Punctum punctum) {
        for (int k = 0; k < Res.publica.channel(); k++) {
            try {
                o_out.writeDouble(punctum.capioAestima(k).doubleValue());
            } catch (IOException ex) {
                throw new ExceptioClamoris(ex);
            }
        }
    }

    public void close() {
        try {
            o_out.flush();
            o_out.close();
            f_out.close();
        } catch (IOException ex) {
            throw new ExceptioClamoris(ex);
        }
    }

}
