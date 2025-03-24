document.getElementById("signInForm").addEventListener("submit", function(event) {
    event.preventDefault(); // Ngăn chặn form gửi mặc định

    let gmail = document.getElementById("gmail").value;
    let password = document.getElementById("password").value;

    fetch("SignInController", {
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
                document.getElementById("errorSignIn").innerText = data.message; // Hiển thị lỗi
            }
        })
        .catch(error => console.error("Error:", error));
});


