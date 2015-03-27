/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package origine_mundi.machine;

import static origine_mundi.OmUtil.MICRO_LITE_4;
import origine_mundi.SysexDataModel;
import origine_mundi.SysexDataModel.BitArray;
import origine_mundi.SysexDataModel.ByteValue;
import origine_mundi.SysexDataModel.ByteValues;
import origine_mundi.SysexDataModel.CodeValue;
import origine_mundi.SysexDataModel.KV;

/**
 *
 * @author Mina
 */
public class GP_16 extends Roland {
    public static int DEVICE_ID = 0x00;
    public static int MODEL_ID = 0x2a;
    private static String MIDI_PORT = MICRO_LITE_4;
    private static GP_16 instance = null;
    public static GP_16 instance(){
        if(instance == null){
            instance = new GP_16();
        }
        return instance;
    }
    private GP_16(){
        super(DEVICE_ID, MODEL_ID, MIDI_PORT, MIDI_PORT);
    }
    public static void main(String[] args){
        GP_16 gp_16 = GP_16.instance();
        gp_16.get(gp_16.getRQT(0x00, 0x00, 0x00, 0x00, 0x01, 0x00), PATCH).getExplanations().print();
        gp_16.finalize();
        //u_110.listen();
    }
    public static int[] TEMPORARY_PATH = new int[]{0x00, 0x00, 0x00, 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x09, 0x0a, 0x08, 0x0b, 0x00, 0x0c, 0x79, 0x32, 0x64, 0x2f, 0x14, 0x32, 0x50, 0x32, 0x32, 0x5a, 0x00, 0x32, 0x23, 0x14, 0x28, 0x01, 0x16, 0x64, 0x4b, 0x5f, 0x47, 0x64, 0x20, 0x18, 0x00, 0x1d, 0x10, 0x00, 0x14, 0x5a, 0x1c, 0x1c, 0x64, 0x5c, 0x64, 0x28, 0x50, 0x14, 0x1e, 0x32, 0x64, 0x0a, 0x46, 0x04, 0x50, 0x00, 0x64, 0x0c, 0x32, 0x00, 0x00, 0x00, 0x14, 0x64, 0x00, 0x09, 0x30, 0x04, 0x58, 0x02, 0x2c, 0x0f, 0x11, 0x16, 0x0c, 0x01, 0x48, 0x0e, 0x07, 0x01, 0x48, 0x1d, 0x21, 0x23, 0x50, 0x46, 0x50, 0x4b, 0x13, 0x00, 0x10, 0x00, 0x4b, 0x00, 0x00, 0x00, 0x53, 0x74, 0x65, 0x70, 0x50, 0x68, 0x61, 0x73, 0x65, 0x45, 0x56, 0x2d, 0x35, 0x41, 0x4d, 0x50, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};

    private static SysexDataModel PATCH = new SysexDataModel("patch_model",
            new ByteValues("address", 3),
            new CodeValue("joint group A0", 0, 4, new KV(0, "COMP"), new KV(1, "DIST/OD"), new KV(2, "PickFilter"), new KV(3, "Step PHASER"), new KV(4, "Para EQ")),
            new CodeValue("joint group A1", 0, 4, new KV(0, "COMP"), new KV(1, "DIST/OD"), new KV(2, "PickFilter"), new KV(3, "Step PHASER"), new KV(4, "Para EQ")),
            new CodeValue("joint group A2", 0, 4, new KV(0, "COMP"), new KV(1, "DIST/OD"), new KV(2, "PickFilter"), new KV(3, "Step PHASER"), new KV(4, "Para EQ")),
            new CodeValue("joint group A3", 0, 4, new KV(0, "COMP"), new KV(1, "DIST/OD"), new KV(2, "PickFilter"), new KV(3, "Step PHASER"), new KV(4, "Para EQ")),
            new CodeValue("joint group A4", 0, 4, new KV(0, "COMP"), new KV(1, "DIST/OD"), new KV(2, "PickFilter"), new KV(3, "Step PHASER"), new KV(4, "Para EQ")),
            new CodeValue("joint group A5", 5, 5, new KV(5, "Noise Surpressor")),
            new CodeValue("joint group B0", 6, 10, new KV(6, "Short DL"), new KV(7, "CHO/FLNG/PS/SD"), new KV(8, "Auto Pan"), new KV(9, "Tap DL"), new KV(10, "REV")),
            new CodeValue("joint group B1", 6, 10, new KV(6, "Short DL"), new KV(7, "CHO/FLNG/PS/SD"), new KV(8, "Auto Pan"), new KV(9, "Tap DL"), new KV(10, "REV")),
            new CodeValue("joint group B2", 6, 10, new KV(6, "Short DL"), new KV(7, "CHO/FLNG/PS/SD"), new KV(8, "Auto Pan"), new KV(9, "Tap DL"), new KV(10, "REV")),
            new CodeValue("joint group B3", 6, 10, new KV(6, "Short DL"), new KV(7, "CHO/FLNG/PS/SD"), new KV(8, "Auto Pan"), new KV(9, "Tap DL"), new KV(10, "REV")),
            new CodeValue("joint group B4", 6, 10, new KV(6, "Short DL"), new KV(7, "CHO/FLNG/PS/SD"), new KV(8, "Auto Pan"), new KV(9, "Tap DL"), new KV(10, "REV")),
            new CodeValue("joint group B5", 11, 11, new KV(11, "Lineout Filter")),
            new CodeValue("onoff0 B1", 0, 3, new KV(0, "CHO"), new KV(1, "FLNG"), new KV(2, "P Shifter"), new KV(3, "Space-D")),
            new BitArray("onoff1", "0[0:A1 DIST/OD][n/a][5:B5][4:B4][3:B3][2:B2][1:B1]", 6, 0, 1, 2, 3, 4),
            new BitArray("onoff2", "0[6:B1][5:A5][4:A4][3:A3][2:A2][1:A1][0:A0]", 0, 1, 2, 3, 4, 5, 6),
            new ByteValues("rest", 113)
    );
    
}
