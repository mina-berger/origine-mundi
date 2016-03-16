/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor;

/**
 *
 * @author user
 * @param <V>
 */
public class Positio<V extends Mergibilis> {
    long positio;
    V value;
    public Positio(double tempus, V value){
        positio = Functiones.adPositio(tempus);//(long)(tempus * REGULA_EXAMPLI_D / 1000d);
        this.value = value;
    }
    public long capioPositio(){
        return positio;
    }
    public V capioValue(){
        return value;
    }
}
