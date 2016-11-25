<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<script src="js/jquery-3.1.1.min.js"></script>
	<script src="bootstrap/js/bootstrap.min.js"></script>
	<link href="css/myChart.css" rel="stylesheet">
	<script src="js/myChart.js"></script>
    <title>KChart</title>
</head>
<body>
<div class="selectFileDiv">
    <button id="inputFile" class="import-btn btn btn-default" onclick="loadImages()">导入数据集</button>
	<div class="dropdown inline">
		<select id="selectFile" class="form-control selectFile" name="selectFile">
            <!-- 这里是所有的数据集合列表 -->
		</select>
	</div>
</div>
<div class="target">
	<div class="displayChart inline" id="KChartTarget">
        <img src="" alt="K线图" width="100%" height="100%">
	</div>
	<div class="displayChart inline" id="amountTarget">
        <img src="" alt="成交量" width="100%" height="100%">
	</div>
</div>
<div class="selectButton">
    <label class="fontColor">PHash算法权重：</label>
    <input type="number" id="pHash" value="1" class="arith1 numberInput form-control margin-right" step="0.1" min="0"
           max="1">
    <label class="fontColor">LevenShtein算法权重：</label>
    <input type="number" id="levenShtein" value="0" class="arith2 numberInput form-control margin-right" step="0.1"
           min="0" max="1">
    <label class="fontColor">Sift算法权重：</label>
    <input type="number" id="sift" value="0" class="arith3 numberInput form-control margin-right" step="0.1" min="0"
           max="1">
	<label class="fontColor">K线权重：</label>
    <input type="number" id="k" value="0.5" class="numberInput form-control margin-right" step="0.1" min="0" max="1">
    <button type="button" id="calBtn" onclick="calculate()" class="mybtn btn btn-default btn-sm margin-right">计算
    </button>
</div>
<div class="compare">

	<div class="displayChart inline" id="KChartTarget2">
        <img src="" alt="K线图" width="100%" height="100%">
    </div>
	<div class="displayChart inline" id="amountTarget2">
        <img src="" alt="成交量" width="100%" height="100%">
    </div>
</div>
</body>
</html>