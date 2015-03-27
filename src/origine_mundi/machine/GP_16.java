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
        GP_16 u_110 = GP_16.instance();
        u_110.get(u_110.getRQT(0x00, 0x00, 0x00, 0x00, 0x01, 0x00), PATCH).getExplanations().print();
        u_110.finalize();
        //u_110.listen();
    }

    private static SysexDataModel PATCH = new SysexDataModel("patch_model",
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
            new CodeValue("onoff0 B2", 0, 3, new KV(0, "CHO"), new KV(1, "FLNG"), new KV(2, "P Shifter"), new KV(3, "Space-D")),
            new BitArray("onoff1", "0[0:A2 DIST/OD][n/a][5:B5][4:B4][3:B3][2:B2][1:B1]", 6, 0, 1, 2, 3, 4),
            new BitArray("onoff2", "0[6:B1][5:A5][4:A4][3:A3][2:A2][1:A1][0:A0]", 0, 1, 2, 3, 4, 5, 6),
            new ByteValues("rest", 131)
    );
    
}
