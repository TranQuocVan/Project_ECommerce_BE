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

  <!-- Bootstrap CSS -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

  <!-- Font Awesome -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

  <!-- Custom CSS -->
  <link rel="stylesheet" href="./styles/admin/addProductCategory.css">
  <link rel="stylesheet" href="./styles/globa.css">
  <link rel="stylesheet" href="./styles/slider.css">
  <link rel="stylesheet" href="./styles/navigation.css">
  <link rel="stylesheet" href="./styles/admin/navigationAdmin.css">
  <link rel="stylesheet" href="./styles/global.css">
  <link rel="stylesheet" href="./styles/admin/globaladmin.css">
</head>

<body>
<header>
  <nav></nav>
  <div class="under-navigation">
    <div class="container ">
      <div class="row">

        <h5 class="titlePageAdmin">Admin add new product category</h5>

        <!-- AdminController Navigation -->
        <div class="col-md-3">
          <div id="navigationAdmin"></div>
        </div>


        <!-- Form Section -->
        <div class="col-9">
          <div class="form-container">
            <form id="categoryForm" class="category-form">
              <label for="name" class="form-label">Category Name:</label>
              <input type="text" id="name" name="name" class="form-input"
                     placeholder="Enter category name" required>

              <label for="description" class="form-label">Description:</label>
              <textarea id="description" name="description" class="form-textarea" rows="3"
                        placeholder="Enter description" required></textarea>

              <label for="picture" class="upload">Upload Picture</label>
              <div class="custom-file-upload">
                <input type="file" id="picture" name="picture" class="custom-file-input"
                       accept="image/*" required="">
              </div>

              <button type="submit" class="form-button">Submit</button>
              <div class="message" id="message"></div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</header>

<footer></footer>


</body>

<script type="module" src="./scripts/admin/addProductCategory.js"></script>
<script src="./components/navigation.js"></script>
<script src="./components/footer.js"></script>
<script src="./scripts/scroll.js"></script>
<script src="./components/navigationadmin.js"></script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>

</html>
