/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mina.sql;

import com.mina.util.Code;
import com.mina.util.CoreException;

/**
 *
 * @author mina
 */
public class RecordCode extends Code {

    public RecordCode(String code_name, Records records, ColumnType[] column_types) {
        super(code_name);
        if (column_types == null || column_types.length != 3) {
            throw new CoreException("unexpected colun_labels for ResultSetCode");
        }
        Records sub_records = records.getSubRecords(column_types);
        options = new Option[sub_records.length()];
        for (int i = 0; i < sub_records.length(); i++) {
            options[i] = new Option(
                    sub_records.getValueString(i, 0),
                    sub_records.getValueString(i, 1),
                    sub_records.getValue(i, 2));
        }
    }
}
