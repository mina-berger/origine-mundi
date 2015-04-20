/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package origine_mundi;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import static origine_mundi.OmUtil.MICRO_LITE_5;
import origine_mundi.OmUtil.Note;
import static origine_mundi.OmUtil.getMidiDevice;

/**
 *
 * @author Mina
 */
public class MidiInTester {
    public static class MidiReceiver implements Receiver {
        Receiver receiver;
        MidiReceiver(Receiver receiver) {
            this.receiver = receiver;
        }
        @Override
        public void send(MidiMessage message, long timeStamp) {
            if(message.getMessage()[0] == -2){
                return;
            }
            if(message instanceof ShortMessage){
                ShortMessage sm = (ShortMessage)message;
                String command = null;
                switch(sm.getCommand()){
                    case 0x90:
                        if(sm.getData2() == 0){
                        }else{
                            command = "note[" + sm.getChannel() + "] " + sm.getData1() + ":" + new Note(sm.getData1()) + " " + OmUtil.hex(sm.getData2());
                        }
                        break;
                    case 0xc0:
                        command = "pc[" + sm.getChannel() + "] " + sm.getData1() + ":" + sm.getData2();
                        break;
                    default :command = OmUtil.hex(sm.getCommand());
                }
                if(command != null){
                    System.out.println(command);
                }
                receiver.send(message, timeStamp);
            }else{
                String str = "";
                for(byte b:message.getMessage()){
                    int i = b < 0?((int)b) + 256:((int)b);
                    if(!str.isEmpty()){
                        str += " ";
                    }
                    str += OmUtil.fill(Integer.toHexString(i), 2);
                    //str += b;
                }
                System.out.println(str);
            }
        }

        @Override
        public void close() {
        }
    }
    public static void _main(String[] a) {
        OmUtil.printEnv(System.out);
    }
    public static void main(String[] a) {
        OmUtil.printEnv(System.out);
        MidiDevice ex_dev = null;
        MidiDevice in_dev = null;
        try {
            ex_dev = getMidiDevice("3- micro lite: Port 2", true);
            //ex_dev = getMidiDevice(MICRO_LITE_2, true);
            //ex_dev = getMidiDevice("Port 4 on MXPXT", true);
            //in_dev = getMidiDevice(MICRO_LITE_5, false);
            in_dev = getMidiDevice(MICRO_LITE_5, false);
            MidiReceiver sr = new MidiReceiver(ex_dev.getReceiver());
            in_dev.getTransmitter().setReceiver(sr);
            while(true){
                Thread.sleep(100000);
                
            }
            /*byte[] data = sysex_ret.getData();
            ArrayList<Integer> ret = new ArrayList<>();
            ret.add(0xf0);
            for(int i = 0;i < data.length;i++){
                ret.add(data[i] < 0?data[i] + 0x100:data[i]);
            }
            return ret;*/
        } catch (Exception ex) {
            ex_dev.close();
            in_dev.close();
        }
    }
    
    
}
