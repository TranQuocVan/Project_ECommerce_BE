package service.util;

import util.Email;

import java.util.Random;

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


}
