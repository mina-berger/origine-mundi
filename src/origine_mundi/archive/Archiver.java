/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package origine_mundi.archive;

import java.io.File;
import static java.lang.Integer.toHexString;
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
import static origine_mundi.archive.ArchiveUtil.toDodeciString;

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
    public void archive(int note, int velocity) throws InvalidMidiDataException, InterruptedException{
        File file = record(note, velocity);
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
    }
    public static void archive_all(String machine, String sound, boolean test) throws Exception{
        //File dir = new File("/Users/mina/drive/doc/origine_mundi/archive/");
        File dir = new File("D:/origine_mundi/archive/" + machine + "/" + sound);
        dir.mkdirs();
        Archiver a = new Archiver(getAudioFormat(48000, 2, 2), dir);
        if(test){
            a.archive(60, 127);
        }else{
            for(int i = 21;i < 108;i++){ // 88 key for piano 
                for(int j = 4;j < 16;j++){
                    a.archive(i, j * 8 + 7);
                    /*File file = a.record(i, j * 8 + 7);
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
                    lima.delete();*/
                }
            }
        }
        a.terminate();
        
    }
    public static void main(String[] args) throws Exception{
        //OmUtil.playNote(receiver, channel, note, velocity, ms);
        archive_all("m3r", "east_of_java", false);
    }
}
