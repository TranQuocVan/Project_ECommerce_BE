<%--
  Created by IntelliJ IDEA.
  User: huyvu
  Date: 4/25/2025
  Time: 3:56 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý trạng thái chữ ký đơn hàng</title>
    <link rel="icon" type="image/svg" href="assets/logo2.svg">

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

    <!-- Custom CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/admin/addProductCategory.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/global.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/slider.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/navigation.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/admin/navigationAdmin.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/admin/globaladmin.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/admin/managerProductWithFilter.css?v=${System.currentTimeMillis()}">
</head>
<body>
<header>
    <nav></nav>
    <div class="under-navigation">
        <div class="container">
            <div class="row mt-5">
                <h5 class="titlePageAdmin">Quản lý trạng thái chữ ký đơn hàng</h5>

                <!-- AdminController Navigation -->
                <div class="col-md-3">
                    <div id="navigationAdmin"></div>
                </div>

                <!-- Main Content Section -->
                <div class="col-9">
                    <div class="data-container">
                        <form class="row g-3 align-items-center" action="${pageContext.request.contextPath}/SignAdminController" method="post">
                            <!-- Signature Status Filter -->
                            <div class="col-md-3">
                                <label for="signatureStatusFilter" class="form-label">Trạng thái chữ ký</label>
                                <select class="form-select" id="signatureStatusFilter" name="signatureStatus">
                                    <option selected value="0">All</option>
                                    <option value="SIGNED">Đã ký</option>
                                    <option value="NOT_SIGNED">Chưa ký</option>
                                    <option value="PENDING">Đang chờ</option>
                                </select>
                            </div>

                            <!-- Order ID Filter -->
                            <div class="col-md-3">
                                <label for="orderIdFilter" class="form-label">ID Đơn hàng</label>
                                <input type="text" class="form-control" id="orderIdFilter" name="orderId" placeholder="Nhập ID đơn hàng">
                            </div>

                            <!-- Search Button -->
                            <div class="col-md-2 d-flex align-items-end">
                                <button type="submit" class="btn btn-primary w-100">Search</button>
                            </div>
                        </form>
                    </div>

                    <!-- Order Signature List -->
                    <div class="form-container mt-4">
                        <div id="loading" class="alert alert-info text-center" style="display: none;">Đang tải dữ liệu...</div>
                        <div id="error" class="alert alert-danger text-center" style="display: none;"></div>

                        <c:if test="${empty listOrderSignatureModels}">
                            <div class="alert alert-info text-center mt-4" role="alert">
                                Dữ liệu trống
                            </div>
                        </c:if>

                        <c:if test="${not empty listOrderSignatureModels}">
                            <c:forEach var="order" items="${listOrderSignatureModels}">
                                <div class="area-shoes mb-4">
                                    <div class="card">
                                        <div class="card-body">
                                            <div class="row align-items-center text-center">
                                                <!-- Order ID -->
                                                <div class="col-md-3">
                                                    <p class="small text-muted mb-1">ID Đơn hàng</p>
                                                    <p class="lead fw-normal mb-0">${order.id}</p>
                                                </div>
                                                <!-- Signature Status -->
                                                <div class="col-md-3">
                                                    <p class="small text-muted mb-1">Trạng thái chữ ký</p>
                                                    <p class="status fw-normal mb-0">${order.signatureStatus}</p>
                                                    <div class="status-list" style="display: none;">
                                                        <form action="${pageContext.request.contextPath}/AdminUpdateSignatureStatusController" method="post" class="edit-status-form">
                                                            <input type="hidden" name="signatureStatus" id="signatureStatus" value="">
                                                            <input type="hidden" name="orderId" value="${order.id}">
                                                            <select class="form-select" onchange="updateHiddenSignatureStatus(this)">
                                                                <option value="SIGNED">Đã ký</option>
                                                                <option value="NOT_SIGNED">Chưa ký</option>
                                                                <option value="PENDING">Đang chờ</option>
                                                            </select>
                                                        </form>
                                                    </div>
                                                </div>
                                                <!-- Action Buttons -->
                                                <div class="col-md-3">
                                                    <button type="button" class="btn btn-warning mt-2" onclick="editStatus(this)">Chỉnh sửa</button>
                                                    <button type="button" class="btn btn-success mt-2" onclick="submitForm(this)" style="display: none;">Xác nhận</button>
                                                </div>
                                            </div>
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
</header>

<footer></footer>

<!-- Scripts -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/components/navigation.js"></script>
<script src="${pageContext.request.contextPath}/components/footer.js"></script>
<script src="${pageContext.request.contextPath}/scripts/scroll.js"></script>
<script src="${pageContext.request.contextPath}/components/navigationadmin.js"></script>
<script src="${pageContext.request.contextPath}/scripts/admin/editStatus.js"></script>
<script src="${pageContext.request.contextPath}/scripts/admin/signAdmin.js?v=${System.currentTimeMillis()}"></script>

</body>
</html>