const data = {
    shop: [{
            col1: 'Mua hàng',
            col1List: [
                { name: 'Mua Sản Phẩm Mới Nhất' },
                { name: 'Giày' },
                { name: 'Dép' },
                { name: 'Thương Hiệu' },
                { name: 'Phụ kiện' }
            ]
        },
        {
            col2: 'Chính sách',
            col2List: [
                { name: 'Chính sách bảo mật' },
                { name: 'Chính sách vận chuyển' },
                { name: 'Chính sách đổi trả' }
            ]
        },
        {
            col3: 'Liên hệ',
            col3List: [
                { name: 'Liên hệ hotline' },
                { name: 'Liên hệ email' }
            ]
        }
    ],
    shoe: [{
            col1: 'Khám phá các loại giày',
            col1List: [
                { name: 'Giày thể thao nam', url: 'men\'sSportsShoes.html' },
                { name: 'Giày thể thao nữ', url: 'womenShoes.html' },
                { name: 'Giày công sở' },
                { name: 'Giày cao gót' },
            ]
        },
        {
            col2: 'Mua giày',
            col2List: [
                { name: 'Giày nam' },
                { name: 'Giày nữ' },
                { name: 'Giày trẻ em' }
            ]
        },
        {
            col3: 'Thương hiệu',
            col3List: [
                { name: 'Adidas' },
                { name: 'Nike' },
                { name: 'Converse' },
                { name: 'Puma' },
                { name: 'Vans' }
            ]
        }
    ],
    sandal: [ // Thêm dữ liệu cho Dép
        {
            col1: 'Khám phá các loại dép',
            col1List: [
                { name: 'Dép nam', url: 'menSandal.html' },
                { name: 'Dép nữ', url: 'womenSandals.html' },
                { name: 'Dép trẻ em', url: 'kid.html' },
            ]
        },
        {
            col2: 'Mua dép',
            col2List: [
                { name: 'Dép nam' },
                { name: 'Dép nữ' },
                { name: 'Dép trẻ em' }
            ]
        },
        {
            col3: 'Thương hiệu',
            col3List: [
                { name: 'Havaianas' },
                { name: 'Crocs' },
                { name: 'Nike' },
                { name: 'Adidas' }
            ]
        }
    ]
};





// Check and update login status
let isLogin = localStorage.getItem('isLogin') === 'true';

const updateLoginStatus = () => {
    const loginElement = document.getElementById("isLogin");
    if (loginElement && loginElement.innerText === "true") {
        localStorage.setItem("isLogin", "true");
        isLogin = true; // Update local variable after storing in localStorage
    }
};
updateLoginStatus();

// Initialize the `isAdmin` variable based on localStorage
let isAdmin = localStorage.getItem('currentRole') === 'admin';

// Function to update the role status based on the element content
const updateRoleStatus = () => {
    const roleElement = document.getElementById("role");

    if (roleElement) {
        const role = roleElement.innerText.trim().toLowerCase(); // Normalize input
        if (role === "admin") {
            localStorage.setItem("currentRole", "admin");
            isAdmin = true; // Update local variable
        } else {
            localStorage.setItem("currentRole", "user"); // Default role or fallback
            isAdmin = false;
        }
    } else {
        console.warn("Role element not found.");
    }
};

// Call the function to update role status
updateRoleStatus();


let urlLogo = "assets/logo2.svg";
checkImageExists(urlLogo)
    .then(exists => {
        if (!exists) {
            urlLogo ="../assets/logo2.svg"
            renderNavigation();
        }
    })


const renderNavigation = () => {
        document.querySelector('nav').innerHTML = `
        <div id="navigation" class="container-fluid main-bg-color">
            <div class="container">
                <div style="font-size: 20px;" class="row">
                    <div class="col-md-9 flex-align-items-center dropdown">
                        <span class="navigation"><a href="IndexController"><img style="width: 20px;" src="${urlLogo}" alt="logo"></a></span>
<!--                        <span class="navigation" id="shop"><a href="/Shoe_war_exploded/IndexController">Cửa hàng</a></span> -->
                        <span class="navigation" id="shop"><a href="IndexController">Cửa hàng</a></span> 
                        <span class="navigation" id="shoe"><a href="productDetails.jsp">Giày</a></span> 
                        <span class="navigation" id="sandal"><a href="#">Dép</a></span> 
                        <span class="navigation"><a href="GetProductByCategoryNameController">Sản phẩm theo loại hàng</a></span>
                        <span class="navigation"> ${isAdmin  ? `<a href='AdminController'>Admin</a>` : ``}</span>
                                                           
                    </div>
                    <div class="col-md-3 flex-align-items-center" style="justify-content: end;">
                        ${isLogin ? `<!--<a href='./userInfo.jsp'><i class="fa-regular fa-user"></i></i></a> -->
                                     <a href='./UserInfoController'><i class="fa-regular fa-user"></i></i></a>
                                     <a href='./ShoppingCartItemsController'><i class="fa-solid fa-cart-shopping"></i></i></a>
                                     <a href='./OrderController'><i class="fa-solid fa-truck"></i></i></a>` : `<a href='SignInController'>Sign in</i></a>`}
                    </div>
                </div>
            </div>
        </div>
        <div class="holiday">
            <div class="shape holiday-yellow"></div>
            <div class="shape holiday-pinkOne"></div>
            <div class="shape holiday-pinkTwo"></div>
            <div class="shape holiday-blue"></div>
        </div>
        <div id="under-nav" style="z-index: 1000;" class="container-fluid content-when-hover main-bg-color"></div>`;
};
renderNavigation();



const contentWhenHover = document.querySelector('.content-when-hover');
const underNavigation = document.querySelector('.under-navigation');
const dropDown = () => {
    contentWhenHover.innerHTML = `
    <div class="container">
        <div class="row">
            <div class="col-md-4">
                <p class="content-when-hover-p">${item[0].col1}</p>
                <div class="flex-direction-column">
                    ${item[0].col1List.map(subitem => `<span><a class="content-when-hover-a-first" href="${subitem.url}">${subitem.name}</a></span>`).join('')}
                </div>
            </div>
            <div class="col-md-2">
                <p class="content-when-hover-p">${item[1].col2}</p>
                <div class="flex-direction-column">
                    ${item[1].col2List.map(subitem => `<span><a class="content-when-hover-a" href="${subitem.url}">${subitem.name}</a></span>`).join('')}
                </div>
            </div>
            <div class="col-md-2">
                <p class="content-when-hover-p">${item[2].col3}</p>
                <div class="flex-direction-column">
                    ${item[2].col3List.map(subitem => `<span><a class="content-when-hover-a" href="${subitem.url}">${subitem.name}</a></span>`).join('')}
                </div>
            </div>
        </div>
    </div>`;
};
let item = data.shop;
dropDown();

document.querySelector('#shop').addEventListener('mouseenter', () => {
    item = data.shop;
    dropDown();
});

document.querySelector('#shoe').addEventListener('mouseenter', () => {
    item = data.shoe;
    dropDown();
});
document.querySelector('#sandal').addEventListener('mouseenter', () => { // Thêm sự kiện cho Dép
    item = data.sandal;
    dropDown();
});

let setOpacity;
let hoverDelay;
let isHovering = false; // Track if currently hovering
const dropdown = document.querySelector('.dropdown');


dropdown.addEventListener('mouseenter', () => {
    // Clear any previous hover timeout and mark as hovering
    clearTimeout(hoverDelay);
    isHovering = true;

    // Set a delay of 10ms before displaying contentWhenHover
    hoverDelay = setTimeout(() => {
        if (isHovering) {
            contentWhenHover.style.display = 'block';
            contentWhenHover.style.animation = 'content-when-hover-animation 0.5s ease-in-out';
            underNavigation.style.animation = 'opacity 0.5s ease-in-out';

            // Apply opacity after animation begins
            setOpacity = setTimeout(() => {
                underNavigation.style.opacity = 0.5;
            }, 500);
        }
    }, 10);
});
const underNav = document.querySelector('#under-nav');
underNav.addEventListener('mouseleave', () => {
    // Clear timeouts and mark as not hovering
    clearTimeout(hoverDelay);
    clearTimeout(setOpacity);
    isHovering = false;

    // Hide contentWhenHover immediately if it’s visible
    contentWhenHover.style.animation = 'content-when-endHover-animation 0.3s ease-in-out';
    underNavigation.style.animation = 'endOpacity 0.3s ease-in-out';

    // Ensure display is set to 'none' after the end animation
    setTimeout(() => {
        if (!isHovering) {
            contentWhenHover.style.display = 'none';
            underNavigation.style.opacity = 1;
        }
    }, 300);
});

function checkImageExists(imageUrl) {
    return new Promise((resolve, reject) => {
        var img = new Image();

        // Khi ảnh tải thành công
        img.onload = function() {
            resolve(true);  // Ảnh tồn tại
        };

        // Khi xảy ra lỗi khi tải ảnh
        img.onerror = function() {
            resolve(false);  // Ảnh không tồn tại
        };

        // Gán đường dẫn ảnh
        img.src = imageUrl;
    });
}

