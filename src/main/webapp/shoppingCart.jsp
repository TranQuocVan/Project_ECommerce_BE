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

                        <c:if test="${not empty shoppingCartItemsList.shoppingCartItemsList}">
                         <c:forEach var="item" items="${shoppingCartItemsList.shoppingCartItemsList}">

                           <h6 class="mb-0 text-muted">${fn:length(shoppingCartItemsList.shoppingCartItemsList)} món</h6>

                         </c:forEach>
                        </c:if>


                      </div>
                      <hr class="my-4">

                      <div class="row mb-4 d-flex justify-content-between align-items-center">
                        <div class="col-md-2 col-lg-2 col-xl-2">
                          <img src="assets/shoeMens/1.png" class="img-fluid rounded-3" alt="Fashion shoes">
                        </div>
                        <div class="col-md-3 col-lg-3 col-xl-3">
                          <h6 class="text-muted">Fashionista</h6>
                          <h6 class="mb-0">Size 43 | Vàng</h6>
                        </div>
                        <div class="col-md-3 col-lg-3 col-xl-2 d-flex">
                          <button data-mdb-button-init data-mdb-ripple-init class="btn btn-link px-2" onclick="this.parentNode.querySelector('input[type=number]').stepDown()">
                            <i class="fas fa-minus"></i>
                          </button>

                          <input id="form" min="0" name="quantity" value="1" type="number" class="form-control form-control-sm" />

                          <button data-mdb-button-init data-mdb-ripple-init class="btn btn-link px-2" onclick="this.parentNode.querySelector('input[type=number]').stepUp()">
                            <i class="fas fa-plus"></i>
                          </button>
                        </div>
                        <div class="col-md-3 col-lg-2 col-xl-2 offset-lg-1">
                          <h6 class="mb-0">599.000</h6>
                        </div>
                        <div class="col-md-1 col-lg-1 col-xl-1 text-end">
                          <a href="#!" class="text-muted hover-red "><i
                                  class="fas fa-times"></i></a>
                        </div>
                      </div>

                      <hr class="my-4">

                      <div class="row mb-4 d-flex justify-content-between align-items-center">
                        <div class="col-md-2 col-lg-2 col-xl-2">
                          <img src="assets/shoeMens/nike.png" class="img-fluid rounded-3" alt="Nike shoes">
                        </div>
                        <div class="col-md-3 col-lg-3 col-xl-3">
                          <h6 class="text-muted">Nike</h6>
                          <h6 class="mb-0">Size 43 | Xanh</h6>
                        </div>

                        <div class="col-md-3 col-lg-3 col-xl-2 d-flex">
                          <button data-mdb-button-init data-mdb-ripple-init class="btn btn-link px-2" onclick="this.parentNode.querySelector('input[type=number]').stepDown()">
                            <i class="fas fa-minus"></i>
                          </button>

                          <input id="form1" min="0" name="quantity" value="1" type="number" class="form-control form-control-sm" />

                          <button data-mdb-button-init data-mdb-ripple-init class="btn btn-link px-2" onclick="this.parentNode.querySelector('input[type=number]').stepUp()">
                            <i class="fas fa-plus"></i>
                          </button>
                        </div>
                        <div class="col-md-3 col-lg-2 col-xl-2 offset-lg-1">
                          <h6 class="mb-0">500.000</h6>
                        </div>
                        <div class="col-md-1 col-lg-1 col-xl-1 text-end">
                          <a href="#!" class="text-muted hover-red "><i
                                  class="fas fa-times"></i></a>
                        </div>
                      </div>

                      <hr class="my-4">

                      <div class="row mb-4 d-flex justify-content-between align-items-center">
                        <div class="col-md-2 col-lg-2 col-xl-2">
                          <img src="assets/shoes/2.png" class="img-fluid rounded-3" alt="Coverse shoes">
                        </div>
                        <div class="col-md-3 col-lg-3 col-xl-3">
                          <h6 class="text-muted">Coverse</h6>
                          <h6 class="mb-0">Size 40 | Đen </h6>
                        </div>
                        <div class="col-md-3 col-lg-3 col-xl-2 d-flex">
                          <button data-mdb-button-init data-mdb-ripple-init class="btn btn-link px-2" onclick="this.parentNode.querySelector('input[type=number]').stepDown()">
                            <i class="fas fa-minus"></i>
                          </button>

                          <input id="form2" min="0" name="quantity" value="1" type="number" class="form-control form-control-sm" />

                          <button data-mdb-button-init data-mdb-ripple-init class="btn btn-link px-2" onclick="this.parentNode.querySelector('input[type=number]').stepUp()">
                            <i class="fas fa-plus"></i>
                          </button>
                        </div>
                        <div class="col-md-3 col-lg-2 col-xl-2 offset-lg-1">
                          <h6 class="mb-0">499.000</h6>
                        </div>
                        <div class="col-md-1 col-lg-1 col-xl-1 text-end">
                          <a href="#!" class="text-muted hover-red "><i
                                  class="fas fa-times"></i></a>
                        </div>
                      </div>

                      <hr class="my-4">

                      <div class="pt-5">
                        <h6 class="mb-0"><a href="index.html" class="text-body"><i
                                class="fas fa-long-arrow-alt-left me-2"></i>Back to
                          shop</a>
                        </h6>
                      </div>
                    </div>
                  </div>
                  <div class="col-lg-4 bg-body-tertiary">
                    <div class="p-5">
                      <h3 class="fw-bold mb-5 mt-2 pt-1">Tóm tắt</h3>
                      <hr class="my-4">

                      <div class="d-flex justify-content-between mb-4">
                        <h5 class="text-uppercase">3 món</h5>
                        <h5>1.598.000</h5>
                      </div>

                      <h5 class="text-uppercase mb-3">Phương thức giao hàng</h5>

                      <div class="d-flex justify-content-between mb-4">
                        <select data-mdb-select-init>
                          <option value="1">Hỏa tốc - Ngày mai</option>
                          <option value="2">Nhanh</option>
                          <option value="3">Tiêu chuẩn</option>
                        </select>
                        <h5>40.000</h5>

                      </div>
                      <h5 class="text-uppercase mb-3">Hình thức thanh toán</h5>

                      <div class="d-flex justify-content-between mb-4">
                        <select data-mdb-select-init>
                          <option value="1">Visa - Mastercard - Jcb</option>
                          <option value="2">Momo</option>
                          <option value="3">Tiêu chuẩn - Cod</option>
                        </select>
                        <h5> Tiện lợi </h5>

                      </div>

                      <h5 class="text-uppercase mb-3">Mã khuyến mãi</h5>

                      <div class="mb-5">
                        <div data-mdb-input-init class="form-outline">
                          <input type="text" id="form3Examplea2" class="form-control form-control-lg" />
                          <label class="form-label" for="form3Examplea2">Nhập mã của
                            bạn</label>
                        </div>
                      </div>

                      <hr class="my-4">

                      <div class="d-flex justify-content-between mb-5">
                        <h5 class="text-uppercase">Tổng thanh toán</h5>
                        <h5>1.638.000</h5>
                      </div>

                      <a href="statusShoes.html">
                        <button type="button" data-mdb-button-init data-mdb-ripple-init class="btn btn-dark btn-block btn-lg" data-mdb-ripple-color="dark">Thanh
                          toán
                        </button>
                      </a>
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



</body>

</html>