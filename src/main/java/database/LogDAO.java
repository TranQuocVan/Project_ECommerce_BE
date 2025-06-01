package database;

import model.request.LogModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public static List<LogModel> getAllLogs() {
        List<LogModel> logs = new ArrayList<>();
        String sql = "SELECT id, user_id, action, table_name, data_before, data_after, ip_address, created_at FROM logs ORDER BY created_at DESC";

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                LogModel log = new LogModel();
                log.setId(rs.getInt("id"));
                log.setUserId(rs.getInt("user_id"));
                log.setAction(rs.getString("action"));
                log.setTableName(rs.getString("table_name"));
                log.setDataBefore(rs.getString("data_before"));
                log.setDataAfter(rs.getString("data_after"));
                log.setIpAddress(rs.getString("ip_address"));
                log.setTimestamp(rs.getTimestamp("created_at").toString());

                logs.add(log);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return logs;
    }

}
