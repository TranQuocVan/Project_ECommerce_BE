package service.log;

import database.LogDAO;
import model.request.LogModel;

import java.util.List;

public class LogService {


    public static void logLogin(int userId, String username, boolean success, String ipAddress) {
        String dataAfter = success ? "SUCCESS" : "INVALID_PASSWORD";
        LogDAO.insertLog(userId, "LOGIN", "users", username, dataAfter, ipAddress);

    }

    public static void logLoginWithGG(int userId, String tokenGG, boolean success, String ipAddress) {
        String dataAfter = success ? "SUCCESS" : "INVALID_PASSWORD";
        LogDAO.insertLog(userId, "LOGIN_WITH_GG", "users", tokenGG, dataAfter, ipAddress);

    }

    public static void logLoginWithFB(int userId, String tokenFB, boolean success, String ipAddress) {
        String dataAfter = success ? "SUCCESS" : "INVALID_PASSWORD";
        LogDAO.insertLog(userId, "LOGIN_WITH_FB", "users", tokenFB, dataAfter, ipAddress);

    }


    public static void resetPassword(int userId,String token, boolean success, String ipAddress) {
        String dataAfter = success ? "PASSWORD_CHANGED" : "PASSWORD_HAS_NOT_BEEN_CHANGED";
        LogDAO.insertLog(userId, "RESET_PASSWORD", "users", token, dataAfter, ipAddress);
    }

    public static void signUp(int userId,String gmail, boolean success, String ipAddress) {
        String dataAfter = success ? "SUCCESS" : "FAILURE";
        LogDAO.insertLog(userId, "SIGN_UP", "users", gmail, dataAfter, ipAddress);
    }

    public static void forgotPassword(int userId,String gmail, boolean success, String ipAddress) {
        String dataAfter = success ? "SUCCESS" : "FAILURE";
        LogDAO.insertLog(userId, "FORGOT_PASSWORD", "users", gmail, dataAfter, ipAddress);
    }
    public static void logout(int userId,String gmail, boolean success, String ipAddress) {
        String dataAfter = success ? "SUCCESS" : "FAILURE";
        LogDAO.insertLog(userId, "LOG_OUT", "users", gmail, dataAfter, ipAddress);
    }

    public static void paymentCod(int userId, String dataAfter, String ipAddress) {
        LogDAO.insertLog(userId, "PAYMENT_COD", "orders", "none","Đơn hàng" + dataAfter, ipAddress);
    }

    public static void paymentVNPay(int userId, String dataAfter, String ipAddress) {
        LogDAO.insertLog(userId, "PAYMENT_VNPay", "orders", "none","Đơn hàng" + dataAfter, ipAddress);
    }

    public static void adminAddCatalog(int userId, String dataAfter, String ipAddress) {
        LogDAO.insertLog(userId, "ADMIN_ADD_CATALOG", "Catalogs", "none", dataAfter, ipAddress);
    }

    public static void adminAddGroupProduct(int userId, String dataAfter, String ipAddress) {
        LogDAO.insertLog(userId, "ADMIN_ADD_GROUP_PRODUCT", "groupProduct", "none", dataAfter, ipAddress);
    }

    public static void adminDeleteProduct(int userId, String dataAfter, String ipAddress) {
        LogDAO.insertLog(userId, "ADMIN_DELETE_PRODUCT", "Products", "none", "productId has deleted" + dataAfter, ipAddress);
    }

    public static List<LogModel> getAllLogs() {
        return LogDAO.getAllLogs();
    }


}
