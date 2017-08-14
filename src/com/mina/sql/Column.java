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
public class Column extends Table implements Comparable {

    protected String column_name;

    public Column(Schema schema, String table_name, String column_name) {
        this(schema.getCatalogName(), schema.getSchemaName(), table_name, column_name);
    }

    public Column(Table table, String column_name) {
        this(table.getCatalogName(), table.getSchemaName(), table.getTableName(), column_name);
    }

    public Column(String catalog_name, String schema_name, String table_name, String column_name) {
        super(catalog_name, schema_name, table_name);
        this.column_name = column_name;
    }

    public String getColumnName() {
        return column_name;
    }

    public String getTableColumnName() {
        return table_name.isEmpty() ? column_name : table_name + "." + column_name;
        //return getTableName() + "." + getColumnName();
    }

    public Table getTable() {
        return new Table(catalog_name, schema_name, table_name);
    }

    @Override
    public String getExplanation() {
        return super.getExplanation() + "."
                + (column_name.isEmpty() ? "no_column" : "column(" + column_name + ")");
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
        String o_column_name = ((Column) o).getColumnName();
        if (o_column_name == null) {
            return 0;
        }
        return column_name.compareTo(o_column_name);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + (this.column_name != null ? this.column_name.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return super.toString() + ":column_name=" + column_name;
    }

    public static String[] getColumnNames(Column[] columns) {
        String[] column_names = new String[columns.length];
        for (int i = 0; i < columns.length; i++) {
            column_names[i] = columns[i].getColumnName();
        }
        return column_names;
    }
}
