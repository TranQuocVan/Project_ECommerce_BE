package service.util;

import jakarta.servlet.http.HttpSession;

public class SessionServices {

    // kiem tra session co ton tai hay khong
    public boolean isSessionExistence(HttpSession session){
        return session != null ;
    }
    // kiem tra thong tin cua session
    public boolean isSessionInformation(HttpSession session){
        String authCodeOfSession = (String) session.getAttribute("authCode");
        String gmail = (String) session.getAttribute("gmail");
        String password = (String) session.getAttribute("password");
        return authCodeOfSession != null && gmail != null && password != null;
    }
    public void invalidateSession(HttpSession session){
        session.invalidate();
    }
}
