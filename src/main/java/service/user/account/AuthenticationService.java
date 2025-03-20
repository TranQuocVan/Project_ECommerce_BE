package service.user.account;

import database.UserDao;
import model.UserModel;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AuthenticationService {
    // Kiểm tra mã xác thực
    public boolean validateAuthCode(String inputCode, String sessionCode) {
        return inputCode != null && inputCode.equals(sessionCode);
    }

    // Xử lý đăng ký người dùng
    public UserModel registerUser(String gmail, String password) {
        UserDao userDao = new UserDao();
        String hashedPassword = hashPassword(password); // Mã hóa mật khẩu
        UserModel userModel = new UserModel(gmail, hashedPassword, "user");

        // Lưu người dùng vào cơ sở dữ liệu
        userDao.insert(userModel);

        // Đặt lại mật khẩu để tránh lộ thông tin
        userModel.setPassword("******");
        return userModel;
    }

    // Hàm mã hóa mật khẩu sử dụng SHA-256
    private String hashPassword(String password) {
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
}
