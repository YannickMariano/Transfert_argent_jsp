<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Transfert d'argent</title>

<link rel="stylesheet"
		href="	https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
		
</head>
<body>

	<header>
        <nav class="navbar navbar-expand-md navbar-dark" style="background-color: #77B5FE">
            <div class="container-fluid">
                <a href="https://www.javaguides.net" class="navbar-brand">Transfert d'argent</a> 
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item"> <a href="<%= request.getContextPath() %>/client" class="nav-link">Clients</a> </li>
                        <li class="nav-item"> <a href="<%= request.getContextPath() %>/taux" class="nav-link">Taux</a> </li>
                        <li class="nav-item"> <a href="<%= request.getContextPath() %>/frais" class="nav-link">Frais</a> </li>
                        <li class="nav-item"> <a href="<%= request.getContextPath() %>/envoyer" class="nav-link">Envoyer</a> </li>
                        
                    </ul>
                </div>
            </div>
        </nav>
    </header>	
		<!-- <div class="alert alert-success" *ngif='message'>{{message}}</div> -->
		
		<main class="container mt-4"> 	
			<h3 class="text-center">Liste des Taux</h3>
			<hr>
			
			<div class="d-flex justify-content-between mb-3">
				<a href="<%=request.getContextPath() %>/new_taux" class="btn btn-success">Ajouter un nouveau taux</a>
			</div>
			
			<br>
			
			<div class="table-responsive">
			<table class="table table-bordered table-hover">
				<thead class="table-light">
					<tr>
						<th>Id</th>
						<th>Montant 1</th>
						<th>Montant 2</th>
						<th>Action</th>
					</tr>
				</thead>
				
				<tbody>
					<c:forEach var="taux" items="${listTaux}">	
						<tr>
							<td><c:out value='${taux.id}'/></td>
							<td><c:out value="${taux.montant1 }" /></td>
							<td><c:out value="${taux.montant2 }" /></td>
							
							<td><a href="edit_taux?id=<c:out value='${taux.id}'/>">Edit</a>
								&nbsp;&nbsp;&nbsp;&nbsp;
								<a href="delete_taux?id=<c:out value='${taux.id}'/>" style="color: red">Delete</a>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</main>
</body>
</html>