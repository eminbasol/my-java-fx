package sample.Util;

import java.sql.*;

public class VeriTabaniUtil {

    static Connection conn=null;
    public static Connection Baglan() {
        try {

            conn = DriverManager.getConnection("jdbc:mysql://localhost/AnalysisAppDb?autoReconnect=true&useSSL=false", "root", "mysql");
            return conn;
        } catch (Exception e) {
            System.out.println(e.getMessage().toString());
            return null;
        }
    }
}
