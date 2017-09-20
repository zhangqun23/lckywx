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
// 路由配置
app.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/addAdver', {
		templateUrl : '/lckywx/jsp/adver/addAdver.html',
		controller : 'PlatformController'
	}).when('/selectAdver', {
		templateUrl : '/lckywx/jsp/adver/selectAdver.html',
		controller : 'PlatformController'
	}).when('/selectAdverInfo/:adid', {
		templateUrl : '/lckywx/jsp/adver/selectAdverInfo.html',
		controller : 'SelectAdController'
	}).when('/myPlace', {
		templateUrl : '/lckywx/jsp/adver/myPlace.html', // 新加内容（ghl）
		controller : 'PlatformController'
	}).when('/updateAd',{
		templateUrl : '/lckywx/jsp/adver/updateAd.html',
		controller : 'PlatformController'
	})
} ]);
app.constant('baseUrl', '/lckywx/');
app.factory('services', [ '$http', 'baseUrl', function($http, baseUrl) { // 加上
	var services = {};
	//添加广告的后台方法
	services.addAdver = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'ad/addAd.do',
			data : data
		});
	};
	//根据type查询广告的后台方法
	services.selectAdver = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'ad/selectAdver.do',
			data : data
		});
	};
	//根据Id查询广告的后台方法
	services.selectAdverInfo = function(data) { // 加上
		return $http({
			method : 'post',
			url : baseUrl + 'ad/selectAdverInfo.do',
			data : data
		});
	};
	//我的发布广告查询的后台方法
	services.myPlace = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'ad/myPlace.do',
			data : data
		});
	};
	//删除广告的后台方法
	services.deleteAd =  function(data){
		return $http({
			method : 'post',
			url : baseUrl + 'ad/deleteAd.do',
			data : data
		});
	};
	//修改广告的后台方法
	services.modifyAd =  function(data){
		return $http({
			method : 'post',
			url : baseUrl + 'ad/modifyAd.do',
			data : data
		});
	};
	return services;
} ]);

app.controller('PlatformController', [ '$scope', 'services', '$location',
		'$routeParams', function($scope, services, $location, $routeParams) {
			var adver = $scope;
			adver.ADLimit = {
				ad_type : "广告类型",
				ad_name : "",
				ad_title : "",
				ad_content : "",
				ad_tel : "",
				ad_remark : "",
				ad_etime : ""
			}
			//比较输入时间与当前时间的大小
 			function compareDateTime(starTime,endTime){
 				var date1 = new Date(starTime);
 				var date2 = new Date(endTime);
 				if(date1.getTime()>date2.getTime()){
 					return true;
 				}else{
 					return false;
 				}
 			}
			// 添加广告
			adver.addAdver = function() {
				var myDate = new Date();
				if(compareDateTime(myDate.toLocaleDateString(),adver.ADLimit.ad_etime)){return alert("请填写正确时间")}
				var adLimit = JSON.stringify(adver.ADLimit);
				if (adver.ADLimit.ad_type == "广告类型") {
					return alert("请输入广告类型！")
				}
				services.addAdver({
					ad : adLimit
				}).success(function(data) {
					console.log("::::::::::::" + data);
					if (data) {
						alert("添加成功");
					} else {
						alert("否");
					}
					$location.path('myPlace/');

				});
			}
			// 根据类型查询广告
			adver.selectAdver = function(adLimit) {
				services.selectAdver({
					adType : adLimit
				}).success(function(data) {
					adver.adList = data.list;
				});
			}
			// 根据openId,state查询我的发布   广告
			adver.myPlace = function(state) {
				services.myPlace({
					ad_state : state
				}).success(function(data) {
					adver.adList = data.list;
				});
			}
			//根据Id查询广告内容
			adver.selectAderInfo = function(adId) {
				$location.path('selectAdverInfo/' + adId);
			}
			//删除广告
			adver.deleteAd = function (ad_id,ad_title){
				if(confirm("确定删除此广告<"+ad_title+">")){
					services.deleteAd({
						adId : ad_id
					}).success(function(data){
						$location.path('myPlace/');
					});
				}else{
					return null;
				}	
			}  
			//根据id的到ad信息用来修改广告
			adver.getAdById = function (adId){
				sessionStorage.setItem("adId",adId);
				$location.path('updateAd/');
			}
				//修改广告
			adver.modifyAd = function() {
				alert("修改")
				var adLimit = JSON.stringify(adver.ADQLimit);
				services.modifyAd({
					ad : adLimit,
					ad_id : sessionStorage.getItem("adId")
				}).success(function(data) {
					$location.path('myPlace/');
				});
			 }
			//修改分栏
			adver.changeBar=function(state){
				switch(state){
				case 0:					
					adver.myPlace("0");					
					adver.show={
						isActive0:true,
						isActive1:false,
						isActive2:false,
				};
					adver.flag=true;					
					break;
				case 1:
					adver.myPlace("1");
					adver.show={
						isActive0:false,
						isActive1:true,
						isActive2:false,
				};
					adver.flag=false;
					break;
				case  2:
					adver.myPlace("2");
					adver.show={
						isActive0:false,
						isActive1:false,
						isActive2:true,						
				};
					adver.flag=true;
					break;
				}
			}
			//修改广告查询分栏
			adver.change=function(state){
				switch(state){
				case 0:					
					adver.selectAdver("0");					
					adver.show={
						isActive0:true,
						isActive1:false,
						isActive2:false,
				}					
					break;
				case 1:
					adver.selectAdver("1");
					adver.show={
						isActive0:false,
						isActive1:true,
						isActive2:false,
				}
					break;
				case  2:
					adver.selectAdver("2");
					adver.show={
						isActive0:false,
						isActive1:false,
						isActive2:true,						
				}
					break;
				}
			}
			//初始化
			function initData() {
				console.log("初始化页面信息");
				if ($location.path().indexOf('/selectAdver') == 0) {
					adver.selectAdver("0");	
					adver.show={
							isActive0:true,
							isActive1:false,
							isActive2:false,
					}
				} else if ($location.path().indexOf('/myPlace') == 0) {
					adver.myPlace("0");
					adver.show={
							isActive0:true,
							isActive1:false,
							isActive2:false,
					};
					adver.flag=true;
				}else if ($location.path().indexOf('/updateAd') == 0) {
					var adId=sessionStorage.getItem("adId");
					services.selectAdverInfo({
						ad_id : adId
					}).success(function(data) {
						$scope.ADQLimit = data.list;
					});
				}
			}
			initData();
		} ]);

app.controller('SelectAdController', [ '$scope', 'services', '$location',
		'$routeParams', function($scope, services, $location, $routeParams) {
			services.selectAdverInfo({
				ad_id : $routeParams.adid
			}).success(function(data) {
				$scope.adIList = data.list;
			});
		} ]);
app.filter('adFilter', function() {
	return function(input) {
		if (input == "" || input == null) {
			var input = "空";
			return input;
		} else {
			return input;
		}
	}
});