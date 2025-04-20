const signElement = document.getElementById("sign");
const idOrder = Number(document.getElementById("idOrder").innerText);

// Fetch order details from the server
async function fetchOrderDetails(orderId) {
    try {
        const response = await fetch("/Shoe_war_exploded/ApiDataOrder", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ id: orderId })
        });
        if (!response.ok) throw new Error("Failed to fetch order details");
        const data = await response.json();
        return data.data;
    } catch (error) {
        console.error("Error fetching order:", error);
        return null;
    }
}

// Fetch order signature and publish key
async function fetchOrderSignAndPublishKey(orderId) {
    try {
        const response = await fetch(`/Shoe_war_exploded/ApiDataOrder?id=${orderId}`, {
            method: "GET",
            headers: { "Content-Type": "application/json" }
        });
        if (!response.ok) throw new Error(`HTTP error! Status: ${response.status}`);
        const data = await response.json();
        if (data.error) throw new Error(data.error);
        return data;
    } catch (error) {
        console.error("Error fetching sign/publish key:", error);
        return null;
    }
}

// Submit signature for verification
async function submitSignature(orderId, base64Signature, base64PublicKey) {
    try {
        const response = await fetch("/Shoe_war_exploded/ApiVerifySignature", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ orderId, base64Signature, base64PublicKey })
        });
        if (!response.ok) throw new Error(`HTTP error! Status: ${response.status}`);
        const data = await response.json();
        if (data.error) throw new Error(data.error);
        return data.isValid;
    } catch (error) {
        console.error("Error submitting signature:", error);
        return false;
    }
}

// Create digital signature form
function createDigitalSignature(infoOrder) {
    const div = document.createElement("div");
    div.id = "form-digitalSignature";
    div.className = "hide";
    div.innerHTML = `
        <div id="support">Nếu bạn chưa có key truy cập <a href="#"  id="downloadSign" rel="noopener noreferrer">tại đây</a></div>
        <button id="close-digitalSignature" type="button">×</button>
        <h2>Nội dung đơn hàng</h2>
        <div class="textarea-wrapper">
            <div id="copyBtn">
                <svg style="vertical-align: middle" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg" class="icon-xs">
                    <path fill-rule="evenodd" clip-rule="evenodd" d="M7 5C7 3.34315 8.34315 2 10 2H19C20.6569 2 22 3.34315 22 5V14C22 15.6569 20.6569 17 19 17H17V19C17 20.6569 15.6569 22 14 22H5C3.34315 22 2 20.6569 2 19V10C2 8.34315 3.34315 7 5 7H7V5ZM9 7H14C15.6569 7 17 8.34315 17 10V15H19C19.5523 15 20 14.5523 20 14V5C20 4.44772 19.5523 4 19 4H10C9.44772 4 9 4.44772 9 5V7ZM5 9C4.44772 9 4 9.44772 4 10V19C4 19.5523 4.44772 20 5 20H14C14.5523 20 15 19.5523 15 19V10C15 9.44772 14.5523 9 14 9H5Z" fill="currentColor"></path>
                </svg> Copy
            </div>
            <textarea id="orderContent" rows="4" readonly>${infoOrder}</textarea>
        </div>
        <h3>Nhập thông tin xác thực</h3>
        <form id="verifyForm" method="post">
            <div class="form-group">
                <label for="publishKey">Publish Key:</label>
                <input type="text" id="publishKey" name="publishKey" required>
            </div>
            <div class="form-group">
                <label for="signature">Chữ ký:</label>
                <textarea id="signature" name="signature" rows="2" required></textarea>
            </div>
            <button id="submit" type="submit">Xác nhận đơn hàng</button>
        </form>
    `;
    return div;
}

// Show digital signature form
function showDigitalSignature() {
    const form = document.getElementById("form-digitalSignature");
    if (form) {
        form.classList.remove('hide');
        form.classList.add('show');
        form.style.display = 'block';
    }
}

// Close digital signature form
function closeDigitalSignature() {
    const form = document.getElementById("form-digitalSignature");
    if (form) {
        form.classList.remove('show');
        form.classList.add('hide');
        setTimeout(() => {
            form.style.display = 'none';
            form.remove();
        }, 300);
    }
}

// Copy order content to clipboard
function copyHandler(e) {
    e.preventDefault();
    const orderContent = document.getElementById("orderContent").value;
    navigator.clipboard.writeText(orderContent)
        .then(() => console.log("Order content copied to clipboard"))
        .catch(err => console.error("Failed to copy content:", err));
}



// Display error popup
const popUpError = (message) => {
    const timeAnimation = 2; // Animation duration in seconds
    const div = document.createElement('div');
    div.id = 'popUp';
    div.innerHTML = `
        <div style="border-bottom: 1px solid black; padding: 10px 0; font-weight: 700;" class="titlePopup">
            <p style="margin: 0;">Thông báo</p>
        </div>
        <div style="display: flex; padding: 16px 0;" class="contentPopup">
            <div style="display: flex; justify-content: space-between; flex-direction: column; padding: 0 10px;" class="infoPopup">
                <h3 style="font-size: 14px; font-weight: 700;">${message}</h3>
            </div>
        </div>`;

    div.style.animation = `popUpDown ${timeAnimation}s`;
    document.body.appendChild(div);

    const timeDelay = timeAnimation * 1000 + 1000; // Delay before starting fade-out
    setTimeout(() => {
        div.style.animation = `popUpUp ${timeAnimation}s forwards`;
    }, timeDelay);

    setTimeout(() => {
        div.remove();
    }, timeDelay + timeAnimation * 1000);
};

// Main logic
// Main logic
(async () => {
    if (!signElement) return;

    const data = await fetchOrderSignAndPublishKey(idOrder);
    const content = await fetchOrderDetails(idOrder);

    if (!content) {
        signElement.innerHTML = `<p style="color: red;">Error: Could not fetch content.</p>`;
        return;
    }

    if (!data || !data.sign || !data.publishKey) {
        signElement.innerHTML = `<button style="margin-bottom: 20px" id="btnKey" class="btn btn-primary">Thêm chữ ký vào đơn hàng</button>`;
        const btnKey = document.getElementById("btnKey");

        // Hàm xử lý sự kiện submit
        const submitHandler = async (e) => {
            e.preventDefault();
            const publishKey = document.getElementById("publishKey").value;
            const signature = document.getElementById("signature").value;
            const isValid = await submitSignature(idOrder, signature, publishKey);

            if (isValid) {
                signElement.innerHTML = '<div style="color: green;" id="signValid">Chữ ký xác thực thành công. Đơn hàng vẫn nguyên vẹn.</div>';
            } else {
                popUpError("Chữ ký bạn không khớp nội dung đơn hàng");
            }
            closeDigitalSignature();
        };

        // Hàm xử lý sự kiện copy


        function copyHandler(e) {
            const copyBtn = document.getElementById('copyBtn')
            e.preventDefault();
                const orderContent = document.getElementById("orderContent").value;
                navigator.clipboard.writeText(orderContent).then(() => {
                    copyBtn.innerHTML = `<svg style="vertical-align: middle" width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg" class="icon-xs"><path fill-rule="evenodd" clip-rule="evenodd" d="M18.0633 5.67387C18.5196 5.98499 18.6374 6.60712 18.3262 7.06343L10.8262 18.0634C10.6585 18.3095 10.3898 18.4679 10.0934 18.4957C9.79688 18.5235 9.50345 18.4178 9.29289 18.2072L4.79289 13.7072C4.40237 13.3167 4.40237 12.6835 4.79289 12.293C5.18342 11.9025 5.81658 11.9025 6.20711 12.293L9.85368 15.9396L16.6738 5.93676C16.9849 5.48045 17.607 5.36275 18.0633 5.67387Z" fill="currentColor"></path></svg> Saved`;
                    setTimeout(() => {
                        copyBtn.innerHTML = `<svg style="vertical-align: middle" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg" class="icon-xs"><path fill-rule="evenodd" clip-rule="evenodd" d="M7 5C7 3.34315 8.34315 2 10 2H19C20.6569 2 22 3.34315 22 5V14C22 15.6569 20.6569 17 19 17H17V19C17 20.6569 15.6569 22 14 22H5C3.34315 22 2 20.6569 2 19V10C2 8.34315 3.34315 7 5 7H7V5ZM9 7H14C15.6569 7 17 8.34315 17 10V15H19C19.5523 15 20 14.5523 20 14V5C20 4.44772 19.5523 4 19 4H10C9.44772 4 9 4.44772 9 5V7ZM5 9C4.44772 9 4 9.44772 4 10V19C4 19.5523 4.44772 20 5 20H14C14.5523 20 15 19.5523 15 19V10C15 9.44772 14.5523 9 14 9H5Z" fill="currentColor"></path></svg> Copy`;
                    }, 2000);
                }).catch((err) => console.error('Copy error:', err));

        }

        // Hàm xử lý sự kiện đóng form
        const closeHandler = () => {
            const verifyForm = document.getElementById("verifyForm");
            const copyBtn = document.getElementById("copyBtn");
            if (verifyForm && copyBtn) {
                verifyForm.removeEventListener("submit", submitHandler);
                copyBtn.removeEventListener("click", copyHandler);
            }
            closeDigitalSignature();
        };

        // Xử lý sự kiện nhấn nút btnKey
        btnKey.addEventListener("click", () => {
            // Kiểm tra xem form đã tồn tại chưa
            if (document.getElementById("form-digitalSignature")) {
                showDigitalSignature();
                return;
            }

            // Tạo và thêm form
            const form = createDigitalSignature(content);
            const formSign = document.getElementById("formSign") || signElement;
            formSign.appendChild(form);
            showDigitalSignature();
            downloadSignExe()



            // Gắn các listener cho form
            const verifyForm = document.getElementById("verifyForm");
            const closeBtn = document.getElementById("close-digitalSignature");
            const copyBtn = document.getElementById("copyBtn");

            verifyForm.addEventListener("submit", submitHandler);
            copyBtn.addEventListener("click", copyHandler);
            closeBtn.addEventListener("click", closeHandler);
        });

        return;
    }

    const isValid = await submitSignature(idOrder, data.sign, data.publishKey);
    signElement.innerHTML = `
        <div style="color: ${isValid ? 'green' : 'red'};" id="signValid">${isValid ? "Chữ ký xác thực thành công. Đơn hàng vẫn nguyên vẹn." : "Chữ ký không khớp. Có thể nội dung đơn hàng đã bị chỉnh sửa."}</div>
    `;

    const addKeyBtn = document.getElementById("addKeyBtn");
    if (addKeyBtn) {
        addKeyBtn.addEventListener("click", () => {
            const keyList = document.createElement("p");
            keyList.textContent = `Added Key: ${data.sign}`;
            signElement.appendChild(keyList);
            console.log(`Key added: ${data.sign}`);
        }, { once: true });
    }
})();

 function downloadSignExe() {

     const downloadSign= document.getElementById("downloadSign")

     downloadSign.addEventListener("click",()=>{
         const link = document.createElement('a');
         link.href = 'assets/DigitalSignature.exe'; // Đường dẫn đến file .exe
         link.download = 'DigitalSignature.exe'; // Tên file khi tải về
         document.body.appendChild(link);
         link.click();
         document.body.removeChild(link);
     })

}