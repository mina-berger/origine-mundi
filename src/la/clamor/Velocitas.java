/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package la.clamor;

import java.util.TreeMap;

/**
 *
 * @author minae.hiyamae
 */
public class Velocitas {
    Punctum primo;
    VelocitasMap map;
    //boolean unus_est;
    public Velocitas(double primo_value){
        this(new Punctum(primo_value));
    }
    public Velocitas(Punctum primo){
        this.primo = primo;
        map = new VelocitasMap();
    }
    public Velocitas pono(int index, Punctum velocitas){
        map.put(index, velocitas);
        return this;
    }
    public Velocitas pono(int index, double velocitas){
        map.put(index, new Punctum(velocitas));
        return this;
    }
    public Velocitas multiplico(double value){
        Velocitas v = new Velocitas(primo.multiplico(value));
        for(Integer i:map.keySet()){
            v.pono(i, map.get(i).multiplico(value));
        }
        return v;
    }
    public static class VelocitasMap extends TreeMap<Integer, Punctum> {
    }
    public Punctum capio(Integer index){
        if(map.containsKey(index)){
            return map.get(index);
        }else{
            return primo;
        }
    }
}
