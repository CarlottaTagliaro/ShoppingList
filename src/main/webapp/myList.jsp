<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/lists.css" rel="stylesheet" type="text/css"/>
        <link rel="icon" href="favicon.ico" type="image/x-icon"/>
        <%@ taglib uri="/tlds/newList" prefix="newList"%>
        <link href="css/jquery.bootstrap-touchspin.css" rel="stylesheet" type="text/css"/>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <title>Shopping List - List</title>
    </head>
    <body>
        <jsp:include page="menu.jsp"/>
        <script src="JS/jquery.bootstrap-touchspin.js" type="text/javascript"></script>

        <script src="JS/carousel.js" type="text/javascript"></script>
        <script>
            function scendi(bottone) {
                if ($(bottone).find(".scendi").hasClass('glyphicon-chevron-down')) {
                    $(bottone).find(".scendi").removeClass('glyphicon-chevron-down').addClass('glyphicon-chevron-up');
                } else {
                    $(bottone).find(".scendi").removeClass('glyphicon-chevron-up').addClass('glyphicon-chevron-down');
                }
            }

            function shareGetUsers(idLista, qry) {
                var elem = "#table_share_" + idLista;

                $(elem).children().empty();
                $.post("ShareGetUsers", {idLista: idLista, qry: qry}, function (data) {
                    $(elem).append('<tr>' +
                            '<input type="hidden" name="idLista" value="' + idLista +'">' +
                            '<th>Name Surname</th>' +
                            '<th>Add/delete products</th>' +
                            '<th>Modify list details</th>' +
                            '<th>Delete list</th>' +
                            '</tr>');

                    for (var i = 0; i < data.length; i++) {
                        var html = '<tr>' +
                                '<td>' + data[i].Nome + '</td>' +
                                '<td>' +
                                '<input type="checkbox" name="perm_add_rem" value="' + data[i].Email + '" ' + (data[i].perm_add_rem ? 'checked' : '') + '>' +
                                '</td>' +
                                '<td>' +
                                '<input type="checkbox" name="perm_edit" value="' + data[i].Email + '" ' + (data[i].perm_edit ? 'checked' : '') + '>' +
                                '</td>' +
                                '<td>' +
                                '<input type="checkbox" name="perm_del" value="' + data[i].Email + '" ' + (data[i].perm_del ? 'checked' : '') + '>' +
                                '</td>' +
                                '</tr>';
                        $(elem).append(html);
                    }
                });
            }
        </script>

        <script>
            $(document).ready(function () {
                selectMenuEl("myList");
            });
        </script>

        <div class="main">
            <c:if test="${not empty sessionScope.User}">
                <div class="row create addList">
                    <div class="crea-lista">
                        <label class="lista1" style="font-size: 20px;"> Create new List: </label>
                        <button class="myButton" onclick="location.href = 'newList'"> <b> + </b> </button>
                    </div>
                </div>
            </c:if>


            <div class="row">
                <c:forEach items="${userLists}" var="lista">
                    <newList:newList lista="${lista}"/>
                </c:forEach>
            </div>


            <!--<div class="col-md-4 liste">
                <div class="row">
                    <div class="img_wrapper">
                        <img class="immagine_liste" src="http://placehold.it/770x300&text=three">
                        <div class="img_description"> <p class="descrizione" > Qui ci andrà la descrizione dell'immagine. </p> </div>
                    </div>
                </div>
                <div class="row">
                    <div class="panel panel-primary">
                        <div class="panel-heading" id="accordion">
                            <div class="row">
                                <div class ="titolo-lista col-xs-7">
                                    <span class="glyphicon glyphicon-shopping-cart"></span> <b> Nome lista </b> (categoria aksfbasjndak)
                                </div>
                                <div class="btn-group pull-right col-xs-5">
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
                        </div>

            <!-- controllare se devo aggiungere anche la categoria della lista, in più vedere se necessaria la foto dell'oggetto e mettere la spunta per dire che è stato comprato-->
            <!--<div class="collapse" id="collapseOne">
                <div class="panel-body">
                    <ul class="lista">
                        <li class="left clearfix"><span class="list-img pull-left">
                                <img src="http://placehold.it/50/55C1E7/fff&text=T" alt="object" class="img-circle" />
                            </span>
                            <div class="list-body clearfix">
                                <div class="header">
                                    <strong class="primary-font" >Tosaerba</strong> 
                                    <button class="myButton3 addTo" text="+" data-toggle="modal" data-target="#exampleModal"><b>+</b></button>

                                    <button class="myButton3 addTo" title="Delete product" class="btn btn-default btn-xs small">
                                        <span class="glyphicon glyphicon-trash"></span>
                                    </button>
                                </div>
                                <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                    <div class="modal-dialog" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h3 class="modal-title" id="exampleModalLabel"><b>Choose the quantity:</b></h3>
                                            </div>
                                            <div class="modal-body">
                                                <div class="row">
                                                    <div class="col-xs-6 col-sm-4">
                                                        <div class="row"> 
                                                            <div class="amount"><label>Amount:</label></div>
                                                        </div>
                                                        <div class="row">
                                                            <input id="demo3" type="text" value="" name="demo3">
                                                        </div>
                                                        <script>
                                                            $("input[name='demo3']").TouchSpin();
                                                        </script>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class=" btn btn-secondary" data-dismiss="modal"><b>Close</b></button>
                                                <button type="button" class="myButton3 btn btn-primary"> <b>Add</b></button>"
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>-->

        </div>  
    </body>
</html>
