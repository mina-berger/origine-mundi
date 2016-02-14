/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor;

import java.util.ArrayList;
import java.util.TreeMap;
import static la.clamor.Constantia.CHANNEL;
import la.clamor.Constantia.Res;
import static la.clamor.Constantia.Res.FB_QUANT;
import static la.clamor.Constantia.Res.FREQ;
import static la.clamor.Constantia.Res.PAN;
import static la.clamor.Constantia.Res.QUANT;
import static la.clamor.Constantia.Res.VCA_FREQ;
import static la.clamor.Constantia.Res.VCA_QUANT;
import static la.clamor.Constantia.Res.VCO_FREQ;
import static la.clamor.Constantia.Res.VCO_QUANT;
import org.apache.commons.math3.util.FastMath;

/**
 *
 * @author user
 */
public class PositionesOscillationis extends AbstractPositionesOscillationis {

    Positiones map_frequentiae;
    Positiones map_quantitatis;
    ArrayList<Positiones> map_pans;
    Positiones map_vco_frequentiae;
    Positiones map_vco_quantitatis;
    Positiones map_vca_frequentiae;
    Positiones map_vca_quantitatis;
    Positiones map_fb_quantitatis;
    SineOscillatio vco;
    SineOscillatio vca;
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
    public PositionesOscillationis(Constantia.Unda unda, double volume, double feedback) {
        this(unda, volume, feedback, 
            new Positiones(true), new Positiones(false), null, 
            new Positiones(false), new Positiones(false), 
            new Positiones(false), new Positiones(false), new Positiones(true));
    }
    public PositionesOscillationis(Constantia.Unda unda, double volume, double feedback, Positiones frequentiae, Positiones quantitatis, AbstractPositionesOscillationis... positiones_modulatores) {
        this(unda, volume, feedback, frequentiae, quantitatis, null, new Positiones(false), new Positiones(false), new Positiones(false), new Positiones(false), new Positiones(true), positiones_modulatores);
    }
    public PositionesOscillationis(
            Constantia.Unda unda, double volume, double feedback,
            Positiones frequentiae, Positiones quantitatis, Positiones[] pans,
            Positiones vco_frequentiae, Positiones vco_quantitatis, 
            Positiones vca_frequentiae, Positiones vca_quantitatis, 
            Positiones fb_quantitatis, 
            AbstractPositionesOscillationis... positiones_modulatores) {
        super(capioUltimum(frequentiae, quantitatis), unda, volume, feedback);
        map_frequentiae = frequentiae;//new Positiones(true, frequentiae);
        map_quantitatis = quantitatis;//new Positiones(false, quantitatis);
        map_pans        = initioMap(pans, true);
        /*map_pans        = initioMap(pans == null?
                new Positio[][]{
                    new Positio[]{new Positio(0, new Punctum(1))},
                    new Positio[]{new Positio(0, new Punctum(1))}
                }:pans, true);*/
        map_vco_frequentiae = vco_frequentiae;//new Positiones(false, vco_frequentiae);
        map_vco_quantitatis = vco_quantitatis;//new Positiones(false, vco_quantitatis);
        map_vca_frequentiae = vca_frequentiae;//new Positiones(false, vca_frequentiae);
        map_vca_quantitatis = vca_quantitatis;//new Positiones(false, vca_quantitatis);
        map_fb_quantitatis  = fb_quantitatis;//new Positiones(true, fb_quantitatis);
        vco = new SineOscillatio();
        vca = new SineOscillatio();

        ponoModulatores(positiones_modulatores);
    }
    private static ArrayList<Positiones> initioMap(Positiones[] positiones, boolean unusEst){
            ArrayList<Positiones> map = new ArrayList<>();
            for(int i = 0;i < CHANNEL;i++){
                if(positiones != null && positiones[i] != null&& positiones[i].size() > 0){
                    map.add(positiones[i]);
                }else{
                    Punctum pc = new Punctum();
                    pc.ponoAestimatio(i, new Aestimatio(1));
                    map.add(new Positiones(false, new Positio(0, pc)));
                }
            }
            return map;
    }
    /*private static TreeMap<Long, Punctum> initioMap(Positio[] positiones, boolean unusEst){
        TreeMap<Long, Punctum> map = new TreeMap<>();
        for(Positio positio:positiones){
            map.put(positio.capioPositio(), positio.capioPunctum());
        }
        map.putIfAbsent(0l, unusEst?new Punctum(1):new Punctum());
        return map;
    }*/
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
    private static long capioUltimum(Positiones frequentiae, Positiones quantitatis){
        return FastMath.max(frequentiae.lastKey(), quantitatis.lastKey());
                
                //Arrays.stream(frequentiae).mapToLong(e -> e.capioPositio()).reduce(0l, (x, y) -> x > y ? x : y),
                //Arrays.stream(quantitatis).mapToLong(e -> e.capioPositio()).reduce(0l, (x, y) -> x > y ? x : y));
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
            pans[i] = map_pans.get(i).capioPunctum(index);
        }
        return pans;
    }        
    @Override
    public Punctum capioFeedback() {
        return map_fb_quantitatis.capioPunctum(index).multiplico(feedback);
    }


    private static Punctum capio(Positiones map, SineOscillatio vc_osc, 
            Positiones map_vc_frequentiae, 
            Positiones map_vc_quantitatis, long index){
        Punctum punctum = map.capioPunctum(index);
        punctum = punctum.multiplico(vc_osc.lego(
                map_vc_frequentiae.capioPunctum(index), 
                map_vc_quantitatis.capioPunctum(index)).addo(new Punctum(1)));
        return punctum;
    }
    /*private static Punctum capioPunctum(TreeMap<Long, Punctum> map, long index){
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
        Aestimatio diff = new Aestimatio(index_tectum - index_solum);
        for(int i = 0;i < CHANNEL;i++){
            punctum.ponoAestimatio(i, 
                    punctum_solum .capioAestimatio(i).multiplico(new Aestimatio(index_tectum - index)).addo( 
                    punctum_tectum.capioAestimatio(i).multiplico(new Aestimatio(index - index_solum))).partior(diff));
        }
        return punctum;

    }*/
    //public static void main(String[] arg){
    //    Positiones p = new Positiones(new Punctum(1));
    //    while(true){
        //    System.out.println(p.capioPunctum(index).multiplico(feedback));
    //    }
    //}
    
}
