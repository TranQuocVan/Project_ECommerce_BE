<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

<link rel="stylesheet" href="styles/navigation.css">
<link rel="stylesheet" href="styles/global.css?v=${System.currentTimeMillis()}">
<link rel="stylesheet" href="styles/productDetails.css?v=${System.currentTimeMillis()}">

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Product Details</title>
</head>
<body>
<header>
    <nav></nav>
    <div class="under-navigation">
        <div class="container mt-5">
            <div class="row">
                    <div class="col-md-12 mt-5">
                        <div class="title">
                            <h3 class="fw-bold">Chi tiết sản phẩm</h3>
                        </div>
                    </div>
                    <!-- Bắt đầu phần hiển thị sản phẩm -->
                    <div class="col-md-1">
                        <div class="images">
                            <c:if test="${not empty product}">
                                <c:forEach var="color" items="${product.colorModels}" varStatus="status">
                                    <div class="color-item-img ${status.index == 0 ? 'active' : ''}">
                                        <!-- Hiển thị hình ảnh -->
                                        <c:if test="${not empty color.imageModels}">
                                            <c:forEach var="image" items="${color.imageModels}">
                                                <img src="data:image/jpeg;base64,${image.imageBase64}" alt="Product Image" width="150">
                                            </c:forEach>
                                        </c:if>
                                        <c:if test="${empty color.imageModels}">
                                            <img src="assets/default/noImageAvailable.jpg" alt="No Image Available">
                                        </c:if>
                                    </div>
                                </c:forEach>
                            </c:if>
                            <c:if test="${empty product}">
                                <p>No product available</p>
                            </c:if>
                        </div>
                    </div>
                    <div class="col-md-5 imgMain">
                        <div id="img_main"></div>
                    </div>
                    <div style="padding-left: 60px" class="col-md-6">
                        <c:if test="${not empty product}">
                            <!-- Hiển thị thông tin sản phẩm -->
                            <div class="name">${product.name}</div>
                            <div class="original-price">  ${product.price}đ</div>
                            <div class="price">
                                <c:choose>
                                    <c:when test="${product.discount > 0}">
                                        <div class="price_sale">${product.price - (product.price * product.discount / 100)}đ</div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="price_sale">${product.price}đ</div>
                                    </c:otherwise>
                                </c:choose>
                                <div class="price_discount">${product.discount}%</div>
                            </div>


                            <!-- Hiển thị các màu -->
                            <h3 class="selectedColorText">Màu sắc:</h3>
                            <div class="color-item">
                                <c:forEach var="color" items="${product.colorModels}" varStatus="status">
                                    <div data-color-name = ${color.name} class="color-box" style="background-color: ${color.hexCode};" ></div>
                                </c:forEach>
                            </div>
                            <h3 class="selectedSizeText">Kích thước:</h3>
                            <c:forEach var="color" items="${product.colorModels}" varStatus="status">
                                <div class="size ${status.index == 0 ? 'active' : ''}">
                                    <c:forEach var="size" items="${color.sizeModels}">
                                        <div class="size-item" data-size-id =${size.id} >${size.size}</div>
                                    </c:forEach>
                                </div>
                            </c:forEach>
                        </c:if>
                        <div class="btnIncreaseDecrease d-flex justify-content-between align-items-center">
                            <div >
                                <i class="fas fa-minus" onclick="decreaseQuantity()"></i>
                                <span id="quantity" class="fw-bold">1</span>
                                <i class="fas fa-plus"></i>
                            </div>

                            <div style="flex: 1;" class="col-auto">
                                <button class="addToCard" >
                                    <i class="fa-solid fa-cart-shopping mx-2"></i>
                                    <span class="fw-bold">Thêm vào giỏ hàng</span>
                                </button>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>

</header>

<footer></footer>

<!-- Các script cần thiết -->
<script src="components/footer.js?v=${System.currentTimeMillis()}"></script>
<script src="components/navigation.js?v=${System.currentTimeMillis()}"></script>
<script type="module" src="scripts/productDetails.js?v=${System.currentTimeMillis()}"></script>
</body>
</html>
