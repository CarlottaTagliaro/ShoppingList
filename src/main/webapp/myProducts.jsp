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
        <link rel="icon" href="favicon.ico" type="image/x-icon"/>
        
        <%@ taglib uri="/tlds/productCard" prefix="productCard"%>
        <link href="css/home_css.css" rel="stylesheet" type="text/css"/>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    </head>
    <body>
        <jsp:include page="menu.jsp"/>
        <script src="JS/jquery.bootstrap-touchspin.js" type="text/javascript"></script>
        <script>
            $(document).ready(function() {
                selectMenuEl("myProducts"); 
            });  
            
            function changePage(num) {
                $("#page").val(num);
                $(".search-form").submit()
            }
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
            
            <form class="search-form" action="myProducts">
                <input type="hidden" name="page" id="page" value="${page}">
            </form>
            
            <div class="row center">
                <ul class="pagination">
                    <c:forEach var = "i" begin = "1" end = "${count}">
                        <li class="${(i-1 == page) ? 'active' : ''}"><a href="#" onclick="changePage(${i-1})">${i}</a></li>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </body>
</html>
