/*
 * CLAMOR project
 * by MINA BERGER
 */
package la.clamor.forma;

import java.io.File;
import la.clamor.Functiones;
import la.clamor.Legibilis;
import la.clamor.Punctum;
import la.clamor.io.ScriptorWav;
import static la.clamor.forma.Compressor.Phase.IMP;
import static la.clamor.forma.Compressor.Phase.LIB;
import static la.clamor.forma.Compressor.Phase.MED;
import static la.clamor.forma.Compressor.Phase.NIH;
import la.clamor.referibile.OscillatioSine;
import origine_mundi.OmUtil;

/**
 *
 * @author minae.hiyamae
 */
public class Compressor implements Forma {

    enum Phase {
        IMP, MED, LIB, NIH
    };
    Punctum compendium_ante;
    Punctum compendium_post;
    double threshold;
    double ratio;
    long longitudo_impetui;
    long longitudo_medio;
    long longitudo_liberationi;

    Phase phase;
    long temporis;

    double temporis_level;
    double temporis_ratio;
    double initial_ratio;
    double previous_ratio;
    //boolean activated;

    public Compressor(
        Punctum threshold, Punctum ratio,
        Punctum tempus_impetui, Punctum tempus_medio, Punctum tempus_liberationi,
        Punctum compendium_ante, Punctum compendium_post) {
        this.compendium_ante = compendium_ante;
        this.compendium_post = compendium_post;
        this.threshold = threshold.maxAbs().doubleValue();
        this.ratio = ratio.maxAbs().doubleValue();
        longitudo_impetui = Functiones.adPositio(tempus_impetui.maxAbs().doubleValue());
        longitudo_medio = Functiones.adPositio(tempus_medio.maxAbs().doubleValue());
        longitudo_liberationi = Functiones.adPositio(tempus_liberationi.maxAbs().doubleValue());

        phase = NIH;
        temporis = 0;
        //activated             = false;
        temporis_level = this.threshold;
        temporis_ratio = 1d;
        initial_ratio = 1d;
        previous_ratio = 1d;
    }

    @Override
    public int resto() {
        return 0;
    }

    @Override
    public Punctum formo(Punctum lectum) {
        Punctum pultiplicatum = lectum.multiplico(compendium_ante);
        double level = pultiplicatum.maxAbs().doubleValue();
        if (level > temporis_level) {
            if (phase == NIH) {
                temporis = longitudo_impetui;
            } else {
                temporis_level = level;
                initial_ratio = previous_ratio;
                temporis_ratio = calculoRatio(level);
                temporis = longitudo_impetui;
            }
            phase = IMP;
        }
        if (level > threshold && phase == MED) {
            temporis = longitudo_medio;
        }
        if (temporis == 0) {
            if (phase == IMP) {
                phase = MED;
                temporis = longitudo_medio;
            } else if (phase == MED) {
                phase = LIB;
                temporis = longitudo_liberationi;
            } else if (phase == LIB) {
                phase = NIH;
                temporis_level = threshold;
                temporis_ratio = 1d;
                initial_ratio = 1d;
                previous_ratio = 1d;
            }
        }
        double _ratio = 0;
        switch (phase) {
            case NIH:
                _ratio = 1;
                break;
            case IMP:
                _ratio = (initial_ratio - temporis_ratio) * (double) temporis / (double) longitudo_impetui + temporis_ratio;
                break;
            case MED:
                _ratio = temporis_ratio;
                break;
            case LIB:
                _ratio = (1d - temporis_ratio) * (double) (longitudo_liberationi - temporis) / (double) longitudo_liberationi + temporis_ratio;
                break;
        }
        //System.out.println(_ratio);
        temporis--;
        previous_ratio = _ratio;
        return pultiplicatum.multiplico(_ratio).multiplico(compendium_post);
    }

    private double calculoRatio(double level) {
        return ((level - threshold) * ratio + threshold) / level;
    }

    public static void main(String[] args) {
        File out_file = new File(OmUtil.getDirectory("opus"), "compressor.wav");

        ScriptorWav sl = new ScriptorWav(out_file);
        sl.scribo(new FormaLegibilis(new Legibilis() {
            OscillatioSine o = new OscillatioSine();
            int count = 0;

            @Override
            public Punctum lego() {
                count++;
                return o.lego(new Punctum(440), new Punctum(count < 120000 ? 1 : 0.5));
            }

            @Override
            public boolean paratusSum() {
                return count < 240000;
            }

            @Override
            public void close() {
            }
        },
            new Compressor(
                new Punctum(0.5), new Punctum(0.1),
                new Punctum(1000), new Punctum(10), new Punctum(100),
                new Punctum(1), new Punctum(1))), false);
        Functiones.ludoLimam(out_file);
    }

}
