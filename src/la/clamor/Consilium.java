/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor;

import java.util.ArrayList;
import java.util.TreeMap;
import org.apache.commons.lang3.ArrayUtils;

/**
 *
 * @author minae.hiyamae
 */
public class Consilium extends TreeMap<Long, ArrayList<Legibilis>> implements Legibilis {

    Legibilis[] pueros;
    long index;
    Envelope<Punctum> volumes;

    public Consilium() {
        pueros = new Legibilis[0];
        volumes = new Envelope<>(new Punctum(1));
        index = 0;
    }

    public void setPositiones(Envelope<Punctum> volumes) {
        this.volumes = volumes;
    }

    public void addo(double tempus, Legibilis legibilis) {
        long positio = Functiones.adPositio(tempus);
        ArrayList<Legibilis> list;
        if (containsKey(positio)) {
            list = get(positio);
        } else {
            list = new ArrayList<>();
            put(positio, list);
        }
        list.add(legibilis);
    }

    @Override
    public Punctum lego() {
        Punctum punctum = new Punctum();
        for (Legibilis legibilis : pueros) {
            punctum = punctum.addo(legibilis.lego());
        }
        Punctum volume = volumes.capioValue(index);
        index++;
        return punctum.multiplico(volume);
    }

    @Override
    public boolean paratusSum() {
        if (containsKey(index)) {
            get(index).stream().forEach((legibilis) -> {
                pueros = ArrayUtils.add(pueros, legibilis);
            });
            remove(index);
            //System.out.println("this.removed:" + size() + "/" + pueros.length);
        }
        for (Legibilis legibilis : pueros) {
            if (!legibilis.paratusSum()) {
                pueros = ArrayUtils.removeElement(pueros, legibilis);
                //System.out.println("puer.removed:" + size() + "/" + pueros.length);
            }
        }
        //System.out.println(isEmpty() + ":" + pueros.length);
        return !isEmpty() || pueros.length > 0;
    }

    @Override
    public void close() {
        for (Legibilis puer : pueros) {
            puer.close();
        }
    }
  public static void main(String[] args) {
double half_tone = Math.pow(2.0, 1.0 / 12.0);
double fifth = Math.pow(half_tone, 7);
System.out.println(fifth);
    /*double note1 = 440;
    double note2 = note1 * Math.pow(Math.pow(2., 1. / 12.), 7);
    Positiones.PositionesFixi p1 = new Positiones.PositionesFixi(
      Constantia.Unda.SINE, 1, 0,
      new Envelope(new Punctum(note1), new Positio(3000, new Punctum(note1)), new Positio(3500, new Punctum(note1))),
      new Envelope(new Punctum(0), new Positio(15, new Punctum(1)), new Positio(3000, new Punctum(1)), new Positio(3500, new Punctum(0))),
      new ArrayList<>(),//new Positio[]{new Positio(0, new Punctum(1, 0)), new Positio(3500, new Punctum(0, 1))},
      new Envelope(),
      new Envelope(),
      new Envelope(),
      new Envelope(),
      new Envelope());
    Oscillatio o1 = new Oscillatio(p1);
    Positiones.PositionesFixi p2 = new Positiones.PositionesFixi(
      Constantia.Unda.SINE, 1, 0,
      new Envelope(new Punctum(note2), new Positio(3000, new Punctum(note2)), new Positio(3500, new Punctum(note2))),
      new Envelope(new Punctum(0), new Positio(15, new Punctum(1)), new Positio(3000, new Punctum(1)), new Positio(3500, new Punctum(0))),
      new ArrayList<>(),//new Positio[]{new Positio(0, new Punctum(1, 0)), new Positio(3500, new Punctum(0, 1))},
      new Envelope(),
      new Envelope(),
      new Envelope(),
      new Envelope(),
      new Envelope());
    Oscillatio o2 = new Oscillatio(p2);
    Consilium c = new Consilium();
    c.addo(0, o1);
    c.addo(1000, o2);
    File out_file = new File(IOUtil.getDirectory("opus"), "sine_440_660_equal.wav");
    ScriptorWav sw = new ScriptorWav(out_file);
    sw.scribo(c, false);
    Functiones.ludoLimam(out_file);*/
  }
}
