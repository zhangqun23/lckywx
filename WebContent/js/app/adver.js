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
	}).when('/updateAd', {
		templateUrl : '/lckywx/jsp/adver/updateAd.html',
		controller : 'PlatformController'
	})
} ]);
app.constant('baseUrl', '/lckywx/');
app.factory('services', [ '$http', 'baseUrl', function($http, baseUrl) { // 加上
	var services = {};
	// 添加广告的后台方法
	services.addAdver = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'ad/addAd.do',
			data : data
		});
	};
	// 根据type查询广告的后台方法
	services.selectAdver = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'ad/selectAdver.do',
			data : data
		});
	};
	// 根据Id查询广告的后台方法
	services.selectAdverInfo = function(data) { // 加上
		return $http({
			method : 'post',
			url : baseUrl + 'ad/selectAdverInfo.do',
			data : data
		});
	};
	// 我的发布广告查询的后台方法
	services.myPlace = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'ad/myPlace.do',
			data : data
		});
	};
	// 删除广告的后台方法
	services.deleteAd = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'ad/deleteAd.do',
			data : data
		});
	};
	// 修改广告的后台方法
	services.modifyAd = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'ad/modifyAd.do',
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
							adver.ADLimit = {
								ad_type : "广告类型",
								ad_name : "",
								ad_title : "",
								ad_content : "",
								ad_tel : "",
								ad_remark : "",
								ad_etime : getNowDate(),
							}
							// 比较输入时间与当前时间的大小
							function compareDateTime(starTime, endTime) {
								var date1 = new Date(starTime);
								var date2 = new Date(endTime);
								if (date1.getTime() > date2.getTime()) {
									return true;
								} else {
									return false;
								}
							}
							// 手机，电话格式判定
							adver.checknum = function(element) {
								if ((/^1[3|4|5|7|8]\d{9}$/.test(element))
										| /^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}$/
												.test(element)) {
									console.log("jinalail2")
									$(".limitNum").css('display', 'none');
								} else {
									console.log("jinalail")
									$(".limitNum").css('display', 'block');
								}
								return;
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
								var currentdate = year + "-" + month + "-" + today;
								return currentdate;
							}
							// 添加广告
							adver.addAdver = function() {
								var myDate = new Date();
								if (compareDateTime(
										myDate.toLocaleDateString(),
										adver.ADLimit.ad_etime)) {
									return alert("应大于当前日期")
								}
								var adLimit = JSON.stringify(adver.ADLimit);
								if (adver.ADLimit.ad_type == "广告类型") {
									return alert("请输入广告类型！")
								}
<<<<<<< HEAD
=======
								console.log(adLimit);
								$('.containerloading').fadeIn(100);
							    $('.overlayer').fadeIn(100);
>>>>>>> dca8b9f6af394949d3c6f5a7f5ec148ebfa85a5b
								services.addAdver({
									ad : adLimit
								}).success(function(data) {
									$('.containerloading').fadeOut(100);
								    $('.overlayer').fadeOut(100);
									$location.path('myPlace');

								});
							}
							/**
							 * 查询广告信息和分页
							 */
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
								$("section").scroll(function() {
										/* 滚动加载时如果已经没有更多的数据了、正在发生请求时，不能继续进行 */
										if (config.isEnd == true || config.isAjax == true) {
											return;
										}
										/* 判断向上滚动或向下滚动 */
										p = getScrollTop()
										if (t <= p) {
											t = p;
											/* 当滚动到底部时， 加载新内容 */
											if (getScrollHeight() - (t + getClientHeight()) < 50) {
												counter++;
												getDate && getDate(config,counter,state);
												$('.loading-loading').fadeIn(100);
											}
										}
									});
							}
							// 查询广告
							function selectAdver(config, counter, state) {
								config.isAjax = true;
								services.selectAdver({
											adType : state,
											page : counter
										})
										.success(
												function(data) {
													if ($('.containerloading').css('display') == 'block'){
														$('.containerloading').fadeOut(100);
													    $('.overlayer').fadeOut(100);
													}
												    if ($('.loading-loading').css('display') == 'block'){
												    	$('.loading-loading').fadeOut(100);
												    };
													if (!adver.adList) {
														adver.adList = [];
													}
													if (typeof (data.list) != "undefined") {
														adver.adList = adver.adList
																.concat(data.list);
													}
													config.isAjax = false;
													if (data.list == ![]
															|| data.list == undefined) {
														$(".limitHint").css(
																'display',
																'block');
														config.isEnd = true;
													}
												});
							}
							/*
							 * // 根据类型查询广告 adver.selectAdver = function(adLimit) {
							 * services.selectAdver({ adType : adLimit
							 * }).success(function(data) { adver.adList =
							 * data.list; }); }
							 */
							/**
							 * 查询我的发布和分页
							 */
							function myPlace(config, counter, state) {
								config, isAjax = true;
								services
										.myPlace({
											ad_state : state,
											page : counter
										})
										.success(
												function(data) {
													if ($('.containerloading').css('display') == 'block'){
														$('.containerloading').fadeOut(100);
													    $('.overlayer').fadeOut(100);
													}
												    if ($('.loading-loading').css('display') == 'block'){
												    	$('.loading-loading').fadeOut(100);
												    };
													if (!adver.adList) {
														adver.adList = [];
													}
													if (typeof (data.list) != "undefined") {
														adver.adList = adver.adList
																.concat(data.list);
													}
													config.isAjax = false;
													if (data.list == ![]
															|| data.list == undefined) {
														$(".limitHint").css(
																'display',
																'block');
														config.isEnd = true;
													}
												});
							}
							/*
							 * // 根据openId,state查询我的发布 广告 adver.myPlace =
							 * function(state) { services.myPlace({ ad_state :
							 * state }).success(function(data) { adver.adList =
							 * data.list; }); }
							 */
							// 根据Id查询广告内容
							adver.selectAderInfo = function(adId) {
								$location.path('selectAdverInfo/' + adId);
							}
							// 删除广告
							adver.deleteAd = function(ad_id, ad_title) {
								if (confirm("确定删除此广告<" + ad_title + ">")) {
									$('.containerloading').fadeIn(100);
								    $('.overlayer').fadeIn(100);
									services.deleteAd({
										adId : ad_id
									}).success(function(data) {
									    if ($('.loading-loading').css('display') == 'block'){
									    	$('.loading-loading').fadeOut(100);
									    };
										$location.path('myPlace/');
									});
								} else {
									return null;
								}
							}
							// 根据id的到ad信息用来修改广告
							adver.getAdById = function(adId) {
								sessionStorage.setItem("adId", adId);
								$location.path('updateAd/');
							}
							// 修改广告
							adver.modifyAd = function() {
								$('.containerloading').fadeIn(100);
								$('.overlayer').fadeIn(100);
								var adLimit = JSON.stringify(adver.ADQLimit);
								services.modifyAd({
									ad : adLimit,
									ad_id : sessionStorage.getItem("adId")
								}).success(function(data) {
									if ($('.containerloading').css('display') == 'block'){
										$('.containerloading').fadeOut(100);
									    $('.overlayer').fadeOut(100);
									}
									$location.path('myPlace/');
								});
							}
							// 修改分栏
							adver.changeBar = function(state) {
								adver.adList = [];
								switch (state) {
								case 0:
									openScroll(myPlace, {}, "0");
									adver.show = {
										isActive0 : true,
										isActive1 : false,
										isActive2 : false,
									};
									adver.flag = true;
									break;
								case 1:
									openScroll(myPlace, {}, "1");
									adver.show = {
										isActive0 : false,
										isActive1 : true,
										isActive2 : false,
									};
									adver.flag = false;
									break;
								case 2:
									openScroll(myPlace, {}, "2");
									adver.show = {
										isActive0 : false,
										isActive1 : false,
										isActive2 : true,
									};
									adver.flag = true;
									break;
								}
							}
							// 修改广告查询分栏
							adver.change = function(state) {
								adver.adList = [];
								switch (state) {
								case 0:
									openScroll(selectAdver, {}, "0");
									adver.show = {
										isActive0 : true,
										isActive1 : false,
										isActive2 : false,
									}
									break;
								case 1:
									openScroll(selectAdver, {}, "1");
									adver.show = {
										isActive0 : false,
										isActive1 : true,
										isActive2 : false,
									}
									break;
								case 2:
									openScroll(selectAdver, {}, "2");
									adver.show = {
										isActive0 : false,
										isActive1 : false,
										isActive2 : true,
									}
									break;
								}
							}

							// 初始化
							function initData() {
								console.log("初始化页面信息");
								if ($location.path().indexOf('/selectAdver') == 0) {
									openScroll(selectAdver, {}, "0");
									adver.show = {
										isActive0 : true,
										isActive1 : false,
										isActive2 : false,
									}
								} else if ($location.path().indexOf('/myPlace') == 0) {
									openScroll(myPlace, {}, "0");
									adver.show = {
										isActive0 : true,
										isActive1 : false,
										isActive2 : false,
									};
									adver.flag = true;
								} else if ($location.path().indexOf('/updateAd') == 0) {
									var adId = sessionStorage.getItem("adId");
										$('.containerloading').fadeIn(100);
									    $('.overlayer').fadeIn(100);
									services.selectAdverInfo({
										ad_id : adId
									}).success(function(data) {
										if ($('.containerloading').css('display') == 'block'){
											$('.containerloading').fadeOut(100);
										    $('.overlayer').fadeOut(100);
										}
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
app.filter('type', function() {
	return function(input) {
		if (input == "0") {
			type = "旅游";
		} else if (input == "1") {
			type = "招工";
		} else if (input == "2") {
			type = "其他";
		} 
			return type;
	}
});