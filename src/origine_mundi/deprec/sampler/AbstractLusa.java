/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package origine_mundi.deprec.sampler;

import la.clamor.Aestimatio;
import la.clamor.Talea;

/**
 *
 * @author user
 */
public abstract class AbstractLusa {
    protected final int track;
    protected final Talea talea;
    protected final Aestimatio volume;
    protected final double duration;
    public AbstractLusa(int track, Talea talea, double duration, Aestimatio volume){
        this.track = track;
        this.talea = talea;
        this.duration = duration;
        this.volume = volume;
    }

    public Talea getTalea() {
        return talea;
    }

    public int getTrack() {
        return track;
    }

    public Aestimatio getVolume() {
        return volume;
    }

    public double getDuration() {
        return duration;
    }

    public Talea getTermina() {
        return talea.shiftBeat(duration);
    }
    
}
