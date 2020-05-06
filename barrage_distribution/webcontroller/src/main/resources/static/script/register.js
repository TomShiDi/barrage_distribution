window.onload = function (e) {
    $('#btn-submit').on("click", function (e) {
        formSubmit();
    });

    $('.toast').on('hidden.bs.toast', function (e) {
        let url = window.location.protocol + "//" + window.location.hostname + ((window.location.port === "") ? "" : (":" + window.location.port));
        // let url = window.location.protocol + "//" + window.location.host;
        $('#toast-area').css("display", "none");
        window.location.href = url;
    });

    $("#name").on("input", function (e) {
        $("#name+.invalid-message").css("display", "none");
    });

    $("#email").on("input", function (e) {
        $("#group-email+.invalid-message").css("display", "none");
    });

    $("#password").on("input", function (e) {
        $("#password+.invalid-message").css("display", "none");
    });

    $("#password-repeat").on("input", function (e) {
        let password = $("#password").val();
        let passwordRepeat = e.target.value;
        if (password === passwordRepeat) {
            $("#password-repeat+.invalid-message").css("display", "none");
        }else{
            $("#password-repeat+.invalid-message").css("display", "block");
        }
    });

    function formSubmit() {
        let params = {};
        let flag = false;
        let nickName = document.getElementById("name").value;
        let userEmail = document.getElementById("email").value;
        let userSex = $('#sex option:selected').val();
        let userPassword = $('#password').val();
        let userPasswordRepeat = $("#password-repeat").val();
        let emailType = $("#email-type option:selected").val();

        if (nickName === "") {
            $("#name+.invalid-message").css("display", "block");
            flag = true;
        }
        if (userEmail === "") {
            $("#group-email+.invalid-message").css("display", "block");
            flag = true;
        }
        if (userPassword === "") {
            $("#password+.invalid-message").css("display", "block");
            flag = true;
        }
        if (userPassword !== userPasswordRepeat) {
            $("#password-repeat+.invalid-message").css("display", "block");
            flag = true;
        }
        if (flag) {
            return;
        }

        params = {
            nickName: nickName,
            userPassword: userPassword,
            userSex: userSex,
            userEmail: userEmail + emailType
        };
        
        if (!params instanceof Map) {
            throw new DOMException("参数格式错误");
        }
        $.ajax({
            url: "/register/do",
            dataType: "json",
            type: "post",
            data: {
                nickName: params.nickName,
                userPassword: params.userPassword,
                userSex: params.userSex,
                userEmail: params.userEmail
            },
            complete:function (response) {
                if (response.status === 200) {
                    console.log(response.responseJSON.code);
                    $(".toast").toast("show");
                    $("#toast-area").css("display", "block");
                }else{
                    console.error(new Date(), response);
                }
            }
        });
    }

    
};