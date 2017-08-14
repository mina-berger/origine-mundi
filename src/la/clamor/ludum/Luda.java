/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor.ludum;

import java.util.ArrayList;
import java.util.Arrays;
import la.clamor.Vel;

/**
 *
 * @author hiyamamina
 */
public class Luda extends ArrayList<Ludum> implements Ludibilis {

    public Luda(Ludum... luda) {
        super(Arrays.asList(luda));
    }

    public Luda(int talea, Mutans<Double> repenso, Mutans<Double> diutirnitas, Mutans<Double> clavis, Mutans<Vel> vel, int length) {
        for (int i = 0; i < length; i++) {
            add(new Ludum(talea, repenso.capio(i), diutirnitas.capio(i), clavis.capio(i), vel.capio(i)));
        }
    }

    @Override
    public int numerus() {
        return size();
    }

    @Override
    public Ludum capitLudum(int index) {
        return get(index);
    }
    
    public Luda repono(double repenso_mutavi, double... claves_novae){
        Luda reditum = new Luda();
        for(int i = 0;i < size();i++){
            reditum.add(get(i).repono(repenso_mutavi, claves_novae[i % size()]));
        }
        return reditum;
    }
    
    public Luda repono(double repenso_mutavi){
        Luda reditum = new Luda();
        for(int i = 0;i < size();i++){
            reditum.add(get(i).repono(repenso_mutavi));
        }
        return reditum;
    }

}
