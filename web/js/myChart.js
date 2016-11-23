$(function () {
    $(".numberInput").keyup(numberLimit);
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
}