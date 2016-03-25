/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor.deinde;

import java.util.TreeMap;
import la.clamor.Mergibilis;
import la.clamor.Punctum;

/**
 *
 * @author mina
 * @param <V>
 */
public class TestGenerique<V extends Mergibilis> extends TreeMap<Long, V> {
    public static void main(String[] args){
        TestGenerique<Punctum> tg = new TestGenerique<>();
        //tg.put
    }
//public class Positio<V extends Mergibilis> {
    
}
