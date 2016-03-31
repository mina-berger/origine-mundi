package la.clamor.forma;

import la.clamor.OrbisPuncti;
import la.clamor.Punctum;

/**
 *
 * @author mina
 */
public class LowFi implements Forma {
    private final OrbisPuncti oa;
    private final double ratio;
    private long index_raw;

    public LowFi(double ratio) {
        this.ratio = ratio;
        oa = new OrbisPuncti((int) Math.ceil(ratio));
        index_raw = 0;
    }
    @Override
    public Punctum formo(Punctum lectum) {
        oa.pono(lectum);
        long index = index_raw - (long) Math.ceil(Math.floor((double)index_raw / ratio) * ratio);
        index_raw++;
        //System.out.println(index + ":" + oa.capio((int)index));
        return oa.capio((int)index);
    }
    @Override
    public int resto() {
        return 0;
    }

    @Override
    public void ponoPunctum(int index, double tempus, Punctum punctum) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
