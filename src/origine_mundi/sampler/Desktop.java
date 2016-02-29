/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package origine_mundi.sampler;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeSet;
import la.clamor.Consilium;
import la.clamor.EnvelopeFilter;
import la.clamor.Functiones;
import la.clamor.io.FunctionesLimae;
import la.clamor.io.LectorLimam;
import la.clamor.Aestimatio;
import la.clamor.Legibilis;
import la.clamor.PunctaTalearum;
import la.clamor.Punctum;
import la.clamor.io.ScriptorWav;
import la.clamor.Talea;
import org.junit.Test;
import origine_mundi.MidiMachines;
import origine_mundi.OmException;
import origine_mundi.OmUtil;
import origine_mundi.ProcessorInfo;
import origine_mundi.deprec.FilterInfo;
import origine_mundi.ludior.Brevs;
import origine_mundi.ludior.Tempus;

/**
 *
 * @author user
 */
public abstract class Desktop {
    private final int ACTION_MIDI  = 0;
    private final int ACTION_LIMAE = 1;
    private final int ACTION_MIX   = 2;
    private final int ACTION_LUDUM = 3;
    private File sample_dir;
    public Desktop(){
        this(OmUtil.getDirectory("sample"));
    }
    public Desktop(File dir){
        sample_dir = dir;
    }
        
    protected abstract void initialize(InitialSettings initials);
    protected abstract void callDevices(MidiMachines midi_machines);
    protected abstract Tempus getTempus();
    protected abstract void getBrevs(HashMap<String, Brevs> brevs_map);
    protected abstract void getLimaLusa(ArrayList<LimaLusa> lusa_list);
    protected abstract void getLegibilisLusa(Tempus tempus, ArrayList<LegibilisLusa> lusa_list);
    protected abstract void getTrackSettings(TrackSettings track_settings);
    @Test
    public void main(){
        InitialSettings initials = new InitialSettings();
        initialize(initials);
        MidiMachines midi_machines = null;
        HashMap<String, Brevs> brevs_map = null;
        ArrayList<LimaLusa> lima_lusa_list = null;
        ArrayList<LegibilisLusa> legi_lusa_list = null;
        Tempus tempus = getTempus();
        File out_file = new File(OmUtil.getDirectory("opus"), getClass().getSimpleName() + ".wav");
        if(initials.action(ACTION_MIDI)){
            midi_machines = new MidiMachines();
            callDevices(midi_machines);
            brevs_map = new HashMap<>();
            getBrevs(brevs_map);
        
            //sampling
            BrevsSampler sampler;
            for(String key:brevs_map.keySet()){
                if(initials.skipLima(key)){
                    System.out.println("MIDI:skip:" + key);
                    continue;
                }
                Brevs brevs = brevs_map.get(key);
                File wav_file = brevs.containsNote()?new File(sample_dir, key + ".wav"):null;
                try{
                    sampler = new BrevsSampler(midi_machines, tempus, wav_file, brevs_map.get(key));
                    sampler.start();
                }catch(Exception e){
                    e.printStackTrace();
                    throw new OmException("faile to sample:" + key, e);
                }
            }
        }
        if(initials.action(ACTION_LIMAE)){
            if(brevs_map == null){
                brevs_map = new HashMap<>();
                getBrevs(brevs_map);
            }
            for(String key:brevs_map.keySet()){
                if(initials.skipLima(key)){
                    System.out.println("LIMAE:skip:" + key);
                    continue;
                }
                if(!brevs_map.get(key).containsNote()){
                    continue;
                }
                File wav_file = new File(sample_dir, key + ".wav");
                File lima     = new File(sample_dir, key + ".lima");
                //System.out.println("1:" + lima.exists());
                FunctionesLimae.facioLimam(wav_file, lima, new Aestimatio(1), false, false);
                //System.out.println("2:" + lima.exists() + ":" + lima.length());
                FunctionesLimae.trim(lima, new Aestimatio(0.01));
                //System.out.println("3:" + lima.exists() + ":" + lima.length());
            }
        }
        
        if(initials.action(ACTION_MIX)){
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
                                new LectorLimam(new File(sample_dir, lusa.getKey() + ".lima")), 
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
        
        if(initials.action(ACTION_LUDUM)){
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
    public class InitialSettings {
        TreeSet<Integer> action_set;
        TreeSet<String>  skip_limae;
        public InitialSettings(){
            action_set = new TreeSet<>();
            action_set.add(ACTION_MIDI);
            action_set.add(ACTION_LIMAE);
            action_set.add(ACTION_MIX);
            action_set.add(ACTION_LUDUM);
            skip_limae = new TreeSet<>();
        }
        public boolean skipLima(String lima){
            return skip_limae.contains(lima);
        }
        public void setSkipLimae(String... limae){
            skip_limae.addAll(Arrays.asList(limae));
        }
        public void setAction(boolean midi, boolean limae, boolean mix, boolean ludum){
            if(midi){
                action_set.add(ACTION_MIDI);
            }else{
                action_set.remove(ACTION_MIDI);
            }
            if(limae){
                action_set.add(ACTION_LIMAE);
            }else{
                action_set.remove(ACTION_LIMAE);
            }
            if(mix){
                action_set.add(ACTION_MIX);
            }else{
                action_set.remove(ACTION_MIX);
            }
            if(ludum){
                action_set.add(ACTION_LUDUM);
            }else{
                action_set.remove(ACTION_LUDUM);
            }
        }
        public boolean action(int action){
            return action_set.contains(action);
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
