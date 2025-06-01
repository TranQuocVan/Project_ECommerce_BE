<%--
  Created by IntelliJ IDEA.
  User: Van Tran
  Date: 1/11/2025
  Time: 5:09 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/admin/managerProductWithFilter.css?v=${System.currentTimeMillis()}">

</head>

<body>
<header>
  <nav></nav>
  <div class="under-navigation">
    <div class="container ">
      <div class="row mt-5">

        <h5 class="titlePageAdmin">Quản li đơn hàng theo bộ lọc</h5>

        <!-- AdminController Navigation -->
        <div class="col-md-3">
          <div id="navigationAdmin"></div>
        </div>


        <!-- Form Section -->
        <div class="col-9">
          <div class="data-container">
            <form class="row g-3 align-items-center" action="${pageContext.request.contextPath}/OrderAdminController" method="post">
              <!-- Payment Filter -->
              <div class="col-md-2 ">
                <label for="paymentFilter" class="form-label">Thanh toán</label>
                <select class="form-select" id="paymentFilter" name="paymentId">
                  <option selected value="0">All</option>
                  <c:if test="${not empty listPaymentModels}">
                  <c:forEach var="item" items="${listPaymentModels}">
                    <option value="${item.id}">${item.methodPayment}</option>
                  </c:forEach>
                  </c:if>
                </select>
              </div>

              <!-- Delivery Filter -->
              <div class="col-md-2">
                <label for="deliveryFilter" class="form-label">Vận chuyển</label>
                <select class="form-select" id="deliveryFilter" name="deliveryId">
                  <option selected value="0">All</option>
                  <c:if test="${not empty listDeliveriesModels}">
                    <c:forEach var="item" items="${listDeliveriesModels}">
                  <option value="${item.id}">${item.name}</option>
                    </c:forEach>
                    </c:if>
                </select>
              </div>

              <!-- Order Date Filter -->
              <div class="col-md-3">
                <label for="orderDateFilter" class="form-label">Ngày đặt hàng</label>
                <input type="date" class="form-control" id="orderDateFilter" name="orderDate">
              </div>

              <!-- Status Filter -->
              <div class="col-md-2">
                <label for="statusFilter" class="form-label">Trạng thái</label>

                <select class="form-select" id="statusFilter" name="statusTypeId">
                  <option selected value="0">All</option>

                  <c:if test="${not empty statusAdminModels}">
                  <c:forEach var="item" items="${statusAdminModels}">

                  <option value="${item.id}">${item.name}</option>

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

            <c:if test="${empty listOrderAdminModel}">
              <div class="alert alert-info text-center mt-4" role="alert">
                Dữ liệu trống
              </div>
            </c:if>


            <c:if test="${not empty listOrderAdminModel}">
              <c:forEach var="od" items="${listOrderAdminModel}">
                <div class="area-shoes mb-4">
                  <div class="card">
                    <div class="card-body">
                      <div class="row align-items-center text-center">
                        <!-- ID -->
                        <div class="col-md-2">
                          <p class="small text-muted mb-1">ID</p>
                          <p class="lead fw-normal mb-0">${od.id}</p>
                        </div>
                        <!-- Ngày mua -->
                        <div class="col-md-2">
                          <p class="small text-muted mb-1">Ngày mua</p>
                          <p class="lead fw-normal mb-0">${od.formattedOrderDate}</p>
                        </div>
                        <!-- Trạng thái -->
                        <div class="col-md-2">
                          <p class="small text-muted mb-1">Trạng thái</p>
                          <p class="status fw-normal mb-0">Đã xác nhận</p>
                          <div class="status-list" style="display: none;">
                            <form action="AdminUpdateStatusController" method="post" class="edit-status-form">
                              <input type="hidden" name="nameStatus" id="nameStatus" value="">
                              <input type="hidden" name="orderId" value="${od.id}">
                              <select class="form-select" onchange="updateHiddenStatus(this)">
                                <c:if test="${not empty statusAdminModels}">
                                  <c:forEach var="item" items="${statusAdminModels}">

                                    <option value="${item.id}">${item.name}</option>

                                  </c:forEach>
                                </c:if>
                              </select>
                            </form>
                          </div>
                        </div>





                        <!-- Phương thức -->
                        <div class="col-md-2">
                          <p class="small text-muted mb-1">Phương thức</p>
                          <p class="lead fw-normal mb-0">${od.paymentName}</p>
                        </div>
                        <!-- Vận chuyển -->
                        <div class="col-md-2">
                          <p class="small text-muted mb-1">Vận chuyển</p>
                          <p class="lead fw-normal mb-0">${od.deliveryName}</p>
                        </div>

                        <div class="col-md-2">
                          <button type="button" class="btn btn-warning mt-2" onclick="editStatus(this)">Chỉnh sửa</button>
                          <button type="button" class="btn btn-success mt-2" onclick="submitForm(this)" style="display: none;">Xác nhận</button>
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
  </div>
</header>

<footer></footer>


</body>

<%--<script type="module" src="${pageContext.request.contextPath}/scripts/admin/addProductCategory.js"></script>--%>
<script src="${pageContext.request.contextPath}/components/navigation.js"></script>
<script src="${pageContext.request.contextPath}/components/footer.js"></script>
<script src="${pageContext.request.contextPath}/scripts/scroll.js"></script>
<script src="${pageContext.request.contextPath}/components/navigationadmin.js"></script>
<script src="${pageContext.request.contextPath}/scripts/admin/editStatus.js"></script>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>

</html>

