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
    	<link href="css//jquery-ui/jquery-ui.css" rel="stylesheet" />
    	<link href="datatables/DataTables-1.10.20/css/jquery.dataTables.min.css" rel="stylesheet" />
    	<link href="css/fontawesome/css/font-awesome.css" rel="stylesheet" />
    	<link href="css/connexion.css" rel="stylesheet" />
    	<link href="css/police.css" rel="stylesheet" />
    	<link rel="icon" href="favicon.ico" />
    	<script src="jquery.chained.js"></script>
    	<script src="jquery-ui.js"></script>
    	<script src="vue.js"></script>
	</head>
<body style="font-family: 'AbrilFatface-Regular';">
<c:if test="${ !empty erreur }">
	<div class="alert alert-danger">
		<strong>
			<c:out value="${ erreur }" />
		</strong>
	</div>
</c:if>

<div class="container-fluid">

	<div class="row">
		<div class="col-md-6">
			<img src="okapi_bleu.png" style="position: absolute; left: 0px;" width="100%" />
			<strong class="offset-md-1" style="color: white;font-size: 30px;position: absolute; top: 120px;">Bienvenue sur</strong>
			<strong class="offset-md-1" style="color: white;position: absolute;bottom: -190px;">Le résultat d'aujourd'hui, la performance de demain</strong>
		</div>
		<div class="col-md-6">
		<img src="Entete_setrag.jpg" class="col-md-3 offset-md-9" />
			<form method="post" action="connexion" class="col-md-8">
				<div class="">
					<strong style="color: #38B6FF; font-size: 25px;">Connexion</strong>
				</div><br>
	        	<div class="input-group form-group" class="username-form">
	                        	<div class="input-group-prepend">
							    	<span class="input-group-text" id="basic-addon1">
							    		<i class="fa fa-user" aria-hidden="true"></i>
							    	</span>
							  	</div>
	                            <input type="text" required name="username" class="form-control" placeholder="Identifiant *" />
	                        </div>
	                        <div class="input-group form-group">
	                        	<div class="input-group-prepend">
							    	<span class="input-group-text" id="basic-addon1">
							    		<i class="fa fa-lock" aria-hidden="true"></i>
							    	</span>
							  	</div>
	                            <input type="password" required name="password" class="form-control" placeholder="Mot de passe *" />
	                        </div>
	                        <div class="form-group">
	                            <input type="submit" style="background-color: #38B6FF; color:white;" class="btnSubmit" value="Se connecter" />
	                        </div>
	         </form>
		</div>
</div>

</div>
</body>
</html>