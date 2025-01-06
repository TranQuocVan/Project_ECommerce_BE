


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


function handleDelete(button, nameProduct,nameSize) {
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

function orderButton(button) {
    // Hiển thị hộp thoại xác nhận
    Swal.fire({
        icon: 'question',
        title: 'Bạn không thể hoàn tác',
        text: `Bạn có muốn thanh toán các sản phẩm này không?`,
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
