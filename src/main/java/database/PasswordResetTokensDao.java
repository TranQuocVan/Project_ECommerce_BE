package database;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PasswordResetTokensDao {

    // Lưu token reset mật khẩu vào database
    public boolean saveResetToken(int userId, String token) {
        try (Connection con = JDBCUtil.getConnection()) {
            String query = "INSERT INTO PasswordResetTokens (userId, token, created_at) VALUES (?, ?, NOW()) " +
                    "ON DUPLICATE KEY UPDATE token = VALUES(token), created_at = NOW()";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, userId);
            stmt.setString(2, token);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Kiểm tra token có hợp lệ không (token phải tồn tại và chưa quá hạn 10 phút)
    public int getUserIdByToken(String token) {
        try (Connection con = JDBCUtil.getConnection()) {
            String query = "SELECT userId FROM PasswordResetTokens WHERE token = ? AND created_at >= NOW() - INTERVAL 10 MINUTE";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, token);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("userId"); // Trả về userId nếu token hợp lệ
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Token không hợp lệ hoặc đã hết hạn
    }

    // Xóa token sau khi đổi mật khẩu thành công
    public boolean deleteToken(String token) {
        try (Connection con = JDBCUtil.getConnection()) {
            String query = "DELETE FROM PasswordResetTokens WHERE token = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, token);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

