/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package origine_mundi.archive;

import java.io.File;
import java.util.TreeMap;
import java.util.TreeSet;
import la.clamor.Functiones;
import la.clamor.Instrument;
import la.clamor.Legibilis;
import la.clamor.Punctum;
import la.clamor.Velocitas;
import org.apache.commons.math3.util.FastMath;
import static origine_mundi.archive.ArchiveUtil.getNote;
import static origine_mundi.archive.ArchiveUtil.getVelocity;

/**
 *
 * @author mina
 */
public class ArchiveLudior extends TreeMap<Integer, TreeSet<Integer>> implements Instrument{

    File dir;
    long decay;
    String name;

    public ArchiveLudior(String machine, String sound, double temps_decay) {
        dir = new File(Functiones.getArchivePath() + machine + "/" + sound);
        if (!dir.exists()) {
            throw new IllegalArgumentException("no sound:" + machine + "/" + sound);
        }
        name = "archive_" + sound + "_" + sound;
        decay = Functiones.adPositio(temps_decay);
        init();
    }

    @Override
    public Legibilis capioNotum(int octave, double note, double diuturnitas, Velocitas velocitas){
        return capioNotum((double)octave * 12. + note, diuturnitas, velocitas);
    }
    @Override
    public Legibilis capioNotum(double note, double diuturnitas, Velocitas velocity) {
        return capioNotum(note, diuturnitas, velocity.capio(0));
    }
    public ArchiveNote capioNotum(double note, double diuturnitas, Punctum velocity) {
        int i_note = (int)FastMath.round(note);
        if(!containsKey(i_note)){
            throw new IllegalArgumentException("note inexists:" + i_note);
        }
        TreeSet<Integer> set = get(i_note);
        int i_velocity = (int) Math.max(0, Math.min(127, velocity.maxAbs().doubleValue() * 128));
        Integer f_velocity = set.ceiling(i_velocity);
        if(f_velocity == null){
            f_velocity = set.floor(i_velocity);
        }
        if(f_velocity == null){
            throw new IllegalArgumentException("note inexists:note=" + note + 
                ":velocity=" + velocity + "(" + i_velocity + ")");
        }
        String name = ArchiveUtil.getName(i_note, f_velocity);
        long length = Functiones.adPositio(diuturnitas);
        File file = new File(dir, name + ".wav");
        return new ArchiveNote(file, velocity, length, decay);
    }
    final public void init(){
        File[] files = dir.listFiles((File dir1, String name) -> name.toLowerCase().matches("^[0-9ab]{2}_[0-9a-f]{2}\\.wav"));
        for(File file:files){
            String name = file.getName();
            int note     = getNote(name);
            int velocity = getVelocity(name);
            if(!containsKey(note)){
                put(note, new TreeSet());
            }
            get(note).add(velocity);
        }
    }
    public static void main(String[] args){
        String name = "0b_3c.wav";
        System.out.println(name.toLowerCase().matches("^[0-9ab]{2}_[0-9a-f]{2}\\.wav"));
        
    }

    @Override
    public String getName() {
        return name;
    }

           
}
