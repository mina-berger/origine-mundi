/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package la.clamor;

import la.clamor.deinde.bk.PositionesDeprec;
import java.util.ArrayList;
import java.util.TreeMap;
import org.apache.commons.lang3.ArrayUtils;

/**
 *
 * @author minae.hiyamae
 */
public class Consilium extends TreeMap<Long, ArrayList<Legibilis>> implements Legibilis {
    Legibilis[] pueros;
    long index;
    PositionesDeprec volumes;
    public Consilium(){
        pueros = new Legibilis[0];
        volumes = new PositionesDeprec(true);
        index = 0;
    }
    public void setPositiones(PositionesDeprec volumes){
        this.volumes = volumes;
    }
    
    public void addo(double tempus, Legibilis legibilis){
        long positio = Functiones.adPositio(tempus);
        ArrayList<Legibilis> list;
        if(containsKey(positio)){
            list = get(positio);
        }else{
            list = new ArrayList<>();
            put(positio, list);
        }
        list.add(legibilis);
    }
    @Override
    public Punctum lego() {
        Punctum punctum = new Punctum();
        for(Legibilis legibilis:pueros){
            punctum = punctum.addo(legibilis.lego()).multiplico(volumes.capioPunctum(index));
        }
        index++;
        return punctum;
    }

    @Override
    public boolean paratusSum() {
        if(containsKey(index)){
            get(index).stream().forEach((legibilis) -> {
                pueros = ArrayUtils.add(pueros, legibilis);
            });
            remove(index);
            //System.out.println("this.removed:" + size() + "/" + pueros.length);
        }
        for(Legibilis legibilis:pueros){
            if(!legibilis.paratusSum()){
                pueros = ArrayUtils.removeElement(pueros, legibilis);
                //System.out.println("puer.removed:" + size() + "/" + pueros.length);
            }
        }
        //System.out.println(isEmpty() + ":" + pueros.length);
        return !isEmpty() || pueros.length > 0;
    }
}
