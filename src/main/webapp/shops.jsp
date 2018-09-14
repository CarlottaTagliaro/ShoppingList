<%-- 
    Document   : lists
    Created on : 15-ago-2018, 18.45.07
    Author     : weatherly
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="css/lists.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <jsp:include page="menu.jsp"/>
        
        <script>
            $(document).ready(function() {
                selectMenuEl("shops"); 
            });   
        </script>

        <div class="main">
            <div class="row">
                <div class="col-md-4 liste">
                    <div class="row">
                        <div class="carousel slide newLista" id="myCarousel1">
                            <!-- Carousel items -->
                            <div class="carousel-inner">
                                <div class="active item" data-slide-number="0">
                                    <img src="http://placehold.it/770x300&text=one">
                                </div>
                                <div class="item" data-slide-number="1">
                                    <img src="http://placehold.it/770x300&text=two"></div>
                                <div class="item" data-slide-number="2">
                                    <img src="http://placehold.it/770x300&text=three"></div>
                                <div class="item" data-slide-number="3">
                                    <img src="http://placehold.it/770x300&text=four"></div>
                                <div class="item" data-slide-number="4">
                                    <img src="http://placehold.it/770x300&text=five"></div>
                                <div class="item" data-slide-number="5">
                                    <img src="http://placehold.it/770x300&text=six"></div>
                            </div><!-- Carousel nav -->
                            <a class="left carousel-control" href="#myCarousel1" data-slide="prev">
                                <span class="glyphicon glyphicon-chevron-left"></span>  
                                <span class="sr-only">Previous</span>
                            </a>
                            <a class="right carousel-control" href="#myCarousel1" data-slide="next">
                                <span class="glyphicon glyphicon-chevron-right"></span>
                                <span class="sr-only">Next</span>
                            </a>                                 
                        </div>
                    </div>
                    <div class="row">
                        <div class="panel panel-primary">
                            <div class="panel-heading" id="accordion">
                                <span class="glyphicon glyphicon-usd"></span> <a class="scegli_negozio" href="shopCategories.jsp"> <b> Nome negozio</b></a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
