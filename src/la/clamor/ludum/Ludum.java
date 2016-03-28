/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor.ludum;

import la.clamor.Vel;

/**
 *
 * @author mina
 */
public class Ludum implements Ludibilis {

    private int talea;
    private double repenso;
    private double clavis;
    private double diuturnitas;
    private Vel velocitas;

    public Ludum(int talea, double repenso, double diuturnitas, int octave, double note, Vel velocitas) {
        this(talea, repenso, diuturnitas, (double) octave * 12. + note, velocitas);
    }

    public Ludum(int talea, double repenso, double diuturnitas, double clavis, Vel velocitas) {
        this.talea = talea;
        this.repenso = repenso;
        this.clavis = clavis;
        this.diuturnitas = diuturnitas;
        this.velocitas = velocitas;
    }

    public int talea() {
        return talea;
    }

    public double repenso() {
        return repenso;
    }

    public double clavis() {
        return clavis;
    }

    public double diuturnitas() {
        return diuturnitas;
    }

    public Vel velocitas() {
        return velocitas;
    }

    @Override
    public int numerus() {
        return 1;
    }

    @Override
    public Ludum capitLudum(int index) {
        return this;
    }
    public Ludum repono(double repenso_mutavi, double clavis_nova){
        return new Ludum(talea, repenso + repenso_mutavi, clavis_nova, diuturnitas, velocitas);
    }
    public Ludum repono(double repenso_mutavi){
        return new Ludum(talea, repenso + repenso_mutavi, clavis, diuturnitas, velocitas);
    }
    
}
