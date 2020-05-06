var prePageButton = null;

var nextPageButton = null;

var page1 = null;

var page2 = null;

var page3 = null;

var page4 = null;

var page5 = null;

var minPage = null;

var maxPage = null;

var targetButton = null;

var currentPage = 1;

window.onload = function (ev) {
    prePageButton = document.getElementById("prePageButton");
    nextPageButton = document.getElementById("nextPageButton");
    page1 = document.getElementById("page-1");
    page2 = document.getElementById("page-2");
    page3 = document.getElementById("page-3");
    page4 = document.getElementById("page-4");
    page5 = document.getElementById("page-5");
    minPage = document.getElementById("min-page");
    maxPage = document.getElementById("max-page");

};

function initButtonStatus(param) {
    var count = parseInt(param);
    if (count <= 0 || count === NaN.valueOf()) {
        alert("搜索出错或结果为空");
        return 0;
    }

    page1.innerHTML = 1;
    page2.innerHTML = 2;
    page3.innerHTML = 3;
    page4.innerHTML = 4;
    page5.innerHTML = 5;

    switch (count) {
        case 1:
            minPage.removeAttribute("disabled");
            page1.removeAttribute("disabled");
            break;
        case 2:
            minPage.removeAttribute("disabled");
            page1.removeAttribute("disabled");
            page2.removeAttribute("disabled");
            nextPageButton.removeAttribute("disabled");
            break;
        case 3:
            minPage.removeAttribute("disabled");
            page1.removeAttribute("disabled");
            page2.removeAttribute("disabled");
            page3.removeAttribute("disabled");
            nextPageButton.removeAttribute("disabled");
            break;
        case 4:
            minPage.removeAttribute("disabled");
            page1.removeAttribute("disabled");
            page2.removeAttribute("disabled");
            page3.removeAttribute("disabled");
            page4.removeAttribute("disabled");
            nextPageButton.removeAttribute("disabled");
            break;
        case 5:
            minPage.removeAttribute("disabled");
            page1.removeAttribute("disabled");
            page2.removeAttribute("disabled");
            page3.removeAttribute("disabled");
            page4.removeAttribute("disabled");
            page5.removeAttribute("disabled");
            nextPageButton.removeAttribute("disabled");
            break;
        default:
            minPage.removeAttribute("disabled");
            page1.removeAttribute("disabled");
            page2.removeAttribute("disabled");
            page3.removeAttribute("disabled");
            page4.removeAttribute("disabled");
            page5.removeAttribute("disabled");
            maxPage.removeAttribute("disabled");
            nextPageButton.removeAttribute("disabled");
            maxPage.innerHTML = count;
            break;
    }
}

function pageButtonClick(object) {

    var searchName = searchInput.value;
    var pageNum = parseInt(object.innerHTML);

    pageNavHttpRequest(searchName, pageNum);
}

function preButtonClick() {

    var searchName = searchInput.value;
    var pageNum = currentPage - 1;
    currentPage = currentPage - 1;

    if (targetButton != null) {
        targetButton.classList.remove("button-current");
        targetButton.removeAttribute("disabled");
    }
    page1.innerHTML = currentPage;
    page2.innerHTML = currentPage + 1;
    page3.innerHTML = currentPage + 2;
    page4.innerHTML = currentPage + 3;
    page5.innerHTML = currentPage + 4;

    pageNavHttpRequest(searchName, pageNum);

    reFreshButtonStatus(currentPage);
}

function nextButtonClick() {
    var searchName = searchInput.value;
    var pageNum = currentPage + 1;
    currentPage = currentPage + 1;

    if (targetButton != null) {
        targetButton.classList.remove("button-current");
        targetButton.removeAttribute("disabled");
    }
    page1.innerHTML = currentPage;
    page2.innerHTML = currentPage + 1;
    page3.innerHTML = currentPage + 2;
    page4.innerHTML = currentPage + 3;
    page5.innerHTML = currentPage + 4;

    pageNavHttpRequest(searchName, pageNum);

    reFreshButtonStatus(currentPage);
}

function minPageButtonClick() {
    var searchName = searchInput.value;

    if (targetButton != null) {
        targetButton.classList.remove("button-current");
        targetButton.removeAttribute("disabled");
    }
    page1.innerHTML = 1;
    page2.innerHTML = 2;
    page3.innerHTML = 3;
    page4.innerHTML = 4;
    page5.innerHTML = 5;

    page1.classList.add("button-current");
    page1.setAttribute("disabled", "disabled");
    targetButton = page1;

    pageNavHttpRequest(searchName, 1);
}

function maxPageButtonClick() {
    var searchName = searchInput.value;

    if (targetButton != null) {
        targetButton.classList.remove("button-current");
        targetButton.removeAttribute("disabled");
    }
    page1.innerHTML = parseInt(maxPage.innerHTML) - 4;
    page2.innerHTML = parseInt(maxPage.innerHTML) - 3;
    page3.innerHTML = parseInt(maxPage.innerHTML) - 2;
    page4.innerHTML = parseInt(maxPage.innerHTML) - 1;
    page5.innerHTML = parseInt(maxPage.innerHTML);

    page5.classList.add("button-current");
    page5.setAttribute("disabled", "disabled");
    targetButton = page5;

    pageNavHttpRequest(searchName, maxPage.innerHTML);
}

function reFreshButtonStatus(index) {
    var numIndex = parseInt(index);

    if (prePageButton.hasAttribute("disabled") && numIndex > 1) {
        prePageButton.removeAttribute("disabled");
    }

    if (numIndex % 5 === 0) {
        if (targetButton != null) {
            targetButton.classList.remove("button-current");
            targetButton.removeAttribute("disabled");
        }
        page1.innerHTML = numIndex + 1;
        page2.innerHTML = numIndex + 2;
        page3.innerHTML = numIndex + 3;
        page4.innerHTML = numIndex + 4;
        page5.innerHTML = numIndex + 5;
        page5.classList.add("button-current");
        setTimeout(function (e) {
            page5.classList.remove("button-current");
            page5.removeAttribute("disabled");
            targetButton = null;
        });
        page5.setAttribute("disabled", "disabled");
        targetButton = page5;
    } else {
        if (targetButton != null) {
            targetButton.classList.remove("button-current");
            targetButton.removeAttribute("disabled");
        }
        if (parseInt(page1.innerHTML) === numIndex) {

            page1.classList.add("button-current");
            page1.setAttribute("disabled", "disabled");
            targetButton = page1;
        }
        if (parseInt(page2.innerHTML) === numIndex) {
            page2.classList.add("button-current");
            page2.setAttribute("disabled", "disabled");
            targetButton = page2;
        }
        if (parseInt(page3.innerHTML) === numIndex) {
            page3.classList.add("button-current");
            page3.setAttribute("disabled", "disabled");
            targetButton = page3;
        }
        if (parseInt(page4.innerHTML) === numIndex) {
            page4.classList.add("button-current");
            page4.setAttribute("disabled", "disabled");
            targetButton = page4;
        }

    }
}

function pageNavHttpRequest(searchName, pageNum) {
    $.ajax({
        url: "/pan/search",
        type: "get",
        dataType: "json",
        data: {
            q: searchName,
            p: pageNum
        },
        complete: function (response) {
            if (response.status == 200) {
                var data = JSON.parse(response.responseText);
                itemCount = Math.ceil(parseInt(data["list"]["count"]) / 10);
                currentPage = parseInt(data["list"]["p"]);

                searchInfoArray = searchInfoArray.concat(data["list"]["data"]);
                console.log("searchData", searchInfoArray);
                if (searchInfoArray.length > 0) {

                    searchContent.innerHTML = "";
                    reFreshButtonStatus(pageNum);
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
