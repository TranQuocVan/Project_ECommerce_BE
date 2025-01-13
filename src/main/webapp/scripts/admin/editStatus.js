function editStatus(buttonElement) {
    // Tìm phần tử cha gần nhất có class .area-shoes
    const areaShoesElement = buttonElement.closest('.area-shoes');

    // Kiểm tra xem phần tử cha có tồn tại không
    if (!areaShoesElement) {
        console.error('Không tìm thấy phần tử cha .area-shoes');
        return;
    }

    // Lấy phần tử trạng thái hiện tại
    const statusElement = areaShoesElement.querySelector('p.status');

    // Kiểm tra xem phần tử trạng thái có tồn tại không
    if (!statusElement) {
        console.error('Không tìm thấy trạng thái trong phần tử');
        return;
    }

    // Lấy phần tử danh sách trạng thái
    const statusListElement = areaShoesElement.querySelector('.status-list');
    const confirmButton = areaShoesElement.querySelector('button.btn-success');

    // Hiển thị danh sách trạng thái và ẩn trạng thái hiện tại
    if (statusListElement.style.display === "none") {
        statusListElement.style.display = "block";
        statusElement.style.display = "none"; // Ẩn trạng thái hiện tại
        buttonElement.style.display = "none"; // Ẩn nút "Chỉnh sửa"
        confirmButton.style.display = "block"; // Hiện nút "Xác nhận"
    } else {
        statusListElement.style.display = "none";
        statusElement.style.display = "block"; // Hiện lại trạng thái nếu cần
        buttonElement.style.display = "block"; // Hiện lại nút "Chỉnh sửa"
        confirmButton.style.display = "none"; // Ẩn nút "Xác nhận"
    }
}
function updateHiddenStatus(selectElement) {
    // Lấy giá trị đã chọn từ dropdown
    const selectedValue = selectElement.value;

    // Cập nhật giá trị của trường ẩn
    const hiddenInput = selectElement.closest('.edit-status-form').querySelector('#nameStatus');
    hiddenInput.value = selectedValue;
}

function submitForm(buttonElement) {
    // Tìm phần tử cha gần nhất có class .area-shoes
    const areaShoesElement = buttonElement.closest('.area-shoes');

    // Tìm form trong phần tử cha
    const form = areaShoesElement.querySelector('.edit-status-form');

    // Gửi form
    if (form) {
        form.submit();
    } else {
        console.error('Không tìm thấy form để gửi');
    }
}