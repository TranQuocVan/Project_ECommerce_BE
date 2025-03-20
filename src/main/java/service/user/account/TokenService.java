package service.user.account;

import java.util.UUID;

public class TokenService {
    public static String generateToken() {
        return UUID.randomUUID().toString();
    }
}
