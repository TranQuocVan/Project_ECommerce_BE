const addNavigationAdmin = () => {
    const navigationAdmin = document.querySelector("#navigationAdmin");
    navigationAdmin.innerHTML = `
            <div id="navAdmin">
                <h4>Navigation Admin</h4>
                <a href="adminPages/admin.jsp">
                    <div class="navAdmin-item">
                        <div class="navAdmin-text">
                            Admin Home
                        </div>
                        <div class="navAdmin-icon">
                            <i class="fa-solid fa-chevron-right"></i>
                        </div>
                    </div>
                </a>

                <a href="adminPages/addProductCategory.jsp">
                    <div class="navAdmin-item">
                        <div class="navAdmin-text">
                            Add Product Category
                        </div>
                        <div class="navAdmin-icon">
                            <i class="fa-solid fa-chevron-right"></i>
                        </div>
                    </div>
                </a>

                <a href="adminPages/addProducts.jsp">
                    <div class="navAdmin-item">
                        <div class="navAdmin-text">
                            Add Product
                        </div>
                        <div class="navAdmin-icon">
                            <i class="fa-solid fa-chevron-right"></i>
                        </div>
                    </div>
                </a>

                <a href="adminPages/delivery.jsp">
                    <div class="navAdmin-item">
                        <div class="navAdmin-text">
                            Delivery
                        </div>
                        <div class="navAdmin-icon">
                            <i class="fa-solid fa-chevron-right"></i>
                        </div>
                    </div>
                </a>

                <a href="adminPages/payment.jsp">
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
};

addNavigationAdmin();
