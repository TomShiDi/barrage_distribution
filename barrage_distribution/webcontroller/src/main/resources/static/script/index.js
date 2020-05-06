var sliderHandle = document.getElementsByClassName("range-slider-handle")[0];
var slider = document.getElementsByClassName("range-slider")[0];
var sliderRect = slider.getBoundingClientRect();
var sliderFill = document.getElementsByClassName("range-slider-fill")[0];
var textPreview = document.getElementsByClassName("text-preview")[0];

var canvas = document.getElementById("canvas-1");
var vas = canvas.getContext("2d");
var barrage_content = document.getElementsByClassName("barrage-content")[0];

var width = barrage_content.clientWidth;
var height = barrage_content.clientHeight;

var positionTop = barrage_content.clientTop;
var postionLeft = barrage_content.clientLeft;

var rect = barrage_content.getBoundingClientRect();

var numX = [100, 150, 260, 270, 300, 310, 315, 415, 400, 250];
var numY = [100, 300, 400, 320, 60, 55, 389, 260, 235, 210];

var rangeValue = 20;

sliderHandle.style.left = "0px";

var barrageData_2 = [
    {
        "content": "第一条弹幕.............",
        "color": "red",
        "speed": 2,
        "textSize": 20,
        "positionX": 0,
        "positionY": 0
    },
    {
        "content": "第三条弹幕.............",
        "color": "green",
        "speed": 6,
        "textSize": 20,
        "positionX": 0,
        "positionY": 0
    },
    {
        "content": "第二条弹幕.............",
        "color": "red",
        "speed": 2,
        "textSize": 20,
        "positionX": 0,
        "positionY": 0
    },
    {
        "content": "第四条弹幕.............",
        "color": "blue",
        "speed": 8,
        "textSize": 20,
        "positionX": 0,
        "positionY": 0
    },
    {
        "content": "第五条弹幕.............",
        "color": "black",
        "speed": 2,
        "textSize": 20,
        "positionX": 0,
        "positionY": 0
    },
    {
        "content": "--------随机弹幕-------------.",
        "color": "green",
        "speed": 2,
        "textSize": 20,
        "positionX": 0,
        "positionY": 0
    },
    {
        "content": "--------随机弹幕-------------.",
        "color": "green",
        "speed": 2,
        "textSize": 20,
        "positionX": 0,
        "positionY": 0
    },
    {
        "content": "--------随机弹幕-------------.",
        "color": "green",
        "speed": 2,
        "textSize": 20,
        "positionX": 0,
        "positionY": 0
    },
    {
        "content": "--------随机弹幕-------------.",
        "color": "green",
        "speed": 2,
        "textSize": 20,
        "positionX": 0,
        "positionY": 0
    },
    {
        "content": "--------随机弹幕-------------.",
        "color": "green",
        "speed": 2,
        "textSize": 20,
        "positionX": 0,
        "positionY": 0
    },
    {
        "content": "--------随机弹幕-------------.",
        "color": "green",
        "speed": 2,
        "textSize": 20,
        "positionX": 0,
        "positionY": 0
    },
    {
        "content": "--------随机弹幕-------------.",
        "color": "green",
        "speed": 2,
        "textSize": 20,
        "positionX": 0,
        "positionY": 0
    },
    {
        "content": "--------随机弹幕-------------.",
        "color": "green",
        "speed": 2,
        "textSize": 20,
        "positionX": 0,
        "positionY": 0
    },
    {
        "content": "--------随机弹幕-------------.",
        "color": "green",
        "speed": 2,
        "textSize": 20,
        "positionX": 0,
        "positionY": 0
    },
    {
        "content": "--------随机弹幕-------------.",
        "color": "green",
        "speed": 2,
        "textSize": 20,
        "positionX": 0,
        "positionY": 0
    },
    {
        "content": "--------随机弹幕-------------.",
        "color": "green",
        "speed": 2,
        "textSize": 20,
        "positionX": 0,
        "positionY": 0
    }
];


(function () {


    var barrageData = [
        "第一条弹幕..............",
        "第二条弹幕...........",
        "测试弹幕-----------------------------------------"
    ];

    // var barrageData_2 = [
    //     {
    //         "content":"第一条弹幕.............",
    //         "color":"red",
    //         "speed":2,
    //         "positionX":0,
    //         "positionY":0
    //     },
    //     {
    //         "content":"第三条弹幕.............",
    //         "color":"green",
    //         "speed":6,
    //         "positionX":0,
    //         "positionY":0
    //     },
    //     {
    //         "content":"第二条弹幕.............",
    //         "color":"red",
    //         "speed":2,
    //         "positionX":0,
    //         "positionY":0
    //     },
    //     {
    //         "content":"第四条弹幕.............",
    //         "color":"blue",
    //         "speed":8,
    //         "positionX":0,
    //         "positionY":0
    //     },
    //     {
    //         "content":"第五条弹幕.............",
    //         "color":"black",
    //         "speed":2,
    //         "positionX":0,
    //         "positionY":0
    //     },
    //     {
    //         "content":"--------随机弹幕-------------.",
    //         "color":"green",
    //         "speed":2,
    //         "positionX":0,
    //         "positionY":0
    //     },
    //     {
    //         "content":"--------随机弹幕-------------.",
    //         "color":"green",
    //         "speed":2,
    //         "positionX":0,
    //         "positionY":0
    //     },
    //     {
    //         "content":"--------随机弹幕-------------.",
    //         "color":"green",
    //         "speed":2,
    //         "positionX":0,
    //         "positionY":0
    //     },
    //     {
    //         "content":"--------随机弹幕-------------.",
    //         "color":"green",
    //         "speed":2,
    //         "positionX":0,
    //         "positionY":0
    //     },
    //     {
    //         "content":"--------随机弹幕-------------.",
    //         "color":"green",
    //         "speed":2,
    //         "positionX":0,
    //         "positionY":0
    //     },
    //     {
    //         "content":"--------随机弹幕-------------.",
    //         "color":"green",
    //         "speed":2,
    //         "positionX":0,
    //         "positionY":0
    //     },
    //     {
    //         "content":"--------随机弹幕-------------.",
    //         "color":"green",
    //         "speed":2,
    //         "positionX":0,
    //         "positionY":0
    //     },
    //     {
    //         "content":"--------随机弹幕-------------.",
    //         "color":"green",
    //         "speed":2,
    //         "positionX":0,
    //         "positionY":0
    //     },
    //     {
    //         "content":"--------随机弹幕-------------.",
    //         "color":"green",
    //         "speed":2,
    //         "positionX":0,
    //         "positionY":0
    //     },
    //     {
    //         "content":"--------随机弹幕-------------.",
    //         "color":"green",
    //         "speed":2,
    //         "positionX":0,
    //         "positionY":0
    //     },
    //     {
    //         "content":"--------随机弹幕-------------.",
    //         "color":"green",
    //         "speed":2,
    //         "positionX":0,
    //         "positionY":0
    //     }
    // ];


    var color = ["red", "blue", "green"];

    console.log("barrage_clientWidth", width, "barrage_clientHeight", height);

    canvas.width = width;
    canvas.height = height;
    // vas.width = width;
    // vas.height = height;
    vas.rect(rect.left, rect.top, rect.width, rect.height);
    // vas.font = "20px Arial"
    for (var i = 0; i < barrageData_2.length; i++) {
        barrageData_2[i]["positionX"] = numX[Math.floor(Math.random() * 10)];
        barrageData_2[i]["positionY"] = numY[Math.floor(Math.random() * 10)];
    }
    setInterval(function () {
        vas.clearRect(0, 0, rect.width, rect.height);

        for (var i = 0; i < barrageData_2.length; i++) {
            barrageData_2[i]["positionX"] = barrageData_2[i]["positionX"] - barrageData_2[i]["speed"] * 0.6;
            vas.fillStyle = barrageData_2[i]["color"];
            vas.font = barrageData_2[i]["textSize"] + "px Arial";
            vas.fillText(barrageData_2[i]["content"], barrageData_2[i]["positionX"], barrageData_2[i]["positionY"]);
        }
        // console.log("textWidth: ", vas.measureText(barrageData_2[0]["content"]).valueOf());
        for (var i = 0; i < barrageData_2.length; i++) {
            if (barrageData_2[i]["positionX"] < -100) {
                barrageData_2[i]["positionX"] = rect.width;
            }
        }

    }, 30);

    // console.log("slider-handle: ", sliderHandle);

    // vas.fillText("================================清除测试文字===================================", 0, 200);
    // vas.clearRect(rect.left, rect.top, rect.width, rect.height);
})();


console.log("slider-rect: ", sliderRect);
sliderHandle.onmousedown = function (event) {
    var that = this;
    var oldX = event.clientX;
    var left = parseInt(that.style.left);


    // console.log("mousedown  ", sliderHandle);
    document.onmousemove = function (ev) {
        var x = ev.clientX - oldX;

        that.style.left = left + x + "px";

        rangeValue = Math.ceil((parseInt(that.style.left) / sliderRect.width) * 40) + 10;


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
    }


    document.onmouseup = function (ev) {
        document.onmouseup = null;
        document.onmousemove = null;
    }
};

function sendBarrage() {
    var barrage_content = document.getElementsByClassName("comment-input")[0];
    var colorSelect = document.getElementsByName("colorSelected");
    var speedSelect = document.getElementsByTagName("select")[0];
    var item = {
        "content": "--------随机弹幕-------------.",
        "color": "green",
        "speed": 2,
        "textSize": 20,
        "positionX": 0,
        "positionY": 0
    };

    if (barrage_content.value === "") {
        return;
    }
    item["content"] = barrage_content.value;
    for (var i = 0; i < colorSelect.length; i++) {
        if (colorSelect[i].checked) {
            item["color"] = colorSelect[i].value;
            console.log(colorSelect[i].value);
        }
    }
    item["speed"] = speedSelect.value;
    item["positionX"] = rect.width;
    item["positionY"] = numY[Math.floor(Math.random() * 10)];
    item["textSize"] = rangeValue;
    barrageData_2.push(item);
    // console.log(item);
}

// slider.onmouseout = function () {
//     document.onmousemove = null;
// }