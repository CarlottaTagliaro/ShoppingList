$(document).ready(function () {
    var suggestions = [
        "I'm going shopping. Do we need something?",
        "List modified. Look at what I've added.",
        "I've been to the shop. You can relax now.",
        "I've shared the list with another person.",
        "Can you go shopping?"
    ];

    for (var i = 0; i < suggestions.length; i++) {
        $("#suggestions-btns").append('<li><a href="#" onclick="setText(this)">' + suggestions[i] + '</a></li>');
    }
});

function setText(sender) {
    $("#msg-text").val($(sender).text());
}


$(document).ready(function () {
    String.prototype.format = function () {
        var formatted = this;
        for (var arg in arguments) {
            formatted = formatted.replace("{" + arg + "}", arguments[arg]);
        }
        return formatted;
    };

    //getListChats();

    refreshChatMessages();
});

var selChatId, lastMsgSender, lastMsgTime;

function getListChats() {
    /*var json = '[\n\
     {"id":1,\n\
     "nome": "Lista1",\n\
     "immagine": "http://placehold.it/50/55C1E7/fff&text=U"},\n\
     {"id":2,\n\
     "nome": "Lista2",\n\
     "immagine": "http://placehold.it/50/55C1E7/fff&text=C"}\n\
     ]';*/

    $.post("/ShoppingList/ChatServlet", {"action": "getChatList"}).done(function (chats) {
        $("#chat-panel").empty();

        if (chats.length > 0) {
            for (var i = 0; i < chats.length; i++) {
                var elem = "<li class='left clearfix chat-selector' onclick='getChatMessages(this, {2})'>" +
                        "<span class='chat-img pull-left'>" +
                        "<img src='{1}' alt='chat image' class='img-circle img-responsive img-chat-list'>" +
                        "</span>" +
                        "<div class='chat-body clearfix'>" +
                        "<div class='header'>" +
                        "<strong class='primary-font'>&nbsp;&nbsp;{0}</strong>" +
                        "</div>" +
                        "</div>" +
                        "</li>";

                elem = elem.format(chats[i].nome, chats[i].immagine, chats[i].id);
                $("#chat-panel").append(elem);
            }

            getChatMessages($("#chat-panel").children()[0], chats[0].id);
        } else {
            //nessuna lista
            elem = "<li class='left clearfix center'>" +
                    "                       <p>No list chat</p>" +
                    "                   </li>";
            $("#chat-panel").append(elem);
        }
    });
}

function getChatMessages(sender, id) {
    selChatId = id;
    lastMsgSender = lastMsgTime = null;

    var liste = $("#chat-panel").children();
    for (var i = 0; i < liste.length; i++) {
        $(liste[i]).css("background-color", "initial");
    }

    $(sender).css("background-color", "rgba(180,180,180, 0.5)");


    //loading messages
    $("#chat-messages").empty();
    elem = "<li class='left clearfix center'>" +
            "   <p>Loading messages...</p>&nbsp;&nbsp;" +
            "</li>";
    $("#chat-messages").append(elem);
}

function refreshChatMessages() {
    /*
     var json = '{\n\
     "id_lista": 1,\n\
     "messages":[\n\
     {\n\
     "email_utente":"ciao@ciao.it",\n\
     "immagine":"http://placehold.it/50/FA6F57/fff&text=ME",\n\
     "isMe":true,\n\
     "nome":"Ciao",\n\
     "cognome":"Cognome",\n\
     "timestamp":"18-09-2018 15:00",\n\
     "messaggio":"Ciao come va?"\n\
     },\n\
     {\n\
     "email_utente":"pers@ciao.it",\n\
     "immagine":"http://placehold.it/50/55C1E7/fff&text=U",\n\
     "isMe":false,\n\
     "nome":"Persona",\n\
     "cognome":"2",\n\
     "timestamp":"18-09-2018 15:30",\n\
     "messaggio":"Bene grazie"\n\
     }\n\
     ]\n\
     }';
     
     var chat = JSON.parse(json);*/

    var interval = 5000;
    if (selChatId === undefined && lastMsgSender === undefined && lastMsgTime === undefined) {
        //load chat list
        getListChats();
        
        
        interval = 8000;
        setTimeout(refreshChatMessages, interval);
    } else {
        $.post("/ShoppingList/ChatServlet", {
            "action": "getChatMessages",
            "list_id": selChatId,
            "lastMsgSender": lastMsgSender,
            "lastMsgTime": lastMsgTime
        }).done(function (chat) {
            console.log("ok refresh");

            if (lastMsgTime === null)
                $("#chat-messages").empty();

            if (chat.messages !== undefined && chat.messages.length > 0) {
                for (var i = 0; i < chat.messages.length; i++) {
                    var elem = "";
                    if (chat.messages[i].isMe) {
                        elem = "<li class='right clearfix'><span class='chat-img pull-right'>" +
                                "                                            <img src='{1}' alt='User Avatar' class='img-circle img-responsive img-chat-list'>" +
                                "                                        </span>" +
                                "                                        <div class='chat-body clearfix'>" +
                                "                                            <div class='header'>" +
                                "                                                <small class=' text-muted'><span class='glyphicon glyphicon-time'></span>{3}</small>" +
                                "                                                <strong class='pull-right primary-font'>{2}</strong>" +
                                "                                            </div>" +
                                "                                            <p>" +
                                "                                                {0}" +
                                "                                            </p>" +
                                "                                        </div>" +
                                "                                    </li>";
                    } else {
                        elem = "<li class='left clearfix'>" +
                                "                       <span class='chat-img pull-left'>" +
                                "                           <img src='{1}' alt='User Avatar' class='img-circle img-responsive img-chat-list'>" +
                                "                       </span>" +
                                "                       <div class='chat-body clearfix'>" +
                                "                           <div class='header'>" +
                                "                               <strong class='primary-font'>{2}</strong> <small class='pull-right text-muted'>" +
                                "                               <span class='glyphicon glyphicon-time'></span>{3}</small>" +
                                "                           </div>" +
                                "                           <p>" +
                                "                               {0}" +
                                "                           </p>" +
                                "                       </div>" +
                                "                   </li>";
                    }

                    elem = elem.format(chat.messages[i].messaggio, chat.messages[i].immagine, chat.messages[i].nome + " " + chat.messages[i].cognome, chat.messages[i].timestamp);
                    $("#chat-messages").append(elem);
                }

                //set last msg
                lastMsgTime = chat.messages[chat.messages.length - 1].milliseconds;

                //scroll to bottom of the chat
                $(".panel-body").animate({scrollTop: $('.panel-body').prop("scrollHeight")}, 500);
            } else if (chat !== "" && lastMsgTime === null) {
                //nessun messaggio nella chat
                elem = "<li class='left clearfix center'>" +
                        "   <p>No messages yet</p>" +
                        "</li>";
                $("#chat-messages").append(elem);
            } else if (chat === "") {
                //loading messages
                $("#chat-messages").empty();
                elem = "<li class='left clearfix center'>" +
                        "   <p>Loading messages...</p>&nbsp;&nbsp;" +
                        "</li>";
                $("#chat-messages").append(elem);
            }


            //check for new msg every tot secs
            if (selChatId === undefined || selChatId === null)
                interval = 500;
            setTimeout(refreshChatMessages, interval);
        });
    }
}

function sendMessage() {
    var text = $("#msg-text").val();
    if (text !== undefined && text !== "") {
        $.post("/ShoppingList/ChatServlet", {"action": "sendMessage", "list_id": selChatId, "text": text}).done(function (res) {
            $("#msg-text").val("");
        });
    }
}