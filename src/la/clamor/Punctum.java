package la.clamor;

import java.io.Serializable;
import java.util.Arrays;
import org.apache.commons.lang3.ArrayUtils;

public class Punctum implements Constantia, Serializable, Comparable<Punctum>, Mergibilis<Punctum> {

    public static final Punctum ZERO = new Punctum();
    private static final long serialVersionUID = 5303754051401035301L;
    private Aestima[] aestimationes;

    //private boolean fixum;
    public Punctum() {
        this(0d);
    }

    public Punctum(Punctum punctum) {
        aestimationes = ArrayUtils.clone(punctum.aestimationes);
    }

    public Punctum(Aestima aestimatio) {
        this.aestimationes = new Aestima[Res.publica.channel()];
        for (int i = 0; i < Res.publica.channel(); i++) {
            this.aestimationes[i] = aestimatio;
        }
    }

    public Punctum(double aestimatio) {
        aestimationes = new Aestima[Res.publica.channel()];
        Arrays.fill(aestimationes, new Aestima(aestimatio));
    }

    public Punctum(double... aestimationes) {
        this.aestimationes = new Aestima[Res.publica.channel()];
        for (int i = 0; i < Res.publica.channel(); i++) {
            this.aestimationes[i] = new Aestima(aestimationes[i]);
        }
    }

    public Punctum partior(double partitor) {
        return partior(new Punctum(partitor));
    }

    public Punctum partior(Aestima partitor) {
        return partior(new Punctum(partitor));
    }

    @Override
    public Punctum partior(Punctum partitor) {
        Punctum punctum = new Punctum();
        for (int i = 0; i < Res.publica.channel(); i++) {
            punctum.aestimationes[i] = aestimationes[i].partior(partitor.aestimationes[i]);
        }
        return punctum;
    }

    public Punctum multiplico(double factor) {
        return multiplico(new Punctum(factor));
    }

    public Punctum multiplico(Aestima factor) {
        return multiplico(new Punctum(factor));
    }

    @Override
    public Punctum multiplico(Punctum factor) {
        Punctum punctum = new Punctum();
        for (int i = 0; i < Res.publica.channel(); i++) {
            punctum.aestimationes[i] = aestimationes[i].multiplico(factor.aestimationes[i]);
        }
        return punctum;
    }

    @Override
    public Punctum addo(Punctum additum) {
        Punctum punctum = new Punctum();
        for (int i = 0; i < Res.publica.channel(); i++) {
            punctum.aestimationes[i] = aestimationes[i].addo(additum.aestimationes[i]);
        }
        return punctum;
    }

    @Override
    public Punctum nego() {
        Punctum punctum = new Punctum();
        for (int i = 0; i < Res.publica.channel(); i++) {
            punctum.aestimationes[i] = aestimationes[i].nego();
        }
        return punctum;
    }

    @Override
    public Punctum subtraho(Punctum additum) {
        Punctum punctum = new Punctum();
        for (int i = 0; i < Res.publica.channel(); i++) {
            punctum.aestimationes[i] = aestimationes[i].subtraho(additum.aestimationes[i]);
        }
        return punctum;
    }

    public Punctum abs() {
        Punctum punctum = new Punctum();
        for (int i = 0; i < Res.publica.channel(); i++) {
            punctum.aestimationes[i] = aestimationes[i].abs();
        }
        return punctum;
    }

    public Punctum sqrt() {
        Punctum punctum = new Punctum();
        for (int i = 0; i < Res.publica.channel(); i++) {
            punctum.aestimationes[i] = aestimationes[i].sqrt();
        }
        return punctum;
    }

    public Punctum pow(double y) {
        Punctum punctum = new Punctum();
        for (int i = 0; i < Res.publica.channel(); i++) {
            punctum.aestimationes[i] = aestimationes[i].pow(y);
        }
        return punctum;
    }

    public static Punctum atan2(Punctum ordinate, Punctum abscissa) {
        Punctum punctum = new Punctum();
        for (int i = 0; i < Res.publica.channel(); i++) {
            punctum.aestimationes[i] = Aestima.atan2(ordinate.aestimationes[i], abscissa.aestimationes[i]);
        }
        return punctum;
    }

    public boolean isLessThan(double value) {
        for (int i = 0; i < Res.publica.channel(); i++) {
            if (aestimationes[i].doubleValue() >= value) {
                return false;
            }
        }
        return true;
    }

    public int longitudo() {
        return aestimationes.length;
    }

    public Aestima capioAestima(int index) {
        return aestimationes[index];
    }

    public void ponoAestimatio(int index, Aestima aestimatio) {
        aestimationes[index] = aestimatio;
    }

    public Aestima maxAbs() {
        Aestima aestimatio = new Aestima();
        for (int i = 0; i < Res.publica.channel(); i++) {
            aestimatio = aestimatio.max(capioAestima(i).abs());
        }
        return aestimatio;
    }

    public Aestima average() {
        Aestima aestimatio = new Aestima();
        for (int i = 0; i < Res.publica.channel(); i++) {
            aestimatio = aestimatio.addo(capioAestima(i));
        }
        return aestimatio.partior(new Aestima(Res.publica.channel()));
    }

    @Override
    public String toString() {
        String filum = "";
        for (Aestima aestimatio : aestimationes) {
            if (!filum.isEmpty()) {
                filum += ",";
            }
            filum += aestimatio.toString();
        }
        return filum;
    }
    public static String serialize(Punctum punctum) {
        String filum = "";
        for (Aestima aestimatio : punctum.aestimationes) {
            if (!filum.isEmpty()) {
                filum += ",";
            }
            filum += Aestima.serialize(aestimatio);
        }
        return filum;
    }
    public static Punctum deserialize(String str){
        String[] split = str.split(",");
        Punctum punctum = new Punctum();
        Aestima[] as = new Aestima[split.length];
        for(int i = 0;i < split.length;i++){
            punctum.ponoAestimatio(i, Aestima.deserialize(split[i]));
        }
        return punctum;
    }
    @Override
    public int compareTo(Punctum p) {
        int compare = Integer.compare(aestimationes.length, p.aestimationes.length);
        if (compare != 0) {
            return compare;
        }
        for (int i = 0; i < aestimationes.length; i++) {
            compare = aestimationes[i].compareTo(p.aestimationes[i]);
            if (compare != 0) {
                return compare;
            }
        }
        return compare;
    }

    public boolean equals(Punctum p) {
        return compareTo(p) == 0;
    }

    public boolean majorAbsTotiSunt(Aestima value) {
        Aestima value_abs = value.abs();
        boolean idEst = false;
        for (int i = 0; i < aestimationes.length; i++) {
            if (aestimationes[i].abs().compareTo(value_abs) <= 0) {
                return false;
            }
        }
        return true;
    }

    public boolean minorAbsTotiSunt(Aestima value) {
        Aestima value_abs = value.abs();
        //boolean idEst = false;
        for (int i = 0; i < aestimationes.length; i++) {
            if (aestimationes[i].abs().compareTo(value_abs) >= 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Punctum mergo(long diff, long index, Punctum tectum) {
        Punctum punctum = new Punctum();
        for (int i = 0; i < Res.publica.channel(); i++) {
            punctum.ponoAestimatio(i,
                capioAestima(i).mergo(diff, index, tectum.capioAestima(i)));
        }
        return punctum;
    }

}
