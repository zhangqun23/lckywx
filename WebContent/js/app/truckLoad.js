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
	}).when('/selectTruckGoods', {
		templateUrl : '/lckywx/jsp/truckLoad/selectTruckGoods.html',
		controller : 'TruckLoadController'
	}).when('/selectTruckNeed', {
		templateUrl : '/lckywx/jsp/truckLoad/selectTruckNeed.html',
		controller : 'TruckLoadController'
	}).when('/selectTruckSend', {
		templateUrl : '/lckywx/jsp/truckLoad/selectTruckSend.html',
		controller : 'TruckLoadController'
	}).when('/truckSendInfo', {
		templateUrl : '/lckywx/jsp/truckLoad/truckSendInfo.html',
		controller : 'TruckLoadController'
	}).when('/truckNeedInfo', {
		templateUrl : '/lckywx/jsp/truckLoad/truckNeedInfo.html',
		controller : 'TruckLoadController'
	}).when('/modifyTruckSend', {
		templateUrl : '/lckywx/jsp/truckLoad/modifyTruckSend.html',
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
	//车主查询货主信息
	services.selectTruckNeed =  function(data){
		return $http({
			method : 'post',
			url : baseUrl + 'truckLoad/selectTruckNeed.do',
			data : data
		});
	};
	services.selectTruckSend = function(data){
		return $http({
			method : 'post',
			url : baseUrl + 'truckLoad/selectTruckSend.do',
			data : data
		});
	};
	services.selectTruckSendById = function(data){
		return $http({
			method : 'post',
			url : baseUrl +'truckLoad/selectTruckSendById.do',
			data : data
		});
	};
	services.selectTruckNeedById = function(data){
		return $http({
			method : 'post',
			url : baseUrl + 'truckLoad/selectTruckNeedById.do',
			data : data
		})
	};
	services.modifyTruckSend = function(data){
		return $http({
			method : 'post',
			url : baseUrl + 'truckLoad/modifyTruckSend.do',
			data : data
		})
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
            	 driver_license_number : "",           	 
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
            /* truckDrSdNd.gotLimit={
				 startDate:"",
				 endDate:""
				}
             truckDrSdNd.gotLimits={
    				 startDate:"",
    				 endDate:""
    				}*/
 			function compareDateTime(starTime,endTime){
 				var date1 = new Date(starTime);
 				var date2 = new Date(endTime);
 				if(date1.getTime()<date2.getTime()){
 					return true;
 				}else{
 					return false;
 				}
 			}
             // pg添加货车+司机的信息
             truckDrSdNd.addTruckDriver = function() {
            	 console.log("你太调皮了");
 				/*var myDate = new Date();
				if(compareDateTime(mtDate.toLocaleDateString(),driverLimit.driver_license_starttime)){return alert("请填写正确时间")}*/
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
             //车主查询货主信息
             truckDrSdNd.selectTruckNeed = function() {               
            	 services.selectTruckNeed({
            		 startTime : truckDrSdNd.startTime,
            		 endTime : truckDrSdNd.endTime,
            		 trne_eplace : truckDrSdNd.trne_eplace
            	 }).success(function(data){
            		 truckDrSdNd.selectTruckNeedList = data.list;
            	 });
             }
             //货主查询车主信息
             truckDrSdNd.selectTruckSend = function() {               
            	 services.selectTruckSend({
            		 startTime : truckDrSdNd.startTime,
            		 endTime : truckDrSdNd.endTime,
            		 trse_eplace : truckDrSdNd.trse_eplace
            	 }).success(function(data){
            		 truckDrSdNd.selectTruckSendList = data.list;
            		 $location.path("selectTruckSend");         
            	 });
             }
             //根据trse_id获得车主信息(先获得Id后查询)
             truckDrSdNd.getTruckSendById = function (trse_id){           	 
            	 sessionStorage.setItem("trse_id",trse_id);
            	 $location.path('truckSendInfo');
             }
             truckDrSdNd.selectTruckSendById = function (trse_id){          
            	 services.selectTruckSendById({
            		 trse_id : trse_id
            	 }).success(function(data){
            		 truckDrSdNd.selectTruckSendByIdList = data.truckSend;
            	 });
             }
             //根据trne_id获得货主信息(先获得Id后查询)
             truckDrSdNd.getTruckNeedById = function (trne_id){
            	 sessionStorage.setItem("trne_id",trne_id);
            	 $location.path('truckNeedInfo');
             }
             truckDrSdNd.selectTruckNeedById = function (trne_id){
            	 services.selectTruckNeedById({
            		 trne_id : trne_id
            	 }).success(function(data){
            		 truckDrSdNd.selectTruckNeedByIdList = data.truckNeed;
            	 });
             }
             //根据trse_id获得车主信息(修改用)
             truckDrSdNd.getModifyTruckSend = function (trse_id){
            	 sessionStorage.setItem("trse_id",trse_id);
            	 $location.path('modifyTruckSend');
             }
             truckDrSdNd.modifyTruckSend = function (){
            	 var truckSend = JSON.stringify(truckDrSdNd.selectTruckSendByIdList);//修改部分只将前台写完，后台没写
            	 services.modifyTruckSend({
            		 truckSend : truckSend,
            		 trse_id : sessionStorage.getItem("trse_id")
            	 }).success(function(data){
            		 truckDrSdNd.modifyTruckSendList = data.list;
            	 });
             }
 			//修改广告查询分栏
             truckDrSdNd.changebar=function(state){
 				switch(state){
 				case 0:														
 					truckDrSdNd.show={
 						isActive0:true,
 						isActive1:false,
 						isActive2:false,
 				};
 				$('#table1').show();
 				$('#table2').hide();
 				$('#table3').hide();
 					break;
 				case 1:
 					truckDrSdNd.show={
 						isActive0:false,
 						isActive1:true,
 						isActive2:false,
 				};
 	 				$('#table1').hide();
 	 				$('#table2').show();
 	 				$('#table3').hide();
 					break;
 				case  2:
 					truckDrSdNd.show={
 						isActive0:false,
 						isActive1:false,
 						isActive2:true,						
 				};
 	 				$('#table1').hide();
 	 				$('#table2').hide();
 	 				$('#table3').show();
 					break;
 				}
 			}
             // 零担货运页面初始化
             function initPage() {
                 console.log("初始化页面信息");             
                 if ($location.path().indexOf('/truckDriver') == 0) {
                 } else if ($location.path().indexOf('/truckSend') == 0) {

                 } else if ($location.path().indexOf('/truckNeed') == 0) {
                
                 } else if ($location.path().indexOf('/selectTruckNeed') == 0) {
                	 
                 } else if ($location.path().indexOf('/selectTruckSend') == 0) {
                	 		
                 } else if($location.path().indexOf('/truckNeedInfo')== 0){
                	 var trne_id = sessionStorage.getItem("trne_id");
                	 truckDrSdNd.selectTruckNeedById(trne_id);
                 } else if ($location.path().indexOf('/truckSendInfo') == 0) {                
                	 var trse_id = sessionStorage.getItem("trse_id");                	
                	 truckDrSdNd.selectTruckSendById(trse_id);
                 } else if ($location.path().indexOf('/modifyTruckSend') == 0){
                	 var trse_id = sessionStorage.getItem("trse_id");
                	 truckDrSdNd.selectTruckSendById(trse_id);
                 }
             }
              initPage();
          } ]);

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
