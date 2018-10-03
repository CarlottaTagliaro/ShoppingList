<%-- 
    Document   : prodotti
    Created on : 19-set-2018, 9.38.26
    Author     : weatherly
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/home_css.css" rel="stylesheet" type="text/css"/>
        <title>Shopping List - Shops</title>
        
        <%@ taglib uri="/tlds/productCard" prefix="productCard"%>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    </head>
    <body>
        <jsp:include page="menu.jsp"/>
        
        <script>
            $(document).ready(function() {
                selectMenuEl("shops"); 
            });   
        </script>

        <div class="main">
            <div class="row search-form">
                    <div class="input-group cerca">
                        <input type="text" class="form-control form-control1" aria-label="..." placeholder="Search product">
                        <div class="input-group-btn">
                            <button type="button" class="btn bottone-cerca btn-default">
                                <span class="glyphicon glyphicon-search"/>
                            </button>
                        </div>
                    </div>
            </div>

            
            <c:forEach items="${productList}" var="product">
                <productCard:productCard product="${product}"/>
            </c:forEach>
        </div>
    </body>
</html>
