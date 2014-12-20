<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>AIFC VALIDATOR by UT-DALLAS</title>
</head>
<body>
<%@ page import ="utd.aifc.validator.Configuration" %>
<%@ page import ="utd.aifc.validator.DBConnector" %>
<%
Configuration.load();
DBConnector.getConnectionToDB(Configuration.prop.getProperty("current-domain"));
%>
<h1>The Validator is running</h1>
</body>
</html>