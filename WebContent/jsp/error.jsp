<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title></title>
<style type="text/css">
div{
width:30%;
margin:10% auto;
text-align:center;
}
img{
	width:50%;
	margin:auto;
}
.content{
	width:100%;
font-family: '微软雅黑';
font-size: 25pt;
text-align:center;
padding: 0;
margin:0 auto;
}
</style>
</head>
<body>
<div>
	<img src="/lckywx/images/qrcode.jpg">
	<div class="content">请打开微信，点开扫一扫，关注公众号后即可完成操作</div>
</div>
</body>
</html>