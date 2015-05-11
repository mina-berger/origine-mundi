package la.clamor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import la.clamor.Punctum.Aestimatio;



public class LectorLimam implements Constantia, Legibilis {
    ObjectInputStream  puncta_stream;
    public LectorLimam(File lima){
        try {
            puncta_stream = new ObjectInputStream(new FileInputStream(lima));
        } catch (IOException ex) {
            throw new ExceptioClamoris(ex);
        }
    }
    @Override
    public Punctum lego() {
        try {
            Punctum punctum = new Punctum();
            for(int i = 0;i < CHANNEL;i++){
                punctum.ponoAestimatio(i, new Aestimatio(puncta_stream.readDouble(), true));
            }
            return punctum;
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
    }
    @Override
    public boolean paratusSum() {
        try {
            return puncta_stream.available() > 0;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
    public void close(){
        try {
            puncta_stream.close();
        } catch (IOException ex) {
            throw new ExceptioClamoris(ex);
        }
    }

}
