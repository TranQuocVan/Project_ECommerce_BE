<%@ page import="model.UserModel" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

    <link rel="stylesheet" href="styles/slider.css">
    <link rel="stylesheet" href="styles/navigation.css">
    <link rel="stylesheet" href="styles/index.css">
    <link rel="stylesheet" href="styles/global.css">
    <link rel="stylesheet" href="styles/lightBox.css">
</head>
<body>

<%
    String isLogin = session.getAttribute("isLogin")+"";
    isLogin = (isLogin.equals("null")) ? "": isLogin;

    UserModel user = (UserModel) session.getAttribute("user");
    String role="";
    if(user != null){
        role = user.getRole();
    }
%>

<header>
    <nav></nav>
    <div class="under-navigation">
        <div id="isLogin" style="position: absolute; color: transparent;background: transparent" ><%=isLogin%></div>
        <div id="role" style="position: absolute; color: transparent;background: transparent" ><%=role%></div>
        <div style="padding-top: 100px;" class="container">
            <div class="row mb-5">
                <div class="col-md-12">
                        <span class="titleHeader" style="
                           ">Cửa Hàng.</span>
                    <span style="color: #6e6e73;" class="titleSubHeader">Cách tốt nhất để <br>
                            mua sản phẩm bạn thích.
                    </span>
                </div>
            </div>
        </div>

        <div class="container">
            <div class="row" id="best-sale">
                <!-- Các item -->
                <div class="col-6 col-md-4 col-lg-2 mb-4">
                    <div class="best-saler-item text-center">
                        <a href="men'sSportsShoes.html">
                            <img src="assets/bestSellerHome/image1.png" alt="Men's Sports Shoes" class="img-fluid">
                        </a>
                        <span>Giày thể thao nam</span>
                    </div>
                </div>
                <div class="col-6 col-md-4 col-lg-2 mb-4">
                    <div class="best-saler-item text-center">
                        <img src="assets/bestSellerHome/image2.png" alt="" class="img-fluid">
                        <span>Giày thể thao nữ</span>
                    </div>
                </div>
                <div class="col-6 col-md-4 col-lg-2 mb-4">
                    <div class="best-saler-item text-center">
                        <img src="assets/bestSellerHome/image3.png" alt="" class="img-fluid">
                        <span>Giày cao gót nữ</span>
                    </div>
                </div>
                <div class="col-6 col-md-4 col-lg-2 mb-4">
                    <div class="best-saler-item text-center">
                        <img src="assets/bestSellerHome/image4.png" alt="" class="img-fluid">
                        <span>Giày công sở nam</span>
                    </div>
                </div>
                <div class="col-6 col-md-4 col-lg-2 mb-4">
                    <div class="best-saler-item text-center">
                        <img src="assets/bestSellerHome/image6.png" alt="" class="img-fluid">
                        <span>Dép nam</span>
                    </div>
                </div>
                <div class="col-6 col-md-4 col-lg-2 mb-4">
                    <div class="best-saler-item text-center">
                        <img src="assets/bestSellerHome/image7.png" alt="" class="img-fluid">
                        <span>Dép nữ</span>
                    </div>
                </div>
            </div>
        </div>
        <div style="padding-top: 100px;" class="container">
            <div class="row">
                <div class="col-md-12">
                    <span class="titleHeaderMin" >Ưu đãi đặc biệt. </span>
                    <span style="color: #6e6e73;" class="titleSubHeaderMin">Chỉ diễn ra trong thời
                            gian khuyến mãi.</span>
                </div>
            </div>
        </div>

        <div style="padding-top: 70px;" class="container-fluid">
            <div class="row">

                <div class="col-md-12">
                    <div id="slide">
                        <div class="item" data-bs-toggle="modal" data-bs-target="#lightboxModal">
                            <img src="assets/homeImages/1.png" alt="">
                        </div>
                        <div class="item" data-bs-toggle="modal" data-bs-target="#lightboxModal">
                            <img src="assets/homeImages/2.png" alt="">
                        </div>
                        <div class="item" data-bs-toggle="modal" data-bs-target="#lightboxModal" data-price="700.000đ">
                            <img src="assets/homeImages/3.png" alt="">
                        </div>
                        <div class="item" data-bs-toggle="modal" data-bs-target="#lightboxModal" data-price="800.000đ">
                            <img src="assets/homeImages/4.png" alt="">
                        </div>
                        <div class="item" data-bs-toggle="modal" data-bs-target="#lightboxModal" data-price="900.000đ">
                            <img src="assets/homeImages/5.png" alt="">
                        </div>
                        <div class="item" data-bs-toggle="modal" data-bs-target="#lightboxModal" data-price="1000.000đ">
                            <img src="assets/homeImages/6.png" alt="">
                        </div>
                        <div class="item" data-bs-toggle="modal" data-bs-target="#lightboxModal" data-price="1100.000đ">
                            <img src="assets/homeImages/1.png" alt="">
                        </div>

                        <button class="btnSlide" id="next">
                            <i class="fa-solid fa-chevron-right"></i>
                        </button>
                        <button class="btnSlide" style="display: none;" id="prev">
                            <i class="fa-solid fa-chevron-left"></i>
                        </button>

                    </div>
                </div>
            </div>
        </div>


        <div style="padding-top: 100px;" class="container">
            <div class="row">
                <div class="col-md-12">
                    <span class="titleHeaderMin" >Lựa chọn cho dịp lễ. </span>
                    <span style="color: #6e6e73;" class="titleSubHeaderMin">Chỉ diễn ra trong thời
                            gian khuyến mãi.</span>
                </div>
            </div>
        </div>

        <div style="padding-top: 70px;" class="container-fluid">
            <div class="row">

            </div>
        </div>


        <div style="padding-top: 70px;" class="container-fluid">
            <div class="row">

                <div class="col-md-12">
                    <div id="slide2">

                        <div class="item" data-bs-toggle="modal" data-bs-target="#lightboxModal2" data-price="500.000đ" >
                            <img src="assets/homeImages/7.png" alt="">
                        </div>
                        <div class="item" data-bs-toggle="modal" data-bs-target="#lightboxModal2" data-price="799.000đ">
                            <img src="assets/homeImages/8.png" alt="">
                        </div>
                        <div class="item" data-bs-toggle="modal" data-bs-target="#lightboxModal2" data-price="900.000đ">
                            <img src="assets/homeImages/9.png" alt="">
                        </div>
                        <div class="item" data-bs-toggle="modal" data-bs-target="#lightboxModal2" data-price="300.000đ">
                            <img src="assets/homeImages/10.png" alt="">
                        </div>
                        <div class="item" data-bs-toggle="modal" data-bs-target="#lightboxModal2" data-price="799.000đ">
                            <img src="assets/homeImages/11.png" alt="">
                        </div>
                        <div class="item" data-bs-toggle="modal" data-bs-target="#lightboxModal2">
                            <img src="assets/homeImages/12.png" alt="" data-price="999.000đ">
                        </div>
                        <div class="item" data-bs-toggle="modal" data-bs-target="#lightboxModal" >
                            <img src="assets/homeImages/9.png" alt="">
                        </div>

                        <button class="btnSlide" id="next2">
                            <i class="fa-solid fa-chevron-right"></i>
                        </button>
                        <button class="btnSlide" style="display: none;" id="prev2">
                            <i class="fa-solid fa-chevron-left"></i>
                        </button>

                    </div>
                </div>
            </div>
        </div>
        <div class="container">
            <div class="row">
                <div id="scroll" style="position: relative;  margin-top: 70px;" class="container">
                    <div class="row">
                        <div class="col-md-12">
                            <h3 style="font-weight: 700; padding: 0 10px;">Hỗ trợ tại đây.
                                <span style="color: #6e6e73;">Ngay khi bạn muốn,
                                        mọi việc bạn cần.</span>
                            </h3>

                            <div style="padding-top: 10px;" class="slider">

                                <div class="form">
                                    <div class="item support">
                                        <div class="content ">
                                            <div style="position: absolute; top: 5%; font-size: 28px;font-weight: 700; left: 50%; transform: translateX(-50%); width: 90%;"
                                                 class="header">
                                                <p>Dịch vụ và hỗ trợ. Chúng tôi luôn sẵn sàng hỗ trợ.</p>
                                            </div>
                                            <img src="assets/supporImgs/store-card-50-genius-202108.jpg" alt="">
                                        </div>
                                    </div>
                                    <div class="item support">
                                        <div class="content ">
                                            <div style="position: absolute; top: 10%; font-size: 28px;font-weight: 700; left: 50%; transform: translateX(-50%); width: 90%;"
                                                 class="header">
                                                <p>Mua hàng với tư vấn trực tiếp từ Chuyên Gia trực tuyến. </p>
                                            </div>
                                            <img src="assets/supporImgs/store-card-50-specialist-help-202309.jpg"
                                                 alt="">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</header>

<!--Light box1 -->
<div class="modal fade" id="lightboxModal" tabindex="-1" aria-labelledby="lightboxModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="lightbox-content">
                    <!-- Nút Mua ngay -->
                    <div class="buy-button-container">
                        <button class="mx-auto d-flex justify-content-center align-items-center rounded-pill order-button cart-box" style="width: 250px;">
                            <i class="fa-solid fa-eye mx-2"></i>                                <span class="fw-bold">Xem ngay</span>
                        </button>
                    </div>


                    <!-- Hiển thị ảnh -->
                    <div class="modal-image-container">
                        <img id="modalImage" src="" class="img-fluid" alt="Full image">
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<!--Light box2 -->

<div class="modal fade" id="lightboxModal2" tabindex="-1" aria-labelledby="lightboxModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="lightbox-content">
                    <!-- Nút Mua ngay -->
                    <div class="buy-button-container2">
                        <button class="mx-auto d-flex justify-content-center align-items-center rounded-pill order-button cart-box" style="width: 250px;">
                            <i class="fa-solid fa-cart-shopping mx-2"></i>
                            <span class="fw-bold">Mua ngay</span>
                        </button>
                    </div>


                    <!-- Hiển thị ảnh -->
                    <div class="modal-image-container">
                        <img id="modalImage2" src="" class="img-fluid" alt="Full image">
                        <div class="modal-price" id="modalPrice2">Giá: </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<footer></footer>

<script src="components/footer.js?v=${System.currentTimeMillis()}"></script>
<script src="components/navigation.js?v=${System.currentTimeMillis()}"></script>
<script src="scripts/scroll.js?v=${System.currentTimeMillis()}"></script>
<script src="scripts/lightBox_index.js?v=${System.currentTimeMillis()}"></script>




</body>


</html>