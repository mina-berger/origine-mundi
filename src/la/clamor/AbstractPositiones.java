package la.clamor;

import la.clamor.Constantia.Unda;
import la.clamor.Punctum.Aestimatio;

/**
 *
 * @author minae.hiyamae
 */
public abstract class AbstractPositiones {
    long   longitudo;
    long   index;
    AbstractPositiones[] modulatores;
    Unda unda;
    double volume;
    double feedback;
    public AbstractPositiones(long longitudo, Unda unda, double volume, double feedback){
        //longitudo = (long)(diuturnitas * REGULA_EXAMPLI_D / 1000d);
        this.longitudo = longitudo;
        this.unda      = unda;
        this.volume    = volume;
        this.feedback  = feedback;
        index = 0;
    }
    protected void ponoModulatores(AbstractPositiones[] modulatores){
        this.modulatores = modulatores;
    }
    public AbstractPositiones[] capioModulatores(){
        return modulatores;
    }
    public boolean paratusSum() {
        return index < longitudo;
    }
    public void deinde(){
        index++;
    }
    public long capioLongitudo(){
        return longitudo;
    }
    public void ponoLongitudo(long longitudo){
        this.longitudo = longitudo;
    }
    public Unda capioUndam(){
        return unda;
    }
    //public double capioVolume(){
    //    return volume;
    //}
    public abstract Punctum capioFeedback();
    
    public abstract Punctum capioFrequentiae();
    public abstract Punctum capioQuantitatis();
    public abstract Punctum[] capioPans();
    public static void main(String[] args){
        int index = 3;
        Aestimatio a1 = 
                new Aestimatio(5).multiplico(new Aestimatio(5 - index)).addo( 
                new Aestimatio(10).multiplico(new Aestimatio(index - 0))).partior(new Aestimatio(5));
        System.out.println(a1);
    }
}
