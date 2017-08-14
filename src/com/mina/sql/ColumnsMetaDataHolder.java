/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mina.sql;

import com.mina.terminal.Terminal;
import com.mina.util.CoreException;
import java.util.ArrayList;

/**
 *
 * @author mina
 */
public class ColumnsMetaDataHolder{

    protected ColumnMetaData[] cmd;
    protected int[] disp_indeces;
    protected ColumnAlias ca;

    protected ColumnsMetaDataHolder() {
        this(new ColumnMetaData[0], new int[0], new ColumnAlias());
    }

    protected ColumnsMetaDataHolder(ColumnMetaData[] cmd, int[] disp_indeces, ColumnAlias ca) {
        this.cmd = cmd;
        this.disp_indeces = disp_indeces == null?new int[0]:disp_indeces;
        this.ca = ca;
    }
    public void setColumnAlias(ColumnAlias ca){
        this.ca = ca;
    }
    public String useColmnAlias(ColumnType column_type, String default_name){
        return ca.convert(column_type, default_name);
    }

    public int columnsLength() {
        return cmd.length;
    }
    public ArrayList<ColumnType> getColumnTypes(Table table){
        ArrayList<ColumnType> vec = new ArrayList<ColumnType>();
        for(int i = 0;i < cmd.length;i++){
            ColumnType column_type = cmd[i].getColumnType();
            if(column_type.getTable().equals(table)){
                vec.add(column_type);
            }
        }
        return vec;

    }

    public ColumnMetaData getColumnMetaData(int index) {
        return cmd[index];
    }
    public ColumnMetaData getColumnMetaData(ColumnType column_type) {
        return cmd[getIndex(column_type)];
    }
    public int[] getIndeces(ColumnType[] column_types){
        if(column_types == null) return new int[0];
        int[] indeces = new int[column_types.length];
        for(int i = 0;i < column_types.length;i++){
            indeces[i] = getIndex(column_types[i]);
        }
        return indeces;
    }
    public boolean hasDispIndeces(){return disp_indeces.length > 0;}
    public int[] getDispIndeces(){return disp_indeces;}
    public void setDispIndeces(ColumnType[] column_types){
        if(column_types == null){
            disp_indeces = new int[0];
            return;
        }
        int[] indeces = new int[column_types.length];
        for(int i = 0;i < column_types.length;i++){
            indeces[i] = getIndex(column_types[i]);
        }
        disp_indeces = indeces;
    }
    public void setDispIndeces(int[] indeces){
        if(indeces == null){
            disp_indeces = new int[0];
            return;
        }
        for(int i = 0;i < indeces.length;i++){
            if(indeces[i] >= cmd.length) throw new CoreException("指定されたインデックスが配列長を超えています。");
        }
        disp_indeces = indeces;
    }
    public boolean hasAllColumnTypes(ColumnType[] column_types){
        for(int i = 0;i < column_types.length;i++){
            if(!hasColumnType(column_types[i])){
                return false;
            }
        }
        return true;
    }
    protected boolean hasColumnType(ColumnType column_type){
        for (int index = 0; index < cmd.length; index++) {
            if (cmd[index].getColumnType().equals(column_type)) {
                return true;
            }
        }
        return false;
    }

    protected int getIndex(ColumnType column_type) {
        for (int index = 0; index < cmd.length; index++) {
            if (cmd[index].getColumnType().equals(column_type)) {
                return index;
            }
        }
        for (int index = 0; index < cmd.length; index++) {
            System.out.println(cmd[index].getColumnType().getExplanation());
        }
        throw new CoreException("column inexists(" + column_type.getExplanation() + ")");
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + (this.cmd != null ? this.cmd.hashCode() : 0);
        return hash;
    }
    @Override
    public boolean equals(Object o){
        return compareTo(o) == 0;
    }
    public int compareTo(Object o) {
        return new Integer(hashCode()).compareTo(new Integer(o.hashCode()));
    }

}
