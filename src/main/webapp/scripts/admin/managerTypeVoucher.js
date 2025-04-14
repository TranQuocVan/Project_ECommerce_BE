// XÓA loại voucher
document.querySelectorAll(".btn-delete-type-voucher").forEach(button => {
    button.addEventListener("click", () => {
        const id = button.dataset.id;
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
                    url: "ManagerTypeVoucherController",
                    data: {
                        action: "delete",
                        id: id
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
document.querySelectorAll(".btn-update-type-voucher").forEach(button => {
    button.addEventListener("click", () => {
        const id = button.dataset.id;

        const row = button.closest("tr");
        const name = row.children[1].innerText.trim();
        const desc = row.children[2].innerText.trim();

        document.getElementById("update-type-voucher-id").value = id;
        document.getElementById("update-type-voucher-name").value = name;
        document.getElementById("update-type-voucher-desc").value = desc;

        const updateModal = new bootstrap.Modal(document.getElementById("updateModal"));
        updateModal.show();
    });
});

// XÁC NHẬN cập nhật
document.getElementById("btn-confirm-update-type-voucher").addEventListener("click", () => {
    const id = document.getElementById("update-type-voucher-id").value;
    const typeName = document.getElementById("update-type-voucher-name").value;
    const description = document.getElementById("update-type-voucher-desc").value;

    Swal.fire({
        title: 'Bạn có chắc muốn cập nhật loại Voucher này không?',
        icon: 'question',
        showCancelButton: true,
        cancelButtonText: 'Hủy',
        confirmButtonText: 'Xác nhận'
    }).then((result) => {
        if (result.isConfirmed) {
            $.ajax({
                type: "POST",
                url: "ManagerTypeVoucherController",
                data: {
                    action: "update",
                    id: id,
                    typeName: typeName,
                    description: description
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
