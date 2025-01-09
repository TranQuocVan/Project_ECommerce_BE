<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h2>Upload Product</h2>
<form action="uploadProduct" method="post" enctype="multipart/form-data">
    <label for="name">Product Name:</label>
    <input type="text" name="name" id="name" required><br><br>

    <label for="quantity">Quantity:</label>
    <input type="number" name="quantity" id="quantity" required><br><br>

    <label for="price">Price:</label>
    <input type="number" step="0.01" name="price" id="price" required><br><br>

    <label for="category">Category :</label>
    <input type="text" name="category" id="category" required><br><br>

    <label for="image">Product Image:</label>
    <input type="file" name="image" id="image" accept="image/*" required><br><br>

    <input type="submit" value="Upload Product">
</form>

</body>
</html>
