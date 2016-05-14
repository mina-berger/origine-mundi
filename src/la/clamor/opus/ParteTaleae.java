/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor.opus;

import com.mina.util.Integers;

/**
 *
 * @author hiyamamina
 */
public abstract class ParteTaleae extends Parte{

    private final int length;
    public ParteTaleae(Mensa mensa, Integers ids, int talea_primo, int length) {
        super(mensa, ids, talea_primo);
        this.length = length;
    }

    @Override
    public void creo() {
        for (int i = 0; i < length; i++) {
            cambio(0);
            creo(i);
        }
        
    }
    protected abstract void creo(int t);
    
}
