var app = angular
		.module(
				'truckLoadForm',
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
	$routeProvider.when('/truckGoods', {
		templateUrl : '/lckywx/jsp/truckLoad/truckGoods.html',
		controller : 'TruckLoadController'
	}).when('/truckDriver', {
		templateUrl : '/lckywx/jsp/truckLoad/truckDriver.html',
		controller : 'TruckLoadController'
	}).when('/truckNeed', {
		templateUrl : '/lckywx/jsp/truckLoad/truckNeed.html',
		controller : 'TruckLoadController'
	}).when('/truckSend', {
		templateUrl : '/lckywx/jsp/truckLoad/truckSend.html',
		controller : 'TruckLoadController'
	})		
} ]);

app.constant('baseUrl', '/lckywx/');
app.factory('services', [ '$http', 'baseUrl', function($http, baseUrl) {
	var services = {};
	//添加司机货车信息
	services.addTruckDriver = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'truckLoad/addTruckDriver.do',
			data : data
		});
	};
	//添加货车货运信息
	services.addTruckSend = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'truckLoad/addTruckSend.do',
			data : data
		});
	};
	//添加货车货运信息
	services.addTruckNeed = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'truckLoad/addTruckNeed.do',
			data : data
		});
	};
	//获取货车信息
	services.selectTruckInfo = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'truckLoad/selectTruckInfo.do',
			data : data
		});
	};
	//获取货物信息
	services.selectGoodsInfo = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'truckLoad/selectGoodsInfo.do',
			data : data
		});
	};

	return services;
} ]);

app.controller('TruckLoadController', [ '$scope', 'services', '$location',
     function($scope, services, $location) {
         var truckDrSdNd = $scope;
             truckDrSdNd.truckLimit = {
                 trck_load : "",
                 is_freeze : "0"  // 0代表未冷冻，1代表冷冻
             }             
             // pg添加车主+货车信息
             truckDrSdNd.driverLimit = {
            	 driver_name : "",
                 driver_job : "",
            	 driver_tel : "",
            	 driver_idcard : "",
            	 driver_license_starttime : "",
            	 driver_license : "",
            	 driver_image : "",
            	 driver_car : ""
             }
             /*
                private Integer driver_id;//司机ID,主键
                private Integer is_audit;//0代表未审核，1代表已审核
              */
             
             truckDrSdNd.trseLimit = {
                 trse_left_load : "",
                 trse_splace : "洛川",
                 trse_eplace : "",
                 trse_price : "",
                 trse_time : ""
             }                
             truckDrSdNd.trneLimit = {
                 trne_name : "",
                 trne_tel : "",
                 trne_type : "",
                 trne_weight : "",
                 trne_splace : "洛川",
                 trne_eplace : "",
                 trne_time : "",
                 trne_remark : "",
                 is_freeze : "0"
             }
             truckDrSdNd.gotLimit={
				 startDate:"",
				 endDate:""
				}
             truckDrSdNd.gotLimits={
    				 startDate:"",
    				 endDate:""
    				}
             // pg添加货车+司机的信息
             truckDrSdNd.addTruckDriver = function() {
            	 console.log("你太调皮了");
                 var truckLimit = JSON.stringify(truckDrSdNd.truckLimit);
                 var driverLimit = JSON.stringify(truckDrSdNd.driverLimit);
                 console.log(truckLimit)
                     services.addTruckDriver({
                    	 truckInfo : truckLimit,
                    	 driverInfo : driverLimit
                     }).success(function(data) {
                         sessionStorage.setItem("trckId", data.result.trck_id);
                         sessionStorage.setItem("driverId", data.result.driver_id);
                         $location.path("truckDriver");
                     });
             }
             // pg添加货主需求信息
             truckDrSdNd.addTruckSend = function() {
            	 console.log("你太调皮了");
                 var truckLimit = JSON.stringify(truckDrSdNd.trseLimit);
                 console.log(truckLimit);
                     services.addTruckSend({
                    	 trseInfo : truckLimit
                     }).success(function(data) {
                         sessionStorage.setItem("trseId", data.result.trse_id);
                         $location.path("truckSend");
                     });
             }
             // pg添加发货需求信息
             truckDrSdNd.addTruckNeed = function() {
            	 console.log("你太调皮了");
                 var truckLimit = JSON.stringify(truckDrSdNd.trneLimit);
                     services.addTruckNeed({
                    	 trneInfo : truckLimit
                     }).success(function(data) {
                         sessionStorage.setItem("trneId", data.result.trne_id);
                         $location.path("truckNeed");
                     });
             }
             // 查询货车需求信息
             truckDrSdNd.selectTruckInfo = function() {
 				services.selectTruckInfo({
 					trk: gotLimit
 				}).success(function(data) {
 					truckDrSdNd.truckInfoList = data.list;
 				});
 			}
             // 查询货车需求信息
             truckDrSdNd.selectGoodsInfo = function() {
 				services.selectGoodsInfo({
 					goods: gotLimits
 				}).success(function(data) {
 					truckDrSdNd.goodsInfoList = data.list;
 				});
 			}
             // 零担货运页面初始化
             function initPage() {
                 console.log("初始化页面信息");
                 if ($location.path().indexOf('/truckDriver') == 0) {
                	 
                 } else if ($location.path().indexOf('/truckSend') == 0) {

                 } else if ($location.path().indexOf('/truckNeed') == 0) {
                
                 }else if ($location.path().indexOf('/truckGoods') == 0) {
              
                 }
             }
              initPage();
          } ]);


