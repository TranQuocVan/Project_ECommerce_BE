<%--
  Created by IntelliJ IDEA.
  User: Van Tran
  Date: 1/9/2025
  Time: 9:08 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thanh Toán</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>


    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

    <link rel="stylesheet" href="./styles/admin/payment.css">
    <link rel="stylesheet" href="./styles/globa.css">
    <link rel="stylesheet" href="./styles/slider.css">
    <link rel="stylesheet" href="./styles/navigation.css">
    <link rel="stylesheet" href="./styles/admin/navigationAdmin.css">
    <link rel="stylesheet" href="./styles/global.css">
    <link rel="stylesheet" href="./styles/admin/globaladmin.css">

</head>

<body>
<header>
    <nav></nav>
    <div class="under-navigation">
        <div class="container ">
            <div class="row mt-5">
                <h5 class="titlePageAdmin">Thêm phương thức thanh toán cho web</h5>
                <div class="col-md-3">
                    <div id="navigationAdmin"></div>
                </div>
                <div class="col-md-9">
                    <div class="form-container">
                        <section>
                            <div class="payment-option" id="creditCardOption">
                                <div>Thẻ Tín Dụng hoặc Thẻ Ghi Nợ</div>
                                <span>Visa, Mastercard, AMEX, JCB</span>
                            </div>
                            <div class="payment-option" id="momoOption">
                                <div>Ví Điện Tử MoMo</div>
                            </div>
                            <div class="payment-option" id="atmOption">
                                <div>ATM</div>
                            </div>

                            <!-- Dropdown list for credit card options -->
                            <div class="payment-options-list" id="creditCardOptions">
                                <div class="payment-option-item">
                                    Visa
                                    <img src="/assets/images/defaults/visa.png" alt="Visa Logo">
                                </div>
                                <div class="payment-option-item">
                                    Mastercard
                                    <img src="/assets/images/defaults/mastercard.png" alt="Mastercard Logo">
                                </div>
                                <div class="payment-option-item">
                                    AMEX
                                    <img src="/assets/images/defaults/amex.webp" alt="AMEX Logo">
                                </div>
                                <div class="payment-option-item">
                                    JCB
                                    <img src="/assets/images/defaults/jcb.jpeg" alt="JCB Logo">
                                </div>
                            </div>
                        </section>
                        <!-- Nút xác nhận -->
                        <button type="submit">Xác nhận thanh toán</button>
                    </div>
                </div>

            </div>
        </div>
    </div>
</header>

<footer></footer>
</body>


<script src="./scripts/admin/payment.js"></script>
<script src="./components/navigation.js"></script>
<script src="./components/footer.js"></script>
<script src="./scripts/scroll.js"></script>
<script src="./components/navigationadmin.js"></script>

</html>
