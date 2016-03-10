/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package origine_mundi.opus;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import la.clamor.Aestimatio;
import la.clamor.Constantia;
import static la.clamor.Functiones.capioHZ;
import la.clamor.io.LectorLimam;
import la.clamor.Envelope;
import la.clamor.deinde.bk.OscillatioDeprec;
import la.clamor.deinde.bk.PositionesDeprec;
import la.clamor.PositionesOscillationis;
import origine_mundi.deprec.sampler.PunctaTalearum;
import la.clamor.Punctum;
import la.clamor.io.ScriptorLimam;
import la.clamor.Talea;
import origine_mundi.deprec.MoraDeprec;
import com.mina.util.Integers;
import origine_mundi.MidiMachines;
import origine_mundi.OmUtil;
import origine_mundi.deprec.sampler.EffectorInfo.ChorusInfo;
import origine_mundi.deprec.FilterInfo;
import origine_mundi.deprec.FilterInfo.ThruInfo;
import origine_mundi.ludior.BrevFactory;
import origine_mundi.ludior.Brevs;
import origine_mundi.ludior.ChordStroke;
import origine_mundi.ludior.Expression;
import origine_mundi.ludior.Iunctum;
import origine_mundi.ludior.Tempus;
import origine_mundi.machine.D_110;
import origine_mundi.machine.K_01RW;
import origine_mundi.deprec.sampler.Desktop;
import origine_mundi.deprec.sampler.LegibilisLusa;
import origine_mundi.deprec.sampler.LimaLusa;

/**
 *
 * @author user
 */
public class Opus011 extends Desktop {
    static final int measure = 48;

    @Override
    protected void callDevices(MidiMachines midi_machines) {
        //midi_machines.put(0, MU500.instance(0));
        //midi_machines.put(1, D_110.instance());
        midi_machines.put(0, D_110.instance());//dummy
        midi_machines.put(1, D_110.instance());//dummy
        midi_machines.put(2, K_01RW.instance());//
    }

    @Override
    protected Tempus getTempus() {
        return new Tempus(
                new Tempus.Comes[]{}, 
                new Tempus.Rapidus[]{new Tempus.Rapidus(new Talea(), 110, true)});
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
        bf1.setLoco(new Talea(1, 0));
        
        bf1.note(new Integers(0, 1, 2, 3, 4, 5), new Integers(40, 47, 52, 56, 59, 64), 120, 1, 1, stroke0, exp0, true);
        brevs_map.put("01d", bf1.remove());
        
        bf1.note(new Integers(5, 4, 3, 2, 1, 0), new Integers(64, 59, 56, 52, 47, 40), 120, 1, 1, stroke0, exp0, true);
        brevs_map.put("01u", bf1.remove());
        
        bf1.note(new Integers(1, 2, 3, 4, 5), new Integers(47, 54, 59, 62, 66), 120, 1, 1, stroke0, exp0, true);
        brevs_map.put("02d", bf1.remove());
        
        bf1.note(new Integers(5, 4, 3, 2, 1), new Integers(66, 62, 59, 54, 47), 120, 1, 1, stroke0, exp0, true);
        brevs_map.put("02u", bf1.remove());
        
        bf1.note(new Integers(0, 1, 2, 3, 4, 5), new Integers(38, 45, 50, 54, 57, 62), 120, 1, 1, stroke0, exp0, true);
        brevs_map.put("03d", bf1.remove());
        
        bf1.note(new Integers(5, 4, 3, 2, 1, 0), new Integers(62, 57, 54, 50, 45, 38), 120, 1, 1, stroke0, exp0, true);
        brevs_map.put("03u", bf1.remove());
        
        bf1.note(new Integers(1, 2, 3, 4, 5), new Integers(45, 52, 57, 60, 64), 120, 1, 1, stroke0, exp0, true);
        brevs_map.put("04d", bf1.remove());
        
        bf1.note(new Integers(5, 4, 3, 2, 1), new Integers(64, 60, 57, 52, 45), 120, 1, 1, stroke0, exp0, true);
        brevs_map.put("04u", bf1.remove());

        BrevFactory bf2 = new BrevFactory(new Iunctum(2, 2, 0));
        bf2.setLoco(new Talea());
        int [] bass_notes = new int[]{40, 35, 38, 33};
        for(int i = 0;i < bass_notes.length;i++){
            bf2.note(bass_notes[i], 0x40, 1, 1, false);
            brevs_map.put("ba_" + i + "_l_40", bf2.remove());
            bf2.note(bass_notes[i], 0x78, 1, 1, false);
            brevs_map.put("ba_" + i + "_l_78", bf2.remove());
            bf2.note(bass_notes[i] + 12, 0x40, 1, 1, false);
            brevs_map.put("ba_" + i + "_h_40", bf2.remove());
            bf2.note(bass_notes[i] + 12, 0x78, 1, 1, false);
            brevs_map.put("ba_" + i + "_h_78", bf2.remove());
        }
    }

    @Override
    protected void getLimaLusa(ArrayList<LimaLusa> lusa_list) {
        Envelope env1 = new Envelope(new Punctum(1));
        Envelope env2 = new Envelope(new Punctum(1));
        env2.ponoPunctum(200, new Punctum(0.8));
        env2.ponoPunctum(400, new Punctum(0));
        FilterInfo fi1 = new ThruInfo();
        //double temp_beat = 500;
        double duration = 1;
        double back = 0.002;
        int track = 0;
        lusa_list.add(new LimaLusa(track, "b_120",   new Talea(0, 2.75 + back),    duration, env1, new Aestimatio(1), fi1));
        lusa_list.add(new LimaLusa(track, "s_120_d", new Talea(0, 2.98),           duration, env2, new Aestimatio(1), fi1));
        lusa_list.add(new LimaLusa(track, "h_120",   new Talea(0, 3.75 + back),    duration, env1, new Aestimatio(1), fi1));
        for(int i = 1;i <= measure;i++){
            lusa_list.add(new LimaLusa(track, "b_120", new Talea(i, 0),           duration, env1, new Aestimatio(1), fi1));
            lusa_list.add(new LimaLusa(track, "s_120", new Talea(i, 1),           duration, env2, new Aestimatio(1), fi1));
            lusa_list.add(new LimaLusa(track, "b_60",  new Talea(i, 1.75 + back), duration, env1, new Aestimatio(0.6), fi1));
            lusa_list.add(new LimaLusa(track, "b_120", new Talea(i, 2),           duration, env1, new Aestimatio(1), fi1));
            lusa_list.add(new LimaLusa(track, "s_120", new Talea(i, 3),           duration, env2, new Aestimatio(1), fi1));
            int count = 0;
            for(int j = 0;j < 4;j++){
                lusa_list.add(new LimaLusa(track, "h_120", new Talea(i, count++ * 0.25),        duration, env1, new Aestimatio(1), fi1));
                lusa_list.add(new LimaLusa(track, "h_80",  new Talea(i, count++ * 0.25 + back), duration, env1, new Aestimatio(0.6), fi1));
                lusa_list.add(new LimaLusa(track, "h_80",  new Talea(i, count++ * 0.25),        duration, env1, new Aestimatio(0.6), fi1));
                lusa_list.add(new LimaLusa(track, "h_95",  new Talea(i, count++ * 0.25 + back), duration, env1, new Aestimatio(0.75), fi1));
            }
        }
        Envelope genv1 = new Envelope(new Punctum(1));
        genv1.ponoPunctum(100, new Punctum(1));
        genv1.ponoPunctum(120, new Punctum(0));
        Envelope genv2 = new Envelope(new Punctum(1));
        genv2.ponoPunctum(50, new Punctum(1));
        genv2.ponoPunctum(60, new Punctum(0));
        Envelope genv3 = new Envelope(new Punctum(1));
        genv3.ponoPunctum(20, new Punctum(1));
        genv3.ponoPunctum(30, new Punctum(0));
        boolean lpf = true;
        FilterInfo gfi1 = new FilterInfo.FIRInfo(20000, lpf, true, new Aestimatio(1));
        FilterInfo gfi2 = new FilterInfo.FIRInfo( 5000, lpf, true, new Aestimatio(1));
        
        
        
        track = 1;
        for(int i = 1;i <= measure;i++){
            //double m_temp = i * (temp_beat * 4);
            //String no = i % 2 == 0?"01":"02";
            String no = (i - 1) % 4 == 0?"01":(i - 1) % 4 == 1?"02":(i - 1) % 4 == 2?"03":"04";
            int count = 0;
            lusa_list.add(new LimaLusa(track, no + "d", new Talea(i, count++ * 0.25),        0.25, genv1, new Aestimatio(1), gfi1));
            lusa_list.add(new LimaLusa(track, no + "u", new Talea(i, count++ * 0.25 + back), 0.25, genv1, new Aestimatio(0.8), gfi1));
            lusa_list.add(new LimaLusa(track, no + "d", new Talea(i, count++ * 0.25),        0.25, genv3, new Aestimatio(0.5), gfi2));
            lusa_list.add(new LimaLusa(track, no + "u", new Talea(i, count++ * 0.25 + back), 0.25, genv2, new Aestimatio(0.8), gfi1));
            lusa_list.add(new LimaLusa(track, no + "d", new Talea(i, count++ * 0.25),        0.25, genv3, new Aestimatio(0.5), gfi2));
            lusa_list.add(new LimaLusa(track, no + "u", new Talea(i, count++ * 0.25 + back), 0.25, genv1, new Aestimatio(1), gfi1));
            lusa_list.add(new LimaLusa(track, no + "d", new Talea(i, count++ * 0.25),        0.25, genv3, new Aestimatio(0.5), gfi2));
            lusa_list.add(new LimaLusa(track, no + "u", new Talea(i, count++ * 0.25 + back), 0.25, genv3, new Aestimatio(0.5), gfi2));
            lusa_list.add(new LimaLusa(track, no + "d", new Talea(i, count++ * 0.25),        0.25, genv1, new Aestimatio(1.0), gfi1));
            lusa_list.add(new LimaLusa(track, no + "u", new Talea(i, count++ * 0.25 + back), 0.25, genv1, new Aestimatio(0.8), gfi1));
            lusa_list.add(new LimaLusa(track, no + "d", new Talea(i, count++ * 0.25),        0.25, genv3, new Aestimatio(0.5), gfi2));
            lusa_list.add(new LimaLusa(track, no + "u", new Talea(i, count++ * 0.25 + back), 0.25, genv2, new Aestimatio(0.8), gfi1));
            lusa_list.add(new LimaLusa(track, no + "d", new Talea(i, count++ * 0.25),        0.25, genv3, new Aestimatio(0.5), gfi2));
            lusa_list.add(new LimaLusa(track, no + "u", new Talea(i, count++ * 0.25 + back), 0.25, genv3, new Aestimatio(0.5), gfi2));
            lusa_list.add(new LimaLusa(track, no + "d", new Talea(i, count++ * 0.25),        0.25, genv1, new Aestimatio(1.0), gfi1));
            lusa_list.add(new LimaLusa(track, no + "u", new Talea(i, count++ * 0.25 + back), 0.25, genv3, new Aestimatio(0.5), gfi2));
        }
    }

    @Override
    protected void getTrackSettings(TrackSettings track_settings) {
        TrackSetting pt0 = new TrackSetting(new Punctum(1));
        track_settings.put(0, pt0);
        
        TrackSetting pt1 = new TrackSetting(new Punctum(0.8), new ChorusInfo(new Punctum(0.3), new Punctum(6), new Punctum(0.5), new Punctum(0.5, -0.5)));
        pt1.putAmp(new Talea(33, 0), new Punctum(0.8));
        pt1.putAmp(new Talea(33, 0.5), new Punctum());
        pt1.putAmp(new Talea(36, 3.999), new Punctum());
        pt1.putAmp(new Talea(37, 0), new Punctum(0.8));
        track_settings.put(1, pt1);
        
        double c_org = 0.12;
        TrackSetting pt2 = new TrackSetting(new Punctum());
        pt2.putAmp(new Talea(4, 0), new Punctum());
        pt2.putAmp(new Talea(4, 1), new Punctum(c_org / 3d));
        pt2.putAmp(new Talea(5, 0), new Punctum(c_org));
        pt2.putAmp(new Talea(5, 1), new Punctum(c_org));
        pt2.putAmp(new Talea(5, 2), new Punctum());
        pt2.putAmp(new Talea(8, 0), new Punctum());
        pt2.putAmp(new Talea(8, 1), new Punctum(c_org / 3d));
        pt2.putAmp(new Talea(9, 0), new Punctum(c_org));
        pt2.putAmp(new Talea(9, 1), new Punctum(c_org));
        pt2.putAmp(new Talea(9, 2), new Punctum());
        pt2.putAmp(new Talea(12, 0), new Punctum());
        pt2.putAmp(new Talea(12, 1), new Punctum(c_org / 3d));
        pt2.putAmp(new Talea(13, 0), new Punctum(c_org));
        pt2.putAmp(new Talea(13, 1), new Punctum(c_org));
        pt2.putAmp(new Talea(13, 2), new Punctum());
        pt2.putAmp(new Talea(16, 0), new Punctum());
        pt2.putAmp(new Talea(16, 1), new Punctum(c_org / 3d));
        pt2.putAmp(new Talea(17, 0), new Punctum(c_org));
        pt2.putAmp(new Talea(33, 1), new Punctum(c_org));
        pt2.putAmp(new Talea(33, 2), new Punctum());
        track_settings.put(2, pt2);
        
        TrackSetting master = track_settings.getMaster();
        master.putAmp(new Talea(41, 0), new Punctum(1));
        master.putAmp(new Talea(49, 0), new Punctum(0));
        
    }
    @Override
    protected void initialize(InitialSettings initials) {
        initials.setAction(true, true, false, true);
        initials.setSkipLimae("b_120", "b_60", "s_120", "s_120_d", "h_120", "h_95", "h_80");
        initials.setSkipLimae("01d", "01u", "02d", "02u", "03d", "03u", "04d", "04u");
    }

    @Override
    protected void getLegibilisLusa(Tempus tempus, ArrayList<LegibilisLusa> lusa_list) {
        int[][] notes = new int[][]{
            new int[]{64, 71, 62, 69},
            new int[]{68, 74, 66, 72},
            new int[]{71, 66, 69, 64},
            new int[]{75, 69, 73, 67}
        };
        double[][] pan = new double[][]{
            new double[]{1.0, 0.6, 0.3, 0.0},
            new double[]{0.6, 0.3, 0.0, 1.0},
            new double[]{0.3, 0.0, 1.0, 0.6},
            new double[]{0.0, 1.0, 0.6, 0.3}
        };
        OscillatioDeprec.Oscillationes os = new OscillatioDeprec.Oscillationes(3);
        //os.add(o);
        for(int i = 0;i < 4;i++){
            PunctaTalearum pitch = new PunctaTalearum(new Punctum(capioHZ(notes[i][0], -5), capioHZ(notes[i][0], 5)));
            pitch.put(new Talea(0, 3), new Punctum(capioHZ(notes[i][0], -5), capioHZ(notes[i][0], 5)));
            pitch.put(new Talea(1, 0), new Punctum(capioHZ(notes[i][1], -5), capioHZ(notes[i][1], 5)));
            pitch.put(new Talea(1, 3), new Punctum(capioHZ(notes[i][1], -5), capioHZ(notes[i][1], 5)));
            pitch.put(new Talea(2, 0), new Punctum(capioHZ(notes[i][2], -5), capioHZ(notes[i][2], 5)));
            pitch.put(new Talea(2, 3), new Punctum(capioHZ(notes[i][2], -5), capioHZ(notes[i][2], 5)));
            pitch.put(new Talea(3, 0), new Punctum(capioHZ(notes[i][3], -5), capioHZ(notes[i][3], 5)));
            pitch.put(new Talea(3, 1.5), new Punctum(capioHZ(notes[i][3], -5), capioHZ(notes[i][3], 5)));
            pitch.put(new Talea(4, 0), new Punctum(capioHZ(notes[i][0], -5), capioHZ(notes[i][0], 5)));
            PunctaTalearum pans = new PunctaTalearum(new Punctum(pan[i][0], 1 - pan[i][0]));
            pans.put(new Talea(0, 1), new Punctum(pan[i][1], 1 - pan[i][1]));
            pans.put(new Talea(0, 2), new Punctum(pan[i][0], 1 - pan[i][0]));
            pans.put(new Talea(0, 3), new Punctum(pan[i][0], 1 - pan[i][0]));
            pans.put(new Talea(1, 0), new Punctum(pan[i][1], 1 - pan[i][1]));
            pans.put(new Talea(1, 1), new Punctum(pan[i][2], 1 - pan[i][2]));
            pans.put(new Talea(1, 2), new Punctum(pan[i][1], 1 - pan[i][1]));
            pans.put(new Talea(1, 3), new Punctum(pan[i][1], 1 - pan[i][1]));
            pans.put(new Talea(2, 0), new Punctum(pan[i][2], 1 - pan[i][2]));
            pans.put(new Talea(2, 1), new Punctum(pan[i][0], 1 - pan[i][0]));
            pans.put(new Talea(2, 2), new Punctum(pan[i][2], 1 - pan[i][2]));
            pans.put(new Talea(2, 3), new Punctum(pan[i][2], 1 - pan[i][2]));
            pans.put(new Talea(3, 0), new Punctum(pan[i][3], 1 - pan[i][3]));
            pans.put(new Talea(4, 0), new Punctum(pan[i][0], 1 - pan[i][0]));
            PunctaTalearum amp = new PunctaTalearum(new Punctum(1));
            amp.put(new Talea(4, 0), new Punctum(1));
            
            PositionesOscillationis p = new PositionesOscillationis(
                Constantia.Unda.DENT, 1, 0, 
                pitch.capioPositiones(tempus, true),
                amp.capioPositiones(tempus, true),
                new PositionesDeprec[]{
                    pans.capioPositiones(tempus, true),
                    pans.capioPositiones(tempus, true)
                }, 
                //null,
                new PositionesDeprec(false), new PositionesDeprec(false), new PositionesDeprec(false), new PositionesDeprec(false), new PositionesDeprec(true));

            os.add(new OscillatioDeprec(p));
        }
        double mora_temp = tempus.capioTempus(new Talea(0, 0.75));
        MoraDeprec mora = new MoraDeprec(os, new Punctum(mora_temp), new Punctum(2), new Punctum(0.5));
        File out_file = new File(OmUtil.getDirectory("sample"), "clamor1.lima");
        ScriptorLimam sl = new ScriptorLimam(out_file);
        while(mora.paratusSum()){
            sl.scribo(mora.lego());
        }
        sl.close();
        for(int i = 1;i <= measure;i += 4){
            lusa_list.add(new LegibilisLusa(2, new LectorLimam(out_file), new Talea(i, 0), 16, new Aestimatio(1)));
        }
        
    }
    
}
