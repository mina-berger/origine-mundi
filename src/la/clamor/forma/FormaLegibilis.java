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

    private Legibilis fons;
    private final Forma forma;
    private Integer terminens;
    private boolean captus_primo;

    public FormaLegibilis(Legibilis fons, Forma forma) {
        captus_primo = (forma instanceof FormaCapta);
        this.fons = fons;
        this.forma = forma;
        terminens = null;
    }
    private void capio(){
        if(captus_primo){
            fons = ((FormaCapta)forma).capio(fons);
            captus_primo = false;
        }
    }

    @Override
    public boolean paratusSum() {
        capio();
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
        capio();
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
