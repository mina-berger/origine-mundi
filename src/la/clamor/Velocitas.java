/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package la.clamor;

import java.util.HashMap;
import la.clamor.Punctum;

/**
 *
 * @author minae.hiyamae
 */
public class Velocitas {
    Punctum primo;
    VelocitasMap map;
    //boolean unus_est;
    public static Velocitas una(double velocitas){
        Velocitas v = new Velocitas();
        v.primo = new Punctum(velocitas);
        v.map = new VelocitasMap();
        return v;
    }
    public static Velocitas multae(double velocitas, VelocitasMap map){
        Velocitas v = new Velocitas();
        v.primo = new Punctum(velocitas);
        v.map = map;
        return v;
    }
    /*@Deprecated
    public static Velocitas multae(Aestimatio[] velocitatis){
        Velocitas v = new Velocitas();
        v.primo = new Punctum(velocitatis[0]);
        v.map = new VelocitasMap();
        if(velocitatis.length <= 1){
            for(int i = 1;i < velocitatis.length;i++){
                v.map.put(i - 1, new Punctum(velocitatis[i]));
            }
        }
        return v;
    }*/
    public static class VelocitasMap extends HashMap<Integer, Punctum> {
        
    }
    public Punctum capio(Integer index){
        if(map.containsKey(index)){
            return map.get(index);
        }else{
            return primo;
        }
    }
}
