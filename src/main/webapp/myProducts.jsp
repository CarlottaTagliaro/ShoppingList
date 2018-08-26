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
        <%@ taglib uri="/tlds/productCard" prefix="productCard"%>
    </head>
    <body>
        <jsp:include page="menu.jsp"/>
        
        <script>
            $(document).ready(function() {
                selectMenuEl("myProducts"); 
            });   
        </script>
        
        <div class="main">
            <div class="row create">
                <div class="crea-prodotto">
                    <label class="crea" style="font-size: 20px;"> Create new product: </label>
                    <button class="myButton2" onclick="location.href='newProduct.jsp'"> <b> + </b> </button>
                </div>
            </div>
            <productCard:productCard nome="Tosaerba" categoria="Giardinaggio" descrizione="questo è un tosaerba bellissimo" immagine="https://www.bricoman.it/media/foto_articoli/2018/02/10058208_LR_PRO_V01_2018_02_1_171605.JPG" />
        </div>
    </body>
</html>
