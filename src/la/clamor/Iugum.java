/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package la.clamor;

import la.clamor.Aestimatio;
import static la.clamor.Constantia.CHANNEL;
import la.clamor.Functiones;
import la.clamor.Punctum;

/**
 *
 * @author minae.hiyamae
 */
public class Iugum {
    Integer index;
    //Integer channel;
    Punctum altum;
    Punctum humile;
    double tempus;
    public Iugum(double tempus, Punctum altum){
        //this(tempus, null, null, altum, altum);
        this(tempus, null, altum, altum);
    }
    public Iugum(double tempus, Punctum altum, Punctum humile){
        //this(tempus, null, null, altum, humile);
        this(tempus, null, altum, humile);
    }
    public Iugum(double tempus, Integer index, /*Integer channel, */Punctum altum, Punctum humile){
        if(altum == null){
            throw new IllegalArgumentException("altum should not be null");
        }
        this.index   = index;
        //this.channel = channel;
        this.altum   = altum;
        this.humile  = humile == null?altum:humile;
        this.tempus  = tempus;
    }
    public void print(){
        System.out.println(String.format("tempus=%s, index=%s, altum=%s, humile=%s", tempus, index == null?"null":index.toString(), altum, humile));
    }
    public Punctum capioPunctum(Velocitas velocitas){
        //return capioPunctumStaticus(altum, humile, velocitas);
        //hv + l(1 - v)
        Punctum punctum = new Punctum();
        Punctum p_velocitas = velocitas.capio(index);
        for(int i = 0;i < CHANNEL;i++){
            Aestimatio a_velocitas = p_velocitas.capioAestimatio(i);
            punctum.ponoAestimatio(i, 
                    altum.capioAestimatio(i).multiplico(a_velocitas).addo(
                    humile.capioAestimatio(i).multiplico(new Aestimatio(1).subtraho(a_velocitas))));
        }
        return punctum;
    }
    public int capioIndicem(){
        return index;
    }
    //public Integer capioChannel(){
    //    return channel;
    //}
    public double capioTempus(){
        return tempus;
    }
    public long capioPositio(){
        return Functiones.adPositio(tempus);
    }
}

