/*
 * CLAMOR project
 * by MINA BERGER
 */

package la.clamor.effector;

import java.io.File;
import static la.clamor.Constantia.CHANNEL;
import la.clamor.Functiones;
import la.clamor.Legibilis;
import la.clamor.OrbisAestimationis;
import la.clamor.OscillatioSimplex;
import la.clamor.Punctum;
import la.clamor.Aestimatio;
import la.clamor.ScriptorWav;
import org.apache.commons.math3.util.FastMath;
import origine_mundi.OmUtil;

/**
 *
 * @author minae.hiyamae
 */
public class Chorus extends AbstractEffector {
    //Punctum profundum;
    Punctum frequentia;
    Punctum compendium_siccus;
    Punctum compendium_humens;
    int longitudo;
    int terminum;
    OrbisAestimationis oa;
    OscillatioSimplex osc;
    public Chorus(Legibilis fons, 
        Punctum profundum, Punctum frequentia, 
        Punctum compendium_siccus, Punctum compendium_humens) {
        super(fons);
        //this.profundum = profundum;
        this.frequentia = frequentia;
        this.compendium_siccus = compendium_siccus;
        this.compendium_humens = compendium_humens;
        long l_longitudo = Functiones.adPositio(profundum.maxAbs().doubleValue());
        if(l_longitudo * 2 > Integer.MAX_VALUE){
            throw new IllegalArgumentException("profundum is illegal.(smaller than " + 
                Functiones.adTempus(Integer.MAX_VALUE) + ")");
        }
        longitudo = new Long(l_longitudo).intValue();
        oa = new OrbisAestimationis(longitudo * 2 + 1);
        osc = new OscillatioSimplex();
        terminum = oa.longitudo();
        System.out.println("longitudo=" + terminum);
    }

    @Override
    public Punctum lego() {
        Punctum lectum = super.legoAFontem();
        Punctum oscillatio = osc.lego(frequentia, new Punctum(1));
        Punctum punctum = new Punctum();
        for(int i = 0;i < CHANNEL;i++){
            double index = oscillatio.capioAestimatio(i).addo(new Aestimatio(1)).multiplico(new Aestimatio(longitudo)).rawValue();
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

    @Override
    public boolean paratusSum() {
        if(fonsParatusEst()){
            return true;
        }
        //terminens = true;
        if(terminum > 0){
            terminum--;
            //System.out.println(terminum);
            return true;
        }
        return false;
    }
     public static void main(String[] args){
        File out_file = new File(OmUtil.getDirectory("opus"), "chorus.wav");
        ScriptorWav sw = new ScriptorWav(out_file);
        sw.scribo(new Chorus(new Legibilis(){
            OscillatioSimplex o = new OscillatioSimplex();
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
        }, new Punctum(0.3), new Punctum(9, 10), new Punctum(1), new Punctum(1, -1)), false);
        Functiones.ludoLimam(out_file);
    }
       
}
