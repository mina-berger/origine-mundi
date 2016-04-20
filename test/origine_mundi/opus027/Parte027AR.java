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
import la.clamor.opus.ParteTaleae;

/**
 *
 * @author hiyamamina
 */
public class Parte027AR extends ParteTaleae {

    public Parte027AR(Mensa mensa, Integers ids, int talea_primo, int length) {
        super(mensa, ids, talea_primo, length);
    }

    @Override
    protected void creo(int t) {
        Chord[] chords;
        switch (t % 16) {
            case 0:
            case 8:
                chords = new Chord[]{
                    new Chord(new Doubles(29, 36), new Doubles(56, 63, 67)),
                    new Chord(new Doubles(34, 41), new Doubles(56, 62, 66))
                };
                break;
            case 1:
            case 9:
                chords = new Chord[]{
                    new Chord(new Doubles(39, 34), new Doubles(55, 62, 65)),
                    new Chord(new Doubles(33, 40), new Doubles(55, 61, 64))
                };
                break;
            case 2:
            case 10:
                chords = new Chord[]{
                    new Chord(new Doubles(38, 32), new Doubles(60, 65, 68)),
                    new Chord(new Doubles(31, 38), new Doubles(59, 65, 68))
                };
                break;
            case 3:
            case 11:
                chords = new Chord[]{
                    new Chord(new Doubles(36, 36), new Doubles(58, 63, 67)),
                    new Chord(new Doubles(35, 35), new Doubles(57, 63, 65)),
                    new Chord(new Doubles(34, 34), new Doubles(56, 62, 64)),
                    new Chord(new Doubles(39, 39), new Doubles(55, 61, 64))//,
                //new Chord(new Doubles(33, 40), new Doubles(55, 61, 64))
                };
                break;
            case 4:
            case 12:
                chords = new Chord[]{
                    new Chord(new Doubles(32, 39), new Doubles(60, 63, 67)),
                    new Chord(new Doubles(37, 32), new Doubles(59, 63, 65))
                };
                break;
            case 5:
            case 13:
                chords = new Chord[]{
                    new Chord(new Doubles(31, 37), new Doubles(61, 65, 70)),
                    new Chord(new Doubles(36, 31), new Doubles(61, 64, 70))
                };
                break;
            case 6:
                chords = new Chord[]{
                    new Chord(new Doubles(29, 36), new Doubles(56, 63, 67)),
                    new Chord(new Doubles(32, 39), new Doubles(59, 63, 65))
                };
                break;
            case 7:
                chords = new Chord[]{
                    new Chord(new Doubles(35, 35), new Doubles(57, 63, 65)),
                    new Chord(new Doubles(34, 34), new Doubles(56, 62, 65)),
                    new Chord(new Doubles(37, 37), new Doubles(59, 65, 68)),
                    new Chord(new Doubles(36, 36), new Doubles(58, 64, 68))
                };
                break;
            case 14:
                chords = new Chord[]{
                    new Chord(new Doubles(29, 36), new Doubles(56, 63, 67)),
                    new Chord(new Doubles(34, 41), new Doubles(56, 62, 67))
                };
                break;
            case 15:
                chords = new Chord[]{
                    new Chord(new Doubles(39, 34), new Doubles(55, 62, 65)),
                    new Chord(new Doubles(39, 34), new Doubles(60, 62, 67))
                };
                break;
            default:
                chords = new Chord[]{
                    new Chord(new Doubles(29, 36), new Doubles(56, 60, 63, 67)),
                    new Chord(new Doubles(29, 36), new Doubles(56, 60, 63, 67))
                };
        }
        ludo(t, chords);

    }

    private void ludo(int t, Chord[] chords) {
        switch (t % 16) {
            case 2:
            case 10:
                cambio(0);
                ludoClaves(t,
                        (int i) -> new Doubles(0, 0.5, 1.25, 1.75, 2.25, 3).get(i),
                        (int i) -> new Doubles(0.2, 0.6, 0.2, 0.2, 0.65, 0.2).get(i),
                        (int i) -> (i < 3 ? chords[0] : chords[1]).capioChord(),
                        (int i) -> new Vel(0.8), 6);
                cambio(1);
                ludo(t,
                        (int i) -> new Doubles(0, 0.75, 1, 1.75, 2, 2.75, 3).get(i),
                        (int i) -> new Doubles(0.2, 0.2, 0.65, 0.2, 0.2, 0.2, 0.2).get(i),
                        (int i) -> (i < 4 ? chords[0] : chords[1]).capioBass(new Integers(0, 3, 4).contains(i) ? 0 : 1),
                        (int i) -> new Vel(0.8), 7);
                break;
            case 3:
            case 11:
                cambio(0);
                ludoClaves(t,
                        (int i) -> new Doubles(-0.5, 0.5, 1.25, 1.75, 2.25, 3, 3.5).get(i),
                        (int i) -> new Doubles(0.7, 0.6, 0.2, 0.2, 0.65, 0.2, 0.3).get(i),
                        (int i) -> (i < 2 ? chords[0] : i < 3 ? chords[1] : i < 5 ? chords[2] : chords[3]).capioChord(),
                        (int i) -> new Vel(0.8), 7);
                cambio(1);
                ludo(t,
                        (int i) -> new Doubles(-0.5, 0.75, 1, 1.75, 2, 2.75, 3, 3.75).get(i),
                        (int i) -> new Doubles(0.9, 0.2, 0.65, 0.2, 0.2, 0.2, 0.65, 0.2).get(i),
                        (int i) -> (i < 2 ? chords[0] : i < 4 ? chords[1] : i < 6 ? chords[2] : chords[3]).capioBass(new Integers(0, 3, 4).contains(i) ? 0 : 1),
                        (int i) -> new Vel(0.8), 8);
                break;
            default:
                cambio(0);
                ludoClaves(t,
                        (int i) -> new Doubles(0, 0.5, 1.25, 1.75, 2.25, 3, 3.5).get(i),
                        (int i) -> new Doubles(0.2, 0.6, 0.2, 0.2, 0.65, 0.2, 0.3).get(i),
                        (int i) -> chords.length == 1 ? chords[0].capioChord()
                                : chords.length <= 2 ? (i < 3 ? chords[0] : chords[1]).capioChord()
                                        : (i < 2 ? chords[0] : i < 3 ? chords[1] : i < 5 ? chords[2] : chords[3]).capioChord(),
                        (int i) -> new Vel(0.8), 7);
                cambio(1);
                ludo(t,
                        (int i) -> new Doubles(0, 0.75, 1, 1.75, 2, 2.75, 3, 3.75).get(i),
                        (int i) -> new Doubles(0.2, 0.2, 0.65, 0.2, 0.2, 0.2, 0.65, 0.25).get(i),
                        (int i) -> (chords.length == 1 ? chords[0]
                                : chords.length <= 2 ? (i < 4 ? chords[0] : chords[1])
                                        : (i < 2 ? chords[0] : i < 4 ? chords[1] : i < 6 ? chords[2] : chords[3])).capioBass(new Integers(0, 3, 4, 7).contains(i) ? 0 : 1),
                        (int i) -> new Vel(0.8), 8);
        }
    }
}
