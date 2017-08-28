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
	}).when('/smallGoodsInfo/:smallgoods', {
		templateUrl : '/lckywx/jsp/smallGoods/smallGoodsInfo.html',
		controller : 'SGInfoController'
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
							smgo_remark:""	
					}
					smallGoods.GolLimit={
							smgo_select:""
					}
					smallGoods.addSmallGoods=function(){
						var goLimit = JSON
						.stringify(smallGoods.GoLimit);
						services.addSmallGoods({
							goNeed : goLimit
						}).success(function(data) {
							
						 	$location.path('smallGoodsInfo/'+data.result);

						});
					}
					
					smallGoods.selectSmallGoods=function(){
						var golLimit = JSON
						.stringify(smallGoods.GolLimit);
						services.selectSmallGoods({
							golNeed : golLimit
						}).success(function(data) {
							smallGoods.smallGoodsList = data.list;
						});
					}
					
					smallGoods.selectSmallGoodsInfo = function(smgo_id){
						services.selectSmallGoodsInfo({
							smgo_id : smgo_id
						}).success(function(data) {
						 	$location.path('smallGoodsInfo/'+JSON.stringify(data.list));

						});
					};
					
					 $("input[name=radio]").each(function(){
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
							services.selectSmallGoods({
								
							}).success(function(data) {
								smallGoods.smallGoodsList = data.list;
							});

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
app.filter('sgFilter',function(){ 
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

