<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order Details</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
<h1>Order Details</h1>

<c:if test="${not empty orderProducts}">
    <div class="order-summary">
        <p><strong>Order ID:</strong> ${orderProducts.id}</p>
        <p><strong>Payment Method:</strong> ${orderProducts.paymentName}</p>
        <p><strong>Order Date:</strong> ${orderProducts.orderDate}</p>
        <p><strong>Delivery Address:</strong> ${orderProducts.deliveryAddress}</p>
        <p><strong>Total Price:</strong> $${orderProducts.totalPrice}</p>
        <p><strong>Delivery Method:</strong> ${orderProducts.deliveryName}</p>
    </div>

    <c:choose>
        <c:when test="${not empty orderProducts.statusModels}">
            <table border="1" cellspacing="0" cellpadding="5">
                <thead>
                <tr>
                    <th>Status ID</th>
                    <th>Status Name</th>
                    <th>Order ID</th>
                    <th>Description</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="status" items="${orderProducts.statusModels}">
                    <tr>
                        <td>${status.statusId}</td>
                        <td>${status.name}</td>
                        <td>${status.orderId}</td>
                        <td>${status.description}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <p>No status records found.</p>
        </c:otherwise>
    </c:choose>

    <table border="1">
        <thead>
        <tr>
            <th>Product Name</th>
            <th>Price</th>
            <th>Discount</th>
            <th>Colors</th>
            <th>Sizes</th>
            <th>Stock</th>
            <th>Images</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="product" items="${orderProducts.productModels}">
            <tr>
                <td>${product.name}</td>
                <td>${product.price}</td>
                <td>${product.discount}%</td>
                <td>
                    <c:forEach var="color" items="${product.colorModels}">
                        <div>${color.name}
                            <span class="color-box" style="background-color: ${color.hexCode};"></span>
                        </div>
                    </c:forEach>
                </td>
                <td>
                    <c:forEach var="color" items="${product.colorModels}">
                        <c:forEach var="size" items="${color.sizeModels}">
                            ${size.size}<br>
                        </c:forEach>
                    </c:forEach>
                </td>
                <td>
                    <c:forEach var="color" items="${product.colorModels}">
                        <c:forEach var="size" items="${color.sizeModels}">
                            ${size.stock}<br>
                        </c:forEach>
                    </c:forEach>
                </td>
                <td>
                    <c:forEach var="color" items="${product.colorModels}">
                        <c:forEach var="image" items="${color.imageModels}">
                            <img src="data:image/png;base64,${image.imageBase64}" alt="${product.name} Image" style="width: 100px; height: auto;">
                        </c:forEach>
                    </c:forEach>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>

<c:if test="${empty orderProducts}">
    <p>No products found for this order.</p>
</c:if>

<a href="/orders">Back to Orders</a>

<style>
    .color-box {
        display: inline-block;
        width: 20px;
        height: 20px;
        margin-left: 5px;
    }
</style>
</body>
</html>
