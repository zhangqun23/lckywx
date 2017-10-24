<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<jsp:include page="/jsp/top.jsp" />
<section id="truckLoad" class="yscroll"  ng-app="truckLoadForm" >
	<div ng-view></div>
</section>
<jsp:include page="/jsp/footer.jsp" />
<script src="${ctx}/js/app/truckLoad.js"></script>

</body>
</html>