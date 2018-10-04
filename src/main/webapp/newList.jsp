<%-- 
    Document   : newList
    Created on : 27-ago-2018, 17.04.53
    Author     : weatherly
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/login.css" rel="stylesheet" type="text/css"/>
        <title>Shopping List - List</title>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    </head>
    <body>
        <jsp:include page="menu.jsp"/>
        <script src="JS/uploadFile.js" type="text/javascript"></script>
        <div class="main">
            <div class="card">
                <form id="upload_form" enctype="multipart/form-data" method="post">
                    <input type="file" name="file" id="file" class="inputfile">
                    <label for="file" class="button1 button2 btn" ><span class="glyphicon glyphicon-open"></span> Choose file </label>
                </form>
                <div class="form-group elemento">
                    <label> Name: </label>
                    <input type="text" id="name" name="name" class="form-control" placeholder="Enter list name" required autofocus>
                </div>
                <div class="form-group elemento">
                    <label> Category: </label>
                    <select class="form-control" id="category-select">
                        <c:forEach items="${categories}" var="catproduct">
                            <option value="${catproduct}">${catproduct}</option>
                        </c:forEach>
                    </select>
                </div>   
                <div class="form-group">
                    <label> Description: </label>
                    <textarea id="description" name="description" cols="40" rows="5" class="form-control descrizione" placeholder="Enter product description" required></textarea>
                </div>
                <button class="button1" type="submit"><b>Create</b></button>
                <button class = "button1"type="submit" onclick="goBack()"><b>Cancel</b></button>
                <script>
                    function goBack() {
                        window.history.back();
                    }
                </script> 
            </div>
        </div>
    </body>
</html>

