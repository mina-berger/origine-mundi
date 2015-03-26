/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package origine_mundi.machine;

import java.util.ArrayList;
import origine_mundi.OmException;
import origine_mundi.OmUtil;
import static origine_mundi.OmUtil.MICRO_LITE_1;
import origine_mundi.SysexDataModel;
import origine_mundi.SysexDataModel.Blank;
import origine_mundi.SysexDataModel.ByteValue;
import origine_mundi.SysexDataModel.ByteValues;
import origine_mundi.SysexDataModel.Characters;
import origine_mundi.SysexDataModel.CodeValue;
import origine_mundi.SysexDataModel.DataBlock;
import origine_mundi.SysexDataModel.DataLength;
import origine_mundi.SysexDataModel.KV;
import origine_mundi.SysexDataModel.MidiChannel;
import origine_mundi.SysexDataModel.OffsetBinary;
import origine_mundi.SysexDataModel.OnOffValue;
import origine_mundi.SysexDataModel.Reserve;

/**
 *
 * @author Mina
 */
public class TG77 extends Yamaha {
    private static final int DEVICE_ID_LOWER = 0x0;
    private static final int MODEL_ID = 0x7A;

    private static final String MIDI_PORT = MICRO_LITE_1;
    private static TG77 instance = null;
    public static TG77 instance(){
        if(instance == null){
            instance = new TG77();
        }
        return instance;
    }
    public TG77() {
        super(DEVICE_ID_LOWER, MODEL_ID, MIDI_PORT, MIDI_PORT);
    }
    public static void main(String[] args){


        //LM_8104PC.getExplanations(OmUtil.toList(LM_8104PC_VALUES)).print();
        
        TG77 tg77 = TG77.instance();
        //int[] data_req = new int[]{0x4c, 0x4d, 0x20, 0x20, 0x38, 0x31, 0x30, 0x31, 0x53, 0x59, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
        //tg77.get(tg77.getRequest("LM  8101SY"), LM_8101SY).getExplanations().print();
        tg77.getVoice(0, 0x00);
        //tg77.listen();
        tg77.finalize();
    }
    public SysexBuilder getVoice(int memory_type, int memory_number){
        //tg77.get(
        ArrayList<Integer> data = getData(getRequest("LM  8101VC", memory_type, memory_number).getSysex());
        System.out.println(data.size());
        //List<Integer> head_data = data.subList(4, 43);
        SysexBuilder head_builder = toBuilder(data.subList(1, 43), LM_8101VC_CHECK);
        head_builder.getExplanations().print();
        System.out.println(head_builder.getValueText("header.elmode"));
        int elmode = head_builder.getInt("header.elmode");
        switch(elmode){
            case 0:return  toBuilder(data.subList(1, data.size() - 2), LM_8101VC_0);
            case 9:return  toBuilder(data.subList(1, data.size() - 2), LM_8101VC_9);
            default: throw new OmException("cannot find elmode for 0x" + OmUtil.hex(elmode));
        }
    }
    
    public SysexBuilder getRequest(String str){
        return getRequest(str, 0, 0);
    }
    public SysexBuilder getRequest(String str, int memory_type, int memory_number){
        int[] data_req = new int[26];
        for(int i = 0;i < 24;i++){
            data_req[i] = i < 10?str.charAt(i):0x00;
        }
        data_req[24] = memory_type;
        data_req[25] = memory_number;
        return getRequest(data_req);
    }
    public static int[] LM_8101SY_VALUES = new int[]{0x00, 0x5a, 0x4c, 0x4d, 0x20, 0x20, 0x38, 0x31, 0x30, 0x31, 0x53, 0x59, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x40, 0x40, 0x00, 0x00, 0x0d, 0x41, 0x01, 0x00, 0x10, 0x01, 0x11, 0x00, 0x01, 0x01, 0x00, 0x00, 0x00, 0x00, 0x7f, 0x20, 0x00, 0x00, 0x0d, 0x0b};
    public static SysexDataModel LM_8101SY = new SysexDataModel("system setup",
            new DataLength("data length", 2),
            new Characters("dump code", 10),
            new Blank("blank1", 16),
            new Characters("greeting0", 20),
            new Characters("greeting1", 20),
            new OffsetBinary("master note shift"),
            new OffsetBinary("master fine tune"),
            new CodeValue("fixed velocity", new KV(0, "off")),
            new ByteValue("velocity curve", 0, 7),
            new ByteValue("modulation wheel2", 0, 120),
            new ByteValue("footswitch assign", 0, 120),
            new OnOffValue("edit confirm switch", 0, 0, 1),
            new MidiChannel("keyboard transmit"),
            new CodeValue("keyboard receive", 0, 0x10, new KV(0x10, "omni")),
            new OnOffValue("local switch", 0, 0, 1),
            new CodeValue("device number", 0, 0x11, 
                    new KV(0x00, "off"), new KV(0x01, "#0" ), new KV(0x02, "#1" ), new KV(0x03, "#2" ), new KV(0x04, "#3" ), new KV(0x05, "#4"), 
                    new KV(0x06, "#5" ), new KV(0x07, "#6" ), new KV(0x08, "#7" ), new KV(0x09, "#8" ), new KV(0x0a, "#9" ), new KV(0x0b, "#10"), 
                    new KV(0x0c, "#11"), new KV(0x0d, "#12"), new KV(0x0e, "#13"), new KV(0x0f, "#15"), new KV(0x10, "#15"), new KV(0x11, "all")),
            new CodeValue("note even odd switch", 0, 2, new KV(0, "all"), new KV(1, "odd"), new KV(2, "even")),
            new OnOffValue("bulk data memory protect", 0, 0, 1),
            new ByteValue("program change mode", 0, 2),
            new Reserve("reserve", new int[]{0x00, 0x00, 0x00, 0x00, 0x7f, 0x20, 0x00, 0x00, 0x0d, 0x0b})
    );
    public static int[] LM_8104PC_VALUES = new int[]{0x02, 0x1a, 0x4c, 0x4d, 0x20, 0x20, 0x38, 0x31, 0x30, 0x34, 0x50, 0x43, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x02, 0x00, 0x02, 0x01, 0x02, 0x02, 0x02, 0x03, 0x02, 0x04, 0x02, 0x05, 0x02, 0x06, 0x02, 0x07, 0x02, 0x08, 0x02, 0x09, 0x02, 0x0a, 0x02, 0x0b, 0x02, 0x0c, 0x02, 0x0d, 0x02, 0x0e, 0x02, 0x0f, 0x02, 0x10, 0x02, 0x11, 0x02, 0x12, 0x02, 0x13, 0x02, 0x14, 0x02, 0x15, 0x02, 0x16, 0x02, 0x17, 0x02, 0x18, 0x02, 0x19, 0x02, 0x1a, 0x02, 0x1b, 0x02, 0x1c, 0x02, 0x1d, 0x02, 0x1e, 0x02, 0x1f, 0x02, 0x20, 0x02, 0x21, 0x02, 0x22, 0x02, 0x23, 0x02, 0x24, 0x02, 0x25, 0x02, 0x26, 0x02, 0x27, 0x02, 0x28, 0x02, 0x29, 0x02, 0x2a, 0x02, 0x2b, 0x02, 0x2c, 0x02, 0x2d, 0x02, 0x2e, 0x02, 0x2f, 0x02, 0x30, 0x02, 0x31, 0x02, 0x32, 0x02, 0x33, 0x02, 0x34, 0x02, 0x35, 0x02, 0x36, 0x02, 0x37, 0x02, 0x38, 0x02, 0x39, 0x02, 0x3a, 0x02, 0x3b, 0x02, 0x3c, 0x02, 0x3d, 0x02, 0x3e, 0x02, 0x3f, 0x03, 0x00, 0x03, 0x01, 0x03, 0x02, 0x03, 0x03, 0x03, 0x04, 0x03, 0x05, 0x03, 0x06, 0x03, 0x07, 0x03, 0x08, 0x03, 0x09, 0x03, 0x0a, 0x03, 0x0b, 0x03, 0x0c, 0x03, 0x0d, 0x03, 0x0e, 0x03, 0x0f, 0x03, 0x10, 0x03, 0x11, 0x03, 0x12, 0x03, 0x13, 0x03, 0x14, 0x03, 0x15, 0x03, 0x16, 0x03, 0x17, 0x03, 0x18, 0x03, 0x19, 0x03, 0x1a, 0x03, 0x1b, 0x03, 0x1c, 0x03, 0x1d, 0x03, 0x1e, 0x03, 0x1f, 0x03, 0x20, 0x03, 0x21, 0x03, 0x22, 0x03, 0x23, 0x03, 0x24, 0x03, 0x25, 0x03, 0x26, 0x03, 0x27, 0x03, 0x28, 0x03, 0x29, 0x03, 0x2a, 0x03, 0x2b, 0x03, 0x2c, 0x03, 0x2d, 0x03, 0x2e, 0x03, 0x2f, 0x03, 0x30, 0x03, 0x31, 0x03, 0x32, 0x03, 0x33, 0x03, 0x34, 0x03, 0x35, 0x03, 0x36, 0x03, 0x37, 0x03, 0x38, 0x03, 0x39, 0x03, 0x3a, 0x03, 0x3b, 0x03, 0x3c, 0x03, 0x3d, 0x03, 0x3e, 0x03, 0x3f};

    public static SysexDataModel LM_8104PC = new SysexDataModel("program change table",
            new DataLength("data length", 2),
            new Characters("dump code", 10),
            new Blank(16),

            new ByteValues("rest", 256)
    );
    public static final DataBlock LM_8101VC_HEAD_BLOCK = new DataBlock("header",
            new DataLength("data length", 2),
            new Characters("dump code", 10),
            new Blank(14),
            new CodeValue("memory type", new KV(0x00, "internal"), new KV(0x02, "preset"), new KV(0x7f, "edit buffer")),
            new ByteValue("memory number"),
            new CodeValue("elmode", 
                    new KV(0x0, "1AFM_mono"), new KV(0x1, "2AFM_mono"), new KV(0x2, "4AFM_mono"), new KV(0x3, "1AFM_poly"), new KV(0x4, "2AFM_poly"), new KV(0x5, "1AWM_poly"),
                    new KV(0x6, "2AWM_poly"), new KV(0x7, "4AWM_poly"), new KV(0x8, "1AFM_1AWM_poly"), new KV(0x9, "2FM_2PCM_poly"),new KV(0xa, "DRUM_SET")),
            new Characters("voice name", 10));    
    public static SysexDataModel LM_8101VC_CHECK = new SysexDataModel("voice_check", LM_8101VC_HEAD_BLOCK);
    public static SysexDataModel LM_8101VC_0 = new SysexDataModel("10m", LM_8101VC_HEAD_BLOCK,
            new ByteValues("rest", 421)
    );
    public static SysexDataModel LM_8101VC_9 = new SysexDataModel("22p", LM_8101VC_HEAD_BLOCK,
            new ByteValues("rest", 1029)
    );
    //public static SysexDataModel LM_8101VC_HEAD = new SysexDataModel("voice_check", LM_8101VC_HEAD_BLOCK);
    //public static SysexDataModel LM_8101VC_HEAD = new SysexDataModel("voice",
            
    //);
}
