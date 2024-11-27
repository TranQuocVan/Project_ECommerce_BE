<%--
  Created by IntelliJ IDEA.
  User: huyvu
  Date: 11/26/2024
  Time: 1:58 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thêm Sản Phẩm - Quản Trị</title>

    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link rel="stylesheet" href="../styles/global.css?v=${System.currentTimeMillis()}">
    <link rel="stylesheet" href="../styles/navigation.css?v=${System.currentTimeMillis()}">
    <link rel="stylesheet" href="../styles/adminAddProduct.css?v=${System.currentTimeMillis()}">

</head>
<style>
    .form-group {
        margin-bottom: 15px;
    }

    .preview-img {
        max-width: 100px;
        margin-right: 10px;
    }

    .size-group label {
        margin-right: 10px;
    }

    .size-group button {
        margin-top: 5px;
    }
</style>

<body>
<header>
    <nav></nav>
    <div class="under-navigation">
        <div class="container">
            <h1 class="mt-5">Thêm Sản Phẩm</h1>
            <form id="productForm">
                <!-- Product Name -->
                <div class="form-group">
                    <label for="productName">Tên sản phẩm:</label>
                    <input type="text" class="form-control" id="productName" name="productName" required>
                </div>

                <!-- Product Category -->
                <div class="form-group">
                    <label for="productCategory">Loại hàng:</label>
                    <select class="form-control" id="productCategory" name="productCategory" required>
                        <option value="Loại A">Loại A</option>
                        <option value="Loại B">Loại B</option>
                        <option value="Loại C">Loại C</option>
                        <option value="new">Thêm loại hàng mới</option>
                    </select>
                    <input type="text" class="form-control mt-2 d-none" id="newCategory" name="newCategory"
                           placeholder="Nhập loại hàng mới">
                </div>

                <!-- Product Images -->
                <div class="form-group">
                    <label for="productImages">Ảnh sản phẩm:</label>
                    <input type="file" class="form-control-file" id="productImages" name="productImages" multiple
                           required>
                    <div id="imagePreview" class="mt-2"></div>
                </div>

                <!-- Size and Quantity Inputs -->
                <div id="sizesContainer">
                    <div class="form-group size-group">
                        <label>Size:</label>
                        <input type="text" class="form-control d-inline-block w-25" name="sizes[]" required>
                        <label>Số lượng:</label>
                        <input type="number" class="form-control d-inline-block w-25" name="quantities[]" required>
                        <button type="button" class="btn btn-danger" onclick="removeSize(this)">Xóa</button>
                    </div>
                </div>
                <button type="button" class="btn btn-primary" onclick="addSize()">Thêm Size</button>

                <!-- Submit Button -->
                <div class="form-group mt-3">
                    <button type="submit" class="btn btn-success">Thêm Sản Phẩm</button>
                </div>
            </form>
        </div>
    </div>
</header>

<footer></footer>

<!-- jQuery and Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

<!-- Custom JS -->
<script src="../components/navigation.js?v=${System.currentTimeMillis()}"></script>
<script src="../components/footer.js?v=${System.currentTimeMillis()}"></script>

<script src="../scripts/adminAddProduct.js?v=${System.currentTimeMillis()}"></script>
</body>

</html>

