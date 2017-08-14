/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mina.sql;

import com.mina.util.Code;
import com.mina.util.Code.Option;
import com.mina.util.CoreException;
import com.mina.util.StringSquare;
import com.mina.util.TextBox;
import com.mina.util.TextUtil;
import com.mina.util.Yyyymmdd;
import java.io.PrintStream;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ArrayList;

/**
 *
 * @author mina
 */
public class Records extends ColumnsMetaDataHolder {

    protected ArrayList<Object[]> valuess;
    protected int[] disp_indeces_for_record = new int[0];

    public Records(ResultSet rs) {
        super();
        ResultSetMetaData rsmd;
        try {
            rsmd = rs.getMetaData();
            cmd = new ColumnMetaData[rsmd.getColumnCount()];
            for (int i = 0; i < rsmd.getColumnCount(); i++) {
                cmd[i] = new ColumnMetaData(rsmd, (i + 1));
            }
            ArrayList<Object[]> vec = new ArrayList<>();
            while (rs.next()) {
                Object[] values = new Object[cmd.length];
                for (int i = 0; i < cmd.length; i++) {
                    values[i] = cmd[i].getValue(rs);
                }
                vec.add(values);
            }
            valuess = vec;
            rs.close();
        } catch (SQLException e) {
            throw new CoreException(e);
        }

    }

    protected Records() {
        super();
        valuess = new ArrayList<>();
    }

    protected Records(ColumnMetaData[] cmd, int[] disp_indeces, ArrayList<Object[]> valuess, ColumnAlias ca) {
        super(cmd, disp_indeces, ca);
        this.valuess = valuess;
    }

    public Records(ArrayList<Record> records) {
        this(records.toArray(new Record[]{}));
    }

    public Records(Record[] records) {
        this();
        if (records.length == 0) {
            throw new CoreException("no records");
        }
        cmd = null;
        valuess = new ArrayList<>();
        for (Record record : records) {
            if (cmd == null) {
                cmd = record.cmd;
            } else if (!Arrays.equals(cmd, record.cmd)) {
                throw new CoreException("order of columns is not identical");
            }
            valuess.add(record.values);
        }
    }

    public void setDispIndecesForRecord(int[] indeces) {
        if (indeces == null) {
            disp_indeces_for_record = new int[0];
            return;
        }
        for (int i = 0; i < indeces.length; i++) {
            if (indeces[i] >= cmd.length) {
                throw new CoreException("one of indeces is out of range");
            }
        }
        disp_indeces_for_record = indeces;
    }

    public int length() {
        return valuess.size();
    }

    public boolean isEmpty() {
        return valuess.isEmpty();
    }

    public boolean isSolo() {
        return valuess.size() == 1;
    }

    public boolean isMulti() {
        return valuess.size() > 1;
    }

    public int getSumInt(ColumnType column_type) {
        return getSumInt(getIndex(column_type));
    }

    public int getSumInt(int column_index) {
        int sum = 0;
        for (int i = 0; i < length(); i++) {
            sum += getValueInt(i, column_index);
        }
        return sum;
    }

    public Object[] getValues(ColumnType column_type) {
        return getValues(getIndex(column_type));
    }

    public Object[] getValues(int column_index) {
        Object[] values = new Object[length()];
        for (int i = 0; i < length(); i++) {
            values[i] = getValue(i, column_index);
        }
        return values;
    }

    public String[] getValueStrings(ColumnType column_type) {
        return getValueStrings(getIndex(column_type));
    }

    public String[] getValueStrings(int column_index) {
        String[] strings = new String[length()];
        for (int i = 0; i < length(); i++) {
            strings[i] = getValueString(i, column_index);
        }
        return strings;
    }

    public Yyyymmdd getValueYyyymmdd(int record_index, ColumnType column_type) {
        return getValueYyyymmdd(record_index, getIndex(column_type));
    }

    public Yyyymmdd getValueYyyymmdd(int record_index, int column_index) {
        if (hasValue(record_index, column_index)) {
            Object value = getValue(record_index, column_index);
            if (!(value instanceof Date)) {
                throw new CoreException("not a date");
            }
            return new Yyyymmdd((Date) value);
        }
        throw new CoreException("null can not be date");
    }

    public boolean getValueBoolean(int record_index, ColumnType column_type) {
        return getValueBoolean(record_index, getIndex(column_type));
    }

    public boolean getValueBoolean(int record_index, int column_index) {
        if (hasValue(record_index, column_index)) {
            Object value = getValue(record_index, column_index);
            if (!(value instanceof Boolean)) {
                throw new CoreException("not a boolean");
            }
            return ((Boolean) value);
        }
        throw new CoreException("null can not be boolean");
    }

    public int getValueInt(int record_index, ColumnType column_type) {
        return getValueInt(record_index, getIndex(column_type));
    }

    public int getValueInt(int record_index, int column_index) {
        if (hasValue(record_index, column_index)) {
            Object value = getValue(record_index, column_index);
            if (!(value instanceof Number)) {
                throw new CoreException("not a number");
            }
            return ((Number) value).intValue();
        }
        throw new CoreException("null can not be number");
    }

    public Object getValue(int record_index, ColumnType column_type) {
        return getValue(record_index, getIndex(column_type));
    }

    public Object getValue(int record_index, int column_index) {
        return valuess.get(record_index)[column_index];
    }

    public String getValueString(int record_index, ColumnType column_type) {
        return getValueString(record_index, getIndex(column_type));
    }

    public String getValueString(int record_index, int column_index) {
        if (hasValue(record_index, column_index)) {
            return TextUtil.parseString(getValue(record_index, column_index));
        }
        return null;
    }

    public boolean hasValue(int record_index, int column_index) {
        return valuess.get(record_index)[column_index] != null;
    }

    public Record getRecord(int record_index) {
        return new Record(cmd, disp_indeces_for_record, valuess.get(record_index), ca);
    }

    public Records getRecords(int index_ab, int index_ad) {
        Record[] records = new Record[index_ad - index_ab];
        for (int i = index_ab; i < index_ad; i++) {
            records[i - index_ab] = getRecord(i);
        }
        Records ret = new Records(records);
        ret.setColumnAlias(ca);
        return ret;
    }

    public Record getSoloRecord() {
        return getSoloRecord("multiple records", "no record");
    }

    public Record getSoloRecord(String multi_message, String empty_message) {
        if (isMulti()) {
            throw new CoreException(multi_message);
        } else if (isEmpty()) {
            throw new CoreException(empty_message);
        }
        return getRecord(0);

    }

    public int getIndex(Record record) {
        for (int i = 0; i < length(); i++) {
            if (getRecord(i).hasSameData(record)) {
                return i;
            }
        }
        return -1;
    }

    public boolean removeRecord(Record record) {
        //return valuess.remove(record);
        for (int i = 0; i < length(); i++) {
            if (getRecord(i).equals(record)) {
                remove(i);
                return true;
            }
        }
        return false;

    }

    public void remove(int record_index) {
        valuess.remove(record_index);
    }

    public void removeFirst() {
        remove(0);
    }

    public void removeLast() {
        remove(length() - 1);
    }

    public int[] searchRecordIndeces(ColumnType column_type, Object value) {
        return searchRecordIndeces(getIndex(column_type), value);
    }

    public int[] searchRecordIndeces(int column_index, Object value) {
        return searchRecordIndeces(new int[]{column_index}, new Object[]{value});
    }

    public int[] searchRecordIndeces(ColumnType[] column_types, Object[] values) {
        int[] column_indeces = new int[column_types.length];
        for (int i = 0; i < column_types.length; i++) {
            column_indeces[i] = getIndex(column_types[i]);
        }
        return searchRecordIndeces(column_indeces, values);
    }

    @SuppressWarnings("unused")
    public int[] searchRecordIndeces(int[] column_indeces, Object[] values) {
        if (column_indeces.length != values.length) {
            throw new CoreException("カラムインデックスと値の配列の長さが違います。");
        }
        ArrayList<Integer> vec_indeces = new ArrayList<>();
        //boolean match = true;
        loop_i:
        for (int i = 0; i < length(); i++) {
            loop_j:
            for (int j = 0; j < column_indeces.length; j++) {
                if (!getValue(i, column_indeces[j]).equals(values[j])) {
                    //match = false;
                    continue loop_i;
                }
            }
            vec_indeces.add(i);
        }
        int[] int_indeces = new int[vec_indeces.size()];
        for (int i = 0; i < vec_indeces.size(); i++) {
            int_indeces[i] = vec_indeces.get(i);
        }
        return int_indeces;
    }

    public Records getSubRecords(ArrayList<ColumnType> column_types) {
        return getSubRecords(column_types.toArray(new ColumnType[]{}));
    }

    public Records getSubRecords(ColumnType[] column_types) {
        return getSubRecords(getIndeces(column_types));
    }

    public Records getSubRecords(int[] column_indeces) {
        ColumnMetaData[] sub_cmd = new ColumnMetaData[column_indeces.length];
        Object[][] sub_valuess = new Object[length()][column_indeces.length];

        for (int i = 0; i < column_indeces.length; i++) {
            sub_cmd[i] = getColumnMetaData(column_indeces[i]);
            for (int j = 0; j < length(); j++) {
                sub_valuess[j][i] = valuess.get(j)[column_indeces[i]];
            }
        }
        ArrayList<Object[]> vec = new ArrayList<>();
        for (int i = 0; i < length(); i++) {
            vec.add(sub_valuess[i]);
        }
        return new Records(sub_cmd, null, vec, ca);
    }

    public Code getCode(String name, ColumnType code_type, ColumnType name_type, ColumnType value_type) {
        return new Code(name, getOptions(code_type, name_type, value_type));
    }

    public ArrayList<Option> getOptions(ColumnType code_type, ColumnType name_type, ColumnType value_type) {
        ArrayList<Option> options = new ArrayList<>();
        for (int i = 0; i < length(); i++) {
            options.add(new Option(
                    getValueString(i, code_type),
                    getValueString(i, name_type),
                    getValue(i, value_type)));
        }
        return options;
    }

    //public void print(){
    //    print(false, 1, 1);
    //}
    public void print(boolean show_row_number, PrintStream stream) {
        print(show_row_number, 1, 0, stream);
    }

    public void print(boolean show_row_number, int parallel_count, int start_index, PrintStream stream) {
        getTextBox(show_row_number, parallel_count, start_index).print(stream);
    }

    private TextBox getTextBox(boolean show_row_number, int parallel_count, int start_index) {
        return new TextBox(new TextBoxDataRecords(getDispSubRecords()), show_row_number, parallel_count, start_index);
    }

    public Records getDispSubRecords() {
        return hasDispIndeces() ? getSubRecords(getDispIndeces()) : this;
    }

    class TextBoxDataRecords implements TextBox.Data {

        Records records;

        //boolean show_row_number;
        TextBoxDataRecords(Records records) {
            this.records = records;
            //this.show_row_number = show_row_number;
        }

        @Override
        public int deviderRowCount() {
            return 5;
        }

        //public boolean showRowNumber() {return show_row_number;}
        @Override
        public boolean showHeader() {
            return true;
        }

        @Override
        public String getHeader(int h) {
            //return records.getColumnMetaData(h).getColumnType().getExplanation()/*.getColumnLabel()*/;
            ColumnMetaData cmd = records.getColumnMetaData(h);
            String header = records.useColmnAlias(cmd.getColumnType(), cmd.getColumnLabel());
            //ColumnType column_type = records.getColumnMetaData(h).getColumnType();
            //String header =  records.useColmnAlias(column_type, column_type.getTableColumnName());

            //Terminal.debugPrintln("Records.print():header=" + header + ":from=" + cmd.getColumnType().toString());
            //Record record = records.getRecord(0);
            //ColumnType column_type = record.getColumnMetaData(h).getColumnType();
            //String label = record.useColmnAlias(column_type, column_type.getTableColumnName());
            //Terminal.debugPrintln("Record .print():header=" + label + ":from=" + column_type.toString());
            return header;
        }

        @Override
        public StringSquare getValue(int v, int h) {
            String str = records.hasValue(v, h) ? records.getValueString(v, h) : "";
            return new StringSquare(str, records.getColumnMetaData(h).getAlignLeft());
        }

        /*public boolean alignLeft(int v, int h) {
            return records.getColumnMetaData(h).getAlignLeft();
        }*/
        @Override
        public int vLength() {
            return records.length();
        }

        @Override
        public int hLength() {
            return records.columnsLength();
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (this.valuess != null ? this.valuess.hashCode() : 0);
        return hash;
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
}
