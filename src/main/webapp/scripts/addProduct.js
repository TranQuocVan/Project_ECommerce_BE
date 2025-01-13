let colorIndex = 1; // Start with the second color

function addColor() {
    const colorContainer = document.getElementById("colorContainer");
    const newColor = `
        <div class="color" data-color-index="${colorIndex}">
            <label for="colorName${colorIndex}">Color Name:</label>
            <input type="text" id="colorName${colorIndex}" name="colorName${colorIndex}" required><br>

            <label for="hexCode${colorIndex}">Hex Code:</label>
            <input type="color" id="hexCode${colorIndex}" name="hexCode${colorIndex}" required><br>

            <h3>Sizes</h3>
            <div class="sizeContainer" data-size-index="0">
                <label>Size:</label>
                <input type="text" name="size${colorIndex}[]" required><br>

                <label>Stock:</label>
                <input type="number" name="stock${colorIndex}[]" required><br>
            </div>
            <button type="button" onclick="addSize(this)">Add Size</button>

            <h3>Images</h3>
            <input type="file" name="image${colorIndex}[]" accept="image/*" onchange="previewImage(event, ${colorIndex})"><br>
            <button type="button" onclick="addImage(this, ${colorIndex})">Add Image</button>
            <div id="imagePreview${colorIndex}" class="image-preview-container"></div> <!-- Preview Container -->
        </div>`;
    colorContainer.insertAdjacentHTML("beforeend", newColor);
    colorIndex++;
}

function addSize(button) {
    const colorDiv = button.closest(".color");
    const colorIndex = colorDiv.dataset.colorIndex;
    const sizeContainer = colorDiv.querySelector(".sizeContainer");
    const newSize = `
        <label>Size:</label>
        <input type="text" name="size${colorIndex}[]" required><br>

        <label>Stock:</label>
        <input type="number" name="stock${colorIndex}[]" required><br>`;
    sizeContainer.insertAdjacentHTML("beforeend", newSize);
}

function addImage(button, colorIndex) {
    const colorDiv = button.closest(".color");
    const newImage = `
        <input type="file" name="image${colorIndex}[]" accept="image/*" onchange="previewImage(event, ${colorIndex})"><br>`;
    button.insertAdjacentHTML("beforebegin", newImage);
}

// Preview Image Function
// function previewImage(event, colorIndex) {
//     const file = event.target.files[0];
//     const previewContainer = document.getElementById(`imagePreview${colorIndex}`); // Corrected ID reference
//
//     if (file && file.type.startsWith('image/')) {
//         const reader = new FileReader();
//
//         reader.onload = function(e) {
//             // Create the image element
//             const imgElement = document.createElement('img');
//             imgElement.src = e.target.result;
//             imgElement.alt = file.name;
//
//             // Create a remove button
//             const removeBtn = document.createElement('div');
//             removeBtn.classList.add('remove-btn');
//             removeBtn.innerHTML = '×';
//             removeBtn.onclick = function() {
//                 imgElement.remove(); // Remove the image
//                 removeBtn.remove(); // Remove the remove button
//                 // Optionally clear the file input here if you want to reset it
//                 event.target.value = ''; // Reset the input
//             };
//
//             // Add the image and remove button to the container
//             const imageWrapper = document.createElement('div');
//             imageWrapper.style.position = 'relative';
//             imageWrapper.appendChild(imgElement);
//             imageWrapper.appendChild(removeBtn);
//             previewContainer.appendChild(imageWrapper); // Append without clearing previous previews
//         };
//
//         reader.readAsDataURL(file); // Read the image as a data URL
//     }
// }
// Preview Image Function
// Preview Image Function
// Preview Image Function
function previewImage(event, colorIndex) {
    const file = event.target.files[0];
    const previewContainer = document.getElementById(`imagePreview${colorIndex}`);

    if (file && file.type.startsWith('image/')) {
        const reader = new FileReader();

        reader.onload = function(e) {
            // Xóa hình ảnh cũ
            const oldImages = document.querySelectorAll('.old-image');
            oldImages.forEach(img => img.remove()); // Xóa tất cả hình ảnh cũ

            // Tạo phần tử hình ảnh mới
            const imgElement = document.createElement('img');
            imgElement.src = e.target.result;
            imgElement.alt = file.name;
            imgElement.classList.add('product-image'); // Thêm lớp CSS

            // Thêm hình ảnh mới vào container
            previewContainer.innerHTML = ''; // Xóa nội dung cũ trong preview container
            previewContainer.appendChild(imgElement); // Thêm hình ảnh mới vào container
        };

        reader.readAsDataURL(file); // Đọc hình ảnh dưới dạng URL dữ liệu
    }
}


