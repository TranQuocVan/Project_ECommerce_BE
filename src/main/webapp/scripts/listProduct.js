document.addEventListener('DOMContentLoaded', function () {
    const productItems = document.querySelectorAll('.product-item');

    productItems.forEach(productItem => {
        const sizeListProducts = productItem.querySelectorAll('.size-listProduct');
        const imageContainer = productItem.querySelector('.image-container');
        const defaultImage = imageContainer.querySelector('.image-default');
        const hoverImage = imageContainer.querySelector('.image-hover');

        const colorButtons = productItem.querySelectorAll('.color-button');

        // Event listener for color button clicks
        colorButtons.forEach(colorButton => {
            colorButton.addEventListener('click', () => {
                // Remove 'active' class from all color buttons within the current productItem
                const productBtn = productItem.querySelectorAll(".color-button");
                productBtn.forEach(btn => {
                    btn.classList.remove('activeSize');
                });

                // Add 'active' class to the clicked color button
                colorButton.classList.add('activeSize');
            });
        });

        let isHoveringSize = false; // Flag to check if hovering over size

        // Hover event on size list
        productItem.addEventListener('mouseenter', () => {
            let activeIndex = 0;
            const productBtn = productItem.querySelectorAll(".color-button");

            // Find the index of the active color button
            productBtn.forEach((colorButton, index) => {
                if (colorButton.classList.contains('activeSize')) {
                    activeIndex = index; // Store the index of the button with 'activeSize' class
                }
            });

            // Display the corresponding size list
            sizeListProducts[activeIndex].style.display = 'flex';
            sizeListProducts[activeIndex].style.animation = 'hoverProduct-listProdct 0.8s forwards';
        });

        productItem.addEventListener('mouseleave', () => {
            // Hide all size lists on mouse leave
            sizeListProducts.forEach(sizeListProduct => {
                sizeListProduct.style.animation = 'notHoverProduct-listProdct 0.8s forwards';
                sizeListProduct.style.display = 'none';
            });
        });

        // Hover events for size list
        sizeListProducts.forEach(sizeListProduct => {
            sizeListProduct.addEventListener('mouseenter', () => {
                isHoveringSize = true; // Activate hover state for size
            });

            sizeListProduct.addEventListener('mouseleave', () => {
                isHoveringSize = false; // Deactivate hover state for size
            });
        });

        // Hover event on image container
        imageContainer.addEventListener('mouseover', () => {
            if (!isHoveringSize) { // Only change image if not hovering over size
                defaultImage.classList.remove('active');
                hoverImage.classList.add('active');
            }
        });

        imageContainer.addEventListener('mouseout', () => {
            if (!isHoveringSize) { // Only change image if not hovering over size
                hoverImage.classList.remove('active');
                defaultImage.classList.add('active');
            }
        });
    });

    // Event listener for color buttons to change images
    productItems.forEach(productItem => {
        const colorButtons = productItem.querySelectorAll('.color-button');
        const colorItems = productItem.querySelectorAll('.color-item');

        colorButtons.forEach(button => {
            button.addEventListener('click', (e) => {
                // Get the value of the data-target attribute
                const targetColor = button.getAttribute('data-target');

                // Hide all color items
                colorItems.forEach(colorItem => {
                    colorItem.classList.remove('active');
                });

                // Show the corresponding color item
                const targetItem = productItem.querySelector(`.color-item[data-color="${targetColor}"]`);
                if (targetItem) {
                    targetItem.classList.add('active');
                }
            });
        });
    });
});
