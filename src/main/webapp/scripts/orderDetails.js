const signElement = document.getElementById("sign");
const idOrder = Number(document.getElementById("idOrder").innerText);
const gmail = document.getElementById("gmail").innerText;

// Fetch order details from the server
async function fetchOrderDetails(orderId) {
    try {
        const response = await fetch("/Shoe_war_exploded/ApiDataOrder", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ id: orderId }),
        });
        if (!response.ok) throw new Error("Failed to fetch order details");
        const { data } = await response.json();
        return data;
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
            headers: { "Content-Type": "application/json" },
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

// Check if order has a signature
async function checkHasKey() {
    try {
        const response = await fetch(`/Shoe_war_exploded/KeyController`, {
            method: "POST", // Changed from GET to POST
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ gmail }), // Send gmail in the body as JSON
        });
        if (!response.ok) throw new Error(`Failed to fetch key status: ${response.status}`);
        const data = await response.json();
        if (data.error) throw new Error(data.error);
        return data.hasKey; // Assuming the response contains "hasKey"
    } catch (error) {
        console.error("Error checking key status:", error);
        return false;
    }
}

// Submit signature for verification
async function submitSignature(orderId, base64Signature) {
    try {
        const response = await fetch("/Shoe_war_exploded/ApiVerifySignature", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ orderId, base64Signature, gmail }),
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

// Submit new publish key and email for creation
async function submitPublishKey(publishKey, gmail) {
    try {
        const response = await fetch("/Shoe_war_exploded/AddKeyController", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({publishKey, gmail}),
        });
        if (!response.ok) throw new Error(`HTTP error! Status: ${response.status}`);
        const data = await response.json();
        if (data.error) throw new Error(data.error);
        return data.success;
    } catch (error) {
        console.error("Error submitting publish key:", error);
        return false;
    }
}

// Create digital signature/key creation form
function createDigitalSignature(infoOrder) {
    const div = document.createElement("div");
    div.id = "form-digitalSignature";
    div.className = "hide";
    div.innerHTML = `
        <div id="support">Nếu bạn chưa có key truy cập <a href="#" id="downloadSign" rel="noopener noreferrer">tại đây</a></div>
        <button id="close-digitalSignature" type="button">×</button>
        <h2>"Nội dung đơn hàng</h2>
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
                <label for="signature"> Chữ ký</label>
                <textarea id="signature" name="signature" rows="2" required></textarea>
            </div>
            <button id="submit" type="submit">Xác nhận đơn hàng</button>
        </form>
    `;
    return div;
}

// Create key input form for publish key and email
function createKeyInputForm(gmail) {
    const div = document.createElement("div");
    div.id = "key-input-form";
    div.style = "color: #000; background-color: #fff; position: fixed; top: 50%; left: 50%; transform: translate(-50%, -50%); width: 800px; padding: 20px; border: 1px solid #ccc; border-radius: 8px; box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1); z-index: 9999;";
    div.innerHTML = `
        <div id="downloadSign">
            <div id="support">Nếu bạn chưa có key truy cập</div>
             <a href="#"  rel="noopener noreferrer">tại đây</a>
        </div>
      
        <h2 style="text-align: center;">Tạo Publish Key</h2>
        <form id="keyForm" method="post">
            <div style="margin-bottom: 15px;">
                <label for="publishKey" style="display: block; margin-bottom: 5px;">Publish Key:</label>
                <textarea id="publishKey" name="publishKey" rows="2" style="width: 100%; padding: 8px;" required></textarea>
            </div>
            <div style="display: none" 15px;">
                <div id="email">${gmail}</div>
            </div>
            <button type="submit" style="width: 100%; padding: 10px; background-color: #007bff; color: white; border: none; border-radius: 4px; cursor: pointer;">Gửi</button>
        </form>
    `;
    return div;
}

// Show digital signature form
function showDigitalSignature() {
    const form = document.getElementById("form-digitalSignature");
    if (form) {
        form.classList.remove("hide");
        form.classList.add("show");
        form.style.display = "block";
    }
}

// Close digital signature form
function closeDigitalSignature() {
    const form = document.getElementById("form-digitalSignature");
    if (form) {
        form.classList.remove("show");
        form.classList.add("hide");
        setTimeout(() => {
            form.style.display = "none";
            form.remove();
        }, 300);
    }
}

// Display error popup
function showErrorPopup(message) {
    const animationDuration = 2; // Seconds
    const div = document.createElement("div");
    div.id = "popUp";
    div.innerHTML = `
        <div style="border-bottom: 1px solid black; padding: 10px 0; font-weight: 700;" class="titlePopup">
            <p style="margin: 0;">Thông báo</p>
        </div>
        <div style="display: flex; padding: 16px 0;" class="contentPopup">
            <div style="display: flex; justify-content: space-between; flex-direction: column; padding: 0 10px;" class="infoPopup">
                <h3 style="font-size: 14px; font-weight: 700;">${message}</h3>
            </div>
        </div>`;
    div.style.animation = `popUpDown ${animationDuration}s`;
    document.body.appendChild(div);

    const timeDelay = animationDuration * 1000 + 1000;
    setTimeout(() => {
        div.style.animation = `popUpUp ${animationDuration}s forwards`;
    }, timeDelay);
    setTimeout(() => div.remove(), timeDelay + animationDuration * 1000);
}

// Initialize and attach form event listeners for signature form
function setupFormListeners(content) {
    const verifyForm = document.getElementById("verifyForm");
    const copyBtn = document.getElementById("copyBtn");
    const closeBtn = document.getElementById("close-digitalSignature");

    const submitHandler = async (e) => {
        e.preventDefault();
        const input = document.getElementById("signature").value;
        const success = await submitSignature(idOrder, input);
        if (success) {
            signElement.innerHTML = `<div style="color: green;" id="signValid">
                Chữ ký xác thực thành công. Đơn hàng vẫn nguyên vẹn.
            }</div>`;
        } else {
            showErrorPopup("Chữ ký bạn không khớp nội dung đơn hàng");
        }
        closeDigitalSignature();
    };

    const copyHandler = (e) => {
        e.preventDefault();
        const orderContent = document.getElementById("orderContent").value;
        navigator.clipboard.writeText(orderContent).then(() => {
            copyBtn.innerHTML = `<svg style="vertical-align: middle" width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg" class="icon-xs"><path fill-rule="evenodd" clip-rule="evenodd" d="M18.0633 5.67387C18.5196 5.98499 18.6374 6.60712 18.3262 7.06343L10.8262 18.0634C10.6585 18.3095 10.3898 18.4679 10.0934 18.4957C9.79688 18.5235 9.50345 18.4178 9.29289 18.2072L4.79289 13.7072C4.40237 13.3167 4.40237 12.6835 4.79289 12.293C5.18342 11.9025 5.81658 11.9025 6.20711 12.293L9.85368 15.9396L16.6738 5.93676C16.9849 5.48045 17.607 5.36275 18.0633 5.67387Z" fill="currentColor"></path></svg> Saved`;
            setTimeout(() => {
                copyBtn.innerHTML = `<svg style="vertical-align: middle" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg" class="icon-xs"><path fill-rule="evenodd" clip-rule="evenodd" d="M7 5C7 3.34315 8.34315 2 10 2H19C20.6569 2 22 3.34315 22 5V14C22 15.6569 20.6569 17 19 17H17V19C17 20.6569 15.6569 22 14 22H5C3.34315 22 2 20.6569 2 19V10C2 8.34315 3.34315 7 5 7H7V5ZM9 7H14C15.6569 7 17 8.34315 17 10V15H19C19.5523 15 20 14.5523 20 14V5C20 4.44772 19.5523 4 19 4H10C9.44772 4 9 4.44772 9 5V7ZM5 9C4.44772 9 4 9.44772 4 10V19C4 19.5523 4.44772 20 5 20H14C14.5523 20 15 19.5523 15 19V10C15 9.44772 14.5523 9 14 9H5Z" fill="currentColor"></path></svg> Copy`;
            }, 2000);
        }).catch((err) => console.error("Copy error:", err));
    };

    const closeHandler = () => {
        verifyForm.removeEventListener("submit", submitHandler);
        copyBtn.removeEventListener("click", copyHandler);
        closeDigitalSignature();
    };

    verifyForm.addEventListener("submit", submitHandler);
    copyBtn.addEventListener("click", copyHandler);
    closeBtn.addEventListener("click", closeHandler);
}

// Initialize and attach event listeners for key input form
function setupKeyInputFormListeners() {
    const keyForm = document.getElementById("keyForm");
    if (!keyForm) return;

    const submitHandler = async (e) => {
        e.preventDefault();
        const publishKey = document.getElementById("publishKey").value;
        const success = await submitPublishKey(publishKey, gmail);
        if (success) {
            document.body.removeChild(keyForm.parentElement);
            showErrorPopup("Tạo Publish Key thành công")
            setTimeout(() => {
                location.reload();
            }, 2000); // delay 1 giây để người dùng thấy popup
        } else {
            showErrorPopup("Tạo Publish Key thất bại. Vui lòng thử lại.");
        }
    };

    keyForm.addEventListener("submit", submitHandler);
}

// Show signature/key creation form and set up download link
function showSignatureForm(content, isKeyCreation = false) {
    if (document.getElementById("form-digitalSignature")) {
        showDigitalSignature();
        return;
    }
    const form = createDigitalSignature(content, isKeyCreation);
    const formSign = document.getElementById("formSign") || signElement;
    formSign.appendChild(form);
    showDigitalSignature();
    setupFormListeners(content, isKeyCreation);

    const downloadSign = document.getElementById("downloadSign");
    downloadSign.addEventListener("click", () => {
        const link = document.createElement("a");
        link.href = "assets/DigitalSignature.exe";
        link.download = "DigitalSignature.exe";
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
    });
}

// Main logic
(async () => {
    if (!signElement) return;

    const [data, content, hasSignature] = await Promise.all([
        fetchOrderSignAndPublishKey(idOrder),
        fetchOrderDetails(idOrder),
        checkHasKey(),
    ]);

    if (!content) {
        signElement.innerHTML = `<p style="color: red;">Error: Could not fetch content.</p>`;
        return;
    }

    if (!hasSignature) {
        // Add a button to show the key input form
        signElement.innerHTML = `<button style="margin-bottom: 20px" id="btnShowKeyForm" class="btn btn-primary">Thêm khoá công khai trên hệ thống để ký đơn hàng</button>`;
        const btnShowKeyForm = document.getElementById("btnShowKeyForm");
        if (btnShowKeyForm) {
            btnShowKeyForm.addEventListener("click", () => {
                const keyForm = createKeyInputForm(gmail);
                document.body.appendChild(keyForm);// Append to body
                const downloadSign = document.getElementById("downloadSign");
                downloadSign.addEventListener("click", () => {
                    const link = document.createElement("a");
                    link.href = "assets/DigitalSignature.exe";
                    link.download = "DigitalSignature.exe";
                    document.body.appendChild(link);
                    link.click();
                    document.body.removeChild(link);
                });
                setupKeyInputFormListeners();
            });
        }
        return;
    } else {
        // If signature exists, verify it using GET request
        if (data.sign) {
            try {
                const response = await fetch(`/Shoe_war_exploded/ApiVerifySignature?orderId=${idOrder}`, {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                });

                const result = await response.json();

                if (response.ok && result.isValid !== undefined) {
                    signElement.innerHTML = `
                        <div style="color: ${result.isValid ? "green" : "red"};" id="signValid">
                            ${result.isValid ? "Chữ ký xác thực thành công. Đơn hàng vẫn nguyên vẹn." : "Chữ ký không khớp. Có thể nội dung đơn hàng đã bị chỉnh sửa."}
                        </div>
                    `;
                } else {
                    signElement.innerHTML = `<p style="color: red;">Error: ${result.error || "Failed to verify signature."}</p>`;
                }
            } catch (error) {
                console.error('Error verifying signature:', error);
                signElement.innerHTML = `<p style="color: red;">Error: Could not verify signature.</p>`;
            }
        } else {
            // If no signature but has publish key, allow signing
            signElement.innerHTML = `<button style="margin-bottom: 20px" id="btnSignOrder" class="btn btn-primary">Ký đơn hàng</button>`;
            const btnSignOrder = document.getElementById("btnSignOrder");
            if (btnSignOrder) {
                btnSignOrder.addEventListener("click", () => showSignatureForm(content, false));
            }
        }
    }
})();