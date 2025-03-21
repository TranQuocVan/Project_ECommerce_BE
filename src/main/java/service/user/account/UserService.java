package service.user.account;

import database.UserDao;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.UserModel;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class UserService {

    public boolean isUserModelExistence(UserModel userModel){
        return userModel != null;
    }

    /**
     * Kiểm tra thông tin đăng nhập (Gmail và mật khẩu).
     * @param userModel - Đối tượng UserModel chứa Gmail và mật khẩu.
     * @return UserModel nếu thông tin hợp lệ, null nếu không hợp lệ.
     * @throws SQLException nếu xảy ra lỗi trong truy vấn cơ sở dữ liệu.
     */
    public static UserModel checkValidGmailAndPassword(UserModel userModel) throws SQLException {
        UserDao userDao = new UserDao();
        return userDao.checkLogin(userModel); // Gọi trực tiếp phương thức từ UserDao
    }

    /**
     * Kiểm tra xem Gmail có hợp lệ và đã tồn tại trong hệ thống hay chưa.
     * @param gmail - Gmail cần kiểm tra.
     * @return "Success" nếu Gmail hợp lệ và chưa tồn tại, ngược lại trả về thông báo lỗi.
     * @throws SQLException nếu xảy ra lỗi trong truy vấn cơ sở dữ liệu.
     */
    public String checkValidGmailAndExists(String gmail) throws SQLException {
        if (!EmailValidator.isValidEmail(gmail)) {
            return "Invalid Gmail";
        }
        UserDao userDao = new UserDao();
        UserModel user = userDao.selectByGmail(gmail); // Gọi trực tiếp phương thức từ UserDao
        if (user == null) {
            return "Success";
        }

        return "Gmail already exists";
    }



    /**
     * Cập nhật token "Remember Me" cho người dùng.
     * @param user - Đối tượng UserModel chứa Gmail và token mới.
     * @throws SQLException nếu xảy ra lỗi trong truy vấn cơ sở dữ liệu.
     */
    public static void updateRememberMeToken(UserModel user) throws SQLException {
        UserDao userDao = new UserDao();
        userDao.updateRememberMeToken(user.getGmail(), user.getRememberMeToken());
    }

    /**
     * Lấy thông tin người dùng dựa trên token "Remember Me".
     * @param token - Token cần tìm.
     * @return UserModel nếu token hợp lệ, null nếu không hợp lệ.
     * @throws SQLException nếu xảy ra lỗi trong truy vấn cơ sở dữ liệu.
     */
    public static UserModel getUserByToken(String token) throws SQLException {
        UserDao userDao = new UserDao();
        return userDao.selectByToken(token);
    }


    public  void invalidateToken(String token) throws SQLException {
        UserDao.invalidateToken(token);
    }

    // Xác thực thông tin người dùng
    public  UserModel authenticateUser(String gmail, String password) throws SQLException {
        UserModel userModel = new UserModel(gmail, hashPassword(password));

        // Kiểm tra tài khoản hợp lệ qua DAO (hoặc thêm các bước xử lý khác)
        return checkValidGmailAndPassword(userModel);
    }

    // Hàm mã hóa mật khẩu sử dụng SHA-256
    private static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    public static void handleRememberMe(UserModel userModel, HttpSession session, HttpServletResponse response) throws SQLException {
        // Xóa mật khẩu trước khi lưu vào session
        userModel.setPassword("******");
        session.setAttribute("user", userModel);

        // Tạo token
        String token = TokenService.generateToken();
        userModel.setRememberMeToken(token);

        // Cập nhật token vào cơ sở dữ liệu
        updateRememberMeToken(userModel);

        // Lưu trạng thái đăng nhập
        session.setAttribute("isLogin", true);

        // Tạo và thêm cookie
        Cookie tokenCookie = new Cookie("remember_me", token);

        tokenCookie.setMaxAge(7 * 24 * 60 * 60); // 7 ngày
        tokenCookie.setHttpOnly(true);
        tokenCookie.setSecure(true); // Chỉ hoạt động qua HTTPS
        tokenCookie.setPath("/"); // Có hiệu lực trên toàn bộ ứng dụng
        response.addCookie(tokenCookie);

    }





}
