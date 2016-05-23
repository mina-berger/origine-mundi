/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mina.terminal;

/**
 *
 * @author hiyamamina
 */
public abstract class TMenu {

    private final String name;

    public TMenu(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void action(TInfo info);
    public abstract boolean option(TInfo info);


}
