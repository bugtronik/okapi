<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8" />
		<title>KPI</title>
		<script src="jquery.js"></script>
		<script src="datatables/DataTables-1.10.20/js/jquery.dataTables.min.js"></script>
		<script src="css/bootstrap/dist/js/bootstrap.min.js"></script>
    	<link href="css/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet" />
    	<link id="ui-theme" href="css/jquery-ui/jquery-ui.css" rel="stylesheet" />
    	<link href="datatables/DataTables-1.10.20/css/jquery.dataTables.min.css" rel="stylesheet" />
    	<link href="css/fontawesome/css/font-awesome.css" rel="stylesheet" />
    	
    	<link href="css/police.css" rel="stylesheet" />
    	<link rel="icon" href="favicon.ico" />
    	<script src="jquery.chained.js"></script>
    	<script src="jquery-ui.js"></script>
    	
    	<style>
    		.container1 {
			  width: 40%;
			  margin: 20px;
			}
			 
			.container2 {
			  width: 50%;
			  margin: 20px;
			}
			 
			.container3 {
			  width: 60%;
			  margin: 20px;
			}
			 
			.message2 {
			  font-size: 13px;
			  font-family: Arial, sans-serif;
			  letter-spacing: 1px;
			}
    	</style>
	</head>
<body style="font-family: 'AbrilFatface-Regular';">
<div class="container">
<nav class="navbar navbar-light" style="background-color: #38B6FF;">
  <a class="navbar-brand" href="accueil" title="Accueil">
  	<img src="icone_bleu.jpg"/>
  </a>
  <a class="nav-link" href="accueil" style="color: white;"><strong>Accueil</strong> <span class="sr-only">(current)</span></a>
  <div class="dropdown">
  <a class="dropdown-toggle" style="color: white;" href="#" role="button" id="kpi" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
   <strong>KPI</strong>
  </a>
  <div class="dropdown-menu" aria-labelledby="kpi" style="background-color: #38B6FF;">
  	<c:if test="${ sessionScope.profil == 'Administrateur' || sessionScope.profil == 'Collaborateur'}">
  	<a class="dropdown-item" href="saisie" style="color: white;" onmouseover="this.style.color='#38B6FF';" onmouseout="this.style.color='#FFFFFF'"><strong>Saisie</strong></a>
  	</c:if>
  	<a class="dropdown-item" href="consultation" style="color: white;" onmouseover="this.style.color='#38B6FF';" onmouseout="this.style.color='#FFFFFF'"><strong>Consultation</strong></a>
  </div>
  </div>
  <div class="dropdown">
  <a class="dropdown-toggle" style="color: white;" href="#" role="button" id="objectif" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
   <strong>Objectifs</strong>
  </a>
  <div class="dropdown-menu" aria-labelledby="objectif" style="background-color: #38B6FF;">
  	<c:if test="${ sessionScope.profil == 'Administrateur' || sessionScope.profil == 'Collaborateur'}">
    <a class="dropdown-item" style="color: white;" onmouseover="this.style.color='#38B6FF';" onmouseout="this.style.color='#FFFFFF'" href="http://10.120.21.145:8124/okapi-import/index.php"><strong>Import</strong></a>
    </c:if>
    <a class="dropdown-item" style="color: white;" href="consultation-objectif" onmouseover="this.style.color='#38B6FF';" onmouseout="this.style.color='#FFFFFF'"><strong>Consultation</strong></a>
    <!-- <a class="dropdown-item" href="#">Privilèges</a> -->
  </div>
  </div>
  <c:if test="${ sessionScope.profil == 'Administrateur'}">
  	<div class="dropdown">
  <a class="dropdown-toggle" style="color: white;" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
   <strong>Référentiel</strong>
  </a>

  <div class="dropdown-menu" aria-labelledby="dropdownMenuLink" style="background-color: #38B6FF;">
    <a class="dropdown-item" href="indicateur" style="color: white;" onmouseover="this.style.color='#38B6FF';" onmouseout="this.style.color='#FFFFFF'"><strong>Indicateur</strong></a>
    <a class="dropdown-item" href="affectation" style="color: white;" onmouseover="this.style.color='#38B6FF';" onmouseout="this.style.color='#FFFFFF'"><strong>Affectation</strong></a>
    <a class="dropdown-item" href="categorie" style="color: white;" onmouseover="this.style.color='#38B6FF';" onmouseout="this.style.color='#FFFFFF'"><strong>Catégories</strong></a>
    <a class="dropdown-item" href="souscategorie" style="color: white;" onmouseover="this.style.color='#38B6FF';" onmouseout="this.style.color='#FFFFFF'"><strong>Sous-catégories</strong></a>
    <a class="dropdown-item" href="frequence" style="color: white;" onmouseover="this.style.color='#38B6FF';" onmouseout="this.style.color='#FFFFFF'"><strong>Fréquences</strong></a>
    <a class="dropdown-item" href="unite" style="color: white;" onmouseover="this.style.color='#38B6FF';" onmouseout="this.style.color='#FFFFFF'"><strong>Unités</strong></a>
    <a class="dropdown-item" href="service" style="color: white;" onmouseover="this.style.color='#38B6FF';" onmouseout="this.style.color='#FFFFFF'"><strong>Services</strong></a>
    <a class="dropdown-item" href="utilisateur" style="color: white;" onmouseover="this.style.color='#38B6FF';" onmouseout="this.style.color='#FFFFFF'"><strong>Utilisateurs</strong></a>
    <a class="dropdown-item" href="profil" style="color: white;" onmouseover="this.style.color='#38B6FF';" onmouseout="this.style.color='#FFFFFF'"><strong>Profil</strong></a>
    <!-- <a class="dropdown-item" href="#">Privilèges</a> -->
  </div>
</div>
  </c:if>
  <div class="form-inline">
  <a href="compte" style="color: white;" title="Mon compte" class="nav-link"><i class="fa fa-user-circle" aria-hidden="true"></i></a>
  <a class="nav-link" href="deconnexion"><strong style="color: red;"><i class="fa fa-power-off" aria-hidden="true" title="Déconnexion"></i>
</strong></a>
</div>
</nav>
