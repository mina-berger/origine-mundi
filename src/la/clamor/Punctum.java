package la.clamor;

import java.io.Serializable;
import java.util.Arrays;
import org.apache.commons.lang3.ArrayUtils;

public class Punctum implements Constantia, Serializable, Comparable<Punctum>, Mergibilis<Punctum> {

    public static final Punctum ZERO = new Punctum();
    private static final long serialVersionUID = 5303754051401035301L;
    private Aestimatio[] aestimationes;

    //private boolean fixum;
    public Punctum() {
        this(0d);
    }

    public Punctum(Punctum punctum) {
        aestimationes = ArrayUtils.clone(punctum.aestimationes);
    }

    public Punctum(Aestimatio aestimatio) {
        this.aestimationes = new Aestimatio[Res.publica.channel()];
        for (int i = 0; i < Res.publica.channel(); i++) {
            this.aestimationes[i] = aestimatio;
        }
    }

    public Punctum(double aestimatio) {
        aestimationes = new Aestimatio[Res.publica.channel()];
        Arrays.fill(aestimationes, new Aestimatio(aestimatio));
    }

    public Punctum(double... aestimationes) {
        this.aestimationes = new Aestimatio[Res.publica.channel()];
        for (int i = 0; i < Res.publica.channel(); i++) {
            this.aestimationes[i] = new Aestimatio(aestimationes[i]);
        }
    }

    public Punctum partior(double partitor) {
        return partior(new Punctum(partitor));
    }

    public Punctum partior(Aestimatio partitor) {
        return partior(new Punctum(partitor));
    }

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

    public Punctum multiplico(Aestimatio factor) {
        return multiplico(new Punctum(factor));
    }

    public Punctum multiplico(Punctum factor) {
        Punctum punctum = new Punctum();
        for (int i = 0; i < Res.publica.channel(); i++) {
            punctum.aestimationes[i] = aestimationes[i].multiplico(factor.aestimationes[i]);
        }
        return punctum;
    }

    public Punctum addo(Punctum additum) {
        Punctum punctum = new Punctum();
        for (int i = 0; i < Res.publica.channel(); i++) {
            punctum.aestimationes[i] = aestimationes[i].addo(additum.aestimationes[i]);
        }
        return punctum;
    }

    public Punctum nego() {
        Punctum punctum = new Punctum();
        for (int i = 0; i < Res.publica.channel(); i++) {
            punctum.aestimationes[i] = aestimationes[i].nego();
        }
        return punctum;
    }

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
            punctum.aestimationes[i] = Aestimatio.atan2(ordinate.aestimationes[i], abscissa.aestimationes[i]);
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

    public Aestimatio capioAestimatio(int index) {
        return aestimationes[index];
    }

    public void ponoAestimatio(int index, Aestimatio aestimatio) {
        aestimationes[index] = aestimatio;
    }

    public Aestimatio maxAbs() {
        Aestimatio aestimatio = new Aestimatio();
        for (int i = 0; i < Res.publica.channel(); i++) {
            aestimatio = aestimatio.max(capioAestimatio(i).abs());
        }
        return aestimatio;
    }

    public Aestimatio average() {
        Aestimatio aestimatio = new Aestimatio();
        for (int i = 0; i < Res.publica.channel(); i++) {
            aestimatio = aestimatio.addo(capioAestimatio(i));
        }
        return aestimatio.partior(new Aestimatio(Res.publica.channel()));
    }

    @Override
    public String toString() {
        String filum = "";
        for (Aestimatio aestimatio : aestimationes) {
            if (!filum.isEmpty()) {
                filum += ",";
            }
            filum += aestimatio.toString();
        }
        return filum;
    }

    public static void main(String[] args) throws Exception {
        /*File tmp_file = File.createTempFile("test", Long.toString(System.currentTimeMillis()));
        try (ObjectOutputStream o_out = new ObjectOutputStream(new FileOutputStream(tmp_file))) {
            o_out.writeObject(new Punctum(1.123));
            o_out.flush();
        }
        try (ObjectInputStream o_in = new ObjectInputStream(new FileInputStream(tmp_file))) {
            System.out.println(o_in.readObject());
        }*/
        System.out.println(new Aestimatio(-1).partior(new Aestimatio(2.3)));
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

    public boolean majorAbsTotiSunt(Aestimatio value) {
        Aestimatio value_abs = value.abs();
        boolean idEst = false;
        for (int i = 0; i < aestimationes.length; i++) {
            if (aestimationes[i].abs().compareTo(value_abs) <= 0) {
                return false;
            }
        }
        return true;
    }

    public boolean minorAbsTotiSunt(Aestimatio value) {
        Aestimatio value_abs = value.abs();
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
                capioAestimatio(i).mergo(diff, index, tectum.capioAestimatio(i)));
        }
        return punctum;
    }
}
