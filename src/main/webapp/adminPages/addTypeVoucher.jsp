<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thêm loại Voucher</title>
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
                    <h5 class="titlePageAdmin">Thêm loại Voucher</h5>
                    <div class="col-md-3">
                        <div id="navigationAdmin"></div>
                    </div>

                    <div class="col-md-9">
                        <div class="form-container">
                            <c:if test="${param.success == 'true'}">
                                <div class="alert alert-success">Thêm loại voucher thành công!</div>
                            </c:if>
                            <c:if test="${param.error == 'true'}">
                                <div class="alert alert-danger">Đã xảy ra lỗi khi thêm loại voucher.</div>
                            </c:if>

                            <form action="${pageContext.request.contextPath}/AddTypeVoucherController" method="post">
                                <label for="name" class="form-label">Tên loại voucher:</label>
                                <input type="text" id="name" name="typeName" class="form-control" placeholder="Tên loại voucher" required>

                                <br>

                                <label for="name" class="form-label">Mô tả loại voucher:</label>
                                <input type="text" id="description" name="description" class="form-control" placeholder="Mô tả loại voucher" required>

                                <button type="submit" class="btn btn-primary mt-3">Thêm loại Voucher</button>
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
