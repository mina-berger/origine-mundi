/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mina.sql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.ArrayList;

import com.mina.sql.Expression.And;
import com.mina.sql.Expression.Exists;
import com.mina.sql.Expression.In;
import com.mina.sql.Expression.IsNull;
import com.mina.sql.Expression.Items;
import com.mina.sql.Expression.NoSelect;
import com.mina.sql.Expression.Or;
import com.mina.sql.Expression.Value;
import com.mina.sql.GroupBy.GroupByInput;
import com.mina.sql.OrderBy.OrderByInput;
import com.mina.sql.SelectItem.SelectColumn;
import com.mina.sql.SelectItem.SelectPhrase;
import com.mina.sql.TypeInfoHolder.TypeInfo;
import com.mina.util.Code;
import com.mina.util.Code.Option;
import com.mina.util.CoreException;
import com.mina.util.TextUtil;

/**
 *
 * @author mina
 */
public class SQLUtil extends TextUtil {

    /*public static Function getFunction(DatabaseMetaData dmd, String func_name, String dict_name){
        return new Function(func_name, getSelectColumn(dmd, dict_name));
    }*/
    public static void updateColumnValue(DatabaseMetaData dmd, UpdateQuery uq, Record record, ColumnType column_type) {
        updateColumnValue(dmd, uq, column_type, record, column_type);
    }

    public static void updateColumnValue(
            DatabaseMetaData dmd, UpdateQuery uq, ColumnType update_column_type,
            Record record, ColumnType source_column_type) {
        //TypeInfo type_info = TypeInfoHolder.get(dmd, source_column_type);
        //String value = type_info.getValueExpression(record.getValue(source_column_type).toString());
        //ColumnValue cv = new ColumnValue(update_column_type, value);
        uq.updateColumnValue(getColumnValue(dmd, update_column_type, record, source_column_type));
    }

    public static ColumnValues getColumnValues(DatabaseMetaData dmd, Record record, String[] dict_names) {
        ColumnValues cv = new ColumnValues();
        //ColumnType[] column_types = getColumnTypes(dmd, dict_names);
        for (int i = 0; i < dict_names.length; i++) {
            cv.add(dmd, dict_names[i], record, dict_names[i]);
        }
        return cv;
    }

    public static ColumnValue getColumnValue(
            DatabaseMetaData dmd, Record record, ColumnType column_type) {
        return getColumnValue(dmd, column_type, record, column_type);
    }

    public static ColumnValue getColumnValue(
            DatabaseMetaData dmd, ColumnType update_column_type,
            Record record, ColumnType source_column_type) {
        TypeInfo type_info = TypeInfoHolder.get(dmd, source_column_type);
        Object o_value = record.getValue(source_column_type);
        if (o_value == null) {
            return ColumnValue.nullValue(update_column_type);
        }
        String value = type_info.getValueExpression(o_value.toString());
        return new ColumnValue(update_column_type, value);

    }

    public static Records getImportedKeys(DatabaseMetaData dmd, Table table) {
        try {
            return new Records(dmd.getImportedKeys(table.getCatalogName(), table.getSchemaName(), table.getTableName()));
        } catch (SQLException e) {
            //Terminal.println(table.getExplanation());
            throw new CoreException(e);
        }
    }

    public static Records getPrimaryKeys(DatabaseMetaData dmd, Table table) {
        try {
            return new Records(dmd.getPrimaryKeys(table.getCatalogName(), table.getSchemaName(), table.getTableName()));
        } catch (SQLException e) {
            throw new CoreException(e);
        }
    }

    public static void releaseSavepoint(DatabaseMetaData dmd, Savepoint savepoint) {
        try {
            Connection conn = dmd.getConnection();
            if (!conn.getAutoCommit()) {
                dmd.getConnection().releaseSavepoint(savepoint);
            }
        } catch (SQLException e) {
            throw new CoreException(e);
        }
    }

    public static Savepoint transactionStart(DatabaseMetaData dmd) {
        try {
            Connection conn = dmd.getConnection();
            conn.setAutoCommit(false);
            return conn.setSavepoint();
        } catch (SQLException e) {
            throw new CoreException(e);
        }
    }

    public static void transactionCommit(DatabaseMetaData dmd) {
        try {
            Connection conn = dmd.getConnection();
            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            throw new CoreException(e);
        }
    }

    public static void transactionRollback(DatabaseMetaData dmd, Savepoint savepoint) {
        try {
            Connection conn = dmd.getConnection();
            if (savepoint == null) {
                conn.rollback();
            } else {
                conn.rollback(savepoint);
            }
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            throw new CoreException(e);
        }
    }

    public static Records getRecords(DatabaseMetaData dmd, String table_name, Expression expr) {
        return getRecords(dmd, getTable(dmd, table_name), expr, new GroupByInput(), new OrderByInput[0], ColumnAlias.getColumnAlias(dmd));
    }

    public static Records getRecords(DatabaseMetaData dmd, Table table, Expression expr) {
        return getRecords(dmd, table, expr, new GroupByInput(), new OrderByInput[0], ColumnAlias.getColumnAlias(dmd));
    }

    public static Records getRecords(DatabaseMetaData dmd, Table table, Expression expr, OrderByInput order_by_input) {
        return getRecords(dmd, table, expr, new GroupByInput(), new OrderByInput[]{order_by_input}, ColumnAlias.getColumnAlias(dmd));
    }

    public static Records getRecords(DatabaseMetaData dmd, Table table, Expression expr, OrderByInput order_by_input, ColumnAlias ca) {
        return getRecords(dmd, table, expr, new GroupByInput(), new OrderByInput[]{order_by_input}, ca);
    }

    public static Records getRecords(DatabaseMetaData dmd, Table table, Expression expr, OrderByInput[] order_by_inputs, ColumnAlias ca) {
        return getRecords(dmd, table, expr, new GroupByInput(), order_by_inputs, ca);
    }

    public static Records getRecords(
            DatabaseMetaData dmd, Table table, Expression expr,
            GroupByInput group_by_input, OrderByInput order_by_input, ColumnAlias ca) {
        return getRecords(dmd, table, expr, group_by_input, new OrderByInput[]{order_by_input}, ca);
    }

    public static Records getRecords(
            DatabaseMetaData dmd, Table table, Expression expr,
            GroupByInput group_by_input, OrderByInput[] order_by_inputs, ColumnAlias ca) {
        OrderBy order_by = OrderBy.instance(dmd, order_by_inputs);
        GroupBy group_by = GroupBy.instance(dmd, group_by_input);
        SelectProvider sp = getSelectProvider(dmd, table, expr, group_by, order_by);
        Records records = getRecords(dmd, sp.getMaxSelect().getQuery());
        records.setColumnAlias(ca);
        records.setDispIndeces(sp.getMinSelect().getColumnTypes());
        return records;
    }

    public static Records getRecords(DatabaseMetaData dmd, Select select, ColumnAlias ca) {
        Records records = getRecords(dmd, select.getQuery());
        records.setColumnAlias(ca);
        return records;
    }

    public static Records getRecords(DatabaseMetaData dmd, String query) {
        Records records;
        try (Statement stmt = dmd.getConnection().createStatement()) {
            //Terminal.println(query);
            stmt.execute(query);
            records = new Records(stmt.getResultSet());
        } catch (SQLException e) {

            throw new CoreException(e.getMessage() + ":" + query, e);
        }
        return records;
    }

    public static SelectProvider getSelectProvider(DatabaseMetaData dmd, Table table, Expression expr, GroupBy group_by, OrderBy order_by) {
        Select select = new Select();
        ArrayList<ColumnType> column_types = getColumnTypes(dmd, table);
        for (int i = 0; i < column_types.size(); i++) {
            select.addSelectItem(new SelectColumn(column_types.get(i)));
        }
        return getSelectProvider(dmd, select, expr, group_by, order_by);
    }

    public static SelectProvider getSelectProvider(DatabaseMetaData dmd, Select select, Expression expr, GroupBy group_by, OrderBy order_by) {
        SelectProvider sp = SelectProvider.instance(dmd, select);
        sp.setExpression(expr);
        sp.setGroupBy(group_by);
        sp.setOrderBy(order_by);

        return sp;
    }

    public static void insert(DatabaseMetaData dmd, String table_name, ColumnValues default_values) {
        insert(dmd, getTable(dmd, table_name), default_values);
    }

    public static void insert(DatabaseMetaData dmd, Table table, ColumnValues default_values) {
        Insert insert = new Insert(table, default_values);
        try {
            dmd.getConnection().createStatement().execute(insert.getQuery());
        } catch (SQLException e) {
            throw new CoreException(insert.getQuery(), e);
        }
    }

    public static void update(DatabaseMetaData dmd, String table_name, ColumnValues default_values, Record record) {
        update(dmd, getTable(dmd, table_name), default_values, getPrimaryKeyExpression(dmd, getTable(dmd, table_name), record));
    }

    public static void update(DatabaseMetaData dmd, String table_name, ColumnValues default_values, Expression expr) {
        update(dmd, getTable(dmd, table_name), default_values, expr);
    }

    public static void update(DatabaseMetaData dmd, Table table, ColumnValues default_values, Expression expr) {
        Update update = new Update(table, default_values, expr);
        try {
            dmd.getConnection().createStatement().execute(update.getQuery());
        } catch (SQLException e) {
            throw new CoreException(update.getQuery(), e);
        }
    }

    public static void delete(DatabaseMetaData dmd, String table_name, Record record) {
        Table table = getTable(dmd, table_name);
        delete(dmd, table, getPrimaryKeyExpression(dmd, table, record));
    }

    public static void delete(DatabaseMetaData dmd, String table_name, Expression expr) {
        delete(dmd, getTable(dmd, table_name), expr);
    }

    public static void delete(DatabaseMetaData dmd, Table table, Expression expr) {
        Delete delete = new Delete(table, expr);
        try {
            dmd.getConnection().createStatement().execute(delete.getQuery());
        } catch (SQLException e) {
            throw new CoreException(delete.getQuery(), e);
        }
    }

    public static Table getTable(DatabaseMetaData dmd, String table_name) {
        Records records;
        try {
            records = new Records(dmd.getTables(null, null, table_name, null));
        } catch (SQLException e) {
            throw new CoreException(e);
        }

        if (records.isEmpty()) {
            throw new CoreException("could not find table(" + table_name + ")");
        }
        if (records.isMulti()) {
            throw new CoreException("複数のテーブル(" + table_name + ")が見つかりました。");
        }
        return new Table(
                records.getValueString(0, SQLUtil.getWildColumnType("TABLE_CAT")),
                records.getValueString(0, SQLUtil.getWildColumnType("TABLE_SCHEM")),
                records.getValueString(0, SQLUtil.getWildColumnType("TABLE_NAME")));
    }

    public static Select getSelect(DatabaseMetaData dmd, SelectItem[] select_items,
            Expression[] and_exprs, GroupByInput group_by_input) {
        return getSelect(dmd, select_items, and_exprs, group_by_input, (OrderBy) null);
    }

    public static Select getSelect(DatabaseMetaData dmd, SelectItem[] select_items,
            Expression[] and_exprs, GroupByInput group_by_input, OrderByInput order_by_input) {
        return getSelect(dmd, select_items, and_exprs, group_by_input, OrderBy.instance(dmd, order_by_input));
    }

    public static Select getSelect(DatabaseMetaData dmd, SelectItem[] select_items,
            Expression[] and_exprs, GroupByInput group_by_input, OrderBy order_by) {
        return new Select(select_items,
                Expression.getMultiAnd(and_exprs),
                GroupBy.instance(dmd, group_by_input), order_by);
    }

    public static Select getSelect(DatabaseMetaData dmd, UpdateQuery uq) {
        Select select = new Select();
        ArrayList<ColumnType> column_types = getColumnTypes(dmd, uq.getTable());
        for (int i = 0; i < column_types.size(); i++) {
            select.addSelectItem(new SelectColumn(column_types.get(i)));
        }
        Expression expr = null;
        if (uq instanceof Insert) {
            for (int i = 0; i < uq.getColumnValueLength(); i++) {
                ColumnValue cv = uq.getColumnValue(i);
                if (cv.isFunction()) {
                    continue;
                }
                Expression this_expr
                        = new Value(new SelectColumn(cv.getColumnType()), cv.getValueExpression(), Expression.EQ);
                expr = expr == null ? this_expr : new And(expr, this_expr);
            }
        } else if (uq instanceof Update) {
            expr = uq.getExpression();
        } else {
            throw new CoreException("Insert、Update以外のUpdateQueryからはSelect文は作成できません。");
        }
        select.setExpression(expr);
        return select;
    }

    public static Record getRecord(DatabaseMetaData dmd, String table_name, Record source) {
        if (source == null) {
            throw new CoreException(table_name + "テーブルのデータがありません。");
        }
        Table table = getTable(dmd, table_name);
        PrimaryKeyInfo pk_info = PrimaryKeyInfo.instance(dmd, table);
        ColumnType[] column_types = getColumnTypes(dmd, pk_info.getColumns()).toArray(new ColumnType[0]);
        if (!source.hasAllColumnTypes(column_types)) {
            throw new CoreException(table_name + "テーブルの主キーデータが不足しています。");
        }
        Record pk_record = source.getSubRecord(column_types);
        Records records
                = SQLUtil.getRecords(dmd, table, getExpression(pk_record, TypeInfoHolder.instance(dmd)));
        if (records.isEmpty()) {
            throw new CoreException("現在設定されているレコードが見つかりません。");
        } else if (records.isMulti()) {
            throw new CoreException("[警告]現在設定されているレコードを特定できません。");
        }
        return records.getRecord(0);
    }

    public static Expression getPrimaryKeyExpression(DatabaseMetaData dmd, Table table, Record record) {
        ColumnType[] pk_columns = SQLUtil.getColumnTypes(dmd, PrimaryKeyInfo.instance(dmd, table).getColumns()).toArray(new ColumnType[0]);
        if (!record.hasAllColumnTypes(pk_columns)) {
            throw new CoreException("レコードに" + table.getSchemaTableName() + "の主キーのデータが含まれていません。");
        }
        return SQLUtil.getExpression(record.getSubRecord(pk_columns), TypeInfoHolder.instance(dmd));
    }

    public static Expression getExpression(Records pk_infos, TypeInfoHolder ti) {
        Expression expr = null;
        for (int i = 0; i < pk_infos.length(); i++) {
            Expression temp_expr = getExpression(pk_infos.getRecord(i), ti);
            expr = expr == null ? temp_expr : new Or(expr, temp_expr);
        }
        return expr;
    }

    public static Expression getExpression(Record pk_info, TypeInfoHolder ti) {
        Expression expr = null;
        for (int i = 0; i < pk_info.columnsLength(); i++) {
            ColumnMetaData cmd = pk_info.getColumnMetaData(i);
            TypeInfo type_info = ti.getTypeInfo(cmd.getColumnType().getTypeName());
            //String value_expr = type_info.getValueExpression(pk_info.getValueString(i));
            String value_expr = type_info.getValueExpression(pk_info.getValue(i).toString());
            Expression temp_expr = new Value(new SelectColumn(cmd.getColumnType()), value_expr, Expression.EQ);
            expr = expr == null ? temp_expr : new And(expr, temp_expr);
        }
        return expr;
    }

    public static boolean isKeywordSearchable(ArrayList<ColumnType> column_types, TypeInfoHolder ti) {
        for (int i = 0; i < column_types.size(); i++) {
            ColumnType column_type = column_types.get(i);
            TypeInfo type_info = ti.getTypeInfo(column_type.getTypeName());
            int seachable = type_info.getSeachable();
            if (seachable == TypeInfoHolder.SEARCH_BOTH || seachable == TypeInfoHolder.SEARCH_CHAR) {
                return true;
            }
        }
        return false;

    }

    public static String getValueExpression(DatabaseMetaData dmd, String dictionary_table_column_name, Object value) {
        ColumnType column_type = getColumnType(dmd, dictionary_table_column_name);
        return TypeInfoHolder.get(dmd, column_type).getValueExpression(value.toString());
    }

    public static String getValueExpression(DatabaseMetaData dmd, Record record, String dictionary_table_column_name) {
        ColumnType column_type = getColumnType(dmd, dictionary_table_column_name);
        Object value = record.getValue(column_type);
        if (value == null) {
            return Query.NULL;
        }
        return TypeInfoHolder.get(dmd, column_type).getValueExpression(value.toString());
    }

    public static String getCsvString(String[] strs) {
        String str = "";
        for (int i = 0; i < strs.length; i++) {
            if (i != 0) {
                str += ", ";
            }
            str += strs[i];
        }
        return str;
    }

    public static boolean isManagable(DatabaseMetaData dmd, Schema schema) {
        String schema_name = schema.getSchemaName();
        try {
            return schema_name != null && dmd.getUserName().equalsIgnoreCase(schema_name);
        } catch (SQLException e) {
            throw new CoreException(e);
        }
    }

    /*public static Code getColumnTypeCodeOmitColumn(DatabaseMetaData dmd, Table table, String[] omit_columns) {
        try {
            Records records = new Records(dmd.getColumns(table.getCatalogName(), table.getSchemaName(), table.getTableName(), null));
            for (int i = 0; i < omit_columns.length; i++) {
                int[] indeces = records.searchRecordIndeces(getWildColumnType("COLUMN_NAME"), omit_columns[i]);
                if (indeces.length == 1) {
                    records.remove(indeces[0]);
                }
            }
            return new Code("カラム一覧", getColumnTypeOptions(records));
        } catch (SQLException e) {
            throw new CoreException(e);
        }
    }*/
    public static Code getColumnTypeCode(DatabaseMetaData dmd, Table table, String[] columns) {
        try {
            Records records = new Records(dmd.getColumns(table.getCatalogName(), table.getSchemaName(), table.getTableName(), null));
            ArrayList<Record> vec = new ArrayList<>();
            for (int i = 0; i < columns.length; i++) {
                int[] indeces = records.searchRecordIndeces(getWildColumnType("COLUMN_NAME"), columns[i]);
                if (indeces.length == 1) {
                    vec.add(records.getRecord(indeces[0]));
                }
            }
            return new Code("カラム一覧", getColumnTypeOptions(new Records(vec)));
        } catch (SQLException e) {
            throw new CoreException(e);
        }
    }

    public static Code getColumnTypeCode(DatabaseMetaData dmd, Table table) {
        try {
            Records records = new Records(dmd.getColumns(table.getCatalogName(), table.getSchemaName(), table.getTableName(), null));
            return new Code("カラム一覧", getColumnTypeOptions(records));
        } catch (SQLException e) {
            throw new CoreException(e);
        }
    }

    public static Code getColumnTypeCode(DatabaseMetaData dmd, Table[] tables) {
        ArrayList<Option> options = new ArrayList<>();
        for (int i = 0; i < tables.length; i++) {
            try {
                Records records = new Records(dmd.getColumns(tables[i].getCatalogName(), tables[i].getSchemaName(), tables[i].getTableName(), null));
                options.addAll(getColumnTypeOptions(records));
            } catch (SQLException e) {
                throw new CoreException(e);
            }
        }
        return new Code("カラム一覧", options);
    }

    public static Option getColumnTypeOption(String name, String type) {
        return new Option(name, name, new ColumnType(null, null, null, name, type));
    }

    private static ArrayList<Option> getColumnTypeOptions(Records records) {
        ArrayList<Option> options = new ArrayList<Option>();
        for (int i = 0; i < records.length(); i++) {
            options.add(new Option(
                    records.getValueString(i, getWildColumnType("COLUMN_NAME")),
                    records.getValueString(i, getWildColumnType("COLUMN_NAME")), getColumnType(records, i)));
        }
        return options;
        //return records.getOptions("TYPE_NAME", "COLUMN_NAME", "COLUMN_NAME");
    }

    public static ArrayList<ColumnType> getColumnTypes(DatabaseMetaData dmd, Table[] tables) {
        ArrayList<ColumnType> column_types = new ArrayList<>();
        for (int i = 0; i < tables.length; i++) {
            column_types.addAll(getColumnTypes(dmd, tables[i]));
        }
        return column_types;
    }

    public static ArrayList<ColumnType> getColumnTypes(DatabaseMetaData dmd, Table table) {
        Records records;
        try {
            records = new Records(dmd.getColumns(table.getCatalogName(), table.getSchemaName(), table.getTableName(), null));
        } catch (SQLException e) {
            throw new CoreException(e);
        }
        ArrayList<ColumnType> column_types = new ArrayList<>();
        for (int i = 0; i < records.length(); i++) {
            column_types.add(getColumnType(records, i));
        }
        return column_types;
    }

    private static ColumnType getColumnType(Records records, int index) {
        return new ColumnType(
                records.getValueString(index, getWildColumnType("TABLE_CAT")),
                records.getValueString(index, getWildColumnType("TABLE_SCHEM")),
                records.getValueString(index, getWildColumnType("TABLE_NAME")),
                records.getValueString(index, getWildColumnType("COLUMN_NAME")),
                records.getValueString(index, getWildColumnType("TYPE_NAME")));
    }

    public static ArrayList<ColumnType> getColumnTypes(DatabaseMetaData dmd, Column[] columns) {
        ArrayList<ColumnType> column_types = new ArrayList<>();
        for (int i = 0; i < columns.length; i++) {
            column_types.add(getColumnType(dmd, columns[i]));
        }
        return column_types;
    }

    public static boolean equals(DatabaseMetaData dmd, Record record, String dict_name, Object value) {
        return getValue(dmd, record, dict_name).equals(value);
    }

    public static String getValueString(DatabaseMetaData dmd, Record record, String dict_name) {
        return record.getValueString(getColumnType(dmd, dict_name));
    }

    public static Object getValue(DatabaseMetaData dmd, Record record, String dict_name) {
        return record.getValue(getColumnType(dmd, dict_name));

    }

    public static Expression createEqualValue(
            DatabaseMetaData dmd, String dict_name, Record record) {
        return createEqualValue(dmd, dict_name, record, dict_name);
    }

    public static Expression createEqualValue(
            DatabaseMetaData dmd, String target_dict_name,
            Record source_record, String source_dict_name) {
        return createEqualValue(dmd, target_dict_name, getValue(dmd, source_record, source_dict_name));
    }

    public static Expression createEqualValue(DatabaseMetaData dmd, String dict_name, Object o) {
        if (o == null) {
            return createIsNull(dmd, dict_name);
        }
        return createValue(dmd, dict_name, o, Expression.EQ);
    }

    public static Exists createExists(DatabaseMetaData dmd, String table_name, Expression expr, boolean is_not) {
        Column[] columns = PrimaryKeyInfo.instance(dmd, getTable(dmd, table_name)).getColumns();
        return new Exists(new SelectPhrase(new Select(getSelectColumns(dmd, columns), expr)), is_not);
    }

    public static In createIn(DatabaseMetaData dmd, String dict_name, Select select) {
        return new In(getSelectColumn(dmd, dict_name), new SelectPhrase(select));
    }

    public static In createNotIn(DatabaseMetaData dmd, String dict_name, Select select) {
        return new In(getSelectColumn(dmd, dict_name), new SelectPhrase(select), true);
    }

    public static Items createEqualItems(DatabaseMetaData dmd, String dict_name_1, String dict_name_2) {
        return new Items(getSelectColumn(dmd, dict_name_1), getSelectColumn(dmd, dict_name_2), Expression.EQ);
    }

    public static Items createEqualItems(DatabaseMetaData dmd, String dict_name, Select select) {
        return new Items(getSelectColumn(dmd, dict_name), new SelectPhrase(select), Expression.EQ);
    }

    public static Value createValue(
            DatabaseMetaData dmd, String target_dict_name,
            Record source_record, String source_dict_name, short equation) {
        return createValue(dmd, target_dict_name, getValue(dmd, source_record, source_dict_name), equation);
    }

    public static Value createValue(DatabaseMetaData dmd, String dict_name, Object o, short equation) {
        return createValue(dmd, getSelectColumn(dmd, dict_name), o, equation);
    }

    public static Value createValue(DatabaseMetaData dmd, SelectItem select_item, Object o, short equation) {
        String value_expression
                = TypeInfoHolder.get(dmd, select_item.getTypeName()).getValueExpression(o.toString());
        return new Value(select_item, value_expression, equation);
    }

    public static IsNull createIsNotNull(DatabaseMetaData dmd, String dict_name) {
        return new IsNull(getSelectColumn(dmd, dict_name), true);
    }

    public static IsNull createIsNull(DatabaseMetaData dmd, String dict_name) {
        return new IsNull(getSelectColumn(dmd, dict_name));
    }

    public static SelectColumn[] getSelectColumns(DatabaseMetaData dmd, Column[] columns) {
        SelectColumn[] scs = new SelectColumn[columns.length];
        for (int i = 0; i < columns.length; i++) {
            scs[i] = getSelectColumn(dmd, columns[i]);
        }
        return scs;
    }

    public static SelectColumn[] getSelectColumns(DatabaseMetaData dmd, String table_name) {
        ArrayList<ColumnType> column_types = getColumnTypes(dmd, getTable(dmd, table_name));
        SelectColumn[] sc = new SelectColumn[column_types.size()];
        for (int i = 0; i < column_types.size(); i++) {
            sc[i] = new SelectColumn(column_types.get(i));
        }
        return sc;
    }

    public static SelectColumn getSelectColumn(DatabaseMetaData dmd, Column column, TableExpression table_expr) {
        return new SelectColumn(getColumnType(dmd, column), table_expr);
    }

    //public static SelectColumn getSelectColumn(DatabaseMetaData dmd, Table table, String column_name){
    //    return new SelectColumn(getColumnType(dmd, table.getColumn(column_name)));
    //}
    public static SelectColumn getSelectColumn(DatabaseMetaData dmd, Column column) {
        return new SelectColumn(getColumnType(dmd, column));
    }

    public static SelectColumn getSelectColumn(DatabaseMetaData dmd, String dictionary_table_column) {
        return new SelectColumn(getColumnType(dmd, dictionary_table_column));
    }

    public static SelectColumn getSelectColumn(DatabaseMetaData dmd, String dictionary_table_column, String correlation_name) {
        return new SelectColumn(getColumnType(dmd, dictionary_table_column), correlation_name);
    }

    public static ColumnType[] getColumnTypes(DatabaseMetaData dmd, String[] dictionary_table_column_names) {
        ColumnType[] column_types = new ColumnType[dictionary_table_column_names.length];
        for (int i = 0; i < dictionary_table_column_names.length; i++) {
            column_types[i] = getColumnType(dmd, dictionary_table_column_names[i]);
        }
        return column_types;
    }

    public static ColumnType getColumnType(DatabaseMetaData dmd, String dictionary_table_column_name) {
        String[] names = TextUtil.separate(dictionary_table_column_name, ".");
        if (names.length != 2) {
            throw new CoreException(dictionary_table_column_name + "はカラムを表すディクショナリ名ではありません。");
        }
        return getColumnType(dmd, names[0], names[1]);
    }

    public static ColumnType getColumnType(DatabaseMetaData dmd, String table_name, String column_name) {
        return getColumnType(dmd, new Column(getTable(dmd, table_name), column_name));
    }

    public static ColumnType getColumnType(DatabaseMetaData dmd, Table table, String column_name) {
        return getColumnType(dmd, new Column(table, column_name));
    }

    public static ColumnType getColumnType(DatabaseMetaData dmd, Column column) {
        try {
            Records records = new Records(dmd.getColumns(column.getCatalogName(), column.getSchemaName(), column.getTableName(), column.getColumnName()));
            String expl = column.getExplanation();
            if (records.isEmpty()) {
                throw new CoreException("カラムが見つかりません。(" + expl + ")");
            }
            if (records.isMulti()) {
                throw new CoreException("複数のカラムがみつかりました。(" + expl + ")");
            }
            return new ColumnType(column, records.getValueString(0, getWildColumnType("TYPE_NAME")));
        } catch (SQLException ex) {
            throw new CoreException(ex);
        }

    }

    public static ColumnsMetaDataHolder getColumnsMetaDataHolder(DatabaseMetaData dmd, Table table) {
        return getRecords(dmd, "SELECT * FROM " + table.getSchemaTableName() + " WHERE 1 = 0");
    }

    public static ColumnsMetaDataHolder getProvidedColumnsMetaDataHolder(DatabaseMetaData dmd, Table table) {
        return getRecords(dmd, getSelectProvider(dmd, table, new NoSelect(), null, null).getMaxSelect().getQuery());
    }

    public static int getDataCount(DatabaseMetaData dmd, Table table) {
        return getDataCount(dmd, table, null);
    }

    public static int getDataCount(DatabaseMetaData dmd, String table_name, Expression expr) {
        return getDataCount(dmd, getTable(dmd, table_name), expr);
    }

    public static int getDataCount(DatabaseMetaData dmd, Table table, Expression expr) {
        return getRecords(dmd, "SELECT COUNT(*) FROM " + table.getSchemaTableName() + ExpressionQuery.getWhere(expr)).getRecord(0).getValueInt(0);
    }

    public static int getDataCount(DatabaseMetaData dmd, Select select) {
        return getRecords(dmd, "SELECT COUNT(*) " + select.getFromWherePhrase()).getRecord(0).getValueInt(0);
    }

    public static ColumnType[] getWildColumnTypes(String[] column_labels) {
        ColumnType[] column_types = new ColumnType[column_labels.length];
        for (int i = 0; i < column_labels.length; i++) {
            column_types[i] = getWildColumnType(column_labels[i]);
        }
        return column_types;
    }

    public static ColumnType getWildColumnType(String column_label) {
        return new ColumnType(null, null, null, column_label, null);
    }
    public static Code IMPORTED_KEY = new Code(
            "外部キー種別",
            new Option[]{
                new Option(Integer.toString(DatabaseMetaData.importedKeyCascade),
                        "CASCADE", DatabaseMetaData.importedKeyCascade),
                new Option(Integer.toString(DatabaseMetaData.importedKeyRestrict),
                        "RESTRICT", DatabaseMetaData.importedKeyRestrict),
                new Option(Integer.toString(DatabaseMetaData.importedKeySetNull),
                        "SET NULL", DatabaseMetaData.importedKeySetNull),
                new Option(Integer.toString(DatabaseMetaData.importedKeyNoAction),
                        "NO ACTION", DatabaseMetaData.importedKeyNoAction),
                new Option(Integer.toString(DatabaseMetaData.importedKeySetDefault),
                        "SET DEFAULT", DatabaseMetaData.importedKeySetDefault)
            });
}
