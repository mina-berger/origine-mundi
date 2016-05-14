/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor.ludum;

import la.clamor.Mergibilis;

/**
 *
 * @author hiyamamina
 * @param <T>
 */
public class Constans<T extends Mergibilis> implements Mutans{
    private T value;
    public Constans(T value){
        this.value = value;
    }

    @Override
    public T capio(int index) {
        return value;
    }
    
}
