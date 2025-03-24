document.addEventListener("DOMContentLoaded", function () {
    const loginForm = document.getElementById("signUpForm");
    const errorLogin = document.getElementById("errorLogin");

    loginForm.addEventListener("submit", async function (event) {
        event.preventDefault(); // Ngăn form gửi theo cách mặc định

        // Lấy dữ liệu từ form
        const formData = new FormData(loginForm);
        const data = {
            gmail: formData.get("gmail"),
            password: formData.get("password")
        };

        try {
            const response = await fetch("SignInController", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(data)
            });

            const result = await response.json();

            if (result.status === "ok") {
                window.location.href = "home.jsp"; // Điều hướng nếu đăng nhập thành công
            } else {
                errorLogin.innerText = result.message; // Hiển thị lỗi
                errorLogin.style.color = "red";
            }
        } catch (error) {
            console.error("Lỗi khi gửi yêu cầu đăng nhập:", error);
            errorLogin.innerText = "Đã xảy ra lỗi, vui lòng thử lại.";
        }
    });
});
