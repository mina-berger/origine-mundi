/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package origine_mundi.archive;


/**
 *
 * @author mina
 */
public class ArchiveUtil {
    public static int getNote(String name){
        if(name.contains(".")){
            name = name.substring(0, name.indexOf("."));
        }
        String note = name.substring(0, name.indexOf("_"));
        return Integer.parseInt(note, 12);
    }
    public static int getVelocity(String name){
        if(name.contains(".")){
            name = name.substring(0, name.indexOf("."));
        }
        String velocity = name.substring(name.indexOf("_") + 1, name.length());
        return Integer.parseInt(velocity, 16);
    }
    public static String getName(int note, int velocity){
        return toDodeciString(note) + "_" + toHexdeciString(velocity);
    }
    public static String toDodeciString(int i){
        return Integer.toHexString(i / 12) + Integer.toHexString(i % 12);
    }
    public static String toHexdeciString(int i){
        String str = Integer.toHexString(i);
        if(str.length() < 2){
            return "0" + str;
        }
        return str;
    }
    public static void trim(){
        
    }
    
}
