/*
 * CLAMOR project
 * by MINA BERGER
 */

package origine_mundi.deprec;

import java.io.File;
import java.util.ArrayList;
import la.clamor.Constantia.Effector;
import la.clamor.Functiones;
import la.clamor.Legibilis;
import la.clamor.Puncta;
import la.clamor.Punctum;
import la.clamor.SineOscillatio;
import la.clamor.io.ScriptorWav;
import la.clamor.forma.Compressor;
import la.clamor.forma.FormaLegibilis;
import origine_mundi.deprec.FactorEffectorisDeprec.PositionesEffectoris;
import origine_mundi.OmUtil;

/**
 *
 * @author minae.hiyamae
 */
public class FactorEffectorisDeprec extends ArrayList<PositionesEffectoris> {
    //private enum Type{DISTORQUETOR, CHORUS}
    public static class PositionesEffectoris {
        Effector type;
        private final Puncta puncta;
        public PositionesEffectoris(Effector type, Punctum... punctum_array){
            this.type = type;
            puncta = new Puncta(punctum_array);
        }
        public void print(){
            System.out.println("PositionesEffectoris:" + type.name() + 
                ":p0=" + puncta.capioPunctum(0) + 
                ":p1=" + puncta.capioPunctum(1) + 
                ":p2=" + puncta.capioPunctum(2) + 
                ":p3=" + puncta.capioPunctum(3));
        }
    }

    public void distorquetor(Punctum terminus, Punctum compendium_ante, Punctum compendium_post){
        add(new PositionesEffectoris(Effector.DIST, terminus, compendium_ante, compendium_post));
    }
    public void chorus(Punctum profundum, Punctum frequentia, Punctum compendium_siccus, Punctum compendium_humens){
        add(new PositionesEffectoris(Effector.CHOR, profundum, frequentia, compendium_siccus, compendium_humens));
        
    }
    public Legibilis capioLegibilis(Legibilis fons){
        Legibilis l = fons;
        for(PositionesEffectoris pe:this){
            switch(pe.type){
                case DIST:
                    l = new DistorquetorDeprec(l, pe.puncta.capioPunctum(0), pe.puncta.capioPunctum(1), pe.puncta.capioPunctum(2));
                    break;
                case CHOR:
                    l = new ChorusDeprec(l, pe.puncta.capioPunctum(0), pe.puncta.capioPunctum(1), pe.puncta.capioPunctum(2), pe.puncta.capioPunctum(3));
                    break;
                case MORA:
                    l = new MoraDeprec(l, pe.puncta.capioPunctum(0), pe.puncta.capioPunctum(1), pe.puncta.capioPunctum(2));
                    break;
                case COMP:
                    l = new FormaLegibilis(l, new Compressor(
                        pe.puncta.capioPunctum(0), pe.puncta.capioPunctum(1), 
                        pe.puncta.capioPunctum(2), pe.puncta.capioPunctum(3), pe.puncta.capioPunctum(4), 
                        pe.puncta.capioPunctum(5), pe.puncta.capioPunctum(6)));
                    break;
            }
        }
        return l;
        
    }
     public static void main(String[] args){
        File out_file = new File(OmUtil.getDirectory("opus"), "factor.wav");
         
        ScriptorWav sl = new ScriptorWav(out_file);
        FactorEffectorisDeprec fe = new FactorEffectorisDeprec();
        fe.distorquetor(new Punctum(1), new Punctum(1.1), new Punctum(1));
        fe.chorus(new Punctum(1), new Punctum(3), new Punctum(1), new Punctum(1, -1));
        sl.scribo(fe.capioLegibilis(new Legibilis(){
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
        }), false);        
        Functiones.ludoLimam(out_file);
    }    
    
}
