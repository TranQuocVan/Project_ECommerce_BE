//

// Lắng nghe sự kiện click vào nút tạm dừng
// Lắng nghe sự kiện cuộn trang
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

// Lắng nghe sự kiện thay đổi kích thước cửa sổ
$(window).on('resize', function () {
    var windowWidth = $(window).width(); // Lấy chiều rộng cửa sổ
    var scale = windowWidth / 1200; // Điều chỉnh tỷ lệ theo chiều rộng (1200 là chiều rộng tối đa mong muốn)

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









