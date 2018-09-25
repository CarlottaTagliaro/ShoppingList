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
        <link href="css/jquery.bootstrap-touchspin.css" rel="stylesheet" type="text/css"/>
        <title>JSP Page</title>

        <%@ taglib uri="/tlds/productCard" prefix="productCard"%>
    </head>
    <body>
        <jsp:include page="menu.jsp"/>

        <script src="JS/jquery.bootstrap-touchspin.js" type="text/javascript"></script>
        <script>
            $(document).ready(function () {
                selectMenuEl("home");
            });
        </script>

        <div class="main">
            <div class="row search-form">
                <div class="col-sm-7">
                    <div class="ordering-form">
                        <label class="search"> Order by: </label>
                        <select class="form-control selezione" id="search-select">
                            <option value="byName">name</option>
                            <option value="byShop">shop</option>
                        </select>
                    </div>
                </div>
                <div class="col-sm-5">
                    <div class="input-group">
                        <input type="text" class="form-control form-control1" aria-label="..." placeholder="Search product">
                        <div class="input-group-btn">
                            <button type="button" class="btn bottone-cerca btn-default">
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
                        <div class="add-lista">
                            <label class="aggiungi"> Add: </label>
                            <button class="myButton" text="+" data-toggle="modal" data-target="#exampleModal"><b>+</b></button>
                        </div>
                        <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h3 class="modal-title" id="exampleModalLabel"><b>Choose the list:</b></h3>
                                    </div>
                                    <div class="modal-body">
                                        <div class="row">
                                            <div class="col-xs-12 col-sm-4 scegliLista">
                                                <select class="form-control" id="search-select1">
                                                    <option value="Pet shop">Pet shop</option>
                                                    <option value="Super Market">Super Market</option>
                                                </select>
                                            </div>
                                            <div class="col-xs-6 col-sm-4">
                                                <div class="text-info"> Quantità presente: </div>
                                            </div>
                                            <div class="col-xs-6 col-sm-4">
                                                <div class="amount"><label>Amount:</label></div>
                                                <input id="demo3" type="text" value="" name="demo3">
                                                <script>
                                                    $("input[name='demo3']").TouchSpin();
                                                </script>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class=" btn btn-secondary" data-dismiss="modal">Close</button>
                                        <button type="button" class="myButton3 btn btn-primary"> Add</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <productCard:productCard nome="Tosaerba" categoria="Giardinaggio" descrizione="questo è un tosaerba bellissimo" immagine="https://www.bricoman.it/media/foto_articoli/2018/02/10058208_LR_PRO_V01_2018_02_1_171605.JPG" />
            <productCard:productCard nome="Tosaerba" categoria="Giardinaggio" descrizione="questo è un tosaerba bellissimo" immagine="https://www.bricoman.it/media/foto_articoli/2018/02/10058208_LR_PRO_V01_2018_02_1_171605.JPG" />
            <productCard:productCard nome="Tosaerba" categoria="Giardinaggio" descrizione="questo è un tosaerba bellissimo" immagine="https://www.bricoman.it/media/foto_articoli/2018/02/10058208_LR_PRO_V01_2018_02_1_171605.JPG" />
            <productCard:productCard nome="Tosaerba" categoria="Giardinaggio" descrizione="questo è un tosaerba bellissimo" immagine="https://www.bricoman.it/media/foto_articoli/2018/02/10058208_LR_PRO_V01_2018_02_1_171605.JPG" />

        </div>
    </body>
</html>
