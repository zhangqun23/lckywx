<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<jsp:include page="/jsp/top.jsp" />
<section id="platformForm" ng-app="platformForm" >
	<div>
	        <article style="margin-top: 42px">
            <div class="weui_cells weui_cells_access animated fadeInRight">
            
              <a class="weui_cell" href="/lckywx/routeController/toBusNeedPage.do#/busNeedList">
                <div class="weui_cell_bd weui_cell_primary">
                    <p><i class="iconfont">&#xe61f;</i>班车定制</p>
                </div>
                <div class="iconfont">&#xe642;</div>
              </a>
              
              <a class="weui_cell" href="/lckywx/routeController/toSmallGoodsListPage.do#/smallGoodsList">
                <div class="weui_cell_bd weui_cell_primary">
                    <p><i class="iconfont">&#xe61f;</i>小件货运</p>
                </div>
                <div class="iconfont">&#xe642;</div>
              </a>
              
              <a class="weui_cell" href="/lckywx/routeController/toTruckGoodsPage.do#/myTruckPublish0">
                <div class="weui_cell_bd weui_cell_primary">
                    <p><i class="iconfont">&#xe61f;</i>零担发布</p>
                </div>
                <div class="iconfont">&#xe642;</div>
              </a>
              
			  <a class="weui_cell" href="/lckywx/routeController/toSelectAdListPage.do#/myPlace">
                <div class="weui_cell_bd weui_cell_primary">
                    <p><i class="iconfont">&#xe61f;</i>广告发布</p>

                </div>
                <div class="iconfont">&#xe642;</div>
              </a>
             
            </div>
        </article>
	</div>
</section>
<jsp:include page="/jsp/footer.jsp" />
<script src="${ctx}/js/app/platform.js"></script>

</body>
</html>