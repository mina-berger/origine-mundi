/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package origine_mundi;

import com.mina.util.Integers;
import java.util.ArrayList;
import java.util.List;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.SysexMessage;
import javax.sound.midi.Transmitter;
import static origine_mundi.OmUtil.MICRO_LITE_5;
import static origine_mundi.OmUtil.fill;
import static origine_mundi.OmUtil.getMidiDevice;
import static origine_mundi.OmUtil.noteoff;
import static origine_mundi.OmUtil.printMidiDeviceInfo;
import static origine_mundi.OmUtil.fill;
import static origine_mundi.OmUtil.fill;
import static origine_mundi.OmUtil.fill;

/**
 *
 * @author Mina
 */
public class MidiMachine {
    MidiDevice in_device;
    MidiDevice ex_device;
    Receiver ex;
    Transmitter in;
    int header_size;
    boolean checksum_req;
    boolean checksum_dump;
    public MidiMachine(String ex_device_name, String in_device_name, int header_size, boolean checksum_req, boolean checksum_dump){
        try {
            ex_device = getMidiDevice(ex_device_name, true);
            ex = ex_device.getReceiver();
            in_device = getMidiDevice(in_device_name, false);
            in = in_device.getTransmitter();
            this.header_size = header_size;
            this.checksum_req = checksum_req;
            this.checksum_dump = checksum_dump;
            //System.out.println(ex);
            //System.out.println(in);
        } catch (MidiUnavailableException ex) {
            throw new OmException("failed to get sysex", ex);
        }
        
    }
    @Override
    public void finalize(){
        ex_device.close();
        in_device.close();
    }
    public MidiDevice getInDevice(){return in_device;}
    public MidiDevice getExDevice(){return ex_device;}
    public boolean getChecksumRequest(){
        return checksum_req;
    }
    public boolean getChecksumDump(){
        return checksum_dump;
    }
    public SysexBuilder get(SysexBuilder sysex_builder, SysexDataModel data_model) {
        return get(sysex_builder, data_model, 1);
    }
    public SysexBuilder get(SysexBuilder sysex_builder, SysexDataModel data_model, int count) {
        
        
        Integers data = getData(sysex_builder.getSysex(), count);
        if(data.get(0) != 0xf0 || data.get(data.size() - 1) != 0xf7){
            throw new OmException("illegal sysex response:" + debugSysex(data));
        }
        int heder_footer_length =  (checksum_dump?1:0) + header_size + 2;
        //System.out.println(data_model.length());
        if(data_model.length() != data.size() - heder_footer_length){
            System.out.println(debugSysex(data));
            System.out.println(data.size());
            throw new OmException("illegal data length (" + (data.size() - heder_footer_length) + " expected " + data_model.length() + ")"); 
        }
        return toBuilder(data.subList(1, data.size() - 1), data_model);
        //return new SysexBuilder(new int[]{data.get(1), data.get(2), data.get(3), data.get(4)}, data_model, data.subList(4, data.size() - 2), checksum_dump);
    }
    protected SysexBuilder toBuilder(List<Integer> data, SysexDataModel data_model){
        Integers sublist = new Integers(data.subList(header_size, data.size() - (checksum_dump?1:0)));
        System.out.println(debugSysex(sublist));
        Integers header = new Integers(data.subList(0, header_size));
        
        return new SysexBuilder(header, data_model, sublist, checksum_dump);
    }
    private SysexReceiver sr = null;
    public Integers getData() {
        SysexMessage sysex_ret = sr.removeHead();
        return toIntegers(sysex_ret.getData());
    }
    public Integers getData(SysexMessage sysex, int count) {
        try {
            sr = new SysexReceiver();
            
            in.setReceiver(sr);
            //Thread.sleep(200);
            ex.send(sysex, -1);
            Thread.sleep(1000);
            if(sr.getList().isEmpty()){
                return null;
            }
            Integers ret = new Integers();
            int read_count = 0;
            while(true){
                Integers data = getData();
                ret.addAll(data.subList(ret.isEmpty()?0:1, data.size()));
                if(data.size() == 0x400 && data.get(data.size() - 1) !=0xf7){
                    continue;
                }
                read_count++;
                if(read_count >= count){
                    break;
                }
            }
            return ret;
            //SysexMessage sysex_ret = sr.removeHead();
            //return toIntList(sysex_ret.getData());
            /*byte[] data = sysex_ret.getData();
            ArrayList<Integer> ret = new ArrayList<>();
            ret.add(0xf0);
            for(int i = 0;i < data.length;i++){
                ret.add(data[i] < 0?data[i] + 0x100:data[i]);
            }
            return ret;*/
        }catch(InterruptedException ex){
                throw new OmException("failed to get sysex", ex);
        }
    }
    private Integers toIntegers(byte[] data){
        Integers ret = new Integers(0xf0);
        for(int i = 0;i < data.length;i++){
            ret.append(data[i] < 0?data[i] + 0x100:data[i]);
        }
        return ret;
    }
    public void listen() {
        try {
            SysexReceiver sr = new SysexReceiver();
            
            in.setReceiver(sr);
            while(true){
                if(sr.isEmpty()){
                    Thread.sleep(500);
                    continue;
                }
                SysexMessage sysex_ret = sr.removeHead();
                byte[] data = sysex_ret.getData();
                Integers ret = new Integers();
                ret.add(0xf0);
                for(int i = 0;i < data.length;i++){
                    ret.add(data[i] < 0?data[i] + 0x100:data[i]);
                }
                System.out.println(debugSysex(ret));
            }
        }catch(InterruptedException ex){
                throw new OmException("failed to get sysex", ex);
        }
    }
    
    public void send(SysexMessage sysex) {
        try {
            ex.send(sysex, -1);
            Thread.sleep(200);
        }catch(InterruptedException ex){
                throw new OmException("failed to send sysex", ex);
        }
    }
    public static String debugSysex(Integers list){
        String str = "";
        for(Integer value:list){
            String hex = fill(Integer.toHexString(value), 2);
            if(!str.isEmpty()){
                str += ", ";
            }
            str += "0x" + hex;
        }
        return "new int[]{" + str + "};";
        
    }
    public class SysexReceiver implements Receiver {

        ArrayList<SysexMessage> list;

        SysexReceiver() {
            list = new ArrayList<>();
        }
        public ArrayList<SysexMessage> getList(){
            return list;
        }
        public boolean isEmpty(){
            return list.isEmpty();
        }
        public synchronized SysexMessage removeHead(){
            return list.remove(0);
        }

        @Override
        public void send(MidiMessage message, long timeStamp) {
            if (message instanceof SysexMessage) {
                list.add((SysexMessage) message);
            }
        }

        @Override
        public void close() {
        }
    }
    /*public static void main(String[] arg){
        String sysexmessage = "f0 41 10 16 12 04 00 00 45 6c 65 63 50 69 61 6e 6f 32 f7";
        try {
            MidiDevice out_dev = getMidiDevice(D_110, true);
            Receiver out = out_dev.getReceiver();
            SysexMessage sysex = OmUtil.sysex(sysexmessage);
            out.send(sysex, -1);
            Thread.sleep(1000);
            out_dev.close();
        }catch(OmException oex){
            throw oex;
        } catch (Exception ex) {
            throw new OmException("failed to get sysex", ex);
        }
    }*/
    public static class Address extends OmUtil.ByteHolder {
        public Address(int ad0, int ad1, int ad2){
            addAll(ad0, ad1, ad2);
        }
        public Address sum(Address address){
            int sum = (get(0) + address.get(0)) * 0x4000 + 
                       (get(1) + address.get(1)) * 0x80   + 
                       (get(2) + address.get(2));
            return new Address(sum / 0x4000, (sum % 0x4000) / 0x80, sum % 0x80);
        }
        public int getIndex(){
            return get(0) * 0x4000 + get(1) * 0x80 + get(2);
        }
    }
    public void checkSound(int channel){
        //printEnv(System.out);
        printMidiDeviceInfo(ex_device.getDeviceInfo(), System.out, 1);
        //System.out.println(receiver1);
        try {
        noteoff(ex);
            for(int i = 0;i < 1;i++){
                int[] notes = new int[1];
                for(int j = 0;j < notes.length;j++){
                    notes[j] = 60;//(j == 0?48:notes[j - 1]) + (int)Math.floor(Math.random() * 5) + 3;
                    ex.send(new ShortMessage(ShortMessage.NOTE_ON, channel, notes[j], 120), 1);
                    Thread.sleep(50);
                }
                Thread.sleep(2000);
                for(int j = 0;j < notes.length;j++){
                    ex.send(new ShortMessage(ShortMessage.NOTE_ON, channel, notes[j], 0), 1);
                }
            }
            Thread.sleep(100);
            for(int i = 0;i < 1;i++){
                int[] notes = new int[]{36, 60, 64, 67};
                for(int j = 0;j < notes.length;j++){
                    ex.send(new ShortMessage(ShortMessage.NOTE_ON, channel, notes[j], 120), 1);
                    Thread.sleep(50);
                }
                Thread.sleep(2000);
                for(int j = 0;j < notes.length;j++){
                    ex.send(new ShortMessage(ShortMessage.NOTE_ON, channel, notes[j], 0), 1);
                }
            }
            Thread.sleep(100);
            noteoff(ex);
        } catch (Exception ex) {
            throw new OmException("cannot play midimachine", ex);
        }
    }
    public static void main(String[] args) throws Exception{
        MidiMachine mm = new MidiMachine(MICRO_LITE_5, MICRO_LITE_5, 0, false, false);
        mm.listen();
    }
}
