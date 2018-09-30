<%-- 
    Document   : newProduct
    Created on : 1-lug-2018, 18.16.43
    Author     : weatherly
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import="java.util.ArrayList"%>
<%@page import="it.webproject2018.db.entities.CategoriaProdotti"%>
<%@page import="java.util.List"%>
<%@page import="java.lang.String"%>
<%@page import="it.webproject2018.db.entities.Utente"%>
<%@page import="it.webproject2018.db.daos.jdbc.JDBCCategoriaProdottiDAO"%>
<%@page import="java.sql.Connection"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/login.css" rel="stylesheet" type="text/css"/>
        <title>Shopping List - Product</title>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    </head>

    <body>
        <jsp:include page="menu.jsp" />

        <%
            Connection conn = (Connection) super.getServletContext().getAttribute("connection");
            JDBCCategoriaProdottiDAO CategoriaProdottiDAO = new JDBCCategoriaProdottiDAO(conn);
            Utente user = (Utente) request.getSession().getAttribute("User");
            List<String> catProductList = new ArrayList<>();

            if (user != null) {
                catProductList = CategoriaProdottiDAO.getAllNames();
            }

            pageContext.setAttribute("CatProductList", catProductList);
        %>

        <div class="main">
            <div class="card">
                <form action="CreateProductServlet" method="POST">
                    <img class="logo" alt="Qui ci sarà la feature di caricamento" width="128" height="128">
                    <div class="form-group elemento">
                        <label> Name: </label>
                        <input type="text" id="name" name="name" class="form-control" placeholder="Enter product name"
                               required autofocus>
                    </div>
                    <div class="form-group elemento">
                        <label> Category: </label>
                        <select class="form-control" name="selectCategory" id="category-select">
                            <c:forEach items="${CatProductList}" var="catproduct">
                                <option value="${catproduct}">${catproduct}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label> Description: </label>
                        <textarea id="description" name="description" cols="40" rows="5" class="form-control descrizione"
                                  placeholder="Enter product description" required></textarea>
                    </div>
                    <button class="button1" type="submit"><b>Create</b></button>
                    <button class="button1" onclick="goBack()"><b>Cancel</b></button>
                </form>
                <script>
                    function goBack() {
                        window.history.back();
                    }
                </script>
            </div>
        </div>
    </body>
</html>
