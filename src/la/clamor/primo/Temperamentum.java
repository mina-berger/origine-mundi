/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package la.clamor.primo;

import org.apache.commons.math3.util.FastMath;

/**
 *
 * @author minae.hiyamae
 */
public class Temperamentum {
    public static final Temperamentum instance = new Temperamentum(12, FastMath.pow(2d, -9d / 12) * 440);
    double numero_octavi;
    double hz_medii;
    public Temperamentum(double numero_octavi, double hz_medii){
        this.numero_octavi = numero_octavi;
        this.hz_medii = hz_medii;
    }
    public double capioHZ(double clevis){
        return FastMath.pow(2d, clevis * 1d / numero_octavi) * hz_medii;        
    }
    
}
