var app = angular
		.module(
				'busNeedForm',
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
	$routeProvider.when('/busNeed', {
		templateUrl : '/lckywx/jsp/busNeed/busNeed.html',
		controller : 'PlatformController'
	})
} ]);

app.constant('baseUrl', '/lckywx/');
app.factory('services', [ '$http', 'baseUrl', function($http, baseUrl) {
	var services = {};
	// zq获取做房用时列表A
	services.addBusNeed = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'busNeed/addBusNeed.do',
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
							var busNeed = $scope;
							busNeed.BusLimit={
									bune_tel:"",
									bune_num:"",
									bune_time:"",
									bune_gath_time:"",
									bune_gath_pla:"",
									bune_goal_pla:"",
									bune_purp:"",
									bune_remark:""
							}
							busNeed.addBusNeed=function(){
								var busLimit = JSON
								.stringify(busNeed.BusLimit);
								console.log("zhangqun"+busLimit);
								
								services.addBusNeed({
									busNeed : busLimit
								}).success(function(data) {
									console.log("::::::::::::"+data);
									if (data) {
										alert("是");
									} else {
										alert("否");
									}
								});
							}
							// zq初始化
							function initData() {
								console.log("初始化页面信息");
								
								if ($location.path().indexOf('/workHouseForm') == 0) {
									

								} else if ($location.path().indexOf(
										'/indexPlat') == 0) {
									
								} 
							}
							initData();
						} ]);

