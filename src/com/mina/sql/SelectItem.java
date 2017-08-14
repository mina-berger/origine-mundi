/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mina.sql;

import com.mina.util.Code.Option;
import com.mina.util.CoreException;
import java.util.ArrayList;
import javax.management.Query;

/**
 *
 * @author mina
 */
public abstract class SelectItem extends Query implements Comparable<SelectItem>, TableCorrelationNameUpdatable {
    protected TableExpression table_expr;
    protected String correlation_name;
    public SelectItem(TableExpression table_expr, String correlation_name){
        this.table_expr = table_expr;
        this.correlation_name = correlation_name;
    } 
    public void updateCorrelationName(TableCorrelationNameProvider tcnp){
        table_expr = tcnp.getProvidedTableExpression(table_expr);
    }
    public void clearCorrelationName(){
        table_expr.clearCorrelaionName();
    }
    
    public Option getOption(){
        String name = getName();
        return new Option(name, name, this);
    }
    protected String getAsPhrase(){
        return correlation_name == null?"":" AS " + correlation_name;
        
    }
    public void setCorrelationName(String correlation_name){
        this.correlation_name = correlation_name;
    }
    public TableExpression getTableExpression(){return table_expr;}
    public abstract String getTypeName();
    public abstract String getName();
    public abstract ArrayList<ColumnType> getColumnTypes();
    public String getQuery(){
        return getName() + getAsPhrase();
        
    }
    public int compareTo(SelectItem osi){
        int compare = table_expr.compareTo(osi.getTableExpression());
        if(compare == 0) return 0;
        if(correlation_name == null) return 0;
        return correlation_name.compareTo(osi.correlation_name);
    }

    @Override
    public boolean equals(Object o){
        return compareTo((SelectItem)o) == 0;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (this.table_expr != null ? this.table_expr.hashCode() : 0);
        hash = 83 * hash + (this.correlation_name != null ? this.correlation_name.hashCode() : 0);
        return hash;
    }
    /*private static class Asterisk extends SelectItem {
        public Asterisk(){
            this(null);
        }
        public Asterisk(TableExpression table_expr){
            this(table_expr, null);
        }
        public Asterisk(TableExpression table_expr, String correlation_name){
            super(table_expr, correlation_name);
        }
        public String getName(){
            return (table_expr == null?"*":table_expr.getSingleName() + ".*");
        }

        @Override
        public String getTypeName() {
            return null;
        }

        @Override
        public ArrayList<ColumnType> getColumnTypes() {
            return new ArrayList<ColumnType>();
        }


    }*/
    
    
    public static class SelectPhrase extends SelectItem implements Comparable<SelectItem> {
        private Select select;
        public SelectPhrase(Select select){
            super(select.getSelectItem(0).getTableExpression(), null);
            this.select = select;
            if(select.getItemLength() != 1){
                throw new CoreException("SELECT句に複数項目が設定されています。(" + select.getQuery() + ")");
            }
        }

        @Override
        public String getTypeName() {
            return select.getSelectItem(0).getTypeName();
        }

        @Override
        public String getName() {
            return "(" + select.getQuery() + ")";
        }

        @Override
        public ArrayList<ColumnType> getColumnTypes() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        @Override
        public boolean equals(Object o){
            return compareTo((SelectPhrase)o) == 0;
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 73 * hash + (this.select != null ? this.select.hashCode() : 0);
            return hash;
        }

        public int compareTo(SelectPhrase select_phrase){
            int compare = super.compareTo(select_phrase);
            if(compare != 0) return compare;
            return select.getQuery().compareTo(select_phrase.select.getQuery());
            
        }
        
    }
    public static class SelectColumn extends SelectItem implements Comparable<SelectItem> {
        private ColumnType column_type;
        public SelectColumn(ColumnType column_type){
            this(column_type, new TableExpression(column_type.getTable()), null);
        }
        public SelectColumn(ColumnType column_type, String correlation_name){
            this(column_type, new TableExpression(column_type.getTable()), correlation_name);
        }
        public SelectColumn(ColumnType column_type, TableExpression table_expr){
            this(column_type, table_expr, null);
        }
        public SelectColumn(ColumnType column_type, TableExpression table_expr, String correlation_name){
            super(table_expr, correlation_name);
            this.column_type = column_type;
        }
        public SelectColumn createSelectColumn(ColumnType new_column_type){
            return new SelectColumn(new_column_type, table_expr, null);
        }

        public String getName() {
            return (table_expr == null?column_type.getTableName():table_expr.getSingleName()) + 
                    "." + column_type.getColumnName();
        }

        @Override
        public String getTypeName() {
            return column_type.getTypeName();
        }
        public ColumnType getColumnType(){return column_type;}

        @Override
        public ArrayList<ColumnType> getColumnTypes() {
            ArrayList<ColumnType> vec = new ArrayList<ColumnType>();
            vec.add(column_type);
            return vec;
        }
        @Override
        public boolean equals(Object o){
            return compareTo((SelectColumn)o) == 0;
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 13 * hash + (this.column_type != null ? this.column_type.hashCode() : 0);
            return hash;
        }
        public int compareTo(SelectColumn select_column){
            int compare = super.compareTo(select_column);
            if(compare != 0) return compare;
            return column_type.compareTo(select_column.getColumnType());
            
        }

        
    }

}
