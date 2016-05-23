/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor.forma;

import la.clamor.Functiones;
import la.clamor.OrbisPuncti;
import la.clamor.Punctum;
import la.clamor.Res;
import static la.clamor.forma.FIRFilter.FilterType.BEF;
import static la.clamor.forma.FIRFilter.FilterType.BPF;
import static la.clamor.forma.FIRFilter.FilterType.HPF;
import static la.clamor.forma.FIRFilter.FilterType.LPF;
import org.apache.commons.math3.analysis.function.Sinc;
import org.apache.commons.math3.util.FastMath;

/**
 *
 * @author mina
 */
public class FIRFilter implements Forma {

    enum FilterType {
        LPF, HPF, BPF, BEF
    };
    int size;
    double[] window;
    double[] b;
    private final OrbisPuncti oa;

    //public FIRFilter(double range) {
    //    this(range, LPF, 1000, 1000);
    //}
    public FIRFilter(double range, double freq, boolean is_lpf) {
        this(range, is_lpf ? LPF : HPF, freq, freq);
    }

    public FIRFilter(double range, double freq1, double freq2, boolean is_bpf) {
        this(range, is_bpf ? LPF : HPF, freq1, freq2);
    }

    private FIRFilter(double range, FilterType type, double freq1, double freq2) {
        double delta = range / Res.publica.sampleRateDouble();
        size = (int) (3.1 / delta + 0.5) - 1;
        if (size % 2 == 1) {
            size++;
        }
        window = Functiones.hanningWindow(size + 1);
        oa = new OrbisPuncti(size + 1);
        b = initFilter(type, freq1, freq2);
    }

    private double[] initFilter(FilterType type, double freq1, double freq2) {
        double fe1 = freq1 / Res.publica.sampleRateDouble();
        double fe2 = freq2 / Res.publica.sampleRateDouble();
        switch (type) {
            case LPF:
                return getLpfHpf(fe1, size, window, true);
            case HPF:
                return getLpfHpf(fe1, size, window, false);
            case BPF:
                return getBpfBef(fe1, fe2, size, window, true);
            case BEF:
                return getBpfBef(fe1, fe2, size, window, false);
            default:
                throw new IllegalArgumentException("unknown FilterType:" + type);
        }
    }

    @Override
    public int resto() {
        return 0;
    }

    @Override
    public Punctum formo(Punctum lectum) {
        return filter(lectum);
    }

    public Punctum formo(Punctum lectum, double freq, boolean is_lpf) {
        //double fe  = freq / REGULA_EXAMPLI_D;
        b = initFilter(is_lpf ? LPF : HPF, freq, freq);
        return filter(lectum);
    }

    public Punctum formo(Punctum lectum, double freq1, double freq2, boolean is_bpf) {
        b = initFilter(is_bpf ? BPF : BEF, freq1, freq2);
        //double fe1   = freq1 / REGULA_EXAMPLI_D;
        //double fe2   = freq2 / REGULA_EXAMPLI_D;
        //double[] b = getBpfBef(fe1, fe2, size, window, is_bpf);
        return filter(lectum);
    }

    private Punctum filter(Punctum lectum) {
        oa.pono(lectum);
        Punctum p = new Punctum();
        for (int m = 0; m <= size; m++) {
            p = p.addo(oa.capio(m).multiplico(b[m]));
        }
        return p;
    }

    @Override
    public void ponoPunctum(int index, double tempus, Punctum punctum) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void close() {
    }

    public static double[] getBpfBef(double freq1, double freq2, int size, double[] window, boolean is_bpf) {
        int offset = size / 2;
        double[] b = new double[window.length];
        Sinc sinc = new Sinc();
        for (int m = -size / 2; m <= size / 2; m++) {
            if (is_bpf) {
                b[offset + m] = 2.0 * freq2 * sinc.value(2.0 * FastMath.PI * freq2 * m)
                        - 2.0 * freq1 * sinc.value(2.0 * FastMath.PI * freq1 * m);
            } else {
                b[offset + m] = sinc.value(FastMath.PI * m)
                        - 2.0 * freq2 * sinc.value(2.0 * FastMath.PI * freq2 * m)
                        + 2.0 * freq1 * sinc.value(2.0 * FastMath.PI * freq1 * m);
            }
        }
        for (int m = 0; m < size + 1; m++) {
            b[m] *= window[m];
        }
        return b;
    }

    public static double[] getLpfHpf(double freq, int size, double[] window, boolean is_lpf) {
        int offset = size / 2;
        double[] b = new double[window.length];
        Sinc sinc = new Sinc();
        for (int m = -size / 2; m <= size / 2; m++) {
            if (is_lpf) {
                b[offset + m] = 2.0 * freq * sinc.value(2.0 * FastMath.PI * freq * m);
            } else {
                b[offset + m]
                        = sinc.value(FastMath.PI * m)
                        - 2.0 * freq * sinc.value(2.0 * FastMath.PI * freq * m);
            }
        }
        for (int m = 0; m < size + 1; m++) {
            b[m] *= window[m];
        }
        return b;
    }

}
