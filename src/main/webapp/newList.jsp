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
        <title>JSP Page</title>
    </head>
    <body>
        <jsp:include page="menu.jsp"/>
        <div class="main">
            <div class="card">
                <img class="logo" alt="Qui ci sarà la feature di caricamento" width="128" height="128">
                <div class="form-group elemento">
                    <label> Name: </label>
                    <input type="text" id="name" name="name" class="form-control" placeholder="Enter list name" required autofocus>
                </div>
                <div class="form-group elemento">
                    <label> Category: </label>
                    <select class="form-control" id="category-select">
                        <option value="byName">supermarket</option>
                        <option value="byShop">pet shop</option>
                    </select>
                </div>   
                <div class="form-group">
                    <label> Description: </label>
                    <textarea id="description" name="description" cols="40" rows="5" class="form-control descrizione" placeholder="Enter product description" required></textarea>
                </div>
                <button class="button1" type="submit" >Create</button>
                <button class = "button1"type="submit"  onclick="goBack()">Annulla</button>
                <script>
                    function goBack() {
                        window.history.back();
                    } 
                </script> 
            </div>
        </div>
    </body>
</html>
