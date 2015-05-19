/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor;

/**
 *
 * @author user
 */
public class Positio {
    long positio;
    Punctum punctum;
    public Positio(double tempus, Punctum punctum){
        positio = Functiones.adPositio(tempus);//(long)(tempus * REGULA_EXAMPLI_D / 1000d);
        this.punctum = punctum;
    }
    public long capioPositio(){
        return positio;
    }
    public Punctum capioPunctum(){
        return punctum;
    }
}
