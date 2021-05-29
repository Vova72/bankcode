$(document).ready(function () {
    registerFunc();
});

function registerFunc() {
    $("#submitButton").click(function (e) {
        if($("#checkPassword").val() == "" || $("#password").val() == "") {
        } else {
            if ($("#checkPassword").val() != $("#password").val()) {
                $("#checkPassword").attr("class", "form-control is-invalid");
            } else {
                var account = {
                    activateCode: getParameterByName("code"),
                    password: $("#password").val()
                }

                $.ajax({
                    type: "POST",
                    url: "/setnewpassword",
                    contentType: "application/json",
                    data: JSON.stringify(account),
                    dataType: "json",
                    success: alert("Your password has been changed"),
                });
                window.location.replace("/login.html");
            }
        }
    });
}

function getParameterByName(name, url) {
    if (!url) url = window.location.href;
    name = name.replace(/[\[\]]/g, "\\$&");
    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}