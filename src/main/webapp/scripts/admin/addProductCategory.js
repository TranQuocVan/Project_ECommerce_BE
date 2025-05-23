import { refreshToken, popUpError } from '../service.js';
// function toBase64(file) {
//     return new Promise((resolve, reject) => {
//         const reader = new FileReader();
//         reader.onload = () => resolve(reader.result.split(',')[1]); // Lấy phần base64 sau dấu phẩy
//         reader.onerror = reject;
//         reader.readAsDataURL(file);
//     });
// }

// const form = () => {
//     document.getElementById('categoryForm').addEventListener('submit', async function (e) {
//         e.preventDefault();
//
//         const name = document.getElementById('name').value;
//         const description = document.getElementById('description').value;
//         const pictureFile = document.getElementById('picture').files[0];
//         const messageEl = document.getElementById('message');
//
//         try {
//             // Chuyển file ảnh sang Base64
//             const pictureBase64 = await toBase64(pictureFile);
//
//             // Đóng gói dữ liệu JSON
//             const data = {
//                 Name: name,
//                 Description: description,
//                 Picture: pictureBase64
//             };
//
//             // Gửi dữ liệu đến API
//             const response = await fetch('../addCategory', {
//                 method: 'POST',
//                 headers: {
//                     'Content-Type': 'application/json'
//                 },
//                 credentials: "include",
//                 body: JSON.stringify(data)
//             });
//
//             if (response.status === 401) {
//                 const success = await refreshToken();
//                 if (success) return form();
//                 return popUpError("Please log in to update the quantity.");
//             }
//
//             if (response.ok) {
//                 const result = await response.json();
//                 messageEl.textContent = 'Category added successfully!';
//                 messageEl.style.color = 'green';
//             } else {
//                 const error = await response.json();
//                 messageEl.textContent = `Error: ${error.Error || 'Something went wrong'}`;
//                 messageEl.style.color = 'red';
//             }
//         } catch (error) {
//             messageEl.textContent = `Error: ${error.message}`;
//             messageEl.style.color = 'red';
//         }
//     });
// }
// form();


document.getElementById('picture').addEventListener('change', function (event) {
    const fileInput = event.target;

    // Check the number of selected files
    const fileCount = fileInput.files.length;

    const upload = document.querySelector('.upload');

    if (fileCount > 0) {
        upload.innerHTML = "File selected";
        upload.style.color = "green";
        upload.style.border = "3px solid green";
    } else {
        upload.innerHTML = "File selected is not valid";
        upload.style.border = "3px solid red";
    }
});

document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("addCatalogForm").addEventListener("submit", function (event) {
        event.preventDefault(); // Ngăn chặn reload trang

        let formData = new FormData(this);

        fetch("../AddCatalogController", {
            method: "POST",
            body: new URLSearchParams(formData), // Chuyển FormData thành URL-encoded
            headers: {
                "Content-Type": "application/x-www-form-urlencoded",
            },
        })
            .then(response => response.json())
            .then(data => {
                let messageBox = document.getElementById("message");
                if (data.success) {
                    messageBox.innerHTML = `<span style="color: green;">${data.message}</span>`;
                    document.getElementById("name").value = ""; // Xóa input sau khi thêm
                    document.getElementById("description").value = "";
                } else {
                    messageBox.innerHTML = `<span style="color: red;">${data.message}</span>`;
                }
            })
            .catch(() => {
                document.getElementById("message").innerHTML = `<span style="color: red;">Lỗi khi gửi yêu cầu!</span>`;
            });
    });
});

