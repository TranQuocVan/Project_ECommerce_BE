// Function to display a popup when a product is added to the cart
const popUp = (img, name, color, size, price) => {
    const animationDuration = 2; // Animation duration in seconds
    const popup = document.createElement('div');
    popup.id = 'popUp';

    // Popup content
    popup.innerHTML = `
        <div class="titlePopup" style="border-bottom: 1px solid black; padding: 10px 0; font-weight: 700;">
            <p style="margin: 0;">Đã thêm vào giỏ hàng</p>
        </div>
        <div class="contentPopup" style="display: flex; padding: 16px 0;">
            <img src="${img}" alt="Hình ảnh sản phẩm">
            <div class="infoPopup" style="display: flex; flex-direction: column; justify-content: space-between; padding: 0 10px;">
                <h3 style="font-size: 20px; font-weight: 700;">${name}</h3>
                <div style="font-size: 14px; line-height: 14px; font-weight: 500;">
                    <p style="margin: 0;">${color} / ${size}</p>
                    <p style="margin: 0;">${price}</p>
                </div>
            </div>
        </div>
        <div style="padding-bottom: 20px;">
            <form action="ShoppingCartItemsController">
                <button id="btnPopup">Xem Giỏ hàng</button>
            </form>
        </div>`;

    // Add animation and append popup to the body
    popup.style.animation = `popUpDown ${animationDuration}s`;
    document.body.appendChild(popup);

    // Remove popup after animation
    const totalDelay = animationDuration * 1000 + 1000; // Total delay in milliseconds
    setTimeout(() => {
        popup.style.animation = `popUpUp ${animationDuration}s forwards`;
    }, totalDelay);

    setTimeout(() => {
        popup.style.display = 'none';
    }, totalDelay + animationDuration * 1000);
};

// Function to send product details to the server
const products = (idSize, img, name, color, size, price) => {
    if (!idSize || typeof idSize !== 'number') {
        console.error('Error: idSize is invalid');
        return;
    }

    // Send data to the server
    fetch('/AddToCartController', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ idSize, quantity: 1 }),
    })
        .then(response => response.json())
        .then(data => {
            // Handle server response
            if (data.status === 'error') {
                Swal.fire({
                    icon: 'error',
                    title: 'Lỗi',
                    text: data.message || 'Không thể thêm sản phẩm vào giỏ hàng.'
                });
            } else if (data.status === 'ok') {
                popUp(img, name, color, size, price);
            }
        })
        .catch(error => {
            // Handle fetch error
            console.error('Error:', error);
            Swal.fire({
                icon: 'error',
                title: 'Lỗi',
                text: 'Đã xảy ra lỗi trong quá trình thêm sản phẩm.'
            });
        });
};

// Add event listeners to product items
document.querySelectorAll(".product-item").forEach(productItem => {
    const sizeOptions = productItem.querySelectorAll(".option-size");

    sizeOptions.forEach(sizeOption => {
        sizeOption.addEventListener("click", () => {
            const idSize = Number(sizeOption.querySelector(".idSize").textContent.trim());
            if (isNaN(idSize)) {
                console.error("Invalid idSize");
                return;
            }

            const product = sizeOption.closest(".product-item");
            const colorButtons = product.querySelectorAll(".color-button");
            const colorOptions = product.querySelectorAll('.option-collor');

            // Find the active color button index
            let activeColorIndex = Array.from(colorButtons).findIndex(button =>
                button.classList.contains('activeSize')
            );
            if (activeColorIndex === -1) activeColorIndex = 0; // Default to first color

            // Extract product details
            const img = product.querySelector(".image-item").src;
            const name = product.querySelector(".name-product").innerHTML.trim();
            const color = colorOptions[activeColorIndex].querySelector(".nameColor").innerHTML.trim();
            const size = sizeOption.querySelector(".sizeName").innerHTML.trim();
            const price = product.querySelector(".price").innerHTML.trim();

            // Send product data to the server
            products(idSize, img, name, color, size, price);
        });
    });
});
