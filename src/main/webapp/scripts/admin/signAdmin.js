document.addEventListener('DOMContentLoaded', () => {
    const loadingDiv = document.getElementById('loading');
    const errorDiv = document.getElementById('error');
    const formContainer = document.querySelector('.form-container');
    const filterForm = document.querySelector('form');
    const signatureStatusFilter = document.getElementById('signatureStatusFilter');
    const orderIdFilter = document.getElementById('orderIdFilter');

    // Function to fetch order signature data
    const fetchOrderSignatures = (filters = {}) => {
        // Show loading
        loadingDiv.style.display = 'block';
        errorDiv.style.display = 'none';
        formContainer.style.display = 'none';

        // Prepare request body
        const requestBody = {
            signatureStatus: filters.signatureStatus || '0',
            orderId: filters.orderId || ''
        };

        fetch('/Shoe_war_exploded/SignAdminController', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(requestBody)
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`Lỗi HTTP! Trạng thái: ${response.status}`);
                }
                return response.json();
            })
            .then(data => {
                // Hide loading
                loadingDiv.style.display = 'none';

                if (data.error) {
                    // Show error
                    errorDiv.textContent = data.error;
                    errorDiv.style.display = 'block';
                    return;
                }

                // Show form container
                formContainer.style.display = 'block';

                // Clear existing content
                const orderList = formContainer.querySelector('.area-shoes')?.parentNode;
                if (orderList) {
                    orderList.innerHTML = '';
                }

                // Check if data is empty
                if (!data || data.length === 0) {
                    formContainer.innerHTML = `
                        <div class="alert alert-info text-center mt-4" role="alert">
                            Dữ liệu trống
                        </div>
                    `;
                    return;
                }
                console.log(data)

                // Render order signatures
                data.forEach(order => {
                    const orderCard = document.createElement('div');
                    orderCard.className = 'area-shoes mb-4';
                    orderCard.innerHTML = `
                        <div class="card">
                            <div class="card-body">
                                <div class="row align-items-center text-center">
                                    <div class="col-md-3">
                                        <p class="small text-muted mb-1">ID Đơn hàng</p>
                                        <p class="lead fw-normal mb-0">${order.id}</p>
                                    </div>
                                    <div class="col-md-3">
                                        <p class="small text-muted mb-1">Trạng thái chữ ký</p>
                                        <p class="status fw-normal mb-0">${order.signStatus}</p>
                              
                                    </div>
                                    <div class="col-md-3">
<!--                                        <button type="button" class="btn btn-warning mt-2" onclick="editStatus(this)">Chỉnh sửa</button>-->
<!--                                        <button type="button" class="btn btn-success mt-2" onclick="submitForm(this)" style="display: none;">Xác nhận</button>-->
                                    </div>
                                </div>
                            </div>
                        </div>
                    `;
                    formContainer.appendChild(orderCard);
                });
            })
            .catch(error => {
                // Handle error
                loadingDiv.style.display = 'none';
                errorDiv.textContent = `Lỗi: ${error.message}`;
                errorDiv.style.display = 'block';
                formContainer.style.display = 'none';
                console.error('Lỗi:', error);
            });
    };

    // Initial data load
    fetchOrderSignatures();

    // Handle form submission
    filterForm.addEventListener('submit', (e) => {
        e.preventDefault();
        const filters = {
            signatureStatus: signatureStatusFilter.value,
            orderId: orderIdFilter.value.trim()
        };
        fetchOrderSignatures(filters);
    });

    // Reset filters on page reload
    signatureStatusFilter.value = '0';
    orderIdFilter.value = '';
});

// Function to update hidden input for signature status
function updateHiddenSignatureStatus(selectElement) {
    const form = selectElement.closest('form');
    const hiddenInput = form.querySelector('#signatureStatus');
    hiddenInput.value = selectElement.value;
}