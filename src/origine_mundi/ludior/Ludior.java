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
        HashMap<String, MidiEvent> double_map = new HashMap<>(); 
        for(Brevs brevs:this){
            for(Brev brev:brevs){
                addBrev(sequence, double_map, tempus, brev);
            }
        }
        play(sequence);
    }
    public void play(Sequence sequence){
        Sequencer sequencer;
        try {
            sequencer = simple?MidiSystem.getSequencer():OmUtil.getSequencer();
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
            //ArrayList<Receiver> receivers = new ArrayList<>();
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
                    //receivers.add(receiver);
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
                //System.out.print("*");
            } catch (InterruptedException ex) {
            }
        }
        System.out.println("terminated");
    }
    private void addBrev(Sequence sequence, HashMap<String, MidiEvent> double_map, Tempus tempus, Brev brev){
        Iunctum iunctum = brev.getIunctum();
        int i_track = iunctum.getTrack();
        int device = iunctum.getDevice();
        int command = brev.getCommand();
        int channel = iunctum.getChannel();
        int data1 = brev.getData1().intValue();
        Track track = getTrack(sequence, i_track);
        long point = (long)(tempus.capioTempus(brev.getTalea(), brev.getBeat()));
        //System.out.println(point);
        try {
            MidiEvent midi_event;
            if(simple){
                midi_event = new MidiEvent(new ShortMessage(command, channel, data1, brev.getData2().intValue()), point);
                //if(command == ShortMessage.CONTROL_CHANGE && data1 == 0x0a){
                //    System.out.println("debug pan:" + channel + ":" + brev.getData2().intValue() + ":" + point);
                //}
                
            }else{
                if(!midi_machines.containsKey(device)){
                    throw new OmException("cannot find device(" + device + ")");
                }
                midi_event = new MidiEvent(
                        new SysexMessage(new byte[]{
                            (byte)SYSEX_STATUS_AB, 
                            (byte)OM_PRODUCT_ID, 
                            (byte)device, 
                            (byte)OM_MSG_TYPE_BREVIS, 
                            (byte)command, 
                            (byte)channel, 
                            (byte)data1, 
                            (byte)brev.getData2().intValue()}, 8), point);
            }
            String double_key = null;
            if(command == ShortMessage.PITCH_BEND){
                double_key = i_track + "_" + device + "_" + command + "_" + channel + "_" + point;
            }else if(command == ShortMessage.CONTROL_CHANGE){
                double_key = i_track + "_" + device + "_" + command + "_" + channel + "_"+ data1 + "_" + point;
            }
            if(double_key != null){
                if(double_map.containsKey(double_key)){
                    track.remove(double_map.get(double_key));
                    //System.out.println(double_key);
                }
                double_map.put(double_key, midi_event);
            }
            track.add(midi_event);
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
