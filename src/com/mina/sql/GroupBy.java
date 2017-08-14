/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mina.sql;

import com.mina.sql.SelectItem.SelectColumn;
import java.sql.DatabaseMetaData;
import java.util.ArrayList;

/**
 *
 * @author mina
 */
public class GroupBy extends Query implements TableCorrelationNameUpdatable {
    private ArrayList<SelectColumn> select_columns; 
    public GroupBy(){
        this(new SelectColumn[0]);
    }
    private GroupBy(SelectColumn[] select_column_array){
        select_columns = new ArrayList<SelectColumn>();
        for(int i = 0;i < select_column_array.length;i++){
            add(select_column_array[i]);
        }
    }
    public void add(SelectColumn select_column){
       select_columns.add(select_column);
    }

    protected String[] getColumnQueryArray(){
        String[] query_array = new String[select_columns.size()];
        for(int i = 0;i < select_columns.size();i++){
            query_array[i] = select_columns.get(i).getQuery();
        }
        return query_array;
    }
    public int selectColumnSize(){return select_columns.size();}
    public SelectColumn getSelectColumn(int index){ return select_columns.get(index);}

    @Override
    public String getQuery() {
        if(select_columns.size() == 0) return "";
        return " GROUP BY " + SQLUtil.getCsvString(getColumnQueryArray());
    }

    public void updateCorrelationName(TableCorrelationNameProvider tcnp) {
        for(int i = 0;i < select_columns.size();i++){
            select_columns.get(i).updateCorrelationName(tcnp);
        }
    }
    public void clearCorrelationName(){
        for(int i = 0;i < select_columns.size();i++){
            select_columns.get(i).clearCorrelationName();
        }
    }
    public static class GroupByInput {
        private String[] dict_names;
        public GroupByInput(){
            this(new String[0]);
        }
        public GroupByInput(String dict_name){
            this(new String[]{dict_name});
        }
        public GroupByInput(String[] dict_names){
            this.dict_names = dict_names;
        }
    }
    public static GroupBy instance(DatabaseMetaData dmd, GroupByInput input){
        if(input == null) return new GroupBy();
        String[] dict_names = input.dict_names;
        if(dict_names == null || dict_names.length == 0) return new GroupBy();
        SelectColumn[] select_columns = new SelectColumn[dict_names.length];
        for(int i = 0;i < dict_names.length;i++){
            select_columns[i] = 
                    SQLUtil.getSelectColumn(dmd, SQLUtil.getColumnType(dmd, dict_names[i]).getColumn());
        }
        return new GroupBy(select_columns);
    }
    

}
