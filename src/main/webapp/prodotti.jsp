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
        <link rel="icon" href="favicon.ico" type="image/x-icon"/>

        <%@ taglib uri="/tlds/productCard" prefix="productCard"%>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    </head>
    <body>
        <jsp:include page="menu.jsp"/>

        <script src="JS/jquery.bootstrap-touchspin.js" type="text/javascript"></script>
        <script>
            $(document).ready(function () {
                selectMenuEl("shops");
            });
            
            function changePage(num) {
                $("#page").val(num);
                $(".search-form").submit()
            }
        </script>

        <div class="main">
            <div class="row search-form">
                <form class="input-group cerca">
                    <input type="hidden" name="page" id="page" value="${page}">
                    <input type="hidden" name="catName" value="<%=request.getParameter("catName")%>">
                    <input type="text" class="form-control form-control1" name="qry" aria-label="..." placeholder="Search product" value="${qry}">
                    <div class="input-group-btn">
                        <button type="submit" class="btn bottone-cerca btn-default">
                            <span class="glyphicon glyphicon-search"/>
                        </button>
                    </div>
                </form>
                <label class="pageFrom">${titolo}</label>

            </div>

            <c:forEach items="${productList}" var="product">
                <productCard:productCard product="${product}"/>
            </c:forEach>


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
