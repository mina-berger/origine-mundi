/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor.deinde;

import la.clamor.Legibilis;
import la.clamor.OrbisAestimationis;
import la.clamor.Punctum;
import la.clamor.effector.AbstractEffector;

/**
 *
 * @author mina
 */
public class LowFi extends AbstractEffector {
    private final OrbisAestimationis oa;
    private final double ratio;
    private long index_raw;

    public LowFi(Legibilis fons, double ratio) {
        super(fons);
        this.ratio = ratio;
        oa = new OrbisAestimationis((int) Math.ceil(ratio));
        index_raw = 0;
    }
    

    @Override
    public Punctum lego() {
        oa.pono(legoAFontem());
        long index = index_raw - (long) Math.ceil(Math.floor((double)index_raw / ratio) * ratio);
        index_raw++;
        return oa.capio((int)index);
    }

    @Override
    public boolean paratusSum() {
        return fonsParatusEst();
    }
    public static void main(String[] a){
        long index_raw = 0;
        double ratio = 3.5;
        //OrbisAestimationis oa = new OrbisAestimationis((int) Math.ceil(ratio));
        for(int i = 0;i < 20;i++){
            long index = (long) Math.ceil(Math.floor((double)index_raw / ratio) * ratio);
            //System.out.println(i + ":" + index + ":" + capio_index);
            index_raw++;
        }
        
        
    }
    
}
