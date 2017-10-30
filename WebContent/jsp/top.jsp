<!DOCTYPE html>
<html lang=zh-cmn-Hans>

<head>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width,initial-scale=1,user-scalable=0">
<title>洛川客运</title>
<link href="${ctx}/css/style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${ctx}/css/fonts/iconfont.css" />
<link rel="stylesheet" href="${ctx}/css/weui.min.css" />
<link rel="stylesheet" href="${ctx}/css/animate.css" />
<link rel="stylesheet" href="${ctx}/css/pages/ad-list.css" />
<script type="text/javascript" src="${ctx}/js/lib/jquery-1.9.1.min.js"></script>
</head>
            
<body class="bodyhe"> 
            
	<header>
            <div class="titlebar reverse">
                <a href="javascript:history.go(-1);">
                    <i class="iconfont">&#xe640;</i>
                </a>
                <h1>洛川客运</h1>
                <a href="/lckywx/routeController/toPlatformPage.do" class="app">
                    <i class="iconfont">&#xe643;</i>
                </a>
            </div>
		
	</header>
	<section>
		<!-- 加载模态框 -->
		<div class="overlayer"></div>
<!-- 菊花图开始 -->
<div class="containerloading" style="display:none;">      
     <div class="spinner">
         <div class="bar1"></div>
         <div class="bar2"></div>
         <div class="bar3"></div>
         <div class="bar4"></div>
         <div class="bar5"></div>
         <div class="bar6"></div>
         <div class="bar7"></div>
         <div class="bar8"></div>
         <div class="bar9"></div>
         <div class="bar10"></div>
         <div class="bar11"></div>
         <div class="bar12"></div>
     </div>
     <div class="base">数据加载中...</div>
</div>
<!-- 菊花图结束 -->
		<!-- 加载模态框 -->
		<script type="text/javascript"
			src="${ctx}/js/lib/jquery.json-2.2.min.js"></script>