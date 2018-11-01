<%-- 
    Document   : home
    Created on : 22-giu-2018, 14.28.49
    Author     : weatherly
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/home_css.css" rel="stylesheet" type="text/css"/>
        <link href="css/jquery.bootstrap-touchspin.css" rel="stylesheet" type="text/css"/>
        <title>Shopping List - Home</title>
        <link rel="icon" href="favicon.ico" type="image/x-icon"/>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib uri="/tlds/productCard" prefix="productCard"%>
    </head>
    <body>
        <jsp:include page="menu.jsp"/>

        <script src="JS/jquery.bootstrap-touchspin.js" type="text/javascript"></script>
        <script>
            $(document).ready(function () {
                selectMenuEl("home");

                //submit form when order selection changed
                $("#search-select").change(function () {
                    $(".search-form").submit()
                });
            });
            $(document).ready(function () {
                var value = "${orderBy}";
                if (value != "")
                    $("select[name=orderBy]").val(value);
            });

            function changePage(num) {
                $("#page").val(num);
                $(".search-form").submit()
            }
        </script>

        <div class="main">
            <form class="row search-form" action="home">
                <input type="hidden" id="page" name="page" value="${page}">
                <div class="col-sm-7">
                    <div class="ordering-form">
                        <label class="search"> Order by: </label>
                        <select class="form-control selezione" name="orderBy" id="search-select">
                            <option value="byName">name</option>
                            <option value="byShop">shop</option>
                        </select>
                    </div>
                </div>

                <div class="col-sm-5">
                    <div class="input-group">
                        <input type="text" class="form-control form-control1" name="qry" aria-label="..." placeholder="Search product" value="${qry}">
                        <div class="input-group-btn">
                            <button type="submit" class="btn bottone-cerca btn-default">
                                <span class="glyphicon glyphicon-search"/>
                            </button>
                        </div>
                    </div>
                </div>
            </form>


            <c:forEach items="${productList}" var="product">
                <productCard:productCard product="${product}"/>
            </c:forEach>

            <div class="row center">
                <ul class="pagination">
                    <c:forEach var = "i" begin = "1" end = "${count}">
                        <li><a href="#" onclick="changePage(${i-1})">${i}</a></li>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </body>
</html>

