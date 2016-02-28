/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package la.clamor.opus;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 *
 * @author minae.hiyamae
 */
public class Taleae {
    TreeMap<Integer, Double> map_comitis;
    TreeMap<Double, Rapidus> map_rapidi;
    public Taleae(Comes[] comites, Rapidus[] rapidi){
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
            //if(pulsus_temporis < 0){
            //    System.out.println("Taelae initial:pulsus_temporis=" + pulsus_temporis + ":rapidus_temporis.fixus_est=" + rapidus_temporis.fixus_est);
            //    System.out.println("rapidus.rapidus=" + rapidus.rapidus + ":rapidus_temporis.rapidus=" + rapidus_temporis.rapidus);
            //    System.out.println("pulsus=" + pulsus + ":pre_pulses_temporis=" + pre_pulses_temporis + ":pulsus_temporis=" + pulsus_temporis);
            //}
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
        //if(talea == 56 && repenso == 0){
        //    System.out.println("tempus=" + tempus + ":talea=" + talea + ":repenso=" + repenso);
        //    System.out.println("rapidus_solum.tempus=" + rapidus_solum.tempus + ":pulsus=" + pulsus + ":clavis_solum=" + clavis_solum);
        //    System.out.println("rapidus_solum.rapidus=" + rapidus_solum.rapidus + ":rapidus_tectum.rapidus=" + rapidus_tectum.rapidus + ":rapidus=" + rapidus);
        //    System.out.println("pulsus=" + pulsus + ":clavis_solum=" + clavis_solum + ":clavis_tectum=" + clavis_tectum);
        //    System.out.println();
        //}
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
