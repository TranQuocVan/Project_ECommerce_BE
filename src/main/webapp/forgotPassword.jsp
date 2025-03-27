<%--
  Created by IntelliJ IDEA.
  User: huyvu
  Date: 3/24/2025
  Time: 1:28 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quên mật khẩu</title>
    <link rel="icon" type="image/svg" href="assets/logo2.svg">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

    <link rel="stylesheet" href="styles/login.css?v=${System.currentTimeMillis()}">
    <link rel="stylesheet" href="styles/loginWithAuthCode.css?v=${System.currentTimeMillis()}">
    <link rel="stylesheet" href="styles/navigation.css?v=${System.currentTimeMillis()}">
    <link rel="stylesheet" href="styles/global.css?v=${System.currentTimeMillis()}">

</head>

<body>

<header>
    <nav></nav>
    <div class="under-navigation">
        <div style="padding-top: 70px;" class="container">
            <div class="row">
                <div class="col-md-12">
                    <span class="titleHeader">Đăng nhập để thanh toán nhanh hơn.</span>
                </div>
            </div>
            <div class="row justify-content-center" style="padding-top: 70px;">
                <div class="col-md-6">
                    <div class="login-container text-center p-4 ">
                        <h1 id="titleLogin">Quên mật khẩu</h1>
                        <form id="forgotPasswordForm" action="ForgotPassword" method="post">
                            <div id="inputName" class="input-container">
                                <input name="email" type="text" id="nameLogin" class="floating-input" placeholder=" " required>
                                <label class="floating-label">Vui lòng nhập email của bạn</label>
                            </div>

                            <button id="btnAccept" type="submit" class="btn btn-primary">
                                Xác nhận
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</header>
<footer></footer>

<script src="components/navigation.js?v=${System.currentTimeMillis()}"></script>
<script src="components/footer.js?v=${System.currentTimeMillis()}"></script>
<script>
    document.addEventListener("DOMContentLoaded", function () {
        document.getElementById("forgotPasswordForm").addEventListener("submit", async function (event) {
            event.preventDefault(); // Prevent default form submission

            let emailInput = document.getElementById("nameLogin");
            let email = emailInput.value.trim();

            if (!validateEmail(email)) {
                alert("Email không hợp lệ. Vui lòng nhập lại!");
                emailInput.focus();
                return;
            }

            try {
                let response = await fetch("ForgotPasswordController", {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify({ email }),
                });

                let data = await response.json();
                alert(data.success ? "Vui lòng vào gmail để đổi mật khẩu" +
                    "" : "Lỗi: " + data.message);
            } catch (error) {
                console.error("Lỗi:", error);
                alert("Có lỗi xảy ra, vui lòng thử lại!");
            }
        });

        function validateEmail(email) {
            return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
        }
    });

</script>
</body>

</html>