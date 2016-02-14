package la.clamor.primo;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import la.clamor.Aestimatio;
import la.clamor.Constantia;
import static la.clamor.Constantia.CHANNEL;
import static la.clamor.Constantia.REGULA_EXAMPLI_D;
import la.clamor.Constantia.Unda;
import static la.clamor.Constantia.Unda.DENT;
import static la.clamor.Constantia.Unda.FRAG;
import static la.clamor.Constantia.Unda.QUAD;
import static la.clamor.Constantia.Unda.SINE;
import static la.clamor.Constantia.Unda.TRIA;
import la.clamor.Functiones;
import la.clamor.Legibilis;
import la.clamor.Positio;
import la.clamor.Punctum;
import la.clamor.ScriptorLimam;
import la.clamor.ScriptorWav;
import la.clamor.primo.PositionesPrimo.PositionesFixi;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.util.FastMath;
import origine_mundi.OmUtil;

/**
 * Oscillation with duration specified, but frequency nor volume unspecified
 * @author minae.hiyamae
 */
public class OscillatioPrimo implements Constantia, Legibilis {
    double t;
    Punctum y_1;
    Punctum y_2;
    int count_buffer;
    Punctum delta;
    
    /** setting for this oscillatio */
    PositionesPrimo positiones;
    
    OscillatioPrimo[] modulatores;
    public OscillatioPrimo(PositionesPrimo positiones){
        this.positiones = positiones;
        //System.out.println(positiones);
        
        t = 1d / REGULA_EXAMPLI_D;
        y_1 = new Punctum();
        y_2 = new Punctum();
        count_buffer = 2;
        
        delta = new Punctum();
        
        modulatores = new OscillatioPrimo[0];
        for(PositionesPrimo positiones_modulationis:positiones.capioModulatores()){
            addoModulator(new OscillatioPrimo(positiones_modulationis));
        }
    }
    public final void addoModulator(OscillatioPrimo modulator){
        modulatores = ArrayUtils.add(modulatores, modulator);
    }
    @Override
    public Punctum lego() {
        //index++;
        
        Punctum punctum = new Punctum();
        if(count_buffer == 2){
            count_buffer--;
            positiones.deinde();
            return punctum;
        }
        
        Punctum frequentia = positiones.capioFrequentiae();
        Punctum quantitas  = positiones.capioQuantitatis();
        Punctum[] pans     = positiones.capioPans();
        Unda unda          = positiones.capioUndam();
        //double volume      = positiones.capioVolume();
        Punctum feedback   = positiones.capioFeedback();
        Punctum modulatio = new Punctum();
        for(OscillatioPrimo modulator:modulatores){
            if(modulator.paratusSum()){
                modulatio = modulatio.addo(modulator.lego());
            }
        }
        modulatio = modulatio.addo(y_1.multiplico(feedback));
        
        Punctum deinde = new Punctum();
        if(count_buffer == 1){
            count_buffer--;
            for(int i = 0;i < CHANNEL;i++){
                Aestimatio omega_t = frequentia.capioAestimatio(i).multiplico(new Aestimatio(2 * FastMath.PI * t));
                Aestimatio delta_t = delta.capioAestimatio(i).addo(frequentia.capioAestimatio(i).multiplico(new Aestimatio(t)));
                switch(unda){
                    case SINE:
                        Aestimatio _aestimatio = new Aestimatio(FastMath.sin(omega_t.doubleValue()));
                        Aestimatio _modulatio  = modulatio.capioAestimatio(i);//.addo(_aestimatio.multiplico(new Aestimatio(feedback)));
                        deinde .ponoAestimatio(i, _aestimatio);
                        punctum.ponoAestimatio(i, new Aestimatio(FastMath.sin(omega_t.addo(_modulatio).doubleValue())));
                        break;
                    case QUAD:
                        deinde .ponoAestimatio(i, undaQuad(delta_t));
                        punctum.ponoAestimatio(i, undaQuad(delta_t));
                        break;
                    case DENT:
                        deinde .ponoAestimatio(i, undaDent(delta_t));
                        punctum.ponoAestimatio(i, undaDent(delta_t));
                        break;
                    case TRIA:
                        deinde .ponoAestimatio(i, undaTria(delta_t));
                        punctum.ponoAestimatio(i, undaTria(delta_t));
                        break;
                    case FRAG:
                        deinde .ponoAestimatio(i, undaFrag());
                        punctum.ponoAestimatio(i, undaFrag());
                        break;
                    default:
                        throw new IllegalArgumentException("unknown unda:" + unda);

                        
                }
                delta.ponoAestimatio(i, delta_t);
            }
        }else{
            for(int i = 0;i < CHANNEL;i++){
                Aestimatio omega_t = frequentia.capioAestimatio(i).multiplico(new Aestimatio(2 * FastMath.PI * t));
                Aestimatio delta_t = delta.capioAestimatio(i).addo(frequentia.capioAestimatio(i).multiplico(new Aestimatio(t)));
                switch(unda){
                    case SINE:
                        Aestimatio _aestimatio = new Aestimatio(FastMath.sin(omega_t.doubleValue()));
                        //System.out.println(FastMath.sin(delta_t.doubleValue()) + ":" + feedback);
                        Aestimatio _modulatio  = modulatio.capioAestimatio(i);//.addo(_aestimatio.multiplico(new Aestimatio(feedback)));
                        Aestimatio b1_d = new Aestimatio(2).multiplico(new Aestimatio(FastMath.cos(omega_t.doubleValue())));
                        deinde .ponoAestimatio(i, b1_d.multiplico(y_1.capioAestimatio(i)).subtraho(y_2.capioAestimatio(i)));

                        Aestimatio b1_m = new Aestimatio(2).multiplico(new Aestimatio(FastMath.cos(omega_t.addo(_modulatio).doubleValue())));
                        punctum.ponoAestimatio(i, b1_m.multiplico(y_1.capioAestimatio(i)).subtraho(y_2.capioAestimatio(i)));
                        break;
                    case QUAD:
                        deinde .ponoAestimatio(i, undaQuad(delta_t));
                        punctum.ponoAestimatio(i, undaQuad(delta_t));
                        break;
                    case DENT:
                        deinde .ponoAestimatio(i, undaDent(delta_t));
                        punctum.ponoAestimatio(i, undaDent(delta_t));
                        break;
                    case TRIA:
                        deinde .ponoAestimatio(i, undaTria(delta_t));
                        punctum.ponoAestimatio(i, undaTria(delta_t));
                        break;
                    case FRAG:
                        deinde .ponoAestimatio(i, undaFrag());
                        punctum.ponoAestimatio(i, undaFrag());
                        break;
                    default:
                        throw new IllegalArgumentException("unknown unda:" + unda);
                }
                delta.ponoAestimatio(i, delta_t);
            }
            y_2 = y_1;
        }
        y_1 = deinde;
        positiones.deinde();
        punctum = punctum.multiplico(quantitas);
        //System.out.println(punctum);
        //return punctum;
        //PAN
        Punctum panned = new Punctum();
        for(int i = 0;i < CHANNEL;i++){ //source
            Aestimatio aestimatio = punctum.capioAestimatio(i);
            Punctum pan = pans[i];
            for(int j = 0;j < CHANNEL;j++){ //target
                panned.ponoAestimatio(j, panned.capioAestimatio(j).addo(aestimatio.multiplico(pan.capioAestimatio(j))));
            }
        }
        
        return panned;
    }
    private Aestimatio undaQuad(Aestimatio delta_t){
        double pos = delta_t.doubleValue() - FastMath.floor(delta_t.doubleValue());
        if(pos < 0){
            pos += 1;
        }
        return new Aestimatio(pos < 0.5 ? 1. : -1.);
    }
    private Aestimatio undaDent(Aestimatio delta_t){
        double pos = delta_t.doubleValue() - FastMath.floor(delta_t.doubleValue());
        if(pos < 0){
            pos += 1;
        }
        return new Aestimatio((1. - pos) * 2. - 1.);
    }
    private Aestimatio undaTria(Aestimatio delta_t){
        double pos = delta_t.doubleValue() - FastMath.floor(delta_t.doubleValue());
        if(pos < 0){
            pos += 1;
        }
        return new Aestimatio(pos < 0.25?pos * 4:pos < 0.75?(0.5 - (pos - 0.25)) * 4. - 1.:(pos - 0.75) * 4. - 1.);
    }
    private Aestimatio undaFrag(){
        return new Aestimatio(new Random().nextGaussian() * 2. - 1);
    }

    @Override
    public boolean paratusSum() {
        return positiones.paratusSum();
        //return index < longitudo;
    }
    public static void main(String[] args){
        PositionesPrimo.PositionesFixi p2 = new PositionesPrimo.PositionesFixi(
            Unda.DENT, 1, 0, 
            new PositionesPrimo.Positio[]{new PositionesPrimo.Positio(0, new Punctum(554)), new PositionesPrimo.Positio(3000, new Punctum(554)), new PositionesPrimo.Positio(3500, new Punctum(554))},
            new PositionesPrimo.Positio[]{new PositionesPrimo.Positio(0, new Punctum(0)), new PositionesPrimo.Positio(15, new Punctum(1)), new PositionesPrimo.Positio(3000, new Punctum(1)), new PositionesPrimo.Positio(3500, new Punctum(0))},
            null, //new PositionesPrimo.Positio[]{new PositionesPrimo.Positio(0, new Punctum(1, 0)), new PositionesPrimo.Positio(3500, new Punctum(0, 1))},
            new PositionesPrimo.Positio[]{/*new PositionesPrimo.Positio(0, new Punctum(2.88))*/}, 
            new PositionesPrimo.Positio[]{/*new PositionesPrimo.Positio(0, new Punctum(0.031))*/}, 
            new PositionesPrimo.Positio[]{/*new PositionesPrimo.Positio(0, new Punctum(3))*/}, 
            new PositionesPrimo.Positio[]{/*new PositionesPrimo.Positio(0, new Punctum(1))*/},
            new PositionesPrimo.Positio[0]);
        OscillatioPrimo o = new OscillatioPrimo(p2);
        File out_file = new File(OmUtil.getDirectory("opus"), "oscillatio_primo.wav");
        ScriptorWav sw = new ScriptorWav(out_file);
        sw.scribo(o, false);
        Functiones.ludoLimam(out_file);
    }
    public static class Oscillationes extends ArrayList<OscillatioPrimo> implements Legibilis {

        public Oscillationes(int initialCapacity){
            super(initialCapacity);
        }
        @Override
        public Punctum lego() {
            Punctum punctum = new Punctum();
            for(OscillatioPrimo oscillatio:this){
                punctum = punctum.addo(oscillatio.lego());
            }
            return punctum;
        }

        @Override
        public boolean paratusSum() {
            for(OscillatioPrimo oscillatio:this){
                //System.out.println(oscillatio.paratusSum());
                if(oscillatio.paratusSum())
                    return true;
            }
            return false;
        }
        
    }

}