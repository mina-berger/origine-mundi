/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package origine_mundi.archive;

import java.io.File;
import la.clamor.Aestimatio;
import la.clamor.io.LectorWav;

/**
 *
 * @author mina
 */
public class ArchiverTest {
    public static void main(String[] args){
        File file = new File("/Users/mina/drive/doc/origine_mundi/archive/50_78.wav");
        LectorWav l1 = new LectorWav(file);
        LectorWav l2 = new LectorWav(file);
        while(l1.paratusSum()){
            l1.lego();
            l2.lego();
            //System.out.println(l1.lego() + " " + l2.lego());
        }
    }
}
