/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor.deinde;

import la.clamor.Oscillator;
import java.io.File;
import java.io.IOException;
import la.clamor.Consilium;
import la.clamor.Functiones;
import static la.clamor.OscillatorUtil.getPreset;
import la.clamor.Punctum;
import la.clamor.io.ScriptorWav;
import la.clamor.Temperamentum;
import la.clamor.Velocitas;
import origine_mundi.OmUtil;

/**
 *
 * @author mina
 */
public class OscillataorDemo {
    public static void main(String[] args) throws IOException {
        demo("epiano");
        //getPreset("epiano").print();
    }
    public static void play(String name){
        File out_file = new File(OmUtil.getDirectory("opus"), "demo_" + name + ".wav");
        Functiones.ludoLimam(out_file);
        
    }
    public static void demo(String name) throws IOException{
        Oscillator osc = new Oscillator(getPreset(name));
        Consilium cns = new Consilium();
        double[] notes = new double[]{
            -24, -12, -12 + 7, 0, 4, 7, 11, 12 + 4, 12 + 7, 12 + 11, 24 + 2, 24 + 6, 24 + 9
        };
        double interval = 150;
        double current = 0;
        for(double note:notes){
            double a = Temperamentum.instance.capioHZ(note);
            cns.addo(current, osc.capioOscillationes(new Punctum(a), interval, Velocitas.una(0.2)));
            current += interval;
        }
        for(double note:notes){
            double a = Temperamentum.instance.capioHZ(note + 1);
            cns.addo(current, osc.capioOscillationes(new Punctum(a), interval, Velocitas.una(0.6)));
            current += interval;
        }
        for(double note:notes){
            double a = Temperamentum.instance.capioHZ(note + 2);
            cns.addo(current, osc.capioOscillationes(new Punctum(a), interval, Velocitas.una(1)));
            current += interval;
        }
        interval *= 8;
        cns.addo(current, osc.capioOscillationes(new Punctum(Temperamentum.instance.capioHZ(-24 + 3)), interval, Velocitas.una(1)));
        cns.addo(current, osc.capioOscillationes(new Punctum(Temperamentum.instance.capioHZ(-12 + 10)), interval, Velocitas.una(1)));
        cns.addo(current, osc.capioOscillationes(new Punctum(Temperamentum.instance.capioHZ(3)), interval, Velocitas.una(1)));
        cns.addo(current, osc.capioOscillationes(new Punctum(Temperamentum.instance.capioHZ(7)), interval, Velocitas.una(1)));
        cns.addo(current, osc.capioOscillationes(new Punctum(Temperamentum.instance.capioHZ(10)), interval, Velocitas.una(1)));
        current += interval;
        cns.addo(current, osc.capioOscillationes(new Punctum(Temperamentum.instance.capioHZ(-24 + 10)), interval, Velocitas.una(1)));
        cns.addo(current, osc.capioOscillationes(new Punctum(Temperamentum.instance.capioHZ(-12 + 10)), interval, Velocitas.una(1)));
        cns.addo(current, osc.capioOscillationes(new Punctum(Temperamentum.instance.capioHZ(2)), interval, Velocitas.una(1)));
        cns.addo(current, osc.capioOscillationes(new Punctum(Temperamentum.instance.capioHZ(8)), interval, Velocitas.una(1)));
        cns.addo(current, osc.capioOscillationes(new Punctum(Temperamentum.instance.capioHZ(10)), interval, Velocitas.una(1)));
        current += interval;
        interval *= 10;
        cns.addo(current, osc.capioOscillationes(new Punctum(Temperamentum.instance.capioHZ(-24 + 11)), interval, Velocitas.una(1)));
        cns.addo(current, osc.capioOscillationes(new Punctum(Temperamentum.instance.capioHZ(-12 + 10)), interval, Velocitas.una(1)));
        cns.addo(current, osc.capioOscillationes(new Punctum(Temperamentum.instance.capioHZ(3)), interval, Velocitas.una(1)));
        cns.addo(current, osc.capioOscillationes(new Punctum(Temperamentum.instance.capioHZ(6)), interval, Velocitas.una(1)));
        cns.addo(current, osc.capioOscillationes(new Punctum(Temperamentum.instance.capioHZ(10)), interval, Velocitas.una(1)));
        
        File out_file = new File(OmUtil.getDirectory("opus"), "demo_" + name + ".wav");
        ScriptorWav sw = new ScriptorWav(out_file);

        sw.scribo(cns, false);
        //sw.scribo(new FIRFilterDeinde(cns, 3000, 500, true), false);
        Functiones.ludoLimam(out_file);
    }
}
