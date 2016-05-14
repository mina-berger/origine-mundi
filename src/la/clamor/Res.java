/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor;

import java.util.HashMap;

/**
 *
 * @author mina
 */
public class Res{
    public static Res publica = new Res();
    private final HashMap<String, Object> map;
    public Res(){
        map = new HashMap<>();
        map.put("CHANNEL", 2);
    }
    public int channel(){
        return (Integer)map.get("CHANNEL");
    }
    public void ponoChannel(int channel){
        map.put("CHANNEL", channel);
        
    }
    
}
