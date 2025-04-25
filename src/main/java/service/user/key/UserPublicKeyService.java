package service.user.key;

import database.UserPublicKeyDao;

import java.sql.SQLException;

public class UserPublicKeyService {

    public boolean hasPublicKeyByGmail(String gmail) throws SQLException {
        UserPublicKeyDao dao = new UserPublicKeyDao();
        return dao.hasPublicKeyByGmail(gmail);
    }

    public String getLatestUserPublicKeyByGmail(String gmail) throws SQLException {
        UserPublicKeyDao dao = new UserPublicKeyDao();
        return dao.getLatestUserPublicKeyByGmail(gmail);
    }

    public boolean insertUserPublicKeyByGmail(String gmail, String publicKey) throws SQLException {
        UserPublicKeyDao dao = new UserPublicKeyDao();
        return dao.insertUserPublicKeyByGmail(gmail,publicKey,"RSA_SHA");
    }
}
