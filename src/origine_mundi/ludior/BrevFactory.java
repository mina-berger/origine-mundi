/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package origine_mundi.ludior;

import java.util.HashMap;
import javax.sound.midi.ShortMessage;
import origine_mundi.Integers;
import origine_mundi.MidiByte;
import origine_mundi.ludior.Expression.Command;
import origine_mundi.ludior.Expression.Control;
import origine_mundi.ludior.Expression.SettingHolder;

/**
 *
 * @author Mina
 */
public class BrevFactory {
    private Brevs brevs;
    private int track;
    private int device;
    private int channel;
    private int talea;
    private double beat;
    public BrevFactory(int track, int device, int channel, int talea, double beat){
        brevs = new Brevs();
        this.track = track;
        this.device = device;
        this.channel = channel;
        this.talea = talea;
        this.beat = beat;
    }
    public void setTrack(int track){
        this.track = track;
    }
    public void setDevice(int device){
        this.device = device;
    }
    public void setChannel(int channel){
        this.channel = channel;
    }
    public void setBeat(double beat){
        this.beat = beat;
    }
    public void setLoco(int talea, double beat){
        this.talea = talea;
        this.beat = beat;
    }
    public void note(int note, int velocity, double duration, double rate, boolean shift){
        brevs.add(new Brev(track, device, ShortMessage.NOTE_ON, channel, new MidiByte(note), new MidiByte(velocity), talea, beat));
        brevs.add(new Brev(track, device, ShortMessage.NOTE_ON, channel, new MidiByte(note), MidiByte.MIN,           talea, beat + duration * rate));
        if(shift){
            beat += duration;
        }
    }
    public void note(NoteInfo info){
        brevs.add(new Brev(track, device, ShortMessage.NOTE_ON, channel, info.getNote(), info.getVelocity(), talea, beat + info.getOffsetOn()));
        brevs.add(new Brev(track, device, ShortMessage.NOTE_ON, channel, info.getNote(), MidiByte.MIN,       talea, beat + info.getOffsetOff()));
    }
    public void note(Integers notes, int velocity, double duration, double rate, ChordStroke stroke, Expression exp, boolean shift){
        double offset_on = 0;
        for(NoteInfo info:stroke.getNotes(notes, velocity, duration * rate)){
            note(info);
            offset_on = Math.min(offset_on, info.getOffsetOn());
        }
        expression(exp, offset_on, duration);
        if(shift){
            beat += duration;
        }
        
    }
    public void expression(Expression exp, double pickup_offset_on, double duration){
        for(SettingHolder setting:exp){
            if(setting.getTempAd() > duration){
                continue;
            }
            if(setting instanceof Control){
                Control control = (Control)setting;
                for(int i = 0;i < control.size();i++){
                    double offset_on = setting.initial?pickup_offset_on:control.getOffset(i);
                    control(control.getControl(), control.getValue(i), offset_on);
                }
            }else if(setting instanceof Command){
                Command command = (Command)setting;
                for(int i = 0;i < command.size();i++){
                    int value = command.getValue(i);
                    double offset_on = setting.initial?pickup_offset_on:command.getOffset(i);
                    command(command.getCommand(), value / 0x80, value % 0x80, offset_on);
                }
            }
        }
    }
    public void control(int control, int value, double offset){
        brevs.add(new Brev(track, device, ShortMessage.CONTROL_CHANGE, channel, new MidiByte(control), new MidiByte(value), talea, beat + offset));
    }
    public void pitch(int value, double offset){
        value = Math.min(8191, Math.max(-8192, value)) + 8192;
        int msb = value / 0x80;
        int lsb = value % 0x80;
        if(msb >= 128 || lsb >= 128){
            System.out.println(value + " " + msb + " " + lsb);
        }
        command(ShortMessage.PITCH_BEND, msb, lsb, offset);
    }
    public void command(int command, int msb, int lsb, double offset){
        brevs.add(new Brev(track, device, command, channel, new MidiByte(lsb), new MidiByte(msb), talea, beat + offset));
    }
    public void program(int bank_m, int bank_l, int program){
        //long tick = Math.round(beat * RESOLUTION);
        brevs.add(new Brev(track, device, ShortMessage.CONTROL_CHANGE, channel, new MidiByte(0x00),    new MidiByte(bank_m), talea, beat));
        brevs.add(new Brev(track, device, ShortMessage.CONTROL_CHANGE, channel, new MidiByte(0x20),    new MidiByte(bank_l), talea, beat));
        brevs.add(new Brev(track, device, ShortMessage.PROGRAM_CHANGE, channel, new MidiByte(program), new MidiByte(0),      talea, beat));
    }
    public void program(int program){
        Brev brev = 
                new Brev(track, device, ShortMessage.PROGRAM_CHANGE, channel, 
                        new MidiByte(program), 
                        new MidiByte(0), talea, beat);
        brevs.add(new Brev(track, device, ShortMessage.PROGRAM_CHANGE, channel, new MidiByte(program), new MidiByte(0), talea, beat));
    }
    public Brevs shift(Shift shift){
        Brevs ret = new Brevs();
        for(Brev brev:brevs){
            int s_talea = brev.getTalea() + shift.talea;
            double s_beat = brev.getBeat() + shift.beat;
            int s_command = brev.getCommand();
            int s_channel = brev.getChannel();
            MidiByte data1 = brev.getData1();
            MidiByte data2 = brev.getData2();
            if((s_command == ShortMessage.NOTE_ON || s_command == ShortMessage.NOTE_OFF) && shift.notes.containsKey(data1)){
                data1 = shift.notes.get(data1);
            }
            ret.add(new Brev(brev.getTrack(), brev.getDevice(), s_command, s_channel, data1, data2, s_talea, s_beat));
        }
        return ret;
    }
    public Brevs remove(){
        Brevs ret = brevs;
        brevs = new Brevs();
        return ret;
    }
    public static class Shift {
        int talea;
        double beat;
        HashMap<MidiByte, MidiByte> notes;
        public Shift(){
            talea = 0;
            beat = 0;
            notes = new HashMap<>();
        }
        public void loco(int talea, double beat){
            this.talea = talea;
            this.beat = beat;
        }
        public void putNote(int key, int value){
            notes.put(new MidiByte(key), new MidiByte(value));
        }
        
    }
}
