const selectors = {
    changePublishKey: "#changePublishKey",
    gmail: "#gmail",
    keyForm: "#keyForm",
    publishKey: "#publishKey",
    popUp: "#popUp",
    keyInputForm: "#key-input-form"
};

const animationConfig = {
    duration: 2, // Seconds
    delay: 1000 // Milliseconds
};

// Utility to get DOM element
const getElement = (selector) => document.querySelector(selector);

// Show error popup with animation
function showErrorPopup(message) {
    const div = document.createElement("div");
    div.id = selectors.popUp.slice(1);
    div.innerHTML = `
    <div style="border-bottom: 1px solid black; padding: 10px 0; font-weight: 700;" class="titlePopup">
      <p style="margin: 0;">Thông báo</p>
    </div>
    <div style="display: flex; padding: 16px 0;" class="contentPopup">
      <div style="display: flex; justify-content: space-between; flex-direction: column; padding: 0 10px;" class="infoPopup">
        <h3 style="font-size: 14px; font-weight: 700;">${message}</h3>
      </div>
    </div>`;
    div.style.animation = `popUpDown ${animationConfig.duration}s`;
    document.body.appendChild(div);

    const totalDelay = animationConfig.duration * 1000 + animationConfig.delay;
    setTimeout(() => {
        div.style.animation = `popUpUp ${animationConfig.duration}s forwards`;
    }, totalDelay);
    setTimeout(() => div.remove(), totalDelay + animationConfig.duration * 1000);
}

// Submit publish key to server
async function submitPublishKey(publishKey, gmail) {
    try {
        const response = await fetch("/Shoe_war_exploded/AddKeyController", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ publishKey, gmail })
        });

        if (!response.ok) throw new Error(`HTTP error! Status: ${response.status}`);
        const data = await response.json();

        if (data.error) {
            showErrorPopup(data.error);
            return false;
        }
        return data.success;
    } catch (error) {
        console.error("Error submitting publish key:", error);
        return false;
    }
}

// Create key input form
function createKeyInputForm(gmail) {
    const div = document.createElement("div");
    div.id = selectors.keyInputForm.slice(1);
    div.style = `
    color: #000;
    background-color: #fff;
    position: fixed;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    width: 800px;
    padding: 20px;
    border: 1px solid #ccc;
    border-radius: 8px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
    z-index: 9999;`;
    div.innerHTML = `
    <div id="support">Nếu bạn chưa có key truy cập <a href="#" id="downloadSign" rel="noopener noreferrer">tại đây</a></div>
    <form id="${selectors.keyForm.slice(1)}" method="post">
      <div style="margin-bottom: 15px;">
        <label for="publishKey" style="display: block; margin-bottom: 5px;">Publish Key:</label>
        <textarea id="publishKey" name="publishKey" rows="2" style="width: 100%; padding: 8px;" required></textarea>
      </div>
      <div style="display: none;">
        <div id="email">${gmail}</div>
      </div>
      <button type="submit" style="width: 100%; padding: 10px; background-color: #007bff; color: white; border: none; border-radius: 4px; cursor: pointer;">Gửi</button>
    </form>`;
    return div;
}

// Setup form event listeners
function setupKeyInputFormListeners(gmail) {
    const keyForm = getElement(selectors.keyForm);
    if (!keyForm) return;

    keyForm.addEventListener("submit", async (e) => {
        e.preventDefault();
        const publishKey = getElement(selectors.publishKey).value;
        const success = await submitPublishKey(publishKey, gmail);

        if (success) {
            document.body.removeChild(keyForm.parentElement); // Removed keyForm parent (the div containing the form)
            showErrorPopup("Thay đổi Publish Key thành công");
        } else {
            showErrorPopup("Thay đổi Publish Key thất bại. Vui lòng thử lại.");
        }
    });
}

// Initialize
function init() {
    const changePublishKey = getElement(selectors.changePublishKey);
    const gmail = getElement(selectors.gmail)?.innerText;

    if (!changePublishKey || !gmail) return;

    changePublishKey.addEventListener("click", (e) => {
        e.preventDefault();
        const keyForm = createKeyInputForm(gmail);
        document.body.appendChild(keyForm);
        setupKeyInputFormListeners(gmail);  // Ensure listeners are set after the form is appended to the DOM
    });
}

init();
