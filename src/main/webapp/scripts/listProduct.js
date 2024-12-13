document.addEventListener('DOMContentLoaded', function () {
    const productItems = document.querySelectorAll('.product-item');

    productItems.forEach(productItem => {
        const sizeListProducts = productItem.querySelectorAll('.size-listProduct');
        const imageContainer = productItem.querySelector('.image-container');
        const defaultImage = imageContainer.querySelector('.image-default');
        const hoverImage = imageContainer.querySelector('.image-hover');

        let isHoveringSize = false; // Cờ trạng thái để kiểm tra khi hover vào size

        // Xử lý hover vào danh sách size
        productItem.addEventListener('mouseenter', () => {
            sizeListProducts.forEach(sizeListProduct => {
                sizeListProduct.style.display = 'flex';
                sizeListProduct.style.animation = 'hoverProduct-listProdct 0.8s forwards';
            });
        });

        productItem.addEventListener('mouseleave', () => {
            sizeListProducts.forEach(sizeListProduct => {
                sizeListProduct.style.animation = 'notHoverProduct-listProdct 0.8s forwards';
                sizeListProduct.style.display = 'none';
            });
        });

        // Sự kiện hover vào danh sách size
        sizeListProducts.forEach(sizeListProduct => {
            sizeListProduct.addEventListener('mouseenter', () => {
                isHoveringSize = true; // Kích hoạt trạng thái hover vào size
            });

            sizeListProduct.addEventListener('mouseleave', () => {
                isHoveringSize = false; // Hủy trạng thái hover vào size
            });
        });

        // Xử lý hover vào ảnh
        imageContainer.addEventListener('mouseover', () => {
            if (!isHoveringSize) { // Chỉ thay đổi ảnh khi không hover vào size
                defaultImage.classList.remove('active');
                hoverImage.classList.add('active');
            }
        });

        imageContainer.addEventListener('mouseout', () => {
            if (!isHoveringSize) { // Chỉ thay đổi ảnh khi không hover vào size
                hoverImage.classList.remove('active');
                defaultImage.classList.add('active');
            }
        });
    });

    // Xử lý click vào màu sắc để thay đổi ảnh
    productItems.forEach(productItem => {
        const colorButtons = productItem.querySelectorAll('.color-button');
        const colorItems = productItem.querySelectorAll('.color-item');

        colorButtons.forEach(button => {
            button.addEventListener('click', () => {
                // Lấy giá trị data-target từ nút bấm
                const targetColor = button.getAttribute('data-target');

                // Ẩn tất cả các color-item
                colorItems.forEach(colorItem => {
                    colorItem.classList.remove('active');
                });

                // Hiển thị color-item tương ứng với màu được chọn
                const targetItem = productItem.querySelector(`.color-item[data-color="${targetColor}"]`);
                if (targetItem) {
                    targetItem.classList.add('active');
                }
            });
        });
    });
});
