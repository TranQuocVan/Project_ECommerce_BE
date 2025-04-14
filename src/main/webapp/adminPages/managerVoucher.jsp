<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="vi_VN" />
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lí loại Voucher</title>
    <link rel="icon" type="image/svg" href="assets/logo2.svg">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>


    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/admin/admin.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/globa.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/slider.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/navigation.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/admin/navigationAdmin.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/global.css">
    <link rel="stylesheet" href="styles/voucherModal.css?v=${System.currentTimeMillis()}">
</head>
<body>
    <header>
        <nav></nav>
        <div class="under-navigation">
            <div class="container">
                <div class="row mt-5">
                    <h5 class="titlePageAdmin">Quản lí Voucher</h5>
                    <div class="col-md-3">
                        <div id="navigationAdmin"></div>
                    </div>

                    <div class="col-md-9">
                        <div class="form-container">
                            <!-- Dropdown lọc loại voucher -->
                            <form method="get" action="ManagerVoucherController">
                                <label for="typeVoucherId">Lọc theo loại voucher:</label>
                                <select name="typeVoucherId" id="typeVoucherId" onchange="this.form.submit()">
                                    <!-- Lựa chọn "Tất cả" -->
                                    <option value="all" ${empty selectedTypeId || selectedTypeId == 'all' ? 'selected' : ''}>Tất cả</option>

                                    <!-- Các loại voucher có trong danh sách -->
                                    <c:forEach var="type" items="${allTypeVouchers}">
                                        <option value="${type.typeVoucherId}" ${selectedTypeId != null && selectedTypeId.equals(String.valueOf(type.typeVoucherId)) ? 'selected' : ''}>
                                                ${type.description}
                                        </option>
                                    </c:forEach>
                                </select>
                            </form>


                            <br>

                            <!-- Bảng hiển thị voucher -->
                            <table class="table">
                                <thead>
                                <tr>
                                    <th scope="col">Mã Voucher</th>
                                    <th scope="col">Loại Voucher</th>
                                    <th scope="col">Giảm giá (%)</th>
                                    <th scope="col">Giảm giá tối đa (VNĐ)</th>
                                    <th scope="col">Ngày bắt đầu</th>
                                    <th scope="col">Ngày kết thúc</th>
                                    <th scope="col">Số lượng</th>
                                    <th scope="col">Cập nhật</th>
                                    <th scope="col">Xóa</th>
                                </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="v" items="${vouchers}">
                                        <tr style="cursor: pointer">
                                            <td>${v.voucherId}</td>
                                            <td style="display: none;">${v.typeVoucherId}</td>
                                            <td><c:out value="${typeVoucherDescriptions[v.typeVoucherId]}" /></td>
                                            <td>${fn:split(v.discountPercent, '.')[0]}%</td>
                                            <td><fmt:formatNumber value="${v.discountMaxValue}" type="number" groupingUsed="true" maxFractionDigits="0" /> VNĐ</td>
                                            <td>${v.startDate}</td>
                                            <td>${v.endDate}</td>
                                            <td>${v.quantity}</td>
                                            <td>
                                                <button class="btn btn-primary btn-update-voucher" data-id="${v.voucherId}">Cập nhật</button>
                                            </td>
                                            <td>
                                                <button class="btn btn-danger btn-delete-voucher" data-id="${v.voucherId}">Xóa</button>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>


                            <!-- Modal cập nhật -->
                            <div class="modal fade" style="margin-top: 5%" id="updateModal" tabindex="-1" aria-labelledby="updateModalLabel" aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <div>
                                                <h5 class="modal-title" style="font-weight: 600">Cập nhật Voucher</h5>
                                                <span>(Vui lòng nhập đầy đủ thông tin mà bạn muốn cập nhật!!!)</span>
                                            </div>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Đóng"></button>
                                        </div>
                                        <div class="modal-body">
                                            <input type="hidden" id="update-voucher-id">
                                            <div class="mb-3">
                                                <label for="update-voucher-type-id" class="form-label">Loại Voucher</label>
<%--                                                <input type="text" class="form-control" id="update-voucher-type-id">--%>
                                                    <select id="update-voucher-type-id" onchange="this.form.submit()">
                                                        <!-- Các loại voucher có trong danh sách -->
                                                        <c:forEach var="type" items="${allTypeVouchers}">
                                                            <option value="${type.typeVoucherId}" ${selectedTypeId != null && selectedTypeId.equals(String.valueOf(type.typeVoucherId)) ? 'selected' : ''}>
                                                                    ${type.description}
                                                            </option>
                                                        </c:forEach>
                                                    </select>
                                            </div>
                                            <div class="mb-3">
                                                <label for="update-voucher-discount-percent" class="form-label">Giảm giá (%)</label>
                                                <input type="number" class="form-control" id="update-voucher-discount-percent">
                                            </div>
                                            <div class="mb-3">
                                                <label for="update-voucher-discount-max-value" class="form-label">Giảm giá tối đa (VNĐ)</label>
                                                <input type="number" class="form-control" id="update-voucher-discount-max-value">
                                            </div>
                                            <div class="mb-3">
                                                <label for="update-voucher-startDate" class="form-label">Ngày bắt đầu giảm giá</label>
                                                <input type="date" class="form-control" id="update-voucher-startDate">
                                            </div>
                                            <div class="mb-3">
                                                <label for="update-voucher-endDate" class="form-label">Ngày hết giảm giá</label>
                                                <input type="date" class="form-control" id="update-voucher-endDate">
                                            </div>
                                            <div class="mb-3">
                                                <label for="update-voucher-quantity" class="form-label">Số lượng</label>
                                                <input type="number" class="form-control" id="update-voucher-quantity">
                                            </div>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                                            <button type="button" class="btn btn-success" id="btn-confirm-update-voucher">Xác nhận</button>
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </div>

                    </div>
                </div>
            </div>

        </div>
    </header>

    <footer></footer>

    <script src="${pageContext.request.contextPath}/components/navigation.js"></script>
    <script src="${pageContext.request.contextPath}/components/footer.js"></script>
    <script src="${pageContext.request.contextPath}/scripts/scroll.js"></script>
    <script src="${pageContext.request.contextPath}/components/navigationadmin.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <script src="${pageContext.request.contextPath}/scripts/admin/managerVoucher.js"></script>
</body>
</html>
