<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<jsp:include page="/jsp/top.jsp" />
<section id="busNeedForm"  ng-app="busNeedForm"  class="yscroll">
	<div ng-view ></div>
</section>
<jsp:include page="/jsp/footer.jsp" />
<script src="${ctx}/js/app/busNeed.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/My97DatePicker/WdatePicker.js"></script>
</body>
</html>