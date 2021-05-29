$(document).ready(
    function () {
        loadExchanges(0);
        loadAnotherInfo();
        saveData();
    }
);

function loadExchanges(page) {
    $("#exchangeTable").empty();
    $.getJSON("/account", function (data) {
        $("#login").empty();
        $("#name").empty();
        $("#surname").empty();
        $("#email").empty();
        $("#phone").empty();
        $("#login").attr("value", data.login);
        $("#name").attr("value", data.name);
        $("#surname").attr("value", data.surname);
        $("#email").attr("value", data.email);
        $("#phone").attr("value", data.phone);

    });
}

function saveData() {
    $("#submitButton").click(function (e) {
        if($("#name").val() != "" && $("#phone").val() != "" && $("#surname").val() != "") {
        var account = {
            name: $("#name").val(),
            surname: $("#surname").val(),
            phone: $("#phone").val(),
        }

        $.ajax({
            type: "POST",
            url: "/updateuser",
            contentType: "application/json",
            data: JSON.stringify(account),
            dataType: "json"
        });
    }})
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
