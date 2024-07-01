<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<form action="exportPDF" method="get">
    <input type="text" name="num_tel" placeholder="Numéro de téléphone" required />
    <input type="number" name="month" placeholder="Mois" required />
    <input type="number" name="year" placeholder="Année" required />
    <button type="submit">Exporter PDF</button>
</form>
	
	
</body>
</html>