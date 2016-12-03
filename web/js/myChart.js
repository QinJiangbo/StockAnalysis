$(function () {
    $(".numberInput").keyup(numberLimit);
    $("#selectFile").change(function () {
        showImage($(this).val(), "kChartTarget", "amountTarget");
    });
});

function numberLimit(event) {
    var c = $(this);
    if (c.val() > 1 || c.val() < 0) {
        $(this).val(0);
    }
}

function handleFiles() {
    var fileList = document.getElementById("inputFile").files;
    var str;
    for (var i = 0; i < fileList.length; i++) {
        str += "<option value='" + fileList[i].name + "'>" + fileList[i].name + "</option>";
    }
    if (str != null) {
        $("#selectFile").html(str);
    }
}

function calculate() {
    var sum = 0;
    sum += $(".arith1").val() - 0;
    sum += $(".arith2").val() - 0;
    sum += $(".arith3").val() - 0;
    if (sum > 1) {
        alert("三个算法权重之和不能大于1");
        return;
    }
    $("#calBtn").attr("disabled", true);
    $("#calBtn").text("计算中...");

    // 参数
    var params = {
        sourceNo: $("#selectFile").val(),
        hashWeight: $("#pHash").val(),
        levenWeight: $("#levenShtein").val(),
        siftWeight: $("#sift").val(),
        klineWeight: $("#k").val()
    };

    $.ajax({
        type: "POST",
        url: "ajax/compare.ajax",
        data: params,
        dataType: "json",
        success: function (data) {
            var result = data.result;
            var array = result.split(",");
            /* 写入sessionStorage */
            sessionStorage.setItem("array", JSON.stringify(array));
            sessionStorage.setItem("current", 0);
            $("#prevBtn").attr("disabled", true);
            showImage(array[0], "kChartTarget2", "amountTarget2");
            $("#calBtn").attr("disabled", false);
            $("#calBtn").text("计算");
        },
        error: function (data) {
            console.log("data=" + data);
        }
    });
}

/**
 * 导入图片信息
 */
function loadImages() {
    $.ajax({
        type: "POST",
        url: "ajax/loadImages.ajax",
        dataType: "json",
        success: function (data) {
            // ajax回调成功以后向select元素中添加option
            var json = eval(data.result);
            $.each(json, function (index, item) {
                $("#selectFile").append("\<option value=" + item + ">" + item + "</option>");
            })
            showImage(json[0], "kChartTarget", "amountTarget");
            // 禁用掉button
            $("#inputFile").attr("disabled", true);
        },
        error: function (data) {
            console.log("data=" + data);
        }
    });
}

/**
 * 显示数据文件对应的图片
 * @param fileNo
 */
function showImage(fileNo, kchartId, amountId) {
    $("#" + kchartId).find("img")[0].src = "data/image/" + fileNo + "-k.jpg";
    $("#" + amountId).find("img")[0].src = "data/image/" + fileNo + "-v.jpg";
}

/**
 * 显示上一张图片
 */
function prev() {
    var current = Number(sessionStorage.getItem("current"));
    var array = JSON.parse(sessionStorage.getItem("array"));
    var fileNo = array[current - 1];
    $("#imageNo").text(fileNo);
    showImage(fileNo, "kChartTarget2", "amountTarget2");
    sessionStorage.setItem("current", (current - 1));
    $("#nextBtn").attr("disabled", false);
    if((current - 1) == 0) {
        $("#prevBtn").attr("disabled", true);
    }
}

/**
 * 显示下一张图片
 */
function next() {
    var current = Number(sessionStorage.getItem("current"));
    var array = JSON.parse(sessionStorage.getItem("array"));
    console.log(array[1]);
    var fileNo = array[current + 1];
    $("#imageNo").text(fileNo);
    showImage(fileNo, "kChartTarget2", "amountTarget2");
    sessionStorage.setItem("current", (current + 1));
    $("#prevBtn").attr("disabled", false);
    if((current + 1) == (array.length - 1)) {
        $("#nextBtn").attr("disabled", true);
    }
}