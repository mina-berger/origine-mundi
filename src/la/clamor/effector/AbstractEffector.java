/*
 * CLAMOR project
 * by MINA BERGER
 */

package la.clamor.effector;

import la.clamor.Legibilis;
import la.clamor.Punctum;

/**
 *
 * @author minae.hiyamae
 */
public abstract class AbstractEffector implements Legibilis {
    private Legibilis fons;
    public AbstractEffector(Legibilis fons){
        this.fons = fons;
    }
    public void ponoFons(Legibilis fons) {
        this.fons = fons;
    }
    protected boolean fonsParatusEst(){
        return fons.paratusSum();
    }
    protected Punctum legoAFontem(){
        return fons.lego();
    }

    
    
}
