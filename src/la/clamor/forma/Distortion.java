/*
 * CLAMOR project
 * by MINA BERGER
 */

package la.clamor.forma;

import java.io.File;
import la.clamor.Aestimatio;
import static la.clamor.Constantia.CHANNEL;
import la.clamor.Functiones;
import la.clamor.Legibilis;
import la.clamor.Punctum;
import la.clamor.SineOscillatio;
import la.clamor.io.ScriptorWav;
import origine_mundi.OmUtil;

/**
 *
 * @author minae.hiyamae
 */
public class Distortion implements Forma {
    Punctum terminus;
    Punctum compendium_ante;
    Punctum compendium_post;

    public Distortion(Punctum terminus, Punctum compendium_ante, Punctum compendium_post) {
        this.terminus = terminus;
        this.compendium_ante = compendium_ante;
        this.compendium_post = compendium_post;
    }


    @Override
    public int resto() {
        return 0;
    }
    @Override
    public Punctum formo(Punctum lectum) {
        final Aestimatio zero = new Aestimatio();
        Punctum multiplicatum = lectum.multiplico(compendium_ante);
        for(int i = 0;i < CHANNEL;i++){
            Aestimatio aestimatio = multiplicatum.capioAestimatio(i);
            if(aestimatio.compareTo(zero) < 0){
                aestimatio = aestimatio.max(terminus.capioAestimatio(i).nego());
            }else{
                aestimatio = aestimatio.min(terminus.capioAestimatio(i));
            }
            lectum.ponoAestimatio(i, aestimatio);
        }
        return lectum.multiplico(compendium_post);
    }
    public static void main(String[] args){
        File out_file = new File(OmUtil.getDirectory("opus"), "distorquetor.wav");
        ScriptorWav sl = new ScriptorWav(out_file);
        sl.scribo(new FormaLegibilis(new Legibilis(){
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
        }, new Distortion(new Punctum(0.1), new Punctum(1.5), new Punctum(1))), false);        
        Functiones.ludoLimam(out_file);
        
    }
    
}
