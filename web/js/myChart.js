$(function () {
    $(".numberInput").keyup(numberLimit);
    $("#selectFile").change(function () {
        showImage($(this).val());
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

    // 参数
    var params = {
        sourceNo: $("#selectFile").val(),
        pHashWeight: $("#pHash").val(),
        levenWeight: $("#levenShtein").val(),
        siftWeight: $("#sift").val(),
        kWeight: $("#k").val()
    };

    console.log(params);

    $.ajax({
        type: "POST",
        url: "ajax/compare.ajax",
        data: params,
        dataType: "json",
        success: function (data) {
            console.log(data);
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
            showImage(json[0]);
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
function showImage(fileNo) {
    $("#KChartTarget").find("img")[0].src = "data/image/" + fileNo + "-k.jpg";
    $("#amountTarget").find("img")[0].src = "data/image/" + fileNo + "-v.jpg";
}