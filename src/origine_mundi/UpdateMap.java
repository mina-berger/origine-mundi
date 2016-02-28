/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package origine_mundi;

import com.mina.util.Integers;
import java.util.HashMap;

/**
 *
 * @author user
 */
public class UpdateMap extends HashMap<String, Integers> {
    public UpdateMap(UpdateKV... kvs){
        for(UpdateKV kv:kvs){
            put(kv.key, kv.values);
        }
    }
    public Integers put(String key, int value){
        return put(key, new Integers(value));
    }
    public static class UpdateKV {
        String key;
        Integers values;
        public UpdateKV(String key, Integers values){
            this.key = key;
            this.values = values;
        }
        public UpdateKV(String key, int value){
            this(key, new Integers(value));
        }
    }
}
