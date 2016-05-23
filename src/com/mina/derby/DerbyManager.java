/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mina.derby;

import com.mina.util.CoreException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Properties;

/**
 *
 * @author mina
 */
public class DerbyManager {

    public static final String DRIVER   = "org.apache.derby.jdbc.ClientDriver";
    public static final String PROTOCOL = "jdbc:derby://localhost:1527/";
    
    //public static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    //public static final String PROTOCOL = "jdbc:derby:";
    //String nsURL="jdbc:derby://localhost:1527/sample";

    public static HashMap<String, Connection> connections;
    public static HashMap<String, Statement> statements;

    static {
        connections = new HashMap<>();
        statements = new HashMap<>();
    }
    private static boolean called = false;

    public static void callConnection(String database_name, String user_name, String password, boolean create) {
        Connection con = connections.get(database_name);
        if (con != null) {
            return;
        }
        Properties props = new Properties();
        props.put("user", user_name);
        props.put("password", password);

        if (!called) {
            try {
                Class.forName(DRIVER).newInstance();
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                throw new CoreException(e);
            }
            System.out.println("Loaded the appropriate driver.");
            called = true;
        }
        String url = PROTOCOL + database_name;
        if (create) {
            url += ";create=true";
        }
        try {
            con = DriverManager.getConnection(url, props);
            con.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
            //System.out.println(e.getMessage());
            Exception ee = e.getNextException();
            if (ee != null) {
                System.out.println(ee.getMessage());
            }
            terminate();
            System.out.println("[terminated]");
            System.exit(0);
            return;
        }
        connections.put(database_name, con);
    }

    public static void commit(String database_name) {
        try {
            getConnection(database_name).commit();
        } catch (SQLException e) {
            throw new CoreException(e);
        }
    }

    public static Connection getConnection(String database_name) {
        Connection con = connections.get(database_name);
        if (con != null) {
            return con;
        }
        throw new CoreException("[error] connection is not established (" + database_name + ")");
    }

    public static Statement getStatement(String database_name) {
        Statement stmt = statements.get(database_name);
        if (stmt != null) {
            return stmt;
        }
        try {
            stmt = getConnection(database_name).createStatement();
            statements.put(database_name, stmt);
            return stmt;
        } catch (SQLException e) {
            String msg = "[error] could not create statement (" + database_name + ")";
            throw new CoreException(msg, e);
        }
    }

    public static void terminate() {
        try {
            for (String s_key:statements.keySet()) {
                Statement s = statements.get(s_key);
                if (!s.isClosed()) {
                    s.close();
                }
            }
            connections.clear();
            statements.clear();
        } catch (SQLException e) {
            throw new CoreException(e);
        }

    }

    public static void terminate(String database_name) {
        try {
            Statement s = statements.get(database_name);
            if (s != null && !s.isClosed()) {
                s.close();
            }
            statements.remove(database_name);
            Connection c = connections.get(database_name);
            if (c != null && !c.isClosed()) {
                c.close();
            }
            connections.remove(database_name);
        } catch (SQLException e) {
            throw new CoreException(e);
        }

    }

    public static DatabaseMetaData getMetaData(String database_name, String user_name, String password, boolean create) {
        try {
            DerbyManager.callConnection(database_name, user_name, password, create);
            Connection con = DerbyManager.getConnection(database_name);
            //SQLDialects.dialects = new DerbyDialects();
            return con.getMetaData();
        } catch (SQLException ex) {
            throw new CoreException(ex);
        }
    }

}
