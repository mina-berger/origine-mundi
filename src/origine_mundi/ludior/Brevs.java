/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package origine_mundi.ludior;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;

/**
 *
 * @author user
 */
public class Brevs extends ArrayList<Brev> {
    public Brevs(Brev... brevs){
        addAll(Arrays.asList(brevs));
    }
    public TreeSet<Integer> getDeviceSet(){
        TreeSet<Integer> set = new TreeSet<>();
        for(Brev brev:this){
            set.add(brev.getIunctum().getDevice());
        }
        return set;
    }
    public boolean containsNote(){
        for(Brev brev:this){
            if(brev.isNote()){
                return true;
            }
        }
        return false;
    }
    
}
