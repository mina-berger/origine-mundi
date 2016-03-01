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
import javax.sound.sampled.AudioFormat;
import la.clamor.Aestimatio;
import la.clamor.io.FunctionesLimae;
import la.clamor.io.LectorLimam;
import la.clamor.io.ScriptorWav;
import origine_mundi.OmException;
import origine_mundi.OmUtil;
import static origine_mundi.OmUtil.US_122;
import static origine_mundi.OmUtil.getMidiDevice;
import static origine_mundi.OmUtil.printMidiDeviceInfo;
import origine_mundi.sampler.RecordThread;
import static la.clamor.Constantia.getAudioFormat;

/**
 *
 * @author mina
 */
public class Archiver {
    static int ms = 16000;
    File dir;
    MidiDevice device;
    Receiver receiver;
    AudioFormat format;
    public Archiver(AudioFormat format, File directory){
        this.format = format;
        dir = directory;
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
    public File record(int note, int velocity) throws InvalidMidiDataException, InterruptedException{
        final int channel = 0;
        File file = new File(dir, toDodeciString(note) + "_" + toHexString(velocity) + ".raw.wav");
        RecordThread record_thread = new RecordThread(file, format);
        System.out.println("Start recording...");
        record_thread.start();
        Thread.sleep(500);
        OmUtil.playNote(receiver, channel, note, velocity, ms);
        Thread.sleep(1000);
        record_thread.terminate();
        System.out.println("terminated");
        return file;
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
    public static void main(String[] args) throws Exception{
        archive("m3r", "east_of_java");
    }
    public static void archive(String machine, String sound) throws Exception{
        //File dir = new File("/Users/mina/drive/doc/origine_mundi/archive/");
        File dir = new File("D:/origine_mundi/archive/" + machine + "/" + sound);
        dir.mkdirs();
        Archiver a = new Archiver(getAudioFormat(48000, 2, 2), dir);
        for(int i = 21;i < 108;i++){ // 88 key for piano 
            for(int j = 0;j < 16;j++){
                File file = a.record(i, j * 8 + 7);
                String name = file.getName().substring(0, file.getName().indexOf(".raw"));
                File lima     = new File(file.getParentFile(), name + ".lima");
                File out_file = new File(file.getParentFile(), name + ".wav");
                AudioFormat format = FunctionesLimae.facioLimam(file, lima, new Aestimatio(1), false, true);
                FunctionesLimae.trim(lima, new Aestimatio(0.01));
                LectorLimam ll = new LectorLimam(lima);
                ScriptorWav sw = new ScriptorWav(out_file, format);
                sw.scribo(ll, false);
                ll.close();
                file.delete();
                lima.delete();
                //break;
            }
            //break;
        }
        
        //Functiones.ludoLimam(out_file);

        a.terminate();
        
    }
}
