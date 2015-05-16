/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package origine_mundi;

import com.mina.sound.midi.RealTimeSequencer;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.SysexMessage;
import javax.sound.midi.Transmitter;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import org.apache.commons.lang3.ArrayUtils;

/**
 *
 * @author Mina
 */
public class OmUtil {
    public static final int RESOLUTION = 1000;//per second;
    public static int SYSEX_STATUS_AB  = 0xf0;
    public static int SYSEX_STATUS_AD  = 0xf7;
    public static final int OM_PRODUCT_ID = 0x7f;
    public static final int OM_MSG_TYPE_BREVIS = 0x00;
    public static final int OM_MSG_TYPE_SYSTEM = 0x01;
    public static final String[] MU500 = new String[]{"Yamaha MU500-1", "Yamaha MU500-2", "Yamaha MU500-3", "Yamaha MU500-4"};
    public static final String MICRO_LITE_1 = "2- micro lite: Port 1";
    public static final String MICRO_LITE_2 = "2- micro lite: Port 2";
    public static final String MICRO_LITE_3 = "2- micro lite: Port 3";
    public static final String MICRO_LITE_4 = "2- micro lite: Port 4";
    public static final String MICRO_LITE_5 = "2- micro lite: Port 5";
    public static final String US_122 = "TASCAM US-122 MKII MIDI";
    
    /*public static List<Integer> toList(int[] data){
        return Arrays.asList(ArrayUtils.toObject(data));
    }*/
    /*public static int[] toArray(List<Integer> data){
        return ArrayUtils.toPrimitive(data.toArray(new Integer[]{}));
    }*/
    public static AudioFileFormat.Type FILETYPE = AudioFileFormat.Type.WAVE;
    public static AudioFormat getAudioFormat() {
        float sampleRate = 48000;
        int sampleSizeInBits = 16;
        int channels = 2;
        boolean signed = true;
        boolean bigEndian = true;
        AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits,
                                             channels, signed, bigEndian);
        return format;
    }
    public static File getDirectory(String subdir){
        boolean is_mac = System.getProperty("os.name").toLowerCase().startsWith("mac");
        File dir = new File((is_mac?"/Users/user/origine_mundi/":"C:\\drive\\doc\\origine_mundi\\") + subdir);
        dir.mkdirs();
        return dir;
    }
   
    public static SysexMessage sysex(String str) throws InvalidMidiDataException {
        String[] strs = str.split(" ");
        byte[] bytes = new byte[strs.length];
        for (int i = 0;i < strs.length;i++) {
            if(strs[i].length() > 2){
                throw new IllegalArgumentException(str);
            }
            bytes[i] = (byte) Integer.parseInt(strs[i], 16);
        }
        return new SysexMessage(bytes, bytes.length);
    }
    public static SysexMessage sysex(ArrayList<Integer> data) throws InvalidMidiDataException {
        byte[] bytes = new byte[data.size()];
        for (int i = 0;i < data.size();i++) {
            bytes[i] = (byte)data.get(i).intValue();
        }
        return new SysexMessage(bytes, bytes.length);
    }
    public static String sysex(SysexMessage sysex){
        //byte[] bytes = sysex.getData();
        byte[] bytes = sysex.getMessage();
        String str = "";
        for(int i = 0;i < bytes.length;i++){
            int value = bytes[i];
            if(value < 0){
                value += 0x100;
            }
            String hex = hex(value);
            str += (str.isEmpty()?"":" ") + hex;
        }
        return str;
    }
    public static String hex(int value){
        return fill(Integer.toHexString(value), 2);
    }
    public static String fill(String str, int length){
        return fill(str, '0', true, length);
    }
    public static String fill(String str, char fill, boolean align_right, int length){
            while(str.length() < length){
                str = align_right?fill + str:str + fill;
            }
            return str;
    }
    public static String toIndexString(int index, int max_index){
        //int byte_width = 0;
        int byte_digit = 0;
        int buff = max_index;
        while(buff > 0){
            buff = buff / 0x80;
            byte_digit++;
        }
        int decimal_width = Integer.toString(max_index, 10).length();
        
        String  byte_str = "";
        buff = index;
        for(int i = 0;i < byte_digit;i++){
            if(!byte_str.isEmpty()){
                byte_str += " ";
            }
            int divider = (int)Math.pow(0x80, byte_digit - i - 1);
            byte_str += hex(buff / divider);
            buff %= divider;
        }
        String decimal_str = fill(Integer.toString(index, 10), decimal_width);
        return "[" + byte_str + "] " + decimal_str;
        
    }
    public static class ByteHolder extends ArrayList<Integer> {
        public void addAll(int... data){
            for(int datum:data){
                add(datum);
            }
        }
        public int[] getArray(){
            return ArrayUtils.toPrimitive(this.toArray(new Integer[0]));
        }
    }
    public static class Note extends ByteHolder {
        public Note(int note){
            add(note);
        }
        @Override
        public String toString(){
            int note = get(0);
            int octave = note / 12 - 1;
            int name = note % 12;
            switch(name){
                case 0: return "C_" + octave;
                case 1: return "C#" + octave;
                case 2: return "D_" + octave;
                case 3: return "Eb" + octave;
                case 4: return "E_" + octave;
                case 5: return "F_" + octave;
                case 6: return "F#" + octave;
                case 7: return "G_" + octave;
                case 8: return "Ab" + octave;
                case 9: return "A_" + octave;
                case 10: return "Bb" + octave;
                case 11: return "B_" + octave;
            }
            return "N/A";
        }
    }

    public static class LogReceiver implements Receiver{
        PrintStream out;
        int id;
        String name;
        LogReceiver(PrintStream out, int id, String name){
            this.out = out;
            this.id = id;
            this.name = name;
        }
        @Override
        public void send(MidiMessage message, long timeStamp) {
            if(message instanceof SysexMessage){
                out.println(sysex((SysexMessage)message));
            }else{
                out.println(message);
            }
            /*try {
                PrintWriter out = new PrintWriter(new File("c:/drive/test.midi" + id + ".txt"));
                out.append(name + "\n");
                out.append(message.toString() + "\n");
                out.flush();
                out.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(OmUtil.class.getName()).log(Level.SEVERE, null, ex);
            }*/
        }
        @Override
        public void close() {}
    }

    public static void main(String[] args) {
        printEnv(System.out);

            //MidiDevice out_dev = getMidiDevice(D_110, true);
            //out_dev.open();
            //Receiver out = out_dev.getReceiver();
            //printMidiDeviceInfo(out_dev.getDeviceInfo(), System.out, 0);
            /*MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
           int count = 0;
            for (int i = 0; i < infos.length; i++) {
                try {
                    MidiDevice device = MidiSystem.getMidiDevice(infos[i]);
                    if(device.getMaxTransmitters() != 0){
                        device.open();
                        device.getTransmitter().setReceiver(new LogReceiver(System.out, i, infos[i].getName()));
                        count++;
                    }
                } catch (MidiUnavailableException mue) {
                }
            }            
            System.out.println("count:" + count);*/
            //MidiDevice in_dev = getMidiDevice(MU500_1, false);
            //in_dev.getTransmitter().setReceiver(new LogReceiver(System.out, 0, ""));
            //out.send(sysex("f0 41 10 16 11 04 01 76 00 01 76 0e f7"), -1);
            /*out.send(new ShortMessage(ShortMessage.NOTE_ON, 0, 60, 120), -1);
            Thread.sleep(1000);
            out.send(new ShortMessage(ShortMessage.NOTE_ON, 0, 60, 0), -1);
            */
            //Thread.sleep(1000);
            
            //out_dev.close();
            //in_dev.close();
        /*MidiDevice.Info[] mdis = MidiSystem.getMidiDeviceInfo();
        for (int i = 0; i < mdis.length; i++) {
            //printMidiDeviceInfo(mdis[i], System.out, 1);
            try{
                MidiDevice device = MidiSystem.getMidiDevice(mdis[i]);
                System.out.println();
                System.out.println(mdis[i].getName());
                if(device == null){
                    System.out.println("null");
                    continue;
                }
                System.out.println(device.getClass().getName());
                System.out.println("transmitter:" + device.getMaxTransmitters());
                System.out.println("receiver   :" + device.getMaxReceivers());
//device.getTransmitter();
                //printMidiDeviceInfo(mdis[i], System.out, 1);
                device.close();
            }catch(Exception e){
                System.out.println("failed:" + e.getMessage());
            }
        }*/
    }
    public static void __main(String[] args) throws Exception {
        Receiver receiver1 = null;
        Receiver receiver2 = null;
        try {
            //receiver = MidiSystem.getReceiver();
            MidiDevice device1 = getMidiDevice(MU500[0], true);
            printMidiDeviceInfo(device1.getDeviceInfo(), System.out, 1);
            receiver1 = device1.getReceiver();
            MidiDevice device2 = getMidiDevice(MU500[1], true);
            printMidiDeviceInfo(device2.getDeviceInfo(), System.out, 1);
            receiver2 = device2.getReceiver();
            int channel = 0;
            //int[] pan = new int[]{16, 32, 48, 64, 72, 96, 108, 127, 0};
            int[] pan = new int[128];
            for(int i = 0;i < 128;i++){
                pan[i] = i;
            }
            //reverb
            receiver1.send(sysex("f0 7f 7f 04 05 01 01 01 01 01 00 7f f7"), -1);
            //receiver2.send(sysex("f0 7f 7f 04 05 01 01 01 01 01 00 00 f7"), -1);
            //receiver.send(sysex("f0 7f 7f 04 05 01 49 01 01 01 01 08 7f f7"), -1);
            int[] bank;
            bank = new int[]{0, 0, 50};
            receiver1.send(new ShortMessage(ShortMessage.CONTROL_CHANGE, channel, 0, bank[0]), 1);
            receiver1.send(new ShortMessage(ShortMessage.CONTROL_CHANGE, channel, 32, bank[1]), 1);
            receiver1.send(new ShortMessage(ShortMessage.PROGRAM_CHANGE, channel, bank[2], 0), 1);
            bank = new int[]{0, 0, 50};
            receiver2.send(new ShortMessage(ShortMessage.CONTROL_CHANGE, channel, 0, bank[0]), 1);
            receiver2.send(new ShortMessage(ShortMessage.CONTROL_CHANGE, channel, 32, bank[1]), 1);
            receiver2.send(new ShortMessage(ShortMessage.PROGRAM_CHANGE, channel, bank[2], 0), 1);
            receiver1.send(new ShortMessage(ShortMessage.CONTROL_CHANGE, channel, 121,  0), 1);
            receiver2.send(new ShortMessage(ShortMessage.CONTROL_CHANGE, channel, 121,  0), 1);
            receiver1.send(new ShortMessage(ShortMessage.CONTROL_CHANGE, channel, 10,  0), 1);
            receiver2.send(new ShortMessage(ShortMessage.CONTROL_CHANGE, channel, 10,  127), 1);
            receiver1.send(new ShortMessage(ShortMessage.CONTROL_CHANGE, channel, 91, 40), 1);
            receiver2.send(new ShortMessage(ShortMessage.CONTROL_CHANGE, channel, 91, 40), 1);
            Thread.sleep(1000);
            int[] notes = new int[]{24, 60, 64, 67, 70};
            playNote(receiver1, channel, notes, 0, true);
            playNote(receiver2, channel, notes, 0, true);
            for (int c = 0; c < 5; c++) {
                for (int p = 0; p < pan.length; p++) {
                    //PAN
                    //int pan_value = (int)(pan[p] / 2) * (pan[p] % 2 == 0?1:-1) + 64;
                    int pan_value = pan[p];
                    receiver1.send(new ShortMessage(ShortMessage.CONTROL_CHANGE, channel, 10,  pan_value), 1);
                    receiver2.send(new ShortMessage(ShortMessage.CONTROL_CHANGE, channel, 10,  127 - pan_value), 1);
                    
                    receiver1.send(new ShortMessage(ShortMessage.CONTROL_CHANGE, channel, 98, 32), 1);
                    receiver1.send(new ShortMessage(ShortMessage.CONTROL_CHANGE, channel, 99,  1), 1);
                    receiver1.send(new ShortMessage(ShortMessage.CONTROL_CHANGE, channel, 6,  pan[p]), 1);
                    receiver1.send(new ShortMessage(ShortMessage.CONTROL_CHANGE, channel, 38, 0), 1);
                    receiver2.send(new ShortMessage(ShortMessage.CONTROL_CHANGE, channel, 98, 32), 1);
                    receiver2.send(new ShortMessage(ShortMessage.CONTROL_CHANGE, channel, 99,  1), 1);
                    receiver2.send(new ShortMessage(ShortMessage.CONTROL_CHANGE, channel, 6,  127 - pan[p]), 1);
                    receiver2.send(new ShortMessage(ShortMessage.CONTROL_CHANGE, channel, 38, 0), 1);
                    /*
                    receiver1.send(new ShortMessage(ShortMessage.CONTROL_CHANGE, channel, 98, 33), 1);
                    receiver1.send(new ShortMessage(ShortMessage.CONTROL_CHANGE, channel, 99,  1), 1);
                    receiver1.send(new ShortMessage(ShortMessage.CONTROL_CHANGE, channel, 6,  64), 1);
                    receiver1.send(new ShortMessage(ShortMessage.CONTROL_CHANGE, channel, 38, 32), 1);
                    */
                    receiver1.send(new ShortMessage(ShortMessage.CONTROL_CHANGE, channel, 98, 36), 1);
                    receiver1.send(new ShortMessage(ShortMessage.CONTROL_CHANGE, channel, 99,  1), 1);
                    receiver1.send(new ShortMessage(ShortMessage.CONTROL_CHANGE, channel, 6,  pan[p]), 1);
                    receiver1.send(new ShortMessage(ShortMessage.CONTROL_CHANGE, channel, 38, 0), 1);
                    receiver2.send(new ShortMessage(ShortMessage.CONTROL_CHANGE, channel, 98, 36), 1);
                    receiver2.send(new ShortMessage(ShortMessage.CONTROL_CHANGE, channel, 99,  1), 1);
                    receiver2.send(new ShortMessage(ShortMessage.CONTROL_CHANGE, channel, 6,  127 - pan[p]), 1);
                    receiver2.send(new ShortMessage(ShortMessage.CONTROL_CHANGE, channel, 38, 0), 1);
                    
                    Thread.sleep(10);
                    //playNote(receiver2, channel, notes, 1, 200);
                    //Thread.sleep(100);
                }
                for (int p = pan.length - 1; p >= 0; p--) {
                    //PAN
                    //int pan_value = (int)(pan[p] / 2) * (pan[p] % 2 == 0?1:-1) + 64;
                    int pan_value = pan[p];
                    receiver1.send(new ShortMessage(ShortMessage.CONTROL_CHANGE, channel, 10,  pan_value), 1);
                    receiver2.send(new ShortMessage(ShortMessage.CONTROL_CHANGE, channel, 10,  127 - pan_value), 1);
                    
                    receiver1.send(new ShortMessage(ShortMessage.CONTROL_CHANGE, channel, 98, 32), 1);
                    receiver1.send(new ShortMessage(ShortMessage.CONTROL_CHANGE, channel, 99,  1), 1);
                    receiver1.send(new ShortMessage(ShortMessage.CONTROL_CHANGE, channel, 6,  pan[p]), 1);
                    receiver1.send(new ShortMessage(ShortMessage.CONTROL_CHANGE, channel, 38, 0), 1);
                    receiver2.send(new ShortMessage(ShortMessage.CONTROL_CHANGE, channel, 98, 32), 1);
                    receiver2.send(new ShortMessage(ShortMessage.CONTROL_CHANGE, channel, 99,  1), 1);
                    receiver2.send(new ShortMessage(ShortMessage.CONTROL_CHANGE, channel, 6,  127 - pan[p]), 1);
                    receiver2.send(new ShortMessage(ShortMessage.CONTROL_CHANGE, channel, 38, 0), 1);
                    /*
                    receiver1.send(new ShortMessage(ShortMessage.CONTROL_CHANGE, channel, 98, 33), 1);
                    receiver1.send(new ShortMessage(ShortMessage.CONTROL_CHANGE, channel, 99,  1), 1);
                    receiver1.send(new ShortMessage(ShortMessage.CONTROL_CHANGE, channel, 6,  64), 1);
                    receiver1.send(new ShortMessage(ShortMessage.CONTROL_CHANGE, channel, 38, 32), 1);
                    */
                    receiver1.send(new ShortMessage(ShortMessage.CONTROL_CHANGE, channel, 98, 36), 1);
                    receiver1.send(new ShortMessage(ShortMessage.CONTROL_CHANGE, channel, 99,  1), 1);
                    receiver1.send(new ShortMessage(ShortMessage.CONTROL_CHANGE, channel, 6,  pan[p]), 1);
                    receiver1.send(new ShortMessage(ShortMessage.CONTROL_CHANGE, channel, 38, 0), 1);
                    receiver2.send(new ShortMessage(ShortMessage.CONTROL_CHANGE, channel, 98, 36), 1);
                    receiver2.send(new ShortMessage(ShortMessage.CONTROL_CHANGE, channel, 99,  1), 1);
                    receiver2.send(new ShortMessage(ShortMessage.CONTROL_CHANGE, channel, 6,  127 - pan[p]), 1);
                    receiver2.send(new ShortMessage(ShortMessage.CONTROL_CHANGE, channel, 38, 0), 1);
                    
                    Thread.sleep(10);
                    //playNote(receiver2, channel, notes, 1, 200);
                    //Thread.sleep(100);
                }
            }
            playNote(receiver1, channel, notes, 0, false);
            playNote(receiver2, channel, notes, 0, false);
        } finally {
            if (receiver1 != null) {
                receiver1.close();
            }
        }
    }

    public static void playNote(Receiver receiver, int channel, int[] notes, int shift, int ms) throws InvalidMidiDataException, InterruptedException {
        for (int i = 0; i < notes.length; i++) {
            ShortMessage note = new ShortMessage();
            note.setMessage(ShortMessage.NOTE_ON, channel, notes[i] + shift, 90);
            receiver.send(note, 1);
            Thread.sleep(5);
        }
        Thread.sleep(ms);
        for (int i = 0; i < notes.length; i++) {
            ShortMessage note = new ShortMessage();
            note.setMessage(ShortMessage.NOTE_OFF, channel, notes[i] + shift, 0);
            receiver.send(note, 1);
        }

    }
    public static void playNote(Receiver receiver, int channel, int[] notes, int shift, boolean on) throws InvalidMidiDataException, InterruptedException {
        for (int i = 0; i < notes.length; i++) {
            ShortMessage note = new ShortMessage();
            note.setMessage(ShortMessage.NOTE_ON, channel, notes[i] + shift, on?90:0);
            receiver.send(note, 1);
            Thread.sleep(50);
        }
    }

    public static void ddmain(String[] args) throws Exception {
        printEnv(System.out);
        /*Sequence seq = new Sequence(Sequence.PPQ, 480, 1);
        Track track = seq.getTracks()[0];
        track.add(new MidiEvent(new SysexMessage(new byte[]{(byte)0xf7, 5, 1, (byte)(144), (byte)1, (byte)60, (byte)120}, 7), 400));
        track.add(new MidiEvent(new SysexMessage(new byte[]{(byte)0xf7, 5, 1, (byte)(144), (byte)1, (byte)60, (byte)0}, 7), 1000));
        track.add(new MidiEvent(new SysexMessage(new byte[]{(byte)0xf7, 5, 2, (byte)(144), (byte)1, (byte)60, (byte)120}, 7), 1400));
        track.add(new MidiEvent(new SysexMessage(new byte[]{(byte)0xf7, 5, 2, (byte)(144), (byte)1, (byte)60, (byte)0}, 7), 2000));
        //track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, 60, 120), 400));
        //track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, 60, 0),   1000));
        playMidi(seq);*/
        //MidiDevice device = getOutputDevice(MU500_1);
        //device.getReceiver();
    }

    public static void noteoff(Receiver receiver){
        for(int i = 0;i < 16;i++){
            try {
                ShortMessage note;
                note = new ShortMessage();
                note.setMessage(ShortMessage.CONTROL_CHANGE, i, 120, 0);
                receiver.send(note, 1);
                note = new ShortMessage();
                note.setMessage(ShortMessage.CONTROL_CHANGE, i, 123, 0);
            } catch (InvalidMidiDataException ex) {
                Logger.getLogger(OmUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public static void ghmain(String[] args) throws Exception {

playMidi(new File("C:/drive/doc/origine_mundi/mid/bwv788.mid"), new String[]{MU500[0]});
//playMidi(new File("C:/drive/doc/origine_mundi/mid/rv297_2.mid"), new String[]{MLP_I_1});
//playMidi(new File("C:/drive/doc/origine_mundi/mid/MatineeIdol.mid"), new String[]{MLP_I_1});
        //playMidi(new File("C:/drive/doc/origine_mundi/mid/MatineeIdol.mid"));//rv297_2.mid
        //playMidi(new File("C:/drive/doc/origine_mundi/mid/yes-roundabout.mid"));
        //playMidi(new File("C:/drive/doc/origine_mundi/mid/bwv788.mid"));
        //playMidi(new File("C:/drive/doc/origine_mundi/mid/weather_report-teentown.mid"));
        //playMidi(new File("C:/drive/doc/origine_mundi/mid/sledgehammer.mid"));
        
        
    }

    public static void playMidi(File midiFile, String[] ports) throws Exception {
        Sequence sequence = null;
        try {
            sequence = MidiSystem.getSequence(midiFile);
        } catch (InvalidMidiDataException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }
        playMidi(sequence, ports);
    }

    public static Sequencer getSequencer() throws MidiUnavailableException{
        RealTimeSequencer sequencer = null;
        try {
            //sequencer = MidiSystem.getSequencer();
            sequencer = new RealTimeSequencer();
        } catch (MidiUnavailableException e) {
            throw e;
        }
        if (sequencer == null) {
            throw new MidiUnavailableException("can't get a Sequencer");
        }
        try {
            sequencer.open();
        } catch (MidiUnavailableException e) {
            throw e;
        }
        return sequencer;
    }
    public static void playMidi(Sequence sequence, String[] ports) throws Exception {
        Sequencer sequencer = getSequencer();
        try {
            sequencer.setSequence(sequence);
        } catch (InvalidMidiDataException e) {
            throw e;
        }
        System.out.println("[Sequencer] " + sequencer.getClass().getName());
        //printMidiDeviceInfo(sequencer.getDeviceInfo(), System.out, 1);
        MidiDevice[] devices = new MidiDevice[ports.length];
        try {
            for(int i = 0;i < ports.length;i++){
                devices[i] = getMidiDevice(ports[i], true);
                Transmitter seqTransmitter = sequencer.getTransmitter();
                Receiver receiver = devices[i].getReceiver();
                noteoff(receiver);
                seqTransmitter.setReceiver(new OmReceiver(receiver, 1));
            }
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
            throw e;
        }
        //System.out.println(sequencer.getReceivers().size());
        //System.out.println(sequencer.getTransmitters().size());

        //sequencer.getTransmitter();
        //System.out.println(sequencer.getTransmitters().size());
        System.out.println("start");
        //System.out.println("[Synthesizer] " + device1.getClass().getName());
        //printMidiDeviceInfo(device1.getDeviceInfo(), System.out, 1);
        //sequencer.addMetaEventListener(new EndOfTrack(sequencer, synthesizer));
        sequencer.start();
    }

    public static MidiDevice getMidiDevice(String strDeviceName, boolean output) {
        MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
        for (int i = 0; i < infos.length; i++) {
            if (!infos[i].getName().equals(strDeviceName)) {
                continue;
            }
            try {
                MidiDevice device = MidiSystem.getMidiDevice(infos[i]);
                if((!output && device.getMaxTransmitters() != 0) ||
                   ( output && device.getMaxReceivers   () != 0)){
                    device.open();
                    return device;
                }
            } catch (MidiUnavailableException mue) {
            }
        }
        return null;
    }

    public static void printEnv(PrintStream out) {
        MidiDevice.Info[] mdis = MidiSystem.getMidiDeviceInfo();
        out.println("MidiDevice.Info");
        for (int i = 0; i < mdis.length; i++) {
            out.println(" MidiDevice.Info[" + i + "]");
            printMidiDeviceInfo(mdis[i], out, 2);
        }
        int[] midi_file_type = MidiSystem.getMidiFileTypes();
        out.print("Midi File Type:");
        for (int i = 0; i < midi_file_type.length; i++) {
            out.print(midi_file_type[i] + " ");
        }
        out.println("");

    }

    public static void printMidiDeviceInfo(MidiDevice.Info mdi, PrintStream out, int indent) {
        String head = "";
        while (head.length() < indent) {
            head += " ";
        }
        out.println(head + "       Name : " + mdi.getName());
        out.println(head + "    Version : " + mdi.getVersion());
        out.println(head + "     Vendor : " + mdi.getVendor());
        out.println(head + "Description : " + mdi.getDescription());
        //MidiDevice md = MidiSystem.getMidiDevice(mdi);
    }

}
