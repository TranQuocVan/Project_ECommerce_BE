package util;

public class GmailServices {
    public boolean isGmailEmpty(String gmail) {
        return gmail.isEmpty();
    }

    public int sendGmail(String gmail) {

//        Random rd = new Random();
//
//        int authCode = Math.abs(rd.nextInt(900000) + 100000);
//
////                 Gửi mã xác thực qua email (bỏ qua phần gửi thực tế để test)
//        Email.sendEmail(gmail, "Auth code", authCode + "");


//        return authCode;
        return 1;
    }

    public boolean sendGmailForForgotPass(String gmail,String token) {
        String resetLink = "http://localhost:8080/Shoe_war_exploded/ResetPasswordForgot?token=" + token;

        String emailContent = "<h2>Yêu cầu đặt lại mật khẩu</h2>" +
                "<p>Bạn đã yêu cầu đặt lại mật khẩu. Nhấn vào liên kết bên dưới để đặt lại:</p>" +
                "<a href='" + resetLink + "' style='background-color: #007bff; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px;'>Đặt lại mật khẩu</a>" +
                "<p>Nếu bạn không yêu cầu, vui lòng bỏ qua email này.</p>";


        return Email.sendEmail(gmail, "Forgot Password", emailContent);
    }

    /**
     * Tạo token đặt lại mật khẩu (có thể sử dụng UUID hoặc random string).
     */
    public String generateTokenForgotPass() {
        return java.util.UUID.randomUUID().toString(); // Sinh token ngẫu nhiên
    }


}
