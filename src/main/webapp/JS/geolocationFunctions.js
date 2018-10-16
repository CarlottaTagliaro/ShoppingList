function getLocation() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(sendLocation);
    }
}

function sendLocation(position) {
    $.post("GeolocationServlet", {"lat": position.coords.latitude, "long": position.coords.longitude}).done(function (resp) {
        for (var i = 0; i < resp.length; i++) {
            var stored = sessionStorage[resp[i].nomeLista];
            if (stored) {
                var parsed = JSON.parse(stored);
                if (parsed.nomeNegozio !== resp[i].nomeNegozio || parsed.distanza >= resp[i].distanza + 50) {
                    sendNotification(resp[i]);
                }
            } else {
                sendNotification(resp[i]);
            }

            sessionStorage[resp[i].nomeLista] = JSON.stringify(resp[i]);
        }
    });
}

function sendNotification(elem) {
    $.notify("There is a " + elem.tipoNegozio + " shop named '" + elem.nomeNegozio + "' " + elem.distanza + " meters from you.<br/>"
            + "Maybe you need something for your list '" + elem.nomeLista + "'.", {
	placement: {
            from: "bottom",
            align: "right"
	},
	newest_on_top: true,
        delay: 15000
    });
}

function startLocating() {
    setInterval(getLocation, 60 * 1000);
}