package Util;

import java.sql.*;

public class VeriTabaniUtil {

    static Connection conn=null;
    public static Connection Baglan() {
        try {

            conn = DriverManager.getConnection("jdbc:mysql://localhost/AnalysisAppDb?autoReconnect=true&useSSL=false", "root", "mysql");
            //conn = DriverManager.getConnection("jdbc:mysql://sql7.freesqldatabase.com/sql7345202", "sql7345202", "HvGU4iDGLM");
            return conn;
        } catch (Exception e) {
            System.out.println(e.getMessage().toString());
            return null;
        }
    }
}
