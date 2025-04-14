<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thêm Voucher</title>
    <link rel="icon" type="image/svg" href="assets/logo2.svg">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>


    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/global.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/slider.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/navigation.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/admin/navigationAdmin.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/global.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/admin/globaladmin.css">
</head>
<body>
    <header>
        <nav></nav>
        <div class="under-navigation">
            <div class="container">
                <div class="row mt-5">
                    <h5 class="titlePageAdmin">Thêm Voucher</h5>
                    <div class="col-md-3">
                        <div id="navigationAdmin"></div>
                    </div>

                    <div class="col-md-9">
                        <div class="form-container">

                            <c:if test="${param.success == 'true'}">
                                <div class="alert alert-success">Thêm Voucher thành công!</div>
                            </c:if>
                            <c:if test="${param.error == 'true'}">
                                <div class="alert alert-danger">Đã xảy ra lỗi khi thêm Voucher.</div>
                            </c:if>

                            <form action="${pageContext.request.contextPath}/AddVoucherController" method="post">
                                <!-- Dropdown chọn loại voucher -->
                                <label for="typeVoucherId" class="form-label">Loại voucher:</label>
                                <select id="typeVoucherId" name="typeVoucherId" class="form-control" required>
                                    <option value="">-- Chọn loại voucher --</option>
                                    <c:forEach var="tv" items="${typeVoucherList}">
                                        <option value="${tv.typeVoucherId}">${tv.description}</option>
                                    </c:forEach>
                                </select>

                                <br>

                                <!-- Input số phần trăm giảm giá -->
                                <label for="discountPercent" class="form-label">Giảm giá (%):</label>
                                <input type="number" id="discountPercent" name="discountPercent" class="form-control" placeholder="%" required>

                                <br>

                                <!-- Input giá trị giảm tối đa -->
                                <label for="discountMaxValue" class="form-label">Giảm giá tối đa (VNĐ):</label>
                                <input type="number" id="discountMaxValue" name="discountMaxValue" class="form-control" placeholder="VNĐ" required>

                                <br>

                                <!-- Ngày bắt đầu -->
                                <label for="startDate" class="form-label">Ngày bắt đầu giảm giá:</label>
                                <input type="date" id="startDate" name="startDate" class="form-control" required>

                                <br>

                                <!-- Ngày kết thúc -->
                                <label for="endDate" class="form-label">Ngày hết giảm giá:</label>
                                <input type="date" id="endDate" name="endDate" class="form-control" required>

                                <br>

                                <!-- Số lượng voucher -->
                                <label for="quantity" class="form-label">Số lượng:</label>
                                <input type="number" id="quantity" name="quantity" class="form-control" required>

                                <button type="submit" class="btn btn-primary mt-3">Thêm voucher</button>
                                <div class="message mt-2" id="message"></div>
                            </form>

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
</body>
</html>
