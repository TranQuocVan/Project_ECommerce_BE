package service.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;

public class ReaderRequest {


    public String readRequestBody(HttpServletRequest request, HttpServletResponse response) throws IOException {
        StringBuilder sb = new StringBuilder();

        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        catch (IOException e) {
            response.getWriter().write("{\"status\":\"error\",\"message\":\"Failed to read request body\"}");
            return null;
        }

        return sb.toString();
    }
    public boolean checkRequestBodyExistence(String requestBody) {
        return requestBody != null && !requestBody.isEmpty();
    }
}
