package com.company;

import java.sql.*;

public class dbConnection {
    static ResultSet rs;
    static Connection con;

    public static Connection connection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        //Replace url in DriverManager.getConnection() to user's database path
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ishop", "root", "");
        return con;
    }

    //Universal SQL statement execution function
    public static ResultSet dbExecuteQuery(String sql) throws SQLException {
        try {
            con = connection();
            Statement stmt = con.createStatement( );
            rs = stmt.executeQuery(sql);
        } catch (Exception e) {
            System.out.println(e);
        }
        return rs;
    }
    public static void databaseExecuteQuery(String sql) throws SQLException {
        try {
            con = connection();
            Statement stmt = con.createStatement( );
            stmt.execute(sql);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //Closes the connection
    public static void dbClose() throws SQLException {
        con.close( );
    }

    //returns the number of rows in a table
    public static int dbNoRows() throws SQLException {
        ResultSet rs = dbConnection.dbExecuteQuery("select * from product");
        int i = 0;
        while (rs.next( )) {
            i++;
        }
        dbConnection.dbClose( );
        return i;
    }
}
