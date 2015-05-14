/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package origine_mundi.sampler;

import la.clamor.Envelope;
import la.clamor.Punctum.Aestimatio;
import origine_mundi.filter.FilterInfo;

/**
 *
 * @author Mina
 */
public class LimaLusa {
    private final String key;
    
    private final int talea;
    private final double beat;
    private final double duration;
    private final Envelope env;
    private final FilterInfo filter_info;
    private final Aestimatio volume;
    public LimaLusa(String key, int talea, double beat, double duration, Envelope env, FilterInfo filter_info, Aestimatio volume){
        this.key = key;
        this.talea = talea;
        this.beat = beat;
        this.duration = duration;
        this.env = env;
        this.filter_info = filter_info;
        this.volume = volume;
    }
    public int getTalea() {
        return talea;
    }
    public double getBeat() {
        return beat;
    }
    public String getKey() {
        return key;
    }

    public double getDuration() {
        return duration;
    }

    public Envelope getEnvelope() {
        return env;
    }

    public FilterInfo getFilterInfo() {
        return filter_info;
    }
    public static void main(String[] a){
    }

    public Aestimatio getVolume() {
        return volume;
    }

}
