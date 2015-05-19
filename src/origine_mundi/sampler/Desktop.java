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
import la.clamor.Punctum.Aestimatio;
import la.clamor.ScriptorWav;
import org.junit.Test;
import origine_mundi.MidiMachines;
import origine_mundi.OmUtil;
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
        this(true, true, true, true);
    }
    public Desktop(boolean midi, boolean limae, boolean mix, boolean ludum){
        skip_set = new TreeSet<>();
        setAction(midi, limae, mix, ludum);
    }
        
    protected abstract void callDevices(MidiMachines midi_machines);
    protected abstract Tempus getTempus();
    protected abstract void getBrevs(HashMap<String, Brevs> brevs_map);
    protected abstract void getLusa(ArrayList<LimaLusa> lusa_list);
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
        MidiMachines midi_machines = null;
        HashMap<String, Brevs> brevs_map = null;
        ArrayList<LimaLusa> lusa_list = null;
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
                sampler = new BrevsSampler(midi_machines, tempus, wav_file, brevs_map.get(key));
                sampler.start();
            }
        }
        if(!skip_set.contains(SKIP_LIMAE)){
            if(brevs_map == null){
                brevs_map = new HashMap<>();
                getBrevs(brevs_map);
            }
            for(String key:brevs_map.keySet()){
                if(brevs_map.get(key).containsNote()){
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
            lusa_list = new ArrayList<>();
            getLusa(lusa_list);

            Consilium cns = new Consilium();
            for(LimaLusa lusa:lusa_list){
                double head = tempus.capioTempus(lusa.getTalea(), lusa.getBeat());
                double tail = tempus.capioTempus(lusa.getTalea(), lusa.getBeat() + lusa.getDuration());
                cns.addo(
                        head, 
                        FilterInfo.getFilter(
                            new EnvelopeFilter(
                                new LectorLimam(new File(dir, lusa.getKey() + ".lima")), 
                                lusa.getEnvelope(), 
                                tail - head, 
                                lusa.getVolume()), 
                            lusa.getFilterInfo()));
            }

            ScriptorWav sw = new ScriptorWav(out_file);
            sw.scribo(cns, false);
        }
        
        if(!skip_set.contains(SKIP_LUDUM)){
            Functiones.ludoLimam(out_file);
        }
                
    }
    
}
