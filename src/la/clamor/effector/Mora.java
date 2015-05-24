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
import la.clamor.Punctum;
import la.clamor.Aestimatio;
import la.clamor.LectorLimam;
import la.clamor.ScriptorWav;
import org.apache.commons.math3.util.FastMath;
import origine_mundi.OmUtil;

/**
 *
 * @author minae.hiyamae
 */
public class Mora extends AbstractEffector {
    Punctum diutius;
    Punctum aetas;
    Punctum feedback;
    int terminum;
    OrbisAestimationis oa;
    
    public Mora(Legibilis fons, Punctum diutius, Punctum aetas, Punctum feedback) {
        super(fons);
        this.diutius = diutius;
        this.aetas = aetas;
        this.feedback = feedback;
        long l_longitudo = Functiones.adPositio(diutius.maxAbs().doubleValue() * aetas.maxAbs().doubleValue());
        if(l_longitudo > Integer.MAX_VALUE){
            throw new IllegalArgumentException("diutius and aetas illegal.(smaller than " + 
                Functiones.adTempus(Integer.MAX_VALUE) + ")");
        }
        int longitudo = new Long(l_longitudo).intValue();
        oa = new OrbisAestimationis(longitudo + 1);
        terminum = oa.longitudo();
    }

    @Override
    public Punctum lego() {
        Punctum lectum = super.legoAFontem();
        Punctum punctum = new Punctum();
        for(int i = 0;i < CHANNEL;i++){
            int i_aetas = (int)FastMath.ceil(aetas.capioAestimatio(i).doubleValue());
            int index   = (int)FastMath.round(Functiones.adPositio(diutius.capioAestimatio(i).doubleValue()));
            //System.out.println(i_aetas + ":" + index + ":" + diutius.capioAestimatio(i).doubleValue() + ":"
            //+ Functiones.adPositio(diutius.capioAestimatio(i).doubleValue()));
            for(int j = 0;j < i_aetas;j++){
                Aestimatio aestimatio = oa.capio(index * (j + 1)).capioAestimatio(i);
                Aestimatio a_feedback = new Aestimatio(FastMath.pow(
                    feedback.capioAestimatio(i).doubleValue(), 
                    aetas   .capioAestimatio(i).doubleValue()));
                punctum.ponoAestimatio(i, 
                    punctum.capioAestimatio(i).addo(aestimatio.multiplico(a_feedback)));

            }
            
        }
        //System.out.println(punctum);
        oa.pono(lectum);
        return punctum.addo(lectum);
    }

    @Override
    public boolean paratusSum() {
        if(fonsParatusEst()){
            return true;
        }
        if(terminum > 0){
            terminum--;
            //System.out.println(terminum);
            return true;
        }
        return false;
    }
     public static void main(String[] args){
        File out_file = new File(OmUtil.getDirectory("sample"), "mora.wav");
        ScriptorWav sl = new ScriptorWav(out_file);
        /*sl.scribo(new Mora(new Legibilis(){
            OscillatioSimplex o = new OscillatioSimplex();
            int count= 0;
            @Override
            public Punctum lego() {
                count++;
                if(count >= 14400){
                    return new Punctum();
                }
                return o.lego(new Punctum(440, 340), new Punctum(1));
            }

            @Override
            public boolean paratusSum() {
                return count < 144000;
            }
        }*/
        sl.scribo(new Mora(new LectorLimam(new File(OmUtil.getDirectory("sample"), "clamor1.lima")), new Punctum(500), new Punctum(2), new Punctum(0.5)), false);
        Functiones.ludoLimam(out_file);
        
    }
    
}
