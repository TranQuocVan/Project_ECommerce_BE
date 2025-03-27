<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng nhập Silk Road</title>
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
<%
    String res = request.getAttribute("res")+"";
    res = (res.equals("null")) ? "" : res;
%>
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
                        <h1 id="titleLogin">Vui lòng nhập mã gửi qua gmail</h1>
                        <form id="loginForm" action="GmailAuthenticationController" method="post">
                            <div id="inputName" class="input-container">
                                <input name="authCode" type="text" id="nameLogin" class="floating-input" placeholder=" " required>
                                <label  class="floating-label">Nhập mã xác thực từ gmail</label>
                            </div>


                            <div class="error">
                                <span id="errorLogin"> <%=res%></span>
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

</body>

</html>