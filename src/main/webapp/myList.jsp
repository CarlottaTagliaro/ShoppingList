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
        <script> 
            $(document).ready(function () {
                $("#prova").click(function () {
                if ($(this).find(".scendi").hasClass('glyphicon-chevron-down')) {
                    $(this).find(".scendi").removeClass('glyphicon-chevron-down').addClass('glyphicon-chevron-up');
                } else {
                    $(this).find(".scendi").removeClass('glyphicon-chevron-up').addClass('glyphicon-chevron-down');
                }
            }); 
        });</script>
        
        <script>
            $(document).ready(function() {
                selectMenuEl("myList"); 
            });   
        </script>

        <div class="main">
            <div class="row create addList">
                <div class="crea-lista">
                    <label class="lista1" style="font-size: 20px;"> Create new List: </label>
                    <button class="myButton" onclick="location.href='newList.jsp'"> <b> + </b> </button>
                </div>
            </div>

            <!-- da capire come fare a mettere più di una foto in un mio tag (ex mettere massimo di foto caricabili e non mettere il required a true)-->
            <!--newList:newList nome="nuova lista" immagine="http://placehold.it/770x300&text=one" categoria="null" />-->
            <div class="row">
                <div class="col-md-4 liste">
                    <div class="row">
                        <div class="img_wrapper">
                            <img class="immagine_liste" src="http://placehold.it/770x300&text=three">
                            <div class="img_description"> <p class="descrizione" > Qui ci andrà la descrizione dell'immagine. </p> </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="panel panel-primary">
                            <div class="panel-heading" id="accordion">
                                <span class="glyphicon glyphicon-shopping-cart"></span> <b> Nome lista </b> (categoria)
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
                <newList:newList nome="Tosaerba" categoria="Giardinaggio" descrizione="questo è un tosaerba bellissimo" immagine="https://www.bricoman.it/media/foto_articoli/2018/02/10058208_LR_PRO_V01_2018_02_1_171605.JPG" />

            </div>   
        </div>
    </body>
</html>
