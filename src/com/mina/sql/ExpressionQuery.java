/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mina.sql;

import com.mina.util.CoreException;

/**
 *
 * @author mina
 */
public abstract class ExpressionQuery extends Query implements TableCorrelationNameUpdatable {

    protected Expression expr;
    private boolean SWITCH_EXPRESSION;

    protected ExpressionQuery() {
        this(true);
    }

    protected ExpressionQuery(Expression expr) {
        SWITCH_EXPRESSION = true;
        this.expr = expr;
    }
    protected ExpressionQuery(boolean switch_expression) {
        SWITCH_EXPRESSION = switch_expression;
        expr = null;
    }

    public boolean hasExpression() {
        if(!SWITCH_EXPRESSION) throw new CoreException("SWITCH_EXPRESSION is not activated.");
        return expr != null;
    }

    public Expression getExpression() {
        if(!SWITCH_EXPRESSION) throw new CoreException("SWITCH_EXPRESSION is not activated.");
        return expr;
    }

    public void setExpression(Expression expr) {
        if(!SWITCH_EXPRESSION) throw new CoreException("SWITCH_EXPRESSION is not activated.");
        this.expr = expr;
    }

    public void removeExpression() {
        if(!SWITCH_EXPRESSION) throw new CoreException("SWITCH_EXPRESSION is not activated.");
        expr = null;
    }
    public String getWhere(){
        if(!SWITCH_EXPRESSION) throw new CoreException("SWITCH_EXPRESSION is not activated.");
        return getWhere(expr);
    }
    public static String getWhere(Expression expr){
        return (expr == null?"":" WHERE " + expr.getQuery());
    }

    public void updateCorrelationName(TableCorrelationNameProvider tcnp) {
        if(expr != null) expr.updateCorrelationName(tcnp);
    }
    public void clearCorrelationName(){
        if(expr != null) expr.clearCorrelationName();
    }
   
}
