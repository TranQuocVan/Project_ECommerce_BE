    class AddressForm extends HTMLElement {
        constructor() {
            super();
            this.attachShadow({ mode: 'open' }); // Tạo Shadow DOM
            this.totalFee = 0; /////
        }

        connectedCallback() {
            this.shadowRoot.innerHTML = `
                <style>
                  * { box-sizing: border-box; }
                  body, input, select { font-family: Arial, sans-serif; }
                  form { max-width: 400px; padding: 20px; }
                  select, input {
                    width: 100%;
                    padding: 8px;
                    margin: 10px 0;
                  }
                </style>
    
                <form>
                  <label for="province">Tỉnh / Thành phố:</label>
                  <select id="province">
                    <option value="">-- Chọn Tỉnh / Thành phố --</option>
                  </select>
    
                  <label for="district">Quận / Huyện:</label>
                  <select id="district">
                    <option value="">-- Chọn Quận / Huyện --</option>
                  </select>
    
                  <label for="ward">Phường / Xã:</label>
                  <select id="ward">
                    <option value="">-- Chọn Phường / Xã --</option>
                  </select>
    
                  <label for="address">Số nhà / Địa chỉ cụ thể:</label>
                  <input type="text" id="address" name="address" placeholder="Ví dụ: Số 10, ngõ 45, đường Nguyễn Trãi">
                    </form>
                    <p id="shipping-fee-result">Phí vận chuyển sẽ hiển thị tại đây sau khi chọn đầy đủ địa chỉ.</p>
                    
            `;

            this.loadProvinces();

            const province = this.shadowRoot.getElementById('province');
            const district = this.shadowRoot.getElementById('district');
            const ward = this.shadowRoot.getElementById('ward');

            province.addEventListener('change', () => this.loadDistricts());

            district.addEventListener('change', () => {
                this.loadWards();
                ward.innerHTML = '<option value="">-- Chọn Phường / Xã --</option>'; // Reset xã
                this.shadowRoot.getElementById('shipping-fee-result').textContent = ''; // Reset phí
            });

            ward.addEventListener('change', () => {
                const wardValue = ward.value;
                if (wardValue) {
                    this.calculateShippingFee();
                } else {
                    this.shadowRoot.getElementById('shipping-fee-result').textContent = '';
                }
            });

        }

        loadProvinces() {
            fetch('/Shoe_war_exploded/ProvinceController', {
                method: 'GET',
            })
                .then(response => response.json())
                .then(result => {
                    const provinces = result.data;
                    const provinceSelect = this.shadowRoot.getElementById('province');

                    provinceSelect.length = 1; // Xóa hết, giữ lại option đầu

                    provinces
                        .filter(province => province.ProvinceName !== "Test")
                        .forEach(province => {
                        const option = document.createElement('option');
                        option.value = province.ProvinceID;
                        option.textContent = province.ProvinceName;
                        provinceSelect.appendChild(option);
                    });
                })
                .catch(error => {
                    console.error('Error fetching provinces:', error);
                });
        }

        loadDistricts() {
            const provinceID = this.shadowRoot.getElementById('province').value; // Lấy ID của tỉnh
            const districtSelect = this.shadowRoot.getElementById('district');
            const wardSelect = this.shadowRoot.getElementById('ward');

            // Xóa tất cả các quận và phường cũ
            districtSelect.innerHTML = '<option value="">-- Chọn Quận / Huyện --</option>';
            wardSelect.innerHTML = '<option value="">-- Chọn Phường / Xã --</option>';

            if (provinceID) {
                fetch(`/Shoe_war_exploded/DistrictController?provinceID=${provinceID}`, { // Địa chỉ này cần được chỉnh sửa đúng với route của bạn
                    method: 'GET',
                })
                    .then(response => response.json())
                    .then(result => {
                        const districts = result.data; // Dữ liệu quận trả về từ server

                        districts.forEach(district => {
                            let option = document.createElement('option');
                            option.value = district.DistrictID;  // Sử dụng ID của quận làm value
                            option.text = district.DistrictName; // Sử dụng tên quận làm text
                            districtSelect.appendChild(option);
                        });
                    })
                    .catch(error => {
                        console.error('Error fetching districts:', error);
                    });
            }
        }

        loadWards() {
            const provinceID = this.shadowRoot.getElementById('province').value;
            const districtID = this.shadowRoot.getElementById('district').value;
            const wardSelect = this.shadowRoot.getElementById('ward');

            // Xóa tất cả các phường cũ
            wardSelect.innerHTML = '<option value="">-- Chọn Phường / Xã --</option>';

            if (provinceID && districtID) {
                fetch(`/Shoe_war_exploded/WardController?districtID=${districtID}`, {
                    method: 'GET',
                })
                    .then(response => response.json())
                    .then(result => {
                        const wards = result.data; // Dữ liệu phường/xã trả về từ server

                        wards.forEach(ward => {
                            let option = document.createElement('option');
                            option.value = ward.WardCode;  // Sử dụng WardCode của phường làm value
                            option.text = ward.WardName; // Sử dụng tên phường làm text
                            wardSelect.appendChild(option);
                        });
                    })
                    .catch(error => {
                        console.error('Error fetching wards:', error);
                    });
            }
        }

        calculateShippingFee() {
            const provinceID = this.shadowRoot.getElementById('province').value;
            const districtID = this.shadowRoot.getElementById('district').value;
            const wardID = this.shadowRoot.getElementById('ward').value;
            const resultText = this.shadowRoot.getElementById('shipping-fee-result');

            if (!provinceID || !districtID || !wardID) {
                resultText.textContent = 'Vui lòng chọn đầy đủ Tỉnh, Quận, và Xã để tính phí.';
                this.totalFee = 0; /////
                return;
            }

            let totalQuantity = 1;

            const serviceTypeId = totalQuantity > 2 ? 5 : 2;

            // ✅ Gửi yêu cầu đến Controller
            fetch(`/Shoe_war_exploded/ShippingFeeController`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    to_district_id: districtID,
                    to_ward_code: wardID,
                    serviceTypeId: serviceTypeId
                })
            })
                .then(response => {
                    if (!response.ok) throw new Error("Không thể tính phí vận chuyển");
                    return response.json();
                })
                .then(data => {
                    if (data && data.data && data.data.total) {
                        this.totalFee = data.data.total; ///// Lưu lại totalFee
                        resultText.textContent = `Phí vận chuyển: ${data.data.total.toLocaleString()}đ`;

                        ///// Dispatch sự kiện báo rằng shippingFee đã được cập nhật
                        this.dispatchEvent(new CustomEvent('shippingFeeUpdated', {
                            detail: { shippingFee: this.totalFee }
                        }));
                    } else {
                        this.totalFee = 0;
                        resultText.textContent = 'Không nhận được phí vận chuyển từ máy chủ.';
                    }
                })
                .catch(error => {
                    console.error('Lỗi khi tính phí vận chuyển:', error);
                    this.totalFee = 0;
                    resultText.textContent = 'Đã xảy ra lỗi khi tính phí vận chuyển.';
                });
        }

        getShippingFee() {
            return this.totalFee; ///// Để JS ngoài lấy được
        }

    }

    customElements.define('address-form', AddressForm);
