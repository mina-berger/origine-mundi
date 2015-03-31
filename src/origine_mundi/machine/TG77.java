/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package origine_mundi.machine;

import java.text.DecimalFormat;
import java.util.ArrayList;
import origine_mundi.OmException;
import origine_mundi.OmUtil;
import static origine_mundi.OmUtil.MICRO_LITE_1;
import origine_mundi.SysexDataModel;
import origine_mundi.SysexDataModel.BitArray;
import origine_mundi.SysexDataModel.Blank;
import origine_mundi.SysexDataModel.ByteValue;
import origine_mundi.SysexDataModel.ByteValues;
import origine_mundi.SysexDataModel.Characters;
import origine_mundi.SysexDataModel.CodeValue;
import origine_mundi.SysexDataModel.DataBlock;
import origine_mundi.SysexDataModel.DataLength;
import origine_mundi.SysexDataModel.KV;
import origine_mundi.SysexDataModel.MidiChannel;
import origine_mundi.SysexDataModel.MultiBitArray;
import origine_mundi.SysexDataModel.MultiBytesOffsetBinary;
import origine_mundi.SysexDataModel.MultiBytesValue;
import origine_mundi.SysexDataModel.NoteValue;
import origine_mundi.SysexDataModel.NoteValues;
import origine_mundi.SysexDataModel.OffsetBinaries;
import origine_mundi.SysexDataModel.OffsetBinary;
import origine_mundi.SysexDataModel.OnOffValue;
import origine_mundi.SysexDataModel.SignedValue;

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


        LM_8101MU.getExplanations(OmUtil.toList(LM_8101MU_VALUES)).print();
        //LM_8104PC.getExplanations(OmUtil.toList(LM_8104PC_VALUES)).print();
        
        /*TG77 tg77 = TG77.instance();
        //int[] data_req = new int[]{0x4c, 0x4d, 0x20, 0x20, 0x38, 0x31, 0x30, 0x31, 0x53, 0x59, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
        //tg77.get(tg77.getRequest("LM  8101SY"), LM_8101SY).getExplanations().print();
        tg77.get(tg77.getRequest("LM  8101MU"), LM_8101MU).getExplanations().print();
        //tg77.getVoice(0, 0x00);
        //tg77.listen();
        tg77.finalize();*/
    }
    public SysexBuilder getVoice(int memory_type, int memory_number){
        //tg77.get(
        ArrayList<Integer> data = getData(getRequest("LM  8101VC", memory_type, memory_number).getSysex(), 1);
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
    //public static final CodeValue VOICE_MEMORY = new CodeValue("memory type", new KV(0x00, "internal"), new KV(0x02, "preset"), new KV(0x7f, "edit buffer"));
    public static final DataBlock COMMON_HEAD0_BLOCK = new DataBlock("common_header0",
            new DataLength("data length", 2),
            new Characters("dump code", 10),
            new Blank(16));
    public static final DataBlock COMMON_HEAD1_BLOCK = new DataBlock("common_header1",
            new DataLength("data length", 2),
            new Characters("dump code", 10),
            new Blank(14),
            new CodeValue("memory type", new KV(0x00, "internal"), new KV(0x02, "preset"), new KV(0x7f, "edit buffer")),
            new ByteValue("memory number"));
    public static final DataBlock CHORUS_BLOCK = new DataBlock("chorus",
            new CodeValue("type", 0, 4, new KV(0x00, "Through"), new KV(0x01, "St.Chorus"), new KV(0x02, "St.Flange"), new KV(0x03, "Symphonic"), new KV(0x04, "Tremolo")),
            new ByteValue("barance", 0, 100),
            new ByteValue("output level", 0, 100),
            new ByteValue("parameter0"),
            new ByteValue("parameter1"),
            new ByteValue("parameter2"),
            new ByteValue("parameter3")
    );
    public static final DataBlock REVERBE_BLOCK = new DataBlock("reverb",
            new CodeValue("type", 0, 40,
                new KV( 0, "Through"),     new KV( 1, "REV Hall"),     new KV( 2, "REV Room"),     new KV( 3, "REV Plate"),  new KV( 4, "REV Church"), 
                new KV( 5, "REV Club"),    new KV( 6, "REV Stage"),    new KV( 7, "REV BathRoom"), new KV( 8, "REV Metal"),  new KV( 9, "SG DL"), 
                new KV(10, "DL LR"),       new KV(11, "ST Echo"),      new KV(12, "Doubler1"),     new KV(13, "Doubler2"),   new KV(14, "Ping Pong Echo"), 
                new KV(15, "Pan Refle."),  new KV(16, "ER"),           new KV(17, "Gate REV"),     new KV(18, "RVRS Gate"),  new KV(19, "FB ER"), 
                new KV(20, "FB Gate"),     new KV(21, "FB RVRS"),      new KV(22, "SG DL&REV"),    new KV(23, "DL LR&REV"),  new KV(24, "Tunnel REV"), 
                new KV(25, "ToneCTRL1"),   new KV(26, "SG DL+Tone1"),  new KV(27, "DL LR+Tone1"),  new KV(28, "ToneCTRL2"),  new KV(29, "SG DL+Tone2"), 
                new KV(30, "DL LR+Tone2"), new KV(31, "DIST+REV"),     new KV(32, "DIST+SG DL"),   new KV(33, "DIST+DL LR"), new KV(34, "DIST"), 
                new KV(35, "IND DL"),      new KV(36, "IND ToneCTRL"), new KV(37, "IND DIST"),     new KV(38, "IND REV"),    new KV(39, "IND DL&REV"), 
                new KV(40, "IND REV&DL")),
            new ByteValue("barance", 0, 100),
            new ByteValue("output level", 0, 100),
            new ByteValue("parameter0"),
            new ByteValue("parameter1"),
            new ByteValue("parameter2")
    );
    public static final DataBlock EFFECT_BLOCK = new DataBlock("effect",
            new CodeValue("mode", new KV(0x00, "off(none&none)"), new KV(0x01, "mode1(MR&MR)"), new KV(0x02, "mode2(MRR&M)"), new KV(0x3f, "mode3((MM)RR&none)")),
            CHORUS_BLOCK.copy("cho0"),
            CHORUS_BLOCK.copy("cho1"),
            REVERBE_BLOCK.copy("rev0"),
            REVERBE_BLOCK.copy("rev1"),
            new OnOffValue("st_mix0", 0, 0, 1),
            new OnOffValue("st_mix1", 0, 0, 1)
    );
    public static final DataBlock MULTI_CHANNEL_BLOCK = new DataBlock("multi channel model",
            new BitArray("output switch", "bit:0[0:off_voice]0000[2:output1][1:output0]", 6, 0, 1),
            new CodeValue("memory type", new KV(0x00, "internal"), new KV(0x01, "card"), new KV(0x02, "preset0"), new KV(0x03, "preset1")),
            new ByteValue("memory number"),
            new ByteValue("volume"),
            new OffsetBinary("tuning", 0, 127, 64),
            new OffsetBinary("note shift", 0, 127, 64),
            new OffsetBinary("static pan", 0, 63, 31)
        );

    public static int[] LM_8101SY_VALUES = new int[]{0x00, 0x5a, 0x4c, 0x4d, 0x20, 0x20, 0x38, 0x31, 0x30, 0x31, 0x53, 0x59, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x40, 0x40, 0x00, 0x00, 0x0d, 0x41, 0x01, 0x00, 0x10, 0x01, 0x11, 0x00, 0x01, 0x01, 0x00, 0x00, 0x00, 0x00, 0x7f, 0x20, 0x00, 0x00, 0x0d, 0x0b};
//                                           new int[]{0x00, 0x5a, 0x4c, 0x4d, 0x20, 0x20, 0x38, 0x31, 0x30, 0x31, 0x53, 0x59, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x40, 0x40, 0x00, 0x00, 0x0d, 0x41, 0x01, 0x00, 0x10, 0x01, 0x11, 0x00, 0x01, 0x02, 0x00, 0x00, 0x00, 0x00, 0x7f, 0x20, 0x00, 0x00, 0x0d};    
    public static SysexDataModel LM_8101SY = new SysexDataModel("system setup",
            COMMON_HEAD0_BLOCK,
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
            new ByteValues("reserve", 10)
    );
    public static int[] LM_8104PC_VALUES = new int[]{0x02, 0x1a, 0x4c, 0x4d, 0x20, 0x20, 0x38, 0x31, 0x30, 0x34, 0x50, 0x43, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x02, 0x00, 0x02, 0x01, 0x02, 0x02, 0x02, 0x03, 0x02, 0x04, 0x02, 0x05, 0x02, 0x06, 0x02, 0x07, 0x02, 0x08, 0x02, 0x09, 0x02, 0x0a, 0x02, 0x0b, 0x02, 0x0c, 0x02, 0x0d, 0x02, 0x0e, 0x02, 0x0f, 0x02, 0x10, 0x02, 0x11, 0x02, 0x12, 0x02, 0x13, 0x02, 0x14, 0x02, 0x15, 0x02, 0x16, 0x02, 0x17, 0x02, 0x18, 0x02, 0x19, 0x02, 0x1a, 0x02, 0x1b, 0x02, 0x1c, 0x02, 0x1d, 0x02, 0x1e, 0x02, 0x1f, 0x02, 0x20, 0x02, 0x21, 0x02, 0x22, 0x02, 0x23, 0x02, 0x24, 0x02, 0x25, 0x02, 0x26, 0x02, 0x27, 0x02, 0x28, 0x02, 0x29, 0x02, 0x2a, 0x02, 0x2b, 0x02, 0x2c, 0x02, 0x2d, 0x02, 0x2e, 0x02, 0x2f, 0x02, 0x30, 0x02, 0x31, 0x02, 0x32, 0x02, 0x33, 0x02, 0x34, 0x02, 0x35, 0x02, 0x36, 0x02, 0x37, 0x02, 0x38, 0x02, 0x39, 0x02, 0x3a, 0x02, 0x3b, 0x02, 0x3c, 0x02, 0x3d, 0x02, 0x3e, 0x02, 0x3f, 0x03, 0x00, 0x03, 0x01, 0x03, 0x02, 0x03, 0x03, 0x03, 0x04, 0x03, 0x05, 0x03, 0x06, 0x03, 0x07, 0x03, 0x08, 0x03, 0x09, 0x03, 0x0a, 0x03, 0x0b, 0x03, 0x0c, 0x03, 0x0d, 0x03, 0x0e, 0x03, 0x0f, 0x03, 0x10, 0x03, 0x11, 0x03, 0x12, 0x03, 0x13, 0x03, 0x14, 0x03, 0x15, 0x03, 0x16, 0x03, 0x17, 0x03, 0x18, 0x03, 0x19, 0x03, 0x1a, 0x03, 0x1b, 0x03, 0x1c, 0x03, 0x1d, 0x03, 0x1e, 0x03, 0x1f, 0x03, 0x20, 0x03, 0x21, 0x03, 0x22, 0x03, 0x23, 0x03, 0x24, 0x03, 0x25, 0x03, 0x26, 0x03, 0x27, 0x03, 0x28, 0x03, 0x29, 0x03, 0x2a, 0x03, 0x2b, 0x03, 0x2c, 0x03, 0x2d, 0x03, 0x2e, 0x03, 0x2f, 0x03, 0x30, 0x03, 0x31, 0x03, 0x32, 0x03, 0x33, 0x03, 0x34, 0x03, 0x35, 0x03, 0x36, 0x03, 0x37, 0x03, 0x38, 0x03, 0x39, 0x03, 0x3a, 0x03, 0x3b, 0x03, 0x3c, 0x03, 0x3d, 0x03, 0x3e, 0x03, 0x3f};

    public static final DataBlock PROGRAM_CHANGE_BLOCK = new DataBlock("pc",
            new CodeValue("vc type", 0, 10, 
                new KV(0, "Internal VC COM"),  new KV(1, "Card VC COM"),  new KV(2, "Preset0 VC COM"), new KV( 3, "Preset1 VC COM"),
                new KV(4, "Internal MLT"),     new KV(5, "Card MLT"),     new KV(6, "Preset MLT"),
                new KV(7, "Internal VC IND"),  new KV(8, "Card VC IND"),  new KV(9, "Preset0 VC IND"), new KV(10, "Preset1 VC IND")),
            new ByteValue("pg#", 0, 63)
    );
    public final static SysexDataModel LM_8104PC = new SysexDataModel("program change table",
            COMMON_HEAD0_BLOCK
    );
    static{
        DecimalFormat df = new DecimalFormat("000");
        for(int i = 0;i < 128;i++){
            LM_8104PC.add(PROGRAM_CHANGE_BLOCK.copy("#" + df.format(i)));
        }
    }
    public static final DataBlock LM_8101VC_HEAD_BLOCK = new DataBlock("vc_header",
            COMMON_HEAD1_BLOCK,
            new CodeValue("elmode", 
                    new KV(0x0, "1AFM_mono"), new KV(0x1, "2AFM_mono"), new KV(0x2, "4AFM_mono"), new KV(0x3, "1AFM_poly"), new KV(0x4, "2AFM_poly"), new KV(0x5, "1AWM_poly"),
                    new KV(0x6, "2AWM_poly"), new KV(0x7, "4AWM_poly"), new KV(0x8, "1AFM_1AWM_poly"), new KV(0x9, "2FM_2PCM_poly"),new KV(0xa, "DRUM_SET")),
            new Characters("voice name", 10));
    public static SysexDataModel LM_8101VC_CHECK = new SysexDataModel("voice_check", LM_8101VC_HEAD_BLOCK);
    
    public final static DataBlock CONTROLLER = new DataBlock("controllder model",
            new ByteValue("midi control#", 0, 121),
            new ByteValue("range", 0, 127)
    );
    public final static DataBlock CONTROLLERS = new DataBlock("controllers", 
            new ByteValue("wheel pitchbend", 0, 12),
            new SignedValue("after touch pitchbend", -12, 12),
            CONTROLLER.copy("pitch moduation"),
            CONTROLLER.copy("amplitude moduation"),
            CONTROLLER.copy("filter moduation"),
            CONTROLLER.copy("pan moduation"),
            CONTROLLER.copy("filter cutoff bias"),
            CONTROLLER.copy("pan bias"),
            CONTROLLER.copy("EG bias"),
            CONTROLLER.copy("voice volume")
    );
    public final static DataBlock ELEMENT_BLOCK = new DataBlock("element_model",
            new ByteValue("level"),
            new SignedValue("detune", -7, 7),
            new OffsetBinary("note shift", 0, 127, 64),
            new NoteValue("lo note limit", 0),
            new NoteValue("hi note limit", 0),
            new ByteValue("lo velo limit", 0, 127),
            new ByteValue("hi velo limit", 0, 127),
            new ByteValue("pan number", 0, 95),
            new BitArray("switch", "00000[2:output sel1][1:output sel0][0:micro tuning]", 0, 1, 2)
    );
    public final static DataBlock AFM_ELEMENT_BLOCK = new DataBlock("afm_element_model",
            new DataBlock("EG",
                new ByteValues("keyon rate", 4, 0, 63),
                new ByteValues("keyoff rate", 2, 0, 63),
                new ByteValues("keyon level", 4, 0, 63),
                new ByteValues("keyoff level", 2, 0, 63),
                new ByteValue("sustain loop point", 0, 3),
                new ByteValue("keyon hold time", 0, 63),
                new ByteValue("keyon level0", 0, 63),
                new SignedValue("rate scaling", -7, 7),
                new ByteValue("amp mod sens", 0, 7),
                new SignedValue("velo sens", -7, 7),
                new ByteValue("reserve")
            ),
            new DataBlock("oscilator",
                    new MultiBitArray("input source", 2, "0000000[4:src1] 0[567:src1][0123:src0]", 3, 2, 1, 0, 7, 6, 5, 4),
                    new BitArray("destination stc", "000[4:out1][23:out0][01:dst]", 1, 0, 3, 2, 4),
                    new BitArray("input shift", "00[012:in0][345:in1]", 5, 4, 3, 2, 1, 0),
                    new ByteValue("output level correction", 0, 7),
                    new ByteValue("wave form", 0, 15),
                    new BitArray("switch", "000[012:pitch mod sens][3:pitch EG][4:freq mode]",  4, 3, 2, 1, 0),
                    new OnOffValue("initial phase set enable", 0, 0, 1),
                    new ByteValue("initial phase"),
                    new SignedValue("pitch detune", -15, 15)),
            new DataBlock("out level",
                    new ByteValue("level"),
                    new NoteValues("scaling break point", 4, 0),
                    new MultiBytesOffsetBinary("offset BP0", 2, 0, 256, 128, true),
                    new MultiBytesOffsetBinary("offset BP1", 2, 0, 256, 128, true),
                    new MultiBytesOffsetBinary("offset BP2", 2, 0, 256, 128, true),
                    new MultiBytesOffsetBinary("offset BP3", 2, 0, 256, 128, true),
                    new OnOffValue("rate velo switch", 0, 0, 1)
            ),
            new ByteValue("freq coarse"),
            new ByteValue("freq fine")
    );
    public final static DataBlock AFM_ELEMENT_COMMON_BLOCK = new DataBlock("afm_element_common",
            new ByteValue("algorithm#", 0, 44),
            new DataBlock("pitch EG",
                    new ByteValues("rate(on3 off1)", 4),
                    new OffsetBinaries("level(on4 off1)", 5, 0, 127, 64),
                    new CodeValue("range", 0, 3, new KV(0, "8oct"), new KV(1, "2oct"), new KV(2, "1oct"), new KV(3, "1/2oct")),
                    new ByteValue("rate scaling", 0, 7),
                    new OnOffValue("velo switch", 0, 0, 1)),
            new DataBlock("main LFO",
                    new ByteValue("speed", 0, 99),
                    new ByteValue("delay time", 0, 99),
                    new ByteValue("pitch mod depth"),
                    new ByteValue("amp mod depth"),
                    new ByteValue("filter mod depth"),
                    new ByteValue("wave", 0, 5),
                    new ByteValue("initial phase", 0, 99),
                    new ByteValue("reserve")),
            new DataBlock("sub LFO",
                    new ByteValue("wave", 0, 3),
                    new ByteValue("speed"),
                    new CodeValue("delay/decay", 0, 1, new KV(0, "delay mode"), new KV(1, "decay mode")),
                    new ByteValue("time", 0, 99),
                    new ByteValue("pitch mod depth")
            )
            
    );
    public static DataBlock FILTER_BLOCK = new DataBlock("filter",
            new CodeValue("type", 0, 2, new KV(0, "LPF"), new KV(1, "HPF"), new KV(2, "THRU")),
            new ByteValue("cutoff freq"),
            new CodeValue("mode", 0, 2, new KV(0, "EG"), new KV(1, "LFO"), new KV(2, "EG-VA")),
            new ByteValues("rate(on4 off2)", 6, 0, 63),
            new OffsetBinaries("cutoff level(on4 off2)", 6, 0, 127, 64),
            new SignedValue("rate scaling", -7, 7),
            new NoteValues("cutoff level break point", 4, 0),
            new MultiBytesValue("cutoff level scaling offset0", 2, true),
            new MultiBytesValue("cutoff level scaling offset1", 2, true),
            new MultiBytesValue("cutoff level scaling offset2", 2, true),
            new MultiBytesValue("cutoff level scaling offset3", 2, true)
    );
    public static SysexDataModel LM_8101VC_0 = new SysexDataModel("10m", LM_8101VC_HEAD_BLOCK,
            EFFECT_BLOCK,
            CONTROLLERS,
            new MultiBytesValue("AWM id", 2, true),
            ELEMENT_BLOCK.copy("element"),
            AFM_ELEMENT_BLOCK.copy("op0"),
            AFM_ELEMENT_BLOCK.copy("op1"),
            AFM_ELEMENT_BLOCK.copy("op2"),
            AFM_ELEMENT_BLOCK.copy("op3"),
            AFM_ELEMENT_BLOCK.copy("op4"),
            AFM_ELEMENT_BLOCK.copy("op5"),
            AFM_ELEMENT_COMMON_BLOCK,
            
            new ByteValues("rest", 421)//421
    );
    public static SysexDataModel LM_8101VC_9 = new SysexDataModel("22p", LM_8101VC_HEAD_BLOCK,
            new ByteValues("rest", 1029)
    );
    public static int[] LM_8101MU_VALUES = new int[]{0x01, 0x44, 0x4c, 0x4d, 0x20, 0x20, 0x38, 0x31, 0x30, 0x31, 0x4d, 0x55, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x53, 0x6f, 0x20, 0x66, 0x61, 0x72, 0x2c, 0x73, 0x6f, 0x20, 0x6c, 0x6f, 0x6e, 0x67, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x01, 0x00, 0x64, 0x64, 0x01, 0x01, 0x01, 0x01, 0x00, 0x64, 0x64, 0x08, 0x08, 0x08, 0x08, 0x01, 0x64, 0x3b, 0x14, 0x09, 0x1d, 0x01, 0x64, 0x24, 0x14, 0x09, 0x1d, 0x01, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x02, 0x31, 0x00, 0x40, 0x40, 0x20, 0x01, 0x00, 0x3d, 0x00, 0x34, 0x34, 0x20, 0x02, 0x02, 0x38, 0x00, 0x40, 0x34, 0x0c, 0x03, 0x00, 0x30, 0x00, 0x40, 0x40, 0x00, 0x01, 0x00, 0x32, 0x00, 0x40, 0x40, 0x00, 0x01, 0x01, 0x2d, 0x00, 0x40, 0x40, 0x34, 0x03, 0x00, 0x0e, 0x00, 0x40, 0x40, 0x19, 0x01, 0x02, 0x3f, 0x00, 0x40, 0x40, 0x00, 0x01, 0x02, 0x2b, 0x00, 0x40, 0x40, 0x20, 0x41, 0x02, 0x3e, 0x00, 0x40, 0x40, 0x00, 0x41, 0x02, 0x3f, 0x00, 0x40, 0x40, 0x20, 0x01, 0x01, 0x00, 0x00, 0x40, 0x40, 0x20, 0x01, 0x01, 0x00, 0x00, 0x40, 0x40, 0x20, 0x01, 0x01, 0x00, 0x00, 0x40, 0x40, 0x20, 0x01, 0x02, 0x3f, 0x00, 0x40, 0x40, 0x00, 0x43, 0x02, 0x1a, 0x7f, 0x40, 0x40, 0x00};
    public static SysexDataModel LM_8101MU = new SysexDataModel("multi_model", 
            COMMON_HEAD1_BLOCK,
            new Characters("name", 0X14),
            EFFECT_BLOCK,
            new Blank("blank", 9),
            MULTI_CHANNEL_BLOCK.copy("VC0"),
            MULTI_CHANNEL_BLOCK.copy("VC1"),
            MULTI_CHANNEL_BLOCK.copy("VC2"),
            MULTI_CHANNEL_BLOCK.copy("VC3"),
            MULTI_CHANNEL_BLOCK.copy("VC4"),
            MULTI_CHANNEL_BLOCK.copy("VC5"),
            MULTI_CHANNEL_BLOCK.copy("VC6"),
            MULTI_CHANNEL_BLOCK.copy("VC7"),
            MULTI_CHANNEL_BLOCK.copy("VC8"),
            MULTI_CHANNEL_BLOCK.copy("VC9"),
            MULTI_CHANNEL_BLOCK.copy("VCa"),
            MULTI_CHANNEL_BLOCK.copy("VCb"),
            MULTI_CHANNEL_BLOCK.copy("VCc"),
            MULTI_CHANNEL_BLOCK.copy("VCd"),
            MULTI_CHANNEL_BLOCK.copy("VCe"),
            MULTI_CHANNEL_BLOCK.copy("VCf")
    );
}
