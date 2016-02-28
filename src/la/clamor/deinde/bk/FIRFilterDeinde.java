/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor.deinde.bk;

import static la.clamor.Constantia.REGULA_EXAMPLI_D;
import la.clamor.Functiones;
import la.clamor.Legibilis;
import la.clamor.OrbisPuncti;
import la.clamor.Punctum;
import la.clamor.forma.FIRFilter;
import la.clamor.LegibileAbstractum;

/**
 *
 * @author mina
 */
public class FIRFilterDeinde extends LegibileAbstractum {
    int j;
    double[] b;
    private final OrbisPuncti oa;

    public FIRFilterDeinde(Legibilis fons, double freq_co, double range, boolean lpf) {
        super(fons);
        double fe    = freq_co / REGULA_EXAMPLI_D;
        double delta = range   / REGULA_EXAMPLI_D;
        j = (int)(3.1 / delta + 0.5) - 1;
        if(j % 2 == 1){
            j++;
        }
        double[] w = Functiones.hanningWindow(j + 1);
        //b = getLPF(fe, j, w, lpf);
        b = FIRFilter.getLpfHpf(fe, j, w, lpf);
        oa = new OrbisPuncti(j + 1);
    }
    /*public static double[] getLPF(double fe, int j, double[] w, boolean lpf){
        int offset = j / 2;
        double[] b = new double[w.length];
        Sinc sinc = new Sinc();
        for(int m = -j / 2;m <= j / 2;m++){
            if(lpf){
                b[offset + m] = 2.0 * fe * sinc.value(2.0 * FastMath.PI * fe * m);
            }else{
                b[offset + m] = sinc.value(FastMath.PI * m) - 2.0 * fe * sinc.value(2.0 * FastMath.PI * fe * m);
            }
        }
        for(int m = 0;m < j + 1;m++){
            b[m] *= w[m];
        }
        return b;
    }*/
    public FIRFilterDeinde(Legibilis fons, double freq_co1, double freq_co2, double range, boolean bpf) {
        super(fons);
        double fe1   = freq_co1 / REGULA_EXAMPLI_D;
        double fe2   = freq_co2 / REGULA_EXAMPLI_D;
        double delta = range    / REGULA_EXAMPLI_D;
        j = (int)(3.1 / delta + 0.5) - 1;
        if(j % 2 == 1){
            j++;
        }
        double[] w = Functiones.hanningWindow(j + 1);
        b = FIRFilter.getBpfBef(fe1, fe2, j, w, bpf);
        oa = new OrbisPuncti(j + 1);
    }
    /*public static double[] getLPF(double fe1, double fe2, int j, double[] w, boolean bpf){
        int offset = j / 2;
        double[] b = new double[w.length];
        Sinc sinc = new Sinc();
        for(int m = -j / 2;m <= j / 2;m++){
            if(bpf){
                b[offset + m] = 2.0 * fe2 * sinc.value(2.0 * FastMath.PI * fe2 * m) 
                              - 2.0 * fe1 * sinc.value(2.0 * FastMath.PI * fe1 * m);
            }else{
                b[offset + m] = sinc.value(FastMath.PI * m)
                              - 2.0 * fe2 * sinc.value(2.0 * FastMath.PI * fe2 * m) 
                              + 2.0 * fe1 * sinc.value(2.0 * FastMath.PI * fe1 * m);
            }
        }
        for(int m = 0;m < j + 1;m++){
            b[m] *= w[m];
        }
        return b;
    }*/

    @Override
    public Punctum lego() {
        oa.pono(legoAFontem());
        Punctum p = new Punctum();
        for(int m = 0;m <= j;m++){
            p = p.addo(oa.capio(m).multiplico(b[m]));
        }
        return p;
    }

    @Override
    public boolean paratusSum() {
        return fonsParatusEst();
    }
    
}
