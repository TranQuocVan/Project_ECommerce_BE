<%--
  Created by IntelliJ IDEA.
  User: Van Tran
  Date: 1/9/2025
  Time: 9:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chọn Hình Thức Giao Hàng</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>


    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">


    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/admin/delivery.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/globa.css">
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
                <h5 class="titlePageAdmin">Thêm Hình Thức Giao Hàng</h5>
                <div class="col-md-3">
                    <div id="navigationAdmin"></div>
                </div>
                <div class="col-md-9">
                    <div class="form-container">
                        <form>
                            <div class="mb-3">
                                <label for="price" class="form-label">Hình thức giao hàng</label>
                                <input type="text" class="form-control" id="price"
                                       placeholder="Nhập hình thức giao hàng">
                            </div>

                            <div class="mb-3">
                                <label for="price" class="form-label">Giá Tiền (VNĐ)</label>
                                <input type="number" class="form-control" id="price2" placeholder="Nhập giá tiền">
                            </div>

                            <button type="submit" class="btn btn-primary w-100 btn-expand">Xác Nhận</button>
                        </form>
                    </div>
                </div>

            </div>
        </div>
    </div>
</header>

<footer>
</footer>


<!-- Bootstrap JS -->

</body>
<script src="${pageContext.request.contextPath}/components/navigation.js"></script>
<script src="${pageContext.request.contextPath}/components/footer.js"></script>
<script src="${pageContext.request.contextPath}/scripts/scroll.js"></script>
<script src="${pageContext.request.contextPath}/components/navigationadmin.js"></script>

</html>
