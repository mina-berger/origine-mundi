/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package origine_mundi.sampler;

import la.clamor.Legibilis;
import la.clamor.Punctum;
import origine_mundi.deprec.ChorusDeprec;
import origine_mundi.deprec.MoraDeprec;
import origine_mundi.ProcessorInfo;

/**
 *
 * @author user
 */
public abstract class EffectorInfo implements ProcessorInfo {
    public static class ThruInfo extends EffectorInfo {
        
    }
    public static class ChorusInfo extends EffectorInfo {
        private final Punctum depth;
        private final Punctum frequency;
        private final Punctum dry_out;
        private final Punctum wet_out;
        public ChorusInfo(Punctum depth, Punctum frequency, Punctum dry_out, Punctum wet_out){
            this.depth = depth;
            this.frequency = frequency;
            this.dry_out = dry_out;
            this.wet_out = wet_out;
        }
    }
    public static class MoraInfo extends EffectorInfo {
        private final Punctum duration;
        private final Punctum times;
        private final Punctum feedback;
        
        public MoraInfo(Punctum duration, Punctum times, Punctum feedback){
            this.duration = duration;
            this.times = times;
            this.feedback = feedback;
        }
    }
    public static Legibilis getEffector(Legibilis legibilis, EffectorInfo info){
        
        if(info instanceof ThruInfo){
            return legibilis;
        }else if(info instanceof MoraInfo){
            MoraInfo mora_info = (MoraInfo)info;
            return new MoraDeprec(legibilis, mora_info.duration, mora_info.times, mora_info.feedback);
        }else if(info instanceof ChorusInfo){
            ChorusInfo chorus_info = (ChorusInfo)info;
            return new ChorusDeprec(legibilis, chorus_info.depth, chorus_info.frequency, chorus_info.dry_out, chorus_info.wet_out);
        }
        return legibilis;
        
    }
}
