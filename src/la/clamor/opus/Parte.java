/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor.opus;

import la.clamor.Velocitas;

/**
 *
 * @author hiyamamina
 */
public abstract class Parte {
    private Mensa mensa;
    private int id;
    private int talea;
    
    public Parte(Mensa mensa, int id, int talea){
        this.mensa = mensa;
        this.id = id;
        this.talea = talea;
    }
    protected void deinde(){
        talea++;
    }
    
    protected void ludo(double repenso, double diutius, double clevis, Velocitas velocitas) {
        mensa.ludo(id, talea, repenso, diutius, clevis, velocitas);
    }

    
}
