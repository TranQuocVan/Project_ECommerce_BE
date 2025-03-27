<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Add Product</title>
  <link rel="icon" type="image/svg" href="assets/logo2.svg">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/admin/addProduct.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/globa.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/slider.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/navigation.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/admin/navigationAdmin.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/global.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/admin/globaladmin.css">
<%--  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/buylater.css">--%>
</head>

<body>
<header>
  <nav></nav>
  <div class="under-navigation">
    <div class="container">
      <div class="row mt-5">
        <h5 class="titlePageAdmin">Thêm sản pham mới</h5>
        <div class="col-md-3">
          <div id="navigationAdmin"></div>
        </div>
        <div class="col-md-9">
          <!-- Form bắt đầu -->
          <form action="ProductController" method="post" enctype="multipart/form-data">

            <!-- Group Section -->
            <div class="section">
              <div id="optionGroup">
                <label for="category">Nhóm sản pham:</label>
                <select id="group" name="group">
                  <c:forEach var="group" items="${ListGroupProduct}">
                    <option value="${group.id}">${group.name}</option>
                  </c:forEach>
                </select>
              </div>
            </div>

            <!-- Category Section -->
            <div class="section">
              <div id="optionCategory">
                <label for="category">Loại sản pham:</label>
                <select id="category" name="category">
                  <c:forEach var="category" items="${ListProductCategory}">
                    <option value="${category.id}">${category.name}</option>
                  </c:forEach>
                </select>
              </div>
            </div>

            <!-- Product Information -->
            <div class="section">
              <label for="name">Product Name:</label>
              <input type="text" id="name" name="name" required>

              <label for="price">Price:</label>
              <input type="number" id="price" name="price" required>

              <label for="discount">Discount:</label>
              <input type="number" id="discount" name="discount">
            </div>

            <h2>Colors</h2>
            <div id="colorContainer">
              <!-- Color template here -->
              <div class="color" data-color-index="0">
                <label for="colorName0">Color Name:</label>
                <input type="text" id="colorName0" name="colorName0" required><br>

                <label for="hexCode0">Hex Code:</label>
                <input type="color" id="hexCode0" name="hexCode0" required><br>

                <h3>Sizes</h3>
                <div class="sizeContainer" data-size-index="0">
                  <label>Size:</label>
                  <input type="text" name="size0[]" required><br>

                  <label>Stock:</label>
                  <input type="number" name="stock0[]" required><br>
                </div>



                <h3>Images</h3>
                <input type="file" name="image0[]" accept="image/*" onchange="previewImage(event, 0)"><br>
                <button type="button" onclick="addImage(this, 0)">Add Image</button>
                <div id="imagePreview0" class="image-preview-container"></div> <!-- Preview Container -->
                <button type="button" onclick="addSize(this)">Add Size</button>

              </div>
            </div>
            <button type="button" onclick="addColor()">Add Color</button>
            <br><br>



            <!-- Đóng Form chính xác -->
            <button type="submit">Submit</button>
          </form>
        </div>
      </div>
    </div>
  </div>
</header>

<footer></footer>

<script type="module" src="${pageContext.request.contextPath}/scripts/admin/addProduct.js"></script>
<script src="${pageContext.request.contextPath}/components/navigation.js"></script>
<script src="${pageContext.request.contextPath}/components/footer.js"></script>
<script src="${pageContext.request.contextPath}/scripts/scroll.js"></script>
<script src="${pageContext.request.contextPath}/components/navigationadmin.js"></script>
<script src="${pageContext.request.contextPath}/scripts/addProduct.js"></script>


</body>
</html>
