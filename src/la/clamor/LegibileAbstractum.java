/*
 * CLAMOR project
 * by MINA BERGER
 */

package la.clamor;

/**
 *
 * @author minae.hiyamae
 */
public abstract class LegibileAbstractum implements Legibilis {
    private Legibilis fons;
    private boolean terminens;
    public LegibileAbstractum(Legibilis fons){
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
    @Override
    public void close(){
        fons.close();
    }

    
    
}
