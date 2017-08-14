/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mina.sql;

import com.mina.util.Code;
import com.mina.util.Code.Option;
import java.util.ArrayList;

/**
 *
 * @author mina
 */
public class Select extends ExpressionQuery implements TableCorrelationNameUpdatable {

    protected ArrayList<SelectItem> select_items;
    protected Join join;
    protected GroupBy group_by;
    protected Having having;
    protected OrderBy order_by;

    public Select() {
        this(new ArrayList<SelectItem>(), null, null, null, null, null);
    }

    public Select(SelectItem select_item) {
        this(new ArrayList<SelectItem>(), null, null, null, null, null);
        addSelectItem(select_item);
    }

    public Select(SelectItem[] select_items) {
        this(new ArrayList<SelectItem>(), null, null, null, null, null);
        addSelectItems(select_items);
    }

    public Select(SelectItem select_item, Expression expr) {
        this(new ArrayList<SelectItem>(), null, expr, null, null, null);
        addSelectItem(select_item);
    }

    public Select(SelectItem select_item, Expression[] and_exprs) {
        this(new ArrayList<SelectItem>(), null, Expression.getMultiAnd(and_exprs), null, null, null);
        addSelectItem(select_item);
    }

    public Select(SelectItem[] select_items, Expression[] and_exprs) {
        this(new ArrayList<SelectItem>(), null, Expression.getMultiAnd(and_exprs), null, null, null);
        addSelectItems(select_items);
    }

    public Select(SelectItem[] select_items, Expression expr) {
        this(new ArrayList<SelectItem>(), null, expr, null, null, null);
        addSelectItems(select_items);
    }

    public Select(ArrayList<SelectItem> select_items, Expression expr) {
        this(select_items, null, expr, null, null, null);
    }

    public Select(ArrayList<SelectItem> select_items, Join join, Expression expr) {
        this(select_items, join, expr, null, null, null);
    }

    public Select(ArrayList<SelectItem> select_items, Join join, Expression expr, OrderBy order_by) {
        this(select_items, join, expr, null, null, order_by);
    }

    public Select(SelectItem select_item, Expression expr, GroupBy group_by) {
        this(new ArrayList<SelectItem>(), null, expr, group_by, null, null);
        addSelectItem(select_item);
    }

    public Select(SelectItem[] select_items, Expression expr, GroupBy group_by, OrderBy order_by) {
        this(new ArrayList<SelectItem>(), null, expr, group_by, null, order_by);
        addSelectItems(select_items);
    }

    public Select(SelectItem select_item, Expression expr, GroupBy group_by, Having having) {
        this(new ArrayList<SelectItem>(), null, expr, group_by, having, null);
        addSelectItem(select_item);
    }

    public Select(ArrayList<SelectItem> select_items, Join join, Expression expr, GroupBy group_by, Having having, OrderBy order_by) {
        super(expr);
        this.join = join;
        this.select_items = select_items;
        this.group_by = group_by;
        this.order_by = order_by;
        this.having = having;
    }

    @Override
    public void updateCorrelationName(TableCorrelationNameProvider tcnp) {
        for (int i = 0; i < getItemLength(); i++) {
            getSelectItem(i).updateCorrelationName(tcnp);
        }
        if (hasJoin()) {
            getJoin().updateCorrelationName(tcnp);
        }
        //?
        if (hasGroupBy()) {
            getGroupBy().updateCorrelationName(tcnp);
        }
        if (hasHaving()) {
            getHaving().updateCorrelationName(tcnp);
        }
        //?
        if (hasOrderBy()) {
            getOrderBy().updateCorrelationName(tcnp);
        }
        super.updateCorrelationName(tcnp);
    }

    public void setJoin(Join join) {
        this.join = join;
    }

    public Join getJoin() {
        return join;
    }

    public boolean hasJoin() {
        return join != null;
    }

    public void setGroupBy(GroupBy group_by) {
        this.group_by = group_by;
    }

    public GroupBy getGroupBy() {
        return group_by;
    }

    public boolean hasGroupBy() {
        return group_by != null;
    }

    public void setHaving(Having having) {
        this.having = having;
    }

    public Having getHaving() {
        return having;
    }

    public boolean hasHaving() {
        return having != null;
    }

    public void setOrderBy(OrderBy order_by) {
        this.order_by = order_by;
    }

    public OrderBy getOrderBy() {
        return order_by;
    }

    public boolean hasOrderBy() {
        return order_by != null;
    }

    public void addSelectItems(SelectItem[] sis) {
        for (int i = 0; i < sis.length; i++) {
            select_items.add(sis[i]);
        }
    }

    public void addSelectItems(ArrayList<SelectItem> sis) {
        select_items.addAll(sis);
    }

    public void addSelectItem(SelectItem si) {
        select_items.add(si);
    }

    public void remove(SelectItem si) {

        while (select_items.remove(si)) {
        }
    }

    public boolean isColumnEmpty() {
        return select_items.isEmpty();
    }

    protected String[] getItemQueryArray() {
        String[] column_names = new String[getItemLength()];
        for (int i = 0; i < getItemLength(); i++) {
            column_names[i] = getSelectItem(i).getQuery();
        }
        return column_names;
    }

    protected TableExpression[] getTableExpressions() {
        ArrayList<TableExpression> table_exprs = new ArrayList<TableExpression>();
        for (int i = 0; i < getItemLength(); i++) {
            TableExpression table_expr = getSelectItem(i).getTableExpression();
            if (table_expr != null && !table_exprs.contains(table_expr)) {
                table_exprs.add(table_expr);
            }
        }
        if (hasExpression()) {
            ArrayList<SelectItem> expr_items = expr.getSelectItems();
            for (int i = 0; i < expr_items.size(); i++) {
                TableExpression table_expr = expr_items.get(i).getTableExpression();
                if (table_expr != null && !table_exprs.contains(table_expr)) {
                    table_exprs.add(table_expr);
                }
            }
        }
        if (join != null) {
            ArrayList<TableExpression> join_tables = join.getTableExpressions();
            for (int i = 0; i < join_tables.size(); i++) {
                TableExpression table_expr = join_tables.get(i);
                if (table_expr != null && !table_exprs.contains(table_expr)) {
                    table_exprs.add(table_expr);
                }
            }
        }
        return table_exprs.toArray(new TableExpression[0]);
    }

    protected String[] getSchemaTableNames() {
        TableExpression[] table_exprs = getTableExpressions();
        String[] schema_table_names = new String[table_exprs.length];
        for (int i = 0; i < table_exprs.length; i++) {
            schema_table_names[i] = table_exprs[i].getQuery();
        }
        return schema_table_names;
    }

    public Table[] getTables() {
        TableExpression[] table_exprs = getTableExpressions();
        ArrayList<Table> tables = new ArrayList<Table>();
        for (int i = 0; i < table_exprs.length; i++) {
            Table table = table_exprs[i].getTable();
            if (!tables.contains(table)) {
                tables.add(table);
            }
        }
        return tables.toArray(new Table[0]);
    }

    public Code getSelectItemCode() {
        Option[] options = new Option[getItemLength()];
        for (int i = 0; i < getItemLength(); i++) {
            options[i] = getSelectItem(i).getOption();
        }
        return new Code("選択カラム", options);
    }

    public int getItemLength() {
        return select_items.size();
    }

    public SelectItem[] getSelectItems() {
        return select_items.toArray(new SelectItem[0]);
    }

    public SelectItem getSelectItem(int index) {
        return select_items.get(index);
    }

    public ColumnType[] getColumnTypes() {
        ArrayList<ColumnType> vec = new ArrayList<ColumnType>();
        for (int i = 0; i < getItemLength(); i++) {
            vec.addAll(getSelectItem(i).getColumnTypes());
        }
        return vec.toArray(new ColumnType[0]);
    }

    @Override
    public String getQuery() {
        return "SELECT " + SQLUtil.getCsvString(getItemQueryArray()) + " "
                + getFromWherePhrase() + (hasOrderBy() ? getOrderBy().getQuery() : "");
        //return
        //        "SELECT " + SQLUtil.getCsvString(getItemQueryArray()) +
        //        " FROM "  + (join == null?SQLUtil.getCsvString(getSchemaTableNames()):join.getQuery()) +
        //       getWhere();

    }

    //public String
    public String getFromWherePhrase() {
        return "FROM " + (join == null ? SQLUtil.getCsvString(getSchemaTableNames()) : join.getQuery())
                + getWhere() + (hasGroupBy() ? getGroupBy().getQuery() : "") + (hasHaving() ? getHaving().getQuery() : "");
    }
}
