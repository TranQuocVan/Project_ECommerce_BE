const timeAnimation = 2;

const popUps = document.querySelectorAll('.popup');

const optionColor = () => {
    const optionColorPopup = document.querySelectorAll('.option-collor');
    let currentColor = null;
    optionColorPopup.forEach((color) => {
        color.addEventListener('click', function (e) {
            e.stopPropagation(); // Ngăn chặn sự kiện click nổi bọt lên thẻ cha
            if (currentColor) {
                currentColor.classList.remove('optionColorOnClicked');
            }
            color.classList.add('optionColorOnClicked');
            currentColor = color;
        });
    });
};

popUps.forEach((popUp) => {
    popUp.addEventListener('click', (e) => {
        e.stopPropagation(); // Đảm bảo sự kiện click không bị ảnh hưởng bởi phần tử cha

        // Gọi lại optionColor để đảm bảo các màu được gắn sự kiện click
        optionColor();

        const div = document.createElement('div');
        div.id = 'popUp';

        const parent = popUp.closest('.product-item'); // Tìm phần tử cha gần nhất
        const imgPopupSrc = parent?.querySelector('img')?.src || document.querySelector('#imgPopUp')?.src;
        const sizeValue = parent?.querySelector('.sizeOnClicked > span')?.textContent || parent?.querySelector('.option-size > span')?.textContent;
        const collorValue = parent?.querySelector('.optionColorOnClicked > .nameColor')?.textContent || parent?.querySelector('.nameColor')?.textContent;
        const price = parent?.querySelector('.priceDetail')?.textContent || parent?.querySelector('.price')?.textContent;

        // Lấy id của màu được chọn


        // Debug giá trị


        div.innerHTML = `
                <div style="border-bottom: 1px solid black; padding: 10px 0; font-weight: 700;" class="titlePopup">
                    <p style="margin: 0;">Đã thêm vào giỏ hàng</p>
                </div>
                <div style="display: flex;padding: 16px 0;" class="contentPopup"> <img
                        style="width: 65px; height: 90px; object-fit:contain;border-radius: 5px ;background-color: beige;"
                        src="${imgPopupSrc}" alt="Hình ảnh sản phẩm">
                    <div style="display: flex;justify-content: space-between; flex-direction: column; padding: 0 10px"
                        class="infoPopup">
                        <h3 style="font-size: 20px; font-weight: 700;">Giày thể thao nam</h3>
                        <div class="" style="font-size: 14px; line-height: 14px; font-weight:500 ">
                            <p style="margin: 0;;">${collorValue}/${sizeValue}</p>
                            <p style="margin: 0;">${price}</p>
                        </div>
                    </div>
                </div>
                <div style="padding-bottom: 20px ;">
                    <button id="btnPopup">
                        Xem Giỏ hàng
                    </button>
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
    });
});
