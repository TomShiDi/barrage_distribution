"use strict";

var barrageContent = document.getElementsByClassName("barrage-content")[0];
// var rect = barrageContent.getBoundingClientRect();

/**
 * 弹道
 * @type {HTMLCollectionOf<Element>}
 */
var barrageRoad = document.getElementsByClassName("barrage-road");


var rangeValue = 16;//字体大小 预设值

var sliderHandle = document.getElementsByClassName("range-slider-handle")[0];//字体调节滑块
var slider = document.getElementsByClassName("range-slider")[0];
var sliderRect = slider.getBoundingClientRect();
var sliderFill = document.getElementsByClassName("range-slider-fill")[0];//滑过区域
var textPreview = document.getElementsByClassName("text-preview")[0];//预览字体区

var content = document.getElementsByClassName("comment-input")[0];//弹幕输入框
var speedNum = document.getElementsByClassName("speed-selector")[0];//弹幕速度
var color = document.getElementsByName("colorSelected");//弹幕颜色选择器
var colorSelected = "white";//被选择颜色 预设值

var transitionEvent = whichTransitionEvent();//判断浏览器内核类型

var index = 1;//数据库弹幕表分页页码

var deviceType = goPAGE();

let I = 0;//右键弹幕的id

let isStart = true;
let isContentEnd = false;
sliderHandle.style.left = "0px";


/**
 * 测试用数据 数据格式
 * @type {*[]}
 */
var barrageData_2 = [
    {
        "barrageId": 1,
        "content": "第二条弹幕.............",
        "color": "red",
        "speed": 16,
        "textSize": 20,
        "road": 0,
        "starNum": 0
    }

];


barrageData_2 = barrageData_2.concat();
/**
 * 未测试方法
 */
// var barrageDataProxy = new Proxy(barrageData_2,{
//     get:function (target,key,receiver) {
//         return target[key];
//     },
//     set: function (target, key, value, receiver) {
//         target[key] = value;
//         console.log("数组监听", value);
//         return true;
//     }
// })


/**
 * 初始化操作自执行函数
 */
(function () {
    for (var i = 0; i < barrageData_2.length; i++) {
        barrageData_2[i]["road"] = i % barrageRoad.length;
    }
    console.log("screen-size", screen.width, screen.height);
    var that = this;
    var timer = setInterval(function (args) {
        if (barrageData_2.length <= 0) {
            console.log("barrageData:", barrageData_2);
            console.log("isStart", isStart);
            if (isStart) {
                regularGetBarrage();
            }
            return;
        }
        sendBarrage(barrageData_2[0]["barrageId"], barrageData_2[0]["content"], barrageData_2[0]["color"], barrageData_2[0]["speed"], barrageData_2[0]["textSize"], barrageData_2[0]["road"]);
        barrageData_2.splice(0, 1);
    }, 1000);
})();


window.onload = function (ev) {
    document.getElementsByClassName("menu-item")[0].onclick = report;//TODO
    let imageArea = document.querySelector(".tipImgArea>img");
    imageArea.style.width = document.body.clientWidth + "px";
    imageArea.style.height = document.body.clientHeight + "px";
    if (document.body.clientWidth <= 480) {
        isStart = false;
    }
    // document.querySelector(".tipImgArea").onclick = function (e) {
    //     document.body.style.webkitTransform = "rotate(90deg)";
    //     document.body.style.transform = "rotate(90deg)";
    // }
};

/**
 * 鼠标指定弹幕事件函数
 * @param event
 */
function onMouseIn(event) {
    var barrage = event.valueOf();
    var computeStyle = window.getComputedStyle(barrage), left = computeStyle.getPropertyValue("left");
    barrage.style.left = left;
    barrage.classList.remove("barrage-active");
}

/**
 * 鼠标离开弹幕事件函数
 * @param event
 */
function onMouseLeave(event) {
    var barrage = event.valueOf();
    barrage.classList.add("barrage-active");
}

/**
 * 弹幕点击事件
 * @param event
 */
function mouseClicked(event) {
    let barrage = event.valueOf();
    let barrageInfoArea = barrage.childNodes[1];
    console.log("childList", barrageInfoArea.childNodes);
    // barrage.classList.add("barrage-clicked");
    // barrage.childNodes[1].setAttribute("src", "/pic/star-active.png");
    // barrage.childNodes[3].innerHTML = parseInt(barrage.childNodes[3].innerHTML) + 1;

    if (barrageInfoArea.classList.contains("barrage-info-area-clicked")) {
        barrageInfoArea.classList.remove("barrage-info-area-clicked");
        barrageInfoArea.childNodes[1].setAttribute("src", "/pic/star.png");
        barrageInfoArea.childNodes[3].innerHTML = parseInt(barrageInfoArea.childNodes[3].innerHTML) - 1;
        unstar(barrageInfoArea);
    } else {
        barrageInfoArea.classList.add("barrage-info-area-clicked");
        barrageInfoArea.childNodes[1].setAttribute("src", "/pic/star-active.png");
        barrageInfoArea.childNodes[3].innerHTML = parseInt(barrageInfoArea.childNodes[3].innerHTML) + 1;
        star(barrageInfoArea);
    }

}


/**
 * 屏幕弹幕显示处理函数
 * @param barrageId 编号
 * @param content 弹幕内容
 * @param colorSelected 选择的颜色
 * @param speedNum 弹幕速度
 * @param rangeValue 字体大小
 * @param roadNum 弹道
 */
function sendBarrage(barrageId, content, colorSelected, speedNum, rangeValue, roadNum) {

    var newBarrage = document.createElement("span");

    var newBarrageInfoArea = document.createElement("div");

    if (content === "") {
        return;
    }

    newBarrage.classList.add("barrage");
    newBarrage.style.transitionDuration = speedNum + "s";
    newBarrage.style.webkitTransitionDuration = speedNum + "s";
    // newBarrage.style.setProperty("color", colorSelected);
    // newBarrage.style.setProperty("font-size", rangeValue * 0.80 + "px");
    // newBarrage.innerHTML = content + "<img src='pic/star.png' class='star-img' alt=''>" +
    //         "                    <span class='star-num-text'>"+barrageData_2[0]["starNum"]+"</span><input type='text' hidden value="+barrageId+">";

    newBarrageInfoArea.classList.add("barrage-info-area");
    newBarrageInfoArea.style.setProperty("color", colorSelected);
    newBarrageInfoArea.style.setProperty("font-size", rangeValue * 0.80 + "px");
    newBarrageInfoArea.innerHTML = content + "<img src='pic/star.png' class='star-img' alt=''>" +
        "                    <span class='star-num-text'>" + barrageData_2[0]["starNum"] + "</span><input type='text' hidden value=" + barrageId + ">";
    newBarrage.innerHTML = "<img src=\"pic/huaji.jpg\" rel=\"icon\" class=\"barrage-head-img\" alt=\"\" />";

    newBarrage.appendChild(newBarrageInfoArea);
    newBarrage.onmouseenter = function (ev) {
        onMouseIn(this);
    };
    newBarrage.onmouseleave = function (ev) {
        onMouseLeave(this);
    };
    if (deviceType === "pc") {
        newBarrage.onclick = function (ev) {
            var ul = document.getElementsByClassName("right-menu-ul")[0];

            ul.style.display = "none";
            mouseClicked(this);
        };
    } else {
        newBarrage.ontouchend = function (ev) {
            var ul = document.getElementsByClassName("right-menu-ul")[0];

            ul.style.display = "none";
            mouseClicked(this);
        }
    }

    newBarrage.oncontextmenu = function (ev) {
        var ul = document.getElementsByClassName("right-menu-ul")[0];
        // console.log("contextmenu", ev.currentTarget.valueOf().childNodes);
        event.preventDefault();
        //TODO
        I = barrageId;
        ul.style.display = "block";
        ul.style.left = event.clientX + 10 + "px";
        ul.style.top = event.clientY + 10 + "px";
    };

    //TODO 备注，dom初始化的时机
    barrageRoad[roadNum].appendChild(newBarrage);

    setTimeout(function () {
        newBarrage.classList.add("barrage-active");
    }, 50);


    newBarrage.addEventListener(transitionEvent, function (evt) {
        // console.log(this);
        this.parentNode.removeChild(this);
        // barrageData_2.splice(0, 1);
        console.log("----------------------transitionEnd事件触发一次---------------");
    });

}

/**
 * 滑块鼠标按下事件函数
 * @param event
 */
if (deviceType === "pc") {
    sliderHandle.onmousedown = function (event) {
        var that = this;
        var oldX = event.clientX;
        var left = parseInt(that.style.left);

        document.onmousemove = function (ev) {
            var x = ev.clientX - oldX;

            that.style.left = left + x + "px";

            rangeValue = Math.ceil((parseInt(that.style.left) / sliderRect.width) * 40) + 1;


            if (parseInt(that.style.left) < 0) {
                that.style.left = "0";
                rangeValue = 16;
            }
            if (parseInt(that.style.left) > sliderRect.width) {
                that.style.left = sliderRect.width - 10 + "px";
                rangeValue = 40;
            }
            sliderFill.style.width = that.style.left;
            textPreview.style.fontSize = rangeValue + "px";
            console.log("rangeValue: ", rangeValue);
        };


        document.onmouseup = function (ev) {
            document.onmouseup = null;
            document.onmousemove = null;
        }
    };
} else {
    sliderHandle.ontouchstart = function (event) {
        var that = this;
        var oldX = event.clientX;
        var left = parseInt(that.style.left);

        document.ontouchmove = function (ev) {
            var x = ev.clientX - oldX;

            that.style.left = left + x + "px";

            console.log("left", that.style.left);

            rangeValue = Math.ceil((parseInt(that.style.left) / sliderRect.width) * 40) + 1;


            if (parseInt(that.style.left) < 0) {
                that.style.left = "0";
                rangeValue = 10;
            }
            if (parseInt(that.style.left) > sliderRect.width) {
                that.style.left = sliderRect.width - 10 + "px";
                rangeValue = 50;
            }
            sliderFill.style.width = that.style.left;
            textPreview.style.fontSize = rangeValue + "px";
            console.log("rangeValue: ", rangeValue);
        };


        document.ontouchend = function (ev) {
            document.ontouchend = null;
            document.ontouchmove = null;
        }
    };

}


/**
 * 发送弹幕按钮点击事件响应函数
 */
function addBarrageData() {
    var item = {
        "content": "",
        "color": "red",
        "speed": 16,
        "textSize": 20,
        "road": 0,
        "starNum": 0
    };
    var content_1 = content.value;
    item["speed"] = speedNum.value;
    item["textSize"] = rangeValue;
    item["road"] = Math.floor(Math.random() * barrageRoad.length);


    content_1 = contentFilter();
    item["content"] = content_1;
    console.log("content: ", content_1);

    for (var i = 0; i < color.length; i++) {
        if (color[i].checked) {
            item["color"] = color[i].value;
            break;
        }
    }
    console.log("添加事件触发");
    barrageData_2.push(item);
    console.log("barrageData :", barrageData_2.length);
    // content.value = "";
    saveBarrage();
}

/**
 * 右键菜单处理函数
 */

// document.oncontextmenu = function (event) {
//
//     var ul = document.getElementsByClassName("right-menu-ul")[0];
//
//     event.preventDefault();
//
//     ul.style.display = "block";
//     ul.style.left = event.clientX +"px";
//     ul.style.top = event.clientY + "px";
//
//     // return false;
// }
/**
 * 全局点击事件重写
 * @param e
 */
document.onclick = function (e) {
    var ul = document.getElementsByClassName("right-menu-ul")[0];

    ul.style.display = "none";
};

/**
 * 举报弹幕响应函数
 * @param id
 */
function report() {

    // $.ajax({
    //     url: "/barrage/save",
    //     type: "post",
    //     dataType: "json",
    //     data: {
    //         "barrageSenderId": 1,
    //         "content": contentFilter(),
    //         "speed": speedNum.value,
    //         "color": function () {
    //             for (var i = 0; i < color.length; i++) {
    //                 if (color[i].checked) {
    //                     return color[i].value;
    //                 }
    //             }
    //         },
    //         "textSize": rangeValue,
    //         "road": Math.floor(Math.random() * barrageRoad.length)
    //     },
    //     complete: function (ev) {
    //
    //     }
    // });

    $.messager.show({
        title: "提示消息",
        msg: "举报成功:" + I,
        timeout: 5000
    });
}

/**
 * 发送的弹幕发送至服务器
 */
function saveBarrage() {
    $.ajax({
        url: "/barrage/save",
        type: "post",
        dataType: "json",
        data: {
            "barrageSenderId": 1,
            "content": contentFilter(),
            "speed": speedNum.value,
            "color": function () {
                for (var i = 0; i < color.length; i++) {
                    if (color[i].checked) {
                        return color[i].value;
                    }
                }
            },
            "textSize": rangeValue,
            "road": Math.floor(Math.random() * barrageRoad.length)
        },
        complete: function (ev) {
            // console.log(ev);
            content.value = "";
            if (ev.status === 200) {
                console.log(ev.responseText);
            } else {
                console.log("error", ev.responseText);
            }
        }
    });
}

/**
 * 后台弹幕数据拉取函数
 */
function regularGetBarrage() {
    if (!isContentEnd) {
        $.ajax({
            url: "/barrage/getData?index=" + index,
            type: "get",
            dataType: "json",
            complete: function (ev) {
                if (ev.status == 200) {
                    // console.log("regular", ev.responseText);
                    var data = JSON.parse(ev.responseText);
                    if (data["resultData"]["barrageInfoPage"]["content"].length === 0) {
                        isContentEnd = true;
                    }
                    barrageData_2 = barrageData_2.concat(data["resultData"]["barrageInfoPage"]["content"]);
                    console.log("regular", barrageData_2);
                    index++;
                    // console.log(data["resultData"]["barrageInfoPage"]["content"]);
                } else {
                    console.log("error", ev.responseText);
                }
            }
        });
    }
}

/**
 * 弹幕点赞处理函数
 * @param option
 */
function star(option) {
    var barrageId = option.childNodes[4].value;
    // console.log("barrageInfoArea", barrageInfoArea);
    $.ajax({
        type: "get",
        dataType: "json",
        url: "/barrage/star?barrageId=" + barrageId,
        complete: function (ev) {
            if (ev.status == 200) {
                $.messager.show({
                    title: "系统提示",
                    msg: "点赞成功",
                    timeout: 3000
                });
                console.log(ev.responseText);
            }
        }
    });
}

function unstar(option) {
    var barrageId = option.childNodes[4].value;
    $.ajax({
        type: "get",
        dataType: "json",
        url: "/barrage/unstar?barrageId=" + barrageId,
        complete: function (ev) {
            if (ev.status == 200) {
                $.messager.show({
                    title: "系统提示",
                    msg: "取消点赞成功",
                    timeout: 3000
                });
                console.log(ev.responseText);
            }
        }
    });
}

/**
 * 浏览器内核判断函数
 * @returns {*}
 */
function whichTransitionEvent() {
    var t;
    var el = document.createElement('fakeelement');
    var transitions = {
        'transition': 'transitionend',
        'OTransition': 'oTransitionEnd',
        'MozTransition': 'transitionend',
        'WebkitTransition': 'webkitTransitionEnd'
    }

    for (t in transitions) {
        if (el.style[t] !== undefined) {
            return transitions[t];
        }
    }
}

/**
 * 输入内容过滤
 */
function contentFilter() {
    var content_1 = content.value;
    content_1 = content_1.replace(/<.*?>.*?<\/.*?>/g, '[非法字段]');
    content_1 = content_1.replace(/<img/g, '[非法字段]');
    content_1 = content_1.replace(/<script/g, '[非法字段]');

    return content_1;
}

/**
 *判断是否为移动端
 */
function goPAGE() {
    if ((navigator.userAgent.match(/(phone|pad|pod|iPhone|iPod|ios|iPad|Android|Mobile|BlackBerry|IEMobile|MQQBrowser|JUC|Fennec|wOSBrowser|BrowserNG|WebOS|Symbian|Windows Phone)/i))) {
        /*window.location.href="你的手机版地址";*/
        // deviceType = "mobile";
        return "mobile";
    }
    else {
        /*window.location.href="你的电脑版地址";    */
        // deviceType = "pc";
        return "pc";
    }
}

window.onresize = function (ev) {
    let imageDom = document.querySelector(".tipImgArea>img");
    imageDom.style.width = document.body.clientWidth + "px";
    imageDom.style.height = document.body.clientHeight + "px";
    if (document.body.clientWidth <= 480) {
        isStart = false;
    } else {
        isStart = true;
    }
    // console.log(imageDom);
};
