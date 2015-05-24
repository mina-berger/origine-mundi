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
    private boolean terminens;
    public AbstractEffector(Legibilis fons){
        this.fons = fons;
        terminens = false;
    }
    public void ponoFons(Legibilis fons) {
        this.fons = fons;
    }
    protected boolean fonsParatusEst(){
        boolean parata = fons.paratusSum();
        if(!parata){
            terminens = true;
        }
        return parata;
    }
    protected Punctum legoAFontem(){
        return terminens?new Punctum():fons.lego();
    }
    protected boolean terminens(){
        return terminens;
    }

    
    
}
