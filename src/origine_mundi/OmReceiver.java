/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package origine_mundi;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.SysexMessage;

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
    private static int toInt(byte b){
        int i = (int)b;
        //System.out.println(i);
        if(i < 0){
            i += 0x100;
            //System.out.println(i);
        }
        return i;
    }

    @Override
    public void send(MidiMessage message, long timeStamp) {
        //if(1 == 1)throw new RuntimeException("");
        if(message instanceof SysexMessage){
            SysexMessage sysex_message  = (SysexMessage)message;
            if(toInt(sysex_message.getData()[1]) != om_id){
                return;
            }
            try {
                ShortMessage short_message = new ShortMessage(
                        toInt(sysex_message.getData()[2]), 
                        toInt(sysex_message.getData()[3]), 
                        toInt(sysex_message.getData()[4]), 
                        toInt(sysex_message.getData()[5]));
                receiver.send(short_message, timeStamp);
                System.out.println("sent:" + short_message.getCommand() + ":" + short_message.getChannel() + ":" + short_message.getData1() + ":" + short_message.getData2());
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
    
}
