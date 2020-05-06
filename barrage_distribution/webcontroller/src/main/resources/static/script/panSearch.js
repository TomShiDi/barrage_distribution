var searchInput = document.getElementsByClassName("search-input")[0];

var searchContent = document.getElementsByClassName("search-content")[0];

var searchInfoArray = [];

var itemCount = 0;

var xmlHttpRequest = null;


function httpRequest(url, method) {
    if (xmlHttpRequest != null) {
        return;
    }
    if (window.XMLHttpRequest) {
        xmlHttpRequest = new XMLHttpRequest();
    }
    else if (window.ActiveXObject) {
        xmlHttpRequest = new ActiveXObject("Microsoft.XMLHTTP");
    }
    else {
        alert("your borrower not support XMLHTTP");
    }
    xmlHttpRequest.onreadystatechange = statusChange;
    xmlHttpRequest.open(method, url);
    xmlHttpRequest.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36");
    xmlHttpRequest.send();
}


function statusChange() {
    if (xmlHttpRequest.status == 200) {
        var data = JSON.parse(xmlHttpRequest.responseText);
        searchInfoArray = searchInfoArray.concat(data);
        console.log("searchInfoArray", searchInfoArray);
    } else {
        console.log("xmlHttpError", xmlHttpRequest);
    }
}


function buttonClick() {
    var searchName = searchInput.value;

    $.ajax({
        url: "/pan/search",
        type: "get",
        dataType: "json",
        data: {
            q: searchName,
            p: 1
        },
        complete: function (response) {
            if (response.status == 200) {
                var data = JSON.parse(response.responseText);
                itemCount = Math.ceil(parseInt(data["list"]["count"]) / 10);
                initButtonStatus(itemCount);
                console.log("itemCount", itemCount);
                searchInfoArray = searchInfoArray.concat(data["list"]["data"]);
                console.log("searchData", searchInfoArray);
                if (searchInfoArray.length > 0) {
                    searchContent.innerHTML = "";
                }
                while (searchInfoArray.length > 0) {

                    var item = document.createElement("div");
                    item.setAttribute("class", "search-item");
                    item.innerHTML = "                <a href=\'" + searchInfoArray[0]["link"] + "\' class='search-item-name' target=\"_blank\">" + searchInfoArray[0]["title"] + "</a>\n" +
                        "                <span class='search-item-info'>" + searchInfoArray[0]["des"] + "</span>\n" +
                        "                <span class='search-item-url'>" + searchInfoArray[0]["link"] + "</span>                ";
                    searchContent.appendChild(item);
                    searchInfoArray.splice(0, 1);
                }
            } else {
                console.log("error", response.responseText);
            }
        }
    });
}