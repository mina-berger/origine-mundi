/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mina.sql;

import com.mina.util.CoreException;
import com.mina.util.TextUtil;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author mina
 */
public class TypeInfoHolder extends Records {

    public static final int SEARCH_NONE = DatabaseMetaData.typePredNone;
    public static final int SEARCH_CHAR = DatabaseMetaData.typePredChar;
    public static final int SEARCH_BASIC = DatabaseMetaData.typePredBasic;
    public static final int SEARCH_BOTH = DatabaseMetaData.typeSearchable;

    private TypeInfoHolder(ResultSet rs) throws SQLException {
        super(rs);
    }

    public RecordCode getCode() {
        return new RecordCode("データタイプ", this, SQLUtil.getWildColumnTypes(new String[]{"DATA_TYPE", "TYPE_NAME", "DATA_TYPE"}));
    }

    public TypeInfo getTypeInfo(String type_name) {
        int[] indeces = searchRecordIndeces(SQLUtil.getWildColumnType("TYPE_NAME"), type_name);
        if (indeces.length == 0) {
            throw new CoreException("コード(" + type_name + ")に対応するレコードが存在しません。");
        }
        if (indeces.length > 1) {
            throw new CoreException("コード(" + type_name + ")に対応するレコードが複数存在します。");
        }
        return new TypeInfo(getRecord(indeces[0]));
    }

    public static TypeInfo get(DatabaseMetaData dmd, ColumnType column_type) {
        return get(dmd, column_type.getTypeName());
    }

    public static TypeInfo get(DatabaseMetaData dmd, String type_name) {
        return instance(dmd).getTypeInfo(type_name);
    }

    public static TypeInfoHolder instance(DatabaseMetaData dmd) {
        try {
            return new TypeInfoHolder(dmd.getTypeInfo());
        } catch (SQLException e) {
            throw new CoreException(e);
        }
    }

    public class TypeInfo extends Record {

        private boolean needs_precision;
        private boolean needs_scale;

        TypeInfo(Record record) {
            super(record.cmd, null, record.values, null);
            String[] create_params = TextUtil.separate(getValueString(SQLUtil.getWildColumnType("CREATE_PARAMS")), ",");
            needs_precision = false;
            needs_scale = false;
            for (int i = 0; i < create_params.length; i++) {
                if (create_params[i].equalsIgnoreCase("precision")
                        || create_params[i].equalsIgnoreCase("length")) {
                    needs_precision = true;
                }
                if (create_params[i].equalsIgnoreCase("scale")) {
                    needs_scale = true;
                }
            }
        }

        public String getTypeName() {
            return getValueString(SQLUtil.getWildColumnType("TYPE_NAME"));
        }

        public boolean needsPrecision() {
            return needs_precision;
        }

        public boolean needsScale() {
            return needs_scale;
        }

        public int getPrecision() {
            return ((Integer) getValue(SQLUtil.getWildColumnType("PRECISION"))).intValue();
        }

        public int getMinimumScale() {
            return ((Integer) getValue(SQLUtil.getWildColumnType("MINIMUM_SCALE"))).intValue();
        }

        public int getMaximumScale() {
            return ((Integer) getValue(SQLUtil.getWildColumnType("MAXIMUM_SCALE"))).intValue();
        }

        public boolean getAutoIncrement() {
            return ((Boolean) getValue(SQLUtil.getWildColumnType("AUTO_INCREMENT"))).booleanValue();
        }

        public String getLiteralPrefix() {
            String type_name = getValueString(SQLUtil.getWildColumnType("TYPE_NAME"));
            if (type_name.equals("DATE")) {
                return "DATE('";
            } else if (type_name.equals("TIME")) {
                return "TIME('";
            } else if (type_name.equals("TIMESTAMP")) {
                return "TIMESTAMP('";
            }
            String str = getValueString(SQLUtil.getWildColumnType("LITERAL_PREFIX"));
            return (str == null) ? "" : str;
        }

        public String getLiteralSuffix() {
            String type_name = getValueString(SQLUtil.getWildColumnType("TYPE_NAME"));
            if (type_name.equals("DATE") || type_name.equals("TIME") || type_name.equals("TIMESTAMP")) {
                return "')";
            }
            String str = getValueString(SQLUtil.getWildColumnType("LITERAL_SUFFIX"));
            return (str == null) ? "" : str;
        }

        public String convertLiteralValue(String value) {
            String type_name = getValueString(SQLUtil.getWildColumnType("TYPE_NAME"));
            if (type_name.equals("DATE") && value.length() == 8) {
                return value.substring(0, 4) + "-" + value.substring(4, 6) + "-" + value.substring(6, 8);
            } else {
                if (type_name.equals("TIME") && value.length() == 6) {
                    return value.substring(0, 2) + ":" + value.substring(2, 4) + ":" + value.substring(4, 6);
                } else {
                    if (type_name.equals("TIMESTAMP") && value.length() == 14) {
                        return value.substring(0, 4) + "-" + value.substring(4, 6) + "-" + value.substring(6, 8) + " "
                                + value.substring(8, 10) + ":" + value.substring(10, 12) + ":" + value.substring(12, 14);
                    }
                }
            }
            return value;
        }

        public String getValueExpression(String value) {
            return TextUtil.convertNullToEmpty(getLiteralPrefix())
                    + convertLiteralValue(value)
                    + TextUtil.convertNullToEmpty(getLiteralSuffix());
        }

        public String getValueString(String value_expression) {
            String str = value_expression.substring(
                    getLiteralPrefix().length(),
                    value_expression.length() - getLiteralSuffix().length());
            return str;
        }

        public boolean checkScale(int scale) {
            return getMinimumScale() <= scale && getMaximumScale() >= scale;
        }

        public int getSeachable() {
            return getValueInt(SQLUtil.getWildColumnType("SEARCHABLE"));
        }
    }
}
