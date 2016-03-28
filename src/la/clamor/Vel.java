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
public class Vel implements Mergibilis<Vel> {
    Punctum primo;
    VelocitasMap map;
    //boolean unus_est;
    public Vel(double primo_value){
        this(new Punctum(primo_value));
    }
    public Vel(Punctum primo){
        this.primo = primo;
        map = new VelocitasMap();
    }
    public Vel pono(int index, Punctum velocitas){
        map.put(index, velocitas);
        return this;
    }
    public Vel pono(int index, double velocitas){
        map.put(index, new Punctum(velocitas));
        return this;
    }
    public Vel multiplico(double value){
        Vel v = new Vel(primo.multiplico(value));
        for(Integer i:map.keySet()){
            v.pono(i, map.get(i).multiplico(value));
        }
        return v;
    }

    @Override
    public Vel mergo(long diff, long index, Vel tectum) {
        Vel reditum = new Vel(primo.mergo(diff, index, tectum.primo));
        for(Integer i:map.keySet()){
            reditum.map.put(i, 
                    map.get(i).mergo(diff, index, tectum.map.containsKey(i)?tectum.map.get(i):new Punctum()));
        }
        for(Integer i:tectum.map.keySet()){
            if(reditum.map.containsKey(i)){
                continue;
            }
            reditum.map.put(i, 
                    new Punctum().mergo(diff, index, tectum.map.get(i)));
        }
        return reditum;
    }

    @Override
    public Vel multiplico(Vel factor) {
        Vel reditum = new Vel(primo.multiplico(factor.primo));
        for(Integer i:map.keySet()){
            reditum.map.put(i, 
                    map.get(i).multiplico(factor.map.containsKey(i)?factor.map.get(i):new Punctum()));
        }
        return reditum;
    }

    @Override
    public Vel partior(Vel partitor) {
        Vel reditum = new Vel(primo.partior(partitor.primo));
        for(Integer i:map.keySet()){
            reditum.map.put(i, 
                    map.get(i).partior(partitor.map.containsKey(i)?partitor.map.get(i):new Punctum()));
        }
        return reditum;
    }

    @Override
    public Vel addo(Vel additor) {
        Vel reditum = new Vel(primo.addo(additor.primo));
        for(Integer i:map.keySet()){
            reditum.map.put(i, 
                    map.get(i).addo(additor.map.containsKey(i)?additor.map.get(i):new Punctum()));
        }
        for(Integer i:additor.map.keySet()){
            if(reditum.map.containsKey(i)){
                continue;
            }
            reditum.map.put(i, 
                    new Punctum().addo(additor.map.get(i)));
        }
        return reditum;
    }

    @Override
    public Vel subtraho(Vel subtractor) {
        Vel reditum = new Vel(primo.subtraho(subtractor.primo));
        for(Integer i:map.keySet()){
            reditum.map.put(i, 
                    map.get(i).subtraho(subtractor.map.containsKey(i)?subtractor.map.get(i):new Punctum()));
        }
        for(Integer i:subtractor.map.keySet()){
            if(reditum.map.containsKey(i)){
                continue;
            }
            reditum.map.put(i, 
                    new Punctum().subtraho(subtractor.map.get(i)));
        }
        return reditum;
    }

    @Override
    public Vel nego() {
        Vel reditum = new Vel(primo.nego());
        for(Integer i:map.keySet()){
            reditum.map.put(i, map.get(i).nego());
        }
        return reditum;
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
