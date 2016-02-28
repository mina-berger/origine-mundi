/*
 * CLAMOR project
 * by MINA BERGER
 */

package la.clamor.forma;

import java.io.File;
import static la.clamor.Constantia.CHANNEL;
import la.clamor.Functiones;
import la.clamor.Legibilis;
import la.clamor.OrbisPuncti;
import la.clamor.SineOscillatio;
import la.clamor.Punctum;
import la.clamor.Aestimatio;
import la.clamor.io.ScriptorWav;
import org.apache.commons.math3.util.FastMath;
import origine_mundi.OmUtil;

/**
 *
 * @author minae.hiyamae
 */
public class Chorus implements Forma {
    Punctum frequentia;
    Punctum compendium_siccus;
    Punctum compendium_humens;
    int longitudo;
    OrbisPuncti oa;
    SineOscillatio osc;
    public Chorus(Punctum profundum, Punctum frequentia, Punctum compendium_siccus, Punctum compendium_humens) {
        this.frequentia = frequentia;
        this.compendium_siccus = compendium_siccus;
        this.compendium_humens = compendium_humens;
        long l_longitudo = Functiones.adPositio(profundum.maxAbs().doubleValue());
        if(l_longitudo * 2 > Integer.MAX_VALUE){
            throw new IllegalArgumentException("profundum is illegal.(smaller than " + 
                Functiones.adTempus(Integer.MAX_VALUE) + ")");
        }
        longitudo = new Long(l_longitudo).intValue();
        oa = new OrbisPuncti(longitudo * 2 + 1);
        osc = new SineOscillatio();
    }


    @Override
    public int resto() {
        return oa.longitudo();
    }
    @Override
    public Punctum formo(Punctum lectum) {
        Punctum oscillatio = osc.lego(frequentia, new Punctum(1));
        Punctum punctum = new Punctum();
        for(int i = 0;i < CHANNEL;i++){
            double index = oscillatio.capioAestimatio(i).addo(new Aestimatio(1)).multiplico(new Aestimatio(longitudo)).doubleValue();
            //System.out.println(index);
            Aestimatio floor = oa.capio((int)FastMath.floor(index)).capioAestimatio(i);
            Aestimatio ceil  = oa.capio((int)FastMath.ceil (index)).capioAestimatio(i);
            Aestimatio aestimatio = 
                floor.multiplico(new Aestimatio(FastMath.ceil (index) - index))
                    .addo(ceil.multiplico(new Aestimatio(index - FastMath.floor(index))));
            punctum.ponoAestimatio(i, aestimatio);
        }
        oa.pono(lectum);
        return punctum.multiplico(compendium_humens).addo(lectum.multiplico(compendium_siccus));
    }

     public static void main(String[] args){
        File out_file = new File(OmUtil.getDirectory("opus"), "chorus.wav");
        ScriptorWav sw = new ScriptorWav(out_file);
        sw.scribo(new FormaLegibilis(new Legibilis(){
            SineOscillatio o = new SineOscillatio();
            int count= 0;
            @Override
            public Punctum lego() {
                count++;
                return o.lego(new Punctum(880), new Punctum(1));
            }

            @Override
            public boolean paratusSum() {
                return count < 144000;
            }
        }, new Chorus(new Punctum(0.3), new Punctum(9, 10), new Punctum(1), new Punctum(1, -1))), false);
        Functiones.ludoLimam(out_file);
    }
       
}
