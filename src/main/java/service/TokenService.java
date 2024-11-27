package service;

import java.util.UUID;

public class TokenService {
    public static String generateToken() {
        return UUID.randomUUID().toString();
    }
}
