/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package origine_mundi.ludior;

import com.mina.sound.midi.EndOfTrack;
import java.util.ArrayList;
import java.util.HashMap;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.SysexMessage;
import javax.sound.midi.Track;
import javax.sound.midi.Transmitter;
import org.junit.Test;
import origine_mundi.MidiMachine;
import origine_mundi.OmException;
import origine_mundi.OmReceiver;
import origine_mundi.OmUtil;
import static origine_mundi.OmUtil.OM_MSG_TYPE_BREVIS;
import static origine_mundi.OmUtil.OM_PRODUCT_ID;
import static origine_mundi.OmUtil.SYSEX_STATUS_AB;
import static origine_mundi.OmUtil.noteoff;

/**
 *
 * @author user
 */
public abstract class Ludior extends ArrayList<Brevs> {
    public static final int RESOLUTION = 1000;
    MidiMachines midi_machines;
    boolean simple;
    public Ludior(boolean simple){
        this.simple = simple;
    }
    protected abstract Tempus getTempus();
    protected abstract void callDevices(MidiMachines midi_machines);
    protected abstract void sequence();
    protected void brev(Brev... brevs){
        add(new Brevs(brevs));
    }
    protected void brevs(Brevs brevs){
        //System.out.println(brevs.size());
        add(brevs);
    }
    @Test
    public void ludio(){
        midi_machines = new MidiMachines();
        if(!simple){
            callDevices(midi_machines);
        }
        sequence();
        Sequence sequence;
        try {
            sequence = new Sequence(Sequence.PPQ, RESOLUTION, 1);
            tempo(sequence, 60, 0);
        } catch (InvalidMidiDataException ex) {
            throw new OmException("cannot create sequence", ex);
        }
        Tempus tempus = getTempus();
        for(Brevs brevs:this){
            for(Brev brev:brevs){
                addBrev(sequence, tempus, brev);
            }
        }
        play(sequence);
    }
    public void play(Sequence sequence){
        Sequencer sequencer;
        try {
            if(simple){
                sequencer = MidiSystem.getSequencer();
            }else{
                sequencer = OmUtil.getSequencer();
            }
        } catch (Exception ex) {
            throw new OmException("cannot get sequencer", ex);
        }
        if(simple){
            try {
                sequencer.setSequence(sequence);
                sequencer.open();
            } catch (InvalidMidiDataException ex) {
                throw new OmException("cannot set sequence", ex);
            } catch (MidiUnavailableException ex) {
                throw new OmException("cannot open sequencer", ex);
            }
        }else{
            ArrayList<Receiver> receivers = new ArrayList<>();
            for(Integer key:midi_machines.keySet()){
                Transmitter transmitter;
                try {
                    transmitter = sequencer.getTransmitter();
                } catch (MidiUnavailableException ex) {
                    throw new OmException("cannot get transmitter", ex);
                }
                try {
                    Receiver row_receiver = midi_machines.get(key).getExDevice().getReceiver();
                    noteoff(row_receiver);
                    Receiver receiver = new OmReceiver(row_receiver, key);

                    transmitter.setReceiver(receiver);
                    receivers.add(receiver);
                } catch (MidiUnavailableException ex) {
                    throw new OmException("cannot get transmitter", ex);
                }
            }
            try {
                sequencer.setSequence(sequence);
            } catch (InvalidMidiDataException ex) {
                throw new OmException("cannot get sequencer", ex);
            }
        }

        
        System.out.println("start");
        EndOfTrack eot = new EndOfTrack(sequencer, midi_machines);
        sequencer.addMetaEventListener(eot);
        sequencer.start();
        while(!eot.isCompleted()){
            try {
                Thread.sleep(1000);
                System.out.print("*");
            } catch (InterruptedException ex) {
            }
        }
        System.out.println("terminated");
    }
    private void addBrev(Sequence sequence, Tempus tempus, Brev brev){
        Track track = getTrack(sequence, brev.getTrack());
        long point = (long)(tempus.capioTempus(brev.getTalea(), brev.getBeat()));
        //System.out.println(point);
        try {
            if(simple){
                track.add(new MidiEvent(new ShortMessage(brev.getCommand(), brev.getChannel(), brev.getData1().intValue(), brev.getData2().intValue()), point));
            }else{
                int device = brev.getDevice();
                if(!midi_machines.containsKey(device)){
                    throw new OmException("cannot find device(" + device + ")");
                }
                track.add(new MidiEvent(
                        new SysexMessage(new byte[]{
                            (byte)SYSEX_STATUS_AB, 
                            (byte)OM_PRODUCT_ID, 
                            (byte)device, 
                            (byte)OM_MSG_TYPE_BREVIS, 
                            (byte)brev.getCommand(), 
                            (byte)brev.getChannel(), 
                            (byte)brev.getData1().intValue(), 
                            (byte)brev.getData2().intValue()}, 8), point));
            }
        } catch (InvalidMidiDataException ex) {
            throw new OmException("illegal midi event", ex);
        }
    }
    private Track getTrack(Sequence sequence, int index){
        if(index < 0 || index > 100){
            throw new OmException("illegal index for track");
        }
        int track_index = index + 1;
        while(sequence.getTracks().length <= track_index){
            sequence.createTrack();
        }
        return sequence.getTracks()[track_index];
    }
    private void tempo(Sequence sequence, double bpm, double beat){
        long quater = Math.round(60d * 1000000d / bpm);
        int[] data = new int[3];
        data[0] = new Long(quater / 256 / 256).intValue();
        data[1] = new Long((quater / 256) % 256).intValue();
        data[2] = new Long(quater % 256).intValue();
        meta(sequence, 0x51, data, Math.round(beat * RESOLUTION));
    }
    private void meta(Sequence sequence, int type, int[] data, double beat){
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
    
    protected class MidiMachines extends HashMap<Integer, MidiMachine>{}

}
