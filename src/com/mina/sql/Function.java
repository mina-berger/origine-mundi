/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mina.sql;

import java.util.ArrayList;

/**
 *
 * @author mina
 */
public abstract class Function extends SelectItem implements Comparable<SelectItem>, TableCorrelationNameUpdatable {
    protected String str_function;
    protected SelectItem select_item;
    public Function(String str_function, SelectItem select_item){
        super(select_item.getTableExpression(), select_item.correlation_name);
        this.str_function = str_function;
        this.select_item = select_item;
    }

    @Override
    public void updateCorrelationName(TableCorrelationNameProvider tcnp){
        super.updateCorrelationName(tcnp);
        select_item.updateCorrelationName(tcnp);
    }
    @Override
    public void clearCorrelationName(){
        super.clearCorrelationName();
        select_item.clearCorrelationName();
    }
    @Override
    public String getName() {
        return str_function + "(" + select_item.getName() + ")";
    }

    @Override
    public ArrayList<ColumnType> getColumnTypes() {
        return select_item.getColumnTypes();
    }

    @Override
    public boolean equals(Object o){
        return compareTo((Function)o) == 0;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + (this.str_function != null ? this.str_function.hashCode() : 0);
        hash = 17 * hash + (this.select_item != null ? this.select_item.hashCode() : 0);
        return hash;
    }


    public int compareTo(Function function){
        int compare = super.compareTo(function);
        if(compare != 0) return compare;
        compare = str_function.compareTo(function.str_function);
        if(compare != 0) return compare;
        return select_item.compareTo(function.select_item);
    }
    public static class Sum extends SimpleFunction implements Distinct {
        public Sum(SelectItem select_item){
            super("SUM", select_item);
        }
    }
    public static class Max extends SimpleFunction implements Distinct {
        public Max(SelectItem select_item){
            super("MAX", select_item);
        }
    }
    public static class Min extends SimpleFunction implements Distinct {
        public Min(SelectItem select_item){
            super("MIN", select_item);
        }
    }
    public static class Count extends Function implements Distinct {
        public Count(SelectItem select_item){
            super("COUNT", select_item);
        }

        @Override
        public String getTypeName() {
            return "INTEGER";
        }
    }
    public static class LCase extends SimpleFunction {
        public LCase(SelectItem select_item){
            super("LCASE", select_item);
        }
    }
    public interface Distinct {}
    public static class SimpleFunction extends Function {
        
        public SimpleFunction(String str_function, SelectItem select_item){
            super(str_function, select_item);
        }
        @Override
        public String getTypeName() {
            return select_item.getTypeName();
        }

        
    }

}
