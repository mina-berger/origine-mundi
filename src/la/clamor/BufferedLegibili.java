/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor;

/**
 *
 * @author hiyamamina
 */
public class BufferedLegibili {

    Legibilis legi;
    Puncta buffer;

    public BufferedLegibili(Legibilis legi) {
        this.legi = legi;
        buffer = new Puncta();
    }

    public boolean buffer(int longitudo) {
        int index = longitudo();
        for (; index < longitudo; index++) {
            if (!legi.paratusSum()) {
                break;
            }
            buffer.addoPunctum(legi.lego());
        }
        return index >= longitudo;
    }
    public boolean stash(int longitudo){
        if(buffer.longitudo() > longitudo){
            buffer.removeo(0, longitudo);
            return true;
        }
        buffer = new Puncta();
        return false;
    }

    public int longitudo() {
        return buffer.longitudo();
    }

    public Punctum capioPunctum(int index) {
        return buffer.capioPunctum(index);
    }

    public Puncta capioPunctaSubtr(int index_ab, int index_ad) {
        return buffer.capioSubtr(index_ab, index_ad);
    }
    public Legibilis capioLegibilem(int longitudo, boolean stash){
        Legibilis legi = buffer.capioSubtr(0, longitudo).capioLegibilem();
        if(stash){
            buffer.removeo(0, longitudo);
        }
        return legi;
    }

}
