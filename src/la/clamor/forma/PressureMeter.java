/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor.forma;

import java.text.DecimalFormat;
import java.util.TreeMap;
import la.clamor.Aestima;
import la.clamor.Punctum;
import la.clamor.Res;
import org.apache.commons.math3.util.FastMath;

/**
 *
 * @author hiyamamina
 */
public class PressureMeter implements Forma {

    private final TreeMap<Integer, Long> totus;
    private final String name;

    public PressureMeter(String name) {
        this.name = name;
        totus = name == null ? null : new TreeMap<>();
    }

    @Override
    public Punctum formo(Punctum lectum) {
        if (name != null) {
            for (int i = 0; i < Res.publica.channel(); i++) {
                count(totus, lectum.capioAestima(i));
            }
        }
        return lectum;
    }

    private void count(TreeMap<Integer, Long> counter, Aestima aestima) {
        if (aestima.equals(new Aestima(0))) {
            return;
        }
        int value = (int) FastMath.round(aestima.abs().doubleValue() * 100);
        if (counter.containsKey(value)) {
            counter.put(value, counter.get(value) + 1);
        } else {
            counter.put(value, 1l);
        }
    }

    @Override
    public int resto() {
        return 0;
    }

    @Override
    public void ponoPunctum(int index, double tempus, Punctum punctum) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void close() {
        if (name != null) {
            DecimalFormat df1 = new DecimalFormat("0.00");
            DecimalFormat df2 = new DecimalFormat("0.0000");
            long all = 0;
            for (int key : totus.keySet()) {
                all += totus.get(key);
            }
            System.err.println("[pressure meter:" + name + "]");
            long value = 0;
            for (int key : totus.keySet()) {
                value += totus.get(key);
                double ratio = (double) value / (double) all;
                double level = (double) key / 100d;
                System.err.println("  " + df1.format(level) + ":" + df2.format(ratio) + "%(" + value + ")");
            }
            System.err.println("  total:" + all);
        }

    }

}
