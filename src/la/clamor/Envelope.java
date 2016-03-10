/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor;

import java.util.Map.Entry;
import java.util.TreeMap;
import static la.clamor.Constantia.CHANNEL;

/**
 *
 * @author mina
 */
public class Envelope extends TreeMap<Long, Punctum> {

    public Envelope() {
    }

    public Envelope(Positio... positiones) {
        ponoPositiones(positiones);
    }

    public Envelope(boolean unusEst, Positio... positiones) {
        put(0l, unusEst ? new Punctum(1) : new Punctum());
        ponoPositiones(positiones);
    }

    public Envelope(Punctum initial, Positio... positiones) {
        put(0l, initial);
        ponoPositiones(positiones);
    }

    public final void ponoPositiones(Positio... positiones) {
        for (Positio positio : positiones) {
            put(positio.capioPositio(), positio.capioPunctum());
        }
    }

    public void ponoPunctum(double temps, Punctum punctum) {
        put(Functiones.adPositio(temps), punctum);
    }

    public Punctum capioPunctum(long index) {
        if (index < 0) {
            throw new ExceptioClamoris("out of index:" + index);
        }
        long index_solum;
        long index_tectum;
        Punctum punctum_solum;
        Punctum punctum_tectum;
        if (containsKey(index)) {
            index_solum = index;
            index_tectum = index + 1;
            punctum_solum = get(index);
            punctum_tectum = get(index);
        } else {
            Entry<Long, Punctum> solum = floorEntry(index);
            Entry<Long, Punctum> tectum = ceilingEntry(index);
            if (tectum == null) {
                index_solum = solum.getKey();
                index_tectum = index;
                punctum_solum = solum.getValue();
                punctum_tectum = solum.getValue();
            } else {
                index_solum = solum.getKey();
                index_tectum = tectum.getKey();
                punctum_solum = solum.getValue();
                punctum_tectum = tectum.getValue();
            }
        }

        Punctum punctum = new Punctum();
        Aestimatio diff = new Aestimatio(index_tectum - index_solum);
        for (int i = 0; i < CHANNEL; i++) {
            punctum.ponoAestimatio(i,
                punctum_solum.capioAestimatio(i).multiplico(new Aestimatio(index_tectum - index)).addo(
                punctum_tectum.capioAestimatio(i).multiplico(new Aestimatio(index - index_solum))).partior(diff));
        }
        return punctum;
    }
}
