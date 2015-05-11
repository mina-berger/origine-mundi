package la.clamor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.util.FastMath;

public class Punctum implements Constantia, Serializable, Comparable<Punctum> {
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
        this.aestimationes = new Aestimatio[CHANNEL];
        for(int i = 0;i < CHANNEL;i++){
            this.aestimationes[i] = aestimatio;
        }
    }
    public Punctum(double aestimatio) {
        aestimationes = new Aestimatio[CHANNEL];
        Arrays.fill(aestimationes, new Aestimatio(aestimatio));
    }
    public Punctum(double... aestimationes) {
        this.aestimationes = new Aestimatio[CHANNEL];
        for(int i = 0;i < CHANNEL;i++){
            this.aestimationes[i] = new Aestimatio(aestimationes[i]);
        }
    }
    public Punctum partior(double partitor){
        return partior(new Punctum(partitor));
    }
    public Punctum partior(Aestimatio partitor){
        return partior(new Punctum(partitor));
    }
    public Punctum partior(Punctum partitor){
        Punctum punctum = new Punctum();
        for(int i = 0;i < CHANNEL;i++){
            punctum.aestimationes[i] = aestimationes[i].partior(partitor.aestimationes[i]);
        }
        return punctum;
    }
    public Punctum multiplico(double factor){
        return multiplico(new Punctum(factor));
    }
    public Punctum multiplico(Aestimatio factor){
        return multiplico(new Punctum(factor));
    }
    public Punctum multiplico(Punctum factor){
        Punctum punctum = new Punctum();
        for(int i = 0;i < CHANNEL;i++){
            punctum.aestimationes[i] = aestimationes[i].multiplico(factor.aestimationes[i]);
        }
        return punctum;
    }
    public Punctum addo(Punctum additum){
        Punctum punctum = new Punctum();
        for(int i = 0;i < CHANNEL;i++){
            punctum.aestimationes[i] = aestimationes[i].addo(additum.aestimationes[i]);
        }
        return punctum;
    }
    public Punctum nego(){
        Punctum punctum = new Punctum();
        for(int i = 0;i < CHANNEL;i++){
            punctum.aestimationes[i] = aestimationes[i].nego();
        }
        return punctum;
    }
    public Punctum subtraho(Punctum additum){
        Punctum punctum = new Punctum();
        for(int i = 0;i < CHANNEL;i++){
            punctum.aestimationes[i] = aestimationes[i].subtraho(additum.aestimationes[i]);
        }
        return punctum;
    }
    public Punctum abs(){
        Punctum punctum = new Punctum();
        for(int i = 0;i < CHANNEL;i++){
            punctum.aestimationes[i] = aestimationes[i].abs();
        }
        return punctum;
    }
    public int longitudo(){
        return aestimationes.length;
    }
    public Aestimatio capioAestimatio(int index) {
        return aestimationes[index];
    }
    public void ponoAestimatio(int index, Aestimatio aestimatio) {
        aestimationes[index] = aestimatio;
    }
    public Aestimatio maxAbs(){
        Aestimatio aestimatio = new Aestimatio();
        for(int i = 0;i < CHANNEL;i++){
            aestimatio = aestimatio.max(capioAestimatio(i).abs());
        }
        return aestimatio;
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
    public static void main(String[] args) throws Exception{
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
        if(compare != 0){
            return compare;
        }
        for(int i = 0;i < aestimationes.length;i++){
            compare = aestimationes[i].compareTo(p.aestimationes[i]);
            if(compare != 0){
                return compare;
            }
        }
        return compare;
    }
    public boolean equals(Punctum p){
        return compareTo(p) == 0;
    }
    public boolean majorAbsTotiSunt(Aestimatio value){
        Aestimatio value_abs = value.abs();
        boolean idEst = false;
        for(int i = 0;i < aestimationes.length;i++){
            if(aestimationes[i].abs().compareTo(value_abs) <= 0){
                return false;
            }
        }
        return true;
    }
    public boolean minorAbsTotiSunt(Aestimatio value){
        Aestimatio value_abs = value.abs();
        boolean idEst = false;
        for(int i = 0;i < aestimationes.length;i++){
            if(aestimationes[i].abs().compareTo(value_abs) >= 0){
                return false;
            }
        }
        return true;
    }
    public static class Aestimatio implements Cloneable, Comparable<Aestimatio>, Serializable {
        private double value;
        public Aestimatio(){
            this(0);
        }
        public Aestimatio(double aestimatio){
            this(aestimatio, false);
        }
        Aestimatio(double aestimatio, boolean is_raw){
            value = aestimatio;
        }
        Aestimatio(BigDecimal value){
            this.value = value.doubleValue();
        }
        public double doubleValue(){
            return value;
        }
        public long longValue(){
            return Math.round(value);
        }
        public int intValue(){
            return new Long(longValue()).intValue();
        }
        public Aestimatio partior(Aestimatio partitor){
            return new Aestimatio(value / partitor.value);
        }
        public Aestimatio multiplico(Aestimatio multiplicator){
            return new Aestimatio(value * multiplicator.value);
        }
        public Aestimatio addo(Aestimatio multiplicator){
            return new Aestimatio(value + multiplicator.value);
        }
        public Aestimatio subtraho(Aestimatio multiplicator){
            return new Aestimatio(value - multiplicator.value);
        }
        public Aestimatio nego(){
            return new Aestimatio(-value);
        }
        public Aestimatio max(Aestimatio aestimatio){
            return new Aestimatio(FastMath.max(value, aestimatio.value));
        }
        public Aestimatio min(Aestimatio aestimatio){
            return new Aestimatio(FastMath.min(value, aestimatio.value));
        }
        public Aestimatio abs(){
            return new Aestimatio(FastMath.abs(value));
        }
        @Override
        public String toString(){
            //return Double.toString(doubleValue());
            return Double.toString(value);
        }

        @Override
        public int compareTo(Aestimatio a) {
            return Double.compare(value, a.value);
        }
        public double rawValue(){
            return doubleValue();
        }
    }

}