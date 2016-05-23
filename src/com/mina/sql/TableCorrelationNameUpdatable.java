/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mina.sql;

/**
 *
 * @author mina
 */
public interface TableCorrelationNameUpdatable {
    public void updateCorrelationName(TableCorrelationNameProvider tcnp);
    public void clearCorrelationName();
}
