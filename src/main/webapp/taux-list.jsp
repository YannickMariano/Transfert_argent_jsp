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
                <a href="https://www.javaguides.net" class="navbar-brand">Transfert d'argent</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item"><a href="<%= request.getContextPath() %>/client" class="nav-link">Clients</a></li>
                        <li class="nav-item"><a href="<%= request.getContextPath() %>/taux" class="nav-link">Taux</a></li>
                        <li class="nav-item"><a href="<%= request.getContextPath() %>/frais" class="nav-link">Frais</a></li>
                        <li class="nav-item"><a href="<%= request.getContextPath() %>/envoyer" class="nav-link">Envoyer</a></li>
                    </ul>
                </div>
            </div>
        </nav>
    </header>	

    <main class="container mt-4">
        <h3 class="text-center">Liste des Taux</h3>
        <hr>

        <div class="d-flex justify-content-between mb-3">
            <a href="<%=request.getContextPath() %>/new_taux" class="btn btn-success">
                <i class="fas fa-plus-circle"></i> Ajouter un nouveau taux
            </a>
        </div>

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
                            <td><c:out value="${taux.montant1}" /></td>
                            <td><c:out value="${taux.montant2}" /></td>
                            <td>
                                <a href="edit_taux?id=<c:out value='${taux.id}'/>" class="btn btn-primary btn-sm">
                                    <i class="fas fa-edit"></i> Edit
                                </a>
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                <a href="delete_taux?id=<c:out value='${taux.id}'/>" class="btn btn-danger btn-sm">
                                    <i class="fas fa-trash-alt"></i> Delete
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </main>

    <footer>
        <p>&copy; 2024 Transfert d'argent. Tous droits réservés.</p>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/js/all.min.js"></script>
</body>
</html>
