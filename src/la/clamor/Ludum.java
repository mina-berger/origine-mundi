/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor;

/**
 *
 * @author mina
 */
public class Ludum {

    private int talea;
    private double repenso;
    private double note;
    private double diuturnitas;
    private Velocitas velocitas;

    public Ludum(int talea, double repenso, int octave, double note, double diuturnitas, Velocitas velocitas) {
        this(talea, repenso, (double) octave * 12. + note, diuturnitas, velocitas);
    }

    public Ludum(int talea, double repenso, double note, double diuturnitas, Velocitas velocitas) {
        this.talea = talea;
        this.repenso = repenso;
        this.note = note;
        this.diuturnitas = diuturnitas;
        this.velocitas = velocitas;
    }

    public int capioTalea() {
        return talea;
    }

    public double capioRepenso() {
        return repenso;
    }

    public double capioNote() {
        return note;
    }

    public double capioDiuturnitas() {
        return diuturnitas;
    }

    public Velocitas capioVelocitas() {
        return velocitas;
    }

}
