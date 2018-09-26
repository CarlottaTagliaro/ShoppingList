<%-- 
    Document   : myProducts
    Created on : 1-lug-2018, 15.10.20
    Author     : weatherly
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="it.webproject2018.db.entities.Prodotto"%>
<%@page import="java.util.List"%>
<%@page import="it.webproject2018.db.entities.Utente"%>
<%@page import="it.webproject2018.db.daos.jdbc.JDBCProdottoDAO"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/myProducts.css" rel="stylesheet" type="text/css"/>
        <title>JSP Page</title>
        
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
                    <button class="myButton2" onclick="location.href='newProduct.jsp'"> <b> + </b> </button>
                </div>
            </div>
        
            
            <%                    
                Connection conn = (Connection) super.getServletContext().getAttribute("connection");
                JDBCProdottoDAO JdbcProdottoDao = new JDBCProdottoDAO(conn);
                Utente user = (Utente)request.getSession().getAttribute("User");
                List<Prodotto> productList;
                    
                if(user != null)
                    productList = JdbcProdottoDao.getUserProducts(user.getEmail());                
                else
                    productList = new ArrayList<>();
                

                pageContext.setAttribute("productList", productList);
            %>

            <c:forEach items="${productList}" var="product">
                <productCard:productCard product="${product}"/>
            </c:forEach>
        </div>
    </body>
</html>
