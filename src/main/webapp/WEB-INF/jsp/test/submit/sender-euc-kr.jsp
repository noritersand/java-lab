<%@ page language="java" contentType="text/html; charset=euc-kr" pageEncoding="euc-kr"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="euc-kr">
<title>sender EUC-KR</title>
</head>
<body>
<h1>input test</h1>
<form method="get" action="/test/submit/receiver.view" accept-charset="euc-kr">
	<fieldset>
		<legend>GET method</legend>
		<input type="text" name="text" value="�ѱ�" />
		<input type="submit" value="submit" />
	</fieldset>
</form>
<form method="post" action="/test/submit/receiver.view" accept-charset="euc-kr">
	<fieldset>
		<legend>POST method</legend>
		<input type="text" name="text" value="�ѱ�" />
		<input type="submit" value="submit" />
	</fieldset>
</form>
<br><a href="/test/submit/sender.view">UTF-8�� ������</a>
<br><a href="/test/submit/sender-iso-8859-1.view">ISO-8859-1�� ������</a>
</body>
</html>
