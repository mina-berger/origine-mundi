/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package origine_mundi.opus;

import java.util.ArrayList;
import java.util.HashMap;
import la.clamor.Punctum;
import la.clamor.Aestimatio;
import la.clamor.Envelope;
import la.clamor.Talea;
import origine_mundi.deprec.sampler.Desktop;
import com.mina.util.Integers;
import origine_mundi.MidiMachines;
import origine_mundi.deprec.FilterInfo;
import origine_mundi.deprec.FilterInfo.FIRInfo;
import origine_mundi.ludior.BrevFactory;
import origine_mundi.ludior.Brevs;
import origine_mundi.ludior.ChordStroke;
import origine_mundi.ludior.Expression;
import origine_mundi.ludior.Iunctum;
import origine_mundi.ludior.Tempus;
import origine_mundi.machine.D_110;
import origine_mundi.deprec.sampler.LegibilisLusa;
import origine_mundi.deprec.sampler.LimaLusa;

/**
 *
 * @author user
 */
public class Opus009 extends Desktop {
    @Override
    public void initialize(InitialSettings initials){
        initials.setAction(false, true, true, true);
    }

    @Override
    protected void callDevices(MidiMachines midi_machines) {
        midi_machines.put(0, D_110.instance());
    }

    @Override
    protected Tempus getTempus() {
        return new Tempus(
                new Tempus.Comes[]{}, 
                new Tempus.Rapidus[]{new Tempus.Rapidus(new Talea(), 120, true)});
    }

    @Override
    protected void getBrevs(HashMap<String, Brevs> brevs_map) {
        ChordStroke stroke0 = new ChordStroke(0.8, 1.0, 0.01, 0.015, true);
        Expression exp0 = new Expression(
                new Expression.Control(0x01, 10, 30, 0, 0.1)//,
                //new Command(ShortMessage.PITCH_BEND, 8300, 8100, 0, 0.15)
        );
        BrevFactory bf0 = new BrevFactory(
                new Iunctum(2, 0, 0), new Iunctum(2, 0, 1), new Iunctum(2, 0, 2), 
                new Iunctum(2, 0, 3), new Iunctum(2, 0, 4), new Iunctum(2, 0, 5));
        bf0.setLoco(new Talea());
        bf0.note(new Integers(0, 1, 2, 3, 4, 5), new Integers(40, 47, 52, 56, 59, 64), 120, 1, 1, stroke0, exp0, true);
        brevs_map.put("01d", bf0.remove());
        
        bf0.note(new Integers(5, 4, 3, 2, 1, 0), new Integers(64, 59, 56, 52, 47, 40), 120, 1, 1, stroke0, exp0, true);
        brevs_map.put("01u", bf0.remove());
        
        /*bf0.note(new Integers(1, 2, 3, 4, 5), new Integers(47, 54, 59, 62, 66), 120, 1, 1, stroke0, exp0, true);
        brevs_map.put("02d", bf0.remove());
        
        bf0.note(new Integers(5, 4, 3, 2, 1), new Integers(66, 62, 59, 54, 47), 120, 1, 1, stroke0, exp0, true);
        brevs_map.put("02u", bf0.remove());
        */
        /*bf0.note(new Integers(1, 2, 3, 4, 5), new Integers(47, 54, 59, 62, 66), 120, 1, 1, stroke0, exp0, true);
        brevs_map.put("03d", bf0.remove());
        
        bf0.note(new Integers(5, 4, 3, 2, 1), new Integers(66, 62, 59, 54, 47), 120, 1, 1, stroke0, exp0, true);
        brevs_map.put("03u", bf0.remove());
        
        bf0.note(new Integers(1, 2, 3, 4, 5), new Integers(47, 54, 59, 62, 66), 120, 1, 1, stroke0, exp0, true);
        brevs_map.put("04d", bf0.remove());
        
        bf0.note(new Integers(5, 4, 3, 2, 1), new Integers(66, 62, 59, 54, 47), 120, 1, 1, stroke0, exp0, true);
        brevs_map.put("04u", bf0.remove());*/
    }

    @Override
    protected void getLimaLusa(ArrayList<LimaLusa> lusa_list) {
        Envelope genv1 = new Envelope(new Punctum(1));
        genv1.ponoValue(100, new Punctum(1));
        genv1.ponoValue(120, new Punctum(0));
        Envelope genv2 = new Envelope(new Punctum(1));
        genv2.ponoValue(50, new Punctum(1));
        genv2.ponoValue(60, new Punctum(0));
        Envelope genv3 = new Envelope(new Punctum(1));
        genv3.ponoValue(20, new Punctum(1));
        genv3.ponoValue(30, new Punctum(0));
        //double cutoff1 = 20000;// i % 2 == 0?1000:20000;
        //double cutoff2 = 5000;// i % 2 == 0?1000:20000;
        boolean lpf = true;
        FilterInfo gfi1 = new FIRInfo(20000, lpf, true, new Aestimatio(1));
        FilterInfo gfi2 = new FIRInfo( 5000, lpf, true, new Aestimatio(1));
        int measure = 8;
        int track = 0;
        //double temp_beat = 500;
        for(int i = 0;i < measure;i++){
            //double m_temp = i * (temp_beat * 4);
            //String no = i % 2 == 0?"01":"02";
            String no = "01";
            int count = 0;
            double back = 0.010;
            Talea t = new Talea(i, 0);
            lusa_list.add(new LimaLusa(track, no + "d", t.shiftBeat(count++ * 0.25),        0.25, genv1, new Aestimatio(1), gfi1));
            lusa_list.add(new LimaLusa(track, no + "u", t.shiftBeat(count++ * 0.25 + back), 0.25, genv1, new Aestimatio(0.8), gfi1));
            lusa_list.add(new LimaLusa(track, no + "d", t.shiftBeat(count++ * 0.25),        0.25, genv3, new Aestimatio(0.5), gfi2));
            lusa_list.add(new LimaLusa(track, no + "u", t.shiftBeat(count++ * 0.25 + back), 0.25, genv2, new Aestimatio(0.8), gfi1));
            lusa_list.add(new LimaLusa(track, no + "d", t.shiftBeat(count++ * 0.25),        0.25, genv3, new Aestimatio(0.5), gfi2));
            lusa_list.add(new LimaLusa(track, no + "u", t.shiftBeat(count++ * 0.25 + back), 0.25, genv1, new Aestimatio(1), gfi1));
            lusa_list.add(new LimaLusa(track, no + "d", t.shiftBeat(count++ * 0.25),        0.25, genv3, new Aestimatio(0.5), gfi2));
            lusa_list.add(new LimaLusa(track, no + "u", t.shiftBeat(count++ * 0.25 + back), 0.25, genv3, new Aestimatio(0.5), gfi2));
            lusa_list.add(new LimaLusa(track, no + "d", t.shiftBeat(count++ * 0.25),        0.25, genv1, new Aestimatio(1.0), gfi1));
            lusa_list.add(new LimaLusa(track, no + "u", t.shiftBeat(count++ * 0.25 + back), 0.25, genv1, new Aestimatio(0.8), gfi1));
            lusa_list.add(new LimaLusa(track, no + "d", t.shiftBeat(count++ * 0.25),        0.25, genv3, new Aestimatio(0.5), gfi2));
            lusa_list.add(new LimaLusa(track, no + "u", t.shiftBeat(count++ * 0.25 + back), 0.25, genv2, new Aestimatio(0.8), gfi1));
            lusa_list.add(new LimaLusa(track, no + "d", t.shiftBeat(count++ * 0.25),        0.25, genv3, new Aestimatio(0.5), gfi2));
            lusa_list.add(new LimaLusa(track, no + "u", t.shiftBeat(count++ * 0.25 + back), 0.25, genv3, new Aestimatio(0.5), gfi2));
            lusa_list.add(new LimaLusa(track, no + "d", t.shiftBeat(count++ * 0.25),        0.25, genv1, new Aestimatio(1.0), gfi1));
            lusa_list.add(new LimaLusa(track, no + "u", t.shiftBeat(count++ * 0.25 + back), 0.25, genv3, new Aestimatio(0.5), gfi2));
        }
            //cns.addo(3000, new FIRFilter(new LectorLimam(lima2), cutoff, false, true, volume));
        
    }

    @Override
    protected void getTrackSettings(TrackSettings track_settings) {
        
    }

    @Override
    protected void getLegibilisLusa(Tempus pempus, ArrayList<LegibilisLusa> lusa_list) {
    }
    
}
