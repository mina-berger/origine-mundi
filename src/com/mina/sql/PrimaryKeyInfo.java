/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mina.sql;

import java.sql.DatabaseMetaData;
import java.util.ArrayList;

/**
 *
 * @author mina
 */
public class PrimaryKeyInfo extends Records {
    private PrimaryKeyInfo(ArrayList<Record> records) {
        super(records);

    }
    public Table getTable() {
        return new Table(
                getValueString(0, SQLUtil.getWildColumnType("TABLE_CAT")),
                getValueString(0, SQLUtil.getWildColumnType("TABLE_SCHEM")),
                getValueString(0, SQLUtil.getWildColumnType("TABLE_NAME")));
    }

    public Column[] getColumns(){
        Column[] columns = new Column[length()];
        for(int i = 0;i < length();i++){
            columns[i] = getColumn(i);
        }
        return columns;
    }
    public boolean hasColumn(Column column){
        for(int i = 0;i < length();i++){
            if(getColumn(i).equals(column)){
                return true;
            }
        }
        return false;
    }
    public Column getColumn(int index){
        return new Column(
                getValueString(index, SQLUtil.getWildColumnType("TABLE_CAT")),
                getValueString(index, SQLUtil.getWildColumnType("TABLE_SCHEM")),
                getValueString(index, SQLUtil.getWildColumnType("TABLE_NAME")),
                getValueString(index, SQLUtil.getWildColumnType("COLUMN_NAME")));
    }
    public String[] getColumnNames() {
        return getValueStrings(SQLUtil.getWildColumnType("COLUMN_NAME"));
    }
    public static PrimaryKeyInfo instance(DatabaseMetaData dmd, Table table){
        Records records = SQLUtil.getPrimaryKeys(dmd, table);
        //if(records.isEmpty()) return null;
        ArrayList<Record> vec = new ArrayList<Record>();
        for (int i = 0; i < records.length(); i++) {
            vec.add(records.getRecord(i));
        }
        return new PrimaryKeyInfo(vec);
        
    }
    public static boolean isPrimaryKey(DatabaseMetaData dmd, ColumnType column_type){
        PrimaryKeyInfo pk_info = instance(dmd, column_type.getTable());
        for(int i = 0;i < pk_info.length();i++){
            if(pk_info.getColumn(i).equals(column_type.getColumn())){
                return true;
            }
        }
        return false;
    }
}
