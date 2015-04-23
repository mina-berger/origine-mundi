/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package origine_mundi.machine;

import origine_mundi.SysexBuilder;
import origine_mundi.OmUtil;
import origine_mundi.SysexDataModel;
import origine_mundi.SysexDataModel.ByteValue;
import origine_mundi.SysexDataModel.ByteValues;
import origine_mundi.SysexDataModel.CodeValue;
import origine_mundi.SysexDataModel.DataBlock;
import origine_mundi.SysexDataModel.KV;
import origine_mundi.SysexDataModel.MultiBytesValue;
import origine_mundi.SysexDataModel.OnOffValue;

/**
 *
 * @author Mina
 */
public class MU500 extends Yamaha {
    private static final int DEVICE_ID_LOWER = 0x20 + 0x0;
    private static final int MODEL_ID = 0x49; //XG 0x4c
    private static final int XG_MODEL_ID = 0x4c; //XG 0x4c

    private static MU500[] instances = new MU500[4];
    public static MU500 instance(int index){
        if(instances[index] == null){
            instances[index] = new MU500(index);
        }
        return instances[index];
    }
    public MU500(int index) {
        super(DEVICE_ID_LOWER, MODEL_ID, OmUtil.MU500[index], OmUtil.MU500[index]);
    }
    public SysexBuilder getRequest(boolean is_xg, int[] address){
        return getRequest(is_xg?XG_MODEL_ID:MODEL_ID, address, REQUEST);
    }
    public SysexBuilder getPartRequest(int part){
        if(part < 0 || part > 63){
            throw new IllegalArgumentException("no part(" + part + ")");
        }
        int middle_address = ((int)(part / 0x40)) * 0x10 + part % 0x40;
        return getRequest(XG_MODEL_ID, new int[]{0x08, middle_address, 0x00}, REQUEST);
    }
    public static final SysexDataModel REQUEST = new SysexDataModel("part", new ByteValues("address", 3));
    
    public static int[] ADDRESS_XG_SYSTEM           = new int[]{0x00, 0x00, 0x00};
    public static int[] ADDRESS_DRUMSET_SETUP_RESET = new int[]{0x00, 0x00, 0x7d};//send_only
    public static int[] ADDRESS_XG_SYSTEM_ON        = new int[]{0x00, 0x00, 0x7e};//send_only
    public static int[] ADDRESS_ALL_PARAMETER_RESET = new int[]{0x00, 0x00, 0x7f};//send_only
    public static int[] ADDRESS_INFORMATION         = new int[]{0x01, 0x00, 0x00};//dump_only
    public static int[] ADDRESS_EFFECT0             = new int[]{0x02, 0x01, 0x00};
    public static int[] ADDRESS_MULTI_EQ            = new int[]{0x02, 0x40, 0x00};
    public static int[] ADDRESS_INSERTION_EFFECT0   = new int[]{0x03, 0x00, 0x00};
    public static int[] ADDRESS_INSERTION_EFFECT1   = new int[]{0x03, 0x01, 0x00};
    public static int[] ADDRESS_DRUM_SETUP0         = new int[]{0x30, 0x0d, 0x00};
    public static int[] ADDRESS_DRUM_SETUP1         = new int[]{0x31, 0x0d, 0x00};
    public static int[] ADDRESS_DRUM_SETUP2         = new int[]{0x32, 0x0d, 0x00};
    public static int[] ADDRESS_DRUM_SETUP3         = new int[]{0x22, 0x0d, 0x00};

    public static final SysexDataModel PART = new SysexDataModel("part",
            new DataBlock("main",
                    new ByteValue("elem reserve"),
                    new MultiBytesValue("bank select", 2, true),
                    new ByteValue("program#"),
                    new CodeValue("rcv channel", 0, 64, new KV(0x7f, "off")),
                    new ByteValue("mono poly mode"),
                    new CodeValue("same note#", 0, 2, new KV(0, "SINGLE"), new KV(1, "MULTI"), new KV(2, "INST")),
                    new CodeValue("part mode", 0, 5, new KV(0, "NORMAL"), new KV(1, "DRUM"),
                        new KV(2, "DRUMS0"), new KV(3, "DRIMS1"), new KV(4, "DRUMS2"), new KV(5, "DRUMS3"))
            ),
            new ByteValues("rest", 38)
    );
    public static final SysexDataModel SYSTEM = new SysexDataModel("xg_system",
            new ByteValues("rest", 15)
    );
    public static void main(String[] args){
        //MU500 mu = MU500.instance(0);
        //mu.checkSound(0);
        //System.out.println(OmUtil.sysex(mu.getRequest(false, ADDRESS_XG_SYSTEM).getSysex()));
        SYSTEM.getExplanations(SYSTEM_DATA).print();
        //mu.get(mu.getRequest(false, ADDRESS_XG_SYSTEM), SYSTEM).getExplanations().print();
        //mu.get(mu.getPartRequest(0), PART).getExplanations().print();
    }
    public static int[] SYSTEM_DATA = new int[]{0x00, 0x0a, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x01, 0x02, 0x00, 0x40, 0x01, 0x00};
    public static int[] PART_VALUE = new int[]{0x00, 0x29, 0x08, 0x00, 0x00, 0x02, 0x00, 0x00, 0x00, 0x00, 0x01, 0x01, 0x00, 0x40, 0x08, 0x00, 0x64, 0x40, 0x40, 0x40, 0x00, 0x7f, 0x7f, 0x00, 0x28, 0x00, 0x40, 0x40, 0x40, 0x40, 0x40, 0x40, 0x40, 0x40, 0x40, 0x40, 0x40, 0x0a, 0x00, 0x00, 0x42, 0x40, 0x40, 0x00, 0x00, 0x00};
    
}
