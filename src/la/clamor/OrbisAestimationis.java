package la.clamor;

import java.io.PrintStream;


public class OrbisAestimationis {
    private Puncta puncta;
    private int    index;
    
    public OrbisAestimationis(int longitudo) {
        puncta = new Puncta(longitudo);
        index = longitudo - 1;
    }
    
    public int longitudo(){
        return puncta.longitudo();
    }
    
    void deinde() {
        index = ++index % puncta.longitudo();
    }
    
    public Punctum presens() {
        return puncta.capioPunctum(index);
    }
    
    public Punctum capio(int mutati) {
        Integer index_mutati = computoMutati(index, mutati, puncta.longitudo());
        //System.out.println("index_mutati=" + index_mutati);
        if (index_mutati == null) {
            return new Punctum();
        }
        return puncta.capioPunctum(index_mutati);
    }
    
    public Punctum pono(Punctum positum) {
        deinde();
        Punctum vetum = puncta.ponoPunctum(index, positum);
        return vetum;
    }
    
    private static Integer computoMutati(int index, int mutati, int longitudo) {
        if (mutati >= longitudo) {
            return null;
        }
        return ((index - mutati) + longitudo) % longitudo;
    }
    
    public synchronized void mutoLongitudo(int longitudo) {
        Puncta nova = new Puncta(longitudo);
        for (int i = 0; i < Math.min(puncta.longitudo(), longitudo); i++) {
            Integer index_mutati = computoMutati(0, i, longitudo);
            if (index_mutati != null) {
                nova.ponoPunctum(index_mutati, capio(i));
            }
        }
        index = 0;
        puncta = nova;
    }
    public void print(PrintStream out){
        out.println("OrbisAestimationis:index=" + index);
        puncta.print(out);
    }
    public static void main(String[] args) {
        OrbisAestimationis oa = new OrbisAestimationis(5);
        oa.print(System.out);
        System.out.println("pono 1");
        print(oa.pono(new Punctum(new double[] { 1, 1.1 })));
        System.out.println();
        oa.print(System.out);
        System.out.println("presens:" + oa.presens());
        System.out.println("pono 2");
        print(oa.pono(new Punctum(new double[] { 2, 2.1 })));
        System.out.println();
        oa.print(System.out);
        System.out.println("presens:" + oa.presens());
        System.out.println("pono 3");
        print(oa.pono(new Punctum(new double[] { 3, 3.1 })));
        System.out.println();
        oa.print(System.out);
        System.out.println("presens:" + oa.presens());
        System.out.println("pono 4");
        print(oa.pono(new Punctum(new double[] { 4, 4.1 })));
        System.out.println();
        oa.print(System.out);
        System.out.println("presens:" + oa.presens());
        System.out.println("pono 5");
        print(oa.pono(new Punctum(new double[] { 5, 5.1 })));
        System.out.println();
        oa.print(System.out);
        System.out.println("presens:" + oa.presens());
        System.out.println("pono 6");
        print(oa.pono(new Punctum(new double[] { 6, 1.1 })));
        System.out.println();
        oa.print(System.out);
        System.out.println("presens:" + oa.presens());
        System.out.println("presens+capio");
        print(oa.presens());
        print(oa.capio(0));
        print(oa.capio(1));
        print(oa.capio(2));
        print(oa.capio(3));
        print(oa.capio(4));
        print(oa.capio(5));
        print(oa.capio(6));
        System.out.println();
        oa.print(System.out);
        System.out.println("mutoLongitudo");
        oa.mutoLongitudo(10);
        System.out.println();
        oa.print(System.out);
        System.out.println("presens+capio");
        print(oa.presens());
        print(oa.capio(0));
        print(oa.capio(1));
        print(oa.capio(2));
        print(oa.capio(3));
        print(oa.capio(4));
        print(oa.capio(5));
        print(oa.capio(6));
    }
    
    public static void print(Punctum punctum) {
        System.out.println(punctum.toString());
    }
}
