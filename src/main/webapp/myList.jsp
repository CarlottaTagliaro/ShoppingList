<%-- 
    Document   : myList
    Created on : 1-lug-2018, 19.14.33
    Author     : weatherly
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <jsp:include page="menu.jsp"/>
        
        <script>
            $(document).ready(function() {
                selectMenuEl("myList"); 
            });   
        </script>
        
        <div class="main">
        </div>
    </body>
</html>
