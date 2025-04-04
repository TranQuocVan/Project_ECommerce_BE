<%--
  Created by IntelliJ IDEA.
  User: Van Tran
  Date: 1/13/2025
  Time: 10:53 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Upload Product Category</title>
    <link rel="icon" type="image/svg" href="assets/logo2.svg">
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

    <!-- Custom CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/admin/addProductCategory.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/globa.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/slider.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/navigation.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/admin/navigationAdmin.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/global.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/admin/globaladmin.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/admin/managerProductWithFilter.css">


</head>

<body>
<header>
    <nav></nav>
    <div class="under-navigation">
        <div class="container ">
            <div class="row mt-5">

                <h5 class="titlePageAdmin">Quản lí sản phẩm theo bộ lọc</h5>

                <!-- AdminController Navigation -->
                <div class="col-md-3">
                    <div id="navigationAdmin"></div>
                </div>


                <!-- Form Section -->
                <div class="col-9">
                    <div class="data-container ms-auto">
                        <form class="row g-3 align-items-center" action="${pageContext.request.contextPath}/ProductAdminController" method="post">
                            <!-- Nhom Filter -->
                            <div class="col-md-2 ">
                                <label for="paymentFilter" class="form-label">Nhóm sản phẩm</label>
                                <select class="form-select" id="paymentFilter" name="groupId">
                                    <option selected value="0">All</option>
                                    <c:if test="${not empty groupProductModels}">
                                        <c:forEach var="item" items="${groupProductModels}">
                                            <option value="${item.id}">${item.name}</option>
                                        </c:forEach>
                                    </c:if>
                                </select>
                            </div>

                            <!-- Loai Filter -->
                            <div class="col-md-2">
                                <label for="deliveryFilter" class="form-label">Loại sản phẩm</label>
                                <select class="form-select" id="deliveryFilter" name="categoryId">
                                    <option selected value="0">All</option>
                                    <c:if test="${not empty productCategoryModels}">
                                        <c:forEach var="item" items="${productCategoryModels}">
                                            <option value="${item.id}">${item.name}</option>
                                        </c:forEach>
                                    </c:if>
                                </select>
                            </div>

                            <div class="col-md-2">
                                <label for="deliveryFilter" class="form-label">Theo kích cỡ</label>
                                <select class="form-select" id="dddd" name="nameSize">
                                    <option selected value="">All</option>
                                    <c:if test="${not empty sizeModels}">
                                        <c:forEach var="item" items="${sizeModels}">
                                            <option value="${item.size}">${item.size}</option>
                                        </c:forEach>
                                    </c:if>
                                </select>
                            </div>

                            <div class="col-md-2">
                                <label for="deliveryFilter" class="form-label">Theo màu sắc</label>
                                <select class="form-select" id="ddd" name="nameColor">
                                    <option selected value="">All</option>
                                    <c:if test="${not empty colorModels}">
                                        <c:forEach var="item" items="${colorModels}">
                                            <option value="${item.name}">${item.name}</option>
                                        </c:forEach>
                                    </c:if>
                                </select>
                            </div>



                            <!-- Search Button -->
                            <div class="col-md-2 d-flex align-items-end">
                                <button type="submit" class="btn btn-primary w-100">Search</button>
                            </div>
                        </form>
                    </div>


                    <div class="form-container mt-4">

                        <c:if test="${empty productModels}">
                            <div class="alert alert-info text-center mt-4" role="alert">
                                Dữ liệu trống
                            </div>
                        </c:if>


                        <c:if test="${not empty productModels}">
                        <c:forEach var="pm" items="${productModels}">

                        <div class="area-shoes mb-4">
                            <div class="card">
                                <div class="card-body">
                                    <form class="edit-status-form" method="POST" action="${pageContext.request.contextPath}/AdminUpdateProductController" enctype="multipart/form-data">
                                    <div class="row text-center">

                                        <div class="col-md-2 product-table-cell">
                                            <c:forEach var="color" items="${pm.colorModels}">
                                                <c:forEach var="image" items="${color.imageModels}">
                                                    <img class="product-image" src="data:image/png;base64,${image.imageBase64}" alt="${pm.name} Image">
                                                    <input type="hidden" name="colorId" value="${color.id}">

                                                </c:forEach>
                                            </c:forEach>
                                        </div>

                                        <!-- Nhom -->

                                        <div class="col-md-2">
                                            <p class="small text-muted mb-1">Nhóm</p>
                                            <p class="lead fw-normal mb-0">${pm.productGroupName}</p>
                                        </div>

                                        <!--Loai-->

                                        <div class="col-md-2">
                                            <p class="small text-muted mb-1">Loại</p>
                                            <p class="lead fw-normal mb-0">${pm.productCategoryName}</p>
                                        </div>

                                        <!-- Ten-->
                                        <div class="col-md-2">
                                            <p class="small text-muted mb-1">Tên sản phẩm</p>
                                            <p class="lead fw-normal mb-0 product">${pm.name}</p>

                                            <div class="edit-list" style="display: none;">
                                                <input class="lead fw-normal mb-0" type="text" name="nameProduct" id="nameProduct" placeholder="Tên sản phẩm" required style="width: 90px;">
                                                <input type="hidden" name="productId" value="${pm.id}">
                                            </div>

                                        </div>

                                        <div class="col-md-2">
                                            <p class="small text-muted mb-1">Giá sản phẩm</p>
                                            <fmt:formatNumber value="${pm.price}" type="number" groupingUsed="true" />đ

                                            <div class="edit-list" style="display: none;">
                                                <input class="lead fw-normal mb-0" type="number" name="priceProduct" id="priceProduct" placeholder="Giá sản phẩm" required style="width: 90px;">
                                                <input type="hidden" name="productId" value="${pm.id}">
                                            </div>
                                        </div>

                                        <div class="col-md-2">
                                            <p class="small text-muted mb-1">Giảm giá</p>
                                            <p class="lead fw-normal mb-0 product">${pm.discount}%</p>
                                            <div class="edit-list" style="display: none;">
                                                <input class="lead fw-normal mb-0" type="number" name="discountProduct" id="discountProduct" placeholder="Giảm giá" required style="width: 90px;">
                                                <input type="hidden" name="productId" value="${pm.id}">
                                            </div>
                                        </div>
                                        <!-- ID -->
                                        <div class="col-md-2">
                                            <p class="small text-muted mb-1 product">ID</p>
                                            <p class="lead fw-normal mb-0 product">${pm.id}</p>
                                        </div>


                                        <div class="col-md-2">
                                            <p class="small text-muted mb-1">Số lượng</p>
                                            <p class="lead fw-normal mb-0 product">${pm.stock}</p>
                                            <div class="edit-list" style="display: none;">
                                                <input class="lead fw-normal mb-0" type="number" name="stockProduct" id="stockProduct" placeholder="Số lượng" required style="width: 90px;">
                                                <input type="hidden" name="productId" value="${pm.id}">
                                            </div>
                                        </div>

                                        <!--Mau sac -->
                                        <div class="col-md-2">
                                            <p class="small text-muted mb-1">Màu sắc</p>
                                            <p class="lead fw-normal mb-0 product">${pm.colorName}</p>

                                            <div class="edit-list" style="display: none;">
                                                <input class="lead fw-normal mb-0" type="text" name="colorName" id="colorName" placeholder="Size" required style="width: 90px;">
                                            </div>

                                        </div>

                                        <div class="col-md-2 d-flex flex-column align-items-center">
                                            <p class="small text-muted mb-1">Hex Code:</p>
                                            <div class="color-bar product" style="width: 50px; height: 20px; background-color: ${pm.hexCode}; border: 1px solid #000;"></div>
                                            <div class="edit-list" style="display: none;">
                                                <input type="color" id="hexCode0"  value="${pm.hexCode}" name="hexCode" required><br>
                                            </div>

                                        </div>


                                        <!-- Trạng thái -->
                                        <div class="col-md-2">
                                            <p class="small text-muted mb-1">Kích cỡ</p>
                                            <p class="lead fw-normal mb-0 product">${pm.nameSize}</p>
                                            <div class="edit-list" style="display: none;">
                                                <input class="lead fw-normal mb-0" type="number" name="sizeProduct" id="sizeProduct" placeholder="Size" required style="width: 90px;">
                                                <input type="hidden" name="productId" value="${pm.id}">
                                            </div>
                                        </div>

                                        <!-- Đặt input file và preview container dưới các hình ảnh -->
                                        <div class="col-md-2">
                                            <p class="small text-muted mb-1">Hình ảnh</p>

                                            <div class="edit-list" style="display: none; position: relative; text-align: center;">
                                                <input type="file" id="file-input-0" name="image0[]" accept="image/*" onchange="previewImage(event, 0)" style="width: 105px; overflow: hidden;"><br>
                                                <div id="imagePreview0" style="margin-top: 5%">
                                                    <!-- Preview Container -->
                                                </div>
                                            </div>
                                        </div>


                                        <div class="col-md-2">
                                            <button type="button" class="btn btn-warning mt-2 " onclick="editStatus(this)" >Chỉnh sửa</button>
                                            <button type="button" class="btn btn-success mt-2 " onclick="submitForm(this)" style="display: none;">Xác nhận</button>
                                        </div>
                                    </div>
                                    </form>
                                </div>
                            </div>

                            <div class="col-md-2">
                                <form class="edit-status-form" method="POST" action="${pageContext.request.contextPath}/AdminDeleteProductController">
                                    <input type="hidden" name="productId" value="${pm.id}">
                                    <c:forEach var="color" items="${pm.colorModels}">
                                        <input type="hidden" name="colorId" value="${color.id}">
                                    </c:forEach>
                                    <button type="submit" class="btn btn-danger mt-2" >Xóa</button>
                                </form>
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

<footer></footer>


</body>

<%--<script type="module" src="${pageContext.request.contextPath}/scripts/admin/addProductCategory.js"></script>--%>
<script src="${pageContext.request.contextPath}/components/navigation.js"></script>
<script src="${pageContext.request.contextPath}/components/footer.js"></script>
<script src="${pageContext.request.contextPath}/scripts/scroll.js"></script>
<script src="${pageContext.request.contextPath}/components/navigationadmin.js"></script>
<script src="${pageContext.request.contextPath}/scripts/admin/editProduct.js"></script>
<script src="${pageContext.request.contextPath}/scripts/addProduct.js"></script>



<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>

</html>
