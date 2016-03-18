/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor;

/**
 *
 * @author mina
 */
public class Cinctum implements Mergibilis<Cinctum> {

    private final Puncta puncta;

    public Cinctum() {
        puncta = new Puncta(Res.publica.channel());
        for (int i = 0; i < Res.publica.channel(); i++) {
            Punctum punctum = new Punctum();
            punctum.ponoAestimatio(i, new Aestimatio(1));
            puncta.ponoPunctum(i, punctum);
        }

    }

    public Cinctum(boolean strict, Punctum... punctum_array) {
        if (strict && punctum_array.length != Res.publica.channel()) {
            throw new IllegalArgumentException("");
        }
        puncta = new Puncta(Res.publica.channel());
        if (punctum_array.length > 0) {
            for (int i = 0; i < Res.publica.channel(); i++) {
                puncta.ponoPunctum(i, punctum_array[i % punctum_array.length]);
            }
        }
    }

    public Punctum cingo(Punctum in) {
        Punctum ex = new Punctum();
        for (int i_ex = 0; i_ex < Res.publica.channel(); i_ex++) {
            Aestimatio aestimatio = new Aestimatio();
            for (int i_in = 0; i_in < Res.publica.channel(); i_in++) {
                aestimatio = aestimatio.addo(in.capioAestimatio(i_in).multiplico(capioOutput(i_in, i_ex)));
            }
            ex.ponoAestimatio(i_ex, aestimatio);
        }
        return ex;
    }

    public Aestimatio capioOutput(int index_in, int index_ex) {
        return puncta.capioPunctum(index_ex).capioAestimatio(index_in);
    }

    @Override
    public Cinctum mergo(long diff, long index, Cinctum tectum) {
        Cinctum cinctum = new Cinctum(false);
        for (int i = 0; i < Res.publica.channel(); i++) {
            cinctum.puncta.ponoPunctum(i,
                puncta.capioPunctum(i).mergo(diff, index, tectum.puncta.capioPunctum(i)));
        }
        return cinctum;
    }
}
