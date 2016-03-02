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
import la.clamor.Instrument;
import la.clamor.io.ScriptorWav;
import la.clamor.Velocitas;
import origine_mundi.OmUtil;
import origine_mundi.archive.ArchiveLudior;

/**
 *
 * @author mina
 */
public class Demo {
    public static void main(String[] args) throws IOException {
        //demoOscillator("epiano");
        demoArchiveLudior("m3r","east_of_java");
        //getPreset("epiano").print();
    }
    public static void play(String name){
        File out_file = new File(OmUtil.getDirectory("opus"), "demo_" + name + ".wav");
        Functiones.ludoLimam(out_file);
        
    }
    public static void demoOscillator(String name) throws IOException{
        demo(new Oscillator(name));
    }
    public static void demoArchiveLudior(String machine, String sound) throws IOException{
        demo(new ArchiveLudior(machine, sound, 500));
    }
    public static void demo(Instrument inst) throws IOException{
        Consilium cns = new Consilium();
        int[] notes = new int[]{
            -24, -12, -12 + 7, 0, 4, 7, 11, 12 + 4, 12 + 7, 12 + 11, 24 + 2, 24 + 6, 24 + 9
        };
        double interval = 150;
        double current = 0;
        for(int note:notes){
            cns.addo(current, inst.capioNotum(note, interval, Velocitas.una(0.2)));
            current += interval;
        }
        for(int note:notes){
            cns.addo(current, inst.capioNotum(note + 1, interval, Velocitas.una(0.6)));
            current += interval;
        }
        for(int note:notes){
            cns.addo(current, inst.capioNotum(note + 2, interval, Velocitas.una(1)));
            current += interval;
        }
        interval *= 8;
        cns.addo(current, inst.capioNotum(-24 + 3, interval, Velocitas.una(1)));
        cns.addo(current, inst.capioNotum(-12 + 10, interval, Velocitas.una(1)));
        cns.addo(current, inst.capioNotum(3, interval, Velocitas.una(1)));
        cns.addo(current, inst.capioNotum(7, interval, Velocitas.una(1)));
        cns.addo(current, inst.capioNotum(10, interval, Velocitas.una(1)));
        current += interval;
        cns.addo(current, inst.capioNotum(-24 + 10, interval, Velocitas.una(1)));
        cns.addo(current, inst.capioNotum(-12 + 10, interval, Velocitas.una(1)));
        cns.addo(current, inst.capioNotum(2, interval, Velocitas.una(1)));
        cns.addo(current, inst.capioNotum(8, interval, Velocitas.una(1)));
        cns.addo(current, inst.capioNotum(10, interval, Velocitas.una(1)));
        current += interval;
        //interval *= 10;
        cns.addo(current, inst.capioNotum(-24 + 11, interval, Velocitas.una(1)));
        cns.addo(current, inst.capioNotum(-12 + 10, interval, Velocitas.una(1)));
        cns.addo(current, inst.capioNotum(3, interval, Velocitas.una(1)));
        cns.addo(current, inst.capioNotum(6, interval, Velocitas.una(1)));
        cns.addo(current, inst.capioNotum(10, interval, Velocitas.una(1)));
        
        File out_file = new File(OmUtil.getDirectory("opus"), "demo_" + inst.getName() + ".wav");
        ScriptorWav sw = new ScriptorWav(out_file);

        sw.scribo(cns, false);
        //sw.scribo(new FIRFilterDeinde(cns, 3000, 500, true), false);
        Functiones.ludoLimam(out_file);
    }
}
