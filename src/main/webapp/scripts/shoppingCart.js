function increaseQuantity(button) {
    var input = button.parentNode.querySelector('input[type=number]');
    var currentValue = parseInt(input.value);
    var maxValue = parseInt(input.getAttribute('max'));

    // Lấy container của item hiện tại
    var itemContainer = button.closest('.items');
    var sizeId = itemContainer.querySelector('input[name="sizeId"]').value;

    if (currentValue < maxValue) {
        input.value = currentValue + 1;

        // Gửi AJAX request để cập nhật số lượng
        updateQuantityInDatabase(sizeId, input.value);
    } else {
        Swal.fire({
            icon: 'info',
            title: 'Thông báo',
            text: 'Số lượng sản phẩm đã đạt giới hạn tồn kho.',
            confirmButtonText: 'OK'
        });
    }
    handleQuantityChange(input);
}

function decreaseQuantity(button) {
    var input = button.parentNode.querySelector('input[type=number]');
    var currentValue = parseInt(input.value);
    var minValue = parseInt(input.getAttribute('min'));

    // Lấy container của item hiện tại
    var itemContainer = button.closest('.items');
    var sizeId = itemContainer.querySelector('input[name="sizeId"]').value;

    if (currentValue > minValue) {
        input.value = currentValue - 1;

        // Gửi AJAX request để cập nhật số lượng
        updateQuantityInDatabase(sizeId, input.value);
    } else {
        Swal.fire({
            icon: 'info',
            title: 'Thông báo',
            text: 'Không thể giảm số lượng dưới 1.',
            confirmButtonText: 'OK'
        });
    }
    handleQuantityChange(input);
}


function updateQuantityInDatabase(sizeId, quantity) {
    $.ajax({
        url: '/Shoe_war_exploded/UpdateQuantityCartController', // URL đến server endpoint xử lý cập nhật
        type: 'POST',
        contentType: 'application/json', // Đảm bảo dữ liệu được gửi dưới dạng JSON
        data: JSON.stringify({ // Chuyển đổi dữ liệu sang JSON
            idSize: sizeId,
            quantity: quantity
        }),
        success: function(response) {
            // Xử lý nếu cập nhật thành công (ví dụ: hiển thị thông báo)
            console.log('Số lượng đã được cập nhật thành công.');
        },

    });
}




function handleDelete(button, nameProduct, nameSize) {
    // Hiển thị hộp thoại xác nhận
    Swal.fire({
        icon: 'warning',
        title: 'Bạn không thể hoàn tác',
        text: `Bạn có muốn xóa sản phẩm này ra khỏi giỏ hàng? (Tên: ${nameProduct}, Size: ${nameSize})`,
        showCancelButton: true,
        confirmButtonText: 'OK',
        cancelButtonText: 'Hủy'
    }).then((result) => {
        if (result.isConfirmed) {
            // Tìm form chứa nút bấm
            const form = button.closest('form');
            if (form) {
                updateSelectedItemsInForm();
                form.submit();
            } else {
                console.error('Không tìm thấy form!');
            }
        }
    });
}


function updateQuantityInForm() {
    const cartItems = document.querySelectorAll('.items');  // Lấy tất cả các sản phẩm trong giỏ hàng
    cartItems.forEach(item => {
        const sizeId = item.querySelector('input[type="hidden"]').value;  // Lấy sizeId của sản phẩm
        const quantity = item.querySelector('input[name="quantity"]').value;  // Lấy số lượng sản phẩm hiện tại

        // Tạo một hidden input trong form để gửi số lượng
        let input = document.querySelector(`#orderForm input[name="quantity_${sizeId}"]`);
        if (!input) {
            input = document.createElement('input');
            input.type = 'hidden';
            input.name = `quantity_${sizeId}`;  // Tạo tên input dựa trên sizeId
            document.getElementById('orderForm').appendChild(input);
        }
        input.value = quantity;  // Cập nhật số lượng vào hidden input
    });
}



function orderButton(button, event) {
    event.preventDefault();
    // Kiểm tra nếu không có sản phẩm nào được chọn
    if (selectedItems.size === 0) {
        Swal.fire({
            title: 'Thông báo',
            text: 'Bạn phải chọn ít nhất một sản phẩm để thanh toán!',
            icon: 'info',
            confirmButtonText: 'OK'
        });
        return; // Dừng thực thi nếu không có sản phẩm nào được chọn
    }
    updateQuantityInForm();

    const form = document.getElementById('orderForm');


    // Cập nhật giá trị 'totalAmount' vào hidden input
    const totalAmountText = document.getElementById('totalAmount').textContent.replace(/[^\d.-]/g, '');
    const totalAmountFloat = parseFloat(totalAmountText).toFixed(3); // 2 chữ số thập phân



    document.getElementById('hiddenTotalAmount').value = totalAmountFloat;

    // Hiển thị hộp thoại SweetAlert để xác nhận
    Swal.fire({
        title: 'Bạn có chắc chắn muốn thanh toán?',
        text: 'Hãy kiểm tra kỹ thông tin trước khi xác nhận!',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'OK',
        cancelButtonText: 'Hủy'
    }).then((result) => {
        if (result.isConfirmed ) {
            // Cập nhật giá trị `selectedItems` vào form trước khi submit
            updateSelectedItemsInForm();

            // Submit form
            form.submit();
        } else {
            console.log('Hủy thanh toán');
        }
    });
}




const selectedItems = new Set();
let totalPrice = 0;  // Khởi tạo tổng tiền
let deliveryFee = 0; // Khởi tạo phí giao hàng

// Hàm để tính tổng khi checkbox thay đổi hoặc số lượng thay đổi
function updateTotalPrice() {
    let total = 0;

    if (selectedItems.size === 0) {
        total = 0;
    }
    else {
        // Duyệt qua tất cả các item đã chọn
        selectedItems.forEach(sizeId => {
            const item = document.querySelector(`.items input[type="hidden"][value="${sizeId}"]`).closest('.items');
            const price = parseFloat(item.querySelector('.select-item').getAttribute('data-price').replace(/[^0-9.-]+/g, ""));
            const quantity = parseInt(item.querySelector('input[name="quantity"]').value, 10);  // Lấy số lượng từ input
            total += price * quantity;  // Cập nhật tổng tiền
        });

        // Cộng phí vận chuyển vào tổng tiền
        total += deliveryFee;
    }
    // Cập nhật tổng tiền vào phần hiển thị
    document.getElementById('totalAmount').textContent = new Intl.NumberFormat('vi-VN', {
        style: 'currency',
        currency: 'VND'
    }).format(total);
}

// Hàm xử lý thay đổi số lượng
function handleQuantityChange(input) {
    const item = input.closest('.items');
    const sizeId = item.querySelector('input[type="hidden"]').value;

    // Cập nhật tổng tiền khi số lượng thay đổi
    updateTotalPrice();
}

function toggleSelection(checkbox) {
    const item = checkbox.closest('.items');
    const sizeId = item.querySelector('input[type="hidden"]').value;




    // Cập nhật danh sách các item đã chọn
    if (checkbox.checked) {
        item.classList.add('selected');
        selectedItems.add(sizeId);
    } else {
        item.classList.remove('selected');
        selectedItems.delete(sizeId);
    }
    const totalCount = selectedItems.size; // Số lượng sản phẩm đã chọn
    document.getElementById('totalSelectedItems').textContent = `${totalCount} món`;

    console.log('Selected sizeIds:', Array.from(selectedItems));

    // Cập nhật tổng tiền sau mỗi lần thay đổi
    updateTotalPrice();
}

// Theo dõi sự thay đổi của select phương thức giao hàng
document.getElementById('deliverySelect').addEventListener('change', function() {
    const selectedOption = this.options[this.selectedIndex];  // Lấy option đã chọn
    deliveryFee = parseFloat(selectedOption.getAttribute('data-fee')) || 0;  // Lấy phí giao hàng từ thuộc tính data-fee

    // Cập nhật phí giao hàng lên màn hình
    document.getElementById('feeDisplay').textContent = `Phí vận chuyển: ${new Intl.NumberFormat('vi-VN', {
        style: 'currency',
        currency: 'VND'
    }).format(deliveryFee)}`;

    // Cập nhật lại tổng tiền sau khi thay đổi phí
    updateTotalPrice();
});

// Tính tổng tiền khi trang được tải, bao gồm phí giao hàng mặc định
window.addEventListener('load', function() {
    const defaultOption = document.querySelector('#deliverySelect option:first-child');  // Lấy phương thức giao hàng mặc định
    deliveryFee = parseFloat(defaultOption.getAttribute('data-fee')) || 0;  // Lấy phí giao hàng mặc định

    // Cập nhật phí giao hàng mặc định lên màn hình
    document.getElementById('feeDisplay').textContent = `Phí vận chuyển: ${new Intl.NumberFormat('vi-VN', {
        style: 'currency',
        currency: 'VND'
    }).format(deliveryFee)}`;
});






function updateSelectedItemsInForm() {
    const selectedItemsList = Array.from(selectedItems);
    const selectedItemsInput = document.getElementById('selectedItems');
    selectedItemsInput.value = selectedItemsList.join(',');
}



document.addEventListener('DOMContentLoaded', function () {
    // Hàm cập nhật giá ban đầu
    function updateFeeDisplay() {
        var selectedOption = document.getElementById('deliverySelect').options[document.getElementById('deliverySelect').selectedIndex];
        var fee = selectedOption.getAttribute('data-fee');

        // Định dạng số với dấu phân cách hàng nghìn
        var formattedFee = new Intl.NumberFormat('vi-VN').format(fee);

        // Hiển thị giá với dấu phân cách và "đ" ở cuối
        document.getElementById('feeDisplay').innerText = 'Phí vận chuyển: ' + formattedFee + 'đ';
    }

    // Gọi hàm để cập nhật giá ngay khi trang tải xong
    updateFeeDisplay();

    // Thêm sự kiện thay đổi khi người dùng chọn option khác
    document.getElementById('deliverySelect').addEventListener('change', function() {
        updateFeeDisplay();
    });
});








