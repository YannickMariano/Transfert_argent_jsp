<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Tranfert d'argent</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <style>
        body {
            font-family: 'Roboto', sans-serif;
            background-color: #f0f0f0;
            display: flex;
            flex-direction: column;
            min-height: 100vh;
        }
        header {
            background-color: #004080;
            color: white;
        }
        .navbar-brand {
            font-size: 1.5rem;
        }
        .navbar-nav .nav-link {
            color: white !important;
        }
        .btn-primary, .btn-success {
            background-color: #009933;
            border: none;
        }
        .btn-primary:hover, .btn-success:hover {
            background-color: #007a2a;
        }
        .btn-outline-info {
            border-color: #004080;
            color: #004080;
        }
        .btn-outline-info:hover {
            background-color: #004080;
            color: white;
        }
        .btn-warning, .btn-danger {
            border: none;
        }
        .btn-warning:hover {
            background-color: #e0a800;
        }
        .btn-danger:hover {
            background-color: #c82333;
        }
        .table-hover tbody tr:hover {
            background-color: #f1f1f1;
        }
        footer {
            background-color: #004080;
            color: white;
            text-align: center;
            padding: 1rem 0;
            margin-top: auto;
        }
    </style>
</head>
<body>
	<header>
        <nav class="navbar navbar-expand-md navbar-dark">
            <div class="container-fluid">
                <a href="#" class="navbar-brand">Transfert d'argent</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item"><a href="<%= request.getContextPath() %>/client" class="nav-link">Retour</a></li>                        
                    </ul>
                </div>
            </div>
        </nav>
    </header>
    
     <main class="container mt-4">
    	
		<h2 class="text-center">Relevé d'opération</h2>
    
	    <p>Contact: <c:out value="${client.numtel}" /></p>
	    <p>Nom: <c:out value="${client.nom}" /></p>
	    <p>Age: 24</p>
	    <p>Sexe: <c:out value="${client.sexe}" /></p>
	    <p>Solde actuel: <c:out value="${client.solde}" /></p>
    
    <div class="table-responsive">
        <table id="transactionsTable" class="table table-bordered table-hover">
            <thead class="table-light">
                <tr>
                    <td>Date</td>
                    <td>Raison</td>
                    <td>Nom du récepteur</td>
                    <td>Montant</td>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="transaction" items="${transactions}">
                    <tr>
                        <td><c:out value="${transaction.date}" /></td>
                        <td><c:out value="${transaction.raison}" /></td>
                        <td><c:out value="${transaction.recepteurNom}" /></td>
                        <td><c:out value="${transaction.montant}" /></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <p id="totalDebit">Total Débit: </p>
    </div>
	    <form action="exportPDF" method="get">
	        <input type="hidden" name="id" value="${client.id}" />
	        <button type="submit" class="btn btn-primary">Télécharger en PDF</button>
	    </form>
    </main>
</body>
<footer>
        <p>&copy; 2024 Transfert d'argent. Tous droits réservés.</p>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/js/all.min.js"></script>
<script>
    // Fonction pour calculer le total des montants
    function calculerTotalDebit() {
        var table = document.getElementById("transactionsTable");
        var totalDebit = 0;
        
        // Parcours des lignes de la table (à partir de la deuxième ligne)
        for (var i = 1; i < table.rows.length; i++) {
            var montantCell = table.rows[i].cells[3]; // La quatrième colonne (index 3) contient le montant
            var montant = parseFloat(montantCell.innerText); // Conversion en nombre
            
            if (!isNaN(montant)) {
                totalDebit += montant; // Ajout du montant au total
            }
        }
        
        // Affichage du total
        var totalDebitElement = document.getElementById("totalDebit");
        totalDebitElement.innerText = "Total Débit: " + totalDebit;
    }
    
    // Appel de la fonction au chargement de la page
    window.onload = function() {
        calculerTotalDebit();
    };
</script>
</html>