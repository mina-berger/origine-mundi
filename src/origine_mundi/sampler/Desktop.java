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
    MidiMachines midi_machines;
    HashMap<String, Brevs> brevs_map;
    ArrayList<LimaLusa> lusa_list;
    TreeSet<Integer> skip_set;
    private final int SKIP_MIDI = 0;
    public Desktop(){
        skip_set = new TreeSet<>();
    }
        
    protected abstract void callDevices(MidiMachines midi_machines);
    protected abstract Tempus getTempus();
    protected abstract void getBrevs(HashMap<String, Brevs> brevs_map);
    protected abstract void getLusa(ArrayList<LimaLusa> lusa_list);
    protected void setSkips(boolean midi_record, boolean k){
        if(midi_record){
            skip_set.add(SKIP_MIDI);
        }
    }
    @Test
    public void main(){
        Tempus tempus = getTempus();
        File dir = OmUtil.getDirectory("sample");
        if(!skip_set.contains(SKIP_MIDI)){
            midi_machines = new MidiMachines();
            callDevices(midi_machines);
            brevs_map = new HashMap<>();
            getBrevs(brevs_map);
        
            //sampling
            BrevsSampler sampler;
            for(String key:brevs_map.keySet()){
                File wav_file = new File(dir, key + ".wav");
                File lima     = new File(dir, key + ".lima");
                sampler = new BrevsSampler(midi_machines, tempus, wav_file, brevs_map.get(key));
                sampler.record();
                FunctionesLimae.facioLimam(wav_file, lima, new Aestimatio(1), false);
                FunctionesLimae.trim(lima, new Aestimatio(0.005));
            }
        }
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
        File out_file = new File(OmUtil.getDirectory("opus"), getClass().getSimpleName() + ".wav");
        ScriptorWav sw = new ScriptorWav(out_file);
        sw.scribo(cns, false);
        Functiones.ludoLimam(out_file);
    }
    
}
