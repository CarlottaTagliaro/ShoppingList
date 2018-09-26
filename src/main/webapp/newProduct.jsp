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
        <title>JSP Page</title>
    </head>
    <body>
        <jsp:include page="menu.jsp"/>
        <div class="main">
            <div class="card">
                <img class="logo" alt="Qui ci sarà la feature di caricamento" width="128" height="128">
                <div class="form-group elemento">
                    <label> Name: </label>
                    <input type="text" id="name" name="name" class="form-control" placeholder="Enter product name" required autofocus>
                </div>
                <div class="form-group elemento">
                    <label> Category: </label>
                    <select class="form-control" id="category-select">
                        <option value="byName">name</option>
                        <option value="byShop">shop</option>
                    </select>
                </div>   
                <div class="form-group">
                    <label> Description: </label>
                    <textarea id="description" name="description" cols="40" rows="5" class="form-control descrizione" placeholder="Enter product description" required></textarea>
                </div>
                <button class="button1" type="submit" ><b>Create</b></button>
                <button class = "button1"type="submit"  onclick="goBack()"><b>Cancel</b></button>
                <script>
                    function goBack() {
                        window.history.back();
                    } 
                </script> 
            </div>
        </div>
    </body>
</html>
