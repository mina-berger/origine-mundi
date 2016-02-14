/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor.deinde;

import la.clamor.Legibilis;
import la.clamor.Punctum;
import la.clamor.effector.AbstractEffector;

/**
 *
 * @author mina
 */
public class Resampler extends AbstractEffector {
    private final double ratio;
    private double index;
    private Punctum ante;
    private Punctum post;
    private boolean fons_parata;

    public Resampler(Legibilis fons, double freq_fontis, double freq_sorti) {
        super(fons);
        ratio = freq_fontis / freq_sorti;
        System.out.println("DEBUG:" + ratio);
        index = 0;
        if(!fons.paratusSum()){
            ante = null;
            post = null;
            fons_parata = false;
            return;
        }
        ante = fons.lego();
        if(!fons.paratusSum()){
            post = null;
            fons_parata = false;
        }
        post = fons.lego();
        fons_parata = true;
    }
    private void deinde(){
        ante = post;
        if(!fonsParatusEst()){
            post = null;
            fons_parata = false;
            return;
        }
        post = legoAFontem();
    }
    

    @Override
    public Punctum lego() {
        Punctum punctum = ante.multiplico(1 - index).addo(post.multiplico(index));
        index += ratio;
        while(index >= 1){
            deinde();
            index--;
        }
        return punctum;
    }

    @Override
    public boolean paratusSum() {
        return fons_parata;
    }
}
