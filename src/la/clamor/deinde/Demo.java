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
import la.clamor.Vel;
import la.clamor.io.IOUtil;
import origine_mundi.OmUtil;
import origine_mundi.archive.ArchiveLudior;

/**
 *
 * @author mina
 */
public class Demo {
    public static void main(String[] args) throws IOException {
        //demoOscillator("epiano");
        demoArchiveLudior("mu500r","clgt", false);
        //getPreset("epiano").print();
    }
    public static void play(String name){
        File out_file = new File(IOUtil.getDirectory("opus"), "demo_" + name + ".wav");
        Functiones.ludoLimam(out_file);
        
    }
    public static void demoOscillator(String name, boolean creo) throws IOException{
        demo(new Oscillator(name), creo);
    }
    public static void demoArchiveLudior(String machine, String sound, boolean creo) throws IOException{
        demo(new ArchiveLudior(machine, sound, 500), creo);
    }
    public static void demo(Instrument inst, boolean creo) throws IOException{
        File out_file = new File(IOUtil.getDirectory("opus"), "demo_" + inst.getName() + ".wav");
        if(creo){
            Consilium cns = new Consilium();
            int[] notes = new int[]{
                36, 48, 48 + 7, 60, 64, 67, 71, 72 + 4, 72 + 7, 72 + 11, 84 + 2, 84 + 6, 84 + 9
                //-24, -12, -12 + 7, 0, 4, 7, 11, 12 + 4, 12 + 7, 12 + 11, 24 + 2, 24 + 6, 24 + 9
            };
            double interval = 150;
            double current = 0;
            for(int note:notes){
                cns.addo(current, inst.capioNotum(note, interval, new Vel(0.2)));
                current += interval;
            }
            for(int note:notes){
                cns.addo(current, inst.capioNotum(note + 1, interval, new Vel(0.6)));
                current += interval;
            }
            for(int note:notes){
                cns.addo(current, inst.capioNotum(note + 2, interval, new Vel(1)));
                current += interval;
            }
            interval *= 8;
            cns.addo(current, inst.capioNotum(36 + 3, interval, new Vel(1)));
            cns.addo(current, inst.capioNotum(48 + 10, interval, new Vel(1)));
            cns.addo(current, inst.capioNotum(63, interval, new Vel(1)));
            cns.addo(current, inst.capioNotum(67, interval, new Vel(1)));
            cns.addo(current, inst.capioNotum(70, interval, new Vel(1)));
            current += interval;
            cns.addo(current, inst.capioNotum(36 + 10, interval, new Vel(1)));
            cns.addo(current, inst.capioNotum(48 + 10, interval, new Vel(1)));
            cns.addo(current, inst.capioNotum(62, interval, new Vel(1)));
            cns.addo(current, inst.capioNotum(68, interval, new Vel(1)));
            cns.addo(current, inst.capioNotum(70, interval, new Vel(1)));
            current += interval;
            //interval *= 10;
            cns.addo(current, inst.capioNotum(36 + 11, interval, new Vel(1)));
            cns.addo(current, inst.capioNotum(48 + 10, interval, new Vel(1)));
            cns.addo(current, inst.capioNotum(63, interval, new Vel(1)));
            cns.addo(current, inst.capioNotum(66, interval, new Vel(1)));
            cns.addo(current, inst.capioNotum(70, interval, new Vel(1)));

            ScriptorWav sw = new ScriptorWav(out_file);

            sw.scribo(cns, false);
            cns.close();
            //sw.scribo(new FIRFilterDeinde(cns, 3000, 500, true), false);
        }
        Functiones.ludoLimam(out_file);
    }
}
