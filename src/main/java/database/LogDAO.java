package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LogDAO {

    public static void insertLog(int userId, String action, String tableName, String dataBefore, String dataAfter, String ipAddress) {
        String sql = "INSERT INTO logs (user_id, action, table_name, data_before, data_after, ip_address) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setString(2, action);
            stmt.setString(3, tableName);
            stmt.setString(4, dataBefore);
            stmt.setString(5, dataAfter);
            stmt.setString(6, ipAddress);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
