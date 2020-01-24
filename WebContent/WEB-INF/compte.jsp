<%@ include file="menu.jsp" %>
<br><br>

<strong style="font-size: 25px;">Mon profil</strong><br><br>
<br>
<c:if test="${ !empty erreur }">
	<div class="alert alert-danger col-md-4" role="alert">
		<c:out value="${ erreur }" />
	</div>
</c:if>

<c:if test="${ !empty message }">
	<div class="alert alert-success col-md-4" role="alert">
		<c:out value="${ message }" />
	</div>
</c:if>
<br><br>

<c:if test="${ !empty utilisateur }">
<c:forEach items="${ utilisateur }" var="compte">
<form method="post" action="compte">
	<div class="row">
		<div class="col">
			<label><strong>Identifiant : </strong></label>
			<input type="text" name="username" class="form-control col-md-10" value="<c:out value="${ compte.username }" />" readonly />
		</div>
		<div class="col">
			<input type="hidden" name="id_utilisateur" class="form-control col-md-10" value="<c:out value="${ compte.id }" />" />
		</div>
	</div><br>
	<div class="row">
		<div class="col">
			<label><strong>Nom : </strong></label>
			<input type="text" name="nom" class="form-control col-md-10" value="<c:out value="${ compte.nom }" />" />
		</div>
		<div class="col">
			<label><strong>Prénom : </strong></label>
			<input type="text" name="prenom" class="form-control col-md-10" value="<c:out value="${ compte.prenom }" />" />
		</div>
	</div><br>
	<div class="row">
		<div class="col">
			<label><strong>Nouveau mot de passe : </strong></label>
			<input type="password" name="password" class="form-control col-md-10" />
		</div>
		<div class="col">
			<label><strong>Confirmation du mot de passe : </strong></label>
			<input type="password" name="password2" class="form-control col-md-10" />
		</div>
	</div><br><br>
	<div class="row">
		<div class="col">
			<input type="hidden" class="form-control col-md-10" placeholder="" />
		</div>
		<div class="col">
			<input type="submit" value="Valider" class="btn btn-warning col-md-4 offset-md-6" style="color: white;font-weight: bold;" />
		</div>
	</div>
	<input type="hidden" name="id_profil" value="${ compte.id_profil }" />
</form>
<br><br>
</c:forEach>
</c:if>