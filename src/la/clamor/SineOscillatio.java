package la.clamor;



import origine_mundi.OmUtil;
import java.io.File;
import org.apache.commons.math3.util.FastMath;

/**
 * Oscillation with duration specified, but frequency nor volume unspecified
 * @author minae.hiyamae
 */
public class SineOscillatio implements Constantia {
    double t;
    Punctum y_1;
    Punctum y_2;
    int count_buffer;
    
    /** setting for this oscillatio */
    public SineOscillatio(){
        
        t = 1d / REGULA_EXAMPLI_D;
        y_1 = new Punctum();
        y_2 = new Punctum();
        count_buffer = 2;
        
    }
    public Punctum lego(Punctum frequentia, Punctum quantitas) {
        frequentia = (frequentia == null)?new Punctum():frequentia;
        quantitas  = (quantitas  == null)?new Punctum():quantitas;
        Punctum punctum = new Punctum();
        if(count_buffer == 2){
            count_buffer--;
            return punctum;
        }
        if(count_buffer == 1){
            count_buffer--;
            for(int i = 0;i < CHANNEL;i++){
                Aestimatio omega_t = frequentia.capioAestimatio(i).multiplico(new Aestimatio(2 * FastMath.PI * t));
                punctum.ponoAestimatio(i, new Aestimatio(FastMath.sin(omega_t.doubleValue())));
            }
        }else{
            for(int i = 0;i < CHANNEL;i++){
                Aestimatio omega_t = frequentia.capioAestimatio(i).multiplico(new Aestimatio(2 * FastMath.PI * t));
                Aestimatio b1_m = new Aestimatio(2).multiplico(new Aestimatio(FastMath.cos(omega_t.doubleValue())));
                punctum.ponoAestimatio(i, b1_m.multiplico(y_1.capioAestimatio(i)).subtraho(y_2.capioAestimatio(i)));
            }
            y_2 = y_1;
        }
        y_1 = punctum;
        return punctum.multiplico(quantitas);
    }
    public static void _main(String[] arg){
        SineOscillatio o = new SineOscillatio();
        for(int i = 0;i < 200;i++){
            System.out.println(o.lego(new Punctum(0), new Punctum(1)));
        }
    }
    public static void main(String[] args){
        
        File out_file = new File(OmUtil.getDirectory("opus"), "oscillatio_sumplex.wav");
        ScriptorWav sw = new ScriptorWav(out_file);
        sw.scribo(new Legibilis(){
            SineOscillatio o = new SineOscillatio();
            int count= 0;
            double pitch = 110;
            @Override
            public Punctum lego() {
                count++;
                pitch += 0.02;
                return o.lego(new Punctum(pitch), new Punctum(1));
            }

            @Override
            public boolean paratusSum() {
                return count < 144000;
            }
        }, false);
        Functiones.ludoLimam(out_file);
                
    }
}
