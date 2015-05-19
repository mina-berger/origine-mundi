/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package origine_mundi.opus;

import java.util.ArrayList;
import java.util.HashMap;
import la.clamor.Envelope;
import la.clamor.Punctum;
import origine_mundi.MidiMachines;
import origine_mundi.filter.FilterInfo;
import origine_mundi.filter.FilterInfo.ThruInfo;
import origine_mundi.ludior.BrevFactory;
import origine_mundi.ludior.Brevs;
import origine_mundi.ludior.Iunctum;
import origine_mundi.ludior.Tempus;
import origine_mundi.machine.MU500;
import origine_mundi.sampler.Desktop;
import origine_mundi.sampler.LimaLusa;

/**
 *
 * @author user
 */
public class Opus011 extends Desktop {

    @Override
    protected void callDevices(MidiMachines midi_machines) {
         midi_machines.put(1, MU500.instance(0));
    }

    @Override
    protected Tempus getTempus() {
        return new Tempus(
                new Tempus.Comes[]{}, 
                new Tempus.Rapidus[]{new Tempus.Rapidus(0, 0, 84, true)});
    }

    @Override
    protected void getBrevs(HashMap<String, Brevs> brevs_map) {
        BrevFactory bf0 = new BrevFactory(new Iunctum(0, 0, 9));
        bf0.setLoco(1, 0d);
        bf0.program(127, 0, 1);
        brevs_map.put("ctrl1", bf0.remove());
        
        bf0.note(36, 120, 0.5, 1, false);
        brevs_map.put("b_120", bf0.remove());
        
        bf0.note(36, 120, 0.5, 1, false);
        brevs_map.put("b_60", bf0.remove());
        
        bf0.note(38, 120, 0.5, 1, false);
        brevs_map.put("s_120", bf0.remove());
        
        bf0.note(38, 90,  0.02, 1, true);
        bf0.note(38, 120, 0.5, 1, false);
        brevs_map.put("s_120_d", bf0.remove());
        
        bf0.note(42, 120, 0.5, 1, false);
        brevs_map.put("h_120", bf0.remove());
        
        bf0.note(42, 95, 0.5, 1, false);
        brevs_map.put("h_95", bf0.remove());
        
        bf0.note(42, 80, 0.5, 1, false);
        brevs_map.put("h_80", bf0.remove());
    }

    @Override
    protected void getLusa(ArrayList<LimaLusa> lusa_list) {
        Envelope env1 = new Envelope();
        env1.ponoPunctum(0, new Punctum(1));
        FilterInfo fi1 = new ThruInfo();
        int measure = 4;
        //double temp_beat = 500;
        lusa_list.add(new LimaLusa("b_120",   0, 2.75,    0.5, env1, fi1, new Punctum.Aestimatio(1)));
        lusa_list.add(new LimaLusa("s_120_d", 0, 2.98,    0.5, env1, fi1, new Punctum.Aestimatio(1)));
        lusa_list.add(new LimaLusa("h_120",   0, 3.75,    0.5, env1, fi1, new Punctum.Aestimatio(1)));
        for(int i = 1;i <= measure;i++){
            lusa_list.add(new LimaLusa("b_120", i, 0,    0.5, env1, fi1, new Punctum.Aestimatio(1)));
            lusa_list.add(new LimaLusa("s_120", i, 1,    0.5, env1, fi1, new Punctum.Aestimatio(1)));
            lusa_list.add(new LimaLusa("b_60",  i, 1.75, 0.5, env1, fi1, new Punctum.Aestimatio(0.6)));
            lusa_list.add(new LimaLusa("b_120", i, 2,    0.5, env1, fi1, new Punctum.Aestimatio(1)));
            lusa_list.add(new LimaLusa("s_120", i, 3,    0.5, env1, fi1, new Punctum.Aestimatio(1)));
            double back = 0.010;
            int count = 0;
            for(int j = 0;j < 4;j++){
                lusa_list.add(new LimaLusa("h_120", i, count++ * 0.25,        0.25, env1, fi1, new Punctum.Aestimatio(1)));
                lusa_list.add(new LimaLusa("h_80",  i, count++ * 0.25 + back, 0.25, env1, fi1, new Punctum.Aestimatio(0.6)));
                lusa_list.add(new LimaLusa("h_80",  i, count++ * 0.25,        0.25, env1, fi1, new Punctum.Aestimatio(0.6)));
                lusa_list.add(new LimaLusa("h_95",  i, count++ * 0.25 + back, 0.25, env1, fi1, new Punctum.Aestimatio(0.75)));
            }
        }
    }
    
}
