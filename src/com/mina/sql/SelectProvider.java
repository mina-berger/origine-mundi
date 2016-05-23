/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mina.sql;

import com.mina.sql.Expression.And;
import com.mina.sql.Join.ElementJoin;
import com.mina.sql.Join.ElementTable;
import com.mina.sql.SelectItem.SelectColumn;
import java.sql.DatabaseMetaData;
import java.util.Stack;
import java.util.ArrayList;


/**
 *
 * @author mina
 */
public class SelectProvider {
    private ArrayList<SelectItem> source_items;
    private ArrayList<SelectItem> max_items;
    private ArrayList<SelectItem> min_items;
    private Expression expr;
    private Join join;
    private GroupBy group_by;
    private Having having;
    private OrderBy order_by;
    private TableCorrelationNameProvider tcnp;
    private SelectProvider(SelectItem[] source_select_items, Join join, Expression expr, GroupBy group_by, Having having, OrderBy order_by){
        source_items = new ArrayList<SelectItem>();
        for(int i = 0;i < source_select_items.length;i++){
            source_items.add(source_select_items[i]);
        }
        this.expr = expr;
        this.join = join;
        this.group_by = group_by;
        this.having = having;
        this.order_by = order_by;
        max_items = new ArrayList<SelectItem>();
        min_items = new ArrayList<SelectItem>();
        tcnp = null;
    }
    public void setOrderBy(OrderBy order_by){
        setOrderBy(order_by, true);
    }
    public void setOrderBy(OrderBy order_by, boolean update_correlation_name){
        this.order_by = order_by;
        if(update_correlation_name && this.order_by != null){
            this.order_by.updateCorrelationName(tcnp);
        }
    }
    public void setGroupBy(GroupBy group_by){
        setGroupBy(group_by, true);
    }
    public void setGroupBy(GroupBy group_by, boolean update_correlation_name){
        this.group_by = group_by;
        if(update_correlation_name && this.group_by != null){
            this.group_by.updateCorrelationName(tcnp);
        }
    }
    public void addAndExpression(Expression add_expr, boolean update_correlation_name){
        if(add_expr == null) return;
        Expression current_expr = getExpression();
        if(update_correlation_name){
            add_expr.updateCorrelationName(tcnp);
        }

        Expression new_expr =
                current_expr != null ? new And(current_expr, add_expr) : add_expr;
        setExpression(new_expr, false);
    }
    public void setExpression(Expression expr){
        setExpression(expr, true);
    }
    public void setExpression(Expression expr, boolean update_correlation_name){
        this.expr = expr;
        if(update_correlation_name && this.expr != null){
            this.expr.updateCorrelationName(tcnp);
        }
    }
    public Expression getExpression(){return expr;}
    public boolean hasExpression(){return expr != null;}
    public void removeExpression() {
        expr = null;
    }
    //public Select getSourceSelect(){
    //    return new Select(source_items, expr);
    //}
    public Select getMaxSelect(){
        return new Select(max_items, join, expr, group_by, having, order_by);
    }
    public Select getMinSelect(){
        return new Select(min_items, join, expr, group_by, having, order_by);
    }
    private boolean addMaxSelectItem(SelectItem select_item){
        max_items.add(select_item);
        return true;
    }
    private boolean addMinSelectItem(SelectItem select_item){
        min_items.add(select_item);
        return true;
    }
    
    public static SelectProvider instance(DatabaseMetaData dmd, Select source){
        TableCorrelationNameProvider tcnp = new TableCorrelationNameProvider();
        source.updateCorrelationName(tcnp);
        SelectItem[] select_items = source.getSelectItems();
        SelectProvider sp = new SelectProvider(select_items, 
                source.getJoin(), source.getExpression(), 
                source.getGroupBy(), source.getHaving(), source.getOrderBy());
        Stack<StackElement> stack = new Stack<StackElement>();
        
        for(int i = 0;i < select_items.length;i++){
            SelectItem select_item = select_items[i];
            if(select_item instanceof SelectColumn){
                SelectColumn select_column = (SelectColumn)select_item;
                stack.add(0, new SelectColumnCheck(select_column, true));
            }else{
                stack.add(0, new SelectItemHolder(select_item));
            }
        }
        while(!stack.empty()){
            addColumnType(dmd, tcnp, stack, sp);
        }
        sp.tcnp = tcnp;
        return sp;
    }
    private static void addColumnType(DatabaseMetaData dmd, TableCorrelationNameProvider tcnp, Stack<StackElement> stack, SelectProvider sp){
        StackElement stack_element = stack.pop();
        if(stack_element instanceof SelectItemHolder){
            SelectItem select_item = ((SelectItemHolder)stack_element).select_item;
            sp.addMaxSelectItem(select_item);
            sp.addMinSelectItem(select_item);
            return;
        }
        SelectColumnCheck ctc = (SelectColumnCheck)stack_element;
        SelectColumn select_column = ctc.getSelectColumn();
        ColumnType column_type = ctc.getColumnType();
        ForeignKeyInfo fk_info = ForeignKeyInfo.instance(dmd, column_type.getColumn());
        if (fk_info == null) {
            sp.addMaxSelectItem(select_column);
            
            if(ctc.getCheck()){
                sp.addMinSelectItem(select_column);
            }
            return;
        }
        
        ArrayList<ColumnType> column_type_fk_key = SQLUtil.getColumnTypes(dmd, fk_info.getFKColumns());
        TableExpression fk_table_expr = select_column.getTableExpression();
        for (int i = 0; i < column_type_fk_key.size(); i++) {
            SelectColumn fk_key_select_column = new SelectColumn(column_type_fk_key.get(i), fk_table_expr);
            sp.addMaxSelectItem(fk_key_select_column);
            stack.remove(new SelectColumnCheck(fk_key_select_column, true));
        }
        
        ArrayList<ColumnType> column_type_pk_all = SQLUtil.getColumnTypes(dmd, fk_info.getPKTable());
        ArrayList<ColumnType> column_type_pk_key = SQLUtil.getColumnTypes(dmd, fk_info.getPKColumns());
        TableExpression pk_table_expr = tcnp.createProvidedTableExpression(new TableExpression(fk_info.getPKTable()));
        for (int i = column_type_pk_all.size() - 1; i >= 0; i--) {
            ColumnType pk_column = column_type_pk_all.get(i);
            boolean check = !column_type_pk_key.contains(pk_column);
            stack.push(new SelectColumnCheck(new SelectColumn(pk_column, pk_table_expr), check));
        }
        Expression expr = null;
        
        for (int i = 0; i < fk_info.length();i++) {
            Expression pk_expr = new Expression.Items(
                    SQLUtil.getSelectColumn(dmd, fk_info.getFKColumn(i), fk_table_expr), 
                    SQLUtil.getSelectColumn(dmd, fk_info.getPKColumn(i), pk_table_expr), Expression.EQ);
            if (expr == null) {
                expr = pk_expr;
            } else {
                expr = new Expression.And(expr, pk_expr);
            }
        }
        if(sp.join == null){
            sp.join = new Join(
                    Join.JOIN_TYPE_LEFT, 
                    new ElementTable(fk_table_expr),
                    new ElementTable(pk_table_expr),
                    expr);
        }else{
            sp.join = new Join(
                    Join.JOIN_TYPE_LEFT, 
                    new ElementJoin(sp.join),
                    new ElementTable(pk_table_expr),
                    expr);
        }
    }
    private static class StackElement{}
    @SuppressWarnings("rawtypes")
	private static class SelectItemHolder extends StackElement implements Comparable {
        private SelectItem select_item;
        SelectItemHolder(SelectItem select_item){
            this.select_item = select_item;
        }
        public int compareTo(Object o) {
            return select_item.compareTo(((SelectItemHolder)o).select_item);
        }
        @Override
        public boolean equals(Object o){
            return compareTo(o) == 0;
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 67 * hash + (this.select_item != null ? this.select_item.hashCode() : 0);
            return hash;
        }
        
    }
    @SuppressWarnings("rawtypes")
	private static class SelectColumnCheck extends StackElement implements Comparable {
        private SelectColumn select_column;
        private boolean check;
        SelectColumnCheck(SelectColumn select_column, boolean check){
            this.select_column = select_column;
            this.check = check;
        }
        SelectColumn getSelectColumn(){return select_column;}
        ColumnType getColumnType(){return select_column.getColumnType();}
        boolean getCheck(){return check;}

        public int compareTo(Object o) {
            return select_column.compareTo(((SelectColumnCheck)o).select_column);
        }
        @Override
        public boolean equals(Object o){
            return compareTo(o) == 0;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 59 * hash + (this.select_column != null ? this.select_column.hashCode() : 0);
            hash = 59 * hash + (this.check ? 1 : 0);
            return hash;
        }

    }

}
