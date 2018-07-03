<%-- 
    Document   : menu
    Created on : 22-giu-2018, 14.08.14
    Author     : weatherly
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="//code.jquery.com/jquery-3.3.1.min.js"></script>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" crossorigin="anonymous">
        <script src="//netdna.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
        <link href="css/menu_css.css" rel="stylesheet" type="text/css"/>
        <script src="JS/js_menu.js" type="text/javascript"></script>
        <title>JSP Page</title>
    </head>
    <body>
        <div class="title">            
            <h1>Shopping List 
                <img src="http://www.settenews.net/wp-content/uploads/2016/08/tmp_25920-Screenshot_2016-08-16-07-03-43-1-564085934.png" height="30px"/>
            </h1>
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
                        <li class="active"><a href="home.jsp">Home<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-home"></span></a></li>
                        <li ><a href="#">Shops<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-usd"></span></a></li>
                        <li ><a href="myList.jsp">My Lists<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-shopping-cart"></span></a></li>
                        <li ><a href="myProducts.jsp">My Products<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-pencil"></span></a></li>
                        <li ><a href="messages.jsp">Messages<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-comment"></span></a></li>
                        <li ><a href="profile.jsp">Profile<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-user"></span></a></li>
                        <li ><a href="login.jsp">Sign In<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-cog"></span></a></li>
                    </ul>
                </div>
            </div>
        </nav>
    </body>
</html>
