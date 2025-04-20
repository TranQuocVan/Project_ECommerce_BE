package util;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class SignatureVerifierUtil {
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

    public static void main(String[] args) {
        // Chuỗi nội dung cần kiểm tra chữ ký
        String content = "BdJJ1uORkTey/r5Z9EEhHuVg3fHCjwt2xh4dHCcg0hQ=";

        // Giả sử chúng ta có một publicKey (có thể là từ một nguồn nào đó) và chữ ký đã được tạo ra từ privateKey tương ứng
        String base64PublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkfLEutEzHIK5XkHpKsOebwBtTMT0sEamLxP1TgS8rH6e9rgjW3WCL6xrU3zZT85d1qHI7bx7T521tlivNFZCITraC+B2l+wifdpXDE34jcID2KE71w9U5dOB27HVv9MKTgf9+Lij2P0i4P5mV+IoCxwHWWTv2nBvRLHhsDwb0dq8i4I8PXBNtdhZ5j3WWrgXMqEbVy5aRyKIbeVVh3O4AIbKq4O2Ol2/uVLpHrOkDkVrJTjk5UgtndTrPvf3asM/cngZGsScLrZB/ighkls5kzdjcCJGD+8O4sN+hGJ6zKc74x1MRl4+l+6Jc7jYyyoi2wvP+LArsRJ+YD3so323BQIDAQAB" ;

        String base64Signature = "AmixNNSltBUwf4j+YAbThBCMGiPMiGbr7jLKgd8wdPubn8OphdM4FdMi5Pq+H3H1plhWqdTsxFIOWEHwnVIVjjutw+NkZmdQ22mHGB0cG85v7B133J6RXN+tE9pr1szWDih2AuJqZj5c9psVCcGS7LTZZAaUf7C7gRVOMyM3Ilj8uBXQWo3QnGxmNoN0ae+whzXeEL2iWNNkRlh3YmticOC6k2ZPyAjb8TxJZtk5ZGezW0KXMc65OBIbB380bCZidwVK+671+lKGvl0Ri6Un8OwvGi+lNREXqaa/QMEfPx6fm/QphbKkEhc4X1aA1vr9owmr/Jh0onj7AAhv69wVLw==" ;

        // Kiểm tra chữ ký
        boolean isSignatureValid = SignatureVerifierUtil.verifySignature(content, base64Signature, base64PublicKey);

        if (isSignatureValid) {
            System.out.println("Chữ ký hợp lệ.");
        } else {
            System.out.println("Chữ ký không hợp lệ.");
        }
    }
}
