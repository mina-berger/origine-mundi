/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor.forma;

import la.clamor.Punctum;

/**
 *
 * @author hiyamamina
 */
public class FormaNominata implements Forma {

    Forma forma;
    String name;

    public FormaNominata(String name, Forma forma) {
        this.name = name;
        this.forma = forma;
    }

    public String capioNomen() {
        return name;
    }

    public Forma capioFormam() {
        return forma;
    }

    @Override
    public Punctum formo(Punctum lectum) {
        return forma.formo(lectum);
    }

    @Override
    public int resto() {
        return forma.resto();
    }

    @Override
    public void ponoPunctum(int index, double tempus, Punctum punctum) {
        forma.ponoPunctum(index, tempus, punctum);
    }

    @Override
    public void close() {
    }

}
