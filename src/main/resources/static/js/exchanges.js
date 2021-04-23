$(document).ready(
    function () {
        loadPages(14);
        loadExchanges(0);
        loadAnotherInfo();
    }
);

function loadExchanges(page) {
    $("#exchangeTable").empty();
    $.getJSON("/exchangelist?page=" + page, function (data) {
        var i;

        for(i = 0; i < data.length; i++) {
            $("#exchangeTable").append($("<tr>").append(
                $("<th scope=\"row\">").append(data[i].date),
                $("<td>").append(data[i].usdBuy),
                $("<td>").append(data[i].usdSale),
                $("<td>").append(data[i].eurBuy),
                $("<td>").append(data[i].eurSale),
            ))
        }
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
    });
}

function loadPages(pageSize) {
    $.getJSON("/countexchanges", function (data) {
        $("pages").empty();

        var pages = data.pagesCount;

        var pageCount = (pages / pageSize) +
            (pages % pageSize > 0 ? 1 : 0);
        var i;

        for (i = 1; i <= pageCount; i++) {
            $('#pages').append(
                $('<li>').attr('class', 'page-item').append(
                    $('<a>').attr('class', 'page-link').attr('id', i - 1)
                        .append('Page ' + i))
            );
        }

        $("#pages").on("click", ".page-link", function(event) {
            loadExchanges(event.target.id);
        });
    });
}