<%-- 
    Document   : myProducts
    Created on : 1-lug-2018, 15.10.20
    Author     : weatherly
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/myProducts.css" rel="stylesheet" type="text/css"/>
        <title>Shopping List - Products</title>
        
        <%@ taglib uri="/tlds/productCard" prefix="productCard"%>
        <link href="css/home_css.css" rel="stylesheet" type="text/css"/>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    </head>
    <body>
        <jsp:include page="menu.jsp"/>
        
        <script>
            $(document).ready(function() {
                selectMenuEl("myProducts"); 
            });   
        </script>
        
        <div class="main">
            <div class="row create">
                <div class="crea-prodotto">
                    <label class="crea" style="font-size: 20px;"> Create new product: </label>
                    <button class="myButton2" onclick="location.href='newProduct'"> <b> + </b> </button>
                </div>
            </div>
        
            <c:forEach items="${productList}" var="product">
                <productCard:productCard product="${product}"/>
            </c:forEach>
        </div>
    </body>
</html>
