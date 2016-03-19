/*
 * CLAMOR project
 * by MINA BERGER
 */
package origine_mundi.deprec;

import java.io.File;
import la.clamor.Functiones;
import la.clamor.Legibilis;
import la.clamor.OrbisPuncti;
import la.clamor.Punctum;
import la.clamor.Aestimatio;
import la.clamor.io.ScriptorWav;
import la.clamor.LegibileAbstractum;
import la.clamor.Res;
import la.clamor.referibile.OscillatioSine;
import org.apache.commons.math3.util.FastMath;
import origine_mundi.OmUtil;

/**
 *
 * @author minae.hiyamae
 */
public class ChorusDeprec extends LegibileAbstractum {

    //Punctum profundum;
    Punctum frequentia;
    Punctum compendium_siccus;
    Punctum compendium_humens;
    int longitudo;
    int terminum;
    OrbisPuncti oa;
    OscillatioSine osc;

    public ChorusDeprec(Legibilis fons,
        Punctum profundum, Punctum frequentia,
        Punctum compendium_siccus, Punctum compendium_humens) {
        super(fons);
        //this.profundum = profundum;
        this.frequentia = frequentia;
        this.compendium_siccus = compendium_siccus;
        this.compendium_humens = compendium_humens;
        long l_longitudo = Functiones.adPositio(profundum.maxAbs().doubleValue());
        if (l_longitudo * 2 > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("profundum is illegal.(smaller than "
                + Functiones.adTempus(Integer.MAX_VALUE) + ")");
        }
        longitudo = new Long(l_longitudo).intValue();
        oa = new OrbisPuncti(longitudo * 2 + 1);
        osc = new OscillatioSine();
        terminum = oa.longitudo();
        System.out.println("longitudo=" + terminum);
    }

    @Override
    public Punctum lego() {
        Punctum lectum = super.legoAFontem();
        Punctum oscillatio = osc.lego(frequentia);
        Punctum punctum = new Punctum();
        for (int i = 0; i < Res.publica.channel(); i++) {
            double index = oscillatio.capioAestimatio(i).addo(new Aestimatio(1)).multiplico(new Aestimatio(longitudo)).doubleValue();
            //System.out.println(index);
            Aestimatio floor = oa.capio((int) FastMath.floor(index)).capioAestimatio(i);
            Aestimatio ceil = oa.capio((int) FastMath.ceil(index)).capioAestimatio(i);
            Aestimatio aestimatio
                = floor.multiplico(new Aestimatio(FastMath.ceil(index) - index))
                .addo(ceil.multiplico(new Aestimatio(index - FastMath.floor(index))));
            punctum.ponoAestimatio(i, aestimatio);
        }
        oa.pono(lectum);
        return punctum.multiplico(compendium_humens).addo(lectum.multiplico(compendium_siccus));
    }

    @Override
    public boolean paratusSum() {
        if (fonsParatusEst()) {
            return true;
        }
        //terminens = true;
        if (terminum > 0) {
            terminum--;
            //System.out.println(terminum);
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        File out_file = new File(OmUtil.getDirectory("opus"), "chorus.wav");
        ScriptorWav sw = new ScriptorWav(out_file);
        sw.scribo(new ChorusDeprec(new Legibilis() {
            OscillatioSine o = new OscillatioSine();
            int count = 0;

            @Override
            public Punctum lego() {
                count++;
                return o.lego(new Punctum(880));
            }

            @Override
            public boolean paratusSum() {
                return count < 144000;
            }

            @Override
            public void close() {
            }
        }, new Punctum(0.3), new Punctum(9, 10), new Punctum(1), new Punctum(1, -1)), false);
        Functiones.ludoLimam(out_file);
    }

}
