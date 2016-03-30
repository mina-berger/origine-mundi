package la.clamor.io;

import origine_mundi.OmUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import la.clamor.Aestima;
import la.clamor.Constantia;
import la.clamor.ExceptioClamoris;
import la.clamor.Legibilis;
import la.clamor.Punctum;
import la.clamor.Res;

public class LectorLimam implements Constantia, Legibilis {

    ObjectInputStream puncta_stream;

    public LectorLimam(File lima) {
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
            for (int i = 0; i < Res.publica.channel(); i++) {
                punctum.ponoAestimatio(i, new Aestima(puncta_stream.readDouble()));
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

    public void close() {
        try {
            puncta_stream.close();
        } catch (IOException ex) {
            throw new ExceptioClamoris(ex);
        }
    }

    public static void main(String[] args) {
        File f = new File(IOUtil.getDirectory("sample"), "01d.lima");
        int length = 1000;
        ArrayList<LectorLimam> lls = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            lls.add(new LectorLimam(f));
        }
        while (lls.get(0).paratusSum()) {
            Punctum o = null;
            Punctum c = null;
            for (int i = 0; i < length; i++) {
                c = lls.get(i).lego();
                if (i == 0) {
                    o = c;
                    continue;
                }
                if (!c.equals(o)) {
                    throw new RuntimeException(c + ":" + o);
                }
            }
        }
    }

}
