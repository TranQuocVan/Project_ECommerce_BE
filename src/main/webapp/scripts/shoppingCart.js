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
            updateTotalPrice();
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
        updateTotalPrice();
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

    let paymentId = parseInt(document.getElementById("paymentId").value, 10);
    // Gán giá trị voucher vào input hidden
    let selectedShippingInput = document.querySelector('input[name="selectedVoucherShipping"]:checked');
    let selectedItemInput = document.querySelector('input[name="selectedVoucherItems"]:checked');

    document.getElementById("selectedVoucherShipping").value = selectedShippingInput ? selectedShippingInput.value : "0";
    document.getElementById("selectedVoucherItems").value = selectedItemInput ? selectedItemInput.value : "0";
    let form = document.getElementById("orderForm");

    if (paymentId === 1) {
        form.action = "OrderController";
    } else if (paymentId === 2) {
        form.action = "VnpayPaymentController";
    }

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

            form.submit();
        } else {
            console.log('Hủy thanh toán');
        }
    });
}




const selectedItems = new Set();
let totalPrice = 0;  // Khởi tạo tổng tiền


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


// VOUCHER
let originalTotalAmount = 0;
let discountShipping = 0;
let discountItems = 0;

function updateTotalPrice() {
    let total = 0;

    // Kiểm tra nếu không có sản phẩm nào được chọn
    if (selectedItems.size === 0) {
        document.getElementById('totalAmount').textContent = formatPrice(0);
        document.getElementById('totalAmountModalVoucher').textContent = formatPrice(0);
        return; // Dừng lại không tính tiếp nếu không có sản phẩm nào được chọn
    }

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

    // Lưu giá trị tổng tiền gốc (chưa áp dụng giảm giá)
    originalTotalAmount = total;

    // **Cập nhật hiển thị tổng tiền đã áp dụng giảm giá**
    let finalTotal = originalTotalAmount - discountShipping - discountItems;

    document.getElementById('totalAmount').textContent = formatPrice(finalTotal);
    document.getElementById('totalAmountModalVoucher').textContent = formatPrice(finalTotal);
}

function selectVoucherShipping(row) {
    // Kiểm tra nếu không có sản phẩm nào được chọn
    if (selectedItems.size === 0) {
        return; // Dừng lại nếu không có sản phẩm nào được chọn
    }

    let isSelected = row.style.backgroundColor === "rgb(144, 238, 144)";

    if (isSelected) {
        row.style.backgroundColor = "";
        let radio = row.querySelector('input[type="radio"]');
        if (radio) {
            radio.checked = false;
        }

        discountShipping = 0;
        document.querySelector("#discountFee").textContent = "0đ";
        document.querySelector("#discountFee").classList.remove("text-danger");

    } else {
        let table = row.closest("table");
        table.querySelectorAll("tr").forEach(tr => tr.style.backgroundColor = "");

        let radio = row.querySelector('input[type="radio"]');
        if (radio) {
            radio.checked = true;
            row.style.backgroundColor = "#90EE90";
        }

        let discountPercent = parseFloat(row.querySelector("td:nth-child(2) div:nth-child(1)").textContent.replace(/[^\d]/g, ""));
        let discountMaxValue = parseFloat(row.querySelector("td:nth-child(2) div:nth-child(2)").textContent.replace(/[^\d]/g, ""));
        const deliveryFee = parseFloat(document.querySelector("#feeDisplay").textContent.replace(/[.,đ\s]+/g, ""));

        discountShipping = Math.min((deliveryFee * discountPercent) / 100, discountMaxValue);
        document.querySelector("#discountFee").textContent = `-${discountShipping.toLocaleString("vi-VN")}đ`;
        document.querySelector("#discountFee").classList.add("text-danger");
    }

    updateFinalTotal();
}

function selectVoucherItems(row) {
    // Kiểm tra nếu không có sản phẩm nào được chọn
    if (selectedItems.size === 0) {
        return; // Dừng lại nếu không có sản phẩm nào được chọn
    }

    let isSelected = row.style.backgroundColor === "rgb(144, 238, 144)";

    if (isSelected) {
        row.style.backgroundColor = "";
        let radio = row.querySelector('input[type="radio"]');
        if (radio) {
            radio.checked = false;
        }

        discountItems = 0;
        document.querySelector("#discountItems").textContent = "0đ";
        document.querySelector("#discountItems").classList.remove("text-danger");

    } else {
        let table = row.closest("table");
        table.querySelectorAll("tr").forEach(tr => tr.style.backgroundColor = "");

        let radio = row.querySelector('input[type="radio"]');
        if (radio) {
            radio.checked = true;
            row.style.backgroundColor = "#90EE90";
        }

        let total = 0;
        let discountPercent = parseFloat(row.querySelector("td:nth-child(2) div:nth-child(1)").textContent.replace(/[^\d]/g, ""));
        let discountMaxValue = parseFloat(row.querySelector("td:nth-child(2) div:nth-child(2)").textContent.replace(/[^\d]/g, ""));

        selectedItems.forEach(sizeId => {
            const item = document.querySelector(`.items input[type="hidden"][value="${sizeId}"]`).closest('.items');
            const price = parseFloat(item.querySelector('.select-item').getAttribute('data-price').replace(/[^0-9.-]+/g, ""));
            const quantity = parseInt(item.querySelector('input[name="quantity"]').value, 10);
            total += price * quantity;
        });

        discountItems = Math.min((total * discountPercent) / 100, discountMaxValue);
        document.querySelector("#discountItems").textContent = `-${discountItems.toLocaleString("vi-VN")}đ`;
        document.querySelector("#discountItems").classList.add("text-danger");
    }

    updateFinalTotal();
}

function updateFinalTotal() {
    let finalTotal = originalTotalAmount - discountShipping - discountItems;
    document.getElementById('totalAmountModalVoucher').textContent = formatPrice(finalTotal);
}

function confirmVoucher() {
    Swal.fire({
        title: 'Bạn có chắc chắn chọn các Voucher này?',
        text: 'Hãy kiểm tra kỹ thông tin trước khi xác nhận!',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'OK',
        cancelButtonText: 'Hủy'
    }).then((result) => {
        if (result.isConfirmed) {
            let finalTotal = parseFloat(document.getElementById('totalAmountModalVoucher').textContent.replace(/[.,đ\s]+/g, ""));

            // Cập nhật totalAmount với giá trị đã trừ giảm giá
            document.getElementById('totalAmount').textContent = formatPrice(finalTotal);

            // **Lưu lại giảm giá để trừ khi thay đổi số lượng sản phẩm**
            discountShipping = parseFloat(document.querySelector("#discountFee").textContent.replace(/[^\d]/g, "")) || 0;
            discountItems = parseFloat(document.querySelector("#discountItems").textContent.replace(/[^\d]/g, "")) || 0;

            let selectedVouchers = [];

            // Lấy voucher vận chuyển
            let selectedShipping = document.querySelector('input[name="selectedVoucherShipping"]:checked');
            if (selectedShipping) {
                let row = selectedShipping.closest("tr");
                let discountText = row.querySelector("td:nth-child(2) div:nth-child(1)").textContent;
                let discountMaxValue = row.querySelector("td:nth-child(2) div:nth-child(2)").textContent;
                let quantity = row.querySelector("td:nth-child(2) div:nth-child(3)").textContent;

                selectedVouchers.push(`
                    <tr style="cursor: pointer">
                        <td><i class="fa-solid fa-truck-fast" style="font-size: 40px"></i></td>
                        <td>
                            <div style="font-weight: 600">Giảm giá vận chuyển</div>
                            <div>${discountText}</div>
                            <div>${discountMaxValue}</div>
                        </td>
                    </tr>
                `);
            }

            // Lấy voucher sản phẩm
            let selectedItem = document.querySelector('input[name="selectedVoucherItems"]:checked');
            if (selectedItem) {
                let row = selectedItem.closest("tr");
                let discountText = row.querySelector("td:nth-child(2) div:nth-child(1)").textContent;
                let discountMaxValue = row.querySelector("td:nth-child(2) div:nth-child(2)").textContent;

                selectedVouchers.push(`
                    <tr style="cursor: pointer">
                        <td><i class="fa-solid fa-bag-shopping" style="font-size: 40px"></i></td>
                        <td>
                            <div style="font-weight: 600">Giảm giá vận chuyển</div>
                            <div>${discountText}</div>
                            <div>${discountMaxValue}</div>
                        </td>
                    </tr>
                `);
            }

            // Nếu không có voucher nào được chọn
            if (selectedVouchers.length === 0) {
                document.getElementById("voucherSelected").innerHTML = ``;
            } else {
                // Cập nhật vào div voucherSelected
                document.getElementById("voucherSelected").innerHTML = `
                    <br>
                    <h5 class=" mb-3">Voucher đang sử dụng</h5>
                    <table class="table table-hover">
                        <tbody>${selectedVouchers.join("")}</tbody>
                    </table>
                `;
            }

            // Hiển thị thông báo thành công
            Swal.fire({
                title: 'Xác nhận thành công!',
                text: 'Voucher của bạn đã được áp dụng thành công.',
                icon: 'success',
                confirmButtonColor: '#3085d6',
                confirmButtonText: 'OK'
            });
        } else {
            // Nếu chọn "Hủy" thì mở lại modal
            let myModal = new bootstrap.Modal(document.getElementById('myModal'));
            myModal.show();
        }
    });
}



function highlightRow(radio) {
    // Xóa màu nền của các hàng trong cùng danh sách
    let table = radio.closest("table");
    table.querySelectorAll("tr").forEach(tr => tr.style.backgroundColor = "");

    // Lấy hàng chứa radio và đặt màu nền
    let row = radio.closest("tr");
    if (row) {
        row.style.backgroundColor = "#90EE90";
    }
}

// Kiểm tra có lựa chọn sản phẩm chưa
function handleChooseVoucher() {
    const totalCount = selectedItems.size; // Lấy số lượng sản phẩm đã chọn

    if (totalCount === 0) {
        Swal.fire({
            title: 'Vui lòng chọn sản phẩm cần mua trước khi chọn Voucher!',
            text: 'Hãy kiểm tra kỹ thông tin trước khi xác nhận!',
            icon: 'warning',
            confirmButtonColor: '#3085d6',
            confirmButtonText: 'OK'
        });
    } else {
        // Nếu tổng tiền > 0 thì mở modal
        let myModal = new bootstrap.Modal(document.getElementById('myModal'));
        myModal.show();
    }
}

function submitVoucher() {
    // Lấy phương thức thanh toán
    let paymentId = parseInt(document.getElementById("paymentId").value, 10);

    // Lấy voucher đã chọn
    let selectedShipping = document.querySelector('input[name="selectedVoucherShipping"]:checked');
    let selectedItems = document.querySelector('input[name="selectedVoucherItems"]:checked');

    // Gán giá trị vào input ẩn để gửi đi
    document.getElementById('selectedVoucherShippingHidden').value = selectedShipping ? selectedShipping.value : "";
    document.getElementById('selectedVoucherItemsHidden').value = selectedItems ? selectedItems.value : "";

    // Tạo đối tượng FormData
    let formData = new FormData(document.getElementById('voucherForm'));

    // Xác định URL dựa vào paymentId
    let url = (paymentId === 1) ? '/OrderController' : '/VnpayPaymentController';

    // Gửi yêu cầu AJAX (POST)
    fetch(url, {
        method: 'POST',
        body: formData
    })
        .then(response => response.json()) // Giả sử bạn muốn trả về JSON
        .then(data => {
            console.log("Dữ liệu gửi đi thành công:", data);
            // Xử lý kết quả từ server (ví dụ: cập nhật giỏ hàng)
        })
        .catch(error => {
            console.error("Có lỗi xảy ra:", error);
        });
}