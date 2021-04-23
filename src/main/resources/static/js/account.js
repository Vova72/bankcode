$(document).ready(
    function () {
        loadExchanges(0);
        loadAnotherInfo();
    }
);

function loadExchanges(page) {
    $("#exchangeTable").empty();
    $.getJSON("/account", function (data) {
        $("#login").empty();
        $("#name").empty();
        $("#email").empty();
        $("#phone").empty();
        $("#login").append(data.login);
        $("#name").append(data.name + data.surname);
        $("#email").append(data.email);
        $("#phone").append(data.phone);

    });
}

function loadAnotherInfo() {
    $("#usdBuy").empty();
    $("#usdSale").empty();
    $("#eurBuy").empty();
    $("#eurSale").empty();
    $("#cardsCount").empty();

    $.getJSON("/maininfo", function(data) {
        $("#usdBuy").append("USD (buy): " + data.usdBuy);
        $("#usdSale").append("USD (sale): " + data.usdSale);
        $("#eurBuy").append("EUR (buy): " + data.eurBuy);
        $("#eurSale").append("EUR (sale): " + data.eurSale);
        $("#cardsCount").append("Cards: " + data.cardsCount);
        $("#cardsCount2").append(data.cardsCount);
    });
}
