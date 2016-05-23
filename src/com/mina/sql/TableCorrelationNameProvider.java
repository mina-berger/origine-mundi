/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mina.sql;

import com.mina.util.Radix;
import java.util.ArrayList;

/**
 *
 * @author mina
 */
public class TableCorrelationNameProvider {
    private Radix correlation_name_generator;
    private int radix_index;
    private ArrayList<Mapping> mappings;
    public TableCorrelationNameProvider(){
        correlation_name_generator = new Radix("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        radix_index = -1;
        mappings = new ArrayList<Mapping>();
    }
    public TableExpression getProvidedTableExpression(TableExpression source){
        Mapping mapping = getMapping(source);
        if(mapping != null) return mapping.target; 
        return createProvidedTableExpression(source);
    } 
    public TableExpression createProvidedTableExpression(TableExpression source){
        TableExpression target = new TableExpression(source.getTable(), getNextCorrelationName());
        mappings.add(new Mapping(source, target));
        return target;
    }
    private Mapping getMapping(TableExpression source){
        for(int i = 0;i < mappings.size();i++){
            Mapping mapping = mappings.get(i);
            
            if(mapping.source.equals(source)){
                return mapping;
            }
        }
        return null;
    }
    //private String getCurrentCorrelationName(){
    //    return correlation_name_generator.getExpression(radix_index);
    //}
    private String getNextCorrelationName(){
        radix_index++;
        return correlation_name_generator.getExpression(radix_index);
    }
    private static class Mapping{
        TableExpression source;
        TableExpression target;
        private Mapping(TableExpression source, TableExpression target){
            this.source = source;
            this.target = target;
        }
        @Override
        public String toString(){
            return "Mapping:source(" + source + "), target(" + target + ")";
        }
    }
}
