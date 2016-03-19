/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor.referibile;

import la.clamor.Envelope;
import la.clamor.Functiones;
import la.clamor.Legibilis;
import la.clamor.Punctum;

/**
 *
 * @author mina
 */
public class Referibile implements Legibilis {

    private final Referibilis referibilis;
    private final Envelope<Punctum> frequentiae;
    private long index;
    private final long length;

    public Referibile(Referibilis referibilis, Envelope<Punctum> frequentiae, double tempus) {
        this.referibilis = referibilis;
        this.frequentiae = frequentiae;
        index = 0;
        length = Functiones.adPositio(tempus);
    }

    @Override
    public Punctum lego() {
        Punctum lectum = referibilis.lego(frequentiae.capioValue(index));
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

    public long capioIndecem() {
        return index;
    }
}
