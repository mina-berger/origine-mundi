/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package origine_mundi.archive;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import origine_mundi.OmUtil;
import static origine_mundi.OmUtil.MU500;
import static origine_mundi.OmUtil.getMidiDevice;
import static origine_mundi.OmUtil.printMidiDeviceInfo;

/**
 *
 * @author mina
 */
public class ArchiverTest {
    Receiver receiver = null;
    int channel;
    public ArchiverTest(String port, int channel) throws MidiUnavailableException{
        MidiDevice device1 = getMidiDevice(port, true);
        printMidiDeviceInfo(device1.getDeviceInfo(), System.out, 1);
        receiver = device1.getReceiver();
        this.channel = channel;
        
    }
    public void playNotes(int... notes) throws InvalidMidiDataException, InterruptedException{
        OmUtil.playNotes(receiver, channel, notes, 0, 1000);
    }
    public void program(int program){
        message(ShortMessage.PROGRAM_CHANGE, channel, program, 0);
    }
    public void program(int bank_m, int bank_l, int program){
        message(ShortMessage.CONTROL_CHANGE, channel, 0x00, bank_m);
        message(ShortMessage.CONTROL_CHANGE, channel, 0x20, bank_l);
        message(ShortMessage.PROGRAM_CHANGE, channel, program, 0);
    }
    public void effect(int reverb, int tremolo, int chorus, int detune, int phaser){
        message(ShortMessage.CONTROL_CHANGE, channel, 91, reverb);
        message(ShortMessage.CONTROL_CHANGE, channel, 92, tremolo);
        message(ShortMessage.CONTROL_CHANGE, channel, 93, chorus);
        message(ShortMessage.CONTROL_CHANGE, channel, 94, detune);
        message(ShortMessage.CONTROL_CHANGE, channel, 95, phaser);
    }
    public void pan(int value){
        message(ShortMessage.CONTROL_CHANGE, channel, 10, value);
    }
    public void reset(){
        message(ShortMessage.CONTROL_CHANGE, channel, 121, 0);
    }
    public void allNoteoff(){
        message(ShortMessage.CONTROL_CHANGE, channel, 123, 0);
    }
    public void message(int message, int channel, int value1, int value2){
        try{
            receiver.send(new ShortMessage(message, channel, value1, value2), -1);
        }catch(Exception e){
            throw new IllegalArgumentException(e);
        }
    }
    public void closer(){
        if (receiver != null) {
            receiver.close();
        }
    }
    
    public static void main(String[] args) throws MidiUnavailableException, InvalidMidiDataException, InterruptedException{
        OmUtil.printEnv(System.out);
        ArchiverTest test = new ArchiverTest("Yamaha MU500-1", 0);
        test.allNoteoff();
        test.reset();
        test.program(0, 64, 33);
        test.pan(64);
        test.effect(0, 0, 0, 0, 0);
        test.playNotes(24);
        test.playNotes(28);
        test.playNotes(31);
        //test.playNotes(24, 60, 64, 67, 70);
    }
}
