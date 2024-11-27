const thumbnailsSlide2 = document.querySelectorAll('#slide2 .item img');
const thumbnailsSlide = document.querySelectorAll('#slide .item img');

thumbnailsSlide2.forEach(thumbnail => {
    thumbnail.addEventListener('click', function(event) {
        event.preventDefault();

        // Lấy nguồn ảnh từ thẻ img
        const imageSrc = this.src;
        const modalImage = document.getElementById('modalImage2');
        modalImage.src = imageSrc;

        // Lấy giá từ thuộc tính data-price của phần tử cha (div.item)
        const imagePrice = this.closest('.item').getAttribute('data-price');
        const modalPrice = document.getElementById('modalPrice2');
        modalPrice.textContent = `Giá: ${imagePrice}`;

        // Cập nhật giá trị của nút "Mua ngay"
        const buyButton = document.getElementById('buyButton');
        buyButton.setAttribute('data-price', imagePrice);
    });
});

// New event listener for the slide section
thumbnailsSlide.forEach(thumbnail => {
    thumbnail.addEventListener('click', function(event) {
        event.preventDefault();

        // Lấy nguồn ảnh từ thẻ img
        const imageSrc = this.src;
        const modalImage = document.getElementById('modalImage');
        modalImage.src = imageSrc;

        // Lấy giá từ thuộc tính data-price của phần tử cha (div.item)
        const imagePrice = this.closest('.item').getAttribute('data-price');
        const modalPrice = document.getElementById('modalPrice');
        modalPrice.textContent = `Giá: ${imagePrice}`;

        // Cập nhật giá trị của nút "Mua ngay"
        const buyButton = document.getElementById('buyButton');
        buyButton.setAttribute('data-price', imagePrice);
    });
});
