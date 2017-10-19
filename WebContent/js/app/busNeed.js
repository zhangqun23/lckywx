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
	}).when('/busNeedTest', {
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
app.controller('BusNeedInfoController', [
		'$scope',
		'services',
		'$location',
		function($scope, services, $location) {
			var busNeed = $scope;
			busNeed.BusLimit = {
				bune_tel : "",
				bune_num : "",
				bune_time : "1",
				bune_gath_time : getNowDate(),
				bune_gath_pla : "洛川",
				bune_goal_pla : "",
				bune_purp : "",
				bune_remark : ""
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
			// zq添加班车需求
			busNeed.addBusNeed = function() {
				var busLimit = JSON.stringify(busNeed.BusLimit);
				services.addBusNeed({
					busNeed : busLimit
				}).success(function(data) {
					sessionStorage.setItem("busNeedId", data.result.bune_id);
					$location.path("busNeedList");
				});
			}
			// zq查询班车需求列表
			busNeed.selectBusNeeds = function() {
				busNeed.busNeedList = [];
				var state = sessionStorage.getItem("busNeedState");
				openScroll(getBusNeedsList, {}, state);
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
			// 修改分栏
			busNeed.changeBar = function(state) {
				busNeed.busNeedList = [];
				sessionStorage.setItem("busNeedState", state);
				busNeed.startDate = "";
				busNeed.endDate = "";
				switch (state) {
				case 0:
					busNeed.show = {
						isActive0 : true,
						isActive1 : false
					}

					break;
				case 1:
					busNeed.show = {
						isActive0 : false,
						isActive1 : true
					}
					break;
				}

				openScroll(getBusNeedsList, {}, state);
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
					clientHeight = Math.min(document.body.clientHeight,
							document.documentElement.clientHeight);
				} else {
					clientHeight = Math.max(document.body.clientHeight,
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
							getDate && getDate(config, counter, state);
						}
					}
				});
			}
			// zq滚动查看班车需求列表
			function getBusNeedsList(config, counter, state) {
				config.isAjax = true;
				services.selectBusNeeds({
					state : state,
					page : counter,
					startDate : busNeed.startDate,
					endDate : busNeed.endDate
				}).success(
						function(data) {
							if (!busNeed.busNeedList) {
								busNeed.busNeedList = [];
							}
							busNeed.busNeedList = busNeed.busNeedList
									.concat(data.list);
							console.log(data.list);
							config.isAjax = false;
							if (data.list == ![]) {
								$(".limitHint").css('display', 'block');
								config.isEnd = true;
							}
						});
			}

			// zq初始化
			function initPage() {
				console.log("初始化页面信息");
				if ($location.path().indexOf('/busNeedIndex') == 0) {

				} else if ($location.path().indexOf('/busNeedList') == 0) {
					openScroll(getBusNeedsList, {}, 0);
					sessionStorage.setItem("busNeedState", 0);
				} else if ($location.path().indexOf('/busNeedInfo') == 0) {
					var busNeedId = sessionStorage.getItem("busNeedId");
					alert(busNeedId);
					busNeed.selectBusNeedById(busNeedId);
				} else if ($location.path().indexOf('/busNeedTest') == 0) {
					busNeed.show = {
						isActive0 : true,
						isActive1 : false
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
// 班车需求状态的过滤
app.filter('butrState', function() {
	return function(input) {
		var type = "";
		if (input) {
			type = "已完成";
		} else {
			type = "进行中";
		}

		return type;
	}
});