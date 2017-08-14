/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mina.sql;


/**
 *
 * @author mina
 */
public class Delete extends UpdateQuery {
    
    public Delete(Table table, Expression expr){
        this();
        setTable(table);
        setExpression(expr);
        
    }
    public Delete(){
        super(false, true);
    }

    @Override
    public String getQuery() {
        return 
            "DELETE FROM " + table.getSchemaTableName() + getWhere();
    }

    @Override
    public ColumnValues getUpdateColumnValues() {
        return super.update_column_values;
    }
    @Override
    public String getOperationName() {
        return "削除";
    }

}
