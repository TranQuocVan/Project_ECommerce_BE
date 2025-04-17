// Constants and global variables
const selectedItems = new Set();
let totalPrice = 0;
let originalTotalAmount = 0;
let discountShipping = 0;
let discountItems = 0;

// Utility functions
const formatPrice = (price) =>
    `${new Intl.NumberFormat('vi-VN', { style: 'decimal', minimumFractionDigits: 0 }).format(price)}đ`;

const showAlert = (title, text, icon, confirmButtonText = 'OK', showCancelButton = false) =>
    Swal.fire({ title, text, icon, confirmButtonText, showCancelButton, confirmButtonColor: '#3085d6', cancelButtonColor: '#d33' });

const getElement = (selector, context = document) => context.querySelector(selector);
const getElements = (selector, context = document) => context.querySelectorAll(selector);

// AJAX utility for database updates
async function updateQuantityInDatabase(sizeId, quantity, isDecreaseQuantity = false) {
    try {
        const response = await fetch('/Shoe_war_exploded/UpdateQuantityCartController', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ idSize: sizeId, quantity, isDecreaseQuantity }),
        });
        const data = await response.json();
        if (data.status === 'ok') return true;
        console.warn('Server validation failed:', data.message);
        return false;
    } catch (error) {
        console.error('Request error:', error);
        throw error;
    }
}

// Quantity management
async function increaseQuantity(button, event) {
    event.stopPropagation();
    const input = getElement('input[type=number]', button.parentNode);
    const currentValue = parseInt(input.value) + 1;
    const itemContainer = button.closest('.items');
    const sizeId = getElement('input[name="sizeId"]', itemContainer).value;

    try {
        const isUpdated = await updateQuantityInDatabase(sizeId, currentValue);
        if (isUpdated) {
            input.value = currentValue;
            updateTotalPrice();
        } else {
            showAlert('Thông báo', 'Số lượng sản phẩm đã đạt giới hạn tồn kho.', 'info');
        }
        handleQuantityChange(input);
    } catch (error) {
        showAlert('Lỗi', 'Đã xảy ra lỗi khi cập nhật số lượng. Vui lòng thử lại sau.', 'error');
    }
}

function decreaseQuantity(button, event) {
    event.stopPropagation();
    const input = getElement('input[type=number]', button.parentNode);
    const currentValue = parseInt(input.value);
    const minValue = parseInt(input.getAttribute('min'));
    const itemContainer = button.closest('.items');
    const sizeId = getElement('input[name="sizeId"]', itemContainer).value;

    if (currentValue <= minValue) {
        showAlert('Thông báo', 'Không thể giảm số lượng dưới 1.', 'info');
        return;
    }

    input.value = currentValue - 1;
    updateQuantityInDatabase(sizeId, input.value, true)
        .then(() => updateTotalPrice())
        .catch(() => showAlert('Lỗi', 'Đã xảy ra lỗi khi cập nhật số lượng.', 'error'));
    handleQuantityChange(input);
}

// Cart item deletion
function handleDelete(button, nameProduct, nameSize) {
    showAlert(
        'Bạn không thể hoàn tác',
        `Bạn có muốn xóa sản phẩm này ra khỏi giỏ hàng? (Tên: ${nameProduct}, Size: ${nameSize})`,
        'warning',
        'OK',
        true
    ).then((result) => {
        if (result.isConfirmed) {
            const form = button.closest('form');
            if (form) {
                updateSelectedItemsInForm();
                form.submit();
            } else {
                console.error('Form not found!');
            }
        }
    });
}

// Form and order management
function updateQuantityInForm() {
    const cartItems = getElements('.items');
    cartItems.forEach((item) => {
        const sizeId = getElement('input[type="hidden"]', item).value;
        const quantity = getElement('input[name="quantity"]', item).value;
        let input = getElement(`#orderForm input[name="quantity_${sizeId}"]`);
        if (!input) {
            input = document.createElement('input');
            input.type = 'hidden';
            input.name = `quantity_${sizeId}`;
            getElement('#orderForm').appendChild(input);
        }
        input.value = quantity;
    });
}

function updateSelectedItemsInForm() {
    const selectedItemsInput = getElement('#selectedItems');
    selectedItemsInput.value = Array.from(selectedItems).join(',');
}

function getInfoOrder() {
    const listItem = getElements('.selected');
    const gmail = getElement('#gmaiHiden').innerText;
    let info = `Email: ${gmail}\n--- Danh sách sản phẩm ---\n`;

    listItem.forEach((item, index) => {
        const name = getElement('.name', item)?.innerText || '';
        const des = getElement('.des', item)?.innerText || '';
        const quantity = getElement('.quantityShoppingCart', item)?.value || '';
        const price = getElement('.discountPrice', item)?.innerText || '';
        info += `${index + 1}. Tên: ${name}\n   Mô tả: ${des}\n   Số lượng: ${quantity}\n   Giá: ${price}\n\n`;
    });

    const select = getElement('#deliverySelect');
    const selectedOption = select.options[select.selectedIndex];
    const deliveryName = selectedOption.text;
    const fee = selectedOption.getAttribute('data-fee');
    info += `--- Thông tin giao hàng ---\nPhương thức: ${deliveryName}\n   Phí vận chuyển: ${fee}đ\n\n`;

    const paymentSelect = getElement('#paymentId');
    const selectedPayment = paymentSelect.options[paymentSelect.selectedIndex];
    const paymentName = selectedPayment.text;
    const paymentValue = selectedPayment.value;
    info += `--- Thanh toán ---\nPhương thức: ${paymentName}\n   Giá trị: ${paymentValue}\n`;

    const totalPrice = getElement('#totalAmount').innerText;
    info += `Tổng cộng: ${totalPrice}`;
    return info;
}

// Digital signature form
function createDigitalSignature(infoOrder) {
    const div = document.createElement('div');
    div.id = 'form-digitalSignature';
    div.innerHTML = `
    <div id="support">Nếu bạn chưa có key truy cập <a href="#" target="_blank" rel="noopener noreferrer">tại đây</a></div>
    <button id="close-digitalSignature" type="button">×</button>
    <h2>Nội dung đơn hàng</h2>
    <div class="textarea-wrapper">
      <div id="copyBtn">
        <svg style="vertical-align: middle" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg" class="icon-xs">
          <path fill-rule="evenodd" clip-rule="evenodd" d="M7 5C7 3.34315 8.34315 2 10 2H19C20.6569 2 22 3.34315 22 5V14C22 15.6569 20.6569 17 19 17H17V19C17 20.6569 15.6569 22 14 22H5C3.34315 22 2 20.6569 2 19V10C2 8.34315 3.34315 7 5 7H7V5ZM9 7H14C15.6569 7 17 8.34315 17 10V15H19C19.5523 15 20 14.5523 20 14V5C20 4.44772 19.5523 4 19 4H10C9.44772 4 9 4.44772 9 5V7ZM5 9C4.44772 9 4 9.44772 4 10V19C4 19.5523 4.44772 20 5 20H14C14.5523 20 15 19.5523 15 19V10C15 9.44772 14.5523 9 14 9H5Z" fill="currentColor"></path>
        </svg> Copy
      </div>
      <textarea id="orderContent" rows="10" readonly>${infoOrder}</textarea>
    </div>
    <h3>Nhập thông tin xác thực</h3>
    <form method="post">
      <div class="form-group">
        <label for="publishKey">Publish Key:</label>
        <input type="text" id="publishKey" name="publishKey" required>
      </div>
      <div class="form-group">
        <label for="signature">Chữ ký:</label>
        <textarea id="signature" name="signature" rows="4" required></textarea>
      </div>
      <button id="submit" type="submit">Xác nhận đơn hàng</button>
    </form>
  `;
    return div;
}

// Form animations
function showDigitalSignature() {
    const form = getElement('#form-digitalSignature');
    form.classList.remove('hide');
    form.classList.add('show');
    form.style.display = 'block';
}

function closeDigitalSignature() {
    const form = getElement('#form-digitalSignature');
    form.classList.remove('show');
    form.classList.add('hide');
    setTimeout(() => (form.style.display = 'none'), 300);
}

// Order submission
function orderButton(button, event) {
    event.preventDefault();

    if (selectedItems.size === 0) {
        showAlert('Thông báo', 'Bạn phải chọn ít nhất một sản phẩm để thanh toán!', 'info');
        return;
    }

    updateQuantityInForm();
    const info = getInfoOrder();
    const signatureContainer = getElement('#signatureContainer');
    if (!signatureContainer) {
        console.error('Signature container not found!');
        return;
    }

    signatureContainer.innerHTML = '';
    signatureContainer.appendChild(createDigitalSignature(info));
    showDigitalSignature();

    getElement('#close-digitalSignature').addEventListener('click', closeDigitalSignature);
    const submitButton = getElement('#submit');
    const form = getElement('#orderForm');
    const paymentId = parseInt(getElement('#paymentId').value, 10);

    submitButton.addEventListener('click', (e) => {
        e.preventDefault();
        const publishKey = getElement('#publishKey').value;
        const signature = getElement('#signature').value;

        if (!publishKey || !signature) {
            showAlert('Thiếu chữ ký', 'Không thể tiến hành nếu thiếu chữ ký số!', 'error');
            return;
        }

        addHiddenInput(form, 'publishKey', publishKey);
        addHiddenInput(form, 'hash', signature);
        addHiddenInput(form, 'data', info);

        form.action = paymentId === 1
            ? contextPath + '/OrderController'
            : contextPath + '/VnpayPaymentController';
        updateSelectedItemsInForm();
        form.submit();
    });

    copyInfo();
}

function addHiddenInput(form, name, value) {
    const input = document.createElement('input');
    input.type = 'hidden';
    input.name = name;
    input.value = value;
    form.appendChild(input);
}

// Total price calculation
function updateTotalPrice() {
    if (selectedItems.size === 0) {
        getElement('#totalAmount').textContent = formatPrice(0);
        getElement('#totalAmountModalVoucher').textContent = formatPrice(0);
        return;
    }

    let total = 0;
    selectedItems.forEach((sizeId) => {
        const item = getElement(`.items input[type="hidden"][value="${sizeId}"]`).closest('.items');
        const price = parseFloat(getElement('.select-item', item).getAttribute('data-price').replace(/[^0-9.-]+/g, ''));
        const quantity = parseInt(getElement('input[name="quantity"]', item).value, 10);
        total += price * quantity;
    });

    const deliveryFee = parseFloat(getElement('#feeDisplay').textContent.replace(/[.,đ\s]+/g, ''));
    total += deliveryFee;
    originalTotalAmount = total;

    const finalTotal = originalTotalAmount - discountShipping - discountItems;
    getElement('#totalAmount').textContent = formatPrice(finalTotal);
    getElement('#totalAmountModalVoucher').textContent = formatPrice(finalTotal);
}

// Voucher management
function selectVoucherShipping(row) {
    if (selectedItems.size === 0) return;

    const isSelected = row.style.backgroundColor === 'rgb(144, 238, 144)';
    const table = row.closest('table');
    table.querySelectorAll('tr').forEach((tr) => (tr.style.backgroundColor = ''));

    const radio = getElement('input[type="radio"]', row);
    if (isSelected) {
        radio.checked = false;
        discountShipping = 0;
        getElement('#discountFee').textContent = '0đ';
        getElement('#discountFee').classList.remove('text-danger');
    } else {
        radio.checked = true;
        row.style.backgroundColor = '#90EE90';
        const discountPercent = parseFloat(getElement('td:nth-child(2) div:nth-child(1)', row).textContent.replace(/[^\d]/g, ''));
        const discountMaxValue = parseFloat(getElement('td:nth-child(2) div:nth-child(2)', row).textContent.replace(/[^\d]/g, ''));
        const deliveryFee = parseFloat(getElement('#feeDisplay').textContent.replace(/[.,đ\s]+/g, ''));
        discountShipping = Math.min((deliveryFee * discountPercent) / 100, discountMaxValue);
        getElement('#discountFee').textContent = `-${discountShipping.toLocaleString('vi-VN')}đ`;
        getElement('#discountFee').classList.add('text-danger');
    }

    updateFinalTotal();
}

function selectVoucherItems(row) {
    if (selectedItems.size === 0) return;

    const isSelected = row.style.backgroundColor === 'rgb(144, 238, 144)';
    const table = row.closest('table');
    table.querySelectorAll('tr').forEach((tr) => (tr.style.backgroundColor = ''));

    const radio = getElement('input[type="radio"]', row);
    if (isSelected) {
        radio.checked = false;
        discountItems = 0;
        getElement('#discountItems').textContent = '0đ';
        getElement('#discountItems').classList.remove('text-danger');
    } else {
        radio.checked = true;
        row.style.backgroundColor = '#90EE90';
        let total = 0;
        const discountPercent = parseFloat(getElement('td:nth-child(2) div:nth-child(1)', row).textContent.replace(/[^\d]/g, ''));
        const discountMaxValue = parseFloat(getElement('td:nth-child(2) div:nth-child(2)', row).textContent.replace(/[^\d]/g, ''));
        selectedItems.forEach((sizeId) => {
            const item = getElement(`.items input[type="hidden"][value="${sizeId}"]`).closest('.items');
            const price = parseFloat(getElement('.select-item', item).getAttribute('data-price').replace(/[^0-9.-]+/g, ''));
            const quantity = parseInt(getElement('input[name="quantity"]', item).value, 10);
            total += price * quantity;
        });
        discountItems = Math.min((total * discountPercent) / 100, discountMaxValue);
        getElement('#discountItems').textContent = `-${discountItems.toLocaleString('vi-VN')}đ`;
        getElement('#discountItems').classList.add('text-danger');
    }

    updateFinalTotal();
}

function updateFinalTotal() {
    const finalTotal = originalTotalAmount - discountShipping - discountItems;
    getElement('#totalAmountModalVoucher').textContent = formatPrice(finalTotal);
}

function confirmVoucher() {
    showAlert(
        'Bạn có chắc chắn chọn các Voucher này?',
        'Hãy kiểm tra kỹ thông tin trước khi xác nhận!',
        'warning',
        'OK',
        true
    ).then((result) => {
        if (result.isConfirmed) {
            const finalTotal = parseFloat(getElement('#totalAmountModalVoucher').textContent.replace(/[.,đ\s]+/g, ''));
            getElement('#totalAmount').textContent = formatPrice(finalTotal);

            discountShipping = parseFloat(getElement('#discountFee').textContent.replace(/[^\d]/g, '')) || 0;
            discountItems = parseFloat(getElement('#discountItems').textContent.replace(/[^\d]/g, '')) || 0;

            const selectedVouchers = [];
            const selectedShipping = getElement('input[name="selectedVoucherShipping"]:checked');
            if (selectedShipping) {
                const row = selectedShipping.closest('tr');
                const discountText = getElement('td:nth-child(2) div:nth-child(1)', row).textContent;
                const discountMaxValue = getElement('td:nth-child(2) div:nth-child(2)', row).textContent;
                selectedVouchers.push(`
          <tr style="cursor: pointer">
            <td><i class="fa-solid fa-truck-fast" style="font-size: 40px"></i></td>
            <td><div style="font-weight: 600">Giảm giá vận chuyển</div><div>${discountText}</div><div>${discountMaxValue}</div></td>
          </tr>
        `);
            }

            const selectedItem = getElement('input[name="selectedVoucherItems"]:checked');
            if (selectedItem) {
                const row = selectedItem.closest('tr');
                const discountText = getElement('td:nth-child(2) div:nth-child(1)', row).textContent;
                const discountMaxValue = getElement('td:nth-child(2) div:nth-child(2)', row).textContent;
                selectedVouchers.push(`
          <tr style="cursor: pointer">
            <td><i class="fa-solid fa-bag-shopping" style="font-size: 40px"></i></td>
            <td><div style="font-weight: 600">Giảm giá sản phẩm</div><div>${discountText}</div><div>${discountMaxValue}</div></td>
          </tr>
        `);
            }

            getElement('#voucherSelected').innerHTML = selectedVouchers.length
                ? `<br><h5 class="mb-3">Voucher đang sử dụng</h5><table class="table table-hover"><tbody>${selectedVouchers.join('')}</tbody></table>`
                : '';

            showAlert('Xác nhận thành công!', 'Voucher của bạn đã được áp dụng thành công.', 'success');
        } else {
            new bootstrap.Modal(getElement('#myModal')).show();
        }
    });
}

// Copy order info
function copyInfo() {
    getElement('#copyBtn').addEventListener('click', () => {
        const textarea = getElement('#orderContent');
        const text = textarea.value;
        navigator.clipboard.writeText(text).then(() => {
            getElement('#copyBtn').innerHTML = `<svg style="vertical-align: middle" width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg" class="icon-xs"><path fill-rule="evenodd" clip-rule="evenodd" d="M18.0633 5.67387C18.5196 5.98499 18.6374 6.60712 18.3262 7.06343L10.8262 18.0634C10.6585 18.3095 10.3898 18.4679 10.0934 18.4957C9.79688 18.5235 9.50345 18.4178 9.29289 18.2072L4.79289 13.7072C4.40237 13.3167 4.40237 12.6835 4.79289 12.293C5.18342 11.9025 5.81658 11.9025 6.20711 12.293L9.85368 15.9396L16.6738 5.93676C16.9849 5.48045 17.607 5.36275 18.0633 5.67387Z" fill="currentColor"></path></svg> Saved`;
            setTimeout(() => {
                getElement('#copyBtn').innerHTML = `<svg style="vertical-align: middle" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg" class="icon-xs"><path fill-rule="evenodd" clip-rule="evenodd" d="M7 5C7 3.34315 8.34315 2 10 2H19C20.6569 2 22 3.34315 22 5V14C22 15.6569 20.6569 17 19 17H17V19C17 20.6569 15.6569 22 14 22H5C3.34315 22 2 20.6569 2 19V10C2 8.34315 3.34315 7 5 7H7V5ZM9 7H14C15.6569 7 17 8.34315 17 10V15H19C19.5523 15 20 14.5523 20 14V5C20 4.44772 19.5523 4 19 4H10C9.44772 4 9 4.44772 9 5V7ZM5 9C4.44772 9 4 9.44772 4 10V19C4 19.5523 4.44772 20 5 20H14C14.5523 20 15 19.5523 15 19V10C15 9.44772 14.5523 9 14 9H5Z" fill="currentColor"></path></svg> Copy`;
            }, 2000);
        }).catch((err) => console.error('Copy error:', err));
    });
}

// Event listeners
document.addEventListener('DOMContentLoaded', () => {
    // Initialize fee display
    const updateFeeDisplay = () => {
        const select = getElement('#deliverySelect');
        const fee = select.options[select.selectedIndex].getAttribute('data-fee');
        getElement('#feeDisplay').textContent = formatPrice(parseFloat(fee));
        updateTotalPrice();
    };
    updateFeeDisplay();
    getElement('#deliverySelect').addEventListener('change', updateFeeDisplay);

    // Cart item selection
    getElements('.items').forEach((item) => {
        item.addEventListener('click', () => {
            const sizeId = getElement('input[type="hidden"]', item).value;
            if (selectedItems.has(sizeId)) {
                selectedItems.delete(sizeId);
                item.classList.remove('selected');
            } else {
                selectedItems.add(sizeId);
                item.classList.add('selected');
            }
            getElement('#totalSelectedItems').textContent = `${selectedItems.size} món`;
            updateTotalPrice();
        });
    });
});