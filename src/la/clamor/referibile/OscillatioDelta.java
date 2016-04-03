package la.clamor.referibile;

import la.clamor.Aestima;
import la.clamor.Constantia;
import static la.clamor.Constantia.REGULA_EXAMPLI_D;
import la.clamor.Punctum;
import la.clamor.Res;

/**
 * Oscillation with duration specified, but frequency nor volume unspecified
 *
 * @author minae.hiyamae
 */
public abstract class OscillatioDelta implements Referibilis, Constantia {

    Punctum delta;
    double t;

    /**
     * setting for this oscillatio
     */
    public OscillatioDelta() {
        delta = new Punctum();
        t = 1d / REGULA_EXAMPLI_D;

    }

    @Override
    public Punctum lego(Punctum frequentia) {
        Punctum punctum = new Punctum();
        for (int i = 0; i < Res.publica.channel(); i++) {
            Aestima delta_t = delta.capioAestima(i).addo(frequentia.capioAestima(i).multiplico(new Aestima(t)));
            punctum.ponoAestimatio(i, capioUnda(delta_t));
            delta.ponoAestimatio(i, delta_t);
        }
        return punctum;
    }
    protected abstract Aestima capioUnda(Aestima delta_t);
}
