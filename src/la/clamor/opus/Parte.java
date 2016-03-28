/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor.opus;

import com.mina.util.Doubles;
import com.mina.util.Integers;
import la.clamor.Vel;
import la.clamor.ludum.Ludibilis;
import la.clamor.ludum.Ludum;
import la.clamor.ludum.Mutans;

/**
 *
 * @author hiyamamina
 */
public abstract class Parte {
    private final Mensa mensa;
    private final int talea_primo;
    private Integers ids;
    private int id;
    
    public Parte(Mensa mensa, Integers ids, int talea_primo){
        this.mensa = mensa;
        this.ids = ids;
        id = ids.get(0);
        this.talea_primo = talea_primo;
    }
    //protected void deinde(){
    //    talea_primo++;
    //}
    protected void cambio(int index){
        this.id = ids.get(index);
    }
    
    protected void ludoClaves(int talea, Mutans<Double> repenso, Mutans<Double> diutirnitas, Mutans<Doubles> claves, Mutans<Vel> vel, int length){
        for (int i = 0; i < length; i++) {
            ludo(talea, repenso.capio(i), diutirnitas.capio(i), claves.capio(i), vel.capio(i));
        }
    }
    protected void ludo(int talea, Mutans<Double> repenso, Mutans<Double> diutirnitas, Mutans<Double> clavis, Mutans<Vel> vel, int length){
        for (int i = 0; i < length; i++) {
            ludo(talea, repenso.capio(i), diutirnitas.capio(i), clavis.capio(i), vel.capio(i));
        }
    }
    protected void ludo(Ludibilis ludibilis) {
        for(int i = 0;i < ludibilis.numerus();i++){
            Ludum ludum = ludibilis.capitLudum(id);
            ludo(ludum.talea(), ludum.repenso(), ludum.diuturnitas(), ludum.clavis(), ludum.velocitas());
        }
    }
    protected void ludo(int talea, double repenso, double diutius, double clavis, Vel velocitas) {
        mensa.ludo(id, talea_primo + talea, repenso, diutius, clavis, velocitas);
    }
    protected void ludo(int talea, double repenso, double diutius, Doubles claves, Vel velocitas) {
        mensa.ludo(id, talea_primo + talea, repenso, diutius, claves, velocitas);
    }
    public abstract void creo();
    
}
