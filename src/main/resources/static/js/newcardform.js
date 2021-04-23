$(document).ready(function () {
    registerFunc();
});

function registerFunc() {
    $("#submitButton").click(function (e) {
        if($("#exchange").val() == "" || $("#pinCode").val() == "") {
        document.getElementById("error").innerHTML = "WARNING: Please enter all fields!";
        } else {
            var card = {
                exchange: $("#exchange").val(),
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