<%-- 
    Document   : shopCategories
    Created on : 14-set-2018, 15.50.19
    Author     : weatherly
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/lists.css" rel="stylesheet" type="text/css"/>
        
        <link rel="icon" href="favicon.ico" type="image/x-icon"/>
        
        <%@ taglib uri="/tlds/shopCategoriesCard" prefix="shopCategoriesCard"%>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        

        <title>Shopping List - Shops</title>
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
                
                <c:forEach items="${categories}" var="cat">
                    <shopCategoriesCard:shopCategoriesCard category="${cat}"/>
                </c:forEach>
                
                <!--
                <div class="col-md-4 liste">
                    <div class="row">
                        <div class="img_wrapper">
                            <img class="immagine_liste" src="http://placehold.it/770x300&text=three">
                            <div class="img_description"> <p class="descrizione" > Qui ci andrà la descrizione della categoria di prodotti. </p> </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="panel panel-primary">
                            <div class="panel-heading" id="accordion">
                                <span class="glyphicon glyphicon-shopping-cart"></span> <a class="scegli_categoria" href="prodotti.jsp"> <b> Nome categoria</b></a>
                            </div>
                        </div>
                    </div>
                </div>-->
            </div>   
        </div>
    </body>
</html>
