document.getElementById("signUpForm").addEventListener("submit", function(event) {
    event.preventDefault(); // Ngăn form gửi mặc định

    let gmail = document.getElementById("gmail").value;
    let password = document.getElementById("password").value;

    fetch("SignUpController", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded",
        },
        body: new URLSearchParams({
            gmail: gmail,
            password: password
        })
    })
        .then(response => response.json())
        .then(data => {
            if (data.status === "success") {
                window.location.href = data.redirect; // Chuyển hướng nếu thành công
            } else {
                document.getElementById("errorSignUp").innerText = data.message; // Hiển thị lỗi
            }
        })
        .catch(error => console.error("Error:", error));
});
