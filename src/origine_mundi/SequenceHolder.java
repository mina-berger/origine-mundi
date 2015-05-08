/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package origine_mundi;

import java.util.HashMap;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.SysexMessage;
import javax.sound.midi.Track;
import static origine_mundi.OmUtil.OM_MSG_TYPE_BREVIS;
import static origine_mundi.OmUtil.OM_PRODUCT_ID;
import static origine_mundi.OmUtil.RESOLUTION;
import static origine_mundi.OmUtil.SYSEX_STATUS_AB;
import origine_mundi.ludior.Brev;
import origine_mundi.ludior.Iunctum;
import origine_mundi.ludior.Tempus;

/**
 *
 * @author user
 */
public class SequenceHolder {
    Sequence sequence;
    public SequenceHolder(){
        try {
            sequence = new Sequence(Sequence.PPQ, RESOLUTION, 1);
            tempo(60, 0);
        } catch (InvalidMidiDataException ex) {
            throw new OmException("cannot create sequence", ex);
        }
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
    public void tempo(double bpm, double beat){
        long quater = Math.round(60d * 1000000d / bpm);
        int[] data = new int[3];
        data[0] = new Long(quater / 256 / 256).intValue();
        data[1] = new Long((quater / 256) % 256).intValue();
        data[2] = new Long(quater % 256).intValue();
        meta(0x51, data, Math.round(beat * RESOLUTION));
    }
    private Track getTrack(int index){
        if(index < 0 || index > 100){
            throw new OmException("illegal index for track");
        }
        int track_index = index + 1;
        while(sequence.getTracks().length <= track_index){
            sequence.createTrack();
        }
        return sequence.getTracks()[track_index];
    }
    public void addBrev(HashMap<String, MidiEvent> double_map, Tempus tempus, Brev brev, MidiMachines midi_machines, boolean simple, long offset){
        Iunctum iunctum = brev.getIunctum();
        int i_track = iunctum.getTrack();
        int device = iunctum.getDevice();
        int command = brev.getCommand();
        int channel = iunctum.getChannel();
        int data1 = brev.getData1().intValue();
        Track track = getTrack(i_track);
        long point = (long)(tempus.capioTempus(brev.getTalea(), brev.getBeat())) - offset;
        //System.out.println(point);
        try {
            MidiEvent midi_event;
            if(simple){
                midi_event = new MidiEvent(new ShortMessage(command, channel, data1, brev.getData2().intValue()), point);
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
    
    public Sequence getSequence(){
        return sequence;
    }
    
}
