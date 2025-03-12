<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Sign In</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng nhập Silk Road</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=SF+Pro+Text&display=swap" rel="stylesheet">


    <link rel="stylesheet" href="styles/login.css?v=${System.currentTimeMillis()}">
    <link rel="stylesheet" href="styles/navigation.css?v=${System.currentTimeMillis()}">
    <link rel="stylesheet" href="styles/global.css?v=${System.currentTimeMillis()}">
</head>
<body>
<%

    String gmail = session.getAttribute("gmail")+"";
    gmail = (gmail.equals("null")) ? "": gmail;

    String password = session.getAttribute("password")+"";
    password = (password.equals("null")) ? "" : password;

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
                    <h1 id="titleLogin">Đăng ký vào Silk Road</h1>

                    <form id="loginForm" action="SignUpController" method="post">
                        <div id="inputName" class="input-container">
                            <input name="gmail" type="text" id="nameLogin" class="floating-input" placeholder=" " required value=<%=gmail%>>
                            <label for="nameLogin"  class="floating-label">Vui lòng điền gmail của bạn</label>
                            <i id="icon" class="fa-solid fa-circle-right"></i>
                        </div>

                        <div class="error ">
                            <span id="errorLogin"> <%=res%></span>
                        </div>

                        <div style="display: none;" id="inputPass" class="input-container">
                            <input name="password" type="password" id="password" class="floating-input" placeholder=" " required value=<%=password%>>
                            <label for="password" class="floating-label">Vui lòng điền mật khẩu của bạn</label>

                        </div>
                        <button id="btnLogin" type="submit" class="btn btn-primary">
                           Lấy mã xác từ gmail
                        </button>
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