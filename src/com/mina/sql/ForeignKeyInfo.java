/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mina.sql;

import com.mina.util.CoreException;
import java.sql.DatabaseMetaData;
import java.util.ArrayList;


/**
 *
 * @author mina
 */
public class ForeignKeyInfo extends Records {

    private ForeignKeyInfo(ArrayList<Record> records) {
        super(records);

    }

    public Table getPKTable() {
        return new Table(
                getValueString(0, SQLUtil.getWildColumnType("PKTABLE_CAT")),
                getValueString(0, SQLUtil.getWildColumnType("PKTABLE_SCHEM")),
                getValueString(0, SQLUtil.getWildColumnType("PKTABLE_NAME")));
    }

    public Table getFKTable() {
        return new Table(
                getValueString(0, SQLUtil.getWildColumnType("FKTABLE_CAT")),
                getValueString(0, SQLUtil.getWildColumnType("FKTABLE_SCHEM")),
                getValueString(0, SQLUtil.getWildColumnType("FKTABLE_NAME")));
    }
    public Column[] getPKColumns(){
        Column[] pk_columns = new Column[length()];
        for(int i = 0;i < length();i++){
            pk_columns[i] = getPKColumn(i);
        }
        return pk_columns;
    }
    public Column[] getFKColumns(){
        Column[] fk_columns = new Column[length()];
        for(int i = 0;i < length();i++){
            fk_columns[i] = getFKColumn(i);
        }
        return fk_columns;
    }
    public Column getPKColumn(int index){
        return new Column(
                getValueString(index, SQLUtil.getWildColumnType("PKTABLE_CAT")),
                getValueString(index, SQLUtil.getWildColumnType("PKTABLE_SCHEM")),
                getValueString(index, SQLUtil.getWildColumnType("PKTABLE_NAME")),
                getValueString(index, SQLUtil.getWildColumnType("PKCOLUMN_NAME")));
    }
    public Column getFKColumn(int index){
        return new Column(
                getValueString(index, SQLUtil.getWildColumnType("FKTABLE_CAT")),
                getValueString(index, SQLUtil.getWildColumnType("FKTABLE_SCHEM")),
                getValueString(index, SQLUtil.getWildColumnType("FKTABLE_NAME")),
                getValueString(index, SQLUtil.getWildColumnType("FKCOLUMN_NAME")));
    }

    public String[] getPKColumnNames() {
        return getValueStrings(SQLUtil.getWildColumnType("PKCOLUMN_NAME"));
    }

    public String[] getFKColumnNames() {
        return getValueStrings(SQLUtil.getWildColumnType("FKCOLUMN_NAME"));
    }

    /*public static ForeignKeyInfo get(DatabaseMetaData dmd, ColumnMetaData cmd) {
        return get(dmd, cmd.getCatalogName(), cmd.getSchemaName(), cmd.getTableName(), cmd.getColumnName());
    }*/

    public static ForeignKeyInfo instance(DatabaseMetaData dmd, Column column) {
        Records records = SQLUtil.getImportedKeys(dmd, column.getTable());
        int index = -1;
        for (int i = 0; i < records.length(); i++) {
            Column fk_column = new Column(
                    records.getValueString(i, SQLUtil.getWildColumnType("FKTABLE_CAT")),
                    records.getValueString(i, SQLUtil.getWildColumnType("FKTABLE_SCHEM")),
                    records.getValueString(i, SQLUtil.getWildColumnType("FKTABLE_NAME")),
                    records.getValueString(i, SQLUtil.getWildColumnType("FKCOLUMN_NAME")));
            //if (records.getValueString(i, "FKCOLUMN_NAME").equals(column)) {
            if (fk_column.equals(column)) {
                index = i;
                break;
            }
        }
        if (index < 0) {
            return null;
        }
        String pk_name = records.getValueString(index, SQLUtil.getWildColumnType("PK_NAME"));
        String fk_name = records.getValueString(index, SQLUtil.getWildColumnType("FK_NAME"));
        ArrayList<Record> vec = new ArrayList<Record>();
        for (int i = 0; i < records.length(); i++) {
            if (records.getValueString(i, SQLUtil.getWildColumnType("PK_NAME")).equals(pk_name) &&
                    records.getValueString(i, SQLUtil.getWildColumnType("FK_NAME")).equals(fk_name)) {
                vec.add(records.getRecord(i));
            }
        }
        if (vec.isEmpty()) {
            throw new CoreException("外部キーが見つかりません。(PK_NAME=" + pk_name + "/FK_NAME=" + fk_name + ")");
        }
        return new ForeignKeyInfo(vec);
    }
}
