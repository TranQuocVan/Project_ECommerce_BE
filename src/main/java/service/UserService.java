package service;

import database.UserDao;
import model.UserModel;

import java.sql.SQLException;

public class UserService {

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
    public static String checkValidGmailAndExists(String gmail) throws SQLException {
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


    public static void invalidateToken(String token) throws SQLException {
        UserDao.invalidateToken(token);
    }
}
