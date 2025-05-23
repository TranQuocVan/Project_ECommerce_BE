package database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

import model.UserModel;

public class UserDao {


    public void updateUserPasswordByGmail(String gmail, String password) throws SQLException {
        String sql = "UPDATE users SET password = ? where gmail = ?";
        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, password);
            st.setString(2, gmail);
            st.executeUpdate();
        }
    }
    public boolean updateTokenFb(long token, String email) throws SQLException {
        String sql = "UPDATE  users SET facebook_id = ? where gmail = ?";
        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setLong(1, token);
            st.setString(2, email);
            return st.executeUpdate() > 0;
        }
    }


    public int insert(UserModel userModel) {
        // Ensure userModel is not null
        if (userModel == null || userModel.getGmail() == null || userModel.getPassword() == null || userModel.getRole() == null) {
            throw new IllegalArgumentException("UserModel or its fields cannot be null");
        }

        // Use modern Java Date API
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // Use standard SQL datetime format

        String sql = "INSERT INTO users (gmail, password, role, created_at) VALUES (?, ?, ?, ?)";

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            // Set parameters
            st.setString(1, userModel.getGmail());
            st.setString(2, userModel.getPassword()); // Hash the password before storing
            st.setString(3, userModel.getRole());
            st.setString(4, now.format(formatter));

            return st.executeUpdate(); // Execute the query
        } catch (SQLException e) {
            // Log the error and rethrow it or handle it accordingly
            System.err.println("Error while inserting user: " + e.getMessage());
            e.printStackTrace();
            return 0; // Return 0 if insertion fails
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
        // Kiểm tra tham số đầu vào
        if (gmail == null || gmail.isEmpty()) {
            throw new IllegalArgumentException("Gmail không được để trống");
        }

        String sql = "SELECT * FROM users WHERE gmail = ?";
        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, gmail);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    if(!Objects.equals(rs.getString("password"), "")) {
                        return new UserModel(
                                rs.getInt("userId"), // Lấy ID từ cơ sở dữ liệu
                                rs.getString("gmail"), // Lấy Gmail từ cơ sở dữ liệu
                                rs.getString("role") // Lấy vai trò người dùng
                        );

                    }
                }
            }
        } catch (SQLException e) {
            // Log lỗi chi tiết và không che giấu thông tin hữu ích
            System.err.println("Error while selecting user by Gmail: " + e.getMessage());
            e.printStackTrace();
        }
        return null; // Trả về null nếu không tìm thấy người dùng
    }

    public UserModel selectByFacebookId(long facebookId) {

        String sql = "SELECT * FROM users WHERE facebook_id = ?";
        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setLong(1, facebookId);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return new UserModel(
                            rs.getInt("userId"), // Lấy ID từ cơ sở dữ liệu
                            rs.getString("gmail"), // Lấy Gmail từ cơ sở dữ liệu
                            rs.getString("role") // Lấy vai trò người dùng
                    );
                }
            }
        } catch (SQLException e) {
            // Log lỗi chi tiết và không che giấu thông tin hữu ích
            System.err.println("Error while selecting user by facebookId: " + e.getMessage());
            e.printStackTrace();
        }
        return null; // Trả về null nếu không tìm thấy người dùng
    }


    public boolean isGmailExists(String gmail) {
        String sql = "SELECT COUNT(*) FROM users WHERE gmail = ?";
        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, gmail);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Trả về true nếu Gmail tồn tại
                }
            }
        } catch (SQLException e) {
            System.err.println("Error while checking Gmail: " + e.getMessage());
            e.printStackTrace();
        }
        return false; // Trả về false nếu có lỗi hoặc không tồn tại
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
                            rs.getInt("userId"),
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
                            rs.getInt("userId"),
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

    public boolean updateUserPasswordById(int userId, String newPassword) {
        String sql = "UPDATE users SET password = ? WHERE userId = ?";

        // Kiểm tra đầu vào
        if (userId <= 0 || newPassword == null || newPassword.isEmpty()) {
            throw new IllegalArgumentException("ID không hợp lệ hoặc mật khẩu mới bị trống");
        }


        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, newPassword);
            st.setInt(2, userId);

            return st.executeUpdate() > 0; // Trả về true nếu cập nhật thành công
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Trả về false nếu có lỗi
    }
}
