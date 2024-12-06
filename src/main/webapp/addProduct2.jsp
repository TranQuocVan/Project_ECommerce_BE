<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Product</title>
</head>
<body>
<h1>Add Product</h1>
<form action="ProductController" method="post" enctype="multipart/form-data">
    <h2>Product Details</h2>
    <label for="productName">Product Name:</label>
    <input type="text" id="productName" name="productName" required><br>

    <label for="price">Price:</label>
    <input type="number" id="price" name="price" step="0.01" required><br>

    <label for="discount">Discount:</label>
    <input type="number" id="discount" name="discount" step="0.01" required><br>

    <h2>Colors</h2>
    <div id="colorContainer">
        <!-- Initial Color Template -->
        <div class="color" data-color-index="0">
            <label for="colorName0">Color Name:</label>
            <input type="text" id="colorName0" name="colorName0" required><br>

            <label for="hexCode0">Hex Code:</label>
            <input type="text" id="hexCode0" name="hexCode0" required><br>

            <h3>Sizes</h3>
            <div class="sizeContainer" data-size-index="0">
                <label>Size:</label>
                <input type="text" name="size0[]" required><br>

                <label>Stock:</label>
                <input type="number" name="stock0[]" required><br>
            </div>
            <button type="button" onclick="addSize(this)">Add Size</button>

            <h3>Images</h3>
            <input type="file" name="image0[]" accept="image/*"><br>
            <button type="button" onclick="addImage(this)">Add Image</button>
        </div>
    </div>
    <button type="button" onclick="addColor()">Add Color</button>
    <br><br>
    <button type="submit">Submit</button>
</form>

<script src="add.js?v=${System.currentTimeMillis()}" ></script>
</body>
</html>
