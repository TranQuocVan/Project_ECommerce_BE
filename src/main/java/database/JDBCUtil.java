package database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.cj.jdbc.Driver;

public class JDBCUtil {
    public static Connection getConnection() {
        Connection c = null;

        try {
            // Đăng ký MySQL Driver với DriverManager
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());

//            String url = "jdbc:mysql://xxx:9999/xxx";
//            String username = "abc";
//            String password = "xyz";

            // LOCAL
            String url = "jdbc:mysql://localhost:3306/shoesql";
            String username = "root";
            String password = "";


//            String url = System.getenv("URL_DB");
//            String username = System.getenv("USER_DB");
//            String password = System.getenv("PASS_DB");

            // Tạo kết nối
            c = DriverManager.getConnection(url, username, password);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return c;
    }

    public static void closeConnection(Connection c) {
        try {
            if(c!=null) {
                c.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void printInfo(Connection c) {
        try {
            if(c!=null) {
                DatabaseMetaData mtdt = c.getMetaData();
                System.out.println(mtdt.getDatabaseProductName());
                System.out.println(mtdt.getDatabaseProductVersion());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}