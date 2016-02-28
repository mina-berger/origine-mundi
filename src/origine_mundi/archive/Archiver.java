/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package origine_mundi.archive;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import la.clamor.Talea;
import origine_mundi.MidiMachine;
import origine_mundi.MidiMachines;
import origine_mundi.ProgramValue;
import origine_mundi.ludior.BrevFactory;
import origine_mundi.ludior.Brevs;
import origine_mundi.ludior.Iunctum;
import origine_mundi.ludior.Tempus;
import origine_mundi.sampler.Desktop;
import origine_mundi.sampler.LegibilisLusa;
import origine_mundi.sampler.LimaLusa;

/**
 *
 * @author mina
 */
public class Archiver extends Desktop {
    MidiMachine machine;
    ProgramValue program_value;
    public Archiver(File dir, MidiMachine machine, ProgramValue program_value){
        super(dir);
        this.machine = machine;
        this.program_value = program_value;
    }
    @Override
    protected void initialize(InitialSettings initials) {
        initials.setAction(true, true, false, false);
    }

    @Override
    protected void callDevices(MidiMachines midi_machines) {
        midi_machines.put(0, machine);
    }

    @Override
    protected Tempus getTempus() {
        return new Tempus(
                new Tempus.Comes[]{}, 
                new Tempus.Rapidus[]{new Tempus.Rapidus(new Talea(), 30, true)});
    }

    @Override
    protected void getBrevs(HashMap<String, Brevs> brevs_map) {
        BrevFactory bf = new BrevFactory(new Iunctum(0, 0, 9));
        bf.setLoco(new Talea());
        bf.program(program_value);
        brevs_map.put("init", bf.remove());
        
        //int note, int velocity, double duration, double rate, boolean shift
        int note = 36;
        int velocity = 120;
        bf.note(note, velocity, 8, 1, false);
        brevs_map.put(toDodeciString(note) + "_" + toHexString(velocity), bf.remove());
        
    }

    @Override
    protected void getLimaLusa(ArrayList<LimaLusa> lusa_list) {
    }

    @Override
    protected void getLegibilisLusa(Tempus tempus, ArrayList<LegibilisLusa> lusa_list) {
    }

    @Override
    protected void getTrackSettings(TrackSettings track_settings) {
    }
    private static String toHexString(int i){
        String str = Integer.toHexString(i);
        if(str.length() < 2){
            return "0" + str;
        }
        return str;
    }
    private static String toDodeciString(int i){
        return Integer.toHexString(i / 12) + Integer.toHexString(i % 12);
    }
    
}
