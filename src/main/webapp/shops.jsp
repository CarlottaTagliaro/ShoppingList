<%@page import="it.webproject2018.db.entities.CategoriaListe"%>
<%@page import="java.util.List"%>
<%@page import="it.webproject2018.db.entities.Utente"%>
<%@page import="it.webproject2018.db.daos.jdbc.JDBCCategoriaListeDAO"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="css/lists.css" rel="stylesheet" type="text/css"/>
        <%@ taglib uri="/tlds/shopCard" prefix="shopCard"%>
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
            <c:if test="${sessionScope.User.isAdmin}">
                <div class="row create addShop">
                    <div class="crea-shop">
                        <label class="shop1" style="font-size: 20px;"> Create new shop: </label>
                        <button class="myButton" onclick="location.href='newShop.jsp'"> <b> + </b> </button>
                    </div>
                </div>
            </c:if>
            
            
                <%                    
                    Connection conn = (Connection) super.getServletContext().getAttribute("connection");
                    JDBCCategoriaListeDAO JdbcCategoriaListeDao = new JDBCCategoriaListeDAO(conn);
                    List<CategoriaListe> shops = JdbcCategoriaListeDao.getAll();
                    
                    pageContext.setAttribute("shops", shops);
                %>

                
                          
                <c:forEach items="${shops}" var="shop">
                    <shopCard:shopCard shop="${shop}"/>
                </c:forEach>
        </div>
    </body>
</html>
