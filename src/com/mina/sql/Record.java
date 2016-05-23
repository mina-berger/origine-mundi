/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mina.sql;

import com.mina.util.CoreException;
import com.mina.util.StringSquare;
import com.mina.util.TextBox;
import com.mina.util.TextUtil;
import com.mina.util.Yyyymmdd;
import java.io.PrintStream;
import java.sql.Date;
import java.util.Arrays;

/**
 *
 * @author mina
 */
@SuppressWarnings("rawtypes")
public class Record extends ColumnsMetaDataHolder implements Comparable {

    protected Object[] values;

    public Record(ColumnMetaData[] cmd, int[] disp_indeces, Object[] values, ColumnAlias ca) {
        super(cmd, disp_indeces, ca);
        initio(values);
    }

    private void initio(Object[] values) {
        if (columnsLength() != values.length) {
            throw new CoreException("Record:different length between columns and values.");
        }
        this.values = values;
    }

    public boolean hasValue(ColumnType column_type) {
        return hasValue(getIndex(column_type));
    }

    public boolean hasValue(int column_index) {
        return values[column_index] != null;
    }

    public Object getValue(ColumnType column_type) {
        return getValue(getIndex(column_type));
    }

    public Object getValue(int column_index) {
        return values[column_index];
    }

    public String getValueString(ColumnType column_type) {
        return getValueString(getIndex(column_type));
    }

    public String getValueString(int column_index) {
        if (hasValue(column_index)) {
            return TextUtil.parseString(values[column_index]);
        }
        return null;
    }

    public int getValueInt(ColumnType column_type) {
        return getValueInt(getIndex(column_type));
    }

    public int getValueInt(int column_index) {
        if (hasValue(column_index)) {
            if (!(values[column_index] instanceof Number)) {
                throw new CoreException("not a number");
            }
            return ((Number) values[column_index]).intValue();
        }
        throw new CoreException("null can not be a number");
    }
    public double getValueDouble(int column_index) {
        if (hasValue(column_index)) {
            if (!(values[column_index] instanceof Number)) {
                throw new CoreException("not a number");
            }
            return ((Number) values[column_index]).doubleValue();
        }
        throw new CoreException("null can not be a number");
    }

    public boolean getValueBoolean(ColumnType column_type) {
        return getValueBoolean(getIndex(column_type));
    }

    public boolean getValueBoolean(int column_index) {
        if (hasValue(column_index)) {
            if (!(values[column_index] instanceof Boolean)) {
                throw new CoreException("not a boolean");
            }
            return (Boolean) values[column_index];
        }
        throw new CoreException("null can not be a boolean");
    }

    public Yyyymmdd getValueYyyymmdd(ColumnType column_type) {
        return getValueYyyymmdd(getIndex(column_type));
    }

    public Yyyymmdd getValueYyyymmdd(int column_index) {
        if (hasValue(column_index)) {
            if (!(values[column_index] instanceof Date)) {
                throw new CoreException("not a date");
            }
            return new Yyyymmdd((Date) values[column_index]);
        }
        throw new CoreException("null can not be date");
    }

    public Record getSubRecord(ColumnType[] column_types) {
        /*int[] indeces = new int[column_types.length];
        for(int i = 0;i < column_types.length;i++){
            indeces[i] = getIndex(column_types[i]);
        }*/
        return getSubRecord(getIndeces(column_types));
    }

    public Record getSubRecord(int[] column_indeces) {
        ColumnMetaData[] sub_cmd = new ColumnMetaData[column_indeces.length];
        Object[] sub_values = new Object[column_indeces.length];

        for (int i = 0; i < column_indeces.length; i++) {
            sub_cmd[i] = getColumnMetaData(column_indeces[i]);
            sub_values[i] = getValue(column_indeces[i]);
        }
        return new Record(sub_cmd, null, sub_values, ca);
    }

    public boolean hasSameData(Record record) {
        for (int i = 0; i < record.columnsLength(); i++) {
            ColumnType column_type = record.getColumnMetaData(i).getColumnType();
            if (!hasColumnType(column_type)) {
                return false;
            }
            Object this_value = getValue(column_type);
            Object that_value = record.getValue(column_type);
            if (this_value == null && that_value == null) {
                continue;
            }
            if (this_value != null && !this_value.equals(that_value)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        return compareTo(o) == 0;
    }

    @Override
    public int compareTo(Object o) {
        int ret = super.compareTo(o);
        return ret == 0 ? new Integer(hashCode()).compareTo(o.hashCode()) : ret;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + (this.values != null ? Arrays.hashCode(this.values) : 0);
        return hash;
    }

    public void print(PrintStream out) {
        Record record = (disp_indeces == null || disp_indeces.length == 0) ? this : getSubRecord(disp_indeces);
        new TextBox(new TextBoxDataRecord(record), false).print(out);
    }

    public class TextBoxDataRecord implements TextBox.Data {

        private final Record record;

        public TextBoxDataRecord(Record record) {
            this.record = record;
        }

        @Override
        public int deviderRowCount() {
            return 5;
        }

        //public boolean showRowNumber() {
        //    return false;
        //}
        @Override
        public boolean showHeader() {
            return false;
        }

        @Override
        public String getHeader(int h) {
            return null;
        }

        @Override
        public StringSquare getValue(int v, int h) {
            if (h == 0) {
                ColumnType column_type = record.getColumnMetaData(v).getColumnType();
                String label = record.useColmnAlias(column_type, column_type.getTableColumnName());
                return new StringSquare(label.toLowerCase());
            }
            String str = record.hasValue(v) ? record.getValueString(v) : "";
            return new StringSquare(str, record.getColumnMetaData(v).getAlignLeft());
        }

        @Override
        public int vLength() {
            return record.columnsLength();
        }

        @Override
        public int hLength() {
            return 2;
        }
    }
}
