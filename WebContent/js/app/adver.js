var app = angular
		.module(
				'adverForm',
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
	$routeProvider.when('/selectAdver', {
		templateUrl : '/lckywx/jsp/adver/selectAdver.html',
		controller : 'PlatformController'
	})
} ]);
app.constant('baseUrl', '/lckywx/');
app.factory('services', [ '$http', 'baseUrl', function($http, baseUrl) {
	var services = {};
	services.selectAdver = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'ad/selectAdver.do',
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
					var adver = $scope;
					adver.ADLimit={
						adverType:""
					}
					/*smallGoods.addSmallGoods=function(){
						var goLimit = JSON
						.stringify(smallGoods.GoLimit);
						services.addSmallGoods({
							goNeed : goLimit
						}).success(function(data) {
							console.log("::::::::::::"+data);
							if (data) {
								alert("是");
							} else {
								alert("否");
							}
						 	$location.path('smallGoodsInfo/'+data);

						});
					}*/
					adver.selectAdver=function(){
						
						services.selectAdver({
							adType : "123"
						}).success(function(data) {
							adver.adList = data.list;
						});
					}
					
					// 初始化
					function initData() {
						console.log("初始化页面信息");
						
						if ($location.path().indexOf('/selectAdver') == 0) {
							console.log("进来了1");
							services.selectAdver({
								adType : "123"
							}).success(function(data) {
								adver.adList = data.list;
							});


						} 
					}
					initData();
				} ]);