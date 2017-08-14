/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor.opus;

import com.mina.util.Doubles;
import org.apache.commons.lang3.ArrayUtils;

/**
 *
 * @author hiyamamina
 */
public class Chord {
    Doubles bass;
    Doubles[] chords; 
    public Chord(Doubles bass, Doubles chord_primum, Doubles... chords){
        this.bass = bass;
        this.chords = ArrayUtils.addAll(new Doubles[]{chord_primum}, chords);
    }
    public double capioBass(int index){
        return bass.get(index);
    }
    public Doubles capioChord(){
        return capioChord(0);
    }
    public Doubles capioChord(int index){
        return chords[index];
    }
}
