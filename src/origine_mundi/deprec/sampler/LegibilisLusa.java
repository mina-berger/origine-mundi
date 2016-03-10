/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package origine_mundi.deprec.sampler;

import la.clamor.Aestimatio;
import la.clamor.Legibilis;
import la.clamor.Talea;

/**
 *
 * @author user
 */
public class LegibilisLusa extends AbstractLusa {
    private final Legibilis legibilis;
    public LegibilisLusa(int track, Legibilis legibilis, Talea talea, double duration, Aestimatio volume){
        super(track, talea, duration, volume);
        this.legibilis = legibilis;
    }
    public Legibilis getLegibilis(){
        return legibilis;
    }
}
