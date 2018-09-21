<%@page import="java.util.ArrayList"%>
<%@page import="it.webproject2018.db.entities.Utente"%>
<%@page import="java.util.List"%>
<%@page import="it.webproject2018.db.entities.Lista"%>
<%@page import="java.sql.Connection"%>
<%@page import="it.webproject2018.db.daos.jdbc.JDBCListaDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/lists.css" rel="stylesheet" type="text/css"/>
        <%@ taglib uri="/tlds/newList" prefix="newList"%>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <title>JSP Page</title>
    </head>
    <body>
        <jsp:include page="menu.jsp"/>

        <script src="JS/carousel.js" type="text/javascript"></script>
        <script>
            function scendi(bottone) {
                if ($(bottone).find(".scendi").hasClass('glyphicon-chevron-down')) {
                    $(bottone).find(".scendi").removeClass('glyphicon-chevron-down').addClass('glyphicon-chevron-up');
                } else {
                    $(bottone).find(".scendi").removeClass('glyphicon-chevron-up').addClass('glyphicon-chevron-down');
                }
            }
        </script>

        <script>
            $(document).ready(function () {
                selectMenuEl("myList");
            });
        </script>

        <div class="main">
            <c:if test="${not empty sessionScope.User}">
                <div class="row create addList">
                    <div class="crea-lista">
                        <label class="lista1" style="font-size: 20px;"> Create new List: </label>
                        <button class="myButton" onclick="location.href='newList.jsp'"> <b> + </b> </button>
                    </div>
                </div>
            </c:if>

                
                <%                    
                    Connection conn = (Connection) super.getServletContext().getAttribute("connection");
                    JDBCListaDAO JdbcListaDao = new JDBCListaDAO(conn);
                    Utente user = (Utente)request.getSession().getAttribute("User");
                    List<Lista> userLists;
                    if(user != null)
                        userLists = JdbcListaDao.getUserLists(user.getEmail());
                    else
                        userLists = new ArrayList<>();
                    
                    pageContext.setAttribute("userLists", userLists);
                %>

                
                <c:forEach items="${userLists}" var="lista">
                    <newList:newList lista="${lista}"/>
                </c:forEach>

            </div>   
        </div>
    </body>
</html>
