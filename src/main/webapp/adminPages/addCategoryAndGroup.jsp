<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Update Link</title>
    <link rel="icon" type="image/svg" href="assets/logo2.svg">
</head>

<body>

<div class="GroupProduct">
    <form action="${pageContext.request.contextPath}/AddGroupController" method="post" enctype="multipart/form-data">
        <input type="text" name="AddGroupProduct" placeholder="Add Group Product" required>
        <input type="file" name="image" accept="image/*" required><br>
        <button type="submit">Add Group Product</button>
    </form>
</div>

<div class="ProductCategory">
    <form action="${pageContext.request.contextPath}/AddCategoryAndGroupController" method="post">
        <input type="text" name="AddProductCategory" placeholder="Add Product Category" required>
        <button type="submit">Add Product Category</button>
    </form>

    <div class="GroupProductCategory">
<%--        <c:forEach var="category" items="${ListProductCategory}">--%>
<%--            <div>${category.name}</div>--%>
<%--        </c:forEach>--%>
    </div>
</div>

</body>

</html>
