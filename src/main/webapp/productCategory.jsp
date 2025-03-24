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
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>



    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" href="styles/navigation.css">
    <link rel="stylesheet" href="styles/global.css?v=${System.currentTimeMillis()}">
    <link rel="stylesheet" href="styles/listProduct.css?v=${System.currentTimeMillis()}">
    <link rel="stylesheet" href="styles/login.css?v=${System.currentTimeMillis()}">
    <link rel="stylesheet" href="styles/popup.css">

</head>

<body>

<header>
    <nav>  </nav>
        <div style="margin-top: 50px" class="under-navigation">
            <div class="container">
                <div class="row">
                    <h3 style="text-align: center; font-weight: bold; margin-top: 50px">Danh sách sản phẩm</h3>
                    <div  class="col-md-2">
                        <div id="filter">
                            <form action="">
                                <p class="title-filter">Kích cỡ</p>
                                <div id="size">
                                    <button>XS</button>
                                    <button>S</button>
                                    <button>M</button>

                                    <button>L</button>
                                    <button>XL</button>
                                    <button>2XL</button>

                                    <button>3XL</button>
                                    <button>4XL</button>
                                </div>
                                <p class="title-filter">Màu sắc</p>
                                <div id="color">
                                    <div>
                                        <button style="background-color: red;"></button>
                                        <p>Đỏ</p>
                                    </div>
                                    <div>
                                        <button style="background-color: blue;"></button>
                                        <p>Xanh</p>
                                    </div>
                                    <div>
                                        <button style="background-color: yellow;"></button>
                                        <p>Vàng</p>
                                    </div>
                                    <div>
                                        <button style="background-color: green;"></button>
                                        <p>Xanh lá</p>
                                    </div>
                                    <div>
                                        <button style="background-color: black;"></button>
                                        <p>Đen</p>
                                    </div>
                                    <div>
                                        <button style="background-color: white;"></button>
                                        <p>Trắng</p>
                                    </div>
                                    <div class="">
                                        <button style="background-color: pink;"></button>
                                        <p>Hồng</p>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                    <div  class="col-md-10">
                        <div class="container">
                            <div class="row">
                                <div class="col-md-12">
                                    <div id="search">
                                        <form action="GetProductByCategoryNameController" method="GET" id="searchForm">
                                            <div style="display: flex ;width: 100% "   id="inputName" class="input-container">
                                                <!-- Search input for 'name' -->
                                                <input
                                                        style="padding: 5px 0 ; "
                                                        type="text"
                                                        id="nameLogin"
                                                        class="floating-input"
                                                        placeholder=" "
                                                        name="name"
                                                        required>
                                                <label class="floating-label">Tìm kiếm theo tên sản phẩm</label>

                                                <!-- Hidden input for 'keyword' -->
                                                <input type="hidden" name="keyword" value="${param.keyword}" />

                                                <!-- Submit button -->
                                                <button type="submit" style="background: none; border: none; cursor: pointer;">
                                                    <i style="right: 0;" class="fas fa-search"></i>
                                                </button>
                                            </div>
                                        </form>

                                    </div>
                                </div>



                                <c:if test="${not empty page.items}">
                                    <c:forEach var="product" items="${page.items}">

                                        <div class="col-md-3">

                                            <div class="product-item">
                                                <!-- Display Color-Specific Product Images (First and Second Image) -->
                                                <c:forEach var="color" items="${product.colorModels}" varStatus="status">
                                                    <div class="product-color-display">
                                                        <!-- Thêm class 'active' chỉ cho màu đầu tiên -->
                                                        <div class="imgAndSize color-item ${status.index == 0 ? 'active' : ''}" data-color="${color.name}">
                                                            <a style="display: block" href="GetProductByIdController?id=${product.id}">
                                                            <div class="image-container">
                                                                <!-- Default Image -->

                                                                <c:choose>
                                                                    <c:when test="${not empty color.imageModels and not empty color.imageModels[0]}">
                                                                        <img
                                                                                class="image-item image-default ${status.index == 0 ? 'active' : ''}"
                                                                                src="data:image/jpeg;base64,${color.imageModels[0].imageBase64}"
                                                                                alt="Default Image"
                                                                                onerror="this.onerror=null; this.src='noImageAvailable.jpg';" />
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <img
                                                                                class="image-item image-default ${status.index == 0 ? 'active' : ''}"
                                                                                src="assets/default/noImageAvailable.jpg"
                                                                                alt="Default Image" />
                                                                    </c:otherwise>
                                                                </c:choose>


                                                                <!-- Hover Image -->
                                                                <c:choose>
                                                                    <c:when test="${not empty color.imageModels and not empty color.imageModels[1]}">
                                                                        <img
                                                                                class="image-item image-hover"
                                                                                src="data:image/jpeg;base64,${color.imageModels[1].imageBase64}"
                                                                                alt="Hover Image"
                                                                                onerror="this.onerror=null; this.src='bg6.png';"/>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <img
                                                                                class="image-item image-hover"
                                                                                src="assets/default/bg6.png"
                                                                                alt="Hover Image"/>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </div>
                                                            </a>
                                                        </div>
                                                    </div>
                                                </c:forEach>

                                                <c:if test="${not empty product.colorModels}">
                                                    <c:forEach var="color" items="${product.colorModels}" varStatus="status">
                                                        <div class="size-listProduct">
                                                            <p class="fw-bold custom-center">Thêm vào giỏ hàng</p>
                                                            <div class="button-spacing">
                                                                <!-- Lặp qua các size của mỗi màu -->
                                                                <c:forEach var="sizes" items="${color.sizeModels}">
                                                                    <button class="option-size">
                                                                        <span class="idSize" style="display: none">${sizes.id}</span>
                                                                        <span class="idProduct" style="display: none">${product.id}</span>
                                                                        <span class="fw-bold sizeName">${sizes.size}</span>
                                                                    </button>
                                                                </c:forEach>
                                                            </div>
                                                        </div>
                                                    </c:forEach>
                                                </c:if>


                                                <div class="underImg">
                                                    <!-- Loop Through Product Colors -->
                                                    <c:if test="${not empty product.colorModels}">
                                                        <div class="color-listProduct color-buttons">
                                                            <c:forEach var="color" items="${product.colorModels}">
                                                                <div class="option-collor" data-color-id="${color.id}">
                                                                    <!-- Lưu trữ thông tin màu sắc -->
                                                                    <div class="nameColor">${color.name}</div>

                                                                    <button class="color-button"
                                                                            data-target="${color.name}"
                                                                            data-color-id="${color.id}" style="background-color:${color.hexCode};"> <!-- Truyền ID vào thuộc tính data -->
                                                                    </button>
                                                                </div>
                                                            </c:forEach>
                                                        </div>
                                                    </c:if>



                                                    <!-- Product Name, Price, Discount, and Original Price -->
                                                    <a style="display: block" href="GetProductByIdController?id=${product.id}">
                                                        <p class="name-product">${product.name}</p>
                                                        <div class="priceDiv">
                                                            <p class="price">
                                                                <fmt:setLocale value="vi_VN" />
                                                                <fmt:formatNumber value="${product.price - (product.price * product.discount / 100)}" type="currency"/>
                                                            </p>

                                                            <p class="discount">${product.discount}%</p>

                                                            <c:if test="${product.discount > 0}">
                                                                <p class="original-price">
                                                                    <fmt:setLocale value="vi_VN" />
                                                                    <fmt:formatNumber value="${product.price}" type="currency"/>
                                                                </p>
                                                            </c:if>
                                                        </div>
                                                    </a>

                                                </div>
                                            </div>

                                        </div>

                                    </c:forEach>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
</header>

<!-- Pagination -->
<div class="pagination">
    <c:if test="${page.totalPages > 1}">
        <ul>
            <!-- Previous Page -->
            <c:if test="${page.pageNo > 1}">
                <li>
                    <a href="GetProductByCategoryNameController?keyword=${param.keyword}&name=${param.name}&pageNo=${page.pageNo - 1}">Previous</a>
                </li>
            </c:if>

            <!-- Calculate Start and End Pages -->
            <c:set var="startPage" value="${page.pageNo - 2}" />
            <c:set var="endPage" value="${page.pageNo + 2}" />

            <!-- Adjust if startPage is less than 1 -->
            <c:if test="${startPage < 1}">
                <c:set var="endPage" value="${endPage + (1 - startPage)}" />
                <c:set var="startPage" value="1" />
            </c:if>

            <!-- Adjust if endPage exceeds totalPages -->
            <c:if test="${endPage > page.totalPages}">
                <c:set var="startPage" value="${startPage - (endPage - page.totalPages)}" />
                <c:set var="endPage" value="${page.totalPages}" />
            </c:if>

            <!-- Ensure startPage does not go below 1 -->
            <c:if test="${startPage < 1}">
                <c:set var="startPage" value="1" />
            </c:if>

            <!-- Page Numbers -->
            <c:forEach begin="${startPage}" end="${endPage}" var="i">
                <li>
                    <a href="GetProductByCategoryNameController?keyword=${param.keyword}&name=${param.name}&pageNo=${i}"
                       class="${i == page.pageNo ? 'active' : ''}">
                            ${i}
                    </a>
                </li>
            </c:forEach>

            <!-- Next Page -->
            <c:if test="${page.pageNo < page.totalPages}">
                <li>
                    <a href="GetProductByCategoryNameController?keyword=${param.keyword}&name=${param.name}&pageNo=${page.pageNo + 1}">Next</a>
                </li>
            </c:if>
        </ul>
    </c:if>
</div>


<footer></footer>

</body>
<script src="components/footer.js?v=${System.currentTimeMillis()}"></script>
<script src="scripts/listProduct.js?v=${System.currentTimeMillis()}"></script>
<script src="scripts/authScript/signInScript.js?v=${System.currentTimeMillis()}"></script>
<script src="components/navigation.js?v=${System.currentTimeMillis()}"></script>
<script src="components/popup.js?v=${System.currentTimeMillis()}"></script>
</html>
