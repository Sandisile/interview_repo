<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/WEB-INF/styles.tld" %>
<html>
<head>
	<meta http-equiv="pragma" content="no-cache" />
	<title>EB Styling Example Page</title>
	<s:styles addStyle="styles/eb-example-page-styles.css" />
	
	<s:scripts addScript="js/eb-example-page.js" />
	
</head>
<body id="core-main-portal" style="overflow:hidden!important;" scroll="no">
	<div id="content">
		<h1>
			<a href="<%= request.getContextPath() %>/example/styling/styling-guide.faces">Styling Guide</a>
		</h1>
	</div>
</body>
</html>
