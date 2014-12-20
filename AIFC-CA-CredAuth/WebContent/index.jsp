<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>AIFC-CA by UT-DALLAS</title>
</head>
<body>
	<h1>Hello,, This is AIFC-CA</h1>
	<%@ page import="utd.aifc.credauth.CAConfiguration"%>
	<%@ page import="utd.aifc.credauth.CADBConnector"%>
	<%
CAConfiguration.load();
CADBConnector.getConnectionToDB(CAConfiguration.prop.getProperty("current-domain"));
%>

</body>
</html>