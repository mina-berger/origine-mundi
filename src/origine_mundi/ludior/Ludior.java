/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package origine_mundi.ludior;

import origine_mundi.EndOfTrackListner;
import java.util.ArrayList;
import java.util.HashMap;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Transmitter;
import org.junit.Test;
import origine_mundi.MidiMachines;
import origine_mundi.OmException;
import origine_mundi.OmReceiver;
import origine_mundi.OmUtil;
import static origine_mundi.OmUtil.noteoff;
import origine_mundi.SequenceHolder;

/**
 *
 * @author user
 */
public abstract class Ludior extends ArrayList<Brevs> {
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
        SequenceHolder sequence_holder = new SequenceHolder();
        /*Sequence sequence;
        try {
            sequence = new Sequence(Sequence.PPQ, RESOLUTION, 1);
            tempo(sequence, 60, 0);
        } catch (InvalidMidiDataException ex) {
            throw new OmException("cannot create sequence", ex);
        }*/
        Tempus tempus = getTempus();
        HashMap<String, MidiEvent> double_map = new HashMap<>(); 
        for(Brevs brevs:this){
            for(Brev brev:brevs){
                sequence_holder.addBrev(double_map, tempus, brev, midi_machines, simple, 0);
            }
        }
        play(sequence_holder.getSequence());
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
                    Receiver row_receiver = midi_machines.get(key).getExDevice().getReceiver();
                    noteoff(row_receiver);
                    Receiver receiver = new OmReceiver(row_receiver, key);
                    transmitter.setReceiver(receiver);
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
        EndOfTrackListner eot = new EndOfTrackListner(sequencer, midi_machines);
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
    

}
