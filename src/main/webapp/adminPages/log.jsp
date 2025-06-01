<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Quản lí Log</title>
    <link rel="icon" type="image/svg" href="assets/logo2.svg" />

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous" />

    <!-- jQuery -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

    <!-- Bootstrap Bundle JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>

    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" />

    <!-- Custom CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/admin/addProductCategory.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/globa.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/slider.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/navigation.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/admin/navigationAdmin.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/global.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/admin/globaladmin.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/admin/log.css?v=${System.currentTimeMillis()}" />
</head>

<body>
<header>
    <nav></nav>
    <div class="under-navigation">
        <div class="container">
            <div class="row mt-5">
                <h5 class="titlePageAdmin">Quản lí log</h5>

                <!-- AdminController Navigation -->
                <div class="col-md-3">
                    <div id="navigationAdmin"></div>
                </div>

                <!-- Log Table Section -->
                <div class="col-9">
                    <!-- Filter Form -->
                    <div class="filter-section mb-3">
                        <form id="filterForm" class="row g-3">
                            <div class="col-md-3">
                                <label for="userIdFilter" class="form-label">User ID</label>
                                <input type="text" class="form-control" id="userIdFilter" placeholder="Enter User ID">
                            </div>
                            <div class="col-md-3">
                                <label for="actionFilter" class="form-label">Action</label>
                                <select class="form-select" id="actionFilter">
                                    <option value="">All Actions</option>
                                    <option value="INSERT">INSERT</option>
                                    <option value="UPDATE">UPDATE</option>
                                    <option value="DELETE">DELETE</option>
                                </select>
                            </div>
                            <div class="col-md-3">
                                <label for="tableNameFilter" class="form-label">Table Name</label>
                                <input type="text" class="form-control" id="tableNameFilter" placeholder="Enter Table Name">
                            </div>
                            <div class="col-md-3">
                                <label for="dateFilter" class="form-label">Timestamp</label>
                                <input type="date" class="form-control" id="dateFilter">
                            </div>
                            <div class="col-6">

                            </div>
                            <div id="filter"  class="col-6">
                                <button type="button" class="btn btn-primary" onclick="filterLogs()">Filter</button>
                                <button type="button" class="btn btn-primary" onclick="resetFilter()">Reset</button>
                            </div>
                        </form>
                    </div>

                    <!-- Log Table -->
                    <table class="table table-bordered table-hover" id="logsTable">
                        <thead class="table-dark">
                        <tr>
                            <th>ID</th>
                            <th>User ID</th>
                            <th>Action</th>
                            <th>Table Name</th>
                            <th>Data Before</th>
                            <th>Data After</th>
                            <th>IP Address</th>
                            <th>Timestamp</th>
                        </tr>
                        </thead>
                        <tbody>
                        <!-- Dữ liệu log sẽ load ở đây -->
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</header>

<footer></footer>

<!-- Scripts -->
<script src="${pageContext.request.contextPath}/components/navigation.js"></script>
<script src="${pageContext.request.contextPath}/components/footer.js"></script>
<script src="${pageContext.request.contextPath}/scripts/scroll.js"></script>
<script src="${pageContext.request.contextPath}/components/navigationadmin.js?v=${System.currentTimeMillis()}"></script>
<script src="${pageContext.request.contextPath}/scripts/admin/log.js?v=${System.currentTimeMillis()}"></script>

</body>
</html>