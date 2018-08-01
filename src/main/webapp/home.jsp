<%-- 
    Document   : home
    Created on : 22-giu-2018, 14.28.49
    Author     : weatherly
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/home_css.css" rel="stylesheet" type="text/css"/>
        <title>JSP Page</title>
        
        <%@ taglib uri="/tlds/productCard" prefix="productCard"%>
    </head>
    <body>
        <jsp:include page="menu.jsp"/>
        
        <script>
            $(document).ready(function() {
                selectMenuEl("home"); 
            });   
        </script>

        <div class="main">
            <div class="row search-form">
                <div class="col-sm-7">
                    <label class="search"> Order by: </label>
                    <select class="form-control" id="search-select">
                        <option value="byName">name</option>
                        <option value="byShop">shop</option>
                    </select>
                </div>
                <div class="col-sm-5">
                    <div class="input-group">
                        <input type="text" class="form-control" aria-label="..." placeholder="Search product">
                        <div class="input-group-btn">
                            <button type="button" class="btn btn-default">
                                <span class="glyphicon glyphicon-search"/>
                            </button>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row card">
                <div class="col-xs-3">
                    <img class="imageList img-responsive" src="https://www.bricoman.it/media/foto_articoli/2018/02/10058208_LR_PRO_V01_2018_02_1_171605.JPG"/>
                </div>
                <div class="col-xs-6">
                    <h4> <b> Tosaerba </b></h4>
                    <h6> Giardinaggio </h6>
                    <p> questo è un tosaerba bellissimo </p>
                </div>

                <div class="col-xs-3 myColumn">
                    <div>
                        <label> Add to list: </label>
                        <button class="myButton" text="+">+</button>
                    </div>
                    <div>
                        <input type="checkbox" value="true" disabled="true"> Already in a list
                    </div>
                </div>
            </div>
            
            <productCard:productCard nome="Tosaerba" categoria="Giardinaggio" descrizione="questo è un tosaerba bellissimo" immagine="https://www.bricoman.it/media/foto_articoli/2018/02/10058208_LR_PRO_V01_2018_02_1_171605.JPG" />
        </div>
    </body>
</html>
