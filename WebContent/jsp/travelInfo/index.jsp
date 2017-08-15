<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<jsp:include page="/jsp/top.jsp" />
<section id="travelInfoForm" ng-app="travelInfoForm" >
	<div ng-view ></div>
</section>
<jsp:include page="/jsp/footer.jsp" />
<script src="${ctx}/js/app/travelInfo.js"></script>

</body>
</html>