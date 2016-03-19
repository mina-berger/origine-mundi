/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor.forma;

import la.clamor.Envelope;
import la.clamor.Punctum;

/**
 *
 * @author mina
 */
public class Amplitudo implements Forma {

    private final Envelope<Punctum> quantitates;
    private long index;

    public Amplitudo(Envelope<Punctum> quantitates) {
        this.quantitates = quantitates;
        index = 0;
    }

    @Override
    public Punctum formo(Punctum lectum) {
        Punctum q = quantitates.capioValue(index);
        index++;
        return lectum.multiplico(q);
    }

    @Override
    public int resto() {
        return 0;
    }

}
