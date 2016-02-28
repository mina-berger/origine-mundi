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

    /*public static void main(String[] a){
        long index_raw = 0;
        double ratio = 3.5;
        //OrbisAestimationis oa = new OrbisAestimationis((int) Math.ceil(ratio));
        for(int i = 0;i < 20;i++){
            long index = (long) Math.ceil(Math.floor((double)index_raw / ratio) * ratio);
            System.out.println(i + ":" + index);
            index_raw++;
        }
        
        
    }*/


}
