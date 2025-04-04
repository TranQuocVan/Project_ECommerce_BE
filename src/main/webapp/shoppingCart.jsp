<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Giỏ hàng của bạn</title>
    <link rel="icon" type="image/svg" href="assets/logo2.svg">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/6.4.2/mdb.min.css" rel="stylesheet"/>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" href="styles/slider.css">
    <link rel="stylesheet" href="styles/navigation.css">
    <link rel="stylesheet" href="styles/global.css">
    <link rel="stylesheet" href="styles/shoppingCart.css?v=${System.currentTimeMillis()}">
    <link rel="stylesheet" href="styles/voucherModal.css?v=${System.currentTimeMillis()}">
</head>

<body>
<header>
    <nav></nav>
    <div class="under-navigation">
        <div class="h-100 h-custom mt-5">
            <div class="container py-5 h-100">
                <div class="row d-flex justify-content-center align-items-center h-100">
                    <div class="col-12">
                        <div class="card card-registration card-registration-2" style="border-radius: 15px;">
                            <div class="card-body p-0">
                                <div class="row g-0">
                                    <div class="col-lg-8">
                                        <div class="p-5">
                                            <div class="d-flex justify-content-between align-items-center mb-5">
                                                <h1 class="fw-bold mb-0">Giỏ hàng</h1>
                                            </div>
                                            <!-- Display message if shopping cart is empty -->
                                            <c:if test="${empty shoppingCartItemsList}">
                                                <div class="alert alert-info text-center mt-4" role="alert">
                                                    Giỏ hàng của bạn đang trống. Hãy thêm sản phẩm vào giỏ hàng để tiếp tục mua sắm!
                                                </div>
                                            </c:if>
                                            <!-- Display shopping cart items -->
                                            <c:if test="${not empty shoppingCartItemsList}">
                                                <c:forEach var="item" items="${shoppingCartItemsList}">
                                                    <hr class="my-4">
                                                    <div class="row mb-4 d-flex justify-content-between align-items-center items" style="cursor: pointer">
                                                        <div class="col-md-1 checkbox">
                                                            <input type="hidden" name="sizeId" value="${item.sizeId}">
                                                            <input type="checkbox" class="select-item" data-item-id="${item.sizeId}" data-price="${item.discountPrice}" />
                                                        </div>
                                                        <div class="col-md-2 col-lg-2 col-xl-2">
                                                            <img src="data:image/jpeg;base64,${item.imageBase64}" class="img-fluid rounded-3" alt="Fashion shoes">
                                                        </div>
                                                        <div class="col-md-3 col-lg-3 col-xl-3">
                                                            <h6 class="text-muted">${item.nameProduct}</h6>
                                                            <h6 class="mb-0">Size ${item.nameSize} | ${item.nameColor}</h6>

                                                        </div>
                                                        <div class="col-md-3 col-lg-3 col-xl-2 d-flex">
                                                            <button class="btn btn-link px-2" onclick="decreaseQuantity(this,event)">
                                                                <i class="fas fa-minus"></i>
                                                            </button>
                                                            <input readonly id="form" min="1" name="quantity" value="${item.quantity}" type="number" class="form-control form-control-sm quantityShoppingCart" oninput="handleQuantityChange(this)" />
                                                            <button class="btn btn-link px-2" onclick="increaseQuantity(this,event)">
                                                                <i class="fas fa-plus"></i>
                                                            </button>
                                                        </div>
                                                        <div class="col-md-3 col-lg-2 col-xl-2 offset-lg-1">
                                                            <h6 class="mb-0"><fmt:formatNumber value="${item.discountPrice}" type="number" groupingUsed="true"/>
                                                            </h6>
                                                        </div>
                                                        <div class="col-md-1 col-lg-1 col-xl-1 text-end">
                                                            <form method="POST" action="DeleteCartItemController">
                                                                <input type="hidden" name="sizeId" value="${item.sizeId}">
                                                                <button class="btn btn-link px-2" onclick="handleDelete(this, '${item.nameProduct}', '${item.nameSize}'); return false;">
                                                                    <i class="fas fa-times"></i>
                                                                </button>
                                                            </form>
                                                        </div>
                                                    </div>
                                                </c:forEach>
                                            </c:if>
                                            <div class="pt-5">
                                                <h6 class="mb-0"><a href="IndexController" class="text-body"><i class="fas fa-long-arrow-alt-left me-2"></i>Trở về trang chủ</a></h6>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- Summary Section -->
                                    <div class="col-lg-4 bg-body-tertiary">
                                        <div class="p-5">
                                            <h3 class="fw-bold mb-5 mt-2 pt-1">Tóm tắt</h3>
                                            <hr class="my-4">
                                            <div class="d-flex justify-content-between mb-4 align-items-center">
                                                <h5 class="">Tổng số sản phẩm</h5>
                                                <div id="totalSelectedItems">0 món</div>
                                            </div>
                                            <form id="orderForm" method="post">
                                                <input type="hidden" id="selectedItems" name="selectedItems" />
                                                <h5 class=" mb-3">Phương thức giao hàng</h5>
                                                <div class="d-flex justify-content-between mb-4">
                                                    <select name="deliveryId" id="deliverySelect">
                                                        <c:if test="${not empty listDeliveriesModels}">
                                                            <c:forEach var="item" items="${listDeliveriesModels}">
                                                                <option value="${item.id}" data-fee="${item.fee}">${item.name}</option>
                                                            </c:forEach>
                                                        </c:if>
                                                    </select>
                                                    <div id="feeDisplay"></div>
                                                </div>
                                                <h5 class=" mb-3">Hình thức thanh toán</h5>
                                                <div class="d-flex justify-content-between mb-4">
                                                    <select name="paymentId" id="paymentId">
                                                        <c:if test="${not empty listPaymentModels}">
                                                            <c:forEach var="item" items="${listPaymentModels}">
                                                                <option value="${item.id}">${item.methodPayment}</option>
                                                            </c:forEach>
                                                        </c:if>
                                                    </select>
                                                    <div id="convenient">Tiện lợi</div>
                                                </div>
                                                <h5 class=" mb-3">Địa chỉ nhận hàng</h5>
                                                <div class="mb-3">
                                                    <input type="text" class="form-control form-control-lg" name="address" placeholder="Nhập địa chỉ của bạn" />
                                                </div>

                                                <h5 class=" mb-3">Voucher hiện có</h5>
                                                <!-- Nút mở modal -->
                                                <button type="button"
                                                        class="btn btn-primary"
<%--                                                        data-bs-toggle="modal"--%>
<%--                                                        data-bs-target="#myModal"--%>
                                                        onclick="handleChooseVoucher()">
                                                    Chọn Voucher
                                                </button>

                                                <!-- Modal -->
                                                <div class="modal fade" id="myModal" tabindex="-1" aria-labelledby="myModalLabel" aria-hidden="true">
                                                    <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                                                        <form id="voucherForm" method="post">
                                                            <div class="modal-content">
                                                                <div class="modal-header">
                                                                    <h5 class="modal-title" id="myModalLabel">Voucher hiện có</h5>
                                                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                                </div>

                                                                <div class="modal-body">
                                                                    <h6>Giảm giá vận chuyển</h6>
                                                                    <c:choose>
                                                                        <c:when test="${not empty listVoucherShipping}">
                                                                            <table class="table table-hover">
                                                                                <tbody>
                                                                                <c:forEach var="voucher" items="${listVoucherShipping}">
                                                                                    <tr style="cursor: pointer;" onclick="selectVoucherShipping(this)">
                                                                                        <td><i class="fa-solid fa-truck-fast" style="font-size: 40px"></i></td>
                                                                                        <td>
                                                                                            <div style="font-weight: 600">Voucher giảm giá: ${voucher.discountPercent.intValue()}%</div>
                                                                                            <div>Giảm tối đa:
                                                                                                <fmt:setLocale value="vi_VN" />
                                                                                                <fmt:formatNumber value="${voucher.discountMaxValue}" type="number" groupingUsed="true" />đ
                                                                                            </div>
                                                                                            <div>Số lượng: x${voucher.quantity}</div>
                                                                                        </td>
                                                                                        <td>
                                                                                            <input type="radio" name="selectedVoucherShipping" value="${voucher.voucherId}" onchange="highlightRow(this)" style="cursor: pointer !important;">
                                                                                        </td>
                                                                                    </tr>
                                                                                </c:forEach>
                                                                                </tbody>
                                                                            </table>
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <p>Hiện không có voucher.</p>
                                                                        </c:otherwise>
                                                                    </c:choose>

                                                                    <br>

                                                                    <h6>Giảm giá sản phẩm</h6>
                                                                    <c:choose>
                                                                        <c:when test="${not empty listVoucherItems}">
                                                                            <table class="table table-hover">
                                                                                <tbody>
                                                                                <c:forEach var="voucher" items="${listVoucherItems}">
                                                                                    <tr style="cursor: pointer;" onclick="selectVoucherItems(this)">
                                                                                        <td><i class="fa-solid fa-bag-shopping" style="font-size: 40px"></i></td>
                                                                                        <td>
                                                                                            <div style="font-weight: 600">Voucher giảm giá: ${voucher.discountPercent.intValue()}%</div>
                                                                                            <div>Giảm tối đa:
                                                                                                <fmt:setLocale value="vi_VN" />
                                                                                                <fmt:formatNumber value="${voucher.discountMaxValue}" type="number" groupingUsed="true" />đ
                                                                                            </div>
                                                                                            <div>Số lượng: x${voucher.quantity}</div>
                                                                                        </td>
                                                                                        <td>
                                                                                            <input type="radio" name="selectedVoucherItems" value="${voucher.voucherId}" onchange="highlightRow(this)" style="cursor: pointer !important;">
                                                                                        </td>
                                                                                    </tr>
                                                                                </c:forEach>
                                                                                </tbody>
                                                                            </table>
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <p>Hiện không có voucher.</p>
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                </div>

                                                                <input type="hidden" id="selectedVoucherShippingHidden" name="selectedVoucherShipping">
                                                                <input type="hidden" id="selectedVoucherItemsHidden" name="selectedVoucherItems">

                                                                <div class="modal-footer d-flex justify-content-between">
                                                                    <div class="me-auto">
                                                                        <div>
                                                                            <span>Giảm giá vận chuyển: </span>
                                                                            <span id="discountFee">0đ</span>
                                                                        </div>
                                                                        <div>
                                                                            <span>Giảm giá sản phẩm: </span>
                                                                            <span id="discountItems">0đ</span>
                                                                        </div>
                                                                        <div>
                                                                            <span>Tổng thanh toán: </span>
                                                                            <span class="text-success" id="totalAmountModalVoucher">0</span>
                                                                        </div>
                                                                    </div>
                                                                    <div>
                                                                        <button type="button" class="btn btn-primary" data-bs-dismiss="modal" onclick="confirmVoucher(); submitVoucher();">Xác nhận</button>
                                                                    </div>
                                                                </div>

                                                            </div>
                                                        </form>
                                                    </div>
                                                </div>

                                                <div id="voucherSelected"></div>

                                                <hr class="my-4">
                                                <div class="d-flex justify-content-between mb-5">
                                                    <h5 class="">Tổng thanh toán</h5>
                                                    <h5><span id="totalAmount">0</span></h5>
                                                    <input type="hidden" name="totalAmount" id="hiddenTotalAmount">
                                                </div>
                                                <button class="btn btn-dark btn-block btn-lg" onclick="orderButton(this, event);">
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

<footer></footer>
<script src="components/navigation.js"></script>
<script src="components/footer.js"></script>
<script src="scripts/shoppingCart.js?v=${System.currentTimeMillis()}"></script>
</body>



</html>
