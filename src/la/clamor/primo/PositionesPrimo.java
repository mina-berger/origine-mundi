package la.clamor.primo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map.Entry;
import java.util.TreeMap;
import static la.clamor.Constantia.CHANNEL;
import la.clamor.Constantia.Unda;
import la.clamor.Functiones;
import la.clamor.Punctum;
import la.clamor.Aestimatio;
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
 * @author minae.hiyamae
 */
public abstract class PositionesPrimo {
    long   longitudo;
    long   index;
    PositionesPrimo[] modulatores;
    Unda unda;
    double volume;
    double feedback;
    public PositionesPrimo(long longitudo, Unda unda, double volume, double feedback){
        //longitudo = (long)(diuturnitas * REGULA_EXAMPLI_D / 1000d);
        this.longitudo = longitudo;
        this.unda      = unda;
        this.volume    = volume;
        this.feedback  = feedback;
        index = 0;
    }
    protected void ponoModulatores(PositionesPrimo[] modulatores){
        this.modulatores = modulatores;
    }
    public PositionesPrimo[] capioModulatores(){
        return modulatores;
    }
    public boolean paratusSum() {
        return index < longitudo;
    }
    public void deinde(){
        index++;
    }
    public long capioLongitudo(){
        return longitudo;
    }
    public void ponoLongitudo(long longitudo){
        this.longitudo = longitudo;
    }
    public Unda capioUndam(){
        return unda;
    }
    //public double capioVolume(){
    //    return volume;
    //}
    public abstract Punctum capioFeedback();
    
    public abstract Punctum capioFrequentiae();
    public abstract Punctum capioQuantitatis();
    public abstract Punctum[] capioPans();
    public static class Positio {
        long positio;
        Punctum punctum;
        public Positio(double tempus, Punctum punctum){
           positio = Functiones.adPositio(tempus);//(long)(tempus * REGULA_EXAMPLI_D / 1000d);
           this.punctum = punctum;
        }
        public long capioPositio(){
            return positio;
        }
        public Punctum capioPunctum(){
            return punctum;
        }
    }
    public static class PositionesFixi extends PositionesPrimo {

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
        public PositionesFixi(Unda unda, double volume, double feedback) {
            this(unda, volume, feedback, 
                new Positio[0], new Positio[0], null, 
                new Positio[0], new Positio[0], 
                new Positio[0], new Positio[0], new Positio[0]);
        }
        public PositionesFixi(Unda unda, double volume, double feedback, Positio[] frequentiae, Positio[] quantitatis, PositionesPrimo... positiones_modulatores) {
            this(unda, volume, feedback, frequentiae, quantitatis, null, new Positio[0], new Positio[0], new Positio[0], new Positio[0], new Positio[0], positiones_modulatores);
        }
        public PositionesFixi(
                Unda unda, double volume, double feedback,
                Positio[] frequentiae, Positio[] quantitatis, ArrayList<ArrayList<Positio>> pans,
                Positio[] vco_frequentiae, Positio[] vco_quantitatis, 
                Positio[] vca_frequentiae, Positio[] vca_quantitatis, 
                Positio[] fb_quantitatis, 
                PositionesPrimo... positiones_modulatores) {
            super(capioUltimum(frequentiae, quantitatis), unda, volume, feedback);
            map_frequentiae = initioMap(frequentiae, true);
            map_quantitatis = initioMap(quantitatis, false);
            map_pans        = initioMap(pans, true);
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
        private static ArrayList<TreeMap<Long, Punctum>> initioMap(ArrayList<ArrayList<Positio>> positiones, boolean unusEst){
                ArrayList<TreeMap<Long, Punctum>> map = new ArrayList<>();
                for(int i = 0;i < CHANNEL;i++){
                    if(positiones != null && positiones.size() > i){
                        map.add(initioMap(positiones.get(i).toArray(new Positio[0]), unusEst));
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
            Entry<Long, Punctum> solum  = map.floorEntry  (index);
            Entry<Long, Punctum> tectum = map.ceilingEntry(index);
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

    }
    public static void main(String[] args){
        int index = 3;
        Aestimatio a1 = 
                new Aestimatio(5).multiplico(new Aestimatio(5 - index)).addo( 
                new Aestimatio(10).multiplico(new Aestimatio(index - 0))).partior(new Aestimatio(5));
        System.out.println(a1);
    }
}
