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

// 路由配置
app.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/truckDriver', {
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
	}).when('/selecttruckDriverInfo', {
		templateUrl : '/lckywx/jsp/truckLoad/truckDriverInfo.html',
		controller : 'TruckLoadController'
	}).when('/modifyTruckSend', {
		templateUrl : '/lckywx/jsp/truckLoad/modifyTruckSend.html',
		controller : 'TruckLoadController'
	}).when('/truckOwnerInput', {
		templateUrl : '/lckywx/jsp/truckLoad/truckOwnerInput.html',
		controller : 'TruckLoadController'
	}).when('/myTruckPublish0', {
		templateUrl : '/lckywx/jsp/truckLoad/myTruckPublish.html',
		controller : 'TruckLoadController'
	}).when('/myTruckPublish1', {
		templateUrl : '/lckywx/jsp/truckLoad/myTruckPublish.html',
		controller : 'TruckLoadController'
	}).when('/myTruckPublish2', {
		templateUrl : '/lckywx/jsp/truckLoad/myTruckPublish.html',
		controller : 'TruckLoadController'
	}).when('/truckGoodInput', {
		templateUrl : '/lckywx/jsp/truckLoad/truckGoodInput.html',
		controller : 'TruckLoadController'
	}).when('/truckSendOrNeedNews', {
		templateUrl : '/lckywx/jsp/truckLoad/truckSendOrNeedNews.html',
		controller : 'TruckLoadController'
	}).when('/truckDriverInfo', {
		templateUrl : '/lckywx/jsp/truckLoad/truckDriverInfo.html',
		controller : 'TruckLoadController'
	}).when('/modifyTruckNeed', {
		templateUrl : '/lckywx/jsp/truckLoad/modifyTruckNeed.html',
		controller : 'TruckLoadController'
	}).when('/modifyTruckSend', {
		templateUrl : '/lckywx/jsp/truckLoad/modifyTruckSend.html',
		controller : 'TruckLoadController'
	}).when('/modifyTruckDriver', {
		templateUrl : '/lckywx/jsp/truckLoad/modifyTruckDriver.html',
		controller : 'TruckLoadController'
	})

} ]);

app.constant('baseUrl', '/lckywx/');
app.factory('services', [ '$http', 'baseUrl', function($http, baseUrl) {
	var services = {};
	// 添加司机货车信息
	services.addTruckDriver = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'truckLoad/addTruckDriver.do',
			data : data
		});
	};
	// 添加货车货运信息
	services.addTruckSend = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'truckLoad/addTruckSend.do',
			data : data
		});
	};
	// 添加货车货运信息
	services.addTruckNeed = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'truckLoad/addTruckNeed.do',
			data : data
		});
	};
	// 车主查询货主信息
	services.selectTruckNeed = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'truckLoad/selectTruckNeed.do',
			data : data
		});
	};
	services.selectTruckSend = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'truckLoad/selectTruckSend.do',
			data : data
		});
	};
	services.selectTruckSendById = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'truckLoad/selectTruckSendById.do',
			data : data
		});
	};
	services.selectTruckNeedById = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'truckLoad/selectTruckNeedById.do',
			data : data
		});
	};
	services.selectTruckDriverById = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'truckLoad/selectTruckDriverById.do',
			data : data
		})
	}
	services.modifyTruckSend = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'truckLoad/modifyTruckSend.do',
			data : data
		});
	};
	// zq
	services.selectUserTruck = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'truckLoad/selectUserTruck.do',
			data : data
		});
	};
	// zq
	services.selectNewTruckSend = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'truckLoad/selectNewTruckSend.do',
			data : data
		});
	};
	// zq
	services.selectNewTruckNeed = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'truckLoad/selectNewTruckNeed.do',
			data : data
		});
	};
	return services;
} ]);
app
		.controller(
				'TruckLoadController',
				[
						'$scope',
						'services',
						'$location',
						function($scope, services, $location) {
							var truckDrSdNd = $scope;
							truckDrSdNd.truckLimit = {
								trck_load : "",
								is_freeze : "0", // 0代表未冷冻，1代表冷冻
								trck_number : "", // 车牌号
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
							 * private Integer driver_id;//司机ID,主键 private
							 * Integer is_audit;//0代表未审核，1代表已审核
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
								trne_time : getNowDate(),
								trne_remark : "",
								is_freeze : "0",
								trne_receive_name : "",
								trne_receive_tel : ""
							}
							
							truckDrSdNd.showShadow = function(){
								if ($('.shadowbox').css('display') == 'none') {
									$('.shadowbox').fadeIn(100);
								} else {
									$('input[type="checkbox"]')[0].checked = 'true';
									$('input[type="checkbox"]')[1].checked = 'true';
									$('.shadowbox').fadeOut(100);
								}
							}
							
							/*
							 * truckDrSdNd.gotLimit={ startDate:"", endDate:"" }
							 * truckDrSdNd.gotLimits={ startDate:"", endDate:"" }
							 */
							function compareDateTime(starTime, endTime) {
								var date1 = new Date(starTime);
								var date2 = new Date(endTime);
								if (date1.getTime() < date2.getTime()) {
									return true;
								} else {
									return false;
								}
							}
							// 根据trse_id获得车主信息(先获得Id后查询)
							truckDrSdNd.getTruckSendById = function(trse_id) {
								sessionStorage.setItem("trse_id", trse_id);
								$location.path('truckSendInfo');
							}
							// 根据trse_id获得修改车主信息(先获得Id后查询)
							truckDrSdNd.getModifyTruckSendById = function(trse_id) {
								sessionStorage.setItem("trse_id", trse_id);
								$location.path('modifyTruckSend');
							}
							truckDrSdNd.selectTruckSendById = function(trse_id) {
								services
										.selectTruckSendById({
											trse_id : trse_id
										})
										.success(
												function(data) {
													truckDrSdNd.selectTruckSendByIdList = data.truckSend;
													truckDrSdNd.trseLimit = data.truckSend;
													truckDrSdNd.trseLimit.trse_time = changeDateType(data.truckSend.trse_time)
												});
							}
							// 根据trne_id获得货主信息(先获得Id后查询)
							truckDrSdNd.getTruckNeedById = function(trne_id) {
								sessionStorage.setItem("trne_id", trne_id);
								$location.path('truckNeedInfo');
							}
							// 修改货主发布信息
							truckDrSdNd.getModifyTruckNeedById = function(
									trne_id) {
								sessionStorage.setItem("trne_id", trne_id);
								$location.path('modifyTruckNeed');
							}
							truckDrSdNd.selectTruckNeedById = function(trne_id) {
								services
										.selectTruckNeedById({
											trne_id : trne_id
										})
										.success(
												function(data) {
													truckDrSdNd.selectTruckNeedByIdList = data.truckNeed;
													truckDrSdNd.trneLimit = data.truckNeed
													truckDrSdNd.trneLimit.trne_time = changeDateType(data.truckNeed.trne_time);
												});
							}
							// 根据truckID获得详情(先获得Id后查询)
							truckDrSdNd.getTruckById = function(tr_id, dr_id) {
								sessionStorage.setItem("tr_id", tr_id);
								sessionStorage.setItem("dr_id", dr_id);
								$location.path('truckDriverInfo');
							}
							// 修改车辆和司机信息
							truckDrSdNd.getModifyTruckById = function(tr_id,
									dr_id) {
								sessionStorage.setItem("tr_id", tr_id);
								sessionStorage.setItem("dr_id", dr_id);
								$location.path('modifyTruckDriver');
							}
							// 根据trck_id和driver_id获取司机车辆信息
							truckDrSdNd.selectTruckDriverById = function(
									trckId, driverId) {
								services
										.selectTruckDriverById({
											trckId : trckId,
											driverId : driverId
										})
										.success(
												function(data) {
													truckDrSdNd.selectDriverByIdList = data.driver;
													truckDrSdNd.selectTruckByIdList = data.truck;
													truckDrSdNd.truckLimit = data.truck;
													truckDrSdNd.driverLimit = data.driver;
												})
							}
							// 根据trse_id获得车主信息(修改用)
							truckDrSdNd.getModifyTruckSend = function(trse_id) {
								sessionStorage.setItem("trse_id", trse_id);
								$location.path('modifyTruckSend');
							}
							truckDrSdNd.modifyTruckSend = function() {
								var truckSend = JSON
										.stringify(truckDrSdNd.selectTruckSendByIdList);// 修改部分只将前台写完，后台没写
								services.modifyTruckSend(
												{
													truckSend : truckSend,
													trse_id : sessionStorage
															.getItem("trse_id")
												})
										.success(
												function(data) {
													truckDrSdNd.modifyTruckSendList = data.list;
												});
							}

							// //////////////////////////////////////zq与pg的分界线
							// 格式化时间
							function changeDateType(date) {
								console.log("传进来的时间" + date);
								if (date != "") {
									var DateTime = new Date(date.time)
											.toLocaleDateString().replace(
													/\//g, '-');
								} else {
									var DateTime = "";
								}
								console.log("转化后的的时间" + DateTime);
								return DateTime;
							}
							// zq pg添加货车+司机的信息
							truckDrSdNd.addTruckDriver = function() {
								var truckLimit = JSON
										.stringify(truckDrSdNd.truckLimit);
								var driverLimit = JSON
										.stringify(truckDrSdNd.driverLimit);
								services.addTruckDriver({
									truckInfo : truckLimit,
									driverInfo : driverLimit
								}).success(
										function(data) {
											sessionStorage.setItem("trckId",
													data.limint.trck_id);
											sessionStorage.setItem("driverId",
													data.result.driver_id);
											$location.path("myTruckPublish0");
										});
							}
							// zq、pg添加货主需求信息
							truckDrSdNd.addTruckSend = function() {
								var myDate = new Date();
								var flag = truckDrSdNd.compareDate(myDate,
										truckDrSdNd.trseLimit.trse_time);
								if (flag) {
									alert("出发时间应大于当前日期！");
									truckDrSdNd.trseLimit.trse_time = getNowDate();
									return;
								}
								var truckLimit = JSON
										.stringify(truckDrSdNd.trseLimit);
								console.log(truckLimit);
								services.addTruckSend({
									trseInfo : truckLimit
								}).success(
										function(data) {
											sessionStorage.setItem("trseId",
													data.result.trse_id);
											$location.path("myTruckPublish1");
										});
							}
							// zq 修改广告查询分栏
							truckDrSdNd.changebar = function(state, fun) {
								var flag = false;
								switch (state) {
								case 0:
									truckDrSdNd.show = {
										isActive0 : true,
										isActive1 : false,
										isActive2 : false,
									};
									services
											.selectUserTruck({})
											.success(
													function(data) {
														switch (data.flag) {
														case 0:
															$('#table1').hide();
															$('#table2').hide();
															alert("您的司机车辆信息还在审核过程中，暂不能发布运货需求！请及时与客运站联系审核！");
															break;
														case 1:
															truckDrSdNd.show = {
																isActive0 : true,
																isActive1 : false,
																isActive2 : false,
															};
															$('#table1').show();
															$('#table2').hide();
															break;
														case 2:
															$('#table1').hide();
															$('#table2').hide();
															alert("您的司机车辆信息审核未通过，请在我的发布中找到你的车辆信息进行修改！");
															$location
																	.path("myTruckPublish0");
															break;
														case 3:
															truckDrSdNd.show = {
																isActive0 : false,
																isActive1 : true,
																isActive2 : false,
															};
															$('#table1').hide();
															$('#table2').show();
															alert("请先登记货车及司机信息！");
															break;
														}
													});

									break;
								case 1:
									truckDrSdNd.show = {
										isActive0 : false,
										isActive1 : true,
										isActive2 : false,
									};
									services
											.selectUserTruck({})
											.success(
													function(data) {
														switch (data.flag) {
														case 0:
															$('#table1').hide();
															$('#table2').hide();
															alert("您的司机车辆信息还在审核过程中，暂不能发布运货需求！请及时与客运站联系审核！");
															break;
														case 1:
															truckDrSdNd.show = {
																isActive0 : true,
																isActive1 : false,
																isActive2 : false,
															};
															$('#table1').show();
															$('#table2').hide();
															alert("您的车辆信息已被审核通过，请直接填写需求发布");
															break;
														case 2:
															$('#table1').hide();
															$('#table2').hide();
															alert("您的司机车辆信息审核未通过，请在我的发布中找到你的车辆信息进行修改！");
															$location
																	.path("myTruckPublish0");
															break;
														case 3:
															truckDrSdNd.show = {
																isActive0 : false,
																isActive1 : true,
																isActive2 : false,
															};
															$('#table1').hide();
															$('#table2').show();
															break;
														}
													});

									break;
								}
							}
							// zq我的发布分栏
							truckDrSdNd.myChangebar = function(state) {
								switch (state) {
								case 0:
									truckDrSdNd.myShow = {
										isActive0 : true,
										isActive1 : false,
										isActive2 : false,
									};
									$('#table0').show();
									$('#table1').hide();
									$('#table2').hide();
									truckDrSdNd.truckList = [];
									truckDrSdNd.selectUserTruckList();
									break;
								case 1:
									truckDrSdNd.myShow = {
										isActive0 : false,
										isActive1 : true,
										isActive2 : false,
									};
									$('#table0').hide();
									$('#table1').show();
									$('#table2').hide();
									truckDrSdNd.truckSendList = [];
									openScroll(truckDrSdNd.selectTruckSendList);
									break;
								case 2:
									truckDrSdNd.myShow = {
										isActive0 : false,
										isActive1 : false,
										isActive2 : true,
									};
									$('#table0').hide();
									$('#table1').hide();
									$('#table2').show();
									truckDrSdNd.truckNeedList = [];
									openScroll(truckDrSdNd.selectTruckNeedList);
									break;
								}
							}
							// zq查询与用户相关的货车列表
							truckDrSdNd.selectUserTruckList = function() {
								$('.containerloading').fadeIn(100);
								$('.overlayer').fadeIn(100);
								services.selectUserTruck({}).success(
										function(data) {
											if ($('.containerloading').css(
													'display') == 'block') {
												$('.containerloading').fadeOut(
														100);
												$('.overlayer').fadeOut(100);
											}
											if ($('.loading-loading').css(
													'display') == 'block') {
												$('.loading-loading').fadeOut(
														100);
											}
											;
											truckDrSdNd.truckList = data.list;
											$(".limitHint").css('display',
													'block');
										});
							}
							// zq 查询车主发布的送货信息
							truckDrSdNd.selectTruckSendList = function(config,
									counter, state) {
								config.isAjax = true;
								services
										.selectTruckSend({
											page : counter
										})
										.success(
												function(data) {
													if ($('.containerloading')
															.css('display') == 'block') {
														$('.containerloading')
																.fadeOut(100);
														$('.overlayer')
																.fadeOut(100);
													}
													if ($('.loading-loading')
															.css('display') == 'block') {
														$('.loading-loading')
																.fadeOut(100);
													}
													;
													if (!truckDrSdNd.truckSendList) {
														truckDrSdNd.truckSendList = [];
													}
													if(typeof(data.list) != "undefined"){
													truckDrSdNd.truckSendList = truckDrSdNd.truckSendList.concat(data.list);
													}
													config.isAjax = false;
													console.log(truckDrSdNd.truckSendList)
													if (data.list == ![] || data.list == null) {
														$(".limitHint").css(
																'display',
																'block');
														config.isEnd = true;
													}
												});
							}

							// zqpg添加发货需求信息
							truckDrSdNd.addTruckNeed = function() {
								var myDate = new Date();
								var flag = truckDrSdNd.compareDate(myDate,
										truckDrSdNd.trneLimit.trne_time);
								if (flag) {
									alert("出发时间应大于当前日期！");
									truckDrSdNd.trneLimit.trne_time = getNowDate();
									return;
								}
								var truckLimit = JSON
										.stringify(truckDrSdNd.trneLimit);
								services.addTruckNeed({
									trneInfo : truckLimit
								}).success(
										function(data) {
											sessionStorage.setItem("trneId",
													data.result.trne_id);
											$location.path("myTruckPublish2");
										});
							}
							// zq 查询货主发布的信息
							truckDrSdNd.selectTruckNeedList = function(config,
									counter, state) {
								config.isAjax = true;
								services
										.selectTruckNeed({
											page : counter
										})
										.success(
												function(data) {
													if ($('.containerloading')
															.css('display') == 'block') {
														$('.containerloading')
																.fadeOut(100);
														$('.overlayer')
																.fadeOut(100);
													}
													if ($('.loading-loading')
															.css('display') == 'block') {
														$('.loading-loading')
																.fadeOut(100);
													}
													;
													if (!truckDrSdNd.truckNeedList) {
														truckDrSdNd.truckNeedList = [];
													}
													truckDrSdNd.truckNeedList = truckDrSdNd.truckNeedList
															.concat(data.list);
													config.isAjax = false;
													if (data.list == ![]) {
														$(".limitHint").css(
																'display',
																'block');
														config.isEnd = true;
													}
												});
							}
							// zq货运动态分栏
							truckDrSdNd.newsChangebar = function(state) {
								switch (state) {
								case 0:
									truckDrSdNd.newsShow = {
										isActive0 : true,
										isActive1 : false
									};
									$('#table0').show();
									$('#table1').hide();
									truckDrSdNd.truckSendList = [];
									openScroll(truckDrSdNd.selectNewTruckSendList);
									break;
								case 1:
									truckDrSdNd.newsShow = {
										isActive0 : false,
										isActive1 : true
									};
									$('#table0').hide();
									$('#table1').show();
									truckDrSdNd.truckNeedList = [];
									openScroll(truckDrSdNd.selectNewTruckNeedList);
									break;
								}
							}
							// zq查询货主的最新需求
							truckDrSdNd.selectNewTruckNeedList = function(
									config, counter, state) {
								config.isAjax = true;
								services
										.selectNewTruckNeed({
											page : counter
										})
										.success(
												function(data) {
													if ($('.containerloading')
															.css('display') == 'block') {
														$('.containerloading')
																.fadeOut(100);
														$('.overlayer')
																.fadeOut(100);
													}
													if ($('.loading-loading')
															.css('display') == 'block') {
														$('.loading-loading')
																.fadeOut(100);
													}
													;
													if (!truckDrSdNd.truckNeedList) {
														truckDrSdNd.truckNeedList = [];
													}
													truckDrSdNd.truckNeedList = truckDrSdNd.truckNeedList
															.concat(data.list);
													config.isAjax = false;
													if (data.list == ![]) {
														$(".limitHint").css(
																'display',
																'block');
														config.isEnd = true;
													}
												});
							}
							// zq查询车主的最新供需
							truckDrSdNd.selectNewTruckSendList = function(
									config, counter, state) {
								config.isAjax = true;
								services
										.selectNewTruckSend({
											page : counter
										})
										.success(
												function(data) {
													if ($('.containerloading')
															.css('display') == 'block') {
														$('.containerloading')
																.fadeOut(100);
														$('.overlayer')
																.fadeOut(100);
													}
													if ($('.loading-loading')
															.css('display') == 'block') {
														$('.loading-loading')
																.fadeOut(100);
													}
													;
													if (!truckDrSdNd.truckSendList) {
														truckDrSdNd.truckSendList = [];
													}
													truckDrSdNd.truckSendList = truckDrSdNd.truckSendList
															.concat(data.list);
													config.isAjax = false;
													if (data.list == ![]) {
														$(".limitHint").css(
																'display',
																'block');
														config.isEnd = true;
													}
												});
							}
							// 获取滚动条当前的位置
							function getScrollTop() {
								var scroll = 0;
								// 判断哪个浏览器
								if (document.documentElement
										&& document.documentElement.scrollTop) {
									scroll = $(".yscroll").scrollTop();
								} else if (document.body) {
									scroll = $(".yscroll").scrollTop();
								}
								return scroll;
							}
							;

							// 获取当前可视范围的高度
							function getClientHeight() {
								var clientHeight = 0;
								// 判断哪个浏览器
								if (document.body.clientHeight
										&& document.documentElement.clientHeight) {
									clientHeight = Math
											.min(
													document.body.clientHeight,
													document.documentElement.clientHeight);
								} else {
									clientHeight = Math
											.max(
													document.body.clientHeight,
													document.documentElement.clientHeight);
								}
								return clientHeight;
							}
							;

							// 获取文档完整的高度
							function getScrollHeight() {
								var aaheight = $(".yscroll")[0].scrollHeight;
								return Math.max($(".yscroll")[0].scrollHeight,
										document.documentElement.scrollHeight);
							}

							function openScroll(getDate, config, state) {
								var config = config ? config : {};
								var counter = 1;/* 计数器 */

								/* 第一次加载页面 */
								getDate(config, counter, state);
								$('.containerloading').fadeIn(100);
								$('.overlayer').fadeIn(100);
								/* 通过自动监听滚动事件加载更多,可选支持 */
								config.isEnd = false; /* 结束标志 */
								config.isAjax = false; /* 防止滚动过快，服务端没来得及响应造成多次请求 */
								var t = 0;
								var p = 0;
								$("section")
										.scroll(
												function() {
													/* 滚动加载时如果已经没有更多的数据了、正在发生请求时，不能继续进行 */
													if (config.isEnd == true
															|| config.isAjax == true) {
														return;
													}
													/* 判断向上滚动或向下滚动 */
													p = getScrollTop()
													if (t <= p) {
														t = p;
														/* 当滚动到底部时， 加载新内容 */
														if (getScrollHeight()
																- (t + getClientHeight()) < 50) {
															counter++;
															getDate
																	&& getDate(
																			config,
																			counter,
																			state);
															$(
																	'.loading-loading')
																	.fadeIn(100);
														}
													}
												});
							}
							// 比较两个时间的大小
							truckDrSdNd.compareDate = function(startDate,
									endDate) {
								var date1 = new Date(startDate);
								var date2 = new Date(endDate);
								if (date2.getTime() < date1.getTime()) {
									return true;
								} else {
									return false;
								}
							}
							truckDrSdNd.limitDate = function(date) {

								var myDate = new Date();
								var flag = truckDrSdNd
										.compareDate(myDate, date);
								if (flag) {
									alert("出发时间应大于当前日期！");
								}
							}
							// 获取当前日期并转化为2017-12-12
							function getNowDate() {
								var nowDate = new Date();
								var year = nowDate.getFullYear();
								var month = nowDate.getMonth() + 1;
								var today = nowDate.getDate();
								if (month >= 1 && month <= 9) {
									month = "0" + month;
								}
								if (today >= 1 && today <= 9) {
									today = "0" + today;
								}
								var currentdate = year + "-" + month + "-"
										+ today;
								return currentdate;
							}
							// 零担货运页面初始化
							function initPage() {
								console.log("初始化页面信息");
								if ($location.path()
										.indexOf('/truckDriverInfo') == 0) {
									var tr_id = sessionStorage.getItem("tr_id");
									var dr_id = sessionStorage.getItem("dr_id");
									truckDrSdNd.selectTruckDriverById(tr_id,
											dr_id);
								} else if ($location.path().indexOf(
										'/truckNeedInfo') == 0) {
									var trne_id = sessionStorage
											.getItem("trne_id");
									truckDrSdNd.selectTruckNeedById(trne_id);
								} else if ($location.path().indexOf(
										'/truckSendInfo') == 0) {
									var trse_id = sessionStorage
											.getItem("trse_id");
									truckDrSdNd.selectTruckSendById(trse_id);
								} else if ($location.path().indexOf(
										'/modifyTruckSend') == 0) {
									var trse_id = sessionStorage
											.getItem("trse_id");
									truckDrSdNd.selectTruckSendById(trse_id);
								} else if ($location.path().indexOf(
										'/truckOwnerInput') == 0) {
									truckDrSdNd.changebar(0);
								} else if ($location.path().indexOf(
										'/myTruckPublish0') == 0) {
									truckDrSdNd.myChangebar(0);

								} else if ($location.path().indexOf(
										'/myTruckPublish1') == 0) {
									truckDrSdNd.myChangebar(1);
								} else if ($location.path().indexOf(
										'/myTruckPublish2') == 0) {
									truckDrSdNd.myChangebar(2);
								} else if ($location.path().indexOf(
										'/truckGoodInput') == 0) {
								} else if ($location.path().indexOf(
										'/truckSendOrNeedNews') == 0) {
									truckDrSdNd.newsChangebar(0);
								} else if ($location.path().indexOf(
										'/modifyTruckNeed') == 0) {
									var trne_id = sessionStorage
											.getItem("trne_id");
									truckDrSdNd.selectTruckNeedById(trne_id);
								} else if ($location.path().indexOf(
										'/modifyTruckSend') == 0) {
									var trse_id = sessionStorage
											.getItem("trse_id");
									truckDrSdNd.selectTruckSendById(trse_id);
								} else if ($location.path().indexOf(
										'/modifyTruckDriver') == 0) {
									var tr_id = sessionStorage.getItem("tr_id");
									var dr_id = sessionStorage.getItem("dr_id");
									truckDrSdNd.selectTruckDriverById(tr_id,
											dr_id);
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
// 货车是否冷冻的判断
app.filter('isFreeze', function() {
	return function(input) {
		var type = "";
		if (input) {
			type = "是";
		} else {
			type = "否";
		}
		return type;
	}
});
// 货车是否冷冻的判断
app.filter('trckCheck', function() {
	return function(input) {
		var type = "";
		if (input == "0") {
			type = "待审核";
		} else if (input == "1") {
			type = "审核通过";
		} else {
			type = "审核未通过";
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