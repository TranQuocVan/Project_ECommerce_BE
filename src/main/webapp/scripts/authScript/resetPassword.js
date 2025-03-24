document.getElementById("resetPasswordForm").addEventListener("submit", function(event) {
    event.preventDefault(); // Ngăn form submit mặc định

    let password = document.getElementById("password").value;
    let confirmPassword = document.getElementById("confirmPassword").value;
    let token = document.getElementById("token").value;

    // Kiểm tra độ mạnh mật khẩu
    // let passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{8,}$/;
    // if (!passwordRegex.test(password)) {
    //     alert("Mật khẩu phải có ít nhất 8 ký tự, bao gồm chữ hoa, chữ thường và số.");
    //     return;
    // }

    // Kiểm tra xác nhận mật khẩu
    if (password !== confirmPassword) {
        alert("Mật khẩu xác nhận không khớp!");
        return;
    }

    // Gửi AJAX request đặt lại mật khẩu
    fetch("ResetPasswordForgotController", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ token: token, password: password })
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                alert("Mật khẩu đã được đặt lại thành công!");
                window.location.href = data.redirect || window.location.origin + "/signIn.jsp";
            } else {
                alert("Lỗi: " + data.message);
            }
        })
        .catch(error => console.error("Error:", error));
});