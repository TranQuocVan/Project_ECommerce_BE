<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

    <link rel="stylesheet" href="styles/navigation.css">
    <link rel="stylesheet" href="styles/global.css?v=${System.currentTimeMillis()}">
    <link rel="stylesheet" href="styles/orderDetails.css?v=${System.currentTimeMillis()}">
</head>
<body>

<header>
    <nav></nav>
    <div class="under-navigation">
        <div class="container mt-5">
            <div class="row">
                <c:if test="${not empty orderProducts}">
                    <h3 class="mt-5 text-center">Order Detail ${orderProducts.id} </h3>
                    <div class="col-md-6 col-lg-6 col-sm-12">
                        <div class="order-summary">
                            <h4>Thông tin đơn hàng</h4>
                            <p><strong>Ngày đặt hàng:</strong> ${orderProducts.orderDate}</p>
                            <p><strong>Tổng số tiền:</strong>
                                <fmt:formatNumber value="${orderProducts.totalPrice}" type="number" maxFractionDigits="0" groupingUsed="true"/>₫
                            </p>
                            <p><strong>Phương thức thanh toán:</strong> ${orderProducts.paymentName}</p>
                            <p><strong>Phương thức giao hàng:</strong> ${orderProducts.deliveryName}</p>
                            <p><strong>Phí Giao hàng:</strong> ${orderProducts.deliveryPrice}đ</p>

                            <p><strong>Địa chỉ nhận hàng:</strong> ${orderProducts.deliveryAddress}</p>
                        </div>
                    </div>
                    <div class="col-md-6 col-lg-6 col-sm-12">
                        <h4>Tiến độ đơn hàng</h4>
                        <c:forEach var="status" items="${orderProducts.statusModels}">
                            <div class="order-status">

                                <div class="timeLine">
                                    <div>
                                        <fmt:formatDate value="${status.startDate}" pattern="dd/MM/yyyy HH:mm:ss" />
                                    </div>
                                </div>
                                <div class="info">
                                    <div>${status.name}</div>
                                    <div >${status.description}</div>
                                </div>

                            </div>
                        </c:forEach>
                    </div>

                    <div class="product-table-container">
                        <table class="product-table">
                            <thead>
                            <tr>
                                <th class="product-table-header">Product Name</th>
                                <th class="product-table-header">Price</th>
                                <th class="product-table-header">Discount</th>
                                <th class="product-table-header">Colors</th>
                                <th class="product-table-header">Sizes</th>
                                <th class="product-table-header">Quantity </th>
                                <th class="product-table-header">Images</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="product" items="${orderProducts.productModels}">
                                <tr class="product-table-row">
                                    <td class="product-table-cell">${product.name}</td>
                                    <td class="product-table-cell">
                                        <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="₫" groupingUsed="true"/>
                                    </td>
                                    <td class="product-table-cell">${product.discount}%</td>

                                    <td class="product-table-cell">
                                        <c:forEach var="color" items="${product.colorModels}">
                                           <div class="color-box">
                                               <div>${color.name}</div>
                                               <div class="color-boxSpan" style="background-color: ${color.hexCode};"></div>
                                           </div>
                                        </c:forEach>
                                    </td>
                                    <td class="product-table-cell">
                                        <c:forEach var="color" items="${product.colorModels}">
                                            <c:forEach var="size" items="${color.sizeModels}">
                                                ${size.size}<br>
                                            </c:forEach>
                                        </c:forEach>
                                    </td>
                                       <td class="product-table-cell">${product.purchaseQuantity}</td>
                                    <td class="product-table-cell">
                                        <c:forEach var="color" items="${product.colorModels}">
                                            <c:forEach var="image" items="${color.imageModels}">
                                                <img class="product-image" src="data:image/png;base64,${image.imageBase64}" alt="${product.name} Image">
                                            </c:forEach>
                                        </c:forEach>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:if>

            </div>
        </div>
    </div>
</header>

<footer>
</footer>
</body>

<script src="components/footer.js?v=${System.currentTimeMillis()}"></script>
<script src="components/navigation.js?v=${System.currentTimeMillis()}"></script>
</html>
