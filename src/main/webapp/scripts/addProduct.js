let colorIndex = 1; // Start with the second color

function addColor() {
    const colorContainer = document.getElementById("colorContainer");
    const newColor = `
                <div class="color" data-color-index="${colorIndex}">
                    <label for="colorName${colorIndex}">Color Name:</label>
                    <input type="text" id="colorName${colorIndex}" name="colorName${colorIndex}" required><br>

                    <label for="hexCode${colorIndex}">Hex Code:</label>
                    <input type="text" id="hexCode${colorIndex}" name="hexCode${colorIndex}" required><br>

                    <h3>Sizes</h3>
                    <div class="sizeContainer" data-size-index="0">
                        <label>Size:</label>
                        <input type="text" name="size${colorIndex}[]" required><br>

                        <label>Stock:</label>
                        <input type="number" name="stock${colorIndex}[]" required><br>
                    </div>
                    <button type="button" onclick="addSize(this)">Add Size</button>

                    <h3>Images</h3>
                    <input type="file" name="image${colorIndex}[]" accept="image/*"><br>
                    <button type="button" onclick="addImage(this)">Add Image</button>
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

function addImage(button) {
    const colorDiv = button.closest(".color");
    const colorIndex = colorDiv.dataset.colorIndex;
    const newImage = `
                <input type="file" name="image${colorIndex}[]" accept="image/*"><br>`;
    button.insertAdjacentHTML("beforebegin", newImage);
}