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
	$routeProvider.when('/busNeedIndex', {
		templateUrl : '/lckywx/jsp/busNeed/busNeed.html',
		controller : 'BusNeedInfoController'
	}).when('/busNeedInfo', {
		templateUrl : '/lckywx/jsp/busNeed/busNeedInfo.html',
		controller : 'BusNeedInfoController'
	}).when('/busNeedList', {
		templateUrl : '/lckywx/jsp/busNeed/busNeedList.html',
		controller : 'BusNeedInfoController'
	}).when('/busTradeList', {
		templateUrl : '/lckywx/jsp/busNeed/busTradeList.html',
		controller : 'BusNeedInfoController'
	})
	.when('/busNeedTest', {
		templateUrl : '/lckywx/jsp/busNeed/busNeedTest.html',
		controller : 'BusNeedInfoController'
	})
} ]);

app.constant('baseUrl', '/lckywx/');
app.factory('services', [ '$http', 'baseUrl', function($http, baseUrl) {
	var services = {};
	// zq添加班车定制需求
	services.addBusNeed = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'busNeed/addBusNeed.do',
			data : data
		});
	};
	// zq查询班车定制需求列表
	services.selectBusNeeds = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'busNeed/selectBusNeed.do',
			data : data
		});
	};

	// zq根据班车预订Id查询班车定制详情
	services.selectBusNeedById = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'busNeed/selectBusNeedById.do',
			data : data
		});
	}
	return services;
} ]);
app.controller('BusNeedInfoController', [ '$scope', 'services', '$location',
		function($scope, services, $location) {
			var busNeed = $scope;
			busNeed.BusLimit = {
				bune_tel : "",
				bune_num : "",
				bune_time : "",
				bune_gath_time : "",
				bune_gath_pla : "",
				bune_goal_pla : "",
				bune_purp : "",
				bune_remark : ""
			}
			// zq添加班车需求
			busNeed.addBusNeed = function() {
				var busLimit = JSON.stringify(busNeed.BusLimit);
				services.addBusNeed({
					busNeed : busLimit
				}).success(function(data) {
					sessionStorage.setItem("busNeedId", data.result.bune_id);
					$location.path("busNeedInfo");
				});
			}
			// zq查询班车需求列表
			busNeed.selectBusNeeds = function() {
				services.selectBusNeeds({
					startDate : busNeed.startDate,
					endDate : busNeed.endDate
				}).success(function(data) {
					busNeed.busNeedList = data.list;
				});
			}
			// zq查询班车定制需求
			busNeed.getBusNeedById = function(bunId) {
				sessionStorage.setItem("busNeedId", bunId);
				$location.path("busNeedInfo");
			}
			// 根据ID获取班车定需求
			busNeed.selectBusNeedById = function(bunId) {
				services.selectBusNeedById({
					busNeed_id : bunId
				}).success(function(data) {
					busNeed.BNeed = data.busNeed;
				});
			}
			//修改分栏
			busNeed.changeBar=function(state){
				switch(state){
				case 1:
					busNeed.show={
						isActive1:true,
						isActive2:false,
						isActive3:false,
						isActive4:false
				}
					
					break;
				case 2:
					busNeed.show={
						isActive1:false,
						isActive2:true,
						isActive3:false,
						isActive4:false
				}
					break;
				case  3:
					busNeed.show={
						isActive1:false,
						isActive2:false,
						isActive3:true,
						isActive4:false
				}
					break;
				case  4:
					busNeed.show={
						isActive1:false,
						isActive2:false,
						isActive3:false,
						isActive4:true
				}
					break;
				}
			}
			// zq初始化
			function initPage() {
				console.log("初始化页面信息");
				if ($location.path().indexOf('/busNeedIndex') == 0) {
		
				} else if ($location.path().indexOf('/busNeedList') == 0) {
					busNeed.selectBusNeeds();
				} else if ($location.path().indexOf('/busNeedInfo') == 0) {
					var busNeedId = sessionStorage.getItem("busNeedId");
					busNeed.selectBusNeedById(busNeedId);
				}else if ($location.path().indexOf('/busNeedTest') == 0) {
					busNeed.show={
							isActive1:true,
							isActive1:false,
							isActive1:false,
							isActive1:false
					}
				}
			}
			initPage();
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
// 时间的格式化的判断
app.filter('isOrNotNull', function() {
	return function(input) {
		var type = "";
		if (input) {
			type = input;
		} else {
			type = "无";
		}

		return type;
	}
});
