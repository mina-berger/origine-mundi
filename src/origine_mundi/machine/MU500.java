/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package origine_mundi.machine;

import origine_mundi.OmUtil;

/**
 *
 * @author Mina
 */
public class MU500 extends Yamaha {
    private static final int DEVICE_ID_LOWER = 0x0;
    private static final int MODEL_ID = 0x49; //XG 0x4c

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
    
}
