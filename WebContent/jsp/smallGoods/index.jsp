<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<jsp:include page="/jsp/top.jsp" />
<section id="smallGoodsForm" ng-app="smallGoodsForm" >
	<div ng-view class="x-scroll"></div>
</section>
<jsp:include page="/jsp/footer.jsp" />
<script src="${ctx}/js/app/smallGoods.js"></script>

</body>
</html>