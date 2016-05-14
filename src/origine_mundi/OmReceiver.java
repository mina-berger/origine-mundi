/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package origine_mundi;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.SysexMessage;
import org.apache.commons.lang3.ArrayUtils;
import static origine_mundi.OmUtil.OM_MSG_TYPE_BREVIS;
import static origine_mundi.OmUtil.OM_MSG_TYPE_SYSTEM;
import static origine_mundi.OmUtil.OM_PRODUCT_ID;
import static origine_mundi.OmUtil.SYSEX_STATUS_AB;

/**
 *
 * @author Mina
 */
public class OmReceiver implements Receiver {
    private final Receiver receiver;
    private final int om_id;
    public OmReceiver(Receiver receiver, int om_id){
        this.receiver = receiver;
        this.om_id = om_id;
    }
    private static int[] toInt(byte[] bs){
        int[] ret = new int[bs.length];
        for(int i = 0; i < bs.length;i++){
            ret[i] = toInt(bs[i]);
        }
        return ret;
    }
    private static int toInt(byte b){
        int i = (int)b;
        if(i < 0){
            i += 0x100;
        }
        return i;
    }

    @Override
    public void send(MidiMessage message, long timeStamp) {
        //if(1 == 1)throw new RuntimeException("");
        if(message instanceof SysexMessage){
            SysexMessage sysex_message  = (SysexMessage)message;
            byte[] data = sysex_message.getMessage();
            if(toInt(data[1]) != OM_PRODUCT_ID || toInt(data[2]) != om_id){
                return;
            }
            int om_msg_type = toInt(data[3]);
            try {
                if(om_msg_type == OM_MSG_TYPE_BREVIS){
                        ShortMessage short_message = new ShortMessage(
                                toInt(data[4]), 
                                toInt(data[5]), 
                                toInt(data[6]), 
                                toInt(data[7]));
                        receiver.send(short_message, timeStamp);
                        //System.out.println("sent:" + short_message.getCommand() + ":" + short_message.getChannel() + ":" + short_message.getData1() + ":" + short_message.getData2());
                }else if(om_msg_type == OM_MSG_TYPE_SYSTEM){
                    receiver.send(toRegularSysexMessage(sysex_message), timeStamp);
                }
            } catch (Exception ex) {
                System.out.println("error:" + ex);
                return;
            }
        }else{
            receiver.send(message, timeStamp);
        }
    }

    @Override
    public void close() {
        receiver.close();
    }
    private SysexMessage toRegularSysexMessage(SysexMessage om_sysex_message) throws InvalidMidiDataException{
        byte[] data = om_sysex_message.getMessage();
        data = ArrayUtils.addAll(new byte[]{(byte)SYSEX_STATUS_AB}, ArrayUtils.subarray(data, 4, data.length));
        SysexMessage sysex = new SysexMessage();
        sysex.setMessage(data, data.length);
        return sysex;
    }
    public static void main(String[] a) throws InvalidMidiDataException{
        SysexMessage sysex = new SysexMessage();
        byte[] data = new byte[0x10];
        for(int i = 0;i < data.length;i++){
            data[i] = i == 0?(byte)SYSEX_STATUS_AB:(byte)(i % 0x10);
        }
        sysex.setMessage(data, data.length);
        System.out.println(OmUtil.sysex(sysex));
        data = ArrayUtils.addAll(new byte[]{(byte)SYSEX_STATUS_AB}, ArrayUtils.subarray(data, 3, data.length));
        sysex.setMessage(data, data.length);
        System.out.println(OmUtil.sysex(sysex));
    }
    
}
