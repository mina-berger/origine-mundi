package la.clamor;

import la.clamor.referibile.OscillatioSine;
import java.util.ArrayList;
import la.clamor.Constantia.Unda;
import la.clamor.Constantia.Rebus;
import static la.clamor.Constantia.Rebus.FB_QUANT;
import static la.clamor.Constantia.Rebus.FREQ;
import static la.clamor.Constantia.Rebus.PAN;
import static la.clamor.Constantia.Rebus.QUANT;
import static la.clamor.Constantia.Rebus.VCA_FREQ;
import static la.clamor.Constantia.Rebus.VCA_QUANT;
import static la.clamor.Constantia.Rebus.VCO_FREQ;
import static la.clamor.Constantia.Rebus.VCO_QUANT;
import org.apache.commons.math3.util.FastMath;

/**
 *
 * @author minae.hiyamae
 */
public abstract class Positiones {

    long longitudo;
    long index;
    Positiones[] modulatores;
    Unda unda;
    double volume;
    double feedback;

    public Positiones(long longitudo, Unda unda, double volume, double feedback) {
        //longitudo = (long)(diuturnitas * REGULA_EXAMPLI_D / 1000d);
        this.longitudo = longitudo;
        this.unda = unda;
        this.volume = volume;
        this.feedback = feedback;
        index = 0;
    }

    protected void ponoModulatores(Positiones[] modulatores) {
        this.modulatores = modulatores;
    }

    public Positiones[] capioModulatores() {
        return modulatores;
    }

    public boolean paratusSum() {
        return index < longitudo;
    }

    public void deinde() {
        index++;
    }

    public long capioLongitudo() {
        return longitudo;
    }

    public void ponoLongitudo(long longitudo) {
        this.longitudo = longitudo;
    }

    public Unda capioUndam() {
        return unda;
    }

    //public double capioVolume(){
    //    return volume;
    //}
    public abstract Punctum capioFeedback();

    public abstract Punctum capioFrequentiae();

    public abstract Punctum capioQuantitatis();

    public abstract Punctum[] capioPans();

    public static class PositionesFixi extends Positiones {

        Envelope<Punctum> map_frequentiae;
        Envelope<Punctum> map_quantitatis;
        ArrayList<Envelope<Punctum>> map_pans;
        Envelope<Punctum> map_vco_frequentiae;
        Envelope<Punctum> map_vco_quantitatis;
        Envelope<Punctum> map_vca_frequentiae;
        Envelope<Punctum> map_vca_quantitatis;
        Envelope<Punctum> map_fb_quantitatis;
        OscillatioSine vco;
        OscillatioSine vca;

        @Override
        public String toString() {
            return "TBD";
            /*//if(1 == 1)throw new RuntimeException();
            String str = "PositionesFixi\n map_frequentiae\n";
            str += toString(map_frequentiae);
            str += " map_quantitatis\n";
            str += toString(map_quantitatis);
            str += " map_pans\n";
            for(Envelope map:map_pans){
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
             */
        }

        /*private static String toString(Envelope map){
            return map.entrySet().stream().map(
                    (entry) -> "  " + entry.getKey() + ":" + entry.getValue().toString() + "\n").reduce("", String::concat);
        }*/
        public PositionesFixi(Unda unda, double volume, double feedback) {
            this(unda, volume, feedback,
                new Envelope(), new Envelope(), null,
                new Envelope(), new Envelope(),
                new Envelope(), new Envelope(), new Envelope());
        }

        /*public PositionesFixi(Unda unda, double volume, double feedback, Positio[] frequentiae, Positio[] quantitatis, PositionesPrimo... positiones_modulatores) {
            this(unda, volume, feedback, frequentiae, quantitatis, null, new Positio[0], new Positio[0], new Positio[0], new Positio[0], new Positio[0], positiones_modulatores);
        }*/
 /*public PositionesFixi(
                Unda unda, double volume, double feedback,
                Positio[] frequentiae, Positio[] quantitatis, ArrayList<ArrayList<Positio>> pans,
                Positio[] vco_frequentiae, Positio[] vco_quantitatis, 
                Positio[] vca_frequentiae, Positio[] vca_quantitatis, 
                Positio[] fb_quantitatis, 
                Positiones... positiones_modulatores) {
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
            vco = new SineOscillatio();
            vca = new SineOscillatio();
            
            ponoModulatores(positiones_modulatores);
        }*/
        public PositionesFixi(
            Unda unda, double volume, double feedback,
            Envelope frequentiae, Envelope quantitatis, ArrayList<Envelope<Punctum>> pans,
            Envelope vco_frequentiae, Envelope vco_quantitatis,
            Envelope vca_frequentiae, Envelope vca_quantitatis,
            Envelope fb_quantitatis,
            Positiones... positiones_modulatores) {
            super(capioUltimum(initioEnvelope(frequentiae, true), initioEnvelope(quantitatis, false)), unda, volume, feedback);
            map_frequentiae = initioEnvelope(frequentiae, true);
            map_quantitatis = initioEnvelope(quantitatis, false);
            map_pans = initioEnvelopes(pans, true);
            map_vco_frequentiae = initioEnvelope(vco_frequentiae, false);
            map_vco_quantitatis = initioEnvelope(vco_quantitatis, false);
            map_vca_frequentiae = initioEnvelope(vca_frequentiae, false);
            map_vca_quantitatis = initioEnvelope(vca_quantitatis, false);
            //System.out.println(fb_quantitatis.length);
            map_fb_quantitatis = initioEnvelope(fb_quantitatis, true);
            vco = new OscillatioSine();
            vca = new OscillatioSine();

            ponoModulatores(positiones_modulatores);
        }

        private static Envelope<Punctum> initioEnvelope(Envelope<Punctum> map, boolean unusEst) {
            map.putIfAbsent(0l, unusEst ? new Punctum(1) : new Punctum());
            return map;
        }

        private static ArrayList<Envelope<Punctum>> initioEnvelopes(ArrayList<Envelope<Punctum>> list, boolean unusEst) {
            if (list == null) {
                list = new ArrayList<>();
            }
            for (int i = 0; i < Res.publica.channel(); i++) {
                if (list.size() > i) {
                    initioEnvelope(list.get(i), unusEst);
                } else {
                    list.add(new Envelope());
                }
            }
            return list;
        }

        /*private static ArrayList<Envelope> initioMap(ArrayList<ArrayList<Positio>> positiones, boolean unusEst){
                ArrayList<Envelope> map = new ArrayList<>();
                for(int i = 0;i < Res.publica.channel();i++){
                    if(positiones != null && positiones.size() > i){
                        map.add(initioMap(positiones.get(i).toArray(new Positio[0]), unusEst));
                    }else{
                        map.add(new Envelope());
                    }
                }
                return map;
        }
        private static Envelope initioMap(Positio[] positiones, boolean unusEst){
            Envelope map = new Envelope();
            for(Positio positio:positiones){
                map.put(positio.capioPositio(), positio.capioPunctum());
            }
            map.putIfAbsent(0l, unusEst?new Punctum(1):new Punctum());
            return map;
        }*/
        private Envelope capioMap(Rebus res, Integer channel) {
            Envelope map;
            switch (res) {
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

        public void pono(Rebus res, Integer channel, long positio, Punctum punctum) {
            //if(res == FB_QUANT){
            //    System.out.println("DEBUG");
            //
            capioMap(res, channel).put(positio, punctum);
        }

        public boolean habet(Rebus res, Integer channel, long positio) {
            return capioMap(res, channel).containsKey(positio);
        }

        public void computoLongitudo() {
            ponoLongitudo(FastMath.max(map_frequentiae.lastKey(), map_quantitatis.lastKey()));
        }

        private static long capioUltimum(Envelope<Punctum> frequentiae, Envelope<Punctum> quantitatis) {
            return FastMath.max(frequentiae.lastKey(), quantitatis.lastKey());
            //return FastMath.max(
            //        Arrays.stream(frequentiae).mapToLong(e -> e.capioPositio()).reduce(0l, (x, y) -> x > y ? x : y),
            //        Arrays.stream(quantitatis).mapToLong(e -> e.capioPositio()).reduce(0l, (x, y) -> x > y ? x : y));
        }

        @Override
        public Punctum capioFrequentiae() {
            return capio(map_frequentiae, vco, map_vco_frequentiae, map_vco_quantitatis, index);
        }

        @Override
        public Punctum capioQuantitatis() {
            return capio(map_quantitatis, vca, map_vca_frequentiae, map_vca_quantitatis, index).multiplico(volume);
        }

        @Override
        public Punctum[] capioPans() {
            Punctum[] pans = new Punctum[Res.publica.channel()];
            for (int i = 0; i < Res.publica.channel(); i++) {
                pans[i] = map_pans.get(i).capioValue(index);
            }
            return pans;
        }

        @Override
        public Punctum capioFeedback() {
            return map_fb_quantitatis.capioValue(index).multiplico(feedback);
        }

        private static Punctum capio(Envelope<Punctum> map, OscillatioSine vc_osc,
            Envelope<Punctum> map_vc_frequentiae,
            Envelope<Punctum> map_vc_quantitatis, long index) {
            Punctum punctum = map.capioValue(index);
            punctum = punctum.multiplico(vc_osc.lego(
                map_vc_frequentiae.capioValue(index),
                map_vc_quantitatis.capioValue(index)).addo(new Punctum(1)));
            return punctum;
        }
    }

    public static void main(String[] args) {
        int index = 3;
        Aestimatio a1
            = new Aestimatio(5).multiplico(new Aestimatio(5 - index)).addo(
            new Aestimatio(10).multiplico(new Aestimatio(index - 0))).partior(new Aestimatio(5));
        System.out.println(a1);
    }
}
