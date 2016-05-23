/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mina.sql;

import com.mina.util.CoreException;
import java.util.ArrayList;

/**
 *
 * @author mina
 */
public class Join extends Query implements TableCorrelationNameUpdatable {

    public static final int JOIN_TYPE_INNER = 0;
    public static final int JOIN_TYPE_LEFT = 1;
    public static final int JOIN_TYPE_RIGHT = 2;
    private int join_type;
    private Element element0;
    private Element element1;
    private Expression expr;

    public Join(int join_type, Element element0, Element element1, Expression expr) {
        this.join_type = join_type;
        this.element0 = element0;
        this.element1 = element1;
        this.expr = expr;
        if(expr == null)throw new CoreException("JOIN句のONの表現がありません。");
    }
    public ArrayList<TableExpression> getTableExpressions(){
        ArrayList<TableExpression> vec = new ArrayList<>();
        vec.addAll(element0.getTableExpressions());
        vec.addAll(element1.getTableExpressions());
        return vec;
    }

    @Override
    public void updateCorrelationName(TableCorrelationNameProvider tcnp) {
        element0.updateCorrelationName(tcnp);
        element1.updateCorrelationName(tcnp);
        expr.updateCorrelationName(tcnp);
    }
    @Override
    public void clearCorrelationName(){
        element0.clearCorrelationName();
        element1.clearCorrelationName();
        expr.clearCorrelationName();
    }
    public static abstract class Element extends Query implements TableCorrelationNameUpdatable{
        public abstract ArrayList<TableExpression> getTableExpressions();
    }

    public static class ElementTable extends Element {

        private TableExpression table_expr;

        public ElementTable(TableExpression table_expr) {
            this.table_expr = table_expr;
        }

        @Override
        public String getQuery() {
            return table_expr.getQuery();
        }

        @Override
        public ArrayList<TableExpression> getTableExpressions() {
            ArrayList<TableExpression> vec = new ArrayList<TableExpression>();
            vec.add(table_expr);
            return vec;
        }

        public void updateCorrelationName(TableCorrelationNameProvider tcnp) {
            table_expr = tcnp.getProvidedTableExpression(table_expr);
        }
        public void clearCorrelationName(){
            table_expr.clearCorrelaionName();
        }
    }

    public static class ElementJoin extends Element {

        private Join join;

        public ElementJoin(Join join) {
            this.join = join;
        }

        @Override
        public String getQuery() {
            return "(" + join.getQuery() + ")";
        }

        @Override
        public ArrayList<TableExpression> getTableExpressions() {
            return join.getTableExpressions();
        }

        public void updateCorrelationName(TableCorrelationNameProvider tcnp) {
            join.updateCorrelationName(tcnp);
        }
        public void clearCorrelationName(){
            join.clearCorrelationName();
        }
    }

    private static String getString(int join_type) {
        switch (join_type) {
            case JOIN_TYPE_INNER:
                return "INNER JOIN";
            case JOIN_TYPE_LEFT:
                return "LEFT JOIN";
            case JOIN_TYPE_RIGHT:
                return "RIGHT JOIN";
            default:
                throw new CoreException("不明なJOINタイプです。(" + join_type + ")");
        }
    }

    @Override
    public String getQuery() {
        return
                element0.getQuery() + " " + getString(join_type) + " " + element1.getQuery() +
                " ON " + expr.getQuery();
    }

}
