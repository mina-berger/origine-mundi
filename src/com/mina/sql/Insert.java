/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mina.sql;


/**
 *
 * @author mina
 */
public class Insert extends UpdateQuery {
    public Insert(Table table, ColumnValues column_values){
        this();
        setTable(table);
        updateColumnValues(column_values);
    }
    public Insert(){
        super(true, false);
    }


    @Override
    public String getQuery() {
        return
            "INSERT INTO " + table.getSchemaTableName() + "(" + SQLUtil.getCsvString(Column.getColumnNames(getColumnTypes())) +
            ") VALUES (" + SQLUtil.getCsvString(getValueExpressions()) + ")";
        //insert into sql_table4 (col1, col2, col3, col4) values (3, '2000-01-01', '12:20:30', '2000-01-01 12:20:30.123456');
    }

    @Override
    public ColumnValues getUpdateColumnValues() {
        return super.update_column_values;
    }

    @Override
    public String getOperationName() {
        return "作成";
    }


}
