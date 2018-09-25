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
        <title>JSP Page</title>

        <%@ taglib uri="/tlds/productCard" prefix="productCard"%>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            <div class="row search-form">
                <div class="col-sm-7">
                    <div class="ordering-form">
                        <label class="search"> Order by: </label>
                        <select class="form-control selezione" id="search-select">
                            <option value="byName">name</option>
                            <option value="byShop">shop</option>
                        </select>
                    </div>
                </div>
                <div class="col-sm-5">
                    <div class="input-group">
                        <input type="text" class="form-control form-control1" aria-label="..." placeholder="Search product">
                        <div class="input-group-btn">
                            <button type="button" class="btn bottone-cerca btn-default">
                                <span class="glyphicon glyphicon-search"/>
                            </button>
                        </div>
                    </div>
                </div>
            </div>

            
            <%                    
                Connection conn = (Connection) super.getServletContext().getAttribute("connection");
                JDBCProdottoDAO JdbcProdottoDao = new JDBCProdottoDAO(conn);
                Utente user = (Utente)request.getSession().getAttribute("User");
                List<Prodotto> productList;
                productList = JdbcProdottoDao.getAll();
                    
                /* Da fare controllo su oggetti creati da utente
                if(user != null)
                
                else
                    userLists = new ArrayList<>();
                */

                pageContext.setAttribute("productList", productList);
            %>

            <c:forEach items="${productList}" var="product">
                <productCard:productCard product="${product}"/>
            </c:forEach>
            
        </div>
    </body>
</html>
