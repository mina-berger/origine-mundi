/*
 * CLAMOR project
 * by MINA BERGER
 */
package la.clamor.forma;

import la.clamor.Legibilis;
import la.clamor.Punctum;

/**
 *
 * @author minae.hiyamae
 */
public class FormaLegibilis implements Legibilis {

    private final Legibilis fons;
    private final Forma forma;
    private Integer terminens;

    public FormaLegibilis(Legibilis fons, Forma forma) {
        this.fons = fons;
        this.forma = forma;
        terminens = null;
    }

    @Override
    public boolean paratusSum() {
        if (fons.paratusSum()) {
            return true;
        }
        if (terminens == null) {
            terminens = forma.resto();
        }
        return terminens > 0;
    }

    @Override
    public Punctum lego() {
        Punctum lectum = terminens == null ? fons.lego() : new Punctum();
        Punctum punctum = forma.formo(lectum);
        if (terminens != null) {
            terminens--;
        }
        return punctum;
    }

    @Override
    public void close() {
        fons.close();
        forma.close();
    }
}
