/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor;

import com.mina.util.Mjson;
import java.util.ArrayList;
import java.util.TreeMap;
import static la.clamor.Constantia.CHANNEL;
import com.mina.util.Mjson.MjsonElement;
import com.mina.util.Mjson.MjsonList;

/**
 *
 * @author mina
 */
public class OscillatorSettings {

    Constantia.Unda unda;
    double volume;
    double feedback;
    TreeMap<Double, Iugum> map_primo_fco;
    TreeMap<Double, Iugum> map_primo_fca;
    TreeMap<Double, Iugum> map_primo_qco;
    TreeMap<Double, Iugum> map_primo_qca;
    ArrayList<TreeMap<Double, Iugum>> map_primo_pco;
    ArrayList<TreeMap<Double, Iugum>> map_primo_pca;
    TreeMap<Double, Iugum> map_vco_fco;
    TreeMap<Double, Iugum> map_vco_fca;
    TreeMap<Double, Iugum> map_vco_qco;
    TreeMap<Double, Iugum> map_vco_qca;
    TreeMap<Double, Iugum> map_vca_fco;
    TreeMap<Double, Iugum> map_vca_fca;
    TreeMap<Double, Iugum> map_vca_qco;
    TreeMap<Double, Iugum> map_vca_qca;
    TreeMap<Double, Iugum> map_fb_qco;
    TreeMap<Double, Iugum> map_fb_qca;
    Double fixa_frequentia;
    OscillatorSettings[] pueros;

    public OscillatorSettings(Mjson json) {
        this(Constantia.Unda.valueOf(json.getString("unda", true).toUpperCase()),
            json.getDouble("volume", true),
            json.getDouble("feedback", true),
            OscillatorUtil.toIugumArray(json.get("freq.corp")),
            OscillatorUtil.toIugumArray(json.get("freq.tail")),
            json.getDouble("freq_fix", false),
            OscillatorUtil.toIugumArray(json.get("amp.corp")),
            OscillatorUtil.toIugumArray(json.get("amp.tail")),
            null, null,
            OscillatorUtil.toIugumArray(json.get("vco.freq.corp")),
            OscillatorUtil.toIugumArray(json.get("vco.freq.tail")),
            OscillatorUtil.toIugumArray(json.get("vco.amp.corp")),
            OscillatorUtil.toIugumArray(json.get("vco.amp.tail")),
            OscillatorUtil.toIugumArray(json.get("vca.freq.corp")),
            OscillatorUtil.toIugumArray(json.get("vca.freq.tail")),
            OscillatorUtil.toIugumArray(json.get("vca.amp.corp")),
            OscillatorUtil.toIugumArray(json.get("vca.amp.tail")),
            OscillatorUtil.toIugumArray(json.get("fb.corp")),
            OscillatorUtil.toIugumArray(json.get("fb.tail")),
            OscillatorSettings.toSettingsArray(new Mjson(json.get("pueros", new MjsonList()))));
            
            //    null, null, null, null, null, null, null, null, null, null, null, null, pueros);
    }

    public OscillatorSettings(
        Constantia.Unda unda,
        double volume, double feedback,
        Iugum[] iuga_frequentiae_corporis,
        Iugum[] iuga_frequentiae_caudae,
        Double fixa_frequentia,
        Iugum[] iuga_quantitatis_corporis,
        Iugum[] iuga_quantitatis_caudae,
        ArrayList<ArrayList<Iugum>> iuga_pans_corporis,
        ArrayList<ArrayList<Iugum>> iuga_pans_caudae,
        Iugum[] iuga_vco_frequentiae_corporis,
        Iugum[] iuga_vco_frequentiae_caudae,
        Iugum[] iuga_vco_quantitatis_corporis,
        Iugum[] iuga_vco_quantitatis_caudae,
        Iugum[] iuga_vca_frequentiae_corporis,
        Iugum[] iuga_vca_frequentiae_caudae,
        Iugum[] iuga_vca_quantitatis_corporis,
        Iugum[] iuga_vca_quantitatis_caudae,
        Iugum[] iuga_fb_quantitatis_corporis,
        Iugum[] iuga_fb_quantitatis_caudae,
        OscillatorSettings... pueros) {
        this.unda = unda;
        this.volume = volume;
        this.feedback = feedback;
        map_primo_fco = init(iuga_frequentiae_corporis, true, true);
        map_primo_fca = init(iuga_frequentiae_caudae, false, false);
        this.fixa_frequentia = fixa_frequentia;
        map_primo_qco = init(iuga_quantitatis_corporis, true, false);
        map_primo_qca = init(iuga_quantitatis_caudae, false, false);
        map_primo_pco = init(iuga_pans_corporis, true, true);
        map_primo_pca = init(iuga_pans_caudae, false, false);
        map_vco_fco = init(iuga_vco_frequentiae_corporis, true, false);
        map_vco_fca = init(iuga_vco_frequentiae_caudae, false, false);
        map_vco_qco = init(iuga_vco_quantitatis_corporis, true, false);
        map_vco_qca = init(iuga_vco_quantitatis_caudae, false, false);
        map_vca_fco = init(iuga_vca_frequentiae_corporis, true, false);
        map_vca_fca = init(iuga_vca_frequentiae_caudae, false, false);
        map_vca_qco = init(iuga_vca_quantitatis_corporis, true, false);
        map_vca_qca = init(iuga_vca_quantitatis_caudae, false, false);
        map_fb_qco = init(iuga_fb_quantitatis_corporis, true, true);
        map_fb_qca = init(iuga_fb_quantitatis_caudae, false, true);
        this.pueros = pueros;
    }

    private static ArrayList<TreeMap<Double, Iugum>> init(ArrayList<ArrayList<Iugum>> iugi, boolean corpusEst, boolean unusEst) {
        ArrayList<TreeMap<Double, Iugum>> map = new ArrayList<>();
        for (int i = 0; i < CHANNEL; i++) {
            map.add(init(iugi == null ? new Iugum[0] : iugi.get(i).toArray(new Iugum[0]), corpusEst, unusEst));
        }
        return map;
    }

    private static TreeMap<Double, Iugum> init(Iugum[] iugi, boolean corpusEst, boolean unusEst) {
        TreeMap<Double, Iugum> map = new TreeMap<>();
        if (iugi != null) {
            for (Iugum iugum : iugi) {
                map.put(iugum.capioTempus(), iugum);
            }
        }
        if (corpusEst) {
            Punctum punctum = unusEst ? new Punctum(1) : new Punctum();
            map.putIfAbsent(0d, new Iugum(0d, punctum, punctum));
        }
        return map;
    }
    public static OscillatorSettings[] toSettingsArray(Mjson json){
        MjsonElement[] elems = json.getAsArray(null);
        //System.out.println(elems);
        if(elems == null){
            return null;
        }
        OscillatorSettings[] array = new OscillatorSettings[elems.length];
        for(int i = 0;i < elems.length;i++){
            array[i] = new OscillatorSettings(new Mjson(elems[i]));
        }
        return array;
    }
}
