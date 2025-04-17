package util;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class SignatureVerifier {

    public static boolean verifySignature(String content, String base64Signature, String base64PublicKey) {
        try {
            // Giải mã Base64 của public key thành PublicKey object
            byte[] publicKeyBytes = Base64.getDecoder().decode(base64PublicKey);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(keySpec);

            // Chuẩn bị kiểm tra chữ ký
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initVerify(publicKey);
            String contentWithLF = content.replace("\r\n", "\n");
            signature.update(contentWithLF.getBytes(StandardCharsets.UTF_8));

            // Giải mã chữ ký từ Base64
            byte[] signatureBytes = Base64.getDecoder().decode(base64Signature);

            // Kiểm tra
            return signature.verify(signatureBytes);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
