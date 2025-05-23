<%@ page import="model.UserModel" %><%--
  Created by IntelliJ IDEA.
  User: huyvu
  Date: 11/27/2024
  Time: 1:01 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

    <link rel="stylesheet" href="./styles/navigation.css">
    <link rel="stylesheet" href="./styles/global.css">
    <link rel="stylesheet" href="./styles/user.css">




</head>

<body>
<%
    UserModel user = (UserModel) session.getAttribute("user");
    String gmail = "Chưa có thông tin";
    if(user != null) {
        gmail = user.getGmail();
    }


%>
<header>
    <div id="gmailUser" style="display: none"><%= gmail %></div>
    <nav></nav>
    <div class="under-navigation">
        <div style="padding-top: 100px;" class="container">
            <div class="row">
                <div id="header-user" class="col-md-12">
                    <div class="row">
                        <div class="col-md-9">
                            <div>
                                <h3>
                                    <%=gmail%>
                                </h3>
                                <img id="rankCurrent" src="./assets/rankImage/none.png" alt="">
                                <div class="spendMore">
                                    <p>
                                        Chi tiêu thêm
                                        <span style="color: #2f5acf;">252.000đ đ </span>
                                        để lên hạng
                                    </p>
                                    <img id="rankNext" src="./assets/rankImage/silver.png" alt="">
                                </div>
                                <div id="lineSpend">
                                    <div class="lineSpend__progress">
                                    </div>
                                    <div style=" left: 0;" class="poinNone">
                                        <img src="./assets/rankImage/none.png" alt="">
                                    </div>
                                    <div style=" left: 33.3%;" class="poinNone">
                                        <img src="./assets/rankImage/silver.png" alt="">
                                    </div>
                                    <div style=" left: 66.6%;" class="poinNone">
                                        <img src="./assets/rankImage/gold.png" alt="">
                                    </div>
                                    <div style=" left: 99.9%;" class="poinNone">
                                        <img src="./assets/rankImage/platinum.png" alt="">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div id="header-left">
                                <div class="CoolCashFull">
                                    <p style="text-align: end;">Bạn đang có</p>
                                    <div class="CoolCash">
                                        <img src="./assets/user/mceclip6_39.png" alt="">
                                        <p>3.000 CoolCash</p>
                                    </div>
                                    <p></p>
                                </div>
                                <div id="reward">
                                    <div class="btn">
                                        <div class="text">
                                            <div class="">
                                                SirkRoad
                                            </div>
                                            <div class="">Rewards Hub</div>
                                        </div>
                                        <div class="icon">
                                            <i class="fa-solid fa-right-long"></i>
                                        </div>
                                    </div>
                                </div>


                            </div>


                        </div>

                    </div>
                </div>
            </div>
        </div>
        <div style="margin-top: 20px;" class="container">
            <div class="row">
                <div style="padding: 0;" class="col-md-3">
                    <div class="">
                        <div id="user__menu">
                            <div class="user__menu-item">
                                <div class="">
                                    Lịch sử đơn hàng
                                </div>
                                <div class="">
                                    <i class="fa-solid fa-chevron-right"></i>
                                </div>
                            </div>
                            <div class="user__menu-item">
                                <div class="">
                                    Chính sách và câu hỏi thường gặp
                                </div>
                                <div class="">
                                    <i class="fa-solid fa-chevron-right"></i>
                                </div>
                            </div>
                            <a href="${pageContext.request.contextPath}/SignOutController">
                                <div id="btnLogOut" class="user__menu-item-logOut">
                                    Đăng xuất
                                </div>
                            </a>
                        </div>
                    </div>
                </div>
                <div style="padding-right: 0;" class="col-md-9">
                    <div class="content-center">
                        <div class="infoPersonal">

                            <h2>
                                Thông tin tài khoản
                            </h2>
                            <div class="content-center__info">
                                <div class="content-center__info-item">
                                    <div class="content-center__info-item__title">
                                        <p>
                                            Họ và tên
                                        </p>
                                    </div>
                                    <div class="content-center__info-item__content">
                                        <p>
                                            Nguyễn Huy Vũ
                                        </p>
                                    </div>
                                </div>
                                <div class="content-center__info-item">
                                    <div class="content-center__info-item__title">
                                        <p>
                                            Số điện thoại
                                        </p>
                                    </div>
                                    <div class="content-center__info-item__content">
                                        <p>
                                            0987654321
                                        </p>
                                    </div>
                                </div>
                                <div class="content-center__info-item">
                                    <div class="content-center__info-item__title">
                                        <p>
                                            Giới tính
                                        </p>
                                    </div>
                                    <div class="content-center__info-item__content">
                                        <p>
                                            Nam
                                        </p>
                                    </div>
                                </div>
                                <div class="content-center__info-item">
                                    <div class="content-center__info-item__title">
                                        <p>
                                            Ngày tháng năm sinh
                                        </p>
                                    </div>
                                    <div class="content-center__info-item__content">
                                        <p>
                                            10/01/2003
                                        </p>
                                    </div>
                                </div>

                                <div class="content-center__info-item">
                                    <div class="content-center__info-item__title">
                                        <p>
                                            Địa chỉ
                                        </p>
                                    </div>
                                    <div class="content-center__info-item__content">
                                        <p>
                                            123, Đường 123, Quận 1, TP.HCM
                                        </p>
                                    </div>
                                </div>
                                <div class="">
                                    <button class="btnEditInfo">Chỉnh sửa thông tin cá nhân</button>
                                </div>
                            </div>
                        </div>
                        <div style="margin-top: 20px;" class="inforLogin">
                            <h2>
                                Thông tin đăng nhập
                            </h2>
                            <div class="content-center__info-item">
                                <div class="content-center__info-item__title">
                                    <p>
                                        Email
                                    </p>
                                </div>
                                <div class="content-center__info-item__content">
                                    <p> huyvu10012003@gmai.com</p>
                                </div>

                            </div>
                            <div class="content-center__info-item">
                                <div class="content-center__info-item__title">
                                    <p>
                                        Mật khẩu
                                    </p>
                                </div>
                                <div class="content-center__info-item__content">
                                    <p>********</p>
                                </div>
                            </div>
                            <div class="">
                                <button class="btnEditInfo">Chỉnh sửa thông tin cá nhân</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</header>


<footer></footer>

<script src="./components/navigation.js"></script>
<script src="./scripts/user.js?v=${System.currentTimeMillis()}"></script>
<script src="./components/footer.js?v=${System.currentTimeMillis()}"></script>




</body>

</html>
