/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor.forma;

import la.clamor.Punctum;

/**
 *
 * @author mina
 */
public class MonoOut implements Forma {
    @Override
    public Punctum formo(Punctum lectum) {
        return new Punctum(lectum.average());
    }

    @Override
    public int resto() {
        return 0;
    }
    
}
