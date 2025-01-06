<%--
  Created by IntelliJ IDEA.
  User: Van Tran
  Date: 12/26/2024
  Time: 2:55 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Document</title>
  <link rel="icon" type="image/svg" href="assets/logo2.svg">

  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

  <link rel="stylesheet" href="styles/slider.css">
  <link rel="stylesheet" href="styles/navigation.css">
  <link rel="stylesheet" href="styles/global.css">
  <link rel="stylesheet" href="styles/statusShoes.css">


</head>

<body>

<header>
  <nav></nav>
  <div class="container under-navigation">
    <div class="container mt-5">
      <div class="row justify-content-center">
        <div class="col-md-8">
          <h1 class="text-center mb-4 fw-bold">Quản lí đơn hàng</h1> <!-- Tiêu đề đã thêm vào đây -->
          <div class="status-tabs">

            <a href="#" class="status-tab" onclick="changeStatus(this)">Tất cả</a>
            <a href="#" class="status-tab" onclick="changeStatus(this)">Chờ thanh toán</a>
            <a href="#" class="status-tab" onclick="changeStatus(this)">Chờ giao hàng
              <a href="#" class="status-tab" onclick="changeStatus(this)">Vận chuyển <span
                      class="badge bg-danger">1</span></a> </a>
            <a href="#" class="status-tab" onclick="changeStatus(this)">Hoàn thành</a>
            <a href="#" class="status-tab" onclick="changeStatus(this)">Đã hủy</a>
            <a href="#" class="status-tab" onclick="changeStatus(this)">Trả hàng/Hoàn tiền</a>
          </div>
        </div>
      </div>

      <form id="supportForm">
        <div id="inputName" class="input-container">
          <input type="text" id="nameSupport" class="floating-input" placeholder=" " required>
          <label class="floating-label">Bạn có thể tìm kiếm theo tên sản phẩm hoặc ID đơn hàng</label>
          <i class="fa-solid fa-magnifying-glass" style="color: #bababa;"></i>
        </div>

      </form>
      <c:if test="${not empty listOrder}">
      <c:forEach var="item" items="${listOrder}">

      <div class="area-shoes">
        <div class="vh-10">
          <div class="container">
            <div class="row d-flex justify-content-center align-items-center">
              <div class="col">
                <div class="card mb-4">
                  <div class="card-body p-4">

                    <div class="row align-items-center">
                      <div class="col-md-2">
                        <h5>ID: 33892</h5>
                        <i class="fa-brands fa-shopify shopify-icon"
                           onclick="openLightbox()"></i>
                      </div>
                      <div class="col-md-2 d-flex justify-content-center">
                        <div>
                          <p class="small text-muted mb-4 pb-2">Ngày mua</p>
                          <p class="lead fw-normal mb-0">${item.orderDate}</p>
                        </div>
                      </div>
                      <div class="col-md-2 d-flex justify-content-center">
                        <div>
                          <p class="small text-muted mb-4 pb-2">Ngày nhận (dự kiến)</p>
                          <p class="lead fw-normal mb-0">
                            05-12-2024</p>
                        </div>
                      </div>
                      <div class="col-md-2 d-flex justify-content-center">
                        <div>
                          <p class="small text-muted mb-4 pb-2">Phương thức</p>
                          <p class="lead fw-normal mb-0">${item.deliveryName}</p>

                        </div>
                      </div>
                      <div class="col-md-2 d-flex justify-content-center">
                        <div>
                          <p class="small text-muted mb-4 pb-2">Tổng tiền</p>
                          <p class="lead fw-normal mb-0">${totalPriceFormat}</p>
                        </div>
                      </div>
                      <div class="col-md-2 d-flex justify-content-center">
                        <div>
                          <p class="small text-muted mb-4 pb-2">Thanh toán</p>
                          <p class="lead fw-normal mb-0 text-success">${item.paymentName}</p>
                        </div>
                      </div>
                    </div>

                  </div>
                </div>
                </c:forEach>
                </c:if>
                <div class="card mb-5">
                  <div class="card-body p-4">

                    <div class="float-end">
                      <p class="mb-0 me-5 d-flex align-items-center">
                        <span class="small text-muted me-2">Thành tiền:</span> <span
                              class="lead fw-normal">${item.totalPrice}</span>
                      </p>
                    </div>

                  </div>
                </div>

                <div class="d-flex justify-content-end">
                  <button type="button" data-mdb-button-init data-mdb-ripple-init
                          class="btn btn-light btn-lg me-2">Liên hệ với Silk Road</button>
                  <button type="button" data-mdb-button-init data-mdb-ripple-init
                          class="btn btn-light btn-lg">Hủy đơn hàng</button>
                </div>

              </div>
            </div>
          </div>
        </div>
      </div>


    </div>
  </div>
</header>

<!-- Lightbox -->
<div id="orderLightbox" class="lightbox" style="display: none;">
  <div class="lightbox-content">
    <span class="close" onclick="closeLightbox()">&times;</span>
    <h2>Chi tiết đơn hàng</h2>
    <ul id="orderItemsList">
      <div class="row align-items-center">
        <div class="col-md-2">
          <img class="shopify-img" src="assets/shoeMens/cocao.png" alt="">
        </div>
        <div class="col-md-2 d-flex justify-content-center">
          <div>
            <p class="small text-muted mb-4 pb-2">Giày thể thao</p>
            <p class="lead fw-normal mb-0">Fashionista</p>
          </div>
        </div>
        <div class="col-md-2 d-flex justify-content-center">
          <div>
            <p class="small text-muted mb-4 pb-2">Kích cỡ</p>
            <p class="lead fw-normal mb-0">
              43</p>
          </div>
        </div>
        <div class="col-md-2 d-flex justify-content-center">
          <div>
            <p class="small text-muted mb-4 pb-2">Số lượng</p>
            <p class="lead fw-normal mb-0">1</p>

          </div>
        </div>
        <div class="col-md-2 d-flex justify-content-center">
          <div>
            <p class="small text-muted mb-4 pb-2">Giá</p>
            <p class="lead fw-normal mb-0">599.000</p>
          </div>
        </div>
        <div class="col-md-2 d-flex justify-content-center">
          <div>
            <p class="small text-muted mb-4 pb-2">Màu</p>
            <p class="lead fw-normal mb-0">Xanh</p>
          </div>
        </div>
      </div>
      <div style="padding-top: 30px;" class="container-fluid">
        <div class="row line"></div>
      </div>
      <div class="row align-items-center">
        <div class="col-md-2">
          <img class="shopify-img" src="assets/shoeMens/nike.png" alt="">
        </div>
        <div class="col-md-2 d-flex justify-content-center">
          <div>
            <p class="small text-muted mb-4 pb-2">Giày thể thao</p>
            <p class="lead fw-normal mb-0">Fashionista</p>
          </div>
        </div>
        <div class="col-md-2 d-flex justify-content-center">
          <div>
            <p class="small text-muted mb-4 pb-2">Kích cỡ</p>
            <p class="lead fw-normal mb-0">
              43</p>
          </div>
        </div>
        <div class="col-md-2 d-flex justify-content-center">
          <div>
            <p class="small text-muted mb-4 pb-2">Số lượng</p>
            <p class="lead fw-normal mb-0">1</p>

          </div>
        </div>
        <div class="col-md-2 d-flex justify-content-center">
          <div>
            <p class="small text-muted mb-4 pb-2">Giá</p>
            <p class="lead fw-normal mb-0">500.000</p>
          </div>
        </div>
        <div class="col-md-2 d-flex justify-content-center">
          <div>
            <p class="small text-muted mb-4 pb-2">Màu sắc</p>
            <p class="lead fw-normal mb-0 ">Vàng</p>
          </div>
        </div>
      </div>
      <div style="padding-top: 30px;" class="container-fluid">
        <div class="row line"></div>
      </div>
      <div class="row align-items-center">
        <div class="col-md-2">
          <img class="shopify-img" src="assets/shoes/2.png" alt="">
        </div>
        <div class="col-md-2 d-flex justify-content-center">
          <div>
            <p class="small text-muted mb-4 pb-2">Giày thể thao</p>
            <p class="lead fw-normal mb-0">Converse</p>
          </div>
        </div>
        <div class="col-md-2 d-flex justify-content-center">
          <div>
            <p class="small text-muted mb-4 pb-2">Kích cỡ</p>
            <p class="lead fw-normal mb-0">
              43</p>
          </div>
        </div>
        <div class="col-md-2 d-flex justify-content-center">
          <div>
            <p class="small text-muted mb-4 pb-2">Số lượng</p>
            <p class="lead fw-normal mb-0">1</p>

          </div>
        </div>
        <div class="col-md-2 d-flex justify-content-center">
          <div>
            <p class="small text-muted mb-4 pb-2">Giá</p>
            <p class="lead fw-normal mb-0">499.000</p>
          </div>
        </div>
        <div class="col-md-2 d-flex justify-content-center">
          <div>
            <p class="small text-muted mb-4 pb-2">Màu sắc</p>
            <p class="lead fw-normal mb-0 ">Đen</p>
          </div>
        </div>
      </div>




    </ul>
  </div>
</div>


<footer>
</footer>
<script src="components/navigation.js"></script>
<script src="components/footer.js"></script>
<script src="scripts/scroll.js"></script>
<script src="scripts/user.js"></script>




</body>

</html>