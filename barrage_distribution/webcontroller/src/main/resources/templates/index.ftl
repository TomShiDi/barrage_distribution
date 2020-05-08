<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>

    <meta http-equiv="X-UA-Compatible" content=="IE=edge"/>
<#--<meta http-equiv="Content-Security-Policy" content="upgrade-insecure-requests">-->
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>-------------</title>
    <link type="text/css" href="/script/jquery-easyui-1.7.5/themes/icon.css" rel="stylesheet">
    <link type="text/css" href="/script/jquery-easyui-1.7.5/themes/default/easyui.css" rel="stylesheet">
    <script type="text/javascript" src="/script/jquery.min.js"></script>
    <script type="text/javascript" src="/script/jquery-easyui-1.7.5/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/script/jquery-easyui-1.7.5/locale/easyui-lang-zh_CN.js"></script>
    <script src="https://use.fontawesome.com/06dae4fa6e.js"></script>

    <link rel="stylesheet" type="text/css" href="/css/style.css?t=1.2.8">
</head>
<body>
<#--<script src="/script/three.min.js"></script>-->
<#--<script src="/script/canvas-index.js"></script>-->
<#--<canvas width="1920" height="1007" style="position: absolute; top: 0;"></canvas>-->
<nav class="side-menu">
    <div class="settings">
        <img class="head-icon" src="pic/head-icon-1.jpg"/>
    </div>
    <div class="menu-ul">
        <ul>
            <li>
                <a href="#">
                    <i class="fa fa-home fa-lg"></i>
                    <span class="nav-text">Home</span>
                </a>
            </li>

            <li>
                <a href="panSearch.html">
                    <i class="fa fa-cloud fa-lg"></i>
                    <span class="nav-text">网盘资源搜索</span>
                </a>
            </li>

            <li>
                <a href="BComment.html">
                    <i class="fa fa-book fa-lg"></i>
                    <span class="nav-text">B站评论提取</span>
                </a>
            </li>

            <li>
                <a href="socket.html">
                    <i class="fa fa-comments fa-lg"></i>
                    <span class="nav-text">聊天室</span>
                </a>
            </li>

            <li>
                <a href="#">
                    <i class="fa fa-rocket fa-lg"></i>
                    <span class="nav-text">Rocket</span>
                </a>
            </li>

            <li>
                <a href="#">
                    <i class="fa fa-code fa-lg"></i>
                    <span class="nav-text">Code</span>
                </a>
            </li>

            <li>
                <a href="#">
                    <i class="fa fa-coffee fa-lg"></i>
                    <span class="nav-text">Coffee</span>
                </a>
            </li>

            <li>
                <a href="#">
                    <i class="fa fa-compass fa-lg"></i>
                    <span class="nav-text">Compass</span>
                </a>
            </li>

            <li>
                <a href="#">
                    <i class="fa fa-download fa-lg"></i>
                    <span class="nav-text">Download</span>
                </a>
            </li>
        </ul>
    </div>
</nav>
<div class="barrage-box">
    <div class="barrage-content">
        <div class="barrage-road">
                <span class="barrage">
                    <img src="pic/huaji.jpg" rel="icon" class="barrage-head-img" alt=""/>
                    <div class="barrage-info-area">
                        测试弹幕.....
                        <img src="pic/star-active.png" class="star-img" alt="">
                        <span class="star-num-text">0</span>
                    </div>
                </span>
        </div>
        <div class="barrage-road">

        </div>
        <div class="barrage-road">

        </div>
        <div class="barrage-road">

        </div>
        <div class="barrage-road">

        </div>
        <div class="barrage-road">

        </div>
        <div class="barrage-road">

        </div>
    </div>

    <div class="barrage-settings">
        <!--<input class="text-size" type="range" max="100" min="0">-->
        <input class="text-preview" type="button" value="Hello World">
        <div class="range-slider">
            <div class="range-slider-fill"></div>
            <div class="range-slider-handle"></div>
        </div>
        <div style="margin-left: 5px">
            <input type="radio" name="colorSelected" value="black">
            <span class="colorSelectorText" style="color: black">黑</span>
        </div>
        <div style="margin-left: 5px">
            <input type="radio" name="colorSelected" value="red">
            <span class="colorSelectorText" style="color: red">红</span>
        </div>
        <div style="margin-left: 5px">
            <input type="radio" name="colorSelected" value="green">
            <span class="colorSelectorText" style="color: green">绿</span>
        </div>
        <div style="margin-left: 5px">
            <input type="radio" name="colorSelected" value="white" checked="checked">
            <span class="colorSelectorText" style="color: white">白</span>
        </div>
        <div style="margin-left: 10px">
            <select class="speed-selector">
                <option value="12" name="speedNum">请选择车速</option>
                <option value="12" name="speedNum">8</option>
                <option value="11" name="speedNum">7</option>
                <option value="10" name="speedNum">6</option>
                <option value="9" name="speedNum">5</option>
                <option value="8" name="speedNum">4</option>
                <option value="7" name="speedNum">3</option>
                <option value="6" name="speedNum">2</option>
                <option value="5" name="speedNum">1</option>
            </select>
        </div>

    </div>

    <div class="comment-box">
        <input class="comment-input" type="text" placeholder="请输入待发送内容">
    </div>
    <div class="button-area">
        <button class="send-button" onclick="addBarrageData()">发送弹幕</button>
    </div>

</div>
<div class="tipImgArea">
    <img src="pic/phone-reverse.gif">
    <span>请解除手机屏幕锁定，并将手机横屏放置</span>
</div>
<script type="text/javascript" src="script/index-2-pro.js?t=1.1.5"></script>
<ul class="right-menu-ul" style="display: none">
    <li class="menu-item">举报当前弹幕</li>
</ul>
</body>
</html>