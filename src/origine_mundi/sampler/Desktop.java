/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package origine_mundi.sampler;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;
import la.clamor.Consilium;
import la.clamor.EnvelopeFilter;
import la.clamor.Functiones;
import la.clamor.FunctionesLimae;
import la.clamor.LectorLimam;
import la.clamor.Aestimatio;
import la.clamor.Legibilis;
import la.clamor.PunctaTalearum;
import la.clamor.Punctum;
import la.clamor.ScriptorWav;
import la.clamor.Talea;
import org.junit.Test;
import origine_mundi.MidiMachines;
import origine_mundi.OmException;
import origine_mundi.OmUtil;
import origine_mundi.ProcessorInfo;
import origine_mundi.effector.EffectorInfo;
import origine_mundi.filter.FilterInfo;
import origine_mundi.ludior.Brevs;
import origine_mundi.ludior.Tempus;

/**
 *
 * @author user
 */
public abstract class Desktop {
    TreeSet<Integer> skip_set;
    private final int SKIP_MIDI  = 0;
    private final int SKIP_LIMAE = 1;
    private final int SKIP_MIX   = 2;
    private final int SKIP_LUDUM = 3;
    public Desktop(){
    //    this(true, true, true, true);
    //}
    //public Desktop(boolean midi, boolean limae, boolean mix, boolean ludum){
        skip_set = new TreeSet<>();
    //    setAction(midi, limae, mix, ludum);
    }
        
    protected abstract void initialize();
    protected abstract void callDevices(MidiMachines midi_machines);
    protected abstract Tempus getTempus();
    protected abstract void getBrevs(HashMap<String, Brevs> brevs_map);
    protected abstract void getLimaLusa(ArrayList<LimaLusa> lusa_list);
    protected abstract void getLegibilisLusa(Tempus tempus, ArrayList<LegibilisLusa> lusa_list);
    protected abstract void getTrackSettings(TrackSettings track_settings);
    protected final void setAction(boolean midi, boolean limae, boolean mix, boolean ludum){
        if(midi){
            skip_set.remove(SKIP_MIDI);
        }else{
            skip_set.add(SKIP_MIDI);
        }
        if(limae){
            skip_set.remove(SKIP_LIMAE);
        }else{
            skip_set.add(SKIP_LIMAE);
        }
        if(mix){
            skip_set.remove(SKIP_MIX);
        }else{
            skip_set.add(SKIP_MIX);
        }
        if(ludum){
            skip_set.remove(SKIP_LUDUM);
        }else{
            skip_set.add(SKIP_LUDUM);
        }
    }
    @Test
    public void main(){
        initialize();
        MidiMachines midi_machines = null;
        HashMap<String, Brevs> brevs_map = null;
        ArrayList<LimaLusa> lima_lusa_list = null;
        ArrayList<LegibilisLusa> legi_lusa_list = null;
        Tempus tempus = getTempus();
        File dir = OmUtil.getDirectory("sample");
        File out_file = new File(OmUtil.getDirectory("opus"), getClass().getSimpleName() + ".wav");
        if(!skip_set.contains(SKIP_MIDI)){
            midi_machines = new MidiMachines();
            callDevices(midi_machines);
            brevs_map = new HashMap<>();
            getBrevs(brevs_map);
        
            //sampling
            BrevsSampler sampler;
            for(String key:brevs_map.keySet()){
                Brevs brevs = brevs_map.get(key);
                File wav_file = brevs.containsNote()?new File(dir, key + ".wav"):null;
                try{
                    sampler = new BrevsSampler(midi_machines, tempus, wav_file, brevs_map.get(key));
                    sampler.start();
                }catch(Exception e){
                    e.printStackTrace();
                    throw new OmException("faile to sample:" + key, e);
                }
            }
        }
        if(!skip_set.contains(SKIP_LIMAE)){
            if(brevs_map == null){
                brevs_map = new HashMap<>();
                getBrevs(brevs_map);
            }
            for(String key:brevs_map.keySet()){
                if(!brevs_map.get(key).containsNote()){
                    continue;
                }
                File wav_file = new File(dir, key + ".wav");
                File lima     = new File(dir, key + ".lima");
                //System.out.println("1:" + lima.exists());
                FunctionesLimae.facioLimam(wav_file, lima, new Aestimatio(1), false);
                //System.out.println("2:" + lima.exists() + ":" + lima.length());
                FunctionesLimae.trim(lima, new Aestimatio(0.01));
                //System.out.println("3:" + lima.exists() + ":" + lima.length());
            }
        }
        
        if(!skip_set.contains(SKIP_MIX)){
            Consilia cns = new Consilia();
            
            lima_lusa_list = new ArrayList<>();
            getLimaLusa(lima_lusa_list);

            for(LimaLusa lusa:lima_lusa_list){
                double head = tempus.capioTempus(lusa.getTalea());
                double tail = tempus.capioTempus(lusa.getTermina());
                cns.getConcilia(lusa.getTrack()).addo(
                        head, 
                        getProcessor(
                            new EnvelopeFilter(
                                new LectorLimam(new File(dir, lusa.getKey() + ".lima")), 
                                lusa.getEnvelope(), 
                                tail - head, 
                                lusa.getVolume()), 
                            lusa.getProcessorInfo()));
            }
            legi_lusa_list = new ArrayList<>();
            getLegibilisLusa(tempus, legi_lusa_list);
            for(LegibilisLusa lusa:legi_lusa_list){
                double head = tempus.capioTempus(lusa.getTalea());
                double tail = tempus.capioTempus(lusa.getTermina());
                cns.getConcilia(lusa.getTrack()).addo(head, new EnvelopeFilter(lusa.getLegibilis(), null, tail - head, lusa.getVolume()));
            }
            
            TrackSettings track_settings = new TrackSettings();
            getTrackSettings(track_settings);
            Consilium csm = new Consilium();
            for(Integer track:cns.keySet()){
                Legibilis legibilis = cns.getConcilia(track);
                Consilium t_csm = cns.getConcilia(track);
                if(track_settings.containsKey(track)){
                    System.out.println("track setting found" + track);
                    TrackSetting track_setting = track_settings.get(track);
                    t_csm.setPositiones(track_setting.getAmp().capioPositiones(tempus, true));
                    legibilis = getProcessor(legibilis, track_setting.getProcessorInfoArray());
                }
                csm.addo(0, legibilis);
            }
            
            csm.setPositiones(track_settings.getMaster().getAmp().capioPositiones(tempus, true));
            Legibilis master = getProcessor(csm, track_settings.getMaster().getProcessorInfoArray());

            ScriptorWav sw = new ScriptorWav(out_file);
            sw.scribo(master, false);
        }
        
        if(!skip_set.contains(SKIP_LUDUM)){
            Functiones.ludoLimam(out_file);
        }
                
    }
    class Consilia extends HashMap<Integer, Consilium>{
        Consilium getConcilia(int track){
            if(!containsKey(track)){
                put(track, new Consilium());
            }
            return get(track);
        }
    }
    public class TrackSettings extends HashMap<Integer, TrackSetting>{
        TrackSetting master;
        TrackSettings(){
            master = new TrackSetting(new Punctum(1));
        }
        public TrackSetting getMaster(){
            return master;
        }
        
    }
    public static class TrackSetting {
        private PunctaTalearum amp;
        private ProcessorInfo[] infos;
        public TrackSetting(Punctum initial_amp, ProcessorInfo... infos){
            amp = new PunctaTalearum(initial_amp);
            this.infos = infos;
        }
        public void putAmp(Talea talea, Punctum punctum){
            amp.put(talea, punctum);
        }
        public PunctaTalearum getAmp(){
            return amp;
        }
        public ProcessorInfo[] getProcessorInfoArray(){
            return infos;
        }
        
        
    }
    public static Legibilis getProcessor(Legibilis legibilis, ProcessorInfo[] infos){
        Legibilis ret = legibilis;
        for(ProcessorInfo info:infos){
            if(info instanceof FilterInfo){
                ret = FilterInfo.getFilter(legibilis, (FilterInfo)info);
            }else if(info instanceof EffectorInfo){
                ret = EffectorInfo.getEffector(legibilis, (EffectorInfo)info);
            }else{
                throw new OmException("unknown info:" + info.getClass().getName());
            }
        }
        return ret;
    }
    
    
}
