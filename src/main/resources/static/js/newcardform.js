$(document).ready(function () {
    registerFunc();
    loadAnotherInfo();
});

function registerFunc() {
    $("#submitButton").click(function (e) {
        if($("#pinCode").val().length < 4 || !is_numeric($("#pinCode").val())) {
            $("#pinCode").attr("class", "form-control is-invalid");
        } else {
            var gridList = document.getElementsByName("gridRadios");
            var exchangeT;
            for(i = 0; i < gridList.length; i++) {
                if(gridList[i].checked) {
                    exchangeT = gridList[i].value;
                }
            }
            var card = {
                exchange: exchangeT,
                pinCode: $("#pinCode").val()
            }

            $.ajax({
                type: "POST",
                url: "/newcard",
                contentType: "application/json",
                data: JSON.stringify(card),
                dataType: "json",
                success: alert("Your new card has been created. Please remember your pin code")
            });
            window.location.replace("/index.html");
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