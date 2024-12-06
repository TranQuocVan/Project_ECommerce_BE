package service;

import database.UserDao;
import model.UserModel;

public class AuthenticationService {
    // Kiểm tra mã xác thực
    public boolean validateAuthCode(String inputCode, String sessionCode) {
        return inputCode != null && inputCode.equals(sessionCode);
    }

    // Xử lý đăng ký người dùng
    public UserModel registerUser(String gmail, String password) {
        UserDao userDao = new UserDao();
        UserModel userModel = new UserModel(gmail, password, "user");

        // Lưu người dùng vào cơ sở dữ liệu
        userDao.insert(userModel);

        // Đặt lại mật khẩu để tránh lộ thông tin
        userModel.setPassword("******");
        return userModel;
    }
}
