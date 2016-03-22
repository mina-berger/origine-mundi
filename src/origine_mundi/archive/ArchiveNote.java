/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package origine_mundi.archive;

import java.io.File;
import la.clamor.Legibilis;
import la.clamor.Punctum;
import la.clamor.io.LectorWav;

/**
 *
 * @author mina
 */
public class ArchiveNote implements Legibilis{
    LectorWav lw;
    Punctum volume;
    long length;
    long decay;
    long index;
    ArchiveNote(File note_file, Punctum volume, long length, long decay){
        lw = new LectorWav(note_file, length + decay);
        this.volume = volume;
        this.length = length;
        this.decay = decay;
        index = 0;
    }

    @Override
    public Punctum lego() {
        if(!lw.paratusSum() || index >= length + decay){
            index++;
            return new Punctum();
        }
        //System.out.println(index + ":" + decay + ":" + length);
        Punctum lectum = lw.lego().multiplico(volume);
        if(index < length){
            index++;
            return lectum;
        }
        lectum = lectum.multiplico((double)(length + decay - index) / (double)decay);
        index++;
        return lectum;
    }

    @Override
    public boolean paratusSum() {
        return index < length + decay;
    }

    @Override
    public void close() {
        lw.close();
    }
    
}
