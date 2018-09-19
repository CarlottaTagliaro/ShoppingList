<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="//code.jquery.com/jquery-3.3.1.min.js"></script>
        <!--<script src="JQUERY/jquery.min.js" type="text/javascript"></script>-->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" crossorigin="anonymous">
        <!--<link href="Bootsrap/bootstrap.min.css" rel="stylesheet" type="text/css"/>-->
        <script src="//netdna.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
        <!--<script src="Bootsrap/bootstrap.min.js" type="text/javascript"></script>-->
        <link href="css/menu_css.css" rel="stylesheet" type="text/css"/>
        <script src="JS/js_menu.js" type="text/javascript"></script>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <title>JSP Page</title>
    </head>
    <body>
        <div class="title">     
            <img class="img-responsive banner" src="images/Senzanome.png" alt=""/>
        </div>

        <nav class="navbar navbar-inverse sidebar" role="navigation">
            <div class="container-fluid">
                <!-- Brand and toggle get grouped for better mobile display -->
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-sidebar-navbar-collapse-1">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                </div>
                <!-- Collect the nav links, forms, and other content for toggling -->
                <div class="collapse navbar-collapse" id="bs-sidebar-navbar-collapse-1">
                    <ul class="nav navbar-nav">
                        <li data="home"><a href="home.jsp">Home<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-home"></span></a></li>
                        <li data="shops"><a href="shops.jsp">Shops<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-usd"></span></a></li>
                        <li data="myList"><a href="myList.jsp">My Lists<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-shopping-cart"></span></a></li>
                        <c:if test="${not empty sessionScope.User}">
                            <li data="myProducts"><a href="myProducts.jsp">My Products<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-pencil"></span></a></li>
                            <li data="messages"><a href="messages.jsp">Messages<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-comment"></span></a></li>
                            <li data="profile"><a href="profile.jsp">Profile<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-user"></span></a></li>
                            <li data="logout"><a href="<%=request.getContextPath()%>/LogoutServlet">Log out<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-log-out"></span></a></li>
                        </c:if>
                        <c:if test="${empty sessionScope.User}">
                            <li data="login"><a href="login.jsp">Sign In<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-cog"></span></a></li>
                        </c:if>
                    </ul>
                </div>
            </div>
        </nav>
    </body>
</html>