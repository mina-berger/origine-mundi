/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mina.sql;

import com.mina.util.CoreException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;

/**
 *
 * @author mina
 */
public class ColumnMetaData {

    private ColumnType column_type;
    private String class_name;
    private String column_label;
    private int disp_size;
    private int int_column_type;
    private int precision;
    private int scale;
    private int is_nullable;
    private boolean is_searchable;
    private boolean is_auto_increment;
    private int index;

    public ColumnMetaData(ResultSetMetaData rsmd, int index) {
        try {
            column_type = new ColumnType(
                    rsmd.getCatalogName(index),
                    rsmd.getSchemaName(index),
                    rsmd.getTableName(index),
                    rsmd.getColumnName(index),
                    rsmd.getColumnTypeName(index));
            class_name = rsmd.getColumnClassName(index);
            column_label = rsmd.getColumnLabel(index);
            int_column_type = rsmd.getColumnType(index);
            //column_type_name  = rsmd.getColumnTypeName(index);
            precision = rsmd.getPrecision(index);
            scale = rsmd.getScale(index);
            disp_size = rsmd.getColumnDisplaySize(index);
            is_nullable = rsmd.isNullable(index);
            is_searchable = rsmd.isSearchable(index);
            is_auto_increment = rsmd.isAutoIncrement(index);
            this.index = index;
            //rsmd.
        } catch (SQLException e) {
            throw new CoreException(e);
        }
    }

    @Override
    public String toString() {
        return column_type.toString() + ":class_name=" + class_name
                + ":column_label=" + column_label
                + ":disp_size=" + disp_size
                + ":int_column_type=" + int_column_type
                + ":precision=" + precision
                + ":scale=" + scale
                + ":is_nullable=" + is_nullable
                + ":is_searchable=" + is_searchable
                + ":is_auto_increment=" + is_auto_increment
                + ":index=" + index;
    }

    public ColumnType getColumnType() {
        return column_type;
    }

    public String getClassName() {
        return class_name;
    }

    public String getColumnLabel() {
        return column_label;
    }

    public int getColumnDispSize() {
        return disp_size;
    }

    public int getIntColumnType() {
        return int_column_type;
    }

    public int getPrecision() {
        return precision;
    }

    public int getScale() {
        return scale;
    }

    public int isNullable() {
        return is_nullable;
    }

    public boolean isSearchable() {
        return is_searchable;
    }

    public boolean isAutoIncrement() {
        return is_auto_increment;
    }

    public boolean isNoNulls() {
        return is_nullable == ResultSetMetaData.columnNoNulls;
    }

    public String getExplanation() {
        String expl = column_type.getTypeName();
        if (precision != 0 && scale == 0) {
            expl += "(" + precision + ")";
        }
        if (precision != 0 && scale != 0) {
            expl += "(" + precision + "," + scale + ")";
        }
        return expl;
    }

    public Object getValue(ResultSet rs) {
        return getValue(rs, index, int_column_type);
    }

    public static Object getValue(ResultSet rs, int index, int type) {
        try {
            switch (type) {
                case Types.ARRAY:
                    return rs.getArray(index);
                //case Types.BIGINT:return rs.
                //case Types.BINARY:return rs.
                case Types.BIT:
                    return rs.getBoolean(index);
                //case Types.BLOB
                case Types.BOOLEAN:
                    return rs.getBoolean(index);
                case Types.CHAR:
                    return rs.getString(index);
                //case Types.CLOB
                //case Types.DATALINK
                case Types.DATE:
                    return rs.getDate(index);
                case Types.DECIMAL:
                case Types.NUMERIC:
                    return rs.getBigDecimal(index);
                //return rs.getDouble(index);
                //case TYpes.DISTINCT
                case Types.DOUBLE:
                    return rs.getDouble(index);
                case Types.FLOAT:
                    return rs.getFloat(index);
                case Types.INTEGER:
                    return rs.getInt(index);
                case Types.JAVA_OBJECT:
                    return rs.getObject(index);
                //case Types.LONGNVARCHAR
                //LONGVARBINARY
                case Types.LONGVARCHAR:
                    return rs.getString(index);
                //NCHAR
                //NCLOB
                case Types.NULL:
                    return null;
                //case Types.NVARCHAR
                //OTHER
                //REAL
                //REF
                //ROWID
                case Types.SMALLINT:
                    return rs.getInt(index);
                //SQLXML
                //STRUCT
                case Types.TIME:
                    return rs.getTime(index);
                case Types.TIMESTAMP:
                    return rs.getTimestamp(index);
                //TINYINT
                //VARBINARY
                case Types.VARCHAR:
                    return rs.getString(index);
                case Types.VARBINARY:
                    return rs.getBytes(index);
                default:
                    throw new CoreException("unknown data_type(" + type + ") for column index = " + index);
            }
        } catch (SQLException e) {
            throw new CoreException(e);
        }
    }

    public boolean getAlignLeft() {
        switch (int_column_type) {
            case Types.ARRAY:
            case Types.BINARY:
            case Types.BIT:
            case Types.BLOB:
            case Types.BOOLEAN:
            case Types.CHAR:
            case Types.CLOB:
            case Types.DATALINK:
            case Types.DATE:
            case Types.DISTINCT:
            case Types.JAVA_OBJECT:
            case Types.LONGNVARCHAR:
            case Types.LONGVARBINARY:
            case Types.LONGVARCHAR:
            case Types.NCHAR:
            case Types.NCLOB:
            case Types.NULL:
            case Types.NVARCHAR:
            case Types.OTHER:
            case Types.REF:
            case Types.SQLXML:
            case Types.STRUCT:
            case Types.TIME:
            case Types.TIMESTAMP:
            case Types.VARBINARY:
            case Types.VARCHAR:
                return true;
            case Types.BIGINT:
            case Types.DECIMAL:
            case Types.DOUBLE:
            case Types.FLOAT:
            case Types.INTEGER:
            case Types.NUMERIC:
            case Types.REAL:
            case Types.ROWID:
            case Types.SMALLINT:
            case Types.TINYINT:
                return false;
            default:
                throw new CoreException("unknown data_type(" + column_type + ") for column " + column_label);
        }
    }

    @Override
    public boolean equals(Object o) {
        return compareTo(o) == 0;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (this.column_type != null ? this.column_type.hashCode() : 0);
        hash = 89 * hash + (this.class_name != null ? this.class_name.hashCode() : 0);
        hash = 89 * hash + (this.column_label != null ? this.column_label.hashCode() : 0);
        hash = 89 * hash + this.disp_size;
        hash = 89 * hash + this.int_column_type;
        hash = 89 * hash + this.precision;
        hash = 89 * hash + this.scale;
        hash = 89 * hash + this.is_nullable;
        hash = 89 * hash + (this.is_searchable ? 1 : 0);
        hash = 89 * hash + (this.is_auto_increment ? 1 : 0);
        return hash;
    }

    public int compareTo(Object o) {
        return new Integer(hashCode()).compareTo(new Integer(o.hashCode()));
    }
}
