function changeStatus(selected) {
    // Lấy tất cả các thẻ a trong status-tabs
    var statusTabs = document.querySelectorAll('.status-tab');

    // Xóa lớp active và gạch dưới cho tất cả thẻ
    statusTabs.forEach(function(tab) {
        tab.classList.remove('active');
        tab.style.textDecoration = 'none'; // Xóa gạch dưới
    });

    // Thêm lớp active cho thẻ được chọn
    selected.classList.add('active');
    selected.style.textDecoration = 'underline'; // Thêm gạch dưới
}

function openLightbox() {
    const lightbox = document.getElementById("orderLightbox");
    lightbox.style.display = "flex"; // Hiện lightbox
}

function closeLightbox() {
    const lightbox = document.getElementById("orderLightbox");
    lightbox.style.display = "none"; // Ẩn lightbox
}

// Đóng lightbox khi nhấn ngoài lightbox-content
document.getElementById("orderLightbox").addEventListener("click", function (event) {
    if (event.target === this) {
        closeLightbox();
    }
});
