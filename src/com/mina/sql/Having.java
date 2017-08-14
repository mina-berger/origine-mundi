/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mina.sql;

/**
 *
 * @author mina
 */
public class Having extends Query implements TableCorrelationNameUpdatable {
    private Expression expr;
    public Having(){
        this((Expression)null);
    }
    public Having(Expression[] exprs){
        expr = Expression.getMultiAnd(exprs);
    }
    public Having(Expression expr){
        this.expr = expr;
    }

    @Override
    public String getQuery() {
        return (expr == null?"":" HAVING " + expr.getQuery());
    }

    public void updateCorrelationName(TableCorrelationNameProvider tcnp) {
        if(expr != null) expr.updateCorrelationName(tcnp);
    }

    public void clearCorrelationName() {
        if(expr != null) expr.clearCorrelationName();
    }

}
