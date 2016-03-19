/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor;

import java.io.File;
import java.util.Map.Entry;
import java.util.TreeMap;
import la.clamor.forma.Amplitudo;
import la.clamor.forma.FormaLegibilis;
import la.clamor.io.ScriptorWav;
import la.clamor.referibile.OscillatioSine;
import la.clamor.referibile.Referibile;
import org.apache.commons.math3.util.FastMath;
import origine_mundi.OmUtil;

/**
 *
 * @author mina
 * @param <V>
 */
public class Envelope<V extends Mergibilis<V>> extends TreeMap<Long, V> {
    
    private boolean exponent;

    public Envelope() {
        exponent = false;
    }

    public Envelope(Positio<V>... positiones) {
        ponoPositiones(positiones);
        exponent = false;
    }

    public Envelope(V initial, Positio<V>... positiones) {
        this(false, initial, positiones);
        //put(0l, initial);
        //ponoPositiones(positiones);
    }
    public Envelope(boolean exponent, V initial, Positio<V>... positiones) {
        put(0l, initial);
        ponoPositiones(positiones);
        this.exponent = exponent;
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
        long part_length = index_tectum - index_solum;
        long part_index = index - index_solum;
        if(exponent){
            //long old = part_index;
            part_index = FastMath.round(
                (1. - FastMath.exp(-5. * (double)part_index / (double)part_length)) * (double)part_length);
            //System.out.println(((double)old / (double)part_length) + ":" + ((double)part_index / (double)part_length));
            //System.out.println(index_tectum + ":" + index_solum);
        }
        return (V) value_solum.mergo(part_length, part_index, value_tectum);

        /*Punctum punctum = new Punctum();
        Aestimatio diff = new Aestimatio(index_tectum - index_solum);
        for (int i = 0; i < CHANNEL; i++) {
            punctum.ponoAestimatio(i,
                value_solum.capioAestimatio(i).multiplico(new Aestimatio(index_tectum - index)).addo(
                value_tectum.capioAestimatio(i).multiplico(new Aestimatio(index - index_solum))).partior(diff));
        }
        return punctum;*/
    }
    public static void main(String[] args) {
        //Res.publica.ponoChannel(4);
        File out_file = new File(OmUtil.getDirectory("opus"), "env.wav");
        ScriptorWav sw = new ScriptorWav(out_file);
        sw.scribo(new FormaLegibilis(new Referibile(new OscillatioSine(),
            new Envelope<>(new Punctum(220)),4000),
            new Amplitudo(new Envelope<>(true,
                new Punctum(0),
                new Positio<>(500, new Punctum(1)),
                new Positio<>(1500, new Punctum(0.5)),
                new Positio<>(2500, new Punctum(0.5)),
                new Positio<>(4000, new Punctum(0.0))
            ))), false);
        Functiones.ludoLimam(out_file);

    }}
