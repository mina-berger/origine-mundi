/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mina.sql;

import com.mina.util.CoreException;

/**
 *
 * @author mina
 */
public class Update extends UpdateQuery {
    protected ColumnValues former_column_values;
    public Update(Table table, ColumnValues column_values, Expression expr){
        this();
        setTable(table);
        updateColumnValues(column_values);
        setExpression(expr);

    }
    public Update(){
        super(true, true);
        former_column_values = new ColumnValues();
    }

    public void updateFormerColumnValue(ColumnValue column_value){
        former_column_values.updateColumnValue(column_value);
    }

    private ColumnValues getUpdatedColumnValues(){
        ColumnValues column_values = new ColumnValues();
        for(int i = 0;i < update_column_values.size();i++){
            ColumnValue update_cv = update_column_values.get(i);
            ColumnType column_type = update_cv.getColumnType();
            if(former_column_values.hasColumnValue(column_type)){
                ColumnValue former_cv = former_column_values.getColumnValue(column_type);
                if(update_cv.equals(former_cv)){
                    continue;
                }
            }
            column_values.add(update_cv);
        }
        return column_values;
    }
    @Override
    public boolean isUpdated(){
        return !getUpdatedColumnValues().isEmpty();
    }
    @Override
    public String getQuery() {
        ColumnValues updated = getUpdatedColumnValues();
        Column[] columns = updated.getColumnTypes();
        String[] value_exprs  = updated.getValueExpressions();
        int length = columns.length;
        if(value_exprs.length != length) throw new CoreException("カラムと値の配列の長さが違います。");
        String[] equations = new String[length];
        for(int i = 0;i < length;i++){
            equations[i] = columns[i].getColumnName() + " = " + value_exprs[i];
        }
        return "UPDATE " + table.getSchemaTableName() + " SET " + SQLUtil.getCsvString(equations) + getWhere();
    }

    @Override
    public ColumnValues getUpdateColumnValues() {
        return getUpdatedColumnValues();
    }

    @Override
    public String getOperationName() {
        return "更新";
    }

}
