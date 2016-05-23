/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mina.sql;

import com.mina.util.StringSquare;

/**
 *
 * @author hiyamamina
 */
public interface MultiColumnDatum {

    public String getHeader(int h);

    public StringSquare getValue(int h);
    //public boolean alignLeft(int h);

    public int hLength();
}
