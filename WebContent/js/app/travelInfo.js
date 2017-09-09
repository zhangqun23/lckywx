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
	
	services.selectTravelInfoById = function(data){
		return $http({
			method : 'post' ,
			url : baseUrl + 'travelInfo/selectTravelInfoById.do',
			data : data
		});
	};
	
	services.addTravelTrade = function(data){
		return $http({
			method : 'post' ,
			url : baseUrl + 'pay/requestPay.do',
			data : data
		});
	}
	
	services.saveTravelTrade = function(data){
		return $http({
			method : 'post' ,
			url : baseUrl + 'travelInfo/saveTravelTrade.do',
			data : data
		});
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
				$location.path("getTravelInfoDetail/");
			}
			
/*			travelInfo.addTravelTrade = function(){
				var payLimit = JSON.stringify(travelInfo.TradeLimit);
				var travel_id = sessionStorage.getItem("travel_id_buy");
				services.addTravelTrade({
					payNeed : payLimit,
					travelid : travel_id
				}).success(function onBridgeReady(data){
		            	var tt=JSON.parse(data);
						   WeixinJSBridge.invoke(
							'getBrandWCPayRequest', {
							    "appId":tt.appId,     //公众号名称，由商户传入     
							    "timeStamp":tt.timeStamp,//时间戳，自1970年以来的秒数     
							    "nonceStr":tt.nonceStr, //随机串     
							    "package":"prepay_id="+tt.pg,
							    "signType":"MD5",//微信签名方式：     
							    "paySign":tt.paySign //微信签名 
						   },
						   function(res){
							// 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回ok，但并不保证它绝对可靠。 
						       if(res.err_msg == "get_brand_wcpay_request:ok" ) {
						    	   services.saveTravelTrade({
						    		   tradeNeed : payLimit,
						    		   out_trade_no : tt.out_trade_no,
						    		   total_fee : tt.total_fee,
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
				
			}*/
			
			travelInfo.addTravelTrade = function(){
				var payLimit = JSON.stringify(travelInfo.TradeLimit);
				var travel_id = sessionStorage.getItem("travel_id_buy");
				services.addTravelTrade({
					payNeed : payLimit,
					travelid : travel_id
				}).success( function(data){
					if (data.e != null){alert(data.e)}
					else{
						services.saveTravelTrade({
				    		   tradeNeed : payLimit,
				    		   out_trade_no : data.out_trade_no,
				    		   total_fee : data.total_fee,
				    		   travelidbuy : sessionStorage.getItem("travel_id_buy")
				    	   }).success(function (data){
				    		   alert("成功保存")
					})
					}
				})
			}
				

			// zq初始化
			function initData() {
				console.log("初始化页面信息");

				if ($location.path().indexOf('/travelInfo') == 0) {
					console.log("进入到旅游信息界面");
					services.selectTravelInfo({

					}).success(function(data) {
						travelInfo.travelList = data.list;
					});
				} else if($location.path().indexOf('/getTravelInfoDetail') ==0){
					services.selectTravelInfoById({
						travel_id_select : sessionStorage.getItem("travel_id_select")
					}).success(function(data){
						$scope.TInfo = data.list
					});
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
app.filter('travelFilter', function() {
	return function(input) {
		if (input == "") {
			var input = "空";
			return input;
		} else {
			return input;
		}
	}
});
// 旅游交易输入判断
app.filter('trtrFilter', function() {
	return function(input) {
		if (input == "") {
			var input = "空";
			return input;
		} else {
			return input;
		}
	}
});

//截取任务内容
app.filter('cutString', function() {
	return function(input) {
		var content = "";
		if (input != "") {
			var shortInput = input.substr(0, 2);
			content = shortInput + "……";
			}
		return content;	
	}
});


