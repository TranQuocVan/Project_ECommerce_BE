<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lí đơn hàng</title>
    <link rel="icon" type="image/svg" href="assets/logo2.svg">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" href="styles/sder.css">
    <link rel="stylesheet" href="styles/navigation.css">
    <link rel="stylesheet" href="styles/global.css">
    <link rel="stylesheet" href="styles/statusShoes.css?v=${System.currentTimeMillis()}">
</head>

<body>
<header>
    <nav></nav>
    <div class="container under-navigation">
        <div class="container mt-5">
            <div class="row justify-content-center">
                <div class="col-md-8">
                    <h1 class="text-center mb-4 fw-bold">Quản lí đơn hàng</h1>
                    <div class="status-tabs">
                        <a href="#" class="status-tab" onclick="changeStatus(this)">Tất cả</a>
                        <a href="#" class="status-tab" onclick="changeStatus(this)">Chờ thanh toán</a>
                        <a href="#" class="status-tab" onclick="changeStatus(this)">Chờ giao hàng</a>
                        <a href="#" class="status-tab" onclick="changeStatus(this)">
                            Vận chuyển <span class="badge bg-danger">1</span>
                        </a>
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
                        <div class="container">
                            <div class="row d-flex justify-content-center align-items-center">
                                <div class="col">
                                    <div class="card mb-4">
                                        <div class="card-body p-4">
                                            <div class="row align-items-center">
                                                <div class="col-md-2">
                                                    <div class="text-center">ID: ${item.id}</div>
                                                    <i class="fa-brands fa-shopify shopify-icon" onclick="openLightbox()"></i>
                                                </div>
                                                <div class="col-md-2 d-flex justify-content-center">
                                                    <div>
                                                        <div class="text-muted">Ngày mua</div>
                                                        <div class="fw-normal mb-0">${item.orderDate}</div>
                                                    </div>
                                                </div>

                                                <div class="col-md-2 d-flex justify-content-center">
                                                    <div>
                                                        <div class="text-muted">Phương thức</div>
                                                        <div class="fw-normal mb-0">${item.deliveryName}</div>
                                                    </div>
                                                </div>
                                                <div class="col-md-2 d-flex justify-content-center">
                                                    <div>
                                                        <div class="text-muted">Thanh toán</div>
                                                        <div class="fw-normal mb-0 text-success">${item.paymentName}</div>
                                                    </div>
                                                </div>
                                                <div class="col-md-2 d-flex justify-content-center">
                                                    <div>
                                                        <div class="text-muted">Tổng tiền</div>
                                                        <div class="fw-normal mb-0">
                                                            <fmt:formatNumber value="${item.totalPrice * 1000}" type="number" groupingUsed="true" minFractionDigits="0" />₫
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-md-2 d-flex justify-content-center">
                                                    <div>
                                                        <form action=ViewOrderProducts method="post">
                                                            <input type="hidden" name="id" value="${item.id}"></input>
                                                            <button class="btn btn-primary viewOrderProduct" type="submit">Xem chi tiết đơn hàng</button>
                                                        </form>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </c:if>
        </div>
    </div>
</header>

<footer></footer>
<script src="components/navigation.js"></script>
<script src="components/footer.js"></script>
<script src="scripts/scroll.js"></script>
<script src="scripts/user.js"></script>
</body>

</html>
