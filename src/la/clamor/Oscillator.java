package la.clamor;

import la.clamor.io.ScriptorWav;
import java.io.File;
import java.util.NavigableMap;
import java.util.TreeMap;
import la.clamor.Constantia.Rebus;
import static la.clamor.Constantia.Rebus.FB_QUANT;
import static la.clamor.Constantia.Rebus.FREQ;
import static la.clamor.Constantia.Rebus.PAN;
import static la.clamor.Constantia.Rebus.QUANT;
import static la.clamor.Constantia.Rebus.VCA_FREQ;
import static la.clamor.Constantia.Rebus.VCA_QUANT;
import static la.clamor.Constantia.Rebus.VCO_FREQ;
import static la.clamor.Constantia.Rebus.VCO_QUANT;
import la.clamor.Constantia.Unda;
import la.clamor.Oscillatio.Oscillationes;
import static la.clamor.OscillatorUtil.getPreset;
import la.clamor.Positiones.PositionesFixi;
import origine_mundi.OmUtil;

/**
 *
 * @author minae.hiyamae
 */
public class Oscillator implements Instrument {

    private OscillatorSettings[] acies_iugorum;
    private String name;

    public Oscillator(String name) {
        this(name, OscillatorSettings.toSettingsArray(getPreset(name)));
    }

    public Oscillator(String name, OscillatorSettings... acies_iugorum) {
        this.name = name;
        this.acies_iugorum = acies_iugorum;
    }

    private static PositionesFixi capioPositionesFixi(OscillatorSettings iugi, Punctum frequentia, double diuturnitas, Vel velocitas) {
        PositionesFixi p = new PositionesFixi(iugi.unda, iugi.volume, iugi.feedback);
        ponoPositiones(p, FREQ, null,
            iugi.map_primo_fco, iugi.map_primo_fca,
            iugi.fixa_frequentia != null ? new Punctum(iugi.fixa_frequentia) : frequentia,
            diuturnitas, velocitas);
        ponoPositiones(p, QUANT, null,
            iugi.map_primo_qco, iugi.map_primo_qca,
            new Punctum(1), diuturnitas, velocitas);
        for (int i = 0; i < Res.publica.channel(); i++) {
            ponoPositiones(p, PAN, i,
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

        Positiones[] modulatores = new Positiones[iugi.pueros.length];
        for (int i = 0; i < iugi.pueros.length; i++) {
            modulatores[i] = capioPositionesFixi(iugi.pueros[i], frequentia, diuturnitas, velocitas);
        }
        p.ponoModulatores(modulatores);
        p.computoLongitudo();

        return p;
    }

    private static void ponoPositiones(
        PositionesFixi p, Rebus res, Integer channel,
        TreeMap<Double, Iugum> map_corporis,
        TreeMap<Double, Iugum> map_caudae,
        Punctum factor, double diuturnitas, Vel velocitas) {
        long ultimo = Functiones.adPositio(diuturnitas);
        //System.out.println("diuturnitas=" + diuturnitas);
        NavigableMap<Double, Iugum> submap1 = map_corporis.subMap(0d, true, diuturnitas, true);
        NavigableMap<Double, Iugum> submap2 = map_corporis.tailMap(diuturnitas, true);
        submap1.keySet().stream().map((key) -> submap1.get(key)).forEach((iugum) -> {
            p.pono(res, channel, iugum.capioPositio(), iugum.capioPunctum(velocitas).multiplico(factor));
        });
        if (!p.habet(res, channel, ultimo)) {
            if (map_caudae.containsKey(0d)) {
                Iugum iugum = map_caudae.get(0d);
                p.pono(res, channel, ultimo, iugum.capioPunctum(velocitas).multiplico(factor));
            } else if (submap2.isEmpty()) {
                p.pono(res, channel, ultimo, submap1.lastEntry().getValue().capioPunctum(velocitas).multiplico(factor));
            } else {
                Double tempus_solum = submap1.lastKey();
                Punctum punctum_solum = submap1.get(tempus_solum).capioPunctum(velocitas);
                long index_solum = Functiones.adPositio(tempus_solum);
                Double tempus_tectum = submap2.firstKey();
                Punctum punctum_tectum = submap2.get(tempus_tectum).capioPunctum(velocitas);
                long index_tectum = Functiones.adPositio(tempus_tectum) + ultimo;
                Punctum punctum = new Punctum();
                for (int i = 0; i < Res.publica.channel(); i++) {
                    punctum.ponoAestimatio(i,
                        punctum_solum.capioAestimatio(i).multiplico(new Aestima(index_tectum - ultimo)).addo(
                        punctum_tectum.capioAestimatio(i).multiplico(new Aestima(ultimo - index_solum))).partior(
                        new Aestima(index_tectum - index_solum)));
                }
                p.pono(res, channel, ultimo, punctum.multiplico(factor));
                //if(res == FREQ) System.out.println("DEBUG4:" + punctum.multiplico(factor));
            }
        }
        NavigableMap<Double, Iugum> submap3 = map_caudae.tailMap(0d, false);
        submap3.keySet().stream().forEach((Double key) -> {
            p.pono(res, channel,
                ultimo + Functiones.adPositio(key),
                submap3.get(key).capioPunctum(velocitas).multiplico(factor));
            //if(res == FREQ) System.out.println("DEBUG5:" + submap3.get(key).capioPunctum(velocitas).multiplico(factor));
        });
    }

    @Override
    public Legibilis capioNotum(double note, double temps, Vel velocitas) {
        return capioOscillationes(
            new Punctum(Temperamentum.instance.capioHZ(note)), temps, velocitas);
    }

    public Oscillationes capioOscillationes(Punctum frequentia, double temps, Vel velocitas) {
        Oscillationes oscillationes = new Oscillationes(acies_iugorum.length);
        for (OscillatorSettings acies_iugorum1 : acies_iugorum) {
            PositionesFixi p = capioPositionesFixi(acies_iugorum1, frequentia, temps, velocitas);
            oscillationes.add(new Oscillatio(p));
        }
        return oscillationes;
    }

    public static void main(String[] args) {
        Oscillator osc = new Oscillator("test",
            new OscillatorSettings(
                //Unda unda,
                //double volume, double feedback,
                Unda.SINE, 1, 10,
                //Iugum[] iuga_frequentiae_corporis,
                new Iugum[]{
                    new Iugum(0, new Punctum(1), new Punctum(1))/*,
                            new Iugum( 100, new Punctum(1), new Punctum(1)),
                            new Iugum(1000, new Punctum(1), new Punctum(1))*/
                },
                //Iugum[] iuga_frequentiae_caudae,
                new Iugum[]{ //new IugumTemporis(1000, new Punctum(1), new Punctum(1))
                },
                //Double fixa_frequentia,
                null,
                //Iugum[] iuga_quantitatis_corporis,
                new Iugum[]{
                    new Iugum(0, new Punctum(0), new Punctum(0)),
                    new Iugum(20, new Punctum(1), new Punctum(1)),
                    new Iugum(500, new Punctum(0.5), new Punctum(0.5))
                },
                //Iugum[] iuga_quantitatis_caudae,
                new Iugum[]{
                    new Iugum(1000, new Punctum(0), new Punctum(0))
                },
                //ArrayList<ArrayList<Iugum>> iuga_pans_corporis,
                null,//new Iugum[]{
                //    new Iugum(0, new Punctum(1, 0), new Punctum(1, 0)), 
                //},
                //ArrayList<ArrayList<Iugum>> iuga_pans_caudae,
                null,//new Iugum[]{
                //    new Iugum(0, new Punctum(0, 1), new Punctum(0, 1)), 
                //    new Iugum(1000, new Punctum(0.5), new Punctum(0.5))
                //},
                //Iugum[] iuga_vco_frequentiae_corporis,
                new Iugum[]{ //new Iugum(0, new Punctum(11.23), new Punctum(11.23))
                },
                //Iugum[] iuga_vco_frequentiae_caudae,
                new Iugum[0],
                //Iugum[] iuga_vco_quantitatis_corporis,
                new Iugum[]{ //new Iugum(0, new Punctum(0.005), new Punctum(0.005))
                },
                //Iugum[] iuga_vco_quantitatis_caudae,
                new Iugum[0],
                //Iugum[] iuga_vca_frequentiae_corporis,
                new Iugum[]{ //new Iugum(0, new Punctum(4), new Punctum(4))
                },
                //Iugum[] iuga_vca_frequentiae_caudae,
                new Iugum[0],
                //Iugum[] iuga_vca_quantitatis_corporis,
                new Iugum[]{ //new Iugum(0, new Punctum(0.02), new Punctum(0.02))
                },
                //Iugum[] iuga_vca_quantitatis_caudae,
                new Iugum[0],
                //Iugum[] iuga_fb_quantitatis_corporis,
                new Iugum[]{new Iugum(0, new Punctum(0), new Punctum(0))},
                //Iugum[] iuga_fb_quantitatis_caudae,
                new Iugum[]{new Iugum(0, new Punctum(1), new Punctum(1))}//,
            //Iuga... pueros) {
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
        double f = Temperamentum.instance.capioHZ(65);
        double a = Temperamentum.instance.capioHZ(69);
        double c = Temperamentum.instance.capioHZ(72);
        double e = Temperamentum.instance.capioHZ(76);
        double g = Temperamentum.instance.capioHZ(79);
        double b = Temperamentum.instance.capioHZ(83);
        Consilium cns = new Consilium();
        cns.addo(0, osc.capioOscillationes(new Punctum(a), 3000, new Vel(1)));
        /*cns.addo(   0, osc.capioOscillationes(new Punctum(f), 5000, new Velocitas(1)));
        cns.addo( 500, osc.capioOscillationes(new Punctum(a), 5500, new Velocitas(1)));
        cns.addo(1000, osc.capioOscillationes(new Punctum(c), 6000, new Velocitas(1)));
        cns.addo(1500, osc.capioOscillationes(new Punctum(e), 6500, new Velocitas(1)));
        cns.addo(2000, osc.capioOscillationes(new Punctum(g), 7000, new Velocitas(1)));
        cns.addo(2500, osc.capioOscillationes(new Punctum(b), 7500, new Velocitas(1)));
         */
        File out_file = new File(OmUtil.getDirectory("opus"), "oscillatior.wav");
        ScriptorWav sw = new ScriptorWav(out_file);

        sw.scribo(cns, false);
        //sw.scribo(new FIRFilterDeinde(cns, 1000, 500, true), false);
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

    @Override
    public String getName() {
        return "osc_" + name;
    }

}
