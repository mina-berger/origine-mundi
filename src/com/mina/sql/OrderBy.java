/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mina.sql;

import com.mina.sql.Function.SimpleFunction;
import com.mina.sql.SelectItem.SelectColumn;
import java.sql.DatabaseMetaData;
import java.util.ArrayList;

/**
 *
 * @author mina
 */
public class OrderBy extends Query implements TableCorrelationNameUpdatable {
    public static final boolean ASC = true;
    public static final boolean DESC = false;
    private ArrayList<OrderItem> order_items;
    public OrderBy(){
        this(new OrderItem[0]);
    }
    public OrderBy(OrderItem[] order_item_array){
        order_items = new ArrayList<OrderItem>();
        for(int i = 0;i < order_item_array.length;i++){
            add(order_item_array[i]);
        }
    }
    public void add(SelectColumn select_column){
       add(select_column, ASC);
    }
    public void add(SelectColumn select_column, boolean asc_desc){
        add(new OrderItem(select_column, asc_desc));
    }
    private void add(OrderItem order_item){
        order_items.add(order_item);
    }

    /*private static class OrderColumn extends Query implements TableCorrelationNameUpdatable {
        private SelectColumn select_column;
        private boolean asc_desc;
        private OrderColumn(SelectColumn select_column, boolean asc_desc){
            this.select_column = select_column;
            this.asc_desc = asc_desc;
        }

        @Override
        public String getQuery() {
            return select_column.getName() + " " + (asc_desc?"ASC":"DESC");
        }

        public void updateCorrelationName(TableCorrelationNameProvider tcnp) {
            select_column.updateCorrelationName(tcnp);
        }

        public void clearCorrelationName() {
            select_column.clearCorrelationName();
        }
    }*/
    public static class OrderItem extends Query implements TableCorrelationNameUpdatable {
        private SelectItem select_item;
        private boolean asc_desc;
        public OrderItem(SelectItem select_item, boolean asc_desc){
            this.select_item = select_item;
            this.asc_desc = asc_desc;
            //System.out.println(getQuery());
        }

        @Override
        public String getQuery() {
            return select_item.getName() + " " + (asc_desc?"ASC":"DESC");
        }

        public void updateCorrelationName(TableCorrelationNameProvider tcnp) {
            select_item.updateCorrelationName(tcnp);
        }

        public void clearCorrelationName() {
            select_item.clearCorrelationName();
        }
    }
    protected String[] getColumnQueryArray(){
        String[] query_array = new String[order_items.size()];
        for(int i = 0;i < order_items.size();i++){
            query_array[i] = order_items.get(i).getQuery();
        }
        return query_array;
    }

    @Override
    public String getQuery() {
        if(order_items.size() == 0) return "";
        return " ORDER BY " + SQLUtil.getCsvString(getColumnQueryArray());
    }

    public void updateCorrelationName(TableCorrelationNameProvider tcnp) {
        for(int i = 0;i < order_items.size();i++){
            order_items.get(i).updateCorrelationName(tcnp);
        }
    }
    public void clearCorrelationName(){
        for(int i = 0;i < order_items.size();i++){
            order_items.get(i).clearCorrelationName();
        }
    }
    public static class OrderByInput {
        private String dictionary_table_column_name;
        private boolean asc_desc;
        private String one_param_function_name;
        public OrderByInput(String dictionary_table_column_name){
            this(null, dictionary_table_column_name, ASC);
        }
        public OrderByInput(String dictionary_table_column_name, boolean asc_desc){
            this(null, dictionary_table_column_name, asc_desc);
        }
        public OrderByInput(String one_param_function_name, String dictionary_table_column_name){
            this(one_param_function_name, dictionary_table_column_name, ASC);
        }
        public OrderByInput(String one_param_function_name, String dictionary_table_column_name, boolean asc_desc){
            this.one_param_function_name = one_param_function_name;
            this.dictionary_table_column_name = dictionary_table_column_name;
            this.asc_desc = asc_desc;
        }
    }
    public static OrderBy instance(DatabaseMetaData dmd, OrderByInput input){
        return instance(dmd, new OrderByInput[]{input});
    }
    public static OrderBy instance(DatabaseMetaData dmd, OrderByInput[] inputs){
        if(inputs == null || inputs.length == 0) return new OrderBy();
        ArrayList<OrderItem> order_columns = new ArrayList<OrderItem>();
        for(int i = 0;i < inputs.length;i++){
            if(inputs[i] == null) continue;
            SelectItem select_item =
                    inputs[i].one_param_function_name != null?
                        new SimpleFunction(
                            inputs[i].one_param_function_name,
                            SQLUtil.getSelectColumn(dmd, SQLUtil.getColumnType(dmd, inputs[i].dictionary_table_column_name).getColumn())):
                        SQLUtil.getSelectColumn(dmd, SQLUtil.getColumnType(dmd, inputs[i].dictionary_table_column_name).getColumn());
            order_columns.add(new OrderItem(select_item, inputs[i].asc_desc));
        }
        return new OrderBy(order_columns.toArray(new OrderItem[]{}));
    }
    

}
