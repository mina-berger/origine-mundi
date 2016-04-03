/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor.opus;

import com.mina.util.Doubles;
import com.mina.util.Integers;
import la.clamor.Cinctum;
import la.clamor.Legibilis;
import la.clamor.Punctum;
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

    public Parte(Mensa mensa, Integers ids, int talea_primo) {
        this.mensa = mensa;
        this.ids = ids;
        id = ids.get(0);
        this.talea_primo = talea_primo;
    }

    //protected void deinde(){
    //    talea_primo++;
    //}
    protected void cambio(int index) {
        this.id = ids.get(index);
    }

    protected void ludoClaves(int talea, Mutans<Double> repenso, Mutans<Double> diutirnitas, Mutans<Doubles> claves, Mutans<Vel> vel, int length) {
        for (int i = 0; i < length; i++) {
            ludo(talea, repenso.capio(i), diutirnitas.capio(i), claves.capio(i), vel.capio(i));
        }
    }

    protected void ludo(int talea, Mutans<Double> repenso, Mutans<Double> diutirnitas, Mutans<Double> clavis, Mutans<Vel> vel, int length) {
        for (int i = 0; i < length; i++) {
            ludo(talea, repenso.capio(i), diutirnitas.capio(i), clavis.capio(i), vel.capio(i));
        }
    }

    protected void ludo(Ludibilis ludibilis) {
        for (int i = 0; i < ludibilis.numerus(); i++) {
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

    public void ponoLevel(int talea, double repenso, Punctum level) {
        mensa.ponoLevel(id, talea_primo + talea, repenso, level);
    }
    public void ponoPan(int talea, double repenso, Cinctum pan) {
        mensa.ponoPan(id, talea_primo + talea, repenso, pan);
    }

    public void ponoFormae(String name, int talea, double repenso, int index, Punctum punctum) {
        mensa.ponoFormae(name, talea_primo + talea, repenso, index, punctum);
    }

    public void sono(int talea, double repenso, Legibilis legibilis) {
        mensa.sono(id, talea_primo + talea, repenso, legibilis);
    }

    public double diu(int talea1, double repenso1, int talea2, double repenso2) {
        return mensa.diu(talea1, repenso1, talea2, repenso2);
    }

    public abstract void creo();

}
