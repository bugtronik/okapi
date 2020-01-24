<%@ include file="menu.jsp" %>

<br><br>
<strong style="font-size: 25px;">Gestion des affectations</strong>
<br><br><br>
<div class="alert alert-success" role="alert" style="display: none;text-align: center;" id="message_success">
	<strong>Opération terminée</strong>
</div>
<br><br>
<form id="formAffectation" method="post" action="affectation">
	<div class="row">
		<div class="col">
			<label><strong>Indicateur : </strong></label>
			<select name="id_indicateur" class="form-control col-md-10">
				<option></option>
				<c:choose>
					<c:when test="${ !empty indicateurs }">
						<c:forEach items="${ indicateurs }" var="indicateur">
							<option value="${ indicateur.id }"><c:out value="${ indicateur.libelle }" /></option>
						</c:forEach>
					</c:when>
				</c:choose>
			</select>
		</div>
		<div class="col">
			<label><strong>Utilisateur : </strong></label>
			<select name="id_utilisateur" class="form-control col-md-10">
				<option></option>
				<c:choose>
					<c:when test="${ !empty utilisateurs }">
						<c:forEach items="${ utilisateurs }" var="utilisateur">
							<option value="${ utilisateur.id }"><c:out value="${ utilisateur.nom }" /> <c:out value="${ utilisateur.prenom }" /></option>
						</c:forEach>
					</c:when>
				</c:choose>
			</select>
		</div>
	</div><br><br>
	
	<div class="row">
		<div class="col">
			<input type="hidden" class="form-control col-md-10" placeholder="" />
		</div>
		<div class="col">
			<input type="submit" id="validation" value="Valider" class="btn btn-success col-md-4 offset-md-6" style="color: white;font-weight: bold;" />
		</div>
	</div>
</form>
<br><br>
<div id="indicateurs">
<table class="table display table-bordered" id="liste_indicateurs">
	<thead>
		<tr style="text-align: center;background-color: #7ED957;color: white;">
			<th>Indicateur</th>
			<th>Utilisateur</th>
			<th>Action</th>
		</tr>
	</thead>
	<tbody class="table-hover">
	
	</tbody>
</table>
</div>

<script src="affectation.js"></script>
<script>
	/*
	$('#validation').click(function() {
	  $('#indicateurs').load('maj_affectation.jsp', function() {
	    alert('Affectation terminée');
	  });
	});
	*/
</script>
