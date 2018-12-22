<%-- 
    Document   : newProduct
    Created on : 1-lug-2018, 18.16.43
    Author     : weatherly
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/login.css" rel="stylesheet" type="text/css"/>
        <title>Shopping List - Product</title>
        <link rel="icon" href="favicon.ico" type="image/x-icon"/>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    </head>

    <body>
        <jsp:include page="menu.jsp" />
        <script src="JS/uploadFile.js" type="text/javascript"></script>

        <div class="main">
            <div class="card">
                <form action="CreateProductServlet" enctype="multipart/form-data" method="POST">
                    <input type="file" name="file" id="file" class="inputfile" data-multiple-caption="{count} files selected" multiple required>
                    <label for="file" class="button1 button2 btn" ><span class="glyphicon glyphicon-open"></span> Choose an image </label>
                    <div class="form-group elemento">
                        <label> Name: </label>
                        <input type="text" id="name" name="name" class="form-control" placeholder="Enter product name"
                               required autofocus>
                    </div>
                    <div class="form-group elemento">
                        <label> Category: </label>
                        <select class="form-control" name="selectCategory" id="category-select">
                            <c:forEach items="${CatProductList}" var="catproduct">
                                <option value="${catproduct}">${catproduct}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label> Description: </label>
                        <textarea id="description" name="description" cols="40" rows="5" class="form-control descrizione"
                                  placeholder="Enter product description" required></textarea>
                    </div>
                    <button class="button1" type="submit"><b>Create</b></button>
                    <button class="button1" onclick="goBack()"><b>Cancel</b></button>
                </form>
                <script>
                    function goBack() {
                        window.history.back();
                    }
                </script>
            </div>
        </div>
    </body>
</html>
