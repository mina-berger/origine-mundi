/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mina.terminal;

import com.mina.terminal.QuitException.Level;

/**
 *
 * @author hiyamamina
 */
public class TMQuit extends TMenu {

    private final Level level;

    public TMQuit(Level level) {
        super(level.name().toLowerCase());
        this.level = level;
    }

    @Override
    public void action(TInfo info) {
        throw new QuitException(level);
    }

    @Override
    public boolean option(TInfo info) {
        return true;
    }

}
