document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("addCategoryForm");
    const fileInput = document.getElementById("picture");
    const fileLabel = document.querySelector("label[for='picture']");
    const messageBox = document.getElementById("message");

    form.addEventListener("submit", function (event) {
        event.preventDefault(); // Ngăn chặn reload trang

        let formData = new FormData(form);

        fetch("../AddGroupController", {
            method: "POST",
            body: formData,
        })
            .then(response => response.json())  // Đọc dữ liệu JSON từ backend
            .then(data => {
                if (data.success) {
                    messageBox.innerHTML = `<span style="color: green;">${data.message}</span>`;
                    form.reset();
                    fileLabel.style.backgroundColor = ""; // Reset màu sau khi submit
                    fileLabel.style.color = "";
                } else {
                    messageBox.innerHTML = `<span style="color: red;">${data.message}</span>`;
                }
            })
            .catch(() => {
                messageBox.innerHTML = `<span style="color: red;">Lỗi khi gửi yêu cầu!</span>`;
            });
    });

    // Change label color when a file is selected
    fileInput.addEventListener("change", function () {
        if (fileInput.files.length > 0) {
            fileLabel.style.borderColor = "#28a745"; // Green background when a file is selected
            fileLabel.textContent = `File selected: ${fileInput.files[0].name}`;
        } else {
            fileLabel.style.backgroundColor = ""; // Reset to default when no file is selected
            fileLabel.textContent = `Upload Picture`;
            fileLabel.style.borderColor = "#000000"; // Green background when a file is selected
        }
    });

});
