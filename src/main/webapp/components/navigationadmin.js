const addNavigationAdmin = () => {
    const navigationAdmin = document.querySelector("#navigationAdmin");
    navigationAdmin.innerHTML = `
            <div id="navAdmin">
                <h4>Navigation Admin</h4>
                <a href="./admin.jsp">
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
                <a href="./addProductGroup.jsp">
                    <div class="navAdmin-subItem">
                        Thêm nhóm sản phẩm
                    </div>
                    </a>
                    
                     <a href="./addProductCatelogy.jsp">
                    <div class="navAdmin-subItem">
                        Thêm loại sản phẩm
                    </div>
                    </a>

                     <a href="../AddProductController">
                    <div class="navAdmin-subItem">
                        Thêm sản phẩm 
                    </div>
                    </a>
                    
                    <div class="navAdmin-subItem">
                        <a>Quản lí sản phẩm</a>
                    </div>
                </div>

                <a href="./addProducts.jsp">
                    <div class="navAdmin-item">
                        <div class="navAdmin-text">
                            Add Product
                        </div>
                        <div class="navAdmin-icon">
                            <i class="fa-solid fa-chevron-right"></i>
                        </div>
                    </div>
                </a>

                <a href="./delivery.jsp">
                    <div class="navAdmin-item">
                        <div class="navAdmin-text">
                            Delivery
                        </div>
                        <div class="navAdmin-icon">
                            <i class="fa-solid fa-chevron-right"></i>
                        </div>
                    </div>
                </a>

                <a href="./payment.jsp">
                    <div class="navAdmin-item">
                        <div class="navAdmin-text">
                            Payment
                        </div>
                        <div class="navAdmin-icon">
                            <i class="fa-solid fa-chevron-right"></i>
                        </div>
                    </div>
                </a>
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
};

addNavigationAdmin();
