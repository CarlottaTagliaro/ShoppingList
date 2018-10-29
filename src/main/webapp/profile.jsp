<%-- 
    Document   : profile
    Created on : 1-lug-2018, 10.45.35
    Author     : weatherly
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="css/profile.css" rel="stylesheet" type="text/css" />
    <link href="css/home_css.css" rel="stylesheet" type="text/css" />

    <link rel="icon" href="favicon.ico" type="image/x-icon" />
    <title>Shopping List - Profile</title>
</head>

<body>
    <jsp:include page="menu.jsp" />

    <script>
        $(document).ready(function () {
            selectMenuEl("profile");
        });
    </script>

    <div class="main">
        <div class="card">
            <img src="${user.getPicture()}" alt="${user.getName()}" style="display: inline;" class="img-responsive">
            <h1>${user.getName()} ${user.getSurname()}</h1>
            <p class="titleNew">Email: ${user.getEmail()} </p>
            <div style="height:0px;overflow:hidden">
                <input type="file" id="fileInput" name="fileInput" />
            </div>
            <form id="form_change_pic" action="ChangeUserPicServlet" enctype="multipart/form-data" method="POST">
                <input type="file" id="file" name="file" onchange="chooseFile()" data-toggle="modal" class="inputfile">
                <label for="file" class="button1">
                    <span class="glyphicon glyphicon-open"></span>Change profile pic
                </label>
            </form>

            <script>
                function chooseFile() {
                    $("#form_change_pic").submit();
                }
            </script>

            <p><button class="button1" style="margin: 0" data-toggle="modal" data-target="#change-cred"> <b> Change
                        details </b> </button></p>
            <form action="ChangeUserDetailsServlet" method="POST">
                <div class="modal fade" id="change-cred" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
                    aria-hidden="true">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h3 class="modal-title" id="exampleModalLabel"><b>Change credentials:</b></h3>
                            </div>
                            <div class="modal-body">
                                <div class="input-group">
                                    <div class="row cambia-dati">
                                        <label class="search">Name:</label>
                                        <input type="text" name="name" class="form-control inserisci" aria-label="..."
                                            placeholder="Insert new name">
                                    </div>

                                    <div class="row cambia-dati">
                                        <label class="search">Surname:</label>
                                        <input type="text" name="surname" class="form-control inserisci" aria-label="..."
                                            placeholder="Insert new surname">
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="button1" class="modal-btn btn btn-secondary" data-dismiss="modal"> <b>Close</b></button>
                                <button type="submit" clicked="false" class="modal-btn btn btn-primary myButton3"><b>Change</b></button>
                            </div>
                        </div>
                    </div>
                </div>
            </form>

            <p><button class="button1" style="margin: 0" data-toggle="modal" data-target="#change-pwd"> <b> Change
                        Password </b> </button></p>
            <div class="modal fade" id="change-pwd" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
                aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h3 class="modal-title" id="exampleModalLabel"><b>Change credentials:</b></h3>
                        </div>
                        <form action="ChangeUserPassword" method="POST">
                            <div class="modal-body">
                                <div class="input-group">
                                    <div class="row cambia-dati">
                                        <label class="search">New Password:</label>
                                        <input type="password" id="passwordNew" name="NewPassword" class="form-control inserisci"
                                            aria-label="..." placeholder="Insert new password">
                                    </div>
                                    <div class="row cambia-dati">
                                        <label class="search">Confirm Password:</label>
                                        <input type="password" id="passwordConfirm" name="ConfirmPassword" class="form-control inserisci"
                                            aria-label="..." placeholder="Confirm password">
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="button1" class="modal-btn btn btn-secondary" data-dismiss="modal"> <b>Close</b></button>
                                <button type="submit" class="modal-btn btn btn-primary myButton3"><b>Change</b></button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    </div>
</body>

</html>