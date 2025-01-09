// Function to update the main image
const changeImgMain = () => {
    const colorItemActive = document.querySelector(".color-item-img.active");
    const imgsOfColorItemActive = colorItemActive ? colorItemActive.querySelectorAll("img") : [];
    const imgMain = document.querySelector("#img_main");

    if (!imgMain) return;

    // Set the initial main image
    imgMain.innerHTML = imgsOfColorItemActive.length > 0
        ? `<img src="${imgsOfColorItemActive[0].src}" alt="Main Image" />`
        : '<img src="assets/default/noImageAvailable.jpg" alt="No Image Available">';

    // Add click event listeners to the images in the active color item
    imgsOfColorItemActive.forEach(img => {
        img.addEventListener("click", () => {
            imgMain.innerHTML = `<img src="${img.src}" alt="Main Image" />`;
        });
    });
};

// Function to initialize selected color and size texts
const initializeSelectionTexts = () => {
    const colorItems = document.querySelectorAll('.color-item div');
    const selectedColorText = document.querySelector(".selectedColorText");

    if (colorItems.length > 0 && selectedColorText) {
        selectedColorText.innerHTML = `Màu sắc: <strong>${colorItems[0].getAttribute('data-color-name')}</strong>`;
    }

    const sizeText = document.querySelector(".selectedSizeText");
    const initialSizeActive = document.querySelector(".size.active .size-item");

    if (sizeText && initialSizeActive) {
        sizeText.setAttribute("idSize", initialSizeActive.getAttribute("data-size-id"));
        sizeText.innerHTML = `Kích thước: <strong>${initialSizeActive.innerHTML}</strong>`;
    }
};

// Function to handle color item click events
const handleColorItemClicks = () => {
    const colorItems = document.querySelectorAll('.color-item div');
    const selectedColorText = document.querySelector(".selectedColorText");
    const sizeText = document.querySelector(".selectedSizeText");

    colorItems.forEach((color, index) => {
        color.addEventListener('click', () => {
            const colorItemImgs = document.querySelectorAll(".color-item-img");
            colorItemImgs.forEach(item => item.classList.remove('active'));
            if (colorItemImgs[index]) {
                colorItemImgs[index].classList.add("active");
            }

            const sizes = document.querySelectorAll(".size");
            sizes.forEach(item => item.classList.remove("active"));
            if (sizes[index]) {
                sizes[index].classList.add("active");
            }

            if (selectedColorText) {
                selectedColorText.innerHTML = `Màu sắc: <strong>${color.getAttribute('data-color-name')}</strong>`;
            }

            const activeSize = document.querySelector(".size.active .size-item");
            if (sizeText && activeSize) {
                sizeText.setAttribute("idSize", activeSize.getAttribute("data-size-id"));
                sizeText.innerHTML = `Kích thước: <strong>${activeSize.innerHTML}</strong>`;
            } else if (sizeText) {
                sizeText.innerHTML = `Kích thước: <strong>N/A</strong>`;
            }

            changeImgMain();
        });
    });
};

// Function to handle size item click events
const handleSizeItemClicks = () => {
    const sizeItems = document.querySelectorAll(".size .size-item");
    const sizeText = document.querySelector(".selectedSizeText");

    sizeItems.forEach(sizeItem => {
        sizeItem.addEventListener('click', () => {
            sizeItems.forEach(item => item.classList.remove('active'));
            sizeItem.classList.add('active');

            if (sizeText) {
                sizeText.setAttribute("idSize", sizeItem.getAttribute("data-size-id"));
                sizeText.innerHTML = `Kích thước: <strong>${sizeItem.innerHTML}</strong>`;
            }
        });
    });
};

// Function to check stock availability
const checkStock = async (idSize, quantity) => {
    try {
        if (isNaN(idSize)) {
            throw new Error("Invalid idSize: must be a valid number.");
        }

        const response = await fetch('/Shoe_war_exploded/CheckStockController', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ idSize, quantity }),
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        const data = await response.json();
        return data.status === "ok";
    } catch (error) {
        console.error('Error checking stock:', error);
        return false;
    }
};

// Function to increase quantity
const increaseQuantity = async () => {
    const quantityInput = document.querySelector("#quantity");
    const sizeText = document.querySelector(".selectedSizeText");

    if (quantityInput && sizeText) {
        const currentQuantity = parseInt(quantityInput.innerHTML, 10) || 0;
        const newQuantity = currentQuantity + 1;
        const idSize = parseInt(sizeText.getAttribute("idSize"), 10);

        if (isNaN(idSize)) {
            popUpError("Invalid size selected. Please select a valid size.");
            return;
        }

        const isStockAvailable = await checkStock(idSize, newQuantity);

        if (isStockAvailable) {
            quantityInput.innerHTML = newQuantity;
        } else {
            popUpError("Not enough stock available for the selected size.");
        }
    }
};

// Function to handle adding to cart
const addToCart = async () => {
    const quantityInput = document.querySelector("#quantity");
    const sizeText = document.querySelector(".selectedSizeText");

    if (quantityInput && sizeText) {
        const currentQuantity = parseInt(quantityInput.innerHTML, 10) || 1;
        const idSize = parseInt(sizeText.getAttribute("idSize"), 10);

        if (isNaN(idSize)) {
            popUpError("Invalid size selected. Please select a valid size.");
            return;
        }

        const isStockAvailable = await checkStock(idSize, currentQuantity);

        if (isStockAvailable) {
            try {
                const response = await fetch('/Shoe_war_exploded/AddToCartController', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({ idSize, quantity: currentQuantity }),
                });

                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }

                const data = await response.json();
                if (data.status === "ok") {
                    handleAddToCartSuccess();
                } else {
                    popUpError("Unable to add item to cart.");
                }
            } catch (error) {
                console.error('Error adding to cart:', error);
                popUpError("Error adding item to cart.");
            }
        } else {
            popUpError("Not enough stock available for the selected size.");
        }
    }
};

// Function to show error pop-up
const popUpError = (message) => {
    const timeAnimation = 2;
    const div = document.createElement('div');
    div.id = 'popUp';

    div.innerHTML = `
        <div style="border-bottom: 1px solid black; padding: 10px 0; font-weight: 700;" class="titlePopup">
            <p style="margin: 0;">Thông báo</p>
        </div>
        <div style="display: flex; padding: 16px 0;" class="contentPopup">
            <div style="display: flex; justify-content: space-between; flex-direction: column; padding: 0 10px;"
                class="infoPopup">
                <h3 style="font-size: 14px; font-weight: 700;">${message}</h3>
            </div>
        </div>`;

    div.style.animation = `popUpDown ${timeAnimation}s`;
    document.body.appendChild(div);

    const timeDelay = timeAnimation * 1000 + 1000;

    setTimeout(() => {
        div.style.animation = `popUpUp ${timeAnimation}s forwards`;
    }, timeDelay);

    setTimeout(() => {
        div.remove();
    }, timeDelay + timeAnimation * 1000);
};

// Event listeners and initialization
const faplus = document.querySelector(".fa-plus");
if (faplus) faplus.addEventListener("click", increaseQuantity);

const addToCartButton = document.querySelector(".addToCard");
if (addToCartButton) addToCartButton.addEventListener("click", addToCart);

const minToCard = () => {
    const quantityInput = document.querySelector("#quantity");
    if (!quantityInput) return;

    const currentQuantity = parseInt(quantityInput.innerHTML, 10) || 0;

    // Ensure quantity doesn't go below zero
    if (currentQuantity > 0) {
        quantityInput.innerHTML = currentQuantity - 1;
    } else {
        popUpError("Quantity cannot be less than 0.");
    }
};

// Attach the event listener for the minus button
const faMinus = document.querySelector(".fa-minus");
if (faMinus) faMinus.addEventListener("click", minToCard);




const popUp = (img,name,color,size,price) => {
    const timeAnimation = 2;
    const div = document.createElement('div');
    div.id = 'popUp';

    div.innerHTML = `
        <div style="border-bottom: 1px solid black; padding: 10px 0; font-weight: 700;" class="titlePopup">
            <p style="margin: 0;">Đã thêm vào giỏ hàng</p>
        </div>
        <div style="display: flex; padding: 16px 0;" class="contentPopup">
            <img
                
                src="${img}" alt="Hình ảnh sản phẩm">
            <div style="display: flex; justify-content: space-between; flex-direction: column; padding: 0 10px;"
                class="infoPopup">
                <h3 style="font-size: 20px; font-weight: 700;">${name}</h3>
                <div style="font-size: 14px; line-height: 14px; font-weight: 500;">
                    <p style="margin: 0;">${color}/${size}</p>
                    <p style="margin: 0;">${price}</p>
                </div>
            </div>
        </div>
        <div style="padding-bottom: 20px;">
        <form action="ShoppingCartItemsController" method="get">
            <button id="btnPopup">
                Xem Giỏ hàng
            </button>
            </form>
        </div>`;

    div.style.animation = `popUpDown ${timeAnimation}s`;
    document.body.appendChild(div); // Thêm popup vào body

    const timeDelay = timeAnimation * 1000 + 1000;

    setTimeout(() => {
        div.style.animation = `popUpUp ${timeAnimation}s forwards`;
    }, timeDelay);

    setTimeout(() => {
        div.style.display = 'none';
    }, timeDelay + timeAnimation * 1000);
};

const handleAddToCartSuccess =()=> {
    const colorItemImgActiveImg = document.querySelector(".color-item-img.active img").src
    const name = document.querySelector(".name").innerHTML;
    const color = document.querySelector(".selectedColorText strong").innerHTML;
    const size = document.querySelector(".selectedSizeText strong").innerHTML
    const price = document.querySelector(".price_sale").innerHTML;

    popUp(colorItemImgActiveImg, name, color,size,price);


}
changeImgMain();
initializeSelectionTexts();
handleColorItemClicks();
handleSizeItemClicks();