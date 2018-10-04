<%-- 
    Document   : prodotti
    Created on : 19-set-2018, 9.38.26
    Author     : weatherly
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/home_css.css" rel="stylesheet" type="text/css"/>
        <title>Shopping List - Shops</title>
        
        <%@ taglib uri="/tlds/productCard" prefix="productCard"%>
    </head>
    <body>
        <jsp:include page="menu.jsp"/>
        
        <script>
            $(document).ready(function() {
                selectMenuEl("shops"); 
            });   
        </script>

        <div class="main">
            <div class="row search-form">
                    <div class="input-group cerca">
                        <input type="text" class="form-control form-control1" aria-label="..." placeholder="Search product">
                        <div class="input-group-btn">
                            <button type="button" class="btn bottone-cerca btn-default">
                                <span class="glyphicon glyphicon-search"/>
                            </button>
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
                        <button class="myButton" text="+" data-toggle="modal" data-target="#exampleModal">+</button>

                        <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h3 class="modal-title" id="exampleModalLabel"><b>Choose the list:</b></h3>
                                    </div>
                                    <div class="modal-body">
                                        <select class="form-control" id="search-select">
                                            <option value="Pet shop">Pet shop</option>
                                            <option value="Super Market">Super Market</option>
                                        </select>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                        <button type="button" class="btn btn-primary">Add</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div>
                            <input type="checkbox" value="true" disabled="true"> Already in a list
                        </div>
                    </div>
                </div>
            </div>

            <productCard:productCard nome="Tosaerba" categoria="Giardinaggio" descrizione="questo è un tosaerba bellissimo" immagine="https://www.bricoman.it/media/foto_articoli/2018/02/10058208_LR_PRO_V01_2018_02_1_171605.JPG" />
            <productCard:productCard nome="Tosaerba" categoria="Giardinaggio" descrizione="questo è un tosaerba bellissimo" immagine="https://www.bricoman.it/media/foto_articoli/2018/02/10058208_LR_PRO_V01_2018_02_1_171605.JPG" />
            <productCard:productCard nome="Tosaerba" categoria="Giardinaggio" descrizione="questo è un tosaerba bellissimo" immagine="https://www.bricoman.it/media/foto_articoli/2018/02/10058208_LR_PRO_V01_2018_02_1_171605.JPG" />

        </div>
    </body>
</html>
