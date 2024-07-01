<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Liste des envois de transferts d'argent</title>
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
                  
                    </ul>
                </div>
            </div>
        </nav>
    </header>    
    <main class="container mt-4">
        <h3 class="text-center">Liste des envois</h3>
        <hr>
        <div class="d-flex justify-content-between mb-3">
            <a href="<%=request.getContextPath()%>/new_envoyer" class="btn btn-success">Ajouter une nouvelle transaction</a>
            <a href="<%=request.getContextPath() %>/envoyer" class="btn btn-outline-info">Actualiser</a>	
        </div>
        
        <!-- Formulaire de recherche -->
            <form action="<%=request.getContextPath()%>/search_envoyer" method="get" class="row g-3 mb-4">
                <div class="col-md-4">
                    <label for="searchType" class="form-label">Type de recherche :</label>
                    <select id="searchType" name="searchType" class="form-select" required>
                        <option value="single" selected>Date unique</option>
                        <option value="range">Période</option>
                    </select>
                </div>
                <div class="col-md-2" id="singleDateField">
                    <label for="singleDate" class="form-label">Date :</label>
                    <input type="date" id="singleDate" name="singleDate" class="form-control">
                </div>
                <div class="col-md-2 d-none" id="dateRangeFields">
                    <label for="startDate" class="form-label">Date de début :</label>
                    <input type="date" id="startDate" name="startDate" class="form-control">
                </div>
                <div class="col-md-2 d-none" id="endDateField">
                    <label for="endDate" class="form-label">Date de fin :</label>
                    <input type="date" id="endDate" name="endDate" class="form-control">
                </div>
                <div class="col-md-2 d-flex align-items-end">
                    <button type="submit" class="btn btn-primary w-100 btn-custom">Rechercher</button>
                </div>
            </form>

        <br>
        <div class="table-responsive">
            <table class="table table-bordered table-hover">
                <thead class="table-light">
                    <tr>
                        <th>ID envoyer</th>
                        <th>Numéro envoyeur</th>
                        <th>Numéro récepteur</th>
                        <th>Montant</th>
                        <th>Date</th>
                        <th>Raison</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="envoyer" items="${listEnvoyer}">
		                <tr>
		                    <td><c:out value="${envoyer.id_env}" /></td>
		                    <td><c:out value="${envoyeurMap[envoyer.num_envoyeur]}" /></td>
		                    <td><c:out value="${recepteurMap[envoyer.num_recepteur]}" /></td>
		                    <td><c:out value="${envoyer.montant}" /></td>
		                    <td><c:out value="${envoyer.date}" /></td>
		                   
		                    <td><c:out value="${envoyer.raison}" /></td>
		                </tr>
		            </c:forEach>
                </tbody>
            </table>
        </div>
    </main>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        document.getElementById('searchType').addEventListener('change', function() {
            var searchType = this.value;
            var singleDateField = document.getElementById('singleDateField');
            var dateRangeFields = document.getElementById('dateRangeFields');
            var endDateField = document.getElementById('endDateField');
            
            if (searchType === 'single') {
                singleDateField.classList.remove('d-none');
                dateRangeFields.classList.add('d-none');
                endDateField.classList.add('d-none');
            } else {
                singleDateField.classList.add('d-none');
                dateRangeFields.classList.remove('d-none');
                endDateField.classList.remove('d-none');
            }
        });
    </script>
</body>
</html>
