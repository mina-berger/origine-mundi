/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package origine_mundi.opus;

import java.util.ArrayList;
import java.util.HashMap;
import la.clamor.Aestimatio;
import la.clamor.Envelope;
import la.clamor.PunctaTalearum;
import la.clamor.Punctum;
import la.clamor.Talea;
import origine_mundi.Integers;
import origine_mundi.MidiMachines;
import origine_mundi.filter.FilterInfo;
import origine_mundi.filter.FilterInfo.ThruInfo;
import origine_mundi.ludior.BrevFactory;
import origine_mundi.ludior.Brevs;
import origine_mundi.ludior.ChordStroke;
import origine_mundi.ludior.Expression;
import origine_mundi.ludior.Iunctum;
import origine_mundi.ludior.Tempus;
import origine_mundi.machine.D_110;
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
        midi_machines.put(0, MU500.instance(0));
        midi_machines.put(1, D_110.instance());
    }

    @Override
    protected Tempus getTempus() {
        return new Tempus(
                new Tempus.Comes[]{}, 
                new Tempus.Rapidus[]{new Tempus.Rapidus(new Talea(), 104, true)});
    }

    @Override
    protected void getBrevs(HashMap<String, Brevs> brevs_map) {
        BrevFactory bf0 = new BrevFactory(new Iunctum(0, 0, 9));
        bf0.setLoco(new Talea());
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
        
        
        
        ChordStroke stroke0 = new ChordStroke(0.8, 1.0, 0.01, 0.015, true);
        Expression exp0 = new Expression(
                new Expression.Control(0x01, 10, 30, 0, 0.1)//,
                //new Command(ShortMessage.PITCH_BEND, 8300, 8100, 0, 0.15)
        );
        
        
        
        BrevFactory bf1 = new BrevFactory(
                new Iunctum(1, 1, 0), new Iunctum(1, 1, 1), new Iunctum(1, 1, 2), 
                new Iunctum(1, 1, 3), new Iunctum(1, 1, 4), new Iunctum(1, 1, 5));
        bf1.setLoco(new Talea());
        bf1.note(new Integers(0, 1, 2, 3, 4, 5), new Integers(40, 47, 52, 56, 59, 64), 120, 1, 1, stroke0, exp0, true);
        brevs_map.put("01d", bf1.remove());
        
        bf1.note(new Integers(5, 4, 3, 2, 1, 0), new Integers(64, 59, 56, 52, 47, 40), 120, 1, 1, stroke0, exp0, true);
        brevs_map.put("01u", bf1.remove());

    }

    @Override
    protected void getLusa(ArrayList<LimaLusa> lusa_list) {
        Envelope env1 = new Envelope();
        env1.ponoPunctum(0, new Punctum(1));
        Envelope env2 = new Envelope();
        env2.ponoPunctum(0, new Punctum(1));
        env2.ponoPunctum(200, new Punctum(0.8));
        env2.ponoPunctum(400, new Punctum(0));
        FilterInfo fi1 = new ThruInfo();
        int measure = 24;
        //double temp_beat = 500;
        double duration = 1;
        double back = 0.002;
        int track = 0;
        lusa_list.add(new LimaLusa(track, "b_120",   new Talea(0, 2.75 + back),    duration, env1, fi1, new Aestimatio(1)));
        lusa_list.add(new LimaLusa(track, "s_120_d", new Talea(0, 2.98),           duration, env2, fi1, new Aestimatio(1)));
        lusa_list.add(new LimaLusa(track, "h_120",   new Talea(0, 3.75 + back),    duration, env1, fi1, new Aestimatio(1)));
        for(int i = 1;i <= measure;i++){
            lusa_list.add(new LimaLusa(track, "b_120", new Talea(i, 0),           duration, env1, fi1, new Aestimatio(1)));
            lusa_list.add(new LimaLusa(track, "s_120", new Talea(i, 1),           duration, env2, fi1, new Aestimatio(1)));
            lusa_list.add(new LimaLusa(track, "b_60",  new Talea(i, 1.75 + back), duration, env1, fi1, new Aestimatio(0.6)));
            lusa_list.add(new LimaLusa(track, "b_120", new Talea(i, 2),           duration, env1, fi1, new Aestimatio(1)));
            lusa_list.add(new LimaLusa(track, "s_120", new Talea(i, 3),           duration, env2, fi1, new Aestimatio(1)));
            int count = 0;
            for(int j = 0;j < 4;j++){
                lusa_list.add(new LimaLusa(track, "h_120", new Talea(i, count++ * 0.25),        duration, env1, fi1, new Aestimatio(1)));
                lusa_list.add(new LimaLusa(track, "h_80",  new Talea(i, count++ * 0.25 + back), duration, env1, fi1, new Aestimatio(0.6)));
                lusa_list.add(new LimaLusa(track, "h_80",  new Talea(i, count++ * 0.25),        duration, env1, fi1, new Aestimatio(0.6)));
                lusa_list.add(new LimaLusa(track, "h_95",  new Talea(i, count++ * 0.25 + back), duration, env1, fi1, new Aestimatio(0.75)));
            }
        }
        Envelope genv1 = new Envelope();
        genv1.ponoPunctum(0, new Punctum(1));
        genv1.ponoPunctum(100, new Punctum(1));
        genv1.ponoPunctum(120, new Punctum(0));
        Envelope genv2 = new Envelope();
        genv2.ponoPunctum(0, new Punctum(1));
        genv2.ponoPunctum(50, new Punctum(1));
        genv2.ponoPunctum(60, new Punctum(0));
        Envelope genv3 = new Envelope();
        genv3.ponoPunctum(0, new Punctum(1));
        genv3.ponoPunctum(20, new Punctum(1));
        genv3.ponoPunctum(30, new Punctum(0));
        boolean lpf = true;
        FilterInfo gfi1 = new FilterInfo.FIRInfo(20000, lpf, true, new Aestimatio(1));
        FilterInfo gfi2 = new FilterInfo.FIRInfo( 5000, lpf, true, new Aestimatio(1));
        track = 1;
        for(int i = 1;i <= measure;i++){
            //double m_temp = i * (temp_beat * 4);
            //String no = i % 2 == 0?"01":"02";
            String no = "01";
            int count = 0;
            lusa_list.add(new LimaLusa(track, no + "d", new Talea(i, count++ * 0.25),        0.25, genv1, gfi1, new Aestimatio(1)));
            lusa_list.add(new LimaLusa(track, no + "u", new Talea(i, count++ * 0.25 + back), 0.25, genv1, gfi1, new Aestimatio(0.8)));
            lusa_list.add(new LimaLusa(track, no + "d", new Talea(i, count++ * 0.25),        0.25, genv3, gfi2, new Aestimatio(0.5)));
            lusa_list.add(new LimaLusa(track, no + "u", new Talea(i, count++ * 0.25 + back), 0.25, genv2, gfi1, new Aestimatio(0.8)));
            lusa_list.add(new LimaLusa(track, no + "d", new Talea(i, count++ * 0.25),        0.25, genv3, gfi2, new Aestimatio(0.5)));
            lusa_list.add(new LimaLusa(track, no + "u", new Talea(i, count++ * 0.25 + back), 0.25, genv1, gfi1, new Aestimatio(1)));
            lusa_list.add(new LimaLusa(track, no + "d", new Talea(i, count++ * 0.25),        0.25, genv3, gfi2, new Aestimatio(0.5)));
            lusa_list.add(new LimaLusa(track, no + "u", new Talea(i, count++ * 0.25 + back), 0.25, genv3, gfi2, new Aestimatio(0.5)));
            lusa_list.add(new LimaLusa(track, no + "d", new Talea(i, count++ * 0.25),        0.25, genv1, gfi1, new Aestimatio(1.0)));
            lusa_list.add(new LimaLusa(track, no + "u", new Talea(i, count++ * 0.25 + back), 0.25, genv1, gfi1, new Aestimatio(0.8)));
            lusa_list.add(new LimaLusa(track, no + "d", new Talea(i, count++ * 0.25),        0.25, genv3, gfi2, new Aestimatio(0.5)));
            lusa_list.add(new LimaLusa(track, no + "u", new Talea(i, count++ * 0.25 + back), 0.25, genv2, gfi1, new Aestimatio(0.8)));
            lusa_list.add(new LimaLusa(track, no + "d", new Talea(i, count++ * 0.25),        0.25, genv3, gfi2, new Aestimatio(0.5)));
            lusa_list.add(new LimaLusa(track, no + "u", new Talea(i, count++ * 0.25 + back), 0.25, genv3, gfi2, new Aestimatio(0.5)));
            lusa_list.add(new LimaLusa(track, no + "d", new Talea(i, count++ * 0.25),        0.25, genv1, gfi1, new Aestimatio(1.0)));
            lusa_list.add(new LimaLusa(track, no + "u", new Talea(i, count++ * 0.25 + back), 0.25, genv3, gfi2, new Aestimatio(0.5)));
        }
        
    }

    @Override
    protected void getTrackSettings(HashMap<Integer, PunctaTalearum> track_settings) {
        PunctaTalearum pt0 = new PunctaTalearum();
        pt0.put(new Talea(), new Punctum(1));
        track_settings.put(0, pt0);
        
        PunctaTalearum pt1 = new PunctaTalearum();
        pt1.put(new Talea(), new Punctum(0));
        pt1.put(new Talea(1, 0), new Punctum(0));
        pt1.put(new Talea(5, 0), new Punctum(0.8));
        track_settings.put(1, pt1);
        
    }
    @Override
    protected void initialize() {
        //setAction(false, false, true, true);
        setAction(false, false, false, true);
        //setAction();
    }
    
}
