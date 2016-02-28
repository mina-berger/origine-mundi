/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package origine_mundi.deprec;

import la.clamor.Constantia;
import la.clamor.Legibilis;
import la.clamor.Puncta;
import la.clamor.Punctum;
import la.clamor.Aestimatio;

/**
 *
 * @author Mina
 */
public class FIRFilterDprec implements Constantia, Legibilis {
    public static final int TAP = 501;
    double[] tap_array = null;
    Buffer buffer;
    Legibilis legibilis;
    Aestimatio volume_ex;
    Aestimatio volume_total;
    
    public FIRFilterDprec(Legibilis legibilis, double cutoff_freq, boolean lpf, boolean hamming, Aestimatio volume){
        this.legibilis = legibilis;
        this.volume_ex = volume;
        buffer = new Buffer();
        updateTap(cutoff_freq, lpf, hamming);
    }
    public void updateTap(double cutoff_freq, boolean lpf, boolean hamming){
        tap_array = new double[TAP];
        int tap_m = ((TAP - 1) / 2);
        double omega_lpf = Math.PI * ((cutoff_freq / REGULA_EXAMPLI_D) * 2d);
        double omega_hpf = Math.PI * (1d - ((cutoff_freq / REGULA_EXAMPLI_D) * 2d));
        double h_center  = 1d - (cutoff_freq / (REGULA_EXAMPLI_D / 2d));
        
        double h = 0d;
        double w_hamming = 0d;
        double w_hanning = 0d;
        double h_win_lpf = 0d;
        double h_win_hpf = 0d;

        // LPF
        if (lpf) {
            for (int i = 1;i <= tap_m;i++) {
                h = Math.sin(i * omega_lpf) / (Math.PI * i);
                if(hamming) {
                    w_hamming = 0.54 + 0.46 * Math.cos((i * Math.PI) / (tap_m));
                    h_win_lpf = h * w_hamming;
                }else{
                    w_hanning = 0.5 + 0.5 * Math.cos((i * Math.PI) / (tap_m));
                    h_win_lpf = h * w_hanning;
                }
                tap_array[tap_m + i] = h_win_lpf;
                tap_array[tap_m - i] = h_win_lpf;
            }
            tap_array[tap_m] = 1.0 - h_center;
        }else{
            for (int i = 1;i <= tap_m;i++) {
                h = Math.sin(i * omega_hpf) / (Math.PI * i);
                if(hamming) {
                    w_hamming = 0.54 + 0.46 * Math.cos((i * Math.PI) / (tap_m));
                    h_win_lpf = h * w_hamming;
                }else{
                    w_hanning = 0.5 + 0.5 * Math.cos((i * Math.PI) / (tap_m));
                    h_win_lpf = h * w_hanning;
                }
                h_win_hpf = h_win_lpf * Math.pow(-1, i);
                tap_array[tap_m + i] = h_win_hpf;
                tap_array[tap_m - i] = h_win_hpf;
            }
            tap_array[tap_m] = h_center;
        }
        double ratio = 0;
        for (int i = 0; i < tap_array.length; i++) {
            ratio += tap_array[i];
        }
        volume_total = new Aestimatio(1d / Math.abs(ratio));
        //System.out.println("VOLUME:" + volume_total);
        
    }

    @Override
    public Punctum lego() {
        Punctum current = legibilis.lego();
        buffer.set(0, current);
        Punctum punctum = new Punctum();
        for(int i = 0;i < TAP;i++){
            punctum = punctum.addo(buffer.get(i).multiplico(tap_array[i]));
        }
        //System.out.println(current + ":" + buffer.get(0) + ":" + punctum);
        buffer.next();
        return punctum.multiplico(volume_total);
    }

    @Override
    public boolean paratusSum() {
        return legibilis.paratusSum();
    }
    public static class Buffer{
        Puncta buffer;
        int point;
        Buffer(){
            buffer = new Puncta(TAP);
            point = 0;
        }
        void next(){
            point++;
            point %= TAP;
        }
        Punctum get(int index){
            return buffer.capioPunctum((point - index + TAP) % TAP);
        }
        void set(int index, Punctum value){
            buffer.ponoPunctum((point - index + TAP) % TAP, value);
        }
        //void setLast(Punctum value){
        //    set(TAP - 1, value);
       // }
        
    }
    public static void main(String[] args){
        Buffer buf = new Buffer();
        buf.set(0, new Punctum(1));
        System.out.println(buf.get(0));
        buf.next();
        buf.set(0, new Punctum(2));
        System.out.println(buf.get(0));
        System.out.println(buf.get(1));
        
    }
}
