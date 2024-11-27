package database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.UserModel;

public class UserDao {

    /**
     * Thêm người dùng mới vào CSDL.
     * @param userModel - Đối tượng UserModel chứa thông tin người dùng.
     * @return Số dòng bị ảnh hưởng (1 nếu thành công, 0 nếu thất bại).
     */
    public int insert(UserModel userModel) {
        String sql = "INSERT INTO users (gmail, password, role) VALUES (?, ?, ?)";
        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, userModel.getGmail());
            st.setString(2, userModel.getPassword()); // Nên mã hóa mật khẩu trước khi lưu
            st.setString(3, userModel.getRole());

            return st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0; // Lỗi trong quá trình thêm người dùng
        }
    }

    /**
     * Thêm danh sách người dùng vào CSDL.
     * @param users - Danh sách UserModel cần thêm.
     * @return Số dòng bị ảnh hưởng.
     */
    public int insertAll(ArrayList<UserModel> users) {
        int count = 0;
        for (UserModel user : users) {
            count += this.insert(user);
        }
        return count;
    }

    /**
     * Lấy thông tin người dùng theo Gmail.
     * @param gmail - Gmail cần tìm.
     * @return UserModel nếu tìm thấy, null nếu không tồn tại.
     */
    public UserModel selectByGmail(String gmail) {
        String sql = "SELECT * FROM users WHERE gmail = ?";
        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, gmail);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return new UserModel(
                            rs.getString("gmail"),
                            "******",
                            rs.getString("role")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Không tìm thấy người dùng
    }

    /**
     * Kiểm tra thông tin đăng nhập của người dùng.
     * @param userModel - Đối tượng UserModel chứa Gmail và mật khẩu.
     * @return UserModel nếu thông tin hợp lệ, null nếu không hợp lệ.
     */
    public UserModel checkLogin(UserModel userModel) {
        String sql = "SELECT * FROM users WHERE gmail = ? AND password = ?";
        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, userModel.getGmail());
            st.setString(2, userModel.getPassword()); // Nên mã hóa mật khẩu trước khi kiểm tra

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return new UserModel(
                            rs.getString("gmail"),
                            "******", // Ẩn mật khẩu khi trả về
                            rs.getString("role")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Không hợp lệ
    }

    /**
     * Cập nhật token "Remember Me" cho người dùng.
     * @param gmail - Gmail của người dùng.
     * @param token - Token cần cập nhật.
     * @return Số dòng bị ảnh hưởng.
     */
    public int updateRememberMeToken(String gmail, String token) {
        String sql = "UPDATE users SET remember_me_token = ? WHERE gmail = ?";
        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, token);
            st.setString(2, gmail);

            return st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0; // Lỗi trong quá trình cập nhật token
        }
    }

    /**
     * Lấy thông tin người dùng dựa trên token "Remember Me".
     * @param token - Token cần tìm.
     * @return UserModel nếu tìm thấy, null nếu không tồn tại.
     */
    public UserModel selectByToken(String token) {
        String sql = "SELECT * FROM users WHERE remember_me_token = ?";
        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, token);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return new UserModel(
                            rs.getString("gmail"),
                            "******", // Ẩn mật khẩu
                            rs.getString("role")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Không tìm thấy người dùng
    }


    public static void invalidateToken(String token) throws SQLException {
        String query = "UPDATE users SET remember_me_token = NULL WHERE remember_me_token = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, token);
            stmt.executeUpdate();
        }
    }
}
