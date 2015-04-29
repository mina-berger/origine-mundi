/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package origine_mundi;

import origine_mundi.ludior.Brev;
import java.util.ArrayList;
import java.util.TreeMap;
import javax.sound.midi.ShortMessage;

/**
 *
 * @author Mina
 */
public class OmBrevCreator {
    private int track;
    private int device;
    private TreeMap<Double, ArrayList<Prototype>> proto;
    private int beats;
    public OmBrevCreator(int track, int device, int beats){
        proto = new TreeMap<>();
        this.track = track;
        this.device = device;
        this.beats = beats;
    }
    private void add(Prototype prototype){
        double key = prototype.getBeat();
        ArrayList<Prototype> list;
        if(proto.containsKey(key)){
            list = proto.get(key);
        }else{
            list = new ArrayList<>();
            proto.put(key, list);
        }
        list.add(prototype);
    }
    public void program(int channel, int bank_m, int bank_l, int program, double beat){
        add(new Program(channel, bank_m, bank_l, program, beat));
    }
    public void note(int channel, int note, int velocity, double beat, double duration){
        add(new Note(channel, note, velocity, beat, duration));
    }
    public ArrayList<Brev> getBrev(int measure_ab, int measure_ad){
        ArrayList<Brev> ret = new ArrayList<>();
        for(int i = measure_ab;i < measure_ad;i++){
            setBrev(ret, i);
        }
        return ret;
    }
    private void setBrev(ArrayList<Brev> ret, int measure){
        double offset = measure * beats;
        for(double key:proto.keySet()){
            for(Prototype prototype:proto.get(key)){
                if(prototype instanceof Note){
                    Note note = (Note)prototype;
                    ret.add(new Brev(track, device, ShortMessage.NOTE_ON, note.getChannel(), note.getNote(), note.getVelocity(), offset + note.getBeat()));
                    ret.add(new Brev(track, device, ShortMessage.NOTE_ON, note.getChannel(), note.getNote(), new MidiByte(0), offset + note.getBeat() + note.getDuration()));
                }else if(prototype instanceof Program){
                    Program program = (Program)prototype;
                    ret.add(new Brev(track, device, ShortMessage.CONTROL_CHANGE, program.getChannel(), new MidiByte(0x00), program.getBankM  (), offset + program.getBeat()));
                    ret.add(new Brev(track, device, ShortMessage.CONTROL_CHANGE, program.getChannel(), new MidiByte(0x20), program.getBankL  (), offset + program.getBeat()));
                    ret.add(new Brev(track, device, ShortMessage.PROGRAM_CHANGE, program.getChannel(), new MidiByte(0x20), program.getProgram(), offset + program.getBeat()));
                }else{
                    throw new OmException("unknown prototype:" + prototype.getClass().getName());
                }
            }
        }
    }
    private static class Prototype{
        private int channel;
        private double beat;
        Prototype(int channel, double beat){
            this.channel = channel;
            this.beat = beat;
        }

        public int getChannel() {
            return channel;
        }

        public double getBeat() {
            return beat;
        }
    }
    private static class Program extends Prototype{
        MidiByte bank_m;
        MidiByte bank_l;
        MidiByte program;
        Program(int channel, int bank_m, int bank_l, int program, double beat){
            super(channel, beat);
            this.bank_m = new MidiByte(bank_m);
            this.bank_l = new MidiByte(bank_l);
            this.program = new MidiByte(program);
        }
        public MidiByte getBankM() {
            return bank_m;
        }

        public MidiByte getBankL() {
            return bank_l;
        }

        public MidiByte getProgram() {
            return program;
        }
    }
    private static class Note extends Prototype {
        private MidiByte note;
        private MidiByte velocity;
        private double duration;

        Note(int channel, int note, int velocity, double beat, double duration){
            super(channel, beat);
            this.note = new MidiByte(note);
            this.velocity = new MidiByte(velocity);
            this.duration = duration;
        }
        public MidiByte getNote() {
            return note;
        }

        public MidiByte getVelocity() {
            return velocity;
        }

        public double getDuration() {
            return duration;
        }
    }

//private ArrayList<OmBrev> 
    
}
