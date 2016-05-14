/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package origine_mundi.opus027;

import com.mina.util.Doubles;
import com.mina.util.Integers;
import la.clamor.Vel;
import la.clamor.opus.Mensa;
import la.clamor.opus.Notes;
import la.clamor.opus.ParteTaleae;

/**
 *
 * @author hiyamamina
 */
public class Parte027BM extends ParteTaleae{
    public boolean piano;
    public Parte027BM(Mensa mensa, Integers ids, int talea_primo, int length, boolean piano) {
        super(mensa, ids, talea_primo, length);
        this.piano = piano;
    }

    @Override
    protected void creo(int t) {
        double[] intervals = piano?new double[]{0, 12}:new double[]{0};
        switch(t % 9){
            case 0:
                ludoClaves(t,
                        (int i) -> new Doubles(0, 0.33, 0.66, 1, 2, 2.75, 3.5).get(i),
                        (int i) -> new Doubles(0.3, 0.3, 0.3, 0.9, 0.7, 0.7, 0.4).get(i),
                        (int i) -> new Notes(new Doubles(63, 59, 61, 63, 63, 70, 68).get(i), intervals),
                        (int i) -> new Vel(1), 7);
                ludoClaves(t,
                        (int i) -> new Doubles(0, 1, 2, 2.75, 3.5).get(i),
                        (int i) -> new Doubles(0.9, 0.9, 0.7, 0.7, 0.4).get(i),
                        (int i) -> new Doubles[]{
                            new Notes(68, -2, 2, 3), new Notes(68, -2, 2, 3), new Notes(68, -3, 2, 3), 
                            new Notes(73, -2, 2, 4), new Notes(73, -2, 2, 4)
                        }[i],
                        (int i) -> new Vel(0.7), piano?5:0);
                break;
            case 1:
                ludoClaves(t,
                        (int i) -> new Doubles(0).get(i),
                        (int i) -> new Doubles(3.5).get(i),
                        (int i) -> new Notes(new Doubles(61).get(i), intervals),
                        (int i) -> new Vel(1), 1);
                ludoClaves(t,
                        (int i) -> new Doubles(0).get(i),
                        (int i) -> new Doubles(3.5).get(i),
                        (int i) -> new Notes(66, -1, 2, 4),
                        (int i) -> new Vel(0.7), piano?1:0);
                break;
            case 2:
                ludoClaves(t,
                        (int i) -> new Doubles(0, 0.33, 0.66, 1, 2, 2.75, 3.5).get(i),
                        (int i) -> new Doubles(0.3, 0.3, 0.3, 0.9, 0.7, 0.7, 0.4).get(i),
                        (int i) -> new Notes(new Doubles(61, 57, 59, 61, 61, 68, 66).get(i), intervals),
                        (int i) -> new Vel(1), 7);
                ludoClaves(t,
                        (int i) -> new Doubles(0, 1, 2, 2.75, 3.5).get(i),
                        (int i) -> new Doubles(0.9, 0.9, 0.7, 0.7, 0.4).get(i),
                        (int i) -> new Doubles[]{
                            new Notes(66, -2, 2, 3), new Notes(66, -2, 2, 3), new Notes(66, -3, 2, 3), 
                            new Notes(71, -2, 2, 4), new Notes(71, -2, 2, 4)
                        }[i],
                        (int i) -> new Vel(0.7), piano?5:0);
                break;
            case 3:
                ludoClaves(t,
                        (int i) -> new Doubles(0, 3.5).get(i),
                        (int i) -> new Doubles(3.5, 0.2).get(i),
                        (int i) -> new Notes(new Doubles(71, 71).get(i), intervals),
                        (int i) -> new Vel(1), 2);
                ludoClaves(t,
                        (int i) -> new Doubles(0).get(i),
                        (int i) -> new Doubles(3.5).get(i),
                        (int i) -> new Notes(76, -1, 2, 4),
                        (int i) -> new Vel(0.7), piano?1:0);
                break;
            case 4:
                ludoClaves(t,
                        (int i) -> new Doubles(0, 0.33, 0.66, 1, 1.33, 1.66, 2, 2.75, 3.5).get(i),
                        (int i) -> new Doubles(0.3, 0.3, 0.3, 0.3, 0.3, 0.3, 0.7, 0.7, 0.4).get(i),
                        (int i) -> new Notes(new Doubles(72, 68, 70, 72, 68, 70, 72, 75, 74).get(i), intervals),
                        (int i) -> new Vel(1), 9);
                ludoClaves(t,
                        (int i) -> new Doubles(0, 0.33, 0.66, 1, 1.33, 1.66, 2, 2.75, 3.5).get(i),
                        (int i) -> new Doubles(0.3, 0.3, 0.3, 0.3, 0.3, 0.3, 0.7, 0.7, 0.4).get(i),
                        (int i) -> new Doubles[]{
                            new Notes(77, -2, 2, 3), new Notes(77, -5, -2), new Notes(77, -2, 2), 
                            new Notes(77, -2, 2, 3), new Notes(77, -5, -2), new Notes(77, -2, 2), 
                            new Notes(77, -3, 2, 3), new Notes(82, -5, -2, 2), new Notes(82, -5, -2, 1)
                        }[i],
                        (int i) -> new Vel(0.7), piano?9:0);
                break;
            case 5:
                ludoClaves(t,
                        (int i) -> new Doubles(0, 0.33, 0.66, 1, 1.33, 1.66, 2, 2.75, 3.5).get(i),
                        (int i) -> new Doubles(0.3, 0.3, 0.3, 0.3, 0.3, 0.3, 0.7, 0.7, 0.4).get(i),
                        (int i) -> new Notes(new Doubles(70, 67, 68, 70, 67, 68, 70, 71, 73).get(i), intervals),
                        (int i) -> new Vel(1), 9);
                ludoClaves(t,
                        (int i) -> new Doubles(0, 0.33, 0.66, 1, 1.33, 1.66, 2).get(i),
                        (int i) -> new Doubles(0.3, 0.3, 0.3, 0.3, 0.3, 0.3, 0.7).get(i),
                        (int i) -> new Doubles[]{
                            new Notes(75, -1, 2, 4), new Notes(75, -5, -1), new Notes(75, -1, 2), 
                            new Notes(75, -1, 2, 4), new Notes(75, -5, -1), new Notes(75, -1, 2), 
                            new Notes(74, -3, 3, 6)
                        }[i],
                        (int i) -> new Vel(0.7), piano?7:0);
                break;
            case 6:
                ludoClaves(t,
                        (int i) -> new Doubles(0, 0.33, 0.66, 1, 1.33, 1.66, 2, 2.75, 3.5).get(i),
                        (int i) -> new Doubles(0.3, 0.3, 0.3, 0.3, 0.3, 0.3, 0.7, 0.7, 0.4).get(i),
                        (int i) -> new Notes(new Doubles(75, 71, 73, 75, 71, 73, 75, 73, 71).get(i), intervals),
                        (int i) -> new Vel(1), 9);
                ludoClaves(t,
                        (int i) -> new Doubles(0, 0.66, 1.33, 2).get(i),
                        (int i) -> new Doubles(0.6, 0.6, 0.7, 0.7).get(i),
                        (int i) -> new Doubles[]{
                            new Notes(73, 3, 7, 10), new Notes(73, 3, 7, 10), new Notes(73, 2, 3, 7), 
                            new Notes(73, 3, 7, 9)
                        }[i],
                        (int i) -> new Vel(0.9), piano?4:0);
                break;
            case 7:
                ludoClaves(t,
                        (int i) -> new Doubles(0, 3.5).get(i),
                        (int i) -> new Doubles(3.3, 0.3).get(i),
                        (int i) -> new Notes(new Doubles(70, 72).get(i), intervals),
                        (int i) -> new Vel(1), 2);
                ludoClaves(t,
                        (int i) -> new Doubles(0.5, 1.25, 1.75).get(i),
                        (int i) -> new Doubles(0.2, 0.2, 2.1).get(i),
                        (int i) -> new Notes[]{
                            new Notes(65, -2, 0, 3), new Notes(65, -2, 0, 3), new Notes(65, -3, 0, 3)
                        }[i],
                        (int i) -> new Vel(0.6), piano?3:0);
                break;
            case 8:
                ludoClaves(t,
                        (int i) -> new Doubles(0).get(i),
                        (int i) -> new Doubles(3.5).get(i),
                        (int i) -> new Notes(new Doubles(70).get(i), intervals),
                        (int i) -> new Vel(1), 1);
                ludoClaves(t,
                        (int i) -> new Doubles(0.5, 1.25, 1.75, 2.5).get(i),
                        (int i) -> new Doubles(0.2, 0.2, 0.2, 1.4).get(i),
                        (int i) -> new Notes[]{
                            new Notes(65, -2, 0, 3), new Notes(65, -2, 0, 3), new Notes(65, -3, 0, 3),
                            new Notes(60, 1, 4, 8)
                        }[i],
                        (int i) -> new Vel(0.6), piano?4:0);
                break;
            
        }
    }
    
}
