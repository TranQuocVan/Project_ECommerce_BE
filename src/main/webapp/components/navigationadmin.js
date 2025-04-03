const addNavigationAdmin = () => {
    const navigationAdmin = document.querySelector("#navigationAdmin");
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
                <a href="http://localhost:8080/Shoe_war_exploded/adminPages/addProductGroup.jsp">
                    <div class="navAdmin-subItem">
                        Thêm nhóm sản phẩm
                    </div>
                    </a>
                    
                     <a href="http://localhost:8080/Shoe_war_exploded/adminPages/addProductCatelogy.jsp">
                    <div class="navAdmin-subItem">
                        Thêm loại sản phẩm
                    </div>
                    </a>

                     <a href="http://localhost:8080/Shoe_war_exploded/AddProductController">
                    <div class="navAdmin-subItem">
                        Thêm sản phẩm 
                    </div>
                    </a>
                    
                     <a href="http://localhost:8080/Shoe_war_exploded/ProductAdminController">
                    <div class="navAdmin-subItem">
                        Quản lí sản phẩm
                    </div>
                     </a>

                </div>


                <div class="navAdmin-item" id="orderToggle">
                    <div class="navAdmin-text">
                       <a>Don hang</a> 
                    </div>
                    <div class="navAdmin-icon">
                        <i class="fa-solid fa-chevron-down"></i>
                    </div>
                </div>
                
                <div class="subMenu" id="orderOptions">
                <a href="http://localhost:8080/Shoe_war_exploded/OrderAdminController">
                    <div class="navAdmin-subItem">
                        Quan li trạng thái đơn hàng
                    </div>
                    </a>
                    
                    
                         
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
                <a href="http://localhost:8080/Shoe_war_exploded/adminPages/addProductGroup.jsp">
                    <div class="navAdmin-subItem">
                        Quản lí user
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
    userToggle.addEventListener("click", ()=>{

        userOptions.classList.toggle("show");
    });
};

addNavigationAdmin();
