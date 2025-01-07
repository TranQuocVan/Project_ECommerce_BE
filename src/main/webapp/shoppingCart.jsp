<%--
  Created by IntelliJ IDEA.
  User: Van Tran
  Date: 12/14/2024
  Time: 1:55 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Shoes</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>



  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

  <link rel="stylesheet" href="styles/slider.css">
  <link rel="stylesheet" href="styles/navigation.css">
  <link rel="stylesheet" href="styles/global.css">
  <link rel="stylesheet" href="styles/shoppingCart.css">


</head>

<body>
<header>
  <nav></nav>
  <div class="under-navigation">

    <div class="h-100 h-custom mt-5">
      <div class="container py-5 h-100">
        <div class="row d-flex justify-content-center align-items-center h-100">
          <div class="col-12">
            <div class="card card-registration card-registration-2" style=" border-radius: 15px;">
              <div class="card-body p-0">
                <div class="row g-0">
                  <div class="col-lg-8">
                    <div class="p-5">
                      <div class="d-flex justify-content-between align-items-center mb-5">
                        <h1 class="fw-bold mb-0">Giỏ hàng</h1>
                      </div>
                        <c:if test="${empty shoppingCartItemsList}">
                            <div class="alert alert-info text-center mt-4" role="alert">
                                Giỏ hàng của bạn đang trống. Hãy thêm sản phẩm vào giỏ hàng để tiếp tục mua sắm!
                            </div>
                        </c:if>
                      <c:if test="${not empty shoppingCartItemsList}">
                      <c:forEach var="item" items="${shoppingCartItemsList}">
                      <hr class="my-4">

                      <div class="row mb-4 d-flex justify-content-between align-items-center items">
                        <div class="col-md-1">
                          <form method="POST" action="ShoppingCartItemsController" id="updateCartForm-${item.sizeId}">
                            <input type="hidden" name="sizeId" value="${item.sizeId}">
                            <input type="checkbox" class="select-item" data-item-id="${item.sizeId}"
                                   onclick="toggleSelection(this)" />
                          </form>

                        </div>
                        <div class="col-md-2 col-lg-2 col-xl-2">
                          <img src="assets/shoeMens/1.png" class="img-fluid rounded-3" alt="Fashion shoes">
                        </div>
                        <div class="col-md-3 col-lg-3 col-xl-3">
                          <h6 class="text-muted">${item.nameProduct}</h6>
                          <h6 class="mb-0">Size ${item.nameSize} | ${item.nameColor}</h6>
                          <p class="text-success">Còn lại: ${item.stock} sản phẩm</p>
                        </div>
                        <div class="col-md-3 col-lg-3 col-xl-2 d-flex">
                          <button data-mdb-button-init data-mdb-ripple-init class="btn btn-link px-2" onclick="decreaseQuantity(this)">
                            <i class="fas fa-minus"></i>
                          </button>

                          <input id="form" min="1" max="${item.stock}" name="quantity" value="${item.quantity}" type="number" class="form-control form-control-sm" />

                          <button data-mdb-button-init data-mdb-ripple-init class="btn btn-link px-2" onclick="increaseQuantity(this)">
                            <i class="fas fa-plus"></i>
                          </button>

                        </div>
                        <div class="col-md-3 col-lg-2 col-xl-2 offset-lg-1">
                          <h6 class="mb-0">${item.formattedPrice}</h6>
                        </div>
                        <div class="col-md-1 col-lg-1 col-xl-1 text-end">

                          <form method="POST" action="DeleteCartItemController">
                            <input type="hidden" name="sizeId" value="${item.sizeId}">
                            <button data-mdb-button-init data-mdb-ripple-init class="btn btn-link px-2"
                                    onclick="handleDelete(this, '${item.nameProduct}', '${item.nameSize}'); return false;">
                              <i class="fas fa-times"></i>
                            </button>
                          </form>


                        </div>
                      </div>

                      </c:forEach>
                      </c:if>




                      <div class="pt-5">
                        <h6 class="mb-0"><a href="./index.jsp" class="text-body"><i
                                class="fas fa-long-arrow-alt-left me-2"></i>Trở về trang chủ</a>
                        </h6>
                      </div>
                    </div>
                  </div>




                  <div class="col-lg-4 bg-body-tertiary">
                    <div class="p-5">
                      <h3 class="fw-bold mb-5 mt-2 pt-1">Tóm tắt</h3>
                      <hr class="my-4">

                      <div class="d-flex justify-content-between mb-4">
                        <h5 class="text-uppercase">Tổng số sản phẩm </h5>
                        <h5> ${shoppingCartItemsList.size()} món</h5>
                      </div>

                      <form id="orderForm" action="OrderController" method="post">
                        <input type="hidden" id="selectedItems" name="selectedItems" />
                      <h5 class="text-uppercase mb-3">Phương thức giao hàng</h5>

                      <div class="d-flex justify-content-between mb-4">
                        <select data-mdb-select-init name="deliveryId">
                          <option value="1">Hỏa tốc - Ngày mai</option>
                          <option value="2">Nhanh</option>
                          <option value="3">Tiêu chuẩn</option>
                        </select>
                        <h5>40.000</h5>

                      </div>
                      <h5 class="text-uppercase mb-3">Hình thức thanh toán</h5>

                      <div class="d-flex justify-content-between mb-4">
                        <select data-mdb-select-init name="paymentId">
                          <option value="1">Visa - Mastercard - Jcb</option>
                          <option value="2">Momo</option>
                          <option value="3">Tiêu chuẩn - Cod</option>
                        </select>
                        <h5> Tiện lợi </h5>

                      </div>

                      <h5 class="text-uppercase mb-3">Địa chỉ nhận hàng</h5>

                      <div class="mb-5">
                        <div data-mdb-input-init class="form-outline">
                          <input type="text" id="form3Examplea2" class="form-control form-control-lg" name="address" />
                          <label class="form-label" for="form3Examplea2">Nhập địa chỉ của
                            bạn</label>
                        </div>
                      </div>

                      <hr class="my-4">

                      <div class="d-flex justify-content-between mb-5">
                        <h5 class="text-uppercase">Tổng thanh toán</h5>
                        <h5>${totalPriceFormat}</h5>
                      </div>

                        <button data-mdb-button-init data-mdb-ripple-init
                                class="btn btn-dark btn-block btn-lg"
                                data-mdb-ripple-color="dark"
                                onclick="orderButton(this, event);">
                          Thanh toán
                        </button>



                      </form>


                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>

</header>

<footer>

</footer>
<script src="components/navigation.js"></script>
<script src="components/footer.js"></script>
<script src="scripts/scroll.js"></script>
<script src="scripts/shoppingCart.js"></script>  <!-- Tham chiếu đến tệp JS chứa các hàm tăng/giảm -->



</body>

</html>