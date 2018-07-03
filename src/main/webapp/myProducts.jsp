<%-- 
    Document   : myProducts
    Created on : 1-lug-2018, 15.10.20
    Author     : weatherly
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/myProducts.css" rel="stylesheet" type="text/css"/>
        <title>JSP Page</title>
    </head>
    <body>
        <jsp:include page="menu.jsp"/>
        <div class="main">
            <div class="row create">
                <label style="font-size: 20px;"> Create new product: </label>
                <button class="myButton2" onclick="location.href='newProduct.jsp'"> + </button>
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
        </div>
    </body>
</html>
