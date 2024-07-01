<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Transfer d'argent</title>
<link rel="stylesheet"
		href="	https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
</head>
<body>
	
	<header>
		<nav class="navbar navbar-expand-md navbar-dark" style="background-color: #77B5FE">
			<div>
				<a href="https://www.javaguides.net" class="navbar-brand"> Transfer d'argent </a>	
			</div>
			
			<ul class="navbar-nav">
				<li> <a href="<%= request.getContextPath() %>/taux" class="nav-link">Taux</a> </li>
			</ul>
		</nav>
	</header>
	<br>
	<div class="container col-md-7">
		<div class="card">
			<div class="card-body">
			
				<c:if test="${taux != null }">
					<form action="update_taux" method="post">
				</c:if>
									
				<c:if test="${taux == null }">
					<form action="insert_taux" method="post">
				</c:if>
													
					<caption>
						<h2>
							<c:if test="${taux != null }">Modifier le Taux</c:if>	
							<c:if test="${taux == null }">Ajouter un nouveau taux</c:if>	
						</h2>
					</caption>
					
					<br>
					
					<fieldset class="form-group">
						<input type="hidden" value="<c:out value='${taux.id }'/>" class="form-control" name="id"/>
					</fieldset>
					
					<br>
					
					<fieldset class="form-group">
						<label>Montant 1</label>
						<input type="text" value="<c:out value='${taux.montant1 }'/>" class="form-control" name="montant1" required="required" />
					</fieldset>	 
					 
					 <br>
					 
					<fieldset class="form-group">
						<label>Montant 2</label>
						<input type="text" value="<c:out value='${taux.montant2 }'/>" class="form-control" name="montant2" required="required" />
					</fieldset>	
										
					
					<br>
					
					<button type="submit" class="btn btn-success">Save</button>
				</form>
			</div>
		</div>
	</div>
</body>
</html>