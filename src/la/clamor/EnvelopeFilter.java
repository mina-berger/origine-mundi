/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor;

/**
 *
 * @author user
 */
public class EnvelopeFilter implements Legibilis {
    Legibilis legibilis;
    Envelope envelope;
    long longitudo;
    long positio;
    Aestimatio volume;
    public EnvelopeFilter(Legibilis legibilis, Envelope envelope, double duration, Aestimatio volume){
        this.legibilis = legibilis;
        this.envelope = envelope == null?new Envelope(new Punctum(1)):envelope;
        this.volume = volume;
        longitudo = Functiones.adPositio(duration);
        positio = 0;
    }

    @Override
    public Punctum lego() {
        positio++;
        return legibilis.lego().multiplico(envelope.capioPunctum(positio)).multiplico(volume);
    }

    @Override
    public boolean paratusSum() {
        return positio < longitudo && legibilis.paratusSum();
        
    }
    
}
