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
public class Schema extends Catalog implements Comparable {

    protected String schema_name;

    public Schema(String catalog_name, String schema_name) {
        super(catalog_name);
        this.schema_name = schema_name;
    }

    public String getSchemaName() {
        return schema_name;
    }

    @Override
    public String getExplanation() {

        return super.getExplanation() + "."
                + (schema_name.isEmpty() ? "no_schema" : "schema(" + schema_name + ")");
    }

    @Override
    public boolean equals(Object o) {
        return compareTo(o) == 0;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + (this.schema_name != null ? this.schema_name.hashCode() : 0);
        return hash;
    }

    @Override
    public int compareTo(Object o) {
        int super_value = super.compareTo(o);
        if (super_value != 0) {
            return super_value;
        }
        String o_schema_name = ((Schema) o).getSchemaName();
        if (o_schema_name == null) {
            return 0;
        }
        return schema_name.compareTo(o_schema_name);
    }

    @Override
    public String toString() {
        return super.toString() + ":schema_name=" + schema_name;
    }

}
