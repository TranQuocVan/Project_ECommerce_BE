<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Product</title>
</head>
<body>
<h1>Add Product</h1>
<form action="ProductController" method="post" enctype="multipart/form-data">
    <h2>Product Details</h2>
    <label for="productName">Product Name:</label>
    <input type="text" id="productName" name="productName" required><br>

    <label for="price">Price:</label>
    <input type="number" id="price" name="price" step="0.01" required><br>

    <label for="discount">Discount:</label>
    <input type="number" id="discount" name="discount" step="0.01" required><br>

    <label for="productCategory">Product Category:</label>
    <select id="productCategory" name="productCategory" required>
        <option value="">-- Select Category --</option>
        <!-- Sử dụng JSTL để lặp qua danh sách -->
        <c:forEach var="category" items="${ListProductCategoty}">
            <option value="${category.id}">${category.name}</option>
        </c:forEach>
    </select><br>

    <h2>Colors</h2>
    <div id="colorContainer">
        <!-- Color template here -->
    </div>
    <button type="button" onclick="addColor()">Add Color</button>
    <br><br>
    <button type="submit">Submit</button>
</form>
<script src="scripts/addProduct.js?v=${System.currentTimeMillis()}"></script>
</body>
</html>
