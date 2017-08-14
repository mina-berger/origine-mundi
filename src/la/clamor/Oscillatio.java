package la.clamor;

import la.clamor.io.ScriptorWav;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import la.clamor.Constantia.Unda;
import static la.clamor.Constantia.Unda.DENT;
import static la.clamor.Constantia.Unda.FRAG;
import static la.clamor.Constantia.Unda.QUAD;
import static la.clamor.Constantia.Unda.SINE;
import static la.clamor.Constantia.Unda.TRIA;
import la.clamor.Positiones.PositionesFixi;
import la.clamor.io.IOUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.util.FastMath;

/**
 * Oscillation with duration specified, but frequency nor volume unspecified
 *
 * @author minae.hiyamae
 */
public class Oscillatio implements Constantia, Legibilis {

  double t;
  Punctum y_1;
  Punctum y_2;
  int count_buffer;
  Punctum delta;

  /**
   * setting for this oscillatio
   */
  Positiones positiones;

  Oscillatio[] modulatores;

  public Oscillatio(Positiones positiones) {
    this.positiones = positiones;
    //System.out.println(positiones);

    t = 1d / Res.publica.sampleRateDouble();
    y_1 = new Punctum();
    y_2 = new Punctum();
    count_buffer = 2;

    delta = new Punctum();

    modulatores = new Oscillatio[0];
    for (Positiones positiones_modulationis : positiones.capioModulatores()) {
      addoModulator(new Oscillatio(positiones_modulationis));
    }
  }

  public final void addoModulator(Oscillatio modulator) {
    modulatores = ArrayUtils.add(modulatores, modulator);
  }

  @Override
  public Punctum lego() {
    //index++;

    Punctum punctum = new Punctum();
    if (count_buffer == 2) {
      count_buffer--;
      positiones.deinde();
      return punctum;
    }

    Punctum frequentia = positiones.capioFrequentiae();
    Punctum quantitas = positiones.capioQuantitatis();
    Punctum[] pans = positiones.capioPans();
    Unda unda = positiones.capioUndam();
    //double volume      = positiones.capioVolume();
    Punctum feedback = positiones.capioFeedback();
    Punctum modulatio = new Punctum();
    for (Oscillatio modulator : modulatores) {
      if (modulator.paratusSum()) {
        modulatio = modulatio.addo(modulator.lego());
      }
    }
    modulatio = modulatio.addo(y_1.multiplico(feedback));

    Punctum deinde = new Punctum();
    if (count_buffer == 1) {
      count_buffer--;
      for (int i = 0; i < Res.publica.channel(); i++) {
        Aestima omega_t = frequentia.capioAestima(i).multiplico(new Aestima(2 * FastMath.PI * t));
        Aestima delta_t = delta.capioAestima(i).addo(frequentia.capioAestima(i).multiplico(new Aestima(t)));
        switch (unda) {
          case SINE:
            Aestima _aestimatio = new Aestima(FastMath.sin(omega_t.doubleValue()));
            Aestima _modulatio = modulatio.capioAestima(i);//.addo(_aestimatio.multiplico(new Aestimatio(feedback)));
            deinde.ponoAestimatio(i, _aestimatio);
            punctum.ponoAestimatio(i, new Aestima(FastMath.sin(omega_t.addo(_modulatio).doubleValue())));
            break;
          case QUAD:
            deinde.ponoAestimatio(i, undaQuad(delta_t));
            punctum.ponoAestimatio(i, undaQuad(delta_t));
            break;
          case DENT:
            deinde.ponoAestimatio(i, undaDent(delta_t));
            punctum.ponoAestimatio(i, undaDent(delta_t));
            break;
          case TRIA:
            deinde.ponoAestimatio(i, undaTria(delta_t));
            punctum.ponoAestimatio(i, undaTria(delta_t));
            break;
          case FRAG:
            deinde.ponoAestimatio(i, undaFrag());
            punctum.ponoAestimatio(i, undaFrag());
            break;
          default:
            throw new IllegalArgumentException("unknown unda:" + unda);

        }
        delta.ponoAestimatio(i, delta_t);
      }
    } else {
      for (int i = 0; i < Res.publica.channel(); i++) {
        Aestima omega_t = frequentia.capioAestima(i).multiplico(new Aestima(2 * FastMath.PI * t));
        Aestima delta_t = delta.capioAestima(i).addo(frequentia.capioAestima(i).multiplico(new Aestima(t)));
        switch (unda) {
          case SINE:
            //Aestimatio _aestimatio = new Aestimatio(FastMath.sin(omega_t.doubleValue()));
            //System.out.println(FastMath.sin(delta_t.doubleValue()) + ":" + feedback);
            Aestima _modulatio = modulatio.capioAestima(i);//.addo(_aestimatio.multiplico(new Aestimatio(feedback)));
            Aestima b1_d = new Aestima(2).multiplico(new Aestima(FastMath.cos(omega_t.doubleValue())));
            deinde.ponoAestimatio(i, b1_d.multiplico(y_1.capioAestima(i)).subtraho(y_2.capioAestima(i)));

            Aestima b1_m = new Aestima(2).multiplico(new Aestima(FastMath.cos(omega_t.addo(_modulatio).doubleValue())));
            punctum.ponoAestimatio(i, b1_m.multiplico(y_1.capioAestima(i)).subtraho(y_2.capioAestima(i)));
            break;
          case QUAD:
            deinde.ponoAestimatio(i, undaQuad(delta_t));
            punctum.ponoAestimatio(i, undaQuad(delta_t));
            break;
          case DENT:
            deinde.ponoAestimatio(i, undaDent(delta_t));
            punctum.ponoAestimatio(i, undaDent(delta_t));
            break;
          case TRIA:
            deinde.ponoAestimatio(i, undaTria(delta_t));
            punctum.ponoAestimatio(i, undaTria(delta_t));
            break;
          case FRAG:
            deinde.ponoAestimatio(i, undaFrag());
            punctum.ponoAestimatio(i, undaFrag());
            break;
          default:
            throw new IllegalArgumentException("unknown unda:" + unda);
        }
        delta.ponoAestimatio(i, delta_t);
      }
      y_2 = y_1;
    }
    y_1 = deinde;
    positiones.deinde();
    punctum = punctum.multiplico(quantitas);
    //System.out.println(punctum);
    //return punctum;
    //PAN
    Punctum panned = new Punctum();
    for (int i = 0; i < Res.publica.channel(); i++) { //source
      Aestima aestimatio = punctum.capioAestima(i);
      Punctum pan = pans[i];
      for (int j = 0; j < Res.publica.channel(); j++) { //target
        panned.ponoAestimatio(j, panned.capioAestima(j).addo(aestimatio.multiplico(pan.capioAestima(j))));
      }
    }

    return panned;
  }

  private Aestima undaQuad(Aestima delta_t) {
    double pos = delta_t.doubleValue() - FastMath.floor(delta_t.doubleValue());
    if (pos < 0) {
      pos += 1;
    }
    return new Aestima(pos < 0.5 ? 1. : -1.);
  }

  private Aestima undaDent(Aestima delta_t) {
    double pos = delta_t.doubleValue() - FastMath.floor(delta_t.doubleValue());
    if (pos < 0) {
      pos += 1;
    }
    return new Aestima((1. - pos) * 2. - 1.);
  }

  private Aestima undaTria(Aestima delta_t) {
    double pos = delta_t.doubleValue() - FastMath.floor(delta_t.doubleValue());
    if (pos < 0) {
      pos += 1;
    }
    return new Aestima(pos < 0.25 ? pos * 4 : pos < 0.75 ? (0.5 - (pos - 0.25)) * 4. - 1. : (pos - 0.75) * 4. - 1.);
  }

  private Aestima undaFrag() {
    return new Aestima(new Random().nextGaussian() * 2. - 1);
  }

  @Override
  public boolean paratusSum() {
    return positiones.paratusSum();
    //return index < longitudo;
  }

  public static void main(String[] args) {
    PositionesFixi p2 = new PositionesFixi(
      Unda.SINE, 1, 0,
      new Envelope(new Punctum(554), new Positio(3000, new Punctum(554)), new Positio(3500, new Punctum(554))),
      new Envelope(new Punctum(0), new Positio(15, new Punctum(1)), new Positio(3000, new Punctum(1)), new Positio(3500, new Punctum(0))),
      new ArrayList<>(),//new Positio[]{new Positio(0, new Punctum(1, 0)), new Positio(3500, new Punctum(0, 1))},
      new Envelope(),
      new Envelope(),
      new Envelope(),
      new Envelope(),
      new Envelope());
    Oscillatio o = new Oscillatio(p2);
    File out_file = new File(IOUtil.getDirectory("opus"), "oscillatio_primo.wav");
    ScriptorWav sw = new ScriptorWav(out_file);
    sw.scribo(o, false);
    Functiones.ludoLimam(out_file);
  }

  @Override
  public void close() {
  }

  public static class Oscillationes extends ArrayList<Oscillatio> implements Legibilis {

    public Oscillationes(int initialCapacity) {
      super(initialCapacity);
    }

    @Override
    public Punctum lego() {
      Punctum punctum = new Punctum();
      for (Oscillatio oscillatio : this) {
        punctum = punctum.addo(oscillatio.lego());
      }
      return punctum;
    }

    @Override
    public boolean paratusSum() {
      for (Oscillatio oscillatio : this) {
        //System.out.println(oscillatio.paratusSum());
        if (oscillatio.paratusSum()) {
          return true;
        }
      }
      return false;
    }

    @Override
    public void close() {
    }

  }

}
