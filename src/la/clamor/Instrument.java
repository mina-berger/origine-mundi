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
public interface Instrument {
    public Legibilis capioNotum(double note, double diuturnitas, Velocitas velocitas);
    public Legibilis capioNotum(int octave, double note, double diuturnitas, Velocitas velocitas);
    
    public String getName();
}
