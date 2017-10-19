var app = angular
		.module(
				'smallGoodsForm',
				[ 'ngRoute' ],
				function($httpProvider) {// ngRoute引入路由依赖
					$httpProvider.defaults.headers.put['Content-Type'] = 'application/x-www-form-urlencoded';
					$httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';

					// Override $http service's default transformRequest
					$httpProvider.defaults.transformRequest = [ function(data) {
						/**
						 * The workhorse; converts an object to
						 * x-www-form-urlencoded serialization.
						 * 
						 * @param {Object}
						 *            obj
						 * @return {String}
						 */
						var param = function(obj) {
							var query = '';
							var name, value, fullSubName, subName, subValue, innerObj, i;

							for (name in obj) {
								value = obj[name];

								if (value instanceof Array) {
									for (i = 0; i < value.length; ++i) {
										subValue = value[i];
										fullSubName = name + '[' + i + ']';
										innerObj = {};
										innerObj[fullSubName] = subValue;
										query += param(innerObj) + '&';
									}
								} else if (value instanceof Object) {
									for (subName in value) {
										subValue = value[subName];
										fullSubName = name + '[' + subName
												+ ']';
										innerObj = {};
										innerObj[fullSubName] = subValue;
										query += param(innerObj) + '&';
									}
								} else if (value !== undefined
										&& value !== null) {
									query += encodeURIComponent(name) + '='
											+ encodeURIComponent(value) + '&';
								}
							}

							return query.length ? query.substr(0,
									query.length - 1) : query;
						};

						return angular.isObject(data)
								&& String(data) !== '[object File]' ? param(data)
								: data;
					} ];
				});
app.run([ '$rootScope', '$location', function($rootScope, $location) {
	$rootScope.$on('$routeChangeSuccess', function(evt, next, previous) {
		console.log('路由跳转成功');
		$rootScope.$broadcast('reGetData');
	});
} ]);

//路由配置
app.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/smallGoods', {
		templateUrl : '/lckywx/jsp/smallGoods/smallGoods.html',
		controller : 'PlatformController'
	}).when('/smallGoodsInfo', {
		templateUrl : '/lckywx/jsp/smallGoods/smallGoodsInfo.html',
		controller : 'PlatformController'
	}).when('/smallGoodsList', {
		templateUrl : '/lckywx/jsp/smallGoods/smallGoodsList.html',
		controller : 'PlatformController'
	})
} ]);
app.constant('baseUrl', '/lckywx/');
app.factory('services', [ '$http', 'baseUrl', function($http, baseUrl) {
	var services = {};
	services.addSmallGoods = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'smallGoods/addSmallGoods.do',
			data : data
		});
	};
	services.selectSmallGoods = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'smallGoods/selectSmallGoods.do',
			data : data
		});
	};
	services.selectSmallGoodsInfo = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'smallGoods/selectSmallGoodsInfo.do',
			data : data
		});
	};
	
	return services;
} ]);
app
.controller(
		'PlatformController',
		[
				'$scope',
				'services',
				'$location',
				'$routeParams',
				function($scope, services, $location, $routeParams) {
					var smallGoods = $scope;
					
					smallGoods.GoLimit={
							smgo_name:"",
							smgo_weight:"",
							smgo_start:"",
							smgo_end:"",
							smgo_sender:"",
							smgo_sender_tel:"",
							smgo_receiver:"",
							smgo_receiver_tel:"",
							smgo_sego:"0",
							smgo_remark:"",
							smgo_send_time:""
					}
					
					smallGoods.GotLimit={
							startDate:"",
							endDate:""
					}
					smallGoods.checknum = function(element){
						if((/^1[34578]\d{9}$/.test(element)) 
								| /^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}$/.test(element)){
							console.log("jinalail2")
					    	$(".limitNum").css('display','none');
					    }else{
					    	console.log("jinalail")
							$(".limitNum").css('display','block');
							
					    }
						return ;
					}
					function compareDateTime(startDate, endDate) {
						var date1 = new Date(startDate);
						var date2 = new Date(endDate);
						if (date2.getTime() < date1.getTime()) {
							return true;
						} else {
							return false;
						}
					}
				    // 添加小件货运
					smallGoods.addSmallGoods=function() {
						var myDate = new Date();
						if(compareDateTime(myDate.toLocaleDateString(),smallGoods.GoLimit.smgo_send_time)){ return alert("选择时间")}
						var goLimit = JSON.stringify(smallGoods.GoLimit);					
						services.addSmallGoods({
							goNeed : goLimit
						}).success(function(data) {
							sessionStorage.setItem("smallGoodsId", data.result.smgo_id);
						 	$location.path('smallGoodsInfo');

						});
					}		
					// 获取小件货运
					smallGoods.selectSmallGoods=function() {
						var gotLimit = JSON.stringify(smallGoods.GotLimit);
						console.log(gotLimit);
						services.selectSmallGoods({
							gotNeed : gotLimit
						}).success(function(data) {
						smallGoods.smallGoodsList = data.list
						});
					}
					
					
					smallGoods.selectSmallGoodsInfo = function(smgo_id) {
						sessionStorage.setItem("smallGoodsId",smgo_id);
						
						 	$location.path('smallGoodsInfo');
					};
					
					
					//获取小件货运信息    
					function getSmallGoodsInfo(smallGoodsId) {
						var id=smallGoodsId;
						services.selectSmallGoodsInfo({
							smgo_id : id
						}).success(function(data) {
							$scope.sgIList=data.list;
						});
					};
				/*	
					//点击“提交并返回货运列表”要跳转的页面
					smallGoods.selectSmallGoods = function(smgo_select){
						sessionStorage.setItem("smallGoods",smgo_select);
						
						 	$location.path('smallGoodsList');
					};
					*/
					
					 $("input[name=radio]").each(function() {
					        $(this).click(function(){
					        	var smgoSego = $('#addSegoAdd');
								smgoSego.empty();
					            var discount = $(this).val();
					            if(discount=="1"){
					            	var $li = $("<li class='inner'></li>");
									var $divFir = $("<div class='item-name' style='display:inline;'></div>");
									$divFir.html("取货地址：<span><b>*</b></span>");
									var $divSco = $("<div class='item-value'></div>");
									var $divThi = $("<div class='p-wrap'></div>");
									var $input = $("<input type='text' ng-model='GoLimit.smgo_add' required>");
									$divThi.append($input);
									$divSco.append($divThi);
									$li.append($divFir);
									$li.append($divSco);
									smgoSego.append($li);
					            }
					        });
					        
					      //小件货运分栏
					        smallGoods.changeBar=function(state){
								switch(state){
								case 1:
									smallGoods.show={
										isActive1:true,
										isActive2:false,
										isActive3:false,
										isActive4:false
										

								}
									$("#table1").show();
									$("#table2").hide();
									break;
								case 2:
									smallGoods.show={
										isActive1:false,
										isActive2:true,
										isActive3:false,
										isActive4:false
								}
									$("#table1").hide();
									$("#table2").show();
									break;
								}
							}
					    });
						//获取滚动条当前的位置 
						function getScrollTop() {
							var scroll = 0;
							//判断哪个浏览器
							if (document.documentElement && document.documentElement.scrollTop) {
								scroll = $(".yscroll").scrollTop();  
							} else if (document.body) {
								scroll = $(".yscroll").scrollTop(); 
							}
							return scroll; 
						};
						
					    //获取当前可视范围的高度 
						function getClientHeight() {
							var clientHeight = 0; 
							//判断哪个浏览器
							if (document.body.clientHeight && document.documentElement.clientHeight) { 
								clientHeight = Math.min(document.body.clientHeight, document.documentElement.clientHeight); 
							} else { 
								clientHeight = Math.max(document.body.clientHeight, document.documentElement.clientHeight); 
							}
					    	return clientHeight; 
					   };

					   //获取文档完整的高度 
					   function getScrollHeight() {
						   var aaheight = $(".yscroll")[0].scrollHeight;
						   return Math.max($(".yscroll")[0].scrollHeight, document.documentElement.scrollHeight); 
					   }

					   function openScroll(getDate, config){
							var config = config ? config : {};
							var counter = 1;/*计数器*/

							/*第一次加载页面*/
							getDate(config, counter);
							
							/*通过自动监听滚动事件加载更多,可选支持*/
							config.isEnd = false; /*结束标志*/
							config.isAjax = false; /*防止滚动过快，服务端没来得及响应造成多次请求*/
							var t = 0;
							var p = 0;
							$("section").scroll(function(){
					        	 /*滚动加载时如果已经没有更多的数据了、正在发生请求时，不能继续进行*/
					        	if(config.isEnd == true || config.isAjax == true){
					          		return;
					        	}
					        	/*判断向上滚动或向下滚动*/
					        	p = getScrollTop()
					        	if(t <= p){
					        		t = p;
						        	/*当滚动到底部时， 加载新内容*/
						        	if (getScrollHeight()-(t + getClientHeight())<50) {
						        		counter ++;
						        		getDate && getDate(config, counter);
						        	}
					        	}
							});
					   }
					   
						function getDate(config, counter, list){
							config.isAjax = true;
							services.selectSmallGoods({
								page : counter
							}).success(function(data) {
								if(!smallGoods.smallGoodsList){
									smallGoods.smallGoodsList = [];
								}
								smallGoods.smallGoodsList = smallGoods.smallGoodsList.concat(data.list);
								console.log(data.list);
								config.isAjax = false;
								if(data.list == ![]){
									config.isEnd = true;
								}
							});
						}
										
					 
					// 初始化
					function initData() {
						console.log("初始化页面信息");
						
						if ($location.path().indexOf('/smallGoodsList') == 0) {
							
							console.log("你弄啥嘞？");
							services.selectSmallGoods({
								
							}).success(function(data) {
								smallGoods.smallGoodsList = data.list;
							});
						} else if($location.path().indexOf('/smallGoodsInfo') == 0){
							var smallGoodsId = sessionStorage.getItem("smallGoodsId");
							getSmallGoodsInfo(smallGoodsId);
						}
					}
					initData();
				} ]);

app
.controller(
		'SGInfoController',
		[
				'$scope',
				'services',
				'$location',
				'$routeParams',
				function($scope, services, $location,$routeParams) {
					$scope.sgIList=JSON.parse($routeParams.smallgoods);
				} ]);

//小件货运内容为空判断
app.filter('sgFilter',function() { 
	return function(input){ 
		if(input == "" || input == null){
			var input = "空";
			return input; 		
		}
		else{
			return input;
		}
	}
});
//时间的格式化的判断
app.filter('dateType', function() {
	return function(input) {
		var type = "";
		if (input) {
			type = new Date(input).toLocaleDateString().replace(/\//g, '-');
		}
		return type;
	}
});

