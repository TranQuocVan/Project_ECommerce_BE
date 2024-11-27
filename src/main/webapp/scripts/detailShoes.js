document.querySelectorAll('.color-button').forEach(button => {
    button.addEventListener('click', function () {
        document.querySelectorAll('.color-button').forEach(btn => {
            btn.classList.remove('active');
        });
        this.classList.add('active');
    });

    button.addEventListener('mousedown', () => {
        button.style.transform = 'translateY(4px)';
    });

    button.addEventListener('mouseup', () => {
        button.style.transform = 'translateY(0)';
    });

    button.addEventListener('mouseleave', () => {
        button.style.transform = 'translateY(0)';
    });
});
document.querySelectorAll('.size-box').forEach(box => {
    box.addEventListener('click', function () {
        // Xóa class active từ tất cả các color-box
        document.querySelectorAll('.size-box').forEach(b => b.classList.remove('active'));
        // Thêm class active vào box được click
        this.classList.add('active');
    });
});

// Thêm code xử lý thumbnail
document.querySelectorAll('.thumbnail-item').forEach((thumbnail, index) => {
    // Đặt thumbnail đầu tiên là active
    if (index === 0) {
        thumbnail.classList.add('active');
    }

    thumbnail.addEventListener('click', function () {
        // Lấy src của ảnh thumbnail được click
        const thumbnailSrc = this.querySelector('img').src;

        // Cập nhật src của ảnh chính
        const mainImage = document.querySelector('.img-detail img');
        mainImage.src = thumbnailSrc;

        // Xử lý active state
        document.querySelectorAll('.thumbnail-item').forEach(item => {
            item.classList.remove('active');
        });
        this.classList.add('active');
    });
});
let quantity = 1; // Giá trị ban đầu

function increaseQuantity() {
    quantity++;
    document.getElementById("quantity").textContent = quantity;
}

function decreaseQuantity() {
    if (quantity > 1) { // Ngăn không cho giảm dưới 1
        quantity--;
        document.getElementById("quantity").textContent = quantity;
    }
}

document.querySelector('.img-detail img').addEventListener('mousedown', function () {
    this.style.transform = 'scale(0.95)'; // Giảm kích thước khi nhấn
});

document.querySelector('.img-detail img').addEventListener('mouseup', function () {
    this.style.transform = 'scale(1)'; // Khôi phục kích thước khi thả
});

document.querySelector('.img-detail img').addEventListener('mouseleave', function () {
    this.style.transform = 'scale(1)'; // Khôi phục kích thước khi rời chuột
});




const optionColor = document.querySelectorAll('.option-collor');
const colorDetail = document.querySelector('#colorDetail');

optionColor.forEach((color) => {
    color.addEventListener('click', function () {
        const nameColor = color.querySelector('.nameColor');
        colorDetail.innerHTML = `<h3>Màu sắc : ${nameColor.textContent} </h3>`;

    });
});

const sizeDetail = document.querySelector('#sizeDetail');
const optionSize = document.querySelectorAll('.option-size');

optionSize.forEach((size) => {
    size.addEventListener('click', function () {
        const nameSize = size.querySelector('span');
        sizeDetail.innerHTML = `<h3>Kích cỡ : ${nameSize.textContent} </h3>`;

    })

});
