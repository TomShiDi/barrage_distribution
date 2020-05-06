<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <title>验证码</title>
    <style type="text/css">
        * {
            padding: 0;
            margin: 0;
        }

        html, body {
            width: 100%;
            height: 100%;
        }

        .container-t {
            display: flex;
            width: 80%;
            margin-left: 10%;
            justify-content: center;
            align-items: center;
            flex-wrap: wrap;
        }

        .tip-area-top {
            font-size: 1em;
            color: #000000;
            height: 20%;
            width: 100%;
            margin-top: 10%;
            text-align: left;
        }

        .tip-area {
            color: #bababa;
            font-size: 2em;
            width: 100%;
            height: 20%;
            text-align: center;
            margin-top: 3%;
        }

        .auth-code-area {
            font-size: 3em;
            color: #000000;
            height: 40%;
            width: 100%;
            margin-top: 5%;
            text-align: center;
        }
    </style>
</head>
<body>
<div class="container-t">
    <div class="tip-area-top">
        Tom的验证码:
    </div>
    <div class="tip-area">
        这是您本次的验证码，有效时长为5分钟
    </div>
    <div class="auth-code-area">
    ${authCode!""}
    </div>
    <div>
        激活地址<br>
        <a href="${authUrl}?email=${userEmail!""}&authCode=${authCode!""}">${authUrl}?email=${userEmail!""}&authCode=${authCode!""}</a>
    </div>
</div>
</body>
</html>