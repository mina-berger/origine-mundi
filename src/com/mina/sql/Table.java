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
public class Table extends Schema implements Comparable {

    protected String table_name;

    public Table(Schema schema, String table_name) {
        this(schema.getCatalogName(), schema.getSchemaName(), table_name);
    }

    public Table(String catalog_name, String schema_name, String table_name) {
        super(catalog_name, schema_name);
        this.table_name = table_name;
    }

    public String getTableName() {
        return table_name;
    }

    public String getSchemaTableName() {
        return schema_name.isEmpty() ? table_name : schema_name + "." + table_name;
    }

    public Schema getSchema() {
        return new Schema(catalog_name, schema_name);
    }

    @Override
    public String getExplanation() {
        return super.getExplanation() + "."
                + (table_name.isEmpty() ? "no_table" : "table(" + table_name + ")");
    }

    @Override
    public boolean equals(Object o) {
        return compareTo(o) == 0;
    }

    @Override
    public int compareTo(Object o) {
        int super_value = super.compareTo(o);
        if (super_value != 0) {
            return super_value;
        }
        String o_table_name = ((Table) o).getTableName();
        if (o_table_name == null) {
            return 0;
        }
        return table_name.compareTo(o_table_name);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (this.table_name != null ? this.table_name.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return super.toString() + ":table_name=" + table_name;
    }
}
