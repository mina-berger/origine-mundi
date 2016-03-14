/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor;

import org.apache.commons.math3.util.FastMath;

/**
 *
 * @author mina
 */
public class Referibile implements Legibilis {
    private final Referibilis referibilis;
    private final Envelope frequentiae;
    private final Envelope quantitates;
    private long index;
    private final long length;
    
    public Referibile(Referibilis referibilis, Envelope frequentiae, Envelope quantitates){
        this.referibilis = referibilis;
        this.frequentiae = frequentiae;
        this.quantitates = quantitates;
        index = 0;
        length = FastMath.max(frequentiae.lastKey(), quantitates.lastKey());
    }

    @Override
    public Punctum lego() {
        Punctum lectum = referibilis.lego(frequentiae.capioPunctum(index), quantitates.capioPunctum(index));
        index++;
        return lectum;
    }

    @Override
    public boolean paratusSum() {
        return index < length;
    }

    @Override
    public void close() {
    }
    
    public long capioIndecem(){
        return index;
    }
}
