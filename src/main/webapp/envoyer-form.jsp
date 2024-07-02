<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <title>Transfert d'argent</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
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
            <div>
                <a href="https://www.javaguides.net" class="navbar-brand">Transfert d'argent</a>
            </div>
            <ul class="navbar-nav">
                <li><a href="<%=request.getContextPath()%>/envoyer" class="nav-link">Liste envoie</a></li>
            </ul>
        </nav>
    </header>
    <br>
    <div class="container col-md-5">
        <div class="card">
            <div class="card-body">
                <c:choose>
                    <c:when test="${envoyer != null}">
                        <form action="<c:url value='/update_envoyer' />" method="post">
                            <h2>Modifier la transaction</h2>
                            <input type="hidden" name="id_env" value="<c:out value='${envoyer.id_env}' />" />
                    </c:when>
                    <c:otherwise>
                        <form action="<c:url value='/insert_envoyer' />" method="post">
                            <h2>Ajouter une nouvelle transaction</h2>
                    </c:otherwise>
                </c:choose>
                         
                <fieldset class="form-group mb-3">
                    <label>Numéro de l'envoyeur</label>
                    <select class="form-control" name="num_envoyeur" required="required">
                        <c:forEach var="client" items="${listClient}">
                            <option value="${client.id}">${client.numtel}</option>
                        </c:forEach>
                    </select>
                </fieldset>
                <fieldset class="form-group mb-3">
                    <label>Numéro du récepteur</label>
                    <select class="form-control" name="num_recepteur" required="required">
                        <c:forEach var="client" items="${listClient}">
                            <option value="${client.id}">${client.numtel}</option>
                        </c:forEach>
                    </select>
                </fieldset>
                <fieldset class="form-group mb-3">
                    <label>Montant</label>
                    <input type="number" value="<c:out value='${envoyer.montant}' />" class="form-control" name="montant" required="required">
                </fieldset>
                <fieldset class="form-group mb-3">
                    <label>Raison</label>
                    <input type="text" value="<c:out value='${envoyer.raison}' />" class="form-control" name="raison" required="required">
                </fieldset>
                
                <button type="submit" class="btn btn-success">
                    <i class="fas fa-save"></i> Sauvegarder
                </button>
                
                </form>
            </div>
        </div>
    </div>
    <footer>
        <p>&copy; 2024 Transfert d'argent App. Tous droits réservés.</p>
    </footer>
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGaXmrh97jf7fbJ3oZPMT3qPxM" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy7WqaHW8OHc8HUs0l7r23bB56dI+HcuMcz9KU" crossorigin="anonymous"></script>
</body>
</html>
