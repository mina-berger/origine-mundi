/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mina.sql;

//import com.mina.sql.Name.ColumnType;
import java.util.ArrayList;

/**
 *
 * @author mina
 */
public class ColumnValue {

    private ColumnType column_type;
    private String value_expression;
    
    public ColumnValue(ColumnValue column_value){
        this(column_value.column_type, column_value.value_expression);
    }

    public ColumnValue(ColumnType column_type, String value_expression) {
        this.column_type = column_type;
        this.value_expression = value_expression;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (this.column_type != null ? this.column_type.hashCode() : 0);
        hash = 17 * hash + (this.value_expression != null ? this.value_expression.hashCode() : 0);
        return hash;
    }
    @Override
    public boolean equals(Object o){
        ColumnValue cv = (ColumnValue)o;
        return 
                column_type.equals(cv.column_type) && 
                value_expression.equals(cv.value_expression);
    }

    public void setValueExpression(String value_expression) {
        this.value_expression = value_expression;
    }
    public String getValueExpression(){return value_expression;}

    public ColumnType getColumnType() {
        return column_type;
    }
    public boolean isFunction(){
        return 
                value_expression.equals("CURRENT_DATE") ||
                value_expression.equals("CURRENT_TIME") ||
                value_expression.equals("CURRENT_TIMESTAMP");

    }
    public static ColumnValue currentDate(ColumnType column_type){
        return new ColumnValue(column_type, "CURRENT_DATE");
    }
    public static ColumnValue currentTime(ColumnType column_type){
        return new ColumnValue(column_type, "CURRENT_TIME");
    }
    public static ColumnValue currentTimestamp(ColumnType column_type){
        return new ColumnValue(column_type, "CURRENT_TIMESTAMP");
    }
    public static ColumnValue nullValue(ColumnType column_type){
        return new ColumnValue(column_type, "NULL");
    }
    public static ColumnValue searchColumnValue(ArrayList<ColumnValue> column_values, ColumnType column_type){
        if(column_values == null) return null;
        for(int i = 0;i < column_values.size();i++){
            ColumnValue column_value = column_values.get(i);
            if(column_value.getColumnType().equals(column_type)){
                return column_value;
            }
        }
        return null;
    }
}
