/*
 * CLAMOR project
 * by MINA BERGER
 */

package origine_mundi.deprec;

import java.io.File;
import la.clamor.Aestimatio;
import static la.clamor.Constantia.CHANNEL;
import la.clamor.Functiones;
import la.clamor.Legibilis;
import la.clamor.Punctum;
import la.clamor.io.ScriptorWav;
import la.clamor.LegibileAbstractum;
import la.clamor.SineOscillatio;
import origine_mundi.OmUtil;

/**
 *
 * @author minae.hiyamae
 */
public class DistorquetorDeprec extends LegibileAbstractum {
    Punctum terminus;
    Punctum compendium_ante;
    Punctum compendium_post;

    public DistorquetorDeprec(Legibilis fons, Punctum terminus, Punctum compendium_ante, Punctum compendium_post) {
        super(fons);
        this.terminus = terminus;
        this.compendium_ante = compendium_ante;
        this.compendium_post = compendium_post;
    }

    @Override
    public Punctum lego() {
        final Aestimatio zero = new Aestimatio();
        Punctum lectum = legoAFontem().multiplico(compendium_ante);
        for(int i = 0;i < CHANNEL;i++){
            Aestimatio aestimatio = lectum.capioAestimatio(i);
            if(aestimatio.compareTo(zero) < 0){
                aestimatio = aestimatio.max(terminus.capioAestimatio(i).nego());
            }else{
                aestimatio = aestimatio.min(terminus.capioAestimatio(i));
            }
            lectum.ponoAestimatio(i, aestimatio);
        }
        return lectum.multiplico(compendium_post);
    }

    @Override
    public boolean paratusSum() {
        return fonsParatusEst();
    }
    public static void main(String[] args){
        File out_file = new File(OmUtil.getDirectory("opus"), "distorquetor.wav");
        ScriptorWav sl = new ScriptorWav(out_file);
        sl.scribo(new DistorquetorDeprec(new Legibilis(){
            SineOscillatio o = new SineOscillatio();
            int count= 0;
            @Override
            public Punctum lego() {
                count++;
                return o.lego(new Punctum(440), new Punctum(1));
            }

            @Override
            public boolean paratusSum() {
                return count < 144000;
            }
        }, new Punctum(1), new Punctum(1.5), new Punctum(1)), false);        
        Functiones.ludoLimam(out_file);
        
    }
    
}
