/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mina.sql;

import com.mina.util.CoreException;
import com.mina.util.ExplainedObject;
import java.io.PrintStream;
import java.util.ArrayList;

/**
 *
 * @author mina
 */
public abstract class UpdateQuery extends ExpressionQuery {
    protected Table table;
    protected ColumnValues update_column_values;
    private final boolean SWITCH_VALUE;
    protected UpdateQuery(boolean switch_value, boolean switch_expression){
        super(switch_expression);
        SWITCH_VALUE = switch_value;
        table = null;
        update_column_values = null;
        if(SWITCH_VALUE){
            update_column_values = new ColumnValues();
        }

    }
    public abstract ColumnValues getUpdateColumnValues();
    public abstract String getOperationName();
    public void printUpdate(PrintStream out, ColumnAlias ca){
        ColumnValues cv = getUpdateColumnValues();
        System.out.println("[" + table.getTableName() + ":" + getOperationName() + "]");
        ExplainedObject.print(out, cv.getExplainedObjectArray(ca));
        //new TextBox(new ExplainedObjectArrayData(cv.getExplainedObjectArray(ca)), false).print(System.out);

    }
    public boolean isValueUpdatable(){return SWITCH_VALUE;}
    public boolean isUpdated(){
        return !update_column_values.isEmpty();
    }
    public void setTable(Table table){
        this.table = table;
    }
    protected boolean hasTable(){return table != null;}
    public Table getTable(){return table;}
    private void check(){
        if(!SWITCH_VALUE) throw new CoreException("SWITCH_VALUE is not activated.");
    }
    public int getColumnValueLength(){
        check();
        return update_column_values.size();
    }
    public boolean hasAllColumnValues(ArrayList<ColumnType> column_types){
        check();
        return update_column_values.hasAllColumnValues(column_types);
    }
    public ColumnValue[] getColumnValueArray(ArrayList<ColumnType> column_types){
        check();
        return update_column_values.getColumnValueArray(column_types);
    }
    protected ColumnValue getColumnValue(int index){
        check();
        return update_column_values.get(index);
    }
    protected ColumnType[] getColumnTypes(){
        check();
        return update_column_values.getColumnTypes();
    }
    protected String[] getValueExpressions(){
        check();
        return update_column_values.getValueExpressions();
    }
    public void updateColumnValues(ColumnValues column_values){
        check();
        update_column_values.updateColumnValues(column_values);
    }
    public void updateColumnValue(ColumnValue column_value){
        check();
        update_column_values.updateColumnValue(column_value);
    }
    public boolean removeColumnValue(ColumnType column_type){
        check();
        return update_column_values.removeColumnValue(column_type);
    }
    public boolean hasColumnValue(ColumnType column_type){
        check();
        return update_column_values.hasColumnValue(column_type);
    }
    public String getValueExpression(ColumnType column_type){
        check();
        return update_column_values.getValueExpression(column_type);
    }
}
