/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package origine_mundi.player;

import origine_mundi.ludior.Brev;
import origine_mundi.*;
import origine_mundi.EndOfTrackListner;
import java.util.ArrayList;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.SysexMessage;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static origine_mundi.OmUtil.printMidiDeviceInfo;
import origine_mundi.ludior.Iunctum;

/**
 *
 * @author Mina
 */
public abstract class OmPlayerSimple {
    private Sequence sequence;
    private final MidiMachines midi_machines;
    //private Track track;
    private Integer track_index;
    private MidiDevice device;
    private Integer device_id;
    public static final int RESOLUTION = 480;
    
    public OmPlayerSimple(){
        try {
            sequence = new Sequence(Sequence.PPQ, RESOLUTION, 1);
        } catch (InvalidMidiDataException ex) {
            throw new OmException("cannot create sequence", ex);
        }
        midi_machines = new MidiMachines();
        track_index = null;
        device = null;
        device_id = null;
    }
    public void callDevice(int id){
        device_id = id;
    }
    public void callDevice(int id, MidiMachine mm){
        if(midi_machines.containsKey(id)){
            device = midi_machines.get(id).getExDevice();
        }else{
            device = mm.getExDevice();
            printMidiDeviceInfo(device.getDeviceInfo(), System.out, 0);
            midi_machines.put(id, mm);
        }
        device_id = id;
    }
    public void callTrack(int index){
        if(index < 0 || index > 100){
            throw new OmException("illegal index for track");
        }
        while(sequence.getTracks().length <= index){
            sequence.createTrack();
        }
        track_index = index;
    }
    public void meta(int type, int[] data, double beat){
        byte[] b_data = new byte[data.length];
        for(int i = 0;i < data.length;i++){
            b_data[i] = (byte)data[i];
        }
        try {
            sequence.getTracks()[0].add(new MidiEvent(new MetaMessage(type, b_data, data.length), Math.round(beat * RESOLUTION)));
        } catch (InvalidMidiDataException ex) {
            throw new OmException("illegal meta event", ex);
        }
        
    }
    public void brev(ArrayList<Brev> brevs){
        for(Brev brev:brevs){
            Iunctum iunctum = brev.getIunctum();
            brev(iunctum.getTrack(), iunctum.getDevice(), brev.getCommand(), iunctum.getChannel(), brev.getData1().intValue(), brev.getData2().intValue(), brev.getTalea().getBeat());
        }
    }
    public void brev(int command, int channel, int data1, int data2, double beat){
        //System.out.println(track_index);
        //System.out.println(device_id);
        brev(track_index, device_id, command, channel, data1, data2, beat);
    }
    public void brev(int track_index, int device_id, int command, int channel, int data1, int data2, double beat){
        try {
            sequence.getTracks()[track_index].add(new MidiEvent(new ShortMessage(command, channel, data1, data2), 
                    Math.round(beat * RESOLUTION)));
        } catch (InvalidMidiDataException ex) {
            throw new OmException("illegal midi event", ex);
        }
    }
    public void sysex(SysexMessage sysex, double beat){
        sysex(track_index, device_id, sysex, beat);
    }
    public void sysex(int track_index, int device_id, SysexMessage sysex, double beat){
        sequence.getTracks()[track_index].add(new MidiEvent(
                sysex,
                Math.round(beat * RESOLUTION)));
    }
    public void program(int channel, int bank_m, int bank_l, int program, double beat){
        //long tick = Math.round(beat * RESOLUTION);
        brev(ShortMessage.CONTROL_CHANGE, channel,    0x00, bank_m, beat);
        brev(ShortMessage.CONTROL_CHANGE, channel,    0x20, bank_l, beat);
        brev(ShortMessage.PROGRAM_CHANGE, channel, program,      0, beat);
    }
    public void note(int channel, int note, int velocity, double beat, double duration){
        brev(ShortMessage.NOTE_ON, channel, note, velocity, beat);
        brev(ShortMessage.NOTE_ON, channel, note,        0, beat + duration);
    }
    public void tempo(double bpm, double beat){
        long quater = Math.round(60d * 1000000d / bpm);
        int[] data = new int[3];
        data[0] = new Long(quater / 256 / 256).intValue();
        data[1] = new Long((quater / 256) % 256).intValue();
        data[2] = new Long(quater % 256).intValue();
        meta(0x51, data, Math.round(beat * RESOLUTION));
    }
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void main(){
        setSequence();
        play();
    }
    
    public abstract void setSequence();
    public void play(){
        Sequencer sequencer;
        //Transmitter transmitter;
        try {
            sequencer = MidiSystem.getSequencer();
            sequencer.setSequence(sequence);
            sequencer.open();
            //transmitter = sequencer.getTransmitter();
            //transmitter.setReceiver(MidiSystem.getReceiver());
        } catch (Exception ex) {
            throw new OmException("cannot get sequencer", ex);
        }
        
        System.out.println("start");
        EndOfTrackListner eot = new EndOfTrackListner(sequencer, midi_machines);
        sequencer.addMetaEventListener(eot);
        sequencer.start();
        while(!eot.isCompleted()){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
            }
        }        
    }

}
