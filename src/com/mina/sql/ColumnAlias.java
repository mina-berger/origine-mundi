/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mina.sql;

import com.mina.util.CoreException;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;

/**
 *
 * @author mina
 */
public class ColumnAlias extends HashMap<ColumnType, String> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    protected static ColumnAliasServer CA_SERVER = new ColumnAliasServer();

    public static ColumnAlias getUpdatedColumnAlias(DatabaseMetaData dmd, AliasInput[] inputs) {
        return CA_SERVER.getUpdatedColumnAlias(dmd, inputs);
    }

    public static ColumnAlias getColumnAlias(DatabaseMetaData dmd) {
        return CA_SERVER.getColumnAlias(dmd);
    }

    public static void putAliasInputs(DatabaseMetaData dmd, AliasInput[] inputs) {
        CA_SERVER.putAliasInputs(dmd, inputs);
    }

    public static class AliasInput {

        private String dict_name;
        private String disp_name;

        public AliasInput(String dict_name, String disp_name) {
            this.dict_name = dict_name;
            this.disp_name = disp_name;
        }
    }

    public String[] convert(ArrayList<ColumnType> column_types, boolean full_name) {
        return convert(column_types.toArray(new ColumnType[0]), full_name);
    }

    public String[] convert(ColumnType[] column_types, boolean full_name) {
        String[] convert_strs = new String[column_types.length];
        for (int i = 0; i < column_types.length; i++) {
            convert_strs[i] = convert(column_types[i], full_name);
        }
        return convert_strs;
    }

    public String convert(ColumnType column_type, boolean full_name) {
        return convert(column_type, full_name ? column_type.getTableColumnName() : column_type.getColumnName());
    }

    public String convert(ColumnType column_type, String default_name) {
        if (containsKey(column_type)) {
            return get(column_type);
        }
        return default_name;
    }

    private ColumnAlias getCopy() {
        ColumnAlias ca = new ColumnAlias();
        Iterator<ColumnType> tables = super.keySet().iterator();
        while (tables.hasNext()) {
            ColumnType ct = tables.next();
            ca.put(ct, get(ct));
        }
        return ca;
    }

    public static void putAliasInput(DatabaseMetaData dmd, ColumnAlias ca, AliasInput input) {
        ca.put(SQLUtil.getColumnType(dmd, input.dict_name), input.disp_name);
    }

    public static class ColumnAliasServer extends HashMap<String, ColumnAlias> {

        /**
         *
         */
        private static final long serialVersionUID = 1L;

        public ColumnAliasServer() {
            super();
        }

        public ColumnAlias getUpdatedColumnAlias(DatabaseMetaData dmd, AliasInput[] inputs) {
            ColumnAlias ca = getColumnAlias(dmd).getCopy();
            if (inputs == null) {
                inputs = new AliasInput[0];
            }
            for (int i = 0; i < inputs.length; i++) {
                putAliasInput(dmd, ca, inputs[i]);
            }
            return ca;
        }

        public ColumnAlias getColumnAlias(DatabaseMetaData dmd) {
            String key = getServerKey(dmd);
            if (containsKey(key)) {
                return get(key);
            } else {
                ColumnAlias ca = new ColumnAlias();
                put(key, ca);
                return ca;
            }

        }

        public void putAliasInputs(DatabaseMetaData dmd, AliasInput[] inputs) {
            for (int i = 0; i < inputs.length; i++) {
                putAliasInput(dmd, getColumnAlias(dmd), inputs[i]);
            }
        }

        private String getServerKey(DatabaseMetaData dmd) {
            try {
                return dmd.getURL();
            } catch (SQLException e) {
                throw new CoreException(e);
            }
        }

    }

}
