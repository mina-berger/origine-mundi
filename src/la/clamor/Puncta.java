package la.clamor;

import java.io.PrintStream;
import java.util.ArrayList;

import org.apache.commons.lang3.ArrayUtils;

public class Puncta {
    public Punctum[] puncta;
    
    public Puncta() {
        this(0);
    }
    public Puncta(Punctum... puncta){
        this.puncta = puncta;
    }
    public Puncta(int longitudo) {
        puncta = new Punctum[longitudo];
        for(int i = 0;i < longitudo;i++){
            puncta[i] = new Punctum();
        }
    }
    
    public int longitudo() {
        return puncta.length;
    }
    
    public Punctum capioPunctum(int index) {
        return puncta[index];
    }
    public double[] capioDoubleArray(int index){
        double[] values = new double[puncta.length];
        for(int i = 0;i < puncta.length;i++){
            values[i] = puncta[i].capioAestima(index).doubleValue();
        }
        return values;
    }
    
    public Punctum ponoPunctum(int index, Punctum positum) {
        Punctum punctum = puncta[index];
        puncta[index] = positum;
        return punctum;
    }
    public void addoPunctum(Punctum additum){
        puncta = ArrayUtils.add(puncta, additum);
    }
    public Puncta capioSubtr(int index_ab, int index_ad){
        return new Puncta(ArrayUtils.subarray(puncta, index_ab, index_ad));
    }
    public void print(PrintStream out){
        for(int i = 0;i < puncta.length;i++){
            out.println(String.format("Puncta[%d] %s", i, puncta[i].toString()));
        }
    }
    public void removeo(int index_ab, int index_ad){
        puncta = ArrayUtils.addAll(
                ArrayUtils.subarray(puncta, 0, index_ab),
                ArrayUtils.subarray(puncta, index_ad, puncta.length));
    }
    public void removeo(ArrayList<Integer> indeces){
        int[] indeces_i = ArrayUtils.toPrimitive(indeces.toArray(new Integer[]{}));
        puncta = ArrayUtils.removeAll(puncta, indeces_i);
    }
    public Legibilis capioLegibilem(){
        return new LegibilemPunctarum(this);
    }
    public static class LegibilemPunctarum implements Legibilis{
        Puncta puncta;
        int index;
        public LegibilemPunctarum(Puncta puncta){
            this.puncta = puncta;
            index = 0;
        }

        @Override
        public Punctum lego() {
            Punctum lectum = puncta.capioPunctum(index);
            index++;
            return lectum;
        }

        @Override
        public boolean paratusSum() {
            return index < puncta.longitudo();
        }

        @Override
        public void close() {
        }
        
    }
    /*public static void main(String[] args){
        Puncta p = new Puncta(new Punctum[]{
        new Punctum(1), new Punctum(2), new Punctum(3), new Punctum(4)});
        ArrayList<Integer> indeces = new ArrayList<>();
        indeces.add(3);
        indeces.add(1);
        p.removeo(indeces);
        p.print(System.out);
    }*/
}
