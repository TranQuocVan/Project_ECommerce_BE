const addNavigationAdmin = () => {
    const navigationAdmin = document.querySelector("#navigationAdmin");
    // <a href="http://localhost:8080/Shoe_war_exploded/AddVoucherController">
    navigationAdmin.innerHTML = `
            <div id="navAdmin">
                <h4>Navigation Admin</h4>
                <a href="AdminController">
                    <div class="navAdmin-item">
                        <div class="navAdmin-text">
                            Admin Home
                        </div>
                        <div class="navAdmin-icon">
                            <i class="fa-solid fa-chevron-right"></i>
                        </div>
                    </div>
                </a>
                

                <div class="navAdmin-item" id="addCategoryToggle">
                    <div class="navAdmin-text">
                       <a>Sản phẩm</a> 
                    </div>
                    <div class="navAdmin-icon">
                        <i class="fa-solid fa-chevron-down"></i>
                    </div>
                </div>
                <div class="subMenu" id="categoryOptions">
                    <span class="navigation">
<!--                        <a href="http://localhost:8080/Shoe_war_exploded/adminPages/addProductGroup.jsp">-->
                        <a href="AddProductGroupController">
                            <div class="navAdmin-subItem">
                                Thêm nhóm sản phẩm
                            </div>
                        </a>
                    </span>
                    
                    <span class="navigation">
<!--                        <a href="http://localhost:8080/Shoe_war_exploded/adminPages/addProductCatelogy.jsp">-->
                        <a href="AddProductCatalogController">
                            <div class="navAdmin-subItem">
                                Thêm loại sản phẩm
                            </div>
                        </a>
                    </span>
                     
                    <span class="navigation">
                        <a href="AddProductController">
                            <div class="navAdmin-subItem">
                                Thêm sản phẩm 
                            </div>
                        </a>
                    </span>
                    
                     <span class="navigation">
                        <a href="ProductAdminController">
                            <div class="navAdmin-subItem">
                                Quản lí sản phẩm
                            </div>
                        </a>
                    </span>
                </div>


                <div class="navAdmin-item" id="orderToggle">
                    <div class="navAdmin-text">
                       <a>Đơn hàng</a> 
                    </div>
                    <div class="navAdmin-icon">
                        <i class="fa-solid fa-chevron-down"></i>
                    </div>
                </div>
                
                <div class="subMenu" id="orderOptions">
                    <span class="navigation">
                        <a href="OrderAdminController">
                            <div class="navAdmin-subItem">
                                Quản lí trạng thái đơn hàng
                            </div>
                        </a>
                    </span>
                    
                    <span class="navigation">
                        <a href="ManageOrderSignatureStatusController">
                            <div class="navAdmin-subItem">
                                Quản lí chữ ký đơn hàng
                            </div>
                        </a>
                    </span>
                </div>
                
                
                 <div class="navAdmin-item" id="userToggle">
                    <div class="navAdmin-text">
                       <a>Người dùng</a> 
                    </div>
                    <div class="navAdmin-icon">
                        <i class="fa-solid fa-chevron-down"></i>
                    </div>
                </div>
                <div class="subMenu" id="userOptions">
                    <span class="navigation">
                        <a href="https://silkroadshop.io.vn/adminPages/addProductGroup.jsp">
                            <div class="navAdmin-subItem">
                                Quản lí user
                            </div>
                        </a>
                    </span>            
                </div>
                
                
                <div class="navAdmin-item" id="voucherToggle">
                    <div class="navAdmin-text">
                       <a>Voucher</a> 
                    </div>
                    <div class="navAdmin-icon">
                        <i class="fa-solid fa-chevron-down"></i>
                    </div>
                </div>
                <div class="subMenu" id="voucherOptions">
                    <span class="navigation">
                        <a href="AddTypeVoucherController">
                            <div class="navAdmin-subItem">
                                Thêm loại Voucher
                            </div>
                        </a>
                    </span>
                   
                    <span class="navigation">
                        <a href="AddVoucherController">
                            <div class="navAdmin-subItem">
                                Thêm Voucher
                            </div>
                        </a>
                    </span>
                     
                    <span class="navigation">
                        <a href="ManagerTypeVoucherController">
                            <div class="navAdmin-subItem">
                                Quản lí loại Voucher
                            </div>
                        </a>
                    </span>
                     
                    <span class="navigation">
                        <a href="ManagerVoucherController">
                            <div class="navAdmin-subItem">
                                Quản lí Voucher
                            </div>
                         </a>
                    </span>
                     

                </div>
                
               
                
                <div class="navAdmin-item" id="logToggle">
                    <div class="navAdmin-text">
                       <a>Quản lý Log</a> 
                    </div>
                    <div class="navAdmin-icon">
                        <i class="fa-solid fa-chevron-down"></i>
                    </div>
                </div>
                <div class="subMenu" id="logOptions">
                    <a href="LogPageController">
                        <div class="navAdmin-subItem">
                            Quản lý log
                        </div>
                    </a>          
                </div>
                

<!--                <a href="./delivery.jsp">-->
<!--                    <div class="navAdmin-item">-->
<!--                        <div class="navAdmin-text">-->
<!--                            Delivery-->
<!--                        </div>-->
<!--                        <div class="navAdmin-icon">-->
<!--                            <i class="fa-solid fa-chevron-right"></i>-->
<!--                        </div>-->
<!--                    </div>-->
<!--                </a>-->

<!--                <a href="./payment.jsp">-->
<!--                    <div class="navAdmin-item">-->
<!--                        <div class="navAdmin-text">-->
<!--                            Payment-->
<!--                        </div>-->
<!--                        <div class="navAdmin-icon">-->
<!--                            <i class="fa-solid fa-chevron-right"></i>-->
<!--                        </div>-->
<!--                    </div>-->
<!--                </a>-->
            </div>
    `;

    // Thêm sự kiện để bật/tắt danh sách con với delay và hiệu ứng mượt
    const addCategoryToggle = document.getElementById("addCategoryToggle");
    const categoryOptions = document.getElementById("categoryOptions");

    addCategoryToggle.addEventListener("click", () => {
        // Thêm độ trễ nhẹ trước khi bắt đầu hiệu ứng
        setTimeout(() => {
            categoryOptions.classList.toggle("show");
        }, 200); // Delay 200ms trước khi bắt đầu hiệu ứng
    });

    // Xử lý toggle cho "Đơn hàng"
    const orderToggle = document.getElementById("orderToggle");
    const orderOptions = document.getElementById("orderOptions");
    orderToggle.addEventListener("click", () => {
        orderOptions.classList.toggle("show");
    });

    const userToggle = document.getElementById("userToggle")
    const userOptions = document.getElementById("userOptions")
    userToggle.addEventListener("click", () => {

        userOptions.classList.toggle("show");
    });

    const voucherToggle = document.getElementById("voucherToggle")
    const voucherOptions = document.getElementById("voucherOptions")
    voucherToggle.addEventListener("click", () => {

        voucherOptions.classList.toggle("show");
    });


    const logToggle = document.getElementById("logToggle");
    const logOptions = document.getElementById("logOptions");
    logToggle.addEventListener("click", () => {
        logOptions.classList.toggle("show");
    });
};

addNavigationAdmin();
