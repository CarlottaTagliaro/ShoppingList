function getLocation() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(sendLocation);
    }
}

function sendLocation(position) {
    $.post("GeolocationServlet", {"lat": position.coords.latitude, "long": position.coords.longitude}).done(function (resp) {
        console.log("ok geolocation");
    });
}

function startLocating() {
    setInterval(getLocation, 60 * 1000);
}