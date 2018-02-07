<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<jsp:include page="/jsp/top.jsp" />
<section id="myTradeForm"  ng-app="myTradeForm" >
	<div ng-view  class="yscroll"></div>
</section>
<jsp:include page="/jsp/footer.jsp" />
<script src="${ctx}/js/app/myTrade.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/My97DatePicker/WdatePicker.js"></script>
</body>
</html>