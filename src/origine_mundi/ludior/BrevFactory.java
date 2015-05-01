/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package origine_mundi.ludior;

import java.util.ArrayList;
import java.util.HashMap;
import javax.sound.midi.ShortMessage;
import origine_mundi.Integers;
import origine_mundi.MidiByte;
import origine_mundi.OmException;
import origine_mundi.ludior.Expression.Command;
import origine_mundi.ludior.Expression.Control;
import origine_mundi.ludior.Expression.SettingHolder;

/**
 *
 * @author Mina
 */
public class BrevFactory {
    private Brevs brevs;
    private final Iunctum[] iuncta;
    private int talea;
    private double beat;
    public BrevFactory(Iunctum... iuncta){
        brevs = new Brevs();
        this.iuncta = iuncta;
        this.talea = 0;
        this.beat = 0;
    }
    //public void setIunctum(Iunctum iunctum){
    //    this.iunctum = iunctum;
    //}
    //public void setBeat(double beat){
    //    this.beat = beat;
   // }
    public void setLoco(int talea, double beat){
        this.talea = talea;
        this.beat = beat;
    }
    public void note(int iunctum_index, int note, int velocity, double duration, double rate, boolean shift){
        brevs.add(new Brev(iuncta[iunctum_index], ShortMessage.NOTE_ON, new MidiByte(note), new MidiByte(velocity), talea, beat));
        brevs.add(new Brev(iuncta[iunctum_index], ShortMessage.NOTE_ON, new MidiByte(note), MidiByte.MIN,           talea, beat + duration * rate));
        if(shift){
            beat += duration;
        }
    }
    public void note(int iunctum_index, NoteInfo info){
        brevs.add(new Brev(iuncta[iunctum_index], ShortMessage.NOTE_ON, info.getNote(), info.getVelocity(), talea, beat + info.getOffsetOn()));
        brevs.add(new Brev(iuncta[iunctum_index], ShortMessage.NOTE_ON, info.getNote(), MidiByte.MIN,       talea, beat + info.getOffsetOff()));
    }
    public void note(int iunctum_index, Integers notes, int velocity, double duration, double rate, ChordStroke stroke, Expression exp, boolean shift){
        note(Integers.fillValue(iunctum_index, notes.size()), notes, velocity, duration, rate, stroke, exp, shift);
    }
    public void note(Integers indeces, Integers notes, int velocity, double duration, double rate, ChordStroke stroke, Expression exp, boolean shift){
        if(notes.size() > indeces.size()){
            throw new OmException("more iunctum necessary");
        }
        double offset_on = 0;
        HashMap<Integer, Double> minimum_offsets = new HashMap<>();
        ArrayList<NoteInfo> note_infos = stroke.getNotes(notes, velocity, duration * rate);
        for(int i = 0;i < note_infos.size();i++){
            NoteInfo info = note_infos.get(i);
            int iunctum_index = indeces.get(i);
            note(iunctum_index, info);
            if(minimum_offsets.containsKey(iunctum_index)){
                minimum_offsets.put(iunctum_index, Math.min(minimum_offsets.get(iunctum_index), info.getOffsetOn()));
            }else{
                minimum_offsets.put(iunctum_index, info.getOffsetOn());
            }
        }
        for(int iunctum_index:minimum_offsets.keySet()){
            expression(iunctum_index, exp, offset_on, duration);
        }
        if(shift){
            beat += duration;
        }
        
    }
    private static final int pb_interval = 64;
    public void expression(int iunctum_index, Expression exp, double pickup_offset_on, double duration){
        for(SettingHolder setting:exp){
            if(setting.getTempAd() > duration){
                continue;
            }
            if(setting instanceof Control){
                Control control = (Control)setting;
                for(int i = 0;i < control.size();i++){
                    double offset_on = setting.initial?pickup_offset_on:control.getOffset(i);
                    control(iunctum_index, control.getControl(), control.getValue(i), offset_on);
                }
            }else if(setting instanceof Command){
                Command command = (Command)setting;
                for(int i = 0;i < command.size();i++){
                    int value = command.getValue(i);
                    if(i != 0 && i != command.size() - 1 && value % pb_interval != 0){
                        continue;
                    }
                    double offset_on = setting.initial?pickup_offset_on:command.getOffset(i);
                    command(iunctum_index, command.getCommand(), value / 0x80, value % 0x80, offset_on);
                }
            }
        }
    }
    public void control(int control, int value, double offset){
        for(int i = 0;i < iuncta.length;i++){
            control(i, control, value, offset);
        }
    }
    public void control(int iunctum_index, int control, int value, double offset){
        brevs.add(new Brev(iuncta[iunctum_index], ShortMessage.CONTROL_CHANGE, new MidiByte(control), new MidiByte(value), talea, beat + offset));
    }
    public void pitch(int iunctum_index, int value, double offset){
        value = Math.min(8191, Math.max(-8192, value)) + 8192;
        int msb = value / 0x80;
        int lsb = value % 0x80;
        if(msb >= 128 || lsb >= 128){
            System.out.println(value + " " + msb + " " + lsb);
        }
        command(iunctum_index, ShortMessage.PITCH_BEND, msb, lsb, offset);
    }
    public void command(int iunctum_index, int command, int msb, int lsb, double offset){
        brevs.add(new Brev(iuncta[iunctum_index], command, new MidiByte(lsb), new MidiByte(msb), talea, beat + offset));
    }
    public void program(int iunctum_index, int bank_m, int bank_l, int program){
        //long tick = Math.round(beat * RESOLUTION);
        brevs.add(new Brev(iuncta[iunctum_index], ShortMessage.CONTROL_CHANGE, new MidiByte(0x00),    new MidiByte(bank_m), talea, beat));
        brevs.add(new Brev(iuncta[iunctum_index], ShortMessage.CONTROL_CHANGE, new MidiByte(0x20),    new MidiByte(bank_l), talea, beat));
        brevs.add(new Brev(iuncta[iunctum_index], ShortMessage.PROGRAM_CHANGE, new MidiByte(program), new MidiByte(0),      talea, beat));
    }
    public void program(int program){
        for(int i = 0;i < iuncta.length;i++){
            program(i, program);
        }
    }
    public void program(int iunctum_index, int program){
        brevs.add(new Brev(iuncta[iunctum_index], ShortMessage.PROGRAM_CHANGE, new MidiByte(program), new MidiByte(0), talea, beat));
    }
    public Brevs shift(Shift shift){
        Brevs ret = new Brevs();
        for(Brev brev:brevs){
            int s_talea = brev.getTalea() + shift.talea;
            double s_beat = brev.getBeat() + shift.beat;
            int s_command = brev.getCommand();
            //int s_channel = brev.getChannel();
            MidiByte data1 = brev.getData1();
            MidiByte data2 = brev.getData2();
            if((s_command == ShortMessage.NOTE_ON || s_command == ShortMessage.NOTE_OFF)){
                if(shift.notes.containsKey(data1)){
                    data1 = shift.notes.get(data1);
                }else if(shift.mutes.contains(data1)){
                    continue;
                }
                //System.out.println("debug:shift:" + brev.getIunctum().getChannel() + ":" + data1.intValue());
            }
            ret.add(new Brev(brev.getIunctum(), s_command, data1, data2, s_talea, s_beat));
        }
        return ret;
    }
    public Brevs remove(){
        Brevs ret = brevs;
        //System.out.println("debug:remove:" + brevs.size());
        brevs = new Brevs();
        return ret;
    }
    public static class Shift {
        int talea;
        double beat;
        HashMap<MidiByte, MidiByte> notes;
        ArrayList<MidiByte> mutes;
        public Shift(){
            talea = 0;
            beat = 0;
            notes = new HashMap<>();
            mutes = new ArrayList<>();
        }
        public void loco(int talea, double beat){
            this.talea = talea;
            this.beat = beat;
        }
        public void putNote(int key, int value){
            MidiByte m_key = new MidiByte(key);
            if(value < 0){
                mutes.add(m_key);
                notes.remove(m_key);
            }else{
                notes.put(m_key, new MidiByte(value));
            }
        }
        
    }
}
