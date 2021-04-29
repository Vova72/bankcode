$(document).ready(function () {
    //setAccount();
    setCards(0);
    loadPages(6);
    loadAnotherInfo();
});

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
    });
}

function setCards(page) {
    $("#row1").empty();
    $("#row2").empty();

    $.getJSON("/cardlist?page=" + page, function (data) {
        var i;

        for (i = 0; i < 3; i++) {
            $('#row1').append(
                $('<div class="col">')
                    .append(
                        $("<div class=\"card\" style=\"width: 18rem; margin: 10%; margin-top: 10%;\">").append($('<div class="cardimg">').append($('<h5 class="card-title" style="margin-top: 5%; margin-left: 15%; margin-bottom: 10%;">').append(setSpace(data[i].number)),
                            $("<h5 class=\"card-title\" style=\"margin-left: 50%;\">").append(data[i].killDate)))
                    .append($("<ul class=\"list-group list-group-flush\">").append(
                        $("<li class=\"list-group-item\">").append("Balance: " + data[i].amount),
                        $("<li class=\"list-group-item\">").append("Exchange: " + data[i].exchange)
                    ))
                    .append($("<div class=\"card-body\">").append(
                        $("<a href=\"/transactionlist.html\" class=\"card-link\" value=\"\">").append("Transaction list"),
                        $("<a href=\"/newtransactionform.html\" class=\"card-link\" value=\"\">").append("Make transaction"),
                        )
                    ))
            );
        }

        for (i = 3; i < 6; i++) {
            $('#row2').append(
                $('<div class="col">')
                    .append(
                        $("<div class=\"card\" style=\"width: 18rem; margin: 10%; margin-top: 10%;\">").append($('<div class="cardimg">').append($('<h5 class="card-title" style="margin-top: 5%; margin-left: 15%; margin-bottom: 10%;">').append(setSpace(data[i].number)),
                            $("<h5 class=\"card-title\" style=\"margin-left: 50%;\">").append(data[i].killDate)))
                            .append($("<ul class=\"list-group list-group-flush\">").append(
                                $("<li class=\"list-group-item\">").append("Balance: " + data[i].amount),
                                $("<li class=\"list-group-item\">").append("Exchange: " + data[i].exchange)
                            ))
                            .append($("<div class=\"card-body\">").append(
                                $("<a href=\"/transactionlist.html\" class=\"card-link\" value=\"\">").append("Transaction list"),
                                $("<a href=\"/newtransactionform.html\" class=\"card-link\" value=\"\">").append("Make transaction"),
                                )
                            ))
            );
        }

    });
}

function loadPages(pageSize) {
    $.getJSON("/cardcount", function (data) {
    $("pages").empty();

    var pages = data.pagesCount;

    var pageCount = (pages / pageSize) +
        (pages % pageSize > 0 ? 1 : 0);

    var i;

    if(pageCount >= 2) {
        for (i = 1; i <= pageCount; i++) {
            $('#pages').append(
                $('<li>').attr('class', 'page-item').append(
                    $('<a>').attr('class', 'page-link').attr('id', i - 1)
                        .append('Page ' + i))
            );
        }
    }

    $("#pages").on("click", ".page-link", function(event) {
        setCards(event.target.id);
    });})
}

function setSpace(num) {
    return num.replace(/(\d)(?=(\d{4})+$)/g, '$1 ');
}