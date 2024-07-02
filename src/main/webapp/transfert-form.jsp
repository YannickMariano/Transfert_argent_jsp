<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Tranfert d'argent</title>
<link rel="stylesheet"
		href="	https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
</head>
<body>
<header>
		<nav class="navbar navbar-expand-md navbar-dark" style="background-color: #77B5FE">
			<div>
				<a href="https://www.javaguides.net" class="navbar-brand"> Transfert d'argent </a>	
			</div>
			
			<ul class="navbar-nav">
				<li> <a href="<%= request.getContextPath() %>/client" class="nav-link">Clients</a> </li>
			</ul>
			<ul class="navbar-nav">
				<li> <a href="<%= request.getContextPath() %>/taux" class="nav-link">Taux</a> </li>
			</ul>
			<ul class="navbar-nav">
				<li> <a href="<%= request.getContextPath() %>/frais" class="nav-link">Frais</a> </li>
			</ul>
			<ul class="navbar-nav">
				<li> <a href="<%= request.getContextPath() %>/envoyer" class="nav-link">Envoyer</a> </li>
			</ul>
			<ul class="navbar-nav">
				<li> <a href="<%= request.getContextPath() %>/transfert-form.jsp" class="nav-link">Transfert</a> </li>
			</ul>			
		</nav>
	</header>	
	<h2>Transfert de Fonds</h2>
    <form action="transfert" method="post">
        <table>
            <tr>
                <td>Numéro de téléphone de l'envoyeur :</td>
                <td><input type="text" name="senderNumTel" required></td>
            </tr>
            <tr>
                <td>Numéro de téléphone du receveur :</td>
                <td><input type="text" name="receiverNumTel" required></td>
            </tr>
            <tr>
                <td>Montant :</td>
                <td><input type="number" name="amount" required></td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit" value="Transférer">
                </td>
            </tr>
        </table>
    </form>
</body>
</html>