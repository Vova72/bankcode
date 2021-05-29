$(document).ready(function () {
    registerFunc();
});

function registerFunc() {
    $("#submitButton").click(function (e) {
        if($("#email").val() == "") {
            $("#email").attr("class", "form-control is-invalid");
        } else {
            var account = {
                email: $("#email").val()
            }

            $.ajax({
                type: "POST",
                url: "/checkaccountemail",
                contentType: "application/json",
                data: JSON.stringify(account),
                dataType: "json",
                success: function () {
                    alert("Please check email to change password");
                    window.location.replace("/login.html");
                },
                error: $("#email").attr("class", "form-control is-invalid")
            });
        }
    });
}