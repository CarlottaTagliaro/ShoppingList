<%-- 
    Document   : home
    Created on : 22-giu-2018, 14.28.49
    Author     : weatherly
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="it.webproject2018.db.entities.Prodotto"%>
<%@page import="java.util.List"%>
<%@page import="it.webproject2018.db.entities.Utente"%>
<%@page import="it.webproject2018.db.daos.jdbc.JDBCProdottoDAO"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/home_css.css" rel="stylesheet" type="text/css"/>
        <link href="css/jquery.bootstrap-touchspin.css" rel="stylesheet" type="text/css"/>
        <title>Shopping List - Home</title>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib uri="/tlds/productCard" prefix="productCard"%>
    </head>
    <body>
        <jsp:include page="menu.jsp"/>

        <script src="JS/jquery.bootstrap-touchspin.js" type="text/javascript"></script>
        <script>
            $(document).ready(function () {
                selectMenuEl("home");
            });
        </script>

        <div class="main">
            <form class="row search-form">
                <div class="col-sm-7">
                    <div class="ordering-form">
                        <label class="search"> Order by: </label>
                        <select class="form-control selezione" name="orderBy" id="search-select">
                            <option value="byName">name</option>
                            <option value="byShop">shop</option>
                        </select>
                    </div>
                </div>

                <script>
                    $(document).ready(function () {
                        var value = "<%= request.getParameter("orderBy") == null ? "" : request.getParameter("orderBy")%>";
                        if(value != "")
                            $("select[name=orderBy]").val(value);
                    });
                </script>

                <div class="col-sm-5">
                    <div class="input-group">
                        <input type="text" class="form-control form-control1" name="qry" aria-label="..." placeholder="Search product" value="<%= request.getParameter("qry") == null ? "" : request.getParameter("qry")%>">
                        <div class="input-group-btn">
                            <button type="submit" class="btn bottone-cerca btn-default">
                                <span class="glyphicon glyphicon-search"/>
                            </button>
                        </div>
                    </div>
                </div>
            </form>


            <%
                JDBCProdottoDAO JdbcProdottoDao = new JDBCProdottoDAO(super.getServletContext());
                Utente user = (Utente) request.getSession().getAttribute("User");
                List<Prodotto> productList;

                String srcText = request.getParameter("qry");
                String orderBy = request.getParameter("orderBy");

                if (user != null) {
                    productList = JdbcProdottoDao.getAllUserVisibleProducts(user.getEmail(), srcText, orderBy);
                } else {
                    productList = JdbcProdottoDao.getAllVisibleProducts(srcText, orderBy);
                }

                pageContext.setAttribute("productList", productList);
            %>

            <c:forEach items="${productList}" var="product">
                <productCard:productCard product="${product}"/>
            </c:forEach>

        </div>
    </body>
</html>

