/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package origine_mundi.archive;

import com.mina.util.Integers;
import java.io.File;
import static java.lang.Integer.toHexString;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.sampled.AudioFormat;
import la.clamor.Aestima;
import la.clamor.io.FunctionesLimae;
import la.clamor.io.LectorLimam;
import la.clamor.io.ScriptorWav;
import origine_mundi.OmException;
import origine_mundi.OmUtil;
import static origine_mundi.OmUtil.getMidiDevice;
import static origine_mundi.OmUtil.printMidiDeviceInfo;
import la.clamor.forma.FormaLegibilis;
import la.clamor.forma.MonoOut;
import static origine_mundi.archive.ArchiveUtil.toDodeciString;
import static la.clamor.Constantia.getAudioFormat;
import la.clamor.io.IOUtil;
import static la.clamor.Constantia.getAudioFormat;

/**
 *
 * @author mina
 */
public class Archiver {
    static int ms = 10000;
    File dir;
    MidiDevice device;
    Receiver receiver;
    AudioFormat format;
    public Archiver(String midi_out, AudioFormat format, File directory){
        this.format = format;
        dir = directory;
        device = getMidiDevice(midi_out, true);
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
    public File record(int channel, int note, int velocity) throws InvalidMidiDataException, InterruptedException{
        File file = new File(dir, toDodeciString(note) + "_" + toHexString(velocity) + ".raw.wav");
        RecordThread record_thread = new RecordThread(file, format);
        System.out.println("Start recording...");
        record_thread.start();
        Thread.sleep(500);
        //OmUtil.playNote(receiver, channel, note, velocity, ms);
        play(channel, note, velocity);
        Thread.sleep(1000);
        record_thread.terminate();
        System.out.println("terminated");
        return file;
    }
    public void play(int channel, int note, int velocity) throws InvalidMidiDataException, InterruptedException{
        //final int channel = 0;
        OmUtil.playNote(receiver, channel, note, velocity, ms);
    }
    public void archive(boolean drum, int note, int velocity) throws InvalidMidiDataException, InterruptedException{
        int channel = drum?9:0;
        File file = record(channel, note, velocity);
        String name = file.getName().substring(0, file.getName().indexOf(".raw"));
        File lima     = new File(file.getParentFile(), name + ".lima");
        File out_file = new File(file.getParentFile(), name + ".wav");
        AudioFormat format = FunctionesLimae.facioLimam(file, lima, new Aestima(1), false, true);
        FunctionesLimae.trim(lima, new Aestima(0.02));
        LectorLimam ll = new LectorLimam(lima);
        ScriptorWav sw = new ScriptorWav(out_file, format);
        sw.scribo(drum?new FormaLegibilis(ll, new MonoOut()):ll, false);
        ll.close();
        file.delete();
        lima.delete();
    }
    public static void archive_all(String midi_out, String machine, String sound, boolean drum, Integers range, Integers velocity) throws Exception{
        //File dir = new File("/Users/mina/drive/doc/origine_mundi/archive/");
        File dir = new File(IOUtil.getArchivePath() + machine + "/" + sound);
        dir.mkdirs();
        Archiver a = new Archiver(midi_out, getAudioFormat(48000, 2, 2), dir);
        for(int i:range){ 
            for(int j:velocity){
                a.archive(drum, i, j);
            }
        }
        a.terminate();
        
    }
    public static void main(String[] args) throws Exception{
        Integers r_piano = Integers.getSequence(21, 108, true);
        Integers r_bass = Integers.getSequence(21, 72, true);
        Integers r_guitar = Integers.getSequence(33, 100, true);
        Integers r_drum = Integers.getSequence(13, 84, true);
        Integers r_test = new Integers(51);
        
        Integers v_full = new Integers(39, 47, 55, 63, 71, 79, 87, 95, 103, 111, 119, 127);
        Integers v_drum = new Integers(63, 79, 95, 111, 127);
        Integers v_test = new Integers(63);
        //archive_all(US_122,  "m3r", "sparkles", false);
        String midi_out = "Yamaha MU500-1";
        String directory = "mu500r";
        String name = "accord";
        //archive_all(midi_out,  directory, name, false, r_test, v_test);
        archive_all(midi_out,  directory, name, false, r_guitar, v_full);
    }
}
