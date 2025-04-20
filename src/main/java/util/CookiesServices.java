package util;

import jakarta.servlet.http.Cookie;
import service.user.account.UserService;

import java.sql.SQLException;

public class CookiesServices {

    public final UserService userService = new UserService();
    public boolean checkCookiesExistence(Cookie[] cookie){
        return cookie != null;
    }

    public Cookie clearRememberMeCookie(Cookie[] cookie) {
        for (Cookie ck : cookie) {
            if ("remember_me".equals(ck.getName())) {
                String token = ck.getValue();
                try {
                    userService.invalidateToken(token);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                // Xóa cookie trên trình duyệt
                ck.setValue("");
                ck.setPath("/");
                ck.setMaxAge(0);
                return ck;
            }
        }
        return null;

       

    }

    public String getCookie(Cookie[] cookie){
        for (Cookie ck : cookie) {
            if ("remember_me".equals(ck.getName())) {
                return ck.getValue();
            }
        }
        return null;
    }

}
