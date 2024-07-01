<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <title>User Management Application</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>
    <header>
        <nav class="navbar navbar-expand-md navbar-dark" style="background-color: #77B5FE">
            <div>
                <a href="https://www.javaguides.net" class="navbar-brand"> User Management App </a>
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
                        <h2>Modifier de la transaction</h2>
                        <input type="hidden" name="id_env" value="<c:out value='${envoyer.id_env}' />" />
                </c:when>
                <c:otherwise>
                    <form action="<c:url value='/insert_envoyer' />" method="post">
                        <h2>Ajouter un nouveau transaction</h2>
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
                
                <button type="submit" class="btn btn-success">Sauvegarder</button>
                                
                </form>
                
               
            </div>
        </div>
    </div>
</body>
</html>