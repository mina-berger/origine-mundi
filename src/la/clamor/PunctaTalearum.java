/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor;

import java.util.HashMap;
import origine_mundi.ludior.Tempus;

/**
 *
 * @author user
 */
public class PunctaTalearum extends HashMap<Talea, Punctum> {
    public PunctaTalearum(Punctum initial){
        put(new Talea(), initial);
    }
    
    public Positiones capioPositiones(Tempus tempus, boolean unusEst){
        Positiones p = new Positiones(unusEst);
        for(Talea talea:keySet()){
            long positio = Functiones.adPositio(tempus.capioTempus(talea));
            if(positio != 0 && p.containsKey(positio)){
                throw new ExceptioClamoris("positio duplicated:" + talea + ":" + positio);
            }
            p.put(positio, get(talea));
        }
        return p;
    }
    
}
