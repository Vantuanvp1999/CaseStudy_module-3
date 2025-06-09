<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 6/9/2025
  Time: 8:47 AM
  To change this template use File | Settings | File Templates.
--%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <style>
        table {
            font-family: arial, sans-serif;
            border-collapse: collapse;
            width: 100%;
        }

        td, th {
            border: 1px solid #dddddd;
            text-align: left;
            padding: 8px;
        }

        tr:nth-child(even) {
            background-color: #dddddd;
        }

        #total {
            position: relative;
            left: 1200px;
            font-size: 18px;
            font-weight: bold;
            color: red;
        }
    </style>
</head>
<body>
<div id="content">
    <h1>
        Carts
    </h1>
    <h2><a href="<c:url value="/products?action=products"/>">List all users</a></h2>
    <table>
        <tr>
            <th>Product Name</th>
            <th>Product Price</th>
            <th>Quantity</th>
            <th>Price</th>
            <th>Delete</th>
            <th></th>
        </tr>
        <c:set var="totalPrice" value="${0}"/>
        <c:forEach var="item" items="${sessionScope.cart}">
        <form action="/carts?action=update" method="post">
            <tr>
                <td><p><c:out value="${item.getProduct().getName().toString()}"/></p></td>
                <td><p><c:out value="${item.getProduct().getPrice().toString()}"/></p></td>
                <input type="hidden" name="id" value="${item.product.id}">
                <td>
                    <input type="text" id="quantity" name="quantity"
                           value="${item.getQuantity().toString()}" onchange="this.form.submit()" min="1" >
                </td>

                <td><p>${item.quantity * item.product.price}</p></td>
                <td>
                    <a href="/carts?action=delete&id=${item.product.id}" class="btn btn-danger">Xóa</a>
                </td>
            </tr>
            <c:set var="totalPrice" value="${totalPrice + (item.getQuantity() * item.getProduct().getPrice())}"/>
        </form>
        </c:forEach>
    </table>
    <p id="total">Total Price: <c:out value="${totalPrice}"/></p>
    <a href="/carts?action=checkout">Checkout</a>
</div>
</body>
</html>
