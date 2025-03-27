<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Kết quả giao dịch</title>
    <link rel="icon" type="image/svg" href="assets/logo2.svg">
<%--    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" --%>
<%--          integrity="sha512-z3gLpd7yknf1YoNbCzqRKc4qyor8gaKU1qmn+CShxbuBusANI9QpRohGBreCFkKxLhei6S9CQXFEbbKuqLg0DA==" --%>
<%--          crossorigin="anonymous" referrerpolicy="no-referrer" />--%>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

    <link rel="stylesheet" href="styles/navigation.css">
    <link rel="stylesheet" href="styles/global.css?v=${System.currentTimeMillis()}">
</head>

<body>
<%--    <nav></nav>--%>
<%--    <div class="under-navigation">--%>
<%--        <div style="padding-top: 100px;" class="container">--%>

<%--        </div>--%>
<%--    </div>--%>


    <nav></nav>
    <div class="under-navigation">
        <div style="padding-top: 100px; padding-bottom: 100px" class="container">
            <div style="margin-top: 50px; text-align: center;">
                <div>
                    <img src="https://cdn2.cellphones.com.vn/insecure/rs:fill:150:0/q:90/plain/https://cellphones.com.vn/media/wysiwyg/Review-empty.png"
                         alt="Transaction Status"
                         style="width: 120px; height: 120px; margin-bottom: 20px;">
                </div>

                <!-- Giao dịch thành công -->
                <c:if test="${transResult}">
                    <div>
                        <h3 style="font-weight: bold; color: #28a745;">
                            Bạn đã giao dịch thành công!
                            <i class="fas fa-check-circle"></i>
                        </h3>
                        <p style="font-size: 18px; margin-top: 15px;">Vui lòng để ý số điện thoại của nhân viên tư vấn:</p>
                        <strong style="color: red; font-size: 24px;">0383456xxx</strong>

                        <a href="http://localhost:8080/Shoe_war_exploded/OrderController"
                           style="font-size: 24px; margin-top: 15px; text-decoration: underline;">
                            Kiểm tra đơn hàng của bạn !
                        </a>
                    </div>
                </c:if>

                <!-- Giao dịch thất bại -->
                <c:if test="${transResult == false}">
                    <div>
                        <h3 style="font-weight: bold; color: #dc3545;">
                            Đơn hàng giao dịch thất bại!
                        </h3>
                        <p style="font-size: 18px; margin-top: 15px;">Cảm ơn quý khách đã dùng dịch vụ của chúng tôi.</p>
                        <p style="font-size: 18px;">Liên hệ tổng đài để được tư vấn:</p>
                        <strong style="color: red; font-size: 24px;">0383456xxx</strong>
                        <a href="http://localhost:8080/Shoe_war_exploded/OrderController"
                           style="font-size: 24px; margin-top: 15px; text-decoration: underline;">
                            Kiểm tra đơn hàng của bạn !
                        </a>
                    </div>
                </c:if>

                <!-- Đang xử lý giao dịch -->
                <c:if test="${transResult == null}">
                    <div>
                        <h3 style="font-weight: bold; color: #ffc107;">
                            Chúng tôi đã tiếp nhận đơn hàng, xin chờ quá trình xử lý!
                        </h3>
                        <p style="font-size: 18px; margin-top: 15px;">Vui lòng để ý số điện thoại của nhân viên tư vấn:</p>
                        <strong style="color: red; font-size: 24px;">0383456xxx</strong>
                        <a href="http://localhost:8080/Shoe_war_exploded/OrderController"
                           style="font-size: 24px; margin-top: 15px; text-decoration: underline;">
                            Kiểm tra đơn hàng của bạn !
                        </a>
                    </div>
                </c:if>
            </div>
        </div>
    </div>

    <footer></footer>
    <script src="components/footer.js?v=${System.currentTimeMillis()}"></script>
    <script src="components/navigation.js?v=${System.currentTimeMillis()}"></script>
</body>
</html>
