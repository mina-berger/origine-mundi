/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package origine_mundi.ludior;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 *
 * @author minae.hiyamae
 */
public class Tempus {
    TreeMap<Integer, Double> map_comitis;
    TreeMap<Double, Rapidus> map_rapidi;
    public Tempus(Comes[] comites, Rapidus[] rapidi){
        map_comitis = new TreeMap<>();
        for(Comes comes:comites){
            map_comitis.put(comes.talea, comes.comes);
        }
        map_comitis.putIfAbsent(0, 4d);
        map_rapidi = new TreeMap<>();
        for(Rapidus rapidus:rapidi){
            map_rapidi.put(capioPulsum(rapidus.talea, rapidus.repenso), rapidus);
        }
        Rapidus rapidus_temporis = new Rapidus(0, 0d, 60d, true);
        double pulsus_temporis = 0;
        double tempus_temporis = 0;
        map_rapidi.putIfAbsent(0d, rapidus_temporis);
        for(Double pulsus:map_rapidi.keySet()){
            Rapidus rapidus = map_rapidi.get(pulsus);
            //double pre_pulses_temporis = pulsus_temporis;
            if(rapidus_temporis.fixus_est){
                tempus_temporis = tempus_temporis + (pulsus - pulsus_temporis) * 60000d / rapidus_temporis.rapidus;
            }else{
                tempus_temporis = 
                        tempus_temporis + (pulsus - pulsus_temporis) * 60000d / 
                        ((rapidus.rapidus + rapidus_temporis.rapidus) / 2d);
            }
            rapidus.tempus = tempus_temporis;
            rapidus_temporis = rapidus;
            pulsus_temporis = pulsus;
        }
        
    }
    public double capioTempus(int talea, double repenso){
        double pulsus = capioPulsum(talea, repenso);
        if(pulsus < 0){
            System.out.println("pulsus=" + pulsus + ":talea=" + talea + ":repenso=" + repenso);
        }
        double clavis_solum = map_rapidi.floorKey(pulsus);
        Rapidus rapidus_solum = map_rapidi.get(clavis_solum);
        if(rapidus_solum.fixus_est){
            return rapidus_solum.tempus + (pulsus - clavis_solum) * 60000d / rapidus_solum.rapidus;
        }
        Double clavis_tectum = map_rapidi.ceilingKey(pulsus);
        Rapidus rapidus_tectum;
        if(clavis_tectum == null){
            clavis_tectum = pulsus;
            rapidus_tectum = rapidus_solum;
        }else{
            rapidus_tectum = map_rapidi.get(clavis_tectum);
        }
        double rapidus;
        if(clavis_tectum == clavis_solum){
            rapidus = rapidus_solum.rapidus;
        }else{
            rapidus = rapidus_solum.rapidus + (rapidus_tectum.rapidus - rapidus_solum.rapidus) * 
                (pulsus - clavis_solum) / (clavis_tectum - clavis_solum);
        }
        double tempus = rapidus_solum.tempus + (pulsus - clavis_solum) * 60000d / 
                ((rapidus_solum.rapidus + rapidus) / 2d);
        return tempus;
    }
    private double capioPulsum(int talea, double repenso){
        double comes = map_comitis.floorEntry(talea).getValue();
        if(repenso > comes){
            return capioPulsum(talea + 1, repenso - comes);
        }
        SortedMap<Integer, Double> submap_comitis = map_comitis.headMap(talea);
        
        int talea_temporis = 0;
        double comes_temporis = 4;
        
        double pulsum = 0;
        for(Integer clavis:submap_comitis.keySet()){
            pulsum += (double)(clavis - talea_temporis) * comes_temporis;
            talea_temporis = clavis;
            comes_temporis = submap_comitis.get(clavis);
        }
        pulsum += (double)(talea - talea_temporis) * comes_temporis;
        return pulsum + repenso;
    }
    public static void main(String[] args){
        Tempus tf = new Tempus(
                new Comes[]{new Comes(0, 4)}, new Rapidus[]{new Rapidus(2, 0, 120, true), new Rapidus(4, 0, 60, false)});
        System.out.println(tf.capioPulsum(0, 0) + ":" + tf.capioTempus(0, 0));
        System.out.println(tf.capioPulsum(0, 1) + ":" + tf.capioTempus(0, 1));
        System.out.println(tf.capioPulsum(1, 0) + ":" + tf.capioTempus(1, 0));
        System.out.println(tf.capioPulsum(3, 0) + ":" + tf.capioTempus(3, 0));
        System.out.println(tf.capioPulsum(3, 2) + ":" + tf.capioTempus(3, 2));
    }
    public static class Comes{
        int talea;
        double comes;
        public Comes(int talea, double comes){
            this.talea = talea;
            this.comes = comes;
        }
    }
    public static class Rapidus {
        int talea;
        double repenso;
        double rapidus;
        boolean fixus_est;
        Double tempus;
        public Rapidus(int talea, double repenso, double rapidus, boolean fixus_est){
            this.talea = talea;
            this.repenso = repenso;
            this.rapidus = rapidus;
            this.fixus_est = fixus_est;
            tempus = null;
        }
    }
}
