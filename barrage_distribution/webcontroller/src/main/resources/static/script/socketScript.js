var sendButtonArea = document.getElementsByClassName("send-button-area")[0];//发送信息按钮dom
var contentBody = document.getElementsByClassName("content-body")[0];//气泡消息显示区域dom
var onlineCountSpan = document.getElementById("onlineCount");//在线人数显示区域dom
var loginButton = document.querySelector("#login-button");//用户输入nickname后的登录按钮dom
var socket = null;//socket对象
var refreshCode = 100;//服务端自定义的刷新消息的返回code
var messageCode = 200;//服务器自定义的信息发送code
var messageSendingSuccessCode = 1;//服务端自定义的：客户端消息发送成功的code
var cookieLoginNameKey = "nickName";//用户nickname保存在cookie中的key字段
var registerSuccessCode = 100;//用户的nickname提交服务端成功的code
var nickName = "";//用户的nickname
/**
 * 页面加载结束事件回调函数,主要用来建立socket连接和设置它的回调方法
 * @param ev
 */
window.onload = function (ev) {
    console.log("event", "window onload");
    //建立websocket连接
    if (window.WebSocket) {
        if (socket == null) {
            socket = new WebSocket("wss://120.77.222.242:9990");
        }
    } else {
        alert("您的浏览器不支持socket");
        return;
    }
    //连接建立成功的回调函数
    socket.onopen = function (e) {
        // setInterval()
    };
    //接收到服务端的消息时的回调函数
    socket.onmessage = function (e) {
        // console.log("message", JSON.parse(e.data));
        var receiveData = JSON.parse(e.data);
        if (receiveData["methodCode"] === refreshCode) {
            refreshOnlineCount(receiveData["onlineCount"]);
        } else if (receiveData["methodCode"] === messageCode) {
            receiveMessage(JSON.parse(receiveData["sendingMessage"])["nickName"], JSON.parse(receiveData["sendingMessage"])["message"]);
        } else if (receiveData["methodCode"] === messageSendingSuccessCode) {
            sendingMessageSuccess();
        }
    };
    //发生错误时的回调函数
    socket.onerror = function (e) {
        console.log("error", e);
    };
    //连接关闭时的回调函数
    socket.onclose = function (e) {
        console.log("close", e);
        console.log("closed socket", socket);
        setTimeout(function () {
            if (socket != null && socket.readyState === 3) {
                socket = new WebSocket("wss://120.77.222.242:9990");
            }
        }, 300);
        // alert("连接断开");
    };

    // console.log("cookie", document.cookie.search(cookieLoginNameKey));
    if (document.cookie.search(cookieLoginNameKey) === -1) {
        document.querySelector(".signWindow").style.display = "block";
    } else {
        var params = document.cookie.split(";");
        for (var i in params) {
            if (params[i].search(cookieLoginNameKey) !== -1) {
                nickName = params[i].split("=")[1];
            }
        }
    }

};

/**
 * 添加enter键的响应事件，回车直接发送信息
 */
window.addEventListener("keypress", function (ev) {
    // console.log("keypress", ev.key);
    if (ev.key === "Enter") {
        doSendingMessage();
    }
});

/**
 * 登录按钮添加点击响应函数，检查用户输入的Nickname是否符合规范
 */
loginButton.addEventListener("click", function (e) {
    var nickName = document.querySelector("#name-input").value;
    if (nickName == null || nickName === "") {
        return;
    }
    nickName = inputFilter(nickName);
    doHttpGet("?nickName=" + nickName);
});

/**
 * 消息发送按钮添加点击事件响应函数
 */
sendButtonArea.addEventListener("click", function (evt) {
    doSendingMessage()
});

/**
 * 刷新在线人数
 * @param count
 */
function refreshOnlineCount(count) {
    onlineCountSpan.innerHTML = count;
}

/**
 * 接收服务端的消息，并动态的在网页上以气泡的形式展示出来
 * @param nickName
 * @param message
 */
function receiveMessage(nickName, message) {
    var newMessageItem = document.createElement("div");
    newMessageItem.innerHTML = "<div class=\"message-content-nickName you\">" + nickName + "</div>\n" +
        "                    <div class=\"message-content you\">\n" + message + "</div>";
    contentBody.appendChild(newMessageItem);
    newMessageItem.classList.add("message-content-father");
    contentBody.scrollTop = contentBody.scrollHeight;
}

/**
 * 仅仅是检查输入的规范和发送socket数据
 */
function doSendingMessage() {
    var input = document.getElementsByClassName("input-field")[0];
    if (input.value.trim() === "") {
        return;
    }
    socket.send(createSendingMessage(input.value));
}

/**
 * 当确定消息服务器收到以后在网页上显示自己发送的消息
 */
function sendingMessageSuccess() {
    var input = document.getElementsByClassName("input-field")[0];
    var newMessageItem = document.createElement("div");

    newMessageItem.innerHTML = "<div class=\"message-content mine\">\n" + input.value + "</div>";

    contentBody.appendChild(newMessageItem);
    newMessageItem.classList.add("message-content-father");
    // newMessageItem.innerHTML = input.value;
    input.value = "";
    contentBody.scrollTop = contentBody.scrollHeight;
}

/**
 * 创建将要发送的消息数据，包括用户nickname和message
 * @param message
 * @returns {string}
 */
function createSendingMessage(message) {
    //TODO
    var filterMessage = inputFilter(message);
    var sendingMessage = {
        nickName: nickName,
        message: filterMessage
    };
    // sendingMessage.nickname = nickName;
    // sendingMessage.message = message;
    console.log("sendingMessage", sendingMessage);
    return JSON.stringify(sendingMessage);

}

/**
 * 用户第一次进入网页时需要注册一个nickname，走springboot的controller接口,
 * 使用jQuery的ajax简化操作
 * @param getParams
 */
function doHttpGet(getParams) {
    console.log("params", getParams);
    $.ajax({
        url: "/chat/register" + getParams,
        dataType: "json",
        type: "GET",
        complete: function (res) {
            // console.log("res", res.responseText);
            // console.log("status", res.status);
            if (res.status === 200) {
                var receiveData = JSON.parse(res.responseText);
                if (receiveData["code"] === registerSuccessCode) {
                    nickName = receiveData["confirmNickName"];
                    // console.log("signWindow", document.querySelector(".signWindow"));
                    document.querySelector(".signWindow").style.display = "none";
                } else {
                    alert("错误代码: " + receiveData["code"]);
                }
            }

        }
    });
}

/**
 * 对用户的输入进行正则过滤，防止xss和csrf攻击
 * @param message
 * @returns {*}
 */
function inputFilter(message) {
    var pattern_1 = /<(.*?)>(.*?)<\/(.*?)>/igm;
    var pattern_2 = /<(.*?)>(.*?)\/?>/igm;
    var pattern_3 = /<(.*?)>/igm;
    // console.log("pattern_1", pattern_1.test(message));
    // console.log("pattern_2", pattern_2.test(message));
    // console.log("pattern_3", pattern_3.test(message));
    if (pattern_1.test(message)) {
        // console.log(message.replace(pattern_1, "$1 [*]"));
        return message.replace(pattern_1, "<$1>$2</[*]>");
    } else if (pattern_2.test(message)) {
        return message.replace(pattern_2, "<$1>[*]>");
    } else if (pattern_3.test(message)) {
        return message.replace(pattern_3, "<$1[*]");
    } else {
        return message;
    }
}




