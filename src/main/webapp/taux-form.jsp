<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Transfert d'argent</title>
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
            <a href="https://www.javaguides.net" class="navbar-brand">Transfert d'argent</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item"><a href="<%= request.getContextPath() %>/taux" class="nav-link">Taux</a></li>
                </ul>
            </div>
        </div>
    </nav>
</header>

<br>
<div class="container col-md-7">
    <div class="card">
        <div class="card-body">

            <c:if test="${taux != null}">
                <form action="update_taux" method="post">
            </c:if>

            <c:if test="${taux == null}">
                <form action="insert_taux" method="post">
            </c:if>

                <caption>
                    <h2>
                        <c:if test="${taux != null}">Modifier le Taux</c:if>
                        <c:if test="${taux == null}">Ajouter un nouveau taux</c:if>
                    </h2>
                </caption>

                <br>

                <fieldset class="form-group">
                    <input type="hidden" value="<c:out value='${taux.id}'/>" class="form-control" name="id"/>
                </fieldset>

                <br>

                <fieldset class="form-group">
                    <label>Montant 1</label>
                    <input type="text" value="<c:out value='${taux.montant1}'/>" class="form-control" name="montant1" required="required" />
                </fieldset>

                <br>

                <fieldset class="form-group">
                    <label>Montant 2</label>
                    <input type="text" value="<c:out value='${taux.montant2}'/>" class="form-control" name="montant2" required="required" />
                </fieldset>

                <br>

                <button type="submit" class="btn btn-success">
                    <i class="fas fa-save"></i> Save
                </button>
            </form>
        </div>
    </div>
</div>
</body>
</html>
