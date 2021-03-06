<%-- 
    Document   : messages
    Created on : 1-lug-2018, 19.16.00
    Author     : weatherly
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/message.css" rel="stylesheet" type="text/css"/>
        <link rel="icon" href="favicon.ico" type="image/x-icon"/>
        <title>Shopping List - Chat</title>
    </head>
    <body>
        <jsp:include page="menu.jsp"/>
        
        <script src="JS/chat.js" type="text/javascript"></script>
        <script>
            $(document).ready(function() {
                selectMenuEl("messages"); 
                
                $("#collapse-btn").click(function () {
                    if ($(this).find(".scendi").hasClass('glyphicon-chevron-down')) {
                        $(this).find(".scendi").removeClass('glyphicon-chevron-down').addClass('glyphicon-chevron-up');
                    } else {
                        $(this).find(".scendi").removeClass('glyphicon-chevron-up').addClass('glyphicon-chevron-down');
                    }
                });                 
            });   
        </script>
        <div class="main">
            <div class="row">
                <div class="col-md-4 selectChat">
                    <div class="panel panel-primary">
                        <div class="panel-heading" id="accordion">
                            <span class="glyphicon glyphicon-user"></span> <b>Select chat</b>
                            <div class="btn-group pull-right">
                                <a type="button" id="collapse-btn" class="btn btn-default btn-xs" data-toggle="collapse" data-parent="#accordion" href="#collapseOne">
                                    <span class="scendi glyphicon glyphicon-chevron-up"></span>
                                </a>
                            </div>
                        </div>
                        <div class="panel-collapse in" id="collapseOne">
                            <div class="panel-body pannello-utenti">
                                <ul id="chat-panel" class="chat">
                                    <!--<li class="left clearfix"><span class="chat-img pull-left">
                                            <img src="http://placehold.it/50/55C1E7/fff&text=U" alt="User Avatar" class="img-circle" />
                                        </span>
                                        <div class="chat-body clearfix">
                                            <div class="header">
                                                <strong class="primary-font" >Jack Sparrow</strong> 
                                            </div>
                                        </div>
                                    </li>-->
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-8 chat">
                    <div class="panel panel-primary">
                        <div class="panel-heading" id="accordion">
                            <span class="glyphicon glyphicon-comment"></span> <b>Chat</b>
                        </div>
                        <div class="panel-collapse" id="collapseOne">
                            <div class="panel-body">
                                <ul id="chat-messages" class="chat">
                                    <!--<li class="left clearfix"><span class="chat-img pull-left">
                                            <img src="http://placehold.it/50/55C1E7/fff&text=U" alt="User Avatar" class="img-circle" />
                                        </span>
                                        <div class="chat-body clearfix">
                                            <div class="header">
                                                <strong class="primary-font">Jack Sparrow</strong> <small class="pull-right text-muted">
                                                    <span class="glyphicon glyphicon-time"></span>12 mins ago</small>
                                            </div>
                                            <p>
                                                Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur bibendum ornare
                                                dolor, quis ullamcorper ligula sodales.
                                            </p>
                                        </div>
                                    </li>
                                    <li class="right clearfix"><span class="chat-img pull-right">
                                            <img src="http://placehold.it/50/FA6F57/fff&text=ME" alt="User Avatar" class="img-circle" />
                                        </span>
                                        <div class="chat-body clearfix">
                                            <div class="header">
                                                <small class=" text-muted"><span class="glyphicon glyphicon-time"></span>13 mins ago</small>
                                                <strong class="pull-right primary-font">Bhaumik Patel</strong>
                                            </div>
                                            <p>
                                                Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur bibendum ornare
                                                dolor, quis ullamcorper ligula sodales.
                                            </p>
                                        </div>
                                    </li>
                                    <li class="left clearfix"><span class="chat-img pull-left">
                                            <img src="http://placehold.it/50/55C1E7/fff&text=U" alt="User Avatar" class="img-circle" />
                                        </span>
                                        <div class="chat-body clearfix">
                                            <div class="header">
                                                <strong class="primary-font">Jack Sparrow</strong> <small class="pull-right text-muted">
                                                    <span class="glyphicon glyphicon-time"></span>14 mins ago</small>
                                            </div>
                                            <p>
                                                Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur bibendum ornare
                                                dolor, quis ullamcorper ligula sodales.
                                            </p>
                                        </div>
                                    </li>
                                    <li class="right clearfix"><span class="chat-img pull-right">
                                            <img src="http://placehold.it/50/FA6F57/fff&text=ME" alt="User Avatar" class="img-circle" />
                                        </span>
                                        <div class="chat-body clearfix">
                                            <div class="header">
                                                <small class=" text-muted"><span class="glyphicon glyphicon-time"></span>15 mins ago</small>
                                                <strong class="pull-right primary-font">Bhaumik Patel</strong>
                                            </div>
                                            <p>
                                                Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur bibendum ornare
                                                dolor, quis ullamcorper ligula sodales.
                                            </p>
                                        </div>
                                    </li>-->
                                </ul>
                            </div>
                            <div class="panel-footer">
                                <div class="input-group">
                                    <div class="input-group-btn dropup">
                                         <button class="btn btn-default dropdown-toggle" type="button" id="suggestions-drop" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                             <b>Preset messages</b>
                                            <span class="caret"></span>
                                        </button>
                                        <ul class="dropdown-menu" id="suggestions-btns" aria-labelledby="suggestions-drop">

                                        </ul>
                                    </div>
                                    <input id="msg-text" type="text" class="form-control" placeholder="Type your message here..." />
                                    <span class="input-group-btn">
                                        <button class="btn btn-warning" id="btn-chat" onclick="sendMessage()">
                                            <b> Send </b>
                                        </button>
                                    </span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
