<%-- 
    Document   : newShop
    Created on : 23-set-2018, 15.02.26
    Author     : weatherly
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/login.css" rel="stylesheet" type="text/css"/>
        <title>Shopping List - Shops</title>
    </head>
    <body>
        <jsp:include page="menu.jsp"/>
        <script src="JS/uploadFile.js" type="text/javascript"></script>
        <div class="main">
            <div class="card">
                <form id="upload_form" enctype="multipart/form-data" method="post">
                    <input type="file" name="file" id="file" class="inputfile" data-multiple-caption="{count} files selected" multiple>
                    <label for="file" class="button1 button2 btn" ><span class="glyphicon glyphicon-open"></span> Choose files </label>
                </form>
                <div class="form-group elemento">
                    <label> Name: </label>
                    <input type="text" id="name" name="name" class="form-control" placeholder="Enter shop name" required autofocus>
                </div>
                <button class="button1" type="submit" ><b>Create</b></button>
                <button class = "button1"  onclick="goBack()"><b>Cancel</b></button>
                <script>
                    function goBack() {
                        window.history.back();
                    }
                </script> 
            </div>
        </div>
    </body>
</html>
