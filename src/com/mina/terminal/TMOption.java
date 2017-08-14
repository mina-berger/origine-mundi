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
public class TMOption extends TMOptionAbstract {

    public TMOption(String name, boolean top) {
        super(name, top);
    }

    @Override
    public boolean option(TInfo info) {
        return true;
    }
    
}
