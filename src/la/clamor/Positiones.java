/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import static la.clamor.Constantia.CHANNEL;
import org.apache.commons.math3.util.FastMath;

/**
 *
 * @author user
 */
public class Positiones extends AbstractPositiones {

    public enum Res{FREQ, QUANT, PAN, VCO_FREQ, VCO_QUANT, VCA_FREQ, VCA_QUANT, FB_QUANT};
    TreeMap<Long, Punctum> map_frequentiae;
    TreeMap<Long, Punctum> map_quantitatis;
    ArrayList<TreeMap<Long, Punctum>> map_pans;
    TreeMap<Long, Punctum> map_vco_frequentiae;
    TreeMap<Long, Punctum> map_vco_quantitatis;
    TreeMap<Long, Punctum> map_vca_frequentiae;
    TreeMap<Long, Punctum> map_vca_quantitatis;
    TreeMap<Long, Punctum> map_fb_quantitatis;
    OscillatioSimplex vco;
    OscillatioSimplex vca;
    @Override
    public String toString(){
        //if(1 == 1)throw new RuntimeException();
        String str = "PositionesFixi\n map_frequentiae\n";
        str += toString(map_frequentiae);
        str += " map_quantitatis\n";
        str += toString(map_quantitatis);
        str += " map_pans\n";
        for(TreeMap<Long, Punctum> map:map_pans){
            str += toString(map);
        }
        str += " map_vco_frequentiae\n";
        str += toString(map_vco_frequentiae);
        str += " map_vco_quantitas\n";
        str += toString(map_vco_quantitatis);
        str += " map_vca_frequentia\n";
        str += toString(map_vca_frequentiae);
        str += " map_vca_quantitas\n";
        str += toString(map_vca_quantitatis);
        return str;

    }
    private static String toString(TreeMap<Long, Punctum> map){
        return map.entrySet().stream().map(
                (entry) -> "  " + entry.getKey() + ":" + entry.getValue().toString() + "\n").reduce("", String::concat);
    }
    public Positiones(Constantia.Unda unda, double volume, double feedback) {
        this(unda, volume, feedback, 
            new Positio[0], new Positio[0], null, 
            new Positio[0], new Positio[0], 
            new Positio[0], new Positio[0], new Positio[0]);
    }
    public Positiones(Constantia.Unda unda, double volume, double feedback, Positio[] frequentiae, Positio[] quantitatis, AbstractPositiones... positiones_modulatores) {
        this(unda, volume, feedback, frequentiae, quantitatis, null, new Positio[0], new Positio[0], new Positio[0], new Positio[0], new Positio[0], positiones_modulatores);
    }
    public Positiones(
            Constantia.Unda unda, double volume, double feedback,
            Positio[] frequentiae, Positio[] quantitatis, Positio[][] pans,
            Positio[] vco_frequentiae, Positio[] vco_quantitatis, 
            Positio[] vca_frequentiae, Positio[] vca_quantitatis, 
            Positio[] fb_quantitatis, 
            AbstractPositiones... positiones_modulatores) {
        super(capioUltimum(frequentiae, quantitatis), unda, volume, feedback);
        map_frequentiae = initioMap(frequentiae, true);
        map_quantitatis = initioMap(quantitatis, false);
        map_pans        = initioMap(pans == null?
                new Positio[][]{
                    new Positio[]{new Positio(0, new Punctum(1))},
                    new Positio[]{new Positio(0, new Punctum(1))}
                }:pans, true);
        map_vco_frequentiae = initioMap(vco_frequentiae, false);
        map_vco_quantitatis = initioMap(vco_quantitatis, false);
        map_vca_frequentiae = initioMap(vca_frequentiae, false);
        map_vca_quantitatis = initioMap(vca_quantitatis, false);
        //System.out.println(fb_quantitatis.length);
        map_fb_quantitatis  = initioMap(fb_quantitatis, true);
        vco = new OscillatioSimplex();
        vca = new OscillatioSimplex();

        ponoModulatores(positiones_modulatores);
    }
    private static ArrayList<TreeMap<Long, Punctum>> initioMap(Positio[][] positiones, boolean unusEst){
            ArrayList<TreeMap<Long, Punctum>> map = new ArrayList<>();
            for(int i = 0;i < CHANNEL;i++){
                if(positiones != null && positiones.length > i){
                    map.add(initioMap(positiones[i], unusEst));
                }else{
                    map.add(new TreeMap<>());
                }
            }
            return map;
    }
    private static TreeMap<Long, Punctum> initioMap(Positio[] positiones, boolean unusEst){
        TreeMap<Long, Punctum> map = new TreeMap<>();
        for(Positio positio:positiones){
            map.put(positio.capioPositio(), positio.capioPunctum());
        }
        map.putIfAbsent(0l, unusEst?new Punctum(1):new Punctum());
        return map;
    }
    private TreeMap<Long, Punctum> capioMap(Res res, Integer channel){
        TreeMap<Long, Punctum> map;
        switch(res){
            case FREQ:
                map = map_frequentiae;
                break;
            case QUANT:
                map = map_quantitatis;
                break;
            case PAN:
                map = map_pans.get(channel);
                break;
            case VCO_FREQ:
                map = map_vco_frequentiae;
                break;
            case VCO_QUANT:
                map = map_vco_quantitatis;
                break;
            case VCA_FREQ:
                map = map_vca_frequentiae;
                break;
            case VCA_QUANT:
                map = map_vca_quantitatis;
                break;
            case FB_QUANT:
                map = map_fb_quantitatis;
                break;
            default:
                throw new IllegalArgumentException("nescio res=" + res);
        }
        return map;
    }
    public void pono(Res res, Integer channel, long positio, Punctum punctum){
        //if(res == FB_QUANT){
        //    System.out.println("DEBUG");
        //
        capioMap(res, channel).put(positio, punctum);
    }
    public boolean habet(Res res, Integer channel, long positio){
        return capioMap(res, channel).containsKey(positio);
    }
    public void computoLongitudo(){
        ponoLongitudo(FastMath.max(map_frequentiae.lastKey(), map_quantitatis.lastKey()));
    }
    private static long capioUltimum(Positio[] frequentiae, Positio[] quantitatis){
        return FastMath.max(
                Arrays.stream(frequentiae).mapToLong(e -> e.capioPositio()).reduce(0l, (x, y) -> x > y ? x : y),
                Arrays.stream(quantitatis).mapToLong(e -> e.capioPositio()).reduce(0l, (x, y) -> x > y ? x : y));
    }

    @Override
    public Punctum capioFrequentiae() {
        return capio(map_frequentiae, vco, map_vco_frequentiae, map_vco_quantitatis, index);
    }
    /*public void print(){
        for(long key:map_frequentiae.keySet()){
            System.out.println("DEBUG:" + map_frequentiae.get(key));
        }
    }*/

    @Override
    public Punctum capioQuantitatis() {
        return capio(
            map_quantitatis, vca, 
            map_vca_frequentiae, map_vca_quantitatis, 
            index).multiplico(volume);
    }
    @Override
    public Punctum[] capioPans() {
        Punctum[] pans = new Punctum[CHANNEL];
        for(int i = 0;i < CHANNEL;i++){
            pans[i] = capioPunctum(map_pans.get(i), index);
        }
        return pans;
    }        
    @Override
    public Punctum capioFeedback() {
        return capioPunctum(map_fb_quantitatis, index).multiplico(feedback);
    }


    private static Punctum capio(TreeMap<Long, Punctum> map, OscillatioSimplex vc_osc, 
            TreeMap<Long, Punctum> map_vc_frequentiae, 
            TreeMap<Long, Punctum> map_vc_quantitatis, long index){
        Punctum punctum = capioPunctum(map, index);
        punctum = punctum.multiplico(vc_osc.lego(
                capioPunctum(map_vc_frequentiae, index), 
                capioPunctum(map_vc_quantitatis, index)).addo(new Punctum(1)));
        return punctum;
    }
    private static Punctum capioPunctum(TreeMap<Long, Punctum> map, long index){
        long index_solum;
        long index_tectum;
        Punctum punctum_solum;
        Punctum punctum_tectum;
        if(map.containsKey(index)){
            index_solum  = index;
            index_tectum = index + 1;
            punctum_solum  = map.get(index);
            punctum_tectum = map.get(index);
        }else{
            Map.Entry<Long, Punctum> solum  = map.floorEntry  (index);
            Map.Entry<Long, Punctum> tectum = map.ceilingEntry(index);
            if(tectum == null){
                index_solum  = solum.getKey();
                index_tectum = index;
                punctum_solum  = solum.getValue();
                punctum_tectum = solum.getValue();
            }else{
                index_solum  = solum.getKey();
                index_tectum = tectum.getKey();
                punctum_solum  = solum.getValue();
                punctum_tectum = tectum.getValue();
            }
        }

        Punctum punctum = new Punctum();
        Punctum.Aestimatio diff = new Punctum.Aestimatio(index_tectum - index_solum);
        for(int i = 0;i < CHANNEL;i++){
            punctum.ponoAestimatio(i, 
                    punctum_solum .capioAestimatio(i).multiplico(new Punctum.Aestimatio(index_tectum - index)).addo( 
                    punctum_tectum.capioAestimatio(i).multiplico(new Punctum.Aestimatio(index - index_solum))).partior(diff));
        }
        return punctum;

    }
    
}
