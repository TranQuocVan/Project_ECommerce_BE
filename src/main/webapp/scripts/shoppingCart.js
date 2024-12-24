function increaseQuantity(button) {
    var input = button.parentNode.querySelector('input[type=number]');
    var currentValue = parseInt(input.value);
    var maxValue = parseInt(input.getAttribute('max'));

    if (currentValue < maxValue) {
        input.value = currentValue + 1;
    } else {
        Swal.fire({
            icon: 'warning',
            title: 'Lỗi!',
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
    }
    else {
        Swal.fire({
            icon: 'warning',
            title: 'Lỗi!',
            text: 'Không thể giảm số lượng dưới 1.',
            confirmButtonText: 'OK'
        });
    }
}
