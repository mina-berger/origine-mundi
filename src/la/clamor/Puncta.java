package la.clamor;

import java.io.PrintStream;
import java.util.ArrayList;

import org.apache.commons.lang3.ArrayUtils;

public class Puncta {
    public Punctum[] aestimationes;
    
    public Puncta() {
        this(0);
    }
    public Puncta(Punctum[] aestimationes){
        this.aestimationes = aestimationes;
    }
    public Puncta(int longitudo) {
        aestimationes = new Punctum[longitudo];
        for(int i = 0;i < longitudo;i++){
            aestimationes[i] = new Punctum();
        }
    }
    
    public int longitudo() {
        return aestimationes.length;
    }
    
    public Punctum capioPunctum(int index) {
        return aestimationes[index];
    }
    public double[] capioDoubleArray(int index){
        double[] values = new double[aestimationes.length];
        for(int i = 0;i < aestimationes.length;i++){
            values[i] = aestimationes[i].capioAestimatio(index).doubleValue();
        }
        return values;
    }
    
    public Punctum ponoPunctum(int index, Punctum positum) {
        Punctum punctum = aestimationes[index];
        aestimationes[index] = positum;
        return punctum;
    }
    public void addoPunctum(Punctum additum){
        aestimationes = ArrayUtils.add(aestimationes, additum);
    }
    public void print(PrintStream out){
        for(int i = 0;i < aestimationes.length;i++){
            out.println(String.format("Puncta[%d] %s", i, aestimationes[i].toString()));
        }
    }
    public void removeo(ArrayList<Integer> indeces){
        int[] indeces_i = ArrayUtils.toPrimitive(indeces.toArray(new Integer[]{}));
        aestimationes = ArrayUtils.removeAll(aestimationes, indeces_i);
    }
    public static void main(String[] args){
        Puncta p = new Puncta(new Punctum[]{
        new Punctum(1), new Punctum(2), new Punctum(3), new Punctum(4)});
        ArrayList<Integer> indeces = new ArrayList<>();
        indeces.add(3);
        indeces.add(1);
        p.removeo(indeces);
        p.print(System.out);
    }
}
