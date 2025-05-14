document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("loginFacebook").addEventListener("click", function (event) {
        event.preventDefault(); // Chặn hành động submit form nếu có
        checkLoginState(); // Gọi hàm đăng nhập Facebook
    });
});




window.fbAsyncInit = function() {
    FB.init({
        appId: window.APP_CONFIG.FACEBOOK_APP_ID,
        cookie: true,
        xfbml: true,
        version: 'v19.0'
    });

    FB.getLoginStatus(function(response) {
        statusChangeCallback(response);
    });
};

(function(d, s, id) {
    var js, fjs = d.getElementsByTagName(s)[0];
    if (d.getElementById(id)) return;
    js = d.createElement(s); js.id = id;
    js.src = "https://connect.facebook.net/en_US/sdk.js";
    fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));

function statusChangeCallback(response) {
    console.log(response);
    if (response.status === 'connected') {
        getUserInfo();
    } else {
        document.getElementById('userInfo').style.display = 'none';
    }
}

function checkLoginState() {
    FB.login(function(response) {
        statusChangeCallback(response);
    }, {scope: 'public_profile,email'});
}

function getUserInfo() {
    FB.api('/me', { fields: 'name,email' }, function (response) {
        if (!response || response.error) {
            console.error("Lỗi khi lấy thông tin người dùng:", response.error);
            return;
        }

        // Lấy access token
        let accessToken = FB.getAuthResponse().accessToken;

        // Tạo payload gửi đến controller
        let userData = {
            email: response.email,
            id: response.id,
            accessToken: accessToken
        };

        console.log("Dữ liệu gửi đến server:", userData);

        // Gửi dữ liệu đến controller bằng Fetch API
        fetch('SignInFacebookController', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(userData)
        })
            .then(response => response.json())
            .then(data => {
                console.log("Phản hồi từ server:", data);

                if (data.status === "success") {
                    if (data.redirect) {
                        window.location.href = data.redirect; // Chuyển hướng đến trang đích
                    } else {
                        console.log("Đăng nhập thành công nhưng không có URL chuyển hướng.");
                    }
                } else {
                    alert(data.message || "Đăng nhập thất bại.");
                }
            })
            .catch(error => console.error("Lỗi khi gửi dữ liệu đến server:", error));
    });
}







function logout() {
    FB.logout(function(response) {
        document.getElementById('userInfo').style.display = 'none';
        console.log('Đã đăng xuất!');
    });
}


window.onload = function () {
    google.accounts.id.initialize({
        client_id: window.APP_CONFIG.GOOGLE_CLIENT_ID,
        callback: handleCredentialResponse,
        ux_mode: "popup",
    });

    google.accounts.id.renderButton(
        document.getElementById("google-login-container"),
        { theme: "outline", size: "large" } // Tùy chỉnh giao diện
    );

    google.accounts.id.prompt(); // Tự động hiển thị hộp thoại đăng nhập
};



function handleCredentialResponse(response) {
    fetch("SignInGoogleController", {
        method: "POST",
        headers: {"Content-Type": "application/x-www-form-urlencoded"},
        body: "credential=" + response.credential
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                if (data.redirect) {
                    window.location.href = data.redirect; // Chuyển hướng đến trang đích
                }
            } else {
                alert("Đăng nhập thất bại!");
            }
        })
        .catch(error => console.error("Error:", error));
}






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


