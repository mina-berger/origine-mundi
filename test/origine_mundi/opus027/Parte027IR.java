/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package origine_mundi.opus027;

import com.mina.util.Doubles;
import com.mina.util.Integers;
import la.clamor.Vel;
import la.clamor.opus.Chord;
import la.clamor.opus.Mensa;
import la.clamor.opus.Notes;
import la.clamor.opus.ParteTaleae;

/**
 *
 * @author hiyamamina
 */
public class Parte027IR extends ParteTaleae {

    public boolean end;

    public Parte027IR(Mensa mensa, Integers ids, int talea_primo, int length, boolean end) {
        super(mensa, ids, talea_primo, length);
        this.end = end;
    }

    @Override
    protected void creo(int t) {
        Chord[] chords = (!end && t % 4 == 3)
                ? new Chord[]{
                    new Chord(new Notes(37, 0, -5), new Notes(49, 4, 10, 14)),
                    new Chord(new Notes(36, 0, -5), new Notes(48, 4, 10, 15))
                }
                : new Chord[]{
                    new Chord(new Notes(39, 0, -5), new Notes(51, 4, 9, 14)),
                    new Chord(new Notes(39, 0, -5), new Notes(51, 4, 11, 14))
                };
        //System.out.println(t);
        if (end && t % 4 == 3) {
            cambio(0);
            ludoClaves(t,
                    (int i) -> new Doubles(0, 0.5, 1.25, 1.75).get(i),
                    (int i) -> new Doubles(0.2, 0.6, 0.2, 4.25).get(i),
                    (int i) -> (i < 3 ? chords[0] : chords[1]).capioChord(),
                    (int i) -> new Vel(0.8), 4);
            cambio(1);
            ludo(t,
                    (int i) -> new Doubles(0, 0.75, 1, 1.75).get(i),
                    (int i) -> new Doubles(0.2, 0.2, 0.65, 4.25).get(i),
                    (int i) -> (i < 4 ? chords[0] : chords[1]).capioBass(new Integers(0, 3).contains(i) ? 0 : 1),
                    (int i) -> new Vel(0.8), 4);
        } else {
            cambio(0);
            ludoClaves(t,
                    (int i) -> new Doubles(0, 0.5, 1.25, 1.75, 2.25, 3, 3.5).get(i),
                    (int i) -> new Doubles(0.2, 0.6, 0.2, 0.2, 0.65, 0.2, 0.3).get(i),
                    (int i) -> (i < 3 ? chords[0] : chords[1]).capioChord(),
                    (int i) -> new Vel(0.8), 7);
            cambio(1);
            ludo(t,
                    (int i) -> new Doubles(0, 0.75, 1, 1.75, 2, 2.75, 3, 3.75).get(i),
                    (int i) -> new Doubles(0.2, 0.2, 0.65, 0.2, 0.2, 0.2, 0.65, 0.25).get(i),
                    (int i) -> (i < 4 ? chords[0] : chords[1]).capioBass(new Integers(0, 3, 4, 7).contains(i) ? 0 : 1),
                    (int i) -> new Vel(0.8), 8);
        }
    }
}
