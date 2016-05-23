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
public class QuitException extends RuntimeException {

    public enum Level {
        TOP, BACK, QUIT
    }
    private final Level level;

    public QuitException(Level level) {
        this.level = level;
    }

    public Level getLevel() {
        return level;
    }
}
