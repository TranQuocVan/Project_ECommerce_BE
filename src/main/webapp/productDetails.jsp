<%--
  Created by IntelliJ IDEA.
  User: huyvu
  Date: 12/11/2024
  Time: 6:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>



<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Product Details</title>
    <style>
        .color-box {
            display: inline-block;
            width: 20px;
            height: 20px;
            border: 1px solid #000;
            margin-right: 5px;
        }
        .color-item {
            margin-bottom: 15px;
        }
        img {
            margin: 5px 0;
            border: 1px solid #ccc;
        }
    </style>
</head>
<body>
<h1>Product Details</h1>

<!-- Check if the product object is not empty -->
<c:if test="${not empty product}">
    <!-- Display Product Information -->
    <h2>${product.name}</h2>
    <p><strong>Price:</strong> $${product.price}</p>
    <p><strong>Discount:</strong> ${product.discount}%</p>
    <p><strong>Category ID:</strong> ${product.productCategoryId}</p>
    <p><strong>Group Product ID:</strong> ${product.groupProductId}</p>

    <!-- Display Colors -->
    <h3>Colors:</h3>
    <c:forEach var="color" items="${product.colorModels}">
        <div class="color-item">
            <p><strong>Color Name:</strong> ${color.name}</p>
            <p>
                <strong>Hex Code:</strong> ${color.hexCode}
                <span class="color-box" style="background-color: ${color.hexCode};"></span>
            </p>

            <!-- Display Sizes -->
            <h4>Sizes:</h4>
            <ul>
                <c:forEach var="size" items="${color.sizeModels}">
                    <li>${size.size} (Stock: ${size.stock})</li>
                </c:forEach>
            </ul>

            <!-- Display Images -->
            <h4>Images:</h4>
            <c:if test="${not empty color.imageModels}">
                <c:forEach var="image" items="${color.imageModels}">
                    <img src="data:image/jpeg;base64,${image.imageBase64}" alt="Product Image" width="150">
                </c:forEach>
            </c:if>
            <c:if test="${empty color.imageModels}">
                <p>No images available for this color.</p>
            </c:if>
        </div>
    </c:forEach>
</c:if>

<!-- If no product is found -->
<c:if test="${empty product}">
    <p>No product found for the given ID.</p>
</c:if>

</body>
</html>
