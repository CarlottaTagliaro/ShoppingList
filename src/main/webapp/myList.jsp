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
        <link href="css/lists.css" rel="stylesheet" type="text/css"/>
        <script src="JS/carousel.js" type="text/javascript"></script>
        
        <%@ taglib uri="/tlds/newList" prefix="newList"%>
        
        
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
            <div class="row create">
                <label style="font-size: 20px;"> Create new List: </label>
                <button class="myButton" > + </button>
            </div>
            <div class="carousel slide newLista" id="myCarousel">
                <!-- Carousel items -->
                <div class="carousel-inner">
                    <div class="active item" data-slide-number="0">
                        <img src="http://placehold.it/770x300&text=one">
                        <div class="carousel-caption">
                            <h3>Qui andrà il nome della lista</h3>
                        </div>
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
                <a class="left carousel-control" href="#myCarousel" data-slide="prev">
                    <span class="glyphicon glyphicon-chevron-left"></span>  
                    <span class="sr-only">Previous</span>
                </a>
                <a class="right carousel-control" href="#myCarousel" data-slide="next">
                    <span class="glyphicon glyphicon-chevron-right"></span>
                    <span class="sr-only">Next</span>
                </a>                                 
            </div>
            <!-- da capire come fare a mettere più di una foto in un mio tag (ex mettere massimo di foto caricabili e non mettere il required a true)-->
            <newList:newList nome="nuova lista" immagine="http://placehold.it/770x300&text=one" categoria="null" />
        </div>
    </body>
</html>
