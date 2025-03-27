<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Product</title>
    <link rel="icon" type="image/svg" href="assets/logo2.svg">
    <style>
        /* Basic Reset */
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        /* Page Container */
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
            padding: 20px;
        }

        h1 {
            font-size: 2rem;
            color: #343a40;
            text-align: center;
            margin-bottom: 20px;
        }

        form {
            background-color: #ffffff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            max-width: 800px;
            margin: 0 auto;
        }

        h2 {
            font-size: 1.5rem;
            color: #495057;
            margin-bottom: 10px;
        }

        label {
            font-size: 1rem;
            color: #495057;
            margin-bottom: 5px;
            display: block;
        }

        input[type="text"],
        input[type="number"],
        select,
        input[type="color"],
        input[type="file"] {
            width: 100%;
            padding: 8px;
            margin-bottom: 15px;
            border: 1px solid #ced4da;
            border-radius: 4px;
            font-size: 1rem;
        }

        input[type="text"]:focus,
        input[type="number"]:focus,
        select:focus,
        input[type="color"]:focus {
            border-color: #007bff;
            outline: none;
        }

        /* Color Section Styles */
        #colorContainer {
            margin-bottom: 20px;
        }

        .color {
            border: 1px solid #ced4da;
            border-radius: 8px;
            padding: 15px;
            margin-bottom: 20px;
            background-color: #f8f9fa;
        }

        .color label {
            font-size: 1rem;
            color: #495057;
            margin-bottom: 8px;
            display: block;
        }

        .color input[type="text"],
        .color input[type="color"] {
            width: 100%;
            padding: 8px;
            margin-bottom: 10px;
            border: 1px solid #ced4da;
            border-radius: 4px;
            font-size: 1rem;
            height: 50px;
        }

        .color input[type="text"]:focus,
        .color input[type="color"]:focus {
            border-color: #007bff;
            outline: none;
        }

        .sizeContainer {
            margin-top: 10px;
        }

        .sizeContainer input {
            margin-bottom: 10px;
        }

        button[type="button"] {
            padding: 10px 15px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 5px;
            font-size: 1rem;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        button[type="button"]:hover {
            background-color: #0056b3;
        }

        /* Add Color Button */
        button[type="button"]:first-of-type {
            background-color: #28a745;
            color: white;
            font-weight: bold;
        }

        button[type="button"]:first-of-type:hover {
            background-color: #218838;
        }

        /* Hover effects for color options */
        .color-button {
            border: 1px solid #ced4da;
            width: 40px;
            height: 40px;
            border-radius: 50%;
            cursor: pointer;
            transition: transform 0.3s ease, border-color 0.3s ease;
        }

        .color-button:hover {
            transform: scale(1.2);
            border-color: #007bff;
        }

        .color-button.selected {
            border: 2px solid #000;
        }

        .color-listProduct {
            display: flex;
            flex-wrap: wrap;
            gap: 10px;
        }

        .color-listProduct .color {
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .color-listProduct .color-button {
            background-color: transparent;
        }

        .optionColorOnClicked {
            border: 2px solid #000 !important;
        }

        .color-listProduct .color-button:focus {
            outline: none;
        }

        /* Add Size and Image Buttons */
        .sizeContainer {
            margin-bottom: 15px;
        }

        .sizeContainer input {
            margin-bottom: 10px;
        }

        button[type="button"] {
            margin-top: 10px;
        }

        /* Add Color Button */
        button[type="button"]:first-of-type {
            background-color: #007bff;
            color: white;
            font-weight: bold;
        }

        button[type="button"]:first-of-type:hover {
            background-color: #0056b3;
        }

        /* Submit Button */
        button[type="submit"] {
            background-color: #28a745;
            color: white;
            font-weight: bold;
            margin-top: 20px;
            width: 100%;
            padding: 15px;
        }

        button[type="submit"]:hover {
            background-color: #218838;
        }

        /* Responsive Design */
        @media (max-width: 768px) {
            form {
                padding: 15px;
            }

            input[type="text"],
            input[type="number"],
            select,
            input[type="color"],
            input[type="file"] {
                font-size: 0.9rem;
            }

            button {
                font-size: 0.9rem;
            }

            button[type="submit"] {
                font-size: 1rem;
            }
        }



        .image-preview-container {
            display: flex;
            justify-content: center;
            margin-top: 10px;
        }

        .image-preview-container img {
            width: 100px;
            height: 100px;
            object-fit: cover;
            border-radius: 8px;
            border: 1px solid #ced4da;
        }

        .image-preview-container .remove-btn {
            position: absolute;
            top: 5px;
            right: 5px;
            background-color: rgba(0, 0, 0, 0.5);
            color: white;
            padding: 5px;
            border-radius: 50%;
            cursor: pointer;
        }

        .image-preview-container .remove-btn:hover {
            background-color: rgba(0, 0, 0, 0.7);
        }

        .image-drop-zone {
            border: 2px dashed #007bff;
            padding: 20px;
            text-align: center;
            background-color: #f0f8ff;
            border-radius: 8px;
            cursor: pointer;
            position: relative;
        }

        .image-drop-zone:hover {
            background-color: #e6f7ff;
        }

        .image-drop-zone p {
            font-size: 1rem;
            color: #007bff;
        }

        .image-drop-zone input[type="file"] {
            width: 100%;
            height: 100%;
            position: absolute;
            top: 0;
            left: 0;
            opacity: 0; /* Hide the default file input */
            cursor: pointer;
        }

        /* Change cursor style on drag over */
        .image-drop-zone.dragover {
            background-color: #d0e7ff;
            border-color: #0056b3;
        }

    </style>

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

    <!-- Dropdown danh mục sản phẩm -->
    <label for="productCategory">Product Category:</label>
    <select id="productCategory" name="productCategory" required>
        <c:forEach var="category" items="${ListProductCategory}">
            <option value="${category.id}">${category.name}</option>
        </c:forEach>
    </select><br>

    <!-- Dropdown nhóm sản phẩm -->
    <label for="groupProduct">Group Product:</label>
    <select id="groupProduct" name="groupProduct">
        <c:forEach var="group" items="${ListGroupProduct}">
            <option value="${group.id}">${group.name}</option>
        </c:forEach>
    </select><br>

    <h2>Colors</h2>
    <div id="colorContainer">
        <!-- Color template here -->
        <div class="color" data-color-index="0">
            <label for="colorName0">Color Name:</label>
            <input type="text" id="colorName0" name="colorName0" required><br>

            <label for="hexCode0">Hex Code:</label>
            <input type="color" id="hexCode0" name="hexCode0" required><br>

            <h3>Sizes</h3>
            <div class="sizeContainer" data-size-index="0">
                <label>Size:</label>
                <input type="text" name="size0[]" required><br>

                <label>Stock:</label>
                <input type="number" name="stock0[]" required><br>
            </div>
            <button type="button" onclick="addSize(this)">Add Size</button>



            <h3>Images</h3>
            <input type="file" name="image0[]" accept="image/*" onchange="previewImage(event, 0)"><br>
            <button type="button" onclick="addImage(this, 0)">Add Image</button>
            <div id="imagePreview0" class="image-preview-container"></div> <!-- Preview Container -->

        </div>
    </div>
    <button type="button" onclick="addColor()">Add Color</button>
    <br><br>
    <button type="submit">Submit</button>
</form>

<script src="${pageContext.request.contextPath}/scripts/addProduct.js?v=<%= System.currentTimeMillis() %>"></script>

</body>
</html>
