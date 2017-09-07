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
	$routeProvider.when('/truckDriver', {
		templateUrl : '/lckywx/jsp/truckDriver/truckDriver.html',
		controller : 'TruckLoadController'
	}).when('/truckNeed', {
		templateUrl : '/lckywx/jsp/truckNeed/truckNeed.html',
		controller : 'TruckLoadController'
	}).when('/truckSend', {
		templateUrl : '/lckywx/jsp/truckSend/truckSend.html',
		controller : 'TruckLoadController'
	})		
} ]);

app.constant('baseUrl', '/lckywx/');
app.factory('services', [ '$http', 'baseUrl', function($http, baseUrl) {
	var services = {};
	
	services.addTruckDriver = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'truckDriver/addTruckDriver.do',
			data : data
		});
	};
	//添加货车货运信息
	services.addTruckSend = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'truckSend/addTruckSend.do',
			data : data
		});
	};
	//添加货车货运信息
	services.addTruckNeed = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'truckNeed/addTruckNeed.do',
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
                 is_freeze : ""
             }                   
             //private Integer is_freeze;//0代表未冷冻，1代表冷冻
             
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
                 trse_splace : "",
                 trse_eplace : "",
                 trse_price : "",
                 trse_time : ""
             } 
                 
             truckDrSdNd.trneLimit = {
                 trne_name : "",
                 trne_tel : "",
                 trne_type : "",
                 trne_weight : "",
                 trne_splace : "",
                 trne_eplace : "",
                 trne_time : "",
                 trne_remark : "",
                 is_freeze : ""
             }
             // pg添加货车+货车的信息
             truckDriver.addtruckDriver = function() {
            	 console.log("你太调皮了");
                 var truckLimit = JSON.stringify(truckDriver.truckLimit);
                 var driverLimit = JSON.stringify(truckDriver.driverLimit);
                     services.addtruckDriver({
                    	 truckInfo : truckLimit,
                    	 driverInfo : driverLimit
                     }).success(function(data) {
                         sessionStorage.setItem("trckId", data.result.trck_id);
                         sessionStorage.setItem("driverId", data.result.driver_id);
                         $location.path("truckDriver");
                     });
             }
            
             
             // 零担货运页面初始化
             function initPage() {
                 console.log("初始化页面信息");
                 if ($location.path().indexOf('/truckDriver') == 0) {
                                  		
                 } else if ($location.path().indexOf('/truckNeed') == 0) {

                 } else if ($location.path().indexOf('/truckSend') == 0) {
                 
                 }
             }
              initPage();
          } ]);


