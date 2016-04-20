/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor.forma;

import la.clamor.Envelope;
import la.clamor.Positio;
import la.clamor.Punctum;
import la.clamor.referibile.ModEnv;

/**
 *
 * @author mina
 */
public class VCA implements Forma {

    private final ModEnv quantitates;
    private long index;

    public VCA(Envelope<Punctum> quantitates) {
        this(new ModEnv(quantitates));
    }

    public VCA(ModEnv quantitates) {
        this.quantitates = quantitates;
        index = 0;
    }

    @Override
    public Punctum formo(Punctum lectum) {
        Punctum q = quantitates.capio(index);
        index++;
        return lectum.multiplico(q);
    }

    @Override
    public int resto() {
        return 0;
    }

    @Override
    public void ponoPunctum(int index, double tempus, Punctum punctum) {
        switch(index){
            case 0:
                quantitates.ponoPrimum(new Positio<>(tempus, punctum));
                break;
            default:
                throw new IllegalArgumentException("index not supported.");
        }
    }

    @Override
    public void close() {
    }

}
