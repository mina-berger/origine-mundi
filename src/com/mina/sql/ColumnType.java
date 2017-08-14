/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mina.sql;

import com.mina.sql.MultiColumnDatum;
import com.mina.util.Code.Option;
import com.mina.util.StringSquare;
import com.mina.util.CoreException;

/**
 *
 * @author hiyamamina
 */
public class ColumnType extends Column implements Comparable, MultiColumnDatum {

    private String type_name;

    public ColumnType(Column column, String type_name) {
        this(column.getCatalogName(), column.getSchemaName(), column.getTableName(), column.getColumnName(), type_name);
    }

    public ColumnType(String catalog_name, String schema_name, String table_name, String column_name, String type_name) {
        super(catalog_name, schema_name, table_name, column_name);
        this.type_name = type_name;
    }

    public Column getColumn() {
        return new Column(getCatalogName(), getSchemaName(), getTableName(), getColumnName());
    }

    public String getTypeName() {
        return type_name;
    }

    public Option getOption() {
        String full_name = getTableColumnName();
        return new Option(full_name, full_name, this);
    }

    @Override
    public int compareTo(Object o) {
        //return toString().compareTo(((ColumnType)o).toString());
        int super_value = super.compareTo(o);
        if (super_value != 0) {
            return super_value;
        }
        String o_type_name = ((ColumnType) o).getTypeName();
        if (o_type_name == null) {
            return 0;
        }
        return type_name.compareTo(o_type_name);
    }

    @Override
    public boolean equals(Object obj) {
        return compareTo(obj) == 0;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (this.type_name != null ? this.type_name.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return super.getTableColumnName() + "(" + type_name + ")";
        //return super.toString() + ":type_name=" + type_name;
    }

    public String getHeader(int h) {
        switch (h) {
            case 0:
                return "テーブル名";
            case 1:
                return "カラム名";
            case 2:
                return "タイプ";
            default:
                throw new CoreException("配列外の指定です。（" + h + "）");
        }
    }

    public StringSquare getValue(int h) {
        switch (h) {
            case 0:
                return new StringSquare(getTableName());
            case 1:
                return new StringSquare(getColumnName());
            case 2:
                return new StringSquare(getTypeName());
            default:
                throw new CoreException("配列外の指定です。（" + h + "）");
        }
    }

    /*public boolean alignLeft(int h) {
            return true;
        }*/
    public int hLength() {
        return 3;
    }
}
