// XÓA loại voucher
document.querySelectorAll(".btn-delete-voucher").forEach(button => {
    button.addEventListener("click", () => {
        const voucherId = button.dataset.id;
        Swal.fire({
            title: 'Bạn có chắc chắn muốn xóa?',
            text: 'Thao tác này không thể hoàn tác!',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Xóa',
            cancelButtonText: 'Hủy'
        }).then((result) => {
            if (result.isConfirmed) {
                $.ajax({
                    type: "POST",
                    url: "ManagerVoucherController",
                    data: {
                        action: "delete",
                        voucherId: voucherId
                    },
                    success: function (data) {
                        if (data === "success") {
                            Swal.fire('Xóa thành công!', '', 'success').then(() => {
                                location.reload();
                            });
                        } else {
                            Swal.fire('Thất bại!', 'Không thể xóa.', 'error');
                        }
                    },
                    error: function () {
                        Swal.fire('Lỗi hệ thống!', '', 'error');
                    }
                });
            }
        });
    });
});

// MỞ MODAL cập nhật
document.querySelectorAll(".btn-update-voucher").forEach(button => {
    button.addEventListener("click", () => {
        const voucherId = button.dataset.id;

        const row = button.closest("tr");
        const typeVoucherId = parseInt(row.children[1].innerText.trim())
        const discountPercent = parseFloat(row.children[3].innerText.trim());
        const rawDiscountMaxValue = row.children[4].innerText.trim();
        const discountMaxValue = parseFloat(rawDiscountMaxValue.replace(/\./g, '').replace(' VNĐ', '').trim());
        const startDate = row.children[5].innerText.trim();
        const endDate = row.children[6].innerText.trim();
        const quantity = parseInt(row.children[7].innerText.trim());

        document.getElementById("update-voucher-id").value = voucherId;
        document.getElementById("update-voucher-type-id").value = typeVoucherId;
        document.getElementById("update-voucher-discount-percent").value = discountPercent;
        document.getElementById("update-voucher-discount-max-value").value = discountMaxValue;
        document.getElementById("update-voucher-startDate").value = startDate;
        document.getElementById("update-voucher-endDate").value = endDate;
        document.getElementById("update-voucher-quantity").value = quantity;

        const updateModal = new bootstrap.Modal(document.getElementById("updateModal"));
        updateModal.show();
    });
});

// XÁC NHẬN cập nhật
document.getElementById("btn-confirm-update-voucher").addEventListener("click", () => {
    const voucherId = document.getElementById("update-voucher-id").value;
    const typeVoucherId = document.getElementById("update-voucher-type-id").value;
    const discountPercent = document.getElementById("update-voucher-discount-percent").value;
    const discountMaxValue = document.getElementById("update-voucher-discount-max-value").value;
    const startDate = document.getElementById("update-voucher-startDate").value;
    const endDate = document.getElementById("update-voucher-endDate").value;
    const quantity = document.getElementById("update-voucher-quantity").value;

    Swal.fire({
        title: 'Bạn có chắc muốn cập nhật Voucher này không?',
        icon: 'question',
        showCancelButton: true,
        cancelButtonText: 'Hủy',
        confirmButtonText: 'Xác nhận'
    }).then((result) => {
        if (result.isConfirmed) {
            $.ajax({
                type: "POST",
                url: "ManagerVoucherController",
                data: {
                    action: "update",
                    voucherId: voucherId,
                    typeVoucherId: typeVoucherId,
                    discountPercent: discountPercent,
                    discountMaxValue: discountMaxValue,
                    startDate: startDate,
                    endDate: endDate,
                    quantity: quantity
                },
                success: function (data) {
                    if (data === "success") {
                        Swal.fire('Cập nhật thành công!', '', 'success').then(() => {
                            location.reload();
                        });
                    } else {
                        Swal.fire('Lỗi!', 'Không thể cập nhật.', 'error');
                    }
                },
                error: function () {
                    Swal.fire('Lỗi hệ thống!', '', 'error');
                }
            });
        } else {
            const updateModal = bootstrap.Modal.getInstance(document.getElementById("updateModal"));
            updateModal.hide();
        }
    });
});
