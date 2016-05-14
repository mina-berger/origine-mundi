/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor;

import la.clamor.io.ScriptorWav;
import com.mina.util.Mjson;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import com.mina.util.Mjson.MjsonElement;
import com.mina.util.Mjson.MjsonList;
import com.mina.util.Mjson.MjsonMap;
import com.mina.util.Mjson.MjsonSingle;
import static com.mina.util.Mjson.list;
import static com.mina.util.Mjson.map;
import la.clamor.forma.FIRFilter;
import com.mina.util.CSVFile;
import la.clamor.io.IOUtil;
import origine_mundi.OmUtil;

/**
 *
 * @author mina
 */
public class OscillatorUtil {

    public static Iugum[] toIugumArray(MjsonElement elem) {
        if (elem == null) {
            return new Iugum[0];
        }
        MjsonElement[] elems = Mjson.toArray(elem);
        if (elems == null) {
            return new Iugum[0];
        }
        Iugum[] array = new Iugum[elems.length];
        for (int i = 0; i < elems.length; i++) {
            //System.out.println(elems[i]);
            array[i] = toIugum(elems[i]);
        }
        return array;
    }

    public static Iugum toIugum(MjsonElement elem) {
        Mjson mjson = new Mjson(elem);
        return new Iugum(
            mjson.getDouble("tempus", true),
            mjson.getInteger("index", false),
            toPunctum(mjson.get("altum"), true),
            toPunctum(mjson.get("humile"), false));
    }

    public static Punctum toPunctum(MjsonElement elem, boolean strict) {
        if (elem == null) {
            if (strict) {
                throw new IllegalArgumentException("bad element:" + elem.getClass().getSimpleName());
            }
            return null;
        } else if (elem instanceof MjsonList) {
            MjsonList list = (MjsonList) elem;
            if (list.isEmpty()) {
                if (strict) {
                    throw new IllegalArgumentException("empty punctum:" + elem.getClass().getSimpleName());
                }
                return null;
            } else if (list.size() >= Res.publica.channel()) {
                double[] aestimationes = new double[Res.publica.channel()];
                for (int i = 0; i < Res.publica.channel(); i++) {
                    aestimationes[i] = Mjson.toDouble(list.get(i));
                }
                return new Punctum(aestimationes);
            }
            return new Punctum(Mjson.toDouble(list.get(0)));
        } else if (elem instanceof MjsonSingle) {
            try {
                return new Punctum(((MjsonSingle) elem).getDouble());
            } catch (RuntimeException e) {
                if (strict) {
                    throw new IllegalArgumentException("illegal type for punctum:" + elem.getClass().getSimpleName());
                }
                return null;
            }
        } else if (strict) {
            throw new IllegalArgumentException("illegal type for punctum:" + elem.getClass().getSimpleName());
        }
        return null;
    }

    private static MjsonMap readCorpTail(CSVFile csv, int row, int col) {
        MjsonList corp = Mjson.list();
        for (int i = 0; i < 4; i++) {
            if (csv.isEmpty(row, col + i)) {
                break;
            }
            corp.add(toUnit(csv.getString(row, col + i)));
        }
        MjsonList tail = Mjson.list();
        for (int i = 4; i < 6; i++) {
            if (csv.isEmpty(row, col + i)) {
                break;
            }
            tail.add(toUnit(csv.getString(row, col + i)));
        }
        return map().unit("corp", corp).unit("tail", tail);
    }

    private static MjsonMap toUnit(String str) {
        if (str == null || str.isEmpty()) {
            return null;
        }
        String[] strs = str.split(":");
        MjsonMap map = map();
        Double tempus = CSVFile.toDouble(strs[0]);
        if (tempus == null) {
            throw new IllegalArgumentException("illegal unit setting:" + str);
        }
        map.unit("tempus", tempus);
        if (strs.length == 1) {
            map.unit("altum", 0d);
        } else {
            map.unit("altum", toDoubleValue(strs[1]));
        }
        if (strs.length >= 3) {
            map.unit("humile", toDoubleValue(strs[2]));
        }
        return map;
    }

    private static MjsonElement toDoubleValue(String str) {
        if (str.startsWith("[") && str.endsWith(("]"))) {
            String[] strs = str.substring(1, str.length() - 1).split(":");
            Double[] ret = new Double[strs.length];
            for (int i = 0; i < strs.length; i++) {
                ret[i] = CSVFile.toDouble(strs[i]);
            }
            return new MjsonList((Object[]) ret);
        } else {
            return new MjsonSingle(CSVFile.toDouble(str));
        }
    }

    private static void fillMap(MjsonMap all_map, MjsonList list, String search_key) {
        for (String key : all_map.keySet()) {
            MjsonMap map = (MjsonMap) all_map.get(key);
            //System.out.println(key + " has out:" + new Mjson(map).get("out"));
            List<String> outs = Arrays.asList(new Mjson(map).getAsStringArray("out"));
            if (outs.contains(search_key)) {
                System.out.println(key + " has search key:" + search_key);
                MjsonList pueros = list();
                fillMap(all_map, pueros, key);
                list.add(map);
            }
        }

    }

    private static MjsonList toOuts(String str) {
        if (str.isEmpty()) {
            return list();
        }
        return list((Object[]) str.split(":"));
    }

    public static void _main(String[] args) throws IOException {
        Oscillator osc = new Oscillator("epiano");
        double a = Temperamentum.instance.capioHZ(69);
        Consilium cns = new Consilium();
        cns.addo(0, osc.capioOscillationes(new Punctum(a), 3000, new Vel(1)));
        File out_file = new File(IOUtil.getDirectory("opus"), "oscillatior.wav");
        ScriptorWav sw = new ScriptorWav(out_file);

        sw.scribo(cns, false);
        //sw.scribo(new FIRFilterDeinde(cns, 3000, 500, true), false);
        Functiones.ludoLimam(out_file);
    }

    public static Mjson getPreset(String name) {
        CSVFile csv;
        try {
            csv = new CSVFile("doc/oscillator/" + name + ".csv");
        } catch (IOException ex) {
            throw new IllegalArgumentException("preset inexists:" + name);
        }
        MjsonMap all_map = Mjson.map();
        int row = 0;
        while (true) {
            MjsonMap map = Mjson.map();
            if (csv.isEmpty(row, 0)) {
                break;
            }
            String id = csv.getString(row, 0);
            //map.unit("id",   csv.getString(row, 0));
            map.unit("out", toOuts(csv.getString(row, 1)));
            map.unit("unda", csv.getString(row, 2));
            map.unit("volume", csv.getDouble(row, 3));
            map.unit("feedback", csv.getDouble(row, 4));
            map.unit("freq", readCorpTail(csv, row, 5));
            map.unit("amp", readCorpTail(csv, row, 11));
            map.list("pan", readCorpTail(csv, row + 1, 5), readCorpTail(csv, row + 1, 11));
            map.unit("vco", map()
                .unit("freq", readCorpTail(csv, row + 2, 5))
                .unit("amp", readCorpTail(csv, row + 2, 11)));
            map.unit("vca", map()
                .unit("freq", readCorpTail(csv, row + 3, 5))
                .unit("amp", readCorpTail(csv, row + 3, 11)));
            //new Mjson(map).print();
            all_map.put(id, map);
            row += 5;
        }
        MjsonList list = list();
        for (String key : all_map.keySet()) {
            MjsonMap map = (MjsonMap) all_map.get(key);
            String[] outs = new Mjson(map).getAsStringArray("out");
            //System.out.println(outs.length);
            if (outs.length == 0) {
                MjsonList pueros = list();
                fillMap(all_map, pueros, key);
                map.unit("pueros", pueros);
                list.add(map);
            }
        }
        return new Mjson(list);
    }

    public static void main(String[] args) {
        Mjson json = new Mjson(list(
            map()
            .unit("unda", "quad")
            .unit("volume", 1)
            .unit("feedback", 10)
            .unit("freq", map()
                .list("corp",
                    map().unit("tempus", 0).unit("altum", 1))
                .list("tail"))
            .unit("amp", map()
                .list("corp",
                    map().unit("tempus", 0).unit("altum", 0),
                    map().unit("tempus", 20).unit("altum", 1),
                    map().unit("tempus", 500).unit("altum", 0.5))
                .list("tail",
                    map().unit("tempus", 1000).unit("altum", 0)))
            .unit("pans", map()
                .list("corp")
                .list("tail"))
            //ArrayList<ArrayList<Iugum>> iuga_pans_corporis,
            //ArrayList<ArrayList<Iugum>> iuga_pans_caudae,
            .unit("vco", map()
                .unit("freq", map()
                    .list("corp"/*,
                        map().unit("tempus", 0).unit("altum", 4)*/)
                    .list("tail"/*,
                        map().unit("tempus", 0).unit("altum", 100)*/))
                .unit("amp", map()
                    .list("corp"/*,
                        map().unit("tempus", 0).unit("altum", 0.05)*/)
                    .list("tail")))
            .unit("vca", map()
                .unit("freq", map()
                    .list("corp")
                    .list("tail"))
                .unit("amp", map()
                    .list("corp")
                    .list("tail")))
            .unit("fb", map()
                .list("corp",
                    map().unit("tempus", 0).unit("altum", 1))
                .list("tail",
                    map().unit("tempus", 0).unit("altum", 1)))
            .list("pueros", map()
                .unit("unda", "sine")
                .unit("volume", 0.4)
                .unit("feedback", 0)
                .unit("freq", map()
                    .list("corp",
                        map().unit("tempus", 0).unit("altum", 1))
                    .list("tail",
                        map().unit("tempus", 0).unit("altum", 1)))
                //.unit("freq_fix")
                .unit("amp", map()
                    .list("corp",
                        map().unit("tempus", 0).unit("altum", 1))
                    .list("tail",
                        map().unit("tempus", 1000).unit("altum", 0)))
                .unit("fb", map()
                    .list("corp",
                        map().unit("tempus", 0).unit("altum", 0))
                    .list("tail",
                        map().unit("tempus", 0).unit("altum", 0))))
        ));

        Oscillator osc = new Oscillator("test", OscillatorSettings.toSettingsArray(json));
        double a = Temperamentum.instance.capioHZ(69);
        Consilium cns = new Consilium();
        cns.addo(0, osc.capioOscillationes(new Punctum(a), 5000, new Vel(1)));
        File out_file = new File(IOUtil.getDirectory("opus"), "quad.wav");
        ScriptorWav sw = new ScriptorWav(out_file);

        //sw.scribo(cns, false);
        //sw.scribo(new FIRFilterDeinde(cns, 1000, 500, true), false);
        sw.scribo(new Legibilis() {
            FIRFilter f1 = new FIRFilter(500, 1000, true);
            FIRFilter f2 = new FIRFilter(500, 1000, true);
            int index = 0;

            @Override
            public Punctum lego() {
                //return f.filter(cns.lego(), 1000, true);
                index++;
                //return f.filter(cns.lego(), 
                //    200  + (index % 12000 > 6000?1000:0), 
                //    2000 + (index % 12000 > 6000?1000:0), true);
                Punctum lectum = cns.lego();
                return new Punctum(
                    f1.formo(lectum,
                        //200  + (double)index /  (48000. * 5.) * 1000, 
                        2000 + ((double) index / (48000. * 5.) * 2000) % 2000, true).capioAestima(0).doubleValue()
                //,
                //f2.filter(lectum, 
                //200  + (1000. - (double)index /  (48000. * 5.) * 1000), 
                //2000 + (1000. - (double)index /  (48000. * 5.) * 1000), true).capioAestimatio(0).doubleValue() 
                );

            }

            @Override
            public boolean paratusSum() {
                return cns.paratusSum();
            }

            @Override
            public void close() {
            }
        }, false);

        Functiones.ludoLimam(out_file);

    }

}
