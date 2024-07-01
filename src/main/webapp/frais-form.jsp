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
				<li> <a href="<%= request.getContextPath() %>/frais" class="nav-link">Retour</a> </li>
			</ul>
		</nav>
	</header>
	<br>
	<div class="container col-md-7">
		<div class="card">
			<div class="card-body">
			
				<c:if test="${frais != null }">
					<form action="update_frais" method="post">
				</c:if>
									
				<c:if test="${frais == null }">
					<form action="insert_frais" method="post">
				</c:if>
													
					<caption>
						<h2>
							<c:if test="${frais != null }">Modifier le frais</c:if>	
							<c:if test="${frais == null }">Ajouter un nouveau frais</c:if>	
						</h2>
					</caption>
					
					<br>
				
						<input type="hidden" value="<c:out value='${frais.id_frais }'/>" class="form-control" name="id_frais" required="required" />
					 
					 <br>
					
					<fieldset class="form-group">
						<label>Montant 1</label>
						<input type="text" value="<c:out value='${frais.montant_1 }'/>" class="form-control" name="montant_1" required="required" />
					</fieldset>	
					 
					 <br>
					
					<fieldset class="form-group">
						<label>Montant 2</label>
						<input type="text" value="<c:out value='${frais.montant_2 }'/>" class="form-control" name="montant_2" required="required" />
					</fieldset>	
					 
					 <br>
					 
					<fieldset class="form-group">
						<label>Frais</label>
						<input type="text" value="<c:out value='${frais.frais }'/>" class="form-control" name="frais" required="required" />
					</fieldset>	
					
					<br>							
										
					<button type="submit" class="btn btn-success">Save</button>
				</form>
			</div>
		</div>
	</div>
</body>
</html>