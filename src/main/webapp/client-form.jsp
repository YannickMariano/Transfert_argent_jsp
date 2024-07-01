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
				<li> <a href="<%= request.getContextPath() %>/client" class="nav-link">Retour</a> </li>
			</ul>
		</nav>
	</header>
	<br>
	<div class="container col-md-7">
		<div class="card">
			<div class="card-body">
			
				<c:if test="${client != null }">
					<form action="update" method="post">
				</c:if>
									
				<c:if test="${client == null }">
					<form action="insert" method="post">
				</c:if>
													
					<caption>
						<h2>
							<c:if test="${client != null }">Modifier le client</c:if>	
							<c:if test="${client == null }">Ajouter un nouveau client</c:if>	
						</h2>
					</caption>
					
					<br>
					
					<input type="hidden" value="<c:out value='${client.id }'/>" class="form-control" name="id"/>
										
					<fieldset class="form-group">
						<label>Numéro télephone</label>
						<input type="text" value="<c:out value='${client.numtel }'/>" class="form-control" name="numtel" required="required" />
					</fieldset>	
					 
					 <br>
					 
					<fieldset class="form-group">
						<label>Nom</label>
						<input type="text" value="<c:out value='${client.nom }'/>" class="form-control" name="nom" required="required" />
					</fieldset>	
					
					<br>
					
					<!-- <fieldset class="form-group">
						<label>Sexe</label>
						<input type="text" value="<c:out value='${client.sexe }'/>" class="form-control" name="sexe" required="required" />
					</fieldset>  -->	
					
					<fieldset class="mb-3">
						<label>Sexe</label>
						<select name="sexe" class="form-select">
	                        <option value="Feminin" ${client != null && client.sexe == 'Feminin' ? 'selected' : ''}>Feminin</option>
	                        <option value="Masculin" ${client != null && client.sexe == 'Masculin' ? 'selected' : ''}>Masculin</option>
	                        <option value="Personnel" ${client != null && client.sexe == 'Personnel' ? 'selected' : ''}>Personnel</option>
                    	</select>
					</fieldset>
					
					<!-- <fieldset class="form-group">
						<label>Pays</label>
						<input type="text" value="<c:out value='${client.pays }'/>" class="form-control" name="pays" required="required" />
					</fieldset>	 -->
					
					<fieldset class="mb-3">
						<label>Pays</label>
						<select name="pays" class="form-select">
	                        <option value="Madagascar" ${client != null && client.pays == 'Madagascar' ? 'selected' : ''}>Madagascar</option>
	                        <option value="France" ${client != null && client.pays == 'France' ? 'selected' : ''}>France</option>
	                      
                    	</select>
					</fieldset>
					
					<fieldset class="form-group">
						<label>Solde</label>
						<input type="text" value="<c:out value='${client.solde }'/>" class="form-control" name="solde" required="required" />
					</fieldset>	
					
					<fieldset class="form-group">
						<label>Mail</label>
						<input type="email" value="<c:out value='${client.mail }'/>" class="form-control" name="mail" required="required" />
					</fieldset>	
					
					<br>
					
					<button type="submit" class="btn btn-success">Save</button>
				</form>
			</div>
		</div>
	</div>
</body>
</html>