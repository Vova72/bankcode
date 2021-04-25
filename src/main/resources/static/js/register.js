$(document).ready(function () {
    registerFunc();
});

function registerFunc() {
    $("#submitButton").click(function (e) {
        if($("#login").val() == "" || $("#name").val() == "" || $("#surname").val() == "" || $("#password").val() == "" || $("#email").val() == "" || $("#phone").val() == "") {
        } else {
            var account = {
                login: $("#login").val(),
                name: $("#name").val(),
                surname: $("#surname").val(),
                password: $("#password").val(),
                email: $("#email").val(),
                phone: $("#phone").val()
            }

            $.ajax({
                type: "POST",
                url: "/newuser",
                contentType: "application/json",
                data: JSON.stringify(account),
                dataType: "json",
                success: alert("Your account has been created. Please check email to activate account"),
                error: function (xhr, status, error) {
                    var jsonError = jQuery.parseJSON( xhr.responseText );
                    var desc = (jsonError != "") ? jsonError.description : "no details";

                    $("#login").attr("class", "form-control is-invalid");
                }
            });
            window.location.replace("/login.html");
        }
    });
}