async function increaseQuantity(button, event) {
    event.stopPropagation();

    // Get the input field and calculate the new quantity
    var input = button.parentNode.querySelector('input[type=number]');
    var currentValue = parseInt(input.value) + 1;

    // Get the sizeId from the current item's container
    var itemContainer = button.closest('.items');
    var sizeId = itemContainer.querySelector('input[name="sizeId"]').value;

    try {
        // Wait for the database update result
        const isUpdated = await updateQuantityInDatabase(sizeId, currentValue);
        console.log(isUpdated)

        if (isUpdated) {
            input.value = currentValue; // Update the input field with the new quantity
        } else {
            // Show a message if the update fails due to inventory limits
            Swal.fire({
                icon: 'info',
                title: 'Thông báo',
                text: 'Số lượng sản phẩm đã đạt giới hạn tồn kho.',
                confirmButtonText: 'OK'
            });
        }

        // Call the quantity change handler regardless of success or failure
        handleQuantityChange(input);
    } catch (error) {
        // Handle errors (e.g., network issues)
        console.error('Failed to update quantity:', error);
        Swal.fire({
            icon: 'error',
            title: 'Lỗi',
            text: 'Đã xảy ra lỗi khi cập nhật số lượng. Vui lòng thử lại sau.',
            confirmButtonText: 'OK'
        });
    }
}

function decreaseQuantity(button,event) {
    event.stopPropagation();
    var input = button.parentNode.querySelector('input[type=number]');
    var currentValue = parseInt(input.value);
    var minValue = parseInt(input.getAttribute('min'));

    // Lấy container của item hiện tại
    var itemContainer = button.closest('.items');
    var sizeId = itemContainer.querySelector('input[name="sizeId"]').value;

    if (currentValue > minValue) {
        input.value = currentValue - 1;

        // Gửi AJAX request để cập nhật số lượng
        updateQuantityInDatabase(sizeId, input.value,true);
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


function updateQuantityInDatabase(sizeId, quantity,isDecreaseQuantity = false) {
    return new Promise((resolve, reject) => {
        $.ajax({
            url: '/Shoe_war_exploded/UpdateQuantityCartController', // Server endpoint
            type: 'POST',
            contentType: 'application/json', // Ensure data is sent as JSON
            data: JSON.stringify({
                idSize: sizeId,
                quantity: quantity,
                isDecreaseQuantity: isDecreaseQuantity, // This will always be true, no need to pass as a parameter
            }),
            success: function (response) {
                if (response.status === "ok") {
                    resolve(true); // Resolve promise with true if the operation was successful
                } else {
                    console.warn('Request failed due to server-side validation:', response.message);
                    resolve(false); // Resolve promise with false if the operation failed
                }
            },
            error: function (xhr, status, error) {
                console.error('Request error:', {
                    status: status,
                    error: error,
                    responseText: xhr.responseText
                });
                reject(false); // Reject promise with false in case of an error
            }
        });
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


function updateTotalPrice() {
    let total = 0;

    // Tính tổng từ các sản phẩm đã chọn
    selectedItems.forEach(sizeId => {
        const item = document.querySelector(`.items input[type="hidden"][value="${sizeId}"]`).closest('.items');
        const price = parseFloat(item.querySelector('.select-item').getAttribute('data-price').replace(/[^0-9.-]+/g, ""));
        const quantity = parseInt(item.querySelector('input[name="quantity"]').value, 10);
        total += price * quantity;
    });

    // Lấy phí vận chuyển
    const feeDisplayText = document.querySelector("#feeDisplay").textContent;
    const deliveryFee = parseFloat(feeDisplayText.replace(/[.,đ\s]+/g, ""));
    total += deliveryFee;


    // Cập nhật hiển thị tổng tiền
    document.getElementById('totalAmount').textContent = formatPrice(total);
}
// Hàm xử lý thay đổi số lượng
function handleQuantityChange(input) {
    const item = input.closest('.items');
    const sizeId = item.querySelector('input[type="hidden"]').value;

    // Cập nhật tổng tiền khi số lượng thay đổi
    updateTotalPrice();
}



const itemShoppingCart = document.querySelectorAll(".items")
itemShoppingCart.forEach(item => {
    item.addEventListener("click", () => {
        const checkbox = item.querySelector(".checkbox"); // Tìm checkbox trong item
        const sizeId = item.querySelector('input[type="hidden"]').value; // Lấy sizeId từ input hidden

        if (selectedItems.has(sizeId)) {
            // Nếu sizeId đã được chọn, bỏ chọn
            selectedItems.delete(sizeId); // Xóa sizeId khỏi danh sách
            item.classList.remove("selected"); // Xóa class "selected"

        } else {
            // Nếu sizeId chưa được chọn, thêm vào danh sách
            selectedItems.add(sizeId); // Thêm sizeId vào danh sách
            item.classList.add("selected"); // Thêm class "selected"

        }

        // Cập nhật tổng số mục đã chọn
        const totalCount = selectedItems.size;
        document.getElementById("totalSelectedItems").textContent = `${totalCount} món`;



        // Cập nhật tổng tiền
        updateTotalPrice();
    });
});

// Theo dõi sự thay đổi của select phương thức giao hàng

document.getElementById('deliverySelect').addEventListener('change', function () {
    const selectedOption = this.options[this.selectedIndex];  // Lấy option đã chọn
    const deliveryFee = parseFloat(selectedOption.getAttribute('data-fee')) || 0;  // Lấy phí giao hàng

    // Cập nhật phí giao hàng lên màn hình
    document.getElementById('feeDisplay').textContent = formatPrice(deliveryFee);

    // Cập nhật lại tổng tiền sau khi thay đổi phí
    updateTotalPrice();
});

// Tính tổng tiền khi trang được tải, bao gồm phí giao hàng mặc định
window.addEventListener('load', function () {
    // Lấy phương thức giao hàng mặc định
    const defaultOption = document.querySelector('#deliverySelect option:first-child');
    const deliveryFee = parseFloat(selectedOption.getAttribute('data-fee')) || 0;

    // Định dạng và hiển thị phí giao hàng mặc định
    document.getElementById('feeDisplay').textContent = formatPrice(deliveryFee);

    // Cập nhật tổng tiền
    updateTotalPrice();
});

function formatPrice(price) {
    return `${new Intl.NumberFormat('vi-VN', { style: 'decimal', minimumFractionDigits: 0 }).format(price)}đ`;
}



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
        var formattedFee = formatPrice(parseFloat(fee));

        // Hiển thị giá với dấu phân cách và "đ" ở cuối
        document.getElementById('feeDisplay').innerText = formattedFee;
    }
    // Gọi hàm để cập nhật giá ngay khi trang tải xong
    updateFeeDisplay();

    // Thêm sự kiện thay đổi khi người dùng chọn option khác
    document.getElementById('deliverySelect').addEventListener('change', function() {
        updateFeeDisplay();
    });
});











