/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mina.terminal;

import com.mina.terminal.QuitException.Level;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author hiyamamina
 */
public class Terminal {

    private final TInfo info;

    public Terminal(TMOptionAbstract menu) {
        info = new TInfo(new BufferedReader(new InputStreamReader(System.in)), System.out, menu);
    }

    public void start() {
        while (true) {
            try {
                info.showOption();
            } catch (QuitException qe) {
                if (qe.getLevel() == Level.QUIT) {
                    break;
                }
                throw qe;
            }
        }
        info.info("adieu");
    }

    public final TInfo getInfo() {
        return info;
    }

    public static void main(String[] args) throws IOException {
        TMOptionAbstract menu = new TMOptionAbstract("top", true) {
                    @Override
                    public boolean option(TInfo info) {
                        return true;
                    }
                }
                .addMenu('0', new TMOptionAbstract("sub1", false) {
                    @Override
                    public boolean option(TInfo info) {
                        return true;
                    }
                }
                        .addMenu('0', new TMenu("hello") {
                            @Override
                            public void action(TInfo info) {
                                info.print("hello");
                            }

                            @Override
                            public boolean option(TInfo info) {
                                return true;
                            }
                        })
                        .addMenu('1', new TMenu("bonjour") {
                            @Override
                            public void action(TInfo info) {
                                info.print("bonjour");
                            }

                            @Override
                            public boolean option(TInfo info) {
                                return true;
                            }
                        }))
                .addMenu('1', new TMOptionAbstract("sub2", false) {
                    @Override
                    public boolean option(TInfo info) {
                        return true;
                    }
                });
        new Terminal(menu).start();
    }

}
