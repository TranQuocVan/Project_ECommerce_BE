function addSize() {
    const sizesContainer = document.getElementById('sizesContainer');
    const sizeGroup = document.createElement('div');
    sizeGroup.className = 'form-group size-group';
    sizeGroup.innerHTML = `
        <label>Size:</label>
        <input type="text" class="form-control d-inline-block w-25" name="sizes[]" required>
        <label>Số lượng:</label>
        <input type="number" class="form-control d-inline-block w-25" name="quantities[]" required>
        <button type="button" class="btn btn-danger" onclick="removeSize(this)">Xóa</button>
    `;
    sizesContainer.appendChild(sizeGroup);
}

function removeSize(button) {
    const sizeGroup = button.parentElement;
    sizeGroup.remove();
}

document.getElementById('productImages').addEventListener('change', function (event) {
    const imagePreview = document.getElementById('imagePreview');
    imagePreview.innerHTML = '';
    const files = event.target.files;
    for (let i = 0; i < files.length; i++) {
        const file = files[i];
        const reader = new FileReader();
        reader.onload = function (e) {
            const img = document.createElement('img');
            img.src = e.target.result;
            img.className = 'preview-img';
            imagePreview.appendChild(img);
        }
        reader.readAsDataURL(file);
    }
});

document.getElementById('productForm').addEventListener('submit', function (event) {
    event.preventDefault();
    // Xử lý dữ liệu form ở đây
    alert('Sản phẩm đã được thêm!');
});