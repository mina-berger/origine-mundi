/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor.referibile;

import la.clamor.Envelope;
import la.clamor.Positio;
import la.clamor.Punctum;

/**
 *
 * @author mina
 */
public class ModEnv {
    private Envelope<Punctum> primus;
    private Envelope<Punctum> freq_mod;
    private Envelope<Punctum> quan_mod;
    private OscillatioSine os;
    public ModEnv(Envelope<Punctum> primus){
        this(primus, new Envelope<>(new Punctum(0)), new Envelope<>(new Punctum(0)));
    }
    
    public ModEnv(Envelope<Punctum> primus, Envelope<Punctum> freq_mod, Envelope<Punctum> quan_mod){
        this.primus = primus;
        this.freq_mod = freq_mod;
        this.quan_mod = quan_mod;
        os = new OscillatioSine();
    }
    public void ponoPrimum(Positio positio){
        primus.ponoPositiones(positio);
    }
    public Punctum capio(long index){
        Punctum lectum = primus.capioValue(index);
        Punctum modulatio = os.lego(freq_mod.capioValue(index)).multiplico(quan_mod.capioValue(index));
        return lectum.multiplico(modulatio.addo(new Punctum(1)));
    }
    public Envelope<Punctum> capioPrimus(){
        return primus;
    }
    public ModEnv duplicate(){
        return new ModEnv(
            primus.multiplico(new Punctum(1)), 
            freq_mod.multiplico(new Punctum(1)), 
            quan_mod.multiplico(new Punctum(1)));
    }
    public ModEnv capioSub(double tempus){
        Envelope<Punctum> _primus = primus.capioSub(tempus);
        Envelope<Punctum> _freq_mod = freq_mod.capioSub(tempus);
        Envelope<Punctum> _quan_mod = quan_mod.capioSub(tempus);
        return new ModEnv(_primus, _freq_mod, _quan_mod);
    }
    public ModEnv multiplico(double factor_primi, double factor_freq, double factor_quant){
        return new ModEnv(
            primus.multiplico(new Punctum(factor_primi)), 
            freq_mod.multiplico(new Punctum(factor_freq)), 
            quan_mod.multiplico(new Punctum(factor_quant)));
    }
    
}
