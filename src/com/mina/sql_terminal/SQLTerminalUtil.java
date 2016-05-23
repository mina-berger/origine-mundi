/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mina.sql_terminal;

import com.mina.sql.Record;
import com.mina.sql.Records;
import com.mina.terminal.TInfo;
import com.mina.util.CoreException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author hiyamamina
 */
public class SQLTerminalUtil {

    public static int update(TInfo info, String update, Object... values) {
        Connection conn = (Connection) info.get("conn");
        try {
            if (values.length == 0) {
                Statement stmt = conn.createStatement();
                return stmt.executeUpdate(update);
            } else {
                PreparedStatement pstmt = getPreparedStatement(conn, update, values);
                /*PreparedStatement pstmt = conn.prepareStatement(update);
                for(int i = 0;i < values.length;i++){
                    if(values[i] instanceof Integer){
                        pstmt.setInt(i + 1, (Integer)values[i]);
                    }else if(values[i] instanceof String){
                        pstmt.setString(i + 1, (String)values[i]);
                    }else{
                        throw new CoreException("unknown type:" + values.getClass().getName());
                    }
                }*/
                return pstmt.executeUpdate();
            }
        } catch (SQLException ex) {
            info.warn(ex.getMessage());//Logger.getLogger(CTSelectSong.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public static Records getRecords(TInfo info, String select, Object... values) {
        Connection conn = (Connection) info.get("conn");
        try {
            ResultSet rs;
            if (values.length == 0) {
                Statement stmt = conn.createStatement();
                rs = stmt.executeQuery(select);
            } else {
                PreparedStatement pstmt = getPreparedStatement(conn, select, values);
                rs = pstmt.executeQuery();
            }
            return new Records(rs);
        } catch (SQLException ex) {
            info.warn(ex.getMessage());//Logger.getLogger(CTSelectSong.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static Record selectRecord(TInfo info, String select, Object... values) {
        Records records = getRecords(info, select, values);
        if (records == null || records.isEmpty()) {
            return null;
        } else if (records.isSolo()) {
            return records.getRecord(0);
        }
        records.print(true, info.getOut());
        return records.getRecord(info.readInt("select", 0, records.length()));
        /*        
        Connection conn = (Connection) info.get("conn");
        try {
            ResultSet rs;
            if(values.length == 0){
                Statement stmt = conn.createStatement();
                rs = stmt.executeQuery(select);
            }else{
                PreparedStatement pstmt = getPreparedStatement(conn, select, values);
                rs = pstmt.executeQuery();
            }
            Records records = new Records(rs);
            if(records.isEmpty()){
                return null;
            }else if(records.isSolo()){
                return records.getRecord(0);
            }
            records.print(true, info.getOut());
            return records.getRecord(info.readInt("select", 0, records.length()));
        } catch (SQLException ex) {
            info.warn(ex.getMessage());//Logger.getLogger(CTSelectSong.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;*/

    }

    private static PreparedStatement getPreparedStatement(Connection conn, String sql, Object[] values) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(sql);
        for (int i = 0; i < values.length; i++) {
            if (values[i] instanceof Integer) {
                pstmt.setInt(i + 1, (Integer) values[i]);
            } else if (values[i] instanceof Double) {
                pstmt.setDouble(i + 1, (Double) values[i]);
            } else if (values[i] instanceof String) {
                pstmt.setString(i + 1, (String) values[i]);
            } else if (values[i] instanceof byte[]) {
                pstmt.setBytes(i + 1, (byte[]) values[i]);
            } else {
                System.out.println(values[i] instanceof byte[]);
                throw new CoreException("unknown type:" + values.getClass().getName());
            }
        }
        return pstmt;

    }

}
