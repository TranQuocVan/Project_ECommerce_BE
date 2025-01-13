function editStatus(buttonElement) {
    // Tìm phần tử cha gần nhất có class .row (hoặc class tương ứng với sản phẩm)
    const productRowElement = buttonElement.closest('.row');

    // Kiểm tra xem phần tử cha có tồn tại không
    if (!productRowElement) {
        console.error('Không tìm thấy phần tử cha cho sản phẩm');
        return;
    }

    // Lấy tất cả các phần tử có lớp .product trong sản phẩm cụ thể
    const productElements = productRowElement.querySelectorAll('.product');
    // Lấy tất cả các phần tử có lớp .edit-list trong sản phẩm cụ thể
    const editListElements = productRowElement.querySelectorAll('.edit-list');
    const confirmButton = productRowElement.querySelector('button.btn-success');

    // Hiển thị danh sách trạng thái và ẩn trạng thái hiện tại
    if (editListElements[0].style.display === "none") {
        // Ẩn tất cả các phần tử có lớp .product
        productElements.forEach(element => {
            element.style.display = "none";
        });

        // Hiện tất cả các phần tử có lớp .edit-list
        editListElements.forEach(element => {
            element.style.display = "block";
        });

        buttonElement.style.display = "none"; // Ẩn nút "Chỉnh sửa"
        confirmButton.style.display = "block"; // Hiện nút "Xác nhận"
    } else {
        // Hiện lại tất cả các phần tử có lớp .product
        productElements.forEach(element => {
            element.style.display = "block";
        });

        // Ẩn tất cả các phần tử có lớp .edit-list
        editListElements.forEach(element => {
            element.style.display = "none";
        });

        buttonElement.style.display = "block"; // Hiện lại nút "Chỉnh sửa"
        confirmButton.style.display = "none"; // Ẩn nút "Xác nhận"
    }
}


function submitForm(buttonElement) {
    // Tìm phần tử cha gần nhất có class .area-shoes
    const areaShoesElement = buttonElement.closest('.area-shoes');

    // Tìm form trong phần tử cha
    const form = areaShoesElement.querySelector('.edit-status-form');

    // Lấy giá trị của các input
    const nameInput = areaShoesElement.querySelector('#nameProduct');
    const priceInput = areaShoesElement.querySelector('#priceProduct');
    const discountInput = areaShoesElement.querySelector('#discountProduct');
    const stockInput = areaShoesElement.querySelector('#stockProduct');
    const sizeInput = areaShoesElement.querySelector('#sizeProduct');

    // Gán giá trị mặc định nếu input trống
    if (nameInput.value.trim() === "") {
        nameInput.value = ""; // Gán giá trị mặc định cho tên sản phẩm
    }

    if (priceInput.value.trim() === "") {
        priceInput.value = "0"; // Gán giá trị mặc định cho giá sản phẩm
    }

    if (discountInput.value.trim() === "") {
        discountInput.value = "0"; // Gán giá trị mặc định cho giảm giá
    }

    if (stockInput.value.trim() === "") {
        stockInput.value = "0"; // Gán giá trị mặc định cho số lượng
    }

    if (sizeInput.value.trim() === "") {
        sizeInput.value = ""; // Gán giá trị mặc định cho size
    }

    // Gửi form
    if (form) {
        form.submit();
    } else {
        console.error('Không tìm thấy form để gửi');
    }
}