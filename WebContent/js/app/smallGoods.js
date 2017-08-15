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
	
	return services;
} ]);
app
.controller(
		'PlatformController',
		[
				'$scope',
				'services',
				'$location',
				function($scope, services, $location) {
					var smallGoods = $scope;
					smallGoods.bb="das";
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
					smallGoods.addSmallGoods=function(){
						var goLimit = JSON
						.stringify(smallGoods.GoLimit);
						console.log("wang123"+goLimit);
						services.addSmallGoods({
							goNeed : goLimit
						}).success(function(data) {
							console.log("::::::::::::"+data);
							if (data) {
								alert("是");
							} else {
								alert("否");
							}
							console.log(data);
						 	$location.path('/smallGoodsInfo');

						});
					}
					
					// 初始化
					function initData() {
						console.log("初始化页面信息");
						
						if ($location.path().indexOf('/smallGoods') == 0) {
							

						} else if ($location.path().indexOf(
								'/indexPlat') == 0) {
							
						} 
					}
					initData();
				} ]);

