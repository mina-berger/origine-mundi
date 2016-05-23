/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mina.sql;

import com.mina.sql.Function.LCase;
import com.mina.sql.SelectItem.SelectColumn;
import com.mina.sql.SelectItem.SelectPhrase;
import com.mina.sql.TypeInfoHolder.TypeInfo;
import com.mina.util.Code;
import com.mina.util.Code.Option;
import com.mina.util.CoreException;
import java.util.ArrayList;

/**
 *
 * @author mina
 */
public abstract class Expression extends Query implements TableCorrelationNameUpdatable {
    public static final short EQ     = 0;
    public static final short NOT_EQ = 1;
    public static final short GT     = 2;
    public static final short GT_EQ  = 3;
    public static final short LT     = 4;
    public static final short LT_EQ  = 5;
    public static final short LIKE   = 6;
    public static final boolean AND = true;
    public static final boolean OR = false;

    //protected abstract String getQuery();
    protected abstract boolean hasChildren();
    protected abstract ArrayList<Expression> getChildren();
    protected abstract ArrayList<SelectItem> getSelectItems();
    @Override
    public String toString(){
        return getQuery();
    }
    public static class NoSelect extends Expression {
        @Override
        protected boolean hasChildren() { return false;}
        @Override
        protected ArrayList<Expression> getChildren() {return new ArrayList<Expression>();}
        @Override
        protected ArrayList<SelectItem> getSelectItems() {return new ArrayList<SelectItem>();}
        @Override
        public String getQuery() { return "1 = 0";}
        public void updateCorrelationName(TableCorrelationNameProvider tcnp) {}
        public void clearCorrelationName() {}

    }
    public static class In extends SubQuery {
        public In(SelectItem item, SelectPhrase phrase){
            super("IN", item, phrase, false);
        }
        public In(SelectItem item, SelectPhrase phrase, boolean is_not){
            super("IN", item, phrase, is_not);
        }
    }
    public static class Exists extends SubQuery {
        public Exists(SelectPhrase phrase){
            super("EXISTS", null, phrase, false);
        }
        public Exists(SelectPhrase phrase, boolean is_not){
            super("EXISTS", null, phrase, is_not);
        }
    }
    public abstract static class SubQuery extends Expression {
        private String word;
        private SelectItem item;
        private SelectPhrase phrase;
        private boolean is_not;

        public SubQuery(String word, SelectItem item, SelectPhrase phrase, boolean is_not){
            this.word = word;
            this.item = item;
            this.phrase = phrase;
            this.is_not = is_not;
        }
        public String getQuery() {
            return (item == null?"":item.getName() + " ") + (is_not?"NOT ":"") +  word + " " + phrase.getName();
        }
        protected boolean hasChildren(){return false;}
        protected ArrayList<Expression> getChildren(){return new ArrayList<Expression>();}
        protected ArrayList<SelectItem> getSelectItems(){
            ArrayList<SelectItem> vec = new ArrayList<SelectItem>();
            vec.add(item);
            //vec.add(phrase);
            return vec;
        }

        public void updateCorrelationName(TableCorrelationNameProvider tcnp) {
            item.updateCorrelationName(tcnp);
            //phrase.updateCorrelationName(tcnp);
        }

        public void clearCorrelationName() {
            item.clearCorrelationName();
            //phrase.clearCorrelationName();
        }

    }
    public static class Items extends Expression {
        private SelectItem item0;
        private SelectItem item1;
        private short equation;
        public Items(SelectItem item0, SelectItem item1, short equation){
            this.item0 = item0;
            this.item1 = item1;
            this.equation = equation;
        }
        public String getQuery() {
            return item0.getName() + " " + getEquation(equation) + " " + item1.getName();
        }
        protected boolean hasChildren(){return false;}
        protected ArrayList<Expression> getChildren(){return new ArrayList<Expression>();}
        protected ArrayList<SelectItem> getSelectItems(){
            ArrayList<SelectItem> vec = new ArrayList<SelectItem>();
            vec.add(item0);
            vec.add(item1);
            return vec;
        }

        public void updateCorrelationName(TableCorrelationNameProvider tcnp) {
            item0.updateCorrelationName(tcnp);
            item1.updateCorrelationName(tcnp);
        }

        public void clearCorrelationName() {
            item0.clearCorrelationName();
            item1.clearCorrelationName();
        }

    }
    public static class IsNull extends Expression {
        private SelectItem item;
        private boolean is_not;
        public IsNull(SelectItem item){
            this(item, false);
        }
        public IsNull(SelectItem item, boolean is_not){
            this.item = item;
            this.is_not = is_not;
        }
        public String getQuery(){
            return item.getName() + " IS" + (is_not?" NOT":"") + " NULL";
        }
        protected boolean hasChildren(){return false;}
        protected ArrayList<Expression> getChildren(){return new ArrayList<Expression>();}

        @Override
        protected ArrayList<SelectItem> getSelectItems() {
            ArrayList<SelectItem> vec = new ArrayList<SelectItem>();
            vec.add(item);
            return vec;
        }

        public void updateCorrelationName(TableCorrelationNameProvider tcnp) {
            item.updateCorrelationName(tcnp);
        }

        public void clearCorrelationName() {
            item.clearCorrelationName();
        }
    }
    public static class Value extends Expression {
        private SelectItem item;
        private String value_expression;
        private short equation;
        public Value(SelectItem item, String value_expression, short equation){
            this.item = item;
            this.value_expression = value_expression;
            this.equation = equation;
        }
        public String getQuery(){
            String str_equation = null;
            if(value_expression.equals(Query.NULL)){
                if(equation == EQ) str_equation = "IS";
                else if(equation == NOT_EQ) str_equation = "IS NOT";
            }
            if(str_equation == null)str_equation = getEquation(equation);
            return item.getName() + " " + str_equation + " " + value_expression;
        }
        protected boolean hasChildren(){return false;}
        protected ArrayList<Expression> getChildren(){return new ArrayList<Expression>();}

        @Override
        protected ArrayList<SelectItem> getSelectItems() {
            ArrayList<SelectItem> vec = new ArrayList<SelectItem>();
            vec.add(item);
            return vec;
        }

        public void updateCorrelationName(TableCorrelationNameProvider tcnp) {
            //Terminal.println("DEBUG" + item.getTableExpression().toString());
            //Terminal.readLine();
            item.updateCorrelationName(tcnp);
        }

        public void clearCorrelationName() {
            item.clearCorrelationName();
        }
    }
    public static class And extends AndOr {
        public And(Expression expr0, Expression expr1){
            super(expr0, expr1, AND);
        }
    }
    public static class Or extends AndOr {
        public Or(Expression expr0, Expression expr1){
            super(expr0, expr1, OR);
        }
    }
    public static class AndOr extends Expression {
        private Expression expr0, expr1;
        private boolean and_or;
        public AndOr(Expression expr0, Expression expr1, boolean and_or){
            this.expr0 = expr0;
            this.expr1 = expr1;
            this.and_or = and_or;
        }
        public String getQuery(){
            return
                "(" + expr0.getQuery() +  " " + getAndOr(and_or) + " " +  expr1.getQuery() + ")";
        }
        protected boolean hasChildren(){return true;}
        protected ArrayList<Expression> getChildren(){
            ArrayList<Expression> vec = new ArrayList<Expression>();
            vec.addAll(listAllExpression(expr0));
            vec.addAll(listAllExpression(expr1));
            return vec;
        }

        @Override
        protected ArrayList<SelectItem> getSelectItems() {
            ArrayList<SelectItem> vec = new ArrayList<SelectItem>();
            vec.addAll(expr0.getSelectItems());
            vec.addAll(expr1.getSelectItems());
            return vec;
        }


        public void updateCorrelationName(TableCorrelationNameProvider tcnp) {
            expr0.updateCorrelationName(tcnp);
            expr1.updateCorrelationName(tcnp);
        }

        public void clearCorrelationName() {
            expr0.clearCorrelationName();
            expr1.clearCorrelationName();
        }
    }
    public static ArrayList<Expression> listAllExpression(Expression expr){
        ArrayList<Expression> vec = expr.hasChildren()?expr.getChildren():new ArrayList<Expression>();
        vec.add(expr);
        return vec;
    }
    public static String getAndOr(boolean and_or){
        return AND_OR.getOption(Boolean.toString(and_or)).getName();
        //return (and_or == AND)?"AND":"OR";
    }
    public static Expression getMultiOr(Expression[] exprs){
        return getMulti(exprs, OR);
    }
    public static Expression getMultiAnd(Expression[] exprs){
        return getMulti(exprs, AND);
        /*Expression expr = null;
        for(int i = 0;i < exprs.length;i++){
            if(expr == null)expr = exprs[i];
            else expr = new AndOr(expr, exprs[i], AND);
        }
        return expr;*/
    }
    public static Expression getMulti(Expression[] exprs, boolean and_or){
        Expression expr = null;
        for(int i = 0;i < exprs.length;i++){
            if(expr == null)expr = exprs[i];
            else expr = new AndOr(expr, exprs[i], and_or);
        }
        return expr;
    }
    public static Expression getExpression(ColumnValue column_value){
        return getExpression(new ColumnValue[]{column_value});
    }
    public static Expression getExpression(ColumnValue[] column_value_array){
        Expression expr = null;
        for(int i = 0;i < column_value_array.length;i++){
            Value value = new Value(
                    new SelectColumn(column_value_array[i].getColumnType()),
                    column_value_array[i].getValueExpression(), EQ);
            if(expr == null) expr = value;
            else expr = new AndOr(expr, value, AND);
        }
        return expr;
    }
    public static String getKeywordString(String str){
        String keyword_expr = str;
        keyword_expr = (keyword_expr.startsWith("$")?
            keyword_expr.substring(1):"%" + keyword_expr);
        keyword_expr = (keyword_expr.endsWith("$")?
            keyword_expr.substring(0, keyword_expr.length() - 1):keyword_expr + "%");
        System.out.println(keyword_expr);
        return keyword_expr;
    }
    public static Expression getKeywordExpression(String keyword, SelectItem[] select_items, TypeInfoHolder ti) {
        Expression expr = null;
        String keyword_expr = getKeywordString(keyword);
        for (int i = 0; i < select_items.length; i++) {
            String type_name = select_items[i].getTypeName();
            if(type_name == null) continue;
            TypeInfo type_info = ti.getTypeInfo(type_name);
            int seachable = type_info.getSeachable();
            Expression temp_expr;
            if (seachable == TypeInfoHolder.SEARCH_BOTH || seachable == TypeInfoHolder.SEARCH_CHAR) {
                //String value_expr = "LCASE(" + type_info.getValueExpression("%" + keyword + "%") + ")";
                String value_expr = "LCASE(" + type_info.getValueExpression(keyword_expr) + ")";
                temp_expr = new Value(new LCase(select_items[i]), value_expr, LIKE);
            //}else if(seachable == TypeInfoHolder.SEARCH_BASIC){
            //    String value_expr = SqlUtil.getValueExpression(keyword, type_info);
            //    temp_expr = new Value(column_code.getOptionName(i), value_expr, Expression.EQ);
            } else {
                continue;
            }
            expr = expr == null ? temp_expr : new AndOr(expr, temp_expr, OR);
        }
        return expr;
    }

    public static Code AND_OR = new Code(
            "連結種別",
            new Option[]{
                new Option(Boolean.toString(AND), "AND", AND),
                new Option(Boolean.toString(OR),  "OR",  OR)});
    public static Code EQUATION = new Code(
            "等号種別",
            new Option[]{
                new Option(Short.toString(EQ),     "=",    EQ),
                new Option(Short.toString(NOT_EQ), "!=",   NOT_EQ),
                new Option(Short.toString(GT),     ">",    GT),
                new Option(Short.toString(GT_EQ),  ">=",   GT_EQ),
                new Option(Short.toString(LT),     "<",    LT),
                new Option(Short.toString(LT_EQ),  "<=",   LT_EQ),
                new Option(Short.toString(LIKE),   "LIKE", LIKE)});
    public static String getEquation(short equation){
        Option option = EQUATION.getOption(Short.toString(equation));
        if(option == null) throw new CoreException("期待されない等号種別です。");
        return option.getName();
    }
}
