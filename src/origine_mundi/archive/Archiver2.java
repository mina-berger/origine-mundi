/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package origine_mundi.archive;

import java.io.File;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import la.clamor.Aestimatio;
import static la.clamor.Constantia.getAudioFormat;
import la.clamor.io.FunctionesLimae;
import la.clamor.io.LectorLimam;
import la.clamor.io.ScriptorWav;
import origine_mundi.OmException;
import origine_mundi.OmUtil;
import static origine_mundi.OmUtil.US_122;
import static origine_mundi.OmUtil.getMidiDevice;
import static origine_mundi.OmUtil.printMidiDeviceInfo;
import origine_mundi.sampler.RecordThread;

/**
 *
 * @author mina
 */
public class Archiver2 {
    static int ms = 16000;
    static File dir = new File("C:/drive/doc/origine_mundi/archive/");
    MidiDevice device;
    Receiver receiver;
    public Archiver2(){
        device = getMidiDevice(US_122, true);
        printMidiDeviceInfo(device.getDeviceInfo(), System.out, 1);
        try {
            receiver = device.getReceiver();
            OmUtil.noteoff(receiver);
        } catch (MidiUnavailableException ex) {
            if (receiver != null) {
                receiver.close();
            }
            throw new OmException("fail to retrieve receiver:", ex);
        }
    }
    public void terminate(){
        if (receiver != null) {
            receiver.close();
        }
    }
    public void record(int note, int velocity) throws InvalidMidiDataException, InterruptedException{
        final int channel = 0;
        RecordThread record_thread = new RecordThread(
            new File(dir, toDodeciString(note) + "_" + toHexString(velocity) + ".raw.wav"),
            getAudioFormat(96000, 2, 2));
        System.out.println("Start recording...");
        record_thread.start();
        Thread.sleep(500);
        OmUtil.playNote(receiver, channel, note, velocity, ms);
        Thread.sleep(1000);
        record_thread.terminate();
        System.out.println("terminated");
    }
    public static void main(String[] args) throws Exception{
        /*Archiver2 a = new Archiver2();
        try {
            a.record(60, 120);
        } catch (InvalidMidiDataException | InterruptedException ex) {
            a.terminate();
        }*/
        //String dir = "/Users/mina/drive/doc/origine_mundi/archive/";
        String dir = "C:/drive/doc/origine_mundi/archive/";
        File file = new File(dir + "50_78.raw.wav");
        File lima = new File(dir + "50_78.lima");
        //AudioFileFormat format = AudioSystem.getAudioFileFormat(file);
        //System.out.println(format);
        
        FunctionesLimae.facioLimam(file, lima, new Aestimatio(1), false);
        FunctionesLimae.trim(lima, new Aestimatio(0.01));
        LectorLimam ll = new LectorLimam(lima);
        File out_file = new File(dir + "50_78.wav");
        ScriptorWav sw = new ScriptorWav(out_file);

        sw.scribo(ll, false);
        
        //AudioFileFormat format2 = new AudioFileFormat(AudioFileFormat.Type.WAVE, getAudioFormat(), 310272);
        //System.out.println(format2);
        
        
    }
    private static String toHexString(int i){
        String str = Integer.toHexString(i);
        if(str.length() < 2){
            return "0" + str;
        }
        return str;
    }
    private static String toDodeciString(int i){
        return Integer.toHexString(i / 12) + Integer.toHexString(i % 12);
    }
    
    
    
    
}
