<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link href="https://cdn.bootcss.com/twitter-bootstrap/3.1.1/css/bootstrap.min.css" rel="stylesheet">
    <title>激活成功</title>
</head>
<body>
<div class="container container-md container-lg">
    <div class="row clearfix">
        <div class="col-md-12 col-sm-6 col-lg-12 column">
            <div class="alert alert-success alert-dismissable">
                <#--<button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>-->
                <h4>
                    激活成功!
                </h4> <strong><a href="${indexUrl}">3秒后自动跳转</a> </strong>
            </div>
        </div>
    </div>
    <script type="text/javascript">
        setTimeout(function () {
            window.location.href = "${indexUrl}";
        }, 3000);
    </script>
</div>
</body>
</html>