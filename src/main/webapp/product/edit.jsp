<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Product</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
        }

        .container {
            width: 500px;
            margin: 40px auto;
            background-color: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
        }

        h2 {
            text-align: center;
            color: #333;
            margin-bottom: 20px;
        }

        table {
            width: 100%;
        }

        th {
            text-align: left;
            padding: 10px 0;
            width: 30%;
        }

        td {
            padding: 10px 0;
        }

        input[type="text"],
        input[type="file"] {
            width: 100%;
            padding: 8px 10px;
            border: 1px solid #ccc;
            border-radius: 6px;
            font-size: 14px;
        }

        input[type="submit"] {
            background-color: #007bff;
            color: white;
            padding: 10px 20px;
            font-size: 16px;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            margin-top: 20px;
        }

        input[type="submit"]:hover {
            background-color: #0056b3;
        }

        .back-link {
            display: block;
            text-align: center;
            margin-bottom: 20px;
            color: #007bff;
            text-decoration: none;
        }

        .back-link:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<div class="container">
    <a class="back-link" href="<c:url value='/products?action=products'/>">← Back to product list</a>
    <form method="post" enctype="multipart/form-data">
        <h2>Edit Product</h2>
        <c:if test="${product != null}">
            <input type="hidden" name="id" value="<c:out value='${product.getId()}' />"/>
        </c:if>
        <table>
            <tr>
                <th><label for="name">Name:</label></th>
                <td>
                    <input type="text" name="name" id="name"
                           value="<c:out value='${product.getName()}' />" required/>
                </td>
            </tr>
            <tr>
                <th><label for="price">Price:</label></th>
                <td>
                    <input type="text" name="price" id="price"
                           value="<c:out value='${product.getPrice()}' />" required/>
                </td>
            </tr>
            <tr>
                <th><label for="image">Image:</label></th>
                <td>
                    <input type="file" name="image" id="image"/>
                </td>
            </tr>
            <tr>
                <td colspan="2" style="text-align: center;">
                    <input type="submit" value="Save"/>
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
