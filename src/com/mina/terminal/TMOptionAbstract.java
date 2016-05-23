/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mina.terminal;

import com.mina.terminal.QuitException.Level;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 *
 * @author hiyamamina
 */
public abstract class TMOptionAbstract extends TMenu {

    private final TreeMap<Character, TMenu> submenu;

    private final boolean top;

    public TMOptionAbstract(String name, boolean top) {
        super(name);
        this.top = top;
        submenu = new TreeMap<>();
        if (top) {
            submenu.put('q', new TMQuit(Level.QUIT));
        } else {
            submenu.put('x', new TMQuit(Level.BACK));
            submenu.put('z', new TMQuit(Level.TOP));
        }

    }

    public TMOptionAbstract addMenu(char c, TMenu menu) {
        submenu.put(c, menu);
        return this;
    }

    public boolean isTop() {
        return top;
    }

    @Override
    public void action(TInfo info) {
        info.printBread(null);
        ArrayList<Character> options = new ArrayList<>();
        for (Character c : submenu.keySet()) {
            TMenu menu = submenu.get(c);
            if(menu.option(info)){
                info.print(c + ":" + menu.getName());
                options.add(c);
            }
        }
        char c;
        while (true) {
            c = info.readChar("select");
            if (options.contains(c)) {
                break;
            }
        }
        TMenu menu = submenu.get(c);
        if (menu instanceof TMOptionAbstract) {
            info.setCurrent((TMOptionAbstract) menu);
        } else {
            info.printBread(menu.getName());
            try {
                menu.action(info);
            } catch (QuitException qe) {
                //System.err.println(qe);
                //System.err.println(qe.getLevel());
                switch (qe.getLevel()) {
                    case QUIT:
                        throw qe;
                    case BACK:
                        info.parent();
                        break;
                    case TOP:
                        info.top();
                        break;
                }
            }
        }
    }

}
