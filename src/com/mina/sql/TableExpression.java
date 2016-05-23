/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mina.sql;



/**
 *
 * @author mina
 */
@SuppressWarnings("rawtypes")
public class TableExpression extends Query implements Comparable {
    Table table;
    String correlation_name;
    public TableExpression(Table table){
        this(table, null);
    }
    public TableExpression(Table table, String correlation_name){
        this.table = table;
        this.correlation_name = correlation_name;
    }
    public Table getTable(){return table;}
    public String getCorrelationName(){return correlation_name;}
    public void setCorrelationName(String correlation_name){
        this.correlation_name = correlation_name;
    }
    public void clearCorrelaionName(){
        correlation_name = null;
    }
    public boolean hasCorrelationName(){return correlation_name != null;}
    public String getSingleName(){
        return hasCorrelationName()?getCorrelationName():table.getTableName();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + (this.table != null ? this.table.hashCode() : 0);
        hash = 47 * hash + (this.correlation_name != null ? this.correlation_name.hashCode() : 0);
        return hash;
    }
    @Override
    public boolean equals(Object o){
        return compareTo(o) == 0;
        //return table.equals(table_expr.table) && correlation_name.equals(table_expr.correlation_name);
    }

    @Override
    public String getQuery() {
        return table.getSchemaTableName() +
                (correlation_name == null?"":" AS " + correlation_name);
    }

    public int compareTo(Object o) {
        TableExpression te = (TableExpression)o;
        int compare = getTable().compareTo(te.getTable());
        if(compare != 0) return compare;
        if(correlation_name != null){
            return correlation_name.compareTo(te.correlation_name);
        }
        return new Integer(0).compareTo(te.correlation_name == null?new Integer(0):new Integer(te.correlation_name.hashCode()));
    }
    @Override
    public String toString(){
        return "Table:" + table + ":correlatione_name" + correlation_name;
    }
}
