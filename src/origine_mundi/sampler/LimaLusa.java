/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package origine_mundi.sampler;

import la.clamor.Envelope;
import la.clamor.Aestimatio;
import la.clamor.Talea;
import origine_mundi.filter.FilterInfo;

/**
 *
 * @author Mina
 */
public class LimaLusa {
    
    private final int track;
    private final String key;
    private final Talea talea;
    private final double duration;
    private final Envelope env;
    private final FilterInfo filter_info;
    private final Aestimatio volume;
    public LimaLusa(int track, String key, Talea talea, double duration, Envelope env, FilterInfo filter_info, Aestimatio volume){
        this.track = track;
        this.key = key;
        this.talea = talea;
        this.duration = duration;
        this.env = env;
        this.filter_info = filter_info;
        this.volume = volume;
    }
    public Talea getTalea() {
        return talea;
    }
    public String getKey() {
        return key;
    }
    public Talea getTermina() {
        return talea.shiftBeat(duration);
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

    public int getTrack() {
        return track;
    }

}
