package model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

public class GroupModel {
    private int id;
    private String name;
    private InputStream image; // Dữ liệu nhị phân của ảnh
    private String imageBase64; // Chuỗi Base64 của ảnh

    // Constructor nhận tên và InputStream cho ảnh
    public GroupModel(String name, InputStream image) {
        this.name = name;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public InputStream getImage() {
        return image;
    }

    public void setImage(InputStream image) {
        this.image = image;
    }

    public String getImageBase64() throws IOException {
        // Nếu imageBase64 đã được set thì trả về
        if (imageBase64 != null) {
            return imageBase64;
        }

        // Nếu chưa có, chuyển đổi InputStream thành Base64
        if (image != null) {
            imageBase64 = convertInputStreamToBase64(image);
        }
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    /**
     * Chuyển đổi InputStream thành chuỗi Base64.
     *
     * @param inputStream Dữ liệu nhị phân của ảnh (InputStream)
     * @return Chuỗi Base64
     * @throws IOException Lỗi khi đọc InputStream
     */
    private String convertInputStreamToBase64(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096]; // Bộ nhớ đệm
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, bytesRead);
        }
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        return Base64.getEncoder().encodeToString(imageBytes);
    }
}
