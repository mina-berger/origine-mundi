/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor.opus;

import com.mina.util.Doubles;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import la.clamor.Cinctum;
import la.clamor.Consilium;
import la.clamor.Functiones;
import la.clamor.Instrument;
import la.clamor.Legibilis;
import la.clamor.ludum.Ludum;
import la.clamor.Mixtor;
import la.clamor.Vel;
import la.clamor.Punctum;
import la.clamor.io.ScriptorWav;
import la.clamor.forma.CadentesFormae;
import la.clamor.forma.Forma;
import la.clamor.forma.FormaNominata;
import la.clamor.io.IOUtil;
import la.clamor.opus.Taleae.Comes;
import la.clamor.opus.Taleae.Rapidus;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author minae.hiyamae
 */
public abstract class Mensa implements ConstantiaOperis {

    TreeMap<Integer, Instrument> instruments;
    TreeMap<Integer, Consilium> consilia;
    TreeMap<Integer, Humanizer> humanizers;
    Mixtor mixtor;
    Taleae taleae;
    String nomen;
    boolean creaturus;
    boolean lusurus;
    Causa talea_ab;
    Causa talea_ad;
    ArrayList<Integer> track_list;
    HashMap<String, Forma> formae_nominatae;
    CadentesFormae master_cf;

    String sub_path;

    private Mensa() {
        this(null, true, false);
    }

    public Mensa(boolean creaturus, boolean lusurus) {
        this(null, creaturus, lusurus);
    }

    public Mensa(String nomen, boolean creaturus, boolean lusurus) {
        this.nomen = nomen;
        this.creaturus = creaturus;
        this.lusurus = lusurus;
        instruments = new TreeMap<>();
        consilia = new TreeMap<>();
        humanizers = new TreeMap<>();
        mixtor = new Mixtor();
        talea_ab = null;
        talea_ad = null;
        track_list = new ArrayList<>();
        formae_nominatae = new HashMap<>();
        sub_path = null;
        master_cf = null;
        //Editor.ponoPrint(false);
    }

    protected void ponoSubPath(String sub_path) {
        this.sub_path = sub_path;
    }

    public void ponoActiones(boolean creaturus, boolean lusurus) {
        this.creaturus = creaturus;
        this.lusurus = lusurus;
    }

    public void ponoTrackList(int... tracks) {
        if (!track_list.isEmpty()) {
            track_list = new ArrayList<>();
        }
        track_list.addAll(Arrays.asList(ArrayUtils.toObject(tracks)));
    }

    public void ponoTaleamAb(int talea, double repenso) {
        talea_ab = ConstantiaOperis.CT(talea, repenso);
    }

    public void ponoTaleamAd(int talea, double repenso) {
        talea_ad = ConstantiaOperis.CT(talea, repenso);
    }

    public void ponoInstrument(int id, Punctum level, Cinctum pan, Instrument inst) {
        ponoInstrument(id, level, pan, inst, null);
    }

    public void ponoNoInstrument(int id, Punctum level, Cinctum pan, CadentesFormae cf) {
        ponoInstrument(id, level, pan, null, cf);
    }

    public void ponoInstrument(int id, Punctum level, Cinctum pan, Instrument inst, CadentesFormae cf) {
        if (inst != null) {
            instruments.put(id, inst);
        }
        consilia.putIfAbsent(id, new Consilium());
        mixtor.ponoLegibilem(id, cf == null ? consilia.get(id) : cf.capioLegibilis(consilia.get(id)));
        mixtor.ponoInitialLevel(id, level);
        mixtor.ponoInitialPan(id, pan);
        for (FormaNominata forma_nominata : cf.capioNominatas()) {
            String _nomen = forma_nominata.capioNomen();
            if(formae_nominatae.containsKey(_nomen)){
                throw new IllegalArgumentException("forma iam nominata est:" + _nomen);
            }
            formae_nominatae.put(_nomen, forma_nominata.capioFormam());
        }
    }
    public void ponoFormae(String name, int talea, double repenso, int index, Punctum punctum){
        if(!formae_nominatae.containsKey(name)){
            throw new IllegalArgumentException("forma abest:" + name);
        }
        formae_nominatae.get(name).ponoPunctum(index, capioTempus(talea, repenso), punctum);
        
    }

    public void ponoHumanizer(Humanizer humanizer, int... ids) {
        for (int id : ids) {
            humanizers.put(id, humanizer);
        }
    }

    public String capioNomen() {
        return nomen == null ? getClass().getSimpleName() : nomen;
    }

    public void ponoNomen(String nomen) {
        this.nomen = nomen;
    }

    public double tempus(int talea, double repenso) {
        return taleae.capioTempus(talea, repenso);
    }

    public double diu(int talea1, double repenso1, int talea2, double repenso2) {
        return taleae.capioTempus(talea2, repenso2) - taleae.capioTempus(talea1, repenso1);
    }

    protected abstract void ponoComitis(ArrayList<Comes> comites);

    protected abstract void ponoRapidi(ArrayList<Rapidus> rapidi);

    protected abstract void creo();

    protected void ludo(int id, int talea, double repenso, double diutius, Doubles claves, Vel velocitas) {
        for (double clavis : claves) {
            ludo(id, talea, repenso, diutius, clavis, velocitas);
        }
    }

    protected void ludo(int id, int talea, double repenso, double diutius, double clavis, Vel velocitas) {
        if (!inRange(id, talea, repenso)) {
            return;
        }
        if (!instruments.containsKey(id)) {
            throw new IllegalArgumentException(String.format("Instrument is unset for track(%s)", id));
        }
        Ludum ludum;
        if (humanizers.containsKey(id)) {
            ludum = humanizers.get(id).humanize(taleae, talea, repenso, diutius, clavis, velocitas);
        } else {
            ludum = new Ludum(talea, repenso, diutius, clavis, velocitas);
        }

        //taleae.capioTempus(talea, repenso + diutius) - taleae.capioTempus(talea, repenso), 
        double temp
                = taleae.capioTempus(ludum.talea(), ludum.repenso() + ludum.diuturnitas())
                - taleae.capioTempus(ludum.talea(), ludum.repenso());
        consilia.get(id).addo(taleae.capioTempus(ludum.talea(), ludum.repenso()),
                instruments.get(id).capioNotum(ludum.clavis(), temp, ludum.velocitas()));
    }

    public void sono(int id, int talea, double repenso, Legibilis legibilis) {
        consilia.get(id).addo(taleae.capioTempus(talea, repenso), legibilis);
    }

    public double capioTempus(int talea, double repenso) {
        return taleae.capioTempus(talea, repenso);
    }

    public void ponoMasterLevel(int talea, double repenso, Punctum level) {
        if (!inRange(null, talea, repenso)) {
            return;
        }
        mixtor.ponoMasterLevel(taleae.capioTempus(talea, repenso), level);
    }
    public void ponoMasterFormas(Forma... formas){
        this.master_cf = new CadentesFormae(formas);
    }

    public void ponoLevel(int id, int talea, double repenso, Punctum level) {
        if (!inRange(id, talea, repenso)) {
            return;
        }
        if (!instruments.containsKey(id)) {
            throw new IllegalArgumentException(String.format("Instrument is unset for track(%s)", id));
        }
        mixtor.ponoLevel(id, taleae.capioTempus(talea, repenso), level);
    }

    public void ponoPan(int id, int talea, double repenso, Cinctum pan) {
        if (!inRange(id, talea, repenso)) {
            return;
        }
        //if (!instruments.containsKey(id)) {
        //    throw new IllegalArgumentException(String.format("Instrument is unset for track(%s)", id));
        //}
        mixtor.ponoPan(id, taleae.capioTempus(talea, repenso), pan);
    }

    private boolean inRange(Integer id, int talea, double repenso) {
        Causa c = ConstantiaOperis.CT(talea, repenso);
        if (talea_ab != null && ConstantiaOperis.compare(c, talea_ab) < 0) {
            return false;
        } else if (talea_ad != null && ConstantiaOperis.compare(talea_ad, c) < 0) {
            return false;
        } else if (id != null && !track_list.isEmpty() && !track_list.contains(id)) {
            return false;
        }
        return true;
    }

    @Before
    public abstract void initio();

    protected abstract void anteFacio();

    /**
     * main method for Mensa
     */
    @Test
    public void facio() {
        //File lima = null;
        IOUtil.clearTempFiles();
        File out_file = new File(IOUtil.getDirectory("opus" + (sub_path != null ? sub_path + "/" : "")), capioNomen() + ".wav");
        if (creaturus) {
            ArrayList<Comes> comites = new ArrayList<>();
            ArrayList<Rapidus> rapidi = new ArrayList<>();
            ponoComitis(comites);
            ponoRapidi(rapidi);
            taleae = new Taleae(comites, rapidi);
            LogFactory.getLog(getClass()).info("ante facio:initio");
            anteFacio();
            LogFactory.getLog(getClass()).info("creo:initio");
            creo();
            //lima.getParentFile().mkdirs();
            ScriptorWav sl = new ScriptorWav(out_file);
            if (talea_ab != null) {
                sl.ponoIndexAb(taleae.capioTempus(talea_ab.capioTaleam(), talea_ab.capioRepenso()));
            }
            if (talea_ad != null) {
                sl.ponoIndexAd(taleae.capioTempus(talea_ad.capioTaleam(), talea_ad.capioRepenso()));
            }
            //master forma
            Legibilis master_out = master_cf.capioLegibilis(mixtor);
            sl.scribo(master_out, false);
            master_out.close();
            
            Logger.getLogger(getClass().getName()).log(Level.INFO, "SCRIPTUM EST:{0}", out_file.getAbsolutePath());
        }
        if (lusurus) {
            LogFactory.getLog(getClass()).info("luso:initio");
            Functiones.ludoLimam(out_file);
        }
    }

}
