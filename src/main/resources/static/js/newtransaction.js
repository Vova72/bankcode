$(document).ready(function () {
    loadCardsList();
    registerFunc();
    loadAnotherInfo();
});

function registerFunc() {
    $("#submitButton").click(function (e) {
        if($("#pinCode").val().length < 4 || !is_numeric($("#pinCode").val())) {
            $("#pinCode").attr("class", "form-control is-invalid");
        } else {
            var commentT = $("#comment").val();
            if (commentT == "") {
                commentT = "-";
            }
            $("#cardsNumbers :selected").val()
            var transaction = {
                donorNumber: $("#cardsNumbers :selected").val(),
                pinCode: $("#pinCode").val(),
                amount: $("#amount").val(),
                recipientNumber: $("#recipientCard").val(),
                comment: commentT
            }

            alert(JSON.stringify(transaction));

            $.ajax({
                type: "POST",
                url: "/maketransaction",
                contentType: "application/json",
                data: JSON.stringify(transaction),
                dataType: "json",
                success: window.location.replace("/index.html"),
                error: function (xhr, status, error) {
                    var jsonError = jQuery.parseJSON( xhr.responseText );
                    var desc = (jsonError != "") ? jsonError.description : "no details";

                    $("#pinCode").attr("class", "form-control is-invalid");
                }
            });
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

function loadCardsList() {
    $.getJSON("/cardsnumbers", function(data) {
        var i;

        for(i = 0; i < data.length; i++) {
            $("#cardsNumbers").append($("<option>").attr("value", data[i]).append(setSpace(data[i])));
        }
    });
}

function is_numeric(str){
    for(i = 0; i < str.length; i++) {
        if(!isCharNumber(str[i])) {
            return false;
        }
    }

    return true;
}

function isCharNumber(c) {
    return c >= '0' && c <= '9';
}

function setSpace(num) {
    return num.replace(/(\d)(?=(\d{4})+$)/g, '$1 ');
}