$(document).ready(
    function () {
        loadPages(14);
        loadTransactions(0);
        loadAnotherInfo();
    }
);

function loadTransactions(page) {
    $("#transactionTable").empty();
    $.getJSON("/alltransactions?page=" + page, function (data) {
        var i;

        for(i = 0; i < data.length; i++) {
            var rowClass = "class='table-success'";
            if (data[i].type == "out") {
                rowClass = "class='table-danger'";
            }
            $("#transactionTable").append($("<tr " + rowClass + ">").append(
                $("<td>").append(data[i].donorNumber),
                $("<td>").append(data[i].recipientNumber),
                $("<td>").append(data[i].amount),
                $("<td>").append(data[i].comment),
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
    $.getJSON("/counttransactions", function (data) {
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
            loadTransactions(event.target.id);
        });
    });
}