/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor;

import java.util.HashMap;

/**
 *
 * @author mina
 */
public class Res{
    public static Res publica = new Res();
    private final HashMap<String, Object> map;
    private Res(){
        map = new HashMap<>();
        map.put("CHANNEL", 2);
        map.put("SAMPLE_RATE", 48000);
        map.put("SAMPLE_SIZE", 2);
    }
    public int channel(){
        return (Integer)map.get("CHANNEL");
    }
    public void ponoChannel(int channel){
        map.put("CHANNEL", channel);
    }
    public int sampleRate(){
        return (Integer)map.get("SAMPLE_RATE");
    }
    public double sampleRateDouble(){
        return (Integer)map.get("SAMPLE_RATE");
    }
    public void ponoSampleRate(int sample_rate){
        map.put("SAMPLE_RATE", sample_rate);
    }
    public int sampleSize(){
        return (Integer)map.get("SAMPLE_SIZE");
    }
    public void ponoSampleSize(int sample_size){
        map.put("SAMPLE_SIZE", sample_size);
        map.put("MAX_AMP", new Aestima(Math.pow(2, sample_size * 8 - 1) - 1));
        map.put("MIN_AMP", new Aestima(Math.pow(2, sample_size * 8 - 1) * -1));
    }
    public Aestima maxAmplitudo(){
        return (Aestima)map.get("MAX_AMP");
    }
    public Aestima minAmplitudo(){
        return (Aestima)map.get("MIN_AMP");
    }
    
}
