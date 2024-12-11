<%--
  Created by IntelliJ IDEA.
  User: huyvu
  Date: 12/11/2024
  Time: 9:17 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Product List</title>
    <style>
        .product-item {
            border: 1px solid #ddd;
            padding: 15px;
            margin-bottom: 20px;
            border-radius: 5px;
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        .product-item img {
            max-width: 200px;
            margin-bottom: 10px;
        }

        .color-box {
            display: inline-block;
            width: 20px;
            height: 20px;
            border: 1px solid #000;
            margin-right: 5px;
        }

        .pagination {
            text-align: center;
            margin-top: 20px;
        }

        .pagination ul {
            list-style: none;
            padding: 0;
            display: inline-flex;
            gap: 10px;
        }

        .pagination a {
            text-decoration: none;
            padding: 5px 10px;
            border: 1px solid #ddd;
            border-radius: 3px;
            color: #007bff;
        }

        .pagination a.active {
            background-color: #007bff;
            color: white;
            pointer-events: none;
        }

        .pagination a:hover {
            background-color: #ddd;
        }

        .search-bar {
            text-align: center;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>

<h1>Product Categories</h1>

<!-- Search form -->
<div class="search-bar">
    <form action="your-servlet-url" method="get">
        <input type="text" name="keyword" placeholder="Search by category name" value="${param.keyword}" />
        <button type="submit">Search</button>
    </form>
</div>

<!-- Product List -->
<div class="product-list">
    <c:if test="${not empty page.items}">
        <c:forEach var="product" items="${page.items}">
            <div class="product-item">
                <h2>${product.name}</h2>
                <p><strong>Price:</strong> <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="$" /></p>
                <p><strong>Discount:</strong> ${product.discount}%</p>
                <p><strong>Category ID:</strong> ${product.productCategoryId}</p>
                <p><strong>Group Product ID:</strong> ${product.groupProductId}</p>

                <!-- Display Colors -->
                <c:if test="${not empty product.colorModels}">
                    <h3>Colors:</h3>
                    <div class="colors">
                        <c:forEach var="color" items="${product.colorModels}">
                            <div class="color-item">
                                <p><strong>Color:</strong> ${color.name}</p>
                                <p>
                                    <strong>Hex Code:</strong> ${color.hexCode}
                                    <span class="color-box" style="background-color: ${color.hexCode};"></span>
                                </p>

                                <!-- Display Sizes for this color -->
                                <h4>Sizes:</h4>
                                <ul>
                                    <c:forEach var="size" items="${color.sizeModels}">
                                        <li>${size.size} (Stock: ${size.stock})</li>
                                    </c:forEach>
                                </ul>

                                <!-- Display Images for this color -->
                                <h4>Images:</h4>
                                <c:if test="${not empty color.imageModels}">
                                    <c:forEach var="image" items="${color.imageModels}">
                                        <img src="data:image/jpeg;base64,${image.imageBase64}" alt="Product Image" />
                                    </c:forEach>
                                </c:if>
                                <c:if test="${empty color.imageModels}">
                                    <p>No images available for this color.</p>
                                </c:if>
                            </div>
                        </c:forEach>
                    </div>
                </c:if>
            </div>
        </c:forEach>
    </c:if>

    <c:if test="${empty page.items}">
        <p>No products found for the given category.</p>
    </c:if>
</div>

<!-- Pagination -->
<div class="pagination">
    <c:if test="${page.totalPages > 1}">
        <ul>
            <!-- Previous Page -->
            <c:if test="${page.pageNo > 1}">
                <li>
                    <a href="GetProductByCategoryNameController?keyword=${param.keyword}&pageNo=${page.pageNo - 1}">Previous</a>
                </li>
            </c:if>

            <!-- Page Numbers -->
            <c:forEach begin="1" end="${page.totalPages}" var="i">
                <li>
                    <a href="GetProductByCategoryNameController?keyword=${param.keyword}&pageNo=${i}"
                       class="${i == page.pageNo ? 'active' : ''}">
                            ${i}
                    </a>
                </li>
            </c:forEach>

            <!-- Next Page -->
            <c:if test="${page.pageNo < page.totalPages}">
                <li>
                    <a href="?keyword=${param.keyword}&pageNo=${page.pageNo + 1}">Next</a>
                </li>
            </c:if>
        </ul>
    </c:if>
</div>
<div>
    12345
</div>

</body>
</html>
