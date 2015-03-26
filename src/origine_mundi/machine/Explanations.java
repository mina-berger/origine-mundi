/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package origine_mundi.machine;

import java.util.ArrayList;
import origine_mundi.OmUtil;
import origine_mundi.machine.Explanations.Explanation;

/**
 *
 * @author Mina
 */
public class Explanations extends ArrayList<Explanation> {
    private String name;
    public Explanations(String name, Explanation... expls){
        this.name = name;
        for(Explanation expl:expls){
            add(expl);
        }
    }
    public int getMaxNameWidth(){
        int length = 0;
        for(Explanation expl:this){
            length = Math.max(length, expl.getName().length());
        }
        return length;
    }
    public void print(){
        for(String str:getOutStrings()){
            System.out.println(str);
        }
    }
    public ArrayList<String> getOutStrings(){
        int width = getMaxNameWidth();
        ArrayList<String> list = new ArrayList<>();
        list.add("[" + name + "]");
        for(Explanation expl:this){
            list.add(" " + OmUtil.fill(expl.getName(), ' ', false, width) + " : " + expl.getValue());
        }
        return list;
    }
    public static class Explanation {
        private String name;
        private String value;
        public Explanation(String name, String value){
            this.name = name;
            this.value = value;
        }
        public String getName(){
            return name;
        }
        public String getValue(){
            return value;
        }
    }
}
