
$(window).on('scroll', function () {
    var scroll = $(window).scrollTop(); // Lấy vị trí cuộn trang

    // Tính toán tỷ lệ thu nhỏ của video
    var scale = 1 - (scroll / 6500); // Điều chỉnh giá trị 1000 theo ý muốn để thay đổi tốc độ

    // Đảm bảo không thu nhỏ quá mức
    if (scale < 0.5) {
        scale = 0.5; // Giới hạn tỷ lệ thu nhỏ không nhỏ hơn 50%
    }

    // Áp dụng tỷ lệ thu nhỏ cho video
    $('.video').css('transform', 'scale(' + scale + ')');


});
const body = document.querySelector('body');
body.addEventListener('scroll', function () {
    let scroll = body.scrollTop;

    let scale = 1 - (scroll / 6500);

    if (scale < 0.8) {
        scale = 0.8;
    }

    document.querySelector('.video').style.transform = 'scale(' + scale + ')';
});









