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
        <script>
            $(document).ready(function () {
                var inputs = document.querySelectorAll('.inputfile');
                Array.prototype.forEach.call(inputs, function (input)
                {
                    var label = input.nextElementSibling,
                            labelVal = label.innerHTML;
                    input.addEventListener('change', function (e)
                    {
                        var fileName = '';
                        if (this.files && this.files.length > 1)
                            fileName = (this.getAttribute('data-multiple-caption') || '').replace('{count}', this.files.length);
                        else
                            fileName = e.target.value.split('\\').pop();
                        if (fileName)
                            label.innerHTML = fileName;
                        else
                            label.innerHTML = labelVal;
                    });

                    input.addEventListener('focus', function () {
                        input.classList.add('has-focus');
                    });
                    input.addEventListener('blur', function () {
                        input.classList.remove('has-focus');
                    });
                });
            });
        </script>
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
