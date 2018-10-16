/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function htmlbodyHeightUpdate() {
    var height3 = $(window).height()
    var height1 = $('.nav').height() + 50
    height2 = $('.main').height()
    if (height2 > height3) {
        $('html').height(Math.max(height1, height3, height2) + 10);
        $('body').height(Math.max(height1, height3, height2) + 10);
    } else
    {
        $('html').height(Math.max(height1, height3, height2));
        $('body').height(Math.max(height1, height3, height2));
    }

}
$(document).ready(function () {
    htmlbodyHeightUpdate()
    $(window).resize(function () {
        htmlbodyHeightUpdate()
    });
    $(window).scroll(function () {
        height2 = $('.main').height()
        htmlbodyHeightUpdate()
    });
});


function selectMenuEl(dataId) {
    //reset all menu active state
    $(".navbar-nav li").removeClass("active");

    //enable only the active menu element
    $(".navbar-nav li[data='" + dataId + "']").addClass("active");
}



function shareProductGetUsers(idProduct, qry) {
    var elem = "#table_shareprod_" + idProduct;

    $(elem).children().empty();
    $.post("ShareProductGetUsers", {idProdotto: idProduct, qry: qry}, function (data) {
        $(elem).append('<tr>' +
                '<input type="hidden" name="idProdotto" value="' + idProduct + '">' +
                '<th>Name Surname</th>' +
                '<th>Share</th>' +
            '</tr>');

        for (var i = 0; i < data.length; i++) {
            var html = '<tr>' +
                    '<td>' + data[i].nome + '</td>' +
                    '<td>' +
                        '<input type="checkbox" name="share" value="' + data[i].email + '" ' + (data[i].shared ? 'checked' : '') + '>' +
                    '</td>' +
                '</tr>';
            $(elem).append(html);
        }
    });
}

function getNotifications(mobile){
    $('.dropdown-menu1').toggle();
    /*
    if(mobile){
        $('.navbar-toggle').toggle(); 
        $('.main').toggle();
    }*/
    
    $.post("GetWebNotifications").done(function (data) {
        $("#notificationsContent").empty();
        
        for(var i = 0; i < data.length; i++){
            var tipo = "";
            if(data[i].tipo === "chat")
                tipo = "New chat message";
            else if(data[i].tipo === "list_share")
                tipo = "List shared with you!";
            else if(data[i].tipo === "product_share")
                tipo = "Product shared with you!";
            
            var elem = "<li>" +
                            /*"<div class=\"col-md-3 col-sm-3 col-xs-3\">" +
                                "<div class=\"notify-img\"><img src=\"http://placehold.it/45x45\"></div>" +
                            "</div>" +*/
                            "<div class=\"col-md-12 col-sm-12 col-xs-12\">" +
                                "<a href=\"\" data-toggle=\"modal\" data-target=\"#modal_accept\">" + tipo + "</a>" +
                                "<a href=\"\"  class=\"rIcon\"><i class=\"fa fa-dot-circle-o\"></i></a>" +
                                "<p>" + data[i].testo + "</p>" +
                                "<p class=\"time\">" + data[i].data + "</p>" +
                            "</div>" +
                        "</li>";
                
            $("#notificationsContent").append(elem);
        }
    });
}