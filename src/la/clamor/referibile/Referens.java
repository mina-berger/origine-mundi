/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor.referibile;

import la.clamor.Instrument;
import la.clamor.Legibilis;
import la.clamor.Punctum;
import la.clamor.Temperamentum;
import la.clamor.Velocitas;
import la.clamor.forma.CadentesFormae;
import la.clamor.forma.VCA;
import la.clamor.forma.VCF;

/**
 *
 * @author mina
 */
public class Referens implements Instrument {
    private final String name;
    private final Referibilis referibilis;
    private final ModEnv freq_rate;
    private final ModEnv filter;
    private final ModEnv amp;
    private final double decay;
    public Referens(String name, Referibilis referibilis, ModEnv freq_rate, ModEnv filter, ModEnv amp, double decay){
        this.name = name;
        this.referibilis = referibilis;
        this.freq_rate = freq_rate;
        this.filter = filter;
        this.amp = amp;
        this.decay = decay;
    }
        

    @Override
    public Legibilis capioNotum(double note, double tempus, Velocitas velocitas) {
        ModEnv _amp = amp.duplicate();
        _amp.capioPrimus().ponoValue(tempus + decay, Punctum.ZERO);
        return CadentesFormae.capioLegibilis(
            new Referibile(referibilis.duplicate(), 
                freq_rate.capioSub(tempus).multiplico(Temperamentum.instance.capioHZ(note), 1, 1), tempus + decay),
            new VCF(filter),
            new VCA(_amp)
        );
    }

    @Override
    public String getName() {
        return "ref_" + name;
    }
    
}
