/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package origine_mundi.sampler;

import com.mina.sound.midi.EndOfTrackListner;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeSet;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Transmitter;
import origine_mundi.Integers;
import origine_mundi.MidiMachines;
import origine_mundi.OmException;
import origine_mundi.OmReceiver;
import origine_mundi.OmUtil;
import static origine_mundi.OmUtil.noteoff;
import origine_mundi.SequenceHolder;
import origine_mundi.ludior.Brev;
import origine_mundi.ludior.BrevFactory;
import origine_mundi.ludior.Brevs;
import origine_mundi.ludior.ChordStroke;
import origine_mundi.ludior.Expression;
import origine_mundi.ludior.Iunctum;
import origine_mundi.ludior.Tempus;
import origine_mundi.machine.D_110;

/**
 *
 * @author user
 */
public class BrevsSampler {
    private final MidiMachines midi_machines;
    private final Tempus tempus;
    private final ArrayList<Brevs> brevs_list;
    private RecordThread record_thread;
    public BrevsSampler(MidiMachines midi_machines, Tempus tempus, File out_file, Brevs... brevs_array){
        this.midi_machines = midi_machines;
        this.tempus = tempus;
        this.brevs_list = new ArrayList<>();
        brevs_list.addAll(Arrays.asList(brevs_array));
        if(out_file == null){
            record_thread = null;
        }else{
            record_thread = new RecordThread(out_file);
        }
    }
    public void start(){
        Sequencer sequencer;
        try {
            sequencer = OmUtil.getSequencer();
        } catch (Exception ex) {
            throw new OmException("cannot get sequencer", ex);
        }
        for(Integer key:getDeviceSet()){
            Transmitter transmitter;
            try {
                transmitter = sequencer.getTransmitter();
                Receiver row_receiver = midi_machines.get(key).getExDevice().getReceiver();
                noteoff(row_receiver);
                Receiver receiver = new OmReceiver(row_receiver, key);
                transmitter.setReceiver(receiver);
            } catch (Exception ex) {
                throw new OmException("cannot get transmitter", ex);
            }
        }
        Range range = getRange();
        SequenceHolder sequence_holder = new SequenceHolder();
        System.out.println("range head : " + range.head);
        System.out.println("duration   : " + range.getDuration());
        HashMap<String, MidiEvent> double_map = new HashMap<>(); 
        for(Brevs brevs:brevs_list){
            for(Brev brev:brevs){
                sequence_holder.addBrev(double_map, tempus, brev, midi_machines, false, range.head);
            }
        }
        try {
            sequencer.setSequence(sequence_holder.getSequence());
        } catch (InvalidMidiDataException ex) {
            throw new OmException("cannot get sequencer", ex);
        }
        System.out.println("start");
        //dont close midimachines
        EndOfTrackListner eot = new EndOfTrackListner(sequencer, null);
        sequencer.addMetaEventListener(eot);
        
        if(record_thread != null){
            System.out.println("Start recording...");
            record_thread.start();
        }
        System.out.println("Start playing...");
        sequencer.start();
        try {
            Thread.sleep(range.getDuration());
        } catch (InterruptedException ex) {
        }
        while(!eot.isEndOfTrack()){
            try {
                Thread.sleep(50);
                System.out.print("-");
            } catch (InterruptedException ex) {
            }
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
        }
        if(record_thread != null){
            record_thread.terminate();
        }
        while(!eot.isCompleted()){
            try {
                Thread.sleep(100);
                System.out.print("+");
            } catch (InterruptedException ex) {
            }
        }
        System.out.println("terminated");
        
        
    }
    public Range getRange(){
        Range range = new Range();
        for(Brevs brevs:brevs_list){
            for(Brev brev:brevs){
                //System.out.println(brev.getTalea() + ":" + brev.getBeat() + ":" + tempus.capioTempus(brev.getTalea(), brev.getBeat()));
                range.range((long)(tempus.capioTempus(brev.getTalea(), brev.getBeat())));
            }
        }
        return range;
    }
    public TreeSet<Integer> getDeviceSet(){
        TreeSet<Integer> set = new TreeSet<>();
        for(Brevs brevs:brevs_list){
            set.addAll(brevs.getDeviceSet());
        }
        return set;
    }
    class Range {
        long head;
        long tail;
        boolean primo;
        Range(){
            head = 0;
            tail = 0;
            primo = true;
        }
        void range(long point){
            if(primo){
                head = point;
                tail = point;
                primo = false;
            }else{
                head = Math.min(head, point);
                tail = Math.max(tail, point);
            }
        }
        long getDuration(){
            return tail - head;
        }
    }
    public static void main(String[] args){
        BrevsSampler sampler;
        MidiMachines midi_machines = new MidiMachines();
        midi_machines.put(0, D_110.instance());
        ChordStroke stroke0 = new ChordStroke(0.8, 1.0, 0.01, 0.015, true);
        Expression exp0 = new Expression(
                new Expression.Control(0x01, 10, 30, 0, 0.1)//,
                //new Command(ShortMessage.PITCH_BEND, 8300, 8100, 0, 0.15)
        );
        BrevFactory bf0 = new BrevFactory(
                new Iunctum(2, 0, 0), new Iunctum(2, 0, 1), new Iunctum(2, 0, 2), 
                new Iunctum(2, 0, 3), new Iunctum(2, 0, 4), new Iunctum(2, 0, 5));
        bf0.setLoco(1, 0d);
        bf0.note(new Integers(0, 1, 2, 3, 4, 5), new Integers(40, 47, 52, 56, 59, 64), 120, 1, 1, stroke0, exp0, true);
        sampler = new BrevsSampler(midi_machines, new Tempus(new Tempus.Comes[]{}, new Tempus.Rapidus[]{new Tempus.Rapidus(0, 0, 100, true)}), 
                new File("C:\\drive\\doc\\origine_mundi\\sample\\sample01.wav"), bf0.remove());
        sampler.start();
        
        /*bf0.note(new Integers(5, 4, 3, 2, 1, 0), new Integers(64, 59, 56, 52, 47, 40), 120, 1, 1, stroke0, exp0, true);
        sampler = new BrevsSampler(midi_machines, new Tempus(new Tempus.Comes[]{}, new Tempus.Rapidus[]{new Tempus.Rapidus(0, 0, 100, true)}), 
                new File("C:\\drive\\doc\\origine_mundi\\sample\\sample02.wav"), bf0.remove());
        sampler.record();*/
    }
}
