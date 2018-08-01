<%-- 
    Document   : profile
    Created on : 1-lug-2018, 10.45.35
    Author     : weatherly
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/profile.css" rel="stylesheet" type="text/css"/>
        <!--<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">-->
        <title>JSP Page</title>
    </head>
    <body>
        <jsp:include page="menu.jsp"/>
        
        <script>
            $(document).ready(function() {
                selectMenuEl("profile"); 
            });   
        </script>
        
        <div class="main">
            <div class="card">
                <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/b/b7/John_Travolta_Deauville_2013_2.jpg/220px-John_Travolta_Deauville_2013_2.jpg" alt="John">
                <h1>John Doe</h1>
                <p class="titleNew">Email: John.Doe@gmail.com </p>
                <p><button >Change profile pic</button></p>
            </div>
        </div>
    </body>
</html>
