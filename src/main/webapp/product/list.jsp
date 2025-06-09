<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="vi">
<head>
    <title>Product List</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
<div id="content" class="container mt-4">
    <h1>Content</h1>

    <div class="row">
        <c:forEach var="p" items="${listProduct}">
            <div class="col-md-4 mb-3">
                <div class="card" style="width: 100%;">
                   <img width="170px" height="200px" src="${pageContext.request.contextPath}/resources/uploads/${p.image}" class="card-img-top" alt="Product Image"/>
                    <div class="card-body">
                        <h5 class="card-title">${p.name}</h5>
                        <p class="card-text">${p.price}</p>
                        <a href="${pageContext.request.contextPath}/products?action=edit&id=${p.id}" class="btn btn-primary">Edit</a>
                        <a href="${pageContext.request.contextPath}/products?action=delete&id=${p.id}" class="btn btn-danger">Delete</a>
                        <a href="${pageContext.request.contextPath}/carts?action=add&id=${p.id}" class="btn btn-success">Add to Cart</a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
</body>
</html>
