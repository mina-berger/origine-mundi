/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package origine_mundi.sampler;

import la.clamor.Envelope;
import la.clamor.Aestimatio;
import la.clamor.Talea;
import origine_mundi.ProcessorInfo;

/**
 *
 * @author Mina
 */
public class LimaLusa extends AbstractLusa {
    
    private final String key;
    private final Envelope env;
    private final ProcessorInfo[] processor_infos;
    public LimaLusa(int track, String key, Talea talea, double duration, Envelope env, Aestimatio volume, ProcessorInfo... processor_infos){
        super(track, talea, duration, volume);
        this.key = key;
        this.env = env;
        this.processor_infos = processor_infos;
    }
    public String getKey() {
        return key;
    }

    public Envelope getEnvelope() {
        return env;
    }

    public ProcessorInfo[] getProcessorInfo() {
        return processor_infos;
    }
    public static void main(String[] a){
    }


}
