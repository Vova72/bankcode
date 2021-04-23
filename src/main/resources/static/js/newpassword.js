$(document).ready(function () {
    registerFunc();
});

function registerFunc() {
    $("#submitButton").click(function (e) {
        if($("#email").val() == "") {
        document.getElementById("error").innerHTML = "WARNING: Please enter all fields!";
        } else {
            var account = {
                email: $("#email").val()
            };
            alert(JSON.stringify(account));
            $.ajax({
                type: "POST",
                url: "/checkaccountemail",
                contentType: "application/json",
                data: JSON.stringify(account),
                dataType: "json",
                success: alert("Please check email to change password")
            });
            window.location.replace("/login.html");
        }
    });
}