/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package la.clamor.opus;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import la.clamor.Consilium;
import la.clamor.ExceptioClamoris;
import la.clamor.Functiones;
import la.clamor.Mixtor.MixtorFixus;
import la.clamor.Oscillator;
import static la.clamor.OscillatorUtil.getPreset;
import la.clamor.Velocitas;
import la.clamor.Punctum;
import la.clamor.io.ScriptorWav;
import la.clamor.Temperamentum;
import la.clamor.forma.CadentesFormae;
import static la.clamor.opus.ConstantiaOperis.CT;
import la.clamor.opus.Taleae.Comes;
import la.clamor.opus.Taleae.Rapidus;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import origine_mundi.OmUtil;

/**
 *
 * @author minae.hiyamae
 */
public abstract class Mensa implements ConstantiaOperis {
    TreeMap<Integer, Oscillator> oscillatores;
    TreeMap<Integer, Consilium> consilia;
    MixtorFixus mixtor;
    Taleae taleae;
    String nomen;
    boolean creaturus;
    boolean lusurus;
    Causa talea_ab;
    Causa talea_ad;
    ArrayList<Integer> track_list;
    
    String sub_path;
    public Mensa(String nomen){
        this(nomen, true, false);
    }
    public Mensa(String nomen, boolean creaturus, boolean lusurus){
        this.nomen = nomen;
        this.creaturus = creaturus;
        this.lusurus = lusurus;
        oscillatores = new TreeMap<>();
        consilia     = new TreeMap<>();
        mixtor = new MixtorFixus();
        talea_ab = null;
        talea_ad = null;
        track_list = new ArrayList<>();
        sub_path = null;
        //Editor.ponoPrint(false);
    }
    protected void ponoSubPath(String sub_path){
        this.sub_path = sub_path;
    }
    public void ponoActiones(boolean creaturus, boolean lusurus){
        this.creaturus = creaturus;
        this.lusurus = lusurus;
    }
    public void ponoTrackList(int... tracks){
        if(!track_list.isEmpty()){
            track_list = new ArrayList<>();
        }
        track_list.addAll(Arrays.asList(ArrayUtils.toObject(tracks)));
    }
    public void ponoTaleamAb(int talea, double repenso){
        talea_ab = CT(talea, repenso);
    }
    public void ponoTaleamAd(int talea, double repenso){
        talea_ad = CT(talea, repenso);
    }
    public void ponoOscillator(int id, Punctum pan, String name){
        ponoOscillator(id, pan, name, null);
    }
    public void ponoOscillator(int id, Punctum pan, String name, CadentesFormae cf){
        Oscillator osc;
        try {
            osc = new Oscillator(getPreset(name));
        } catch (IOException ex) {
            throw new ExceptioClamoris("cannot find preset:" + name, ex);
        }
        //System.out.println("DEBUG:" + (fe == null?"null":fe.size()));
       // for(PositionesEffectoris pe:fe){
        //    pe.print();
        //}
        oscillatores.put(id, osc);
        consilia.putIfAbsent(id, new Consilium());
        mixtor.ponoLegibilem(id, cf == null?consilia.get(id):cf.capioLegibilis(consilia.get(id)));
        mixtor.ponoPan(id, pan);
    }
    //public void ponoOscillator(int id, Punctum pan, OscillatorSettings... array_iugorum){
    //    ponoOscillator(id, pan, new Oscillator(array_iugorum));
    //}
    public String capioNomen(){
        return nomen;
    }
    public void ponoNomen(String nomen){
        this.nomen = nomen;
    }
    protected abstract Comes[] capioComitis();
    protected abstract Rapidus[] capioRapidi();
    protected abstract void creo();
    protected void ludo(int id, int talea, double repenso, double diutius, double clevis, Velocitas velocitas){
        Causa c = CT(talea, repenso);
        if((talea_ab != null && ConstantiaOperis.compare(c, talea_ab) < 0) ||
           (talea_ad != null && ConstantiaOperis.compare(talea_ad, c) < 0) ||
           (!track_list.isEmpty() && !track_list.contains(id))){
            return;
        }
        if(!oscillatores.containsKey(id)){
            throw new IllegalArgumentException(String.format("Oscillator is unset for track(%s)", id));
        }
        consilia.get(id).addo(
                taleae.capioTempus(talea, repenso), 
                oscillatores.get(id).capioOscillationes(
                        new Punctum(Temperamentum.instance.capioHZ(clevis)), 
                        taleae.capioTempus(talea, repenso + diutius) - taleae.capioTempus(talea, repenso), 
                        velocitas));
    }

    @Before
    public abstract void initio();
    
    protected abstract void anteFacio();
    /**
     * main method for Mensa
     */
    @Test
    public void facio(){
        //File lima = null;
        File out_file = new File(OmUtil.getDirectory("sample" + (sub_path != null?sub_path + "/":"")), nomen + ".wav");
        LogFactory.getLog(getClass()).info("ante facio:initio");
        anteFacio();
        if(creaturus){
            LogFactory.getLog(getClass()).info("creo:initio");
            taleae = new Taleae(capioComitis(), capioRapidi());
            creo();
            //lima.getParentFile().mkdirs();
            ScriptorWav sl = new ScriptorWav(out_file);
            if(talea_ab != null){
                sl.ponoIndexAb(taleae.capioTempus(talea_ab.capioTaleam(), talea_ab.capioRepenso()));
            }
            if(talea_ad != null){
                sl.ponoIndexAd(taleae.capioTempus(talea_ad.capioTaleam(), talea_ad.capioRepenso()));
            }
            sl.scribo(mixtor, false);
            Logger.getLogger(getClass().getName()).log(Level.INFO, "SCRIPTUM EST:{0}", out_file.getAbsolutePath());
        }
        if(lusurus){
            LogFactory.getLog(getClass()).info("luso:initio");
            /*if(lima == null){
                lima = new File("C:/drive/doc/clamor/sample/" + (sub_path != null?sub_path + "/":"") + nomen + ".wav");
            }*/
            Functiones.ludoLimam(out_file);
        }
    }
    
    
}
