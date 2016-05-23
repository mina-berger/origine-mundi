/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mina.sql;

/**
 *
 * @author hiyamamina
 */
public class Catalog implements Comparable {

    protected String catalog_name;

    public Catalog(String catalog_name) {
        this.catalog_name = catalog_name;
    }

    public String getCatalogName() {
        return catalog_name;
    }


    public String getExplanation() {
        return catalog_name.isEmpty() ? "no_catalog" : "catalog(" + catalog_name + ")";
    }

    @Override
    public int compareTo(Object o) {
        String o_catalog_name = ((Catalog) o).getCatalogName();
        if (o_catalog_name == null) {
            return 0;
        }
        return catalog_name.compareTo(o_catalog_name);
    }

    @Override
    public boolean equals(Object o) {
        return compareTo(o) == 0;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (this.catalog_name != null ? this.catalog_name.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "catalog_name=" + catalog_name;
    }
}
