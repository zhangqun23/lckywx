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
									$divFir.html("取货地址：");
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
					    });
					
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

//
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

