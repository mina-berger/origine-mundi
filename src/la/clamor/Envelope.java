/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor;

import java.util.Map.Entry;
import java.util.TreeMap;

/**
 *
 * @author mina
 * @param <V>
 */
public class Envelope<V extends Mergibilis<V>> extends TreeMap<Long, V> {

    public Envelope() {
    }

    public Envelope(Positio<V>... positiones) {
        ponoPositiones(positiones);
    }

    /*public Envelope(boolean unusEst, Positio<Punctum>... positiones) {
        put(0l, unusEst ? new Punctum(1) : new Punctum());
        ponoPositiones(positiones);
    }*/

    public Envelope(V initial, Positio<V>... positiones) {
        put(0l, initial);
        ponoPositiones(positiones);
    }

    public final void ponoPositiones(Positio<V>... positiones) {
        for (Positio<V> positio : positiones) {
            put(positio.capioPositio(), positio.capioValue());
        }
    }

    public void ponoValue(double temps, V value) {
        put(Functiones.adPositio(temps), value);
    }

    public V capioValue(long index) {
        if (index < 0) {
            throw new ExceptioClamoris("out of index:" + index);
        }
        long index_solum;
        long index_tectum;
        Mergibilis value_solum;
        Mergibilis value_tectum;
        if (containsKey(index)) {
            index_solum = index;
            index_tectum = index + 1;
            value_solum = get(index);
            value_tectum = get(index);
        } else {
            Entry<Long, V> solum = floorEntry(index);
            Entry<Long, V> tectum = ceilingEntry(index);
            if (tectum == null) {
                index_solum = solum.getKey();
                index_tectum = index;
                value_solum = solum.getValue();
                value_tectum = solum.getValue();
            } else {
                index_solum = solum.getKey();
                index_tectum = tectum.getKey();
                value_solum = solum.getValue();
                value_tectum = tectum.getValue();
            }
        }
        return (V) value_solum.mergo(index_tectum - index_solum, index - index_solum, value_tectum);

        /*Punctum punctum = new Punctum();
        Aestimatio diff = new Aestimatio(index_tectum - index_solum);
        for (int i = 0; i < CHANNEL; i++) {
            punctum.ponoAestimatio(i,
                value_solum.capioAestimatio(i).multiplico(new Aestimatio(index_tectum - index)).addo(
                value_tectum.capioAestimatio(i).multiplico(new Aestimatio(index - index_solum))).partior(diff));
        }
        return punctum;*/
    }
}
