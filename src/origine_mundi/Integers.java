/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package origine_mundi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;

/**
 *
 * @author user
 */
public class Integers extends ArrayList<Integer> {
    boolean termine = false;
    public Integers(int integer){
        add(integer);
    }
    public Integers(int... ints){
        addAll(Arrays.asList(ArrayUtils.toObject(ints)));
    }
    public Integers(List<Integer> ints){
        addAll(ints);
    }
    public int[] toPrimitiveArray(){
        return ArrayUtils.toPrimitive(toArray(new Integer[]{}));
    }
    public Integers append(int integer){
        if(termine){
            throw new OmException("terminated");
        }
        add(integer);
        return this;
    }
    public Integers append(int... ints){
        if(termine){
            throw new OmException("terminated");
        }
        addAll(Arrays.asList(ArrayUtils.toObject(ints)));
        return this;
    }
    public Integers append(List<Integer> ints){
        if(termine){
            throw new OmException("terminated");
        }
        addAll(ints);
        return this;
    }
    public void terminate(){
        termine = true;
    }
    public static Integers fillValue(int value, int length){
        Integers ints = new Integers();
        while(ints.size() < length){
            ints.append(value);
        }
        return ints;
    }
    
}
