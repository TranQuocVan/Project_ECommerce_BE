function increaseQuantity(button) {
    var input = button.parentNode.querySelector('input[type=number]');
    var currentValue = parseInt(input.value);
    var maxValue = parseInt(input.getAttribute('max'));

    if (currentValue < maxValue) {
        input.value = currentValue + 1;
    } else {
        Swal.fire({
            icon: 'info',
            title: 'Thông báo',
            text: 'Số lượng sản phẩm đã đạt giới hạn tồn kho.',
            confirmButtonText: 'OK'
        });
    }

}

function decreaseQuantity(button) {
    var input = button.parentNode.querySelector('input[type=number]');
    var currentValue = parseInt(input.value);
    var minValue = parseInt(input.getAttribute('min'));

    if (currentValue > minValue) {
        input.value = currentValue - 1;
    } else {
        Swal.fire({
            icon: 'info',
            title: 'Thông báo',
            text: 'Không thể giảm số lượng dưới 1.',
            confirmButtonText: 'OK'
        });
    }
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
                form.submit();
            } else {
                console.error('Không tìm thấy form!');
            }
        }
    });
}

function orderButton(button, event) {
    // Ngăn hành vi mặc định của form
    event.preventDefault();

    // Lấy form từ nút bấm
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
        if (result.isConfirmed) {
            // Cập nhật giá trị `selectedItems` vào form trước khi submit
            updateSelectedItemsInForm();
            console.log('Updated selectedItems:', document.getElementById('selectedItems').value);

            // Người dùng nhấn "OK" -> Submit form
            form.submit();
        } else {
            // Người dùng nhấn "Hủy" -> Không làm gì
            console.log('Hủy thanh toán');
        }
    });
}

// function updateSelectedItemsInForm() {
//     const selectedItemsList = Array.from(selectedItems);
//     const selectedItemsInput = document.getElementById('selectedItems');
//     selectedItemsInput.value = selectedItemsList.join(',');
//     console.log('Selected items updated:', selectedItemsInput.value);
// }


    // Khai báo một Set để lưu trữ các sizeId đã chọn
    const selectedItems = new Set();

    function toggleSelection(checkbox) {
    const item = checkbox.closest('.items');
    const buttons = item.querySelectorAll('button');
    const input = item.querySelector('input[type="number"]');
    const sizeId = item.querySelector('input[type="hidden"]').value;

    if (checkbox.checked) {
    item.classList.add('selected');
    buttons.forEach(btn => (btn.style.display = 'none'));
    if (input) input.disabled = true;

    selectedItems.add(sizeId);
} else {
    item.classList.remove('selected');
    buttons.forEach(btn => (btn.style.display = ''));
    if (input) input.disabled = false;

    selectedItems.delete(sizeId);
}

    console.log('Selected sizeIds:', Array.from(selectedItems));
}

    function updateSelectedItemsInForm() {
    const selectedItemsList = Array.from(selectedItems);
    const selectedItemsInput = document.getElementById('selectedItems');
    selectedItemsInput.value = selectedItemsList.join(',');
}

//     document.addEventListener('DOMContentLoaded', function () {
//     const orderForm = document.getElementById('orderForm');
//
//     orderForm.onsubmit = function (event) {
//     updateSelectedItemsInForm();
//     console.log('Form submitted with selectedItems:', document.getElementById('selectedItems').value);
// };
// });








