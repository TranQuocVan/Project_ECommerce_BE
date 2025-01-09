const itemslides = document.querySelectorAll("#slide .item");
console.log(itemslides)
let nameGroup = ""; // Biến lưu tên nhóm sản phẩm

// Lấy giá trị `data-name` từ các item khi được click
itemslides.forEach(item => {
    item.addEventListener("click", () => {
        nameGroup = item.getAttribute("data-name"); // Lấy giá trị của thuộc tính data-name
    });
});

// Chuyển trang dựa trên sự kiện click vào listProduct
const listProduct = document.querySelector(".listProduct");
listProduct.addEventListener("click", () => {
    if (nameGroup) {
        // Chuyển trang với keyword từ nameGroup
        window.location.href = `GetProductByCategoryNameController?keyword=${nameGroup}`;
    } else {
        // Trường hợp nameGroup chưa được chọn
        alert("Vui lòng chọn một nhóm sản phẩm trước!");
    }
});
