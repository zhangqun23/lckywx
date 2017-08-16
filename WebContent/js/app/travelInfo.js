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
	$routeProvider.when('/travelInfo', {
		templateUrl : '/lckywx/jsp/travelInfo/travelInfo.html',
		controller : 'PlatformController'  
	}).when('/travelInfoDetail/:travelInfo', {
		templateUrl : '/lckywx/jsp/travelInfo/travelInfoDetail.html',
		controller : 'travelInfoDetailController'
	}).when('/travelInfoList', {
		templateUrl : '/lckywx/jsp/travelInfo/travelInfoList.html',
		controller : 'PlatformController'
	}).when('/travelTrade', {
		templateUrl : '/lckywx/jsp/travelInfo/travelTrade.html',
		controller : 'PlatformController'
	})
} ]);

app.constant('baseUrl', '/lckywx/');
app.factory('services', [ '$http', 'baseUrl', function($http, baseUrl) {
	var services = {};
	
	// zq获取做房用时列表A
	services.addtravelInfo = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'travelInfo/addTravelInfo.do',
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
							var travelInfo = $scope;
							travelInfo.TravelLimit={
									 travel_title :"", // 标(主)题
									 travel_content :"", // 活动描述
									 travel_route :"", // 路线
									 travel_mprice :"", // 成人票价格
									 travel_cprice :"", // 儿童票价格
									 travel_insurance :"", // 保险费
									 travel_discount :"", // 折扣
									 travel_stime :"", // 出发时间
									 travel_location :"", // 出发地点
									 travel_days :"", // 游玩天数
									 travel_tel :"", // 联系电话
									 travel_total_num :"", // 总人数
									 travel_left_num :"", // 剩余人数
									 travel_firm :"" // 旅游承办公司
							}
							travelInfo.addtravelInfo=function(){
								var travelLimit = JSON
								.stringify(travelInfo.travelLimit);
								services.addtravelInfo({
									travelInfo : travelLimit
								}).success(function(data) {
									
									$location.path("travelInfoDetail/"+JSON.stringify(data.result));
									if (result.data) {
										alert("是");
									} else {
										alert("否");
									}
								});
							}
							travelInfo.toProducer = function () { 
								$location.path("#/travelInfoDetail");
							};
							
							// zq初始化
							function initData() {
								console.log("初始化页面信息");
								
								if ($location.path().indexOf('/travelInfo') == 0) {
									

								} else if ($location.path().indexOf(
										'/travelInfoDetail') == 0) {
									var producerId = $stateParams.producerId;
									alert(producerId);	
								} 
							}
							initData();
						} ]);

app
.controller(
		'travelInfoDetailController',
		[
				'$scope',
				'services',
				'$location',
				'$routeParams',
				function($scope, services, $location,$routeParams) {
					
					$scope.TInfo=JSON.parse($routeParams.travelInfo);
				
				} ]);

//时间的格式化的判断
app.filter('dateType', function() {
	return function(input) {
		console.log(input);
		var type = "";
		if (input) {
			type = new Date(input).toLocaleDateString().replace(/\//g, '-');
		}

		return type;
	}
});
//旅游活动内容的格式化的判断
app.filter('isOrNotNull', function() {
	return function(input) {
		var type = "";
		if (input) {
			type = input;
		}else{
			type="无";
		}

		return type;
	}
});





