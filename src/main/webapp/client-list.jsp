<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Transfert d'argent</title>

<link rel="stylesheet"
    href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
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
                      	<!-- <li class="nav-item"> <a href="<%= request.getContextPath() %>/ExportPDF.jsp" class="nav-link">PDF</a> </li> -->
                    </ul>
                </div>
            </div>
        </nav>
    </header>	

    <main class="container mt-4">
        <h3 class="text-center">Liste des clients</h3>
        <hr>

        <div class="d-flex justify-content-between mb-3">		    
	        <a href="<%=request.getContextPath() %>/new" class="btn btn-success">Ajouter un nouveau client</a>
	        <a href="<%=request.getContextPath() %>/client" class="btn btn-outline-info">Actualiser</a>		    
		</div>


        <br>

        <div class="d-flex justify-content-between mb-3">
		    <form action="<%= request.getContextPath() %>/search" method="post" class="row g-3 align-items-center">
		        <div class="col-auto">
		            <label for="nom" class="col-form-label">Recherche :</label>
		        </div>
		        <div class="col-auto">
		            <input type="text" id="nom" name="nom" class="form-control" placeholder="Entrez le nom" required>
		        </div>
		        <div class="col-auto">
		            <input type="submit" value="Rechercher" class="btn btn-primary">
		        </div>
		    </form>
		</div>


        <br>
		<div class="table-responsive">
	        <table class="table table-bordered table-hover">
	            <thead class="table-light">
	                <tr>
	                    <th>Id</th>
	                    <th>Numéro Télephone</th>
	                    <th>Nom</th>
	                    <th>Sexe</th>
	                    <th>Pays</th>
	                    <th>Solde</th>
	                    <th>Mail</th>
	                    <th>Action</th>
	                </tr>
	            </thead>	
	            <tbody>
	                <c:forEach var="client" items="${listClient}">
	                    <tr>
	                        <td><c:out value="${client.id}" /></td>
	                        <td><c:out value="${client.numtel}" /></td>
	                        <td><c:out value="${client.nom}" /></td>
	                        <td><c:out value="${client.sexe}" /></td>
	                        <td><c:out value="${client.pays}" /></td>
	                        <td><c:out value="${client.solde}" /></td>
	                        <td><c:out value="${client.mail}" /></td>
								
	                        <td>
	                        	<!-- <a style="color: orange" href="info?num_tel=${client.numtel}">Info</a> -->
	                        	<!-- <a style="color: orange" href="ClientServlet?action=infoClient&id=${client.id}">Info</a> -->
	                        	<a style="color: orange" href="info?id=<c:out value='${client.id}'/>">Info</a>
	                        	&nbsp;&nbsp;&nbsp;&nbsp;
	                        	<a href="edit?id=<c:out value='${client.id}'/>">Edit</a>
	                            &nbsp;&nbsp;&nbsp;&nbsp;
	                            <a href="delete?id=<c:out value='${client.id}'/>" style="color: red">Delete</a>
	                        </td>
	                    </tr>
	                </c:forEach>
	            </tbody>
	        </table>
        </div>
    </main>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
