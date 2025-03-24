<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    // Lấy token từ URL
    String token = request.getParameter("token");

    // Nếu token không có, chuyển hướng về trang lỗi
    if (token == null || token.trim().isEmpty()) {
        response.sendRedirect("error.jsp?message=InvalidOrExpiredToken");
        return;
    }
%>

<html>
<head>
    <title>Đặt lại mật khẩu</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

    <link rel="stylesheet" href="styles/navigation.css">
    <link rel="stylesheet" href="styles/global.css?v=${System.currentTimeMillis()}">
    <link rel="stylesheet" href="styles/orderDetails.css?v=${System.currentTimeMillis()}">
</head>
<body>
    <nav></nav>
    <div class="under-navigation">
        <div style="padding-top: 70px;" class="container">
            <h2 class="text-center">Đặt lại mật khẩu</h2>
            <form id="resetPasswordForm">
                <input type="hidden" id="token" value="<%= token %>">
                <div class="mb-3">
                    <label for="password" class="form-label">Mật khẩu mới:</label>
                    <input type="password" id="password" class="form-control" required>
                    <small class="text-muted">Mật khẩu phải có ít nhất 8 ký tự, bao gồm chữ hoa, chữ thường và số.</small>
                </div>
                <div class="mb-3">
                    <label for="confirmPassword" class="form-label">Xác nhận mật khẩu:</label>
                    <input type="password" id="confirmPassword" class="form-control" required>
                </div>
                <button type="submit" class="btn btn-primary w-100">Xác nhận</button>
            </form>
        </div>

    </div>



    <footer></footer>

    <!-- Các script cần thiết -->
    <script src="components/footer.js?v=${System.currentTimeMillis()}"></script>
    <script src="components/navigation.js?v=${System.currentTimeMillis()}"></script>
    <script src="scripts/authScript/resetPassword.js?v=${System.currentTimeMillis()}"></script>
</body>
</html>
