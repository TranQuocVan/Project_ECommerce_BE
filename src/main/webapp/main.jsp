<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "f" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Main</title>

<body>

<!-- Header -->
<header>
    <h1>Main page</h1>

</header>

<!-- Main Content: Products -->
<main>
    <section class="products">
        <!-- Product 1 -->
        <c:forEach var="p" items="${products}">
            <div class="product-item">
                <h3>${p.getNameProduct()}</h3>
                <h3>Price ${p.getPrice()}</h3>
                <h3>Quantity ${p.getQuantity()}</h3>
                <h3>Category ${p.getIdProductCategory()}</h3>
                <img src="data:image/jpeg;base64, ${p.getImageBase64()}" alt="${p.getNameProduct()}">
                <button class="add-to-cart">Thêm vào giỏ</button>
            </div>
        </c:forEach>

    </section>
</main>

<!-- Footer -->
<footer>
    <p>&copy; 2024 Cửa Hàng Quần Áo. Tất cả quyền được bảo lưu.</p>
</footer>

</body>
</html>

