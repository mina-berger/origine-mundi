/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package la.clamor;

import java.io.File;
import java.util.ArrayList;
import java.util.TreeMap;
import org.apache.commons.lang3.ArrayUtils;
import origine_mundi.filter.FIRFilter;

/**
 *
 * @author minae.hiyamae
 */
public class Consilium extends TreeMap<Long, ArrayList<Legibilis>> implements Legibilis {
    Legibilis[] pueros;
    long index;
    Positiones volumes;
    public Consilium(){
        pueros = new Legibilis[0];
        volumes = new Positiones(true);
        index = 0;
    }
    public void setPositiones(Positiones volumes){
        this.volumes = volumes;
    }
    
    public void addo(double tempus, Legibilis legibilis){
        long positio = Functiones.adPositio(tempus);
        ArrayList<Legibilis> list;
        if(containsKey(positio)){
            list = get(positio);
        }else{
            list = new ArrayList<>();
            put(positio, list);
        }
        list.add(legibilis);
    }
    @Override
    public Punctum lego() {
        Punctum punctum = new Punctum();
        for(Legibilis legibilis:pueros){
            punctum = punctum.addo(legibilis.lego()).multiplico(volumes.capioPunctum(index));
        }
        index++;
        return punctum;
    }

    @Override
    public boolean paratusSum() {
        if(containsKey(index)){
            get(index).stream().forEach((legibilis) -> {
                pueros = ArrayUtils.add(pueros, legibilis);
            });
            remove(index);
            //System.out.println("this.removed:" + size() + "/" + pueros.length);
        }
        for(Legibilis legibilis:pueros){
            if(!legibilis.paratusSum()){
                pueros = ArrayUtils.removeElement(pueros, legibilis);
                //System.out.println("puer.removed:" + size() + "/" + pueros.length);
            }
        }
        //System.out.println(isEmpty() + ":" + pueros.length);
        return !isEmpty() || pueros.length > 0;
    }
    public static void main(String[] args){
        File lima1 = new File("doc/sample/lima01.lima");
        File lima2 = new File("doc/sample/lima02.lima");
        File trg = new File("doc/sample/sample03.wav");
        boolean skip = true;

        if(!skip){
            Aestimatio volume = new Aestimatio(1);
            //double cutoff = 10000;
            Consilium cns = new Consilium();
            //cns.addo(0, new LectorLimam(lima1));
            //cns.addo(1000, new LectorLimam(lima2));
            //ZTransformare zt1 = new ZTransformare(Butterworth.capioModulum(5, 1000, false));
            //zt1.ponoFons(new LectorLimam(lima1));
            //cns.addo(0, zt1);
            
            //ZTransformare zt2 = new ZTransformare(Butterworth.capioModulum(10, 20000, false));
            //zt2.ponoFons(new LectorLimam(lima2));
            //cns.addo(1000, zt2);
            Envelope env1 = new Envelope(new Punctum(1));
            env1.ponoPunctum(100, new Punctum(1));
            env1.ponoPunctum(120, new Punctum(0));
            Envelope env2 = new Envelope(new Punctum(1));
            env2.ponoPunctum(50, new Punctum(1));
            env2.ponoPunctum(60, new Punctum(0));
            Envelope env3 = new Envelope(new Punctum(1));
            env3.ponoPunctum(20, new Punctum(1));
            env3.ponoPunctum(30, new Punctum(0));
            int measure = 16;
            double temp_beat = 500;
            for(int i = 0;i < measure;i++){
                double cutoff1 = 20000;// i % 2 == 0?1000:20000;
                double cutoff2 = 5000;// i % 2 == 0?1000:20000;
                boolean lpf = true;
                double m_temp = i * (temp_beat * 4);
                int count = 0;
                double back = 32;
                cns.addo(m_temp + count++ * temp_beat / 4d, new FIRFilter(new EnvelopeFilter(new LectorLimam(lima1), env1, 125, new Aestimatio(1)), cutoff1, lpf, true, volume));
                cns.addo(m_temp + count++ * temp_beat / 4d + back, new FIRFilter(new EnvelopeFilter(new LectorLimam(lima2), env1, 125, new Aestimatio(0.8)), cutoff1, lpf, true, volume));
                cns.addo(m_temp + count++ * temp_beat / 4d, new FIRFilter(new EnvelopeFilter(new LectorLimam(lima1), env3, 125, new Aestimatio(0.5)), cutoff2, lpf, true, volume));
                cns.addo(m_temp + count++ * temp_beat / 4d + back, new FIRFilter(new EnvelopeFilter(new LectorLimam(lima2), env2, 125, new Aestimatio(0.8)), cutoff1, lpf, true, volume));
                cns.addo(m_temp + count++ * temp_beat / 4d, new FIRFilter(new EnvelopeFilter(new LectorLimam(lima1), env3, 125, new Aestimatio(0.5)), cutoff2, lpf, true, volume));
                cns.addo(m_temp + count++ * temp_beat / 4d + back, new FIRFilter(new EnvelopeFilter(new LectorLimam(lima2), env1, 125, new Aestimatio(1)), cutoff1, lpf, true, volume));
                cns.addo(m_temp + count++ * temp_beat / 4d, new FIRFilter(new EnvelopeFilter(new LectorLimam(lima1), env3, 125, new Aestimatio(0.5)), cutoff2, lpf, true, volume));
                cns.addo(m_temp + count++ * temp_beat / 4d + back, new FIRFilter(new EnvelopeFilter(new LectorLimam(lima2), env3, 125, new Aestimatio(0.5)), cutoff2, lpf, true, volume));
                cns.addo(m_temp + count++ * temp_beat / 4d, new FIRFilter(new EnvelopeFilter(new LectorLimam(lima1), env1, 125, new Aestimatio(1.0)), cutoff1, lpf, true, volume));
                cns.addo(m_temp + count++ * temp_beat / 4d + back, new FIRFilter(new EnvelopeFilter(new LectorLimam(lima2), env1, 125, new Aestimatio(0.8)), cutoff1, lpf, true, volume));
                cns.addo(m_temp + count++ * temp_beat / 4d, new FIRFilter(new EnvelopeFilter(new LectorLimam(lima1), env3, 125, new Aestimatio(0.5)), cutoff2, lpf, true, volume));
                cns.addo(m_temp + count++ * temp_beat / 4d + back, new FIRFilter(new EnvelopeFilter(new LectorLimam(lima2), env2, 125, new Aestimatio(0.8)), cutoff1, lpf, true, volume));
                cns.addo(m_temp + count++ * temp_beat / 4d, new FIRFilter(new EnvelopeFilter(new LectorLimam(lima1), env3, 125, new Aestimatio(0.5)), cutoff2, lpf, true, volume));
                cns.addo(m_temp + count++ * temp_beat / 4d + back, new FIRFilter(new EnvelopeFilter(new LectorLimam(lima2), env3, 125, new Aestimatio(0.5)), cutoff2, lpf, true, volume));
                cns.addo(m_temp + count++ * temp_beat / 4d, new FIRFilter(new EnvelopeFilter(new LectorLimam(lima1), env1, 125, new Aestimatio(1.0)), cutoff1, lpf, true, volume));
                cns.addo(m_temp + count++ * temp_beat / 4d + back, new FIRFilter(new EnvelopeFilter(new LectorLimam(lima2), env3, 125, new Aestimatio(0.5)), cutoff2, lpf, true, volume));
            }
            //cns.addo(3000, new FIRFilter(new LectorLimam(lima2), cutoff, false, true, volume));
            ScriptorWav sw = new ScriptorWav(trg);
            sw.scribo(cns, false);
        }
        Functiones.ludoLimam(trg);
                
    }
    
}
