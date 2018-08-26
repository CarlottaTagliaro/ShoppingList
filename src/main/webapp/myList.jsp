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
        <%@ taglib uri="/tlds/newList" prefix="newList"%>
        

        <title>JSP Page</title>
    </head>
    <body>
        <jsp:include page="menu.jsp"/>
        
        <script src="JS/carousel.js" type="text/javascript"></script>
        <script src="JS/changeCollapseButton.js" type="text/javascript"></script>
        
        <script>
            $(document).ready(function() {
                selectMenuEl("myList"); 
            });   
        </script>

        <div class="main">
            <div class="row create addList">
                <label style="font-size: 20px;"> Create new List: </label>
                <button class="myButton" > + </button>
            </div>

            <!-- da capire come fare a mettere più di una foto in un mio tag (ex mettere massimo di foto caricabili e non mettere il required a true)-->
            <!--newList:newList nome="nuova lista" immagine="http://placehold.it/770x300&text=one" categoria="null" />-->
            <div class="row">
                <div class="col-md-4">
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
                                <span class="glyphicon glyphicon-shopping-cart"></span> <b> Nome lista </b>
                                <div class="btn-group pull-right">
                                    <a type="button1 " title="Share list" class="btn btn-default btn-xs small">
                                        <span class="glyphicon glyphicon-share-alt"></span>
                                    </a>
                                    <a type="button1 " title="Delete list" class="btn btn-default btn-xs small">
                                        <span class="glyphicon glyphicon-trash"></span>
                                    </a>
                                    <button type="button" title="Show list" id="prova" class="btn btn-default btn-xs small" data-toggle="collapse" data-parent="#accordion" href="#collapseOne">
                                        <span class=" scendi glyphicon glyphicon-chevron-down"></span>
                                    </button>
                                </div>
                            </div>
                            
                            <!-- controllare se devo aggiungere anche la categoria della lista, in più vedere se necessaria la foto dell'oggetto e mettere la spunta per dire che è stato comprato-->
                            <div class="collapse" id="collapseOne">
                                <div class="panel-body">
                                    <ul class="lista">
                                        <li class="left clearfix"><span class="list-img pull-left">
                                                <img src="http://placehold.it/50/55C1E7/fff&text=T" alt="object" class="img-circle" />
                                            </span>
                                            <div class="list-body clearfix">
                                                <div class="header">
                                                    <strong class="primary-font" >Tosaerba</strong> 
                                                </div>
                                            </div>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>   
        </div>
    </body>
</html>
