var app = angular
		.module(
				'travelInfoForm',
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

// 路由配置
app.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/travelInfo', { // 页面初始为travelInfo时加载内容
		templateUrl : '/lckywx/jsp/travelInfo/travelInfo.html', // 显示的内容
		controller : 'PlatformController' // 控制器
	}).when('/getTravelInfoDetail', {
		templateUrl : '/lckywx/jsp/travelInfo/travelInfoDetail.html',
		controller : 'PlatformController'
	}).when('/getTravelTravelDetail', {
		templateUrl : '/lckywx/jsp/travelInfo/travelTrade.html',
		controller : 'PlatformController'
	}).when('/myTravelTrade', {
		templateUrl : '/lckywx/jsp/travelInfo/myTravelTrade.html',
		controller : 'PlatformController'
	}).when('/detailmyTravelTrade', {
		templateUrl : '/lckywx/jsp/travelInfo/myTravelTradeDetail.html',
		controller : 'PlatformController'
	}).when('/enSure', {
		templateUrl : '/lckywx/jsp/travelInfo/enSure.html',
		controller : 'PlatformController'
	})
} ]);

app.constant('baseUrl', '/lckywx/');
app.factory('services', [ '$http', 'baseUrl', function($http, baseUrl) {
	var services = {};

	services.selectTravelInfo = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'travelInfo/selectTravelInfo.do',
			data : data
		});
	};

	services.selectTravelInfoById = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'travelInfo/selectTravelInfoById.do',
			data : data
		});
	};

	services.addTravelTrade = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'pay/travelPay.do',
			data : data
		});
	}

	services.saveTravelTrade = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'travelInfo/saveTravelTrade.do',
			data : data
		});
	}

	services.selectMyTravelInfoByOId = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'travelInfo/selectMyTravelInfoByOId.do',
			data : data
		});
	}

	services.travelRefundPay = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'pay/travelRefundPay.do',
			data : data
		})
	}

	return services;
} ]);


app.controller('PlatformController', [
		'$scope',
		'services',
		'$location',
		function($scope, services, $location) { // 页面控制函数
			var travelInfo = $scope;
			travelInfo.TradeLimit = {
					trtr_tel:"",
					trtr_mnum:"",
					trtr_cnum:""
			}
			
			travelInfo.getTravelTradeById = function(travel_id){
				sessionStorage.setItem("travel_id_buy",travel_id);
				$location.path("getTravelTravelDetail/");
			}
			
			travelInfo.getTravelInfoById = function(travel_id) {
				sessionStorage.setItem("travel_id_select",travel_id);
				$location.path("getTravelInfoDetail");
			}
			
			travelInfo.getMyTravelTradeById = function(travelTrade){
				sessionStorage.setItem("travelTrade",JSON.stringify(travelTrade));
				$location.path("detailmyTravelTrade");
			}
			
			travelInfo.addTravelTrade = function(){
				console.log(travelInfo.TradeLimit);
				if (travelInfo.TradeLimit.trtr_tel == undefined){
					alert("请输入正确的手机号码");
					return
				}
				if (isNaN(travelInfo.TradeLimit.trtr_mnum)){
					alert("请输入正确的成人票数");
					return
				}
				if (isNaN(travelInfo.TradeLimit.trtr_cnum) && travelInfo.TradeLimit.trtr_cnum !=""){
					alert("请输入正确的儿童票数");
					return
				}
				$('.containerloading').fadeIn(100);
				$('.overlayer').fadeIn(100);
				var payLimit = JSON.stringify(travelInfo.TradeLimit);
				var travel_id = sessionStorage.getItem("travel_id_buy");
				services.addTravelTrade({
					payNeed : payLimit,
					travelid : travel_id
				}).success(function onBridgeReady(data){
					if ($('.containerloading').css('display') == 'block'){
						$('.containerloading').fadeOut(100);
				    	$('.overlayer').fadeOut(100);
					}
					alert(data.appId);
					alert(data.timeStamp);
		            	if(data.e != undefined){
		            		alert(data.e);
		            		return;
		            	}
						   WeixinJSBridge.invoke(
							'getBrandWCPayRequest', {
							    "appId":data.appId,     //公众号名称，由商户传入     
							    "timeStamp":data.timeStamp,//时间戳，自1970年以来的秒数     
							    "nonceStr":data.nonceStr, //随机串     
							    "package":"prepay_id="+data.pg,
							    "signType":"MD5",//微信签名方式：     
							    "paySign":data.paySign //微信签名 
						   },
						   function(res){
							// 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回ok，但并不保证它绝对可靠。 
						       if(res.err_msg == "get_brand_wcpay_request:ok" ) {
						    	   services.saveTravelTrade({
						    		   tradeNeed : payLimit,
						    		   out_trade_no : data.out_trade_no,
						    		   total_fee : data.total_fee,
						    		   travelidbuy : sessionStorage.getItem("travel_id_buy")
						    	   }).success(function (data){
						    		   
						    	   })
						       }
						   else if(res.err_msg == "get_brand_wcpay_request:fail"){
							   alert("没有成功fail")
						   }else if(res.err_msg == "get_brand_wcpay_request:cancel"){
							   alert("没有成功cancel")
							           }
								})
						   if (typeof WeixinJSBridge == "undefined"){
						   if( document.addEventListener ){
						       document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
						   }else if (document.attachEvent){
						       document.attachEvent('WeixinJSBridgeReady', onBridgeReady); 
						   document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
						   }
						}else{
						   onBridgeReady();
						}
		            
				})
				
			}
			
/*			travelInfo.addTravelTrade = function(){
				var payLimit = JSON.stringify(travelInfo.TradeLimit);
				var travel_id = sessionStorage.getItem("travel_id_buy");
				services.addTravelTrade({
					payNeed : payLimit,
					travelid : travel_id
				}).success( function(data){
					if (data.e != null) {
						alert(data.e)
						return;
					}
					sessionStorage.setItem("travelTrade",JSON.stringify(data.trTrade));
					console.log(JSON.stringify(data.trTrade));
					$location.path("enSure");

				})
			}*/
			

			travelInfo.traderefundpay = function(travel_trade_id) {
				if(confirm("是否确定退款，将扣除20%手续费")){
					$('.containerloading').fadeIn(100);
					$('.overlayer').fadeIn(100);
					services.travelRefundPay({
						travel_trade_id : travel_trade_id
					}).success(function(data){
						if ($('.containerloading').css('display') == 'block'){
							$('.containerloading').fadeOut(100);
						    $('.overlayer').fadeOut(100);
						}
						if(data.e != null){
							alert(data.e)
							return;
						}
						$location.path("enSure");
					})
				}
			}
		
			
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

		   function openScroll(getDate, config , state){
				var config = config ? config : {};
				var counter = 1;/*计数器*/

				/*第一次加载页面*/
				getDate(config, counter, state);
				$('.containerloading').fadeIn(100);
				$('.overlayer').fadeIn(100);
				
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
			        		getDate && getDate(config, counter, state);
			        		$('.loading-loading').fadeIn(100);
			        	}
		        	}
				});
		   }
		   
			function getTravelList(config, counter){
				config.isAjax = true;
				services.selectTravelInfo({
					page : counter
				}).success(function(data) {
					if ($('.containerloading').css('display') == 'block'){
						$('.containerloading').fadeOut(100);
					    $('.overlayer').fadeOut(100);
					}
				    if ($('.loading-loading').css('display') == 'block'){
				    	$('.loading-loading').fadeOut(100);
				    };
					if(!travelInfo.travelList){
						travelInfo.travelList = [];
					}
					travelInfo.travelList = travelInfo.travelList.concat(data.list);
					config.isAjax = false;
					if(data.list == ![]){
						$(".limitHint").css('display','block');
						config.isEnd = true;
					}
				});
			}
			
			function getMyTravelList(config, counter, state){
				config.isAjax = true;
				services.selectMyTravelInfoByOId({
					state : state,
					page : counter
				}).success(function(data) {
					if(!travelInfo.MTTInfo){
						travelInfo.MTTInfo = [];
					}
					if(typeof(data.list) != "undefined"){
						travelInfo.MTTInfo = travelInfo.MTTInfo.concat(data.list);
					}
					config.isAjax = false;
					if ($('.containerloading').css('display') == 'block'){
						$('.containerloading').fadeOut(100);
					    $('.overlayer').fadeOut(100);
					}
					if(data.list == ![] || data.list == undefined){
						$(".limitHint").css('display','block');
						config.isEnd = true;
					}
				});
			}
			
			travelInfo.changeBar=function(state){
				switch(state){
				case 1:
					travelInfo.show={
						isActive1:true,
						isActive2:false,
						isActive3:false
				}
					travelInfo.MTTInfo = null;
					openScroll(getMyTravelList, {}, 1);
					break;
				/*case 1:
					travelInfo.show={
						isActive1:false,
						isActive2:true,
						isActive3:false
				}
					travelInfo.MTTInfo = null;
					openScroll(getMyTravelList, {} , 1);
					break;*/
				case 2:
					travelInfo.show={
						isActive1:false,
						isActive2:false,
						isActive3:true
				}
					travelInfo.MTTInfo = null;
					openScroll(getMyTravelList, {} , 2);
					break;
				}
			}
			
			// zq确定交易跳转到我的交易列表
			travelInfo.redirectToMyList = function() {
				$location.path("myTravelTrade");
			}
			// zq获取交易详情
			travelInfo.selectTarvelTradeInfo = function() {		
				$location.path("detailmyTravelTrade");
			}
			
			// zq初始化
			function initData() {
				console.log("初始化页面信息");

				if ($location.path().indexOf('/travelInfo') == 0) {
					console.log("进入到旅游信息界面");
					openScroll(getTravelList, {});
				} else if ($location.path().indexOf('/getTravelInfoDetail') == 0) {
					$('.containerloading').fadeIn(100);
				    $('.overlayer').fadeIn(100);
					services.selectTravelInfoById({
							travel_id_select : sessionStorage.getItem("travel_id_select")
						}).success(function(data) {
							if ($('.containerloading').css('display') == 'block'){
								$('.containerloading').fadeOut(100);
							    $('.overlayer').fadeOut(100);
							}
							$scope.TInfo = data.list
						});
				} else if ($location.path().indexOf('/myTravelTrade') == 0) {
					openScroll(getMyTravelList, {}, 1);
				} else if ($location.path().indexOf('/detailmyTravelTrade') == 0) {
					$scope.MMTT = JSON.parse(sessionStorage.getItem("travelTrade"));
					if ($scope.MMTT.is_state == 1) {
						$("#hasPaied").css('display', 'block');
						$("#refund-pay").css('display', 'block');
					} else if($scope.MMTT.is_state == 2){
						$("#hasRefunded").css('display', 'block');
					} 
				}
			}
			initData();
		} ]);

// 时间的格式化的判断
app.filter('dateType', function() {
	return function(input) {
		var type = "";
		if (input) {
			type = new Date(input).toLocaleDateString().replace(/\//g, '-');
		}

		return type;
	}
});
// 旅游活动内容的格式化的判断
app.filter('trtrFilter', function() {
	return function(input) {
		if (input == "" || input == null) {
			var input = "空";
			return input;
		} else {
			return input;
		}
	}
});
// 旅游活动内容的格式化的判断
app.filter('stateFilter', function() {
	return function(input) {
		switch (input) {
		case 0:
			var input = "待付款";
			return input;
		case 1:
			var input = "已付款";
			return input;
		case 2:
			var input = "已退款";
			return input;
		}
	}
});
// 截取任务内容
app.filter('cutString', function() {
	return function(input) {
		var content = "";
		if (input != "") {
			var shortInput = input.substr(0, 10);
			content = shortInput + "……";
		}
		return content;
	}
});
//小数过滤器
app.filter('cutFloat', function() {
	return function(input) {
		if (!input) {
			var money = parseFloat('0').toFixed(2);
		} else {
			var money = parseFloat(input).toFixed(2);
		}

		return money;
	}
});
