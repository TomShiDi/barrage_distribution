window.onload = function (e) {

    $(".toast").on("hidden.bs.toast", function (e) {
        $("#toast-area").css("display", "none");
    });

    $("form>button").on("click", function (e) {
        let userName = $("#username").val();
        let password = $("#password").val();

        $.ajax({
            url: "/auth/login",
            type: "post",
            dataType: "json",
            data: {
                userName: userName,
                password: password
            },
            complete:function (response) {
                if (response.status === 200) {
                    $(".toast-body").text(response.responseJSON.message);
                    $("#toast-area").css("display", "block");
                    if (response.responseJSON.code === 24) {
                        // debugger;
                        window.location.href = window.location.protocol + "//" + window.location.host + "/index";
                    }
                    $(".toast").toast("show");
                }else{
                    console.error(response);
                }
            }
        });
    })
};