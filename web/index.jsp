<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
	<script src="js/jquery-3.1.1.min.js"></script>
	<script src="bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
	<link href="css/myChart.css" rel="stylesheet">
	<script src="js/myChart.js"></script>
	<title>myChart</title>

</head>
<body>
<div class="selectFileDiv">
	<input type="file" id="inputFile" class="inline" onchange="handleFiles()" multiple="multiple">
	<div class="dropdown inline">
		<select id="selectFile" class="form-control selectFile" name="selectFile">

		</select>
	</div>
</div>
<div class="target">
	<div class="displayChart inline" id="KChartTarget">
		<p class="greyColor">K线图</p>
	</div>
	<div class="displayChart inline" id="amountTarget">
		<p class="greyColor">成交量 </p>
	</div>
</div>
<div class="selectButton">
	<label class="fontColor">算法1权重：</label>
	<input type="number" value="1" class="arith1 numberInput form-control margin-right" step="0.1" min="0" max="1">
	<label class="fontColor">算法2权重：</label>
	<input type="number" value="0" class="arith2 numberInput form-control margin-right" step="0.1" min="0" max="1">
	<label class="fontColor">算法3权重：</label>
	<input type="number" value="0" class="arith3 numberInput form-control margin-right" step="0.1" min="0" max="1">
	<label class="fontColor">K线权重：</label>
	<input type="number" value="0.5" class="numberInput form-control margin-right" step="0.1" min="0" max="1">
	<button type="button" onclick="calculate()" class="mybtn btn btn-default btn-sm margin-right">计算</button>
</div>
<div class="compare">

	<div class="displayChart inline" id="KChartTarget2">
		<p class="greyColor">K线图</p>
	</div>
	<div class="displayChart inline" id="amountTarget2">
		<p class="greyColor">成交量 </p>
	</div>
</div>
</body>
</html>