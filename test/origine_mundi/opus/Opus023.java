/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package origine_mundi.opus;

import java.util.ArrayList;
import la.clamor.Cinctum;
import la.clamor.Envelope;
import la.clamor.Positio;
import la.clamor.Punctum;
import la.clamor.Velocitas;
import la.clamor.forma.CadentesFormae;
import la.clamor.forma.Chorus;
import la.clamor.forma.Compressor;
import la.clamor.forma.Delay;
import la.clamor.forma.Equalizer;
import la.clamor.forma.IIRFilter;
import la.clamor.opus.Humanizer;
import la.clamor.opus.Mensa;
import la.clamor.opus.Taleae;
import la.clamor.referibile.ModEnv;
import la.clamor.referibile.OscillatioQuad;
import la.clamor.referibile.Referens;
import origine_mundi.archive.ArchiveLudior;

/**
 *
 * @author mina
 */
public class Opus023 extends Mensa {

    public Opus023() {
        super(false, true);
    }   

    @Override
    protected void ponoComitis(ArrayList<Taleae.Comes> comites) {
    }

    @Override
    protected void ponoRapidi(ArrayList<Taleae.Rapidus> rapidi) {
        rapidi.add(new Taleae.Rapidus(0, 0, 90, false));
    }

    @Override
    protected void creo() {
        ponoInstrument(0, new Punctum(1), new Cinctum(), new ArchiveLudior("mu500r", "drum_basic", 10), new CadentesFormae(
            new Compressor(new Punctum(0.2), new Punctum(0.1)),
            new Equalizer(1., 0., 0., 0., 0., 0., 0., -0.5, -1., -1.)
        ));
        ponoInstrument(1, new Punctum(0.5, 0.5), new Cinctum(), new ArchiveLudior("mu500r", "drum_basic", 10), new CadentesFormae(
            new IIRFilter(500, 7000, true)));
        
        ponoInstrument(2, new Punctum(0.5, 0.5), new Cinctum(), new ArchiveLudior("mu500r", "drum_basic", 10), new CadentesFormae(
            new Compressor(new Punctum(0.2), new Punctum(0.1)),
            new Equalizer(-1., -1., -1., 0., 0., 0., 0., -0.5, -1., -1.)
        ));
        ponoInstrument(3, new Punctum(0.7), new Cinctum(true, new Punctum(1), new Punctum(0.5)), new ArchiveLudior("mu500r", "bass006433", 10), new CadentesFormae(
            new Equalizer(2., 1., 1., 0., 0, 0, 0., -0.5, -1., -1.)
        ));
        ponoInstrument(4, new Punctum(2.0), new Cinctum(), new ArchiveLudior("mu500r", "piano", 500), new CadentesFormae(
            new Equalizer(0., 1., 1., 0., -0.5, -0.5, -1., -1., 3., 3.)
        ));
        ponoInstrument(5, new Punctum(0.4), new Cinctum(), 
            new Referens("test", new OscillatioQuad(), 
                new ModEnv(
                    new Envelope<>(new Punctum(1)),
                    new Envelope<>(new Punctum(3)),
                    new Envelope<>(new Punctum(0.008, -0.008))
                ), 
                new ModEnv(new Envelope<>(new Punctum(1500), new Positio<>(1000, new Punctum(400)))), 
                new ModEnv(new Envelope<>(new Punctum(1))), 500), 
            
            new CadentesFormae(
            new Equalizer(0., 1., 1., 0., -0.5, -0.5, -1., -1., 1., 1.),
            new Delay(new Punctum(Delay.temps(90, 0.5)), new Punctum(3), new Punctum(0.5, -0.5)),
            new IIRFilter(3000, true)
            )
        );
        ponoInstrument(6, new Punctum(1), new Cinctum(), 
            new Referens("test", new OscillatioQuad(), 
                new ModEnv(
                    new Envelope<>(new Punctum(1)),
                    new Envelope<>(new Punctum(3)),
                    new Envelope<>(new Punctum(0.008, -0.008))
                ), 
                new ModEnv(new Envelope<>(new Punctum(1500), new Positio<>(1000, new Punctum(400)))), 
                new ModEnv(new Envelope<>(new Punctum(1))), 500), 
            
            new CadentesFormae(
            new Equalizer(-1., -1., -0.5, 0., 2., 2., 0., -0.5, -1., -1.)
            //new IIRFilter(3000, true)
            )
        );
        
        ponoInstrument(10, new Punctum(0.5), new Cinctum(), new ArchiveLudior("mu500r", "piano", 500), new CadentesFormae(
            new Chorus(new Punctum(0.1), new Punctum(10), new Punctum(1, 0), new Punctum(0, 1)),
            new Delay(new Punctum(Delay.temps(120, 0.75)), new Punctum(3), new Punctum(0.5, -0.5)),
            new IIRFilter(500, 7000, true)));
        ponoHumanizer(new Humanizer()
            .pono(0, 0, 1).pono(0.25, 0.02, 0.5).pono(0.5, 0, 0.8).pono(0.75, 0.02, 0.5)
            .ponoRandomVelocitas(0, 0.5), 0, 1, 2, 4, 5);
        ponoHumanizer(new Humanizer()
            .pono(0, 0, 1).pono(0.25, 0.02, 0.8).pono(0.5, 0, 0.9).pono(0.75, 0.02, 0.8)
            .ponoRandomVelocitas(0, 0.5), 0, 1, 2, 3, 4, 5);
        ludoBD(0, 2);   
        ludoBM(0, 4);
        ludoBB(0, 3);
        /*
        ludoAB(0, 4, 3, 5, false);
        ludoAD(0, 4);
        ludoAM(0, 4, false);
        ludoAB(4, 4, 3, 5, true);
        ludoAD(4, 4);
        ludoAM(4, 4, true);
        ludoAB(8, 4, 3, 5, false);
        ludoAD(8, 4);
        ludoAM(8, 4, false);
        ludoAB(12, 4, 3, 5, true);
        ludoAD(12, 4);
        ludoAM(12, 4, true);
        */
    }
    public void ludoBD(int talea, int length) {
        for (int i = talea; i < talea + length; i++) {
            ludo(0, i, 0.0, 0.5, 36, new Velocitas(1));
            ludo(0, i, 0.75, 0.5, 36, new Velocitas(1));
            ludo(0, i, 1.5, 0.5, 36, new Velocitas(1));
            ludo(0, i, 2.25, 0.5, 36, new Velocitas(1));
            ludo(0, i, 3.5, 0.5, 36, new Velocitas(0.5));
            
            ludo(2, i, 3.0, 1, 40, new Velocitas(1));
            
            int ride = 51;
            ludo(1, i, 0,   0.5, ride, new Velocitas(1));
            ludo(1, i, 0.5, 0.5, ride, new Velocitas(0.5));
            ludo(1, i, 0.75, 0.5, ride, new Velocitas(1));
            ludo(1, i, 1.25, 0.5, ride, new Velocitas(0.5));
            ludo(1, i, 1.5,  0.5, ride, new Velocitas(1));
            ludo(1, i, 2.0,  0.5, ride, new Velocitas(0.5));
            ludo(1, i, 2.25, 0.5, ride, new Velocitas(1));
            ludo(1, i, 2.75, 0.5, ride, new Velocitas(0.5));
            ludo(1, i, 3.,  0.5, ride, new Velocitas(1));
            ludo(1, i, 3.5, 0.5, ride, new Velocitas(1));
            //for (int j = 0; j < 8; j++) {
            //    ludo(1, i, j * 0.5, 0.5, j == 7?46:42, new Velocitas(1));
            //}
        }
    }
    public void ludoBM(int talea, int track) {
        int i = talea;
        ludo(track, i, 0.0,  0.7, 69, new Velocitas(0.8));
        ludo(track, i, 0.75, 0.2, 65, new Velocitas(0.8));
        ludo(track, i, 1.5,  0.7, 74, new Velocitas(0.8));
        ludo(track, i, 2.25, 1.2, 72, new Velocitas(0.8));
        ludo(track, i, 3.5, 0.2, 69, new Velocitas(0.8));
        ludo(track, i, 3.7, 0.2, 70, new Velocitas(0.8));
        i++;
        ludo(track, i, 0.0, 0.2, 72, new Velocitas(0.8));
        ludo(track, i, 0.25, 0.5, 70, new Velocitas(0.8));
        ludo(track, i, 0.75, 0.2, 69, new Velocitas(0.8));
        ludo(track, i, 1.0, 0.5, 67, new Velocitas(0.8));
        ludo(track, i, 1.5, 0.2, 67, new Velocitas(0.8));
        ludo(track, i, 1.75, 0.5, 69, new Velocitas(0.8));
        ludo(track, i, 2.25, 1.2, 65, new Velocitas(0.8));
        ludo(track, i, 3.5, 0.2, 62, new Velocitas(0.8));
        ludo(track, i, 3.7, 0.2, 64, new Velocitas(0.8));
        
    }
    
    public void ludoBB(int talea, int track) {
        int i = talea;
        ludo(track, i, 0.0, 0.5, 34, new Velocitas(0.8));
        ludo(track, i, 0.5, 0.2, 46, new Velocitas(1));
        ludo(track, i, 0.75, 0.7, 33, new Velocitas(0.8));
        ludo(track, i, 1.5, 0.7, 31, new Velocitas(0.8));
        ludo(track, i, 2.25, 1., 36, new Velocitas(0.8));
        ludo(track, i, 3.25, 0.2, 48, new Velocitas(1));
        ludo(track, i, 3.5, 0.5, 31, new Velocitas(0.8));
        i++;
        ludo(track, i, 0.0, 0.5, 36, new Velocitas(0.8));
        ludo(track, i, 0.5, 0.2, 48, new Velocitas(1));
        ludo(track, i, 0.75, 0.7, 37, new Velocitas(0.8));
        ludo(track, i, 1.5, 0.7, 38, new Velocitas(0.8));
        ludo(track, i, 2.25, 1.0, 45, new Velocitas(0.8));
        ludo(track, i, 3.25, 0.2, 50, new Velocitas(1));
        ludo(track, i, 3.5, 0.5, 42, new Velocitas(0.8));
        i++;
        ludo(track, i, 0.0, 0.7, 31, new Velocitas(0.8));
        
    }
    public void ludoAM(int talea, int track, boolean end) {
        int i = talea;
        ludo(track, i, 0.0, 0.2, 69, new Velocitas(0.8));
        ludo(track, i, 0.25, 0.2, 67, new Velocitas(0.8));
        ludo(track, i, 0.75, 0.5, 69, new Velocitas(0.8));
        ludo(track, i, 1.25, 0.2, 70, new Velocitas(0.8));
        ludo(track, i, 1.75, 0.5, 72, new Velocitas(0.8));
        ludo(track, i, 2.25, 0.2, 70, new Velocitas(0.8));
        ludo(track, i, 2.75, 0.5, 69, new Velocitas(0.8));
        ludo(track, i, 3.25, 1.2, 67, new Velocitas(0.8));
        i++;
        ludo(track, i, 0.0, 0.2, 65, new Velocitas(0.8));
        ludo(track, i, 0.25, 0.2, 64, new Velocitas(0.8));
        ludo(track, i, 0.75, 0.5, 65, new Velocitas(0.8));
        ludo(track, i, 1.25, 0.2, 67, new Velocitas(0.8));
        ludo(track, i, 1.75, 0.5, 69, new Velocitas(0.8));
        ludo(track, i, 2.25, 0.2, 67, new Velocitas(0.8));
        ludo(track, i, 2.75, 0.5, 65, new Velocitas(0.8));
        ludo(track, i, 3.25, 1.2, 60, new Velocitas(0.8));
        i++;
        ludo(track, i, 0.0, 0.7, 62, new Velocitas(0.8));
        ludo(track, i, 0.75, 0.7, 65, new Velocitas(0.8));
        ludo(track, i, 1.5, 0.4, 62, new Velocitas(0.8));
        ludo(track, i, 2.0, 0.7, 60, new Velocitas(0.8));
        ludo(track, i, 2.75, 0.7, 65, new Velocitas(0.8));
        if(end){
            //ludo(track, i, 3.5, 0.4, 69, new Velocitas(0.8));
            i++;
            ludo(track, i, 0.0, 0.3, 65, new Velocitas(0.8));
            ludo(track, i, 0.5, 0.3, 65, new Velocitas(0.8));
            ludo(track, i, 1.0, 0.3, 67, new Velocitas(0.8));
            ludo(track, i, 1.5, 2.7, 65, new Velocitas(0.8));
        }else{
            ludo(track, i, 3.5, 0.4, 69, new Velocitas(0.8));
            i++;
            ludo(track, i, 0.0, 0.3, 70, new Velocitas(0.8));
            ludo(track, i, 0.5, 0.3, 69, new Velocitas(0.8));
            ludo(track, i, 1.0, 0.3, 65, new Velocitas(0.8));
            ludo(track, i, 1.5, 2.7, 67, new Velocitas(0.8));
        }
    }
    public void ludoAB(int talea, int length, int track1, int track2, boolean end) {
        double[][] notess = new double[][]{
            new double[]{41, 65, 69, 72, 40, 67, 70, 72},
            new double[]{38, 65, 69, 72, 36, 65, 69, 75},
            new double[]{34, 65, 69, 74, 33, 67, 72, 74},
            new double[]{31, 65, 70, 74, 36, 64, 70, 73},
        };
        for (int i = talea; i < talea + length; i++) {
            double[] notes = notess[i % notess.length];
            if(i % 4 != 3){
                ludo(track1, i, 0.0, 0.3, notes[0], new Velocitas(0.8));
                ludo(track1, i, 0.5, 0.48, notes[0], new Velocitas(1));
                ludo(track1, i, 1.0, 0.3, notes[0], new Velocitas(0.8));
                ludo(track1, i, 1.5, 0.48, notes[0], new Velocitas(1));
                ludo(track1, i, 2.0, 0.3, notes[4], new Velocitas(0.8));
                ludo(track1, i, 2.5, 0.48, notes[4], new Velocitas(1));
                ludo(track1, i, 3.0, 0.3, notes[4], new Velocitas(0.8));
                ludo(track1, i, 3.5, 0.48, notes[4], new Velocitas(1));
            }else if(end){
                ludo(track1, i, 0.0, 0.3, 34, new Velocitas(0.8));
                ludo(track1, i, 0.5, 0.48, 35, new Velocitas(1));
                ludo(track1, i, 1.0, 0.3, 36, new Velocitas(0.8));
                ludo(track1, i, 1.5, 0.98, 41, new Velocitas(1));
                ludo(track1, i, 2.5, 0.48, 29, new Velocitas(1));
                ludo(track1, i, 3.25, 0.7, 29, new Velocitas(1));
            }else{
                ludo(track1, i, 0.0, 0.3, 31, new Velocitas(0.8));
                ludo(track1, i, 0.5, 0.48, 33, new Velocitas(1));
                ludo(track1, i, 1.0, 0.3, 34, new Velocitas(0.8));
                ludo(track1, i, 1.5, 0.98, 36, new Velocitas(1));
                ludo(track1, i, 2.5, 0.48, 36, new Velocitas(1));
                ludo(track1, i, 3.25, 0.7, 36, new Velocitas(1));
            }
            
            if(i % 4 == 3 && end){
                ludo(track2, i, 0.0, 0.5, 67, new Velocitas(1));
                ludo(track2, i, 0.5, 0.5, 74, new Velocitas(1));
                ludo(track2, i, 1.0, 0.5, 76, new Velocitas(1));
                ludo(track2, i, 1.5, 0.5, 70, new Velocitas(1));

                ludo(track2, i, 2.0, 0.5, 69, new Velocitas(1));
                ludo(track2, i, 2.5, 0.5, 72, new Velocitas(1));
                ludo(track2, i, 3.0, 0.5, 77, new Velocitas(1));
                ludo(track2, i, 3.5, 0.5, 72, new Velocitas(1));
            }else{
                ludo(track2, i, 0.0, 0.5, notes[1], new Velocitas(1));
                ludo(track2, i, 0.5, 0.5, notes[2], new Velocitas(1));
                ludo(track2, i, 1.0, 0.5, notes[3], new Velocitas(1));
                ludo(track2, i, 1.5, 0.5, notes[2], new Velocitas(1));

                ludo(track2, i, 2.0, 0.5, notes[5], new Velocitas(1));
                ludo(track2, i, 2.5, 0.5, notes[6], new Velocitas(1));
                ludo(track2, i, 3.0, 0.5, notes[7], new Velocitas(1));
                ludo(track2, i, 3.5, 0.5, notes[6], new Velocitas(1));
            }
        }

    }

    public void ludoAD(int talea, int length) {
        for (int i = talea; i < talea + length; i++) {
            if(i % 4 == 3){
                ludo(0, i, 0.0, 0.5, 36, new Velocitas(1));
                ludo(2, i, 1.0, 1, 40, new Velocitas(1));
                ludo(0, i, 1.5, 0.5, 36, new Velocitas(0.5));
                ludo(0, i, 2.5, 0.5, 36, new Velocitas(1));
                ludo(2, i, 3.0, 1, 40, new Velocitas(1));
                ludo(0, i, 3.25, 0.5, 36, new Velocitas(0.5));
                for (int j = 0; j < 8; j++) {
                    ludo(1, i, j * 0.5, 0.5, j == 7 || j == 3?46:42, new Velocitas(1));
                }
            }else{
                ludo(0, i, 0.0, 0.5, 36, new Velocitas(1));
                ludo(2, i, 1.0, 1, 40, new Velocitas(1));
                ludo(0, i, 1.5, 0.5, 36, new Velocitas(0.5));
                ludo(0, i, 2.0, 0.5, 36, new Velocitas(1));
                ludo(2, i, 3.0, 1, 40, new Velocitas(1));
                ludo(0, i, 3.5, 0.5, 36, new Velocitas(0.5));
                for (int j = 0; j < 8; j++) {
                    ludo(1, i, j * 0.5, 0.5, j == 7?46:42, new Velocitas(1));
                }
            }
        }

    }

    @Override
    public void initio() {
    }

    @Override
    protected void anteFacio() {
    }

}
