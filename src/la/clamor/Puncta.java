package la.clamor;

import java.io.PrintStream;

import org.apache.commons.lang3.ArrayUtils;

public class Puncta implements Constantia {
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
}
