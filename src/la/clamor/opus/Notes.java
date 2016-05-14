/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor.opus;

import com.mina.util.Doubles;

/**
 *
 * @author hiyamamina
 */
public class Notes extends Doubles {
    public Notes(double root, double... intervals){
        this(false, root, intervals);
    }
    public Notes(boolean include_root, double root, double... intervals){
        if(include_root){
            add(root);
        }
        for(double interval:intervals){
            add(root + interval);
        }
    }
    
}
