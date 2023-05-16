package com.hum.util;

/**
 *
 * @author Hassan Mahfuj
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

public class db {

    final private String DB_NAME = "bank_system";
    final private String DB_USER = "root";
    final private String DB_PASS = "admin123";

    private static db dbInstance = null;
    private Connection con;
    private PreparedStatement pst;

    private db() {
        connect();
    }

    public static db get() {
        if (dbInstance == null) {
            dbInstance = new db();
        }
        return dbInstance;
    }

    private void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + DB_NAME, DB_USER, DB_PASS);
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e);
        }
    }

    public ResultSet executeQuery(String statement) {
        ResultSet rs = null;
        try {
            pst = con.prepareStatement(statement);
            rs = pst.executeQuery();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        return rs;
    }
    
    public ResultSet executeQuery(String statement, Object ...args) {
        ResultSet rs = null;
        try {
            pst = con.prepareStatement(statement);
            for(int i=0; i<args.length; i++) {
//                pst.setString(i+1, args[i]);
                if(args[i] instanceof String)
                    pst.setString(i+1, (String) args[i]);
                if(args[i] instanceof Date)
                    pst.setDate(i+1, (Date) args[i]);
                if(args[i] == null)
                    pst.setString(i+1, null);
            }
            rs = pst.executeQuery();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        return rs;
    }

    public boolean executeUpdate(String statement) {
        try {
            pst = con.prepareStatement(statement);
            if (pst.executeUpdate() != -1) {
                return true;
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        return false;
    }
    
    public boolean executeUpdate(String statement, Object ...args) {
        try {
            pst = con.prepareStatement(statement);
            for(int i=0; i<args.length; i++) {
                if(args[i] instanceof String)
                    pst.setString(i+1, (String) args[i]);
                if(args[i] instanceof Date)
                    pst.setDate(i+1, (Date) args[i]);
                if(args[i] == null)
                    pst.setString(i+1, null);
            }
            if (pst.executeUpdate() != -1) {
                return true;
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        return false;
    }
}
