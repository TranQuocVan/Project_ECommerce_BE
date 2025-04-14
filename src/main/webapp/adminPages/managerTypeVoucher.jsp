<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
                            <table class="table">
                                <thead>
                                <tr>
                                    <th scope="col">#</th>
                                    <th scope="col">Loại Voucher</th>
                                    <th scope="col">Mô tả</th>
                                    <th scope="col">Cập nhật</th>
                                    <th scope="col">Xóa</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="tv" items="${typeVoucherList}">
                                    <tr style="cursor: pointer">
                                        <td>${tv.typeVoucherId}</td>
                                        <td>${tv.typeName}</td>
                                        <td>${tv.description}</td>
                                        <td>
                                            <button class="btn btn-primary btn-update-type-voucher" data-id="${tv.typeVoucherId}">Cập nhật</button>
                                        </td>
                                        <td>
                                            <button class="btn btn-danger btn-delete-type-voucher" data-id="${tv.typeVoucherId}">Xóa</button>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>


                            <!-- Modal cập nhật -->
                            <div class="modal fade" style="margin-top: 10%" id="updateModal" tabindex="-1" aria-labelledby="updateModalLabel" aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <div>
                                                <h5 class="modal-title" style="font-weight: 600">Cập nhật loại voucher</h5>
                                                <span>(Vui lòng nhập đầy đủ thông tin mà bạn muốn cập nhật!!!)</span>
                                            </div>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Đóng"></button>
                                        </div>
                                        <div class="modal-body">
                                            <input type="hidden" id="update-type-voucher-id">
                                            <div class="mb-3">
                                                <label for="update-type-voucher-name" class="form-label">Tên loại voucher</label>
                                                <input type="text" class="form-control" id="update-type-voucher-name">
                                            </div>
                                            <div class="mb-3">
                                                <label for="update-type-voucher-desc" class="form-label">Mô tả</label>
                                                <textarea class="form-control" id="update-type-voucher-desc"></textarea>
                                            </div>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                                            <button type="button" class="btn btn-success" id="btn-confirm-update-type-voucher">Xác nhận</button>
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
    <script src="${pageContext.request.contextPath}/scripts/admin/managerTypeVoucher.js"></script>
</body>
</html>
