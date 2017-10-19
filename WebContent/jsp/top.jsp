<!DOCTYPE html>
<html lang=zh-cmn-Hans>

<head>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width,initial-scale=1,user-scalable=0">
<title>合同信息管理</title>
<link href="${ctx}/css/style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${ctx}/css/fonts/iconfont.css" />
<link rel="stylesheet" href="${ctx}/css/font.css" />
<link rel="stylesheet" href="${ctx}/css/weui.min.css" />
<link rel="stylesheet" href="${ctx}/css/jquery-weui.min.css" />
<link rel="stylesheet" href="${ctx}/css/mui.css" />
<link rel="stylesheet" href="${ctx}/css/animate.css" />
<link rel="stylesheet" href="${ctx}/css/pages/order-list.css" />
 <link rel="stylesheet" href="${ctx}/css/pages/order-detail.css" /> 
<link rel="stylesheet" href="${ctx}/css/pages/ad-list.css" />
<link rel="stylesheet" href="${ctx}/css/pages/form-detail.css" />
<link rel="stylesheet" href="${ctx}/css/pages/info-detail.css" />
 <link href="${ctx}/css/pages/app.css" rel="stylesheet" type="text/css" />
 <link href="${ctx}/css/pages/branch.css" rel="stylesheet" type="text/css" />
  <link href="${ctx}/css/pages/page.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/js/lib/jquery-1.9.1.min.js"></script>
</head>
            
<body class="bodyhe">

<!--  <div id="loadingToast" class="weui_loading_toast" style="display:none;">
                <div class="weui_mask_transparent"></div>
                <div class="weui_toast">
                    <div class="weui_loading">
                        <div class="weui_loading_leaf weui_loading_leaf_0"></div>
                        <div class="weui_loading_leaf weui_loading_leaf_1"></div>
                        <div class="weui_loading_leaf weui_loading_leaf_2"></div>
                        <div class="weui_loading_leaf weui_loading_leaf_3"></div>
                        <div class="weui_loading_leaf weui_loading_leaf_4"></div>
                        <div class="weui_loading_leaf weui_loading_leaf_5"></div>
                        <div class="weui_loading_leaf weui_loading_leaf_6"></div>
                        <div class="weui_loading_leaf weui_loading_leaf_7"></div>
                        <div class="weui_loading_leaf weui_loading_leaf_8"></div>
                        <div class="weui_loading_leaf weui_loading_leaf_9"></div>
                        <div class="weui_loading_leaf weui_loading_leaf_10"></div>
                        <div class="weui_loading_leaf weui_loading_leaf_11"></div>
                    </div>
                    <p class="weui_toast_content">数据加载中</p>
                </div>
            </div> --> 
            
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
		<div class="tipLoading">
			<img class="tipimage" src="../images/wait.gif" />
			<div class="tiptext">正在加载，请稍后……</div>
		</div>
		<!-- 加载模态框 -->
		<script type="text/javascript"
			src="${ctx}/js/lib/jquery.json-2.2.min.js"></script>