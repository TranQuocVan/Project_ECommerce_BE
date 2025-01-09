<%--
  Created by IntelliJ IDEA.
  User: Van Tran
  Date: 1/9/2025
  Time: 9:06 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Add Product</title>

  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

  <link rel="stylesheet" href="./styles/admin/addProduct.css">
  <link rel="stylesheet" href="./styles/globa.css">
  <link rel="stylesheet" href="./styles/slider.css">
  <link rel="stylesheet" href="./styles/navigation.css">
  <link rel="stylesheet" href="./styles/admin/navigationAdmin.css">
  <link rel="stylesheet" href="./styles/global.css">
  <link rel="stylesheet" href="./styles/admin/globaladmin.css">
  <link rel="stylesheet" href="./styles/buylater.css">


</head>

<body>
<header>
  <nav></nav>
  <div class="under-navigation">
    <div class="container">
      <div class="row mt-5">
        <h5 class="titlePageAdmin">Admin add new product</h5>
        <div class="col-md-3">
          <div id="navigationAdmin"></div>
        </div>
        <div class="col-md-9">
          <form class="form-container">
            <!-- Category Section -->
            <div class="section">
              <div id="optionCategory">
                <label for="category">Category:</label>
                <select id="category" name="category">
                  <!-- Dynamic options will be inserted here -->
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

            <!-- Colors Section -->
            <div class="section" id="colorsSection">
              <h2>Colors</h2>
              <button class="btn btn-primary" id="addColor">Add Color</button>
              <div id="colorContainer"></div>
            </div>
            <button type="submit">Submit</button>
          </form>
        </div>
      </div>
    </div>
  </div>
</header>


<footer>
</footer>

<script type="module" src="./scripts/admin/addProduct.js"></script>
<script src="./components/navigation.js"></script>
<script src="./components/footer.js"></script>
<script src="./scripts/scroll.js"></script>
<script src="./components/navigationadmin.js"></script>

</body>
</html>
