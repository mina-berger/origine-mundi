/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor.opus;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import la.clamor.Consilium;
import la.clamor.Functiones;
import la.clamor.Instrument;
import la.clamor.Ludum;
import la.clamor.Mixtor;
import la.clamor.Velocitas;
import la.clamor.Punctum;
import la.clamor.io.ScriptorWav;
import la.clamor.forma.CadentesFormae;
import la.clamor.opus.Taleae.Comes;
import la.clamor.opus.Taleae.Rapidus;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import origine_mundi.OmUtil;
import static la.clamor.opus.ConstantiaOperis.CT;

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

    String sub_path;

    public Mensa() {
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
        sub_path = null;
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
        talea_ab = CT(talea, repenso);
    }

    public void ponoTaleamAd(int talea, double repenso) {
        talea_ad = CT(talea, repenso);
    }

    public void ponoInstrument(int id, Punctum pan, Instrument inst) {
        Mensa.this.ponoInstrument(id, pan, inst, null);
    }

    public void ponoInstrument(int id, Punctum pan, Instrument inst, CadentesFormae cf) {
        instruments.put(id, inst);
        consilia.putIfAbsent(id, new Consilium());
        mixtor.ponoLegibilem(id, cf == null ? consilia.get(id) : cf.capioLegibilis(consilia.get(id)));
        mixtor.ponoInitialPan(id, pan);
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

    protected abstract void ponoComitis(ArrayList<Comes> comites);

    protected abstract void ponoRapidi(ArrayList<Rapidus> rapidi);

    protected abstract void creo();

    protected void ludo(int id, int talea, double repenso, double diutius, double clevis, Velocitas velocitas) {
        if (!inRange(id, talea, repenso)) {
            return;
        }
        if (!instruments.containsKey(id)) {
            throw new IllegalArgumentException(String.format("Instrument is unset for track(%s)", id));
        }
        Ludum ludum;
        if (humanizers.containsKey(id)) {
            ludum = humanizers.get(id).humanize(taleae, talea, repenso, diutius, clevis, velocitas);
        } else {
            ludum = new Ludum(talea, repenso, clevis, diutius, velocitas);
        }
        //taleae.capioTempus(talea, repenso + diutius) - taleae.capioTempus(talea, repenso), 
        double temp
            = taleae.capioTempus(ludum.capioTalea(), ludum.capioRepenso() + ludum.capioDiuturnitas())
            - taleae.capioTempus(ludum.capioTalea(), ludum.capioRepenso());
        consilia.get(id).addo(taleae.capioTempus(ludum.capioTalea(), ludum.capioRepenso()),
            instruments.get(id).capioNotum(ludum.capioNote(), temp, ludum.capioVelocitas()));
        /*
        consilia.get(id).addo(taleae.capioTempus(talea, repenso),
            instruments.get(id).capioNotum(new Ludum(talea, repenso, clevis,
            taleae.capioTempus(talea, repenso + diutius) - taleae.capioTempus(talea, repenso),
            velocitas)));*/
    }

    public void ponoPan(int id, int talea, double repenso, Punctum pan) {
        if (!inRange(id, talea, repenso)) {
            return;
        }
        if (!instruments.containsKey(id)) {
            throw new IllegalArgumentException(String.format("Instrument is unset for track(%s)", id));
        }
        mixtor.ponoPan(id, taleae.capioTempus(talea, repenso), pan);
    }

    private boolean inRange(int id, int talea, double repenso) {
        Causa c = CT(talea, repenso);
        if (talea_ab != null && ConstantiaOperis.compare(c, talea_ab) < 0) {
            return false;
        } else if (talea_ad != null && ConstantiaOperis.compare(talea_ad, c) < 0) {
            return false;
        } else if (!track_list.isEmpty() && !track_list.contains(id)) {
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
        File out_file = new File(OmUtil.getDirectory("sample" + (sub_path != null ? sub_path + "/" : "")), capioNomen() + ".wav");
        LogFactory.getLog(getClass()).info("ante facio:initio");
        anteFacio();
        if (creaturus) {
            LogFactory.getLog(getClass()).info("creo:initio");
            ArrayList<Comes> comites = new ArrayList<>();
            ArrayList<Rapidus> rapidi = new ArrayList<>();
            ponoComitis(comites);
            ponoRapidi(rapidi);
            taleae = new Taleae(comites, rapidi);
            creo();
            //lima.getParentFile().mkdirs();
            ScriptorWav sl = new ScriptorWav(out_file);
            if (talea_ab != null) {
                sl.ponoIndexAb(taleae.capioTempus(talea_ab.capioTaleam(), talea_ab.capioRepenso()));
            }
            if (talea_ad != null) {
                sl.ponoIndexAd(taleae.capioTempus(talea_ad.capioTaleam(), talea_ad.capioRepenso()));
            }
            sl.scribo(mixtor, false);
            mixtor.close();
            Logger.getLogger(getClass().getName()).log(Level.INFO, "SCRIPTUM EST:{0}", out_file.getAbsolutePath());
        }
        if (lusurus) {
            LogFactory.getLog(getClass()).info("luso:initio");
            Functiones.ludoLimam(out_file);
        }
    }

}
