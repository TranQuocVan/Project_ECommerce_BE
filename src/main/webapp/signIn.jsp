<%--
  Created by IntelliJ IDEA.
  User: huyvu
  Date: 11/26/2024
  Time: 2:12 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng nhập Silk Road</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=SF+Pro+Text&display=swap" rel="stylesheet">


    <link rel="stylesheet" href="styles/login.css">
    <link rel="stylesheet" href="styles/navigation.css">
    <link rel="stylesheet" href="styles/global.css">

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
        </div>
        <div class="row justify-content-center" style="padding-top: 70px;">
            <div class="col-md-6">
                <div class="login-container text-center p-4 ">
                    <h1 id="titleLogin">Đăng nhập vào Silk Road</h1>
                    <form id="loginForm"  action="SignInController" method="post">

                        <div id="inputName" class="input-container">
                            <input name="gmail" type="text" id="nameLogin" class="floating-input" placeholder=" " required>
                            <label  class="floating-label">Vui lòng điền tài khoản của bạn</label>
                            <i id="icon" class="fa-solid fa-circle-right"></i>
                        </div>
                        <div style="display: none;" id="inputPass" class="input-container">
                            <input name="password" type="text" id="password" class="floating-input" placeholder=" " required>
                            <label for="password" class="floating-label">Vui lòng điền mật khẩu của bạn</label>

                        </div>

                        <button id="btnLogin" type="submit" class="btn btn-primary">
                            Đăng nhập
                        </button>

                        <div class="error">
                            <span id="errorLogin"> <%=res%></span>
                        </div>


                        <div id="alterClick">
                            <div style="padding: 0;" class="form-check my-3">
                                <input type="checkbox" class="checkbox">
                                <label class="form-check-label" >Lưu tôi</label>
                            </div>
                        </div>

                        <div class="links">
                            <a style="color: #06c;" href="#">Bạn đã quên mật khẩu ?</a>
                            <a href="#">Bạn không có Tài khoản?
                                <span style="color: #06c;">Tạo tài khoản của bạn ngay
                                        bây giờ.
                                </span>
                            </a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</header>
<footer></footer>

<script src="components/footer.js?v=${System.currentTimeMillis()}"></script>
<script src="components/navigation.js?v=${System.currentTimeMillis()}"></script>
<script src="scripts/login.js?v=${System.currentTimeMillis()}"></script>

</body>

</html>