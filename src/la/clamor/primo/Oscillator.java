

package la.clamor.primo;

import java.io.File;
import java.util.ArrayList;
import java.util.NavigableMap;
import java.util.TreeMap;
import la.clamor.Aestimatio;
import la.clamor.Consilium;
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
import la.clamor.Constantia.Unda;
import la.clamor.Functiones;
import la.clamor.Punctum;
import la.clamor.ScriptorWav;
import la.clamor.primo.OscillatioPrimo.Oscillationes;
import la.clamor.primo.PositionesPrimo.PositionesFixi;
import origine_mundi.OmUtil;

/**
 *
 * @author minae.hiyamae
 */
public class Oscillator {
    public static class Iuga{
        Unda unda;
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
        Iuga[] pueros;
        public Iuga(
                Unda unda,
                double volume, double feedback,
                Iugum[] iuga_frequentiae_corporis, 
                Iugum[] iuga_frequentiae_caudae, 
                Double fixa_frequentia,
                Iugum[] iuga_quantitatis_corporis, 
                Iugum[] iuga_quantitatis_caudae, 
                Iuga... pueros){
            
            this(
                unda, volume, feedback,
                iuga_frequentiae_corporis,
                iuga_frequentiae_caudae, 
                fixa_frequentia,
                iuga_quantitatis_corporis,
                iuga_quantitatis_caudae, 
                null, null, null, null, null, null, null, null, null, null, null, null, pueros);
        }
        public Iuga(
                Unda unda,
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
                Iuga... pueros){
            this.unda = unda;
            this.volume = volume;
            this.feedback = feedback;
            map_primo_fco = init(iuga_frequentiae_corporis, true,  true);
            map_primo_fca = init(iuga_frequentiae_caudae,   false, false);
            this.fixa_frequentia = fixa_frequentia;
            map_primo_qco = init(iuga_quantitatis_corporis,     true,  false);
            map_primo_qca = init(iuga_quantitatis_caudae,       false, false);
            map_primo_pco = init(iuga_pans_corporis,            true,  true);
            map_primo_pca = init(iuga_pans_caudae,              false, false);
            map_vco_fco   = init(iuga_vco_frequentiae_corporis, true,  false);
            map_vco_fca   = init(iuga_vco_frequentiae_caudae,   false, false);
            map_vco_qco   = init(iuga_vco_quantitatis_corporis, true,  false);
            map_vco_qca   = init(iuga_vco_quantitatis_caudae,   false, false);
            map_vca_fco   = init(iuga_vca_frequentiae_corporis, true,  false);
            map_vca_fca   = init(iuga_vca_frequentiae_caudae,   false, false);
            map_vca_qco   = init(iuga_vca_quantitatis_corporis, true,  false);
            map_vca_qca   = init(iuga_vca_quantitatis_caudae,   false, false);
            map_fb_qco    = init(iuga_fb_quantitatis_corporis,  true,  true);
            map_fb_qca    = init(iuga_fb_quantitatis_caudae,    false, true);
            this.pueros   = pueros;
        }
    }
    private static ArrayList<TreeMap<Double, Iugum>> init(ArrayList<ArrayList<Iugum>> iugi, boolean corpusEst, boolean unusEst){
        ArrayList<TreeMap<Double, Iugum>> map = new ArrayList<>();
        for(int i = 0;i < CHANNEL;i++){
            map.add(init(iugi == null?new Iugum[0]:iugi.get(i).toArray(new Iugum[0]), corpusEst, unusEst));
        }
        return map;
    }
    
    private static TreeMap<Double, Iugum> init(Iugum[] iugi, boolean corpusEst, boolean unusEst){
        TreeMap<Double, Iugum> map = new TreeMap<>();
        if(iugi != null){
            for(Iugum iugum:iugi){
                //iugum.print();
                map.put(iugum.capioTempus(), iugum);
            }
        }
        if(corpusEst){
            Punctum punctum = unusEst?new Punctum(1):new Punctum();
            map.putIfAbsent(0d, new Iugum(0d, punctum, punctum));
        }
        return map;
    }
    
    private static PositionesFixi capioPositionesFixi(Iuga iugi, Punctum frequentia, double diuturnitas, Velocitas velocitas){
        PositionesFixi p = new PositionesFixi(iugi.unda, iugi.volume, iugi.feedback);
        ponoPositiones(p, FREQ, null,  
                iugi.map_primo_fco, iugi.map_primo_fca, 
                iugi.fixa_frequentia != null?new Punctum(iugi.fixa_frequentia):frequentia, 
                diuturnitas, velocitas);
        ponoPositiones(p, QUANT, null,  
                iugi.map_primo_qco, iugi.map_primo_qca, 
                new Punctum(1), diuturnitas, velocitas);
        for(int i = 0;i < CHANNEL;i++){
            ponoPositiones(p, PAN,  i, 
                    iugi.map_primo_pco.get(i), iugi.map_primo_pca.get(i), 
                    new Punctum(1), diuturnitas, velocitas);
        }
        ponoPositiones(p, VCO_FREQ, null, 
                iugi.map_vco_fco, iugi.map_vco_fca, 
                new Punctum(1), diuturnitas, velocitas);
        ponoPositiones(p, VCO_QUANT, null,  
                iugi.map_vco_qco, iugi.map_vco_qca, 
                new Punctum(1), diuturnitas, velocitas);
        ponoPositiones(p, VCA_FREQ, null, 
                iugi.map_vca_fco, iugi.map_vca_fca, 
                new Punctum(1), diuturnitas, velocitas);
        ponoPositiones(p, VCA_QUANT, null,  
                iugi.map_vca_qco, iugi.map_vca_qca, 
                new Punctum(1), diuturnitas, velocitas);
        //System.out.println("DEBUG:" + iugi.map_fb_qco.size() + ":" + iugi.map_fb_qca.size());
        ponoPositiones(p, FB_QUANT, null,  
                iugi.map_fb_qco, iugi.map_fb_qca, 
                new Punctum(1), diuturnitas, velocitas);
        
        PositionesPrimo[] modulatores = new PositionesPrimo[iugi.pueros.length];
        for(int i = 0;i < iugi.pueros.length;i++){
            modulatores[i] = capioPositionesFixi(iugi.pueros[i], frequentia, diuturnitas, velocitas);
        }
        p.ponoModulatores(modulatores);
        p.computoLongitudo();
        
        
        return p;
    }
    private static void ponoPositiones(
            PositionesFixi p, Res res, Integer channel,
            TreeMap<Double, Iugum> map_corporis, 
            TreeMap<Double, Iugum> map_caudae, 
            Punctum factor, double diuturnitas, Velocitas velocitas){
        long ultimo = Functiones.adPositio(diuturnitas);
        //System.out.println("diuturnitas=" + diuturnitas);
        NavigableMap<Double,Iugum> submap1 = map_corporis.subMap (0d, true, diuturnitas, true);
        NavigableMap<Double,Iugum> submap2 = map_corporis.tailMap(diuturnitas, true);
        submap1.keySet().stream().map((key) -> submap1.get(key)).forEach((iugum) -> {
            p.pono(res, channel, iugum.capioPositio(), iugum.capioPunctum(velocitas).multiplico(factor));
        });
        if(!p.habet(res, channel, ultimo)){
            if(map_caudae.containsKey(0d)){
                Iugum iugum = map_caudae.get(0d);
                p.pono(res, channel, ultimo, iugum.capioPunctum(velocitas).multiplico(factor));
                //if(res == FREQ) System.out.println("DEBUG2:" + iugum.capioPunctum(velocitas).multiplico(factor));
            }else if(submap2.isEmpty()){
                //System.out.println(submap1);
                p.pono(res, channel, ultimo, submap1.lastEntry().getValue().capioPunctum(velocitas).multiplico(factor));
                //if(res == FREQ) System.out.println("DEBUG3:" + submap1.lastEntry().getValue().capioPunctum(velocitas).multiplico(factor));
            }else{
                Double    tempus_solum = submap1.lastKey();
                Punctum  punctum_solum = submap1.get(tempus_solum).capioPunctum(velocitas);
                long       index_solum = Functiones.adPositio(tempus_solum);
                Double   tempus_tectum = submap2.firstKey();
                Punctum punctum_tectum = submap2.get(tempus_tectum).capioPunctum(velocitas);
                long      index_tectum = Functiones.adPositio(tempus_tectum) + ultimo;
                Punctum punctum = new Punctum();
                for(int i = 0;i < CHANNEL;i++){
                    punctum.ponoAestimatio(i, 
                            punctum_solum .capioAestimatio(i).multiplico(new Aestimatio(index_tectum - ultimo)).addo(
                            punctum_tectum.capioAestimatio(i).multiplico(new Aestimatio(ultimo - index_solum))).partior(
                                    new Aestimatio(index_tectum - index_solum)));
                }
                p.pono(res, channel, ultimo, punctum.multiplico(factor));
                //if(res == FREQ) System.out.println("DEBUG4:" + punctum.multiplico(factor));
            }
        }
        NavigableMap<Double,Iugum> submap3 = map_caudae.tailMap(0d, false);
        submap3.keySet().stream().forEach((Double key) -> {
            p.pono(res, channel, 
                    ultimo + Functiones.adPositio(key),
                    submap3.get(key).capioPunctum(velocitas).multiplico(factor));
            //if(res == FREQ) System.out.println("DEBUG5:" + submap3.get(key).capioPunctum(velocitas).multiplico(factor));
        });
    }
    private Iuga[] acies_iugorum;
    public Oscillator(Iuga... acies_iugorum){
        this.acies_iugorum = acies_iugorum;
    }
    public Oscillationes capioOscillationes(Punctum frequentia, double diuturnitas, Velocitas velocitas){
        Oscillationes oscillationes = new Oscillationes(acies_iugorum.length);
        for (Iuga acies_iugorum1 : acies_iugorum) {
            PositionesFixi p = capioPositionesFixi(acies_iugorum1, frequentia, diuturnitas, velocitas);
            //System.out.println(p);
            oscillationes.add(new OscillatioPrimo(p));
        }
        //System.out.println(oscillationes.size());
        //System.out.println(oscillationes.paratusSum());
        return oscillationes;
    }
    public static void main(String[] args){
        Oscillator osc = new Oscillator(
                new Iuga(
                        Unda.SINE, 1, 100, 
                        new Iugum[]{
                            new Iugum(   0, new Punctum(1), new Punctum(1))/*,
                            new Iugum( 100, new Punctum(1), new Punctum(1)),
                            new Iugum(1000, new Punctum(1), new Punctum(1))*/
                        }, 
                        new Iugum[]{
                            //new IugumTemporis(1000, new Punctum(1), new Punctum(1))
                        }, 
                        null,
                        new Iugum[]{
                            new Iugum(   0, new Punctum(0), new Punctum(0)),
                            new Iugum(  20, new Punctum(1), new Punctum(1)),
                            new Iugum( 500, new Punctum(0.5), new Punctum(0.5))
                        }, 
                        new Iugum[]{
                            new Iugum(1000, new Punctum(0), new Punctum(0))
                        },
                        null,//new Iugum[]{
                        //    new Iugum(0, new Punctum(1, 0), new Punctum(1, 0)), 
                        //},
                        null,//new Iugum[]{
                        //    new Iugum(0, new Punctum(0, 1), new Punctum(0, 1)), 
                        //    new Iugum(1000, new Punctum(0.5), new Punctum(0.5))
                        //},
                        new Iugum[]{
                                    //new Iugum(0, new Punctum(11.23), new Punctum(11.23))
                        }, 
                        new Iugum[0],
                        new Iugum[]{
                                    //new Iugum(0, new Punctum(0.005), new Punctum(0.005))
                        }, 
                        new Iugum[0],
                        new Iugum[]{
                                    //new Iugum(0, new Punctum(4), new Punctum(4))
                        }, 
                        new Iugum[0],
                        new Iugum[]{
                                    //new Iugum(0, new Punctum(0.02), new Punctum(0.02))
                        }, 
                        new Iugum[0], 
                        new Iugum[]{new Iugum(0, new Punctum(0), new Punctum(0))}, 
                        new Iugum[]{new Iugum(0, new Punctum(1), new Punctum(1))}//,
                        /*new Iuga(
                                Unda.SINE, 1, 0, 
                                new Iugum[]{
                                    new Iugum(0, new Punctum(2), new Punctum(2))
                                },
                                new Iugum[]{
                                    new Iugum(0, new Punctum(4), new Punctum(4)),
                                    new Iugum(1000, new Punctum(2), new Punctum(2))
                                },
                                null,
                                new Iugum[]{
                                    new Iugum(0, new Punctum(1), new Punctum(1)), 
                                    new Iugum(500, new Punctum(0.5), new Punctum(0.5))
                                },
                                new Iugum[]{
                                    new Iugum(1000, new Punctum(0), new Punctum(0))
                                },
                                null,
                                null,
                                new Iugum[]{
                                    new Iugum(0, new Punctum(3.00), new Punctum(3.00))}, 
                                new Iugum[]{
                                    new Iugum(0, new Punctum(10.00), new Punctum(10.00))},
                                new Iugum[]{
                                    new Iugum(0, new Punctum(0.005), new Punctum(0.005))}, 
                                new Iugum[]{
                                    new Iugum(0, new Punctum(0.5), new Punctum(0.5))},
                                new Iugum[]{
                                    new Iugum(0, new Punctum(22), new Punctum(22))}, 
                                new Iugum[]{
                                    new Iugum(0, new Punctum(10), new Punctum(10))},
                                new Iugum[]{
                                    new Iugum(0, new Punctum(0.3), new Punctum(0.3))},
                                new Iugum[]{
                                    new Iugum(0, new Punctum(1), new Punctum(1)),
                                    new Iugum(1000, new Punctum(0), new Punctum(0))}),
                        new Iuga(
                                Unda.SINE, 1, 0, 
                                new Iugum[]{
                                    new Iugum(0, new Punctum(8.015), new Punctum(8.015))
                                },
                                new Iugum[]{
                                    new Iugum(1000, new Punctum(8.015), new Punctum(8.015))
                                },
                                null,
                                new Iugum[]{
                                    new Iugum(0, new Punctum(0.3), new Punctum(0.3)), 
                                    new Iugum(500, new Punctum(0.1), new Punctum(0.1))
                                },
                                new Iugum[]{
                                    new Iugum(1000, new Punctum(0), new Punctum(0))
                                }
                        )*/
                )
        );
        double f = Temperamentum.instance.capioHZ(5);
        double a = Temperamentum.instance.capioHZ(9);
        double c = Temperamentum.instance.capioHZ(12);
        double e = Temperamentum.instance.capioHZ(16);
        double g = Temperamentum.instance.capioHZ(19);
        double b = Temperamentum.instance.capioHZ(23);
        Consilium cns = new Consilium();
        cns.addo(   0, osc.capioOscillationes(new Punctum(f), 5000, Velocitas.una(1)));
        cns.addo( 500, osc.capioOscillationes(new Punctum(a), 5500, Velocitas.una(1)));
        cns.addo(1000, osc.capioOscillationes(new Punctum(c), 6000, Velocitas.una(1)));
        cns.addo(1500, osc.capioOscillationes(new Punctum(e), 6500, Velocitas.una(1)));
        cns.addo(2000, osc.capioOscillationes(new Punctum(g), 7000, Velocitas.una(1)));
        cns.addo(2500, osc.capioOscillationes(new Punctum(b), 7500, Velocitas.una(1)));
        File out_file = new File(OmUtil.getDirectory("opus"), "oscillatior.wav");
        ScriptorWav sw = new ScriptorWav(out_file);
        sw.scribo(cns, false);
        Functiones.ludoLimam(out_file);
        /*Pretia pretia = new Pretia();
        Tabula tabula = Tabula.combo(
                "OSCILLATOR", new File("c://drive/doc/clamor/spec/oscillator.html"), "ratio", 
                new String[]{"Ampitudo"}, 
                new Collocatio("Ampitudo", true));
        int index = 0;
        while(cns.paratusSum() && index < 4000){
            Punctum y = cns.lego(pretia);
            tabula.addo(index++, y.capioAestimatio(0).doubleValue());
        }
        tabula.imprimo();*/
    }
    
}