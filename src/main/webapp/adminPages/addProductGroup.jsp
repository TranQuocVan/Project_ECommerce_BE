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
    <title>Upload Product Category</title>
    <link rel="icon" type="image/svg" href="assets/logo2.svg">
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

    <!-- Custom CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/admin/addProductCategory.css?v=${System.currentTimeMillis()}">
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
        <div class="container ">
            <div class="row mt-5">

                <h5 class="titlePageAdmin">Thêm nhóm sản phẩm </h5>

                <!-- AdminController Navigation -->
                <div class="col-md-3">
                    <div id="navigationAdmin"></div>
                </div>


                <!-- Form Section -->
                <div class="col-9">
                    <div class="form-container">
                        <form id="addCategoryForm">
                            <label for="name" class="form-label">Tên nhóm sản phẩm:</label>
                            <input type="text" id="name" name="name" class="form-control" placeholder="Enter category name" required>

                            <label for="description" class="form-label">Mô tả:</label>
                            <textarea id="description" name="description" class="form-control" rows="3" placeholder="Enter description" required></textarea>

                            <label for="picture" class="upload">Upload Picture</label>
                            <div class="custom-file-upload">
                                <input id="picture" type="file" name="image" accept="image/*" required>
                            </div>

                            <button type="submit" class="btn btn-primary mt-3">Submit</button>
                            <div class="message mt-2" id="message"></div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</header>

<footer></footer>


</body>

<script type="module" src="${pageContext.request.contextPath}/scripts/admin/addProductGroup.js?v=${System.currentTimeMillis()}"></script>
<script src="${pageContext.request.contextPath}/components/navigation.js"></script>
<script src="${pageContext.request.contextPath}/components/footer.js"></script>
<script src="${pageContext.request.contextPath}/scripts/scroll.js"></script>
<script src="${pageContext.request.contextPath}/components/navigationadmin.js"></script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>

</html>
