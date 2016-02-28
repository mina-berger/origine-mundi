/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor.deinde.bk;

import java.util.ArrayList;
import la.clamor.Legibilis;
import la.clamor.Punctum;
import la.clamor.LegibileAbstractum;

/**
 *
 * @author mina
 */
public class Marker extends LegibileAbstractum {
    ArrayList<Punctum> buffer;
    boolean mark;
    int index;
    
    
    public Marker(Legibilis fons){
        super(fons);
        buffer = new ArrayList<>();
        mark = false;
        index = 0;
    }
    public void mark(){
        if(buffer.size() > index){
            buffer = new ArrayList(buffer.subList(index, buffer.size()));
        }else{
            buffer.clear();
        }
        index = 0;
        mark = true;
    }
    public void back(){
        mark = false;
        index = 0;
    }

    @Override
    public Punctum lego() {
        Punctum punctum;
        if(mark){
            if(buffer.size() > index){
                punctum = buffer.get(index);
            }else{
                punctum = super.legoAFontem();
                buffer.add(punctum);
            }
            index++;
        }else if(buffer.size() > index){
            punctum = buffer.remove(0);
        }else{
            punctum = legoAFontem();
        }
        return punctum;
    }

    @Override
    public boolean paratusSum() {
        return buffer.size() > index || super.fonsParatusEst();
    }
    public static void main(String[] args){
        Marker m = new Marker(new Legibilis() {
            int index = 0;
            @Override
            public Punctum lego() {
                return new Punctum(index++);
            }

            @Override
            public boolean paratusSum() {
                return true;
            }
        });
        System.out.println(m.lego());
        System.out.println(m.lego());
        m.mark();
        System.out.println(m.lego());
        System.out.println(m.lego());
        m.back();
        System.out.println(m.lego());
        System.out.println(m.lego());
        System.out.println(m.lego());
        System.out.println(m.lego());
        m.mark();
        System.out.println(m.lego());
        m.mark();
        System.out.println(m.lego());
        m.back();
        System.out.println(m.lego());
        System.out.println(m.lego());
        
        
    }
    
}
