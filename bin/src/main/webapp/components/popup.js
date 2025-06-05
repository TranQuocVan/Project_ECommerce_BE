
const popUp = (img,name,color,size,price , productId) => {
    const timeAnimation = 2;
    const div = document.createElement('div');
    div.id = 'popUp';

    div.innerHTML = `
        <div style="border-bottom: 1px solid black; padding: 10px 0; font-weight: 700;" class="titlePopup">
            <p style="margin: 0;">Đã thêm vào giỏ hàng</p>
        </div>
        <div style="display: flex; padding: 16px 0;" class="contentPopup">
            <img
                style="width: 65px; height: 90px; object-fit: contain; border-radius: 5px; background-color: beige;"
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


    // Gửi dữ liệu đến server


    const timeDelay = timeAnimation * 1000 + 1000;

    setTimeout(() => {
        div.style.animation = `popUpUp ${timeAnimation}s forwards`;
    }, timeDelay);

    setTimeout(() => {
        div.style.display = 'none';
    }, timeDelay + timeAnimation * 1000);
};

const productItems = document.querySelectorAll(".product-item");

const products = (idSize, img, name, color, size, price) => {
    if (!idSize || typeof idSize !== 'number') {
        console.error('Error: idSize is invalid');
        return;
    }

    fetch('/AddToCartController', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ idSize }),
    })
        .then(response => response.json())
        .then(data => {
            if (data.status === 'error') {
                Swal.fire({
                    icon: 'error',
                    title: 'Lỗi',
                    text: data.message || 'Không thể thêm sản phẩm vào giỏ hàng.'
                });
            } else if (data.status === 'ok') {
                // Chỉ hiển thị popup khi sản phẩm được thêm thành công
                popUp(img, name, color, size, price);
            }
        })
        .catch(error => {
            console.error('Error:', error);
            Swal.fire({
                icon: 'error',
                title: 'Lỗi',
                text: 'Đã xảy ra lỗi trong quá trình thêm sản phẩm.'
            });
        });
};

productItems.forEach(productItem => {
    const optionSizes = productItem.querySelectorAll(".option-size");
    optionSizes.forEach(optionSize =>{
        optionSize.addEventListener("click",()=>{
            const idSize = optionSize.querySelector(".idSize").textContent.trim();
            console.log(idSize.innerHTML)

            const product = optionSize.closest(".product-item")
            let activeIndex = 0;
            const productBtn = product.querySelectorAll(".color-button");

            // Find the index of the active color button
            productBtn.forEach((colorButton, index) => {
                if (colorButton.classList.contains('activeSize')) {
                    activeIndex = index; // Store the index of the button with 'activeSize' class
                }
            });

            const optionColor = product.querySelectorAll('.option-collor');

            console.log(optionColor[activeIndex])
            const img = product.querySelector(".image-item").src
            const name = product.querySelector(".name-product").innerHTML
            const color = optionColor[activeIndex].querySelector(".nameColor").innerHTML
            const size = product.querySelector(".sizeName").innerHTML
            const price = product.querySelector(".price").innerHTML
            const productId = product.querySelector(".idProduct").innerHTML;
            products(Number(idSize), img, name, color, size, price);
            // popUp(img,name,color,size,price)


        })
    })

});

